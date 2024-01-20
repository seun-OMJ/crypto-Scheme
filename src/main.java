import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class main {
    public static void main(String[] args) {

        int [] message = new int[16];
        try {// opening message file
            //constructor of File class having file as argument
            File file1 = new File(args[0]);
            //creates a buffer reader input stream
            BufferedReader br = new BufferedReader(new FileReader(file1));

            String r;
            String g;

            while ((r = br.readLine()) != null){
                //process the line as required

                g = r;
                String [] temp  = g.split(" ");
                for(int i = 0; i < temp.length; i++){
                    message[i] = Integer.parseInt(temp[i], 16);
                }

            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }
        int [] key = new int[16];
        try { //opening key file
            //constructor of File class having file as argument
            File file2 = new File(args[1]);
            //creates a buffer reader input stream
            BufferedReader br = new BufferedReader(new FileReader(file2));

            String r;
            String g;

            while ((r = br.readLine()) != null){
                //process the line as required

                g = r;
                String [] temp  = g.split(" ");
                for(int i = 0; i < temp.length; i++){
                    key[i] = Integer.parseInt(temp[i], 16);
                }

            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("\nPlaintext");
        for(int i = 0; i < message.length; i++){
            System.out.print(String.format("%02x", message[i]));
        }
        System.out.println("\nKey");
        for(int i = 0; i < key.length; i++){
            System.out.print(String.format("%02x", key[i]));
        }




        int [] expandedKey = new int[176];
         genKey(key, expandedKey);


         encrypt(message, expandedKey);
        System.out.println("\n\n");
        decrypt(message,expandedKey);
        System.out.println("\n\nEnd of Processing \n");

    }




    //-----------------------------------------------------------------------
    //PURPOSE:calls the key expansion algorithm that takes a key and expands it into the original key plus 10 times the length
    //INPUT PARAMETER:
    //int[]key: an integer array holding the original key
    //int [] expandedKey: the array to store the expanded key
    //OUTPUT PARAMETER:
    //No output parameter
    //-----------------------------------------------------------------------
    public static void genKey(int[] key, int[] expandedKey){
        KeyExpansion.keyExpansion(key, expandedKey);

        System.out.println("\nKey Schedule:");
        for(int i = 0; i < expandedKey.length; i++){
            System.out.print(String.format("%02x", expandedKey[i]));
            if((i+1) % 4== 0 && (i+1) % 16!= 0){
                System.out.print(",");
            }
            if((i+1) % 16 == 0){
                System.out.println();
            }
        }
    }

    //-----------------------------------------------------------------------
    //PURPOSE:encrypts the given message using the provided key
    //INPUT PARAMETER:
    //int[] message: the array containing the message to be encrypted
    //int [] expandedKey: the array containing the expanded key
    //OUTPUT PARAMETER:
    //No output parameter
    //-----------------------------------------------------------------------
    public static void encrypt(int[] message, int[] expandedKey){
        System.out.println("\nENCRYPTION PROCESS \n------------------\nPlain Text:");

        int[] state = new int[16];

        for(int i = 0; i < state.length; i++){
            System.out.print(String.format("%02x", message[i])+ " ");
            if((i+1)%4 == 0){
                System.out.print("  ");
            }
            state[i] = message[i];
        }
        int numberOfRounds = 9;
        int [] rkey = new int[16];

        for(int i = 0; i < rkey.length; i++){// copying the needed round key
            rkey[i] = expandedKey[i];}
        KeyExpansion.addRoundKey(state, rkey);

        for(int i = 0; i < numberOfRounds; i++ ){
            SboxSubstitution. subType(state);
            ShiftRows.shiftRows(state);
            MixCols.mixColumns(state);
            System.out.println("\n\nstate after call "+ (i+1) +" to mixColumns() \n----------------------------------------------");
            for(int k = 0; k < message.length; k++){
                System.out.print(String.format("%02x", state[k])+" ");
            }
            for(int j = 0; j < rkey.length; j++){// copying the needed round key
                rkey[j] = expandedKey[(16*(i+1)) + j];
            }
            KeyExpansion.addRoundKey(state, rkey);
        }

        //final round which doesn't include mixColumns
        SboxSubstitution. subType(state);
        ShiftRows.shiftRows(state);
        for(int i = 0; i < rkey.length; i++){
            rkey[i] = expandedKey[160 + i];}
        KeyExpansion.addRoundKey(state, rkey);

        System.out.println("\n\nCipher Text:");
        for(int i = 0; i < state.length; i++){
            message[i] = state[i];
            System.out.print(String.format("%02x", message[i])+ " ");
            if((i+1)%4 == 0){
                System.out.print("  ");}
        }

    }




    //-----------------------------------------------------------------------
    //PURPOSE:decrypts the given ciphertext using the provided key
    //INPUT PARAMETER:
    //int[]cipherText: the array containing the cipher text
    //int [] expandedKey: the array containing the expanded key
    //OUTPUT PARAMETER:
    //No output parameter
    //-----------------------------------------------------------------------
    public static void decrypt(int [] cipherText, int [] expandedKey) {
        System.out.println("\nDECRYPTION PROCESS \n------------------\nCipher Text:");

        int[] state = new int[16];

        for (int i = 0; i < state.length; i++) {
            System.out.print(String.format("%02x", cipherText[i]) + " ");
            if ((i + 1) % 4 == 0) {
                System.out.print("  ");
            }
            state[i] = cipherText[i];
        }
        int numberOfRounds = 9;
        int[] rkey = new int[16];

        for (int i = 0; i < rkey.length; i++) {// copying the needed round key
            rkey[i] = expandedKey[160 + i];
        }
        KeyExpansion.addRoundKey(state, rkey);

        for (int i = numberOfRounds-1; i >= 0; i--) {
            ShiftRows.invShiftRows(state);
            SboxSubstitution.invsubType(state);
            for (int j = 0; j < rkey.length; j++) {// copying the needed round key
                rkey[j] = expandedKey[(16 * (i +1)) + j];
            }
        KeyExpansion.addRoundKey(state, rkey);
        MixCols.invMixColumns(state);
        System.out.println("\n\nstate after call " + (numberOfRounds - (i) ) + " to InvMixColumns() \n----------------------------------------------");
        for (int k = 0; k < cipherText.length; k++) {
            System.out.print(String.format("%02x", state[k]) + " ");
        }
    }

        //final round which doesn't include mixColumns
        ShiftRows.invShiftRows(state);
        SboxSubstitution. invsubType(state);
        for(int i = 0; i < rkey.length; i++){
            rkey[i] = expandedKey[i];}
        KeyExpansion.addRoundKey(state, rkey);

        System.out.println("\n\nPlain Text:");
        for(int i = 0; i < state.length; i++){
            cipherText[i] = state[i];
            System.out.print(String.format("%02x", cipherText[i])+ " ");
            if((i+1)%4 == 0){
                System.out.print("  ");}
        }



    }


}

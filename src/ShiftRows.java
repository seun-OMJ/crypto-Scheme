public class ShiftRows {

   //-----------------------------------------------------------------------
   //PURPOSE: shifts the rows of the state array as specified in AES document
   //INPUT PARAMETER:
   //int[] state: the array containing the values to be shifted
   //OUTPUT PARAMETER:
   //No output parameter
   //-----------------------------------------------------------------------
   public static void shiftRows(int []state){
      int[] temp = new int[state.length];
      temp[0] = state[0];
      temp[1] = state[5];
      temp[2] = state[10];
      temp[3] = state[15];

      temp[4] = state[4];
      temp[5] = state[9];
      temp[6] = state[14];
      temp[7] = state[3];

      temp[8] = state[8];
      temp[9] = state[13];
      temp[10] = state[2];
      temp[11] = state[7];

      temp[12] = state[12];
      temp[13] = state[1];
      temp[14] = state[6];
      temp[15] = state[11];

      for(int i = 0; i < state.length; i++ ){
         state[i] = temp[i];
      }
   }

   //-----------------------------------------------------------------------
   //PURPOSE: shifts the rows of the state array as specified in AES document
   //INPUT PARAMETER:
   //int[] state: the array containing the values to be shifted
   //OUTPUT PARAMETER:
   //No output parameter
   //-----------------------------------------------------------------------
   public static void invShiftRows(int [] state){
      int[] temp = new int[state.length];
      temp[0] = state[0];
      temp[1] = state[13];
      temp[2] = state[10];
      temp[3] = state[7];

      temp[4] = state[4];
      temp[5] = state[1];
      temp[6] = state[14];
      temp[7] = state[11];

      temp[8] = state[8];
      temp[9] = state[5];
      temp[10] = state[2];
      temp[11] = state[15];

      temp[12] = state[12];
      temp[13] = state[9];
      temp[14] = state[6];
      temp[15] = state[3];

      for(int i = 0; i < state.length; i++ ){
         state[i] = temp[i];
      }

   }


   }

package 이전연습;

public class KMP {
    public int[] preProcessPattern(char[] ptrn) {
        int i = 0, j = -1;
        int ptrnLen = ptrn.length;
        int[] b = new int[ptrnLen + 1];
        b[i] = j;

        //while (i < ptrnLen) {
        
        for (i=0 ; i < ptrnLen ;i++){
            while (j >= 0 && ptrn[i] != ptrn[j]) {
                // if there is mismatch consider next widest border
                j = b[j];
            }
            i++;
            j++;
            b[i] = j;
        
    	}
        return b;
    }


    public void searchSubString(char[] text, char[] ptrn) {
        int i = 0, j = 0;
        int ptrnLen = ptrn.length;
        int txtLen = text.length;
        int[] b = preProcessPattern(ptrn);

        while (i < txtLen) {
            while (j >= 0 && text[i] != ptrn[j]) {
                j = b[j];
            }
            i++;
            j++;
            // a match is found
            if (j == ptrnLen) {
                System.out.println("FOUND SUBSTRING AT i " + i + " and index:" + (i - ptrnLen));
                //System.out.println("Setting j from " + j + " to " + b[j]);
                j = b[j];
            }
        }
    }
    // only for test purposes
    public static void main(String[] args) {

        KMP stm = new KMP();
        // pattern
        char[] ptrn = "abcabdabc".toCharArray();
        char[] text = "abcabdabcabeabcabdabcabd".toCharArray();

        System.out.print("");
        for (char c : text) {
            System.out.print(c + "");
        }
        System.out.println();
        // search for pattern in the string
        stm.searchSubString(text, ptrn);

    }



}

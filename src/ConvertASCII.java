public class ConvertASCII {

    public static final int BLACK = 0;
    public static final int WHITE = 255;

    String finalThing = "";

    /**
     * Convert greyscale img array to braille text
     *
     * @param picture image array (black and white)
     */
    public ConvertASCII(int[][] picture) {

        // flatten image to black and white
        // row echelon (y pixels)
        for (int i = 0; i < picture.length; i ++) {
            for (int j = 0; j < picture[0].length; j ++) {

                int val = picture[i][j];
                if (val < imgToBraille.threshold) {
                    picture[i][j] = BLACK;
                }
                else {
                    picture[i][j] = WHITE;
                }
            }
        }


        for (int i = 0; i < picture.length; i+=4) {
            for (int j = 0; j < picture[0].length; j+=2) {
                int val = 0;

                /*
                0 3
                1 4
                2 5
                6 7
                 */
                /**
                 * Hi. This is very ugly but I want all my pixels, and my brain is currently fired, and is incapable of finding a more elegant way at the moment.
                 * With just the whole try catch block it will cut off incomplete any incomplete braille chars
                 */
                // 0-2
                val += 1;//0x2800 (blank) is NOT SAME WIDTH as rest of chars wtf
                try {if (picture[i+1][j] == BLACK) {val += 2;}} catch (Exception e) {}
                try {if (picture[i+2][j] == BLACK) {val += 4;}} catch (Exception e) {}
                // 3-5
                try {if (picture[i+0][j+1] == BLACK) {val += 8;}} catch (Exception e) {}
                try {if (picture[i+1][j+1] == BLACK) {val += 16;}} catch (Exception e) {}
                try {if (picture[i+2][j+1] == BLACK) {val += 32;}} catch (Exception e) {}
                // 6-7
                try {if (picture[i+3][j+0] == BLACK) {val += 64;}} catch (Exception e) {}
                try {if (picture[i+3][j+1] == BLACK) {val += 128;}} catch (Exception e) {}

                finalThing += imgToBraille.mappingsOWO[val];


                //                try {
//                    val += 1;//0x2800 (blank) is NOT SAME WIDTH as rest of chars wtf
//                    if (picture[i+1][j] == BLACK) {val += 2;}
//                    if (picture[i+2][j] == BLACK) {val += 4;}
//
//                    if (picture[i+0][j+1] == BLACK) {val += 8;}
//                    if (picture[i+1][j+1] == BLACK) {val += 16;}
//                    if (picture[i+2][j+1] == BLACK) {val += 32;}
//
//                    if (picture[i+3][j+0] == BLACK) {val += 64;}
//                    if (picture[i+3][j+1] == BLACK) {val += 128;}
//                    finalThing += mappingsOWO[val];
//                } catch (Exception e) {}

            }
            finalThing += "\n";
        }
    }

    /**
     * Return the result after conversion
     * @return
     */
    public String getResult() {
        return finalThing;
    }

}
public class Cluster {

    int x; int y; // new array
    int xPointer; int yPointer; // original array

    int[][] out;
    int[][] input;


    /**
     * Downscales the image, integer scaling via taking average brightness of pixels
     *
     * @param input input array
     * @param newx new x size
     * @param newy new y size
     */
    public Cluster(int[][] input, int newx, int newy) {
        if (input.length * input[0].length <= 16000) {
            out = input;
            return;
        }

        this.input = input;
        out = new int[newy][newx];

        // x and y values for original array
        xPointer = 0;
        yPointer = 0;

        // x and y values for new array
        x = newx;
        y = newy;


        // downscale image. Take chunk, average brightness, move to next chunk
        // row echelon (y pixels)
        for (int i = 0; i < out.length; i ++) {
            for (int j = 0; j < out[0].length; j ++) {
                out[i][j] = average(xPointer, yPointer);
                xPointer += imgToBraille.factor;
            }

            yPointer += imgToBraille.factor;
            xPointer = 0;
        }
    }

    /**
     * Get average brightness of the square
     * @param xVal
     * @param yVal
     * @return
     */
    private int average(int xVal, int yVal) {
        int value = 0;
        // get the mean
        for (int i = yVal; i < yVal + imgToBraille.factor; i++) {
            for (int j = xVal; j < xVal + imgToBraille.factor; j++) {
                value += input[i][j];
            }
        }

        return value / (imgToBraille.factor * imgToBraille.factor);
    }


    public int[][] getIntArr() {
        return out;
    }
}

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class imgToBraille {

    private PrintStream out = new PrintStream(System.out, true, "UTF-8");
    public static int maxPixels;
    public static int threshold;
    private static String path;
    static int factor = 9;
    static char[] mappingsOWO = new char[256];

    /**
     * Process all frames in folder
     *
     * @throws IOException
     */
    public imgToBraille() throws IOException {
        /*
        0 3
        1 4
        2 5
        6 7
         */
        // Create braille mappings to int ID
        for (int i = 0; i < 256; i++) {
            mappingsOWO[i] = Character.toChars(0x2800 + i)[0];
        }
        // 0x2800 (blank) is NOT SAME WIDTH as rest of chars wtf
        mappingsOWO[0] = Character.toChars(0x2801)[0];

        // file stuff
        File frameDir = new File(path);
        for (String file: frameDir.list()) {
            convImg(ImageIO.read(new File(path + file)));;
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Hewwo world!");
        threshold = Integer.parseInt(args[0]);
        maxPixels = Integer.parseInt(args[1]);
        path = args[2];
        imgToBraille waaaah = new imgToBraille();
        System.out.println("Goodbye world");
    }


    /**
     * Find new resolution. Gets factor of resolution, to maintain same aspect ratio with largest x/y
     * @param x
     * @param y
     * @return
     */
    private static int findRes(int x, int y) {
        double factor = Math.sqrt((maxPixels / (double) (x*y)));
        return (int) Math.ceil(1/factor); // I think ceiling instead of floor because I did jank reciprocal stuff
    }


    /**
     * Convert image into brightness array, then to braille text
     *
     * @param img image array
     */
    private void convImg (BufferedImage img) {
        int[][] wtfAmIDoing = new int[img.getHeight()][img.getWidth()]; // brightness
//        System.out.println(img.getHeight());
//        System.out.println(img.getWidth());

        // get brightness value
        // row major (y pixels)
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                int colour = img.getRGB(j, i);
//                int alpha = (colour >> 24) & 0xFF;
                int r =   (colour >> 16) & 0xFF;
                int g = (colour >>  8) & 0xFF;
                int b =  (colour) & 0xFF;
                wtfAmIDoing[i][j] = (r + g + b) / 3;
            }
        }

        // downscale image
        factor = findRes(img.getWidth(), img.getHeight());
        Cluster downscaled = new Cluster(wtfAmIDoing, img.getWidth() / factor, img.getHeight() / factor);

        // convert to braille
        ConvertASCII converted = new ConvertASCII(downscaled.getIntArr());

        out.print(converted.finalThing);
        out.flush();
    }

}

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Main {

    static class BMPImage {
        String filename;
        int imageSize = 0;
        int width = 0;
        int height = 0;
        int padding = 0;
        int stride = 0;

        void calcStride() {
            stride = (((width * 3) + 3) / 4) * 4;
        }

        void calcPadding() {
            padding = stride - width * 3;
        }

        void calcImageSize() {
            imageSize = stride * height;
        }
    }


   /*void calc_stride(BMPImage myBmp) {

        myBmp.calcStride();

        myBmp.stride = ((myBmp.width * 3 + 3) / 4) * 4;
    }*/


    public static void main(String[] args) throws IOException {

        int argc = args.length;
        if (argc < 4) {
            System.out.println("Usage: " + args[0] + " <file.bmp> <width> <height> [commands...]");
            return;
        }

        BMPImage myBmp = new BMPImage();
        myBmp.filename = args[1];
        myBmp.width = Integer.parseInt(args[2]);
        myBmp.height = Integer.parseInt(args[3]);
        myBmp.calcStride();
        myBmp.calcPadding();

        byte[] bytes = new byte[myBmp.imageSize];

        for (int i = 4; i < bytes.length;) {
            String arg = args[i];
            if (arg == "clear") {
                if (i + 1 >= argc || )
            }
        }

        Files.write(Path.of("myBytes"), bytes);
        Files.write(Path.of("myBytes"), bytes, StandardOpenOption.APPEND);


        for (String arg : args) {
            System.out.println("arg = " + arg);
            try {
                int num = Integer.parseInt(arg); // num will be 123
            } catch (NumberFormatException e) {
                System.out.println("e.getMessage() = " + e.getMessage());
            }

        }
        System.err.println("Hello world!");
    }
    }
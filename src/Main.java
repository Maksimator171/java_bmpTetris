import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.lang.Math;

public class Main {

    static class BMPImage {
        String filename;
        int imageSize = 0;
        int width = 0;
        int height = 0;
        int padding = 0;
        int stride = 0;
        byte[] header_data = new byte[54];
        int header_data_index = 0;
        byte[] data;

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

    static void pushUint16_t(BMPImage myBmp, int value) {
        myBmp.header_data[myBmp.header_data_index++] = (byte)(value);
        myBmp.header_data[myBmp.header_data_index++] = (byte)(value >> 8);
    }

    static void pushUint32_t(BMPImage myBmp, long value) {
        myBmp.header_data[myBmp.header_data_index++] = (byte)(value);
        myBmp.header_data[myBmp.header_data_index++] = (byte)(value >> 8);
        myBmp.header_data[myBmp.header_data_index++] = (byte)(value >> 16);
        myBmp.header_data[myBmp.header_data_index++] = (byte)(value >> 24);
    }

    static void pushInt32_t(BMPImage myBmp, long value) {
        myBmp.header_data[myBmp.header_data_index++] = (byte)(value);
        myBmp.header_data[myBmp.header_data_index++] = (byte)(value >> 8);
        myBmp.header_data[myBmp.header_data_index++] = (byte)(value >> 16);
        myBmp.header_data[myBmp.header_data_index++] = (byte)(value >> 24);
    }

    static void writeFile(String filename, byte[] bytes) throws IOException {
        Files.write(Path.of(filename), bytes);
    }

    static void AddToFile(String filename, byte[] bytes) throws IOException {
        Files.write(Path.of(filename), bytes, StandardOpenOption.APPEND);
    }


    static void createBMP(BMPImage myBMP) throws IOException {
  final int bfType = 0x4D42;
  final long bfOffBits = 54;
  final long biSize = 40;
  final int biPlanes = 1;
  final int biBitCount = 24;
  final long biCompression = 0;
  final long biClrUsed = 0;
  final long biClrImportant = 0;
  final int biXPelsPerMeter = 2835;
  final int biYPelsPerMeter = 2835;

        long bfSize = bfOffBits + myBMP.imageSize;
        int biHeight = -myBMP.height;

        pushUint16_t(myBMP, bfType);
        pushUint32_t(myBMP, bfSize);
        pushUint16_t(myBMP,0);
        pushUint16_t(myBMP,0);
        pushUint32_t(myBMP, bfOffBits);
        pushUint32_t(myBMP, biSize);
        pushInt32_t(myBMP, myBMP.width);
        pushInt32_t(myBMP, biHeight);
        pushUint16_t(myBMP, biPlanes);
        pushUint16_t(myBMP, biBitCount);
        pushUint32_t(myBMP, biCompression);
        pushUint32_t(myBMP, myBMP.imageSize);
        pushInt32_t(myBMP, biXPelsPerMeter);
        pushInt32_t(myBMP, biYPelsPerMeter);
        pushUint32_t(myBMP, biClrUsed);
        pushUint32_t(myBMP, biClrImportant);

        writeFile(myBMP.filename, myBMP.header_data);
        AddToFile(myBMP.filename, myBMP.data);
    }

    static void draw_clear(BMPImage myBmp, String hex_color) {
        byte[] color = new byte[3];
        int value = Integer.parseInt(hex_color, 16);
        color[0] = (byte)(value % 256);
        color[1] = (byte) ((value / 256) % 256);
        color[2] = (byte) (((value / 256) / 256) % 256);

        for (int y = 0; y < myBmp.height; ++y) {
            for (int x = 0; x < myBmp.width; ++x) {
                int index = y * myBmp.stride + x * 3;
                myBmp.data[index] = color[0];
                myBmp.data[index + 1] = color[1];
                myBmp.data[index + 2] = color[2];
            }
        }
    }

    static void draw_spiral(BMPImage myBmp,String hex_color) {
        byte[] color = new byte[3];
        int value = Integer.parseInt(hex_color, 16);
        color[0] = (byte)(value % 256);
        color[1] = (byte) ((value / 256) % 256);
        color[2] = (byte) (((value / 256) / 256) % 256);

        int min_side = Math.min(myBmp.height, myBmp.width);
        for (int y = 0; y < myBmp.height; ++y) {
            for (int x = 0; x < myBmp.width; ++x) {
                int min_dist = Math.min(Math.min(x, myBmp.width - 1 - x), Math.min(y, myBmp.height - 1 - y));
                boolean parity = (min_dist % 2) == 1;
                boolean not_parity = !parity;
                boolean not_in_frame = ((x < min_side / 2) && (y - x == 1));
                if ((parity && !not_in_frame) || (not_parity && not_in_frame && (x % 2 == 0))) {
                    int index = y * myBmp.stride + x * 3;
                    myBmp.data[index] = color[0];
                    myBmp.data[index + 1] = color[1];
                    myBmp.data[index + 2] = color[2];
                }

            }
        }
    }


    static void draw_rect(BMPImage myBmp, int x, int y, int rect_width, int rect_height, String hex_color) {
        byte[] color = new byte[3];
        int value = Integer.parseInt(hex_color, 16);
        color[0] = (byte)(value % 256);
        color[1] = (byte) ((value / 256) % 256);
        color[2] = (byte) (((value / 256) / 256) % 256);

        for (int y_coor = y; y_coor < y + rect_height && y_coor < myBmp.height; ++y_coor) {
            for (int x_coor = x; x_coor < x + rect_width && x_coor < myBmp.width; ++x_coor) {
                int index = y_coor * myBmp.stride + x_coor * 3;
                myBmp.data[index] = color[0];
                myBmp.data[index + 1] = color[1];
                myBmp.data[index + 2] = color[2];
            }
        }
    }

    public static void main(String[] args) throws IOException {

        int argc = args.length;
        if (argc < 3) {
            System.out.println("Usage: " + args[0] + " <file.bmp> <width> <height> [commands...]");
            return;
        }

        BMPImage myBmp = new BMPImage();
        myBmp.filename = args[0];
        myBmp.width = Integer.parseInt(args[1]);
        myBmp.height = Integer.parseInt(args[2]);
        myBmp.calcStride();
        myBmp.calcPadding();
        myBmp.calcImageSize();
        myBmp.data = new byte[myBmp.imageSize];

        for (int i = 3; i < argc;) {
            String arg = args[i];
            if (arg.equals("clear")) {
                if (i + 1 >= argc || args[i + 1].length() != 6) {
                    System.err.println("Ошибка: некорректный цвет или введённые данные для clear");
                    break;
                }
                 draw_clear(myBmp, args[i + 1]);
                 i += 2;

            } else if (arg.equals("spiral")) {
                if (i + 1 >= argc || args[i + 1].length() != 6) {
                    System.err.println("Ошибка: некорректный цвет или введённые данные для spiral");
                    break;
                }
                draw_spiral(myBmp, args[i+1]);
                i += 2;

            } else if (arg.equals("rect")) {
                if (i + 1 >= argc || args[i + 5].length() != 6) {
                    System.err.println("Ошибка: некорректные данные для rect");
                    break;
                }
                int x = Integer.parseInt(args[i + 1]);
                int y = Integer.parseInt(args[i + 2]);
                int rect_width = Integer.parseInt(args[i + 3]);
                int rect_height = Integer.parseInt(args[i + 4]);
                draw_rect(myBmp, x, y, rect_width, rect_height, args[i + 5]);
                i += 6;

            } else {
                System.err.println("Ошибка6 неизвестная команда '" + arg + "'");
                break;
            }
        }

        createBMP(myBmp);
        //return;

    }
    }
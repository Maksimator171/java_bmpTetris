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

  static int pushUint16_t(int value, byte[] data, int index) {
    data[index++] = (byte) (value);
    data[index++] = (byte) (value >> 8);
    return index;
  }

  static int pushUint32_t(int value, byte[] data, int index) {
    data[index++] = (byte) (value);
    data[index++] = (byte) (value >> 8);
    data[index++] = (byte) (value >> 16);
    data[index++] = (byte) (value >> 24);
    return index;
  }

  static int pushInt32_t(int value, byte[] data, int index) {
    return pushUint32_t(value, data, index);
  }

  static void writeFile(String filename, byte[] bytes) throws IOException {
    Files.write(Path.of(filename), bytes);
  }

  static void AddToFile(String filename, byte[] bytes) throws IOException {
    Files.write(Path.of(filename), bytes, StandardOpenOption.APPEND);
  }


  static void createBMP(BMPImage myBMP) throws IOException {
    final int bfType = 0x4D42;
    final int bfOffBits = 54;
    final int biSize = 40;
    final int biPlanes = 1;
    final int biBitCount = 24;
    final int biCompression = 0;
    final int biClrUsed = 0;
    final int biClrImportant = 0;
    final int biXPelsPerMeter = 2835;
    final int biYPelsPerMeter = 2835;

    int bfSize = bfOffBits + myBMP.imageSize;
    int biHeight = -myBMP.height;

    int index = 0;
    index = pushUint16_t(bfType, myBMP.header_data, index);
    index = pushUint32_t(bfSize, myBMP.header_data, index);
    index = pushUint16_t(0, myBMP.header_data, index);
    index = pushUint16_t(0, myBMP.header_data, index);
    index = pushUint32_t(bfOffBits, myBMP.header_data, index);
    index = pushUint32_t(biSize, myBMP.header_data, index);
    index = pushInt32_t(myBMP.width,myBMP.header_data, index);
    index = pushInt32_t(biHeight,myBMP.header_data, index);
    index = pushUint16_t(biPlanes, myBMP.header_data, index);
    index = pushUint16_t(biBitCount, myBMP.header_data, index);
    index = pushUint32_t(biCompression, myBMP.header_data, index);
    index = pushUint32_t( myBMP.imageSize, myBMP.header_data, index);
    index = pushInt32_t(biXPelsPerMeter,myBMP.header_data, index);
    index = pushInt32_t(biYPelsPerMeter,myBMP.header_data, index);
    index = pushUint32_t(biClrUsed, myBMP.header_data, index);
    pushUint32_t(biClrImportant, myBMP.header_data, index);

    writeFile(myBMP.filename, myBMP.header_data);
    AddToFile(myBMP.filename, myBMP.data);
  }

  static byte[] parseColor (String hex_color) {
    byte[] color = new byte[3];
    int value = Integer.parseInt(hex_color, 16);
    color[0] = (byte) (value % 256);
    color[1] = (byte) ((value / 256) % 256);
    color[2] = (byte) (((value / 256) / 256) % 256);
    return color;
  }

  static void draw_clear(BMPImage myBmp, String hex_color) {
    byte[] color = parseColor(hex_color);

    for (int y = 0; y < myBmp.height; ++y) {
      for (int x = 0; x < myBmp.width; ++x) {
        int index = y * myBmp.stride + x * 3;
        myBmp.data[index] = color[0];
        myBmp.data[index + 1] = color[1];
        myBmp.data[index + 2] = color[2];
      }
    }
  }

  static void drawTetrisBlock(BMPImage myBmp, int x, int y, byte[] color, int blockSize) {
    byte[] borderColor = {0, 0, 0};

    int startX = Math.max(x, 0);
    int startY = Math.max(y, 0);
    int endX = Math.min(x + blockSize, myBmp.width);
    int endY = Math.min(y + blockSize, myBmp.height);
    for (int y_coor = startY; y_coor < endY; ++y_coor) {
      for (int x_coor = startX; x_coor < endX; ++x_coor) {
        int index = y_coor * myBmp.stride + x_coor * 3;
        boolean isBorder = (x_coor == x) || (x_coor == x + blockSize - 1) ||
            (y_coor == y) || (y_coor == y + blockSize - 1);

        if (isBorder) {
          myBmp.data[index]     = borderColor[0];
          myBmp.data[index + 1] = borderColor[1];
          myBmp.data[index + 2] = borderColor[2];
        } else {
          myBmp.data[index]     = color[0];
          myBmp.data[index + 1] = color[1];
          myBmp.data[index + 2] = color[2];
        }
      }
    }

  }

  static void drawOtetromino(BMPImage myBmp, int x, int y) {
    final int blockSize = 10;
    byte[] color = {(byte)0x00, (byte)0xff, (byte)0xff };
    drawTetrisBlock(myBmp, x, y, color, blockSize);
    drawTetrisBlock(myBmp, x + blockSize, y, color, blockSize);
    drawTetrisBlock(myBmp, x, y + blockSize, color, blockSize);
    drawTetrisBlock(myBmp, x + blockSize, y + blockSize, color, blockSize);

  }

  static void drawItetromino(BMPImage myBmp, int x, int y) {
    final int blockSize = 10;
    byte[] color = {(byte)0xff, (byte)0xff, (byte)0x00 };
    drawTetrisBlock(myBmp, x, y, color, blockSize);
    drawTetrisBlock(myBmp, x, y + blockSize, color, blockSize);
    drawTetrisBlock(myBmp, x, y + 2 * blockSize, color, blockSize);
    drawTetrisBlock(myBmp, x, y + 3 * blockSize, color, blockSize);
  }

  static void drawStetromino(BMPImage myBmp, int x, int y) {
    final int blockSize = 10;
    byte[] color = {(byte)0x00, (byte)0x00, (byte)0xff };
    drawTetrisBlock(myBmp, x, y, color, blockSize);
    drawTetrisBlock(myBmp, x + blockSize, y, color, blockSize);
    drawTetrisBlock(myBmp, x + blockSize, y + blockSize, color, blockSize);
    drawTetrisBlock(myBmp, x + 2 * blockSize, y + blockSize, color, blockSize);
  }

  static void drawZtetromino(BMPImage myBmp, int x, int y) {
    final int blockSize = 10;
    byte[] color = {(byte)0x00, (byte)0xff, (byte)0x00 };
    drawTetrisBlock(myBmp, x, y, color, blockSize);
    drawTetrisBlock(myBmp, x + blockSize, y, color, blockSize);
    drawTetrisBlock(myBmp, x, y + blockSize, color, blockSize);
    drawTetrisBlock(myBmp, x - blockSize, y + blockSize, color, blockSize);
  }

  static void drawLtetromino(BMPImage myBmp, int x, int y) {
    final int blockSize = 10;
    byte[] color = {(byte)0x00, (byte)0xff, (byte)0xff };
    drawTetrisBlock(myBmp, x, y, color, blockSize);
    drawTetrisBlock(myBmp, x + blockSize, y, color, blockSize);
    drawTetrisBlock(myBmp, x, y + blockSize, color, blockSize);
    drawTetrisBlock(myBmp, x, y + 2 * blockSize, color, blockSize);
  }

  static void drawJtetromino(BMPImage myBmp, int x, int y) {
    final int blockSize = 10;
    byte[] color = {(byte)0x88, (byte)0x88, (byte)0xff };
    drawTetrisBlock(myBmp, x, y, color, blockSize);
    drawTetrisBlock(myBmp, x + blockSize, y, color, blockSize);
    drawTetrisBlock(myBmp, x + blockSize, y + blockSize, color, blockSize);
    drawTetrisBlock(myBmp, x + blockSize, y + 2 * blockSize, color, blockSize);
  }

  static void drawTtetromino(BMPImage myBmp, int x, int y) {
    final int blockSize = 10;
    byte[] color = {(byte)0xff, (byte)0x00, (byte)0xff };
    drawTetrisBlock(myBmp, x, y, color, blockSize);
    drawTetrisBlock(myBmp, x - blockSize, y + blockSize, color, blockSize);
    drawTetrisBlock(myBmp, x, y + blockSize, color, blockSize);
    drawTetrisBlock(myBmp, x + blockSize, y + blockSize, color, blockSize);
  }

  static void draw_spiral(BMPImage myBmp, String hex_color) {
    byte[] color = parseColor(hex_color);

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

  static void draw_rect_(BMPImage myBmp, int x, int y, int rect_width, int rect_height, byte[] color) {
    for (int y_coor = y; y_coor < y + rect_height && y_coor < myBmp.height; ++y_coor) {
      for (int x_coor = x; x_coor < x + rect_width && x_coor < myBmp.width; ++x_coor) {
        int index = y_coor * myBmp.stride + x_coor * 3;
        myBmp.data[index] = color[0];
        myBmp.data[index + 1] = color[1];
        myBmp.data[index + 2] = color[2];
      }
    }
  }

  static void draw_rect(BMPImage myBmp, int x, int y, int rect_width, int rect_height, String hex_color) {
    byte[] color = parseColor(hex_color);

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

    for (int i = 3; i < argc; ) {
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
        draw_spiral(myBmp, args[i + 1]);
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

/*
public class ArrayOp {
  static int pushUint16_t(int value, byte[] data, int index) {
    data[index++] = (byte) (value);
    data[index++] = (byte) (value >> 8);
    return index;
  }
}
*/
}
package org.maxim;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.lang.Math;
import java.util.Random;

import static org.maxim.ArrayOp.*;

public class DrawToBmp {
  static int blockSize = 10;

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
    color[2] = (byte) (value % 256);
    color[1] = (byte) ((value / 256) % 256);
    color[0] = (byte) (((value / 256) / 256) % 256);
    return color;
  }

  static void clear(BMPImage myBmp, String hex_color) {
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


  static void draw_spiral(BMPImage myBmp, byte[] color) {
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

  static void draw_spiral(BMPImage myBmp, String hex_color) {
    byte[] color = parseColor(hex_color);
    draw_spiral(myBmp, color);
  }

  static void draw_rect(BMPImage myBmp, int x, int y, int width, int height, byte[] color) {
    for (int y_coor = y; y_coor < y + height && y_coor < myBmp.height; ++y_coor) {
      for (int x_coor = x; x_coor < x + width && x_coor < myBmp.width; ++x_coor) {
        int index = y_coor * myBmp.stride + x_coor * 3;
        myBmp.data[index] = color[0];
        myBmp.data[index + 1] = color[1];
        myBmp.data[index + 2] = color[2];
      }
    }
  }

  static void draw_rect(BMPImage myBmp, int x, int y, int width, int height, String hex_color) {
    byte[] color = parseColor(hex_color);
    draw_rect(myBmp, x, y, width, height, color);
  }

  static int now_frame = 0;

  static void saveFrame(BMPImage myBmp) throws IOException {
    if (now_frame >= 10) {
      return;
    }
    String nowFrame = String.format("frame%d.bmp", now_frame++);
    myBmp.filename = nowFrame;
    createBMP(myBmp);
  }

  static boolean fallTetramino(BMPImage myBmp, int tetraminoType, int x, int y, boolean endGame) throws IOException {
    int checkIndex = (y + 1) * myBmp.stride + x * 3;
    boolean endFall = false;
    if (checkIndex > myBmp.imageSize) {
      endFall = true;
    }
    switch (tetraminoType) {
      case 1:
        while (!endFall ) {
          saveFrame(myBmp);
          if (myBmp.data[checkIndex] != (byte)-1 || myBmp.data[checkIndex + 3 * blockSize] != (byte)-1) {
            if (y == 0) {
              endGame = true;
            }
            return endGame;
          }
          TetrisRenderer.shiftOTetromino(myBmp, x, y, blockSize);
          y += blockSize;
          checkIndex += myBmp.stride;
        }
        break;
      case 2:
        while (!endFall ) {
          saveFrame(myBmp);
          if (myBmp.data[checkIndex] != (byte)-1) {
            if (y == 0) {
              endGame = true;
            }
            return endGame;
          }
          TetrisRenderer.shiftITetromino(myBmp, x, y, blockSize);
          y += blockSize;
          checkIndex += myBmp.stride;
        }
        break;
      case 3:
        while (!endFall ) {
          saveFrame(myBmp);
          if (myBmp.data[checkIndex] != (byte)-1 || myBmp.data[checkIndex + 30] != (byte)-1 ||
              myBmp.data[checkIndex + 60 - myBmp.stride] != (byte)-1) {
            if (y == 0) {
              endGame = true;
            }
            return endGame;
          }
          TetrisRenderer.shiftSTetromino(myBmp, x, y, blockSize);
          y += blockSize;
          checkIndex += myBmp.stride;
        }
        break;
      case 4:
        while (!endFall ) {
          saveFrame(myBmp);
          if (myBmp.data[checkIndex] != (byte)-1 || myBmp.data[checkIndex - 30] != (byte)-1 ||
              myBmp.data[checkIndex - 60 - myBmp.stride] != (byte)-1) {
            if (y == 0) {
              endGame = true;
            }
            return endGame;
          }
          TetrisRenderer.shiftZTetromino(myBmp, x, y, blockSize);
          y += blockSize;
          checkIndex += myBmp.stride;
        }
        break;
      case 5:
        while (!endFall ) {
          saveFrame(myBmp);
          if (myBmp.data[checkIndex] != (byte)-1 || myBmp.data[checkIndex + 30] != (byte)-1) {
            if (y == 0) {
              endGame = true;
            }
            return endGame;
          }
          TetrisRenderer.shiftLTetromino(myBmp, x, y, blockSize);
          y += blockSize;
          checkIndex += myBmp.stride;
        }
        break;
      case 6:
        while (!endFall ) {
          saveFrame(myBmp);
          if (myBmp.data[checkIndex] != (byte)-1 || myBmp.data[checkIndex - 30] != (byte)-1) {
            if (y == 0) {
              endGame = true;
            }
            return endGame;
          }
          TetrisRenderer.shiftJTetromino(myBmp, x, y, blockSize);
          y += blockSize;
          checkIndex += myBmp.stride;
        }
        break;
      case 7:
        while (!endFall ) {
          saveFrame(myBmp);
          if (myBmp.data[checkIndex] != (byte)-1 || myBmp.data[checkIndex - 30 - myBmp.stride] != (byte)-1 ||
              myBmp.data[checkIndex + 30 - myBmp.stride] != (byte)-1) {
            if (y == 0) {
              endGame = true;
            }
            return endGame;
          }
          TetrisRenderer.shiftTTetromino(myBmp, x, y, blockSize);
          y += blockSize;
          checkIndex += myBmp.stride;
        }
        break;
      default:
        System.err.println("fallTetramino не сработало");
        break;
    }
    return endGame;
  }

  static void tetrisGame(BMPImage myBmp) throws IOException {
    boolean endGame = false;
    int y = 0;
    Random random = new Random();
    while (!endGame) {
      int randomInt = random.nextInt(7) + 1;
      int RanomCoor = random.nextInt(myBmp.width-10);
      int x = RanomCoor / 10 * 10;
      int a = randomInt;
      switch (a) {
        case 1 -> {
          TetrisRenderer.drawOTetromino(myBmp, x, y, blockSize);
          fallTetramino(myBmp, a, x, y, endGame);
        }
        case 2 -> {
          TetrisRenderer.drawITetromino(myBmp, x, y, blockSize);
          fallTetramino(myBmp, a, x, y, endGame);
        }
        case 3 -> {
          TetrisRenderer.drawSTetromino(myBmp, x, y, blockSize);
          fallTetramino(myBmp, a, x, y, endGame);
        }
        case 4 -> {
          TetrisRenderer.drawZTetromino(myBmp, x, y, blockSize);
          fallTetramino(myBmp, a, x, y, endGame);
        }
        case 5 -> {
          TetrisRenderer.drawLTetromino(myBmp, x, y, blockSize);
          fallTetramino(myBmp, a, x, y, endGame);
        }
        case 6 -> {
          TetrisRenderer.drawJTetromino(myBmp, x, y, blockSize);
          fallTetramino(myBmp, a, x, y, endGame);
        }
        case 7 -> {
          TetrisRenderer.drawTTetromino(myBmp, x, y, blockSize);
          fallTetramino(myBmp, a, x, y, endGame);
        }
        default -> System.err.println("tetrisGame не сработало");
      }

    }
  }
}
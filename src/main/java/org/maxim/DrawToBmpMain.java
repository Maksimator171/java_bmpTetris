package org.maxim;

import java.io.IOException;

public class  DrawToBmpMain {
  public static void main(String[] args) throws IOException {

    int argc = args.length;
    if (argc < 3) {
      System.out.println("Usage: DrawToBmpMain <file.bmp> <width> <height> [commands...]");
      return;
    }

    DrawToBmp.BMPImage myBmp = new DrawToBmp.BMPImage();
    myBmp.filename = args[0];
    myBmp.width = Integer.parseInt(args[1]);
    myBmp.height = Integer.parseInt(args[2]);
    myBmp.calcStride();
    myBmp.calcPadding();
    myBmp.calcImageSize();
    myBmp.data = new byte[myBmp.imageSize];

    int x = 0;
    int y = 0;
    for (int i = 3; i < argc; ) {
      String arg = args[i];
      switch (arg) {
        case "clear":
          if (i + 1 >= argc || args[i + 1].length() != 6) {
            System.err.println("Ошибка: некорректный цвет или введённые данные для clear");
            break;
          }
          DrawToBmp.clear(myBmp, args[i + 1]);
          i += 2;
          break;
        case "spiral":
          if (i + 1 >= argc || args[i + 1].length() != 6) {
            System.err.println("Ошибка: некорректный цвет или введённые данные для spiral");
            break;
          }
          DrawToBmp.draw_spiral(myBmp, args[i + 1]);
          i += 2;
          break;
        case "rect":
          if (i + 1 >= argc || args[i + 5].length() != 6) {
            System.err.println("Ошибка: некорректные данные для rect");
            break;
          }
          x = Integer.parseInt(args[i + 1]);
          y = Integer.parseInt(args[i + 2]);
          int width = Integer.parseInt(args[i + 3]);
          int height = Integer.parseInt(args[i + 4]);
          DrawToBmp.draw_rect(myBmp, x, y, width, height, args[i + 5]);
          i += 6;
          break;
        case "Otetromino":
          if (i + 2 >= argc ) {
            System.err.println("Ошибка: некорректные данные для Otetromino");
            break;
          }
          x = Integer.parseInt(args[i + 1]);
          y = Integer.parseInt(args[i + 2]);
          TetrisRenderer.drawOTetromino(myBmp, x, y, DrawToBmp.blockSize);
          i += 3;
          break;
        case "Itetromino":
          if (i + 2 >= argc ) {
            System.err.println("Ошибка: некорректные данные для Itetromino");
            break;
          }
          x = Integer.parseInt(args[i + 1]);
          y = Integer.parseInt(args[i + 2]);
          TetrisRenderer.drawITetromino(myBmp, x, y, DrawToBmp.blockSize);
          i += 3;
          break;
        case "Stetromino":
          if (i + 2 >= argc ) {
            System.err.println("Ошибка: некорректные данные для Stetromino");
            break;
          }
          x = Integer.parseInt(args[i + 1]);
          y = Integer.parseInt(args[i + 2]);
          TetrisRenderer.drawSTetromino(myBmp, x, y, DrawToBmp.blockSize);
          i += 3;
          break;
        case "Ztetromino":
          if (i + 2 >= argc ) {
            System.err.println("Ошибка: некорректные данные для Ztetromino");
            break;
          }
          x = Integer.parseInt(args[i + 1]);
          y = Integer.parseInt(args[i + 2]);
          TetrisRenderer.drawZTetromino(myBmp, x, y, DrawToBmp.blockSize);
          i += 3;
          break;
        case "Ltetromino":
          if (i + 2 >= argc ) {
            System.err.println("Ошибка: некорректные данные для Ltetromino");
            break;
          }
          x = Integer.parseInt(args[i + 1]);
          y = Integer.parseInt(args[i + 2]);
          TetrisRenderer.drawLTetromino(myBmp, x, y, DrawToBmp.blockSize);
          i += 3;
          break;
        case "Jtetromino":
          if (i + 2 >= argc ) {
            System.err.println("Ошибка: некорректные данные для Jtetromino");
            break;
          }
          x = Integer.parseInt(args[i + 1]);
          y = Integer.parseInt(args[i + 2]);
          TetrisRenderer.drawJTetromino(myBmp, x, y, DrawToBmp.blockSize);
          i += 3;
          break;
        case "Ttetromino":
          if (i + 2 >= argc ) {
            System.err.println("Ошибка: некорректные данные для Ttetromino");
            break;
          }
          x = Integer.parseInt(args[i + 1]);
          y = Integer.parseInt(args[i + 2]);
          TetrisRenderer.drawTTetromino(myBmp, x, y, DrawToBmp.blockSize);
          i += 3;
          break;
        case "tetrisGame":
          myBmp.width = 100;
          myBmp.height = 200;
          DrawToBmp.clear(myBmp, "ffffff");
          TetrisGame.tetrisGame(myBmp);
          i += 1;
          break;
        default:
          System.err.println("Ошибка6 неизвестная команда '" + arg + "'");
          break;

      }
    }
    DrawToBmp.writeBmpToFile(myBmp, myBmp.filename);
    //return;

  }
}

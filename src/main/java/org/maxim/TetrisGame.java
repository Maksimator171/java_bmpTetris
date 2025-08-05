package org.maxim;

import java.io.IOException;
import java.util.Random;

class TetrisGame {
  static int tetraminoType = 0;
  static int x_tetramino = 0;
  static int y_tetramino = 0;
  static boolean endGame = false;
  boolean fallTetramino = false;
  static int now_frame = 0;
  private int frameNumber;
  boolean[] field = new boolean[150];

  static void saveFrame(DrawToBmp.BMPImage myBmp) throws IOException {
    if (now_frame >= 10) {
      return;
    }
    String nowFrame = String.format("frame%d.bmp", now_frame++);
    myBmp.filename = nowFrame;
    DrawToBmp.writeBmpToFile(myBmp);
  }

  static boolean fallTetramino(DrawToBmp.BMPImage myBmp, int tetraminoType, int x, int y) throws IOException {
    int checkIndex = (y + 1) * myBmp.stride + x * 3;
    boolean endFall = false;
    if (checkIndex > myBmp.imageSize) {
      endFall = true;
    }
    switch (tetraminoType) {
      case 1:
        while (!endFall ) {
          saveFrame(myBmp);
          if (myBmp.data[checkIndex] != (byte)-1 || myBmp.data[checkIndex + 3 * DrawToBmp.blockSize] != (byte)-1) {
            if (y == 0) {
              endGame = true;
            }
            return endGame;
          }
          TetrisRenderer.shiftOTetromino(myBmp, x, y, DrawToBmp.blockSize);
          y += DrawToBmp.blockSize;
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
          TetrisRenderer.shiftITetromino(myBmp, x, y, DrawToBmp.blockSize);
          y += DrawToBmp.blockSize;
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
          TetrisRenderer.shiftSTetromino(myBmp, x, y, DrawToBmp.blockSize);
          y += DrawToBmp.blockSize;
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
          TetrisRenderer.shiftZTetromino(myBmp, x, y, DrawToBmp.blockSize);
          y += DrawToBmp.blockSize;
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
          TetrisRenderer.shiftLTetromino(myBmp, x, y, DrawToBmp.blockSize);
          y += DrawToBmp.blockSize;
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
          TetrisRenderer.shiftJTetromino(myBmp, x, y, DrawToBmp.blockSize);
          y += DrawToBmp.blockSize;
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
          TetrisRenderer.shiftTTetromino(myBmp, x, y, DrawToBmp.blockSize);
          y += DrawToBmp.blockSize;
          checkIndex += myBmp.stride;
        }
        break;
      default:
        System.err.println("fallTetramino не сработало");
        break;
    }
    return endGame;
  }

  static String frameFileName(int frameNumber) {
    String nowFrame = String.format("frame%d.bmp", frameNumber);
    return nowFrame;
  }

  static void tetrisGame(DrawToBmp.BMPImage bmp) throws IOException {
    TetrisGame tetrisGame = new TetrisGame();
    tetrisGame.writeToFile(bmp);
    while (tetrisGame.isRunning()) {
      tetrisGame.step(bmp);
      tetrisGame.writeToFile(bmp);
    }
  }

  static void createRandomTetramino (DrawToBmp.BMPImage bmp) throws IOException {
    Random random = new Random();
      int randomInt = random.nextInt(7) + 1;
      int RanomCoor = random.nextInt(bmp.width-10);
      x_tetramino = RanomCoor / 10 * 10;
      int a = randomInt;
      switch (a) {
        case 1 -> TetrisRenderer.drawOTetromino(bmp, x_tetramino, y_tetramino, DrawToBmp.blockSize);
        case 2 -> TetrisRenderer.drawITetromino(bmp, x_tetramino, y_tetramino, DrawToBmp.blockSize);
        case 3 -> TetrisRenderer.drawSTetromino(bmp, x_tetramino, y_tetramino, DrawToBmp.blockSize);
        case 4 -> TetrisRenderer.drawZTetromino(bmp, x_tetramino, y_tetramino, DrawToBmp.blockSize);
        case 5 -> TetrisRenderer.drawLTetromino(bmp, x_tetramino, y_tetramino, DrawToBmp.blockSize);
        case 6 -> TetrisRenderer.drawJTetromino(bmp, x_tetramino, y_tetramino, DrawToBmp.blockSize);
        case 7 -> TetrisRenderer.drawTTetromino(bmp, x_tetramino, y_tetramino, DrawToBmp.blockSize);
        default -> System.err.println("tetrisGame не сработало");
      }
      tetraminoType = a;
  }

  void step(DrawToBmp.BMPImage bmp) throws IOException {
    if (!endGame && !fallTetramino)  {
      createRandomTetramino (bmp);
    } else if (!endGame && fallTetramino) {
      fallTetramino(bmp, tetraminoType, x_tetramino, y_tetramino);
    } else {
      endGame = true;
    }
  }

  void render(DrawToBmp.BMPImage myBmp) {
  }

  boolean isRunning() {
  }

  public void writeToFile(DrawToBmp.BMPImage bmp) {
    this.render(bmp);
    DrawToBmp.writeBmpToFile(bmp, frameFileName(frameNumber));
    frameNumber++;
  }
}

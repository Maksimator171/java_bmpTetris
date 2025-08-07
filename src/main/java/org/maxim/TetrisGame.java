package org.maxim;

import java.io.IOException;
import java.util.Random;

class TetrisGame {
  static int tetraminoType = 0;
  static int x_tetramino = 0;
  static int y_tetramino = 0;
  static boolean endGame = false;
  static boolean fallTetramino = false;
  private int frameNumber;
  static boolean[] field = new boolean[200];

  static String frameFileName(int frameNumber) {
    return String.format("frame%d.bmp", frameNumber);
  }

  static void tetrisGame(DrawToBmp.BMPImage bmp) throws IOException {
    TetrisGame tetrisGame = new TetrisGame();
    tetrisGame.writeToFile(bmp);
    int a = 0;
    while (100 > a++ && !endGame) {
      tetrisGame.step(bmp);
      tetrisGame.writeToFile(bmp);
    }
  }

  static void createRandomTetramino (DrawToBmp.BMPImage bmp) {
    Random random = new Random();
      int a = random.nextInt(7) + 1;
      switch (a) {
        case 1 -> {
          x_tetramino = random.nextInt((bmp.width - DrawToBmp.blockSize) / 10 * 10);
          if (field[x_tetramino / 10] || field[x_tetramino / 10 + 1]) {
            endGame = true;
            return;
          }
          field[x_tetramino / 10] = true;
          field[x_tetramino / 10 + 1] = true;
          fallTetramino = true;
          TetrisRenderer.drawOTetromino(bmp, x_tetramino, y_tetramino, DrawToBmp.blockSize);
        }
        case 2 -> {
          x_tetramino = random.nextInt(bmp.width / 10 * 10);
          if (field[x_tetramino / 10]) {
            endGame = true;
            return;
          }
          field[x_tetramino / 10] = true;
          fallTetramino = true;
          TetrisRenderer.drawITetromino(bmp, x_tetramino, y_tetramino, DrawToBmp.blockSize);
        }
        case 3 -> {
          x_tetramino = random.nextInt((bmp.width - 2 * DrawToBmp.blockSize) / 10 * 10);
          if (field[x_tetramino / 10] || field[x_tetramino / 10 + 1]) {
            endGame = true;
            return;
          }
          field[x_tetramino / 10] = true;
          field[x_tetramino / 10 + 1] = true;
          fallTetramino = true;
          TetrisRenderer.drawSTetromino(bmp, x_tetramino, y_tetramino, DrawToBmp.blockSize);
        }
        case 4 -> {
          x_tetramino = 2 + random.nextInt((bmp.width - 2 * DrawToBmp.blockSize) / 10 * 10);
          if (field[x_tetramino / 10] || field[x_tetramino / 10 - 1]) {
            endGame = true;
            return;
          }
          field[x_tetramino / 10] = true;
          field[x_tetramino / 10 - 1] = true;
          fallTetramino = true;
          TetrisRenderer.drawZTetromino(bmp, x_tetramino, y_tetramino, DrawToBmp.blockSize);
        }
        case 5 -> {
          x_tetramino = random.nextInt((bmp.width - DrawToBmp.blockSize) / 10 * 10);
          if (field[x_tetramino / 10] || field[x_tetramino / 10 + 1]) {
            endGame = true;
            return;
          }
          field[x_tetramino / 10] = true;
          field[x_tetramino / 10 + 1] = true;
          fallTetramino = true;
          TetrisRenderer.drawLTetromino(bmp, x_tetramino, y_tetramino, DrawToBmp.blockSize);
        }
        case 6 -> {
          x_tetramino = 1 + random.nextInt((bmp.width - DrawToBmp.blockSize) / 10 * 10);
          if (field[x_tetramino / 10] || field[x_tetramino / 10 - 1]) {
            endGame = true;
            return;
          }
          field[x_tetramino / 10] = true;
          field[x_tetramino / 10 - 1] = true;
          fallTetramino = true;
          TetrisRenderer.drawJTetromino(bmp, x_tetramino, y_tetramino, DrawToBmp.blockSize);
        }
        case 7 -> {
          x_tetramino = 1 + random.nextInt((bmp.width - 2 * DrawToBmp.blockSize) / 10 * 10);
          if (field[x_tetramino / 10]) {
            endGame = true;
            return;
          }
          field[x_tetramino / 10] = true;
          fallTetramino = true;
          TetrisRenderer.drawTTetromino(bmp, x_tetramino, y_tetramino, DrawToBmp.blockSize);
        }
        default -> System.err.println("tetrisGame не сработало");
      }
      tetraminoType = a;
  }

  void step(DrawToBmp.BMPImage bmp) {
    if (!endGame && !fallTetramino) {
      createRandomTetramino(bmp);
    } else if (!endGame) {
      fallTetramino(bmp, tetraminoType, x_tetramino, y_tetramino);
    }
  }

  //void render(DrawToBmp.BMPImage bmp) {}


  public void writeToFile(DrawToBmp.BMPImage bmp) throws IOException {
    //this.render(bmp);
    DrawToBmp.writeBmpToFile(bmp, frameFileName(frameNumber));
    frameNumber++;
  }

  static boolean fallTetramino(DrawToBmp.BMPImage bmp, int tetraminoType, int x, int y) {
    int checkIndexField = x / 10 + (y / 10 + 1) * (bmp.width / 10);
    if (checkIndexField > 200) {
      fallTetramino = false;
      y_tetramino = 0;
      return true;
    }
    switch (tetraminoType) {
      case 1:
          if (field[checkIndexField] || field[checkIndexField + 1]) {
            if (y == 0) {
              endGame = true;
            }
            fallTetramino = false;
            y_tetramino = 0;
            return endGame;
          }
          TetrisRenderer.shiftOTetromino(bmp, x, y, DrawToBmp.blockSize);
          field[checkIndexField] = true;
          field[checkIndexField + 1] = true;
          if (checkIndexField > 2 * bmp.width / 10) {
            field[checkIndexField + 1 - 2 * (bmp.width / 10)] = false;
            field[checkIndexField - 2 * (bmp.width / 10)] = false;
          }
          y_tetramino += DrawToBmp.blockSize;
          break;
      case 2:
          if (field[checkIndexField]) {
            if (y == 0) {
              endGame = true;
            }
            fallTetramino = false;
            y_tetramino = 0;
            return endGame;
          }
          TetrisRenderer.shiftITetromino(bmp, x, y, DrawToBmp.blockSize);
          field[checkIndexField] = true;
        if (checkIndexField > 4 * bmp.width / 10) {
          field[checkIndexField - 4 * (bmp.width / 10)] = false;
        }
          y_tetramino += DrawToBmp.blockSize;
          break;
      case 3:
          if (field[checkIndexField] || field[checkIndexField + 1] ||
              field[checkIndexField + 2 - (bmp.width / 10)]) {
            if (y == 0) {
              endGame = true;
            }
            fallTetramino = false;
            y_tetramino = 0;
            return endGame;
          }
          TetrisRenderer.shiftSTetromino(bmp, x, y, DrawToBmp.blockSize);
          field[checkIndexField] = true;
          field[checkIndexField + 1] = true;
          field[checkIndexField + 2 - (bmp.width / 10)] = true;
        if (checkIndexField > 2 * bmp.width / 10) {
          field[checkIndexField - (bmp.width / 10)] = false;
          field[checkIndexField + 1 - 2 * (bmp.width / 10)] = false;
          field[checkIndexField + 2 - 2 * (bmp.width / 10)] = false;
        }
          y_tetramino += DrawToBmp.blockSize;
          break;
      case 4:
          if (field[checkIndexField] || field[checkIndexField - 1] ||
              field[checkIndexField - 2 - (bmp.width / 10)]) {
            if (y == 0) {
              endGame = true;
            }
            fallTetramino = false;
            y_tetramino = 0;
            return endGame;
          }
          TetrisRenderer.shiftZTetromino(bmp, x, y, DrawToBmp.blockSize);
          field[checkIndexField] = true;
          field[checkIndexField - 1] = true;
          field[checkIndexField - 2 - (bmp.width / 10)] = true;
        if (checkIndexField > 2 * bmp.width / 10) {
          field[checkIndexField - (bmp.width / 10)] = false;
          field[checkIndexField - 1 - 2 * (bmp.width / 10)] = false;
          field[checkIndexField - 2 - 2 * (bmp.width / 10)] = false;
        }
          y_tetramino += DrawToBmp.blockSize;
          break;
      case 5:
          if (field[checkIndexField] || field[checkIndexField + 1]) {
            if (y == 0) {
              endGame = true;
            }
            fallTetramino = false;
            y_tetramino = 0;
            return endGame;
          }
          TetrisRenderer.shiftLTetromino(bmp, x, y, DrawToBmp.blockSize);
          field[checkIndexField] = true;
          field[checkIndexField + 1] = true;
        if (checkIndexField > 3 * bmp.width / 10) {
          field[checkIndexField - 3 * (bmp.width / 10)] = false;
          field[checkIndexField + 1 - (bmp.width / 10)] = false;
        }
          y_tetramino += DrawToBmp.blockSize;
          break;
      case 6:
          if (field[checkIndexField] || field[checkIndexField - 1]) {
            if (y == 0) {
              endGame = true;
            }
            fallTetramino = false;
            y_tetramino = 0;
            return endGame;
          }
          TetrisRenderer.shiftJTetromino(bmp, x, y, DrawToBmp.blockSize);
          field[checkIndexField] = true;
          field[checkIndexField - 1] = true;
        if (checkIndexField > 3 * bmp.width / 10) {
          field[checkIndexField - 3 * (bmp.width / 10)] = false;
          field[checkIndexField - 1 - (bmp.width / 10)] = false;
        }
          y_tetramino += DrawToBmp.blockSize;
        break;
      case 7:
          if (field[checkIndexField] || field[checkIndexField - 1 - (bmp.width / 10)] ||
              field[checkIndexField + 1 - (bmp.width / 10)]) {
            if (y == 0) {
              endGame = true;
            }
            fallTetramino = false;
            y_tetramino = 0;
            return endGame;
          }
          TetrisRenderer.shiftTTetromino(bmp, x, y, DrawToBmp.blockSize);
          field[checkIndexField] = true;
          field[checkIndexField - 1 - (bmp.width / 10)] = true;
          field[checkIndexField + 1 - (bmp.width / 10)] = true;
        if (checkIndexField > 2 * bmp.width / 10) {
          field[checkIndexField - 2 * (bmp.width / 10)] = false;
          field[checkIndexField - 1 - 2 * (bmp.width / 10)] = false;
          field[checkIndexField + 1 - 2 * (bmp.width / 10)] = false;
        }
          y_tetramino += DrawToBmp.blockSize;
        break;
      default:
        System.err.println("fallTetramino не сработало");
        break;
    }
    return endGame;
  }

}

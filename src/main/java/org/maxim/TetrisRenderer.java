package org.maxim;

public class TetrisRenderer {

  static class TetrisColors {
    public TetrisColors(String color, String border) {
      this.color = DrawToBmp.parseColor(color);
      this.border = DrawToBmp.parseColor(border);
    }

    byte[] color;
    byte[] border;
  }

  interface TColors {
    TetrisColors O = new TetrisColors("00ffff", "222222");
    TetrisColors I = new TetrisColors("ffff00", "222222");
    TetrisColors S = new TetrisColors("0000ff", "222222");
    TetrisColors Z = new TetrisColors("00ff00", "222222");
    TetrisColors L = new TetrisColors("0088ff", "222222");
    TetrisColors J = new TetrisColors("8888ff", "222222");
    TetrisColors T = new TetrisColors("ff00ff", "222222");
    TetrisColors background = new TetrisColors("ffffff", "ffffff");
  }

  static void shiftOTetromino(DrawToBmp.BMPImage myBmp, int x, int y, int blockSize) {
    drawTetrisBlock(myBmp, x, y - blockSize, blockSize, TColors.background.color, TColors.background.border);
    drawTetrisBlock(myBmp, x + blockSize, y - blockSize, blockSize, TColors.background.color, TColors.background.border);

    drawTetrisBlock(myBmp, x, y + blockSize, blockSize, TColors.O.color, TColors.O.border);
    drawTetrisBlock(myBmp, x + blockSize, y + blockSize, blockSize, TColors.O.color, TColors.O.border);
  }


  static void shiftITetromino(DrawToBmp.BMPImage myBmp, int x, int y, int blockSize) {
    drawTetrisBlock(myBmp, x, y - 3 * blockSize, blockSize, TColors.background.color, TColors.background.border);

    drawTetrisBlock(myBmp, x, y + blockSize , blockSize, TColors.I.color, TColors.I.border);
  }


  static void shiftSTetromino(DrawToBmp.BMPImage myBmp, int x, int y, int blockSize) {
    drawTetrisBlock(myBmp, x, y, blockSize, TColors.background.color, TColors.background.border);
    drawTetrisBlock(myBmp, x + blockSize, y - blockSize, blockSize, TColors.background.color, TColors.background.border);
    drawTetrisBlock(myBmp, x + 2 * blockSize, y - blockSize, blockSize, TColors.background.color, TColors.background.border);

    drawTetrisBlock(myBmp, x, y + blockSize, blockSize, TColors.S.color, TColors.S.border);
    drawTetrisBlock(myBmp, x + blockSize, y + blockSize, blockSize, TColors.S.color, TColors.S.border);
    drawTetrisBlock(myBmp, x + 2 * blockSize, y, blockSize, TColors.S.color, TColors.S.border);
  }

  static void shiftZTetromino(DrawToBmp.BMPImage myBmp, int x, int y, int blockSize) {
    drawTetrisBlock(myBmp, x, y, blockSize, TColors.background.color, TColors.background.border);
    drawTetrisBlock(myBmp, x - blockSize, y - blockSize, blockSize, TColors.background.color, TColors.background.border);
    drawTetrisBlock(myBmp, x - 2 * blockSize, y - blockSize, blockSize, TColors.background.color, TColors.background.border);

    drawTetrisBlock(myBmp, x, y + blockSize, blockSize, TColors.Z.color, TColors.Z.border);
    drawTetrisBlock(myBmp, x - blockSize, y + blockSize, blockSize, TColors.Z.color, TColors.Z.border);
    drawTetrisBlock(myBmp, x - 2 * blockSize, y, blockSize, TColors.Z.color, TColors.Z.border);
  }

  static void shiftLTetromino(DrawToBmp.BMPImage myBmp, int x, int y, int blockSize) {
    drawTetrisBlock(myBmp, x + blockSize, y, blockSize, TColors.background.color, TColors.background.border);
    drawTetrisBlock(myBmp, x, y - 2 * blockSize, blockSize, TColors.background.color, TColors.background.border);

    drawTetrisBlock(myBmp, x, y + blockSize, blockSize, TColors.L.color, TColors.L.border);
    drawTetrisBlock(myBmp, x + blockSize, y + blockSize, blockSize, TColors.L.color, TColors.L.border);
  }

  static void shiftJTetromino(DrawToBmp.BMPImage myBmp, int x, int y, int blockSize) {
    drawTetrisBlock(myBmp, x, y, blockSize, TColors.background.color, TColors.background.border);
    drawTetrisBlock(myBmp, x + blockSize, y - 2 * blockSize, blockSize, TColors.background.color, TColors.background.border);

    drawTetrisBlock(myBmp, x, y + blockSize, blockSize, TColors.J.color, TColors.J.border);
    drawTetrisBlock(myBmp, x + blockSize, y + blockSize, blockSize, TColors.J.color, TColors.J.border);
  }

  static void shiftTTetromino(DrawToBmp.BMPImage myBmp, int x, int y, int blockSize) {
    drawTetrisBlock(myBmp, x, y, blockSize, TColors.background.color, TColors.background.border);
    drawTetrisBlock(myBmp, x - blockSize, y - blockSize, blockSize, TColors.background.color, TColors.background.border);
    drawTetrisBlock(myBmp, x + blockSize, y - blockSize, blockSize, TColors.background.color, TColors.background.border);

    drawTetrisBlock(myBmp, x, y + blockSize, blockSize, TColors.T.color, TColors.T.border);
    drawTetrisBlock(myBmp, x - blockSize, y, blockSize, TColors.T.color, TColors.T.border);
    drawTetrisBlock(myBmp, x + blockSize, y, blockSize, TColors.T.color, TColors.T.border);
  }

  static void drawOTetromino(DrawToBmp.BMPImage myBmp, int x, int y, int blockSize) {
    drawTetrisBlock(myBmp, x, y, blockSize, TColors.O.color, TColors.O.border);
    drawTetrisBlock(myBmp, x + blockSize, y, blockSize, TColors.O.color, TColors.O.border);
    drawTetrisBlock(myBmp, x, y - blockSize, blockSize, TColors.O.color, TColors.O.border);
    drawTetrisBlock(myBmp, x + blockSize, y - blockSize, blockSize, TColors.O.color, TColors.O.border);

  }

  static void drawITetromino(DrawToBmp.BMPImage myBmp, int x, int y, int blockSize) {
    drawTetrisBlock(myBmp, x, y, blockSize, TColors.I.color, TColors.I.border);
    drawTetrisBlock(myBmp, x, y - blockSize, blockSize, TColors.I.color, TColors.I.border);
    drawTetrisBlock(myBmp, x, y - 2 * blockSize, blockSize, TColors.I.color, TColors.I.border);
    drawTetrisBlock(myBmp, x, y - 3 * blockSize, blockSize, TColors.I.color, TColors.I.border);
  }

  static void drawSTetromino(
      DrawToBmp.BMPImage myBmp, int x, int y, int blockSize
  ) {
    drawTetrisBlock(myBmp, x, y, blockSize, TColors.S.color, TColors.S.border);
    drawTetrisBlock(myBmp, x + blockSize, y, blockSize, TColors.S.color, TColors.S.border);
    drawTetrisBlock(myBmp, x + blockSize, y - blockSize, blockSize, TColors.S.color, TColors.S.border);
    drawTetrisBlock(myBmp, x + 2 * blockSize, y - blockSize, blockSize, TColors.S.color, TColors.S.border);
  }

  static void drawZTetromino(DrawToBmp.BMPImage myBmp, int x, int y, int blockSize) {
    drawTetrisBlock(myBmp, x, y, blockSize, TColors.Z.color, TColors.Z.border);
    drawTetrisBlock(myBmp, x - blockSize, y, blockSize, TColors.Z.color, TColors.Z.border);
    drawTetrisBlock(myBmp, x - blockSize, y - blockSize, blockSize, TColors.Z.color, TColors.Z.border);
    drawTetrisBlock(myBmp, x - 2 * blockSize, y - blockSize, blockSize, TColors.Z.color, TColors.Z.border);
  }

  static void drawLTetromino(DrawToBmp.BMPImage myBmp, int x, int y, int blockSize) {
    drawTetrisBlock(myBmp, x, y, blockSize, TColors.L.color, TColors.L.border);
    drawTetrisBlock(myBmp, x + blockSize, y, blockSize, TColors.L.color, TColors.L.border);
    drawTetrisBlock(myBmp, x, y - blockSize, blockSize, TColors.L.color, TColors.L.border);
    drawTetrisBlock(myBmp, x, y - 2 * blockSize, blockSize, TColors.L.color, TColors.L.border);
  }

  static void drawJTetromino(DrawToBmp.BMPImage myBmp, int x, int y, int blockSize) {
    drawTetrisBlock(myBmp, x, y, blockSize, TColors.J.color, TColors.J.border);
    drawTetrisBlock(myBmp, x + blockSize, y, blockSize, TColors.J.color, TColors.J.border);
    drawTetrisBlock(myBmp, x + blockSize, y - blockSize, blockSize, TColors.J.color, TColors.J.border);
    drawTetrisBlock(myBmp, x + blockSize, y - 2 * blockSize, blockSize, TColors.J.color, TColors.J.border);
  }

  static void drawTTetromino(DrawToBmp.BMPImage myBmp, int x, int y, int blockSize) {
    drawTetrisBlock(myBmp, x, y, blockSize, TColors.T.color, TColors.T.border);
    drawTetrisBlock(myBmp, x - blockSize, y - blockSize, blockSize, TColors.T.color, TColors.T.border);
    drawTetrisBlock(myBmp, x, y - blockSize, blockSize, TColors.T.color, TColors.T.border);
    drawTetrisBlock(myBmp, x + blockSize, y - blockSize, blockSize, TColors.T.color, TColors.T.border);
  }

  private static void drawTetrisBlock(
      DrawToBmp.BMPImage myBmp,
      int x, int y,
      int blockSize, byte[] color, byte[] borderColor
  ) {

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
}

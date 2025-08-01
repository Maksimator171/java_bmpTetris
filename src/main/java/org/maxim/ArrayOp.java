package org.maxim;

public class ArrayOp {

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
}

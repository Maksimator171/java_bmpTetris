import java.io.IOException;

public class test {

  static String[] a(String ... a) {
    return a;
  }

  public static void main(String[] args) throws IOException {
    Main.main(a("picture.bmp", "150", "100", "clear", "FFFFFF", "spiral", "FF0000",
        "rect", "10", "10", "50", "30", "00FF00"));

    Main.main(a("picture1.bmp", "300", "300", "clear", "FFFFFF", "Otetromino",
        "50", "50", "Itetromino", "100", "50", "Stetromino", "150", "50",
        "Ztetromino", "50", "120", "Ltetromino", "100", "120",
        "Jtetromino", "150", "120", "Ttetromino", "200", "120"));

    Main.main(a("picture2.bmp", "100", "100", "clear", "a0a0a0",
        "Otetromino", "-5", "-5", "Itetromino", "90", "90", "Ttetromino", "80", "80"));

    Main.main(a("picture3.bmp", "500", "500", "clear", "000000", "Itetromino", "0", "0",
        "Itetromino", "0", "100", "Itetromino", "0", "200", "Itetromino", "0", "300", "Itetromino", "0", "400"));

    Main.main(a("picture4.bmp", "400", "400", "clear", "FFFFFF" , "rect", "10", "10", "380", "380", "000000",
        "spiral", "FFFFFF", "Otetromino", "50", "50", "Itetromino", "150", "50", "Stetromino", "250", "50",
        "Ztetromino", "50", "150", "Ltetromino", "150", "150", "Jtetromino", "250", "150", "Ttetromino", "350", "150",
        "rect", "300", "300", "50", "50", "FF0000"));

    Main.main(a("picture5.bmp", "100", "200", "Otetromino", "0", "0", "Itetromino", "25", "25", "Stetromino", "25", "50",
        "Ztetromino", "50", "25", "Ltetromino", "50", "50", "Jtetromino", "50", "75", "Ttetromino", "75", "50"));
  }

}

import java.util.Random;

public class Main {
    public static void main(String args[]) throws Exception {
        //duzySwiat();
        //malySwiat();
        //dowolnySwiat();


    }

    public static void duzySwiat() {
        int a = 200, b = 200;
        Swiat swiat = new Swiat(a, b);
        for (int i = 0; i < a * b / 300; i++)
            swiat.dodajRecznie('G', new Random().nextInt(a), new Random().nextInt(b));
        for (int i = 0; i < a * b / 1500; i++)
            swiat.dodajRecznie('S', new Random().nextInt(a), new Random().nextInt(b));
        for (int i = 0; i < a * b / 450; i++)
            swiat.dodajRecznie('D', new Random().nextInt(a), new Random().nextInt(b));
        for (int i = 0; i < a * b / 2000; i++)
            swiat.dodajRecznie('W', new Random().nextInt(a), new Random().nextInt(b));
        for (int i = 0; i < a * b / 200; i++)
            swiat.dodajRecznie('T', new Random().nextInt(a), new Random().nextInt(b));
        for (int i = 0; i < a * b / 2000; i++)
            swiat.dodajRecznie('O', new Random().nextInt(a), new Random().nextInt(b));
        swiat.dodajNieFizycznego('K', 10, 0);
        for (int i = 0; i < 1000; i++) {
            swiat.tura();
            if (i % 50 == 0)
                swiat.drukujWynik();
        }
        swiat.drukujWynik();

    }

    public static void malySwiat() throws Exception {
        int a = 15, b = 15;
        Swiat swiat = new Swiat(a, b);
        for (int i = 0; i < 10; i++)
            swiat.dodajRecznie('G', new Random().nextInt(a), new Random().nextInt(b));
        for (int i = 0; i < 3; i++)
            swiat.dodajRecznie('S', new Random().nextInt(a), new Random().nextInt(b));
        for (int i = 0; i < 10; i++)
            swiat.dodajRecznie('D', new Random().nextInt(a), new Random().nextInt(b));
        for (int i = 0; i < 2; i++)
            swiat.dodajRecznie('W', new Random().nextInt(a), new Random().nextInt(b));
        for (int i = 0; i < 5; i++)
            swiat.dodajRecznie('T', new Random().nextInt(a), new Random().nextInt(b));
        for (int i = 0; i < 3; i++)
            swiat.dodajRecznie('O', new Random().nextInt(a), new Random().nextInt(b));
        swiat.dodajNieFizycznego('K', 10, 0);
        for (int i = 0; i < 30; i++) {
            swiat.tura();
            swiat.drukuj();
            System.in.read();
        }
        swiat.drukujWynik();
    }

    public static void dowolnySwiat() throws Exception {
        int a = 2, b = 1;
        Swiat swiat = new Swiat(a, b);
        swiat.dodajRecznie('W', 0, 0);
        swiat.dodajRecznie('O', 1, 0);
        swiat.drukuj();
        for (int i = 0; i < 30; i++) {
            swiat.tura();
            swiat.drukuj();
            System.in.read();
        }
        swiat.drukujWynik();
    }
}

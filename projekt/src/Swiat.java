import java.util.*;


public class Swiat {
    private int bokX, bokY;
    private Fabryka f = new Fabryka(this);
    private Organizm[][] mapa;
    private ArrayList<Organizm> obecne = new ArrayList<>();
    private ArrayList<Organizm> nowe = new ArrayList<>();
    private ArrayList<Organizm> blokowane = new ArrayList<>();

    Swiat(int a, int b) {
        mapa = new Organizm[a][b];
        bokX = a;
        bokY = b;
    }

    public void drukuj() {
        for (int i = 0; i < bokX; i++) {
            for (int j = 0; j < bokY; j++) {
                if (mapa[i][j] == null)
                    System.out.print(".     ");
                else
                    System.out.print(mapa[i][j].getZnak() + "     ");
            }
            System.out.println("\n");
        }
        System.out.println("\n");
    }


    public void tura() {
        obecne.sort(Swiat.InicjatywaComparator);
        nowe.clear();

        for (int j = 0; j < obecne.size(); j++) {
            if (!blokowane.contains(obecne.get(j)))
                obecne.get(j).tura();
        }
        //drukuj();
        obecne.addAll(nowe);
        blokowane.clear();
    }

    public static Comparator<Organizm> InicjatywaComparator = new Comparator<Organizm>() {
        @Override
        public int compare(Organizm o1, Organizm o2) {
            return o2.getInicjatywa() - o1.getInicjatywa();
        }
    };

    public void smierc(Organizm o) {
        mapa[o.getPozycjaX()][o.getPozycjaY()] = null;
        obecne.remove(o);
        nowe.remove(o);
    }

    private void dodajOrganizm(Organizm o, int x, int y) {
        Organizm c = f.zrob(o.getZnak(), x, y);
        mapa[c.getPozycjaX()][c.getPozycjaY()] = c;
        nowe.add(c);
    }

    public void dodajRecznie(char c, int x, int y) {
        if (mapa[x][y] == null) {
            Organizm o = f.zrob(c, x, y);
            mapa[x][y] = o;
            obecne.add(o);
        }
    }

    public void dodajNieFizycznego(char c, int x, int y) {
        Organizm o = f.zrob(c, x, y);
        obecne.add(o);
    }

    private ArrayList<Para> sasiedzi(int x, int y) {
        ArrayList<Para> zwrocSasiadow = new ArrayList<>();
        if (x > 0)
            zwrocSasiadow.add(new Para(x - 1, y));
        if (y > 0)
            zwrocSasiadow.add(new Para(x, y - 1));
        if (x < bokX - 1)
            zwrocSasiadow.add(new Para(x + 1, y));
        if (y < bokY - 1)
            zwrocSasiadow.add(new Para(x, y + 1));
        return zwrocSasiadow;
    }

    private Para wybierzPuste(ArrayList<Para> x) {
        ArrayList<Para> zwrocPuste = new ArrayList<>();
        for (Para para : x)
            if (mapa[para.getX()][para.getY()] == null)
                zwrocPuste.add(para);
        if (zwrocPuste.size() > 0)
            return zwrocPuste.get((new Random().nextInt(zwrocPuste.size())));
        return null;
    }

    private Para wybierzNieZGatunku(ArrayList<Para> x, Organizm o) {
        ArrayList<Para> zwroc = new ArrayList<>();
        for (Para para : x) {
            if (mapa[para.getX()][para.getY()] == null || mapa[para.getX()][para.getY()].getClass() != o.getClass())
                zwroc.add(para);
        }
        if (zwroc.size() > 0)
            return zwroc.get((new Random().nextInt(zwroc.size())));
        return null;
    }

    public void rozmnoz(Organizm o) {
        Para x = wybierzPuste(sasiedzi(o.getPozycjaX(), o.getPozycjaY()));
        if (x != null)
            dodajOrganizm(o, x.getX(), x.getY());
    }

    public void ruch(Organizm o) {
        Para x = wybierzNieZGatunku(sasiedzi(o.getPozycjaX(), o.getPozycjaY()), o);
        if (x != null) {
            if (mapa[x.getX()][x.getY()] == null)
                swap(x.getX(), x.getY(), o);
            else if ((mapa[x.getX()][x.getY()].spotkanie(o)))
                swap(x.getX(), x.getY(), o);
        }
    }

    private void swap(int x, int y, Organizm o) {
        mapa[o.getPozycjaX()][o.getPozycjaY()] = null;
        mapa[x][y] = o;
        o.setPozycjaX(x);
        o.setPozycjaY(y);
    }

    public void drukujWynik() {
        int[] dane = {0, 0, 0, 0, 0, 0, 0};
        for (Organizm organizm : obecne) {
            switch (organizm.getZnak()) {
                case ('G') -> dane[0]++;
                case ('S') -> dane[1]++;
                case ('D') -> dane[2]++;
                case ('W') -> dane[3]++;
                case ('T') -> dane[4]++;
                case ('O') -> dane[5]++;
                default -> dane[6]++;
            }
        }
        System.out.println("\n");
        System.out.println("Trawa " + dane[0]);
        System.out.println("Owce " + dane[1]);
        System.out.println("Mlecze " + dane[2]);
        System.out.println("Wilki " + dane[3]);
        System.out.println("Muchomory " + dane[4]);
        System.out.println("Zolwie " + dane[5]);
    }

    public int getBokX() {
        return bokX;
    }

    public int getBokY() {
        return bokY;
    }

    public Organizm[][] getMapa() {
        return mapa;
    }

    public void blokuj(Kosmita k) {
        int x = k.getPozycjaX(), y = k.getPozycjaY();
        for (int i = x - 2; i > 0 && i < x + 2 && i < bokX - 1; i++) {
            for (int j = y - 2; j > 0 && j < y + 2 && j < bokY - 1; j++) {
                if (mapa[i][j] != null && mapa[i][j].getClass() != k.getClass())
                    blokowane.add(mapa[i][j]);
            }
        }
    }

    public Organizm getZPozycji(int x, int y) {
        return mapa[x][y];
    }

    public void ruchKosmity(Kosmita k) {
        ArrayList<Para> mozliwe = sasiedzi(k.getPozycjaX(), k.getPozycjaY());
        Para cel = mozliwe.get((new Random().nextInt(mozliwe.size())));
        k.setPozycjaX(cel.getX());
        k.setPozycjaY(cel.getY());
    }

    public void wymuszonyRuch(Organizm o, int x, int y) {
        boolean wynik;
        if (mapa[x][y] == null)
            swap(x, y, o);
        else if (mapa[x][y].spotkanie((o)))
            swap(x, y, o);

    }

    public void wymusWstawienie(Organizm o, int x, int y) {
        if (mapa[x][y] != null) {
            mapa[x][y].smierc();
        }
        mapa[x][y] = o;
    }

    public boolean zwrocCzyIstnieje(Organizm o) {
        return obecne.contains(o);
    }

}
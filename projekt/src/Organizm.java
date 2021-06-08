import java.util.Random;

public abstract class Organizm {
    private int sila, inicjatywa, dlugoscZycia, silaDoRozmnozenia, zasieg, pozycjaX, pozycjaY;
    private char znak;
    protected Swiat swiat;

    Organizm(Swiat s, int x, int y) {
        setSwiat(s);
        setPozycjaX(x);
        setPozycjaY(y);
    }

    public void tura() {
        sila++;
        dlugoscZycia--;
        if (zasieg > 0)
            ruch();
        if (sila > silaDoRozmnozenia)
            rozmnozenie();
        if (dlugoscZycia < 0)
            smierc();

    }

    protected void ruch() {
        swiat.ruch(this);
    }

    protected void smierc() {
        swiat.smierc(this);
    }

    protected void rozmnozenie() {
        sila /= 2;
        swiat.rozmnoz(this);
    }

    public void setSwiat(Swiat swiat) {
        this.swiat = swiat;
    }

    public void setSila(int sila) {
        this.sila = sila;
    }

    public void setInicjatywa(int inicjatywa) {
        this.inicjatywa = inicjatywa;
    }

    public void setDlugoscZycia(int dlugoscZycia) {
        this.dlugoscZycia = dlugoscZycia;
    }

    public void setSilaDoRozmnozenia(int silaDoRozmnozenia) {
        this.silaDoRozmnozenia = silaDoRozmnozenia;
    }

    public void setZasieg(int zasieg) {
        this.zasieg = zasieg;
    }

    public void setZnak(char znak) {
        this.znak = znak;
    }

    public void setPozycjaX(int pozycjaX) {
        this.pozycjaX = pozycjaX;
    }

    public void setPozycjaY(int pozycjaY) {
        this.pozycjaY = pozycjaY;
    }

    public int getSila() {
        return sila;
    }

    public int getInicjatywa() {
        return inicjatywa;
    }

    public char getZnak() {
        return znak;
    }

    public int getPozycjaX() {
        return pozycjaX;
    }

    public int getPozycjaY() {
        return pozycjaY;
    }

    public abstract boolean spotkanie(Organizm o);

    public int getSilaDoRozmnozenia() {
        return silaDoRozmnozenia;
    }
}

class Roslina extends Organizm {
    Roslina(Swiat s, int x, int y) {
        super(s, x, y);
        setZasieg(0);
    }

    @Override
    public boolean spotkanie(Organizm o) {
        o.setSila(o.getSila() + getSilaDoRozmnozenia() / 2);
        smierc();
        return true;
    }
}

class Trawa extends Roslina {
    Trawa(Swiat s, int x, int y) {
        super(s, x, y);
        setSila(0);
        setInicjatywa(0);
        setDlugoscZycia(6);
        setSilaDoRozmnozenia(3);
        setZnak('G');
    }
}


class Mlecz extends Roslina {
    Mlecz(Swiat s, int x, int y) {
        super(s, x, y);
        setSila(0);
        setInicjatywa(0);
        setDlugoscZycia(6);
        setSilaDoRozmnozenia(3);
        setZnak('D');
    }
}

class Zwierze extends Organizm {
    Zwierze(Swiat s, int x, int y) {
        super(s, x, y);
        setZasieg(1);
    }

    @Override
    public boolean spotkanie(Organizm o) {
        if (o.getSila() > getSila()) {
            o.setSila(o.getSila() + getSila() / 2);
            smierc();
            return true;
        } else {

            o.smierc();
            return false;
        }
    }
}

class Owca extends Zwierze {
    Owca(Swiat s, int x, int y) {
        super(s, x, y);
        setSila(3);
        setInicjatywa(3);
        setDlugoscZycia(10);
        setSilaDoRozmnozenia(10);
        setZnak('S');
    }
}

class Wilk extends Zwierze {
    Wilk(Swiat s, int x, int y) {
        super(s, x, y);
        setSila(6);
        setInicjatywa(5);
        setDlugoscZycia(15);
        setSilaDoRozmnozenia(20);
        setZnak('W');
    }

}

class Muchomor extends Roslina {
    Muchomor(Swiat s, int x, int y) {
        super(s, x, y);
        setSila(0);
        setInicjatywa(1);
        setDlugoscZycia(20);
        setSilaDoRozmnozenia(13);
        setZnak('T');
    }

    @Override
    public boolean spotkanie(Organizm o) {
        smierc();
        o.smierc();
        return false;
    }
}

class Kosmita extends Organizm {
    private boolean aktywny = false;

    Kosmita(Swiat s, int x, int y) {
        super(s, x, y);
        setInicjatywa(999999);
        setZasieg(1);
        setSwiat(s);
    }

    @Override
    public void tura() {
        if (new Random().nextInt(100) < 20 && aktywny) {
            aktywny = false;
        } else if (new Random().nextInt(100) < 20 && !aktywny) {
            aktywny = true;
            pojawSie();
        }
        if (aktywny) {
            blokuj();
            ruch();

        }
    }

    @Override
    public void ruch() {
        swiat.ruchKosmity(this);
    }

    public void blokuj() {
        swiat.blokuj(this);
    }

    public void pojawSie() {
        setPozycjaX(new Random().nextInt(swiat.getBokX()));
        setPozycjaY(new Random().nextInt(swiat.getBokY()));

    }

    @Override
    public boolean spotkanie(Organizm o) {
        return false;
    }
}

class Zulw extends Zwierze {
    private boolean przyczepiony = false;
    private int trawienie = 0;
    private Organizm nosiciel;

    Zulw(Swiat s, int x, int y) {
        super(s, x, y);
        setSila(0);
        setInicjatywa(1);
        setDlugoscZycia(20);
        setSilaDoRozmnozenia(17);
        setZnak('O');
    }

    public void przyczep(Organizm o) {
        swiat.wymusWstawienie(o, o.getPozycjaX(), getPozycjaY());
        nosiciel = o;
        setPozycjaX(o.getPozycjaX());
        setPozycjaY(o.getPozycjaY());
        przyczepiony = true;
    }

    @Override
    public void tura() {
        if (!przyczepiony)
            super.tura();
        else {
            if (trawienie > 2) {
                nosiciel.smierc();
                swiat.wymusWstawienie(this, getPozycjaX(), getPozycjaY());
                trawienie = 0;
                przyczepiony = false;
            } else {
                trawienie++;
                setPozycjaX(nosiciel.getPozycjaX());
                setPozycjaY(nosiciel.getPozycjaY());
            }
        }
    }

    @Override
    public boolean spotkanie(Organizm o) {
        przyczep(o);
        return true;
    }
}

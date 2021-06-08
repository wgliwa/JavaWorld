public class Fabryka {
    private Swiat swiat;

    Fabryka(Swiat s) {
        setSwiat(s);
    }

    public void setSwiat(Swiat swiat) {
        this.swiat = swiat;
    }

    public Organizm zrob(char typ, int x, int y) {
        switch (typ) {
            case ('G'):
                return new Trawa(swiat, x, y);
            case ('S'):
                return new Owca(swiat, x, y);
            case ('D'):
                return new Mlecz(swiat, x, y);
            case ('W'):
                return new Wilk(swiat, x, y);
            case ('T'):
                return new Muchomor(swiat, x, y);
            case ('K'):
                return new Kosmita(swiat, x, y);
            case ('O'):
                return new Zulw(swiat, x, y);
        }
        throw new IllegalArgumentException();
    }
}

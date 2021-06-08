import static org.junit.Assert.*;

import org.junit.*;

public class ProjektTest {
    Swiat swiat = new Swiat(10, 10);

    @Before
    public void a() {
        swiat = new Swiat(10, 10);
    }

    @Test
    public void trawaVsOwca() {
        Trawa t = new Trawa(swiat, 0, 0);
        Owca o = new Owca(swiat, 1, 1);
        swiat.wymuszonyRuch(o, 0, 0);
        assertSame(swiat.getZPozycji(0, 0), o);
        assertNull(swiat.getZPozycji(1, 1));
    }

    @Test
    public void muchomorVsOwca() {
        swiat.dodajRecznie('T', 0, 0);
        swiat.dodajRecznie('S', 1, 1);
        System.out.println(swiat.getZPozycji(1, 1));
        swiat.wymuszonyRuch(swiat.getZPozycji(1, 1), 0, 0);
        assertNull(swiat.getZPozycji(0, 0));
        assertNull(swiat.getZPozycji(1, 1));

    }

    @Test
    public void testKosmity() {
        swiat.dodajRecznie('T', 0, 0);
        swiat.dodajRecznie('S', 1, 1);
        swiat.dodajNieFizycznego('K', 2, 2);
        assertEquals(3, swiat.getZPozycji(1, 1).getSila());
        assertEquals(0, swiat.getZPozycji(0, 0).getSila());
    }

    @Test
    public void testZolwia() {
        swiat.dodajRecznie('O', 0, 0);
        swiat.dodajRecznie('S', 1, 1);
        Organizm o = swiat.getZPozycji(1, 1);
        swiat.wymuszonyRuch(swiat.getZPozycji(1, 1), 0, 0);
        swiat.tura();
        swiat.tura();
        swiat.tura();
        swiat.tura();
        swiat.tura();
        assertFalse(swiat.zwrocCzyIstnieje(o));
    }

    @Test
    public void owcaVsWilk() {
        swiat.dodajRecznie('S', 0, 0);
        swiat.dodajRecznie('W', 1, 1);
        Organizm o = swiat.getZPozycji(1, 1);
        swiat.wymuszonyRuch(swiat.getZPozycji(1, 1), 0, 0);
        assertEquals(7, swiat.getZPozycji(0, 0).getSila());
    }
}

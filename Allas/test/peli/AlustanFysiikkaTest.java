package peli;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import allas.domain.Pallo;
import allas.peli.Alusta;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Sami
 */
public class AlustanFysiikkaTest {

    Alusta alusta;

    public AlustanFysiikkaTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        alusta = new Alusta(1000, 400, 10, 30, 30);
        alusta.generoiPallot();
        alusta.asetaPallot(1000 * 6 / 8, 400 / 2);
        alusta.getPallot().get(0).setX(50);
        alusta.getPallot().get(0).setY(50);
    }

    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

    public void asetaVauhti() {
    }

    @Test
    public void putoaakoPallo() {
        alusta.getPallot().get(0).setX(500);
        alusta.getPallot().get(0).setY(350);
        alusta.getPallot().get(0).setVx(0);
        alusta.getPallot().get(0).setVy(2);
        for (int i = 0; i < 50; i++) {
            alusta.getPallot().get(0).liikuta();
            System.out.println(alusta.getPallot().get(0).getX() + ", " + alusta.getPallot().get(0).getY());
            if (alusta.putoaaPussiin(alusta.getPallot().get(0)) != 0) {
                if (alusta.getPallot().get(0).getPussissa()) {
                    continue;
                }
                alusta.getPallot().get(0).setPussissa(true);
                System.out.println("pallo putosi pussiin");
            }
        }
        assertEquals(true, alusta.getPallot().get(0).getPussissa());


    }

    @Test
    public void putoaakoPalloKulmapussiin() {
        alusta.getPallot().get(0).setX(970);
        alusta.getPallot().get(0).setY(370);
        alusta.getPallot().get(0).setVx(1);
        alusta.getPallot().get(0).setVy(1);
        for (int i = 0; i < 50; i++) {
            alusta.getPallot().get(0).liikuta();
//                   System.out.println(alusta.getPallot().get(0).getX() + ", " + alusta.getPallot().get(0).getY());
            if (alusta.putoaaPussiin(alusta.getPallot().get(0)) != 0) {
                if (alusta.getPallot().get(0).getPussissa()) {
                    continue;
                }
                alusta.getPallot().get(0).setPussissa(true);
//                System.out.println("pallo putosi kulmapussiin");
            }
        }
        assertEquals(true, alusta.getPallot().get(0).getPussissa());
    }

//    @Test
//    public void poistetaankoPudonnutPallo(){
//        
//    }
    @Test
    public void tunnistetaankoTormaysSeinaan() {
        Pallo pallo = alusta.getPallot().get(0);
        alusta.getPallot().get(0).setVx(0);
        alusta.getPallot().get(0).setVy(-2.5);
        boolean tunnistettu = false;

        for (int i = 0; i < 20; i++) {
            pallo.liikuta();
//            System.out.println(pallo.getX() + ", " + pallo.getY());
            if (alusta.osuuSeinaan(pallo) && tunnistettu == false) {
                tunnistettu = true;
//                System.out.println("Osui seinään paikassa:" + pallo.getX() + ", " + pallo.getY());
            }
        }
        assertEquals(true, tunnistettu);
    }

    @Test
    public void tunnistetaankoVinoTormaysSeinaan() {
        Pallo pallo = alusta.getPallot().get(0);
        pallo.setVx(1.1358);
        pallo.setVy(-2.13545);
        boolean tunnistettu = false;

        for (int i = 0; i < 20; i++) {
            pallo.liikuta();
//            System.out.println(pallo.getX() + ", " + pallo.getY());
            if (alusta.osuuSeinaan(pallo) && tunnistettu == false) {
                tunnistettu = true;
//                System.out.println("Osui seinään paikassa:" + pallo.getX() + ", " + pallo.getY());
            }
        }
        assertEquals(true, tunnistettu);
    }

    @Test
    public void tunnistetaankoTormaysPalloon() {
        Pallo pallo = alusta.getPallot().get(0);
        Pallo pallo2 = new Pallo(70, 50, 2, 10);

        pallo.setVx(2);
        pallo.setVy(0);
        boolean tunnistettu = false;

        for (int i = 0; i < 10; i++) {
            pallo.liikuta();
//            System.out.println(pallo.getX() + ", " + pallo.getY());
//            System.out.println(pallo2.getX() + ", " + pallo2.getY());
            if (alusta.osuuPalloon(pallo, pallo2) && tunnistettu == false) {
                tunnistettu = true;
//                System.out.println("Osui palloon");
            }
        }
        assertEquals(true, tunnistettu);

    }

    @Test
    public void tunnistetaankoTormaysLiikkuvaanPalloon() {
        Pallo pallo = alusta.getPallot().get(0);
        Pallo pallo2 = new Pallo(70, 50, 2, 10);

        pallo.setVx(3);
        pallo.setVy(0);
        pallo2.setVx(1);
        pallo2.setVy(0);

        boolean tunnistettu = false;

        for (int i = 0; i < 10; i++) {
            pallo.liikuta();
            pallo2.liikuta();
//            System.out.println(pallo.getX() + ", " + pallo.getY());
//            System.out.println(pallo2.getX() + ", " + pallo2.getY());
            if (alusta.osuuPalloon(pallo, pallo2) && tunnistettu == false) {
                tunnistettu = true;
//                System.out.println("Osui palloon");
            }
        }
        assertEquals(true, tunnistettu);

    }
}

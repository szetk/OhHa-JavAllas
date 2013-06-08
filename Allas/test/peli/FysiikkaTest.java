package peli;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import allas.domain.Pallo;
import allas.peli.Peli;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Sami
 */
public class FysiikkaTest {

    Peli peli;

    public FysiikkaTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        peli = new Peli(1000, 400, 10, 30, 30);
        peli.getPallot().get(0).setX(50);
        peli.getPallot().get(0).setY(50);
    }

    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

    @Test
    public void putoaakoPallo() {
        peli.getPallot().get(0).setX(500);
        peli.getPallot().get(0).setY(350);
        peli.asetaVauhti(peli.getPallot().get(0), 0, 2);
        for (int i = 0; i < 50; i++) {
            peli.getPallot().get(0).liikuta();
                   System.out.println(peli.getPallot().get(0).getX() + ", " + peli.getPallot().get(0).getY());
            if (peli.putoaaPussiin(peli.getPallot().get(0)) != 0) {
                if (peli.getPallot().get(0).getPussissa()) {
                    continue;
                }
                peli.getPallot().get(0).setPussissa(true);
                System.out.println("pallo putosi pussiin");
            }
        }
        assertEquals(true, peli.getPallot().get(0).getPussissa());


    }

    @Test
    public void putoaakoPalloKulmapussiin() {
        peli.getPallot().get(0).setX(970);
        peli.getPallot().get(0).setY(370);
        peli.asetaVauhti(peli.getPallot().get(0), 1, 1);
        for (int i = 0; i < 50; i++) {
            peli.getPallot().get(0).liikuta();
//                   System.out.println(peli.getPallot().get(0).getX() + ", " + peli.getPallot().get(0).getY());
            if (peli.putoaaPussiin(peli.getPallot().get(0)) != 0) {
                if (peli.getPallot().get(0).getPussissa()) {
                    continue;
                }
                peli.getPallot().get(0).setPussissa(true);
//                System.out.println("pallo putosi kulmapussiin");
            }
        }
        assertEquals(true, peli.getPallot().get(0).getPussissa());
    }

//    @Test
//    public void poistetaankoPudonnutPallo(){
//        
//    }
    @Test
    public void tunnistetaankoTormaysSeinaan() {
        Pallo pallo = peli.getPallot().get(0);
        peli.asetaVauhti(pallo, 0, -2.5);
        boolean tunnistettu = false;

        for (int i = 0; i < 20; i++) {
            pallo.liikuta();
//            System.out.println(pallo.getX() + ", " + pallo.getY());
            if (peli.osuuSeinaan(pallo) && tunnistettu == false) {
                tunnistettu = true;
//                System.out.println("Osui seinään paikassa:" + pallo.getX() + ", " + pallo.getY());
            }
        }
        assertEquals(true, tunnistettu);
    }

    @Test
    public void tunnistetaankoVinoTormaysSeinaan() {
        Pallo pallo = peli.getPallot().get(0);
        peli.asetaVauhti(pallo, 1.1358, -2.13545);
        boolean tunnistettu = false;

        for (int i = 0; i < 20; i++) {
            pallo.liikuta();
//            System.out.println(pallo.getX() + ", " + pallo.getY());
            if (peli.osuuSeinaan(pallo) && tunnistettu == false) {
                tunnistettu = true;
//                System.out.println("Osui seinään paikassa:" + pallo.getX() + ", " + pallo.getY());
            }
        }
        assertEquals(true, tunnistettu);
    }

    @Test
    public void tunnistetaankoTormaysPalloon() {
        Pallo pallo = peli.getPallot().get(0);
        Pallo pallo2 = new Pallo(70, 50, 2, 10);

        peli.asetaVauhti(pallo, 2, 0);
        boolean tunnistettu = false;

        for (int i = 0; i < 10; i++) {
            pallo.liikuta();
//            System.out.println(pallo.getX() + ", " + pallo.getY());
//            System.out.println(pallo2.getX() + ", " + pallo2.getY());
            if (peli.osuuPalloon(pallo, pallo2) && tunnistettu == false) {
                tunnistettu = true;
//                System.out.println("Osui palloon");
            }
        }
        assertEquals(true, tunnistettu);

    }

    @Test
    public void tunnistetaankoTormaysLiikkuvaanPalloon() {
        Pallo pallo = peli.getPallot().get(0);
        Pallo pallo2 = new Pallo(70, 50, 2, 10);

        peli.asetaVauhti(pallo, 3, 0);
        peli.asetaVauhti(pallo2, 1, 0);
        boolean tunnistettu = false;

        for (int i = 0; i < 10; i++) {
            pallo.liikuta();
            pallo2.liikuta();
//            System.out.println(pallo.getX() + ", " + pallo.getY());
//            System.out.println(pallo2.getX() + ", " + pallo2.getY());
            if (peli.osuuPalloon(pallo, pallo2) && tunnistettu == false) {
                tunnistettu = true;
//                System.out.println("Osui palloon");
            }
        }
        assertEquals(true, tunnistettu);

    }
    
    
}

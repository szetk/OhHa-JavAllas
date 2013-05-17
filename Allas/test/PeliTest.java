/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import allas.domain.Pallo;
import allas.peli.Peli;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Sami
 */
public class PeliTest {

    Peli peli;

    public PeliTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        peli = new Peli(1000, 400, 10);
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
    public void lasketaankoEtaisyysOikein() { 
        assertEquals(10, peli.etaisyys(peli.getPallot().get(0), 60, 50));
        assertEquals(10, peli.etaisyys(peli.getPallot().get(0), 40, 50));
        assertEquals(14, peli.etaisyys(peli.getPallot().get(0), 60, 60)); // Oikea tulos olisi toki 14,1421356...
        assertEquals(14, peli.etaisyys(peli.getPallot().get(0), 40, 40));
    }



    @Test
    public void putoaakoPallo() {
        peli.getPallot().get(0).setX(500);
        peli.getPallot().get(0).setY(350);
        peli.asetaVauhti(peli.getPallot().get(0), 0, 1);
        for (int i = 0; i < 50; i++) {
            peli.getPallot().get(0).liikuta();
            //       System.out.println(peli.getPallot().get(0).getX() + ", " + peli.getPallot().get(0).getY());
            if (peli.putoaaPussiin(peli.getPallot().get(0))) {
                if (peli.getPallot().get(0).getPussissa()) {
                    continue;
                }
                peli.getPallot().get(0).pussita();
//                System.out.println("pallo putosi pussiin");
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
            if (peli.putoaaPussiin(peli.getPallot().get(0))) {
                if (peli.getPallot().get(0).getPussissa()) {
                    continue;
                }
                peli.getPallot().get(0).pussita();
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
        peli.asetaVauhti(pallo, 0, -5);
        boolean tunnistettu = false;
        
        for (int i = 0; i < 10; i++){
            pallo.liikuta();
//            System.out.println(pallo.getX() + ", " + pallo.getY());
            if (peli.osuuSeinaan(pallo) && tunnistettu == false){
                tunnistettu = true;
//                System.out.println("Osui seinään");
            }
        }
        assertEquals(true, tunnistettu);
    }

    @Test
    public void tunnistetaankoTormaysPalloon() {
        
    }
}

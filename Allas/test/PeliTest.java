/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import allas.peli.Peli;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Sami
 */
public class PeliTest {

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
        Peli peli = new Peli(1000, 400, 10);
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
    public void jarruTesti() {
        Peli peli = new Peli(1000, 400, 10);
        peli.asetaVauhti(peli.getPallot().get(0), 3, 3);
        assertEquals(3, peli.getPallot().get(0).getVx());
        peli.jarruta(peli.getPallot().get(0), 2, 1);
        assertEquals(1, peli.getPallot().get(0).getVx());
        assertEquals(2, peli.getPallot().get(0).getVy());
        peli.jarruta(peli.getPallot().get(0), 0, 4);
        assertEquals(0, peli.getPallot().get(0).getVy());
    }

    @Test
    public void lasketaankoEtaisyysOikein() { // Copy-pastekoodia on koska en saanu tota @Before setUppia toimimaan!, katon tän myöhemmin järkeväksi
        Peli peli = new Peli(1000, 400, 10);
        peli.getPallot().get(0).setX(50);
        peli.getPallot().get(0).setY(50);
        assertEquals(10, peli.etaisyys(peli.getPallot().get(0), 60, 50));
        assertEquals(10, peli.etaisyys(peli.getPallot().get(0), 40, 50));
        assertEquals(14, peli.etaisyys(peli.getPallot().get(0), 60, 60)); // Oikea tulos olisi toki 14,1421356...
        assertEquals(14, peli.etaisyys(peli.getPallot().get(0), 40, 40));
    }

    @Test
    public void toimiikoLiikuttaminenYhtaan() {
        Peli peli = new Peli(1000, 400, 10);
        peli.getPallot().get(0).setX(50);
        peli.getPallot().get(0).setY(50);
        peli.asetaVauhti(peli.getPallot().get(0), 3, 3);

    }

    @Test
    public void putoaakoPallo() {
        Peli peli = new Peli(1000, 400, 10);
        peli.getPallot().get(0).setX(500);
        peli.getPallot().get(0).setY(350);
        peli.asetaVauhti(peli.getPallot().get(0), 0, 1);
        for (int i = 0; i < 50; i++) {
            peli.liikuta(peli.getPallot().get(0));
     //       System.out.println(peli.getPallot().get(0).getX() + ", " + peli.getPallot().get(0).getY());
            if (peli.putoaaPussiin(peli.getPallot().get(0))){
                if (peli.getPallot().get(0).getPussissa()){
                    continue;
                }
                peli.getPallot().get(0).pussita();
                System.out.println("pallo putosi pussiin");
            }
        }
        assertEquals(true, peli.getPallot().get(0).getPussissa());


    }
    
    @Test
    public void putoaakoPalloKulmapussiin(){
    
    }
    
    @Test
    public void negatiivinenNopeus(){
        
    }
    
    @Test
    public void negatiivisenNopeudenJarrutus(){
        
    }
    
    @Test
    public void tunnistetaankoSeinaanOsuminen(){
        
    }
    
    @Test
    public void tunnistetaankoPalloonOsuminen(){
        
    }
    
}

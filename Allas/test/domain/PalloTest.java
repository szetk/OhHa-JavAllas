package domain;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import allas.domain.Pallo;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Sami
 */
public class PalloTest {
    Pallo pallo;

    public PalloTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        pallo = new Pallo (50,50,2);
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
    public void pallonKonstruktori() {
        assertEquals(2, pallo.getN());
        assertEquals(50, pallo.getX(), 0.1);
        assertEquals(50, pallo.getY(), 0.1);
    }

    @Test
    public void lasketaankoEtaisyysOikein() {
        assertEquals(10, pallo.etaisyys(60, 50), 0.001);
        assertEquals(10, pallo.etaisyys(40, 50), 0.001);
        assertEquals(14.1421356, pallo.etaisyys(60, 60), 0.0001); // Oikea tulos 14,1421356...
        assertEquals(14.1421356, pallo.etaisyys(40, 40), 0.0001);
    }

    @Test
    public void pussittuukoPallo() {
        assertEquals(false, pallo.getPussissa());
        pallo.pussita();
        assertEquals(true, pallo.getPussissa());
    }

    @Test
    public void liikuttuukoPallo() {
        pallo.liikuta();
        assertEquals(50, pallo.getX(), 0.1);
        assertEquals(50, pallo.getY(), 0.1);
        pallo.setVx(3);
        pallo.setVy(3);
        assertEquals(50, pallo.getX(), 0.1);
        assertEquals(50, pallo.getY(), 0.1);
        pallo.liikuta();
        assertEquals(53, pallo.getX(), 0.1);
        assertEquals(53, pallo.getY(), 0.1);
    }

    @Test
    public void liikuttuukoNegatiivisesti() {
        pallo.liikuta();
        assertEquals(50, pallo.getX(), 0.1);
        assertEquals(50, pallo.getY(), 0.1);
        pallo.setVx(-3);
        pallo.setVy(-3);
        assertEquals(50, pallo.getX(), 0.1);
        assertEquals(50, pallo.getY(), 0.1);
        pallo.liikuta();
        assertEquals(47, pallo.getX(), 0.1);
        assertEquals(47, pallo.getY(), 0.1);
    }

    @Test
    public void jarruTesti() {
        pallo.setVx(3);
        pallo.setVy(3);
        assertEquals(3, pallo.getVx(), 0.1);
        assertEquals(3, pallo.getVy(), 0.1);
        pallo.jarruta(2, 1);
        assertEquals(1, pallo.getVx(), 0.1);
        assertEquals(2, pallo.getVy(), 0.1);
        pallo.jarruta(0, 4);
        assertEquals(0, pallo.getVy(), 01);
    }

    @Test
    public void jarruTestiNegatiiviselleNopeudelle() {
        pallo.setVx(-3);
        pallo.setVy(-3);
        assertEquals(-3, pallo.getVx(), 0.1);
        assertEquals(-3, pallo.getVy(), 0.1);
        pallo.jarruta(2, 1);
        assertEquals(-1, pallo.getVx(), 0.1);
        assertEquals(-2, pallo.getVy(), 0.1);
        pallo.jarruta(0, 4);
        assertEquals(0, pallo.getVy(), 0.1);
    }
    
    @Test
    public void stoppiTesti() {
        assertEquals(false, pallo.liikkuuko());
        pallo.setVx(-0.00002);
        pallo.setVy(-3);
        assertEquals(true, pallo.liikkuuko());
        pallo.setVx(-3);
        pallo.jarruta(2, 1);
        assertEquals(true, pallo.liikkuuko());
        pallo.jarruta(0, 4);
        assertEquals(true, pallo.liikkuuko());
        pallo.jarruta(9.125, 2);
        assertEquals(false, pallo.liikkuuko());
        pallo.setVx(-0.00000000009);
//        System.out.println(pallo.getVx() + " " + pallo.getVy());
        assertEquals(false, pallo.liikkuuko());
        
        
    }
}

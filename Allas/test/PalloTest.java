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
        assertEquals(0, pallo.getX(), pallo.getY());
    }

    @Test
    public void lasketaankoEtaisyysOikein() {
        assertEquals(10, pallo.etaisyys(60, 50));
        assertEquals(10, pallo.etaisyys(40, 50));
        assertEquals(14, pallo.etaisyys(60, 60)); // Oikea tulos olisi toki 14,1421356...
        assertEquals(14, pallo.etaisyys(40, 40));
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
        assertEquals(50, pallo.getX(), pallo.getY());
        pallo.setVx(3);
        pallo.setVy(3);
        assertEquals(50, pallo.getX(), pallo.getY());
        pallo.liikuta();
        assertEquals(53, pallo.getX(), pallo.getY());
    }

    @Test
    public void liikuttuukoNegatiivisesti() {
        pallo.liikuta();
        assertEquals(50, pallo.getX(), pallo.getY());
        pallo.setVx(-3);
        pallo.setVy(-3);
        assertEquals(50, pallo.getX(), pallo.getY());
        pallo.liikuta();
        assertEquals(47, pallo.getX(), pallo.getY());
    }

    @Test
    public void jarruTesti() {
        pallo.setVx(3);
        pallo.setVy(3);
        assertEquals(3, pallo.getVx(), pallo.getVy());
        pallo.jarruta(2, 1);
        assertEquals(1, pallo.getVx());
        assertEquals(2, pallo.getVy());
        pallo.jarruta(0, 4);
        assertEquals(0, pallo.getVy());
    }

    @Test
    public void jarruTestiNegatiiviselleNopeudelle() {
        pallo.setVx(-3);
        pallo.setVy(-3);
        assertEquals(-3, pallo.getVx(), pallo.getVy());
        pallo.jarruta(2, 1);
        assertEquals(-1, pallo.getVx());
        assertEquals(-2, pallo.getVy());
        pallo.jarruta(0, 4);
        assertEquals(0, pallo.getVy());
    }
}

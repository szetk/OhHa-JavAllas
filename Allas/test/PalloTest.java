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
        Pallo boltsi = new Pallo(0, 0, 2);
        assertEquals(2, boltsi.getN());
        assertEquals(0, boltsi.getX(), boltsi.getY());
    }

    @Test
    public void pussittuukoPallo() {
        Pallo boltsi = new Pallo(0, 0, 2);
        assertEquals(false, boltsi.getPussissa());
        boltsi.pussita();
        assertEquals(true, boltsi.getPussissa());
    }

    @Test
    public void liikuttuukoPallo() {
        Pallo pallo = new Pallo(50, 50, 2);
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
        Pallo pallo = new Pallo(50, 50, 2);
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
        Pallo pallo = new Pallo(50, 50, 2);
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
        Pallo pallo = new Pallo(50, 50, 2);
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

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
        peli = new Peli(1000, 400, 10, 30);
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
    public void regenerointiEstetty() {
        assertFalse(peli.generoiPallot());
    }

    @Test
    public void pallojenJarjestys() {
        for (int i = 0; i < 500; i++) {
            peli = new Peli(1000, 400, 10, 30);
            assertTrue(peli.getPallot().get(5).getN() == 8);
            assertTrue(peli.getPallot().get(0).getN() == 0);
        }
    }

    @Test
    public void kulmapallotOikein() {
        int pallo11 = peli.getPallot().get(11).getN();
        int pallo15 = peli.getPallot().get(15).getN();
        assertTrue((pallo11 > 8 && pallo15 < 8) || (pallo11 < 8 && pallo15 > 8));
    }

    @Test
    public void vaihtaaPallon() {
        int n = peli.getPallot().get(15).getN();
        peli.vaihdaPallo(n);
        assertFalse(peli.getPallot().get(15).getN() == n);
        pallojenJarjestys();
    }
    
    
}

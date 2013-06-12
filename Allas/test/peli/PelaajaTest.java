/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package peli;

import allas.domain.Pelaaja;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Sami
 */
public class PelaajaTest {
    
    Pelaaja pelaaja;
    
    public PelaajaTest() {
        pelaaja = new Pelaaja();
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
    
    @Test
    public void onkoPalloOma(){
        pelaaja.setHasIsotPallot(true);
        for(int i = 0; i < 8; i++){
            assertFalse(pelaaja.onOma(i));
        }
        for(int i = 8; i < 16; i++){
            assertTrue(pelaaja.onOma(i));
        }
        pelaaja.setHasIsotPallot(false);
        for(int i = 0; i <= 8; i++){
            assertTrue(pelaaja.onOma(i));
        }
        for(int i = 9; i < 16; i++){
            assertFalse(pelaaja.onOma(i));
        }
    }
    
    @Test
    public void onkoPalloRyhmaValittu(){
        assertFalse(pelaaja.getPalloRyhmaValittu());
        pelaaja.setHasIsotPallot(false);
        assertTrue(pelaaja.getPalloRyhmaValittu());
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}

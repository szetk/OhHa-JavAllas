package peli;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import allas.peli.Alusta;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Sami
 */
public class PallojenAlustusTest {

    Alusta alusta;

    public PallojenAlustusTest() {
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

    @Test
    public void pallojenJarjestys() {
        for (int i = 0; i < 500; i++) {
            alusta = new Alusta(1000, 400, 10, 30, 30);
            alusta.generoiPallot();
            alusta.asetaPallot(1000 * 6 / 8, 400 / 2);
            assertTrue(alusta.getPallot().get(5).getPallonNumero() == 8);
            assertTrue(alusta.getPallot().get(0).getPallonNumero() == 0);
        }
    }

    @Test
    public void kulmapallotOikein() {
        int pallo11 = alusta.getPallot().get(11).getPallonNumero();
        int pallo15 = alusta.getPallot().get(15).getPallonNumero();
        assertTrue((pallo11 > 8 && pallo15 < 8) || (pallo11 < 8 && pallo15 > 8));
    }

    @Test
    public void vaihtaaPallon() {
        int n = alusta.getPallot().get(15).getPallonNumero();
        alusta.vaihdaPallo(n);
        assertFalse(alusta.getPallot().get(15).getPallonNumero() == n);
        pallojenJarjestys();
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package peli;

import allas.domain.Pelaaja;
import allas.peli.Lyonti;
import javax.swing.JLabel;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Sami
 */
public class LyontiTest {

    Lyonti lyonti;
    Pelaaja pelaaja;
    JLabel tekstikentta;

    public LyontiTest() {
        tekstikentta = new JLabel("");
        pelaaja = new Pelaaja();
        pelaaja.setHasIsotPallot(true);
        lyonti = new Lyonti(new Pelaaja(), false, tekstikentta); // Pelaaja, aloitustilanne, tekstikentta
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
    public void valkoinenPussiinAiheuttaaFaulin() {
        lyonti.add(0);
        assertTrue(lyonti.faul());
    }

    @Test
    public void osumaEnsinVastustajanPalloonAiheuttaaFaulin() {
        lyonti.kivenEnsimmainenOsuma = 4;
        assertTrue(lyonti.faul());
    }

    @Test
    public void tuleeFaulKunKiviEiOsuKohdepalloon() {
        assertTrue(lyonti.faul());
    }

    @Test
    public void tuleeFaulKunEiOsuSeinaanTaiMenePussiin() {
        lyonti.kivenEnsimmainenOsuma = 9;
        assertTrue(lyonti.faul());
    }

    @Test
    public void eiFauliaKunOsuuSeinaan() {
        lyonti.kivenEnsimmainenOsuma = 9;
        lyonti.tormayksetSeinaan = 1;
        assertFalse(lyonti.faul());
    }

    @Test
    public void eiFauliaKunMeneePussiin() {
        lyonti.kivenEnsimmainenOsuma = 9;
        lyonti.add(2);
        assertFalse(lyonti.faul()); // Ei faulia jos menee vastustajan pallo sisään
        lyonti.add(11);
        assertFalse(lyonti.faul());
    }

    @Test
    public void tuleeFaulJosMeneeKiviOmanLisaksi() {
        lyonti.kivenEnsimmainenOsuma = 9;
        lyonti.add(12);
        assertFalse(lyonti.faul());
        lyonti.add(0);
        assertTrue(lyonti.faul());
    }

    @Test
    public void vuoronVaihtoKunValkonenSisaan() {
        lyonti.kivenEnsimmainenOsuma = 11;
        lyonti.add(0);
        assertTrue(lyonti.vuoronVaihto());
    }

    @Test
    public void eiVaihdetaVuoroaKunVainOmiaSisaan() {
        lyonti.kivenEnsimmainenOsuma = 11;
        lyonti.add(11);
        assertFalse(lyonti.vuoronVaihto());
    }
        
    @Test
    public void eiVaihdetaVuoroaTurhaan() {
        lyonti.kivenEnsimmainenOsuma = 11;
        lyonti.add(12);
        assertFalse(lyonti.vuoronVaihto());
    }
}

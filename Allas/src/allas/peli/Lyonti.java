package allas.peli;

import allas.domain.Pelaaja;
import allas.tools.Vektori;
import java.util.ArrayList;
import javax.swing.JLabel;

/**
 * Tämä luokka kuvaa biljardipelin lyöntiä ja tallettaa tietoja pelaajan lyönnin
 * jälkeisistä tapahtumista. Luokka sisältää myös metodit, joiden avulla
 * päätetään onko tapahtunut virhe (eli faul) pelaajan lyönnin seurauksena, ja
 * tuleeko vaihtaa vuoro. Lisäksi luokkaa käytetään tallettamaan pelaajan
 * valitsema lyönnin suunta ja lyöntivoima. Luokkaa hallinnoi Peli-luokan olio.
 *
 * @author Sami
 */
public class Lyonti {

    private Pelaaja pelaaja; // Pelaaja joka suorittaa tämän lyönnin
    public int kivenEnsimmainenOsuma; // Pallon, johon valkoinen pallo osuu ensimmäisenä, numero
    public int tormayksetSeinaan; // Luku seiniin osuneista törmäyksistä.
    public ArrayList<Integer> pudonneetPallot; // Lista lyönnin aikana pudonneista palloista
    private boolean aloitustilanne; // Onko aloitustilanne
    private JLabel tekstikentta; // Käyttöliittymän tekstikenttä, johon tulostetaan
    private double[] lyontivoima; // Selitetään konstruktorissa
    private Vektori lyonninSuunta; // Vektori, jota käytetään lyönnin suunnan asettamiseen

    /**
     * Konstruktori, joka saa Peli-luokan oliolta pelissä ajankohtaisia tietoja,
     * sekä tekstikentän, johon tulostetaan viestit.
     *
     * @param pelaaja Parametrinä saadaan vuorossa oleva pelaaja
     * @param aloitustilanne Totuusarvo, joka kertoo onko pelissä menossa
     * aloitustilanne vai ei
     * @param tekstikentta JLabel-olio, johon tarvittaessa tulostetaan tietoa
     */
    public Lyonti(Pelaaja pelaaja, Boolean aloitustilanne, JLabel tekstikentta) {
        this.pudonneetPallot = new ArrayList<>();
        this.pelaaja = pelaaja;
        this.aloitustilanne = aloitustilanne;
        this.kivenEnsimmainenOsuma = 0;
        this.tormayksetSeinaan = 0;
        this.tekstikentta = tekstikentta;

        double[] d = {0, 0.5, 30}; // Ensimmäinen on lyönnin voima, toinen lyönnin kasvu iteraation aikana(nopeudenvalitsinta varten) ja kolmas maksimivoima
        this.lyontivoima = d;
    }

    /**
     * Tämä metodi lisää parametrinä saatua numeroa vastaavan pallon
     * pudonneetPallot()-listaan.
     *
     * @param pallonNumero Numero, jota vastaava pallo on pudonnut pussiin tämän
     * lyönnin aikana
     */
    public void add(int pallonNumero) {
        this.pudonneetPallot.add(pallonNumero);
    }

    /**
     * Tätä metodia käytetään tarkastamaan onko vuorossa oleva pelaaja tehnyt
     * virheen lyöntinsä aikana. Virhe syntyy mm. kun valkoinen pallo on
     * lyötypussiin, ei ole osuttu lainkaan kohdepalloon, aloituslyönti ei ole
     * aiheuttanut tarpeeksi seinätörmäyksiä, lyöntipallo osuu ensimmäisenä
     * vastustajan palloon tai yksikään pallo ei osu seinään tai mene pussiin.
     * Virheen sattuessa tulostetaan syy, ja palautetaan true.
     *
     * @return Palautetaan true, mikäli on tapahtunut virhe, muutoin false.
     */
    public boolean faul() {
        if (this.pudonneetPallot.contains(0)) {
            this.tekstikentta.setText("Faul! - Löit kiven pussiin");
            return true;
        }
        if (this.kivenEnsimmainenOsuma == 0) {
            this.tekstikentta.setText("Faul! - Ei osunut kohdepalloon");
            return true;
        }
        if (this.aloitustilanne && this.tormayksetSeinaan < 4) {
            this.tekstikentta.setText("Faul! - Ei osunut tarpeeksi seinään");
            return true;
        }

        if (!this.aloitustilanne && !this.pelaaja.onOma(this.kivenEnsimmainenOsuma)) {
            this.tekstikentta.setText("Faul! - Et osunut ensimmäisenä omaan kohdepalloosi");
            return true;
        }

        if (!this.aloitustilanne && this.tormayksetSeinaan == 0 && this.pudonneetPallot.isEmpty()) { // jos ei osu seinään, eikä yksikään pallo mene pussiin, tulee käsipallo
            this.tekstikentta.setText("Faul! - Et osunut seinään tai saanut yhtään palloa pussiin");
            return true;
        }

        return false;
    }

    /**
     * Tämä metodi selvittää onko syytä vaihtaa vuoro vai ei.
     *
     * @return Palautetaan true, mikäli vuoro vaihtuu, muutoin false.
     */
    public boolean vuoronVaihto() {
        if (this.aloitustilanne && pudonneetPallot.isEmpty()) {
            return true;
        }
        if (kivenEnsimmainenOsuma == 0) {
            return false;
        }
        if (this.pudonneetPallot.contains(0)) {
            return true;
        }
        if (vastustajanPalloja()) {
            return true;
        } else if (this.pudonneetPallot.isEmpty()) {
            return true;
        }

        return false;
    }

    /**
     * Tämä metodi tarkastaa onko vuorossa oleva pelaaja lyönyt vastustajan
     * pallon pussiin.
     *
     * @return Palautetaan true, mikäli pelaaja on lyönyt vastustajan pallon pussiin.
     */
    public Boolean vastustajanPalloja() {
        for (int pallonNumero : this.pudonneetPallot) {
            if (!this.pelaaja.onOma(pallonNumero)) {
                return true;
            }
        }
        return false;
    }

    public void setLyonninSuunta(Vektori lyonninSuunta) {
        this.lyonninSuunta = lyonninSuunta;
    }

    public double[] getLyontivoima() {
        return this.lyontivoima;
    }

    public Vektori getLyonninSuunta() {
        return this.lyonninSuunta;
    }
}

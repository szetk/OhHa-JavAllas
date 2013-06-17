package allas.peli;

import allas.domain.Pallo;
import allas.domain.Pelaaja;
import allas.gui.Poyta;
import allas.tools.Vektori;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Scanner;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * Tämä luokka halinnoi biljardipelin muita luokkia ja koko pelin toimintaa ja
 * kulkua. Peli on vastuussa pelin pyörittämisestä, sen säännöistä,
 * vuoronjaosta, ajastamisesta ja käyttäjäsyötteiden hallinnasta. Peli on
 * suorassa yhteydessä luokkiin Pelaaja, Lyonti ja Alusta, mutta ei Pallo-luokan
 * olioihin.
 *
 * @author Sami
 */
public class Peli extends Timer {
    /**
     * Alusta, joka sisältää fysiikan pallot yms.
     */
    private Alusta alusta;
    /**
     * Biljardipelipöydän leveys.
     */
    private int leveys; 
    /**
     * Pöydän pituus.
     */
    private int pituus;
    /**
     * Pelin seinien (vallien) paksuus.
     */
    private int seina; 
    /**
     * Pallon säde.
     */
    private int pallonSade; 
    /**
     * Pelin kitka.
     */
    private double kitka; 
    /**
     * Pelitilanne, kertoo mikä tilanne pelissä on menossa. Pelitilanteet: pyörii(0), aseta valkoinen pallo(1), aseta suuntavektori(2), aseta nopeus(3), kasipallo lyöty sisään (4).
     */
    private int pelitilanne; 
    /**
     * Graafinen piirtoalusta, pöytä, joka päivitetään kun on tapahtunut muutos.
     */
    private Poyta poyta;
    /**
     * Pelaaja, joka on tällä hetkellä vuorossa.
     */
    private Pelaaja vuorossaOlevaPelaaja; 
    /**
     * Se pelaaja, joka ei ole tällä hetkellä vuorossa.
     */
    private Pelaaja vastustaja; 
    /**
     * Käytetään kontrolloimaan käyttäjän syötteitä, jotka hallinnoi Nappaimistonkuuntelija.
     */
    public boolean odottaaVastausta; 
    /**
     * Kertoo onko menossa aloitustilanne(true), jossa on erikoissääntöjä voimassa.
     */
    private boolean aloitustilanne; 
    /**
     * Hiiren osoittimen x-koordinaatti, joka päivitetään Hiirenkuuntelijan kautta aina muuttuessa.
     */
    public int hiirenX;
     /**
     * Hiiren osoittimen y-koordinaatti, joka päivitetään Hiirenkuuntelijan kautta aina muuttuessa.
     */
    public int hiirenY;
    /**
     * Käyttöliittymän tekstikenttä, johon tulostetaan (Huomaa metodi tulosta())
     */
    public JLabel tekstikentta;
    /**
     * Sisältää tiedot aina viimeisimmästä lyönnistä.
     */
    private Lyonti lyonti; 

    /**
     * Konstruktori, joka kerää tarvittavia parametrejä Alusta-tyyppsiestä
     * oliosta.
     *
     * @param alusta Parametrinä saadaan alusta parametrejä varten.
     */
    public Peli(Alusta alusta) {
        super(100, null);
        this.alusta = alusta;
        this.leveys = alusta.getPoydanLeveys();
        this.pituus = alusta.getPoydanPituus();
        this.seina = alusta.getSeinanPaksuus();
        this.pallonSade = alusta.getPallonSade();
        this.kitka = alusta.getKitka();


        this.aloitustilanne = true;
        this.odottaaVastausta = false;
        this.pelitilanne = 1;
        this.lyonti = null;
        this.vuorossaOlevaPelaaja = new Pelaaja();
        this.vastustaja = new Pelaaja();
        this.vuorossaOlevaPelaaja.setNimi("ykkönen");
        this.vastustaja.setNimi("kakkonen");

    }

    /**
     * Tämä metodi käynnistää ohjelman ja hallinnoi pelitilanteita. Tätä metodia
     * kutsutaan main()-metodista, ja tämän päättyessä peli päättyy.
     *
     */
    public void aja() {
        this.alusta.generoiPallot();
        this.alusta.asetaPallot(this.pituus * 6 / 8, this.leveys / 2);
        this.lyonti = new Lyonti(this.vuorossaOlevaPelaaja, this.aloitustilanne, this.tekstikentta);

        while (true) {


            if (this.pelitilanne == 4) { // Pelitilanne 4 lopettaa pelin.
                return;
            }
            nuku(1000 / 60);
            if (this.alusta.pallotPussissa(this.vuorossaOlevaPelaaja) && this.vuorossaOlevaPelaaja.getMaalattuPussi() == 0) {
                tulosta("Maalaa pussi painamalla numeroa 1-6");
            }
            if (this.pelitilanne == 0) {

                pyorita();
                tarkastaLyonti();
            }
            if (this.alusta.getPallot().get(0).getPussissa()) { // Jos lyöntipallo on pussissa, täytyy se asettaa pöydäle (pelitilanne 1)
                this.pelitilanne = 1;

                /*
                 * Seuraavassa toteutetaan lyöntivoimakkuuden valitseminen.
                 * Pelaajalle piirretään tämän pelitilanteen aikana vaihtuvan
                 * kokoinen palkki, jonka pituus kuvastaa lyönnin voimakkuutta
                 */
            } else if (this.pelitilanne == 3) { // Pelitilanteessa 3 tulee valita nopeuden voimakkuus
                this.lyonti.getLyontivoima()[0] += this.lyonti.getLyontivoima()[1]; // Lyöntivoimaa muutetaan joka iteraation aikana
                if (this.lyonti.getLyontivoima()[0] <= 0 || this.lyonti.getLyontivoima()[0] >= this.lyonti.getLyontivoima()[2]) { // Jos lyöntivoima saavuttaa maksimin tai minimin, vaihdetaan muutoksen suunta
                    this.lyonti.getLyontivoima()[1] *= -1;
                }
            }
            this.poyta.paivita(); //Paivitetaan
        }
    }

    /**
     * Tämä metodi päättää, että mitä tehdään pelaajan suorittaman lyönnin
     * jälkeen. Suuressa merkityksessä on tuorein lyönti pelissä, jota käytetään
     * apuna lyönnin arvioinnissa.
     */
    public void tarkastaLyonti() {
        if (this.alusta.getPallot().get(5).getPussissa()) { // Jos 8-pallo on pussissa, ei tarvitse tarkastaa tilaa
            return;
        }
        // Jos pelissä on tapahtunut faul (virhe), jonka seurauksena vastustaja saa käsipallon, tulee vaihtaa vuoro ja siirtyä pelitilanteeseen 1 (valkoisen pallon asetus)
        if (this.lyonti.faul()) {
            vuoronVaihto();
            // Jos 8-palloa lukuun ottamatta pallot on pussissa, ja pelaaja tekee virhen, hän häviää. 
            if (this.lyonti.pudonneetPallot.isEmpty() && this.alusta.pallotPussissa(this.vuorossaOlevaPelaaja) && this.alusta.pallotPussissa(this.vastustaja)) {
                tulosta("Voittaja on " + getVastustaja().getNimi() + "!"); // Käsitellään tämä poikkeus tässä
                this.pelitilanne = 4;
            } else {
                this.pelitilanne = 1;
            }
            return; // Faulin jälkeen voidaan lopettaa tarkastaminen
            // Jos vuorossa oleva pelaaja ei ole vielä valinnut palloryhmää, ja saa pallon sisään, kehotetaan häntä valitsemaan palloryhmä
        } else if (!this.vuorossaOlevaPelaaja.getPalloRyhmaValittu() && !this.lyonti.pudonneetPallot.isEmpty()) {
            valitsePalloryhma();
        }
        // Vaihdetaan vuoro, mikäli se on tarpeen
        if (this.lyonti.vuoronVaihto()) {
            vuoronVaihto();
        }
        // Tähän päädytään vain jos ei ole tapahtunut virhettä, ja aloitus on ollut onnistunut
        this.aloitustilanne = false;
    }

    /**
     * Tämä metodi liikuttaa palloja, kunnes pallot pysähtyvät tai
     * 8-pallo menee pussiin
     *
     * @see #aikaHyppy()
     */
    public void pyorita() {
        tulosta(""); // Tyhjennetään tekstikenttä
        do {
            nuku(1000 / 60); // Virkistystaajuus
            aikaHyppy(); // Liikutetaan palloja yhden iteraation verran
            this.poyta.paivita(); // Päivitetään piirtoalusta
            if (this.alusta.pallotPaikoillaan()) { // Jos pudonneetPallot eivät enää liiku, voidaan lopettaa liikuttaminen
                this.pelitilanne = 2;
                break;
            }
            // Lopetetaan, jos kasipallo päätyy pussiin
        } while (!this.alusta.getPallot().get(5).getPussissa());
    }

    /**
     * Tämä metodi liikuttaa palloja yhden iteraation verran. Metodi liikuttaa
     * yhtä palloa kerrallaan, hidastaa sen nopeutta kitkan mukaan ja tarkastaa
     * putoamisen ja törmäykset. Mikäli putoaminen tai törmäys tapahtuu, niiden
     * käsittely alkaa täältä.
     *
     * @see #pyorita()
     */
    public void aikaHyppy() {
        // Ei liikuteta pussissa olevia palloja
        for (Pallo pallo : this.alusta.getPallot()) {
            if (pallo.getPussissa()) {
                continue;
            }
            pallo.liikuta();
            pallo.jarruta(this.kitka);

            int pussinNumero = this.alusta.putoaaPussiin(pallo); // putoaaPussiin() palauttaa arvon 0 jos ei putoa pussiin, muutoin pussin numeron.
            if (pussinNumero > 0) {
                tulosta("Pallo " + pallo.getPallonNumero() + " putosi");
                pallo.setPussissa(true);
                this.lyonti.add(pallo.getPallonNumero()); // Talletetaan lyönnin taulukkoon pudonneen pallon numero
                if (pallo.getPallonNumero() == 8) {
                    kasinPussitus(pussinNumero); // Hoidetaan erikoistilanne kun 8-pallo on tippunut
                } else {
                    this.alusta.poistaPelista(pallo); // Poistetaan pallo pelistä

                }
                // Jos pallo ei tipu pussiin, tarkastetaan osuuko se seinään tai johonkin toiseen palloon
            } else {
                osuuko(pallo);
            }
        }

    }

    /**
     * Tämä metodi tarkastaa osuuko pallo seinään tai johonkin palloon.
     * Tarvittaessa kutsutaan törmäyksien laskemisesta vastaavia metodeja, jotka
     * ovat alustassa.
     *
     * @param pallo1 Tarkasteltava pallo
     * @return Palautetaan true, jos pallo osuu johonkin, muutoin false.
     */
    public boolean osuuko(Pallo pallo1) {
        if (this.alusta.osuuSeinaan(pallo1)) {
            this.lyonti.tormayksetSeinaan++; // Pidetään lukua lyönnin aikana seinään törmänneistä palloista
            return true;
        }

        // Ei tarkasteta pallon törmäystä itseensä
        for (Pallo pallo2 : this.alusta.getPallot()) {
            if (pallo1 == pallo2) {
                continue;
            }

            if (this.alusta.osuuPalloon(pallo1, pallo2)) {
                // Otetaan talteen pallo, johon lyöntipallo törmäsi ensimmäisenä
                if (pallo1.getPallonNumero() == 0 && this.lyonti.kivenEnsimmainenOsuma == 0) {
                    this.lyonti.kivenEnsimmainenOsuma = pallo2.getPallonNumero();
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Tämä metodi piirtää pallot ja muut tilpehöörit(lyöntivoimapalkki, lyönnin
     * suuntaviiva) parametrinä saatuun grafiikkaan.
     *
     * @param graphics Parametrinä saadaan grafiikka, johon piirretään.
     */
    public void piirra(Graphics graphics) {
        if (this.aloitustilanne) {
            graphics.setColor(Color.WHITE);
            graphics.drawLine(this.pituus / 4, this.seina, this.pituus / 4, this.seina + this.leveys);
        }

        // Piirretään viiva hiiren osoittimen ja lyöntipallon välille tähtäystä varten sitä tarvittaessa

        if (this.pelitilanne == 2 || this.pelitilanne == 3) {
            graphics.setColor(Color.BLACK);
            graphics.drawLine((int) this.alusta.getPallot().get(0).getX() + this.seina, (int) this.alusta.getPallot().get(0).getY() + this.seina, this.hiirenX, this.hiirenY);
        }

        // Pelaajan valitessa lyöntivoimakkuutta piirretään lyöntivoiman suuuruutta kuvaava palkki, joka kasvaa ja pienenee ajan kulkiessa. 
        if (this.pelitilanne == 3) {
            graphics.drawRect(this.pituus * 2 / 3 - 1, this.leveys + 2 * this.seina, (int) (6 * this.lyonti.getLyontivoima()[2]), 2 * this.pallonSade);
            graphics.setColor(Color.RED);
            graphics.fillRect(this.pituus * 2 / 3, this.leveys + 2 * this.seina + 1, (int) (6 * this.lyonti.getLyontivoima()[0]), 2 * this.pallonSade - 1);
        }

        // Piirretään vuorossa olevan pelaajan tärkeimmät tiedot
        graphics.setColor(Color.BLACK);
        graphics.drawString("Vuorossa", this.pituus - 100, (int) (this.leveys + 2.5 * this.seina));
        graphics.drawString(this.vuorossaOlevaPelaaja.getNimi(), this.pituus - 100, this.leveys + 3 * this.seina);
        if (this.vuorossaOlevaPelaaja.getPalloRyhmaValittu()) {
            if (this.vuorossaOlevaPelaaja.hasIsotPallot()) {
                graphics.drawString("Isot pallot (siniset)", this.pituus - 100, (int) (this.leveys + 3.5 * this.seina));
            } else {
                graphics.drawString("Pienet pallot (vihreät)", this.pituus - 100, (int) (this.leveys + 3.5 * this.seina));
            }
        }
        // Piirretään pallot
        for (Pallo pallo : this.alusta.getPallot()) {
            pallo.piirra(graphics, this.seina);
        }
    }

    /**
     * Tämä metodin tarkoitus on hoitaa tilanne kun 8-pallo on lyöty pussiin.
     * Tehtäviin kuuluu pelin kekeyttäminen ja voittajan selvittäminen ja
     * julkistaminen.
     *
     * @param pussinNumero Numero, joka kertoo mihin pussiin pallo on pudonnut.
     */
    public void kasinPussitus(int pussinNumero) {
        // Jos vuorossa oleva pelaaja häviää
        if (!this.alusta.pallotPussissa(this.vuorossaOlevaPelaaja) || (this.alusta.pallotPussissa(this.vuorossaOlevaPelaaja) && this.vuorossaOlevaPelaaja.getMaalattuPussi() != pussinNumero) || this.lyonti.faul()) {
            tulosta("Voittaja on " + getVastustaja().getNimi() + "!");
        } else { // Muutoin vuorossa oleva pelaaja voittaa
            tulosta("Voittaja on " + this.vuorossaOlevaPelaaja.getNimi() + "!");
        }
        // Asetetaan pelitilanteeksi 4, joka tarkoittaa, että peli on päättynyt ja voittaja julkistettu
        this.pelitilanne = 4;
    }

    /**
     * Tämä metodi nukkuu parametrinä saadun ajan.
     *
     * @param aika Nukuttava aika
     */
    public void nuku(int aika) {
        try {
            Thread.sleep(aika);
        } catch (InterruptedException ex) {
            tulosta("Nukkuminen ei onnistunut.");
        }
    }

    /**
     * Tämä metodi vaihtaa vuoron pelaajalta toiselle.
     */
    public void vuoronVaihto() {
        Pelaaja temp = this.vuorossaOlevaPelaaja;
        this.vuorossaOlevaPelaaja = this.vastustaja;
        this.vastustaja = temp;
    }

    /**
     * Tämä metodi tulostaa rivin joka kehottaa pelaajaa valitsemaan pallo
     * ryhmän, sekä valmistaa ohjelman vastaanottamaan käyttäjän syötteen
     * palloryhmän valitsemiseksi. Tämän jälkeen näppäin i tai p, valitsevat
     * palloryhmän vuorossa olevalle pelaajall. Näppäin i tarkottaa isoja
     * palloja ja p pieniä.
     */
    public void valitsePalloryhma() {
        tulosta("Valitse palloryhmä i/p");
        this.odottaaVastausta = true;
    }

    /**
     * Tämä metodi tarkastaa onko lyöntipallo paikassa, johon sen saa asettaa.
     * Lyöntipalloa ei voi asettaa muiden pallojen päälle.
     *
     * @return
     */
    public boolean valkoinenPalloSallitussaPaikassa() {
        for (Pallo pallo : this.alusta.getPallot()) { // Skipataan lyöntipallo
            if (pallo.getPallonNumero() == 0) {
                continue;
            }
            if (this.alusta.getPallot().get(0).etaisyys(pallo.getX(), pallo.getY()) <= 2 * this.pallonSade) { // Pallon asettaminen kohdepallon päälle tai siihen kiinni on kiellettyä
                tulosta("Et voi asettaa lyöntipalloa jonkin muun pallon päälle");
                return false;
            }
        }
        return true;
    }

    /**
     * Tämä metodi asettaa lyöntipallon kentälle siihen missä hiiren osoitin on.
     * Lyöntipallo pysyy kuitenkin kentällä, vaikka hiiren osoitin menisi kentän
     * ulkopuolelle. Myös aloitustilanteen rajoitettu sijoituspaikka on
     * huomioitu. Metodia käytetään pelitilanteessa 1.
     */
    public void valkoisenPallonAsetus() {
        if (this.aloitustilanne && this.hiirenX > this.pituus / 4) {
            this.alusta.getPallot().get(0).setX(this.pituus / 4 - this.seina);
        } else if (this.hiirenX < this.seina + this.pallonSade) {
            this.alusta.getPallot().get(0).setX(this.pallonSade);
        } else if (this.hiirenX > this.pituus + this.seina - this.pallonSade) {
            this.alusta.getPallot().get(0).setX(this.pituus - this.pallonSade);
        } else {
            this.alusta.getPallot().get(0).setX(this.hiirenX - this.seina);
        }

        if (this.hiirenY < this.seina + this.pallonSade) {
            this.alusta.getPallot().get(0).setY(this.pallonSade);
        } else if (this.hiirenY > this.leveys + this.seina - this.pallonSade) {
            this.alusta.getPallot().get(0).setY(this.leveys - this.pallonSade);
        } else {
            this.alusta.getPallot().get(0).setY(this.hiirenY - this.seina);
        }

    }

    /**
     * Asettaa lyöntipallon lyönnille suunnan, joka on lyöntipallon ja
     * hiirenpaikan väliin piirretyn viivan suuntainen yksikkövektori. Tätä
     * yksikkövektoria käytetään myöhemmin lyömiseen. Sääntö, joka kieltää
     * alkutilanteessa lyönnin taaksepäin on huomioitu.
     */
    public void asetaLyonninSuunta() {
        this.lyonti = new Lyonti(this.vuorossaOlevaPelaaja, this.aloitustilanne, this.tekstikentta);

        Vektori hiirenPaikkavektori = new Vektori(this.hiirenX - this.seina, this.hiirenY - this.seina);

        // Aloitustilanteesta ei saa lyödä taaksepäin
        if (aloitustilanne && hiirenPaikkavektori.getX() < this.alusta.getPallot().get(0).getX()) {
            tulosta("Et voi lyödä aloitustilanteesta taaksepäin!");
            return;
        }
        // Otetaan hiirenPaikkavektori lyöntipallon suhteen, ja tehdään siitä sen suuntainen yksikkövektori. Myöhemmin tätä käytetään pallon lyömiseen.
        Vektori lyonninSuunta = hiirenPaikkavektori.erotus(this.alusta.getPallot().get(0).getPaikkavektori());
        this.lyonti.setLyonninSuunta(lyonninSuunta.normalisoi());
        this.pelitilanne = 3;


    }

    /**
     * Asettaa lyönnin nopeuden ja suorittaa lyönnin. Lyönnin nopeus määräytyy
     * lyöntivoimapalkin koon mukaan hiirtä klikattaessa.
     */
    public void asetaLyonninNopeusJaLyo() {
        // Aiemmin lyöntisuunta on valittu, ja nyt lyönnin suunta voidaan kertoa valitulla lyöntivoimalla. Nimi lyontiB valittu, jotta ei sekoiteta Lyonti-tyyppiseksi lyonti-olioksi.
        Vektori lyontiB = this.lyonti.getLyonninSuunta().tulo(this.lyonti.getLyontivoima()[0]);

        // Asetetaan lyöntipallolle sen nopeus
        this.alusta.getPallot().get(0).setVx(lyontiB.getX());
        this.alusta.getPallot().get(0).setVy(lyontiB.getY());

//        this.lyonti.getLyontivoima()[0] = 0; // Asetetaan lyöntivoima takaisin nollaksi
//        if (this.lyonti.getLyontivoima()[1] < 0) { // Vaihdetaan lyöntivoiman muutoksen suunta positiiviseksi, jotta lyöntivoiman suurus alkaa kasvaa minimistä kohti maksimia kun pelaaja aloittaa lyöntivoiman valitsemisen
//            this.lyonti.getLyontivoima()[1] *= -1;
//        }
        this.pelitilanne = 0; // Siirrytään pelitilanteeseen, jossa pallot alkavat liikkua

    }

    public void tulosta(String tuloste) {
        this.tekstikentta.setText(tuloste);
        this.poyta.paivita();
    }

    public void setPelitilanne(int pelitilanne) {
        this.pelitilanne = pelitilanne;
    }

    public void setPoyta(Poyta poyta) {
        this.poyta = poyta;
    }

    public void setHiirenPaikka(int x, int y) {
        this.hiirenX = x;
        this.hiirenY = y;
    }

    public void setTekstikentta(JLabel tekstikentta) {
        this.tekstikentta = tekstikentta;
    }

    public Poyta getpoyta(Poyta poyta) {
        return this.poyta;
    }

    public Pelaaja getVuorossaOlevaPelaaja() {
//        if (vuorossaOlevaPelaaja.getVuorossa()) {
//            return vuorossaOlevaPelaaja;
//        }
//        return vastustaja;
        return this.vuorossaOlevaPelaaja;
    }

    public Pelaaja getVastustaja() {
//        if (vuorossaOlevaPelaaja.getVuorossa()) {
//            return vastustaja;
//        }
//        return vuorossaOlevaPelaaja;
        return this.vastustaja;
    }

    public int getPelitilanne() {
        return this.pelitilanne;
    }

    public Alusta getAlusta() {
        return this.alusta;
    }
}

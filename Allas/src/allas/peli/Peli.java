package allas.peli;

import allas.domain.Pallo;
import allas.domain.Pelaaja;
import allas.gui.Paivitettava;
import allas.tools.Vektori;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Peli extends Timer {

    private Alusta alusta; // Alusta, sisältää fysiikan, pallot yms.
    private int leveys; // Pöydän koko
    private int pituus;
    private int seina; // Seinän paksuus
    private int pallonSade; // Pallon säde
    private double kitka; // Kitkakerroin
    private int pelitilanne; // Pyörii(0), aseta valkoinen pallo(1), aseta suuntavektori(2), aseta nopeus(3)
    private Paivitettava paivitettava;
    private Pelaaja vuorossaOlevaPelaaja; // Tällä hetkellä vuorossa oleva pelaaja, päivitetään
    private Pelaaja vastustaja; // Pelaaja, joka ei ole tällä hetkellä vuorossa
    private double[] lyontivoima; // Ensimmäinen alkio on lyönnin voima, toinen lyönnin kasvu iteraation aikana ja kolmas maksimivoima
    public boolean odottaaVastausta; // Käytetään kontrolloimaan käyttäjän syötteitä, jotka hallinnoi Nappaimistonkuuntelija
    private boolean aloitustilanne; // Kertoo onko menossa aloitustilanne, jossa on erikoissääntöjä voimassa
    public int hiirenX;        // Hiiren lokaatio, joka päivitetään Hiirenkuuntelijan kautta aina muuttuessa
    public int hiirenY;
    public JLabel tekstikentta; // Käyttöliittymän tekstikenttä, johon tulostetaan (Huomaa metodi sout())
    private Vektori lyonninSuunta; // Vektori, jota käytetään lyönnin suunnan asettamiseen
    private Lyonti lyonti; // Sisältää tiedot aina viimeisimmästä lyönnistä
    private Scanner lukija;

    public Peli(Alusta alusta) {
        super(100, null);
        this.alusta = alusta;
        this.leveys = alusta.getLeveys();
        this.pituus = alusta.getPituus();
        this.seina = alusta.getSeina();
        this.pallonSade = alusta.getPallonSade();
        this.kitka = alusta.getKitka();

        double[] d = {0, 0.2, 20}; // Ensimmäinen on lyönnin voima, toinen lyönnin kasvu iteraation aikana ja kolmas maksimivoima
        this.lyontivoima = d;
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
     * Tämä metodi aloittaa uuden pelin, mikäli se onnistuu.
     */
    public void aloitaAlusta() {
        this.aloitustilanne = true;
        this.odottaaVastausta = false;
        this.pelitilanne = 1;
        this.lyonti = null;
        this.lukija = new Scanner(System.in);
        this.vuorossaOlevaPelaaja = new Pelaaja();
        this.vastustaja = new Pelaaja();
        aja();
    }

    /**
     * Tämä metodi käynnistää ohjelman.
     *
     */
    public void aja() {
        this.alusta.generoiPallot();
        this.alusta.asetaPallot(this.pituus * 6 / 8, this.leveys / 2);

        while (true) {
            if (this.pelitilanne == 4) { // Pelitilanne 4 lopettaa pelin.
                return;
            }
            nuku(10);
            if (this.alusta.pallotPussissa(this.vuorossaOlevaPelaaja) && this.vuorossaOlevaPelaaja.getMaalattuPussi() == 0) {
                sout("Maalaa pussi painamalla numeroa 1-6");
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
                this.lyontivoima[0] += this.lyontivoima[1]; // Lyöntivoimaa muutetaan joka iteraation aikana
                if (this.lyontivoima[0] <= 0 || this.lyontivoima[0] >= this.lyontivoima[2]) { // Jos lyöntivoima saavuttaa maksimin tai minimin, vaihdetaan muutoksen suunta
                    this.lyontivoima[1] *= -1;
                }
            }
            this.paivitettava.paivita(); //Paivitetaan
        }
    }

    /**
     * Tämä metodi päättää, että mitä tehdään pelaajan suorittaman lyönnin
     * jälkeen.
     */
    public void tarkastaLyonti() {
        if (this.alusta.getPallot().get(5).getPussissa()) { // Jos 8-pallo on pussissa, ei tarvitse tarkastaa tilaa
            return;
        }
        // Jos pelissä on tapahtunut faul (virhe), jonka seurauksena vastustaja saa käsipallon, tulee vaihtaa vuoro ja siirtyä pelitilanteeseen 1 (valkoisen pallon asetus)
        if (this.lyonti.faul()) {
            vuoronVaihto();
            this.pelitilanne = 1;
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
     * Tämä metodi liikuttaa palloja, kunnes pudonneetPallot pysähtyvät tai
     * 8-pallo menee pussiin
     *
     * @see #aikaHyppy()
     */
    public void pyorita() {
        sout(""); // Tyhjennetään tekstikenttä
        this.lyonti = new Lyonti(this.vuorossaOlevaPelaaja, this.aloitustilanne, this.tekstikentta);
        do {
            nuku(10); // Virkistystaajuus
            aikaHyppy(); // Liikutetaan palloja yhden iteraation verran
            this.paivitettava.paivita(); // Päivitetään piirtoalusta
            if (this.alusta.pallotPaikoillaan()) { // Jos pudonneetPallot eivät enää liiku, voidaan lopettaa liikuttaminen
                this.pelitilanne = 2;
                break;
            }
            // Lopetetaan, jos kasipallo päätyy pussiin
        } while (!this.alusta.getPallot().get(5).getPussissa());
    }

    /**
     * Tämä metodi liikuttaa palloja yhden iteraation verran.
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
                sout("Pallo " + pallo.getPallonNumero() + " putosi");
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
     * Tarvittaessa kutsutaan törmäyksien laskemisesta vastaavia metodeja.
     *
     * @param pallo1 Tarkasteltava pallo
     * @return
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
     * Tämä metodi piirtää pudonneetPallot ja muut tilpehöörit parametrinä
     * saatuun grafiikkaan.
     *
     * @param g
     */
    public void piirra(Graphics g) {
        g.setColor(Color.WHITE);
        if (this.aloitustilanne) { // Aloitustilanteessa piirretään viiva, jonka takaa on lyötävä (eteenpäin)
            g.drawLine(this.pituus / 4, this.seina, this.pituus / 4, this.seina + this.leveys);
        }
        g.setColor(Color.BLACK); // Piirretään viiva hiiren osoittimen ja lyöntipallon välille tähtäystä varten sitä tarvittaessa
        if (this.pelitilanne == 2 || this.pelitilanne == 3) {
            g.drawLine((int) this.alusta.getPallot().get(0).getX() + this.seina, (int) this.alusta.getPallot().get(0).getY() + this.seina, this.hiirenX, this.hiirenY);
        }

        if (this.pelitilanne == 3) { // Pelaajan valitessa lyöntivoimakkuutta piirretään lyöntivoiman suuuruutta kuvaava palkki, joka kasvaa ja pienenee ajan kulkiessa. 
            g.drawRect(this.pituus * 2 / 3 - 1, this.leveys + 2 * this.seina, (int) (10 * this.lyontivoima[2]), 2 * this.pallonSade);
            g.setColor(Color.RED);
            g.fillRect(this.pituus * 2 / 3, this.leveys + 2 * this.seina + 1, (int) (10 * this.lyontivoima[0]), 2 * this.pallonSade - 1);
        }

        g.setColor(Color.BLACK); // Piirretään vuorossa olevan pelaajan tärkeimmät tiedot
        g.drawString("Vuorossa", this.pituus - 100, (int) (this.leveys + 2.5 * this.seina));
        g.drawString(this.vuorossaOlevaPelaaja.getNimi(), this.pituus - 100, this.leveys + 3 * this.seina);
        if (this.vuorossaOlevaPelaaja.getPalloRyhmaValittu()) {
            if (this.vuorossaOlevaPelaaja.hasIsotPallot()) {
                g.drawString("Isot pallot (siniset)", this.pituus - 100, (int) (this.leveys + 3.5 * this.seina));
            } else {
                g.drawString("Pienet pallot (vihreät)", this.pituus - 100, (int) (this.leveys + 3.5 * this.seina));
            }
        }

        // Piirretään pudonneetPallot
        for (Pallo pallo : this.alusta.getPallot()) {
            pallo.piirra(g, this.seina);
        }
    }

    /**
     * Tämä metodin tarkoitus on hoitaa tilanne kun 8-pallo on lyöty pussiin.
     * Tehtäviin kuuluu pelin kekeyttäminen ja voittajan julkistaminen.
     *
     * @param pussinNumero
     */
    public void kasinPussitus(int pussinNumero) {
        // Jos vuorossa oleva pelaaja häviää
        if (!this.alusta.pallotPussissa(this.vuorossaOlevaPelaaja) || (this.alusta.pallotPussissa(this.vuorossaOlevaPelaaja) && this.vuorossaOlevaPelaaja.getMaalattuPussi() != pussinNumero) || this.lyonti.faul()) {
            sout("Voittaja on " + getVastustaja().getNimi() + "!");
        } else { // Muutoin vuorossa oleva pelaaja voittaa
            sout("Voittaja on " + this.vuorossaOlevaPelaaja.getNimi() + "!");
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
            sout("Nukkuminen ei onnistunut.");
        }
    }

    /**
     * Tämä metodi vaihtaa vuoron.
     */
    public void vuoronVaihto() {
        Pelaaja temp = this.vuorossaOlevaPelaaja;
        this.vuorossaOlevaPelaaja = this.vastustaja;
        this.vastustaja = temp;
    }

    public void valitsePalloryhma() {
        sout("Valitse palloryhmä i/p");
        this.odottaaVastausta = true;
    }

    /**
     * Tämä metodi tarkastaa onko lyöntipallo paikassa, johon sen saa asettaa.
     *
     * @return
     */
    public boolean valkoinenPalloSallitussaPaikassa() {
        for (Pallo pallo : this.alusta.getPallot()) { // Skipataan lyöntipallo
            if (pallo.getPallonNumero() == 0) {
                continue;
            }
            if (this.alusta.getPallot().get(0).etaisyys(pallo.getX(), pallo.getY()) <= 2 * this.pallonSade) { // Pallon asettaminen kohdepallon päälle tai siihen kiinni on kiellettyä
                sout("Et voi asettaa lyöntipalloa jonkin muun pallon päälle");
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
        Vektori hiirenPaikkavektori = new Vektori(this.hiirenX - this.seina, this.hiirenY - this.seina);

        // Aloitustilanteesta ei saa lyödä taaksepäin
        if (aloitustilanne && hiirenPaikkavektori.getX() < this.alusta.getPallot().get(0).getX()) {
            sout("Et voi lyödä aloitustilanteesta taaksepäin!");
            return;
        }
        // Otetaan hiirenPaikkavektori lyöntipallon suhteen, ja tehdään siitä sen suuntainen yksikkövektori. Myöhemmin tätä käytetään pallon lyömiseen.
        this.lyonninSuunta = hiirenPaikkavektori.erotus(this.alusta.getPallot().get(0).getPaikkavektori());
        this.lyonninSuunta = this.lyonninSuunta.normalisoi();
        this.pelitilanne = 3;


    }

    /**
     * Asettaa lyönnin nopeuden ja suorittaa lyönnin.
     */
    public void asetaLyonninNopeusJaLyo() {
        // Aiemmin lyöntisuunta on valittu, ja nyt lyönnin suunta voidaan kertoa valitulla lyöntivoimalla
        Vektori lyontiB = this.lyonninSuunta.tulo(this.lyontivoima[0]);

        // Asetetaan lyöntipallolle sen nopeus
        this.alusta.getPallot().get(0).setVx(lyontiB.getX());
        this.alusta.getPallot().get(0).setVy(lyontiB.getY());

        this.lyontivoima[0] = 0; // Asetetaan lyöntivoima takaisin nollaksi
        if (lyontivoima[1] < 0) { // Vaihdetaan lyöntivoiman muutoksen suunta positiiviseksi, jotta lyöntivoiman suurus alkaa kasvaa minimistä kohti maksimia kun pelaaja aloittaa lyöntivoiman valitsemisen
            lyontivoima[1] *= -1;
        }
        this.pelitilanne = 0; // Siirrytään pelitilanteeseen, jossa pudonneetPallot alkavat liikkua

    }

    public void sout(String tuloste) {
        this.tekstikentta.setText(tuloste);
        this.paivitettava.paivita();
    }

    public void setPelitilanne(int pelitilanne) {
        this.pelitilanne = pelitilanne;
    }

    public void setPaivitettava(Paivitettava paivitettava) {
        this.paivitettava = paivitettava;
    }

    public void setHiirenPaikka(int x, int y) {
        this.hiirenX = x;
        this.hiirenY = y;
    }

    public void setTekstikentta(JLabel tekstikentta) {
        this.tekstikentta = tekstikentta;
    }

    public Paivitettava getPaivitettava(Paivitettava paivitettava) {
        return this.paivitettava;
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

/*
 * häviää jos kasi sisään ja on palloja ja vuoro häviää jos maalattu väärin (tai
 * ei maalattu) alotuksessa valkosta ei saa lyödä taakse jos alotuksessa menee
 * valkonen pussiin, koitetaan uusiks
 *
 * pussittamista
 *
 * käsipallo: alotuslyönnistä alle neljä koskettaa seinään
 *
 * valkonen ei törmää ekana omaan palloon mikään ei törmää valliin tai mene
 * pussiin valkonen menee pussiin jos vain kasi jäljellä, virheen tehnyt häviää
 *
 *
 * pelitilanteet: aloitustilanne lyö palloa aseta suuntavektori (jos alotus Vx <
 * 0 kielletty) aseta nopeus (aina > 0) pudonneetPallot pyörii kasi pussissa
 * faul - virhetilanne, printtaa syy
 *
 *
 * valkoisen eka osuma
 *
 * komentojen antaminen näppiksellä aina: maalaa aloita alusta kysymyksiin
 * vastaaminen
 *
 * 0 - pyörii (jatkuu) 1 - aseta valkoinen pallo 2 - aseta suuntavektori 3 -
 * aseta nopeus ja lyö 4 - kasipallo sisään
 *
 *
 *
 *
 * aloitustilanne 0/1
 *
 *
 */
//
//Metodeille järjestys:
//
//- luokka, konstruktori, uusiks alotus
//- ajamiseen liittyvät
//- säännöt
//- fysiikka
//- setterit
//- getterit
//
//
//Käytetäänkö kaikkia metodeja? 
//
//
//Voisko vuoron vaihdon toteuttaa järkevämmin
//- vuorossa oleva pelaaja peli-luokkaan?
//
//Nimentä:
//- r = pallonSade
//- n = pallonNumero
//- pussinSade = pussinSade
//
//Jos toteuttais tarkennuksen, ja laittais sille bitin
//- hae piste jossa pallo törmää johonkin
//- piirrä pisteeseen viiva, ja pallo
//- laske lähtökulmat / yksikkövektorit
//
//
//Pallo luokassa ei varmaan tarvii muuttujaa pussissa
//ois vaik metodi:
//
//public boolean getPussissa(){
//if (this.y > this.leveys + 2*seina + this.pallonSade){
//return true;
//}
//return false;
//}
//
// 
//
//Miten erottaa peli, logiikka, säännöt, vuoron jakelu ja fysiikka
//
//
//peli
//- aja
//
//fyssa
//pyorita
//-
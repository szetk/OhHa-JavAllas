/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package allas.peli;

import allas.domain.Pallo;
import allas.domain.Pelaaja;
import allas.tools.Vektori;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Tämä luokka käsittelee biljardipelin tärkeimmät fysikaaliset
 * toiminnallisuudet. Alustan tehtäviin kuuluu mm. pallojen generointi,
 * säilyttäminen ja liiketilojen muutosten laskeminen.
 *
 * @author Sami
 */
public class Alusta {

    /**
     * Lista pelin palloista.
     */
    private ArrayList<Pallo> pallot;
    /**
     * Pöydän leveys.
     */
    private int poydanLeveys;
    /**
     * Pöydän pituus.
     */
    private int poydanPituus;
    /**
     * Pöydän seinän (vallin) paksuus.
     */
    private int seinanPaksuus;
    /**
     * Pallon säde. Kaikilla palloilla on sama säde.
     */
    private int pallonSade;
    /**
     * Pöydän pussien säde.
     */
    private int pussinSade;
    /**
     * Pelin kitka.
     */
    private double kitka;
    /**
     * Radom-generaattori, jota käytetään pallojen järjestyksen satunnaisuuteen.
     */
    private Random arpoja;

    /**
     * Alustan konstruktori. Tätä kutsutaan main-metodista, jossa määritellään
     * pelin tärkeimmät parametrit.
     *
     * @param poydanPituus Pelipöydän pituus
     * @param poydanLeveys Pelipöydän leveys
     * @param seinanPaksuus Pelipöydän seinien (vallien) paksuus
     * @param pallonSade Biljardipallon säde
     * @param pussinSade Kulma- ja sivupussien säde
     */
    public Alusta(int poydanPituus, int poydanLeveys, int seinanPaksuus, int pallonSade, int pussinSade) {
        this.pallot = new ArrayList<>();
        this.poydanLeveys = poydanLeveys;
        this.poydanPituus = poydanPituus;
        this.seinanPaksuus = seinanPaksuus;
        this.pallonSade = pallonSade;
        this.pussinSade = pussinSade;
        this.kitka = 0.04;
        this.arpoja = new Random();
    }

    /**
     * Tämä metodi lisää peliin pallot arvotussa järjestyksessä niin, että
     * 8-pallo on indeksissä 5 ja lyöntipallo indeksissä 0. Lisäksi indekseissä
     * 11 ja 15 olevien pallojen tulee olla eri palloryhmistä. Pallot asetetaan
     * alkuasetelman kärkikolmioon tässä arvotussa järjestyksessä.
     *
     * @return
     */
    public void generoiPallot() {
        this.pallot.add(new Pallo(this.poydanPituus / 8, this.poydanLeveys / 2, 0, this.pallonSade)); // Lyöntipallo eli kivi
        int pallonNro;

        int i = 1;
        while (i < 16) { // Lisätään 15 kohdepalloa peliin
            if (i == 5) {
                this.pallot.add(new Pallo(100, 100, 8, this.pallonSade));
                i++;
                continue;
            }
            pallonNro = arpoja.nextInt(15) + 1;

            if (haePallo(pallonNro) == null && pallonNro != 8) { // Tarkistetaan, ettei arvottu pallo ole 8, tai jo generoitu
                this.pallot.add(new Pallo(100, 100, pallonNro, this.pallonSade));
                i++;
            }
        }
        // Tarkistetaan vielä, että kolmion kulmissa on eli palloryhmiin kuuluvat pallot
        int pallo11 = this.pallot.get(11).getPallonNumero();
        int pallo15 = this.pallot.get(15).getPallonNumero();
        if ((pallo11 < 8 && pallo15 < 8) || (pallo11 > 8 && pallo15 > 8)) { // Vaihdetaan kulma pallo, mikäli kulmapallot ovaa samasta palloryhmästä
            vaihdaPallo(pallo15);
        }
    }

    /**
     * Tarkastaa ovatko kaikki pallot paikallaan.
     *
     * @return Palautetaan true, mikäli kaikki pallot ovat paikoillaan, muutoin
     * false.
     */
    public boolean pallotPaikoillaan() {
        for (Pallo pallo : this.pallot) {
            if (pallo.getPussissa()) { // Jos pallo on pudonnut pussiin, sen liiketilaa ei tarvitse tarkastaa
                continue;
            }
            if (pallo.liikkuuko()) { // Palautetaan false, jos joku palloista liikkuu
                return false;
            }
        }
        return true;
    }

    /**
     * Tarkastaa putoaako parametrinä saatu pallo johonkin pussiin, ja palauttaa
     * pussia vastaavan numeron. Mikäli pallo ei putoa pussiin, palautetaan arvo
     * 0. Pussit ovat numeroitu vasemmalta oikealle ja ylhäältä alas.
     *
     * @param pallo Parametrinä saadaan pallo, jonka putoamista tarkastetaan
     * @return Palautetaan 0, jos pallo ei putoa pussiin, muutoin pussin numero,
     * joka on kokonaisluku välillä 1-6.
     */
    public int putoaaPussiin(Pallo pallo) {
        if (pallo.etaisyys(0, 0) <= this.pussinSade) {
            return 1; // Ylävasen pussi
        }
        if (pallo.etaisyys(poydanPituus / 2, 0) <= this.pussinSade) {
            return 2; // Yläseinän keskimmäinen pussi
        }
        if (pallo.etaisyys(0, poydanLeveys) <= this.pussinSade) {
            return 3; // Yläoikea pussi
        }
        if (pallo.etaisyys(poydanPituus, 0) <= this.pussinSade) {
            return 4; // Alavasen pussi
        }
        if (pallo.etaisyys(poydanPituus / 2, poydanLeveys) <= this.pussinSade) {
            return 5; // Alaseinän keskimmäinen pussi
        }
        if (pallo.etaisyys(poydanPituus, poydanLeveys) <= this.pussinSade) {
            return 6; // Alaoikea pussi
        }
        return 0; // Palautetaan 0, jos pallo ei putoa mihinkään pussiin.
    }

    /**
     * Poistaa pussiin pudonneen pallon pelistä. Tarkalleen ottaen pallo
     * siirretään pöydän ulkopuolelle, jossa pelaajat näkevät pudonneet pallot.
     *
     * @param pallo Parametrinä saadaan pallo, joka poistetaan pelistä.
     */
    public void poistaPelista(Pallo pallo) { // Siirretään pallo pois pelipöydältä "hyllylle"
        pallo.setX(pallo.getPallonNumero() * 2 * this.pallonSade);
        pallo.setY(this.poydanLeveys + 2 * this.seinanPaksuus);
    }

    /**
     * Tämä metodi asettaa palloille koordinaatit. Pallot sisältävä lista on jo
     * valmiiksi järjestetty sopivaan järjestykseen generoiPallot()-metodissa.
     *
     * @see #generoiPallot()
     * @param rivinEkaX Pallokolmion keulassa olevan pallon x-koordinaatti
     * @param rivinEkaY Pallokolmion keulassa olevan pallon y-koordinaatti
     */
    public void asetaPallot(double rivinEkaX, double rivinEkaY) {
        double ekaX = rivinEkaX;
        double ekaY = rivinEkaY;
        int nro = 1;

        for (int i = 1; i < 6; i++) { // Aloituskolmiossa on viisi y-suuntaista pystyriviä, joisat ensimmäisessä on 1 pallo, toisessa 2, kolmannessa 3 jne.
            for (int j = 0; j < i; j++) { // Yhdelle riville asetettavat pallot
                this.pallot.get(nro).setX(ekaX);
                this.pallot.get(nro).setY(ekaY + j * 2 * this.pallonSade); // Rivin seuraava pallo tulee edellisen yläpuolelle y-suunnassa
                nro++;
            }
            // Lasketaan lopuksi seuraavalla rivin y-suunnassa alimman pallon paikka
            ekaX += Math.sqrt(3 * this.pallonSade * this.pallonSade);
            ekaY -= this.pallonSade;
        }
    }

    /**
     * Tämä metodi vaihtaa alkuasetelmassa kulmapallon, mikäli se on tarpeen.
     * Alkuasetelmassa takakulmissa tulee olla eri palloryhmiin kuuluvat pallot.
     *
     * @param pallo15 Parametrinä saadaan kulmapallon numero, jota käytetään
     * metodissa.
     * @see #generoiPallot()
     */
    public void vaihdaPallo(int pallo15) {
        while (true) {
            int nro = arpoja.nextInt(14) + 1; // Arvotaan, että mikä pallo vaihdetaan
            int palloX = this.pallot.get(nro).getPallonNumero();
            if (((palloX < 8 && pallo15 > 8) || (palloX > 8 && pallo15 < 8)) && (palloX != 11)) { // Palloja  8  tai 11 ei saa vaihtaa 
                Collections.swap(this.pallot, nro, 15);
                break;
            }
        }
    }

    /**
     * Tarkastaa osuvatko parametrinä saadut pallot toisiinsa. Mikäli pallot
     * törmäävät, kutsutaan metodia laskeTormaysPalloille()-joka laskee
     * palloille tulevat liiketilat.
     *
     * @param pallo1 Tarkasteltava pallo
     * @param pallo2 Toinen tarkasteltava pallo
     * @return Palautetaan true, mikäli pallot törmäävät, muutoin false.
     * @see #laskeTormaysPalloille(allas.domain.Pallo, allas.domain.Pallo)
     */
    public boolean osuuPalloon(Pallo pallo1, Pallo pallo2) {
        if (pallo1.etaisyys(pallo2.getX(), pallo2.getY()) <= 2 * this.pallonSade) {
            if (pallo1.liikkuuko() || pallo2.liikkuuko()) { // Jos edes toinen pallo liikkuu, prosessoidaan törmäys
                laskeTormaysPalloille(pallo1, pallo2);
                return true;
            }
        }
        return false;
    }

    /**
     * Tarkastaa osuuko parametrinä saatu pallo johonkin pöydän seinistä, sekä
     * laskee tarvittaessa pallolle tulevan liiketilan. Törmäävä pallo
     * siirretään pois seinän sisästä, mikäli näin pääsee käymään. Tällä
     * estetään pallon törmääminen toistumiseen, sekä hyvin pitkälti seinän
     * penetraatio.
     *
     * @param pallo Tarkasteltava pallo
     * @return Palautetaan true, mikäli pallo törmää seinään, muutoin false.
     */
    public boolean osuuSeinaan(Pallo pallo) {
        if (pallo.getX() <= 0 + this.pallonSade) { // Vasen seinä
            pallo.setVx(-1 * pallo.getVx()); // Vaihdetaan nopeuden x-komponentti
            pallo.setX(this.pallonSade); // Siirretään pallo kohtaan, jossa se ei enää törmää seinään (eipähän törmää samalla kertaa useampaa kertaa samaan seinään)
            return true;

        } else if (pallo.getX() >= this.poydanPituus - this.pallonSade) { // Oikea seinä
            pallo.setVx(-1 * pallo.getVx());
            pallo.setX(this.poydanPituus - this.pallonSade);
            return true;

        } else if (pallo.getY() <= 0 + this.pallonSade) { // Yläseinä
            pallo.setVy(-1 * pallo.getVy());
            pallo.setY(this.pallonSade);
            return true;


        } else if (pallo.getY() > this.poydanLeveys - this.pallonSade) { // Alaseinä
            pallo.setVy(-1 * pallo.getVy());
            pallo.setY(this.poydanLeveys - this.pallonSade);
            return true;
        }
        return false;
    }

    /**
     * Laskee törmäyksen parametrinä saaduille, toisiinsa törmääville palloille
     * käyttämällä Vektoreita apunaan. Palloja siirretään toisistaan pois päin
     * sen verran, että ne eivät enää törmää toistamiseen, ja lasketaan uudet
     * nopeudet.
     *
     * @param pallo1 Parametrinä saatu pallo
     * @param pallo2 Parametrinä saatu toinen pallo
     */
    public void laskeTormaysPalloille(Pallo pallo1, Pallo pallo2) {
        Vektori delta = (pallo1.getPaikkavektori().erotus(pallo2.getPaikkavektori()));// Pallojen välinen etaisyysvektori 
        Vektori siirtoMatka = delta.tulo(((2 * this.pallonSade) - delta.pituus()) / delta.pituus()); //Lyhin matka, joka palloja tulee siirtää(ettei enää törmää uudestaan)

        pallo1.setPaikkavektori(pallo1.getPaikkavektori().summa(siirtoMatka.tulo(1 / 2)));// Lasketaan uudet etäisyydet, huom! palloilla on sama massa
        pallo2.setPaikkavektori(pallo2.getPaikkavektori().erotus(siirtoMatka.tulo(1 / 2)));

        Vektori deltaNopeus = (pallo1.getNopeusvektori().erotus(pallo2.getNopeusvektori()));// Nopeuksien erotus
        double impulssi = deltaNopeus.pistetulo(siirtoMatka.normalisoi());

        if (impulssi > 0) {// Pallot ovat liikkeellä pois toisistaan, joten voidaan poistua
            return;
        }

        Vektori impulse = siirtoMatka.tulo(-impulssi);

        pallo1.setNopeusvektori(pallo1.getNopeusvektori().summa(impulse.tulo(1))); // Päivitetään nopeudet
        pallo2.setNopeusvektori(pallo2.getNopeusvektori().erotus(impulse.tulo(1)));
    }

    /**
     * Hakee pallot sisältävästä listasta pallon, jonka numero on sama kuin
     * parametrinä saatu numero.
     *
     * @param EtsittavanPallonNumero Parametrinä saadaan numero, jota vastaava
     * pallo haetaan
     * @return Palautetaan pallo, jonka numero vastaa parametrinä saatua numeroa
     */
    public Pallo haePallo(int EtsittavanPallonNumero) {
        for (Pallo pallo : this.pallot) {
            if (pallo.getPallonNumero() == EtsittavanPallonNumero) {
                return pallo;
            }
        }
        return null;
    }

    /**
     * Tarkastaa onko pelaajan kaikki pallot 8-palloa lukuunottamatta pussissa.
     *
     * @param pelaaja Parametrinä saadaan pelaaja, jonka palloja tarkastellaan
     * @return Palautetaan true, mikäli pelaajan pallot 8-palloa lukuunottamatta
     * on pussissa, muutoin false.
     */
    public boolean pallotPussissa(Pelaaja pelaaja) {
        if (pelaaja.hasIsotPallot()) {
            for (int i = 9; i < 16; i++) {
                if (!haePallo(i).getPussissa()) {
                    return false;
                }
            }
        } else {
            for (int i = 1; i < 8; i++) {
                if (!haePallo(i).getPussissa()) {
                    return false;
                }
            }
        }
        return true;
    }

    public ArrayList<Pallo> getPallot() {
        return this.pallot;
    }

    public double getKitka() {
        return this.kitka;
    }

    public int getPoydanPituus() {
        return this.poydanPituus;
    }

    public int getPallonSade() {
        return this.pallonSade;
    }

    public int getPoydanLeveys() {
        return this.poydanLeveys;
    }

    public int getSeinanPaksuus() {
        return this.seinanPaksuus;
    }

    public int getPussinSade() {
        return pussinSade;
    }
}

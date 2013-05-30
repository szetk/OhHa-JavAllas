package allas.peli;

import allas.domain.Pallo;
import allas.gui.Paivitettava;
import allas.tools.Vektori;
import java.awt.Component;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.swing.Timer;

/**
 * Tämä luokka sisältää pelin pallot, fysiikan ja säännöt. Tulevaisuudessa tämä tullaan halkaisemaan muutamaan luokkaan.
 * @author Sami
 */
public class Peli extends Timer{
    private ArrayList<Pallo> pallot;
    private Random arpoja;

    private int leveys; // pöydän koko
    private int pituus;
    private int seina; // seinän paksuus
    private int r; // pallon säde
    private double kitka;
    private boolean putosi;
    private Paivitettava paivitettava;

    public Peli(int pituus, int leveys, int seina, int r) {
        super(100, null);

        this.pallot = new ArrayList<>();
        this.arpoja = new Random();
        this.leveys = leveys;
        this.pituus = pituus;
        this.seina = seina;
        this.r = r;
        this.kitka = 1;
        this.putosi = false;

        generoiPallot();
        asetaPallot(this.pituus *6 / 8, this.leveys / 2);
    }

/**
 * Tämä metodi luo biljardin 8-palloon pallot tulevan asettelun järjestykseen. 
 * 
 * Nollas pallo on valkoinen lyöntipallo. Pallojen järjestys on sattumanvarainen, mutta pallot-listan viides alkio on 8-pallo. 
 * @return Palauttaa totuusarvona true, mikäli generointi onnistuu.
 */
    public boolean generoiPallot() {
        if (!this.pallot.isEmpty()) {
            return false;
        }
        this.pallot.add(new Pallo(this.pituus/8, this.leveys/2, 0, this.r)); // lyöntipallo
        int nro;

        int i = 1;
        while (i < 16) {
            if (i == 5) {
                this.pallot.add(new Pallo(100, 100, 8, this.r));
                i++;
                continue;
            }
            nro = arpoja.nextInt(15) + 1;
//            System.out.println(nro);

            if (haePallo(nro) == null && nro != 8) {
                this.pallot.add(new Pallo(100, 100, nro, this.r));
                i++;
            }
        }
        int pallo11 = this.pallot.get(11).getN();
        int pallo15 = this.pallot.get(15).getN();
        if ((pallo11 < 8 && pallo15 < 8) || (pallo11 > 8 && pallo15 > 8)) {
            vaihdaPallo(pallo15);
        }
        return true;
    }
/**
 * Tämä metodi vaihtaa kulmapallon johonkin toisen palloryhmän (pienet tai suuret, raidattomat tai raidalliset) palloon.
 * @param pallo15 
 */
    public void vaihdaPallo(int pallo15) {
        while (true) {
            int nro = arpoja.nextInt(14) + 1;
            int palloX = this.pallot.get(nro).getN();
            if (((palloX < 8 && pallo15 > 8) || (palloX > 8 && pallo15 < 8)) && (palloX != 11)) { // Palloja 8 tai 11 ei saa vaihtaa
                Collections.swap(this.pallot, nro, 15);
                break;
            }
        }
    }
/**
 * Tämä metodi asettaa pallojen koordinaateiksi alkuasetelman mukaiset koordinaatit. Parametreinä otetaan kärkipallon koordinaatit.
 * @param rivinEkaX Etukärjessä olevan pallon x-koordinaatti.
 * @param rivinEkaY Etukärjessä olevan pallon y-koordinaatti.
 */
    public void asetaPallot(double rivinEkaX, double rivinEkaY) {
        double ekaX = rivinEkaX; 
        double ekaY = rivinEkaY;
        int nro = 1;

        for (int i = 1; i < 6; i++) {
            for (int j = 0; j < i; j++) {
                this.pallot.get(nro).setX(ekaX);
                this.pallot.get(nro).setY(ekaY + j * 2 * this.r);
                nro++;
            }
            ekaX += Math.sqrt(3 * this.r * this.r); 
            ekaY -= this.r;
        }
    }
    /**
     * Tämä metodi ajaa ohjelmaa toistaiseksi.
     */
    public void aja(){
        int i = 1;
        while(true){
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
                System.out.println("Nukkuminen ei onnistunut.");
            }
            this.pallot.get(8).setVx(10);
            aikaHyppy();
//            System.out.println(this.pallot.get(8).getX() + " " + this.pallot.get(8).getY());
            i++;
        }
    }
/**
 * Tämä metodi suorittaa yhden ohjelman iteraation.
 */
    public void aikaHyppy() {
        for (Pallo pallo : this.pallot) {
            if (pallo.getPussissa()) {
                continue;
            }
            // jos this.putosi = false, ei tarvii kattoa joku pallo pussittunut
            pallo.liikuta();
//            pallo.jarruta(this.kitka, this.kitka);
            if (putoaaPussiin(pallo)) {
                pallo.setPussissa(true); // tai ehkä new Boolean(true)
                poistaPelista(pallo);
                this.putosi = true;
            } else {
                osuuko(pallo);
            }
        }
        
    }
/**
 * Tämä metodi tarkistaa osuuko parametrina saatu pallo seinään tai johonkin toiseen palloon.
 * @param pallo1 Tutkittava pallo.
 * @return Palautetaan totuusarvo osumisesta.
 */
    public boolean osuuko(Pallo pallo1) {
        for (Pallo pallo2 : this.pallot) {
            if (osuuPalloon(pallo1, pallo2)) {
                laskeTormaysPalloille(pallo1, pallo2);
                return true;
            }

        }
        if (osuuSeinaan(pallo1)) {
            return true;
        }
        return false;
    }
/**
 * Tämä metodi tarkistaa osuuko parametrina saatu pallo seinään. Tätä metodia käytetään metodissa osuuko().
 * @see osuuko()
 * @param pallo
 * @return 
 */
    public boolean osuuSeinaan(Pallo pallo) {
        // lisää ehtoja pussien lähettyville tulossa
        if (pallo.getX() <= 0 + this.r + this.seina || pallo.getX() >= this.pituus + this.r + this.seina) {
            pallo.setVx(-1 * pallo.getVx());
            return true;
        }
        if (pallo.getY() <= 0 + this.r + this.seina || pallo.getY() >= this.leveys + this.r + this.seina) {
            pallo.setVy(-1 * pallo.getVy());
            return true;
        }
        return false;
    }
/**
 * Tämä metodi tarkistaa törmäävätkö parametreina saadut pallot. Tätä metodia käytetään metodissa osuuko().
 * @see osuuko()
 * @param pallo1
 * @param pallo2
 * @return 
 */
    public boolean osuuPalloon(Pallo pallo1, Pallo pallo2) {
        if (pallo1.etaisyys(pallo2.getX(), pallo2.getY()) <= 2 * this.r) {
            laskeTormaysPalloille(pallo1, pallo2);
            return true;
        }
        return false;
    }
/**
 * Tämä metodi tulee laskemaan kahden pallon liiketilat törmäyksen jälkeen.
 * @param pallo1
 * @param pallo2 
 */
    public void laskeTormaysPalloille(Pallo pallo1, Pallo pallo2) {        
        Vektori paikka1 = new Vektori(pallo1.getX(), pallo1.getY());
        Vektori paikka2 = new Vektori(pallo2.getX(), pallo2.getY());
        
        Vektori vauhti1 = new Vektori(pallo1.getVx(), pallo1.getVy());
        Vektori vauhti2 = new Vektori(pallo2.getVx(), pallo2.getVy());
        
    }
/**
 * Tämä metodi tarlostaa tippuuko pallo pussiin nykyisessä sijainnissaan.
 * @param pallo Parametrina saadaan tutkittava pallo.
 * @return Palautetaan totuusarvo putoamisesta.
 */
    public boolean putoaaPussiin(Pallo pallo) {
        if (pallo.etaisyys(0, 0) <= this.seina * 2) {
            return true;
        }
        if (pallo.etaisyys(0, leveys) <= this.seina * 2) {
            return true;
        }
        if (pallo.etaisyys(pituus, 0) <= this.seina * 2) {
            return true;
        }
        if (pallo.etaisyys(pituus, leveys) <= this.seina * 2) {
            return true;
        }
        if (pallo.etaisyys(pituus / 2, 0) <= this.seina * 2) {
            return true;
        }
        if (pallo.etaisyys(pituus / 2, leveys) <= this.seina * 2) {
            return true;
        }
        return false;
    }
    /**
     * Tämä metodi asettaa pallolle vauhdin parametrien mukaisesti.
     * @param pallo
     * @param vx
     * @param vy 
     */
    public void asetaVauhti(Pallo pallo, double vx, double vy) {
        pallo.setVx(vx);
        pallo.setVy(vy);
    }
/**
 * Tämä pallo poistaa parametrina annetun pallon pelistä. 
 * @see putoaaPussiin()
 * @param pallo 
 */
    public void poistaPelista(Pallo pallo) {
        this.pallot.remove(this.haePallo(pallo.getN()));
    }
/**
 * Tämä metodi palauttaa pallot-listasta sen pallon, jonka numero on parametrina annettu int.
 * @param n Parametrina saadaan int-muuttuja, jota vastaavaa palloa etsitään.
 * @return  Palauttaa löydetyn Pallo-olion.
 */
    public Pallo haePallo(int n) {
        for (Pallo pallo : this.pallot) {
            if (pallo.getN() == n) {
                return pallo;
            }
        }
        return null;
    }

    public ArrayList<Pallo> getPallot() {
        return this.pallot;
    }

    public double getKitka() {
        return this.kitka;
    }

    public boolean getPutosi() {
        return this.putosi;

    }

    public void piirra(Graphics g) {
        for (Pallo pallo : pallot){
            pallo.piirra(g);
        }
    }
    public void setPaivitettava(Paivitettava paivitettava) {
        this.paivitettava = paivitettava;
    }
    public Paivitettava getPaivitettava(Paivitettava paivitettava) {
        return this.paivitettava;
    }

    public int getPituus(){
        return this.pituus;
    }

    public int getLeveys(){
        return this.leveys;
    }

    public int getSeina() {
        return this.seina;
    }
}
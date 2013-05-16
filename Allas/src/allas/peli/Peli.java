/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package allas.peli;

import allas.domain.Pallo;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Sami
 */
public class Peli {

    private ArrayList<Pallo> pallot;
    private int r;
    private Random arpoja;
    private int kitka;
    private int leveys; //pöydän koko
    private int pituus;
    private int seina; // seinän paksuus

    public Peli(int pituus, int leveys, int seina) {
        this.pallot = new ArrayList<>();
        this.leveys = leveys;
        this.pituus = pituus;
        this.seina = seina;
        this.arpoja = new Random();
        this.r = 5;
        this.kitka = 1;
        generoiPallot();
        asetaPallot();
    }

    public ArrayList<Pallo> getPallot() {
        return this.pallot;
    }
    
    public int getKitka(){
        return this.kitka;
    }

    public void generoiPallot() {
        for (int i = 0; i <= 15; i++) {
            Pallo pallo = new Pallo(100, 200, i);
            this.pallot.add(pallo);
        }
    }

    public void asetaPallot() {
        int x = (int) this.pituus / 8;
        int y = (int) this.leveys / 2;

        this.pallot.get(8).setX(6*x);
        this.pallot.get(8).setY(y);

        this.pallot.get(0).setX(2*x);
        this.pallot.get(0).setY(y);

        // Kesken
    }

    public void aikaHyppy() {
        for (int i = 0; i <= 15; i++) {
            Pallo pallo = pallot.get(i);
            if (pallo.getPussissa()){
                continue;
            }
            liikuta(pallo);
            jarruta(pallo, this.kitka, this.kitka);
            if (putoaaPussiin(pallo)) {
                pallo.pussita();
                poistaPelista(pallo);
            } else {
                osuuko(pallo);
            }
        }
    }

    public void liikuta(Pallo pallo) {
        pallo.setX(pallo.getX() + pallo.getVx());
        pallo.setY(pallo.getY() + pallo.getVy());
    }

    public void osuuko(Pallo pallo) {
        for (int i = 0; i <= 15; i++) {
            osuuPalloon(pallo, this.pallot.get(i));
        }
        osuuSeinaan(pallo);

    }

    public void osuuSeinaan(Pallo pallo) {
        // lisää ehtoja pussien lähettyville tulossa
        if (pallo.getX() <= 0 + this.r + this.seina || pallo.getX() >= this.pituus + this.r + this.seina) {
            laskeTormaysSeinaan(pallo);
        }
        if (pallo.getY() <= 0 + this.r + this.seina || pallo.getY() >= this.pituus + this.r + this.seina) {
            laskeTormaysSeinaan(pallo);
        }
    }

    public void osuuPalloon(Pallo pallo1, Pallo pallo2) {
        if (etaisyys(pallo1, pallo2.getX(), pallo2.getY()) <= 2 * r) {
            laskeTormaysPalloille(pallo1, pallo2);
        }
    }

    public void laskeTormaysPalloille(Pallo pallo1, Pallo pallo2) {
        System.out.println("Not supported yet!");
    }

    public void laskeTormaysSeinaan(Pallo pallo) {
        System.out.println("Not supported yet!");
    }

    public boolean putoaaPussiin(Pallo pallo) {
        if (etaisyys(pallo, 0, 0) <= this.seina*2) {
            return true;
        }
        if (etaisyys(pallo, 0, leveys) <= this.seina*2) {
            return true;
        }
        if (etaisyys(pallo, pituus, 0) <= this.seina*2) {
            return true;
        }
        if (etaisyys(pallo, pituus, leveys) <= this.seina*2) {
            return true;
        }
        if (etaisyys(pallo, (int) pituus / 2, 0) <= this.seina*2) {
            return true;
        }
        if (etaisyys(pallo, (int) pituus/2, leveys) <= this.seina*2) {
            return true;
        }
        return false;
    }

    public void jarruta(Pallo pallo, int deltax, int deltay) {
        int vx = pallo.getVx();
        int vy = pallo.getVy();

        if (vx >= deltax) {
            pallo.setVx(vx - deltax);
        } else {
            pallo.setVx(0);
        }

        if (vy >= deltay) {
            pallo.setVy(vy - deltay);
        } else {
            pallo.setVy(0);
        }

    }

    public int etaisyys(Pallo pallo, int x, int y) {
        int deltax = pallo.getX() - x;
        int deltay = pallo.getY() - y;
        deltax *= deltax;
        deltay *= deltay;

        return (int) Math.sqrt(deltax + deltay);
    }

    public void asetaVauhti(Pallo pallo, int vx, int vy) {
        pallo.setVx(vx);
        pallo.setVy(vy);
    }

    public void poistaPelista(Pallo pallo) {
        System.out.println("Not supported yet!");
    }
}

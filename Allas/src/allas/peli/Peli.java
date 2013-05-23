/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package allas.peli;

import allas.domain.Pallo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Sami
 */
public class Peli {

    private ArrayList<Pallo> pallot;
    private double r;
    private Random arpoja;
    private double kitka;
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
        asetaPallot(this.pituus * 6 / 8, this.leveys / 2);
    }

    

    public boolean generoiPallot() {
        if (!this.pallot.isEmpty()){
            return false;
        }
        this.pallot.add(new Pallo(100, 100, 0)); //valkoinen pallo
        int nro;

        int i = 1;
        while (i < 16) {
            if (i == 5){
                this.pallot.add(new Pallo(100, 100, 8));
                i++;
                continue;
            }
            nro = arpoja.nextInt(15) + 1;
//            System.out.println(nro);
            
            if (haePallo(nro) == null && nro != 8) {
                this.pallot.add(new Pallo(100, 100, nro));
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

    public void asetaPallot(double rivinEkaX, double rivinEkaY) {
        double ekaX = rivinEkaX; // Tarviiko?
        double ekaY = rivinEkaY;
        int nro = 1;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j <= i; j++) {
                this.pallot.get(nro).setX(ekaX);
                this.pallot.get(nro).setY(ekaY + j*2*this.r);
                nro++;
            }
            ekaY = Math.sqrt(3*r*r);
            ekaX -= r;
        }
    }

    public void aikaHyppy() {
        for (Pallo pallo : this.pallot) {
            if (pallo.getPussissa()) {
                continue;
            }
            pallo.liikuta();
            pallo.jarruta(this.kitka, this.kitka);
            if (putoaaPussiin(pallo)) {
                pallo.pussita();
                poistaPelista(pallo);
            } else {
                osuuko(pallo);
            }
        }
    }

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

    public boolean osuuPalloon(Pallo pallo1, Pallo pallo2) {
        if (pallo1.etaisyys(pallo2.getX(), pallo2.getY()) <= 2 * r) {
            laskeTormaysPalloille(pallo1, pallo2);
            return true;
        }
        return false;
    }

    public void laskeTormaysPalloille(Pallo pallo1, Pallo pallo2) {
    }

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

    public void asetaVauhti(Pallo pallo, double vx, double vy) {
        pallo.setVx(vx);
        pallo.setVy(vy);
    }

    public void poistaPelista(Pallo pallo) {
        this.pallot.remove(this.haePallo(pallo.getN()));
    }

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
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package allas.peli;

import allas.domain.Pallo;
import allas.domain.Pelaaja;
import java.util.ArrayList;

/**
 *
 * @author Sami
 */
public class Lyonti {

    public int kivenEnsimmainenOsuma;
    public int tormayksetSeinaan;
    public boolean kiviPussiin;
    private ArrayList<Integer> pallot;
    private Pelaaja pelaaja;
    private boolean aloitustilanne;

    public Lyonti(Pelaaja pelaaja, Boolean aloitustilanne) {
        this.pallot = new ArrayList<>();
        this.pelaaja = pelaaja;
        this.aloitustilanne = aloitustilanne;
        this.kivenEnsimmainenOsuma = 0;
        this.tormayksetSeinaan = 0;
        this.kiviPussiin = false;
    }

    public void add(int pallonNumero) {
        this.pallot.add(pallonNumero);
    }

    public boolean faul() {
        if (kivenEnsimmainenOsuma == 0) {
            System.out.println("Ei osunut kohdepalloon");
            return true;
        }
        if (this.aloitustilanne && this.tormayksetSeinaan < 4) {
            System.out.println("Ei osunut tarpeeksi seinään");
            return true;
        }

        if (!this.aloitustilanne && !this.pelaaja.onOma(this.kivenEnsimmainenOsuma)) {
            System.out.println("Osuit ensiksi vastustajan palloon");
            return true;
        }

        if (!this.aloitustilanne && this.tormayksetSeinaan == 0 && this.pallot.isEmpty()) { // jos ei osu seinään, eikä yksikään pallo mene pussiin, tulee käsipallo
            System.out.println("Et osunut seinään tai saanut yhtään palloa pussiin");
            return true;
        }

        if (this.kiviPussiin) {
            System.out.println("Löit kiven pussiin");
            return true;
        }
        return false;
    }

    public boolean vuoronVaihto() {
        if (this.aloitustilanne && pallot.isEmpty()) {
            return true;
        }
        if (kivenEnsimmainenOsuma == 0) {
            return false;
        }
        if (this.kiviPussiin || vastustajanPalloja()) {
            return true;
        } else if (this.pallot.isEmpty()) {
            return true;
        }

        return false;
    }

    public Boolean vastustajanPalloja() {
        for (int pallonNumero : pallot) {
            if (!this.pelaaja.onOma(pallonNumero)) {
                return true;
            }
        }
        return false;
    }
}

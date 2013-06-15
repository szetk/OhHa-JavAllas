package allas.peli;

import allas.domain.Pelaaja;
import allas.tools.Vektori;
import java.util.ArrayList;
import javax.swing.JLabel;

public class Lyonti {

    private Pelaaja pelaaja; // Pelaaja joka suorittaa tämän lyönnin
    public int kivenEnsimmainenOsuma; // Pallon, johon valkoinen pallo osuu ensimmäisenä, numero
    public int tormayksetSeinaan; // Luku seiniin osuneista törmäyksistä.
    public ArrayList<Integer> pudonneetPallot; // Lista lyönnin aikana pudonneista palloista
    private boolean aloitustilanne; // Onko aloitustilanne
    private JLabel tekstikentta; // Käyttöliittymän tekstikenttä, johon tulostetaan
    private double[] lyontivoima; // Selitetään konstruktorissa
     private Vektori lyonninSuunta; // Vektori, jota käytetään lyönnin suunnan asettamiseen

    public Lyonti(Pelaaja pelaaja, Boolean aloitustilanne, JLabel tekstikentta) {
        this.pudonneetPallot = new ArrayList<>();
        this.pelaaja = pelaaja;
        this.aloitustilanne = aloitustilanne;
        this.kivenEnsimmainenOsuma = 0;
        this.tormayksetSeinaan = 0;
        this.tekstikentta = tekstikentta;

        double[] d = {0, 0.2, 20}; // Ensimmäinen on lyönnin voima, toinen lyönnin kasvu iteraation aikana(nopeudenvalitsinta varten) ja kolmas maksimivoima
        this.lyontivoima = d;
    }

    public void add(int pallonNumero) {
        this.pudonneetPallot.add(pallonNumero);
    }

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
    
    public Vektori getLyonninSuunta(){
        return this.lyonninSuunta;
    }
}

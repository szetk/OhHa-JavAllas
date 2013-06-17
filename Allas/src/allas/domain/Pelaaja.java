package allas.domain;

/**
 * Tämä luokka kuvaa pelaajaa bijlardipelissä. Pelaajia on pelissä kaksi, joista
 * toinen on vuorossa ja toinen ei. Pelaajalla on nimi, maalattu pussi, ja
 * mahdollisesti valittuna palloryhmäksi isot tai pienet. Pelaaja-oliota
 * hallitsee luokka Peli.
 *
 * @author Sami
 */
public class Pelaaja {

    /**
     * Pelaajan nimi, lähinnä jatkokehittelyn varalta.
     */
    private String nimi;
    /**
     * Viimeksi maalatun pussin numero (1-6 numeroituna vasemmalta oikealle ja
     * ylhäältä alas).
     */
    private int maalattuPussi;
    /**
     * Kertoo onko tällä pelaajalla isot(true) vai pienet pallot(false).
     */
    private boolean hasIsotPallot;
    /**
     * Tämä on true, mikäli pelissä on valittu palloryhmät.
     */
    private boolean palloRyhmaValittu;

    /**
     * Pelaajan konstruktori. Pelaajalla ei aluksi ole valittua palloryhmää eikä
     * maalattua pussia (palloRyhmaValittu = false, maalattuPussi = 0).
     */
    public Pelaaja() {
        this.palloRyhmaValittu = false;
        this.maalattuPussi = 0;
    }

    /**
     * Tämä metodi tarkastaa kuuluuko pelaajan palloihin pallo, jonka numero on
     * annettu parametrinä.
     *
     * @param pallonNumero Tarkasteltavan pallon numero
     * @return Palautetaan true, jos annettu pallon numero kuuluu pelaajan
     * palloryhmään ja muutoin false.
     */
    public boolean onOma(int pallonNumero) {
        if (!this.palloRyhmaValittu) {
            return true;
        }
        if (this.hasIsotPallot && pallonNumero >= 8) {
            return true;
        }
        if (!this.hasIsotPallot && pallonNumero <= 8) {
            return true;
        }
        return false;
    }

    /**
     * Tämä metodi asettaa pelaajalle palloryhmän. Kun palloryhmä valitaan,
     * asetetaan muuttujan palloRyhmaValittu arvoksi true.
     *
     * @param hasIsotPallot
     */
    public void setHasIsotPallot(Boolean hasIsotPallot) {
        this.hasIsotPallot = hasIsotPallot;
        this.palloRyhmaValittu = true;
    }

    public void setMaalattuPussi(int pussi) {
        this.maalattuPussi = pussi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getNimi() {
        return this.nimi;
    }

    public boolean getPalloRyhmaValittu() {
        return this.palloRyhmaValittu;
    }

    public int getMaalattuPussi() {
        return this.maalattuPussi;
    }

    public boolean hasIsotPallot() {
        return this.hasIsotPallot;
    }
}
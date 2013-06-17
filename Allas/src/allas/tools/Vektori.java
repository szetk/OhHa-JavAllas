package allas.tools;

/**
 * Tämä luokka on vektorityökalu, jota tarvitaan biljardipelissä liiketilojen
 * laskemiseen. Vektorilla on kaksi komponenttia, x ja y. Nämä ovat toisiaan
 * kohtisuorassa. Luokka sisältää keskeiset vektorilaskutoimitusmetodit, sekä
 * pituuden palauttavan metodin.
 *
 * @author Sami
 */
public class Vektori {

    /**
     * Vektorin x-koordinaatti.
     */
    private double x;
    /**
     * Vektorin y-koordinaatti.
     */
    private double y;

    public Vektori(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Laskee kyseisen vektorin ja parametrina saadun vektorin erotuksen, ja
     * palauttaa näin saadun vektorin.
     *
     * @param a Vektori, joka vähennetään tästä vektorista.
     * @return Palautetaan vektoreiden erotuksena saatu vektori.
     */
    public Vektori erotus(Vektori a) {
        Vektori c = new Vektori(this.x - a.getX(), this.y - a.getY());
        return c;
    }

    /**
     * Laskee kahden vektorin pistetulon, ja palauttaa double- tyyppisen
     * skalaariarvon.
     *
     * @param vektori Vektori, jonka kanssa pistetulo lasketaan.
     * @return Palautetaan pistetulon tulos.
     */
    public double pistetulo(Vektori vektori) {
        return this.x * vektori.getX() + this.y * vektori.getY();
    }

    /**
     * Kertoo vektorin kertoimella
     *
     * @param kerroin Kerroin, jolla tämä vektori kerrotaan.
     * @return Palautetaan kerrottu vektori.
     */
    public Vektori tulo(double kerroin) {
        return new Vektori(this.x * kerroin, this.y * kerroin);
    }

    /**
     * Summaa kaksi kyseiseen vektoriin parametrina saadun vektorin, ja
     * palauttaa saadun vektorin.
     *
     * @param a Parametrinä saadaan summattava vektori.
     * @return Palautetaan tuloksena saatu vektori.
     */
    public Vektori summa(Vektori a) {
        Vektori c = new Vektori(this.x + a.getX(), this.y + a.getY());
        return c;
    }

    /**
     * Tämä metodi tekee palauttaa tämän vektorin suuntaisen yksikkövektorin.
     *
     * @return Yksikkövektori, joka on tämän vektorin suuntainen.
     */
    public Vektori normalisoi() {
        double pituus = this.pituus();
        if (pituus != 0) {
            this.x = this.x / pituus;
            this.y = this.y / pituus;
        } else {
            this.x = 0;
            this.y = 0;
        }
        return this;
    }

    /**
     * Palauttaa tämän vektorin pituuden.
     *
     * @return Palauttaa vektorin pituuden.
     */
    public double pituus() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
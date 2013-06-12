package allas.tools;

public class Vektori {

    private double x; // Vektorin x-koordinaatti
    private double y; // Vektorin y-koordinaatti

    public Vektori(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Laskee kyseisen vektorin ja parametrina saadun vektorin erotuksen, ja
     * palauttaa näin saadun vektorin.
     *
     * @param a
     * @return
     */
    public Vektori erotus(Vektori a) {
        Vektori c = new Vektori(this.x - a.getX(), this.y - a.getY());
        return c;
    }

    /**
     * Laskee kahden vektorin pistetulon, ja palauttaa double- tyyppisen
     * skalaariarvon.
     *
     * @param vektori
     * @return
     */
    public double pistetulo(Vektori vektori) {
        return this.x * vektori.getX() + this.y * vektori.getY();
    }

    /**
     * Kertoo vektorin kertoimella
     *
     * @param kerroin
     * @return
     */
    public Vektori tulo(double kerroin) {
        return new Vektori(this.x * kerroin, this.y * kerroin);
    }

    /**
     *Summaa kaksi kyseiseen vektoriin parametrina saadun vektorin, ja palauttaa saadun vektorin.
     * @param a
     * @return
     */
    public Vektori summa(Vektori a) {
        Vektori c = new Vektori(this.x + a.getX(), this.y + a.getY());
        return c;
    }

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
     * Palauttaa tämän vektorin pituuden
     *
     * @return
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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package allas.tools;

/**
 *
 * @author Sami
 */
public class Vektori {

    private double x;
    private double y;

    public Vektori(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vektori miinus(Vektori a) {
        Vektori c = new Vektori(this.x - a.getX(), this.y - a.getY());
        return c;
    }

    public double pistetulo(Vektori vektori) {
        return this.x * vektori.getX() + this.y * vektori.getY();
    }

    public Vektori tulo(double kerroin) {
        return new Vektori(this.x * kerroin, this.y * kerroin);
    }

    public Vektori plus(Vektori a) {
        Vektori c = new Vektori(this.x + a.getX(), this.y + a.getY());
        return c;
    }

    public double pituus() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }
/**
 * Tekee vektorista yksikkövektorin
 * @return 
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

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}

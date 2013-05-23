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
    private double suuruus;

    public Vektori(double x, double y) {
        this.x = x;
        this.y = y;
        this.suuruus = x * x + y * y;
    }

    public Vektori miinus(Vektori a) {
        Vektori c = new Vektori(this.x - a.getX(), this.y - a.getY());
        return c;
    }

    public Vektori kerro(Vektori a) {
        Vektori c = new Vektori(a.getX() , a.getY());
        System.out.println("Ei vielä toimi");
        return c;
    }

    public Vektori plus(Vektori a) {
        Vektori c = new Vektori(this.x + a.getX(), this.y + a.getY());
        return c;
    }

    public double getSuuruus() {
        return suuruus;
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

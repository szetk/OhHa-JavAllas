/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package allas.domain;

/**
 *
 * @author Sami
 */
public class Pallo {

    private double x; // paikka
    private double y;
    private double vx; // vauhti
    private double vy;
    private int n; // pallon numero
    private boolean pussissa;

    public Pallo(double x, double y, int n) {
        this.x = x;
        this.y = y;
        this.n = n;
        this.vx = 0;
        this.vy = 0;
        this.pussissa = false;
    }

    public void pussita() {
        this.pussissa = true;
    }

    public void liikuta() {
        this.x += this.vx;
        this.y += this.vy;
    }

    public void jarruta(double deltax, double deltay) {
        if (vx < 0) {
            setVx(jarrutaNegatiivista(vx, deltax));
        } else {
            setVx(jarrutaPositiivista(vx, deltax));
        }

        if (vy < 0) {
            setVy(jarrutaNegatiivista(vy, deltay));
        } else {
            setVy(jarrutaPositiivista(vy, deltay));
        }

    }

    public double jarrutaNegatiivista(double v, double deltaV) {
        if (v * (-1) >= deltaV) {
            v += deltaV;
        } else {
            v = 0;
        }
        return v;
    }

    public double jarrutaPositiivista(double v, double deltaV) {
        if (v >= deltaV) {
            v -= deltaV;
        } else {
            v = 0;
        }
        return v;
    }

    public double etaisyys(double x, double y) {
        double deltax = this.x - x;
        double deltay = this.y - y;
        deltax *= deltax;
        deltay *= deltay;
        return (double) Math.sqrt(deltax + deltay);
    }

    public boolean getPussissa() {
        return this.pussissa;
    }

    public boolean liikkuuko() {
        if (this.vx >= 0.0000000001 || this.vx <= -0.0000000001) {
            return true;
        }
        if (this.vy >= 0.0000000001 || this.vy <= -0.0000000001) {
            return true;
        }
        return false;
    }

    public int getN() {
        return n;
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setVx(double vx) {
        this.vx = vx;

    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}

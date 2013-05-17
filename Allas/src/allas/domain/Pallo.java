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

    private int x; // paikka
    private int y;
    private int vx; // vauhti
    private int vy;
    private int n; // pallon numero
    private boolean pussissa;

    public Pallo(int x, int y, int n) {
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

    public void jarruta(int deltax, int deltay) {
        if (vx < 0) {
            setVx(jarrutaNegatiivista(vx, deltax));
        } else {
            setVx(jarrutaPositiivista(vx, deltax));
        }

        if (vy< 0) {
            setVy(jarrutaNegatiivista(vy, deltay));
        } else {
            setVy(jarrutaPositiivista(vy, deltay));
        }

    }

    public int jarrutaNegatiivista(int v, int deltaV) {
        if (v*(-1) >= deltaV) {
            v += deltaV;
        } else {
            v = 0;
        }
        return v;
    }

    public int jarrutaPositiivista(int v, int deltaV) {
        if (v >= deltaV) {
            v -= deltaV;
        } else {
            v = 0;
        }
        return v;
    }

    public boolean getPussissa() {
        return this.pussissa;
    }
    
    public int etaisyys(int x, int y) {
        int deltax = this.x - x;
        int deltay = this.y - y;
        deltax *= deltax;
        deltay *= deltay;
        return (int) Math.sqrt(deltax + deltay);
    }

    public int getN() {
        return n;
    }

    public int getVx() {
        return vx;
    }

    public int getVy() {
        return vy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}

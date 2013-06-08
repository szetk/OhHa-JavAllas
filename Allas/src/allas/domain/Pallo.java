package allas.domain;

import allas.tools.Vektori;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Tämä luokka kuvaa palloa biljardipelissä.
 *
 * @author Sami
 */
public class Pallo {

    private double x; // paikka
    private double y;
    private double vx; // vauhti
    private double vy;
    private int n; // pallon numero
    private int r;
    private boolean pussissa;

    public Pallo(double x, double y, int n, int r) {
        this.x = x;
        this.y = y;
        this.n = n;
        this.vx = 0;
        this.vy = 0;
        this.r = r;
        this.pussissa = false;
    }

    /**
     * Tämä metodi siirtää pallon uuteen paikkaan.
     */
    public void liikuta() {
        this.x += this.vx;
        this.y += this.vy;
    }

    /**
     * Tämä metodi hidastaa vähentää pallon liikenopeutta.
     *
     * @param kitka 
     */
    public void jarruta(double kitka) {
        
        Vektori kitkavektori = this.getNopeusvektori().normalisoi();
        
        
        kitkavektori = kitkavektori.tulo(kitka);
//        System.out.println(kitkavektori.pituus());
        
        double deltaX = Math.abs(kitkavektori.getX());
        double deltaY = Math.abs(kitkavektori.getY());
//        System.out.println(deltaX);
        
        if (vx < 0) {
            setVx(jarrutaNegatiivista(vx, deltaX));
        } else {
            setVx(jarrutaPositiivista(vx, deltaX));
        }

        if (vy < 0) {
            setVy(jarrutaNegatiivista(vy, deltaY));
        } else {
            setVy(jarrutaPositiivista(vy, deltaY));
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

    /**
     * Tämä metodi laskee pallon ja pisteen välisen etäisyyden.
     *
     * @param x Pisteen x-koordinaatti.
     * @param y Pisteen y-koordinaatti.
     * @return
     */
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

    public void setPussissa(Boolean pussissa) {
        this.pussissa = pussissa;
    }

    /**
     * Tämä metodi kertoo liikkuuko pallo tarkkuuden rajoissa.
     *
     * @return Palauttaa totuusarvona pallon liiketilan.
     */
    public boolean liikkuuko() {
        if (this.vx > 0 || this.vx < -0) {
            return true;
        }
        if (this.vy > 0 || this.vy < -0) {
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

    public Vektori getPaikkavektori() {
        return new Vektori(this.x, this.y);
    }

    public Vektori getNopeusvektori() {
        return new Vektori(this.vx, this.vy);
    }

    public void setPaikkavektori(Vektori vektori) {
        this.x = vektori.getX();
        this.y = vektori.getY();
    }

    public void setNopeusvektori(Vektori vektori) {
        this.vx = vektori.getX();
        this.vy = vektori.getY();
    }

    /**
     * Tämä metodi piirtää pallon parametrina saatuun grafiikkaan. Lyöntipallo
     * on valkoinen, 8-pallo musta, muut pienet vihreitä ja suuret sinisiä.
     *
     * @param graphics Parametrina saatu grafiikka.
     */
    public void piirra(Graphics graphics, int seina) {
        if (this.n == 0) {
            graphics.setColor(Color.WHITE);
        } else if (this.n < 8) {
            graphics.setColor(Color.GREEN);
        } else if (this.n > 8) {
            graphics.setColor(Color.BLUE);
        } else {
            graphics.setColor(Color.BLACK);
        }

        graphics.fillOval((int) this.x + seina - this.r, (int) this.y + seina - this.r, 2 * this.r, 2 * this.r);
    }
}

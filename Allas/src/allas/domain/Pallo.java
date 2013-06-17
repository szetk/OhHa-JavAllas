package allas.domain;

import allas.tools.Vektori;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Tämä luokka kuvaa biljardipelin palloa, jolla on paikka, nopeus, säde ja
 * numero. Lisäksi pallo voi olla pussitettuna tai pöydällä (mukana pelissä).
 *
 * @author Sami
 */
public class Pallo {
    /**
     * Pallon paikan x-koordinaatti
     */
    private double x;
    /**
     * Pallon paikan y-koordinaatti
     */
    private double y;
    /**
     * Pallon nopeuden x-koordinaatti
     */
    private double vx; 
    /**
     * Pallon nopeuden y-koordinaatti
     */
    private double vy;
    /**
     * Pallon numero
     */
    private int pallonNumero;
    /**
     * Pallon säde
     */
    private int pallonSade;
    /**
     * Kertoo onko pallo pussissa(trie) vai vielä pelissä(false)
     */
    private boolean pussissa; 

    /**
     *
     * @param x Pallon paikan x-koordinaatti
     * @param y Pallon paikan y-koordinaatti
     * @param pallonNumero Pallon numero
     * @param pallonSade Pallon säde
     */
    public Pallo(double x, double y, int pallonNumero, int pallonSade) {
        this.x = x;
        this.y = y;
        this.pallonNumero = pallonNumero;
        this.vx = 0;
        this.vy = 0;
        this.pallonSade = pallonSade;
        this.pussissa = false;
    }

    /**
     * Tämä metodi siirtää pallon uuteen paikkaan pallon nopeuden mukaisesti.
     */
    public void liikuta() {
        this.x += this.vx;
        this.y += this.vy;
    }

    /**
     * Tämä metodi hidastaa pallon liikettä kitkan vaikutuksesta. Liikettä
     * hidastetaan etenemissuunnan mukaisesti.
     *
     * @see #jarrutaNegatiivista(double, double)
     * @see #jarrutaPositiivista(double, double)
     *
     * @param kitka Pallon nopeutta vähentävä kitka
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

    /**
     * Tämä metodi jarruttaa pallon liikenopeuden komponentin arvoa, kun
     * komponentti on negatiivinen. Tätä käytetään sekä x- että
     * y-koordinaateille.
     *
     * @param v Vähennettävä nopeuden komponentti
     * @param deltaV Vähennyksen suuruus
     * @return Palauttaa nopeudnek omponentin vähennetyn arvon
     * @see #jarruta(double)
     */
    public double jarrutaNegatiivista(double v, double deltaV) {
        if (v * (-1) >= deltaV) {
            v += deltaV;
        } else {
            v = 0;
        }
        return v;
    }

    /**
     * Tämä metodi jarruttaa pallon liikenopeuden komponentin arvoa, kun
     * komponentti on positiivinen. Tätä käytetään sekä x- että
     * y-koordinaateille.
     *
     * @param v Vähennettävä nopeuden komponentti
     * @param deltaV Vähennyksen suuruus
     * @return Palauttaa nopeudnek omponentin vähennetyn arvon
     * @see #jarruta(double)
     */
    public double jarrutaPositiivista(double v, double deltaV) {
        if (v >= deltaV) {
            v -= deltaV;
        } else {
            v = 0;
        }
        return v;
    }

    /**
     * Tämä metodi laskee pallon ja parametrinä saadun pisteen välisen
     * etäisyyden Pythagoraan lauseen avulla.
     *
     * @param x Pisteen x-koordinaatti.
     * @param y Pisteen y-koordinaatti.
     * @return Palauttaa etäisyyden arvon.
     */
    public double etaisyys(double x, double y) {
        double deltax = this.x - x;
        double deltay = this.y - y;
        deltax *= deltax;
        deltay *= deltay;
        return (double) Math.sqrt(deltax + deltay);
    }

    /**
     * Tämä metodi tarkastaa liikkuuko pallo.
     *
     * @return Palautetaan true, jos pallo liikkuu ja false, mikäli ei.
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

    /**
     * Tämä metodi piirtää pallon parametrinä saatuun grafiikkaan. Pienet pallot
     * ovat vihreitä ja isot sinisiä. 8-pallo on musta ja lyöntipallo valkoinen.
     * Palloille piirretään myös numerot ja kasipallolle valkoinen ympyrä.
     *
     * @param graphics Grafiikka, johon piirretään
     * @param seina Peli-luokan seinän paksuus tarvitaan paikan laskemiseen.
     */
    public void piirra(Graphics graphics, int seina) {
        graphics.setColor(Color.BLACK);
        if (this.pallonNumero == 0) {
            graphics.setColor(Color.WHITE);
        } else if (this.pallonNumero < 8) {
            graphics.setColor(Color.GREEN);
        } else if (this.pallonNumero > 8) {
            graphics.setColor(Color.BLUE);
        }
        graphics.fillOval((int) this.x + seina - this.pallonSade, (int) this.y + seina - this.pallonSade, 2 * this.pallonSade, 2 * this.pallonSade);

        // Piirretään kasipalloon valkoinen ympyrä
        if (this.pallonNumero == 8) {
            graphics.setColor(Color.WHITE);
            graphics.fillOval((int) this.x + seina - this.pallonSade / 2, (int) this.y + seina - this.pallonSade / 2, this.pallonSade, this.pallonSade);
        }

        // Piirretään pallojen numerot
        graphics.setColor(Color.BLACK);
        if (this.pallonNumero < 10 && this.pallonNumero != 0) {
            graphics.drawString(this.pallonNumero + "", (int) this.x + seina - 3, (int) this.y + seina + 3);
        } else if (this.pallonNumero != 0) {
            graphics.drawString(this.pallonNumero + "", (int) this.x + seina - 6, (int) this.y + seina + 3);
        }

    }

    public boolean getPussissa() {
        return this.pussissa;
    }

    public int getPallonNumero() {
        return pallonNumero;
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

    public void setPallonNumero(int pallonNumero) {
        this.pallonNumero = pallonNumero;
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

    public void setPussissa(Boolean pussissa) {
        this.pussissa = pussissa;
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
}

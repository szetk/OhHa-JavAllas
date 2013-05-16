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

    public void pussita(){
        this.pussissa = true;
    }
    
    public boolean getPussissa(){
        return this.pussissa;
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

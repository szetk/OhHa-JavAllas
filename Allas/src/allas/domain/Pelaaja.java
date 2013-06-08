/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package allas.domain;

/**
 * Tämä luokka on kuvaus pelaajasta.
 *
 * @author Sami
 */
public class Pelaaja {

    private String nimi;
    private boolean hasIsotPallot;
    private boolean vuorossa;
    private int maalattuPussi;

    public Pelaaja() {
    }

    public boolean getVuorossa() {
        return this.vuorossa;
    }
    
    public int getMaalattuPussi(){
        return this.maalattuPussi;
    }

    public void setVuorossa(Boolean totuusarvo) {
        this.vuorossa = totuusarvo;
    }
    public void setHasIsotPallot(Boolean hasIsotPallot){
        this.hasIsotPallot = hasIsotPallot;
    }
    
    public void setMaalattuPussi(int pussi){
        this.maalattuPussi = pussi;
    }
    
    public boolean hasIsotPallot(){
        return this.hasIsotPallot;
    }
    
    public void setNimi(String nimi){
        this.nimi = nimi;
    }
    
    public String getNimi(){
        return this.nimi;
    }
    
    public boolean onOma(int n){
        if(this.hasIsotPallot && n >= 8){
            return true;
        } if (!this.hasIsotPallot && n <= 8){
            return true;
        }
        return false;
    }
}

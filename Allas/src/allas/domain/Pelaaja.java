package allas.domain;


public class Pelaaja {

    private String nimi; // Pelaajan nimi
    private int maalattuPussi; // Viimeksi maalatun pussin numero (1-6 numeroituna vasemmalta oikealle ja ylhäältä alas)
    private boolean hasIsotPallot; // Kertoo onko tällä pelaajalla isot vai pienet pallot
//    private boolean vuorossa; // Kertoo onko pelaaja vuorossa tällä hetkellä
    private boolean palloRyhmaValittu; // Tämä on true, mikäli pelissä on valittu palloryhmät, ei tarvita jos boolean voi olla null

    public Pelaaja() {
        this.palloRyhmaValittu = false;
        this.maalattuPussi = 0;
    }
    
        
    public boolean onOma(int pallonNumero){
        if (!this.palloRyhmaValittu){
            return true;
        }
        if(this.hasIsotPallot && pallonNumero >= 8){
            return true;
        } if (!this.hasIsotPallot && pallonNumero <= 8){
            return true;
        }
        return false;
    }


//    public void setVuorossa(Boolean totuusarvo) {
//        this.vuorossa = totuusarvo;
//    }
    public void setHasIsotPallot(Boolean hasIsotPallot){
        this.hasIsotPallot = hasIsotPallot;
        this.palloRyhmaValittu = true;
    }
    
    public void setMaalattuPussi(int pussi){
        this.maalattuPussi = pussi;
    }
    
    public void setNimi(String nimi){
        this.nimi = nimi;
    }
    
    public String getNimi(){
        return this.nimi;
    }

    public boolean getPalloRyhmaValittu() {
        return this.palloRyhmaValittu;
    }

//    public boolean getVuorossa() {
//        return this.vuorossa;
//    }
    
    public int getMaalattuPussi(){
        return this.maalattuPussi;
    }

    public boolean hasIsotPallot(){
        return this.hasIsotPallot;
    }
}
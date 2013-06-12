package allas;

import allas.domain.Pelaaja;
import allas.gui.Kayttoliittyma;
import allas.peli.Alusta;
import allas.peli.Peli;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        // TODO code application logic here
        Peli peli = new Peli(new Alusta(1000, 500, 40, 15, 25)); //pituus, leveys, seinä, pallon säde, pussin säde
        Kayttoliittyma kayttis = new Kayttoliittyma(peli);
        
        Thread thread = new Thread(kayttis);
//        SwingUtilities.invokeLater(kayttis);
        thread.start();

        while (kayttis.getPaivitettava() == null) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                System.out.println("Piirtoalustaa ei ole vielä luotu.");
            }
        }
        peli.setPaivitettava(kayttis.getPaivitettava());
        peli.aja();
        System.out.println("Peli päättyi!");
    
        
    }
}
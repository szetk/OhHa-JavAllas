package allas;

import allas.gui.Kayttoliittyma;
import allas.peli.Alusta;
import allas.peli.Peli;

public class Main {
/**
 * Main method which starts the program
 * @param args 
 */
    public static void main(String[] args) {
        // TODO code application logic here
        Peli peli = new Peli(new Alusta(1000, 500, 40, 15, 25)); //pituus, leveys, seinä, pallon säde, pussin säde
        Kayttoliittyma kayttis = new Kayttoliittyma(peli);
        
        Thread thread = new Thread(kayttis);
//        SwingUtilities.invokeLater(kayttis);
        thread.start();

        while (kayttis.getPoyta() == null) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                System.out.println("Piirtoalustaa ei ole vielä luotu.");
            }
        }
        peli.setPoyta(kayttis.getPoyta());
        peli.aja();
        System.out.println("Peli päättyi!");
    
        
    }
}
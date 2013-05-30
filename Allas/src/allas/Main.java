package allas;

import allas.gui.Kayttoliittyma;
import allas.peli.Peli;
import javax.swing.SwingUtilities;


public class Main {
    
        public static void main(String[] args) {
        // TODO code application logic here
            Peli peli = new Peli (1000, 500, 15, 15);
            Kayttoliittyma kayttis = new Kayttoliittyma(peli);
                     SwingUtilities.invokeLater(kayttis);
                     
                     while (kayttis.getPaivitettava() == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.out.println("Piirtoalustaa ei ole vielä luotu.");
            }
        }
        
        peli.setPaivitettava(kayttis.getPaivitettava());
        peli.aja();
        peli.getPallot().get(8).setVx(200);
            
    }
}
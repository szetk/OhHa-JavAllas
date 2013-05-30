package allas.gui;

import allas.peli.Peli;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * Tämä luokka sisältää pelin käyttöliittymän.
 * @author Sami
 */
public class Kayttoliittyma implements Runnable {

    private Peli peli;
    private JFrame frame;
    private Poyta poyta;

    public Kayttoliittyma(Peli peli) {
        this.peli = peli;
    }

    private void luoKomponentit(Container container) {
        this.poyta = new Poyta(this.peli);
        container.add(this.poyta);
    }

    @Override
    public void run() {
        frame = new JFrame("Allaspeli");
        frame.setPreferredSize(new Dimension(this.peli.getPituus(), this.peli.getLeveys()));

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        luoKomponentit(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }



    public Paivitettava getPaivitettava() {
        return this.poyta;
    }
}

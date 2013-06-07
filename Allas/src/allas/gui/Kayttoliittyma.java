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
        Nappaimistonkuuntelija nappaimistonkuuntelija = new Nappaimistonkuuntelija(this.peli);
        Hiirenkuuntelija hiirenkuuntelija = new Hiirenkuuntelija(this.peli);
        HiirenLiikkeenKuuntelija h = new HiirenLiikkeenKuuntelija();
        
        this.frame.addKeyListener(nappaimistonkuuntelija);
        this.frame.addMouseListener(hiirenkuuntelija);
        this.frame.addMouseMotionListener(hiirenkuuntelija);
        
    }

    @Override
    public void run() {
        this.frame = new JFrame("Allaspeli");
        this.frame.setPreferredSize(new Dimension(this.peli.getPituus() + this.peli.getSeina() * 3, 

this.peli.getLeveys() + 5*this.peli.getSeina()));

        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        luoKomponentit(this.frame.getContentPane());

        this.frame.pack();
        this.frame.setVisible(true);
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public Paivitettava getPaivitettava() {
        return this.poyta;
    }
}


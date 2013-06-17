package allas.gui;

import allas.peli.Peli;
import java.awt.*;
import javax.swing.*;

/**
 * Tämä luokka sisältää pelin käyttöliittymän.
 *
 * @author Sami
 */
public class Kayttoliittyma implements Runnable {

    /**
     * Peli-luokan olio, johon tämä käyttöliittymä liittyy.
     */
    private Peli peli;
    /**
     * Javan grafiikan ikkuna.
     */
    private JFrame frame;
    /**
     * Käyttöliittymän piirtoalusta, johon pirretään.
     */
    private Poyta poyta;
    /**
     * Käyttöliittymän tekstikenttä, johon tulostetaan viestit käyttäjälle.
     */
    private JLabel tekstikentta;

    public Kayttoliittyma(Peli peli) {
        this.peli = peli;
    }

    private void luoKomponentit(Container container) {
        container.setLayout(new BorderLayout());
        Container sailio = new Container();
        sailio.setLayout(new GridLayout(2, 1));

        this.poyta = new Poyta(this.peli);
        this.poyta.setBackground(Color.DARK_GRAY);

        this.tekstikentta = new JLabel("Biljardipeli");
        this.tekstikentta.setHorizontalAlignment(SwingConstants.CENTER);


        container.add(this.poyta, BorderLayout.CENTER);
        sailio.add(this.tekstikentta);

        container.add(sailio, BorderLayout.SOUTH);

        Nappaimistonkuuntelija nappaimistonkuuntelija = new Nappaimistonkuuntelija(this.peli);
        Hiirenkuuntelija hiirenkuuntelija = new Hiirenkuuntelija(this.peli);

        this.frame.addKeyListener(nappaimistonkuuntelija);
        this.frame.addMouseListener(hiirenkuuntelija);
        this.frame.addMouseMotionListener(hiirenkuuntelija);
//        this.frame.setResizable(false); // muuttaa ikkunan kokoa

        this.peli.setTekstikentta(this.tekstikentta);

    }

    @Override
    public void run() {
        this.frame = new JFrame("Allaspeli");
        this.frame.setPreferredSize(new Dimension(this.peli.getAlusta().getPoydanPituus() + this.peli.getAlusta().getSeinanPaksuus() * 2 + this.peli.getAlusta().getPallonSade(),
                this.peli.getAlusta().getPoydanLeveys() + 6 * this.peli.getAlusta().getSeinanPaksuus()));

        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        luoKomponentit(this.frame.getContentPane());

        this.frame.pack();
        this.frame.setVisible(true);
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public Poyta getPoyta() {
        return this.poyta;
    }
}

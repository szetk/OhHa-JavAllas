package allas.gui;

import allas.peli.Peli;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics;
import javax.swing.JPanel;
/**
 * Tämä luokka on käyttöjärjestelmän piirtoalusta. Tämä sisältää pöydän piirto-ohjeet.
 * @author Sami
 */
public class Poyta extends JPanel implements Paivitettava {

    private Peli peli;
    private int pituus;
    private int leveys;
    private int seina;

    public Poyta(Peli peli) {
        this.peli = peli;
        this.pituus = this.peli.getPituus();
        this.leveys = this.peli.getLeveys();
        this.seina = this.peli.getSeina();
        super.setBackground(Color.GREEN);
    }

    @Override
    public void paintComponent(Graphics graphics) {
//        super.paintComponent(graphics);
        
//        graphics.fillRect(0, 0, this.pituus, this.seina;
//        graphics.fillRect(0, 0, this.seina, this.leveys);
//        graphics.fillRect(0, this.leveys - this.seina, this.pituus, this.seina);
//        graphics.fillRect(this.pituus - this.seina, 0, this.seina, this.leveys);

        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, this.pituus, this.leveys);

        graphics.setColor(Color.PINK);
        graphics.fillRect(this.seina, this.seina, this.pituus - 2 * this.seina, this.leveys - 2 * this.seina);

//        graphics.setColor(Color.BROWN);
//        graphics.fillRect(0, 0, this.pituus, this.leveys);

        graphics.setColor(Color.BLACK);
        
        graphics.fillOval(0, 0, 2 * this.seina, 2 * this.seina);
        graphics.fillOval(0, this.leveys, (2 * this.seina), 2 * this.seina);
        graphics.fillOval(this.pituus, this.leveys, 2 * this.seina, 2 * this.seina);
        graphics.fillOval(this.pituus, 0, 2 * this.seina, 2 * this.seina);

        graphics.fillOval(this.pituus / 2, 0, 2 * this.seina, 2 * this.seina);
        graphics.fillOval(this.pituus / 2, this.leveys, 2 * this.seina, 2 * this.seina);
        this.peli.piirra(graphics); // piiretään pallot
    }

    @Override
    public void paivita() {
        super.repaint();
    }
}
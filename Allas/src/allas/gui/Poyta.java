package allas.gui;

import allas.peli.Peli;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Tämä luokka on käyttöjärjestelmän piirtoalusta. Tämä sisältää pöydän
 * piirto-ohjeet.
 *
 * @author Sami
 */
public class Poyta extends JPanel implements Paivitettava {

    private Peli peli;
    private int pituus;
    private int leveys;
    private int seina;
    private int reijanKoko;

    public Poyta(Peli peli) {
        this.peli = peli;
        this.pituus = this.peli.getPituus();
        this.leveys = this.peli.getLeveys();
        this.seina = this.peli.getSeina();
        this.reijanKoko = this.peli.getReijanKoko();
        super.setBackground(Color.GREEN);
    }

    @Override
    public void paintComponent(Graphics graphics) {
//        super.paintComponent(graphics);

//        graphics.fillRect(0, 0, this.pituus, this.seina;
//        graphics.fillRect(0, 0, this.seina, this.leveys);
//        graphics.fillRect(0, this.leveys - this.seina, this.pituus, this.seina);
//        graphics.fillRect(this.pituus - this.seina, 0, this.seina, this.leveys);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, this.pituus + 200, this.leveys + 200);
        
        graphics.setColor(Color.ORANGE);
        graphics.fillRect(0, 0, this.pituus + 2 * this.seina, this.leveys + 2 * this.seina);

        graphics.setColor(Color.PINK);
        graphics.fillRect(this.seina, this.seina, this.pituus, this.leveys);

//        graphics.setColor(Color.BROWN);
//        graphics.fillRect(0, 0, this.pituus, this.leveys);

        graphics.setColor(Color.BLACK);

        // pussit, tätä vois vähän kaunistaa
        int pituus1 = this.seina - this.reijanKoko;
        int pituus2 = (this.pituus + this.seina) / 2;
        int pituus3 = this.pituus + this.seina - this.reijanKoko;
        int leveys1 = pituus1;
        int leveys2 = this.leveys + this.seina - this.reijanKoko;
        int halkaisija = this.reijanKoko * 2;

        graphics.fillOval(pituus1, leveys1, halkaisija, halkaisija);
        graphics.fillOval(pituus1, leveys2, halkaisija, halkaisija);

        graphics.fillOval(pituus2, leveys1, halkaisija, halkaisija);
        graphics.fillOval(pituus2, leveys2, halkaisija, halkaisija);

        graphics.fillOval(pituus3, leveys1, halkaisija, halkaisija);
        graphics.fillOval(pituus3, leveys2, halkaisija, halkaisija);



        this.peli.piirra(graphics); // piiretään pallot
    }

    @Override
    public void paivita() {
        super.repaint();
    }
}
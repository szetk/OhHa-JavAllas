package allas.gui;

import allas.peli.Alusta;
import allas.peli.Peli;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Tämä luokka on käyttöjärjestelmän piirtoalusta. Tämä sisältää pöydän
 * piirtämisen ja päivittämisen. Tämä luokka kutsuu päivitettäessä Peli-luokan
 * piirtävää metodia.
 *
 * @author Sami
 */
public class Poyta extends JPanel {

    private Peli peli;
    private int pituus;
    private int leveys;
    private int seina;
    private int reijanKoko;

    /**
     * Poyta-luokan konstruktori. Parametrinä saadaan Peli-luokan olio, josta
     * lypsetään tietoja pöydän piirtämistä varten.
     *
     * @param peli Tätä käytetään tarvittavien mittojen noutamiseen, jotta
     * piirretään pelin mukainen pöytä
     */
    public Poyta(Peli peli) {
        this.peli = peli;
        Alusta alusta = peli.getAlusta();
        this.pituus = alusta.getPoydanPituus();
        this.leveys = alusta.getPoydanLeveys();
        this.seina = alusta.getSeinanPaksuus();
        this.reijanKoko = alusta.getPussinSade();
        super.setBackground(Color.GREEN);
    }

    /**
     * Tämä metodi piirtää pöydän parametrinä saatuun grafiikkaan.
     *
     * @param graphics Parametrinä saadaan grafiikka.
     */
    @Override
    public void paintComponent(Graphics graphics) {
        graphics.setColor(Color.GRAY);
        graphics.fillRect(0, 0, this.pituus + 200, this.leveys + 200);

        graphics.setColor(Color.ORANGE);
        graphics.fillRect(0, 0, this.pituus + 2 * this.seina, this.leveys + 2 * this.seina);

        graphics.setColor(Color.PINK);
        graphics.fillRect(this.seina, this.seina, this.pituus, this.leveys);

        graphics.setColor(Color.BLACK);

        // pussit
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

    /**
     * Tämä metodi piirtää pöydän uudelleen. Tätä käytetään aina kun pelissä on
     * tapahtunut muutos tietyn aika välin jälkeen.
     */
    public void paivita() {
        super.repaint();
    }
}
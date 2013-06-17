/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package allas.gui;

import allas.peli.Peli;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Tämä on biljardipelin hiirenkuuntelija. Toiminta riippuu Peli-luokan oliosta,
 * johon hiirenkuuntelija on kytketty.
 *
 * @author Sami
 */
public class Hiirenkuuntelija implements MouseListener, MouseMotionListener {

    /**
     * Peli-luokan olio, johon tämä Hiirenkuuntelija on kytkettynä.
     */
    private Peli peli;

    /**
     * Konstruktori, joka saa parametrinä Peli-luokan olion, johon
     * hiirenkuuntelija on kytketty.
     *
     * @param peli Peli-luokan olio
     */
    public Hiirenkuuntelija(Peli peli) {
        this.peli = peli;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Tämä metodi käsittelee hiiren klikkauksia. Hiiren klikkauksen vaikutus
     * riippuu Pelin pelitilanteessa.
     *
     * @param e Hiiritapahtuma
     */
    @Override
    public void mousePressed(MouseEvent e) {

        switch (this.peli.getPelitilanne()) {
            case 0:
                return;
            case 1:
                if (this.peli.valkoinenPalloSallitussaPaikassa()) {
                    this.peli.setPelitilanne(2);
                    this.peli.getAlusta().getPallot().get(0).setPussissa(false);
                }
                break;
            case 2:
                this.peli.asetaLyonninSuunta();
                break;
            case 3:
                this.peli.asetaLyonninNopeusJaLyo();
        }


    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    /**
     * Tämä metodi tallentaa biljardipelin Peli-luokan olioon hiiren
     * koordinaatit aina kun ne muuttuvat.
     *
     * @param e Hiiren tapahtuma
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        /*
         * Hiiren koordinaateista vähennettävät numerot 9 ja 30 korjaavat
         * ikkunan aiheuttaman virheen koordinaateissa. Tämä toimii ainakin
         * Windows 7:lla hyvin, ja Ubuntussa kohtuullisesti. Parempi menetelmä
         * on työjonossa. Pelaamisen kannalta ei synny haittaa, sillä lyönnin
         * suunta valitaan käyttäjälle piirretyn viivan mukaisesti.
         */
        double eX = e.getX() - 9;
        double eY = e.getY() - 30;

        switch (this.peli.getPelitilanne()) {
            case 0:
                return;
            case 1:
                this.peli.valkoisenPallonAsetus();
                break;
            case 2:
                break;
            case 3:
                return;
            case 4:
                return;
        }
        this.peli.setHiirenPaikka((int) eX, (int) eY);
    }
}
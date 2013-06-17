
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package allas.gui;

import allas.peli.Peli;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Tämä luokka vastaa näppäimistön painalluksisat biljardipelissä. Toimintaa
 * säätelee Peli-luokan olio.
 *
 * @author Sami
 */
public class Nappaimistonkuuntelija implements KeyListener {

    /**
     * Peli-luokan olio, johon tämä Nappaimistonkuuntelija liittyy.
     */
    private Peli peli;

    /**
     * Konstruktori, joka saa parametrinä Peli-luokan olion, johon
     * näppäimistönkuuntelija on kytketty.
     *
     * @param peli Kytketty Peli-luokan olio
     */
    public Nappaimistonkuuntelija(Peli peli) {
        this.peli = peli;
    }

    /**
     * Näppäimiä painaessa tarkastetaan pelintilanne, ennen kuin päätetään
     * toiminnasta. Näppäimet i ja p toimivat vain tietyissä tilanteissa.
     * Numerot 1-6 toimivat kaikissa tilanteissa.
     *
     * @param ke Näppäintapahtuma
     */
    @Override
    public void keyPressed(KeyEvent ke) {

        switch (ke.getKeyCode()) {
            case KeyEvent.VK_I:
                if (!this.peli.getVuorossaOlevaPelaaja().getPalloRyhmaValittu() && this.peli.odottaaVastausta) {
                    this.peli.odottaaVastausta = false;
                    this.peli.getVuorossaOlevaPelaaja().setHasIsotPallot(true);
                    this.peli.getVastustaja().setHasIsotPallot(false);
                    this.peli.tulosta("Valitsit isot pallot (siniset)!");
                }
                break;
            case KeyEvent.VK_P:
                if (!this.peli.getVuorossaOlevaPelaaja().getPalloRyhmaValittu() && this.peli.odottaaVastausta) {
                    this.peli.odottaaVastausta = false;
                    this.peli.getVuorossaOlevaPelaaja().setHasIsotPallot(false);
                    this.peli.getVastustaja().setHasIsotPallot(true);
                    this.peli.tulosta("Valitsit pienet pallot (vihreät)!");
                }
                break;
            case KeyEvent.VK_1:
                this.peli.getVuorossaOlevaPelaaja().setMaalattuPussi(1);
                this.peli.tulosta("Maalattu pussi " + this.peli.getVuorossaOlevaPelaaja().getMaalattuPussi());
                break;
            case KeyEvent.VK_2:
                this.peli.getVuorossaOlevaPelaaja().setMaalattuPussi(2);
                this.peli.tulosta("Maalattu pussi " + this.peli.getVuorossaOlevaPelaaja().getMaalattuPussi());
                break;
            case KeyEvent.VK_3:
                this.peli.getVuorossaOlevaPelaaja().setMaalattuPussi(3);
                this.peli.tulosta("Maalattu pussi " + this.peli.getVuorossaOlevaPelaaja().getMaalattuPussi());
                break;
            case KeyEvent.VK_4:
                this.peli.getVuorossaOlevaPelaaja().setMaalattuPussi(4);
                this.peli.tulosta("Maalattu pussi " + this.peli.getVuorossaOlevaPelaaja().getMaalattuPussi());
                break;
            case KeyEvent.VK_5:
                this.peli.getVuorossaOlevaPelaaja().setMaalattuPussi(5);
                this.peli.tulosta("Maalattu pussi " + this.peli.getVuorossaOlevaPelaaja().getMaalattuPussi());
                break;
            case KeyEvent.VK_6:
                this.peli.getVuorossaOlevaPelaaja().setMaalattuPussi(6);
                this.peli.tulosta("Maalattu pussi " + this.peli.getVuorossaOlevaPelaaja().getMaalattuPussi());
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }
}

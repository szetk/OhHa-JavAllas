
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package allas.gui;

import allas.peli.Peli;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Samiǥ
 */
public class Nappaimistonkuuntelija implements KeyListener {

    private Peli peli;

    public Nappaimistonkuuntelija(Peli peli) {
        this.peli = peli;
    }

    @Override
    public void keyPressed(KeyEvent ke) {
//        if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
//            this.peli.lueSyote();
//        }
//        if (ke.getKeyCode() == KeyEvent.VK_F6) {
//            this.peli = new Peli(1000, 500, 40, 15, 25);
//        }

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
                case KeyEvent.VK_F6:;
//                    this.peli.aloitaAlusta();
//                this.peli.setPelitilanne(4);
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

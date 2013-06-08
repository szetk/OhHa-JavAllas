/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package allas.gui;

import allas.domain.Pallo;
import allas.peli.Peli;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author Sami
 */
public class Hiirenkuuntelija implements MouseListener, MouseMotionListener {

    private Peli peli;

    public Hiirenkuuntelija(Peli peli) {
        this.peli = peli;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
      
        switch (this.peli.getPelitilanne()) {
            case 0:
                return;
            case 1:
                if (this.peli.valkoinenPalloSallitussaPaikassa()) {
                    this.peli.setPelitilanne(2);
                    this.peli.getPallot().get(0).setPussissa(false);
                }
                break;
            case 2:
                System.out.println("klikkauksen koordinaatit: " + (e.getX() -9) + " " + (e.getY() -30));
                this.peli.asetaLyonninSuunta();
                break;
            case 3:
                this.peli.asetaNopeus();
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

    @Override
    public void mouseMoved(MouseEvent e) {
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
        }
        this.peli.setHiirenPaikka((int) eX, (int) eY);
    }
}
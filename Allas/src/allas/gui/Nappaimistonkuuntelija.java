
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package allas.gui;

import allas.peli.Peli;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JLabel;

/**
 *
 * @author Sami
 */
public class Nappaimistonkuuntelija implements KeyListener {
    
	private Peli peli;

    public Nappaimistonkuuntelija(Peli peli) {
	this.peli = peli;
    }

    @Override
    public void keyPressed(KeyEvent ke) {
         if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
            System.out.println("enteriä painettu");
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }
}


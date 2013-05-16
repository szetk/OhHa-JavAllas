/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package allas.gui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Sami
 */
public class Poyta extends JPanel implements Paivitettava {
    
    public Poyta(){
        
    }

    @Override
    public void paintComponent(Graphics g) {
    }

    @Override
    public void paivita() {
        super.repaint();
    }
}

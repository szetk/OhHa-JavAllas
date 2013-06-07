/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package allas.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author Sami
 */
public class HiirenLiikkeenKuuntelija implements MouseMotionListener {

    @Override
    public void mouseDragged(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println(e.getX() + " " + e.getY());
    }
    
}

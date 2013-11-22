package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
@SuppressWarnings("serial")
public class Board extends JPanel implements ActionListener {
        Character p;
        public Image img;
        Timer time;
        int v = 172;
       
        public Board() {
                p = new Character();
                addKeyListener(new AL());
                setFocusable(true);
                ImageIcon i = new ImageIcon("GUI/background.jpg");
                img = i.getImage();
                time = new Timer(8, this);
                time.start();
        }
 
        public void actionPerformed(ActionEvent e) {
                p.move();
                repaint();
        }
 
        public void paint(Graphics g) {

                super.paint(g);
                Graphics2D g2d = (Graphics2D) g;
 
                g2d.drawImage(img, -p.getX(), -p.getY(), null);

                g2d.drawImage(p.getImage(), p.charX, p.getCharY(), null);

//                System.out.println("X- " + p.getX() + "   nX- " + p.getnX() + "   nX2- " + p.getnX2() + "   charX- " + p.charX +
//                		"   moving- " + p.moving);
        }
 
        private class AL extends KeyAdapter {
                public void keyReleased(KeyEvent e) {
                	p.keyReleased(e);
                }

                public void keyPressed(KeyEvent e) {
                	p.keyPressed(e);
                }
        }
        
}
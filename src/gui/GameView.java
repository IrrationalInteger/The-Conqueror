package gui;
import javax.swing.*;

import engine.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
@SuppressWarnings({ "unused", "serial" })
public class GameView extends JFrame {
public GameView(){
	this.setTitle("The Conqueror");
	this.setVisible(true);
    try {
        this.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("jpeg.jpg")))));
      } catch (IOException e) {
          e.printStackTrace();
      }
    this.pack();
    this.setVisible(true);
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
	this.setExtendedState(JFrame.MAXIMIZED_BOTH);
}	
}

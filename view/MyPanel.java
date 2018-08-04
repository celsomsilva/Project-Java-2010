package br.com.view;

import java.awt.Image;
import java.awt.Graphics;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Panel com a imagem de fundo
 * 
 *@author CMarcelo
 */
@SuppressWarnings("serial")
public class MyPanel extends JPanel{
    
    private Image imgFundo = null;

    public void paintComponent(Graphics g){
        try{
            imgFundo = ImageIO.read(new URL(getClass().getResource
            		("geomatica_background.jpg"), "geomatica_background.jpg"));
            if(imgFundo != null){
                g.drawImage(imgFundo, 0, 0, this.getWidth(), this.getHeight(), this);
            }
            
        }catch(Exception e){
        	e.printStackTrace();
        }
    }

}
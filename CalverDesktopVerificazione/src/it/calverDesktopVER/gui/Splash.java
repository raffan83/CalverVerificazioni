package it.calverDesktopVER.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import java.awt.Window.Type;

public class Splash implements Runnable
{
	private JFrame g ;
	
	JPanel pan ;
	JPanel panel = new JPanel();
	static JLabel lab;
	
    public Splash(JComponent frm)
    {
    	g= new JFrame();
    	g.setResizable(false);
    	g.setType(Type.POPUP);
		
    	g.setUndecorated(true);
    	
    	g.setBackground(new Color(1.0f,1.0f,1.0f,0.5f));
    	Point d=frm.getLocation(); 
      	
        int x = ( frm.getWidth()  - 300) / 2;
 		int y = ( frm.getHeight()  - 400) / 2;
 		
 		x=x+(int)d.getX();
 		y=y+(int)d.getY();
 		
 		g.setLocation(x, y);
 		
 	//	stop();
    }

//	private void stop() {
//	
//		double currentTime=System.currentTimeMillis();
//		
//		while(true) 
//		{
//			if(System.currentTimeMillis()-currentTime>10000) 
//			{
//				g.dispose();
//				break;
//			}
//		}
//	}



	public void close() {
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g.dispose();	
	}
	
	@Override
	public void run() {
		try 
		{
		URL iconURL = this.getClass().getResource("/image/logo.png");


		ImageIcon img = new ImageIcon(iconURL);
		g.setIconImage(img.getImage());
		
		g.getContentPane().setLayout(new BorderLayout());
    	
    //	g.setTitle("Attendere...");
    	g.setPreferredSize(new Dimension(200, 200));
    	
    	String imgLocation = "/image/Splash.gif";
		URL imageURL = GeneralGUI.class.getResource(imgLocation);
		
    	lab = new JLabel(new ImageIcon(imageURL));
    	
        g.getContentPane().add(lab);
        Dimension screenSize =
                Toolkit.getDefaultToolkit().getScreenSize();
              Dimension labelSize = g.getPreferredSize();
              g.setLocation(screenSize.width/2 - (labelSize.width/2),
                          screenSize.height/2 - (labelSize.height/2));
              
        g.pack();
		g.setVisible(true);
		
		}catch (Exception e) {
		e.printStackTrace();
		}
		
	}



}
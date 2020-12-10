package it.calverDesktopVER.exe;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import it.calverDesktopVER.bo.VersionHTTPBO;
import it.calverDesktopVER.gui.GeneralGUI;
import it.calverDesktopVER.gui.InitSplash;
import it.calverDesktopVER.gui.PannelloTOP;

public class ExecuteVerificazione {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try 
		{


			SwingUtilities.invokeLater(new Runnable(){
				public void run() 
				{
					try
					{

						final InitSplash fr= new InitSplash();

						fr.setVisible(true);

						InitSplash.setMessage("Inizzializzazione applicazione...", 0);
						Thread.sleep(50);

						InitSplash.setMessage("Caricamento chiavi di registro...", 1);

						InitSplash.setMessage("Creazione interfaccia utente...", 30);

						GeneralGUI g1 = new GeneralGUI();

						//	g1.setResizable(false);

						URL iconURL = this.getClass().getResource("/image/logo.png");

						BufferedImage imgB = ImageIO.read(iconURL);

						ImageIcon img = new ImageIcon(imgB);

						g1.setIconImage(img.getImage());

						g1.setDefaultCloseOperation(3);
						g1.setVisible(true);

						InitSplash.setMessage("Avvio Applicazione...", 100);
						Thread.sleep(500);
						fr.dispose();

				  	      Thread th = new Thread(){
			      	    	  
				      	    	 public void run() {
				      	           try {
				      	        	  if(VersionHTTPBO.checkVersion()!=null)
				    	      	      {
				    	      	    	if (!VersionHTTPBO.checkVersion().equals("OK_VER")) 
				    	      	    	{
				    	      	    		JOptionPane.showMessageDialog(null,"Disponibile nuova versione \n"+VersionHTTPBO.checkVersion(),"Aggiornamenti disponibili",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
				    	      	    	} 

				    	      	      }
				      	           } catch(Exception v) {
				      	               System.out.println(v);
				      	           }
				      	       }  
				      	    	  
				      	      };   
				      	    th.start();
					}

					catch(Exception ex)
					{
						//	GeneralGUI.printException(ex);
						ex.printStackTrace();
					}
				}

			});


		} catch (Exception e) {
			e.printStackTrace();
		}

	}


}



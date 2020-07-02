package it.calverDesktopVER.exe;

import static com.sun.jna.platform.win32.WinReg.HKEY_CURRENT_USER;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.sun.jna.platform.win32.Advapi32Util;

import it.calverDesktopVER.bo.SessionBO;

import it.calverDesktopVER.gui.GeneralGUI;
import it.calverDesktopVER.gui.InitSplash;
import it.calverDesktopVER.utl.Costanti;

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



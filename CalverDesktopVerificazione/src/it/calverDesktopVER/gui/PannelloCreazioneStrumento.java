package it.calverDesktopVER.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import it.calverDesktopVER.bo.GestioneStrumentoVER_BO;
import it.calverDesktopVER.bo.SessionBO;
import it.calverDesktopVER.dao.SQLiteDAO;
import it.calverDesktopVER.utl.Costanti;
import net.miginfocom.swing.MigLayout;
import javax.swing.JCheckBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;

public class PannelloCreazioneStrumento extends JPanel implements FocusListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField_denominazione;
	private JTextField textField_costruttore;
	private JTextField textField_modello;
	private JTextField textField_pr_min_c1;
	private JTextField textField_freqMesi;
	private BufferedImage img;
	private int widthImg;
	private int heightImg;
	 static JFrame myFrame=null;
	 private JTextField textField_pr_max_c1;
	 private JTextField textField_divisione_verifica_c1;
	 private JTextField textField_divisione_reali_c1;
	 private JTextField textField_numero_divisioni_c1;
	 private JTextField textField_pr_min_c2;
	 private JTextField textField_pr_min_c3;
	 private JTextField textField_pr_max_c2;
	 private JTextField textField_pr_max_c3;
	 private JTextField textField_divisione_verifica_c2;
	 private JTextField textField_divisione_verifica_c3;
	 private JTextField textField_divisione_reali_c2;
	 private JTextField textField_divisione_reali_c3;
	 private JTextField textField_numero_divisioni_c2;
	 private JTextField textField_numero_divisioni_c3;
	 private JTextField textField_anno_ce;
	 private JTextField textField_data_ms;
	
	 @SuppressWarnings("unchecked")
	public PannelloCreazioneStrumento() {
		
		
		myFrame=SessionBO.generarFrame;
		URL imageURL = GeneralGUI.class.getResource("/image/creaStr.png");
		try {
			setImage(ImageIO.read(imageURL));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		SessionBO.prevPage="PSS";
		
	//	setBackground(Costanti.backgroundGrey);
		setLayout(new MigLayout("", "[86.00][120px:120:200px,grow][120px:120:200px,grow][120px:120px:200px,grow][120px:120px:200px,grow][120px:120px:200px,grow]", "[][40][40][40][40][][][40][40][40][40][][50][]"));
		
		JLabel lblCreazioneStrumentoIn = new JLabel("Creazione Strumento");
		lblCreazioneStrumentoIn.setFont(new Font("Arial", Font.ITALIC, 22));
		add(lblCreazioneStrumentoIn, "cell 0 0 6 1,alignx center,aligny center");
		
		JLabel lblDenominazione = new JLabel("Denominazione");
		lblDenominazione.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblDenominazione, "cell 0 1,alignx right");
		
		textField_denominazione = new JTextField();
		textField_denominazione.setFont(new Font("Arial", Font.PLAIN, 18));
		add(textField_denominazione, "cell 1 1 2 1,growx");
		textField_denominazione.setColumns(10);
		
		JLabel lblMatricola = new JLabel("Matricola");
		lblMatricola.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblMatricola, "cell 3 1,alignx right");
		
		final JTextField textField_matricola = new JTextField();
		textField_matricola.setFont(new Font("Arial", Font.PLAIN, 18));
		textField_matricola.setColumns(10);
		add(textField_matricola, "cell 4 1 2 1");
		
		JLabel lblCostruttore = new JLabel("Costruttore");
		lblCostruttore.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblCostruttore, "cell 0 2,alignx right");
		
		textField_costruttore = new JTextField();
		textField_costruttore.setFont(new Font("Arial", Font.PLAIN, 18));
		textField_costruttore.setColumns(10);
		add(textField_costruttore, "cell 1 2 2 1,growx");
		
		JLabel lblFreqVerifica = new JLabel("Freq Verifica");
		lblFreqVerifica.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblFreqVerifica, "cell 3 2,alignx right");
		
		textField_freqMesi = new JTextField();
		textField_freqMesi.setFont(new Font("Arial", Font.PLAIN, 18));
		textField_freqMesi.setColumns(10);
		add(textField_freqMesi, "flowx,cell 4 2 2 1,width :80:");
		
		JLabel lblModello = new JLabel("Modello");
		lblModello.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblModello, "cell 0 3,alignx right");
		
		textField_modello = new JTextField();
		textField_modello.setFont(new Font("Arial", Font.PLAIN, 18));
		textField_modello.setColumns(10);
		add(textField_modello, "cell 1 3 2 1,growx");
		
		JLabel lblClasse = new JLabel("Classe");
		lblClasse.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblClasse, "cell 3 3,alignx trailing");
		
		JComboBox comboBox_classe = new JComboBox();
		comboBox_classe.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox_classe.setModel(new DefaultComboBoxModel(new String[] {"I", "II", "III", "IIII"}));
		comboBox_classe.setMaximumRowCount(4);
		add(comboBox_classe, "cell 4 3 2 1,width :50:");
		
		JLabel lblTipo_strumento = new JLabel("Tipo strumento");
		lblTipo_strumento.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblTipo_strumento, "cell 0 4,alignx trailing");
		
		JComboBox comboBox_tipo_strumento = new JComboBox();
		comboBox_tipo_strumento.setFont(new Font("Arial", Font.PLAIN, 14));
		comboBox_tipo_strumento.setModel(new DefaultComboBoxModel(new String[] {"Singolo campo di pesatura", "Divisioni plurime", "Campi plurimi"}));
		add(comboBox_tipo_strumento, "cell 1 4 2 1");
		
		JLabel lblUM = new JLabel("UM");
		lblUM.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblUM, "cell 3 4,alignx trailing");
		
		JComboBox comboBox_um = new JComboBox();
		comboBox_um.setFont(new Font("Arial", Font.PLAIN, 14));
		comboBox_um.setModel(new DefaultComboBoxModel(new String[] {"KG", "g"}));
		add(comboBox_um, "cell 4 4 2 1,width : 50:");
		
		JLabel lblAnnoMarcatureCe = new JLabel("Anno Marcature CE");
		lblAnnoMarcatureCe.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblAnnoMarcatureCe, "cell 0 5,alignx trailing");
		
		textField_anno_ce = new JTextField();
		textField_anno_ce.setFont(new Font("Arial", Font.PLAIN, 18));
		textField_anno_ce.setColumns(10);
		add(textField_anno_ce, "cell 1 5 2 1,growx");
		
		JLabel lblDataMessaIn = new JLabel("Data MS");
		lblDataMessaIn.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblDataMessaIn, "cell 3 5,alignx trailing");
		
		textField_data_ms = new JTextField();
		textField_data_ms.setFont(new Font("Arial", Font.PLAIN, 18));
		textField_data_ms.setColumns(10);
		add(textField_data_ms, "cell 4 5,growx");
		
		JLabel lblPortatamin = new JLabel("Portata(min)");
		lblPortatamin.setFont(new Font("Arial", Font.BOLD, 14));
		add(lblPortatamin, "cell 1 7,alignx center");
		
		JLabel lblPortatamax = new JLabel("Portata(max)");
		lblPortatamax.setFont(new Font("Arial", Font.BOLD, 14));
		add(lblPortatamax, "flowx,cell 2 7,alignx center");
		
		JLabel lblDivisioniVerifica = new JLabel("Divisioni Verifica");
		lblDivisioniVerifica.setFont(new Font("Arial", Font.BOLD, 14));
		add(lblDivisioniVerifica, "flowx,cell 3 7,alignx center");
		
		JLabel lblCampo1 = new JLabel("Campo 1");
		lblCampo1.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblCampo1, "cell 0 8,alignx right");
		
		textField_pr_min_c1 = new JTextField();
		textField_pr_min_c1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_pr_min_c1.setFont(new Font("Arial", Font.BOLD, 14));
		textField_pr_min_c1.setColumns(10);
		add(textField_pr_min_c1, "cell 1 8,width :200:");
		
		
		textField_pr_max_c1 = new JTextField();
		textField_pr_max_c1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_pr_max_c1.setFont(new Font("Arial", Font.BOLD, 14));
		textField_pr_max_c1.setColumns(10);
		add(textField_pr_max_c1, "cell 2 8,growx");
		
		textField_divisione_verifica_c1 = new JTextField();
		textField_divisione_verifica_c1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_divisione_verifica_c1.setFont(new Font("Arial", Font.BOLD, 14));
		textField_divisione_verifica_c1.setColumns(10);
		add(textField_divisione_verifica_c1, "cell 3 8,growx");
		
		textField_divisione_reali_c1 = new JTextField();
		textField_divisione_reali_c1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_divisione_reali_c1.setFont(new Font("Arial", Font.BOLD, 14));
		textField_divisione_reali_c1.setColumns(10);
		add(textField_divisione_reali_c1, "cell 4 8,growx");
		
		textField_numero_divisioni_c1 = new JTextField();
		textField_numero_divisioni_c1.setEditable(false);
		textField_numero_divisioni_c1.setFont(new Font("Arial", Font.BOLD, 14));
		textField_numero_divisioni_c1.setColumns(10);
		add(textField_numero_divisioni_c1, "cell 5 8,growx");
		
		JLabel lblCampo2 = new JLabel("Campo 2");
		lblCampo2.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblCampo2, "cell 0 9,alignx trailing");
		
		
		textField_pr_min_c2 = new JTextField();
		textField_pr_min_c2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_pr_min_c2.setFont(new Font("Arial", Font.BOLD, 14));
		textField_pr_min_c2.setColumns(10);
		add(textField_pr_min_c2, "cell 1 9,growx");
		
		textField_pr_max_c2 = new JTextField();
		textField_pr_max_c2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_pr_max_c2.setFont(new Font("Arial", Font.BOLD, 14));
		textField_pr_max_c2.setColumns(10);
		add(textField_pr_max_c2, "cell 2 9,growx");
		
		textField_divisione_verifica_c2 = new JTextField();
		textField_divisione_verifica_c2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_divisione_verifica_c2.setFont(new Font("Arial", Font.BOLD, 14));
		textField_divisione_verifica_c2.setColumns(10);
		add(textField_divisione_verifica_c2, "cell 3 9,growx");
		
		textField_divisione_reali_c2 = new JTextField();
		textField_divisione_reali_c2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_divisione_reali_c2.setFont(new Font("Arial", Font.BOLD, 14));
		textField_divisione_reali_c2.setColumns(10);
		add(textField_divisione_reali_c2, "cell 4 9,growx");
		
		textField_numero_divisioni_c2 = new JTextField();
		textField_numero_divisioni_c2.setFont(new Font("Arial", Font.BOLD, 14));
		textField_numero_divisioni_c2.setEditable(false);
		textField_numero_divisioni_c2.setColumns(10);
		add(textField_numero_divisioni_c2, "cell 5 9,growx");
		
		JLabel lblCampo3 = new JLabel("Campo 3");
		lblCampo3.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblCampo3, "cell 0 10,alignx trailing");
		
		
		textField_pr_min_c3 = new JTextField();
		textField_pr_min_c3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_pr_min_c3.setFont(new Font("Arial", Font.BOLD, 14));
		textField_pr_min_c3.setColumns(10);
		add(textField_pr_min_c3, "cell 1 10,growx");
		
		textField_pr_max_c3 = new JTextField();
		textField_pr_max_c3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_pr_max_c3.setFont(new Font("Arial", Font.BOLD, 14));
		textField_pr_max_c3.setColumns(10);
		add(textField_pr_max_c3, "cell 2 10,growx");
		
		textField_divisione_verifica_c3 = new JTextField();
		textField_divisione_verifica_c3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_divisione_verifica_c3.setFont(new Font("Arial", Font.BOLD, 14));
		textField_divisione_verifica_c3.setColumns(10);
		add(textField_divisione_verifica_c3, "cell 3 10,growx");
		
		textField_divisione_reali_c3 = new JTextField();
		textField_divisione_reali_c3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_divisione_reali_c3.setFont(new Font("Arial", Font.BOLD, 14));
		textField_divisione_reali_c3.setColumns(10);
		add(textField_divisione_reali_c3, "cell 4 10,growx");
		
		textField_numero_divisioni_c3 = new JTextField();
		textField_numero_divisioni_c3.setFont(new Font("Arial", Font.BOLD, 14));
		textField_numero_divisioni_c3.setEditable(false);
		textField_numero_divisioni_c3.setColumns(10);
		add(textField_numero_divisioni_c3, "cell 5 10,growx");
		
		JButton btnInserisci = new JButton("Inserisci");
		btnInserisci.setFont(new Font("Arial", Font.BOLD, 18));
		btnInserisci.setIcon(new ImageIcon(PannelloTOP.class.getResource("/image/insert.png")));
		add(btnInserisci, "cell 0 12 6 1,alignx center,height :50:");
		
		JLabel lblDivisioniReali = new JLabel("Divisioni Reali");
		lblDivisioniReali.setFont(new Font("Arial", Font.BOLD, 14));
		add(lblDivisioniReali, "cell 4 7,alignx center");
		
		JLabel lblNumeroDivisioni = new JLabel("Numero Divisioni");
		lblNumeroDivisioni.setFont(new Font("Arial", Font.BOLD, 14));
		add(lblNumeroDivisioni, "cell 5 7,alignx center");
		
		JLabel lblmesi = new JLabel("(Mesi)");
		add(lblmesi, "cell 4 2 2 1");
		lblmesi.setFont(new Font("Arial",Font.PLAIN,14));
		
		textField_pr_min_c1.addFocusListener(this);
		textField_pr_min_c2.addFocusListener(this);
		textField_pr_min_c3.addFocusListener(this);

		textField_pr_max_c1.addFocusListener(this);
		textField_pr_max_c2.addFocusListener(this);
		textField_pr_max_c3.addFocusListener(this);
		
		textField_divisione_reali_c1.addFocusListener(this);
		textField_divisione_reali_c2.addFocusListener(this);
		textField_divisione_reali_c3.addFocusListener(this);
		
		textField_divisione_verifica_c1.addFocusListener(this);
		textField_divisione_verifica_c2.addFocusListener(this);
		textField_divisione_verifica_c3.addFocusListener(this);
		
		textField_divisione_verifica_c1.addFocusListener(this);
		textField_divisione_verifica_c2.addFocusListener(this);
		textField_divisione_verifica_c3.addFocusListener(this);
		
		btnInserisci.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			
				
			}
		});
	}
	
	  public void setImage(BufferedImage img){
		    this.img = img;
		    widthImg = img.getWidth();
		    heightImg = img.getHeight();
		  }

		  public void paintComponent(Graphics g){
		    super.paintComponent(g);
		   // System.out.println(myFrame.getHeight()+myFrame.getWidth());
		    this.setBounds(0, 0, myFrame.getWidth()-32, myFrame.getHeight()-272);
		    g.drawImage(img, 5, 5, myFrame.getWidth()-32, myFrame.getHeight()-272,this);
		
		    
		  }

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void focusLost(FocusEvent e) {
						JTextField ex=(JTextField)e.getComponent();
			
	
					if(ex.getText().length()>0) 
					{	
			try 
			{
				Double.parseDouble(ex.getText());
				ex.setBackground(Color.WHITE);
				System.out.println(e.getComponent().getName());
			} catch (NumberFormatException e2) {
				ex.setBackground(Color.RED);
				ex.requestFocus();
			}
					}
		}
		  
}

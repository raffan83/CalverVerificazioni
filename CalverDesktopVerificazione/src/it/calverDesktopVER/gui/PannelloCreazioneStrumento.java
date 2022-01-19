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
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;

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
import it.calverDesktopVER.dto.VerStrumentoDTO;
import it.calverDesktopVER.utl.Costanti;
import it.calverDesktopVER.utl.Utility;
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
	 private JTextField textField_posizioni_cambio;
	
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
		setLayout(new MigLayout("", "[86.00][120px:120:200px,grow][120px:120:200px,grow][120px:120px:200px,grow][120px:120px:200px,grow][120px:120px:200px,grow]", "[][40][40][40][40][40][40][40px][][40][40][40][40][][40][][50][]"));
		
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
		
		
		textField_matricola.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
			
			try {	
				String matricola =textField_matricola.getText();
				if(matricola.length()>0) 
				{
					if(SQLiteDAO.checkMatricola(matricola)) 
					{
						JOptionPane.showMessageDialog(null,"La matricola inserita è già assegnata a questo cliente ma non è stata inserita in quest'intervento \nContattare l'amministrazione","Attenzione",JOptionPane.WARNING_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
						textField_matricola.setText("");
					}
					System.out.println("text: "+textField_matricola.getText());
				}
			}
			catch (Exception exe) 
			{
				PannelloConsole.printArea("Errore recupero matricola, probabile file obsoleto");
			}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
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
		
		final JComboBox comboBox_classe = new JComboBox();
		comboBox_classe.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox_classe.setModel(new DefaultComboBoxModel(new String[] {"I", "II", "III", "IIII","I - lettura fine","II - lettura fine"}));
		comboBox_classe.setMaximumRowCount(4);
		add(comboBox_classe, "cell 4 3 2 1,width :50:");
		
		JLabel lblTipo_strumento = new JLabel("Tipo strumento");
		lblTipo_strumento.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblTipo_strumento, "cell 0 4,alignx trailing");
		
		final JComboBox comboBox_tipo_strumento = new JComboBox();
		
		comboBox_tipo_strumento.setFont(new Font("Arial", Font.PLAIN, 14));
		comboBox_tipo_strumento.setModel(new DefaultComboBoxModel(new String[] {"Singolo campo di pesatura","Divisioni Plurime", "Campi plurimi","Semiautomatiche con masse a corredo esterno","Semiautomatiche con masse a corredo interno"}));
		add(comboBox_tipo_strumento, "cell 1 4 2 1");
		
		
		
		JLabel lblUM = new JLabel("UM");
		lblUM.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblUM, "cell 3 4,alignx trailing");
		
		final JComboBox comboBox_um = new JComboBox();
		comboBox_um.setFont(new Font("Arial", Font.PLAIN, 14));
		comboBox_um.setModel(new DefaultComboBoxModel(new String[] {"kg", "g"}));
		add(comboBox_um, "cell 4 4 2 1,width : 50:");
		
		JLabel lblAnnoMarcatureCe = new JLabel("Anno Marcature CE");
		lblAnnoMarcatureCe.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblAnnoMarcatureCe, "cell 0 5,alignx trailing");
		
		textField_anno_ce = new JTextField();
		textField_anno_ce.setFont(new Font("Arial", Font.PLAIN, 18));
		textField_anno_ce.setColumns(10);
		add(textField_anno_ce, "cell 1 5 2 1,width :80:");
		
		JLabel lblDataMessaIn = new JLabel("Data MS");
		lblDataMessaIn.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblDataMessaIn, "cell 3 5,alignx trailing");
		
		textField_data_ms = new JTextField();
		textField_data_ms.setFont(new Font("Arial", Font.PLAIN, 18));
		textField_data_ms.setColumns(10);
		add(textField_data_ms, "cell 4 5,growx");
		
		JLabel lblggmmaaaa = new JLabel("(gg/mm/aaaa)");
		lblggmmaaaa.setFont(new Font("Arial", Font.PLAIN, 14));
		add(lblggmmaaaa, "cell 5 5");
		
		JLabel lblTipo = new JLabel("Tipologia");
		lblTipo.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblTipo, "cell 0 6,alignx trailing");
		
		final JComboBox comboBox_tipologia = new JComboBox();
		comboBox_tipologia.setModel(new DefaultComboBoxModel(new String[] {"Elettronica", "Non Elettronica"}));
		comboBox_tipologia.setFont(new Font("Arial", Font.PLAIN, 14));
		add(comboBox_tipologia, "cell 1 6 2 1");
		
		
		comboBox_tipologia.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				if(comboBox_tipologia.getSelectedIndex()==1) 
				{
					textField_freqMesi.setText("36");
					textField_freqMesi.setEditable(false);
				}else 
				{
					textField_freqMesi.setEditable(true);
				}
				
			}
		});
		
		JLabel lblFamigliaStrumento = new JLabel("Famiglia strumento");
		lblFamigliaStrumento.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblFamigliaStrumento, "cell 0 7,alignx trailing");
		
		final JComboBox comboBox_famiglia = new JComboBox();
		comboBox_famiglia.setFont(new Font("Arial", Font.PLAIN, 12));
		comboBox_famiglia.setModel(new DefaultComboBoxModel(new String[] {"Strumenti per pesare a funzionamento NON automatico MECCANICI", "Strumenti per pesare a funzionamento NON automatico ELETTRONICI"}));
		add(comboBox_famiglia, "cell 1 7 3 1,growx");
		
		JLabel lblPortatamin = new JLabel("Portata(min)");
		lblPortatamin.setFont(new Font("Arial", Font.BOLD, 14));
		add(lblPortatamin, "cell 1 9,alignx center");
		
		JLabel lblPortatamax = new JLabel("Portata(max)");
		lblPortatamax.setFont(new Font("Arial", Font.BOLD, 14));
		add(lblPortatamax, "flowx,cell 2 9,alignx center");
		
		JLabel lblDivisioniVerifica = new JLabel("Divisioni Verifica");
		lblDivisioniVerifica.setFont(new Font("Arial", Font.BOLD, 14));
		add(lblDivisioniVerifica, "flowx,cell 3 9,alignx center");
		
		JLabel lblCampo1 = new JLabel("Campo 1 / Parziale");
		lblCampo1.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblCampo1, "cell 0 10,alignx right");
		
		textField_pr_min_c1 = new JTextField();
		textField_pr_min_c1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_pr_min_c1.setFont(new Font("Arial", Font.BOLD, 14));
		textField_pr_min_c1.setColumns(10);
		add(textField_pr_min_c1, "cell 1 10,width :200:");
		
		
		textField_pr_max_c1 = new JTextField();
		textField_pr_max_c1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_pr_max_c1.setFont(new Font("Arial", Font.BOLD, 14));
		textField_pr_max_c1.setColumns(10);
		add(textField_pr_max_c1, "cell 2 10,growx");
		
		textField_divisione_verifica_c1 = new JTextField();
		textField_divisione_verifica_c1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_divisione_verifica_c1.setFont(new Font("Arial", Font.BOLD, 14));
		textField_divisione_verifica_c1.setColumns(10);
		add(textField_divisione_verifica_c1, "cell 3 10,growx");
		
		textField_divisione_reali_c1 = new JTextField();
		textField_divisione_reali_c1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_divisione_reali_c1.setFont(new Font("Arial", Font.BOLD, 14));
		textField_divisione_reali_c1.setColumns(10);
		add(textField_divisione_reali_c1, "cell 4 10,growx");
		
		textField_numero_divisioni_c1 = new JTextField();
		textField_numero_divisioni_c1.setEditable(false);
		textField_numero_divisioni_c1.setFont(new Font("Arial", Font.BOLD, 14));
		textField_numero_divisioni_c1.setColumns(10);
		add(textField_numero_divisioni_c1, "cell 5 10,growx");
		
		JLabel lblCampo2 = new JLabel("Campo 2 / Parziale");
		lblCampo2.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblCampo2, "cell 0 11,alignx trailing");
		
		
		textField_pr_min_c2 = new JTextField();
		textField_pr_min_c2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_pr_min_c2.setFont(new Font("Arial", Font.BOLD, 14));
		textField_pr_min_c2.setColumns(10);
		add(textField_pr_min_c2, "cell 1 11,growx");
		
		textField_pr_max_c2 = new JTextField();
		textField_pr_max_c2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_pr_max_c2.setFont(new Font("Arial", Font.BOLD, 14));
		textField_pr_max_c2.setColumns(10);
		add(textField_pr_max_c2, "cell 2 11,growx");
		
		textField_divisione_verifica_c2 = new JTextField();
		textField_divisione_verifica_c2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_divisione_verifica_c2.setFont(new Font("Arial", Font.BOLD, 14));
		textField_divisione_verifica_c2.setColumns(10);
		add(textField_divisione_verifica_c2, "cell 3 11,growx");
		
		textField_divisione_reali_c2 = new JTextField();
		textField_divisione_reali_c2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_divisione_reali_c2.setFont(new Font("Arial", Font.BOLD, 14));
		textField_divisione_reali_c2.setColumns(10);
		add(textField_divisione_reali_c2, "cell 4 11,growx");
		
		textField_numero_divisioni_c2 = new JTextField();
		textField_numero_divisioni_c2.setFont(new Font("Arial", Font.BOLD, 14));
		textField_numero_divisioni_c2.setEditable(false);
		textField_numero_divisioni_c2.setColumns(10);
		add(textField_numero_divisioni_c2, "cell 5 11,growx");
		
		JLabel lblCampo3 = new JLabel("Campo 3 / Parziale");
		lblCampo3.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblCampo3, "cell 0 12,alignx trailing");
		
		
		textField_pr_min_c3 = new JTextField();
		textField_pr_min_c3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_pr_min_c3.setFont(new Font("Arial", Font.BOLD, 14));
		textField_pr_min_c3.setColumns(10);
		add(textField_pr_min_c3, "cell 1 12,growx");
		
		textField_pr_max_c3 = new JTextField();
		textField_pr_max_c3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_pr_max_c3.setFont(new Font("Arial", Font.BOLD, 14));
		textField_pr_max_c3.setColumns(10);
		add(textField_pr_max_c3, "cell 2 12,growx");
		
		textField_divisione_verifica_c3 = new JTextField();
		textField_divisione_verifica_c3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_divisione_verifica_c3.setFont(new Font("Arial", Font.BOLD, 14));
		textField_divisione_verifica_c3.setColumns(10);
		add(textField_divisione_verifica_c3, "cell 3 12,growx");
		
		textField_divisione_reali_c3 = new JTextField();
		textField_divisione_reali_c3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_divisione_reali_c3.setFont(new Font("Arial", Font.BOLD, 14));
		textField_divisione_reali_c3.setColumns(10);
		add(textField_divisione_reali_c3, "cell 4 12,growx");
		
		textField_numero_divisioni_c3 = new JTextField();
		textField_numero_divisioni_c3.setFont(new Font("Arial", Font.BOLD, 14));
		textField_numero_divisioni_c3.setEditable(false);
		textField_numero_divisioni_c3.setColumns(10);
		add(textField_numero_divisioni_c3, "cell 5 12,growx");
		
		JLabel lblLimitiPosizioniCambio = new JLabel("Numero posizioni cambio");
		lblLimitiPosizioniCambio.setFont(new Font("Arial", Font.BOLD, 14));
		add(lblLimitiPosizioniCambio, "cell 0 14,alignx trailing");
		
		textField_posizioni_cambio = new JTextField();
		textField_posizioni_cambio.setEnabled(false);
		textField_posizioni_cambio.setEditable(false);
		textField_posizioni_cambio.setHorizontalAlignment(SwingConstants.CENTER);
		textField_posizioni_cambio.setFont(new Font("Arial", Font.BOLD, 14));
		textField_posizioni_cambio.setColumns(10);
		add(textField_posizioni_cambio, "cell 1 14,growx");
		
		JButton btnInserisci = new JButton("Inserisci");
		btnInserisci.setFont(new Font("Arial", Font.BOLD, 18));
		btnInserisci.setIcon(new ImageIcon(PannelloTOP.class.getResource("/image/insert.png")));
		add(btnInserisci, "cell 0 16 6 1,alignx center,height :50:");
		
		JLabel lblDivisioniReali = new JLabel("Divisioni Reali");
		lblDivisioniReali.setFont(new Font("Arial", Font.BOLD, 14));
		add(lblDivisioniReali, "cell 4 9,alignx center");
		
		JLabel lblNumeroDivisioni = new JLabel("Numero Divisioni");
		lblNumeroDivisioni.setFont(new Font("Arial", Font.BOLD, 14));
		add(lblNumeroDivisioni, "cell 5 9,alignx center");
		
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
		
		
		textField_pr_min_c2 .setEnabled(false);
		textField_pr_max_c2.setEnabled(false);
		textField_divisione_verifica_c2.setEnabled(false);
		textField_divisione_reali_c2.setEnabled(false);
		textField_numero_divisioni_c2.setEnabled(false);
		
		textField_pr_min_c3 .setEnabled(false);
		textField_pr_max_c3.setEnabled(false);
		textField_divisione_verifica_c3.setEnabled(false);
		textField_divisione_reali_c3.setEnabled(false);
		textField_numero_divisioni_c3.setEnabled(false);
		
		textField_pr_min_c2 .setEditable(false);
		textField_pr_max_c2.setEditable(false);
		textField_divisione_verifica_c2.setEditable(false);
		textField_divisione_reali_c2.setEditable(false);
		textField_numero_divisioni_c2.setEditable(false);
		
		textField_pr_min_c3 .setEditable(false);
		textField_pr_max_c3.setEditable(false);
		textField_divisione_verifica_c3.setEditable(false);
		textField_divisione_reali_c3.setEditable(false);
		textField_numero_divisioni_c3.setEditable(false);

		textField_pr_min_c2.setText("0");
		textField_pr_max_c2.setText("0");
		textField_divisione_verifica_c2.setText("0");
		textField_divisione_reali_c2.setText("0");
		textField_numero_divisioni_c2.setText("0");
		
		textField_pr_min_c3.setText("0");
		textField_pr_max_c3.setText("0");
		textField_divisione_verifica_c3.setText("0");
		textField_divisione_reali_c3.setText("0");
		textField_numero_divisioni_c3.setText("0");
		
		comboBox_tipo_strumento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(comboBox_tipo_strumento.getSelectedIndex()==0 || comboBox_tipo_strumento.getSelectedIndex()==3 ||comboBox_tipo_strumento.getSelectedIndex()==4) 
				{
					textField_pr_min_c2 .setEnabled(false);
					textField_pr_max_c2.setEnabled(false);
					textField_divisione_verifica_c2.setEnabled(false);
					textField_divisione_reali_c2.setEnabled(false);
					textField_numero_divisioni_c2.setEnabled(false);
					
					textField_pr_min_c3 .setEnabled(false);
					textField_pr_max_c3.setEnabled(false);
					textField_divisione_verifica_c3.setEnabled(false);
					textField_divisione_reali_c3.setEnabled(false);
					textField_numero_divisioni_c3.setEnabled(false);
					
					textField_pr_min_c2 .setEditable(false);
					textField_pr_max_c2.setEditable(false);
					textField_divisione_verifica_c2.setEditable(false);
					textField_divisione_reali_c2.setEditable(false);
					textField_numero_divisioni_c2.setEditable(false);
					
					textField_pr_min_c3 .setEditable(false);
					textField_pr_max_c3.setEditable(false);
					textField_divisione_verifica_c3.setEditable(false);
					textField_divisione_reali_c3.setEditable(false);
					textField_numero_divisioni_c3.setEditable(false);
					
				}
				else 
				{
					textField_pr_min_c2 .setEnabled(true);
					textField_pr_max_c2.setEnabled(true);
					textField_divisione_verifica_c2.setEnabled(true);
					textField_divisione_reali_c2.setEnabled(true);
					textField_numero_divisioni_c2.setEnabled(true);
					
					textField_pr_min_c3 .setEnabled(true);
					textField_pr_max_c3.setEnabled(true);
					textField_divisione_verifica_c3.setEnabled(true);
					textField_divisione_reali_c3.setEnabled(true);
					textField_numero_divisioni_c3.setEnabled(true);
					
					textField_pr_min_c2 .setEditable(true);
					textField_pr_max_c2.setEditable(true);
					textField_divisione_verifica_c2.setEditable(true);
					textField_divisione_reali_c2.setEditable(true);
					textField_numero_divisioni_c2.setEditable(true);
					
					textField_pr_min_c3 .setEditable(true);
					textField_pr_max_c3.setEditable(true);
					textField_divisione_verifica_c3.setEditable(true);
					textField_divisione_reali_c3.setEditable(true);
					textField_numero_divisioni_c3.setEditable(true);
				}
				
				if(comboBox_tipo_strumento.getSelectedIndex()==4) 
				{
				
			
					comboBox_tipologia.setSelectedIndex(1);
					comboBox_tipologia.setEnabled(false);
					textField_posizioni_cambio.setEnabled(true);
					 
					  
				  textField_posizioni_cambio.setEditable(true);
				
				}else 
				{
					comboBox_tipologia.setSelectedIndex(0);
					comboBox_tipologia.setEnabled(true);
					
					textField_posizioni_cambio.setEnabled(false);
					
					  
					  textField_posizioni_cambio.setEditable(false);
					
				} 
				
			}
		});
		
		btnInserisci.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VerStrumentoDTO strumento = new VerStrumentoDTO();
				boolean save=true;
				try 
				{
					if(textField_denominazione.getText().length()>0) 
					{
						strumento.setDenominazione(textField_denominazione.getText());
						textField_denominazione.setBackground(Color.WHITE);
					}
					else 
					{
						save=false;
						textField_denominazione.setBackground(Color.RED);
					}
					
					if(textField_costruttore.getText().length()>0) 
					{
						strumento.setCostruttore(textField_costruttore.getText());
						textField_costruttore.setBackground(Color.WHITE);
					}
					else 
					{
						save=false;
						textField_costruttore.setBackground(Color.RED);
					}
					if(textField_modello.getText().length()>0) 
					{
						strumento.setModello(textField_modello.getText());
						textField_modello.setBackground(Color.WHITE);
					}
					else 
					{
						save=false;
						textField_modello.setBackground(Color.RED);
					}
					
					if(textField_matricola.getText().length()>0) 
					{
						strumento.setMatricola(textField_matricola.getText());
						textField_matricola.setBackground(Color.WHITE);
					}
					else 
					{
						save=false;
						textField_matricola.setBackground(Color.RED);
					}
					
					if(Utility.isNumber(textField_freqMesi.getText())) 
					{
						strumento.setFreq_mesi(Integer.parseInt(textField_freqMesi.getText()));
						textField_freqMesi.setBackground(Color.white);
					}else 
					{
						save=false;
						textField_freqMesi.setBackground(Color.RED);
					}
					
					strumento.setClasse(comboBox_classe.getSelectedIndex()+1);
					strumento.setUm(comboBox_um.getSelectedItem().toString());
					strumento.setTipologia(comboBox_tipologia.getSelectedIndex()+1);
					
					if(comboBox_tipo_strumento.getSelectedIndex()==0) 
					{
						strumento.setId_tipo_strumento(1);
					}
					else if (comboBox_tipo_strumento.getSelectedIndex()==1)
					{
						strumento.setId_tipo_strumento(2);
					}
					else if (comboBox_tipo_strumento.getSelectedIndex()==2)
					{
						strumento.setId_tipo_strumento(3);
					}
					else if (comboBox_tipo_strumento.getSelectedIndex()==3)
					{
						strumento.setId_tipo_strumento(4);
					}else 
					{
							strumento.setId_tipo_strumento(5);
					}
					
					
					if(comboBox_famiglia.getSelectedIndex()==0) 
					{
						strumento.setFamiglia_strumento("0211");
					}
					else 
					{
						strumento.setFamiglia_strumento("0212");
					}
					
					
					if(Utility.isNumber(textField_anno_ce.getText())) 
					{
						strumento.setAnno_marcatura_ce(Integer.parseInt(textField_anno_ce.getText()));
						textField_anno_ce.setBackground(Color.white);
					}else 
					{
						save=false;
						textField_anno_ce.setBackground(Color.RED);
					}
					
					if(Utility.isDate(textField_data_ms.getText())) 
					{
						strumento.setData_messa_in_servizio(textField_data_ms.getText());
						textField_data_ms.setBackground(Color.white);
						
					}else 
					{
						save=false;
						textField_data_ms.setBackground(Color.red);
					}
					
					/*PORTATA 1*/
					if(Utility.isDouble(textField_pr_min_c1.getText())) 
					{
						strumento.setPortata_min_C1(new BigDecimal(textField_pr_min_c1.getText()));
					}
					if(Utility.isDouble(textField_pr_max_c1.getText())) 
					{
						strumento.setPortata_max_C1(new BigDecimal(textField_pr_max_c1.getText()));
					}
					if(Utility.isDouble(textField_divisione_reali_c1.getText())) 
					{
						strumento.setDiv_rel_C1(new BigDecimal(textField_divisione_reali_c1.getText()));
					}
					if(Utility.isDouble(textField_divisione_verifica_c1.getText())) 
					{
						strumento.setDiv_ver_C1(new BigDecimal(textField_divisione_verifica_c1.getText()));
					}
					if(Utility.isDouble(textField_numero_divisioni_c1.getText())) 
					{
						strumento.setNumero_div_C1(new BigDecimal(textField_numero_divisioni_c1.getText()));
					}
					
					
					/*PORTATA 2*/
					if(Utility.isDouble(textField_pr_min_c2.getText())) 
					{
						strumento.setPortata_min_C2(new BigDecimal(textField_pr_min_c2.getText()));
					}
					if(Utility.isDouble(textField_pr_max_c2.getText())) 
					{
						strumento.setPortata_max_C2(new BigDecimal(textField_pr_max_c2.getText()));
					}
					if(Utility.isDouble(textField_divisione_reali_c2.getText())) 
					{
						strumento.setDiv_rel_C2(new BigDecimal(textField_divisione_reali_c2.getText()));
					}
					if(Utility.isDouble(textField_divisione_verifica_c2.getText())) 
					{
						strumento.setDiv_ver_C2(new BigDecimal(textField_divisione_verifica_c2.getText()));
					}
					if(Utility.isDouble(textField_numero_divisioni_c2.getText())) 
					{
						strumento.setNumero_div_C2(new BigDecimal(textField_numero_divisioni_c2.getText()));
					}
					
					/*PORTATA 3*/
					if(Utility.isDouble(textField_pr_min_c3.getText())) 
					{
						strumento.setPortata_min_C3(new BigDecimal(textField_pr_min_c3.getText()));
					}
					if(Utility.isDouble(textField_pr_max_c3.getText())) 
					{
						strumento.setPortata_max_C3(new BigDecimal(textField_pr_max_c3.getText()));
					}
					if(Utility.isDouble(textField_divisione_reali_c3.getText())) 
					{
						strumento.setDiv_rel_C3(new BigDecimal(textField_divisione_reali_c3.getText()));
					}
					if(Utility.isDouble(textField_divisione_verifica_c3.getText())) 
					{
						strumento.setDiv_ver_C3(new BigDecimal(textField_divisione_verifica_c3.getText()));
					}
					if(Utility.isDouble(textField_numero_divisioni_c3.getText())) 
					{
						strumento.setNumero_div_C3(new BigDecimal(textField_numero_divisioni_c3.getText()));
					}
					
					boolean valutazionePortate=getValutazionePortate(strumento);
					
					if(valutazionePortate==false) 
					{
						save=false;
						JOptionPane.showMessageDialog(null,"Inserire tutti i dati per singolo campo/divisione coerentemente con il tipo strumento scelto \n i campi vuoti non sono ammessi inserire \"0\" ","Attenzione",JOptionPane.WARNING_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
					}
					
					if(comboBox_tipo_strumento.getSelectedIndex()==4) 
					{
						
						if(Utility.isNumber(textField_posizioni_cambio.getText())) 
						{
							strumento.setPosizioni_cambio(Integer.parseInt(textField_posizioni_cambio.getText()));
						}
					
					}else 
					{
						strumento.setPosizioni_cambio(0);
					}
					
					if(save)
					{
						int id=GestioneStrumentoVER_BO.insertStrumento(strumento);
					
						if(id>0) 
						{
							JOptionPane.showMessageDialog(null,"Inserimento completato correttamente - ID Strumento: "+id,"Strumento",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/confirm.png")));
						}
					}
				} 
				catch (Exception e2) {
					e2.printStackTrace();
				}
				
			
				
			}

			private boolean getValutazionePortate(VerStrumentoDTO strumento) {
				
				boolean esito=true;
				
				try 
				{
					
						Double.parseDouble(textField_pr_min_c1.getText());
						Double.parseDouble(textField_pr_max_c1.getText());
						Double.parseDouble(textField_divisione_reali_c1.getText());
						Double.parseDouble(textField_divisione_verifica_c1.getText());
						Double.parseDouble(textField_numero_divisioni_c1.getText());
						
						Double.parseDouble(textField_pr_min_c2.getText());
						Double.parseDouble(textField_pr_max_c2.getText());
						Double.parseDouble(textField_divisione_reali_c2.getText());
						Double.parseDouble(textField_divisione_verifica_c2.getText());
						Double.parseDouble(textField_numero_divisioni_c2.getText());
						
						Double.parseDouble(textField_pr_min_c3.getText());
						Double.parseDouble(textField_pr_max_c3.getText());
						Double.parseDouble(textField_divisione_reali_c3.getText());
						Double.parseDouble(textField_divisione_verifica_c3.getText());
						Double.parseDouble(textField_numero_divisioni_c3.getText());
					
					
					
				}
				catch (Exception e) 
				{
					return false;
				}
				
				return esito;
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
					
					if(Utility.isDouble(textField_pr_max_c1.getText())&& Utility.isDouble(textField_divisione_verifica_c1.getText())) 
					{
						if(Double.parseDouble(textField_divisione_verifica_c1.getText())!=0) 
						{
							double divisioni =Double.parseDouble(textField_pr_max_c1.getText())/Double.parseDouble(textField_divisione_verifica_c1.getText());
							textField_numero_divisioni_c1.setText(""+divisioni);
						}
					}
					
					if(Utility.isDouble(textField_pr_max_c2.getText())&& Utility.isDouble(textField_divisione_verifica_c2.getText())) 
					{
						if(Double.parseDouble(textField_divisione_verifica_c2.getText())!=0) 
						{
							double divisioni =Double.parseDouble(textField_pr_max_c2.getText())/Double.parseDouble(textField_divisione_verifica_c2.getText());
							textField_numero_divisioni_c2.setText(""+divisioni);
						}
					}
					
					if(Utility.isDouble(textField_pr_max_c3.getText())&& Utility.isDouble(textField_divisione_verifica_c3.getText())) 
					{
						if(Double.parseDouble(textField_divisione_verifica_c3.getText())!=0)
							{
								double divisioni =Double.parseDouble(textField_pr_max_c3.getText())/Double.parseDouble(textField_divisione_verifica_c3.getText());
								textField_numero_divisioni_c3.setText(""+divisioni);
							}
					}
				} catch (NumberFormatException e2) {
					ex.setBackground(Color.RED);
					ex.requestFocus();
				}
			}
		}
		  
}

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

public class PannelloVisualizzazioneStrumento extends JPanel  implements FocusListener{
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
	 
	 VerStrumentoDTO strumento=null;
	 @SuppressWarnings("unchecked")
	public PannelloVisualizzazioneStrumento(VerStrumentoDTO _strumento) {
		 
		 strumento=_strumento;
		
		
		myFrame=SessionBO.generarFrame;
		URL imageURL = GeneralGUI.class.getResource("/image/creaStr.png");
		try {
			setImage(ImageIO.read(imageURL));
		} catch (IOException e2) 
		{
			e2.printStackTrace();
		}
		
		SessionBO.prevPage="PSS";
		
	//	setBackground(Costanti.backgroundGrey);
		setLayout(new MigLayout("", "[86.00][120px:120:200px,grow][120px:120:200px,grow][120px:120px:200px,grow][120px:120px:200px,grow][120px:120px:200px,grow]", "[][40][40][40][40][40][40][40px][][40][40][40][40][][50][]"));
		
		JLabel lblCreazioneStrumentoIn = new JLabel("Visualizza Strumento");
		lblCreazioneStrumentoIn.setFont(new Font("Arial", Font.ITALIC, 22));
		add(lblCreazioneStrumentoIn, "cell 0 0 6 1,alignx center,aligny center");
		
		JLabel lblDenominazione = new JLabel("Denominazione");
		lblDenominazione.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblDenominazione, "cell 0 1,alignx right");
		
		textField_denominazione = new JTextField();
		textField_denominazione.setEditable(false);
		textField_denominazione.setFont(new Font("Arial", Font.PLAIN, 18));
		add(textField_denominazione, "cell 1 1 2 1,growx");
		textField_denominazione.setColumns(10);
		
		JLabel lblMatricola = new JLabel("Matricola");
		lblMatricola.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblMatricola, "cell 3 1,alignx right");
		
		final JTextField textField_matricola = new JTextField();
		textField_matricola.setEditable(false);
		textField_matricola.setFont(new Font("Arial", Font.PLAIN, 18));
		textField_matricola.setColumns(10);
		add(textField_matricola, "cell 4 1 2 1");
		
		JLabel lblCostruttore = new JLabel("Costruttore");
		lblCostruttore.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblCostruttore, "cell 0 2,alignx right");
		
		textField_costruttore = new JTextField();
		textField_costruttore.setEditable(false);
		textField_costruttore.setFont(new Font("Arial", Font.PLAIN, 18));
		textField_costruttore.setColumns(10);
		add(textField_costruttore, "cell 1 2 2 1,growx");
		
		JLabel lblFreqVerifica = new JLabel("Freq Verifica");
		lblFreqVerifica.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblFreqVerifica, "cell 3 2,alignx right");
		
		textField_freqMesi = new JTextField();
		textField_freqMesi.setEditable(false);
		textField_freqMesi.setFont(new Font("Arial", Font.PLAIN, 18));
		textField_freqMesi.setColumns(10);
		add(textField_freqMesi, "flowx,cell 4 2 2 1,width :80:");
		
		JLabel lblModello = new JLabel("Modello");
		lblModello.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblModello, "cell 0 3,alignx right");
		
		textField_modello = new JTextField();
		textField_modello.setEditable(false);
		textField_modello.setFont(new Font("Arial", Font.PLAIN, 18));
		textField_modello.setColumns(10);
		add(textField_modello, "cell 1 3 2 1,growx");
		
		JLabel lblClasse = new JLabel("Classe");
		lblClasse.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblClasse, "cell 3 3,alignx trailing");
		
		final JComboBox comboBox_classe = new JComboBox();
		comboBox_classe.setEnabled(false);
		comboBox_classe.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboBox_classe.setModel(new DefaultComboBoxModel(new String[] {"I", "II", "III", "IIII"}));
		comboBox_classe.setMaximumRowCount(4);
		add(comboBox_classe, "cell 4 3 2 1,width :50:");
		
		JLabel lblTipo_strumento = new JLabel("Tipo strumento");
		lblTipo_strumento.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblTipo_strumento, "cell 0 4,alignx trailing");
		
		final JComboBox comboBox_tipo_strumento = new JComboBox();
		comboBox_tipo_strumento.setEnabled(false);
		
		comboBox_tipo_strumento.setFont(new Font("Arial", Font.PLAIN, 14));
		comboBox_tipo_strumento.setModel(new DefaultComboBoxModel(new String[] {"Singolo campo di pesatura", "Campi plurimi"}));
		add(comboBox_tipo_strumento, "cell 1 4 2 1");
		
		
		
		JLabel lblUM = new JLabel("UM");
		lblUM.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblUM, "cell 3 4,alignx trailing");
		
		final JComboBox comboBox_um = new JComboBox();
		comboBox_um.setEnabled(false);
		comboBox_um.setFont(new Font("Arial", Font.PLAIN, 14));
		comboBox_um.setModel(new DefaultComboBoxModel(new String[] {"kg", "g"}));
		add(comboBox_um, "cell 4 4 2 1,width : 50:");
		
		JLabel lblAnnoMarcatureCe = new JLabel("Anno Marcature CE");
		lblAnnoMarcatureCe.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblAnnoMarcatureCe, "cell 0 5,alignx trailing");
		
		textField_anno_ce = new JTextField();
		textField_anno_ce.setEditable(false);
		textField_anno_ce.setFont(new Font("Arial", Font.PLAIN, 18));
		textField_anno_ce.setColumns(10);
		add(textField_anno_ce, "cell 1 5 2 1,width :80:");
		
		JLabel lblDataMessaIn = new JLabel("Data MS");
		lblDataMessaIn.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblDataMessaIn, "cell 3 5,alignx trailing");
		
		textField_data_ms = new JTextField();
		textField_data_ms.setEditable(false);
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
		comboBox_tipologia.setEnabled(false);
		comboBox_tipologia.setModel(new DefaultComboBoxModel(new String[] {"Elettronica", "Non Elettronica"}));
		comboBox_tipologia.setFont(new Font("Arial", Font.PLAIN, 14));
		add(comboBox_tipologia, "cell 1 6 2 1");
		
		JLabel lblFamiglia = new JLabel("Famiglia");
		lblFamiglia.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblFamiglia, "cell 0 7,alignx trailing");
		
		final JComboBox comboBox_famiglia = new JComboBox();
		comboBox_famiglia.setModel(new DefaultComboBoxModel(new String[] {"Strumenti per pesare a funzionamento NON automatico MECCANICI", "Strumenti per pesare a funzionamento NON automatico ELETTRONICI"}));
		comboBox_famiglia.setEnabled(false);
		comboBox_famiglia.setFont(new Font("Arial", Font.PLAIN, 12));
		add(comboBox_famiglia, "cell 1 7 2 1,growx");
		
		JLabel lblPortatamin = new JLabel("Portata(min)");
		lblPortatamin.setFont(new Font("Arial", Font.BOLD, 14));
		add(lblPortatamin, "cell 1 9,alignx center");
		
		JLabel lblPortatamax = new JLabel("Portata(max)");
		lblPortatamax.setFont(new Font("Arial", Font.BOLD, 14));
		add(lblPortatamax, "flowx,cell 2 9,alignx center");
		
		JLabel lblDivisioniVerifica = new JLabel("Divisioni Verifica");
		lblDivisioniVerifica.setFont(new Font("Arial", Font.BOLD, 14));
		add(lblDivisioniVerifica, "flowx,cell 3 9,alignx center");
		
		JLabel lblCampo1=null;
		if(strumento.getId_tipo_strumento()!=2) {
			
		  lblCampo1 = new JLabel("Campo 1");
		 }
		else
		{
	     lblCampo1 = new JLabel("Campo Parziale 1");
		}
		
		lblCampo1.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblCampo1, "cell 0 10,alignx right");
		
		textField_pr_min_c1 = new JTextField();
		textField_pr_min_c1.setEditable(false);
		textField_pr_min_c1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_pr_min_c1.setFont(new Font("Arial", Font.BOLD, 14));
		textField_pr_min_c1.setColumns(10);
		add(textField_pr_min_c1, "cell 1 10,width :200:");
		
		
		textField_pr_max_c1 = new JTextField();
		textField_pr_max_c1.setEditable(false);
		textField_pr_max_c1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_pr_max_c1.setFont(new Font("Arial", Font.BOLD, 14));
		textField_pr_max_c1.setColumns(10);
		add(textField_pr_max_c1, "cell 2 10,growx");
		
		textField_divisione_verifica_c1 = new JTextField();
		textField_divisione_verifica_c1.setEditable(false);
		textField_divisione_verifica_c1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_divisione_verifica_c1.setFont(new Font("Arial", Font.BOLD, 14));
		textField_divisione_verifica_c1.setColumns(10);
		add(textField_divisione_verifica_c1, "cell 3 10,growx");
		
		textField_divisione_reali_c1 = new JTextField();
		textField_divisione_reali_c1.setEditable(false);
		textField_divisione_reali_c1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_divisione_reali_c1.setFont(new Font("Arial", Font.BOLD, 14));
		textField_divisione_reali_c1.setColumns(10);
		add(textField_divisione_reali_c1, "cell 4 10,growx");
		
		textField_numero_divisioni_c1 = new JTextField();
		textField_numero_divisioni_c1.setEditable(false);
		textField_numero_divisioni_c1.setFont(new Font("Arial", Font.BOLD, 14));
		textField_numero_divisioni_c1.setColumns(10);
		add(textField_numero_divisioni_c1, "cell 5 10,growx");
		
		JLabel lblCampo2=null;
		if(strumento.getId_tipo_strumento()!=2) {
			
		  lblCampo2 = new JLabel("Campo 2");
		 }
		else
		{
	     lblCampo2 = new JLabel("Campo Parziale 2");
		}
		
		lblCampo2.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblCampo2, "cell 0 11,alignx trailing");
		
		
		textField_pr_min_c2 = new JTextField();
		textField_pr_min_c2.setEditable(false);
		textField_pr_min_c2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_pr_min_c2.setFont(new Font("Arial", Font.BOLD, 14));
		textField_pr_min_c2.setColumns(10);
		add(textField_pr_min_c2, "cell 1 11,growx");
		
		textField_pr_max_c2 = new JTextField();
		textField_pr_max_c2.setEditable(false);
		textField_pr_max_c2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_pr_max_c2.setFont(new Font("Arial", Font.BOLD, 14));
		textField_pr_max_c2.setColumns(10);
		add(textField_pr_max_c2, "cell 2 11,growx");
		
		textField_divisione_verifica_c2 = new JTextField();
		textField_divisione_verifica_c2.setEditable(false);
		textField_divisione_verifica_c2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_divisione_verifica_c2.setFont(new Font("Arial", Font.BOLD, 14));
		textField_divisione_verifica_c2.setColumns(10);
		add(textField_divisione_verifica_c2, "cell 3 11,growx");
		
		textField_divisione_reali_c2 = new JTextField();
		textField_divisione_reali_c2.setEditable(false);
		textField_divisione_reali_c2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_divisione_reali_c2.setFont(new Font("Arial", Font.BOLD, 14));
		textField_divisione_reali_c2.setColumns(10);
		add(textField_divisione_reali_c2, "cell 4 11,growx");
		
		textField_numero_divisioni_c2 = new JTextField();
		textField_numero_divisioni_c2.setEditable(false);
		textField_numero_divisioni_c2.setFont(new Font("Arial", Font.BOLD, 14));
		textField_numero_divisioni_c2.setColumns(10);
		add(textField_numero_divisioni_c2, "cell 5 11,growx");
		
		JLabel lblCampo3=null;
		if(strumento.getId_tipo_strumento()!=2) {
			
		  lblCampo3 = new JLabel("Campo 3");
		 }
		else
		{
	     lblCampo3 = new JLabel("Campo Parziale 3");
		}
		
		lblCampo3.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblCampo3, "cell 0 12,alignx trailing");
		
		
		textField_pr_min_c3 = new JTextField();
		textField_pr_min_c3.setEditable(false);
		textField_pr_min_c3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_pr_min_c3.setFont(new Font("Arial", Font.BOLD, 14));
		textField_pr_min_c3.setColumns(10);
		add(textField_pr_min_c3, "cell 1 12,growx");
		
		textField_pr_max_c3 = new JTextField();
		textField_pr_max_c3.setEditable(false);
		textField_pr_max_c3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_pr_max_c3.setFont(new Font("Arial", Font.BOLD, 14));
		textField_pr_max_c3.setColumns(10);
		add(textField_pr_max_c3, "cell 2 12,growx");
		
		textField_divisione_verifica_c3 = new JTextField();
		textField_divisione_verifica_c3.setEditable(false);
		textField_divisione_verifica_c3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_divisione_verifica_c3.setFont(new Font("Arial", Font.BOLD, 14));
		textField_divisione_verifica_c3.setColumns(10);
		add(textField_divisione_verifica_c3, "cell 3 12,growx");
		
		textField_divisione_reali_c3 = new JTextField();
		textField_divisione_reali_c3.setEditable(false);
		textField_divisione_reali_c3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_divisione_reali_c3.setFont(new Font("Arial", Font.BOLD, 14));
		textField_divisione_reali_c3.setColumns(10);
		add(textField_divisione_reali_c3, "cell 4 12,growx");
		
		textField_numero_divisioni_c3 = new JTextField();
		textField_numero_divisioni_c3.setEditable(false);
		textField_numero_divisioni_c3.setFont(new Font("Arial", Font.BOLD, 14));
		textField_numero_divisioni_c3.setColumns(10);
		add(textField_numero_divisioni_c3, "cell 5 12,growx");
		
		JLabel lblDivisioniReali = new JLabel("Divisioni Reali");
		lblDivisioniReali.setFont(new Font("Arial", Font.BOLD, 14));
		add(lblDivisioniReali, "cell 4 9,alignx center");
		
		JLabel lblNumeroDivisioni = new JLabel("Numero Divisioni");
		lblNumeroDivisioni.setFont(new Font("Arial", Font.BOLD, 14));
		add(lblNumeroDivisioni, "cell 5 9,alignx center");
		
		JLabel lblmesi = new JLabel("(Mesi)");
		add(lblmesi, "cell 4 2 2 1");
		lblmesi.setFont(new Font("Arial",Font.PLAIN,14));
		
		
		textField_denominazione.setText(strumento.getDenominazione());
		textField_costruttore.setText(strumento.getCostruttore());
		textField_matricola.setText(strumento.getMatricola());
		textField_modello.setText(strumento.getModello());
		textField_freqMesi.setText(""+strumento.getFreq_mesi());
		textField_anno_ce.setText(""+strumento.getAnno_marcatura_ce());
		textField_data_ms.setText(strumento.getData_messa_in_servizio());
		
		
		if(strumento.getId_tipo_strumento()==1)
		{
			comboBox_tipo_strumento.setSelectedIndex(0);
		}else 
		{
			comboBox_tipo_strumento.setSelectedIndex(1);
		}
		
		comboBox_tipologia.setSelectedIndex(strumento.getTipologia()-1);
		comboBox_classe.setSelectedIndex(strumento.getClasse()-1);
		
		if(strumento.getFamiglia_strumento().equals("0211")) 
		{
			comboBox_famiglia.setSelectedIndex(0);
		}else 
		{
			comboBox_famiglia.setSelectedIndex(1);
		}
		
		if(strumento.getUm().equals("kg")) 
		{
			comboBox_um.setSelectedIndex(0);
		}else 
		{
			comboBox_um.setSelectedIndex(1);
		}

		if(strumento.getPortata_min_C1()!=null) 
		{
			textField_pr_min_c1.setText(strumento.getPortata_min_C1().toPlainString());
		}
		
		if(strumento.getPortata_max_C1()!=null) 
		{
			textField_pr_max_c1.setText(strumento.getPortata_max_C1().toPlainString());
		}
		
		if(strumento.getDiv_ver_C1()!=null) 
		{
			textField_divisione_verifica_c1.setText(strumento.getDiv_ver_C1().toPlainString());
		}
		
		if(strumento.getDiv_rel_C1()!=null) 
		{
			textField_divisione_reali_c1.setText(strumento.getDiv_rel_C1().toPlainString());
		}
		
		if(strumento.getNumero_div_C1()!=null) 
		{
			textField_numero_divisioni_c1.setText(strumento.getNumero_div_C1().toEngineeringString());
		}
		
		if(strumento.getPortata_min_C2()!=null) 
		{
			textField_pr_min_c2.setText(strumento.getPortata_min_C2().toPlainString());
		}
		if(strumento.getPortata_max_C2()!=null) 
		{
			textField_pr_max_c2.setText(strumento.getPortata_max_C2().toPlainString());
		}
		if(strumento.getDiv_ver_C2()!=null) 
		{
			textField_divisione_verifica_c2.setText(strumento.getDiv_ver_C2().toPlainString());
		}
		if(strumento.getDiv_rel_C2()!=null) 
		{
			textField_divisione_reali_c2.setText(strumento.getDiv_rel_C2().toPlainString());
		}
		if(strumento.getNumero_div_C2()!=null) 
		{
			textField_numero_divisioni_c2.setText(strumento.getNumero_div_C2().toEngineeringString());
		}
		
		if(strumento.getPortata_min_C3()!=null) 
		{
			textField_pr_min_c3.setText(strumento.getPortata_min_C3().toPlainString());
		}
		if(strumento.getPortata_max_C3()!=null) 
		{
			textField_pr_max_c3.setText(strumento.getPortata_max_C3().toPlainString());
		}
		if(strumento.getDiv_ver_C3()!=null) 
		{
			textField_divisione_verifica_c3.setText(strumento.getDiv_ver_C3().toPlainString());
		}
		if(strumento.getDiv_rel_C3()!=null) 
		{
			textField_divisione_reali_c3.setText(strumento.getDiv_rel_C3().toPlainString());
		}
		
		if(strumento.getNumero_div_C3()!=null) 
		{
			textField_numero_divisioni_c3.setText(strumento.getNumero_div_C3().toEngineeringString());
		}
		
		JButton btnModifica = new JButton("Modifica");
		btnModifica.setIcon(new ImageIcon(PannelloVisualizzazioneStrumento.class.getResource("/image/incertezza.png")));
		btnModifica.setFont(new Font("Arial", Font.BOLD, 16));
		
		try {
		if(SQLiteDAO.getMisuraByIDStrumento(strumento.getId())==null)
		
			add(btnModifica, "flowx,cell 0 13 6 1,width :150:,alignx center,height :35:");
		}
		catch (Exception e) 
		{
			PannelloConsole.printArea("Errore recupero misura by ID");
		}
		
		
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
		
		comboBox_tipo_strumento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(comboBox_tipo_strumento.getSelectedIndex()==0) 
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
				
			}
		});
		
		
		
		final JButton btnSalva = new JButton("Salva");
		btnSalva.setIcon(new ImageIcon(PannelloVisualizzazioneStrumento.class.getResource("/image/save.png")));
		btnSalva.setFont(new Font("Arial", Font.BOLD, 16));
		add(btnSalva, "cell 0 13,width :150:,height :35:");
		
		btnSalva.setVisible(false);
		
		
		btnModifica.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnSalva.setVisible(true);
				textField_matricola.setEditable(true);
				textField_anno_ce.setEditable(true);
				textField_costruttore.setEditable(true);
				textField_denominazione.setEditable(true);
				textField_modello.setEditable(true);
				textField_data_ms.setEditable(true);
				textField_freqMesi.setEditable(true);
				
				comboBox_classe.setEnabled(true);
				comboBox_um.setEnabled(true);
				comboBox_tipo_strumento.setEnabled(true);
				comboBox_tipologia.setEnabled(true);
				comboBox_famiglia.setEnabled(true);
				
				
				if(comboBox_tipo_strumento.getSelectedIndex()==0) 
				{
					textField_pr_min_c1.setEditable(true);
					textField_pr_max_c1.setEditable(true);
					textField_divisione_reali_c1.setEditable(true);
					textField_divisione_verifica_c1.setEditable(true);
					textField_numero_divisioni_c1.setEditable(true);
				}
				else 
				{
					textField_pr_min_c1.setEditable(true);
					textField_pr_max_c1.setEditable(true);
					textField_divisione_reali_c1.setEditable(true);
					textField_divisione_verifica_c1.setEditable(true);
					textField_numero_divisioni_c1.setEditable(true);
					
					textField_pr_min_c2.setEditable(true);
					textField_pr_max_c2.setEditable(true);
					textField_divisione_reali_c2.setEditable(true);
					textField_divisione_verifica_c2.setEditable(true);
					textField_numero_divisioni_c2.setEditable(true);
					
					textField_pr_min_c3.setEditable(true);
					textField_pr_max_c3.setEditable(true);
					textField_divisione_reali_c3.setEditable(true);
					textField_divisione_verifica_c3.setEditable(true);
					textField_numero_divisioni_c3.setEditable(true);
				}
			}
		});
		
		
		btnSalva.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				boolean update=true;
				
				if(textField_matricola.getText().length()==0) 
				{
					update=false;
				}
				if(textField_costruttore.getText().length()==0) 
				{
					update=false;
				}
				if(textField_modello.getText().length()==0) 
				{
					update=false;
				}
				if(textField_denominazione.getText().length()==0) 
				{
					update=false;
				}
				if(textField_anno_ce.getText().length()==0 || !Utility.isNumber(textField_anno_ce.getText())) 
				{
					update=false;
				}
				if(textField_freqMesi.getText().length()==0 || !Utility.isNumber(textField_freqMesi.getText())) 
				{
					update=false;
				}
				
				if(textField_data_ms.getText().length()==0 || !Utility.isDate(textField_data_ms.getText())) 
				{
					update=false;
				}
				
				/*Controllo valori*/
				
				if(comboBox_tipo_strumento.getSelectedIndex()==0) 
				{
					if(!Utility.isDouble(textField_pr_min_c1.getText())) 
					{
						update=false;
					}
					
					if(!Utility.isDouble(textField_pr_max_c1.getText())) 
					{
						update=false;
					}
					
					if(!Utility.isDouble(textField_divisione_reali_c1.getText())) 
					{
						update=false;
					}
					
					if(!Utility.isDouble(textField_divisione_verifica_c1.getText())) 
					{
						update=false;
					}
					
					if(!Utility.isDouble(textField_numero_divisioni_c1.getText())) 
					{
						update=false;
					}
					
				}
				
			/*	if(comboBox_tipo_strumento.getSelectedIndex()==1){
				
						if(!Utility.isDouble(textField_pr_min_c1.getText())) 
						{
							update=false;
						}
						
						if(!Utility.isDouble(textField_pr_max_c1.getText())) 
						{
							update=false;
						}
						
						if(!Utility.isDouble(textField_divisione_reali_c1.getText())) 
						{
							update=false;
						}
						
						if(!Utility.isDouble(textField_divisione_verifica_c1.getText())) 
						{
							update=false;
						}
						
						if(!Utility.isDouble(textField_numero_divisioni_c1.getText())) 
						{
							update=false;
						}
						
						if(!Utility.isDouble(textField_pr_min_c2.getText()) && !Utility.isDouble(textField_pr_min_c3.getText())) 
						{
						 update=false;
						}
						
						if(!Utility.isDouble(textField_pr_max_c2.getText()) && !Utility.isDouble(textField_pr_max_c3.getText())) 
						{
						 update=false;
						}
						
						if(!Utility.isDouble(textField_divisione_reali_c2.getText()) && !Utility.isDouble(textField_divisione_reali_c3.getText())) 
						{
						 update=false;
						}
						
						if(!Utility.isDouble(textField_divisione_verifica_c2.getText()) && !Utility.isDouble(textField_divisione_verifica_c3.getText())) 
						{
						 update=false;
						}
						
						if(!Utility.isDouble(textField_numero_divisioni_c2.getText()) && !Utility.isDouble(textField_numero_divisioni_c3.getText())) 
						{
						 update=false;
						}
					}*/
				
				if(comboBox_tipo_strumento.getSelectedIndex()==1)
					{
					
							if(!Utility.isDouble(textField_pr_min_c1.getText())) 
							{
								update=false;
							}
							
							if(!Utility.isDouble(textField_pr_max_c1.getText())) 
							{
								update=false;
							}
							
							if(!Utility.isDouble(textField_divisione_reali_c1.getText())) 
							{
								update=false;
							}
							
							if(!Utility.isDouble(textField_divisione_verifica_c1.getText())) 
							{
								update=false;
							}
							
							if(!Utility.isDouble(textField_numero_divisioni_c1.getText())) 
							{
								update=false;
							}
							
							if(!Utility.isDouble(textField_pr_min_c2.getText())) 
							{
							 update=false;
							}
							
							if(!Utility.isDouble(textField_pr_max_c2.getText())) 
							{
							 update=false;
							}
							
							if(!Utility.isDouble(textField_divisione_reali_c2.getText())) 
							{
							 update=false;
							}
							
							if(!Utility.isDouble(textField_divisione_verifica_c2.getText())) 
							{
							 update=false;
							}
							
							if(!Utility.isDouble(textField_numero_divisioni_c2.getText())) 
							{
							 update=false;
							}
							if(!Utility.isDouble(textField_pr_min_c3.getText())) 
							{
							 update=false;
							}
							
							if(!Utility.isDouble(textField_pr_max_c3.getText())) 
							{
							 update=false;
							}
							
							if(!Utility.isDouble(textField_divisione_reali_c3.getText())) 
							{
							 update=false;
							}
							
							if(!Utility.isDouble(textField_divisione_verifica_c3.getText())) 
							{
							 update=false;
							}
							
							if(!Utility.isDouble(textField_numero_divisioni_c3.getText())) 
							{
							 update=false;
							}
						}
				
				
				if(update)
				{
					strumento.setAnno_marcatura_ce(Integer.parseInt(textField_anno_ce.getText()));
					strumento.setDenominazione(textField_denominazione.getText());
					strumento.setMatricola(textField_matricola.getText());
					strumento.setModello(textField_modello.getText());
					strumento.setData_messa_in_servizio(textField_data_ms.getText());
					strumento.setCostruttore(textField_costruttore.getText());
					strumento.setFreq_mesi(Integer.parseInt(textField_freqMesi.getText()));
					
					strumento.setClasse(comboBox_classe.getSelectedIndex()+1);
					strumento.setUm(comboBox_um.getSelectedItem().toString());
					strumento.setTipologia(comboBox_tipologia.getSelectedIndex()+1);
					
					if(comboBox_famiglia.getSelectedIndex()==0) 
					{
						strumento.setFamiglia_strumento("0211");
					}
					else 
					{
						strumento.setFamiglia_strumento("0212");	
					}
					
					if(comboBox_tipo_strumento.getSelectedIndex()==0) 
					{
						strumento.setId_tipo_strumento(1);
					}
					else 
					{
						strumento.setId_tipo_strumento(3);
					}
					strumento.setPortata_min_C1(new BigDecimal(textField_pr_min_c1.getText()));
					strumento.setPortata_max_C1(new BigDecimal(textField_pr_max_c1.getText()));
					strumento.setDiv_rel_C1(new BigDecimal(textField_divisione_reali_c1.getText()));
					strumento.setDiv_ver_C1(new BigDecimal(textField_divisione_verifica_c1.getText()));
					strumento.setNumero_div_C1(new BigDecimal(textField_numero_divisioni_c1.getText()));
					
					if(textField_pr_min_c2.getText().length()>0) 
					{
						strumento.setPortata_min_C2(new BigDecimal(textField_pr_min_c2.getText()));
					}
					else 
					{
						strumento.setPortata_min_C2(BigDecimal.ZERO);
					}
					
					if(textField_pr_max_c2.getText().length()>0) 
					{
						strumento.setPortata_max_C2(new BigDecimal(textField_pr_max_c2.getText()));
					}
					else 
					{
						strumento.setPortata_max_C2(BigDecimal.ZERO);
					}
					
					if(textField_divisione_reali_c2.getText().length()>0) 
					{
						strumento.setDiv_rel_C2(new BigDecimal(textField_divisione_reali_c2.getText()));
					}
					else 
					{
						strumento.setDiv_rel_C2(BigDecimal.ZERO);
					}
					
					if(textField_divisione_verifica_c2.getText().length()>0) 
					{
						strumento.setDiv_ver_C2(new BigDecimal(textField_divisione_verifica_c2.getText()));
					}
					else 
					{
						strumento.setDiv_ver_C2(BigDecimal.ZERO);
					}
					
					if(textField_numero_divisioni_c2.getText().length()>0) 
					{
						strumento.setNumero_div_C2(new BigDecimal(textField_numero_divisioni_c2.getText()));
					}
					else 
					{
						strumento.setNumero_div_C2(BigDecimal.ZERO);
					}
					
					/*CAMPO 3*/
					
					if(textField_pr_min_c3.getText().length()>0) 
					{
						strumento.setPortata_min_C3(new BigDecimal(textField_pr_min_c3.getText()));
					}
					else 
					{
						strumento.setPortata_min_C3(BigDecimal.ZERO);
					}
					
					if(textField_pr_max_c3.getText().length()>0) 
					{
						strumento.setPortata_max_C3(new BigDecimal(textField_pr_max_c3.getText()));
					}
					else 
					{
						strumento.setPortata_max_C3(BigDecimal.ZERO);
					}
					
					if(textField_divisione_reali_c3.getText().length()>0) 
					{
						strumento.setDiv_rel_C3(new BigDecimal(textField_divisione_reali_c3.getText()));
					}
					else 
					{
						strumento.setDiv_rel_C3(BigDecimal.ZERO);
					}
					
					if(textField_divisione_verifica_c3.getText().length()>0) 
					{
						strumento.setDiv_ver_C3(new BigDecimal(textField_divisione_verifica_c3.getText()));
					}
					else 
					{
						strumento.setDiv_ver_C3(BigDecimal.ZERO);
					}
					
					if(textField_numero_divisioni_c3.getText().length()>0) 
					{
						strumento.setNumero_div_C3(new BigDecimal(textField_numero_divisioni_c3.getText()));
					}
					else 
					{
						strumento.setNumero_div_C3(BigDecimal.ZERO);
					}
					
					try {
						GestioneStrumentoVER_BO.updateStrumento(strumento);
					} catch (Exception e1) {
						
						e1.printStackTrace();
					}
					
					btnSalva.setVisible(false);
					textField_matricola.setEditable(false);
					textField_anno_ce.setEditable(false);
					textField_costruttore.setEditable(false);
					textField_denominazione.setEditable(false);
					textField_modello.setEditable(false);
					textField_data_ms.setEditable(false);
					textField_freqMesi.setEditable(false);
					comboBox_classe.setEnabled(false);
					comboBox_um.setEnabled(false);
					comboBox_tipo_strumento.setEnabled(false);
					comboBox_tipologia.setEnabled(false);
					comboBox_famiglia.setEnabled(false);
					
					
					textField_pr_min_c1.setEditable(false);
					textField_pr_max_c1.setEditable(false);
					textField_divisione_reali_c1.setEditable(false);
					textField_divisione_verifica_c1.setEditable(false);
					textField_numero_divisioni_c1.setEditable(false);
					
					textField_pr_min_c2.setEditable(false);
					textField_pr_max_c2.setEditable(false);
					textField_divisione_reali_c2.setEditable(false);
					textField_divisione_verifica_c2.setEditable(false);
					textField_numero_divisioni_c2.setEditable(false);
					
					textField_pr_min_c3.setEditable(false);
					textField_pr_max_c3.setEditable(false);
					textField_divisione_reali_c3.setEditable(false);
					textField_divisione_verifica_c3.setEditable(false);
					textField_numero_divisioni_c3.setEditable(false);
				
				}
				else 
				{
					JOptionPane.showMessageDialog(null,"Completare tutti i campi","Attenzione",JOptionPane.WARNING_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
				}
			
				
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

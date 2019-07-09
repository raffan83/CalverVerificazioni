package it.calverDesktopVER.gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import it.calverDesktopVER.bo.GestioneDB;
import it.calverDesktopVER.bo.GestioneMisuraBO;
import it.calverDesktopVER.bo.GestioneStrumentoVER_BO;
import it.calverDesktopVER.bo.SessionBO;
import it.calverDesktopVER.dto.VerStrumentoDTO;
import it.calverDesktopVER.utl.Costanti;
import net.miginfocom.swing.MigLayout;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Canvas;


public class PannelloMisuraMaster extends JPanel 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static JTree tree =null;
	JPopupMenu popupMenu,popupMenuMisura;
	JMenuItem jmit,jmit1,jmitMisura;
	int current;
	JLabel lblTipoStrumento;

	JPanel panel_dati_misura;
	JPanel panel_struttura;
	JPanel panel_avvio;
	JPanel panel_stampa;
	private JPanel panel_tabella;
	private JPanel panel_prova_ripetibilita;
	private JPanel panel_prova_decentramento;
	private JPanel panel_prova_linearita;
	private JPanel panel_prova_accuratezza;
	private JPanel panel_prova_mobilita;
	JSplitPane splitPane;


	VerStrumentoDTO strumento;
	
	private BufferedImage img;
	static JFrame myFrame=null;
	JTable table_dati_strumento=null;
	JTable tabellaSecurTest=null;
	JFrame f;
	ArrayList<ButtonGroup> listaRisposte = new ArrayList<>();
	private JTable tableRip,tableDec,tableLin,tableAcc,tabellaMobilita1,tabellaMobilita2;
	
	ModelRipetibilita modelRip;
	ModelDecentramento modelDec;
	ModelLinearita modelLin;
	ModelAccuratezza modelAccuratezza;
	ModelMobilita modelMobilita_1;
	ModelMobilita modelMobilita_2;
	
	private JTextField textField_p_p_rip;
	private JTextField textField_mpe_rip;
	private JTextField textField_esito_rip;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_esito_controllo_preliminare;
	
	public PannelloMisuraMaster(String id) throws Exception
	{
		SessionBO.prevPage="PSS";
		current=-1;
		strumento=GestioneStrumentoVER_BO.getStrumento(SessionBO.idStrumento);


		myFrame=SessionBO.generarFrame;
		

		this.setLayout(new MigLayout("","[grow]","[grow]"));

		JPanel masterPanel = new JPanel();
		
		masterPanel.setLayout(new MigLayout("", "[grow]", "[][70%,grow][15%]"));


		panel_tabella = costruisciTabella();
		panel_prova_ripetibilita=costruisciPanelloRipetibilita();
		panel_prova_decentramento=costruisciPanelloDecentramento();
		panel_prova_linearita=costruisciPannelloLinearita();
		panel_prova_accuratezza= costruisciPannelloAccuratezza();
		panel_prova_mobilita=costruisciPannelloMobilita();
		
		
		panel_struttura =creaPanelStruttura();
		
		JLabel lblCampo = new JLabel("Campo");
		lblCampo.setFont(new Font("Arial", Font.BOLD, 14));
		masterPanel.add(lblCampo, "flowx,cell 0 0");
		

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		tabbedPane.addTab("Controllo preliminare",panel_tabella);
		tabbedPane.addTab("Prova Ripetibilità", panel_prova_ripetibilita);
		tabbedPane.addTab("Prova Decentramento", panel_prova_decentramento);
		tabbedPane.addTab("Prova Linearità", panel_prova_linearita);
		tabbedPane.addTab("Prova Accuratezza", panel_prova_accuratezza);
		tabbedPane.addTab("Prova Mobilità", panel_prova_mobilita);
		
		masterPanel.add(tabbedPane, "cell 0 1,grow");
		masterPanel.add(panel_struttura,"cell 0 2,grow");


     	double height=(SessionBO.heightFrame*73)/100;
		double width=(SessionBO.widthFrame*70)/100;
		masterPanel.setPreferredSize(new Dimension((int)width-50,(int) height/2));

		JScrollPane scroll= new JScrollPane(masterPanel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3"}));
		comboBox.setFont(new Font("Arial", Font.BOLD, 14));
		masterPanel.add(comboBox, "cell 0 0");

		this.add(scroll, "cell 0 0,grow");

	}


	private JPanel costruisciPanelloRipetibilita() {
		
		JPanel pannelloRipetibilita= new JPanel();
		
		pannelloRipetibilita.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 2, true), "Prova Ripetibilità", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pannelloRipetibilita.setBackground(Color.WHITE);
		pannelloRipetibilita.setLayout(new MigLayout("", "[22.00][][grow]", "[][][][][][][][13.00][][13.00][]"));
		
		tableRip = new JTable();
		
		modelRip = new ModelRipetibilita(strumento.getUm());
		
		tableRip.setModel(modelRip);
		
		tableRip.setRowHeight(25);
		tableRip.setFont(new Font("Arial", Font.PLAIN, 12));
		tableRip.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
		
		tableRip.setDefaultRenderer(Object.class, new MyCellRenderer());
		
		
		if (strumento.getClasse()==1 || strumento.getClasse()==2) 
		{
			for (int i = 0; i < 6; i++) {
				
				modelRip.addRow(new Object[0]);
				modelRip.setValueAt(i+1, i, 0);
			}
		}
		else 
		{	
				for (int i = 0; i < 3; i++) {
				
					modelRip.addRow(new Object[0]);
					modelRip.setValueAt(i+1, i, 0);
				}
		}
		
		JLabel lblNewLabel = new JLabel("Prova di Ripetibilit\u00E0");
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		pannelloRipetibilita.add(lblNewLabel, "cell 0 0 2 1");
		
		JLabel lblNewLabel_2 = new JLabel("(Rif.UNI CEI EN 45501:2015: A.4.10)");
		lblNewLabel_2.setFont(new Font("Calibri", Font.PLAIN, 14));
		pannelloRipetibilita.add(lblNewLabel_2, "cell 2 0");
		
		
		JScrollPane scrollTab = new JScrollPane(tableRip);
		
		pannelloRipetibilita.add(scrollTab, "cell 1 3 2 1,width :750:,alignx left,height :200:,aligny top");
		
		JLabel lblNewLabel_3 = new JLabel("Note: 3 ripetizioni per Bilance di Classe III e IIII, 6 ripetizioni per bilance di Classe I e II");
		lblNewLabel_3.setForeground(Color.RED);
		lblNewLabel_3.setFont(new Font("Arial", Font.BOLD, 12));
		pannelloRipetibilita.add(lblNewLabel_3, "cell 1 4 2 1");
		
		JLabel lblPmaxpmin = new JLabel("Pmax-Pmin:");
		lblPmaxpmin.setFont(new Font("Arial", Font.BOLD, 12));
		pannelloRipetibilita.add(lblPmaxpmin, "cell 1 6,alignx trailing");
		
		textField_p_p_rip = new JTextField();
		textField_p_p_rip.setFont(new Font("Arial", Font.PLAIN, 12));
		pannelloRipetibilita.add(textField_p_p_rip, "cell 2 6,width :100:");
		textField_p_p_rip.setColumns(10);
		
		JLabel lblMpe = new JLabel("MPE :");
		lblMpe.setFont(new Font("Arial", Font.BOLD, 12));
		pannelloRipetibilita.add(lblMpe, "cell 1 8,alignx trailing");
		
		textField_mpe_rip = new JTextField();
		textField_mpe_rip.setFont(new Font("Arial", Font.PLAIN, 12));
		textField_mpe_rip.setColumns(10);
		pannelloRipetibilita.add(textField_mpe_rip, "cell 2 8,width :100:");
		
		JLabel lblEsito = new JLabel("ESITO:");
		lblEsito.setFont(new Font("Arial", Font.BOLD, 12));
		pannelloRipetibilita.add(lblEsito, "cell 1 10,alignx trailing");
		
		textField_esito_rip = new JTextField();
		textField_esito_rip.setBackground(Color.YELLOW);
		textField_esito_rip.setFont(new Font("Arial", Font.PLAIN, 12));
		textField_esito_rip.setColumns(10);
		pannelloRipetibilita.add(textField_esito_rip, "cell 2 10,width :100:");

		return pannelloRipetibilita;
	}
	
	private JPanel costruisciPanelloDecentramento() {
		JPanel pannelloDecentramento= new JPanel();
		
		pannelloDecentramento.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 2, true), "Prova Ripetibilità", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pannelloDecentramento.setBackground(Color.WHITE);
		pannelloDecentramento.setLayout(new MigLayout("", "[22.00][][160.00][160][160][]", "[][][][][][][][]"));
		
		tableDec = new JTable();
		
		modelDec = new ModelDecentramento(strumento.getUm());
		
		tableDec.setModel(modelDec);
		
		tableDec.setRowHeight(17);
		tableDec.setFont(new Font("Arial", Font.PLAIN, 12));
		tableDec.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
		
		tableDec.setDefaultRenderer(Object.class, new MyCellRenderer());
		
		int indice=1;
		
		for (int i = 0; i < 10; i=i+2) {
			
			modelDec.addRow(new Object[0]);
			modelDec.setValueAt("E0", i, 0);
			modelDec.addRow(new Object[0]);
			modelDec.setValueAt(indice, i+1, 0);
			indice++;
		}

		
		JLabel lblNewLabel = new JLabel("Prova di Ripetibilit\u00E0");
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		pannelloDecentramento.add(lblNewLabel, "cell 0 0 6 1");
		
		JLabel lblNewLabel_2 = new JLabel("(Rif.UNI CEI EN 45501:2015: A.4.7)");
		lblNewLabel_2.setFont(new Font("Calibri", Font.PLAIN, 14));
		pannelloDecentramento.add(lblNewLabel_2, "cell 0 0");
		
		JLabel lblEsempiDiTipici = new JLabel("Esempi di tipici ricettori di carico");
		lblEsempiDiTipici.setFont(new Font("Arial", Font.BOLD, 12));
		pannelloDecentramento.add(lblEsempiDiTipici, "cell 0 1 5 1,alignx center");
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(new ImageIcon(PannelloMisuraMaster.class.getResource("/image/bil_qua.png")));
		pannelloDecentramento.add(lblNewLabel_4, "cell 2 2,alignx center");
		
		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setIcon(new ImageIcon(PannelloMisuraMaster.class.getResource("/image/bil_cer.png")));
		pannelloDecentramento.add(lblNewLabel_5, "cell 3 2,alignx center");
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(PannelloMisuraMaster.class.getResource("/image/bil_tri.png")));
		pannelloDecentramento.add(label, "cell 4 2,alignx center");
		
		JRadioButton rdbtn_quad = new JRadioButton("");
		rdbtn_quad.setBackground(Color.WHITE);
		pannelloDecentramento.add(rdbtn_quad, "cell 2 3,alignx center");
		
		JRadioButton rdbtn_cer = new JRadioButton("");
		rdbtn_cer.setBackground(Color.WHITE);
		pannelloDecentramento.add(rdbtn_cer, "cell 3 3,alignx center");
		
		JRadioButton rdbtn_tri = new JRadioButton("");
		rdbtn_tri.setBackground(Color.WHITE);
		pannelloDecentramento.add(rdbtn_tri, "cell 4 3,alignx center");
		
		JLabel lblNewLabel_6 = new JLabel("Numero di punti di appoggi del ricettore di carico:");
		lblNewLabel_6.setFont(new Font("Arial", Font.BOLD, 12));
		pannelloDecentramento.add(lblNewLabel_6, "cell 1 4 3 1,alignx left");
		
		JLabel lblCarico = new JLabel("Carico");
		lblCarico.setFont(new Font("Arial", Font.BOLD, 12));
		pannelloDecentramento.add(lblCarico, "flowx,cell 4 4,alignx right");
		
		JLabel lblStrumentoSpeciale = new JLabel("Strumento speciale");
		lblStrumentoSpeciale.setFont(new Font("Arial", Font.BOLD, 12));
		pannelloDecentramento.add(lblStrumentoSpeciale, "flowx,cell 5 4,alignx right");
		
		
		JScrollPane scrollTab = new JScrollPane(tableDec);
		
		pannelloDecentramento.add(scrollTab, "cell 1 5 5 1,width :750:,alignx left,height :200:,aligny top");
		
		JLabel lblEsito = new JLabel("ESITO:");
		lblEsito.setFont(new Font("Arial", Font.BOLD, 12));
		pannelloDecentramento.add(lblEsito, "cell 1 7,alignx trailing");
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		pannelloDecentramento.add(textField_1, "cell 4 4,width :50:50,alignx right");
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"SI", "NO"}));
		pannelloDecentramento.add(comboBox, "cell 5 4,alignx right");
		
		textField = new JTextField();
		pannelloDecentramento.add(textField, "cell 1 4,width :50:50");
		textField.setColumns(10);
		
		textField_esito_rip = new JTextField();
		textField_esito_rip.setBackground(Color.YELLOW);
		textField_esito_rip.setFont(new Font("Arial", Font.PLAIN, 12));
		textField_esito_rip.setColumns(10);
		pannelloDecentramento.add(textField_esito_rip, "cell 2 7,width :100:");

		ButtonGroup bg=new ButtonGroup();
		bg.add(rdbtn_quad);
		bg.add(rdbtn_cer);
		bg.add(rdbtn_tri);
		
		return pannelloDecentramento;
	}
	
	

	private JPanel costruisciPannelloLinearita() {
		
		JPanel pannelloLinearita= new JPanel();
		
		pannelloLinearita.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 2, true), "Prova Ripetibilità", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pannelloLinearita.setBackground(Color.WHITE);
		pannelloLinearita.setLayout(new MigLayout("", "[22.00][][grow]", "[][][][][][][][][13.00][][13.00][]"));
		
		tableLin = new JTable();
		
		modelLin = new ModelLinearita(strumento.getUm());
		
		tableLin.setModel(modelLin);
		
		tableLin.setRowHeight(25);
		tableLin.setFont(new Font("Arial", Font.PLAIN, 12));
		tableLin.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
		
		tableLin.setDefaultRenderer(Object.class, new MyCellRenderer());
		
		for (int i = 0; i <=6; i++) {
			
			modelLin.addRow(new Object[0]);
			
			if(i==0)
			{
				modelLin.setValueAt("E0", i, 0);
			}else
			{
				modelLin.setValueAt(i, i, 0);
			}
		}
		
		
		JLabel lblNewLabel = new JLabel("Prova di Ripetibilit\u00E0");
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		pannelloLinearita.add(lblNewLabel, "cell 0 0 2 1");
		
		JLabel lblNewLabel_2 = new JLabel("(Rif.UNI CEI EN 45501:2015: A.4.4.1 - A.4.2.3)");
		lblNewLabel_2.setFont(new Font("Calibri", Font.PLAIN, 14));
		pannelloLinearita.add(lblNewLabel_2, "cell 2 0");
		
		JLabel lblTipoDispositivoDi = new JLabel("Tipo dispositivo di azzeramento");
		lblTipoDispositivoDi.setFont(new Font("Arial", Font.BOLD, 12));
		pannelloLinearita.add(lblTipoDispositivoDi, "cell 1 2,alignx trailing");
		
		JComboBox comboBox_tipo_azzeramento = new JComboBox();
		comboBox_tipo_azzeramento.setModel(new DefaultComboBoxModel(new String[] {"Automatico", "Non automatico o semiautomatico"}));
		comboBox_tipo_azzeramento.setFont(new Font("Arial", Font.PLAIN, 12));
		pannelloLinearita.add(comboBox_tipo_azzeramento, "cell 2 2");
		
		
		JScrollPane scrollTab = new JScrollPane(tableLin);
		
		pannelloLinearita.add(scrollTab, "cell 1 4 2 1,growx,alignx left,height :200:,aligny top");
		

		JLabel lblEsito = new JLabel("ESITO:");
		lblEsito.setFont(new Font("Arial", Font.BOLD, 12));
		pannelloLinearita.add(lblEsito, "cell 1 6,alignx left");
		
		textField_esito_rip = new JTextField();
		textField_esito_rip.setBackground(Color.YELLOW);
		textField_esito_rip.setFont(new Font("Arial", Font.PLAIN, 12));
		textField_esito_rip.setColumns(10);
		pannelloLinearita.add(textField_esito_rip, "cell 1 6,width :100:");

		return pannelloLinearita;
	}
	

	private JPanel costruisciPannelloAccuratezza() {
	
		JPanel pannelloAccuratezza= new JPanel();
		
		pannelloAccuratezza.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 2, true), "Prova Ripetibilità", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pannelloAccuratezza.setBackground(Color.WHITE);
		pannelloAccuratezza.setLayout(new MigLayout("", "[22.00][][grow]", "[][][][][][][][][13.00][][13.00][]"));
		
		tableAcc = new JTable();
		
		modelAccuratezza = new ModelAccuratezza(strumento.getUm());
		
		tableAcc.setModel(modelAccuratezza);
		
		tableAcc.setRowHeight(25);
		tableAcc.setFont(new Font("Arial", Font.PLAIN, 12));
		tableAcc.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
		
		tableAcc.setDefaultRenderer(Object.class, new MyCellRenderer());
		
		modelAccuratezza.addRow(new Object[0]);
		modelAccuratezza.setValueAt("Et", 0, 0);
		
		
		
		JLabel lblNewLabel = new JLabel("Prova di accuratezza del dispositivo di tara");
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		pannelloAccuratezza.add(lblNewLabel, "cell 0 0 3 1");
		
		JLabel lblNewLabel_2 = new JLabel("(Rif.UNI CEI EN 45501:2015: A.4.6.1 - A.4.6.2)");
		lblNewLabel_2.setFont(new Font("Calibri", Font.PLAIN, 14));
		pannelloAccuratezza.add(lblNewLabel_2, "cell 0 0");
		
		JLabel lblTipoDispositivoDi = new JLabel("Tipo dispositivo di tara");
		lblTipoDispositivoDi.setFont(new Font("Arial", Font.BOLD, 12));
		pannelloAccuratezza.add(lblTipoDispositivoDi, "cell 1 2,alignx trailing");
		
		JComboBox comboBox_tipo_azzeramento = new JComboBox();
		comboBox_tipo_azzeramento.setModel(new DefaultComboBoxModel(new String[] {"Automatico", "Non automatico o semiautomatico"}));
		comboBox_tipo_azzeramento.setFont(new Font("Arial", Font.PLAIN, 12));
		pannelloAccuratezza.add(comboBox_tipo_azzeramento, "cell 2 2");
		
		
		JScrollPane scrollTab = new JScrollPane(tableAcc);
		
		pannelloAccuratezza.add(scrollTab, "cell 1 4 2 1,width :800:,height :75:,aligny top");
		

		JLabel lblEsito = new JLabel("ESITO:");
		lblEsito.setFont(new Font("Arial", Font.BOLD, 12));
		pannelloAccuratezza.add(lblEsito, "cell 1 6,alignx left");
		
		textField_esito_rip = new JTextField();
		textField_esito_rip.setBackground(Color.YELLOW);
		textField_esito_rip.setFont(new Font("Arial", Font.PLAIN, 12));
		textField_esito_rip.setColumns(10);
		pannelloAccuratezza.add(textField_esito_rip, "cell 1 6,width :100:");

		return pannelloAccuratezza;
	}

	private JPanel costruisciPannelloMobilita() {
	
		JPanel pannelloMobilita= new JPanel();
		
		pannelloMobilita.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 2, true), "Prova Ripetibilità", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pannelloMobilita.setBackground(Color.WHITE);
		pannelloMobilita.setLayout(new MigLayout("", "[22.00][][grow]", "[][][][][][][][][13.00][][13.00][]"));
		
		modelMobilita_1 = new ModelMobilita(strumento.getUm());
		
		modelMobilita_2 = new ModelMobilita(strumento.getUm());
		
		JLabel lblNewLabel = new JLabel("Prova di mobilità");
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		pannelloMobilita.add(lblNewLabel, "cell 0 0 3 1");
		
		JLabel lblNewLabel_2 = new JLabel("(Rif.UNI CEI EN 45501:2015: A.4.8)");
		lblNewLabel_2.setFont(new Font("Calibri", Font.PLAIN, 14));
		pannelloMobilita.add(lblNewLabel_2, "cell 0 0 3 1");
		
		tabellaMobilita1 = new JTable();
		
		tabellaMobilita1.setModel(modelMobilita_1);
		
		tabellaMobilita1.setRowHeight(25);
		tabellaMobilita1.setFont(new Font("Arial", Font.PLAIN, 12));
		tabellaMobilita1.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
		
		tabellaMobilita1.setDefaultRenderer(Object.class, new MyCellRenderer());
		
		JLabel lblCaso = new JLabel("Caso 1) - Strumenti ad equilibrio non automatico (con indicazione analogica)");
		lblCaso.setFont(new Font("Arial", Font.BOLD, 12));
		pannelloMobilita.add(lblCaso, "cell 1 2");
		

		JScrollPane scrollTab = new JScrollPane(tabellaMobilita1);
		
		pannelloMobilita.add(scrollTab, "cell 1 3,width :800:,height :100:,aligny top");
		tabellaMobilita2 = new JTable();
		tabellaMobilita2.setModel(modelMobilita_2);
		
		tabellaMobilita2.setRowHeight(25);
		tabellaMobilita2.setFont(new Font("Arial", Font.PLAIN, 12));
		tabellaMobilita2.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
		tabellaMobilita2.setDefaultRenderer(Object.class, new MyCellRenderer());
		
		JLabel lblCaso_1 = new JLabel("Caso 2) - Strumenti ad equilibrio automatico o semiautomatico (con indicazione analogica)");
		lblCaso_1.setFont(new Font("Arial", Font.BOLD, 12));
		pannelloMobilita.add(lblCaso_1, "cell 1 5");
		JScrollPane scrollTab2 = new JScrollPane(tabellaMobilita2);
		
		pannelloMobilita.add(scrollTab2, "cell 1 6,width :800:,height :100:,aligny top");
		

		JLabel lblEsito = new JLabel("ESITO:");
		lblEsito.setFont(new Font("Arial", Font.BOLD, 12));
		pannelloMobilita.add(lblEsito, "flowx,cell 1 8,alignx left");
		
		textField_esito_rip = new JTextField();
		textField_esito_rip.setBackground(Color.YELLOW);
		textField_esito_rip.setFont(new Font("Arial", Font.PLAIN, 12));
		textField_esito_rip.setColumns(10);
		pannelloMobilita.add(textField_esito_rip, "cell 1 8,width :100:");

		return pannelloMobilita;
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
	
		this.setBounds(0, 0, myFrame.getWidth()-32, myFrame.getHeight()-272);
		g.drawImage(img, 5, 5, myFrame.getWidth()-32, myFrame.getHeight()-272,this);


	}
	
	

	private JPanel costruisciTabella() {


		JPanel panel_tabella = new JPanel();
		panel_tabella.setBorder(new TitledBorder(new LineBorder(new Color(215, 23, 29), 2, true), "Tabella Misura", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(215, 23, 29)));
		panel_tabella.setBackground(Color.WHITE);
		panel_tabella.setLayout(new MigLayout("", "[grow][][]", "[][][400.00][45.00]"));

		
		JLabel lblNewLabel_1 = new JLabel("CHECK LIST CONTROLLO PRELIMINARE");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 16));
		panel_tabella.add(lblNewLabel_1, "cell 0 0,alignx center");

		JPanel pannelloDomande = new JPanel();
		pannelloDomande.setLayout(null);
		
		ArrayList<String> listaDomande=GestioneMisuraBO.getListaDomandeControlloPreliminare();
		
		int y=10;
		
		pannelloDomande.setPreferredSize(new Dimension(1200,35*listaDomande.size()));
		
		for (int i=0;i<listaDomande.size();i++) 
		{
			Font f = new Font("Arial",Font.ITALIC, 14);
			
			String domanda =listaDomande.get(i);
			y=i*30;
			
			JLabel lab1= new JLabel(domanda);
			lab1.setBounds(10, y, 900, 25);
			lab1.setFont(f);
			
			JRadioButton si = new JRadioButton("SI");
			JRadioButton no = new JRadioButton("NO");
			JRadioButton na = new JRadioButton("N/A");
			
			if(i>8) 
			{
				na.setSelected(true);
			}
			
			si.setBounds(910, y, 50, 25);
			no.setBounds(960, y, 50, 25);
			na.setBounds(1010, y, 50, 25);
			
			si.setFont(f);
			no.setFont(f);
			na.setFont(f);
			ButtonGroup bg = new ButtonGroup();
			  
			    bg.add(si);
			    bg.add(no);
			    bg.add(na);
			    
			  listaRisposte.add(bg);
			  
			  pannelloDomande.add(lab1);
			  pannelloDomande.add(si);
			  pannelloDomande.add(no);
			  pannelloDomande.add(na);
		}
		
		JScrollPane scroll = new JScrollPane(pannelloDomande);
		panel_tabella.add(scroll,"cell 0 2 3 1,grow");
		
		JLabel lblControlloPreliminare = new JLabel("Controllo preliminare");
		lblControlloPreliminare.setForeground(Color.BLACK);
		lblControlloPreliminare.setFont(new Font("Arial", Font.BOLD, 14));
		panel_tabella.add(lblControlloPreliminare, "flowx,cell 0 3");
		
		textField_esito_controllo_preliminare = new JTextField();
		textField_esito_controllo_preliminare.setEditable(false);
		textField_esito_controllo_preliminare.setFont(new Font("Arial", Font.BOLD, 14));
		panel_tabella.add(textField_esito_controllo_preliminare, "cell 0 3");
		textField_esito_controllo_preliminare.setColumns(10);
		
		return panel_tabella;

	}




	private Object toString(String text) {
	
		if(text ==null || text.length()==0) 
		{
			return "-";
		}
		return text;
	}


	private Double getNumber(String test) {
	try {
		Pattern p = Pattern.compile("(-?[0-9]+(?:[,.][0-9]+)?)");
		Matcher m = p.matcher(test);
		while (m.find()) {
		  return Double.parseDouble(m.group());
		}
	}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	


	

	private JPanel creaPanelStruttura() {

		final JPanel panel_m_build = new JPanel();
		panel_m_build.setBorder(new TitledBorder(new LineBorder(Costanti.COLOR_RED, 2, true), "Termina Misura", TitledBorder.LEADING, TitledBorder.TOP, null, Costanti.COLOR_RED));
		panel_m_build.setBackground(Color.white);
		panel_m_build.setLayout(new MigLayout("", "[50%][grow]", "[grow]"));


		JButton btnStampa = new JButton("Salva");
		panel_m_build.add(btnStampa, "cell 0 0,alignx center,height : 50:");
		btnStampa.setIcon(new ImageIcon(PannelloMisuraMaster.class.getResource("/image/save.png")));
		btnStampa.setFont(new Font("Arial", Font.BOLD, 16));



		btnStampa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try
				{
					boolean save=true;
					
					String sequence="";
					for (ButtonGroup item : listaRisposte) {
						
						String singleDecision="";
						 
						for (Enumeration<AbstractButton> buttons = item.getElements(); buttons.hasMoreElements();) {
					            AbstractButton button = buttons.nextElement();

					            if (button.isSelected()) {
					                if(button.getText().equals("SI"))
					                {
					                	singleDecision="0";
					                }
					                if (button.getText().equals("NO"))
					                {
					                	singleDecision="1";
					                }
					               if (button.getText().equals("N/A"))
					                {
					                	singleDecision="2";
					                }
					                	
					                }
					            }
							
								if(singleDecision.length()>0) 
								{
									sequence=sequence.concat(singleDecision)+";";
								}else 
								{
									JOptionPane.showMessageDialog(null,"Rispondere a tutte le domande","Attenzione",JOptionPane.WARNING_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
									save=false;
									break;
								}
								
					        }
					
					if(save) {
							boolean esito = getEsitoControlloPreliminare(sequence.substring(0, sequence.length()-1));
							
							if(esito==true) 
							{
								textField_esito_controllo_preliminare.setText("SUPERATO");
								textField_esito_controllo_preliminare.setForeground(Color.green);
							}
							else 
							{
								textField_esito_controllo_preliminare.setText("NON SUPERATO");
								textField_esito_controllo_preliminare.setForeground(Color.red);
							}
							
							GestioneMisuraBO.saveControlloPreliminare(SessionBO.idMisura,sequence.substring(0, sequence.length()-1));
					}
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}

			private boolean getEsitoControlloPreliminare(String sequence) {
				
				String[] list =sequence.split(";");
				
				
				for (String decision : list) {
					
					if(decision.equals("1")) 
					{
						return false;
					}
				}
				return true;
			}

			
	


		});

		final JButton btnChiudiMisura = new JButton("Termina Misura");
		btnChiudiMisura.setIcon(new ImageIcon(PannelloTOP.class.getResource("/image/insert.png")));
		btnChiudiMisura.setFont(new Font("Arial", Font.BOLD, 16));
		panel_m_build.add(btnChiudiMisura, "cell 1 0,alignx center,height : 50:");

		btnChiudiMisura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 

			{

				int scelta=	JOptionPane.showConfirmDialog(null,"Vuoi terminare la misura ?","Salvataggio",JOptionPane.YES_NO_OPTION,0,new ImageIcon(PannelloTOP.class.getResource("/image/question.png")));

				if(scelta==0)
				{

					if(true)
					{
						try 
						{
					//		GestioneMisuraBO.terminaMisura(SessionBO.idStrumento,textField_classe.getText());
							

							JOptionPane.showMessageDialog(null,"Salvataggio effettuato","Salvataggio",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/confirm.png")));


							
						} catch (Exception e) {
							e.printStackTrace();
						}
						

					}else
					{
						JOptionPane.showMessageDialog(null,"Per terminare la misura, tutti i punti devono essere valorizzati","Salvataggio",JOptionPane.ERROR_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
					}
				}

			}

		});




		return panel_m_build;

	}
	
	class ModelRipetibilita extends DefaultTableModel {


		public ModelRipetibilita(String um) 
		{
			addColumn("N° Ripetizioni");
			addColumn("Massa L ("+um+")");
			addColumn("Indicazione I ("+um+")");
			addColumn("Carico Agg.  ΔL ("+um+")");
			addColumn("P ("+um+")");

		}
		@Override
		public Class<?> getColumnClass(int column) {
			switch (column) {
			case 0:
				return String.class;
			case 1:
				return String.class;
			case 2:
				return String.class;
			case 3:
				return String.class;
			case 4:
				return String.class;
			default:
				return String.class;
			}
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			
			if(column>2 && column<5)
			{
				return true;
			}else
			{
				return true;
			}
		}


	}
	
	class ModelDecentramento extends DefaultTableModel {


		public ModelDecentramento(String um) 
		{
			addColumn("Posizione n°");
			addColumn("Massa L ("+um+")");
			addColumn("Indicazione I ("+um+")");
			addColumn("Carico Agg.  ΔL ("+um+")");
			addColumn("Errore E ("+um+")");
			addColumn("Er. corretto Ec ("+um+")");
			addColumn("MPE(±) ("+um+")");

		}
		@Override
		public Class<?> getColumnClass(int column) {
			switch (column) {
			case 0:
				return String.class;
			case 1:
				return String.class;
			case 2:
				return String.class;
			case 3:
				return String.class;
			case 4:
				return String.class;
			case 5:
				return String.class;
			case 6:
				return String.class;
			default:
				return String.class;
			}
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			
			if(column>2 && column<5)
			{
				return true;
			}else
			{
				return true;
			}
		}


	}
	
	class ModelLinearita extends DefaultTableModel {


		public ModelLinearita(String um) 
		{
			addColumn("Rif. n°");
			addColumn("Massa L ("+um+")");
			addColumn("↑ Indicazione I ("+um+")");
			addColumn("↓ Indicazione I ("+um+")");
			addColumn("↑ Carico Agg.  ΔL ("+um+")");
			addColumn("↓ Carico Agg.  ΔL ("+um+")");
			addColumn("↑ Errore E ("+um+")");
			addColumn("↓ Errore E ("+um+")");
			addColumn("↑ Er. corretto Ec ("+um+")");
			addColumn("↓ Er. corretto Ec ("+um+")");
			addColumn("MPE(±) ("+um+")");

		}
		@Override
		public Class<?> getColumnClass(int column) {
			switch (column) {
			case 0:
				return String.class;
			case 1:
				return String.class;
			case 2:
				return String.class;
			case 3:
				return String.class;
			case 4:
				return String.class;
			case 5:
				return String.class;
			case 6:
				return String.class;
			case 7:
				return String.class;
			case 8:
				return String.class;
			case 9:
				return String.class;
			case 10:
				return String.class;
			default:
				return String.class;
			}
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			
			if(column>2 && column<5)
			{
				return true;
			}else
			{
				return true;
			}
		}


	}
	
	class ModelAccuratezza extends DefaultTableModel {


		public ModelAccuratezza(String um) 
		{
			addColumn("Rif.");
			addColumn("Massa L ("+um+")");
			addColumn("Indicazione I ("+um+")");
			addColumn("Carico Agg.  ΔL ("+um+")");
			addColumn("Errore E ("+um+")");
			addColumn("Er. corretto Ec ("+um+")");
			addColumn("MPE(±) ("+um+")");

		}
		@Override
		public Class<?> getColumnClass(int column) {
			switch (column) {
			case 0:
				return String.class;
			case 1:
				return String.class;
			case 2:
				return String.class;
			case 3:
				return String.class;
			case 4:
				return String.class;
			case 5:
				return String.class;
			case 6:
				return String.class;
			default:
				return String.class;
			}
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			
			if(column>2 && column<5)
			{
				return true;
			}else
			{
				return true;
			}
		}


	}
	
	class ModelMobilita extends DefaultTableModel {


		public ModelMobilita(String um) 
		{
			addColumn("Carico");
			addColumn("Massa L ("+um+")");
			addColumn("Indicazione L1 ("+um+")");
			addColumn("Carico Agg=0,4 . |MPE|  ΔL ("+um+")");
			addColumn("Indicazione L2 ("+um+")");
			addColumn("Differneza L2 - L1("+um+")");
			addColumn("Div. reale strumento d ("+um+")");
			addColumn("Check |L2-L1|≥ d");
			
			
			addRow(new Object[0]);
			addRow(new Object[0]);
			addRow(new Object[0]);
			
			setValueAt("Min", 0, 0);
			setValueAt("1/2 Max", 1, 0);
			setValueAt("Max", 2, 0);
		}
		@Override
		public Class<?> getColumnClass(int column) {
			switch (column) {
			case 0:
				return String.class;
			case 1:
				return String.class;
			case 2:
				return String.class;
			case 3:
				return String.class;
			case 4:
				return String.class;
			case 5:
				return String.class;
			case 6:
				return String.class;
			case 7:
				return String.class;
			default:
				return String.class;
			}
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			
			if(column>2 || column<5)
			{
				return true;
			}else
			{
				return true;
			}
		}


	}
	
public class MyCellRenderer extends javax.swing.table.DefaultTableCellRenderer {
		

        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, java.lang.Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        	final java.awt.Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

      
         
            if (row % 2 == 0) 
            {
                cellComponent.setForeground(Color.black);
                cellComponent.setBackground(Color.white);

            }
            else
            {
            	cellComponent.setForeground(Color.black);
                cellComponent.setBackground(new Color(224,224,224));
                
            }
			return cellComponent;
       
        } 


        }
}



package it.calverDesktopVER.gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import it.calverDesktopVER.bo.GestioneMisuraBO;
import it.calverDesktopVER.bo.GestioneStrumentoVER_BO;
import it.calverDesktopVER.bo.SessionBO;
import it.calverDesktopVER.dto.VerClassiDTO;
import it.calverDesktopVER.dto.VerDecentramentoDTO;
import it.calverDesktopVER.dto.VerLinearitaDTO;
import it.calverDesktopVER.dto.VerMisuraDTO;
import it.calverDesktopVER.dto.VerRipetibilitaDTO;
import it.calverDesktopVER.dto.VerStrumentoDTO;
import it.calverDesktopVER.utl.Costanti;
import net.miginfocom.swing.MigLayout;


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
	VerMisuraDTO misura;
	
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
	private JTextField textField_esito_mob;
	private JTextField textField_esito_acc;
	private JTextField textField_esito_lin;
	private JTextField textField_esito_dec;
	private JTextField textField_punti_appoggio;
	private JTextField textField_carico;
	private JTextField textField_esito_controllo_preliminare;
	JComboBox comboBox_campo;
	ArrayList<VerClassiDTO> listaClassi;
	public PannelloMisuraMaster(String id) throws Exception
	{
		SessionBO.prevPage="PSS";
		current=-1;
		
		strumento=GestioneStrumentoVER_BO.getStrumento(SessionBO.idStrumento);
		misura=GestioneMisuraBO.getMisura(SessionBO.idMisura);
		
		listaClassi=GestioneMisuraBO.getListaClassi(strumento.getClasse());
		
		myFrame=SessionBO.generarFrame;
		

		this.setLayout(new MigLayout("","[grow]","[grow]"));

		JPanel masterPanel = new JPanel();
		
		masterPanel.setLayout(new MigLayout("", "[grow]", "[][70%,grow][15%]"));


		comboBox_campo = new JComboBox();
		comboBox_campo.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3"}));
		comboBox_campo.setFont(new Font("Arial", Font.BOLD, 14));
		

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
		
		masterPanel.add(comboBox_campo, "cell 0 0");
		

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
		
		TableColumn column = tableRip.getColumnModel().getColumn(tableRip.getColumnModel().getColumnIndex("id"));
		tableRip.removeColumn(column);
		
		
		tableRip.setDefaultRenderer(Object.class, new MyCellRenderer());
		
		String massa="";
		
		ArrayList<VerRipetibilitaDTO> lista=(ArrayList)misura.getVerRipetibilitas();
	
				for (int i = 0; i < 6; i++) {
				
					VerRipetibilitaDTO ver=lista.get(i);
					modelRip.addRow(new Object[0]);
					modelRip.setValueAt(ver.getNumeroRipetizione(), i, 0);
					
					 massa =getMassa(comboBox_campo.getSelectedIndex());
					
					if(ver.getMassa()!=null) 
					{
						modelRip.setValueAt(ver.getMassa(), i, 1);
					}
					else 
					{
						modelRip.setValueAt(massa, i, 1);
					}
					
					if(ver.getIndicazione()!=null) 
					{
						modelRip.setValueAt(ver.getIndicazione(), i, 2);
					}
					
					if(ver.getCaricoAgg()!=null) 
					{
						modelRip.setValueAt(ver.getCaricoAgg(), i, 3);
					}
					
					if(ver.getPortata()!=null) 
					{
						modelRip.setValueAt(ver.getPortata(), i, 4);
					}
					modelRip.setValueAt(ver.getId(), i, 5);
					
					if (i==2 && (strumento.getClasse()==3 || strumento.getClasse()==4)) 
					{
						break;
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
		textField_p_p_rip.setEditable(false);
		textField_p_p_rip.setFont(new Font("Arial", Font.PLAIN, 12));
		pannelloRipetibilita.add(textField_p_p_rip, "cell 2 6,width :100:");
		textField_p_p_rip.setColumns(10);
		
		JLabel lblMpe = new JLabel("MPE :");
		lblMpe.setFont(new Font("Arial", Font.BOLD, 12));
		pannelloRipetibilita.add(lblMpe, "cell 1 8,alignx trailing");
		
		textField_mpe_rip = new JTextField();
		textField_mpe_rip.setEditable(false);
		textField_mpe_rip.setFont(new Font("Arial", Font.PLAIN, 12));
		textField_mpe_rip.setColumns(10);
		pannelloRipetibilita.add(textField_mpe_rip, "cell 2 8,width :100:");
		
		JLabel lblEsito = new JLabel("ESITO:");
		lblEsito.setFont(new Font("Arial", Font.BOLD, 12));
		pannelloRipetibilita.add(lblEsito, "cell 1 10,alignx trailing");
		
		textField_esito_rip = new JTextField();
		textField_esito_rip.setEditable(false);
		textField_esito_rip.setFont(new Font("Arial", Font.PLAIN, 12));
		textField_esito_rip.setColumns(10);
		pannelloRipetibilita.add(textField_esito_rip, "cell 2 10,width :100:");

		if(lista.get(0).getDeltaPortata()!=null) 
		{
			textField_p_p_rip.setText(lista.get(0).getDeltaPortata().toPlainString());
		}
		if(lista.get(0).getMpe()!=null) 
		{
			textField_mpe_rip.setText(lista.get(0).getMpe().toPlainString());
		}else 
		{
			textField_mpe_rip.setText(getMPE(massa,comboBox_campo.getSelectedIndex()));
		}
		if(lista.get(0).getEsito()!=null) 
		{
			if(lista.get(0).getEsito().equals("POSITIVO")) 
			{
				textField_esito_rip.setBackground(Color.GREEN);
			}else 
			{
				textField_esito_rip.setBackground(Color.RED);
			}
			textField_esito_rip.setText(lista.get(0).getEsito());
		}
		
		tableRip.getModel().addTableModelListener(new TableModelListener() {

			  public void tableChanged(TableModelEvent e) {
			   
					int row = e.getFirstRow();
					int column=e.getColumn();

					if(column==2 || column==3) 
					{
						
					
					Object indicazione=modelRip.getValueAt(row, 2);
					Object carico=modelRip.getValueAt(row, 3);
					
					BigDecimal err =getE(comboBox_campo.getSelectedIndex());
					try 
					{
					if(indicazione!=null && carico!=null) 
					{	
						BigDecimal ind=new BigDecimal(indicazione.toString());
						BigDecimal car=new BigDecimal(carico.toString());
						
						BigDecimal portata=ind.add(err.divide(new BigDecimal(2).setScale(4, RoundingMode.HALF_UP)).subtract(car));
						
						BigDecimal deltaPortata=getDeltaPorta(portata,row).setScale(4, RoundingMode.HALF_UP);
						
						textField_p_p_rip.setText(deltaPortata.toPlainString());
						
						String esito=controllaEsitoRipetibilita();
						
						if (esito.equals("POSITIVO"))
						{
							textField_esito_rip.setBackground(Color.GREEN);
						}
						else 
						{
							textField_esito_rip.setBackground(Color.RED);
						}
						textField_esito_rip.setText(esito);
						
						VerRipetibilitaDTO ripetibilita= new VerRipetibilitaDTO();
						ripetibilita.setId(Integer.parseInt(modelRip.getValueAt(row, 5).toString()));
						ripetibilita.setMassa(new BigDecimal(modelRip.getValueAt(row, 1).toString()));
						ripetibilita.setIndicazione(new BigDecimal(modelRip.getValueAt(row, 2).toString()));
						ripetibilita.setCaricoAgg(new BigDecimal(modelRip.getValueAt(row, 3).toString()));
						ripetibilita.setPortata(portata);
						ripetibilita.setDeltaPortata(deltaPortata);
						ripetibilita.setMpe(new BigDecimal(textField_mpe_rip.getText()));
						ripetibilita.setEsito(esito);
						
						ripetibilita.setCampo(comboBox_campo.getSelectedIndex()+1);
						GestioneMisuraBO.updateVerRipetibilita(ripetibilita,misura.getId());
						
						modelRip.setValueAt(portata.toPlainString(),row, 4);
						
					}
					
					
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			  }

			

		
			});
		
		return pannelloRipetibilita;
	}
	
	private BigDecimal getDeltaPorta(BigDecimal portataCorrente, int row) {
		BigDecimal deltaPortata=BigDecimal.ZERO;
		
		double min=0;
		double max=0;
		Object portata=null;
		 
		for (int i = 0; i < modelRip.getRowCount(); i++) {
			
			if(i==row) 
			{
				portata=portataCorrente;
			}
			else 
			{
				portata=modelRip.getValueAt(i, 4);
			}
			
			
			if(portata!=null) 
			{
				double port=Double.parseDouble(portata.toString());
				
				if(port>max) 
				{
					max=port;
				}
				
				if(min==0) 
				{
					min=port;
				}else 
				{
					if(port<min) 
					{
						min=port;
					}
				}
				
			}
			 
		}
		
		double sottr=max-min;
		deltaPortata=new BigDecimal(sottr);
		
		return deltaPortata;
	}

	private String controllaEsitoRipetibilita()throws Exception {
		
		if(textField_p_p_rip.getText().length()>0 && textField_mpe_rip.getText().length()>0) 
		{

			double mpe=Double.parseDouble(textField_mpe_rip.getText());
			
			double portaMinMax=Double.parseDouble(textField_p_p_rip.getText());
			
			if(mpe>=portaMinMax) 
			{
				return "POSITIVO";
			}
			else 
			{
				return "NEGATIVO";
			}
		}
		
		
		return "";
	}
	
	private BigDecimal getE(int campo)
	{
		
		BigDecimal e = BigDecimal.ZERO;
		
		if(campo==0) 
		{
			e=strumento.getDiv_ver_C1();
		}
		if(campo==1) 
		{
			e=strumento.getDiv_ver_C2();
		}
		if(campo==2) 
		{
			e=strumento.getDiv_ver_C3();
		}
		
		return e;
	}
	
	private String getMPE(String _massa, int campo) {
		
		BigDecimal massa=new BigDecimal(_massa);
		
		BigDecimal e=getE(campo);

		int pivot=massa.divide(e,RoundingMode.HALF_DOWN).intValue();
		
		BigDecimal errore=null;
		
		for (int y=0;y<listaClassi.size();y++) 
		{
			VerClassiDTO classe=listaClassi.get(y);
			
			if(classe.getErrore().compareTo(new BigDecimal("0.5"))==0) 
			{
				if(pivot>=classe.getLimiteInferiore() && pivot<=classe.getLimiteSuperiore()) 
				{
					errore=classe.getErrore();
				}
			}
			
			if(classe.getErrore().compareTo(new BigDecimal("1"))==0) 
			{
				if(pivot>classe.getLimiteInferiore() && pivot<=classe.getLimiteSuperiore()) 
				{
					errore=classe.getErrore();
				}
			}
			
			if(classe.getErrore().compareTo(new BigDecimal("1.5"))==0) 
			{
				if(strumento.getClasse()==1) 
				{
					if(pivot>classe.getLimiteSuperiore()) 
					{
						errore=classe.getErrore();
					}	
				}else 
				{
					if(pivot>classe.getLimiteInferiore() && pivot<=classe.getLimiteSuperiore()) 
					{
						errore=classe.getErrore();
					}
				}	
			}
		}
		
		
		BigDecimal mpe=errore.multiply(e).multiply(new BigDecimal(2));
		
		return mpe.toPlainString();
	}


	private String getMassa(int i) {
		
		if(i==0) 
		{
			BigDecimal z8=new BigDecimal("0.8");
			return strumento.getPortata_max_C1().setScale(1,RoundingMode.HALF_UP).multiply(z8).setScale(1,RoundingMode.HALF_UP).toPlainString();
		}
		if(i==1) 
		{
			BigDecimal z8=new BigDecimal("0.8");
			return strumento.getPortata_max_C2().setScale(1,RoundingMode.HALF_UP).multiply(z8).setScale(1,RoundingMode.HALF_UP).toPlainString();
		}
		if(i==2) 
		{
			BigDecimal z8=new BigDecimal("0.8");
			return strumento.getPortata_max_C3().setScale(1,RoundingMode.HALF_UP).multiply(z8).setScale(1,RoundingMode.HALF_UP).toPlainString();
		}
		
		return"";
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
		
		ArrayList<VerDecentramentoDTO> listaDecentramento=(ArrayList<VerDecentramentoDTO>)misura.getVerDecentramentos();
	
		TableColumn column = tableDec.getColumnModel().getColumn(tableDec.getColumnModel().getColumnIndex("id"));
		tableDec.removeColumn(column);
		
		int indice=1;
		
		for (int i = 0; i < listaDecentramento.size(); i=i+2) {
			
			VerDecentramentoDTO ver = listaDecentramento.get(i);
			modelDec.addRow(new Object[0]);
			modelDec.setValueAt("E0", i, 0);
			modelDec.addRow(new Object[0]);
			modelDec.setValueAt(indice, i+1, 0);

			if(ver.getMassa()!=null) 
			{
				modelDec.setValueAt(ver.getMassa(), i, 1);
			}
			if(ver.getIndicazione()!=null) 
			{
				modelDec.setValueAt(ver.getIndicazione(), i, 2);
			}
			if(ver.getCaricoAgg()!=null) 
			{
				modelDec.setValueAt(ver.getCaricoAgg(), i, 3);
			}
			if(ver.getErrore()!=null) 
			{
				modelDec.setValueAt(ver.getErrore(), i, 4);
			}
			if(ver.getErroreCor()!=null) 
			{
				modelDec.setValueAt(ver.getErroreCor(), i, 5);
			}
			if(ver.getMpe()!=null) 
			{
				modelDec.setValueAt(ver.getMpe(), i, 6);
			}
			
			ver=listaDecentramento.get(i+1);
			if(ver.getMassa()!=null) 
			{
				modelDec.setValueAt(ver.getMassa(), i+1, 1);
			}
			if(ver.getIndicazione()!=null) 
			{
				modelDec.setValueAt(ver.getIndicazione(), i+1, 2);
			}
			if(ver.getCaricoAgg()!=null) 
			{
				modelDec.setValueAt(ver.getCaricoAgg(), i+1, 3);
			}
			if(ver.getErrore()!=null) 
			{
				modelDec.setValueAt(ver.getErrore(), i+1, 4);
			}
			if(ver.getErroreCor()!=null) 
			{
				modelDec.setValueAt(ver.getErroreCor(), i+1, 5);
			}
			if(ver.getMpe()!=null) 
			{
				modelDec.setValueAt(ver.getMpe(), i+1, 6);
			}
			
			modelDec.setValueAt(listaDecentramento.get(i).getId(), i, 7);
			modelDec.setValueAt(listaDecentramento.get(i+1).getId(), i+1, 7);
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
		
		textField_carico = new JTextField();
		textField_carico.setEditable(false);
		textField_carico.setColumns(10);
		pannelloDecentramento.add(textField_carico, "cell 4 4,width :50:50,alignx right");
		
		final JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(comboBox.getSelectedItem().toString().equals("SI")) 
				{
					tableDec.setDefaultRenderer(Object.class, new MyRendererDecentramentoSpecial());
					repaint();
				}else 
				{
					tableDec.setDefaultRenderer(Object.class, new MyRendererDecentramentoNormal());
					repaint();
				}
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"NO\t", "SI"}));
		pannelloDecentramento.add(comboBox, "cell 5 4,alignx right");
		
		textField_punti_appoggio = new JTextField();
		pannelloDecentramento.add(textField_punti_appoggio, "cell 1 4,width :50:50");
		textField_punti_appoggio.setColumns(10);
		
		textField_punti_appoggio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				textField_carico.setText(getCaricoDecentramento(Integer.parseInt(textField_punti_appoggio.getText()),comboBox_campo.getSelectedIndex()));
				
				BigDecimal err =getE(comboBox_campo.getSelectedIndex());
				
				for (int i = 0; i < modelDec.getRowCount(); i++) 
				{
					if(i==0) 
					{
						modelDec.setValueAt(err.multiply(BigDecimal.TEN).toPlainString(),i, 1);
					}else 
					{
						if(i%2!=0) 
						{
							modelDec.setValueAt(textField_carico.getText(),i, 1);
						}else 
						{
							if(comboBox.getSelectedItem().toString().equals("SI")) 
							{
								modelDec.setValueAt(err.multiply(BigDecimal.TEN).toPlainString(),i, 1);
							}else 
							{
								modelDec.setValueAt("",i, 1);
							}
						}
					}
				}
			}
		});
		
		
		textField_esito_dec = new JTextField();
		textField_esito_dec.setEditable(false);
		textField_esito_dec.setBackground(Color.YELLOW);
		textField_esito_dec.setFont(new Font("Arial", Font.PLAIN, 12));
		textField_esito_dec.setColumns(10);
		pannelloDecentramento.add(textField_esito_dec, "cell 2 7,width :100:");

		final ButtonGroup bg=new ButtonGroup();
		bg.add(rdbtn_quad);
		bg.add(rdbtn_cer);
		bg.add(rdbtn_tri);
		
		if(listaDecentramento.get(0).getPuntiAppoggio()!=0) 
		{
			textField_punti_appoggio.setText(""+listaDecentramento.get(0).getPuntiAppoggio());
			textField_carico.setText(getCaricoDecentramento(listaDecentramento.get(0).getPuntiAppoggio(),comboBox_campo.getSelectedIndex()));
		}
		
		comboBox.setSelectedIndex(0);
		
		
		if(listaDecentramento.get(0).getEsito()!=null) 
		{
			if(listaDecentramento.get(0).getEsito().equals("POSITIVO")) 
			{
				textField_esito_dec.setBackground(Color.GREEN);
			}else 
			{
				textField_esito_dec.setBackground(Color.RED);
			}
			textField_esito_dec.setText(listaDecentramento.get(0).getEsito());
		}
		
	
		
		tableDec.getModel().addTableModelListener(new TableModelListener() {

			  public void tableChanged(TableModelEvent e) {
			   
					int row = e.getFirstRow();
					int column=e.getColumn();

					if(column==1 || column==2 || column==3) 
					{
						
						Object massa=modelDec.getValueAt(row, 1);
						Object indicazione=modelDec.getValueAt(row, 2);
						Object carico=modelDec.getValueAt(row, 3);
						
						BigDecimal err =getE(comboBox_campo.getSelectedIndex());
						
						try 
						{
							if(indicazione!=null && carico!=null) 
							{	
								BigDecimal mas=new BigDecimal(massa.toString());
								BigDecimal ind=new BigDecimal(indicazione.toString());
								BigDecimal car=new BigDecimal(carico.toString());
							
								BigDecimal errore=getErrore(mas,ind,err,car);
								BigDecimal errore_corr=null;
								
								if(row==0) 
								{
									errore_corr=BigDecimal.ZERO;
								}else 
								{
									if(modelDec.getValueAt(0, 4)!=null) 
									{
										BigDecimal  errGen=new BigDecimal(modelDec.getValueAt(0, 4).toString());
										errore_corr=errore.subtract(errGen);
									}
									
								}
								String mpe=getMPE(mas.toPlainString(), comboBox_campo.getSelectedIndex());
								
								if(errore!=null)
								{
									modelDec.setValueAt(errore.toString(), row, 4);
								}
								if(errore_corr!=null) 
								{
									modelDec.setValueAt(errore_corr.toString(), row, 5);
								
								}
								if(mpe!=null) 
								{
									modelDec.setValueAt(mpe, row, 6);
								}
								
								String esito =valutaEsitoDecentramento();
								
								VerDecentramentoDTO decentramento = new VerDecentramentoDTO();
								
								decentramento.setId(Integer.parseInt(modelDec.getValueAt(row, 7).toString()));
								decentramento.setTipoRicettore(getTipoRicettore(bg));
								decentramento.setPuntiAppoggio(Integer.parseInt(textField_punti_appoggio.getText()));
								decentramento.setMassa(mas);
								decentramento.setIndicazione(ind);
								decentramento.setCaricoAgg(car);
								decentramento.setErrore(errore);
								decentramento.setErroreCor(errore_corr);
								decentramento.setMpe(new BigDecimal(mpe));
								decentramento.setEsito(esito);
								decentramento.setCampo(comboBox_campo.getSelectedIndex()+1);
							
								GestioneMisuraBO.updateValoriDecentramento(decentramento,misura.getId());
								
								
							}
							
						}catch (Exception ex) {
						ex.printStackTrace();
						}
						
					
				}
			  }

			private String valutaEsitoDecentramento() {
				
				
				for (int i = 0; i <modelDec.getRowCount(); i++) 
				
				{
					Object mpe= modelDec.getValueAt(i,6);
					Object err_cor=modelDec.getValueAt(i,5);
					
					if(mpe!=null && err_cor!=null) 
					{
						if(Double.parseDouble(err_cor.toString())>Double.parseDouble(mpe.toString())) 
						{
							return "NEGATIVO";
						}
					}
					
				}
				return "POSITIVO";
			}

			private int getTipoRicettore(ButtonGroup bg) {
				
				int i=0;
				
					int indice=1;
					for (Enumeration<AbstractButton> buttons = bg.getElements(); buttons.hasMoreElements();) {
				            AbstractButton button = buttons.nextElement();

				            if (button.isSelected()) 
				            {
				            	return indice;
				                	
				            }
				            indice++;
					}
					return i;
			}

			private BigDecimal getErrore(BigDecimal mas, BigDecimal ind, BigDecimal err, BigDecimal car) {
				
				
				return ind.add(err.divide(new BigDecimal(2),RoundingMode.HALF_UP)).subtract(car).subtract(mas);
			}

			});
		
		
		
		return pannelloDecentramento;
	}
	
	private String getCaricoDecentramento(int punti_appoggio, int i) {
		
		if(i==0) 
		{
			if(punti_appoggio<=4) 
			{
				return new BigDecimal(strumento.getPortata_max_C1().doubleValue()/3).setScale(1, RoundingMode.HALF_UP).toPlainString();
			}
			else 
			{
				return new BigDecimal(strumento.getPortata_max_C1().doubleValue()/(punti_appoggio-1)).setScale(1, RoundingMode.HALF_UP).toPlainString();
			}
		}
		if(i==1) 
		{
			if(punti_appoggio<=4) 
			{
				return new BigDecimal(strumento.getPortata_max_C2().doubleValue()/3).setScale(1, RoundingMode.HALF_UP).toPlainString();
			}
			else 
			{
				return new BigDecimal(strumento.getPortata_max_C2().doubleValue()/(punti_appoggio-1)).setScale(1, RoundingMode.HALF_UP).toPlainString();
			}
		}
		if(i==2) 
		{
			if(punti_appoggio<=4) 
			{
				return new BigDecimal(strumento.getPortata_max_C3().doubleValue()/3).setScale(1, RoundingMode.HALF_UP).toPlainString();
			}
			else 
			{
				return new BigDecimal(strumento.getPortata_max_C3().doubleValue()/(punti_appoggio-1)).setScale(1, RoundingMode.HALF_UP).toPlainString();
			}
		}
		
		return"";
	
	}
	private JPanel costruisciPannelloLinearita() {
		
		JPanel pannelloLinearita= new JPanel();
		
		pannelloLinearita.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 2, true), "Prova Ripetibilità", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pannelloLinearita.setBackground(Color.WHITE);
		pannelloLinearita.setLayout(new MigLayout("", "[22.00][][grow]", "[][][][][][][][][13.00][][13.00][]"));
		
		final JComboBox comboBox_tipo_azzeramento = new JComboBox();
		comboBox_tipo_azzeramento.setModel(new DefaultComboBoxModel(new String[] {"Non automatico o semiautomatico","Automatico"}));
		comboBox_tipo_azzeramento.setFont(new Font("Arial", Font.PLAIN, 12));
		
		
		tableLin = new JTable();
		
		modelLin = new ModelLinearita(strumento.getUm());
		
		tableLin.setModel(modelLin);
		
		tableLin.setRowHeight(25);
		tableLin.setFont(new Font("Arial", Font.PLAIN, 12));
		tableLin.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
		
		tableLin.setDefaultRenderer(Object.class, new MyCellRenderer());
		
		ArrayList<VerLinearitaDTO> listaLinearita = (ArrayList<VerLinearitaDTO>)misura.getVerLinearitas();
		
		int campo=comboBox_campo.getSelectedIndex()+1;
		
		for (int i = 0; i <=5; i++) {
			
			VerLinearitaDTO lin=listaLinearita.get(i);
			
			modelLin.addRow(new Object[0]);
			
			if(i==0)
			{
				modelLin.setValueAt("E0", i, 0);
				
			}else
			{
				modelLin.setValueAt(i, i, 0);
			}
			
			if(lin.getMassa()!=null) 
			{
				modelLin.setValueAt(lin.getMassa(), i, 1);	
			}else 
			{
				if(i==0) {
					if(comboBox_tipo_azzeramento.getSelectedIndex()==0) 
					{
						modelLin.setValueAt("0", i, 1);
					}
					else
					{
						BigDecimal massa=getE(comboBox_campo.getSelectedIndex()).multiply(BigDecimal.TEN).setScale(2,RoundingMode.HALF_DOWN);
						modelLin.setValueAt(massa.toPlainString(), i, 1);
					}
				}
				if(i==1) 
				{
					modelLin.setValueAt(strumento.getPortataMinCampo(campo).setScale(2).toPlainString(), i, 1);
				}
				if(i==2) 
				{
					modelLin.setValueAt(strumento.getPortataMaxCampo(campo).setScale(2).multiply(new BigDecimal("0.4")).setScale(2,RoundingMode.HALF_UP), i, 1);
				}
				if(i==3) 
				{
					modelLin.setValueAt(strumento.getPortataMaxCampo(campo).multiply(new BigDecimal("0.6")).toPlainString(), i, 1);
				}
				if(i==4) 
				{
					modelLin.setValueAt(strumento.getPortataMaxCampo(campo).multiply(new BigDecimal("0.8")).toPlainString(), i, 1);
				}
				if(i==5) 
				{
					modelLin.setValueAt(strumento.getPortataMaxCampo(campo), i, 1);
				}
			}
			
			if(lin.getIndicazioneSalita()!=null) 
			{
				modelLin.setValueAt(lin.getIndicazioneSalita(), i, 2);	
			}
			
			if(lin.getIndicazioneDiscesa()!=null) 
			{
				modelLin.setValueAt(lin.getIndicazioneDiscesa(), i, 3);	
			}
			
			if(lin.getCaricoAggSalita()!=null) 
			{
				modelLin.setValueAt(lin.getCaricoAggSalita(), i, 4);	
			}
			
			if(lin.getCaricoAggDiscesa()!=null) 
			{
				modelLin.setValueAt(lin.getCaricoAggDiscesa(), i, 5);	
			}
			
			if(lin.getErroreSalita()!=null) 
			{
				modelLin.setValueAt(lin.getErroreSalita(), i, 6);	
			}
			
			if(lin.getErroreDiscesa()!=null) 
			{
				modelLin.setValueAt(lin.getErroreDiscesa(), i, 7);	
			}
			
			if(lin.getErroreCorSalita()!=null) 
			{
				modelLin.setValueAt(lin.getErroreCorSalita(), i, 8);	
			}
			
			if(lin.getErroreCorDiscesa()!=null) 
			{
				modelLin.setValueAt(lin.getErroreCorDiscesa(), i, 9);	
			}
			
			if(lin.getMpe()!=null) 
			{
				modelLin.setValueAt(lin.getMpe(), i, 10);	
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
		
		
		pannelloLinearita.add(comboBox_tipo_azzeramento, "cell 2 2");
		
		
		if(listaLinearita.get(0).getTipoAzzeramento()!=0) 
		{
			if(listaLinearita.get(0).getTipoAzzeramento()==1) 
			{
				comboBox_tipo_azzeramento.setSelectedIndex(1);
			}
			else 
			{
				comboBox_tipo_azzeramento.setSelectedIndex(1);
			}
		}
		
		
		
		JScrollPane scrollTab = new JScrollPane(tableLin);
		
		pannelloLinearita.add(scrollTab, "cell 1 4 2 1,growx,alignx left,height :200:,aligny top");
		

		JLabel lblEsito = new JLabel("ESITO:");
		lblEsito.setFont(new Font("Arial", Font.BOLD, 12));
		pannelloLinearita.add(lblEsito, "cell 1 6,alignx left");
		
		textField_esito_lin = new JTextField();
		textField_esito_lin.setEditable(false);
		textField_esito_lin.setBackground(Color.YELLOW);
		textField_esito_lin.setFont(new Font("Arial", Font.PLAIN, 12));
		textField_esito_lin.setColumns(10);
		pannelloLinearita.add(textField_esito_lin, "cell 1 6,width :100:");
		
		if(listaLinearita.get(0).getEsito()!=null) 
		{
			if(listaLinearita.get(0).getEsito().equals("POSITIVO")) 
			{
				textField_esito_lin.setBackground(Color.GREEN);
			}else 
			{
				textField_esito_lin.setBackground(Color.RED);
			}
			textField_esito_lin.setText(listaLinearita.get(0).getEsito());
		}
		
		
		tableLin.getModel().addTableModelListener(new TableModelListener() {

			  public void tableChanged(TableModelEvent e) {
			   
					int row = e.getFirstRow();
					int column=e.getColumn();

					if(column==2 || column==3 || column==4 || column==5) 
					{
						int campo=comboBox_campo.getSelectedIndex();
						
						Object massa=modelLin.getValueAt(row, 1);
						Object indicazioneSalita=modelLin.getValueAt(row, 2);
						Object indicazioneDiscesa=modelLin.getValueAt(row, 3);
						Object car_agg_salita=modelLin.getValueAt(row, 4);
						Object car_agg_discesa=modelLin.getValueAt(row, 5);
						
						if(massa!=null && indicazioneSalita!=null && indicazioneDiscesa!=null
						   && car_agg_salita!=null && car_agg_discesa!=null) 
						{
							BigDecimal mas;
							BigDecimal indSalita;
							BigDecimal indDiscesa;
							BigDecimal car_aggSalita;
							BigDecimal car_aggDiscesa;
							BigDecimal erroreSalita;
							BigDecimal erroreDiscesa;
							BigDecimal erroreSalita_cor = null;
							BigDecimal erroreDiscesa_cor = null;
							
							try 
							{
								 mas=new BigDecimal(massa.toString());
								 indSalita=new BigDecimal(indicazioneSalita.toString());
								 indDiscesa=new BigDecimal(indicazioneSalita.toString());
								
								 car_aggSalita=new BigDecimal(car_agg_salita.toString());
								 car_aggDiscesa=new BigDecimal(car_agg_discesa.toString());
						
								if(row==0) 
								{
								 erroreSalita=indSalita.add(getE(campo).divide(new BigDecimal("2"), RoundingMode.HALF_UP)
										                .subtract(car_aggSalita).subtract(mas));
								
								 erroreDiscesa=indDiscesa.add(getE(campo).divide(new BigDecimal("2"), RoundingMode.HALF_UP)
						                .subtract(car_aggDiscesa).subtract(mas));
								
								String mpe=getMPE(mas.toPlainString(), campo);
								
								modelLin.setValueAt(erroreSalita, row, 6);
								modelLin.setValueAt(erroreDiscesa, row, 7);
								modelLin.setValueAt("0", row, 8);
								modelLin.setValueAt("0", row, 9);
								modelLin.setValueAt(mpe, row, 10);
								}
								
								else 
								{
									 erroreSalita=indSalita.add(getE(campo).divide(new BigDecimal("2"), RoundingMode.HALF_UP)
							                .subtract(car_aggSalita).subtract(mas));
					
									 erroreDiscesa=indDiscesa.add(getE(campo).divide(new BigDecimal("2"), RoundingMode.HALF_UP)
							                .subtract(car_aggDiscesa).subtract(mas));
					
									
									erroreSalita_cor=new BigDecimal(modelLin.getValueAt(0, 7).toString()).subtract(erroreSalita);
					
									erroreDiscesa_cor=new BigDecimal(modelLin.getValueAt(0, 8).toString()).subtract(erroreDiscesa);
									
									String mpe=getMPE(mas.toPlainString(), campo);
									
									modelLin.setValueAt(erroreSalita, row, 6);
									modelLin.setValueAt(erroreDiscesa, row, 7);
									modelLin.setValueAt(erroreSalita_cor, row, 8);
									modelLin.setValueAt(erroreDiscesa_cor, row, 9);
									modelLin.setValueAt(mpe, row, 10);
									
								}
								
								String esito=valutaEsitoLinearita();
								
								if(esito.equals("POSITIVO")) 
								{
									textField_esito_lin.setBackground(Color.GREEN);
								}
								else 
								{
									textField_esito_lin.setBackground(Color.RED);	
								}
								textField_esito_lin.setText(esito);
								
								VerLinearitaDTO linearita = new VerLinearitaDTO();
								
								linearita.setTipoAzzeramento(comboBox_tipo_azzeramento.getSelectedIndex());
								linearita.setCampo(comboBox_campo.getSelectedIndex()+1);
								linearita.setMassa(mas);
								linearita.setIndicazioneSalita(indSalita);
								linearita.setIndicazioneDiscesa(indDiscesa);
								linearita.setCaricoAggSalita(car_aggSalita);
								linearita.setCaricoAggSalita(car_aggDiscesa);
								linearita.setErroreSalita(erroreSalita);
								linearita.setErroreDiscesa(erroreDiscesa);
								linearita.setErroreCorSalita(erroreSalita_cor);
								linearita.setErroreCorDiscesa(erroreDiscesa_cor);
								linearita.setEsito(esito);
								
								GestioneMisuraBO.updateValoriLinearita(linearita, misura.getId());
								
								
							}
							catch (Exception e2) 
							{
								e2.printStackTrace();
							}
							
							
						}
						
					}
					
			  }

			private String valutaEsitoLinearita() {
				
				for (int i = 0; i <modelLin.getRowCount(); i++) 
					
				{
					Object mpe= modelLin.getValueAt(i,10);
					Object err_sal=modelLin.getValueAt(i,6);
					Object err_dis=modelLin.getValueAt(i,7);
					Object err_cor_sal=modelLin.getValueAt(i,8);
					Object err_cor_dis=modelLin.getValueAt(i,9);
					if(i==0 && mpe!=null && err_sal!=null && err_dis!=null) 
					{
						if(Math.abs(Double.parseDouble(err_sal.toString())) > Math.abs(Double.parseDouble(mpe.toString())) ||
						   Math.abs(Double.parseDouble(err_dis.toString())) > Math.abs(Double.parseDouble(mpe.toString()))	) 
							{
								return "NEGATIVO";
							} 
					}
					
					if(i>0 && mpe!=null && err_sal!=null && err_dis!=null) 
					{
						if(Math.abs(Double.parseDouble(err_cor_sal.toString())) > Math.abs(Double.parseDouble(mpe.toString())) ||
						   Math.abs(Double.parseDouble(err_cor_dis.toString())) > Math.abs(Double.parseDouble(mpe.toString()))	) 
									{
										return "NEGATIVO";
									} 
					}
					
				}
				return "POSITIVO";
			}
			  
		});
		

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
		
		textField_esito_acc = new JTextField();
		textField_esito_acc.setEditable(false);
		textField_esito_acc.setBackground(Color.YELLOW);
		textField_esito_acc.setFont(new Font("Arial", Font.PLAIN, 12));
		textField_esito_acc.setColumns(10);
		pannelloAccuratezza.add(textField_esito_acc, "cell 1 6,width :100:");

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
		
		textField_esito_mob = new JTextField();
		textField_esito_mob.setEditable(false);
		textField_esito_mob.setBackground(Color.YELLOW);
		textField_esito_mob.setFont(new Font("Arial", Font.PLAIN, 12));
		textField_esito_mob.setColumns(10);
		pannelloMobilita.add(textField_esito_mob, "cell 1 8,width :100:");

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
		
		Font f = new Font("Arial",Font.ITALIC, 14);
		
		String[] seq=misura.getSeqRisposte().split(";");
		
		for (int i=0;i<listaDomande.size();i++) 
		{
			
			String domanda =listaDomande.get(i);
			y=i*30;
			
			JLabel lab1= new JLabel(domanda);
			lab1.setBounds(10, y, 900, 25);
			lab1.setFont(f);
			
			JRadioButton si = new JRadioButton("SI");
			JRadioButton no = new JRadioButton("NO");
			JRadioButton na = new JRadioButton("N/A");
		
		
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
			  
			  if(!misura.getSeqRisposte().equals("0")) 
			  {
				 String res=seq[i];
				 
				 if(res.equals("0")) 
				 {
					 si.setSelected(true);
				 }
				 if(res.equals("1")) 
				 {
					 no.setSelected(true);
				 }
				 if(res.equals("2")) 
				 {
					 na.setSelected(true);
				 }
				 
				
					
			  }else 
			  {
				  
			  if(i>8) 
				{
					na.setSelected(true);
				}
			  }
			  
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
		
		if(!misura.getSeqRisposte().equals("0")) 
		{
		 boolean esito = getEsitoControlloPreliminare(misura.getSeqRisposte());
			
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
		}	
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
	
	class ModelRipetibilita extends DefaultTableModel {


		public ModelRipetibilita(String um) 
		{
			addColumn("N° Ripetizioni");
			addColumn("Massa L ("+um+")");
			addColumn("Indicazione I ("+um+")");
			addColumn("Carico Agg.  ΔL ("+um+")");
			addColumn("P ("+um+")");
			addColumn("id");

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
			default:
				return String.class;
			}
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			
			if(column==2 || column==3)
			{
				return true;
			}else
			{
				return false;
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
			addColumn("id");

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
			
			if(column==2 || column==3)
			{
				return true;
			}else
			{
				return false;
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
			
			if(column==2 || column==3 || column==4 || column==5)
			{
				return true;
			}else
			{
				return false;
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

      
         setHorizontalAlignment(CENTER);
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
        public class MyRendererDecentramentoSpecial extends javax.swing.table.DefaultTableCellRenderer {
    		

            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, java.lang.Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            
            	final java.awt.Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

          
             setHorizontalAlignment(CENTER);
           
                    cellComponent.setForeground(Color.black);
                    cellComponent.setBackground(Color.white);

             
    			return cellComponent;
           
            } 

        }
        
        public class MyRendererDecentramentoNormal extends javax.swing.table.DefaultTableCellRenderer {
    		

            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, java.lang.Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            
            	final java.awt.Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

          
             setHorizontalAlignment(CENTER);
                if (row % 2 == 0 && row!=0) 
                {
                	cellComponent.setForeground(Color.black);
                    cellComponent.setBackground(new Color(224,224,224));          
                    cellComponent.setEnabled(false);
                }
                else
                {
                	cellComponent.setForeground(Color.black);
                    cellComponent.setBackground(Color.white);
                }
    			return cellComponent;
           
            } 

        }
}



package it.calverDesktopVER.gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
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
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import it.calverDesktopVER.bo.GestioneCampioneBO;
import it.calverDesktopVER.bo.GestioneMisuraBO;
import it.calverDesktopVER.bo.GestioneStrumentoVER_BO;
import it.calverDesktopVER.bo.SessionBO;
import it.calverDesktopVER.dto.VerAccuratezzaDTO;
import it.calverDesktopVER.dto.VerClassiDTO;
import it.calverDesktopVER.dto.VerDecentramentoDTO;
import it.calverDesktopVER.dto.VerLinearitaDTO;
import it.calverDesktopVER.dto.VerMisuraDTO;
import it.calverDesktopVER.dto.VerMobilitaDTO;
import it.calverDesktopVER.dto.VerRipetibilitaDTO;
import it.calverDesktopVER.dto.VerStrumentoDTO;
import it.calverDesktopVER.utl.Costanti;
import it.calverDesktopVER.utl.FileTypeFilter;
import it.calverDesktopVER.utl.Utility;
import net.miginfocom.swing.MigLayout;

public class PannelloMisuraMaster extends JPanel 
{
	
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
	private JPanel panel_datiGenarali;
	private JPanel panel_tabella;
	private JPanel panel_prova_ripetibilita;
	private JPanel panel_prova_decentramento;
	private JPanel panel_prova_linearita;
	private JPanel panel_prova_accuratezza;
	private JPanel panel_prova_mobilita;
	JSplitPane splitPane;
	private static DefaultListModel<String> dlm;


	VerStrumentoDTO strumento;
	VerMisuraDTO misura;

	private BufferedImage img;
	static JFrame myFrame=null;
	JTable table_dati_strumento=null;
	JTable tabellaSecurTest=null;
	JFrame f;
	ArrayList<ButtonGroup> listaRisposte = new ArrayList<>();
	private JTable tableRip,tableDec,tableLin,tableAcc,tabellaMobilita1,tabellaMobilita2;
	
	private static String[] listaCampioniCompleta=null;
	

	ModelRipetibilita modelRip;
	ModelDecentramento modelDec;
	ModelLinearita modelLin;
	ModelAccuratezza modelAccuratezza;
	ModelMobilita modelMobilita_1;
	ModelMobilita modelMobilita_2;

	private JTextField textField_p_p_rip;
	private JTextField textField_mpe_rip;
	private JTextField textField_esito_rip;
	private JTextField textField_esito_mob_2;
	private JTextField textField_esito_acc;
	private JTextField textField_esito_lin;
	private JTextField textField_esito_dec;
	private JTextField textField_punti_appoggio;
	private JTextField textField_carico;
	private JTextField textField_esito_controllo_preliminare;
	JComboBox comboBox_campo;
	ArrayList<VerClassiDTO> listaClassi;
	private JTextField textField_esito_mob_1;
	private boolean flag;
	private JTextField textField_nomeRiparatore;
	private JTextField textField_dataRiparazione;
	JCheckBox chckbx;
	JComboBox comboBox_tipo_verifica=null;
	JComboBox comboBox_motivo=null;
	private JTextField textField_foto_inizio;
	private JTextField textField_foto_fine;
	
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
		comboBox_campo.setFont(new Font("Arial", Font.BOLD, 14));

		if(strumento.getId_tipo_strumento()==3) 
		{
			comboBox_campo.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3"}));
		}else 
		{
			comboBox_campo.setModel(new DefaultComboBoxModel(new String[] {"1"}));
		}

		panel_datiGenarali = costruisciDatiGenerali();
		
		panel_tabella = costruisciTabella();
		
		
		panel_struttura =creaPanelStruttura();

		JLabel lblCampo = new JLabel("Campo");
		lblCampo.setFont(new Font("Arial", Font.BOLD, 14));
		masterPanel.add(lblCampo, "flowx,cell 0 0");

		masterPanel.add(comboBox_campo, "cell 0 0");


		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

		
		tabbedPane.addTab("Dati Generali",panel_datiGenarali);
		
		tabbedPane.addTab("Controllo preliminare",panel_tabella);
		
		panel_prova_ripetibilita=costruisciPanelloRipetibilita();
		tabbedPane.addTab("Prova Ripetibilità", panel_prova_ripetibilita);
		
		panel_prova_decentramento=costruisciPanelloDecentramento();
		tabbedPane.addTab("Prova Decentramento", panel_prova_decentramento);
		
		panel_prova_linearita=costruisciPannelloLinearita();
		tabbedPane.addTab("Prova Linearità", panel_prova_linearita);
		
		if(strumento.getTipologia()==2) 
		{
		panel_prova_accuratezza= costruisciPannelloAccuratezza();
		tabbedPane.addTab("Prova Accuratezza", panel_prova_accuratezza);
		
		panel_prova_mobilita=costruisciPannelloMobilita();
		tabbedPane.addTab("Prova Mobilità", panel_prova_mobilita);
		
		}
		masterPanel.add(tabbedPane, "cell 0 1,grow");
		masterPanel.add(panel_struttura,"cell 0 2,grow");


		double height=(SessionBO.heightFrame*73)/100;
		double width=(SessionBO.widthFrame*70)/100;
		masterPanel.setPreferredSize(new Dimension((int)width-50,(int) height/2));

		JScrollPane scroll= new JScrollPane(masterPanel);
		this.add(scroll, "cell 0 0,grow");

		comboBox_campo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					misura=GestioneMisuraBO.getMisura(SessionBO.idMisura);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				panel_prova_ripetibilita=costruisciPanelloRipetibilita();
				panel_prova_decentramento=costruisciPanelloDecentramento();
				panel_prova_linearita=costruisciPannelloLinearita();
				
				tabbedPane.setComponentAt(2, panel_prova_ripetibilita);
				tabbedPane.setComponentAt(3, panel_prova_decentramento);
				tabbedPane.setComponentAt(4, panel_prova_linearita);
				
				if(strumento.getTipologia()==2) 
				{
					panel_prova_accuratezza= costruisciPannelloAccuratezza();
					tabbedPane.setComponentAt(5, panel_prova_accuratezza);
					
					panel_prova_mobilita=costruisciPannelloMobilita();
					tabbedPane.setComponentAt(6, panel_prova_mobilita);
				}
			}
		});


	}


	
	private JPanel costruisciDatiGenerali() {
		
		JPanel pannelloDatiGenerali = new JPanel();
		pannelloDatiGenerali.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 2, true), "Dati Generali", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pannelloDatiGenerali.setBackground(Color.WHITE);
		pannelloDatiGenerali.setLayout(new MigLayout("", "[][9.00][][][][]", "[10.00][][10.00][][10.00][][10.00][39.00][30.00][31.00][][grow][][][][][]"));
		
		JLabel lblTipoVerifica = new JLabel("Tipo Verifica");
		lblTipoVerifica.setFont(new Font("Arial", Font.BOLD, 14));
		pannelloDatiGenerali.add(lblTipoVerifica, "cell 0 1,alignx trailing");
		
		comboBox_tipo_verifica = new JComboBox();
		comboBox_tipo_verifica.setFont(new Font("Arial", Font.BOLD, 12));
		comboBox_tipo_verifica.setModel(new DefaultComboBoxModel(new String[] {"Seleziona","Periodica", "Prima", "Collaudo"}));
		pannelloDatiGenerali.add(comboBox_tipo_verifica, "cell 2 1");
		
		JLabel lblMotivoVerifica = new JLabel("Motivo Verifica");
		lblMotivoVerifica.setFont(new Font("Arial", Font.BOLD, 14));
		pannelloDatiGenerali.add(lblMotivoVerifica, "cell 0 3,alignx trailing");
		
		comboBox_motivo = new JComboBox();
		comboBox_motivo.setFont(new Font("Arial", Font.BOLD, 12));
		comboBox_motivo.setModel(new DefaultComboBoxModel(new String[] {"Seleziona","Scadenza validità periodica", "Verifica dopo riparazione", "Accertamento CCIAA"}));
		pannelloDatiGenerali.add(comboBox_motivo, "flowx,cell 2 3");
		
		
		final JLabel lblDataRiparazione = new JLabel("Data riparazione");
		lblDataRiparazione.setFont(new Font("Arial", Font.BOLD, 12));
		pannelloDatiGenerali.add(lblDataRiparazione, "flowx,cell 4 3,alignx trailing");
		
		chckbx = new JCheckBox("Lo strumento presenta difetti tali da pregiudicare l'affidabilità metrologica");
		chckbx.setFont(new Font("Arial", Font.BOLD, 14));
		chckbx.setBackground(Color.WHITE);
		pannelloDatiGenerali.add(chckbx, "cell 0 5 5 1");
		
		JLabel lblListaCampioniLavoro = new JLabel("Lista campioni lavoro");
		lblListaCampioniLavoro.setFont(new Font("Arial", Font.BOLD, 14));
		pannelloDatiGenerali.add(lblListaCampioniLavoro, "cell 0 7 5 1");
		
		JLabel lblListaCampioni = new JLabel("Lista Campioni");
		lblListaCampioni.setFont(new Font("Arial", Font.BOLD, 12));
		pannelloDatiGenerali.add(lblListaCampioni, "cell 0 8,alignx right");
		
		try {
			listaCampioniCompleta=GestioneCampioneBO.getListaCampioniCompleta();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		final JComboBox comboBox_lista_campioni = new JComboBox(listaCampioniCompleta);
		pannelloDatiGenerali.add(comboBox_lista_campioni, "cell 2 8 3 1");
		
		JLabel lblListaParametri = new JLabel("Lista Parametri");
		lblListaParametri.setFont(new Font("Arial", Font.BOLD, 12));
		pannelloDatiGenerali.add(lblListaParametri, "cell 0 9,alignx right");
		
		final JComboBox comboBox_listaParametri = new JComboBox();
		comboBox_listaParametri.setModel(new DefaultComboBoxModel(new String[] {"Seleziona Parametro..."}));
		pannelloDatiGenerali.add(comboBox_listaParametri, "cell 2 9 3 1");
		
		JScrollPane scrollPaneListaCMP = new JScrollPane();
		pannelloDatiGenerali.add(scrollPaneListaCMP, "cell 2 11,grow");
		
		dlm= new DefaultListModel<String>();
		
		final JList list_cmp = new JList(dlm);
		list_cmp.setFont(new Font("Arial", Font.BOLD, 12));
		scrollPaneListaCMP.setViewportView(list_cmp);
		
		JButton btnNewButton = new JButton("Aggiungi");
		
			btnNewButton.setIcon(new ImageIcon(PannelloMisuraMaster.class.getResource("/image/add.png")));
			btnNewButton.setFont(new Font("Arial", Font.BOLD, 12));
			pannelloDatiGenerali.add(btnNewButton, "flowx,cell 4 11,aligny top");
			
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					if(comboBox_lista_campioni.getSelectedIndex()>0 && comboBox_listaParametri.getSelectedIndex()>0 ) 
					{
						String campione= comboBox_lista_campioni.getSelectedItem().toString()+"("+comboBox_listaParametri.getSelectedItem().toString()+")";

						dlm.addElement(campione);
					}
					if(comboBox_lista_campioni.getSelectedIndex()>0 && comboBox_listaParametri.getSelectedIndex()==0 ) 
					{
						dlm.addElement(comboBox_lista_campioni.getSelectedItem().toString());
					}
				}
			});
		
		final JLabel lblNomeRiparatore = new JLabel("Nome Riparatore");
		lblNomeRiparatore.setFont(new Font("Arial", Font.BOLD, 12));
		pannelloDatiGenerali.add(lblNomeRiparatore, "cell 2 3,alignx trailing");
		
		textField_nomeRiparatore = new JTextField();
		pannelloDatiGenerali.add(textField_nomeRiparatore, "cell 2 3,width :200:");
		textField_nomeRiparatore.setColumns(10);
		
		textField_dataRiparazione = new JTextField();
		textField_dataRiparazione.setColumns(10);
		pannelloDatiGenerali.add(textField_dataRiparazione, "cell 4 3,width :120:");
		
		JButton btnElimina = new JButton("Elimina");
		btnElimina.setFont(new Font("Arial", Font.BOLD, 12));
		btnElimina.setIcon(new ImageIcon(PannelloMisuraMaster.class.getResource("/image/delete.png")));
		pannelloDatiGenerali.add(btnElimina, "cell 4 11,aligny top");
		
		JLabel lblFotoInizioProva = new JLabel("Foto inizio prova");
		lblFotoInizioProva.setFont(new Font("Arial", Font.BOLD, 14));
		pannelloDatiGenerali.add(lblFotoInizioProva, "cell 0 13");
		
		textField_foto_inizio = new JTextField();
		textField_foto_inizio.setEditable(false);
		pannelloDatiGenerali.add(textField_foto_inizio, "cell 2 13,growx");
		textField_foto_inizio.setColumns(10);
		
		JButton btnCarica_inizio = new JButton("Carica");
		final JButton btnVisualizza_inizio = new JButton("Visualizza");
		
		
		btnCarica_inizio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser jfc = new JFileChooser();

				javax.swing.filechooser.FileFilter docFilter = new FileTypeFilter(".jpg", "JPG,JPEG");
				jfc.addChoosableFileFilter(docFilter);
				jfc.showOpenDialog(GeneralGUI.g);
				File f= jfc.getSelectedFile();
				if(f!=null)
				{
					String ext1 = FilenameUtils.getExtension(f.getPath()); 
					if(ext1.equalsIgnoreCase("jpg") || ext1.equalsIgnoreCase("jpeg"))
					{
						try {

							FileInputStream fis = new FileInputStream(f);
							byte[] buffer = new byte[1024];
							ByteArrayOutputStream file_att = new ByteArrayOutputStream();
							for (int len; (len = fis.read(buffer)) != -1;) {
								file_att.write(buffer, 0, len);
							}
					
							textField_foto_inizio.setText(f.getPath());
							GestioneMisuraBO.saveFoto(1,misura.getId(),file_att,f.getName());
							fis.close();
							
							btnVisualizza_inizio.setEnabled(true);
							
							} catch (Exception e2) {
							System.err.println(e2.getMessage());
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null,"Il sistema può caricare solo file in formato JPG","Exstension Error",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
					}

				}
			}
		});
		btnCarica_inizio.setIcon(new ImageIcon(PannelloMisuraMaster.class.getResource("/image/attach.png")));
		btnCarica_inizio.setFont(new Font("Arial", Font.BOLD, 12));
		pannelloDatiGenerali.add(btnCarica_inizio, "flowx,cell 4 13");
		
		JLabel lblFotoFineProva = new JLabel("Foto fine prova");
		lblFotoFineProva.setFont(new Font("Arial", Font.BOLD, 14));
		pannelloDatiGenerali.add(lblFotoFineProva, "cell 0 15");
		
		textField_foto_fine = new JTextField();
		textField_foto_fine.setEditable(false);
		textField_foto_fine.setColumns(10);
		pannelloDatiGenerali.add(textField_foto_fine, "cell 2 15,growx");
		
		
		btnVisualizza_inizio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				try {

						JFileChooser jfc = new JFileChooser();
						jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						jfc.showSaveDialog(f);

						File f= jfc.getSelectedFile();
						if(f!=null)
						{

							misura=GestioneMisuraBO.getMisura(SessionBO.idMisura);
							FileUtils.writeByteArrayToFile(new File(f.getAbsoluteFile()+"\\"+misura.getNomeFile_inizio_prova()), misura.getFile_inizio_prova());
							JOptionPane.showMessageDialog(null, "File salvato con successo ","Salvataggio",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/confirm.png")));

						}

					
				} catch (Exception ec) {

					PannelloConsole.printException(ec);
				}
				
			}
		});
		btnVisualizza_inizio.setIcon(new ImageIcon(PannelloMisuraMaster.class.getResource("/image/search_24.png")));
		btnVisualizza_inizio.setFont(new Font("Arial", Font.BOLD, 12));
		pannelloDatiGenerali.add(btnVisualizza_inizio, "cell 4 13");
		
		JButton btnCarica_fine = new JButton("Carica");
		final JButton btnVisualizza_fine = new JButton("Visualizza");
		
		btnVisualizza_fine.setIcon(new ImageIcon(PannelloMisuraMaster.class.getResource("/image/search_24.png")));
		btnVisualizza_fine.setFont(new Font("Arial", Font.BOLD, 12));
		
		btnCarica_fine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser jfc = new JFileChooser();

				javax.swing.filechooser.FileFilter docFilter = new FileTypeFilter(".jpg", "JPG,JPEG");
				jfc.addChoosableFileFilter(docFilter);
				jfc.showOpenDialog(GeneralGUI.g);
				File f= jfc.getSelectedFile();
				if(f!=null)
				{
					String ext1 = FilenameUtils.getExtension(f.getPath()); 
					if(ext1.equalsIgnoreCase("jpg") || ext1.equalsIgnoreCase("jpeg"))
					{
						try {

							FileInputStream fis = new FileInputStream(f);
							byte[] buffer = new byte[1024];
							ByteArrayOutputStream file_att = new ByteArrayOutputStream();
							for (int len; (len = fis.read(buffer)) != -1;) {
								file_att.write(buffer, 0, len);
							}
					
							textField_foto_fine.setText(f.getPath());
							GestioneMisuraBO.saveFoto(2,misura.getId(),file_att,f.getName());
							fis.close();
							
							btnVisualizza_fine.setEnabled(true);
							
							} catch (Exception e2) {
							System.err.println(e2.getMessage());
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null,"Il sistema può caricare solo file in formato JPG","Exstension Error",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/error.png")));
					}

				}
			}
		});
		
		btnVisualizza_fine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				try {

						JFileChooser jfc = new JFileChooser();
						jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						jfc.showSaveDialog(f);

						File f= jfc.getSelectedFile();
						if(f!=null)
						{
							
							misura=GestioneMisuraBO.getMisura(SessionBO.idMisura);
							FileUtils.writeByteArrayToFile(new File(f.getAbsoluteFile()+"\\"+misura.getNomeFile_fine_prova()), misura.getFile_fine_prova());
							JOptionPane.showMessageDialog(null, "File salvato con successo ","Salvataggio",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/confirm.png")));

						}

					
				} catch (Exception ec) {
					ec.printStackTrace();
					PannelloConsole.printException(ec);
				}
				
			}
		});
		
		btnCarica_fine.setIcon(new ImageIcon(PannelloMisuraMaster.class.getResource("/image/attach.png")));
		btnCarica_fine.setFont(new Font("Arial", Font.BOLD, 12));
		pannelloDatiGenerali.add(btnCarica_fine, "flowx,cell 4 15");
		
		
		pannelloDatiGenerali.add(btnVisualizza_fine, "cell 4 15");
		
		
		btnElimina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(dlm.size()>0 && list_cmp.getSelectedIndex()>-1)
				{
					dlm.removeElementAt(list_cmp.getSelectedIndex());
				}
				
			}
		});
		
		
		if(misura.getTipo_verifica()!=0) 
		{
			comboBox_tipo_verifica.setSelectedIndex(misura.getTipo_verifica());
			
			if(misura.getMotivo_verifica()==2) 
			{
				lblNomeRiparatore.setVisible(true);
				lblDataRiparazione.setVisible(true);
				textField_nomeRiparatore.setVisible(true);
				textField_dataRiparazione.setVisible(true);
				textField_nomeRiparatore.setText(misura.getNomeRiparatore());
				textField_dataRiparazione.setText(misura.getDataRiparazione());
			}
			else 
			{
				lblNomeRiparatore.setVisible(false);
				lblDataRiparazione.setVisible(false);
				textField_nomeRiparatore.setVisible(false);
				textField_dataRiparazione.setVisible(false);
			
			}
			
		}

		if(misura.getMotivo_verifica()!=0) 
		{
			comboBox_motivo.setSelectedIndex(misura.getMotivo_verifica());
		}else 
		{
			comboBox_motivo.setSelectedIndex(0);
		}
		
		if(misura.getIs_difetti()!=null && misura.getIs_difetti().equals("S"))
		{
			chckbx.setSelected(true);
		}
		
		
		
		if(misura.getCampioniLavoro()!=null && misura.getCampioniLavoro().length()>0)
		{
			String[] campioni=misura.getCampioniLavoro().split(";");
			
			for (int i = 0; i < campioni.length; i++) {
			
				dlm.add(i, campioni[i]);
			}
		}
		comboBox_motivo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(comboBox_motivo.getSelectedIndex()==2) 
				{
					lblNomeRiparatore.setVisible(true);
					lblDataRiparazione.setVisible(true);
					textField_nomeRiparatore.setVisible(true);
					textField_dataRiparazione.setVisible(true);
				}
				else 
				{
					lblNomeRiparatore.setVisible(false);
					lblDataRiparazione.setVisible(false);
					textField_nomeRiparatore.setVisible(false);
					textField_dataRiparazione.setVisible(false);
				
				}
				
			}
		});
		
		comboBox_lista_campioni.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
			if (comboBox_lista_campioni.getSelectedIndex()!=0) 
			{
				String[] listaParametriTaratura;
				try {
					listaParametriTaratura = GestioneCampioneBO.getParametriTaratura(comboBox_lista_campioni.getSelectedItem().toString());
					
					comboBox_listaParametri.removeAllItems();

					for(String str : listaParametriTaratura) {


						comboBox_listaParametri.addItem(str);
					}
				
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else 
			{
				comboBox_listaParametri.removeAllItems();
				comboBox_listaParametri.addItem("Seleziona Parametro...");
			}	
			}
		});
		
		if(misura.getNomeFile_inizio_prova()!=null && misura.getNomeFile_inizio_prova().length()>0) 
		{
			textField_foto_inizio.setText(misura.getNomeFile_inizio_prova());
			btnVisualizza_inizio.setEnabled(true);
		}
		else 
		{
			btnVisualizza_inizio.setEnabled(false);
		}
		
		if(misura.getNomeFile_fine_prova()!=null && misura.getNomeFile_fine_prova().length()>0) 
		{
			textField_foto_fine.setText(misura.getNomeFile_fine_prova());
			btnVisualizza_fine.setEnabled(true);
		}
		else 
		{
			btnVisualizza_fine.setEnabled(false);
		}
		
		
		
		return pannelloDatiGenerali;
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

		ArrayList<VerRipetibilitaDTO> lista=(ArrayList)misura.getVerRipetibilitas(comboBox_campo.getSelectedIndex()+1);

		for (int i = 0; i < 6; i++) {

			VerRipetibilitaDTO ver=lista.get(i);
			modelRip.addRow(new Object[0]);
			modelRip.setValueAt(ver.getNumeroRipetizione(), i, 0);

			massa =getMassa(comboBox_campo.getSelectedIndex(),strumento.getId_tipo_strumento());

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


					Object massa=modelRip.getValueAt(row, 1);
					Object indicazione=modelRip.getValueAt(row, 2);
					Object carico=modelRip.getValueAt(row, 3);

					
					
					try 
					{
						if(indicazione!=null && carico!=null && massa!=null) 
						{	
							BigDecimal mas=new BigDecimal(massa.toString());
							BigDecimal ind=new BigDecimal(indicazione.toString());
							BigDecimal car=new BigDecimal(carico.toString());

							BigDecimal err =getE(comboBox_campo.getSelectedIndex(),strumento.getId_tipo_strumento(),mas);
							
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

	private BigDecimal getE(int campo, int id_tipo_strumento,BigDecimal carico)
	{
		
		BigDecimal e = BigDecimal.ZERO;
		
		if(id_tipo_strumento==1) 
		{
			e=strumento.getDiv_ver_C1();
		}

		
		if(id_tipo_strumento==2) 
		{
			if(carico.doubleValue()>=0 && carico.doubleValue() <strumento.getPortata_max_C1().doubleValue()) 
			{
				e=strumento.getDiv_ver_C1();
			}
			if( strumento.getPortata_min_C2().doubleValue()!=0 && carico.doubleValue()>=strumento.getPortata_min_C2().doubleValue() && carico.doubleValue() <=strumento.getPortata_max_C2().doubleValue()) 
			{
				e=strumento.getDiv_ver_C2();
			}
			if(strumento.getPortata_min_C3().doubleValue()!=0 && carico.doubleValue()>strumento.getPortata_min_C3().doubleValue() && carico.doubleValue() <=strumento.getPortata_max_C3().doubleValue()) 
			{
				e=strumento.getDiv_ver_C3();
			}
		}
		
		if(id_tipo_strumento==3) 
		{
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
		}
		
		
		return e;
	}

	private String getMPE(String _massa, int campo) {

		BigDecimal massa=new BigDecimal(_massa);

		BigDecimal e=getE(campo,strumento.getId_tipo_strumento(),massa);

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
					if(pivot>classe.getLimiteInferiore()) 
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


	private String getMassa(int campo,int tipoStrumento) {

		if(tipoStrumento==1) 
		{
			BigDecimal z8=new BigDecimal("0.8");
			return strumento.getPortata_max_C1().setScale(1,RoundingMode.HALF_UP).multiply(z8).setScale(1,RoundingMode.HALF_UP).toPlainString();
		
		} 	
		
	if(tipoStrumento==2) {
	
		BigDecimal max = null;
		
		if(strumento.getPortata_max_C1().doubleValue()!=0)
		{
		 max=	strumento.getPortata_max_C1();
		}
		
		if(strumento.getPortata_max_C2().doubleValue()!=0)
		{
		 max=	strumento.getPortata_max_C2();
		}
		
		if(strumento.getPortata_max_C3().doubleValue()!=0)
		{
		 max=	strumento.getPortata_max_C3();
		}
		
		
			BigDecimal z8=new BigDecimal("0.8");
			return max.setScale(1,RoundingMode.HALF_UP).multiply(z8).setScale(1,RoundingMode.HALF_UP).toPlainString();
	
	}	
		
	if(tipoStrumento==3) 
	{
		if(campo==0) 
		{
			BigDecimal z8=new BigDecimal("0.8");
			return strumento.getPortata_max_C1().setScale(1,RoundingMode.HALF_UP).multiply(z8).setScale(1,RoundingMode.HALF_UP).toPlainString();
		}
		if(campo==1) 
		{
			BigDecimal z8=new BigDecimal("0.8");
			return strumento.getPortata_max_C2().setScale(1,RoundingMode.HALF_UP).multiply(z8).setScale(1,RoundingMode.HALF_UP).toPlainString();
		}
		if(campo==2) 
		{
			BigDecimal z8=new BigDecimal("0.8");
			return strumento.getPortata_max_C3().setScale(1,RoundingMode.HALF_UP).multiply(z8).setScale(1,RoundingMode.HALF_UP).toPlainString();
		}
	}
		return"";
	}


	private JPanel costruisciPanelloDecentramento() {
		JPanel pannelloDecentramento= new JPanel();

		pannelloDecentramento.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 2, true), "Prova Decentramento", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pannelloDecentramento.setBackground(Color.WHITE);
		pannelloDecentramento.setLayout(new MigLayout("", "[22.00][][160.00][160][160][]", "[][][][][][][][]"));

		ArrayList<VerDecentramentoDTO> listaDecentramento=(ArrayList<VerDecentramentoDTO>)misura.getVerDecentramentos(comboBox_campo.getSelectedIndex()+1);
		
		tableDec = new JTable();
		final JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(comboBox.getSelectedItem().toString().equals("SI")) 
				{
					tableDec.setDefaultRenderer(Object.class, new MyRendererDecentramentoSpecial());
					flag=true;
					repaint();
				}else 
				{
					tableDec.setDefaultRenderer(Object.class, new MyRendererDecentramentoNormal());
					flag=false;
					repaint();
				}
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"NO", "SI"}));
		pannelloDecentramento.add(comboBox, "cell 5 4,alignx right");
		
		if(listaDecentramento.get(0).getSpeciale().equals("N")) 
		{
			comboBox.setSelectedIndex(0);
			tableDec.setDefaultRenderer(Object.class, new MyRendererDecentramentoNormal());
			flag=false;
		}else 
		{
			comboBox.setSelectedIndex(1);
			tableDec.setDefaultRenderer(Object.class, new MyRendererDecentramentoSpecial());
			flag=true;
		}
		

		modelDec = new ModelDecentramento(strumento.getUm());

		tableDec.setModel(modelDec);

		tableDec.setRowHeight(17);
		tableDec.setFont(new Font("Arial", Font.PLAIN, 12));
		tableDec.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

		

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


		JLabel lblNewLabel = new JLabel("Prova di Decentramento");
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

		
		
		
		


		
	
		
		
		textField_punti_appoggio = new JTextField();
		pannelloDecentramento.add(textField_punti_appoggio, "cell 1 4,width :50:50");
		textField_punti_appoggio.setColumns(10);

		textField_punti_appoggio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				BigDecimal carico = getCaricoDecentramento(Integer.parseInt(textField_punti_appoggio.getText()),comboBox_campo.getSelectedIndex(),strumento.getId_tipo_strumento());
				textField_carico.setText(carico.toPlainString());

				BigDecimal err =getE(comboBox_campo.getSelectedIndex(),strumento.getId_tipo_strumento(),carico);

				for (int i = 0; i < modelDec.getRowCount(); i++) 
				{
					if(i==0) 
					{
						if(comboBox_campo.getSelectedIndex()==0) 
						{
							modelDec.setValueAt(strumento.getDiv_rel_C1().multiply(BigDecimal.TEN).toPlainString(),i, 1);
						}
						if(comboBox_campo.getSelectedIndex()==1) 
						{
							modelDec.setValueAt(strumento.getDiv_rel_C2().multiply(BigDecimal.TEN).toPlainString(),i, 1);
						}
						if(comboBox_campo.getSelectedIndex()==2) 
						{
							modelDec.setValueAt(strumento.getDiv_rel_C3().multiply(BigDecimal.TEN).toPlainString(),i, 1);
						}
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

		rdbtn_quad.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					GestioneMisuraBO.setRicettoreDecentramento(misura.getId(),0);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		rdbtn_cer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					GestioneMisuraBO.setRicettoreDecentramento(misura.getId(),1);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		rdbtn_tri.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			GestioneMisuraBO.setRicettoreDecentramento(misura.getId(),2);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
});
		
		
		
		if(listaDecentramento.get(0).getPuntiAppoggio()!=0) 
		{
			textField_punti_appoggio.setText(""+listaDecentramento.get(0).getPuntiAppoggio());
			BigDecimal caricoDec=getCaricoDecentramento(listaDecentramento.get(0).getPuntiAppoggio(),comboBox_campo.getSelectedIndex(),strumento.getId_tipo_strumento());
			textField_carico.setText(caricoDec.toPlainString());
			
			
			if(listaDecentramento.get(0).getTipoRicettore()==0) 
			{
				rdbtn_quad.setSelected(true);
			}
			if(listaDecentramento.get(0).getTipoRicettore()==1) 
			{
				rdbtn_cer.setSelected(true);
			}
			if(listaDecentramento.get(0).getTipoRicettore()==2) 
			{
				rdbtn_tri.setSelected(true);
			}
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

					

					try 
					{
						if(indicazione!=null && carico!=null) 
						{	
							BigDecimal mas=new BigDecimal(massa.toString());
							BigDecimal ind=new BigDecimal(indicazione.toString());
							BigDecimal car=new BigDecimal(carico.toString());

							BigDecimal err =getE(comboBox_campo.getSelectedIndex(),strumento.getId_tipo_strumento(),mas);
							
							BigDecimal errore=getErrore(mas,ind,err.setScale(5),car);
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

							if (esito.equals("POSITIVO"))
							{
								textField_esito_dec.setBackground(Color.GREEN);
							}
							else 
							{
								textField_esito_dec.setBackground(Color.RED);
							}
							textField_esito_dec.setText(esito);
							
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
							
							if(comboBox.getSelectedIndex()==0) 
							{
								decentramento.setSpeciale("N");
							}else 
							{
								decentramento.setSpeciale("S");
							}
							decentramento.setCarico(new BigDecimal(textField_carico.getText()));
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
						if(Math.abs(Double.parseDouble(err_cor.toString()))>Math.abs(Double.parseDouble(mpe.toString()))) 
						{
							return "NEGATIVO";
						}
					}

				}
				return "POSITIVO";
			}

			private int getTipoRicettore(ButtonGroup bg) {

				int i=0;

				int indice=0;
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

	private BigDecimal getCaricoDecentramento(int punti_appoggio, int campo,int idTipoStrumento) {

		if(idTipoStrumento==1) 
		{
			if(punti_appoggio<=4) 
			{
				return new BigDecimal(strumento.getPortata_max_C1().doubleValue()/3).setScale(1, RoundingMode.HALF_UP);
			}
			else 
			{
				return new BigDecimal(strumento.getPortata_max_C1().doubleValue()/(punti_appoggio-1)).setScale(1, RoundingMode.HALF_UP);
			}
		}
		
		if(idTipoStrumento==2) 
		{
			BigDecimal max=null;
			
			if(strumento.getPortata_max_C1().doubleValue()!=0) 
			{
				max=strumento.getPortata_max_C1();
			}
			
			if(strumento.getPortata_max_C2().doubleValue()!=0) 
			{
				max=strumento.getPortata_max_C2();
			}
			
			if(strumento.getPortata_max_C3().doubleValue()!=0) 
			{
				max=strumento.getPortata_max_C3();
			}
			
			if(punti_appoggio<=4) 
			{
				return new BigDecimal(max.doubleValue()/3).setScale(1, RoundingMode.HALF_UP);
			}
			else 
			{
				return new BigDecimal(max.doubleValue()/(punti_appoggio-1)).setScale(1, RoundingMode.HALF_UP);
			}
			
		}
		
		
	if(idTipoStrumento==3) 
	{	
	if(campo==0) 
		{
			if(punti_appoggio<=4) 
			{
				return new BigDecimal(strumento.getPortata_max_C1().doubleValue()/3).setScale(1, RoundingMode.HALF_UP);
			}
			else 
			{
				return new BigDecimal(strumento.getPortata_max_C1().doubleValue()/(punti_appoggio-1)).setScale(1, RoundingMode.HALF_UP);
			}
		}
		if(campo==1) 
		{
			if(punti_appoggio<=4) 
			{
				return new BigDecimal(strumento.getPortata_max_C2().doubleValue()/3).setScale(1, RoundingMode.HALF_UP);
			}
			else 
			{
				return new BigDecimal(strumento.getPortata_max_C2().doubleValue()/(punti_appoggio-1)).setScale(1, RoundingMode.HALF_UP);
			}
		}
		if(campo==2) 
		{
			if(punti_appoggio<=4) 
			{
				return new BigDecimal(strumento.getPortata_max_C3().doubleValue()/3).setScale(1, RoundingMode.HALF_UP);
			}
			else 
			{
				return new BigDecimal(strumento.getPortata_max_C3().doubleValue()/(punti_appoggio-1)).setScale(1, RoundingMode.HALF_UP);
			}
		}
	}
		return null;

	}
	private JPanel costruisciPannelloLinearita() {

		JPanel pannelloLinearita= new JPanel();

		pannelloLinearita.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 2, true), "Prova Linearità", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pannelloLinearita.setBackground(Color.WHITE);
		pannelloLinearita.setLayout(new MigLayout("", "[22.00][][grow]", "[][][][][][][][][13.00][][13.00][]"));

		final JComboBox comboBox_tipo_azzeramento = new JComboBox();
		comboBox_tipo_azzeramento.setModel(new DefaultComboBoxModel(new String[] {"Non automatico o semiautomatico","Automatico"}));
		comboBox_tipo_azzeramento.setFont(new Font("Arial", Font.PLAIN, 12));


		comboBox_tipo_azzeramento.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(comboBox_tipo_azzeramento.getSelectedIndex()==0) 
				{
					modelLin.setValueAt("0", 0, 1);
				}
				else 
				{
					if(comboBox_campo.getSelectedIndex()==0) 
					{
						BigDecimal eDiv=strumento.getDiv_ver_C1().multiply(BigDecimal.TEN);
						modelLin.setValueAt(eDiv.toPlainString(), 0, 1);
					}
					if(comboBox_campo.getSelectedIndex()==1) 
					{
						BigDecimal eDiv=strumento.getDiv_ver_C2().multiply(BigDecimal.TEN);
						modelLin.setValueAt(eDiv.toPlainString(), 0, 1);
					}
					if(comboBox_campo.getSelectedIndex()==2) 
					{
						BigDecimal eDiv=strumento.getDiv_ver_C3().multiply(BigDecimal.TEN);
						modelLin.setValueAt(eDiv.toPlainString(), 0, 1);
					}
					
				}

			}
		});


		tableLin = new JTable();

		modelLin = new ModelLinearita(strumento.getUm());

		tableLin.setModel(modelLin);

		tableLin.setRowHeight(25);
		tableLin.setFont(new Font("Arial", Font.PLAIN, 12));
		tableLin.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

		tableLin.setDefaultRenderer(Object.class, new MyCellRenderer());

		TableColumn column = tableLin.getColumnModel().getColumn(tableLin.getColumnModel().getColumnIndex("id"));
		tableLin.removeColumn(column);



		ArrayList<VerLinearitaDTO> listaLinearita = (ArrayList<VerLinearitaDTO>)misura.getVerLinearitas(comboBox_campo.getSelectedIndex()+1);

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
						BigDecimal massa=strumento.getDiv_ver_C1().multiply(BigDecimal.TEN).setScale(2,RoundingMode.HALF_DOWN);
						modelLin.setValueAt(massa.toPlainString(), i, 1);
					}
				}
				if(i==1) 
				{
					modelLin.setValueAt(strumento.getPortata_min_C1().setScale(2).toPlainString(), i, 1);
				}
				if(i==2) 
				{
					modelLin.setValueAt(strumento.getPortataMaxCampo(campo,strumento.getId_tipo_strumento()).setScale(2).multiply(new BigDecimal("0.4")).setScale(2,RoundingMode.HALF_UP), i, 1);
				}
				if(i==3) 
				{
					modelLin.setValueAt(strumento.getPortataMaxCampo(campo,strumento.getId_tipo_strumento()).multiply(new BigDecimal("0.6")).toPlainString(), i, 1);
				}
				if(i==4) 
				{
					modelLin.setValueAt(strumento.getPortataMaxCampo(campo,strumento.getId_tipo_strumento()).multiply(new BigDecimal("0.8")).toPlainString(), i, 1);
				}
				if(i==5) 
				{
					modelLin.setValueAt(strumento.getPortataMaxCampo(campo,strumento.getId_tipo_strumento()), i, 1);
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

			modelLin.setValueAt(lin.getId(), i, 11);
		}


		JLabel lblNewLabel = new JLabel("Prova di Linearità");
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		pannelloLinearita.add(lblNewLabel, "cell 0 0 3 1");

		JLabel lblNewLabel_2 = new JLabel("(Rif.UNI CEI EN 45501:2015: A.4.4.1 - A.4.2.3)");
		lblNewLabel_2.setFont(new Font("Calibri", Font.PLAIN, 14));
		pannelloLinearita.add(lblNewLabel_2, "cell 0 0");

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

				if(column==1 || column==2 || column==4 ) 
				{
					int campo=comboBox_campo.getSelectedIndex();

					Object massa=modelLin.getValueAt(row, 1);
					Object indicazioneSalita=modelLin.getValueAt(row, 2);
					Object car_agg_salita=modelLin.getValueAt(row, 4);

					if(massa!=null && indicazioneSalita!=null && car_agg_salita!=null ) 
					{
						BigDecimal mas;
						BigDecimal indSalita;
						BigDecimal car_aggSalita;
						BigDecimal erroreSalita;
						BigDecimal erroreSalita_cor = null;
						

						try 
						{
							mas=new BigDecimal(massa.toString());
							indSalita=new BigDecimal(indicazioneSalita.toString());
							car_aggSalita=new BigDecimal(car_agg_salita.toString());
							BigDecimal err_div=getE(campo,strumento.getId_tipo_strumento(),mas);
							
							if(row==0) 
							{
								erroreSalita=indSalita.add(err_div.setScale(4).divide(new BigDecimal("2"), RoundingMode.HALF_UP)
										.subtract(car_aggSalita).subtract(mas));

								String mpe=getMPE(mas.toPlainString(), campo);
								erroreSalita_cor=BigDecimal.ZERO;
								modelLin.setValueAt(erroreSalita, row, 6);
								modelLin.setValueAt("0", row, 8);
							//	modelLin.setValueAt("0", row, 9);
								modelLin.setValueAt(mpe, row, 10);
							}

							else 
							{
								erroreSalita=indSalita.add(err_div.setScale(4).divide(new BigDecimal("2"), RoundingMode.HALF_UP)
										.subtract(car_aggSalita).subtract(mas));

							
								erroreSalita_cor=erroreSalita.subtract(new BigDecimal(modelLin.getValueAt(0, 6).toString()));
								erroreSalita_cor=erroreSalita_cor.setScale(4,RoundingMode.HALF_UP);
								String mpe=getMPE(mas.toPlainString(), campo);
								modelLin.setValueAt(erroreSalita, row, 6);
								modelLin.setValueAt(erroreSalita_cor, row, 8);
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

							linearita.setId(Integer.parseInt(modelLin.getValueAt(row, 11).toString()));
							linearita.setTipoAzzeramento(comboBox_tipo_azzeramento.getSelectedIndex());
							linearita.setCampo(comboBox_campo.getSelectedIndex()+1);
							linearita.setMassa(mas);
							linearita.setIndicazioneSalita(getValue(modelLin.getValueAt(row,2)));
							linearita.setIndicazioneDiscesa(getValue(modelLin.getValueAt(row,3)));
							linearita.setCaricoAggSalita(getValue(modelLin.getValueAt(row,4)));
							linearita.setCaricoAggDiscesa(getValue(modelLin.getValueAt(row,5)));
							linearita.setErroreSalita(getValue(modelLin.getValueAt(row,6)));
							linearita.setErroreDiscesa(getValue(modelLin.getValueAt(row,7)));
							linearita.setErroreCorSalita(getValue(modelLin.getValueAt(row,8)));
							linearita.setErroreCorDiscesa(getValue(modelLin.getValueAt(row,9)));
							linearita.setEsito(esito);
							linearita.setMpe(new BigDecimal(getMPE(mas.toPlainString(), campo)));

							GestioneMisuraBO.updateValoriLinearita(linearita, misura.getId());


						}
						catch (Exception e2) 
						{
							e2.printStackTrace();
						}


					}

				}
				
				if(column==1 ||  column==3 || column==5) 
				{
					int campo=comboBox_campo.getSelectedIndex();
					Object massa=modelLin.getValueAt(row, 1);
					Object indicazioneDiscesa=modelLin.getValueAt(row, 3);
					Object car_agg_discesa=modelLin.getValueAt(row, 5);

					if(massa!=null  && indicazioneDiscesa!=null && car_agg_discesa!=null) 
					{
						BigDecimal mas;
						BigDecimal indDiscesa;
						BigDecimal car_aggDiscesa;
						BigDecimal erroreDiscesa;
						BigDecimal erroreDiscesa_cor = null;

						try 
						{
							mas=new BigDecimal(massa.toString());
							
							indDiscesa=new BigDecimal(indicazioneDiscesa.toString());

							
							car_aggDiscesa=new BigDecimal(car_agg_discesa.toString());

							BigDecimal err_div=getE(campo,strumento.getId_tipo_strumento(),mas);
							
							if(row==0) 
							{
							
								erroreDiscesa=indDiscesa.add(err_div.setScale(4).divide(new BigDecimal("2"), RoundingMode.HALF_UP)
										.subtract(car_aggDiscesa).subtract(mas));

								String mpe=getMPE(mas.toPlainString(), campo);
								
								modelLin.setValueAt(erroreDiscesa.toPlainString(), row, 7);
								
								erroreDiscesa_cor=BigDecimal.ZERO;
							
								for (int i = 0; i <modelLin.getRowCount(); i++) 
								{
									if(modelLin.getValueAt(i, 7)!=null) 
									{
										erroreDiscesa_cor=new BigDecimal(modelLin.getValueAt(i, 7).toString()).subtract(erroreDiscesa);
										erroreDiscesa_cor=	erroreDiscesa_cor.setScale(4,RoundingMode.HALF_UP);
										modelLin.setValueAt(erroreDiscesa_cor.toPlainString(), i, 9);
										int id =Integer.parseInt(modelLin.getValueAt(i, 11).toString());									
										GestioneMisuraBO.updateErrore_correttoDiscesa(id,erroreDiscesa_cor);	
									}
								 	
									
								}
								modelLin.setValueAt("0", row, 9);
								modelLin.setValueAt(mpe, row, 10);
							}

							else 
							{
							
								erroreDiscesa=indDiscesa.add(err_div.setScale(4).divide(new BigDecimal("2"), RoundingMode.HALF_UP)
										.subtract(car_aggDiscesa).subtract(mas));
								
								String mpe=getMPE(mas.toPlainString(), campo);
								modelLin.setValueAt(erroreDiscesa, row, 7);
								
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

							linearita.setId(Integer.parseInt(modelLin.getValueAt(row, 11).toString()));
							linearita.setTipoAzzeramento(comboBox_tipo_azzeramento.getSelectedIndex());
							linearita.setCampo(comboBox_campo.getSelectedIndex()+1);
							linearita.setMassa(mas);
							
							linearita.setIndicazioneSalita(getValue(modelLin.getValueAt(row,2)));
							linearita.setIndicazioneDiscesa(getValue(modelLin.getValueAt(row,3)));
							linearita.setCaricoAggSalita(getValue(modelLin.getValueAt(row,4)));
							linearita.setCaricoAggDiscesa(getValue(modelLin.getValueAt(row,5)));
							linearita.setErroreSalita(getValue(modelLin.getValueAt(row,6)));
							linearita.setErroreDiscesa(getValue(modelLin.getValueAt(row,7)));
							linearita.setErroreCorSalita(getValue(modelLin.getValueAt(row,8)));
							linearita.setErroreCorDiscesa(getValue(modelLin.getValueAt(row,9)));
							
							linearita.setEsito(esito);
							linearita.setMpe(new BigDecimal(getMPE(mas.toPlainString(), campo)));

							GestioneMisuraBO.updateValoriLinearita(linearita, misura.getId());


						}
						catch (Exception e2) 
						{
							e2.printStackTrace();
						}


					}

				}

			}

			private BigDecimal getValue(Object valueAt) {
				
				if(valueAt==null) 
				{
					return null;
				}else 
				{
					return new BigDecimal(valueAt.toString());
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
					if(i==0 && mpe!=null && err_sal!=null && err_dis!=null && err_cor_sal!=null && err_dis!=null ) 
					{
						if(Math.abs(Double.parseDouble(err_sal.toString())) > Math.abs(Double.parseDouble(mpe.toString())) ||
								Math.abs(Double.parseDouble(err_dis.toString())) > Math.abs(Double.parseDouble(mpe.toString()))	) 
						{
							return "NEGATIVO";
						} 
					}

					if(i>0 && mpe!=null && err_sal!=null && err_dis!=null && err_cor_sal!=null && err_cor_dis!=null) 
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

		pannelloAccuratezza.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 2, true), "Prova Accuratezza", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pannelloAccuratezza.setBackground(Color.WHITE);
		pannelloAccuratezza.setLayout(new MigLayout("", "[22.00][][grow]", "[][][][][][][][][13.00][][13.00][]"));

		tableAcc = new JTable();

		modelAccuratezza = new ModelAccuratezza(strumento.getUm());

		tableAcc.setModel(modelAccuratezza);

		tableAcc.setRowHeight(25);
		tableAcc.setFont(new Font("Arial", Font.PLAIN, 12));
		tableAcc.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

		tableAcc.setDefaultRenderer(Object.class, new MyCellRenderer());


		TableColumn column = tableAcc.getColumnModel().getColumn(tableAcc.getColumnModel().getColumnIndex("id"));
		tableAcc.removeColumn(column);



		JLabel lblNewLabel = new JLabel("Prova di accuratezza del dispositivo di tara");
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		pannelloAccuratezza.add(lblNewLabel, "cell 0 0 3 1");

		JLabel lblNewLabel_2 = new JLabel("(Rif.UNI CEI EN 45501:2015: A.4.6.1 - A.4.6.2)");
		lblNewLabel_2.setFont(new Font("Calibri", Font.PLAIN, 14));
		pannelloAccuratezza.add(lblNewLabel_2, "cell 0 0");

		JLabel lblTipoDispositivoDi = new JLabel("Tipo dispositivo di tara");
		lblTipoDispositivoDi.setFont(new Font("Arial", Font.BOLD, 12));
		pannelloAccuratezza.add(lblTipoDispositivoDi, "cell 1 2,alignx trailing");

		final JComboBox comboBox_tipo_azzeramento = new JComboBox();
		comboBox_tipo_azzeramento.setModel(new DefaultComboBoxModel(new String[] {"Non automatico o semiautomatico","Automatico"}));
		comboBox_tipo_azzeramento.setFont(new Font("Arial", Font.PLAIN, 12));
		pannelloAccuratezza.add(comboBox_tipo_azzeramento, "cell 2 2");

		comboBox_tipo_azzeramento.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(comboBox_tipo_azzeramento.getSelectedIndex()==0) 
				{
					modelAccuratezza.setValueAt("0", 0, 1);
				}
				else 
				{
					
					if(comboBox_campo.getSelectedIndex()==0) 
					{
					BigDecimal eDiv=strumento.getDiv_ver_C1().multiply(BigDecimal.TEN);
					modelAccuratezza.setValueAt(eDiv.toPlainString(), 0, 1);
					}
					if(comboBox_campo.getSelectedIndex()==1) 
					{
					BigDecimal eDiv=strumento.getDiv_ver_C2().multiply(BigDecimal.TEN);
					modelAccuratezza.setValueAt(eDiv.toPlainString(), 0, 1);
					}
					if(comboBox_campo.getSelectedIndex()==2) 
					{
					BigDecimal eDiv=strumento.getDiv_ver_C3().multiply(BigDecimal.TEN);
					modelAccuratezza.setValueAt(eDiv.toPlainString(), 0, 1);
					}
					}

			}
		});



		JScrollPane scrollTab = new JScrollPane(tableAcc);

		pannelloAccuratezza.add(scrollTab, "cell 1 4 2 1,width :800:,height :75:,aligny top");


		VerAccuratezzaDTO ver = (VerAccuratezzaDTO)misura.getVerAccuratezzas(comboBox_campo.getSelectedIndex()+1).get(0);

		modelAccuratezza.addRow(new Object[0]);
		modelAccuratezza.setValueAt("Et", 0, 0);


		if(ver.getMassa()!=null) 
		{
			modelAccuratezza.setValueAt(ver.getMassa(), 0, 1);
		}else 
		{
			modelAccuratezza.setValueAt("0", 0, 1);
		}
		
		if(ver.getIndicazione()!=null) 
		{
			modelAccuratezza.setValueAt(ver.getIndicazione(),0, 2);
		}
		if(ver.getCaricoAgg()!=null) 
		{
			modelAccuratezza.setValueAt(ver.getCaricoAgg(), 0, 3);
		}
		if(ver.getErrore()!=null) 
		{
			modelAccuratezza.setValueAt(ver.getErrore(), 0, 4);
		}
		if(ver.getErroreCor()!=null) 
		{
			modelAccuratezza.setValueAt(ver.getErroreCor(), 0, 5);
		}
		if(ver.getMpe()!=null) 
		{
			modelAccuratezza.setValueAt(ver.getMpe(), 0, 6);
		}

		modelAccuratezza.setValueAt(ver.getId(), 0, 7);


		if(ver.getTipoTara()!=0) 
		{
			if(ver.getTipoTara()==1) 
			{
				comboBox_tipo_azzeramento.setSelectedIndex(1);
			}
			else 
			{
				comboBox_tipo_azzeramento.setSelectedIndex(1);
			}
		}

		JLabel lblEsito = new JLabel("ESITO:");
		lblEsito.setFont(new Font("Arial", Font.BOLD, 12));
		pannelloAccuratezza.add(lblEsito, "cell 1 6,alignx left");





		textField_esito_acc = new JTextField();
		textField_esito_acc.setEditable(false);
		textField_esito_acc.setBackground(Color.YELLOW);
		textField_esito_acc.setFont(new Font("Arial", Font.PLAIN, 12));
		textField_esito_acc.setColumns(10);
		pannelloAccuratezza.add(textField_esito_acc, "cell 1 6,width :100:");

		if(ver.getEsito()!=null) 
		{
			if(ver.getEsito().equals("POSITIVO")) 
			{
				textField_esito_acc.setBackground(Color.GREEN);
			}else 
			{
				textField_esito_acc.setBackground(Color.RED);
			}
			textField_esito_acc.setText(ver.getEsito());
		}


		tableAcc.getModel().addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {

				int row = e.getFirstRow();
				int column=e.getColumn();

				if(column==1 || column==2 || column==3 ) 
				{
					int campo=comboBox_campo.getSelectedIndex();

					Object massa=modelAccuratezza.getValueAt(row, 1);
					Object indicazione=modelAccuratezza.getValueAt(row, 2);
					Object car_agg=modelAccuratezza.getValueAt(row, 3);

					if(massa!=null && indicazione!=null && indicazione!=null && car_agg!=null) 
					{
						BigDecimal mas;
						BigDecimal indicaz;
						BigDecimal car_aggiuntivo;

						try 
						{
							mas=new BigDecimal(massa.toString());
							indicaz=new BigDecimal(indicazione.toString());
							car_aggiuntivo=new BigDecimal(car_agg.toString());

							BigDecimal e2=getE(campo,strumento.getId_tipo_strumento(),mas).setScale(5).divide(new BigDecimal(2).setScale(5), RoundingMode.HALF_UP);

							BigDecimal errore=indicaz.add(e2).subtract(car_aggiuntivo).subtract(mas);


							String mpe=getMPE(mas.toPlainString(), campo);

							modelAccuratezza.setValueAt(errore.setScale(4,RoundingMode.HALF_DOWN).toPlainString(), 0, 4);
							modelAccuratezza.setValueAt("0", 0, 5);
							modelAccuratezza.setValueAt(mpe, 0, 6);

							String esito=valutaEsitoAccuratezza();

							if(esito.equals("POSITIVO")) 
							{
								textField_esito_acc.setBackground(Color.GREEN);
							}
							else 
							{
								textField_esito_acc.setBackground(Color.RED);	
							}
							textField_esito_acc.setText(esito);

							VerAccuratezzaDTO acc= new VerAccuratezzaDTO();

							acc.setTipoTara(comboBox_tipo_azzeramento.getSelectedIndex());
							acc.setCampo(comboBox_campo.getSelectedIndex()+1);
							acc.setMassa(mas);
							acc.setIndicazione(indicaz);
							acc.setCaricoAgg(car_aggiuntivo);
							acc.setErrore(errore);
							acc.setErroreCor(BigDecimal.ZERO);
							acc.setMpe(new BigDecimal(mpe));
							acc.setEsito(esito);
							acc.setId(Integer.parseInt(modelAccuratezza.getValueAt(0, 7).toString()));

							GestioneMisuraBO.updateAccuratezzaTara(acc,misura.getId());

						}
						catch (Exception ex) {
							ex.printStackTrace();
						}
					}

				}
			}

			private String valutaEsitoAccuratezza() {

				if(Math.abs(Double.parseDouble(modelAccuratezza.getValueAt(0, 4).toString()))>Math.abs(Double.parseDouble(modelAccuratezza.getValueAt(0, 6).toString()))) 
				{
					return "NEGATIVO";
				}
				else
				{
					return "POSITIVO";
				}


			}
		});

		return pannelloAccuratezza;
	}

	private JPanel costruisciPannelloMobilita() {

		JPanel pannelloMobilita= new JPanel();

		pannelloMobilita.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 2, true), "Prova Mobilità", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pannelloMobilita.setBackground(Color.WHITE);
		pannelloMobilita.setLayout(new MigLayout("", "[22.00][][grow]", "[][][][][][][][][][13.00][]"));

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


		TableColumn column = tabellaMobilita1.getColumnModel().getColumn(tabellaMobilita1.getColumnModel().getColumnIndex("id"));
		tabellaMobilita1.removeColumn(column);



		column = tabellaMobilita2.getColumnModel().getColumn(tabellaMobilita2.getColumnModel().getColumnIndex("id"));
		tabellaMobilita2.removeColumn(column);


		tabellaMobilita2.getColumnModel().getColumn(3).setHeaderValue("Carico Agg=|MPE|  ΔL ("+strumento.getUm()+")");
		tabellaMobilita2.getColumnModel().getColumn(6).setHeaderValue("0,7·Carico aggiuntivo 0,7·MPE ("+strumento.getUm()+")");

		tabellaMobilita2.repaint();

		JLabel label = new JLabel("ESITO:");
		label.setFont(new Font("Arial", Font.BOLD, 12));
		pannelloMobilita.add(label, "flowx,cell 1 4");

		JLabel lblCaso_1 = new JLabel("Caso 2) - Strumenti ad equilibrio automatico o semiautomatico (con indicazione analogica)");
		lblCaso_1.setFont(new Font("Arial", Font.BOLD, 12));
		pannelloMobilita.add(lblCaso_1, "cell 1 6");
		JScrollPane scrollTab2 = new JScrollPane(tabellaMobilita2);

		pannelloMobilita.add(scrollTab2, "cell 1 7,width :800:,height :100:,aligny top");


		JLabel lblEsito = new JLabel("ESITO:");
		lblEsito.setFont(new Font("Arial", Font.BOLD, 12));
		pannelloMobilita.add(lblEsito, "flowx,cell 1 8,alignx left");

		textField_esito_mob_1 = new JTextField();
		textField_esito_mob_1.setFont(new Font("Arial", Font.PLAIN, 12));
		textField_esito_mob_1.setEditable(false);
		textField_esito_mob_1.setColumns(10);
		textField_esito_mob_1.setBackground(Color.YELLOW);
		pannelloMobilita.add(textField_esito_mob_1, "cell 1 4,width :100:");

		textField_esito_mob_2 = new JTextField();
		textField_esito_mob_2.setEditable(false);
		textField_esito_mob_2.setBackground(Color.YELLOW);
		textField_esito_mob_2.setFont(new Font("Arial", Font.PLAIN, 12));
		textField_esito_mob_2.setColumns(10);
		pannelloMobilita.add(textField_esito_mob_2, "cell 1 8,width :100:");

		ArrayList<VerMobilitaDTO> listaMobilita1=(ArrayList<VerMobilitaDTO>)misura.getVerMobilitas(comboBox_campo.getSelectedIndex()+1, 1);

		int campo=comboBox_campo.getSelectedIndex()+1;
		for (int i = 0; i <3; i++) {

			VerMobilitaDTO mob=listaMobilita1.get(i);

			if(mob.getMassa()!=null) 
			{
				modelMobilita_1.setValueAt(mob.getMassa(),i, 1);
			}
			else 
			{
				if(i==0) 
				{	
					modelMobilita_1.setValueAt(strumento.getPortataMinCampo(campo, strumento.getId_tipo_strumento()).toPlainString(),i, 1);
					
					String mpe=getMPE(strumento.getPortataMinCampo(campo, strumento.getId_tipo_strumento()).toPlainString(),comboBox_campo.getSelectedIndex());

					BigDecimal carr_agg=new BigDecimal(mpe).multiply(new BigDecimal("0.4"));
					
					modelMobilita_1.setValueAt(carr_agg.toPlainString(),i,3);
					
				}
				if(i==1) 
				{
					modelMobilita_1.setValueAt(strumento.getPortataMaxCampo(campo, strumento.getId_tipo_strumento()).setScale(2).divide(new BigDecimal("2"),RoundingMode.HALF_DOWN).setScale(2, RoundingMode.HALF_DOWN).toPlainString(),i, 1);
					
					String mpe=getMPE(strumento.getPortataMaxCampo(campo, strumento.getId_tipo_strumento()).setScale(2).divide(new BigDecimal("2"),RoundingMode.HALF_DOWN).setScale(2, RoundingMode.HALF_DOWN).toPlainString(),comboBox_campo.getSelectedIndex());

					BigDecimal carr_agg=new BigDecimal(mpe).multiply(new BigDecimal("0.4"));
					
					modelMobilita_1.setValueAt(carr_agg.toPlainString(),i,3);
					
				
				}
				if(i==2) 
				{
					modelMobilita_1.setValueAt(strumento.getPortataMaxCampo(campo, strumento.getId_tipo_strumento()).toPlainString(),i, 1);
					
					String mpe=getMPE(strumento.getPortataMaxCampo(campo, strumento.getId_tipo_strumento()).toPlainString(),comboBox_campo.getSelectedIndex());

					BigDecimal carr_agg=new BigDecimal(mpe).multiply(new BigDecimal("0.4"));
					
					modelMobilita_1.setValueAt(carr_agg.toPlainString(),i,3);
				}
				
			

			}
			if(mob.getIndicazione()!=null) 
			{
				modelMobilita_1.setValueAt(mob.getIndicazione(),i, 2);
			}
			if(mob.getCaricoAgg()!=null) 
			{
				modelMobilita_1.setValueAt(mob.getCaricoAgg(),i, 3);
			}
			if(mob.getPostIndicazione()!=null) 
			{
				modelMobilita_1.setValueAt(mob.getPostIndicazione(),i, 4);
			}
			if(mob.getDifferenziale()!=null) 
			{
				modelMobilita_1.setValueAt(mob.getDifferenziale(),i, 5);
			}
			if(mob.getDivisione()!=null) 
			{
				modelMobilita_1.setValueAt(mob.getDivisione(),i, 6);
			}
			if(mob.getCheck()!=null) 
			{
				modelMobilita_1.setValueAt(mob.getCheck(),i, 7);
			}
			modelMobilita_1.setValueAt(mob.getId(), i, 8);
		}

		if(listaMobilita1.get(0).getEsito()!=null) 
		{
			if(listaMobilita1.get(0).getEsito().equals("POSITIVO")) 
			{
				textField_esito_mob_1.setBackground(Color.GREEN);
			}else 
			{
				textField_esito_mob_1.setBackground(Color.RED);
			}
			textField_esito_mob_1.setText(listaMobilita1.get(0).getEsito());
		}


		ArrayList<VerMobilitaDTO> listaMobilita2=(ArrayList<VerMobilitaDTO>)misura.getVerMobilitas(comboBox_campo.getSelectedIndex()+1, 2);

		for (int i = 0; i <3; i++) {

			VerMobilitaDTO mob=listaMobilita2.get(i);

			if(mob.getMassa()!=null) 
			{
				modelMobilita_2.setValueAt(mob.getMassa(),i, 1);
			}
			else 
			{
				if(i==0) 
				{	
					modelMobilita_2.setValueAt(strumento.getPortataMinCampo(campo, strumento.getId_tipo_strumento()).toPlainString(),i, 1);
					
					String mpe=getMPE(strumento.getPortataMinCampo(campo, strumento.getId_tipo_strumento()).toPlainString(),comboBox_campo.getSelectedIndex());

					modelMobilita_2.setValueAt(mpe,i,3);
					
				}
				if(i==1) 
				{
					modelMobilita_2.setValueAt(strumento.getPortataMaxCampo(campo, strumento.getId_tipo_strumento()).setScale(2).divide(new BigDecimal("2"),RoundingMode.HALF_DOWN).setScale(2, RoundingMode.HALF_DOWN).toPlainString(),i, 1);
				
					String mpe=getMPE(strumento.getPortataMaxCampo(campo, strumento.getId_tipo_strumento()).setScale(2).divide(new BigDecimal("2"),RoundingMode.HALF_DOWN).setScale(2, RoundingMode.HALF_DOWN).toPlainString(),comboBox_campo.getSelectedIndex());

					modelMobilita_2.setValueAt(mpe,i,3);
					
				}
				if(i==2) 
				{
					modelMobilita_2.setValueAt(strumento.getPortataMaxCampo(campo, strumento.getId_tipo_strumento()).toPlainString(),i, 1);
					
					String mpe=getMPE(strumento.getPortataMaxCampo(campo, strumento.getId_tipo_strumento()).toPlainString(),comboBox_campo.getSelectedIndex());
		
					modelMobilita_2.setValueAt(mpe,i,3);
					
				}
				

			}
			if(mob.getIndicazione()!=null) 
			{
				modelMobilita_2.setValueAt(mob.getIndicazione(),i, 2);
			}
			if(mob.getCaricoAgg()!=null) 
			{
				modelMobilita_2.setValueAt(mob.getCaricoAgg(),i, 3);
			}
			if(mob.getPostIndicazione()!=null) 
			{
				modelMobilita_2.setValueAt(mob.getPostIndicazione(),i, 4);
			}
			if(mob.getDifferenziale()!=null) 
			{
				modelMobilita_2.setValueAt(mob.getDifferenziale(),i, 5);
			}
			if(mob.getDivisione()!=null) 
			{
				modelMobilita_2.setValueAt(mob.getDivisione(),i, 6);
			}
			if(mob.getCheck()!=null) 
			{
				modelMobilita_2.setValueAt(mob.getCheck(),i, 7);
			}
			modelMobilita_2.setValueAt(mob.getId(), i, 8);
		}

		if(listaMobilita2.get(0).getEsito()!=null) 
		{
			if(listaMobilita2.get(0).getEsito().equals("POSITIVO")) 
			{
				textField_esito_mob_2.setBackground(Color.GREEN);
			}else 
			{
				textField_esito_mob_2.setBackground(Color.RED);
			}
			textField_esito_mob_2.setText(listaMobilita2.get(0).getEsito());
		}


		/*CASO 1*/
		tabellaMobilita1.getModel().addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {

				int row = e.getFirstRow();
				int column=e.getColumn();

				if(column==2 || column==4 ) 
				{

					Object indicazione=modelMobilita_1.getValueAt(row, 2);
					Object post_indicazione=modelMobilita_1.getValueAt(row, 4);

					try 
					{

						if(indicazione!=null && post_indicazione!=null) 
						{	
							BigDecimal indicazioneTab=new BigDecimal (indicazione.toString());
							BigDecimal post_indicazioneTab=new BigDecimal (post_indicazione.toString());

							String mpe=getMPE(modelMobilita_1.getValueAt(row, 1).toString(),comboBox_campo.getSelectedIndex());

							BigDecimal carr_agg=new BigDecimal(mpe).multiply(new BigDecimal("0.4"));

							modelMobilita_1.setValueAt(carr_agg.toPlainString(), row, 3);

							BigDecimal diff=post_indicazioneTab.subtract(indicazioneTab).setScale(4, RoundingMode.HALF_DOWN);

							modelMobilita_1.setValueAt(diff, row, 5);

							BigDecimal divisione=strumento.getDivisioneReale(comboBox_campo.getSelectedIndex()+1,strumento.getId_tipo_strumento(),new BigDecimal(modelMobilita_1.getValueAt(row, 1).toString()).doubleValue());

							modelMobilita_1.setValueAt(divisione, row, 6);

							String check="";
							if(Math.abs(diff.doubleValue())>=Math.abs(divisione.doubleValue())) 
							{
								modelMobilita_1.setValueAt("OK", row, 7);
								check="OK";
							}
							else 
							{
								modelMobilita_1.setValueAt("KO", row, 7);
								check="KO";
							}

							String esito =valutaEsitoMobilita(modelMobilita_1);

							if(esito.equals("POSITIVO")) 
							{
								textField_esito_mob_1.setBackground(Color.GREEN);
							}
							else 
							{
								textField_esito_mob_1.setBackground(Color.RED);	
							}
							textField_esito_mob_1.setText(esito);

							VerMobilitaDTO mob = new VerMobilitaDTO();

							mob.setId(Integer.parseInt(modelMobilita_1.getValueAt(row, 8).toString()));
							mob.setCampo(comboBox_campo.getSelectedIndex()+1);
							mob.setCaso(1);
							mob.setMassa(new BigDecimal(modelMobilita_1.getValueAt(row, 1).toString()));
							mob.setIndicazione(indicazioneTab);
							mob.setCaricoAgg(carr_agg);
							mob.setPostIndicazione(post_indicazioneTab);
							mob.setDifferenziale(diff);
							mob.setDivisione(divisione);
							mob.setCheck(check);
							mob.setEsito(esito);


							GestioneMisuraBO.updateValoriMobilita(mob,misura.getId());

						}
					}catch (Exception ex) 
					{
						ex.printStackTrace();
					}
				}
			}



		});

		/*CASO 2*/			 
		tabellaMobilita2.getModel().addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {

				int row = e.getFirstRow();
				int column=e.getColumn();

				if(column==2 || column==4 ) 
				{

					Object indicazione=modelMobilita_2.getValueAt(row, 2);
					Object post_indicazione=modelMobilita_2.getValueAt(row, 4);

					try 
					{

						if(indicazione!=null && post_indicazione!=null) 
						{	
							BigDecimal indicazioneTab=new BigDecimal (indicazione.toString());
							BigDecimal post_indicazioneTab=new BigDecimal (post_indicazione.toString());

							String mpe=getMPE(modelMobilita_2.getValueAt(row, 1).toString(),comboBox_campo.getSelectedIndex());

							BigDecimal carr_agg=new BigDecimal(mpe);

							modelMobilita_2.setValueAt(carr_agg.toPlainString(), row, 3);

							BigDecimal diff=post_indicazioneTab.subtract(indicazioneTab).setScale(4, RoundingMode.HALF_DOWN);

							modelMobilita_2.setValueAt(diff, row, 5);

							BigDecimal car_agg_07=carr_agg.multiply(new BigDecimal("0.7"));

							modelMobilita_2.setValueAt(car_agg_07, row, 6);

							String check="";

							if(diff.doubleValue()>=car_agg_07.doubleValue()) 
							{
								modelMobilita_2.setValueAt("OK", row, 7);
								check="OK";
							}
							else 
							{
								modelMobilita_2.setValueAt("KO", row, 7);
								check="KO";
							}

							String esito =valutaEsitoMobilita(modelMobilita_2);

							if(esito.equals("POSITIVO")) 
							{
								textField_esito_mob_2.setBackground(Color.GREEN);
							}
							else 
							{
								textField_esito_mob_2.setBackground(Color.RED);	
							}
							textField_esito_mob_2.setText(esito);

							VerMobilitaDTO mob = new VerMobilitaDTO();

							mob.setId(Integer.parseInt(modelMobilita_2.getValueAt(row, 8).toString()));
							mob.setCampo(comboBox_campo.getSelectedIndex()+1);
							mob.setCaso(2);
							mob.setMassa(new BigDecimal(modelMobilita_2.getValueAt(row, 1).toString()));
							mob.setIndicazione(indicazioneTab);
							mob.setCaricoAgg(carr_agg);
							mob.setPostIndicazione(post_indicazioneTab);
							mob.setDifferenziale(diff);
							mob.setDivisione(car_agg_07);
							mob.setCheck(check);
							mob.setEsito(esito);


							GestioneMisuraBO.updateValoriMobilita(mob,misura.getId());

						}	
					}catch (Exception ex) 
					{
						ex.printStackTrace();
					}
				}
			}



		});





		return pannelloMobilita;
	}

	private String valutaEsitoMobilita(ModelMobilita modelMobilita) {

		String esito ="POSITIVO";

		for (int i = 0; i < modelMobilita.getRowCount(); i++) {

			Object o =modelMobilita.getValueAt(i, 7);

			if(o!=null && o.toString().equals("KO")) 
			{
				return "NEGATIVO";
			}
		}

		return esito;
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


		JButton btnSave = new JButton("Salva");
		panel_m_build.add(btnSave, "cell 0 0,alignx center,height : 50:");
		btnSave.setIcon(new ImageIcon(PannelloMisuraMaster.class.getResource("/image/save.png")));
		btnSave.setFont(new Font("Arial", Font.BOLD, 16));



		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try
				{
					boolean save=true;
					String sequence="";
					
					
					if(!chckbx.isSelected()) 
					{
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

						if(comboBox_tipo_verifica.getSelectedIndex()>0 && comboBox_motivo.getSelectedIndex()>0 &&dlm.size()>0) 
						{
							misura.setSeqRisposte(sequence.substring(0, sequence.length()-1));
							misura.setTipo_verifica(comboBox_tipo_verifica.getSelectedIndex());
							misura.setMotivo_verifica(comboBox_motivo.getSelectedIndex());
							
							misura.setNomeRiparatore(textField_nomeRiparatore.getText());
							
							if(Utility.isDate(textField_dataRiparazione.getText())) 
							{
								misura.setDataRiparazione(textField_dataRiparazione.getText());
							}
							
							if(chckbx.isSelected()) 
							{
								misura.setIs_difetti("S");
							}else 
							{
								misura.setIs_difetti("N");
							}
							
							misura.setCampioniLavoro(componiCampioni(dlm));
							GestioneMisuraBO.updateMisura(misura);
						}else 
						{
							JOptionPane.showMessageDialog(null,"Selezionare tipo verifica, motivo verifica e almeno un campione di lavoro","Attenzione",JOptionPane.WARNING_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
						}
					}
					}else 
					{
						if(comboBox_tipo_verifica.getSelectedIndex()>0 && comboBox_motivo.getSelectedIndex()>0)
						{
							misura.setTipo_verifica(comboBox_tipo_verifica.getSelectedIndex());
							misura.setMotivo_verifica(comboBox_motivo.getSelectedIndex());
							misura.setNomeRiparatore(textField_nomeRiparatore.getText());
							
							if(Utility.isDate(textField_dataRiparazione.getText())) 
							{
								misura.setDataRiparazione(textField_dataRiparazione.getText());
							}
							
							misura.setIs_difetti("S");
							GestioneMisuraBO.updateMisura(misura);
						}else 
						{
							JOptionPane.showMessageDialog(null,"Selezionare tipo verifica e motivo verifica","Attenzione",JOptionPane.WARNING_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
						}
						
					}
				}	
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}

			private String componiCampioni(DefaultListModel<String> dlm) {
				String toReturn="";
				for (int i=0;i<dlm.size();i++) {

					toReturn=toReturn+dlm.getElementAt(i).toString()+";";

				}
				return toReturn.substring(0,toReturn.length()-1);
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

					
						try 
						{
							//Controllo completamento dati
							
							misura=GestioneMisuraBO.getMisura(misura.getId());

							if(misura.getIs_difetti().equals("S")) 
							{
								String dataScadenza=GestioneMisuraBO.getDataScadenzaMisura(misura,strumento.getFreq_mesi());
								
								
								GestioneMisuraBO.terminaMisura(misura.getId(),dataScadenza);


								JOptionPane.showMessageDialog(null,"Salvataggio effettuato","Salvataggio",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/confirm.png")));
							}
							else 
							{
								if(misura.getFile_inizio_prova()!=null && misura.getFile_fine_prova()!=null) 
								{
									String dataScadenza=GestioneMisuraBO.getDataScadenzaMisura(misura,strumento.getFreq_mesi());
									
									
									GestioneMisuraBO.terminaMisura(misura.getId(),dataScadenza);


									JOptionPane.showMessageDialog(null,"Salvataggio effettuato","Salvataggio",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/confirm.png")));
									
								}
								else 
								{
									JOptionPane.showMessageDialog(null,"Per terminare la misura inserire le foto","Salvataggio",JOptionPane.ERROR_MESSAGE,new ImageIcon(PannelloTOP.class.getResource("/image/attention.png")));
								}
								
							}
						
							
							



						} catch (Exception e) {
							e.printStackTrace();
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

		if(flag==true) 
		{
			if(column==2 || column==3)
			{
				return true;
			}else
			{
				return false;
			}
		}else 
		{
			if(row%2==0 && row!=0) 
			{
				return false;
			}else 
			{
				if(column==2 || column==3)
				{
					return true;
				}else
				{
					return false;
				}
			}
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
			case 8:
				return String.class;
			case 9:
				return String.class;
			case 10:
				return String.class;
			case 11:
				return String.class;
			default:
				return String.class;
			}
		}

		@Override
		public boolean isCellEditable(int row, int column) {

			if(column==1 || column==2 || column==3 || column==4 || column==5)
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

	class ModelMobilita extends DefaultTableModel {


		public ModelMobilita(String um) 
		{
			addColumn("Carico");
			addColumn("Massa L ("+um+")");
			addColumn("Indicazione L1 ("+um+")");
			addColumn("Carico Agg=0,4 . |MPE|  ΔL ("+um+")");
			addColumn("Indicazione L2 ("+um+")");
			addColumn("Differenza L2 - L1("+um+")");
			addColumn("Div. reale strumento d ("+um+")");
			addColumn("Check |L2-L1|≥ d");
			addColumn("id");

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
			case 8:
				return String.class;
			default:
				return String.class;
			}
		}

		@Override
		public boolean isCellEditable(int row, int column) {

			if(column==1 || column==2 || column==4)
			{
				return true;
			}else
			{
				return false;
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
				table.getCellEditor(row, column).stopCellEditing();
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



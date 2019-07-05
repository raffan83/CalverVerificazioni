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
	private JTextField denominazioneField;
	private JTextField cod_int_field;
	private JTextField construct_field;
	private JTextField model_field;
	private JTextField matricola_field;
	private JTextField freq_field;
	private JTextField res_field;
	private JTextField campo_misura_field;
	private JTextField id_str_field;
	private JTextField proc_field;
	private JTextArea textAreaNote;
	private JButton btnSalva;
	static JTree tree =null;
	JPopupMenu popupMenu,popupMenuMisura;
	JMenuItem jmit,jmit1,jmitMisura;
	int current;
	
	private JSlider slider_1=null;
	private JSlider slider_2=null;
	private JSlider slider_3=null;
	private JSlider slider_4=null;
	private JSlider slider_5=null;
	private JSlider slider_6=null;
	

	JLabel lblTipoStrumento;
	private JTextField id_field;
	private JTextField address_field;
	private JTextField reparto_field;
	private JTextField utilizzatore_field;
	private JTextField textField_com_port;
	private JTextField textField_delay;
	
	private Splash spl=null;
	JPanel panel_dati_misura;
	JPanel panel_struttura;
	JPanel panel_avvio;
	JPanel panel_stampa;
	private JPanel panel_tabella,panel_controllo_visuale;
	JSplitPane splitPane;


	VerStrumentoDTO strumento;
	
	private BufferedImage img;
	static JFrame myFrame=null;
	JTable table_dati_strumento=null;
	JTable tabellaSecurTest=null;
	private JTable tabellaDomandePreliminari;
	JFrame f;
	ArrayList<ButtonGroup> listaRisposte = new ArrayList<>();
	private JTable table;
	public PannelloMisuraMaster(String id) throws Exception
	{
		SessionBO.prevPage="PSS";
		current=-1;
		strumento=GestioneStrumentoVER_BO.getStrumento(SessionBO.idStrumento);


		myFrame=SessionBO.generarFrame;
		

		this.setLayout(new MigLayout("","[grow]","[grow]"));

		JPanel masterPanel = new JPanel();
		
		masterPanel.setLayout(new MigLayout("", "[grow]", "[70%,grow][15%]"));


		
		panel_controllo_visuale=costruisciPanelloLinearita();
		panel_tabella = costruisciTabella();
		panel_struttura =creaPanelStruttura();
		

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		tabbedPane.addTab("Misura",panel_tabella);
		
		tabbedPane.addTab("Controllo Visivo", panel_controllo_visuale);
		masterPanel.add(tabbedPane, "cell 0 0,grow");
		masterPanel.add(panel_struttura,"cell 0 1,grow");


		double height=(SessionBO.heightFrame*73)/100;
		double width=(SessionBO.widthFrame*70)/100;

		masterPanel.setPreferredSize(new Dimension((int)width-50,(int) height/2));

		JScrollPane scroll= new JScrollPane(masterPanel);

		this.add(scroll, "cell 0 0,grow");



		


	}

	
	private JPanel costruisciPanelloLinearita() {
		
		JPanel pannello= new JPanel();
		
		pannello.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 2, true), "Condizioni visive", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pannello.setBackground(Color.WHITE);
		pannello.setLayout(new MigLayout("", "[][grow]", "[][][grow]"));
		
		JLabel lblNewLabel = new JLabel("Prova di Ripetibilit\u00E0");
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		pannello.add(lblNewLabel, "flowx,cell 1 0");
		
		JLabel lblNewLabel_2 = new JLabel("(Rif.UNI CEI EN 45501:2015: A.4.10)");
		lblNewLabel_2.setFont(new Font("Calibri", Font.PLAIN, 14));
		pannello.add(lblNewLabel_2, "cell 1 0");
		
		table = new JTable();
		
		ModelLinearita modelLin = new ModelLinearita();
		
		table.setModel(modelLin);
		
		table.setRowHeight(25);
		table.setFont(new Font("Arial", Font.PLAIN, 14));
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
		
		table.setDefaultRenderer(Object.class, new MyCellRenderer());
		
		
		if (strumento.getClasse()==1 || strumento.getClasse()==2) 
		{
			for (int i = 0; i < 6; i++) {
				
				modelLin.addRow(new Object[0]);
				modelLin.setValueAt(i+1, i, 0);
			}
		}
		else 
		{	
				for (int i = 0; i < 3; i++) {
				
					modelLin.addRow(new Object[0]);
					modelLin.setValueAt(i+1, i, 0);
				}
		}
		
		
		
		pannello.add(table, "cell 1 2,alignx left,aligny top");

		return pannello;
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
		panel_tabella.setLayout(new MigLayout("", "[grow][][]", "[][][grow]"));

		
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
			
			System.out.println(y);
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


		JButton btnStampa = new JButton("Etichetta");
		panel_m_build.add(btnStampa, "cell 0 0,alignx center,height : 50:");
		btnStampa.setIcon(new ImageIcon(PannelloTOP.class.getResource("/image/print.png")));
		btnStampa.setFont(new Font("Arial", Font.BOLD, 16));



		btnStampa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try
				{

					for (ButtonGroup item : listaRisposte) {
						
						 for (Enumeration<AbstractButton> buttons = item.getElements(); buttons.hasMoreElements();) {
					            AbstractButton button = buttons.nextElement();

					            if (button.isSelected()) {
					                System.out.println(button.getText());
					            }
					        }
						
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
	
	class ModelLinearita extends DefaultTableModel {


		public ModelLinearita() {
			addColumn("N° Ripetizioni");
			addColumn("Massa L");
			addColumn("Indicazione I");
			addColumn("Carico aggiuntivo  ΔL");
			addColumn("P");

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



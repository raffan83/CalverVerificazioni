package it.calverDesktopVER.dao;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import it.calverDesktopVER.dto.VerAccuratezzaDTO;
import it.calverDesktopVER.dto.VerClassiDTO;
import it.calverDesktopVER.dto.VerDecentramentoDTO;
import it.calverDesktopVER.dto.VerLinearitaDTO;
import it.calverDesktopVER.dto.VerMisuraDTO;
import it.calverDesktopVER.dto.VerMobilitaDTO;
import it.calverDesktopVER.dto.VerRipetibilitaDTO;
import it.calverDesktopVER.dto.VerStrumentoDTO;
import it.calverDesktopVER.utl.Costanti;
import it.calverDesktopVER.utl.Utility;

public class SQLiteDAO {

	static DatabaseMetaData metadata = null;

	private static  Connection getConnection() throws ClassNotFoundException, SQLException
	{
		Class.forName("org.sqlite.JDBC");
		
		Connection con=DriverManager.getConnection("jdbc:sqlite:"+Costanti.PATH_DB);
		
		return con;
	}
	private static  Connection getConnectionExternal(String filename) throws ClassNotFoundException, SQLException
	{
		Class.forName("org.sqlite.JDBC");
		
		Connection con=DriverManager.getConnection("jdbc:sqlite:"+filename);
		
		return con;
	}


	
	public static String[] listaTabelleDB() throws SQLException, ClassNotFoundException {
		 
		String table[] = { "TABLE" };
		 
		         ResultSet rs = null;
		
		         ArrayList<String> tables = null;
		
		         DatabaseMetaData metadata =getConnection().getMetaData();
		
		         rs = metadata.getTables(null, null, null, table);
		 
		         tables = new ArrayList<String>();
		 
		         while (rs.next()) {
		
		             tables.add(rs.getString("TABLE_NAME"));
		
		         }
		
		         return Utility.convertString(tables);

	}



	public static String[] getListaColonne(String tabella) throws SQLException, ClassNotFoundException {				
			
		ArrayList<String> listaColonne= new ArrayList<String>();
		
		ResultSet rs = getConnection().getMetaData().getColumns(null, null, tabella, null);
			
			while (rs.next()) {
				listaColonne.add(rs.getString("COLUMN_NAME"));
			}

		return Utility.convertString(listaColonne);
	}



	public static Object[][] getPlayLoad(int length,String tableName) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		Object[][]data=null;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("SELECT * FROM "+tableName);
			
			rs=pst.executeQuery();
			
			int numberRow=getNumberRow(tableName);
		
			data=new Object[numberRow][length];
			int ind=0;
			while(rs.next())
			{
							
				for (int i = 0; i < length; i++) {
					data[ind][i] =rs.getString(i+1);
				}
				ind++;
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return data;
	}



	private static int getNumberRow(String nomeTaballa) throws SQLException, ClassNotFoundException {
		int i=0;
		
		Connection con=getConnection();
		
		PreparedStatement pst=con.prepareStatement("SELECT * FROM "+nomeTaballa);
		ResultSet rs=pst.executeQuery();
		
		while(rs.next())
		{
			i++;
		}
		return i;
	}



	public static ArrayList<VerStrumentoDTO> getListaStrumenti(int filter) throws Exception {

		
		Connection con=null;
		PreparedStatement pst=null;
		ArrayList<VerStrumentoDTO> listaStrumenti = new ArrayList<>();
	try{
		con=getConnection();
		
		String sql="";
		if(filter==0)
		{
			sql="SELECT a.* FROM ver_strumento a";
		}
		if(filter==1)
		{
			sql="select a.* FROM ver_strumento a join ver_misura b on a.id=b.id_ver_strumento where b.stato=0";
		}
		if(filter==2)
		{
			sql="select a.* FROM ver_strumento a join ver_misura b on a.id=b.id_ver_strumento where b.stato=1";
		}
		if(filter==3)
		{
			sql="select a.* FROM ver_strumento a left join ver_misura b on a.id=b.id_ver_strumento where b.id_ver_strumento is null";
		}
		
		pst=con.prepareStatement(sql);
		ResultSet rs=pst.executeQuery();
		
		VerStrumentoDTO strumento =null;
		while(rs.next())
		{
			strumento= new VerStrumentoDTO();
			strumento.setId(rs.getInt("id"));//
			strumento.setDenominazione(rs.getString("denominazione"));//
			strumento.setCostruttore(rs.getString("costruttore"));
			strumento.setModello(rs.getString("modello"));
			strumento.setMatricola(rs.getString("matricola"));
			strumento.setClasse(rs.getInt("classe"));
			strumento.setId_tipo_strumento(rs.getInt("id_ver_tipo_strumento"));
			strumento.setUm(rs.getString("um"));
			strumento.setData_ultima_verifica(rs.getString("data_ultima_verifica"));
			strumento.setData_prossima_verifica(rs.getString("data_prossima_verifica"));
			strumento.setPortata_min_C1(rs.getBigDecimal("portata_min_C1"));
			strumento.setPortata_max_C1(rs.getBigDecimal("portata_max_C1"));
			strumento.setDiv_ver_C1(rs.getBigDecimal("div_ver_C1"));
			strumento.setDiv_ver_C1(rs.getBigDecimal("div_rel_C1"));
			strumento.setNumero_div_C1(rs.getBigDecimal("numero_div_C1"));
			strumento.setPortata_min_C2(rs.getBigDecimal("portata_min_C2"));
			strumento.setPortata_max_C2(rs.getBigDecimal("portata_max_C2"));
			strumento.setDiv_ver_C2(rs.getBigDecimal("div_ver_C2"));
			strumento.setDiv_ver_C2(rs.getBigDecimal("div_rel_C2"));
			strumento.setNumero_div_C2(rs.getBigDecimal("numero_div_C2"));
			strumento.setPortata_min_C3(rs.getBigDecimal("portata_min_C3"));
			strumento.setPortata_max_C3(rs.getBigDecimal("portata_max_C3"));
			strumento.setDiv_ver_C3(rs.getBigDecimal("div_ver_C3"));
			strumento.setDiv_ver_C3(rs.getBigDecimal("div_rel_C3"));
			strumento.setNumero_div_C3(rs.getBigDecimal("numero_div_C3"));
			strumento.setAnno_marcatura_ce(rs.getInt("anno_marcatura_CE"));
			strumento.setData_messa_in_servizio(rs.getString("data_ms"));
			strumento.setTipologia(rs.getInt("id_tipologia"));
			strumento.setFreq_mesi(rs.getInt("freq_mesi"));

			
			listaStrumenti.add(strumento);
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}
			return listaStrumenti;
		}
	



	public static VerStrumentoDTO getStrumento(String id) throws Exception {
	Connection con =null;
	PreparedStatement pst= null;
	
	VerStrumentoDTO strumento =null;	
	try{
		con=getConnection();
		pst=con.prepareStatement("SELECT a.* from ver_strumento a WHERE a.id=?");
		pst.setInt(1,Integer.parseInt(id));
		ResultSet rs=pst.executeQuery();
		
		
		
		
		while(rs.next())
		{
			strumento= new VerStrumentoDTO();
			strumento.setId(rs.getInt("id"));//
			strumento.setDenominazione(rs.getString("denominazione"));//
			strumento.setCostruttore(rs.getString("costruttore"));
			strumento.setModello(rs.getString("modello"));
			strumento.setMatricola(rs.getString("matricola"));
			strumento.setClasse(rs.getInt("classe"));
			strumento.setId_tipo_strumento(rs.getInt("id_ver_tipo_strumento"));
			strumento.setUm(rs.getString("um"));
			strumento.setData_ultima_verifica(rs.getString("data_ultima_verifica"));
			strumento.setData_prossima_verifica(rs.getString("data_prossima_verifica"));
			strumento.setPortata_min_C1(rs.getBigDecimal("portata_min_C1"));
			strumento.setPortata_max_C1(rs.getBigDecimal("portata_max_C1"));
			strumento.setDiv_ver_C1(rs.getBigDecimal("div_ver_C1"));
			strumento.setDiv_rel_C1(rs.getBigDecimal("div_rel_C1"));
			strumento.setNumero_div_C1(rs.getBigDecimal("numero_div_C1"));
			strumento.setPortata_min_C2(rs.getBigDecimal("portata_min_C2"));
			strumento.setPortata_max_C2(rs.getBigDecimal("portata_max_C2"));
			strumento.setDiv_ver_C2(rs.getBigDecimal("div_ver_C2"));
			strumento.setDiv_rel_C2(rs.getBigDecimal("div_rel_C2"));
			strumento.setNumero_div_C2(rs.getBigDecimal("numero_div_C2"));
			strumento.setPortata_min_C3(rs.getBigDecimal("portata_min_C3"));
			strumento.setPortata_max_C3(rs.getBigDecimal("portata_max_C3"));
			strumento.setDiv_ver_C3(rs.getBigDecimal("div_ver_C3"));
			strumento.setDiv_rel_C3(rs.getBigDecimal("div_rel_C3"));
			strumento.setNumero_div_C3(rs.getBigDecimal("numero_div_C3"));
			strumento.setAnno_marcatura_ce(rs.getInt("anno_marcatura_CE"));
			strumento.setData_messa_in_servizio(rs.getString("data_ms"));
			strumento.setTipologia(rs.getInt("id_tipologia"));
			strumento.setFreq_mesi(rs.getInt("freq_mesi"));
			strumento.setFamiglia_strumento(rs.getString("famiglia_strumento"));
			strumento.setPosizioni_cambio(rs.getInt("posizione_cambio"));
		
			
		}
	}catch(Exception ex)
	{
		throw ex;
	}
	finally
	{
		pst.close();
		con.close();
	}
		return strumento;
	}

	public static int isPresent(String id) throws Exception {
		Connection con =null;
		PreparedStatement pst= null;
		
		try{
			con=getConnection();
			pst=con.prepareStatement("SELECT * FROM ver_misura WHERE id_ver_strumento=?");
			pst.setInt(1,Integer.parseInt(id));
			ResultSet rs=pst.executeQuery();
			while(rs.next())
			{
			 return rs.getInt(1);
			}
			return 0;
		}catch(Exception ex)
		{
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}
			
	}



	public static int insertMisura(String id) throws Exception {
		
		Connection con =null;
		PreparedStatement pst= null;

		try{
			con=getConnection();
			pst=con.prepareStatement("INSERT INTO ver_misura(id_ver_strumento,data_verificazione,tipo_risposta,seq_risposte,stato) VALUES(?,?,?,?,?)",pst.RETURN_GENERATED_KEYS);
			
			pst.setInt(1,Integer.parseInt(id));
			SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
			Date d = new Date();
			pst.setString(2,sdf.format(d));
			pst.setString(3,"1");
			pst.setString(4,"0");
			pst.setString(5,"0");
			pst.execute();
		
		    ResultSet generatedKeys = pst.getGeneratedKeys(); 
		    	
		            if (generatedKeys.next()) {
		               return (int) generatedKeys.getLong(1);
		            }
		            else {
		                throw new SQLException("Error insert Misura, no ID obtained.");
		            }
		        
			  
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}
		
	}



	public static int getMaxTablella(int idMisura) throws Exception 
	{
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		int toRet=0;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("SELECT MAX(id_tabella) FROM tblTabelleMisura WHERE id_misura=?");
			pst.setInt(1,idMisura);
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				toRet=rs.getInt(1);
			}
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}

		return toRet;
	}

	public static int getMaxTablellaGeneral() throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		int toRet=0;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("SELECT MAX(id) FROM tblTabelleMisura");
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				toRet=rs.getInt(1);
			}
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}

		return toRet;
	}
	


	public static void inserisciMisuraLineare(int idMisura, int seq_tab,Integer punti, String labelPunti, String calibrazione) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("INSERT INTO tblTabelleMisura(id_misura,id_tabella,tipoProva,id_ripetizione,ordine,tipoVerifica,label,calibrazione,applicabile) VALUES(?,?,?,?,?,?,?,?,?)");
			String calLabel="";
			if(calibrazione!=null && calibrazione.length()>0){calLabel="("+calibrazione+")";}
			
			
			pst.setInt(1, idMisura);
			pst.setInt(2,seq_tab);
			pst.setString(3, "L"+"_"+punti);
			
			for (int i = 1; i <= punti; i++) {
			
				pst.setInt(4, 0);
			pst.setInt(5, i);
			pst.setString(6,labelPunti+" "+i+calLabel);
			pst.setString(7,labelPunti);
			pst.setString(8, calibrazione);
			pst.setString(9, "S");
			pst.execute();
			}
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}
	}


	
public static int inserisciMisuraRDP(int idMisura, String campioniString, String descrizione,
		BigDecimal valore_rilevato, String esito, ByteArrayOutputStream file_att) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		int id=0;
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("INSERT INTO tblTabelleMisura(id_misura,id_tabella,tipoProva,id_ripetizione,ordine,tipoVerifica,label,valoreStrumento,esito,desc_campione,applicabile,file_att) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			int ordine=getOrdine(idMisura);
			ordine++;
			
			
			pst.setInt(1, idMisura);
			pst.setInt(2,1);
			pst.setString(3, "RDP");
			pst.setInt(4, 0);
			pst.setInt(5,ordine );
			pst.setString(6,descrizione);
			pst.setString(7,descrizione);
			pst.setBigDecimal(8, valore_rilevato);
			pst.setString(9, esito);
			pst.setString(10,campioniString);
			pst.setString(11, "S");
			if(file_att!=null)
			{
				pst.setBytes(12, file_att.toByteArray());
			}
			else
			{
				pst.setBytes(12, null);
			}
			pst.execute();
			ResultSet rs =pst.getGeneratedKeys();
			rs.next();
			id=(int)rs.getLong(1);
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}
		return id;
	}

public static void updateMisuraRDP(int idRecord, String descrizioneCampione, String descrizioneProva,BigDecimal valoreRilevato, String esito, ByteArrayOutputStream file_att) throws Exception {
	
	Connection con=null;
	PreparedStatement pst=null;
	try 
	{
		con=getConnection();
		pst=con.prepareStatement("UPDATE tblTabelleMisura SET tipoVerifica=?,label=?,valoreStrumento=?,esito=?,desc_campione=?,file_att=? WHERE id=?");
		
	
		pst.setString(1,descrizioneProva);
		pst.setString(2,descrizioneProva);
		pst.setBigDecimal(3, valoreRilevato);
		pst.setString(4, esito);
		pst.setString(5,descrizioneCampione);
		if(file_att!=null) 
		{
			pst.setBytes(6, file_att.toByteArray());
		}
		else 
		{
			pst.setBytes(6, null);
		}
		pst.setInt(7, idRecord);
		pst.execute();
		
	}
	catch (Exception e) 
	{
	 e.printStackTrace();	
	 throw e;
	}
	finally
	{
		pst.close();
		con.close();
	}
}

	private static int getOrdine(int idMisura) throws Exception 
	{
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		int toRet=0;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("SELECT MAX(ordine) FROM tblTabelleMisura WHERE id_misura=?");
			pst.setInt(1,idMisura);
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				toRet=rs.getInt(1);
			}
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}

		return toRet;
	}
	public static void inserisciMisuraRipetibile(int idMisura, int seq_tab,Integer punti, Integer ripetizioni, String labelPunti, String calibrazione) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("INSERT INTO tblTabelleMisura(id_misura,id_tabella,tipoProva,id_ripetizione,ordine,tipoVerifica,label,calibrazione,applicabile) VALUES(?,?,?,?,?,?,?,?,?)");
			
			String calLabel="";
			if(calibrazione!=null && calibrazione.length()>0){calLabel="("+calibrazione+")";}
			
			pst.setInt(1, idMisura);
			pst.setInt(2,seq_tab);
			pst.setString(3, "R"+"_"+punti+"_"+ripetizioni);
			
			
			int ordine=1;
			
			for (int i = 1; i <= ripetizioni; i++) {
			
				for (int j = 0; j < punti; j++) {
					
					pst.setInt(4,i);
					pst.setInt(5, ordine);
					pst.setString(6,"["+i+" - rp] "+labelPunti+" "+(j+1)+calLabel);
					pst.setString(7,labelPunti);
					pst.setString(8, calibrazione);
					pst.setString(9, "S");
					pst.execute();
					ordine++;
				}
			
			}
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}
		
	}



	


	public static int getNumeroTabellePerProva(int id) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		
		int numTab=0;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("SELECT max(id_tabella) FROM tblTabelleMisura WHERE id_misura=?");
			pst.setInt(1, id);
		
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				numTab=rs.getInt(1);
			}
			
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}
		return numTab;
	}




	public static Integer getStatoMisura(String id) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		Integer toRet=null;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("SELECT * FROM ver_misura WHERE id_ver_strumento=?");
			pst.setString(1, id);
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
							
				toRet=rs.getInt("stato");
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return toRet;
	}



	public static ArrayList<String> getListaCampioniPerStrumento(String idStrumento) throws Exception {

		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		ArrayList<String> toRet=new  ArrayList<String>();
		toRet.add("Seleziona Campione....");
	
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select distinct(codice) from tblCampioni " +
					"left join tbl_ts_tg on tbl_ts_tg.id_tipo_grandezza=tblCampioni.id_tipo_grandezza " +
					"left join tblStrumenti on tbl_ts_tg.id_tipo_strumento=tblStrumenti.id_tipo_strumento " +
					"where tblStrumenti.id=?  ORDER BY codice ASC");
			
			pst.setString(1, idStrumento);
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				toRet.add(rs.getString(1));			
				
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return toRet;
	}

	public static ArrayList<String> getListaCampioniCompleta() throws Exception {

		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		ArrayList<String> toRet=new  ArrayList<String>();
		toRet.add("Seleziona Campione....");
	
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select distinct(codice) from tblCampioni where tipoGrandezza=?");
			pst.setString(1, "Massa");

			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				toRet.add(rs.getString(1));			
				
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return toRet;
	}
	
	public static ArrayList<String> getListaCampioniCompletaNoInterpolabili() throws Exception {

		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		ArrayList<String> toRet=new  ArrayList<String>();
		toRet.add("Seleziona Campione....");
	
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select distinct(codice) from tblCampioni WHERE interpolazione_permessa='0'");
			

			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				toRet.add(rs.getString(1));			
				
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return toRet;
	}

	public static ArrayList<String> getListaParametriTaratura(String codiceCampione) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		ArrayList<String> toRet=new  ArrayList<String>();
		toRet.add("Seleziona Parametro....");
		try
		{
			con=getConnection();
			

			
			pst=con.prepareStatement("select distinct(parametri_taratura) FROM tblCampioni WHERE codice=?");
					
			pst.setString(1, codiceCampione);
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				toRet.add(rs.getString(1));			
				
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return toRet;
	}



	private static String preparaTipiGrandezza(ArrayList<String> listaTipiGrandezza) {
		String tipoGrandezza="";
		
		for (int i = 0; i < listaTipiGrandezza.size(); i++) {
			
			tipoGrandezza=tipoGrandezza+" OR id_tipo_grandezza ="+listaTipiGrandezza.get(i);
		}
		
		return tipoGrandezza.substring(4,tipoGrandezza.length())+")";
	}


	public static boolean isInterpolabile(String codiceCampione) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		boolean toRet=false;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select interpolazione_permessa FROM  tblCampioni WHERE codice=?");
			
			pst.setString(1, codiceCampione);
			
			rs=pst.executeQuery();		
			rs.next();
			
				if(rs.getInt(1)==1)
				{
					toRet=true;
				}
				
		
		
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return toRet;
		
		
	}



	public static ArrayList<String> getListaParametriTaraturaDistinct(String codiceCampione, ArrayList<String> listaTipiGrandezza) throws Exception {
			Connection con=null;
			PreparedStatement pst=null;
			ResultSet rs =null;
			ArrayList<String> toRet=new  ArrayList<String>();
			toRet.add("Seleziona Parametro....");
			try
			{
				con=getConnection();
				
				String tipoGrandezza=preparaTipiGrandezza(listaTipiGrandezza);
				
				pst=con.prepareStatement("select DISTINCT(parametri_taratura) FROM tblCampioni WHERE codice=? AND ("+tipoGrandezza);
						
				pst.setString(1, codiceCampione);
				
				rs=pst.executeQuery();
				
				while(rs.next())
				{
					toRet.add(rs.getString(1));			
					
				}
				
			}catch (Exception e) {
				throw e;
			}finally
			{
				pst.close();
				con.close();
			}
			return toRet;
		
	}



	




	

	public static ArrayList<String> getListaFattoriMoltiplicativi() throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		ArrayList<String> toRet=new  ArrayList<String>();
		toRet.add("Seleziona Parametro....");
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select * FROM tbl_fattori_moltiplicativi ");
					
		
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				toRet.add(rs.getString(1)+" ("+rs.getString(2)+") | "+rs.getBigDecimal("fm").toEngineeringString());			
				
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return toRet;
	}



	public static double getPotenza(String str) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		double potenza=0;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select potenza FROM tbl_fattori_moltiplicativi WHERE sigla=? ");
			pst.setString(1, str);		
		
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				potenza= rs.getDouble(1);
				
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return potenza;
	}

	public static double getPotenzaPerClasse(String cls) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		double potenza=0;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select potenza FROM tbl_fattori_moltiplicativi WHERE descrizione=? ");
			pst.setString(1, cls);		
		
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				potenza= rs.getDouble(1);
				
			}
			
		}catch (Exception e) {
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return potenza;
	}





	public static BigDecimal[] getMinMaxScala(String codice,String parametro) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		BigDecimal[] minMax =new BigDecimal[2];
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("SELECT MIN(valore_taratura),MAX(valore_taratura)  FROM tblCampioni WHERE codice=? AND parametri_Taratura=?");
			pst.setString(1, codice);
			pst.setString(2, parametro);
		
		
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				minMax[0]=rs.getBigDecimal(1);
				minMax[1]=rs.getBigDecimal(2);
			
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return minMax;
	}



	public static ArrayList<BigDecimal> getValoriMediCampione(int idMisura,int id_ripetizione, int id) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		ArrayList<BigDecimal> listaValoriMedi =new ArrayList<BigDecimal>();
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("SELECT valoreCampione  FROM tblTabelleMisura WHERE id_ripetizione=?  AND id_misura=? AND id<>? AND id_Tabella = (select id_tabella from tblTabelleMisura where id=?)");
			
			pst.setInt(1,id_ripetizione);
			pst.setInt(2, idMisura);
			pst.setInt(3, id);
			pst.setInt(4, id);
		
		
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				Object obj =rs.getObject(1);
				if(obj!=null && obj.toString().length()>0)
				{
					listaValoriMedi.add(rs.getBigDecimal(1));
				}
				}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return listaValoriMedi;
	}



	public static void setValoriMediCampione(int idMisura, int id_ripetizioni,BigDecimal valoreMedioCampione, int id) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("update tblTabelleMisura  SET valoreMedioCampione=? WHERE id_ripetizione = ? AND id_misura=? AND id_Tabella = (select id_tabella from tblTabelleMisura where id=?)");
			
			pst.setBigDecimal(1, valoreMedioCampione);
			pst.setInt(2, id_ripetizioni);
			pst.setInt(3, idMisura);
			pst.setInt(4, id);
		
		
			pst.execute();
			
		
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}

	}



	public static ArrayList<BigDecimal> getValoriMediStumento(int idMisura,int id_ripetizione, int id) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		ArrayList<BigDecimal> listaValoriMedi =new ArrayList<BigDecimal>();
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("SELECT valoreStrumento  FROM tblTabelleMisura WHERE id_ripetizione=?  AND id_misura=? AND id<>? AND id_Tabella = (select id_tabella from tblTabelleMisura where id=?)");
			pst.setInt(1, id_ripetizione);
			pst.setInt(2, idMisura);
			pst.setInt(3, id);
			pst.setInt(4, id);
		
		
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				Object obj =rs.getObject(1);
				if(obj!=null && obj.toString().length()>0)
				{
					listaValoriMedi.add(rs.getBigDecimal(1));
				}
				}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return listaValoriMedi;
	}



	public static void setValoriMediStrumento(int idMisura, int id_ripetizione,BigDecimal valoreMedioStrumento, int id) throws Exception {
	
		Connection con=null;
		PreparedStatement pst=null;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("update tblTabelleMisura  SET valoreMedioStrumento=? WHERE id_ripetizione=? AND id_misura=? AND id_Tabella = (select id_tabella from tblTabelleMisura where id=?) ");
			
			pst.setBigDecimal(1, valoreMedioStrumento);
			pst.setInt(2, id_ripetizione);
			pst.setInt(3, idMisura);
			pst.setInt(4, id);
		
		
			pst.execute();
			
		
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		
	}

	public static boolean isPresentCampione(int idTabella, int idMisura) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs;
		boolean isPresent=false;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select * from tblCampioniUtilizzati WHERE id_tabellaMisura=? AND id_misura=?");
			
			pst.setInt(1,idTabella);
			pst.setInt(2, idMisura);

			rs=pst.executeQuery();
			
		while(rs.next())
		{
			isPresent=true;
		}
		
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		
		return isPresent;
	}



	public static void insertCampioneUtilizzato(int idTabella, int idMisura,String campione, String parametro) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
	
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("INSERT INTO tblCampioniUtilizzati(id_tabellaMisura,id_misura,desc_parametro,desc_campione) VALUES(?,?,?,?)");
			
			pst.setInt(1,idTabella);
			pst.setInt(2, idMisura);
			pst.setString(3, campione);
			pst.setString(4, parametro);

			pst.execute();		
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		
		
	}



	public static void updateCampioneUtilizzato(int idTabella, int idMisura,String campione, String parametro) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
	
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("UPDATE  tblCampioniUtilizzati SET desc_campione=? , desc_parametro=? WHERE id_tabellaMisura=? AND id_misura=?");
			
			pst.setString(1, campione);
			pst.setString(2, parametro);
			pst.setInt(3,idTabella);
			pst.setInt(4, idMisura);
			

			pst.execute();		
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		
	}

	public static int insertStrumento(VerStrumentoDTO strumento) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("INSERT INTO ver_strumento(denominazione,costruttore,modello,matricola,classe,id_ver_tipo_strumento,um,"+
																"portata_min_C1,portata_max_C1,div_ver_C1,div_rel_C1,numero_div_C1," +
																"portata_min_C2,portata_max_C2,div_ver_C2,div_rel_C2,numero_div_C2," +
																"portata_min_C3,portata_max_C3,div_ver_C3,div_rel_C3,numero_div_C3," +
																"anno_marcatura_CE,data_ms,id_tipologia,freq_mesi,creato,famiglia_strumento,posizione_cambio) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
																Statement.RETURN_GENERATED_KEYS);
			
			

			pst.setString(1, strumento.getDenominazione());
			pst.setString(2, strumento.getCostruttore());
			pst.setString(3, strumento.getModello());
			pst.setString(4, strumento.getMatricola());
			pst.setInt(5, strumento.getClasse());
			pst.setInt(6, strumento.getId_tipo_strumento());
			pst.setString(7, strumento.getUm());
			
			pst.setBigDecimal(8, strumento.getPortata_min_C1());
			pst.setBigDecimal(9, strumento.getPortata_max_C1());
			pst.setBigDecimal(10, strumento.getDiv_ver_C1());
			pst.setBigDecimal(11, strumento.getDiv_rel_C1());
			pst.setBigDecimal(12, strumento.getNumero_div_C1());
			
			pst.setBigDecimal(13, strumento.getPortata_min_C2());
			pst.setBigDecimal(14, strumento.getPortata_max_C2());
			pst.setBigDecimal(15, strumento.getDiv_ver_C2());
			pst.setBigDecimal(16, strumento.getDiv_rel_C2());
			pst.setBigDecimal(17, strumento.getNumero_div_C2());
			
			pst.setBigDecimal(18, strumento.getPortata_min_C3());
			pst.setBigDecimal(19, strumento.getPortata_max_C3());
			pst.setBigDecimal(20, strumento.getDiv_ver_C3());
			pst.setBigDecimal(21, strumento.getDiv_rel_C3());
			pst.setBigDecimal(22, strumento.getNumero_div_C3());
			
			pst.setInt(23, strumento.getAnno_marcatura_ce());
			pst.setString(24,strumento.getData_messa_in_servizio());
			pst.setInt(25, strumento.getTipologia());
			pst.setInt(26, strumento.getFreq_mesi());
			pst.setString(27,"S");
			pst.setString(28, strumento.getFamiglia_strumento());
			pst.setInt(29, strumento.getPosizioni_cambio());
			
	
			pst.executeUpdate();
			
			ResultSet rs = pst.getGeneratedKeys();
			rs.next();
		    return rs.getInt(1);
				
		
			
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		}
	}

	
	public static String getNomeSede() {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs = null;
		String nomeSede="";
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select sede FROM tbl_general WHERE id=1");
			rs=pst.executeQuery();
			
			
			while(rs.next())
			{
				nomeSede= rs.getString(1);
				
			}
			
			
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return nomeSede;
	}



	public static boolean checkExecute(String pATH_DB) {
		boolean toReturn=true;

		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs = null;
	
		
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select * FROM tbl_general WHERE upload='S'");
			rs=pst.executeQuery();
			
			
			while(rs.next())
			{
			toReturn=false;
				
			}
			
		}catch (Exception e) 
		 {
			e.printStackTrace();
		}	
		
		return toReturn;
		}



	public static void chiudiMisura(String pATH_DB) {
		Connection con=null;
		PreparedStatement pst=null;
	
		
		try
		{
		  con=getConnection();
			
		  pst=con.prepareStatement("UPDATE tbl_general SET upload='S'");
			
		   pst.execute();	
		

		
		}catch (Exception e) 
		 {
			e.printStackTrace();
		
		 }	
		
		
		}



	public static void deleteRecordMisura(int id) {
		
		Connection con=null;
		PreparedStatement pst=null;
	
		
		try
		{
		  con=getConnection();
			
		  pst=con.prepareStatement("DELETE FROM tblTabelleMisura WHERE id=?");
		  pst.setInt(1, id);	
		   pst.execute();	
		
			
		}catch (Exception e) 
		 {
			e.printStackTrace();
		}	
		
		}






	public static void salvaCertificato(int id, String code) throws SQLException {
		
		Connection con=null;
		PreparedStatement pst=null;
		
		try
		{
		  con=getConnection();
			
		  pst=con.prepareStatement("Update tblStrumenti set nCertificato=? WHERE id=?");
		  pst.setString(1, code);
		  pst.setInt(2, id);
		  
		  pst.execute();
		 
		}
		catch (Exception e) 
		 {
			e.printStackTrace();
			
		}finally
		{
			pst.close();
			con.close();
		}	
		
	}


	public static ArrayList<String> getListaGrandezze() throws Exception {
		
		Connection con =null;
		PreparedStatement pst=null;
		ResultSet rs = null;
		ArrayList<String> listaRitorno= new ArrayList<String>();
		listaRitorno.add("Selezione grandezza");
		try {
			con =getConnection();
			pst=con.prepareStatement("SELECT COUNT(tipo_misura) AS count,tipo_misura FROM tbl_conversione GROUP BY tipo_misura");
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				int numero =rs.getInt("count");
				if(numero>0)
				{
					listaRitorno.add(rs.getString("tipo_misura"));
				}
			}
			
		} catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
			
		}
		return listaRitorno;
	}


	public static ArrayList<String> getListaUM(String param) throws Exception {
		Connection con =null;
		PreparedStatement pst=null;
		ResultSet rs = null;
		ArrayList<String> listaRitorno= new ArrayList<String>();
		listaRitorno.add("Selezione Unità Misura");
		try {
			con =getConnection();
			pst=con.prepareStatement("select distinct (um_a),um from tbl_conversione where tipo_misura=?");
			pst.setString(1, param);
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				listaRitorno.add(rs.getString(1)+" @ "+rs.getString(2));			
				
			}
			
		} catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
			
		}
		
		return listaRitorno;
	}


	public static boolean controllaMisuraCertificato() throws Exception {
		Connection con =null;
		PreparedStatement pst=null;
		ResultSet rs = null;

		try {
			con =getConnection();
			pst=con.prepareStatement("SELECT * FROM tblMisure a, tblStrumenti b where a.id_str=b.id AND (b.nCertificato='' OR b.nCertificato is null) AND statoMisura=1");
		
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				
				return false;
			}
			
		} catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
			
		}
		
		return true;
	}


	public static int updateStrumento(VerStrumentoDTO strumento) throws Exception {
		
		Connection con =null;
		PreparedStatement pst=null;
		int toReturn=0;
		
		try {
			con =getConnection();
			pst=con.prepareStatement("UPDATE ver_strumento set denominazione=?,costruttore=?,modello=?,matricola=?,anno_marcatura_CE=?,data_ms=?,freq_mesi=?, "
										   +"portata_min_C1=?,portata_max_C1=?,div_ver_C1=?,div_rel_C1=?,numero_div_C1=?,"
										   +"portata_min_C2=?,portata_max_C2=?,div_ver_C2=?,div_rel_C2=?,numero_div_C2=?,"
										   +"portata_min_C3=?,portata_max_C3=?,div_ver_C3=?,div_rel_C3=?,numero_div_C3=?, "
										   +"classe=?,id_ver_tipo_strumento=?,um=?,id_tipologia=?, famiglia_strumento=?,posizione_cambio=? WHERE id=?");
		
			pst.setString(1,strumento.getDenominazione());
			pst.setString(2,strumento.getCostruttore());
			pst.setString(3,strumento.getModello());
			pst.setString(4,strumento.getMatricola());
			pst.setInt(5,strumento.getAnno_marcatura_ce());
			pst.setString(6,strumento.getData_messa_in_servizio());
			pst.setInt(7,strumento.getFreq_mesi());
			
			pst.setBigDecimal(8, strumento.getPortata_min_C1());
			pst.setBigDecimal(9, strumento.getPortata_max_C1());
			pst.setBigDecimal(10, strumento.getDiv_ver_C1());
			pst.setBigDecimal(11, strumento.getDiv_rel_C1());
			pst.setBigDecimal(12, strumento.getNumero_div_C1());
			
			pst.setBigDecimal(13, strumento.getPortata_min_C2());
			pst.setBigDecimal(14, strumento.getPortata_max_C2());
			pst.setBigDecimal(15, strumento.getDiv_ver_C2());
			pst.setBigDecimal(16, strumento.getDiv_rel_C2());
			pst.setBigDecimal(17, strumento.getNumero_div_C2());
			
			pst.setBigDecimal(18, strumento.getPortata_min_C3());
			pst.setBigDecimal(19, strumento.getPortata_max_C3());
			pst.setBigDecimal(20, strumento.getDiv_ver_C3());
			pst.setBigDecimal(21, strumento.getDiv_rel_C3());
			pst.setBigDecimal(22, strumento.getNumero_div_C3());
			
			pst.setInt(23, strumento.getClasse());
			pst.setInt(24, strumento.getId_tipo_strumento());
			pst.setString(25, strumento.getUm());
			pst.setInt(26, strumento.getTipologia());
			pst.setString(27, strumento.getFamiglia_strumento());

			pst.setInt(28, strumento.getPosizioni_cambio());

			
			pst.setInt(29, strumento.getId());
			
			
			toReturn=pst.executeUpdate();
			
		} catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
			
		}
		
		return toReturn;
	}


	public static void removeTabellaMisura(int idMisura, int id_tabella) throws Exception {
		
		Connection con =null;
		PreparedStatement pst=null;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("DELETE FROM tblTabelleMisura WHERE id_misura=? AND id_tabella=?");
			pst.setInt(1, idMisura);
			pst.setInt(2, id_tabella);
			
			
			pst.execute();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		}
		
	}


	public static HashMap<String, String> getListaIDCampioni() throws Exception {
		HashMap<String, String> listaCampioni = new HashMap<>();
		Connection con =null;
		PreparedStatement pst=null;
		ResultSet rs = null;
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("SELECT DISTINCT(ID_CAMP) FROM tblCampioni");
			
			rs= pst.executeQuery();
			
			while(rs.next())
			{
				listaCampioni.put(rs.getString(1),"");
			}
			
		}catch(Exception e)
		{
			throw e;
		}

		return listaCampioni;
	}



	public static void cambiaStatoMisura(String idStrumento, int stato) throws Exception 
	{
		Connection con=null;
		PreparedStatement pst=null;
	
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("UPDATE  tblMisure SET statoMisura=? WHERE id_str=?");
			
			pst.setInt(1, stato);
			pst.setString(2, idStrumento);
		
			
			

			pst.execute();		
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		
	}


	public static ArrayList<String> getListaTipoGrandezzeByStrumento(String idStrumento) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		ArrayList<String> listaTipoGrandezza= new ArrayList<>();
		
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("select id_tipo_grandezza FROM  tbl_ts_tg AS a " +
									 "left join tblStrumenti AS b on a.id_tipo_strumento=b.id_tipo_strumento " +
									 "where b.id=?");
			
			pst.setString(1, idStrumento);
			rs = pst.executeQuery();		
			
			while(rs.next())
			{
				listaTipoGrandezza.add(rs.getString(1));
			}
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		return listaTipoGrandezza;
	}


	public static int getMaxIDStrumento() throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		int toRet=0;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("SELECT MAX(id) FROM tblStrumenti");
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				toRet=rs.getInt(1);
			}
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}

		return toRet;
	}
	
	
	public static void insertProvaLineare(int idMisura, VerStrumentoDTO strumento) throws Exception {

		Connection con =null;
		PreparedStatement pst= null;

		try{
			con=getConnection();
			pst=con.prepareStatement("INSERT INTO ver_linearita(id_misura,campo,riferimento) VALUES(?,?,?)");

			if(strumento.getId_tipo_strumento()==4) 
			{
				for (int i = 0; i < 9; i++) 

				{
					pst.setInt(1,idMisura);		
					pst.setInt(2,1);
					pst.setInt(3,i+1);

					pst.execute();
				}
				return;
			}
				
			
			if(strumento.getId_tipo_strumento()==5) 
			{
				int sizeRow =(strumento.getPosizioni_cambio()+1)*3;
			
				
				
				for(int i=0;i<sizeRow+1;i++) 
				{
					pst.setInt(1,idMisura);		
					pst.setInt(2,1);
					pst.setInt(3,i+1);

					pst.execute();
				}
				return;
			}

			
			if(strumento.getId_tipo_strumento()!=3 && strumento.getId_tipo_strumento()!=2) 
			{
				for (int i = 0; i < 6; i++) 

				{
					pst.setInt(1,idMisura);		
					pst.setInt(2,1);
					pst.setInt(3,i+1);

					pst.execute();
				}

			}
			else 
			{
				for (int y = 0; y < 3; y++) {

					int campo=y+1;	

					for (int i = 0; i < 6; i++) 

					{
						pst.setInt(1,idMisura);		
						pst.setInt(2,campo);
						pst.setInt(3,i+1);

						pst.execute();
					}
				}	
			}

		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}


	}
	public static void insertProvaRipetibilita(int idMisura, int tipoStrumento) throws Exception {
	
		Connection con =null;
		PreparedStatement pst= null;

		try{
			con=getConnection();
			pst=con.prepareStatement("INSERT INTO ver_ripetibilita(id_misura,campo,numero_ripetizione) VALUES(?,?,?)");


			if(tipoStrumento!=3 && tipoStrumento!=2) 
			{
				for (int i = 0; i < 6; i++) 

				{
					pst.setInt(1,idMisura);		
					pst.setInt(2,1);
					pst.setInt(3,i+1);

					pst.execute();
				}

			}
			else 
			{
				for (int y = 0; y < 3; y++) {

					int campo=y+1;	

					for (int i = 0; i < 6; i++) 

					{
						pst.setInt(1,idMisura);		
						pst.setInt(2,campo);
						pst.setInt(3,i+1);

						pst.execute();
					}
				}	
			}

		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}

		
	}
	public static void insertProvaAccuratezza(int idMisura, int tipoStrumento) throws Exception {
		
		Connection con =null;
		PreparedStatement pst= null;

		try{
			con=getConnection();
			pst=con.prepareStatement("INSERT INTO ver_accuratezza(id_misura,campo,posizione) VALUES(?,?,?)");


			if(tipoStrumento!=3 && tipoStrumento!=2) 
			{
				
					pst.setInt(1,idMisura);		
					pst.setInt(2,1);
					pst.setInt(3,1);

					pst.execute();
				

			}
			else 
			{
				for (int y = 0; y < 3; y++) {

					int campo=y+1;	

		
						pst.setInt(1,idMisura);		
						pst.setInt(2,campo);
						pst.setInt(3,1);

						pst.execute();
					
				}	
			}

		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}
		
	}
	public static void insertProvaMobilita(int idMisura, int tipoStrumento) throws Exception {
		
		
		Connection con =null;
		PreparedStatement pst= null;

		try{
			con=getConnection();
			pst=con.prepareStatement("INSERT INTO ver_mobilita(id_misura,campo,caso,carico) VALUES(?,?,?,?)");


			if(tipoStrumento!=3 && tipoStrumento!=2) 
			{
			
			for (int z = 1; z <=2; z++) {
				
			
				for (int i = 0; i < 3; i++) 

				{
					pst.setInt(1,idMisura);		
					pst.setInt(2,1);
					pst.setInt(3,z);
					pst.setInt(4,i+1);

					pst.execute();
				}
			}	
			}
			else 
			{
				for (int y = 0; y < 3; y++) {

					int campo=y+1;	

					for (int z = 1; z <=2; z++) {
						
					
					for (int i = 0; i < 3; i++) 

					{
						pst.setInt(1,idMisura);		
						pst.setInt(2,campo);
						pst.setInt(3,z);
						pst.setInt(4,i+1);

						pst.execute();
					}
					}
					}	
			}

		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}

		
	}
	public static void insertProvaDecentramento(int idMisura, int tipoStrumento) throws Exception {
	
		Connection con =null;
		PreparedStatement pst= null;

		try{
			con=getConnection();
			pst=con.prepareStatement("INSERT INTO ver_decentramento(id_misura,campo,posizione,speciale) VALUES(?,?,?,?)");


			if(tipoStrumento!=3 && tipoStrumento!=2) 
			{
				for (int i = 0; i < 10; i++) 

				{
					pst.setInt(1,idMisura);		
					pst.setInt(2,1);
					pst.setInt(3,i+1);
					pst.setString(4, "N");

					pst.execute();
				}

			}
			else 
			{
				for (int y = 0; y < 3; y++) {

					int campo=y+1;	

					for (int i = 0; i < 10; i++) 

					{
						pst.setInt(1,idMisura);		
						pst.setInt(2,campo);
						pst.setInt(3,i+1);
						pst.setString(4, "N");

						pst.execute();
					}
				}	
			}

		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
		finally
		{
			pst.close();
			con.close();
		}
		
		
	}
	public static void saveControlloPreliminare(int idMisura, String sequence) throws Exception {
		
		
		Connection con=null;
		PreparedStatement pst=null;

		try 
		{
			con=getConnection();
			pst=con.prepareStatement("UPDATE ver_misura set seq_risposte=? WHERE id=?");
			
			pst.setString(1, sequence);
			pst.setInt(2, idMisura);
			
			pst.execute();
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}

	}
	public static VerMisuraDTO getMisura(int idMisura) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		VerMisuraDTO misura=null;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("SELECT * FROM ver_misura WHERE id=?");
			pst.setInt(1, idMisura);
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				misura= new VerMisuraDTO();
				misura.setId(idMisura);
				misura.setIdVerStrumento(rs.getInt("id_ver_strumento"));
				misura.setDataVerificazione(rs.getString("data_verificazione"));
				misura.setDataScadenza(rs.getString("data_scadenza"));
				misura.setNumeroRapporto(rs.getString("numero_rapporto"));
				misura.setNumeroAttestato(rs.getString("numero_attestato"));
				misura.setTipo_verifica(rs.getInt("tipo_verifica"));
				misura.setMotivo_verifica(rs.getInt("motivo_verifica"));
				misura.setNomeRiparatore(rs.getString("nome_riparatore"));
				misura.setDataRiparazione(rs.getString("data_riparazione"));
				misura.setTipoRisposte(rs.getInt("tipo_risposta"));
				misura.setSeqRisposte(rs.getString("seq_risposte"));
				misura.setIdNonConforme(rs.getInt("id_non_conforme"));
				misura.setStato(rs.getInt("stato"));
				misura.setIs_difetti(rs.getString("isDifetti"));
				misura.setCampioniLavoro(rs.getString("campioni_lavoro"));
				misura.setFile_inizio_prova(rs.getBytes("file_inizio_prova"));
				misura.setFile_fine_prova(rs.getBytes("file_fine_prova"));
				misura.setNomeFile_inizio_prova(rs.getString("nomefile_inizio_prova"));
				misura.setNomeFile_fine_prova(rs.getString("nomefile_fine_prova"));
				misura.setNumeroSigilli(rs.getInt("numeroSigilli"));
				
				misura.settInizio(rs.getDouble("tInizio"));
				misura.settFine(rs.getDouble("tFine"));
				misura.setAltezza_org(rs.getDouble("altezza_org"));
				misura.setAltezza_util(rs.getDouble("altezza_util"));
				misura.setLatitudine_org(rs.getDouble("latitudine_org"));
				misura.setLatitudine_util(rs.getDouble("latitudine_util"));
				misura.setgOrg(rs.getDouble("gOrg"));
				misura.setgUtil(rs.getDouble("gUtil"));
				misura.setgFactor(rs.getDouble("gFactor"));
				misura.setNoteCombinazioni(rs.getString("note_combinazioni"));
				
				if (rs.wasNull()) {
					misura.setNumeroSigilli(null);
			    }
			}
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}

		return misura;
	}
	
	public static VerMisuraDTO getMisuraByIDStrumento(int idStrumento) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		VerMisuraDTO misura=null;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("SELECT * FROM ver_misura WHERE id_ver_strumento=?");
			pst.setInt(1, idStrumento);
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				misura= new VerMisuraDTO();
				misura.setId(rs.getInt("id"));
				misura.setIdVerStrumento(idStrumento);
				misura.setDataVerificazione(rs.getString("data_verificazione"));
				misura.setDataScadenza(rs.getString("data_scadenza"));
				misura.setNumeroRapporto(rs.getString("numero_rapporto"));
				misura.setNumeroAttestato(rs.getString("numero_attestato"));
				misura.setTipo_verifica(rs.getInt("tipo_verifica"));
				misura.setMotivo_verifica(rs.getInt("motivo_verifica"));
				misura.setNomeRiparatore(rs.getString("nome_riparatore"));
				misura.setDataRiparazione(rs.getString("data_riparazione"));
				misura.setTipoRisposte(rs.getInt("tipo_risposta"));
				misura.setSeqRisposte(rs.getString("seq_risposte"));
				misura.setIdNonConforme(rs.getInt("id_non_conforme"));
				misura.setStato(rs.getInt("stato"));
				misura.setIs_difetti(rs.getString("isDifetti"));
				misura.setCampioniLavoro(rs.getString("campioni_lavoro"));
				misura.setFile_inizio_prova(rs.getBytes("file_inizio_prova"));
				misura.setFile_fine_prova(rs.getBytes("file_fine_prova"));
				misura.setNomeFile_inizio_prova(rs.getString("nomefile_inizio_prova"));
				misura.setNomeFile_fine_prova(rs.getString("nomefile_fine_prova"));
				
				misura.settInizio(rs.getDouble("tInizio"));
				misura.settFine(rs.getDouble("tFine"));
				misura.setAltezza_org(rs.getDouble("altezza_org"));
				misura.setAltezza_util(rs.getDouble("altezza_util"));
				misura.setLatitudine_org(rs.getDouble("latitudine_org"));
				misura.setLatitudine_util(rs.getDouble("latitudine_util"));
				misura.setgOrg(rs.getDouble("gOrg"));
				misura.setgUtil(rs.getDouble("gUtil"));
				misura.setgFactor(rs.getDouble("gFactor"));
			}
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}

		return misura;
	}
	
	public static List<VerRipetibilitaDTO> getListaProvaRipetibilita(int idMisura) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		ArrayList<VerRipetibilitaDTO> listaRipetibilita= new ArrayList<>();
		
		VerRipetibilitaDTO ver_rip = null;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("SELECT * FROM ver_ripetibilita WHERE id_misura=? ORDER BY id ASC");
			pst.setInt(1, idMisura);
			
			rs=pst.executeQuery();
			
			
			while(rs.next())
			{
				ver_rip=new VerRipetibilitaDTO(); 
				ver_rip.setId(rs.getInt("id"));
			    ver_rip.setCampo(rs.getInt("campo"));
			    ver_rip.setNumeroRipetizione(rs.getInt("numero_ripetizione"));
			    ver_rip.setMassa(rs.getBigDecimal("massa"));
			    ver_rip.setIndicazione(rs.getBigDecimal("indicazione"));
			    ver_rip.setCaricoAgg(rs.getBigDecimal("carico_agg"));
			    ver_rip.setPortata(rs.getBigDecimal("portata"));
			    ver_rip.setDeltaPortata(rs.getBigDecimal("delta_portata"));
			    ver_rip.setMpe(rs.getBigDecimal("mpe"));
			    ver_rip.setEsito(rs.getString("esito"));
			    ver_rip.setPosizione(rs.getString("posizione"));
			    
			    listaRipetibilita.add(ver_rip);
			    
			}
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}

		return listaRipetibilita;
	}
	public static List<VerDecentramentoDTO> getListaProvaDecentramento(int idMisura) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		ArrayList<VerDecentramentoDTO> listaDecentraento= new ArrayList<>();
		
		VerDecentramentoDTO ver_dec = null;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("SELECT * FROM ver_decentramento WHERE id_misura=? ORDER BY id ASC");
			pst.setInt(1, idMisura);
			
			rs=pst.executeQuery();
			
			
			while(rs.next())
			{
				ver_dec=new VerDecentramentoDTO();
				ver_dec.setId(rs.getInt("id"));
				ver_dec.setTipoRicettore(rs.getInt("tipo_ricettore"));
				ver_dec.setPuntiAppoggio(rs.getInt("punti_appoggio"));
			    ver_dec.setCampo(rs.getInt("campo"));
			    ver_dec.setPosizione(rs.getInt("posizione"));
			    ver_dec.setMassa(rs.getBigDecimal("massa"));
			    ver_dec.setIndicazione(rs.getBigDecimal("indicazione"));
			    ver_dec.setCaricoAgg(rs.getBigDecimal("carico_agg"));
			    ver_dec.setErrore(rs.getBigDecimal("errore"));
			    ver_dec.setErroreCor(rs.getBigDecimal("errore_cor"));
			    ver_dec.setMpe(rs.getBigDecimal("mpe"));
			    ver_dec.setEsito(rs.getString("esito"));
			    ver_dec.setCarico(rs.getBigDecimal("carico"));
			    ver_dec.setSpeciale(rs.getString("speciale"));
			    
			    listaDecentraento.add(ver_dec);
			    
			}
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}

		return listaDecentraento;
	}
	
	public static List<VerLinearitaDTO> getListaProvaLinearita(int idMisura) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		ArrayList<VerLinearitaDTO> listaLinearita= new ArrayList<>();
		
		VerLinearitaDTO ver_lin = null;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("SELECT * FROM ver_linearita WHERE id_misura=? ORDER BY id ASC");
			pst.setInt(1, idMisura);
			
			rs=pst.executeQuery();
			
			
			while(rs.next())
			{
				ver_lin=new VerLinearitaDTO();
				ver_lin.setId(rs.getInt("id"));
				ver_lin.setTipoAzzeramento(rs.getInt("tipo_azzeramento"));
			    ver_lin.setCampo(rs.getInt("campo"));
			    ver_lin.setRiferimento(rs.getInt("riferimento"));
			    ver_lin.setMassa(rs.getBigDecimal("massa"));
			    ver_lin.setIndicazioneSalita(rs.getBigDecimal("indicazione_salita"));
			    ver_lin.setIndicazioneDiscesa(rs.getBigDecimal("indicazione_discesa"));
			    ver_lin.setCaricoAggSalita(rs.getBigDecimal("carico_agg_salita"));
			    ver_lin.setCaricoAggDiscesa(rs.getBigDecimal("carico_agg_discesa"));		    
			    ver_lin.setErroreSalita(rs.getBigDecimal("errore_salita"));
			    ver_lin.setErroreDiscesa(rs.getBigDecimal("errore_discesa"));
			    ver_lin.setErroreCorSalita(rs.getBigDecimal("errore_cor_salita"));
			    ver_lin.setErroreCorDiscesa(rs.getBigDecimal("errore_cor_discesa"));
			    ver_lin.setMpe(rs.getBigDecimal("mpe"));
			    ver_lin.setDivisione(rs.getBigDecimal("divisione"));
			    ver_lin.setEsito(rs.getString("esito"));
			    ver_lin.setPosizioneDiscesa(rs.getString("posizione_discesa"));
			    ver_lin.setPosizioneSalita(rs.getString("posizione_salita"));
			    
			    listaLinearita.add(ver_lin);
			    
			}
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}

		return listaLinearita;
	}
	
	
	public static List<VerAccuratezzaDTO> getListaProvaAccuratezza(int idMisura) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		ArrayList<VerAccuratezzaDTO> listaAccuratezza= new ArrayList<>();
		
		VerAccuratezzaDTO ver_acc = null;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("SELECT * FROM ver_accuratezza WHERE id_misura=? ORDER BY id ASC");
			pst.setInt(1, idMisura);
			
			rs=pst.executeQuery();
			
			
			while(rs.next())
			{
				ver_acc=new VerAccuratezzaDTO();
				ver_acc.setId(rs.getInt("id"));
				ver_acc.setTipoTara(rs.getInt("tipo_tara"));
			    ver_acc.setCampo(rs.getInt("campo"));
			    ver_acc.setPosizione(rs.getInt("posizione"));
			    ver_acc.setMassa(rs.getBigDecimal("massa"));
			    ver_acc.setIndicazione(rs.getBigDecimal("indicazione"));
			    ver_acc.setCaricoAgg(rs.getBigDecimal("carico_agg"));
			    ver_acc.setErrore(rs.getBigDecimal("errore"));
			    ver_acc.setErroreCor(rs.getBigDecimal("errore_cor"));
			    ver_acc.setMpe(rs.getBigDecimal("mpe"));
			    ver_acc.setEsito(rs.getString("esito"));
			    
			    listaAccuratezza.add(ver_acc);
			    
			}
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}

		return listaAccuratezza;
	}
	public static List<VerMobilitaDTO> getListaProvaMobilita(int idMisura) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		ArrayList<VerMobilitaDTO> listaMobilita= new ArrayList<>();
		
		VerMobilitaDTO ver_mob = null;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("SELECT * FROM ver_mobilita WHERE id_misura=? ORDER BY id ASC");
			pst.setInt(1, idMisura);
			
			rs=pst.executeQuery();			
			
			while(rs.next())
			{
				ver_mob=new VerMobilitaDTO();
				ver_mob.setId(rs.getInt("id"));
			    ver_mob.setCampo(rs.getInt("campo"));
			    ver_mob.setCaso(rs.getInt("caso"));
			    ver_mob.setCarico(rs.getInt("carico"));
			    ver_mob.setMassa(rs.getBigDecimal("massa"));
			    ver_mob.setIndicazione(rs.getBigDecimal("indicazione"));
			    ver_mob.setCaricoAgg(rs.getBigDecimal("carico_agg"));
			    ver_mob.setPostIndicazione(rs.getBigDecimal("post_indicazione"));
			    ver_mob.setDifferenziale(rs.getBigDecimal("differenziale"));
			    ver_mob.setDivisione(rs.getBigDecimal("divisione"));
			    ver_mob.setCheck(rs.getString("check_stato"));
			    ver_mob.setEsito(rs.getString("esito"));
			    
			    listaMobilita.add(ver_mob);
			    
			}
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}

		return listaMobilita;
		
	}
	public static ArrayList<VerClassiDTO> getListaClassi(int _classe) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		
		ArrayList<VerClassiDTO> listaClassi = new ArrayList<>();
		
		VerClassiDTO classe;
		
		if(_classe==5) _classe=1;
		
		if(_classe==6) _classe=2;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("SELECT * FROM ver_classi WHERE classe=? ORDER BY errore ASC");
			pst.setInt(1, _classe);
			
			rs=pst.executeQuery();
			
			while(rs.next())
			{
				classe=new VerClassiDTO();
				classe.setClasse(_classe);
				classe.setLimiteInferiore(rs.getInt("limite_inferiore"));
				classe.setLimiteSuperiore(rs.getInt("limite_superiore"));
				classe.setErrore(rs.getBigDecimal("errore"));
				
				listaClassi.add(classe);
			}
			
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}

		return listaClassi;
	}
	public static void updateVerRipetibilita(VerRipetibilitaDTO ripetibilita,int idMisura) throws Exception {
		

		Connection con=null;
		PreparedStatement pst=null;

		try 
		{
			con=getConnection();
			pst=con.prepareStatement("UPDATE ver_ripetibilita set massa=?,indicazione=?,carico_agg=?,portata=?,posizione=?"
					+ " WHERE id=? and campo=?");
			
			pst.setBigDecimal(1, ripetibilita.getMassa());
			pst.setBigDecimal(2, ripetibilita.getIndicazione());
			pst.setBigDecimal(3, ripetibilita.getCaricoAgg());
			pst.setBigDecimal(4, ripetibilita.getPortata());
			pst.setString(5, ripetibilita.getPosizione());
			pst.setInt(6, ripetibilita.getId());
			pst.setInt(7, ripetibilita.getCampo());
			
			
			pst.execute();
			
		
		
		 updateValoriComuniRipetibilita(con,ripetibilita,idMisura);
	} 
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}
		
	}
	private static void updateValoriComuniRipetibilita(Connection con, VerRipetibilitaDTO ripetibilita,int idMisura) throws Exception {
		
		PreparedStatement pst=null;

		try 
		{
			con=getConnection();
			pst=con.prepareStatement("UPDATE ver_ripetibilita set delta_portata=?,mpe=?,esito=? "
					+ " WHERE id_misura=? and campo=?");
			
			pst.setBigDecimal(1, ripetibilita.getDeltaPortata());
			pst.setBigDecimal(2, ripetibilita.getMpe());
			pst.setString(3, ripetibilita.getEsito());
			pst.setInt(4,idMisura);
			pst.setInt(5, ripetibilita.getCampo());
			
			pst.execute();
			
		
		
		
	} 
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
	
		}
		
	}
	public static void updateValoriDecentramento(VerDecentramentoDTO decentramento, int idMisura) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		PreparedStatement pst1=null;

		try 
		{
			con=getConnection();
			pst=con.prepareStatement("UPDATE ver_decentramento set tipo_ricettore=?,punti_appoggio=?,massa=?,indicazione=?,carico_agg=?,"
									+"errore=?,errore_cor=?,mpe=?"
									+"WHERE id=? AND campo=? ");
			
			pst.setInt(1,decentramento.getTipoRicettore() );
			pst.setInt(2, decentramento.getPuntiAppoggio());
			pst.setBigDecimal(3, decentramento.getMassa());
			pst.setBigDecimal(4,decentramento.getIndicazione());
			pst.setBigDecimal(5, decentramento.getCaricoAgg());
			pst.setBigDecimal(6, decentramento.getErrore());
			pst.setBigDecimal(7, decentramento.getErroreCor());
			pst.setBigDecimal(8, decentramento.getMpe());
			pst.setInt(9, decentramento.getId());
			pst.setInt(10, decentramento.getCampo());
			pst.execute();
			
		
			pst1=con.prepareStatement("UPDATE ver_decentramento set esito=?,speciale=?,carico=? WHERE id_misura=? AND campo=?");
			pst1.setString(1, decentramento.getEsito());
			pst1.setString(2, decentramento.getSpeciale());
			pst1.setBigDecimal(3, decentramento.getCarico());
			pst1.setInt(4, idMisura);
			pst1.setInt(5, decentramento.getCampo());
			pst1.execute();
			
			
			
	} 
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			pst1.close();
			con.close();
		}
		
		
	}
	public static void updateValoriLinearita(VerLinearitaDTO linearita, int id_misura) throws Exception {
	
		Connection con=null;
		PreparedStatement pst=null;
		PreparedStatement pst1=null;

		try 
		{
			con=getConnection();
			pst=con.prepareStatement("UPDATE ver_linearita set tipo_azzeramento=?,massa=?,indicazione_salita=?,indicazione_discesa=?,carico_agg_salita=?,carico_agg_discesa=?,"
									+"errore_salita=?,errore_discesa=?,errore_cor_salita=?,errore_cor_discesa=?,mpe=?,posizione_salita=?,posizione_discesa=? "
									+"WHERE id=? AND campo=? ");
			
			pst.setInt(1,linearita.getTipoAzzeramento() );
			pst.setBigDecimal(2,linearita.getMassa());
			pst.setBigDecimal(3, linearita.getIndicazioneSalita());
			pst.setBigDecimal(4,linearita.getIndicazioneDiscesa());
			pst.setBigDecimal(5, linearita.getCaricoAggSalita());
			pst.setBigDecimal(6, linearita.getCaricoAggDiscesa());
			pst.setBigDecimal(7, linearita.getErroreSalita());
			pst.setBigDecimal(8, linearita.getErroreDiscesa());
			pst.setBigDecimal(9, linearita.getErroreCorSalita());
			pst.setBigDecimal(10, linearita.getErroreCorDiscesa());
			pst.setBigDecimal(11, linearita.getMpe());
			pst.setString(12, linearita.getPosizioneSalita());
			pst.setString(13, linearita.getPosizioneDiscesa());
			pst.setInt(14, linearita.getId());
			pst.setInt(15, linearita.getCampo());
			pst.execute();
			
		
			pst1=con.prepareStatement("UPDATE ver_linearita set esito=? WHERE id_misura=? AND campo=?");
			pst1.setString(1, linearita.getEsito());
			pst1.setInt(2, id_misura);
			pst1.setInt(3, linearita.getCampo());
			pst1.execute();
			
			
			
	} 
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			pst1.close();
			con.close();
		}
		
	}
	public static void updateAccuratezzaTara(VerAccuratezzaDTO acc, int idMisura) throws Exception {
	
		
		Connection con=null;
		PreparedStatement pst=null;

		try 
		{
			con=getConnection();
			pst=con.prepareStatement("UPDATE ver_accuratezza set tipo_tara=?,massa=?,indicazione=?,carico_agg=?,"
									+"errore=?,errore_cor=?,mpe=?,esito=?"
									+"WHERE id=? AND campo=? ");
			
			pst.setInt(1,acc.getTipoTara() );
			pst.setBigDecimal(2, acc.getMassa());
			pst.setBigDecimal(3,acc.getIndicazione());
			pst.setBigDecimal(4, acc.getCaricoAgg());
			pst.setBigDecimal(5, acc.getErrore());
			pst.setBigDecimal(6, acc.getErroreCor());
			pst.setBigDecimal(7, acc.getMpe());
			pst.setString(8, acc.getEsito());
			pst.setInt(9, acc.getId());
			pst.setInt(10, acc.getCampo());
			pst.execute();
			
	} 
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}
		
		
	}
	public static void updateValoriMobilita(VerMobilitaDTO mob, int idMisura) throws Exception {
	
		
		Connection con=null;
		PreparedStatement pst=null;
		PreparedStatement pst1=null;

		try 
		{
			con=getConnection();
			pst=con.prepareStatement("UPDATE ver_mobilita set massa=?,indicazione=?,carico_agg=?,post_indicazione=?,differenziale=?,divisione=?,check_stato=? "+		
									"WHERE id=? AND campo=? ");
			

			pst.setBigDecimal(1, mob.getMassa());
			pst.setBigDecimal(2,mob.getIndicazione());
			pst.setBigDecimal(3, mob.getCaricoAgg());
			pst.setBigDecimal(4, mob.getPostIndicazione());
			pst.setBigDecimal(5,mob.getDifferenziale());
			pst.setBigDecimal(6, mob.getDivisione());
			pst.setString(7, mob.getCheck());
			pst.setInt(8, mob.getId());
			pst.setInt(9, mob.getCampo());
			pst.execute();
			
		
			pst1=con.prepareStatement("UPDATE ver_mobilita set esito=? WHERE id_misura=? AND campo=? AND caso=?");
			pst1.setString(1, mob.getEsito());
			pst1.setInt(2, idMisura);
			pst1.setInt(3, mob.getCampo());
			pst1.setInt(4, mob.getCaso());
			pst1.execute();
			
			
			
	} 
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			pst1.close();
			con.close();
		}
		
	}
	public static void terminaMisura(int id, String dataScadenza) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
	
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("UPDATE  ver_misura SET stato=1, data_scadenza =? WHERE id=?");

			pst.setString(1, dataScadenza);
			pst.setInt(2, id);
			
			

			pst.execute();		
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}

		
	}
	

	
	public static void updateMisura(VerMisuraDTO misura) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
	
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("UPDATE  ver_misura SET data_verificazione=?,data_scadenza=?,tipo_verifica=?,motivo_verifica=?,isDifetti=?,nome_riparatore=?,data_riparazione=?,"
								  + "tipo_risposta=?,seq_risposte=?,campioni_lavoro=?,numeroSigilli=? ,tInizio=?, tFine=?, altezza_org=?,altezza_util=?,latitudine_org=?,latitudine_util=?,"
								  + "gOrg=?,gUtil=?,gFactor=? WHERE id=?");

			
			if(misura.getDataVerificazione()!=null && misura.getDataVerificazione().length()>0) 
			{
				pst.setString(1,misura.getDataVerificazione());
			}
			else 
			{
				pst.setString(1,"");
			}
			
			if(misura.getDataScadenza()!=null && misura.getDataScadenza().length()>0) 
			{
				pst.setString(2,misura.getDataScadenza());
			}
			else 
			{
				pst.setString(2,"");
			}

			pst.setInt(3,misura.getTipo_verifica());
			pst.setInt(4,misura.getMotivo_verifica());
			
			if(misura.getIs_difetti()!=null && misura.getIs_difetti().length()>0) 
			{
				pst.setString(5,misura.getIs_difetti());
			}
			else 
			{
				pst.setString(5,"");
			}
			
			if(misura.getNomeRiparatore()!=null && misura.getNomeRiparatore().length()>0) 
			{
				pst.setString(6,misura.getNomeRiparatore());
			}
			else 
			{
				pst.setString(6,"");
			}
			
			if(misura.getDataRiparazione()!=null && misura.getDataRiparazione().length()>0) 
			{
				pst.setString(7,misura.getDataRiparazione());
			}
			else 
			{
				pst.setString(7,"");
			}
			
			pst.setInt(8, misura.getTipoRisposte());
			
			if(misura.getSeqRisposte()!=null && misura.getSeqRisposte().length()>0) 
			{
				pst.setString(9,misura.getSeqRisposte());
			}
			else 
			{
				pst.setString(9,"");
			}
			
			if(misura.getCampioniLavoro()!=null && misura.getCampioniLavoro().length()>0) 
			{
				pst.setString(10,misura.getCampioniLavoro());
			}
			else 
			{
				pst.setString(10,"");
			}
			
			pst.setInt(11,misura.getNumeroSigilli());

			pst.setDouble(12,misura.gettInizio());
			pst.setDouble(13,misura.gettFine());
			
			pst.setDouble(14,misura.getAltezza_org());
			pst.setDouble(15,misura.getAltezza_util());
			
			pst.setDouble(16,misura.getLatitudine_org());
			pst.setDouble(17,misura.getLatitudine_util());
			
			pst.setDouble(18,misura.getgOrg());
			pst.setDouble(19,misura.getgUtil());
			pst.setDouble(20,misura.getgFactor());

			pst.setInt(21, misura.getId());

			pst.execute();		
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
		
		
	}
	public static void updateErrore_correttoDiscesa(int id, BigDecimal erroreDiscesa_cor) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
	
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("UPDATE  ver_linearita SET errore_cor_discesa=? WHERE id=?");

			
			pst.setBigDecimal(1, erroreDiscesa_cor);
			pst.setInt(2, id);
			

			pst.execute();		
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally
		{
			pst.close();
			con.close();
		}
	}
	public static void saveFoto(int tipo, int idMisura, ByteArrayOutputStream file_att, String name) throws Exception {
	
		Connection con=null;
		PreparedStatement pst=null;
		try 
		{
			con=getConnection();
			
		if(tipo==1) 
		{
				pst=con.prepareStatement("UPDATE ver_misura SET file_inizio_prova=?,nomeFile_inizio_prova=? WHERE id=?");
				pst.setBytes(1, file_att.toByteArray());
				pst.setString(2,name);
				pst.setInt(3, idMisura);
				
			pst.execute();
		}
		if(tipo==2) 
		{
				pst=con.prepareStatement("UPDATE ver_misura SET file_fine_prova=?,nomeFile_fine_prova=? WHERE id=?");
				pst.setBytes(1, file_att.toByteArray());
				pst.setString(2,name);
				pst.setInt(3, idMisura);
				
			pst.execute();
		}
	
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}
	}
	public static boolean checkMatricola(String matricola) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs=null;
		try 
		{
			con=getConnection();
			
			pst=con.prepareStatement("SELECT * FROM ver_lista_matricole WHERE matricola=?");
			pst.setString(1, matricola);
			
			rs=pst.executeQuery();
			
			while(rs.next()) 
			{
				return true;
			}
	
				
			pst.execute();
		
	
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}
		return false;
	}
	public static void setRicettoreDecentramento(int idMisura, int i) throws Exception {
		

		Connection con=null;
		PreparedStatement pst=null;
		try 
		{
			con=getConnection();
			
		
				pst=con.prepareStatement("UPDATE ver_decentramento SET tipo_ricettore=? WHERE id_misura=?");
				pst.setInt(1, i);
				pst.setInt(2, idMisura);
				
			pst.execute();
		
	
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}
		
	}
	public static void updateNoteCombinazioni(String text, int id) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		try 
		{
			con=getConnection();
			
		
				pst=con.prepareStatement("UPDATE ver_misura SET note_combinazioni=? WHERE id=?");
				pst.setString(1, text);
				pst.setInt(2, id);
				
			pst.execute();
		
	
		}
		catch (Exception e) 
		{
		 e.printStackTrace();	
		 throw e;
		}
		finally
		{
			pst.close();
			con.close();
		}
		
	}




}

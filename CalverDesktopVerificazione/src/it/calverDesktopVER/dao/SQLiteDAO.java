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
import java.util.Iterator;

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
			sql="select a.* FROM ver_strumento a left join ver_misura b on a.id=b.id_ver_strumento where b.id_str is null";
		}
		
		if(filter==4)
		{
			sql="select a.* FROM ver_strumento a join ver_misura b on a.id=b.id_ver_strumento where b.stato=2";
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
			strumento.setData_ultima_verifica(rs.getDate("data_ultima_verifica"));
			strumento.setData_prossima_verifica(rs.getDate("data_prossima_verifica"));
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
			strumento.setData_ultima_verifica(rs.getDate("data_ultima_verifica"));
			strumento.setData_prossima_verifica(rs.getDate("data_prossima_verifica"));
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
			pst=con.prepareStatement("INSERT INTO ver_misura(id_ver_strumento,data_verificazione,seq_risposte,stato) VALUES(?,?,?,?)",pst.RETURN_GENERATED_KEYS);
			
			pst.setInt(1,Integer.parseInt(id));
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date d = new Date();
			pst.setString(2,sdf.format(d));
			pst.setString(3,"0");
			pst.setString(4,"0");
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
			
			pst=con.prepareStatement("select distinct(codice) from tblCampioni ");
			

			
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

	public static ArrayList<String> getListaParametriTaratura(String codiceCampione, ArrayList<String> listaTipiGrandezza) throws Exception {
		Connection con=null;
		PreparedStatement pst=null;
		ResultSet rs =null;
		ArrayList<String> toRet=new  ArrayList<String>();
		toRet.add("Seleziona Parametro....");
		try
		{
			con=getConnection();
			
			String tipoGrandezza=preparaTipiGrandezza(listaTipiGrandezza);
			
			pst=con.prepareStatement("select parametri_taratura FROM tblCampioni WHERE codice=? AND ("+tipoGrandezza);
					
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



	public static void terminaMisura(String idStrumento, String classe) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
	
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("UPDATE  tblMisuraSicurezzaElettrica SET stato=1, SK=? WHERE id_strumento=?");

			pst.setString(1, classe);
			pst.setInt(2, Integer.parseInt(idStrumento));
			
		

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
	


	public static int insertStrumento(VerStrumentoDTO strumento, String nomeSede) throws Exception {
		
		Connection con=null;
		PreparedStatement pst=null;
		try
		{
			con=getConnection();
			
			pst=con.prepareStatement("INSERT INTO tblStrumenti(indirizzo,denominazione,codice_interno,costruttore,modello,classificazione,matricola," +
															   "risoluzione,campo_misura,freq_verifica_mesi,tipoRapporto,statoStrumento," +
																"reparto,utilizzatore,procedura,id_tipo_strumento,note,creato,importato,luogo_verifica) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
																Statement.RETURN_GENERATED_KEYS);
			
			
//			pst.setString(1, nomeSede);
//			pst.setString(2, strumento.getDenominazione());
//			pst.setString(3, strumento.getCodice_interno());
//			pst.setString(4, strumento.getCostruttore());
//			pst.setString(5, strumento.getModello());
//			pst.setString(6, strumento.getClassificazione());
//			pst.setString(7, strumento.getMatricola());
//			pst.setString(8, strumento.getRisoluzione());
//			pst.setString(9, strumento.getCampo_misura());
//			pst.setInt(10, strumento.getFreq_taratura());
//			pst.setString(11, strumento.getTipoRapporto());
//			pst.setString(12, strumento.getStatoStrumento());
//			pst.setString(13, strumento.getReparto());
//			pst.setString(14, strumento.getUtilizzatore());
//			pst.setString(15, strumento.getProcedura());
//			pst.setString(16, strumento.getId_tipoStrumento());
//			pst.setString(17, strumento.getNote());
//			pst.setString(18, "S");
//			pst.setString(19, "N");
//			pst.setString(20, ""+strumento.getLuogoVerifica());
//		
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
			pst=con.prepareStatement("UPDATE tblStrumenti set denominazione=?,codice_interno=?,costruttore=?,modello=?," +
															  "classificazione=?,matricola=?,risoluzione=?,campo_misura=?," +
															  "freq_verifica_mesi=?,tipoRapporto=?,reparto=?,utilizzatore=?," +
															  "procedura=?,id_tipo_strumento=?,note=?,strumentoModificato='S',luogo_verifica=? WHERE id=?");
		
//			pst.setString(1,strumento.getDenominazione());
//			pst.setString(2,strumento.getCodice_interno());
//			pst.setString(3,strumento.getCostruttore());
//			pst.setString(4,strumento.getModello());
//			pst.setInt(5,strumento.getIdClassificazione());
//			pst.setString(6,strumento.getMatricola());
//			pst.setString(7,strumento.getRisoluzione());
//			pst.setString(8,strumento.getCampo_misura());
//			pst.setInt(9,strumento.getFreq_taratura());
//			pst.setInt(10, strumento.getIdTipoRappoto());
//			pst.setString(11,strumento.getReparto());
//			pst.setString(12,strumento.getUtilizzatore());
//			pst.setString(13,strumento.getProcedura());
//			pst.setString(14, strumento.getId_tipoStrumento());
//			pst.setString(15, strumento.getNote());
//			pst.setInt(16, strumento.getLuogoVerifica());
//			pst.setInt(17, strumento.get__id());
//			
			
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
	
	
	public static void insertProvaLineare(int idMisura, int tipoStrumento) throws Exception {

		Connection con =null;
		PreparedStatement pst= null;

		try{
			con=getConnection();
			pst=con.prepareStatement("INSERT INTO ver_linearita(id_misura,campo,riferimento) VALUES(?,?,?)");


			if(tipoStrumento!=3) 
			{
				for (int i = 0; i < 5; i++) 

				{
					pst.setInt(1,idMisura);		
					pst.setInt(2,0);
					pst.setInt(3,i+1);

					pst.execute();
				}

			}
			else 
			{
				for (int y = 0; y < 3; y++) {

					int campo=y+1;	

					for (int i = 0; i < 5; i++) 

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


			if(tipoStrumento!=3) 
			{
				for (int i = 0; i < 6; i++) 

				{
					pst.setInt(1,idMisura);		
					pst.setInt(2,0);
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


			if(tipoStrumento!=3) 
			{
				
					pst.setInt(1,idMisura);		
					pst.setInt(2,0);
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


			if(tipoStrumento!=3) 
			{
			
			for (int z = 1; z <=2; z++) {
				
			
				for (int i = 0; i < 3; i++) 

				{
					pst.setInt(1,idMisura);		
					pst.setInt(2,0);
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
						
					
					for (int i = 0; i < 6; i++) 

					{
						pst.setInt(1,idMisura);		
						pst.setInt(2,campo);
						pst.setInt(3,z);
						pst.setInt(3,i+1);

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
			pst=con.prepareStatement("INSERT INTO ver_decentramento(id_misura,campo,posizione) VALUES(?,?,?)");


			if(tipoStrumento!=3) 
			{
				for (int i = 0; i < 10; i++) 

				{
					pst.setInt(1,idMisura);		
					pst.setInt(2,0);
					pst.setInt(3,i+1);

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
		ResultSet rs=null;
		int toRet=0;
		
		try 
		{
			con=getConnection();
			pst=con.prepareStatement("UPDATE ver_misura set seq_risposte=? WHERE id=?");
			
			pst.setString(1, sequence);
			pst.setInt(2, idMisura);
			
			pst.executeQuery();
			
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

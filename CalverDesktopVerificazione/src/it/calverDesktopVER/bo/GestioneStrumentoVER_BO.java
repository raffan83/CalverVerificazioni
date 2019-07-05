package it.calverDesktopVER.bo;

import java.util.ArrayList;

import it.calverDesktopVER.dao.SQLiteDAO;
import it.calverDesktopVER.dto.VerStrumentoDTO;

public class GestioneStrumentoVER_BO {

	public static ArrayList<VerStrumentoDTO> getListaStrumenti(int filter) throws Exception {
	
		return SQLiteDAO.getListaStrumenti(filter);
	}

	public static VerStrumentoDTO getStrumento(String id) throws Exception {
		
		return SQLiteDAO.getStrumento(id);
	}

	public static int insertStrumento(VerStrumentoDTO strumento) throws Exception {
		
		String nomeSede=SQLiteDAO.getNomeSede();
		
		return SQLiteDAO.insertStrumento(strumento,nomeSede);
		
	}

	public static int updateStrumento(VerStrumentoDTO strumento) throws Exception {
	
		return SQLiteDAO.updateStrumento(strumento);
		
	}

	/*

	public static int duplicaStrumento(String idStrumento) throws Exception {
		
		StrumentoDTO str=SQLiteDAO.getStrumentoDB(idStrumento);
		
		int max =SQLiteDAO.getMaxIDStrumento();
		
		String nomeSede=SQLiteDAO.getNomeSede();
		
		str.set__id(max+1);
		str.setMatricola("DUP");
		str.setCodice_interno("DUP");
		
		SQLiteDAO.insertStrumento(str,nomeSede);
		
		ProvaMisuraDTO provaMisura=null;
		
		try 
		{
			provaMisura =  GestioneMisuraBO.getProvaMisura(idStrumento);	
		} catch (Exception e) {
			// TODO: handle exception
		} 
		
		
		if(provaMisura!=null) 
		{
			duplicaMisura(str.get__id(),provaMisura);
		}
		
		return max+1;
	}

	private static void duplicaMisura(int idStr, ProvaMisuraDTO provaMisura) throws Exception {
		
		int idMisura=GestioneMisuraBO.insertMisura(""+idStr);
		
		for (TabellaMisureDTO tabella : provaMisura.getListaTabelle()) 
		{
			int seq_tab = SQLiteDAO.getMaxTablella(idMisura);
			seq_tab++;
			
			for (MisuraDTO misura : tabella.getListaMisure()) 
			{
				SQLiteDAO.inserisciRigaTabellaDuplicata(misura,seq_tab,idMisura,tabella.getTipoProva());
			}
			
		}
		
	}
*/
}

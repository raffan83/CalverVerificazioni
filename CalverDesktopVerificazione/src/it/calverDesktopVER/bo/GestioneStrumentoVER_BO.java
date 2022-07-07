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
		
		return SQLiteDAO.insertStrumento(strumento);
		
	}

	public static int updateStrumento(VerStrumentoDTO strumento) throws Exception {
	
		return SQLiteDAO.updateStrumento(strumento);
		
	}

	public static boolean valutaStrumento(VerStrumentoDTO strumento) {
		
		if(strumento.getId_tipo_strumento()==1) 
		{
			if(strumento.getPortata_min_C1()==null) 
			{
				return false;
			}
			if(strumento.getPortata_max_C1()==null) 
			{
				return false;
			}
			if(strumento.getDiv_rel_C1()==null) 
			{
				return false;
			}
			if(strumento.getDiv_ver_C1()==null) 
			{
				return false;
			}
			if(strumento.getNumero_div_C1()==null) 
			{
				return false;
			}
		}
		
		if(strumento.getId_tipo_strumento()==2) 
		{
			if(strumento.getPortata_min_C1()==null) 
			{
				return false;
			}
			if(strumento.getPortata_max_C1()==null) 
			{
				return false;
			}
			if(strumento.getDiv_rel_C1()==null) 
			{
				return false;
			}
			if(strumento.getDiv_ver_C1()==null) 
			{
				return false;
			}
			if(strumento.getNumero_div_C1()==null) 
			{
				return false;
			}
			
			if(strumento.getPortata_min_C2()==null) 
			{
				return false;
			}
			if(strumento.getPortata_max_C2()==null) 
			{
				return false;
			}
			if(strumento.getDiv_rel_C2()==null) 
			{
				return false;
			}
			if(strumento.getDiv_ver_C2()==null) 
			{
				return false;
			}
			if(strumento.getNumero_div_C2()==null) 
			{
				return false;
			}
		}
		
		if(strumento.getId_tipo_strumento()==3) 
		{
			if(strumento.getPortata_min_C1()==null) 
			{
				return false;
			}
			if(strumento.getPortata_max_C1()==null) 
			{
				return false;
			}
			if(strumento.getDiv_rel_C1()==null) 
			{
				return false;
			}
			if(strumento.getDiv_ver_C1()==null) 
			{
				return false;
			}
			if(strumento.getNumero_div_C1()==null) 
			{
				return false;
			}
			
			if(strumento.getPortata_min_C3()==null) 
			{
				return false;
			}
			if(strumento.getPortata_max_C3()==null) 
			{
				return false;
			}
			if(strumento.getDiv_rel_C3()==null) 
			{
				return false;
			}
			if(strumento.getDiv_ver_C3()==null) 
			{
				return false;
			}
			if(strumento.getNumero_div_C3()==null) 
			{
				return false;
			}
			
			if(strumento.getPortata_min_C2()==null) 
			{
				return false;
			}
			if(strumento.getPortata_max_C2()==null) 
			{
				return false;
			}
			if(strumento.getDiv_rel_C2()==null) 
			{
				return false;
			}
			if(strumento.getDiv_ver_C2()==null) 
			{
				return false;
			}
			if(strumento.getNumero_div_C2()==null) 
			{
				return false;
			}
		}
		if(strumento.getId_tipo_strumento()==4) 
		{
			if(strumento.getPortata_min_C1()==null) 
			{
				return false;
			}
			if(strumento.getPortata_max_C1()==null) 
			{
				return false;
			}
			if(strumento.getDiv_rel_C1()==null) 
			{
				return false;
			}
			if(strumento.getDiv_ver_C1()==null) 
			{
				return false;
			}
			if(strumento.getNumero_div_C1()==null) 
			{
				return false;
			}
		}
		if(strumento.getId_tipo_strumento()==5) 
		{
			if(strumento.getPortata_min_C1()==null) 
			{
				return false;
			}
			if(strumento.getPortata_max_C1()==null) 
			{
				return false;
			}
			if(strumento.getDiv_rel_C1()==null) 
			{
				return false;
			}
			if(strumento.getDiv_ver_C1()==null) 
			{
				return false;
			}
			if(strumento.getNumero_div_C1()==null) 
			{
				return false;
			}
		}
		
		return true;
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

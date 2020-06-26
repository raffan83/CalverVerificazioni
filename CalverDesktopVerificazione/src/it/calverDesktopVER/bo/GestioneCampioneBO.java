package it.calverDesktopVER.bo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import it.calverDesktopVER.dao.SQLiteDAO;
import it.calverDesktopVER.dto.ParametroTaraturaDTO;


public class GestioneCampioneBO {

	public static String[] getListaCampioniPerStrumento(String idStrumento) throws Exception {
		
		
		ArrayList<String> tmp=SQLiteDAO.getListaCampioniPerStrumento(idStrumento);
		
		String[] stockArr = new String[tmp.size()];
		
		stockArr = tmp.toArray(stockArr);
		
		return stockArr;
	
	}

	
	public static String[] getListaCampioniCompleta() throws Exception {
		
		
		ArrayList<String> tmp=SQLiteDAO.getListaCampioniCompleta();
		
		String[] stockArr = new String[tmp.size()];
		
		stockArr = tmp.toArray(stockArr);
		
		return stockArr;
	
	}
	
	public static String[] getParametriTaratura(String codiceCampione) throws Exception {

		
		ArrayList<String> tmp=SQLiteDAO.getListaParametriTaratura(codiceCampione);
		
		String[] stockArr = new String[tmp.size()];
		
		stockArr = tmp.toArray(stockArr);
		
			return stockArr;
	
	}
	


}

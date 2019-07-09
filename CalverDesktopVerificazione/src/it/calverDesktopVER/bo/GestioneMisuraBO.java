package it.calverDesktopVER.bo;


import java.util.ArrayList;

import it.calverDesktopVER.dao.SQLiteDAO;


// Referenced classes of package it.calverDesktop.bo:
//            SessionBO

public class GestioneMisuraBO
{

    public static int isPresent(String id)
        throws Exception
    {
        int result = SQLiteDAO.isPresent(id);
        return result;
    }

    public static int insertMisura(String id, int tipoStrumento)
        throws Exception
    {
    	int idMisura=SQLiteDAO.insertMisura(id);
    	
    	SQLiteDAO.insertProvaLineare(idMisura,tipoStrumento);
    	
    	SQLiteDAO.insertProvaRipetibilita(idMisura,tipoStrumento);
    	
    	SQLiteDAO.insertProvaAccuratezza(idMisura,tipoStrumento);
    	
    	SQLiteDAO.insertProvaMobilita(idMisura,tipoStrumento);
    	
    	SQLiteDAO.insertProvaDecentramento(idMisura,tipoStrumento);
    	
        return idMisura;
    }

    public static Integer getStatoMisura(String id)
        throws Exception
    {
        Integer toRet = SQLiteDAO.getStatoMisura(id);
        return toRet;
    }


	public static ArrayList<String> getListaDomandeControlloPreliminare() {
		ArrayList<String> toReturn = new ArrayList<>();
		
		toReturn.add("Lo strumento sottoposto a verificazione presenta la targhetta con il simbolo della \"marcatura CE\"?");
		toReturn.add("Lo strumento sottoposto a verificazione è munito di \"Dichiarazione di Conformità CE\" con relativo numero di identificazione?");
		toReturn.add("Lo strumento sottoposto a verificazione è munito di \"marcatura metrologica supplementare M”?");
		toReturn.add("Nella targhetta identificativa è presente il nome del fabbricante, la sua denominazione commerciale registrata o il suo marchio registrato?");
		toReturn.add("Nella targhetta identificativa è presente la classe di precisione, racchiusa in un ovale o in due lineette orizzontali unite da due semicerchi?");
		toReturn.add("Nella targhetta identificativa è presente la portata massima (Max) dello strumento?");
		toReturn.add("Nella targhetta identificativa è presente la portata minima (Min) dello strumento?");
		toReturn.add("Nella targhetta identificativa è presente la divisione di verifica (e) dello strumento?");
		toReturn.add("Nella targhetta identificativa è presente il numero di tipo, di lotto o di serie dello strumento?");
		toReturn.add("Per gli strumenti costituiti di unità distinte ma associate, è presente il marchio di identificazione su ciascuna unità?");
		toReturn.add("La divisione, se è diversa da e, è presente nella forma d=…?");
		toReturn.add("È presente l’effetto massimo additivo di tara, nella forma T = + …?");
		toReturn.add("È presente l’effetto massimo sottrattivo di tara, se è diverso da Max, nella forma T = - …?");
		toReturn.add("È presente il carico limite, se è diverso da Max, nella forma Lim …?");
		toReturn.add("Sono presenti i valori limite di temperatura, nella forma …°C/…°C?");
		toReturn.add("È presente il rapporto tra ricettore di peso e di carico?");
		
		
		return toReturn;
	}

	public static void saveControlloPreliminare(int idMisura, String sequence) throws Exception {
		
		SQLiteDAO.saveControlloPreliminare(idMisura,sequence);
		
	}

	
}


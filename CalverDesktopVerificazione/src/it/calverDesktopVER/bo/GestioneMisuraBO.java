package it.calverDesktopVER.bo;


import java.math.BigDecimal;
import java.util.ArrayList;

import org.sqlite.SQLite;

import it.calverDesktopVER.dao.SQLiteDAO;
import it.calverDesktopVER.dto.VerAccuratezzaDTO;
import it.calverDesktopVER.dto.VerClassiDTO;
import it.calverDesktopVER.dto.VerDecentramentoDTO;
import it.calverDesktopVER.dto.VerLinearitaDTO;
import it.calverDesktopVER.dto.VerMisuraDTO;
import it.calverDesktopVER.dto.VerMobilitaDTO;
import it.calverDesktopVER.dto.VerRipetibilitaDTO;


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

	public static VerMisuraDTO getMisura(int idMisura) throws Exception {
		
		VerMisuraDTO misura = SQLiteDAO.getMisura(idMisura);
		
		misura.setVerRipetibilitas(SQLiteDAO.getListaProvaRipetibilita(idMisura));
		
		misura.setVerDecentramentos(SQLiteDAO.getListaProvaDecentramento(idMisura));
		
		misura.setVerLinearitas(SQLiteDAO.getListaProvaLinearita(idMisura));
		
		
		misura.setVerAccuratezzas(SQLiteDAO.getListaProvaAccuratezza(idMisura));
		
		misura.setVerMobilitas(SQLiteDAO.getListaProvaMobilita(idMisura));
		
		return misura;
	}

	public static ArrayList<VerClassiDTO> getListaClassi(int classe) throws Exception {
		
		
		return SQLiteDAO.getListaClassi(classe);
	}

	public static void updateVerRipetibilita(VerRipetibilitaDTO ripetibilita,int idMisura) throws Exception {
		
		SQLiteDAO.updateVerRipetibilita(ripetibilita,idMisura);
		
	}

	public static void updateValoriDecentramento(VerDecentramentoDTO decentramento, int idMisura) throws Exception {
		
		SQLiteDAO.updateValoriDecentramento(decentramento,idMisura);
		
	}

	public static void updateValoriLinearita(VerLinearitaDTO linearita, int idMisura) throws Exception {
		
		SQLiteDAO.updateValoriLinearita(linearita,idMisura);
		
	}

	public static void updateAccuratezzaTara(VerAccuratezzaDTO acc, int idMisura) throws Exception {
		
		SQLiteDAO.updateAccuratezzaTara(acc,idMisura);
		
	}

	public static void updateValoriMobilita(VerMobilitaDTO mob, int idMisura) throws Exception {
		
		SQLiteDAO.updateValoriMobilita(mob,idMisura);
		
	}

	public static void terminaMisura(int id) throws Exception {

		SQLiteDAO.terminaMisura(id);
	}

	public static void updateMisura(VerMisuraDTO misura) throws Exception {
		
		SQLiteDAO.updateMisura(misura);
		
	}

	public static void updateErrore_correttoDiscesa(int id, BigDecimal erroreDiscesa_cor) throws Exception {
		
		SQLiteDAO.updateErrore_correttoDiscesa(id,erroreDiscesa_cor);
		
	}

	
}


package it.calverDesktopVER.bo;


import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.sqlite.SQLite;

import it.calverDesktopVER.dao.SQLiteDAO;
import it.calverDesktopVER.dto.VerAccuratezzaDTO;
import it.calverDesktopVER.dto.VerClassiDTO;
import it.calverDesktopVER.dto.VerDecentramentoDTO;
import it.calverDesktopVER.dto.VerLinearitaDTO;
import it.calverDesktopVER.dto.VerMisuraDTO;
import it.calverDesktopVER.dto.VerMobilitaDTO;
import it.calverDesktopVER.dto.VerRipetibilitaDTO;
import it.calverDesktopVER.dto.VerStrumentoDTO;


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

    public static int insertMisura(String id, int tipoStrumento, VerStrumentoDTO strumento)
        throws Exception
    {
    	int idMisura=SQLiteDAO.insertMisura(id,strumento);
    	
    	SQLiteDAO.insertProvaLineare(idMisura,strumento);
    	
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


	public static ArrayList<String> getListaDomandeControlloPreliminare(int i) {
		ArrayList<String> toReturn = new ArrayList<>();
		
		if(i==0)
		{
			toReturn.add("<html> A) Verificare se lo strumento di misura sottoposto a verificazione è munito dell’ulteriore documentazione indicata nel Decreto di ammissione <br>"
					+ "a verifica (per gli strumenti muniti di bolli di verificazione prima nazionale) es. manuale istruzione, manuale di uso e manutenzione, data sheet, eventuali<br> "
					+ "specifiche dei componenti software, ecc.<html>");
			toReturn.add("<html>B) Verificare l’esistenza e la corrispondenza sullo strumento di misura delle iscrizioni regolamentari previste nel corrispondente decreto di ammissione <br>"
					+ "a verifica (per gli strumenti muniti di bolli di verificazione prima nazionale)</html>");
			toReturn.add("<html>C) Accertare la presenza, integrità, leggibilità e rispondenza ai documenti di omologazione e piani di legalizzazione dello strumento di misura, delle seguenti<br> "
					+ "tipologie di impronte di verificazione prima nazionale, oppure di verificazione prima CEE, oppure di verificazione prima CE, oppure di verifica CE.<html>");
			toReturn.add("<html>D) Verificare l’esistenza sullo strumento di misura dei sigilli o di altri elementi di protezione in rispondenza a decreto di ammissione a verifica <br>"
					+ "(per gli strumenti muniti di bolli di verificazione prima nazionale)</html>");
		}else 
		{
			toReturn.add("<html>A) Verificare se lo strumento di misura sottoposto a verificazione è munito dell’ulteriore documentazione indicata nell’Attestazione/Certificazione <br>"
					+ "di esame CE/UE del tipo o di progetto (per gli strumenti conformi alla normativa europea) es. manuale istruzione, manuale di uso e manutenzione, data <br>"
					+ "sheet, eventuali specifiche dei componenti software, ecc.</html>");
			toReturn.add("<html>B)Verificare l’esistenza e la corrispondenza sullo strumento di misura delle iscrizioni regolamentari previste nel corrispondente <br>"
					+ "Attestazione/Certificazione di esame CE/UE del tipo o di progetto (per gli strumenti conformi alla normativa europea)<html>");
			toReturn.add("<html>C)Accertare la presenza, integrità, leggibilità e rispondenza ai documenti di omologazione e piani di legalizzazione dello strumento di misura, delle <br>"
					+ "seguenti tipologie di impronte di verificazione prima nazionale, oppure di verificazione prima CEE, oppure di verificazione prima CE, oppure di verifica CE.</html>");
			toReturn.add("<html>D)Verificare l’esistenza sullo strumento di misura dei sigilli o di altri elementi di protezione in rispondenza all Attestazione/Certificazione di esame CE/UE<br> "
					+ "del tipo o di progetto (per gli strumenti conformi alla normativa europea)<html>");
		}
		toReturn.add("<html>E) In caso di presenza di sigillo elettronico con contatore di eventi, accertare la corrispondenza tra l’indicazione di detto contatore e il numero <br>"
				+ " riscontrato ,secondo i casi, in occasione dell’ultima verificazione periodica, della verificazione prima o CE oppure dell’ultima rilegalizzazione</html>");
		toReturn.add("<html>F) Strumento di misura sottoposto a riparazione (ove siano stati rimossi i sigilli di protezione anche di tipo elettronico) antecedentemente alla prima <br>"
				+ "verificazione periodica.<br>"+
				"Se applicabile, procedere con i seguenti punti 1, 2 e 3.<br>"+ 
				"In caso di non applicabilità, procedere al successivo punto H<br>"+
				"1. Verificare presenza della dichiarazione (o sua copia) rilasciata al titolare dello strumento dal riparatore, contenente la descrizione dell’intervento<br>"
				+ " &nbsp; &nbsp;effettuato e dei sigilli provvisori applicati e di cui è stata informata la CCIAA competente per territorio <br>"+
				"2. Verificare la corrispondenza tra i sigilli provvisori applicati sullo strumento dal riparatore e la descrizione della riparazione effettuata annotata sulla <br>"
				+ " &nbsp; &nbsp;dichiarazione di cui al precedente punto G.1.<br>"+
				"3. Annotare sul libretto metrologico la dichiarazione di riparazione (o sua copia) rilasciata al titolare dello strumento dal riparatore indicata al precedente"
				+ "<br>&nbsp; &nbsp; punto G.1.</html>");
		toReturn.add("<html>G) Strumento di misura sottoposto a riparazione (ove siano stati rimossi i sigilli di protezione anche di tipo elettronico) successivamente alla prima verificazione periodica:<br>" + 
				"Se applicabile, procedere con i seguenti punti 1 e 2.<br>" + 
				"1. Verificare la presenza dell’annotazione della riparazione effettuata dal riparatore all’interno del libretto metrologico dello strumento di misura. <br>" + 
				"2. Verificare la corrispondenza tra i sigilli provvisori applicati sullo strumento dal riparatore e la descrizione della riparazione effettuata annotata sul libretto<br>"
				+ "&nbsp; &nbsp; metrologico di cui al precedente punto H.1.<br>");
		toReturn.add("<html>H) Verificare le condizioni esterne dello strumento, dei dispositivi di comando, regolazione (es. piedini di livello e bolla) e di visualizzazione <br>"
				+ "(es. display, scale graduate)<html>");
		toReturn.add("<html>I) Rilascio libretto metrologico (art.4 c.12 DM 93/17)<html>");
		toReturn.add("<html>L) Compilazione libretto metrologico esistente<html>");
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

	public static void terminaMisura(int id, String dataScadenza) throws Exception {

		SQLiteDAO.terminaMisura(id,dataScadenza);
	}

	public static void updateMisura(VerMisuraDTO misura) throws Exception {
		
		SQLiteDAO.updateMisura(misura);
		
	}

	public static void updateErrore_correttoDiscesa(int id, BigDecimal erroreDiscesa_cor) throws Exception {
		
		SQLiteDAO.updateErrore_correttoDiscesa(id,erroreDiscesa_cor);
		
	}

	public static String getDataScadenzaMisura(VerMisuraDTO misura, int freq_mesi) throws ParseException {
		
		SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
		
		Calendar c = Calendar.getInstance(); 
		c.setTime(sdf.parse(misura.getDataVerificazione())); 
		c.add(Calendar.MONTH,freq_mesi);
		c.getTime();
		
		return sdf.format(new java.sql.Date(c.getTime().getTime()));
		
	}

	public static void saveFoto(int tipo, int idMisura, ByteArrayOutputStream file_att, String name) throws Exception {
		
		SQLiteDAO.saveFoto(tipo,idMisura,file_att,name);
		
	}

	public static void setRicettoreDecentramento(int id, int i) throws Exception {
		
		SQLiteDAO.setRicettoreDecentramento(id,i);
		
	}

	public static BigDecimal calcoloG(double a, double lon) {
		
		
		double part1= 1+0.0053024*Math.pow(Math.sin(Math.toRadians(lon)),2);
	
		double part2= 0.0000058*Math.pow(Math.sin(2*Math.toRadians(lon)),2);
		
		double g= 9.780318*(part1-part2)-0.000003085*a;
		
		return new BigDecimal(g);
	}

	public static void updateNoteCombinazioni(String text, int id) throws Exception {
		
		SQLiteDAO.updateNoteCombinazioni(text,id);
		
	}

	public static void updateValoriLinearitaMassa(double m0, int id_m0) throws Exception {
	
		SQLiteDAO.updateValoriLinearita(m0,id_m0);
		
	}
	
}


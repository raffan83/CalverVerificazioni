package it.calverDesktopVER.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class VerMisuraDTO {

	private static final long serialVersionUID = 1L;

	
	private int id;

	private Date dataRiparazione;

	private Date dataScadenza;

	private Date dataVerificazione;

	
	private int idNonConforme;

	
	private int idTecnicoVerificatore;


	private int idVerIntervento;

	
	private int idVerStrumento;

	
	private String nomeRiparatore;

	private String numeroAttestato;

	private String numeroRapporto;

	private String procedura;

	private String registro;

	
	private String seqRisposte;
	
	private int stato;
	

	private List<VerDecentramentoDTO> verDecentramentos;

	private List<VerRipetibilitaDTO> verRipetibilitas;
	
	private List<VerAccuratezzaDTO> verAccuratezzas;
	
	private List<VerMobilitaDTO> verMobilitas;
	
	private List<VerLinearitaDTO> verLinearitas;

	public VerMisuraDTO() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDataRiparazione() {
		return this.dataRiparazione;
	}

	public void setDataRiparazione(Date dataRiparazione) {
		this.dataRiparazione = dataRiparazione;
	}

	public Date getDataScadenza() {
		return this.dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public Date getDataVerificazione() {
		return this.dataVerificazione;
	}

	public void setDataVerificazione(Date dataVerificazione) {
		this.dataVerificazione = dataVerificazione;
	}

	public int getIdNonConforme() {
		return this.idNonConforme;
	}

	public void setIdNonConforme(int idNonConforme) {
		this.idNonConforme = idNonConforme;
	}

	public int getIdTecnicoVerificatore() {
		return this.idTecnicoVerificatore;
	}

	public void setIdTecnicoVerificatore(int idTecnicoVerificatore) {
		this.idTecnicoVerificatore = idTecnicoVerificatore;
	}

	public int getIdVerIntervento() {
		return this.idVerIntervento;
	}

	public void setIdVerIntervento(int idVerIntervento) {
		this.idVerIntervento = idVerIntervento;
	}

	public int getIdVerStrumento() {
		return this.idVerStrumento;
	}

	public void setIdVerStrumento(int idVerStrumento) {
		this.idVerStrumento = idVerStrumento;
	}

	public String getNomeRiparatore() {
		return this.nomeRiparatore;
	}

	public void setNomeRiparatore(String nomeRiparatore) {
		this.nomeRiparatore = nomeRiparatore;
	}

	public String getNumeroAttestato() {
		return this.numeroAttestato;
	}

	public void setNumeroAttestato(String numeroAttestato) {
		this.numeroAttestato = numeroAttestato;
	}

	public String getNumeroRapporto() {
		return this.numeroRapporto;
	}

	public void setNumeroRapporto(String numeroRapporto) {
		this.numeroRapporto = numeroRapporto;
	}

	public String getProcedura() {
		return this.procedura;
	}

	public void setProcedura(String procedura) {
		this.procedura = procedura;
	}

	public String getRegistro() {
		return this.registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}

	public String getSeqRisposte() {
		return this.seqRisposte;
	}

	public void setSeqRisposte(String seqRisposte) {
		this.seqRisposte = seqRisposte;
	}

	public List<VerDecentramentoDTO> getVerDecentramentos(int campo) {

		    List<VerDecentramentoDTO> lista = new ArrayList<VerDecentramentoDTO>();
				
				for (VerDecentramentoDTO ver : verDecentramentos) 
				{
					if(ver.getCampo()==campo) 
					{
						lista.add(ver);
					}
				}
				
				return lista;
				
		
	}

	public void setVerDecentramentos(List<VerDecentramentoDTO> verDecentramentos) {
		this.verDecentramentos = verDecentramentos;
	}

	public List<VerRipetibilitaDTO> getVerRipetibilitas(int campo) {
		
		List<VerRipetibilitaDTO> lista = new ArrayList<VerRipetibilitaDTO>();
		
		for (VerRipetibilitaDTO ver : verRipetibilitas) 
		{
			if(ver.getCampo()==campo) 
			{
				lista.add(ver);
			}
		}
		
		return lista;
		
	}

	public void setVerRipetibilitas(List<VerRipetibilitaDTO> verRipetibilitas) {
		this.verRipetibilitas = verRipetibilitas;
	}

	public List<VerAccuratezzaDTO> getVerAccuratezzas(int campo) {
		
		List<VerAccuratezzaDTO> lista = new ArrayList<VerAccuratezzaDTO>();
		
		for (VerAccuratezzaDTO ver : verAccuratezzas) 
		{
			if(ver.getCampo()==campo) 
			{
				lista.add(ver);
			}
		}
		
		return lista;
	}

	public void setVerAccuratezzas(List<VerAccuratezzaDTO> verAccuratezzas) {
		this.verAccuratezzas = verAccuratezzas;
	}

	public List<VerMobilitaDTO> getVerMobilitas(int campo,int caso) {
		
		  List<VerMobilitaDTO> lista = new ArrayList<VerMobilitaDTO>();
			
			for (VerMobilitaDTO ver : verMobilitas) 
			{
				if(ver.getCampo()==campo && ver.getCaso()==caso) 
				{
					lista.add(ver);
				}
			}
			
			return lista;
		

	}

	public void setVerMobilitas(List<VerMobilitaDTO> verMobilitas) {
		this.verMobilitas = verMobilitas;
	}

	public List<VerLinearitaDTO> getVerLinearitas(int campo) {
		
    List<VerLinearitaDTO> lista = new ArrayList<VerLinearitaDTO>();
		
		for (VerLinearitaDTO ver : verLinearitas) 
		{
			if(ver.getCampo()==campo) 
			{
				lista.add(ver);
			}
		}
		
		return lista;
		
	
	}

	public void setVerLinearitas(List<VerLinearitaDTO> verLinearitas) {
		this.verLinearitas = verLinearitas;
	}

	public int getStato() {
		return stato;
	}

	public void setStato(int stato) {
		this.stato = stato;
	}
	
	
}

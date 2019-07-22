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

	private int idVerStrumento;

	
	private String nomeRiparatore;

	private String numeroAttestato;

	private String numeroRapporto;

	private String seqRisposte;
	

	private List<VerDecentramentoDTO> verDecentramentos;

	private List<VerRipetibilitaDTO> verRipetibilitas;
	
	private List<VerAccuratezzaDTO> verAccuratezzas;
	
	private List<VerMobilitaDTO> verMobilitas;
	
	private List<VerLinearitaDTO> verLinearitas;
	
	private String campioniLavoro;
	
	private int tipo_verifica;
	
	private int motivo_verifica;
	
	private String is_difetti;
	
	private int stato;

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

	public List<VerAccuratezzaDTO> getVerAccuratezzas() {
		return verAccuratezzas;
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

	public String getCampioniLavoro() {
		return campioniLavoro;
	}

	public void setCampioniLavoro(String campioniLavoro) {
		this.campioniLavoro = campioniLavoro;
	}
	

	public String getIs_difetti() {
		return is_difetti;
	}

	public void setIs_difetti(String is_difetti) {
		this.is_difetti = is_difetti;
	}

	public int getTipo_verifica() {
		return tipo_verifica;
	}

	public void setTipo_verifica(int tipo_verifica) {
		this.tipo_verifica = tipo_verifica;
	}

	public int getMotivo_verifica() {
		return motivo_verifica;
	}

	public void setMotivo_verifica(int motivo_verifica) {
		this.motivo_verifica = motivo_verifica;
	}

	public int getStato() {
		return stato;
	}

	public void setStato(int stato) {
		this.stato = stato;
	}

	public int getIdVerStrumento() {
		return idVerStrumento;
	}

	public void setIdVerStrumento(int idVerStrumento) {
		this.idVerStrumento = idVerStrumento;
	}
	
	
}

package it.calverDesktopVER.dto;

import java.math.BigDecimal;


public class VerLinearitaDTO {

	private static final long serialVersionUID = 1L;

	
	private int id;

	private int campo;

	
	private BigDecimal caricoAggSalita;
	
	private BigDecimal caricoAggDiscesa;

	private BigDecimal divisione;

	private BigDecimal erroreSalita;
	
	private BigDecimal erroreDiscesa;

	
	private BigDecimal erroreCorSalita;
	
	private BigDecimal erroreCorDiscesa;

	private String esito;

	
	private int idMisura;

	private BigDecimal indicazioneSalita;
	
	private BigDecimal indicazioneDiscesa;

	private BigDecimal massa;

	private BigDecimal mpe;

	private int riferimento;

	private String posizioneSalita;
	
	private String posizioneDiscesa;
	
	
	
	
	private int tipoAzzeramento;

	public VerLinearitaDTO() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCampo() {
		return this.campo;
	}

	public void setCampo(int campo) {
		this.campo = campo;
	}

	public BigDecimal getDivisione() {
		return this.divisione;
	}

	public void setDivisione(BigDecimal divisione) {
		this.divisione = divisione;
	}

	public String getEsito() {
		return this.esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}

	public int getIdMisura() {
		return this.idMisura;
	}

	public void setIdMisura(int idMisura) {
		this.idMisura = idMisura;
	}

	public BigDecimal getMassa() {
		return this.massa;
	}

	public void setMassa(BigDecimal massa) {
		this.massa = massa;
	}

	public BigDecimal getMpe() {
		return this.mpe;
	}

	public void setMpe(BigDecimal mpe) {
		this.mpe = mpe;
	}

	public int getRiferimento() {
		return this.riferimento;
	}

	public void setRiferimento(int riferimento) {
		this.riferimento = riferimento;
	}

	public int getTipoAzzeramento() {
		return this.tipoAzzeramento;
	}

	public void setTipoAzzeramento(int tipoAzzeramento) {
		this.tipoAzzeramento = tipoAzzeramento;
	}

	public BigDecimal getCaricoAggSalita() {
		return caricoAggSalita;
	}

	public void setCaricoAggSalita(BigDecimal caricoAggSalita) {
		this.caricoAggSalita = caricoAggSalita;
	}

	public BigDecimal getCaricoAggDiscesa() {
		return caricoAggDiscesa;
	}

	public void setCaricoAggDiscesa(BigDecimal caricoAggDiscesa) {
		this.caricoAggDiscesa = caricoAggDiscesa;
	}

	public BigDecimal getErroreSalita() {
		return erroreSalita;
	}

	public void setErroreSalita(BigDecimal erroreSalita) {
		this.erroreSalita = erroreSalita;
	}

	public BigDecimal getErroreDiscesa() {
		return erroreDiscesa;
	}

	public void setErroreDiscesa(BigDecimal erroreDiscesa) {
		this.erroreDiscesa = erroreDiscesa;
	}

	public BigDecimal getErroreCorSalita() {
		return erroreCorSalita;
	}

	public void setErroreCorSalita(BigDecimal erroreCorSalita) {
		this.erroreCorSalita = erroreCorSalita;
	}

	public BigDecimal getErroreCorDiscesa() {
		return erroreCorDiscesa;
	}

	public void setErroreCorDiscesa(BigDecimal erroreCorDiscesa) {
		this.erroreCorDiscesa = erroreCorDiscesa;
	}

	public BigDecimal getIndicazioneSalita() {
		return indicazioneSalita;
	}

	public void setIndicazioneSalita(BigDecimal indicazioneSalita) {
		this.indicazioneSalita = indicazioneSalita;
	}

	public BigDecimal getIndicazioneDiscesa() {
		return indicazioneDiscesa;
	}

	public void setIndicazioneDiscesa(BigDecimal indicazioneDiscesa) {
		this.indicazioneDiscesa = indicazioneDiscesa;
	}

	public String getPosizioneSalita() {
		return posizioneSalita;
	}

	public void setPosizioneSalita(String posizioneSalita) {
		this.posizioneSalita = posizioneSalita;
	}

	public String getPosizioneDiscesa() {
		return posizioneDiscesa;
	}

	public void setPosizioneDiscesa(String posizioneDiscesa) {
		this.posizioneDiscesa = posizioneDiscesa;
	}
	
	

}

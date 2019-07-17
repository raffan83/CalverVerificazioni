package it.calverDesktopVER.dto;

import java.math.BigDecimal;
import java.util.Date;


public class VerStrumentoDTO {
	
	private int id;
	private String denominazione;
	private String costruttore;
	private String modello;
	private String matricola;
	private int classe;
	private int id_tipo_strumento;
	private String um;
	private String data_ultima_verifica;
	private String data_prossima_verifica;
	private BigDecimal portata_min_C1;
	private BigDecimal portata_max_C1;
	private BigDecimal div_ver_C1;
	private BigDecimal div_rel_C1;
	private BigDecimal numero_div_C1;
	private BigDecimal portata_min_C2;
	private BigDecimal portata_max_C2;
	private BigDecimal div_ver_C2;
	private BigDecimal div_rel_C2;
	private BigDecimal numero_div_C2;
	private BigDecimal portata_min_C3;
	private BigDecimal portata_max_C3;
	private BigDecimal div_ver_C3;
	private BigDecimal div_rel_C3;
	private BigDecimal numero_div_C3;
	private int id_cliente;
	private int id_sede;
	private int anno_marcatura_ce;
	private String data_messa_in_servizio;
	private int tipologia;
	private int freq_mesi;

	public int getId() {
		return id;
	}
	public int getAnno_marcatura_ce() {
		return anno_marcatura_ce;
	}
	public void setAnno_marcatura_ce(int anno_marcatura_ce) {
		this.anno_marcatura_ce = anno_marcatura_ce;
	}

	public int getTipologia() {
		return tipologia;
	}
	public void setTipologia(int tipologia) {
		this.tipologia = tipologia;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getCostruttore() {
		return costruttore;
	}
	public void setCostruttore(String costruttore) {
		this.costruttore = costruttore;
	}
	public String getModello() {
		return modello;
	}
	public void setModello(String modello) {
		this.modello = modello;
	}
	public String getMatricola() {
		return matricola;
	}
	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}
	public int getClasse() {
		return classe;
	}
	public void setClasse(int classe) {
		this.classe = classe;
	}

	public int getId_tipo_strumento() {
		return id_tipo_strumento;
	}
	public void setId_tipo_strumento(int id_tipo_strumento) {
		this.id_tipo_strumento = id_tipo_strumento;
	}
	public String getUm() {
		return um;
	}
	public void setUm(String um) {
		this.um = um;
	}
	public BigDecimal getPortata_min_C1() {
		return portata_min_C1;
	}
	public void setPortata_min_C1(BigDecimal portata_min_C1) {
		this.portata_min_C1 = portata_min_C1;
	}
	public BigDecimal getPortata_max_C1() {
		return portata_max_C1;
	}
	public void setPortata_max_C1(BigDecimal portata_max_C1) {
		this.portata_max_C1 = portata_max_C1;
	}
	public BigDecimal getDiv_ver_C1() {
		return div_ver_C1;
	}
	public void setDiv_ver_C1(BigDecimal div_ver_C1) {
		this.div_ver_C1 = div_ver_C1;
	}
	public BigDecimal getDiv_rel_C1() {
		return div_rel_C1;
	}
	public void setDiv_rel_C1(BigDecimal div_rel_C1) {
		this.div_rel_C1 = div_rel_C1;
	}
	public BigDecimal getNumero_div_C1() {
		return numero_div_C1;
	}
	public void setNumero_div_C1(BigDecimal numero_div_C1) {
		this.numero_div_C1 = numero_div_C1;
	}
	public BigDecimal getPortata_min_C2() {
		return portata_min_C2;
	}
	public void setPortata_min_C2(BigDecimal portata_min_C2) {
		this.portata_min_C2 = portata_min_C2;
	}
	public BigDecimal getPortata_max_C2() {
		return portata_max_C2;
	}
	public void setPortata_max_C2(BigDecimal portata_max_C2) {
		this.portata_max_C2 = portata_max_C2;
	}
	public BigDecimal getDiv_ver_C2() {
		return div_ver_C2;
	}
	public void setDiv_ver_C2(BigDecimal div_ver_C2) {
		this.div_ver_C2 = div_ver_C2;
	}
	public BigDecimal getDiv_rel_C2() {
		return div_rel_C2;
	}
	public void setDiv_rel_C2(BigDecimal div_rel_C2) {
		this.div_rel_C2 = div_rel_C2;
	}
	public BigDecimal getNumero_div_C2() {
		return numero_div_C2;
	}
	public void setNumero_div_C2(BigDecimal numero_div_C2) {
		this.numero_div_C2 = numero_div_C2;
	}
	public BigDecimal getPortata_min_C3() {
		return portata_min_C3;
	}
	public void setPortata_min_C3(BigDecimal portata_min_C3) {
		this.portata_min_C3 = portata_min_C3;
	}
	public BigDecimal getPortata_max_C3() {
		return portata_max_C3;
	}
	public void setPortata_max_C3(BigDecimal portata_max_C3) {
		this.portata_max_C3 = portata_max_C3;
	}
	public BigDecimal getDiv_ver_C3() {
		return div_ver_C3;
	}
	public void setDiv_ver_C3(BigDecimal div_ver_C3) {
		this.div_ver_C3 = div_ver_C3;
	}
	public BigDecimal getDiv_rel_C3() {
		return div_rel_C3;
	}
	public void setDiv_rel_C3(BigDecimal div_rel_C3) {
		this.div_rel_C3 = div_rel_C3;
	}
	public BigDecimal getNumero_div_C3() {
		return numero_div_C3;
	}
	public void setNumero_div_C3(BigDecimal numero_div_C3) {
		this.numero_div_C3 = numero_div_C3;
	}
	public int getId_cliente() {
		return id_cliente;
	}
	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}
	public int getId_sede() {
		return id_sede;
	}
	public void setId_sede(int id_sede) {
		this.id_sede = id_sede;
	}

	public BigDecimal getDivisioneReale(int campo, int tipoStrumento, double carico) {

	if(tipoStrumento==1) 
	{
		return div_rel_C1;
	}	
	
	
	if(tipoStrumento==2) 
	{
		if(carico>=0 && carico <portata_max_C1.doubleValue()) 
		{
			return div_rel_C1;
		}
		if(carico>=portata_min_C2.doubleValue() && carico <portata_max_C2.doubleValue()) 
		{
			return div_rel_C2;
		}
		
		if(carico>=portata_min_C3.doubleValue() && carico <portata_max_C3.doubleValue()) 
		{
			return div_rel_C3;
		}
		
	}
	
	if(tipoStrumento==3) 
	{	
	if(campo==1) 
		{
			return div_rel_C1;
		}
		if(campo==2) 
		{
			return div_rel_C2;
		}
		if(campo==3) 
		{
			return div_rel_C3;
		}
	}	
		return null;
}
	
	
	public BigDecimal getPortataMaxCampo(int campo, int idTipoStrumento) {

		if(idTipoStrumento==1) 
		{
			return getPortata_max_C1();
		}
		
		if(idTipoStrumento==2) 
		{
			if(portata_max_C3.doubleValue()!=0) 
			{
				return portata_max_C3;
			}
			if(portata_max_C2.doubleValue()!=0) 
			{
				return portata_max_C2;
			}
			if(portata_max_C1.doubleValue()!=0) 
			{
				return portata_max_C1;
			}
		}
	
	if(idTipoStrumento==3) 
	{	
		if(campo==1) 
		{
			return getPortata_max_C1();
		}
		if(campo==2) 
		{
			return getPortata_max_C2();
		}
		if(campo==3) 
		{
			return getPortata_max_C3();
		}
	}	
		return null;
}
	
	public BigDecimal getPortataMinCampo(int campo, int idTipoStrumento) {

		if(idTipoStrumento==1) 
		{
			return getPortata_min_C1();
		}
		
		if(idTipoStrumento==2) 
		{
			if(portata_min_C1.doubleValue()!=0) 
			{
				return portata_min_C1;
			}
			if(portata_min_C2.doubleValue()!=0) 
			{
				return portata_min_C2;
			}
			if(portata_min_C3.doubleValue()!=0) 
			{
				return portata_min_C3;
			}
		}
	
	if(idTipoStrumento==3) 
	{	
		if(campo==1) 
		{
			return getPortata_min_C1();
		}
		if(campo==2) 
		{
			return getPortata_min_C2();
		}
		if(campo==3) 
		{
			return getPortata_min_C3();
		}
	}	
		return null;
}
	public int getFreq_mesi() {
		return freq_mesi;
	}
	public void setFreq_mesi(int freq_mesi) {
		this.freq_mesi = freq_mesi;
	}
	public String getData_ultima_verifica() {
		return data_ultima_verifica;
	}
	public void setData_ultima_verifica(String data_ultima_verifica) {
		this.data_ultima_verifica = data_ultima_verifica;
	}
	public String getData_prossima_verifica() {
		return data_prossima_verifica;
	}
	public void setData_prossima_verifica(String data_prossima_verifica) {
		this.data_prossima_verifica = data_prossima_verifica;
	}
	public String getData_messa_in_servizio() {
		return data_messa_in_servizio;
	}
	public void setData_messa_in_servizio(String data_messa_in_servizio) {
		this.data_messa_in_servizio = data_messa_in_servizio;
	}
		
}

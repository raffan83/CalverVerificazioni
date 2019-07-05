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
	private Date data_ultima_verifica;
	private Date data_prossima_verifica;
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

	public int getId() {
		return id;
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
	public Date getData_ultima_verifica() {
		return data_ultima_verifica;
	}
	public void setData_ultima_verifica(Date data_ultima_verifica) {
		this.data_ultima_verifica = data_ultima_verifica;
	}
	public Date getData_prossima_verifica() {
		return data_prossima_verifica;
	}
	public void setData_prossima_verifica(Date data_prossima_verifica) {
		this.data_prossima_verifica = data_prossima_verifica;
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
		
}

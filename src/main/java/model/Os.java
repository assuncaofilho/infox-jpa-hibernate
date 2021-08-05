package model;

import java.io.Serializable;

public class Os implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
    private String data;
    private String tipo;
    private String situacao;
    private String equipamento;
    private String defeito;
    private String servico;
    private Double valor;
    private Integer idcli;
    private Integer idtec;
    
    
	public boolean isNovo() {

		if (this.id == null) {
			return true; /* Inserir novo */
		} else if (this.id != null && this.id > 0) {
			return false; /* Atualizar */
		}

		return id == null;
	}
    
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public String getEquipamento() {
		return equipamento;
	}
	public void setEquipamento(String equipamento) {
		this.equipamento = equipamento;
	}
	public String getDefeito() {
		return defeito;
	}
	public void setDefeito(String defeito) {
		this.defeito = defeito;
	}
	public String getServico() {
		return servico;
	}
	public void setServico(String servico) {
		this.servico = servico;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Integer getIdcli() {
		return idcli;
	}
	public void setIdcli(Integer idcli) {
		this.idcli = idcli;
	}

	public Integer getIdtec() {
		return idtec;
	}
	
	public void setIdtec(Integer idtec) {
		this.idtec = idtec;
	}
	
	
    
    
}

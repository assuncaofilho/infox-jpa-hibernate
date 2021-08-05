package model;

import java.io.Serializable;

public class Cliente implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private String end;
	private String fone;
	private String email;
	
	
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
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getFone() {
		return fone;
	}
	public void setFone(String fone) {
		this.fone = fone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	

}

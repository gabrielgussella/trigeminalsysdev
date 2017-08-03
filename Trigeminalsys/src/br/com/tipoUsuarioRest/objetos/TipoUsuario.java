package br.com.tipoUsuarioRest.objetos;

import java.io.Serializable;

public class TipoUsuario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int cod_tipo_usuario;
	private String tu_descricao;
	
	public int getCod_tipo_usuario() {
		return cod_tipo_usuario;
	}
	
	public void setCod_tipo_usuario(int cod_tipo_usuario) {
		this.cod_tipo_usuario = cod_tipo_usuario;
	}
	
	public String getTu_descricao() {
		return tu_descricao;
	}
	
	public void setTu_descricao(String tu_descricao) {
		this.tu_descricao = tu_descricao;
	}
	
}

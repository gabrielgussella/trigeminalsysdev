package br.com.tipoUsuarioRest.jdbcinterface;

import java.util.List;

import br.com.tipoUsuarioRest.objetos.TipoUsuario;

public interface TipoUsuarioDAO {
	
	public boolean inserir(TipoUsuario tipousuario);
	
	public List<TipoUsuario> buscarPorDescricao(String tu_descricao);
	
	public boolean deletarTipoUsuario(int cod_tipo_usuario);
	
	public TipoUsuario buscarPorCodigo(int cod_tipo_usuario);
	
	public boolean atualizar(TipoUsuario tipousuario);
}

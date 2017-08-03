package br.com.tipoUsuarioRest.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;




import br.com.tipoUsuarioRest.jdbcinterface.TipoUsuarioDAO;
import br.com.tipoUsuarioRest.objetos.TipoUsuario;

public class JDBCTipoUsuarioDAO implements TipoUsuarioDAO {
	
	private Connection conexao;
	
	public JDBCTipoUsuarioDAO(Connection conexao) {
		
		this.conexao = conexao;

	}
	
	public boolean inserir(TipoUsuario tipousuario) {
		String comando = "INSERT INTO tb_tipo_usuario " +
				"(tu_descricao)" + 
				"VALUES (?)";
		
		PreparedStatement p;
		
		try {
			p = this.conexao.prepareStatement(comando);
			p.setString(1,  tipousuario.getTu_descricao());
			p.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public List<TipoUsuario> buscarPorDescricao(String tu_descricao){
		String comando = "SELECT * FROM tb_tipo_usuario ";
		if(!tu_descricao.equals("null") && !tu_descricao.equals("")){
			comando += "WHERE tu_descricao LIKE'" + tu_descricao + "%'";
		}

		List<TipoUsuario> listTipoUsuario = new ArrayList<TipoUsuario>();
		TipoUsuario tipousuario = null;
		try{
			java.sql.Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			while(rs.next()){
				tipousuario = new TipoUsuario();
				String descricaoTipoUsuario =  rs.getString("tu_descricao");
				int cod = rs.getInt("cod_tipo_usuario");


				tipousuario.setCod_tipo_usuario(cod);
				tipousuario.setTu_descricao(descricaoTipoUsuario);

				listTipoUsuario.add(tipousuario);
			}
		}catch (Exception e){
			e.printStackTrace();

		}

		return listTipoUsuario;
	}
	
	public boolean deletarTipoUsuario(int cod_tipo_usuario) {
		
		String comando = "DELETE FROM tb_tipo_usuario WHERE cod_tipo_usuario = " + cod_tipo_usuario;
		Statement p; 
		
		try {
			p = this.conexao.createStatement();
			p.execute(comando);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public TipoUsuario buscarPorCodigo(int cod_tipo_usuario) {
		System.out.println("buscarPorCodigo JDBCTipoUsuarioDAO" +cod_tipo_usuario); 
		String comando = "SELECT * FROM tb_tipo_usuario WHERE cod_tipo_usuario = " + cod_tipo_usuario;
		TipoUsuario tipousuario = new TipoUsuario();
		
		try {
			java.sql.Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			
			while (rs.next()) {
				String tu_descricao = rs.getString("tu_descricao");
				int cod = rs.getInt("cod_tipo_usuario");
				
				tipousuario.setTu_descricao(tu_descricao);
				tipousuario.setCod_tipo_usuario(cod);
			}
		 } catch (Exception e) {
			 e.printStackTrace();
			 
		 }
		
		return tipousuario; 
	}
	
	public boolean atualizar(TipoUsuario tipousuario) {
		System.out.println("Metodo: atualizar - Campo COD" +tipousuario.getCod_tipo_usuario());
		System.out.println("Metodo: atualizar - Campo DESCRICAO" + tipousuario.getTu_descricao());
		
		String comando = "UPDATE tb_tipo_usuario SET tu_descricao=? WHERE cod_tipo_usuario = " + tipousuario.getCod_tipo_usuario();
		PreparedStatement p;

		try{
			p = this.conexao.prepareStatement(comando);
			p.setString(1, tipousuario.getTu_descricao());
			p.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}

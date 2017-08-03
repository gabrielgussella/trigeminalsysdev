package br.com.tipoUsuarioRest.rest;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import br.com.tipoUsuarioRest.bd.conexao.Conexao;
import br.com.tipoUsuarioRest.jdbc.JDBCTipoUsuarioDAO;
import br.com.tipoUsuarioRest.objetos.TipoUsuario;

@Path("tipoUsuarioRest")
public class TipoUsuarioRest extends UtilRest {
	
	public TipoUsuarioRest() {		
	}
	
	@POST
	@Path("/addTipoUsuario")
	@Consumes("application/*")
	public Response addTipoUsuario(String tipousuarioParam) {
		
		try{
			TipoUsuario tipousuario = new ObjectMapper().readValue(tipousuarioParam, TipoUsuario.class);
					
					Conexao conec = new Conexao();
					Connection conexao = conec.abrirConexao();
					JDBCTipoUsuarioDAO jdbcTipoUsuario = new JDBCTipoUsuarioDAO(conexao);
					jdbcTipoUsuario.inserir(tipousuario);
					conec.fecharConexao();
					return this.buildResponse("Tipo de Usuario cadastrado com sucesso");
		}catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@POST
	@Path("/buscarTipoUsuariosPorDescricao/{tu_descricao}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response buscarContatosPorNome (@PathParam("tu_descricao") String tu_descricao){
		try{
			List<TipoUsuario> tipousuario = new ArrayList<TipoUsuario>();
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			
			JDBCTipoUsuarioDAO jdbcTipoUsuario = new JDBCTipoUsuarioDAO(conexao);
			tipousuario = jdbcTipoUsuario.buscarPorDescricao(tu_descricao);
			conec.fecharConexao();
					
			return this.buildResponse(tipousuario);
		}catch (Exception e){
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@POST
	@Path("/deletarTipoUsuario/{cod_tipo_usuario}")
	@Consumes("application/*")
	public Response deletarTipoUsuario(@PathParam("cod_tipo_usuario") int cod_tipo_usuario) {
		try {
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCTipoUsuarioDAO jdbcTipoUsuario = new JDBCTipoUsuarioDAO(conexao);
			jdbcTipoUsuario.deletarTipoUsuario(cod_tipo_usuario);
			conec.fecharConexao();
			
			return this.buildResponse("Contato deletado com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@POST
	@Path("/buscarTipoUsuarioPeloCodigo/{cod_tipo_usuario}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response buscarTipoUsuarioPeloCodigo(@PathParam("cod_tipo_usuario") int cod_tipo_usuario) {
		try {
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			
			JDBCTipoUsuarioDAO jdbcTipoUsuario = new JDBCTipoUsuarioDAO(conexao);
			System.out.println("BUSCAR PELO CODIGO" +cod_tipo_usuario); 
			TipoUsuario tipousuario = jdbcTipoUsuario.buscarPorCodigo(cod_tipo_usuario);
			
			return this.buildResponse(tipousuario);
		} catch (Exception e) {
			e.printStackTrace();
			
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@POST
	@Path("/editarTipoUsuario")
	@Consumes("application/*")
	public Response editarTipoUsuario(String contatoParam) {
		try {
			TipoUsuario tipousuario = new ObjectMapper().readValue(contatoParam, TipoUsuario.class);
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCTipoUsuarioDAO jdbcTipoUsuario = new JDBCTipoUsuarioDAO(conexao);
			jdbcTipoUsuario.atualizar(tipousuario);
			conec.fecharConexao();
			
			return this.buildResponse("Tipo de Usuario editado com sucesso");
			
		} catch (Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
}

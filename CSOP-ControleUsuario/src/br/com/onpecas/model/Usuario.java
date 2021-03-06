package br.com.onpecas.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.onpecas.helper.Alerta;
import br.com.onpecas.helper.MySqlConnect;

public class Usuario {

	private int oid_usuario;
	private Grupo grupo;
	private String nomeCompleto, login, senha, email, nomeGrupo;

	public int getOid_usuario() {
		return oid_usuario;
	}
	public void setOid_usuario(int oid_usuario) {
		this.oid_usuario = oid_usuario;
	}
	public Grupo getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
		setNomeGrupo(grupo.getNome());
	}
	public String getNomeCompleto() {
		return nomeCompleto;
	}
	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
	public String getNomeGrupo() {
		return nomeGrupo;
	}
	public void setNomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	//Metodo usado para inserir um novo grupo no banco de dados
	public static void Insert(Usuario usuario){
		Connection con = MySqlConnect.ConectarDb();

		String sql ="insert into usuariosistema (nomecompleto, email, login, senha, oid_grupo) values(?, ?, ?, ?, ?);";

		PreparedStatement parametros;

		try {
			parametros = con.prepareStatement(sql);
			parametros.setString(1, usuario.getNomeCompleto());
			parametros.setString(2, usuario.getEmail());
			parametros.setString(3, usuario.getLogin());
			parametros.setString(4, usuario.getSenha());
			parametros.setInt(5, usuario.getGrupo().getOid_grupo());

			parametros.executeUpdate();
			con.close();

			Alerta.showInformation("sucesso", "Inserido com sucesso");
			//limpar();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alerta.showError("Erro", "Ocorreu um erro, tente novamente.");
		}
	}

	//Metodo que retorna todos os grupos do banco de dados
	public static List<Usuario> Select(){
		Connection con = MySqlConnect.ConectarDb();

		List<Usuario> lstUsuario = new ArrayList<>();
		String sql = "select u.*, g.* from usuariosistema as u "
				+ "inner join grupousuario as g on (u.oid_grupo = g.oid_grupo);";

		try {
			ResultSet rs = con.createStatement().executeQuery(sql);

			while(rs.next()){

				Usuario usuario = new Usuario();
				Grupo grupo = new Grupo();

				grupo.setOid_grupo(rs.getInt("oid_grupo"));
				grupo.setNome(rs.getString("nome"));
				grupo.setDescricao(rs.getString("descricao"));

				usuario.setGrupo(grupo);

				usuario.setOid_usuario(rs.getInt("oid_usuario"));
				usuario.setNomeCompleto(rs.getString("nomecompleto"));
				usuario.setEmail(rs.getString("email"));
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));

				lstUsuario.add(usuario);
			}

			con.close();
			return lstUsuario;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	//Metodo usado para excluir um grupo no banco de dados
	public static void Delete(Usuario usuario){
		Connection con = MySqlConnect.ConectarDb();

		String sql ="delete from usuariosistema where oid_usuario = ?;";

		PreparedStatement parametros;

		try {
			parametros = con.prepareStatement(sql);
			parametros.setInt(1, usuario.getOid_usuario());

			parametros.executeUpdate();
			con.close();

			Alerta.showInformation("sucesso", "Deletado com sucesso");
			//limpar();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alerta.showError("Erro", "Ocorreu um erro, tente novamente.");
		}
	}

	//Metodo usado para atualizar um grupo no banco de dados
	public static void Update(Usuario usuario){
		Connection con = MySqlConnect.ConectarDb();

		String sql ="update usuariosistema set nomecompleto = ?, email = ?, login = ?, senha = ?, oid_grupo = ? where oid_usuario = ?;";
		PreparedStatement parametros;

		try {
			parametros = con.prepareStatement(sql);
			parametros.setString(1, usuario.getNomeCompleto());
			parametros.setString(2, usuario.getEmail());
			parametros.setString(3, usuario.getLogin());
			parametros.setString(4, usuario.getSenha());
			parametros.setInt(5, usuario.getGrupo().getOid_grupo());
			parametros.setInt(6, usuario.getOid_usuario());

			parametros.executeUpdate();
			con.close();

			Alerta.showInformation("sucesso", "Atualizado com sucesso");
			//limpar();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alerta.showError("Erro", "Ocorreu um erro, tente novamente.");
		}
	}

	//M�todo utilizado para filtrar os usuarios que aparecer�o na table
	public static List<Usuario> FiltrarGrupo(boolean temNome, boolean temGrupo,
			boolean taNome, boolean taLogin, int grupo, String nomerecebido){

		Connection con = MySqlConnect.ConectarDb();
		List<Usuario> lstUsuario = new ArrayList<>();
		String sql ="";

		if(temNome){
			if(temGrupo){
				if(taNome && taLogin){
					// Tem Nome, grupo, ta no login, ta no nome completo
					
					sql = "select u.*, g.* from usuariosistema as u "
							+ "inner join grupousuario as g on (u.oid_grupo = g.oid_grupo) "
							+ "where u.oid_grupo = "+grupo+" and (u.nomecompleto like '%"+nomerecebido+"%' or u.login like '%"+nomerecebido+"%');";
				}else if(taNome){
					// Tem Nome, grupo, ta no nome completo
					
					sql = "select u.*, g.* from usuariosistema as u "
							+ "inner join grupousuario as g on (u.oid_grupo = g.oid_grupo) "
							+ "where u.oid_grupo = "+grupo+" and u.nomecompleto like '%"+nomerecebido+"%';";
				}else if(taLogin){
					
					// Tem Nome, grupo, ta no login
					sql = "select u.*, g.* from usuariosistema as u "
							+ "inner join grupousuario as g on (u.oid_grupo = g.oid_grupo) "
							+ "where u.oid_grupo = "+grupo+" and u.login like '%"+nomerecebido+"%';";
				}else{
					// Tem Nome, grupo
					
					
					sql = "select u.*, g.* from usuariosistema as u "
							+ "inner join grupousuario as g on (u.oid_grupo = g.oid_grupo) "
							+ "where u.oid_grupo = "+grupo+" and (u.nomecompleto like '%"+nomerecebido+"%' or u.login like '%"+nomerecebido+"%');";
					System.out.println(sql);
				}
			}else{
				// Tem Nome
				sql = "select u.*, g.* from usuariosistema as u "
						+ "inner join grupousuario as g on (u.oid_grupo = g.oid_grupo) "
						+ "where  u.nomecompleto like '%"+nomerecebido+"%' or u.login like '%"+nomerecebido+"%';";
			}
		}else if(temGrupo){
			// Tem grupo
			sql = "select u.*, g.* from usuariosistema as u "
					+ "inner join grupousuario as g on (u.oid_grupo = g.oid_grupo) "
					+ "where u.oid_grupo = "+grupo+" ;";
		}

		try {

			ResultSet rs = con.createStatement().executeQuery(sql);

			while(rs.next()){

				Usuario usuario = new Usuario();
				Grupo grupoN = new Grupo();

				grupoN.setOid_grupo(rs.getInt("oid_grupo"));
				grupoN.setNome(rs.getString("nome"));
				grupoN.setDescricao(rs.getString("descricao"));

				usuario.setGrupo(grupoN);

				usuario.setOid_usuario(rs.getInt("oid_usuario"));
				usuario.setNomeCompleto(rs.getString("nomecompleto"));
				usuario.setEmail(rs.getString("email"));
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));

				lstUsuario.add(usuario);
			}

			con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return lstUsuario;
	}
}

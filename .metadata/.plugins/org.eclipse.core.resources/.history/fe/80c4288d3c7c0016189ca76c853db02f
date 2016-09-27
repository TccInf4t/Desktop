package br.com.onpecas.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.onpecas.helper.Alerta;
import br.com.onpecas.helper.MySqlConnect;

public class Grupo {

	private int oid_grupo;
	private String nome;
	private String descricao;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return nome;
	}
	public int getOid_grupo() {
		return oid_grupo;
	}
	public void setOid_grupo(int oid_grupo) {
		this.oid_grupo = oid_grupo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public static void Insert(Grupo grupo){
		Connection con = MySqlConnect.ConectarDb();

		String sql ="insert into grupousuario (nome, descricao) values(?, ?);";

		PreparedStatement parametros;

		try {
			parametros = con.prepareStatement(sql);
			parametros.setString(1, grupo.getNome());
			parametros.setString(2, grupo.getDescricao());

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
	public static List<Grupo> Select(){
		Connection con = MySqlConnect.ConectarDb();

		List<Grupo> lstGrupo = new ArrayList<>();
		String sql = "select * from grupousuario;";

		try {
			ResultSet rs = con.createStatement().executeQuery(sql);

			while(rs.next()){

				Grupo grupo = new Grupo();
				grupo.setNome(rs.getString("nome"));
				grupo.setDescricao(rs.getString("descricao"));
				grupo.setOid_grupo(rs.getInt("oid_grupo"));

				lstGrupo.add(grupo);
			}

			con.close();
			return lstGrupo;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static void Delete(Grupo grupo){
		Connection con = MySqlConnect.ConectarDb();
		
		String sql2 ="delete from permissao_grupo where oid_grupo = ?;";

		String sql ="delete from grupousuario where oid_grupo = ?;";
	

		PreparedStatement parametros;

		try {
			parametros = con.prepareStatement(sql2);
			parametros.setInt(1, grupo.getOid_grupo());

			parametros.executeUpdate();
			
			parametros = con.prepareStatement(sql);
			parametros.setInt(1, grupo.getOid_grupo());

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

	public static void Update(Grupo grupo){
		Connection con = MySqlConnect.ConectarDb();

		String sql ="update grupousuario set nome = ?, descricao = ? where oid_grupo = ?;";

		PreparedStatement parametros;

		try {
			parametros = con.prepareStatement(sql);
			parametros.setString(1, grupo.getNome());
			parametros.setString(2, grupo.getDescricao());
			parametros.setInt(3, grupo.getOid_grupo());

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

	public static void FiltrarGrupo(int grupo, int nomuser, int nome, int login){

		if(nomuser ==1){
			if(grupo==1){
				if(nome == 1 && login == 1){
					/* TEM NOME DE USUARIO, GRUPO SELECIONADO, NOME E LOGIN */
				}else if(nome == 1){
					/* TEM NOME DE USUARIO, GRUPO SELECIONADO E NOME */
				}else if( login == 1){
					/* TEM NOME DE USUARIO, GRUPO SELECIONADO E LOGIN */
				}else{
					/* TEM NOME DE USUARIO E GRUPO SELECIONADO */
				}
			}else{
				/* TEM NOME DE USUARIO SELECIONADO */
			}
		} else if(grupo==1){
			if(nome == 1 && login == 1){
				/* TEM GRUPO SELECIONADO, NOME E LOGIN */
			}else if(nome == 1){
				/* TEM GRUPO SELECIONADO, E NOME */
			}else if(login == 1){
				/* TEM GRUPO SELECIONADO E LOGIN */
			}else{
				/* TEM GRUPO SELECIONADO */
			}
		}
	}

}

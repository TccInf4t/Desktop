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
	private int oid_permissao;

	public int getOid_permissao() {
		return oid_permissao;
	}
	public void setOid_permissao(int oid_permissao) {
		this.oid_permissao = oid_permissao;
	}
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

	//Metodo usado para inserir um novo grupo no banco de dados
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
		} catch (SQLException e) {
			e.printStackTrace();
			Alerta.showError("Erro", "Ocorreu um erro, tente novamente.");
		}
	}

	//Metodo que retorna todos os grupos do banco de dados
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
				grupo.setOid_permissao(rs.getInt("oid_permissao"));

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

	//Metodo usado para excluir um grupo no banco de dados
	public static void Delete(Grupo grupo){
		Connection con = MySqlConnect.ConectarDb();

		String sql ="delete from grupousuario where oid_grupo = ?;";


		PreparedStatement parametros;

		try {

			parametros = con.prepareStatement(sql);
			parametros.setInt(1, grupo.getOid_grupo());

			parametros.executeUpdate();
			Permissao.Delete(grupo.getOid_permissao());
			con.close();

			Alerta.showInformation("sucesso", "Deletado com sucesso");
		} catch (SQLException e) {
			if(e.getErrorCode() == 1451){
				Alerta.showError("N�o � poss�vel excluir", "Este grupo possui usu�rios associados");
			}else{
				Alerta.showError("Erro", "Ocorreu um erro, tente novamente.");
			}
		}
	}

	//Metodo usado para atualizar um grupo no banco de dados
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
		} catch (SQLException e) {
			e.printStackTrace();
			Alerta.showError("Erro", "Ocorreu um erro, tente novamente.");
		}
	}
}

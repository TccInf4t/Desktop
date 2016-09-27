package br.com.onpecas.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import br.com.onpecas.helper.Alerta;
import br.com.onpecas.helper.MySqlConnect;

public class Permissao {

	private int Oid_permissao;
	private boolean acs_cms;

	public int getOid_permissao() {
		return Oid_permissao;
	}
	public void setOid_permissao(int oid_permissao) {
		Oid_permissao = oid_permissao;
	}
	public boolean getAcs_cms() {
		return acs_cms;
	}
	public void setAcs_cms(boolean acs_cms) {
		this.acs_cms = acs_cms;
	}

	public static void Insert(Permissao permissao,int oid_grupo){
		Connection con = MySqlConnect.ConectarDb();

		String sql ="insert into permissao (acs_cms) values(?);";

		String sql2="update grupousuario set oid_permissao = (select oid_permissao from permissao order by oid_permissao desc limit 1) where oid_grupo = ?;";

		PreparedStatement parametros;

		try {
			parametros = con.prepareStatement(sql);
			parametros.setBoolean(1, permissao.getAcs_cms());

			parametros.executeUpdate();

			parametros = con.prepareStatement(sql2);
			//parametros.setInt(1, permissao.getOid_permissao());
			parametros.setInt(1, oid_grupo);

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

	public static void Update(Permissao permissao){
		Connection con = MySqlConnect.ConectarDb();

		String sql ="update permissao set acs_cms = ? where oid_permissao = ?;";

		PreparedStatement parametros;

		try {
			parametros = con.prepareStatement(sql);
			parametros.setBoolean(1, permissao.getAcs_cms());
			parametros.setInt(2, permissao.getOid_permissao());

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

	public static void Delete(int oid_permissao){
		Connection con = MySqlConnect.ConectarDb();

		String sql ="delete from permissao where oid_permissao = ?;";

		PreparedStatement parametros;

		try {
			parametros = con.prepareStatement(sql);
			parametros.setInt(1, oid_permissao);

			parametros.executeUpdate();
			con.close();

			//limpar();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alerta.showError("Erro", "Ocorreu um erro, tente novamente.");
		}
	}

	public static Permissao Select(Grupo grupo){

		Connection con = MySqlConnect.ConectarDb();

		String sql = "select p.* from permissao p inner join grupousuario pg on(p.oid_permissao = pg.oid_permissao) where pg.oid_grupo = "+grupo.getOid_grupo()+";";

		try{
			ResultSet rs = con.createStatement().executeQuery(sql);

			Permissao permissao = new Permissao();

			while(rs.next()){

			permissao.setOid_permissao(rs.getInt("oid_permissao"));
			permissao.setAcs_cms(rs.getBoolean("acs_cms"));

			}
			con.close();
			return permissao;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static boolean Buscar(Grupo grupo){
		Connection con = MySqlConnect.ConectarDb();


		String sql = "select * from permissao p inner join grupousuario g on(p.oid_permissao = g.oid_permissao) where oid_grupo = "+grupo.getOid_grupo()+";";

		try {
			ResultSet rs = con.createStatement().executeQuery(sql);


			while(rs.next()){

				if(rs.getRow()== 1){

					return true;

				}else{

					return false;

				}

				}

			con.close();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}
}


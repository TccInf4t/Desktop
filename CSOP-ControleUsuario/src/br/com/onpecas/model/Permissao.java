package br.com.onpecas.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import br.com.onpecas.helper.Alerta;
import br.com.onpecas.helper.MySqlConnect;

public class Permissao {

	private int Oid_permissao;
	private boolean acs_cms, acs_sge, acs_expedicao, acs_relatorios, acs_ctrluser;


	public boolean getAcs_sge() {
		return acs_sge;
	}
	public void setAcs_sge(boolean acs_sge) {
		this.acs_sge = acs_sge;
	}
	public boolean getAcs_expedicao() {
		return acs_expedicao;
	}
	public void setAcs_expedicao(boolean acs_expedicao) {
		this.acs_expedicao = acs_expedicao;
	}
	public boolean getAcs_relatorios() {
		return acs_relatorios;
	}
	public void setAcs_relatorios(boolean acs_relatorios) {
		this.acs_relatorios = acs_relatorios;
	}
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
	public boolean getAcs_ctrluser() {
		return acs_ctrluser;
	}
	public void setAcs_ctrluser(boolean acs_ctrluser) {
		this.acs_ctrluser = acs_ctrluser;
	}

	public static void Insert(Permissao permissao, int oid_grupo){
		Connection con = MySqlConnect.ConectarDb();

		String sql ="insert into permissao (acs_cms, acs_expedicao, acs_sge, acs_relatorios, acs_ctrluser) values(?, ?, ?, ?,?);";

		String sql2="update grupousuario set oid_permissao = (select oid_permissao from permissao order by oid_permissao desc limit 1) where oid_grupo = ?;";

		PreparedStatement parametros;

		try {
			parametros = con.prepareStatement(sql);
			parametros.setBoolean(1, permissao.getAcs_cms());
			parametros.setBoolean(2, permissao.getAcs_expedicao());
			parametros.setBoolean(3, permissao.getAcs_sge());
			parametros.setBoolean(4, permissao.getAcs_relatorios());
			parametros.setBoolean(6, permissao.getAcs_ctrluser());

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

		String sql ="update permissao set acs_cms = ?, acs_expedicao=?, acs_sge=?, acs_relatorios=?, acs_ctrluser= ? where oid_permissao = ?;";

		PreparedStatement parametros;

		try {
			parametros = con.prepareStatement(sql);
			parametros.setBoolean(1, permissao.getAcs_cms());
			parametros.setBoolean(2, permissao.getAcs_expedicao());
			parametros.setBoolean(3, permissao.getAcs_sge());
			parametros.setBoolean(4, permissao.getAcs_relatorios());
			parametros.setBoolean(5, permissao.getAcs_ctrluser());
			parametros.setInt(6, permissao.getOid_permissao());

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
				permissao.setAcs_expedicao(rs.getBoolean("acs_expedicao"));
				permissao.setAcs_relatorios(rs.getBoolean("acs_relatorios"));
				permissao.setAcs_sge(rs.getBoolean("acs_sge"));
				permissao.setAcs_ctrluser(rs.getBoolean("acs_ctrluser"));

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


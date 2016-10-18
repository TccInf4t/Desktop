package br.com.onpecas.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.onpecas.helper.MySqlConnect;

public class Status {
	private int oid_status;
	private String nome;
	private String descricao;

	public int getOid_status() {
		return oid_status;
	}
	public void setOid_status(int oid_status) {
		this.oid_status = oid_status;
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

	public static Status BuscarStatus(int oid_status){
		Connection con = MySqlConnect.ConectarDb();

		Status status = new Status();
		String sql ="select * from status where oid_status ="+oid_status;

		try {
			ResultSet rs = con.createStatement().executeQuery(sql);

			while(rs.next()){
				status.setNome(rs.getString("nome"));
				status.setOid_status(rs.getInt("oid_status"));
				status.setDescricao(rs.getString("descricao"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
}

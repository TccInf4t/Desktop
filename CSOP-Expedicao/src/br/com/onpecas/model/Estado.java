package br.com.onpecas.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.onpecas.helper.MySqlConnect;

public class Estado {
	private int oid_estado;
	private String nome;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getNome();
	}
	public int getOid_estado() {
		return oid_estado;
	}
	public void setOid_estado(int oid_estado) {
		this.oid_estado = oid_estado;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public static List<Estado> Select(){
		Connection con = MySqlConnect.ConectarDb();

		List<Estado> lstEstado = new ArrayList<>();
		String sql ="select * from estado";

		try {
			ResultSet rs = con.createStatement().executeQuery(sql);

			while(rs.next()){
				Estado estado = new Estado();
				estado.setNome(rs.getString("nome"));
				estado.setOid_estado(rs.getInt("oid_estado"));

				lstEstado.add(estado);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lstEstado;
	}

}

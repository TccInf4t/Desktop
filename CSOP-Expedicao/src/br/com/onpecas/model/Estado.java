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
	private String UF;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getNome()+" - "+getUF();
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

	public String getUF() {
		return UF;
	}
	public void setUF(String uF) {
		UF = uF;
	}

	/*M�todo usado para listar todos os registros da tabela estado*/
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
				estado.setUF(rs.getString("uf"));

				lstEstado.add(estado);
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lstEstado;
	}

	/*M�todo para buscar um estado especifico no banco de dados*/
	public static Estado BuscarEstado(int oid_estado){
		Connection con = MySqlConnect.ConectarDb();

		List<Estado> lstEstado = new ArrayList<>();
		String sql ="select * from estado where oid_estado ="+oid_estado;

		Estado estado = new Estado();

		try {
			ResultSet rs = con.createStatement().executeQuery(sql);

			while(rs.next()){

				estado.setNome(rs.getString("nome"));
				estado.setOid_estado(rs.getInt("oid_estado"));
				estado.setUF(rs.getString("uf"));

				lstEstado.add(estado);
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return estado;
	}

}

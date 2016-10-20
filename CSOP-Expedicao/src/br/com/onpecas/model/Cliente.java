package br.com.onpecas.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.onpecas.helper.MySqlConnect;

public class Cliente {
	private int oid_cliente;
	private String email;
	private String nome;
	private String cpfcnpj;
	private String dtNasc;
	private int ativo;
	private Endereco endereco;

	public int getOid_cliente() {
		return oid_cliente;
	}
	public void setOid_cliente(int oid_cliente) {
		this.oid_cliente = oid_cliente;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpfcnpj() {
		return cpfcnpj;
	}
	public void setCpfcnpj(String cpfcnpj) {
		this.cpfcnpj = cpfcnpj;
	}
	public String getDtNasc() {
		return dtNasc;
	}
	public void setDtNasc(String dtNasc) {
		this.dtNasc = dtNasc;
	}
	public int getAtivo() {
		return ativo;
	}
	public void setAtivo(int ativo) {
		this.ativo = ativo;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public static Cliente Select(int oid_cliente){
		Connection con = MySqlConnect.ConectarDb();

		String sql = "Select * from cliente where oid_cliente="+oid_cliente;
		Cliente cliente = new Cliente();

		try {
			ResultSet rs = con.createStatement().executeQuery(sql);
			while(rs.next()){

				cliente.setOid_cliente(rs.getInt("oid_cliente"));
				cliente.setAtivo(rs.getInt("ativo"));
				cliente.setCpfcnpj(rs.getString("cpfcnpj"));
				cliente.setDtNasc(rs.getString("data_nascimento"));
				cliente.setEmail(rs.getString("email"));
				cliente.setNome(rs.getString("nome"));
				cliente.setEndereco(Endereco.BuscarEndereco(rs.getInt("oid_endereco")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cliente;
	}
}

package br.com.onpecas.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.onpecas.helper.MySqlConnect;

public class Produto {
	private int oid_peca;
	private String nome, ano, descricao, validade, precovendido;
	public int getOid_peca() {
		return oid_peca;
	}
	public void setOid_peca(int oid_peca) {
		this.oid_peca = oid_peca;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getAno() {
		return ano;
	}
	public void setAno(String ano) {
		this.ano = ano;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getValidade() {
		return validade;
	}
	public void setValidade(String validade) {
		this.validade = validade;
	}
	public String getPrecovendido() {
		return precovendido;
	}
	public void setPrecovendido(String precovendido) {
		this.precovendido = precovendido;
	}

	public static List<Produto> Select(int oid_pedido){
		Connection con = MySqlConnect.ConectarDb();

		List<Produto> lstEstado = new ArrayList<>();
		String sql ="select * from estado";

		try {
			ResultSet rs = con.createStatement().executeQuery(sql);

			while(rs.next()){
				Estado estado = new Estado();
				estado.setNome(rs.getString("nome"));
				estado.setOid_estado(rs.getInt("oid_estado"));
				estado.setUF(rs.getString("uf"));

				//lstEstado.add(estado);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lstEstado;
	}
}

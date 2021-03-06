package br.com.onpecas.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.onpecas.helper.MySqlConnect;

public class Produto {
	private int oid_peca, qtdItens;
	private String nome, ano, descricao, validade, precovendido, precoTotalvendido;

	public int getQtdItens() {
		return qtdItens;
	}
	public void setQtdItens(int qtdItens) {
		this.qtdItens = qtdItens;
	}
	public String getPrecoTotalvendido() {
		return precoTotalvendido;
	}
	public void setPrecoTotalvendido(String precoTotalvendido) {
		this.precoTotalvendido = precoTotalvendido;
	}
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

		List<Produto> lstProdudo = new ArrayList<>();
		String sql ="select p.*, pp.* from peca as p inner join pedido_peca as pp on "
				+ "(pp.oid_peca = p.oid_peca) where pp.oid_pedido = "+oid_pedido;

		try {
			ResultSet rs = con.createStatement().executeQuery(sql);

			while(rs.next()){
				Produto produto = new Produto();

				produto.setOid_peca(rs.getInt("oid_peca"));
				produto.setAno(rs.getString("ano"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setNome(rs.getString("nome"));
				produto.setPrecoTotalvendido(rs.getString("valortotal"));
				produto.setPrecovendido(rs.getString("valorvendido"));
				produto.setValidade(rs.getString("validade"));
				produto.setQtdItens(rs.getInt("qtd"));

				lstProdudo.add(produto);
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lstProdudo;
	}
}

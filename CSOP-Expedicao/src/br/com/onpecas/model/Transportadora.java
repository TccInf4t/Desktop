package br.com.onpecas.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.onpecas.helper.Alerta;
import br.com.onpecas.helper.MySqlConnect;

public class Transportadora {
	private int oid_transportadora;
	private String nome;
	private String cnpj;
	private String frete;
	private String ramo;
	private String observacoes;
	private Endereco endereco;


	public int getOid_transportadora() {
		return oid_transportadora;
	}

	public void setOid_transportadora(int oid_transportadora) {
		this.oid_transportadora = oid_transportadora;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getFrete() {
		return frete;
	}

	public void setFrete(String frete) {
		this.frete = frete;
	}

	public String getRamo() {
		return ramo;
	}

	public void setRamo(String ramo) {
		this.ramo = ramo;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public static List<Transportadora> Select(){
		Connection con = MySqlConnect.ConectarDb();

		List<Transportadora> lstTransportadora = new ArrayList<Transportadora>();
		String sql ="select * from transportadora";

		try {
			ResultSet rs = con.createStatement().executeQuery(sql);

			while(rs.next()){
				Transportadora transportadora = new Transportadora();

				transportadora.setOid_transportadora(rs.getInt("oid_transportadora"));
				transportadora.setCnpj(rs.getString("cnpj"));
				transportadora.setFrete(rs.getString("frete"));
				transportadora.setNome(rs.getString("nome"));
				transportadora.setRamo(rs.getString("ramo"));

				lstTransportadora.add(transportadora);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lstTransportadora;
	}

	public void Insert(Transportadora transportadora){
		Connection con = MySqlConnect.ConectarDb();

		String sql ="insert into transportadora (nome, cnpj, frete, ramo, observacoes, oid_endereco) values(?, ?, ?, ?, ?, ?);";

		PreparedStatement parametros;

		try {
			parametros = con.prepareStatement(sql);

			parametros.setString(1, transportadora.getNome());
			parametros.setString(2, transportadora.getCnpj());
			parametros.setString(3, transportadora.getFrete());
			parametros.setString(4, transportadora.getRamo());
			parametros.setString(5, transportadora.getObservacoes());
			parametros.setInt(6, transportadora.getEndereco().getOid_endereco());

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
}

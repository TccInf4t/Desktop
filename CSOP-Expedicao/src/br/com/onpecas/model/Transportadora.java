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
	private String estado;
	private String cidade;
	private String rg;
	private String natureza;
	private String telefone;


	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getNatureza() {
		return natureza;
	}

	public void setNatureza(String natureza) {
		this.natureza = natureza;
	}

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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public static List<Transportadora> Select(){
		Connection con = MySqlConnect.ConectarDb();

		List<Transportadora> lstTransportadora = new ArrayList<Transportadora>();
		String sql ="select * from transportadora order by oid_transportadora desc";

		try {
			ResultSet rs = con.createStatement().executeQuery(sql);

			while(rs.next()){
				Transportadora transportadora = new Transportadora();
				Endereco endereco = Endereco.BuscarEndereco(rs.getInt("oid_endereco"));

				transportadora.setOid_transportadora(rs.getInt("oid_transportadora"));
				transportadora.setCnpj(rs.getString("cnpj"));
				transportadora.setFrete(rs.getString("frete"));
				transportadora.setNome(rs.getString("nome"));
				transportadora.setRamo(rs.getString("ramo"));
				transportadora.setObservacoes(rs.getString("observacoes"));
				transportadora.setNatureza(rs.getString("natureza"));
				transportadora.setRg(rs.getString("rg"));
				transportadora.setTelefone(rs.getString("telefone"));
				
				transportadora.setEndereco(endereco);
				transportadora.setCidade(endereco.getCidade().getNome());
				transportadora.setEstado(endereco.getCidade().getEstado().getNome());

				lstTransportadora.add(transportadora);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lstTransportadora;
	}

	public static void Insert(Transportadora transportadora){
		Connection con = MySqlConnect.ConectarDb();

		String sql ="insert into transportadora (nome, cnpj, frete, ramo, observacoes, oid_endereco, natureza, rg, telefone) values(?, ?, ?, ?, ?, ?, ?, ?, ?); ";

		PreparedStatement parametros;

		try {
			parametros = con.prepareStatement(sql);

			parametros.setString(1, transportadora.getNome());
			parametros.setString(2, transportadora.getCnpj());
			parametros.setString(3, transportadora.getFrete());
			parametros.setString(4, transportadora.getRamo());
			parametros.setString(5, transportadora.getObservacoes());
			parametros.setInt(6, transportadora.getEndereco().getOid_endereco());
			parametros.setString(7, transportadora.getNatureza());
			parametros.setString(8, transportadora.getRg());
			parametros.setString(8, transportadora.getTelefone());

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

	public static void Update(Transportadora transportadora) {
		Connection con = MySqlConnect.ConectarDb();

		String sql ="update transportadora set nome = ?, cnpj = ?, frete = ?, ramo = ?, observacoes = ?, rg = ?, natureza =?, telefone =? where oid_transportadora = ?; ";

		PreparedStatement parametros;

		try {
			parametros = con.prepareStatement(sql);

			parametros.setString(1, transportadora.getNome());
			parametros.setString(2, transportadora.getCnpj());
			parametros.setString(3, transportadora.getFrete());
			parametros.setString(4, transportadora.getRamo());
			parametros.setString(5, transportadora.getObservacoes());

			parametros.setString(6, transportadora.getRg());
			parametros.setString(7, transportadora.getNatureza());
			parametros.setString(8, transportadora.getTelefone());
			parametros.setInt(9, transportadora.getOid_transportadora());

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

	public void Delete(Transportadora transportadora) {
		Connection con = MySqlConnect.ConectarDb();

		String sql ="delete from transportadora where oid_transportadora = ?; ";

		PreparedStatement parametros;

		try {
			parametros = con.prepareStatement(sql);

			parametros.setInt(1, transportadora.getOid_transportadora());

			parametros.executeUpdate();
			con.close();

			Endereco.Delete(transportadora.getEndereco().getOid_endereco());
			Alerta.showInformation("sucesso", "Deletado com sucesso");
			//limpar();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alerta.showError("Erro", "Ocorreu um erro, tente novamente.");
		}
	}
}

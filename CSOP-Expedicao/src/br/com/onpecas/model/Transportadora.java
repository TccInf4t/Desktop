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
	private String estado;
	private String cidade;
	private String rg;
	private String natureza;
	private String telefone;
	private Endereco endereco;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return nome;
	}

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

	//Metodo usado para listar os registros referentes as transportadoras
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
			e.printStackTrace();
		}
		return lstTransportadora;
	}

	/*Método para inserir um novo registro de tranportadora no banco*/
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
			parametros.setString(9, transportadora.getTelefone());

			parametros.executeUpdate();
			con.close();

			Alerta.showInformation("sucesso", "Inserido com sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
			Alerta.showError("Erro", "Ocorreu um erro, tente novamente.");
		}
	}

	/*Método para atualizar o regostro de transportadora no banco de dados*/
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
		} catch (SQLException e) {
			e.printStackTrace();
			Alerta.showError("Erro", "Ocorreu um erro, tente novamente.");
		}
	}

	/*Método para apagar transportadoras no banco de dados*/
	public static void Delete(Transportadora transportadora) {
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
		} catch (SQLException e) {
			e.printStackTrace();
			Alerta.showError("Erro", "Ocorreu um erro, tente novamente.");
		}
	}

	public static Transportadora Buscar(int oid_transportadora){
		Connection con = MySqlConnect.ConectarDb();

		String sql ="select * from transportadora where oid_transportadora = "+oid_transportadora;
		Transportadora transportadora = new Transportadora();

		try {
			ResultSet rs = con.createStatement().executeQuery(sql);
			while(rs.next()){

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

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transportadora;
	}

	public static List<Transportadora> BuscarSemTransporte(){
		Connection con = MySqlConnect.ConectarDb();

		List<Transportadora> lstTransportadora = new ArrayList<Transportadora>();
		String sql ="select * from transportadora where emtransp <>1 or emtransp is null order by oid_transportadora desc";

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
			e.printStackTrace();
		}
		return lstTransportadora;
	}

	public static List<Transportadora> Filtrar(boolean temNome, boolean temEstado,
			boolean temCidade, String nomeTransp, Estado estado, Cidade cidade){
		Connection con = MySqlConnect.ConectarDb();

		List<Transportadora> lstTransportadora = new ArrayList<Transportadora>();
		String sql =null;

		if(temNome){
			if(temEstado){
				if(temCidade){
					//Filtrar pelo nome da transportadora e pela cidade
					sql ="select * from transportadora where oid_cidade = "+cidade.getOid_cidade()+" and nome like '%"+nomeTransp+"%'";

					sql="select t.* from transportadora as t inner join endereco as e on (e.oid_endereco = t.oid_endereco) "
							+ "where e.oid_cidade ="+cidade.getOid_cidade()
							+ " and nome like '%"+nomeTransp+"%'";

				}else{
					//Filtrar pelo Nome da Transportadora e pelo estado

					sql="select t.* from transportadora as t inner join endereco as e on (e.oid_endereco = t.oid_endereco) "
							+ "inner join cidade as c on (e.oid_cidade = c.oid_cidade) where c.oid_estado = "+estado.getOid_estado()
							+ " and nome like '%"+nomeTransp+"%'";

				}
			}else{
				//Filtrar por nome da transportadora
				sql ="select * from transportadora where nome like '%"+nomeTransp+"%'";
			}
		}else if(temEstado){
			if(temCidade){
				//Filtrar pelas cidade
				sql ="select * from transportadora where oid_cidade = "+cidade.getOid_cidade()+";";
				
				sql="select t.* from transportadora as t inner join endereco as e on (e.oid_endereco = t.oid_endereco) "
						+ "where e.oid_cidade ="+cidade.getOid_cidade();
			}else{
				//filtrar pelo estado
				sql="select t.* from transportadora as t inner join endereco as e on (e.oid_endereco = t.oid_endereco) "
						+ "inner join cidade as c on (e.oid_cidade = c.oid_cidade) where c.oid_estado = "+estado.getOid_estado();
			}
		}

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
			e.printStackTrace();
		}
		return lstTransportadora;
	}
}

package br.com.onpecas.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.onpecas.helper.*;

public class Lote {

	private int oid_lote;
	private String data_saida, data_entrega, frete, data_criacao, nomeTransp, status;
	private List<Pedido> lstPedido;
	private Transportadora transportadora;
	private int qtdItens;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return oid_lote +" - qtd.: "+ qtdItens ;
	}

	public String getData_criacao() {
		return data_criacao;
	}
	public void setData_criacao(String data_criacao) {
		this.data_criacao = data_criacao;
	}
	public int getQtdItens() {
		return qtdItens;
	}
	public void setQtdItens(int qtdItens) {
		this.qtdItens = qtdItens;
	}
	public Transportadora getTransportadora() {
		return transportadora;
	}
	public void setTransportadora(Transportadora transportadora) {
		this.transportadora = transportadora;
	}
	public List<Pedido> getLstPedido() {
		return lstPedido;
	}
	public void setLstPedido(List<Pedido> lstPedido) {
		this.lstPedido = lstPedido;
	}
	public int getOid_lote() {
		return oid_lote;
	}
	public void setOid_lote(int oid_lote) {
		this.oid_lote = oid_lote;
	}
	public String getData_saida() {
		return data_saida;
	}
	public void setData_saida(String data_saida) {
		this.data_saida = data_saida;
	}
	public String getData_entrega() {
		return data_entrega;
	}
	public void setData_entrega(String data_entrega) {
		this.data_entrega = data_entrega;
	}
	public String getFrete() {
		return frete;
	}
	public void setFrete(String frete) {
		this.frete = frete;
	}

	public String getNomeTransp() {
		return nomeTransp;
	}

	public void setNomeTransp(String nomeTransp) {
		this.nomeTransp = nomeTransp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public static void Insert(Lote lote){
		Connection con =  MySqlConnect.ConectarDb();

		String sqlLote ="insert into lote(dt_criacao) value(now());";

		PreparedStatement parametros;

		try {
			parametros = con.prepareStatement(sqlLote);
			parametros.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			Alerta.showError("Erro", "Ocorreu um erro, tente novamente.");
		}

		int oid_lote = BuscarUltimoIdLote();

		String sqlPedidoLote ="insert into lote_pedido(oid_pedido, oid_lote) values ";
		String values = "";
		int cont=0;
		for(Pedido item :lote.getLstPedido()){
			cont++;
			if(cont == lote.getLstPedido().size()){
				values = values+"("+item.getOid_pedido()+", "+oid_lote+");";
			}else{
				values = values+"("+item.getOid_pedido()+", "+oid_lote+"),";
			}
		}
		sqlPedidoLote = sqlPedidoLote+values;
		PreparedStatement parametros1;

		try {
			parametros1 = con.prepareStatement(sqlPedidoLote);
			parametros1.executeUpdate();
			con.close();

			Pedido.MudarStatusPedido(lote.getLstPedido(), 3);
			Alerta.showInformation("sucesso", "Inserido com sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
			Alerta.showError("Erro", "Ocorreu um erro, tente novamente.");
		}

	}

	public static int BuscarUltimoIdLote(){
		Connection con =  MySqlConnect.ConectarDb();

		String sqlLote ="select * from lote order by oid_lote desc limit 1;";

		Lote lote = new Lote();
		try {
			ResultSet rs = con.createStatement().executeQuery(sqlLote);
			while(rs.next()){
				lote.setOid_lote(rs.getInt("oid_lote"));

			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lote.getOid_lote();
	}

	public static List<Lote> Select(){
		Connection con =  MySqlConnect.ConectarDb();

		String sqlLote ="select * from lote order by oid_lote desc;";

		List<Lote> lstLote = new ArrayList<Lote>();

		try {
			ResultSet rs = con.createStatement().executeQuery(sqlLote);
			while(rs.next()){
				Lote lote = new Lote();
				lote.setOid_lote(rs.getInt("oid_lote"));
				lote.setData_criacao(rs.getString("dt_criacao"));
				lote.setData_entrega(rs.getString("dt_entrega"));
				lote.setData_saida(rs.getString("dt_saida"));
				lote.setFrete(rs.getString("frete"));

				if(rs.getInt("oid_transportadora") == 0){
					lote.setTransportadora(null);
				}else{
					lote.setTransportadora(Transportadora.Buscar(rs.getInt("oid_transportadora")));
					lote.setNomeTransp(lote.getTransportadora().getNome());
				}
				lote.setStatus(rs.getString("status"));
				lote.setLstPedido(Pedido.Buscar(rs.getInt("oid_lote")));
				lote.setQtdItens(lote.getLstPedido().size());

				lstLote.add(lote);
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lstLote;
	}

	public static List<Lote> BuscarLoteSemTransporte(){
		Connection con =  MySqlConnect.ConectarDb();

		String sqlLote ="select * from lote where emtransp<>1 or emtransp is null order by oid_lote desc;";

		List<Lote> lstLote = new ArrayList<Lote>();

		try {
			ResultSet rs = con.createStatement().executeQuery(sqlLote);
			while(rs.next()){
				Lote lote = new Lote();
				lote.setOid_lote(rs.getInt("oid_lote"));
				lote.setData_criacao(rs.getString("dt_criacao"));
				lote.setData_entrega(rs.getString("dt_entrega"));
				lote.setData_saida(rs.getString("dt_saida"));
				lote.setFrete(rs.getString("frete"));

				if(rs.getInt("oid_transportadora") == 0){
					lote.setTransportadora(null);
				}else{
					lote.setTransportadora(Transportadora.Buscar(rs.getInt("oid_transportadora")));
					lote.setNomeTransp(lote.getTransportadora().getNome());
				}

				lote.setStatus(rs.getString("status"));
				lote.setLstPedido(Pedido.Buscar(rs.getInt("oid_lote")));
				lote.setQtdItens(lote.getLstPedido().size());

				lstLote.add(lote);
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lstLote;
	}

	public static void IniciarTransporte(Lote lote){
		Connection con = MySqlConnect.ConectarDb();

		String sql ="update lote set oid_transportadora = ?, dt_saida = ?, dt_entrega = ?, frete = ?, emtransp = ? where oid_lote = ?; ";

		PreparedStatement parametros;

		try {
			parametros = con.prepareStatement(sql);

			parametros.setInt(1, lote.getTransportadora().getOid_transportadora());
			parametros.setString(2, lote.getData_saida());
			parametros.setString(3, lote.getData_entrega());
			parametros.setString(4, lote.getFrete());
			parametros.setInt(5, 1);

			parametros.setInt(6, lote.getOid_lote());
			Transportadora.IniciarTransporte(lote.getTransportadora().getOid_transportadora());
			parametros.executeUpdate();
			con.close();

			Alerta.showInformation("sucesso", "Inserido com sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
			Alerta.showError("Erro", "Ocorreu um erro, tente novamente.");
		}

	}

	public static void FinalizarLote(Lote lote) {
		Connection con = MySqlConnect.ConectarDb();

		String sql ="update lote set dt_entrega = now(), status = 'Finalizado' where oid_lote = ?; ";

		PreparedStatement parametros;

		try {
			parametros = con.prepareStatement(sql);

			parametros.setInt(1, lote.getOid_lote());

			parametros.executeUpdate();
			con.close();

			Alerta.showInformation("sucesso", "Inserido com sucesso");
		} catch (SQLException e) {
			e.printStackTrace();
			Alerta.showError("Erro", "Ocorreu um erro, tente novamente.");
		}

	}

	public static List<Lote> Filtrar(boolean temNumLote, boolean temTransp, boolean taEmTransp, boolean taFinalizado,
			int numLote, Transportadora transportadora ){

		Connection con =  MySqlConnect.ConectarDb();

		String sqlLote =null ;

		if(temNumLote){
			//Filtrar pelo numero do
			sqlLote ="select * from lote where oid_lote = "+numLote;
		}else if(temTransp){
			//Filtrar apenas pelo nome da transportadora
			sqlLote ="select * from lote where oid_transportadora = "+transportadora.getOid_transportadora();
		}else if(taEmTransp){
			//Filtrar se estiver em transporte
			sqlLote ="select * from lote where emTransp = 1;";
		}else if(taFinalizado){
			//Filtrar se estiver em transporte
			sqlLote ="select * from lote where status = 'Finalizado';";
		}

		List<Lote> lstLote = new ArrayList<Lote>();

		try {
			ResultSet rs = con.createStatement().executeQuery(sqlLote);
			while(rs.next()){
				Lote lote = new Lote();
				lote.setOid_lote(rs.getInt("oid_lote"));
				lote.setData_criacao(rs.getString("dt_criacao"));
				lote.setData_entrega(rs.getString("dt_entrega"));
				lote.setData_saida(rs.getString("dt_saida"));
				lote.setFrete(rs.getString("frete"));

				if(rs.getInt("oid_transportadora") == 0){
					lote.setTransportadora(null);
				}else{
					lote.setTransportadora(Transportadora.Buscar(rs.getInt("oid_transportadora")));
					lote.setNomeTransp(lote.getTransportadora().getNome());
				}

				lote.setStatus(rs.getString("status"));
				lote.setLstPedido(Pedido.Buscar(rs.getInt("oid_lote")));
				lote.setQtdItens(lote.getLstPedido().size());

				lstLote.add(lote);
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lstLote;
	}
}

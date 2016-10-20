package br.com.onpecas.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import br.com.onpecas.helper.*;

public class Lote {

	private int oid_lote;
	private String data_saida, data_entrega, frete;
	private List<Pedido> lstPedido;
	private Transportadora transportadora;


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

	public static void Insert(Lote lote){
		Connection con =  MySqlConnect.ConectarDb();

		String sqlLote ="insert into lote() value();";

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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lote.getOid_lote();
	}
}

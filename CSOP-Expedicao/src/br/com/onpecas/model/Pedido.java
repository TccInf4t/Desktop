package br.com.onpecas.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.onpecas.helper.*;

public class Pedido {
	private int oid_pedido, qtdItens;
	private String vlrTotal, tipoCartao, numeroCartao, vlrFrete, formaPagamento, dtRealizada, estado, cidade, statusedt, nomeCliente;

	private List<Produto> lstProduto;
	private Cliente cliente;
	private Endereco enderecoEntrega;
	private Status status;


	public String getVlrTotal() {
		return vlrTotal;
	}
	public void setVlrTotal(String vlrTotal) {
		this.vlrTotal = vlrTotal;
	}
	public int getOid_pedido() {
		return oid_pedido;
	}
	public void setOid_pedido(int oid_pedido) {
		this.oid_pedido = oid_pedido;
	}
	public Endereco getEnderecoEntrega() {
		return enderecoEntrega;
	}
	public void setEnderecoEntrega(Endereco enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}
	public String getTipoCartao() {
		return tipoCartao;
	}
	public void setTipoCartao(String tipoCartao) {
		this.tipoCartao = tipoCartao;
	}
	public String getNumeroCartao() {
		return numeroCartao;
	}
	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}
	public String getVlrFrete() {
		return vlrFrete;
	}
	public void setVlrFrete(String vlrFrete) {
		this.vlrFrete = vlrFrete;
	}
	public String getFormaPagamento() {
		return formaPagamento;
	}
	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getDtRealizada() {
		return dtRealizada;
	}
	public void setDtRealizada(String dtRealizada) {
		this.dtRealizada = dtRealizada;
	}
	public List<Produto> getLstProduto() {
		return lstProduto;
	}
	public void setLstProduto(List<Produto> lstProduto) {
		this.lstProduto = lstProduto;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public int getQtdItens() {
		return qtdItens;
	}
	public void setQtdItens(int qtdItens) {
		this.qtdItens = qtdItens;
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
	public String getStatusedt() {
		return statusedt;
	}
	public void setStatusedt(String statusedt) {
		this.statusedt = statusedt;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public static List<Pedido> Select(){
		Connection con = MySqlConnect.ConectarDb();
		List<Pedido> lstPedidos = new ArrayList<Pedido>();
		String sql ="select * from pedido where oid_status =2;";

		try {
			ResultSet rs = con.createStatement().executeQuery(sql);
			while(rs.next()){

				Pedido pedido = new Pedido();

				pedido.setOid_pedido(rs.getInt("oid_pedido"));
				pedido.setTipoCartao(rs.getString("tipocartao"));
				pedido.setNumeroCartao(rs.getString("numcartao"));
				pedido.setVlrFrete(rs.getString("frete"));
				pedido.setFormaPagamento(rs.getString("formapagamento"));
				pedido.setDtRealizada(rs.getString("dtrealizado"));

				pedido.setCliente(Cliente.Select(rs.getInt("oid_cliente")));
				pedido.setEnderecoEntrega(Endereco.BuscarEndereco(rs.getInt("oid_endereco")));
				pedido.setStatus(Status.BuscarStatus(rs.getInt("oid_status")));
				pedido.setLstProduto(Produto.Select(rs.getInt("oid_pedido")));

				pedido.setQtdItens(pedido.getLstProduto().size());

				double vlrFinalItens = 0;
				for(Produto item: pedido.getLstProduto()){
					vlrFinalItens+= Double.parseDouble(item.getPrecoTotalvendido());
				}

				double valorFinal = vlrFinalItens + Double.parseDouble(pedido.vlrFrete);
				pedido.setVlrTotal(""+valorFinal);

				pedido.setCidade(pedido.getEnderecoEntrega().getCidade().getNome());
				pedido.setEstado(pedido.getEnderecoEntrega().getCidade().getEstado().getNome());
				pedido.setNomeCliente(pedido.getCliente().getNome());
				pedido.setStatusedt(pedido.getStatus().getNome());
				lstPedidos.add(pedido);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lstPedidos;
	}

	public static void MudarStatusPedido(List<Pedido> lstPedidos, int oid_status){

		Connection con =  MySqlConnect.ConectarDb();

		String sql = "update pedido set oid_status = "+oid_status+" where ";
		String values = "";

		int cont = 0;
		for(Pedido item : lstPedidos){
			cont ++;
			values = values + "";

			if(cont == lstPedidos.size()){
				values = values + "oid_pedido = " +item.getOid_pedido()+";";
			}else{
				values = values + "oid_pedido = "+item.getOid_pedido()+" or ";
			}
		}

		sql += values;

		PreparedStatement parametros;

		try {
			parametros = con.prepareStatement(sql);
			parametros.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			Alerta.showError("Erro", "Ocorreu um erro, tente novamente.");
		}
	}

	public static List<Pedido> Buscar(int oid_lote){
		Connection con = MySqlConnect.ConectarDb();
		List<Pedido> lstPedidos = new ArrayList<Pedido>();
		String sql ="select * from pedido inner join lote_pedido on (pedido.oid_pedido = lote_pedido.oid_pedido) where lote_pedido.oid_lote="+oid_lote;

		try {
			ResultSet rs = con.createStatement().executeQuery(sql);
			while(rs.next()){

				Pedido pedido = new Pedido();

				pedido.setOid_pedido(rs.getInt("oid_pedido"));
				pedido.setTipoCartao(rs.getString("tipocartao"));
				pedido.setNumeroCartao(rs.getString("numcartao"));
				pedido.setVlrFrete(rs.getString("frete"));
				pedido.setFormaPagamento(rs.getString("formapagamento"));
				pedido.setDtRealizada(rs.getString("dtrealizado"));

				pedido.setCliente(Cliente.Select(rs.getInt("oid_cliente")));
				pedido.setEnderecoEntrega(Endereco.BuscarEndereco(rs.getInt("oid_endereco")));
				pedido.setStatus(Status.BuscarStatus(rs.getInt("oid_status")));
				pedido.setLstProduto(Produto.Select(rs.getInt("oid_pedido")));

				pedido.setQtdItens(pedido.getLstProduto().size());

				double vlrFinalItens = 0;
				for(Produto item: pedido.getLstProduto()){
					vlrFinalItens+= Double.parseDouble(item.getPrecoTotalvendido());
				}

				double valorFinal = vlrFinalItens + Double.parseDouble(pedido.vlrFrete);
				pedido.setVlrTotal(""+valorFinal);

				pedido.setCidade(pedido.getEnderecoEntrega().getCidade().getNome());
				pedido.setEstado(pedido.getEnderecoEntrega().getCidade().getEstado().getNome());
				pedido.setNomeCliente(pedido.getCliente().getNome());
				pedido.setStatusedt(pedido.getStatus().getNome());
				lstPedidos.add(pedido);
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lstPedidos;
	}
}

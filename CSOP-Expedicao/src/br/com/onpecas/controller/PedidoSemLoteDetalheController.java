package br.com.onpecas.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.onpecas.model.*;
import br.com.onpecas.view.CallScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class PedidoSemLoteDetalheController implements Initializable{

	@FXML TextField txtValorFrete, txtCliente, txtNumPedido, txtDataPedido, txtFormaPagamento, txtNumLote, txtValorFinal, txtQtdItens, txtEnderecoEntrega ;
	
	@FXML TableView<Produto> tblListProduto;
	
	@FXML TableColumn<Produto, String> clnNomePeca, clnDescricao, clnQtd, clnDataVencimento, clnValorUnit, clnValorTotal;
	
	@FXML Button btnVoltar;

	Pedido pedido;
	int lote;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		PreecherCampos();
		btnVoltar.setOnAction(l-> CallScene.secondStage.close());
	}

	public void PreecherCampos(){
		if(pedido == null){
			
		}else{
			txtCliente.setText(pedido.getCliente().getNome());
			txtNumPedido.setText(""+pedido.getOid_pedido());
			txtDataPedido.setText(pedido.getDtRealizada());
			txtFormaPagamento.setText(pedido.getFormaPagamento());
			txtNumLote.setText("--------");
			txtValorFinal.setText(pedido.getVlrTotal());
			txtQtdItens.setText(""+pedido.getQtdItens());
			txtEnderecoEntrega.setText(pedido.getEnderecoEntrega().getEnderecoCompleto());
			txtValorFrete.setText(pedido.getVlrFrete());
			
			CarregarTblPeca(pedido.getLstProduto());
		}
		
	}
	public PedidoSemLoteDetalheController(Pedido pedido, int lote) {
		this.pedido = pedido;
		this.lote = lote;
	}

	public void CarregarTblPeca(List<Produto> lstProduto){
		 clnNomePeca.setCellValueFactory(new PropertyValueFactory<Produto, String>("nome"));
		 clnDescricao.setCellValueFactory(new PropertyValueFactory<Produto, String>("descricao"));
		 clnQtd.setCellValueFactory(new PropertyValueFactory<Produto, String>("qtdItens"));
		 clnDataVencimento.setCellValueFactory(new PropertyValueFactory<Produto, String>("validade"));
		 clnValorUnit.setCellValueFactory(new PropertyValueFactory<Produto, String>("precovendido"));
		 clnValorTotal.setCellValueFactory(new PropertyValueFactory<Produto, String>("precoTotalvendido"));
		 
		 ObservableList<Produto> data = FXCollections.observableList(lstProduto);

		 tblListProduto.setItems(data);
	}

}
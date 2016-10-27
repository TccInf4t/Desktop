package br.com.onpecas.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.com.onpecas.helper.Helper;
import br.com.onpecas.model.*;
import br.com.onpecas.view.CallScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class PedidoSemLoteDetalheController implements Initializable{

	@FXML TextField txtValorFrete, txtCliente, txtNumPedido, txtDataPedido, txtFormaPagamento, txtValorFinal, txtQtdItens, txtEnderecoEntrega ;
	@FXML TableView<Produto> tblListProduto;
	@FXML TableColumn<Produto, String> clnNomePeca, clnDescricao, clnQtd, clnDataVencimento, clnValorUnit, clnValorTotal;
	@FXML Button btnVoltar, btnAlterar;
	@FXML ComboBox<Status> cboStatus;

	Pedido pedido;
	boolean lote;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		PreecherCampos();
		btnVoltar.setOnAction(l-> CallScene.thirdStage.close());
		btnAlterar.setOnAction(l-> AlterarStatus());
		cboStatus.getItems().addAll(Status.Select());
		if(!lote){
			cboStatus.setDisable(true);
		}
	}

	public PedidoSemLoteDetalheController(Pedido pedido, boolean lote) {
		this.pedido = pedido;
		this.lote = lote;
	}

	@FXML
	private void handleComboBoxAction() {
		btnAlterar.setDisable(false);
	}

	public void PreecherCampos(){
		if(pedido != null){
			txtCliente.setText(pedido.getCliente().getNome());
			txtNumPedido.setText(""+pedido.getOid_pedido());
			txtDataPedido.setText(pedido.getDtRealizada());
			txtFormaPagamento.setText(pedido.getFormaPagamento());
			txtValorFinal.setText(pedido.getVlrTotal());
			txtQtdItens.setText(""+pedido.getQtdItens());
			txtEnderecoEntrega.setText(pedido.getEnderecoEntrega().getEnderecoCompleto());
			txtValorFrete.setText(pedido.getVlrFrete());
			cboStatus.getSelectionModel().select(pedido.getStatus());

			CarregarTblPeca(pedido.getLstProduto());
		}
	}

	public void AlterarStatus(){
		List<Pedido> lstPedido = new ArrayList<Pedido>();
		lstPedido.add(pedido);
		Status status =  cboStatus.getSelectionModel().getSelectedItem();
		Pedido.MudarStatusPedido(lstPedido,status.getOid_status());

		Helper.AUXPEDIDOLOTEDET.setValue(1);
		CallScene.thirdStage.close();
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

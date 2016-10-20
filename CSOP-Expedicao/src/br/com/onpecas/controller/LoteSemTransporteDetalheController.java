package br.com.onpecas.controller;

import java.net.URL;
import java.util.*;

import br.com.onpecas.model.*;
import br.com.onpecas.view.CallScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class LoteSemTransporteDetalheController implements Initializable{

	@FXML TextField txtNumLote, txtDataCriacao, txtDataPrevSaida, txtDataPrevFinal, txtDataRealSaida, txtDataRealFinal, txtValorTotalLote;
	@FXML Button btnVoltar;

	@FXML TableView<Pedido> tblPedido;
	@FXML TableColumn<Pedido, String> clnNumPedido, clnDtCompra, clnStatus, clnCliente, clnQtdItens,clnFormaPagamento;

	Lote lote;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		PreecherCampos();


		tblPedido.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
			@Override
		    public void handle(MouseEvent click) {
		        if (click.getClickCount() == 2) {
		        	CallScene callScene = new CallScene();
		        	Pedido pedido = tblPedido.getSelectionModel().getSelectedItem();
		        	callScene.LoadPedidoSemLoteDetalhe(pedido);
		        }else if(click.getClickCount() == 1){

		        }
		    }
		});

		btnVoltar.setOnAction(l-> CallScene.secondStage.close());
	}

	public void PreecherCampos(){
		if(lote == null){

		}else{
			txtNumLote.setText(""+lote.getOid_lote());
			txtDataCriacao.setText(lote.getData_criacao());
			txtDataPrevSaida.setText(lote.getData_saida());
			txtDataPrevFinal.setText(lote.getData_entrega());
			txtDataRealSaida.setText("");
			txtDataRealFinal.setText("");
			txtValorTotalLote.setText("");

			AtualizarTblPedido(lote.getLstPedido());
		}
	}

	public LoteSemTransporteDetalheController(Lote lote) {
		this.lote = lote;
	}

	public void AtualizarTblPedido(List<Pedido> lstPedido){

		clnFormaPagamento.setCellValueFactory(new PropertyValueFactory<Pedido, String>("formaPagamento"));
		clnNumPedido.setCellValueFactory(new PropertyValueFactory<Pedido, String>("oid_pedido"));
		clnDtCompra.setCellValueFactory(new PropertyValueFactory<Pedido, String>("dtRealizada"));
		clnStatus.setCellValueFactory(new PropertyValueFactory<Pedido, String>("statusedt"));
		clnCliente.setCellValueFactory(new PropertyValueFactory<Pedido, String>("nomeCliente"));
		clnQtdItens.setCellValueFactory(new PropertyValueFactory<Pedido, String>("qtdItens"));

        ObservableList<Pedido> data = FXCollections.observableList(lstPedido);

        tblPedido.setItems(data);
	}

}
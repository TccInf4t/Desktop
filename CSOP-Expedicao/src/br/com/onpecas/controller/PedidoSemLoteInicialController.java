package br.com.onpecas.controller;

import java.net.URL;
import java.util.*;

import br.com.onpecas.helper.Alerta;
import br.com.onpecas.helper.Helper;
import br.com.onpecas.model.*;
import br.com.onpecas.view.CallScene;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class PedidoSemLoteInicialController implements Initializable{

	@FXML TableView<Pedido> tblPedido;
	@FXML TableColumn<Pedido, String> clnNumPedido, clnDtCompra, clnStatus, clnCliente, clnQtdItens, clnEstado, clnCidade;
	@FXML Button btnVisualizar, btnGerarLote;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		AtualizarTblPedido();
		btnVisualizar.setOnAction(l-> VisualizarPedido());
		btnGerarLote.setOnAction(l-> GerarLote());


		Helper.AUXPEDIDOLOTE.addListener(new ChangeListener<Object>() {
		     @Override
		     public void changed(ObservableValue<?> observableValue, Object oldValue,
		         Object newValue) {
		         int newValuenovo =Integer.parseInt(newValue.toString());
		         if(newValuenovo == 1){
		            AtualizarTblPedido();
		            Helper.AUXPEDIDOLOTE.setValue(0);
		         }else{

		         }
		     }
		   });
	}

	public void VisualizarPedido(){
		Pedido pedidoSelecionado = tblPedido.getSelectionModel().getSelectedItem();
		CallScene callScene = new CallScene();
		if(pedidoSelecionado != null){
			callScene.LoadPedidoSemLoteDetalhe(pedidoSelecionado);
		}else{
			Alerta.showError("N�o foi possivel visualizar", "Selecione ao menos um pedido para servisualizado");
		}
	}

	public void GerarLote(){
		List<Pedido> lstPedidos = tblPedido.getItems();

		if(lstPedidos ==null){
			Alerta.showError("N�o foi possivel gerar lote", "A tabela de pedidos est� vazia");
		}else{
			CallScene callScene = new CallScene();
			callScene.LoadGerarLote(lstPedidos);
		}
	}

	public void AtualizarTblPedido(){

		clnCidade.setCellValueFactory(new PropertyValueFactory<Pedido, String>("cidade"));
		clnEstado.setCellValueFactory(new PropertyValueFactory<Pedido, String>("estado"));
		clnNumPedido.setCellValueFactory(new PropertyValueFactory<Pedido, String>("oid_pedido"));
		clnDtCompra.setCellValueFactory(new PropertyValueFactory<Pedido, String>("dtRealizada"));
		clnStatus.setCellValueFactory(new PropertyValueFactory<Pedido, String>("statusedt"));
		clnCliente.setCellValueFactory(new PropertyValueFactory<Pedido, String>("nomeCliente"));
		clnQtdItens.setCellValueFactory(new PropertyValueFactory<Pedido, String>("qtdItens"));

        List<Pedido> lstPedido = Pedido.Select();
        ObservableList<Pedido> data = FXCollections.observableList(lstPedido);

        tblPedido.setItems(data);
	}
}

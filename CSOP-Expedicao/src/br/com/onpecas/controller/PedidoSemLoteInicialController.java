package br.com.onpecas.controller;

import java.net.URL;
import java.util.*;

import br.com.onpecas.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class PedidoSemLoteInicialController implements Initializable{

	@FXML TableView<Pedido> tblPedido;
	@FXML TableColumn<Pedido, String> clnNumPedido, clnDtCompra, clnStatus, clnCliente, clnQtdItens, clnEstado, clnCidade;
	@FXML Button btnVisualizar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		AtualizarTblPedido();
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

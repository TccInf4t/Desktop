package br.com.onpecas.controller;

import java.net.URL;
import java.util.*;

import br.com.onpecas.helper.Alerta;
import br.com.onpecas.helper.Helper;
import br.com.onpecas.model.*;
import br.com.onpecas.view.CallScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class GerarLoteController implements Initializable{

	@FXML Button btnCancelar, btnConcluir, btnPassarUm, btnVoltarUm, btnPassarTodos, btnVoltarTodos;
	@FXML TableView<Pedido> tblItensNaoPassados, tblItensPassados;
	@FXML TableColumn<Pedido, String> clnNumPedidoNP, clnDataRealizadoNP, clnNumPedidoP, clnDataRealizadoP;

	List<Pedido> lstItensNaoPassados, lstItensPassados;
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btnCancelar.setOnAction(l-> CallScene.secondStage.close());
		btnConcluir.setOnAction(l-> Concluir());

		btnPassarUm.setOnAction(l-> PassarUm());
		btnVoltarUm.setOnAction(l-> VoltarUm());
		btnPassarTodos.setOnAction(l-> PassarTodos());
		btnVoltarTodos.setOnAction(l-> VoltarTodos());

		CarregarItensNaoPassados();
	}

	public GerarLoteController(List<Pedido> lstTodosPedidos) {
		this.lstItensNaoPassados = new ArrayList<Pedido>();
		this.lstItensNaoPassados.addAll(lstTodosPedidos);

	}

	public void PassarUm(){
		Pedido pedidoSelecionado = tblItensNaoPassados.getSelectionModel().getSelectedItem();

		if(pedidoSelecionado == null){
			Alerta.showError("Erro ao passar item", "Selecione um item para passar");
		}else{
			lstItensPassados.add(pedidoSelecionado);
			lstItensNaoPassados.remove(pedidoSelecionado);

			AtualizarItensNaoPassados();
			AtualizarItensPassados();
		}
	}

	public void VoltarUm(){
		Pedido pedidoSelecionado = tblItensPassados.getSelectionModel().getSelectedItem();

		if(pedidoSelecionado == null){
			Alerta.showError("Erro ao voltar item", "Selecione um item para voltar");
		}else{
			lstItensPassados.remove(pedidoSelecionado);
			lstItensNaoPassados.add(pedidoSelecionado);

			AtualizarItensNaoPassados();
			AtualizarItensPassados();
		}
	}

	public void PassarTodos(){
		if(tblItensNaoPassados.getItems().isEmpty()){
			Alerta.showError("Erro ao passar todos item", "A tabela Pedidos Sem Lote est� vazia");
		}else{
			lstItensPassados.addAll(tblItensNaoPassados.getItems());
			lstItensNaoPassados.removeAll(tblItensNaoPassados.getItems());

			AtualizarItensNaoPassados();
			AtualizarItensPassados();
		}
	}

	public void VoltarTodos(){
		if(tblItensPassados.getItems().isEmpty()){
			Alerta.showError("Erro ao passar todos item", "A tabela Pedidos no Lote est� vazia");
		}else{
			lstItensNaoPassados.addAll(tblItensPassados.getItems());
			lstItensPassados.removeAll(tblItensPassados.getItems());

			AtualizarItensNaoPassados();
			AtualizarItensPassados();
		}
	}

	public void Concluir(){
		if(tblItensPassados.getItems().isEmpty()){
			Alerta.showError("N�o foi possivel gerar Lote", "A tabela de Pedidos no Lote est� vazia");
		}else{
			Lote lote = new Lote();
			lote.setLstPedido(lstItensPassados);
			Lote.Insert(lote);
			Helper.AUXPEDIDOLOTE.setValue(1);
			CallScene.secondStage.close();

		}

	}

	public void CarregarItensNaoPassados(){
		lstItensPassados = new ArrayList<Pedido>();

		clnNumPedidoNP.setCellValueFactory(new PropertyValueFactory<Pedido, String>("oid_pedido"));
		clnDataRealizadoNP.setCellValueFactory(new PropertyValueFactory<Pedido, String>("dtRealizada"));

		ObservableList<Pedido> data = FXCollections.observableList(lstItensNaoPassados);
	    tblItensNaoPassados.setItems(data);
	}

	public void AtualizarItensNaoPassados(){
		clnNumPedidoNP.setCellValueFactory(new PropertyValueFactory<Pedido, String>("oid_pedido"));
		clnDataRealizadoNP.setCellValueFactory(new PropertyValueFactory<Pedido, String>("dtRealizada"));

		ObservableList<Pedido> data = FXCollections.observableList(lstItensNaoPassados);
	    tblItensNaoPassados.setItems(data);
	}

	public void AtualizarItensPassados(){
		clnNumPedidoP.setCellValueFactory(new PropertyValueFactory<Pedido, String>("oid_pedido"));
		clnDataRealizadoP.setCellValueFactory(new PropertyValueFactory<Pedido, String>("dtRealizada"));

		ObservableList<Pedido> data = FXCollections.observableList(lstItensPassados);
	    tblItensPassados.setItems(data);
	}
}

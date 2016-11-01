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
import javafx.scene.input.MouseEvent;

public class LoteSemTransporteDetalheController implements Initializable{

	@FXML TextField txtNumLote, txtDataCriacao, txtDataPrevSaida, txtDataPrevFinal, txtValorFrete, txtStatusLote;
	@FXML Button btnVoltar;

	@FXML TableView<Pedido> tblPedido;
	@FXML TableColumn<Pedido, String> clnNumPedido, clnDtCompra, clnStatus, clnCliente, clnQtdItens, clnFormaPagamento;

	static Lote lote;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		PreecherCampos();

		tblPedido.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
			@Override
		    public void handle(MouseEvent click) {
		        if (click.getClickCount() == 2) {
		        	CallScene callScene = new CallScene();
		        	Pedido pedido = tblPedido.getSelectionModel().getSelectedItem();
		        	
		        	if(pedido!= null && pedido.getStatus().getOid_status() != 5){
		        		if(lote.getStatus() == null){
			        		callScene.LoadPedidoSemLoteDetalhe(pedido, false);
			        	}else{
			        		if(!lote.getStatus().equals("Finalizado")){
			        			callScene.LoadPedidoSemLoteDetalhe(pedido, true);
			        		}else{
			        			callScene.LoadPedidoSemLoteDetalhe(pedido, false);
			        		}
			        	}
		        	}else{
		        		Alerta.showError("N�o foi poss�vel detalhar", "O pedido selecionado j� foi entregue");
		        	}
		        }else if(click.getClickCount() == 1){

		        }
		    }
		});


		Helper.AUXPEDIDOLOTEDET.addListener(new ChangeListener<Object>() {
		     @Override
		     public void changed(ObservableValue<?> observableValue, Object oldValue,
		         Object newValue) {
	        	lote.setLstPedido(Pedido.Buscar(lote.getOid_lote()));
	            AtualizarTblPedido(lote.getLstPedido());
	            Helper.AUXPEDIDOLOTEDET.setValue(0);

	            int cont=0;
	            for(Pedido item : lote.getLstPedido()){
	            	if(item.getStatus().getOid_status() == 5){
	            		cont ++;
	            	}
	            }

	            if(cont >0 && cont == lote.getLstPedido().size()){
	            	Lote.FinalizarLote(lote);
	            }
	            PreecherCampos();
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
			txtValorFrete.setText(lote.getFrete());
			txtStatusLote.setText(lote.getStatus());
			AtualizarTblPedido(lote.getLstPedido());
		}
	}

	public LoteSemTransporteDetalheController(Lote lote) {
		LoteSemTransporteDetalheController.lote = lote;
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

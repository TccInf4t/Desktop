package br.com.onpecas.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.onpecas.helper.Alerta;
import br.com.onpecas.model.*;
import br.com.onpecas.view.CallScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class LoteSemTransporteInicialController implements Initializable {

	@FXML TableView<Lote> tblLote;
	@FXML TableColumn<Lote, String> clnNumLote, clnDataPrevSaida, clnDataPrevFinal, clnQtdPedido, clnDataCriacao, clnTransportadora;
	@FXML Button btnIniciarTransporte, btnVisualizar;
	@FXML CheckBox chbEmTransporte;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		AtualizarTblLote();

		btnVisualizar.setOnAction(l-> CarregarLoteDetalhe());
		btnIniciarTransporte.setOnAction(l-> IniciarTransporte());
	}

	public void IniciarTransporte(){
		CallScene callScene = new CallScene();

		callScene.LoadIniciarTransporte();
	}

	public void CarregarLoteDetalhe(){
		CallScene callScene = new CallScene();

		Lote loteSelecionado = tblLote.getSelectionModel().getSelectedItem();

		if(loteSelecionado == null){
			Alerta.showError("N�o foi possivel Detalhar", "Selecione um lote para visualizar");
		}else{
			callScene.LoadLoteSemTransporteDetalhe(loteSelecionado);
		}
	}

	public void AtualizarTblLote(){
		clnNumLote.setCellValueFactory(new PropertyValueFactory<Lote, String>("oid_lote"));
		clnDataPrevSaida.setCellValueFactory(new PropertyValueFactory<Lote, String>("data_saida"));
		clnDataPrevFinal.setCellValueFactory(new PropertyValueFactory<Lote, String>("data_entrega"));
		clnQtdPedido.setCellValueFactory(new PropertyValueFactory<Lote, String>("qtdItens"));
		clnDataCriacao.setCellValueFactory(new PropertyValueFactory<Lote, String>("data_criacao"));
		clnTransportadora.setCellValueFactory(new PropertyValueFactory<Lote, String>("nomeTransp"));
		
		List<Lote> lstLote = Lote.Select();
        ObservableList<Lote> data = FXCollections.observableList(lstLote);

        tblLote.setItems(data);
	}
}

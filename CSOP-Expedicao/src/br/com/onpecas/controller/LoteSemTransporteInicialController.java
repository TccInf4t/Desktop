package br.com.onpecas.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.onpecas.helper.Alerta;
import br.com.onpecas.helper.Helper;
import br.com.onpecas.helper.Mascaras;
import br.com.onpecas.model.*;
import br.com.onpecas.view.CallScene;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;

public class LoteSemTransporteInicialController implements Initializable {

	@FXML TableView<Lote> tblLote;
	@FXML TableColumn<Lote, String> clnNumLote, clnDataPrevSaida, clnDataPrevFinal, clnQtdPedido, clnDataCriacao, clnTransportadora, clnStatusLote;
	@FXML Button btnIniciarTransporte, btnVisualizar, btnLimparFiltro, btnFiltrar;
	@FXML CheckBox chbEmTransporte, chbFinalizado;
	@FXML ComboBox<Transportadora> cboTransportadora;
	@FXML TextField txtNumLote;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		AtualizarTblLote();
		ConfigurarBotoes();

		cboTransportadora.setOnKeyPressed(k-> {
			KeyCombination  backspace = new KeyCodeCombination(KeyCode.BACK_SPACE);
			if(backspace.match(k)){
				cboTransportadora.getSelectionModel().clearSelection();
				txtNumLote.setDisable(false);
				chbEmTransporte.setDisable(false);
				chbFinalizado.setDisable(false);

			}
		});

		txtNumLote.textProperty().addListener((observable, oldValue, newValue) -> {
		    if(newValue.isEmpty()){
		    	chbEmTransporte.setDisable(false);
				cboTransportadora.setDisable(false);
				chbFinalizado.setDisable(false);
		    }else{
		    	chbEmTransporte.setDisable(true);
				cboTransportadora.setDisable(true);
				chbFinalizado.setDisable(true);
		    }
		});

		Helper.AUXLOTETRANSPORTE.addListener(new ChangeListener<Object>() {
		     @Override
		     public void changed(ObservableValue<?> observableValue, Object oldValue,
		         Object newValue) {
		         int newValuenovo =Integer.parseInt(newValue.toString());
		         if(newValuenovo == 1){
		            AtualizarTblLote();
		            Helper.AUXLOTETRANSPORTE.setValue(0);
		         }else{

		         }
		     }
		   });
	}

	public void IniciarTransporte(){
		CallScene callScene = new CallScene();

		callScene.LoadIniciarTransporte();
	}

	public void ConfigurarBotoes(){
		btnVisualizar.setOnAction(l-> CarregarLoteDetalhe());
		btnIniciarTransporte.setOnAction(l-> IniciarTransporte());
		btnLimparFiltro.setOnAction(l-> LimparFiltro());
		btnFiltrar.setOnAction(l-> Filtrar());

		Mascaras.mascaraNumeroInteiro(txtNumLote);
		cboTransportadora.getItems().addAll(Transportadora.Select());
	}

	@FXML
	private void handleCheckBoxAction() {
		if(chbEmTransporte.isSelected()){
			txtNumLote.setDisable(true);
			cboTransportadora.setDisable(true);
			chbFinalizado.setDisable(true);
		}else{
			txtNumLote.setDisable(false);
			cboTransportadora.setDisable(false);
			chbFinalizado.setDisable(false);
		}
	}

	@FXML
	private void handleCheckBoxActionFinalizado() {
		if(chbFinalizado.isSelected()){
			txtNumLote.setDisable(true);
			cboTransportadora.setDisable(true);
			chbEmTransporte.setDisable(true);
		}else{
			txtNumLote.setDisable(false);
			cboTransportadora.setDisable(false);
			chbEmTransporte.setDisable(false);
		}
	}


	@FXML
	private void handleComboBoxAction() {
		txtNumLote.setDisable(true);
		chbEmTransporte.setDisable(true);
		chbFinalizado.setDisable(true);
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

	public void LimparFiltro(){
		txtNumLote.setText("");
		cboTransportadora.getSelectionModel().clearSelection();
		chbEmTransporte.setSelected(false);
		chbFinalizado.setSelected(false);

		txtNumLote.setDisable(false);
		chbEmTransporte.setDisable(false);
		cboTransportadora.setDisable(false);
		chbFinalizado.setDisable(false);

		AtualizarTblLote();
	}

	public void Filtrar(){
		String numLote = txtNumLote.getText();
		Transportadora transportadora = cboTransportadora.getSelectionModel().getSelectedItem();
		boolean emTransp = chbEmTransporte.isSelected();
		boolean finalizado = chbFinalizado.isSelected();

		if(!numLote.isEmpty()){
			//Filtrar pelo numero do
			int numeroLote = Integer.parseInt(numLote);
			AtualizarTblLoteComFiltro(Lote.Filtrar(true, false, false, false, numeroLote, null));

		}else if(transportadora != null){
			//Filtrar apenas pelo nome da transportadora
			AtualizarTblLoteComFiltro(Lote.Filtrar(false, true, false, false, 0, transportadora));
		}else if(emTransp){
			//Filtrar se estiver em transporte
			AtualizarTblLoteComFiltro(Lote.Filtrar(false, false, true, false, 0, null));
		}else if(finalizado){
			//Filtrar se estiver em transporte
			AtualizarTblLoteComFiltro(Lote.Filtrar(false, false, false, true, 0, null));
		}else{
			Alerta.showError("N�o foi possivel filtrar", "� preciso selecionar/preencher algum campo de filtro");
		}
	}

	public void AtualizarTblLote(){
		clnNumLote.setCellValueFactory(new PropertyValueFactory<Lote, String>("oid_lote"));
		clnDataPrevSaida.setCellValueFactory(new PropertyValueFactory<Lote, String>("data_saida"));
		clnDataPrevFinal.setCellValueFactory(new PropertyValueFactory<Lote, String>("data_entrega"));
		clnQtdPedido.setCellValueFactory(new PropertyValueFactory<Lote, String>("qtdItens"));
		clnDataCriacao.setCellValueFactory(new PropertyValueFactory<Lote, String>("data_criacao"));
		clnTransportadora.setCellValueFactory(new PropertyValueFactory<Lote, String>("nomeTransp"));
		clnStatusLote.setCellValueFactory(new PropertyValueFactory<Lote, String>("status"));

		List<Lote> lstLote = Lote.Select();
        ObservableList<Lote> data = FXCollections.observableList(lstLote);

        tblLote.setItems(data);
	}


	public void AtualizarTblLoteComFiltro(List<Lote> lstLote){
		clnNumLote.setCellValueFactory(new PropertyValueFactory<Lote, String>("oid_lote"));
		clnDataPrevSaida.setCellValueFactory(new PropertyValueFactory<Lote, String>("data_saida"));
		clnDataPrevFinal.setCellValueFactory(new PropertyValueFactory<Lote, String>("data_entrega"));
		clnQtdPedido.setCellValueFactory(new PropertyValueFactory<Lote, String>("qtdItens"));
		clnDataCriacao.setCellValueFactory(new PropertyValueFactory<Lote, String>("data_criacao"));
		clnTransportadora.setCellValueFactory(new PropertyValueFactory<Lote, String>("nomeTransp"));
		clnStatusLote.setCellValueFactory(new PropertyValueFactory<Lote, String>("status"));

        ObservableList<Lote> data = FXCollections.observableList(lstLote);

        tblLote.setItems(data);
	}
}

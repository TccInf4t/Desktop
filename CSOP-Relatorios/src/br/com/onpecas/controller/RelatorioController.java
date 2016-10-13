package br.com.onpecas.controller;

import java.net.URL;
import java.util.*;

import br.com.onpecas.helper.Alerta;
import br.com.onpecas.model.Relatorio;
import javafx.beans.value.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;

public class RelatorioController implements Initializable{

	@FXML Button btnFiltrar;
	@FXML ComboBox<String> cboTipoRelatorio, cboPeriodoRelat�rio;

	@FXML LineChart<String, String> lineCharRelatorio;
	@FXML DatePicker datePickerInicial, datePickerFinal;

	@FXML RadioButton radioPeriodo, radioDataPeriodo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CarregarBotoes();
		ConfigurarBotoes();
		cboPeriodoRelat�rio.setDisable(true);
		datePickerInicial.setDisable(true);
		datePickerFinal.setDisable(true);

		radioPeriodo.setDisable(true);
		radioDataPeriodo.setDisable(true);
	}

	public void CarregarBotoes(){
		cboTipoRelatorio.getItems().addAll("Faturamento", "Pedido");
		btnFiltrar.setOnAction(l-> Filtrar());
	}

	public void ConfigurarBotoes(){
		ToggleGroup group = new ToggleGroup();

		radioDataPeriodo.toggleGroupProperty().set(group);
		radioPeriodo.toggleGroupProperty().set(group);

		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				if(radioPeriodo.isSelected()){
					String sltComboTipo = cboTipoRelatorio.getSelectionModel().getSelectedItem();
					if(sltComboTipo!= null){
						if(sltComboTipo.equals("Faturamento")){
							cboPeriodoRelat�rio.setDisable(false);;
							cboPeriodoRelat�rio.setPromptText("Selecione");
							
						}else if(sltComboTipo.equals("Pedido")){
							cboPeriodoRelat�rio.setDisable(false);;
							cboPeriodoRelat�rio.setPromptText("Selecione");
						}
						datePickerInicial.setDisable(true);
						datePickerFinal.setDisable(true);
					}
				}else if (radioDataPeriodo.isSelected()){
					cboPeriodoRelat�rio.setDisable(true);
					cboPeriodoRelat�rio.setPromptText("");
					datePickerInicial.setDisable(false);
					datePickerFinal.setDisable(false);
				}
			}
		});
	}

	 @FXML
	 private void handleButtonAction(ActionEvent event) {
	     // Button was clicked, do something...
		 String sltComboTipo = cboTipoRelatorio.getSelectionModel().getSelectedItem();
		 if(sltComboTipo.equals("Faturamento")){
			 cboPeriodoRelat�rio.getItems().addAll("Semanal", "Mensal", "Anual");

		 }if(sltComboTipo.equals("Pedido")){
			 cboPeriodoRelat�rio.getItems().addAll("Semanal", "Mensal", "Anual");

		 }
		 radioPeriodo.setDisable(false);
		 radioDataPeriodo.setDisable(false);
	 }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void Filtrar(){
		String sltComboTipo = cboTipoRelatorio.getSelectionModel().getSelectedItem();
		if(sltComboTipo != null){
			if(sltComboTipo.equals("Faturamento")){
				if(radioPeriodo.isSelected()){
					String sltComboPeriodo = cboPeriodoRelat�rio.getSelectionModel().getSelectedItem();
					if(sltComboPeriodo != null){
						if(sltComboPeriodo.equals("Anual")){
							lineCharRelatorio.getData().clear();
							List<HashMap<Integer,String>> lstHash = Relatorio.ListarFaturamento("Anual");

							XYChart.Series lucro = new XYChart.Series();
							lucro.setName("Faturamento anual");
							for(int i = 0; i<lstHash.size(); i++ ){

								double valorfaturado = Double.parseDouble(lstHash.get(i).get(2));
								lucro.getData().add(new XYChart.Data(lstHash.get(i).get(1), valorfaturado));
							}
							lineCharRelatorio.getData().addAll(lucro);

						}else if(sltComboPeriodo.equals("Mensal")){
							lineCharRelatorio.getData().clear();
							List<HashMap<Integer,String>> lstHash = Relatorio.ListarFaturamento("Mensal");

							XYChart.Series lucro = new XYChart.Series();
							lucro.setName("Faturamento Mensal");
							for(int i = 0; i<lstHash.size(); i++ ){

								double valorfaturado = Double.parseDouble(lstHash.get(i).get(2));
								lucro.getData().add(new XYChart.Data(lstHash.get(i).get(1), valorfaturado));
							}
							lineCharRelatorio.getData().addAll(lucro);

						}else if(sltComboPeriodo.equals("Semanal")){
							lineCharRelatorio.getData().clear();
							List<HashMap<Integer,String>> lstHash = Relatorio.ListarFaturamento("Semanal");

							XYChart.Series lucro = new XYChart.Series();
							lucro.setName("Faturamento Semanal");
							for(int i = 0; i<lstHash.size(); i++ ){

								double valorfaturado = Double.parseDouble(lstHash.get(i).get(2));
								lucro.getData().add(new XYChart.Data(lstHash.get(i).get(1), valorfaturado));
							}
							lineCharRelatorio.getData().addAll(lucro);
						}
					}else{
						Alerta.showError("Erro ao filtrar", "Selecione um Tipo Per�odo");
					}
				}else if(radioDataPeriodo.isSelected()){
					/*Caso o Radio de data seja selecionado*/

				}else{
					Alerta.showError("Erro ao filtrar", "Selecione um Per�odo");
				}
			}else if(sltComboTipo.equals("Venda")){
				/*Relat�rio de vendas*/

			}else if(sltComboTipo.equals("Pedido")){
				/*Relat�rio de Pedidos*/
				if(radioPeriodo.isSelected()){
					String sltComboPeriodo = cboPeriodoRelat�rio.getSelectionModel().getSelectedItem();
					if(sltComboPeriodo != null){
						if(sltComboPeriodo.equals("Anual")){
							lineCharRelatorio.getData().clear();
							List<HashMap<Integer,String>> lstHash = Relatorio.ListarPedido("Anual");

							XYChart.Series lucro = new XYChart.Series();
							lucro.setName("Pedidos anual");
							for(int i = 0; i<lstHash.size(); i++ ){

								double valorfaturado = Double.parseDouble(lstHash.get(i).get(2));
								lucro.getData().add(new XYChart.Data(lstHash.get(i).get(1), valorfaturado));
							}
							lineCharRelatorio.getData().addAll(lucro);

						}else if(sltComboPeriodo.equals("Mensal")){
							lineCharRelatorio.getData().clear();
							List<HashMap<Integer,String>> lstHash = Relatorio.ListarPedido("Mensal");

							XYChart.Series lucro = new XYChart.Series();
							lucro.setName("Pedidos Mensal");
							for(int i = 0; i<lstHash.size(); i++ ){

								double valorfaturado = Double.parseDouble(lstHash.get(i).get(2));
								lucro.getData().add(new XYChart.Data(lstHash.get(i).get(1), valorfaturado));
							}
							lineCharRelatorio.getData().addAll(lucro);

						}else if(sltComboPeriodo.equals("Semanal")){
							lineCharRelatorio.getData().clear();
							List<HashMap<Integer,String>> lstHash = Relatorio.ListarPedido("Semanal");

							XYChart.Series lucro = new XYChart.Series();
							lucro.setName("Pedidos Semanal");
							for(int i = 0; i<lstHash.size(); i++ ){

								double valorfaturado = Double.parseDouble(lstHash.get(i).get(2));
								lucro.getData().add(new XYChart.Data(lstHash.get(i).get(1), valorfaturado));
							}
							lineCharRelatorio.getData().addAll(lucro);
						}
					}else{
						Alerta.showError("Erro ao filtrar", "Selecione um Tipo Per�odo");
					}
				}else if(radioDataPeriodo.isSelected()){
					/*Caso o Radio de data seja selecionado*/

				}else{
					Alerta.showError("Erro ao filtrar", "Selecione um Per�odo");
				}
			}
		}else{
			Alerta.showError("Erro ao gerar relat�rio", "Selecione o Tipo de Relat�rio que ser� emitido");
		}
	}
}

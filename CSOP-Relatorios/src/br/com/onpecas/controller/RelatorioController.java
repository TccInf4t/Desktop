package br.com.onpecas.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import br.com.onpecas.model.Relatorio;
import javafx.fxml.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;

public class RelatorioController implements Initializable{

	@FXML Button btnFiltrar;
	@FXML ComboBox<String> cboTipoRelatorio, cboPeriodoRelat�rio;

	@FXML LineChart lineCharRelatorio;
	@FXML DatePicker datePickerInicial, datePickerFinal;

	@FXML RadioButton radioPeriodo, radioDataPeriodo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		CarregarBotoes();

	}

	public void CarregarBotoes(){
		cboTipoRelatorio.getItems().addAll("Faturamento");
		cboPeriodoRelat�rio.getItems().addAll("Mensal", "Anual");
		btnFiltrar.setOnAction(l-> Filtrar());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void Filtrar(){
		if(cboTipoRelatorio.getSelectionModel().getSelectedItem().equals("Faturamento")){
			if(cboPeriodoRelat�rio.getSelectionModel().getSelectedItem().equals("Anual")){
				List<HashMap<Integer,String>> lstHash = Relatorio.ListarFaturamento();


				XYChart.Series lucro = new XYChart.Series();
				lucro.setName("Faturamento anual");
				for(int i = 0; i<lstHash.size(); i++ ){

					double valorfaturado = Double.parseDouble(lstHash.get(i).get(2));
					System.out.println("Data: "+lstHash.get(i).get(1)+" Valor: "+lstHash.get(i).get(2));
					lucro.getData().add(new XYChart.Data(lstHash.get(i).get(1), valorfaturado));
					/*lucro.getData().add(new XYChart.Data("Fevereiro", 02));
					lucro.getData().add(new XYChart.Data("Mar�o", 3));
					lucro.getData().add(new XYChart.Data("Abril", 04));
					lucro.getData().add(new XYChart.Data("Maio", 05));
					lucro.getData().add(new XYChart.Data("Junho", 06));
					lucro.getData().add(new XYChart.Data("Julho", 07));
					lucro.getData().add(new XYChart.Data("Agosto", 8));
					lucro.getData().add(new XYChart.Data("Setembro", 9));
					lucro.getData().add(new XYChart.Data("Outubro", 10));
					lucro.getData().add(new XYChart.Data("Novembro",11));
					lucro.getData().add(new XYChart.Data("Dezembro", 12));*/


				}
				lineCharRelatorio.getData().addAll(lucro);
			}else if(cboPeriodoRelat�rio.getSelectionModel().getSelectedItem().equals("Mensal")){
				List<HashMap<Integer,String>> lstHash = Relatorio.ListarFaturamento();

				XYChart.Series lucro = new XYChart.Series();
				lucro.setName("Faturamento anual");
				for(int y=1; y<=12; y++){
					String data = null;
					double valorfaturado = 0;

					for(int i = 0; i<lstHash.size(); i++ ){

						int mes = Integer.parseInt(lstHash.get(i).get(1).substring(5, 7));

						if(mes == y){
							data = lstHash.get(i).get(1);
							valorfaturado+= Double.parseDouble(lstHash.get(i).get(2));
							System.out.println(data.substring(5, 7));

						}
					}
					//double valorfaturado = Double.parseDouble(lstHash.get(i).get(2));
					//System.out.println("Data: "+lstHash.get(i).get(1)+" Valor: "+lstHash.get(i).get(2));
					lucro.getData().add(new XYChart.Data(data, valorfaturado));
					/*lucro.getData().add(new XYChart.Data("Fevereiro", 02));
					lucro.getData().add(new XYChart.Data("Mar�o", 3));
					lucro.getData().add(new XYChart.Data("Abril", 04));
					lucro.getData().add(new XYChart.Data("Maio", 05));
					lucro.getData().add(new XYChart.Data("Junho", 06));
					lucro.getData().add(new XYChart.Data("Julho", 07));
					lucro.getData().add(new XYChart.Data("Agosto", 8));
					lucro.getData().add(new XYChart.Data("Setembro", 9));
					lucro.getData().add(new XYChart.Data("Outubro", 10));
					lucro.getData().add(new XYChart.Data("Novembro",11));
					lucro.getData().add(new XYChart.Data("Dezembro", 12));*/


				}
				lineCharRelatorio.getData().addAll(lucro);
			}
		}
	}
}

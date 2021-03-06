package br.com.onpecas.controller;

import java.net.URL;
import java.util.*;

import br.com.onpecas.helper.*;
import br.com.onpecas.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class RelatorioController implements Initializable{

	@FXML Button btnFiltrar, btnExportarExcel;

	@FXML ComboBox<String> cboTipoRelatorio, cboPeriodoRelat�rio, cboEntidade, cboPeriodoVenda;
	@FXML ComboBox<Estado> cboEstado;
	@FXML ComboBox<Cidade> cboCidade;

	@FXML LineChart<String, String> lineChart;
	@FXML BarChart<String, String> barChart;

	@FXML TableView<Relatorio> table;

	@FXML DatePicker datePickerInicial, datePickerFinal;

	@FXML CheckBox chbDataSelecionavel;

	@FXML Pane panePeriodo, paneVenda, paneRegiao, paneEntidade;

	@FXML Tab tabTabela, tabLinha, tabBarra;
	@FXML TabPane tabPane;

	@FXML TextField txtNomeEntidade;

	List<Relatorio> lstRelatorio;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CarregarBotoes();
		DestivarTodos();
	}

	public void CarregarBotoes(){
		cboTipoRelatorio.getItems().addAll("Faturamento", "Pedido", "Venda");
		btnFiltrar.setOnAction(l-> Filtrar());
		btnExportarExcel.setOnAction(l-> ExportaExcel());
	}

	@FXML
	private void handleEstado() {
		Estado estado = cboEstado.getSelectionModel().getSelectedItem();

		if(estado != null){
			CarregarCidades(estado);
		}
	}

	/*
	 * M�todo para preencher a combobox cboCidade ap�s selecionar um estado
	 * */
	public void CarregarCidades(Estado estado){
		cboCidade.setDisable(false);
		cboCidade.getItems().clear();
		cboCidade.getItems().addAll(Cidade.BuscarCidade(estado));
	}


	/*
	 * Desativa todos os componentes que n�o ser�o usados at� a sele��o de um item no combobox cboTipoRelatorio
	 * */
	public void DestivarTodos(){
		cboPeriodoRelat�rio.setDisable(true);
		datePickerInicial.setDisable(true);
		btnExportarExcel.setDisable(true);
		datePickerFinal.setDisable(true);
		tabTabela.setDisable(true);
		tabLinha.setDisable(true);
		tabBarra.setDisable(true);
		cboCidade.setDisable(true);

		panePeriodo.setVisible(false);
		paneVenda.setVisible(false);
		paneRegiao.setVisible(false);
		paneEntidade.setVisible(false);

		cboCidade.getItems().clear();

		cboEstado.getItems().addAll(Estado.Select());
	}

	//Listener para quando o combobox cboTipoRelatorio for selecionado
	//Para cada item selecionado, � atribuido seus tipo de pesquisa
	@FXML
	private void handleButtonAction(ActionEvent event) {
	    // Button was clicked, do something...
		String sltComboTipo = cboTipoRelatorio.getSelectionModel().getSelectedItem();
		if(sltComboTipo.equals("Faturamento")){
			DestivarTodos();
			panePeriodo.setVisible(true);
			cboPeriodoRelat�rio.setDisable(false);;
			cboPeriodoRelat�rio.setPromptText("Selecione");
			cboPeriodoRelat�rio.getItems().clear();
			cboPeriodoRelat�rio.getItems().addAll("Diario", "Semanal", "Mensal", "Anual");

		}else if(sltComboTipo.equals("Pedido")){
			DestivarTodos();
			FiltrarPorPedido();
			panePeriodo.setVisible(false);
			paneVenda.setVisible(false);
			tabLinha.setDisable(true);
		}else if(sltComboTipo.equals("Venda")){
			DestivarTodos();
			paneVenda.setVisible(true);

			cboEntidade.getItems().clear();
			cboEntidade.getItems().addAll("Cliente", "Produto", "Regi�o");

			cboPeriodoRelat�rio.setDisable(false);;
			cboPeriodoRelat�rio.getItems().clear();
			cboPeriodoRelat�rio.getItems().addAll("Diario", "Semanal", "Mensal", "Anual");
		}
	}

	@FXML
	private void handleCheckBoxAction() {
		if(chbDataSelecionavel.isSelected()){
			datePickerInicial.setDisable(false);
			datePickerFinal.setDisable(false);
		}else{
			datePickerInicial.setDisable(true);
			datePickerFinal.setDisable(true);
		}
	}

	@FXML
	private void handleCboEntidade(){
		String entidade = cboEntidade.getValue();
		datePickerFinal.setValue(null);
		datePickerInicial.setValue(null);
		chbDataSelecionavel.setSelected(false);
		txtNomeEntidade.clear();
		cboCidade.getSelectionModel().clearSelection();
		cboEstado.getSelectionModel().clearSelection();
		if(entidade != null){
			if(entidade.equals("Regi�o")){
				paneRegiao.setVisible(true);
				paneEntidade.setVisible(false);
				panePeriodo.setVisible(true);
			}else {
				paneRegiao.setVisible(false);
				panePeriodo.setVisible(true);
				paneEntidade.setVisible(true);
			}
		}

	}

	public void Filtrar(){
		String sltComboTipo = cboTipoRelatorio.getSelectionModel().getSelectedItem();
		if(sltComboTipo != null){
			if(sltComboTipo.equals("Faturamento")){
				FiltrarPorFaturamento();
			}else if(sltComboTipo.equals("Pedido")){
				FiltrarPorPedido();
			}else if(sltComboTipo.equals("Venda")){
				FiltrarPorVenda();
			}
		}else{
			Alerta.showError("Erro ao gerar relat�rio", "Selecione o Tipo de Relat�rio que ser� emitido");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void FiltrarPorFaturamento(){
		String periodo = cboPeriodoRelat�rio.getSelectionModel().getSelectedItem();
		if(periodo ==  null || periodo.isEmpty()){
			Alerta.showError("Erro ao filtrar", "Selecione um Tipo Per�odo");
		}else{
			if(chbDataSelecionavel.isSelected()){
				if(datePickerInicial.getValue() ==  null && datePickerFinal.getValue() == null){
					Alerta.showError("Erro ao filtrar", "Preencha a Data Inicial (De) e a Data Final (At�)");
				}else{
					String dataInicial = datePickerInicial.getValue().toString();
					String dataFinal = datePickerFinal.getValue().toString();

					if(dataFinal.isEmpty() || dataInicial.isEmpty()){
						Alerta.showError("Erro ao filtrar", "Preencha a Data Inicial (De) e a Data Final (At�)");
					}else{

						List<Relatorio> lstRelatorio = Relatorio.FiltarFaturamento(periodo, true, dataInicial, dataFinal);

						if(lstRelatorio.size()<=0){
							Alerta.showError("Erro ao gerar", "N�o h� registro neste per�odo");
						}else{

							tabTabela.setDisable(false);
							tabLinha.setDisable(false);
							tabBarra.setDisable(false);
							btnExportarExcel.setDisable(false);

							lineChart.getData().clear();
							barChart.getData().clear();

							XYChart.Series lucroLine = new XYChart.Series();
							XYChart.Series lucroBar = new XYChart.Series();

							lucroBar.setName(cboTipoRelatorio.getValue().toString()+" - "+ cboPeriodoRelat�rio.getValue().toString());
							lucroLine.setName(cboTipoRelatorio.getValue().toString()+" - "+ cboPeriodoRelat�rio.getValue().toString());

							for(Relatorio item:lstRelatorio){
								double valorfaturado = Double.parseDouble(item.getValorQuantidade());
								lucroBar.getData().add(new XYChart.Data(item.getTituloData(), valorfaturado));
							}

							barChart.getData().addAll(lucroBar);
							lineChart.getData().addAll(lucroLine);

							this.lstRelatorio = lstRelatorio;
						}
					}
				}
			}else{
				List<Relatorio> lstRelatorio = Relatorio.FiltarFaturamento(periodo, false, null, null);

				tabTabela.setDisable(false);
				tabLinha.setDisable(false);
				tabBarra.setDisable(false);
				btnExportarExcel.setDisable(false);

				lineChart.getData().clear();
				barChart.getData().clear();

				XYChart.Series lucroLine = new XYChart.Series();
				XYChart.Series lucroBar = new XYChart.Series();

				lucroBar.setName(cboTipoRelatorio.getValue().toString()+" - "+ cboPeriodoRelat�rio.getValue().toString());
				lucroLine.setName(cboTipoRelatorio.getValue().toString()+" - "+ cboPeriodoRelat�rio.getValue().toString());

				for(Relatorio item:lstRelatorio){
					double valorfaturado = Double.parseDouble(item.getValorQuantidade());
					lucroBar.getData().add(new XYChart.Data(item.getTituloData(), valorfaturado));
					lucroLine.getData().add(new XYChart.Data(item.getTituloData(), valorfaturado));
				}

				barChart.getData().addAll(lucroBar);
				lineChart.getData().addAll(lucroLine);
				this.lstRelatorio = lstRelatorio;
				GerarTable(1, lstRelatorio);
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void FiltrarPorPedido(){
		panePeriodo.setVisible(false);
		tabTabela.setDisable(false);
		tabBarra.setDisable(false);
		btnExportarExcel.setDisable(false);
		tabPane.getSelectionModel().select(tabBarra);

		//List<HashMap<Integer,String>> lstHash = Relatorio.FiltrarPedido();

		List<Relatorio> lstRelatorio = Relatorio.FiltrarPedido();

		barChart.getData().clear();

		XYChart.Series lucroBar = new XYChart.Series();
		lucroBar.setName("Quantidade de Pedido por Status");

		for(Relatorio item:lstRelatorio){

			int quantidade =Integer.parseInt(item.getValorQuantidade());
			lucroBar.getData().add(new XYChart.Data(item.getTituloData(), quantidade));
		}

		/*for(int i = 0; i<lstRelatorio.size(); i++ ){

			String status =lstHash.get(i).get(1).toString();
			int quantidade =Integer.parseInt(lstHash.get(i).get(2).toString());

			lucroBar.getData().add(new XYChart.Data(status, quantidade));
		}*/
		GerarTable(2, lstRelatorio);
		barChart.getData().addAll(lucroBar);
		//this.lstHash = lstHash;
		this.lstRelatorio = lstRelatorio;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void FiltrarPorVenda(){

		String entidadeSelecionada = cboEntidade.getSelectionModel().getSelectedItem();
		if(entidadeSelecionada == null){
			Alerta.showError("Erro ao filtrar", "Selecione uma Entidade");
		}else if(entidadeSelecionada.equals("Regi�o")){

			Estado estado = cboEstado.getValue();
			Cidade cidade = cboCidade.getValue();

			if(estado != null){
				if(cidade != null){
					String periodo = cboPeriodoRelat�rio.getSelectionModel().getSelectedItem();
					if(periodo == null){
						Alerta.showError("Erro ao filtrar", "Selecione um Periodo");
					}else{
						if(chbDataSelecionavel.isSelected()){
							if(datePickerInicial.getValue() ==  null || datePickerFinal.getValue() == null){
								Alerta.showError("Erro ao filtrar", "Preencha a Data Inicial (De) e a Data Final (At�)");
							}else{
								String dataInicial = datePickerInicial.getValue().toString();
								String dataFinal = datePickerFinal.getValue().toString();

								if(dataFinal.isEmpty() || dataInicial.isEmpty()){
									Alerta.showError("Erro ao filtrar", "Preencha a Data Inicial (De) e a Data Final (At�)");
								}else{
									// Se tiver a cidade, estado, periodo, data inicial e a data final

									List<Relatorio> lstRelatorio = Relatorio.FiltrarVenda(entidadeSelecionada, null, estado.getOid_estado(), true, cidade.getOid_cidade(), periodo, true, dataInicial, dataFinal);

									tabTabela.setDisable(false);
									tabLinha.setDisable(false);
									tabBarra.setDisable(false);
									btnExportarExcel.setDisable(false);

									lineChart.getData().clear();
									barChart.getData().clear();

									XYChart.Series lucroLine = new XYChart.Series();
									XYChart.Series lucroBar = new XYChart.Series();

									lucroBar.setName("Venda "+cboPeriodoRelat�rio.getValue()+" - "+cboEntidade.getValue()+" De: "+dataInicial+" At�: "+dataFinal);
									lucroLine.setName("Venda "+cboPeriodoRelat�rio.getValue()+" - "+cboEntidade.getValue()+" De: "+dataInicial+" At�: "+dataFinal);

									for(Relatorio item:lstRelatorio){
										double valorfaturado = Double.parseDouble(item.getValorQuantidade());
										lucroBar.getData().add(new XYChart.Data(item.getTituloData(), valorfaturado));
										lucroLine.getData().add(new XYChart.Data(item.getTituloData(), valorfaturado));
									}

									barChart.getData().addAll(lucroBar);
									lineChart.getData().addAll(lucroLine);
									this.lstRelatorio = lstRelatorio;
									GerarTable(3, lstRelatorio);
								}
							}
						}else{
							// Se tiver a cidade, estado e periodo

							List<Relatorio> lstRelatorio = Relatorio.FiltrarVenda(entidadeSelecionada, null, estado.getOid_estado(), true, cidade.getOid_cidade(), periodo, false, null, null);

							tabTabela.setDisable(false);
							tabLinha.setDisable(false);
							tabBarra.setDisable(false);
							btnExportarExcel.setDisable(false);

							lineChart.getData().clear();
							barChart.getData().clear();

							XYChart.Series lucroLine = new XYChart.Series();
							XYChart.Series lucroBar = new XYChart.Series();

							lucroBar.setName("Venda "+cboPeriodoRelat�rio.getValue()+" - "+cboEntidade.getValue());
							lucroLine.setName("Venda "+cboPeriodoRelat�rio.getValue()+" - "+cboEntidade.getValue());

							for(Relatorio item:lstRelatorio){
								double valorfaturado = Double.parseDouble(item.getValorQuantidade());
								lucroBar.getData().add(new XYChart.Data(item.getTituloData(), valorfaturado));
								lucroLine.getData().add(new XYChart.Data(item.getTituloData(), valorfaturado));
							}

							barChart.getData().addAll(lucroBar);
							lineChart.getData().addAll(lucroLine);
							this.lstRelatorio = lstRelatorio;
							GerarTable(3, lstRelatorio);
						}
					}
				}else{
					String periodo = cboPeriodoRelat�rio.getSelectionModel().getSelectedItem();
					if(periodo == null){
						Alerta.showError("Erro ao filtrar", "Selecione um Periodo");
					}else{
						if(chbDataSelecionavel.isSelected()){
							if(datePickerInicial.getValue() ==  null || datePickerFinal.getValue() == null){
								Alerta.showError("Erro ao filtrar", "Preencha a Data Inicial (De) e a Data Final (At�)");
							}else{
								String dataInicial = datePickerInicial.getValue().toString();
								String dataFinal = datePickerFinal.getValue().toString();

								if(dataFinal.isEmpty() || dataInicial.isEmpty()){
									Alerta.showError("Erro ao filtrar", "Preencha a Data Inicial (De) e a Data Final (At�)");
								}else{
									// Se tiver a cidade, estado, periodo, data inicial e a data final

									List<Relatorio> lstRelatorio = Relatorio.FiltrarVenda(entidadeSelecionada, null, estado.getOid_estado(), false, 0, periodo, false, dataInicial, dataFinal);

									tabTabela.setDisable(false);
									tabLinha.setDisable(false);
									tabBarra.setDisable(false);
									btnExportarExcel.setDisable(false);

									lineChart.getData().clear();
									barChart.getData().clear();

									XYChart.Series lucroLine = new XYChart.Series();
									XYChart.Series lucroBar = new XYChart.Series();

									lucroBar.setName("Venda "+cboPeriodoRelat�rio.getValue()+" - "+cboEntidade.getValue()+" De: "+dataInicial+" At�: "+dataFinal);
									lucroLine.setName("Venda "+cboPeriodoRelat�rio.getValue()+" - "+cboEntidade.getValue()+" De: "+dataInicial+" At�: "+dataFinal);

									for(Relatorio item:lstRelatorio){
										double valorfaturado = Double.parseDouble(item.getValorQuantidade());
										lucroBar.getData().add(new XYChart.Data(item.getTituloData(), valorfaturado));
										lucroLine.getData().add(new XYChart.Data(item.getTituloData(), valorfaturado));
									}

									barChart.getData().addAll(lucroBar);
									lineChart.getData().addAll(lucroLine);
									this.lstRelatorio = lstRelatorio;
									GerarTable(3, lstRelatorio);
								}
							}
						}else{
							// Se tiver a cidade, estado e periodo

							List<Relatorio> lstRelatorio = Relatorio.FiltrarVenda(entidadeSelecionada, null, estado.getOid_estado(), false, 0, periodo, false, null, null);

							tabTabela.setDisable(false);
							tabLinha.setDisable(false);
							tabBarra.setDisable(false);
							btnExportarExcel.setDisable(false);

							lineChart.getData().clear();
							barChart.getData().clear();

							XYChart.Series lucroLine = new XYChart.Series();
							XYChart.Series lucroBar = new XYChart.Series();

							lucroBar.setName("Venda "+cboPeriodoRelat�rio.getValue()+" - "+cboEntidade.getValue());
							lucroLine.setName("Venda "+cboPeriodoRelat�rio.getValue()+" - "+cboEntidade.getValue());

							for(Relatorio item:lstRelatorio){
								double valorfaturado = Double.parseDouble(item.getValorQuantidade());
								lucroBar.getData().add(new XYChart.Data(item.getTituloData(), valorfaturado));
								lucroLine.getData().add(new XYChart.Data(item.getTituloData(), valorfaturado));
							}

							barChart.getData().addAll(lucroBar);
							lineChart.getData().addAll(lucroLine);
							this.lstRelatorio = lstRelatorio;
							GerarTable(3, lstRelatorio);
						}
					}
				}
			}else{
				Alerta.showError("Erro ao filtrar", "Selecione uma Regi�o");
			}
		}else if(!entidadeSelecionada.equals("Regi�o")){
			String nomeEntidade = txtNomeEntidade.getText();

			if(nomeEntidade.isEmpty()){
				Alerta.showError("Erro ao filtrar", "Selecione preecha o nome da entidade");
			}else{
				String periodo = cboPeriodoRelat�rio.getSelectionModel().getSelectedItem();
				if(periodo == null){
					Alerta.showError("Erro ao filtrar", "Selecione um Periodo");
				}else{
					if(chbDataSelecionavel.isSelected()){
						if(datePickerInicial.getValue() ==  null || datePickerFinal.getValue() == null){
							Alerta.showError("Erro ao filtrar", "Preencha a Data Inicial (De) e a Data Final (At�)");
						}else{
							String dataInicial = datePickerInicial.getValue().toString();
							String dataFinal = datePickerFinal.getValue().toString();

							if(dataFinal.isEmpty() || dataInicial.isEmpty()){
								Alerta.showError("Erro ao filtrar", "Preencha a Data Inicial (De) e a Data Final (At�)");
							}else{
								// Se tiver entidade, nomeentidade,  periodo, data inicial e a data final

								List<Relatorio> lstRelatorio = Relatorio.FiltrarVenda(entidadeSelecionada, nomeEntidade, 0, false, 0, periodo, true, dataInicial, dataFinal);

								tabTabela.setDisable(false);
								tabLinha.setDisable(false);
								tabBarra.setDisable(false);
								btnExportarExcel.setDisable(false);

								lineChart.getData().clear();
								barChart.getData().clear();

								XYChart.Series lucroLine = new XYChart.Series();
								XYChart.Series lucroBar = new XYChart.Series();

								lucroBar.setName("Venda "+cboPeriodoRelat�rio.getValue()+" - "+cboEntidade.getValue()+" De: "+dataInicial+" At�: "+dataFinal);
								lucroLine.setName("Venda "+cboPeriodoRelat�rio.getValue()+" - "+cboEntidade.getValue()+" De: "+dataInicial+" At�: "+dataFinal);

								for(Relatorio item:lstRelatorio){
									double valorfaturado = Double.parseDouble(item.getValorQuantidade());
									lucroBar.getData().add(new XYChart.Data(item.getTituloData(), valorfaturado));
									lucroLine.getData().add(new XYChart.Data(item.getTituloData(), valorfaturado));
								}

								barChart.getData().addAll(lucroBar);
								lineChart.getData().addAll(lucroLine);
								this.lstRelatorio = lstRelatorio;
								GerarTable(3, lstRelatorio);
							}
						}
					}else{
						// Se tiver entidade, nomeentidade e periodo
						List<Relatorio> lstRelatorio = Relatorio.FiltrarVenda(entidadeSelecionada, nomeEntidade, 0, false, 0, periodo, false, null, null);

						tabTabela.setDisable(false);
						tabLinha.setDisable(false);
						tabBarra.setDisable(false);
						btnExportarExcel.setDisable(false);

						lineChart.getData().clear();
						barChart.getData().clear();

						XYChart.Series lucroLine = new XYChart.Series();
						XYChart.Series lucroBar = new XYChart.Series();

						lucroBar.setName("Venda "+cboPeriodoRelat�rio.getValue()+" - "+cboEntidade.getValue());
						lucroLine.setName("Venda "+cboPeriodoRelat�rio.getValue()+" - "+cboEntidade.getValue());

						for(Relatorio item:lstRelatorio){
							double valorfaturado = Double.parseDouble(item.getValorQuantidade());
							lucroBar.getData().add(new XYChart.Data(item.getTituloData(), valorfaturado));
							lucroLine.getData().add(new XYChart.Data(item.getTituloData(), valorfaturado));
						}

						barChart.getData().addAll(lucroBar);
						lineChart.getData().addAll(lucroLine);
						this.lstRelatorio = lstRelatorio;
						GerarTable(3, lstRelatorio);
					}
				}
			}
		}
	}

	public void ExportaExcel(){
		String sltComboTipo = cboTipoRelatorio.getSelectionModel().getSelectedItem();
		if(lstRelatorio!=null){
			if(sltComboTipo != null){
				if(sltComboTipo.equals("Faturamento")){
					if(this.lstRelatorio != null){
						CreateExlFile.Gerar(lstRelatorio, "Faturamento", cboPeriodoRelat�rio.getValue());
					}else{
						Alerta.showError("Erro ao exportar", "Nenhum relat�rio realizado");
					}
				}else if(sltComboTipo.equals("Pedido")){
					if(this.lstRelatorio != null){
						CreateExlFile.Gerar(lstRelatorio, "Pedido", cboPeriodoRelat�rio.getValue());
					}else{
						Alerta.showError("Erro ao exportar", "Nenhum relat�rio realizado");
					}
				}else if(sltComboTipo.equals("Venda")){
					if(this.lstRelatorio != null){
						CreateExlFile.Gerar(lstRelatorio, "Venda", cboPeriodoRelat�rio.getValue());
					}else{
						Alerta.showError("Erro ao exportar", "Nenhum relat�rio realizado");
					}
				}
			}else{
				Alerta.showError("Erro ao exportar", "Nenhum relat�rio realizado");
			}
		}else{
			Alerta.showError("Erro ao exportar", "Nenhum relat�rio realizado");
		}
	}

	@SuppressWarnings("unchecked")
	public void GerarTable(int tipo, List<Relatorio> lstRelatorio){
		table.getItems().clear();
		table.getColumns().clear();
		if(tipo == 1){
			TableColumn<Relatorio, String> coluna1 = new TableColumn<>("Data Referencia");
			TableColumn<Relatorio, String> coluna2 = new TableColumn<>("Valor Total");

			coluna1.setResizable(true);
			coluna2.setResizable(true);
			coluna1.setCellValueFactory(new PropertyValueFactory<Relatorio, String>("tituloData"));
			coluna2.setCellValueFactory(new PropertyValueFactory<Relatorio, String>("valorQuantidade"));

			table.getColumns().addAll(coluna1, coluna2);

			ObservableList<Relatorio> data = FXCollections.observableList(lstRelatorio);

			table.setItems(data);
		}else if(tipo == 2){
			TableColumn<Relatorio, String> coluna1 = new TableColumn<>("Status");
			TableColumn<Relatorio, String> coluna2 = new TableColumn<>("Quantidade");

			coluna1.setResizable(true);
			coluna2.setResizable(true);
			coluna1.setCellValueFactory(new PropertyValueFactory<Relatorio, String>("tituloData"));
			coluna2.setCellValueFactory(new PropertyValueFactory<Relatorio, String>("valorQuantidade"));

			table.getColumns().addAll(coluna1, coluna2);

			ObservableList<Relatorio> data = FXCollections.observableList(lstRelatorio);

			table.setItems(data);

		}else if(tipo == 3){
			TableColumn<Relatorio, String> coluna1 = new TableColumn<>("Data Referencia");
			TableColumn<Relatorio, String> coluna2 = new TableColumn<>("Valor Total");
			TableColumn<Relatorio, String> coluna3 = new TableColumn<>("Quantidade");

			coluna1.setResizable(true);
			coluna2.setResizable(true);
			coluna3.setResizable(true);
			coluna1.setCellValueFactory(new PropertyValueFactory<Relatorio, String>("tituloData"));
			coluna2.setCellValueFactory(new PropertyValueFactory<Relatorio, String>("valorQuantidade"));
			coluna3.setCellValueFactory(new PropertyValueFactory<Relatorio, String>("quantidadeVenda"));

			table.getColumns().addAll(coluna1, coluna2, coluna3);

			ObservableList<Relatorio> data = FXCollections.observableList(lstRelatorio);

			table.setItems(data);
		}

	}
}

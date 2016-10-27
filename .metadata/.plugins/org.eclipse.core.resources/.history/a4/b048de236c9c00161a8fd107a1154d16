package br.com.onpecas.controller;

import java.net.URL;
import java.util.*;

import br.com.onpecas.helper.Alerta;
import br.com.onpecas.helper.Mascaras;
import br.com.onpecas.model.*;
import br.com.onpecas.view.CallScene;
import javafx.fxml.*;
import javafx.scene.control.*;

public class IniciarTransporteController implements Initializable{

	@FXML TextField txtValorFrete;
	@FXML ComboBox<Lote> cboNumLote;
	@FXML ComboBox<Transportadora> cboNomeTransp;
	@FXML DatePicker dtpDataPrevSaida, dtpDataPrevChegada;
	@FXML Button btnIniciar, btnCancelar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ConfigurarBotoes();

	}

	public void ConfigurarBotoes(){
		cboNumLote.getItems().addAll(Lote.BuscarLoteSemTransporte());
		cboNomeTransp.getItems().addAll(Transportadora.BuscarSemTransporte());

		btnCancelar.setOnAction(l-> CallScene.secondStage.close());
		btnIniciar.setOnAction(l-> IniciarTransporte());
		Mascaras.mascaraNumero(txtValorFrete);
	}

	public void IniciarTransporte (){
		Lote loteSelecionado  = cboNumLote.getSelectionModel().getSelectedItem();
		Transportadora transpSelecionado  = cboNomeTransp.getSelectionModel().getSelectedItem();

		if(loteSelecionado != null && transpSelecionado != null){
			double valorFrete = Double.parseDouble(txtValorFrete.getText());

			if(valorFrete != 0){
				Pedido.MudarStatusPedido(loteSelecionado.getLstPedido(), 4);
				
				loteSelecionado.setFrete(""+valorFrete);
				loteSelecionado.setTransportadora(transpSelecionado);
				loteSelecionado.setData_entrega(dtpDataPrevChegada.getValue().toString());
				loteSelecionado.setData_saida(dtpDataPrevSaida.getValue().toString());
				
				Lote.IniciarTransporte(loteSelecionado);
				
			}else{
				Alerta.showError("Não foi possivel Iniciar", "É preciso atribuir um frete");
			}
		}else{
			Alerta.showError("Não foi possivel Iniciar", "É preciso selecionar um Lote e uma Transpotadora");
		}
	}
}

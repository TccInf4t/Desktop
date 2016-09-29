package br.com.onpecas.controller;

import java.net.URL;
import java.util.ResourceBundle;
import br.com.onpecas.model.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class EnderecoController implements Initializable{

	@FXML Button btnCancelar, btnConfirmar;
	@FXML TextField txtLogradouro, txtBairro, txtNumero, txtCEP;
	@FXML ComboBox<Cidade> cboCidade;
	@FXML ComboBox<Estado> cboEstado;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		AtribuirBotoes();

	}

	@FXML
	private void handleComboBoxAction() {
		Estado estado = cboEstado.getSelectionModel().getSelectedItem();

		if(estado != null){
			cboCidade.getItems().clear();
			cboCidade.getItems().addAll(Cidade.BuscarCidade(estado));
		}
	}
	public void AtribuirBotoes(){
		cboEstado.getItems().addAll(Estado.Select());
		btnConfirmar.setOnAction(l-> Adicionar());
	}

	private void Adicionar() {

	}
}

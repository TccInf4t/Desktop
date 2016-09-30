package br.com.onpecas.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.onpecas.helper.Alerta;
import br.com.onpecas.helper.Helper;
import br.com.onpecas.helper.Mascaras;
import br.com.onpecas.model.*;
import br.com.onpecas.view.CallScene;
import javafx.fxml.*;
import javafx.scene.control.*;

public class EnderecoController implements Initializable{

	@FXML Button btnCancelar, btnConfirmar;
	@FXML TextField txtLogradouro, txtBairro, txtNumero, txtCEP, txtComplemento;
	@FXML ComboBox<Cidade> cboCidade;
	@FXML ComboBox<Estado> cboEstado;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		AtribuirBotoes();
		Mascaras.mascaraCEP(txtCEP);
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
		btnCancelar.setOnAction(l-> CallScene.thirdStage.close());
	}

	private void Adicionar() {
		if(cboCidade.getSelectionModel().getSelectedItem() == null){
			Alerta.showError("Erro no cadastro", "Selecione um estado e uma cidade");
		}else{
			Endereco endereco = new Endereco();

			endereco.setLogradouro(txtLogradouro.getText());
			endereco.setBairro(txtBairro.getText());
			endereco.setNumero(txtNumero.getText());
			endereco.setCep(txtCEP.getText());
			endereco.setClassname("TEnderecoTransportadora");
			endereco.setComplemento(txtComplemento.getText());

			endereco.setCidade(cboCidade.getSelectionModel().getSelectedItem());

			String enderecoCompleto = String.format("%s, n� %s, %s, %s, %s %s - %s", endereco.getLogradouro(), endereco.getNumero(),
					endereco.getComplemento() , endereco.getBairro(), endereco.getCep(), endereco.getCidade().getNome(), endereco.getCidade().getEstado().getUF());

			endereco.setEnderecoCompleto(enderecoCompleto);

			Endereco.Insert(endereco);

			Helper.ENDERECO_GERADO = Endereco.BuscarUltimaEndereco();
			Helper.AUXENDERECOCOMPLETO.setValue(1);
			CallScene.thirdStage.close();
		}
	}
}
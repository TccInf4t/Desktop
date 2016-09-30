package br.com.onpecas.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.onpecas.helper.*;
import br.com.onpecas.model.*;
import br.com.onpecas.view.CallScene;
import javafx.beans.value.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class TransportadoraCadastroDetalheController implements Initializable{

	@FXML Button btnAbrirEndereco, btnCadastrar, btnCancelar;

	@FXML TextField txtNomeCompleto, txtCPF, txtRG, txtTelefone, txtFrete, txtRamo;

	@FXML TextArea txtObservacoes;

	@FXML Label lblEnderecoCompleto;

	@FXML ComboBox<String> cboTipoPessoa;

	Transportadora transportadora;

	CallScene callscene;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		callscene = new CallScene();
		AtribuirBotoes();

		if(transportadora != null){
			btnCadastrar.setText("ATUALIZAR");
		}else{
			btnCadastrar.setText("CADASTRAR");
		}
		cboTipoPessoa.getItems().add("F�SICA");
		cboTipoPessoa.getItems().add("JUR�DICA");

		 Helper.AUXENDERECOCOMPLETO.addListener(new ChangeListener<Object>() {
		     @Override
		     public void changed(ObservableValue<?> observableValue, Object oldValue,
		         Object newValue) {
		         int newValuenovo =Integer.parseInt(newValue.toString());
		         if(newValuenovo == 1){
		             lblEnderecoCompleto.setText(Helper.ENDERECO_GERADO.getEnderecoCompleto());
		             Helper.AUXENDERECOCOMPLETO.setValue(0);
		         }else{}
		     }
		   });
	}

	public void AtribuirBotoes(){
		btnAbrirEndereco.setOnAction(l-> AdicionarEndereco());
		btnCadastrar.setOnAction(l-> AdicionarTransportadora());
		btnCancelar.setOnAction(l-> Cancelar());

		txtCPF.setEditable(false);
		txtRG.setEditable(false);
	}

	@FXML
	private void handleComboBoxAction() {

		if(cboTipoPessoa.getSelectionModel().getSelectedItem().equals("F�SICA")){
			Mascaras.mascaraCPF(txtCPF);
			txtCPF.setEditable(true);
			txtRG.setEditable(true);
		}else if (cboTipoPessoa.getSelectionModel().getSelectedItem().equals("JUR�DICA")){
			Mascaras.mascaraCNPJ(txtCPF);
			txtCPF.setEditable(true);
			txtRG.setEditable(false);
		}
	}

	public TransportadoraCadastroDetalheController(Transportadora transportadora) {
		this.transportadora = transportadora;
	}

	public TransportadoraCadastroDetalheController() {

	}

	private void Cancelar() {
		CallScene.secondStage.close();
	}

	public void AdicionarEndereco(){
		callscene.LoadEndereco();
	}

	public void AdicionarTransportadora(){
		if(btnCadastrar.getText().equals("CADASTRAR")){
			Transportadora transportadora_gerada = new Transportadora();

			Endereco endereco = Helper.ENDERECO_GERADO;

			transportadora_gerada.setEndereco(endereco);

			transportadora_gerada.setCnpj(txtCPF.getText());
			transportadora_gerada.setFrete(txtFrete.getText());
			transportadora_gerada.setObservacoes(txtObservacoes.getText());
			transportadora_gerada.setRamo(txtRamo.getText());
			transportadora_gerada.setNome(txtNomeCompleto.getText());

			Transportadora.Insert(transportadora_gerada);
			Helper.AUXTRANSPORTADORA.setValue(1);
			CallScene.secondStage.close();

		}else if(btnCadastrar.getText().equals("ATUALIZAR")){
			Transportadora transportadora_editada = new Transportadora();

			transportadora_editada.setEndereco(transportadora.getEndereco());
			transportadora_editada.setOid_transportadora(transportadora.getOid_transportadora());

			transportadora_editada.setCnpj(txtCPF.getText());
			transportadora_editada.setFrete(txtFrete.getText());
			transportadora_editada.setObservacoes(txtObservacoes.getText());
			transportadora_editada.setRamo(txtRamo.getText());
			transportadora_editada.setNome(txtNomeCompleto.getText());

			Transportadora.Update(transportadora_editada);
			Helper.AUXTRANSPORTADORA.setValue(1);
			CallScene.secondStage.close();
		}
	}
}
package br.com.onpecas.controller;

import java.net.URL;
import java.text.NumberFormat;
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

	Endereco endereco;

	NumberFormat format;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		callscene = new CallScene();
		format = NumberFormat.getCurrencyInstance();

		AtribuirBotoes();

		cboTipoPessoa.getItems().add("F�SICA");
		cboTipoPessoa.getItems().add("JUR�DICA");

		if(transportadora != null){
			btnCadastrar.setText("ATUALIZAR");

			txtNomeCompleto.setText(transportadora.getNome());
			txtFrete.setText(transportadora.getFrete().substring(3).replace(".", "").replace(",", "."));
			txtRamo.setText(transportadora.getRamo());
			txtObservacoes.setText(transportadora.getObservacoes());
			txtTelefone.setText(transportadora.getTelefone());
			lblEnderecoCompleto.setText(transportadora.getEndereco().getEnderecoCompleto());

			if(transportadora.getNatureza().equals("F")){
				cboTipoPessoa.setValue("F�SICA");
				Mascaras.mascaraCPF(txtCPF);
				txtCPF.setEditable(true);
				txtRG.setEditable(true);
				txtCPF.setText(transportadora.getCnpj());
				txtRG.setText(transportadora.getRg());
			}else if(transportadora.getNatureza().equals("J")){
				cboTipoPessoa.setValue("JUR�DICA");
				Mascaras.mascaraCNPJ(txtCPF);
				txtCPF.setEditable(true);
				txtRG.setEditable(false);
				txtCPF.setText(transportadora.getCnpj());
			}

			Helper.ENDERECO_GERADO = transportadora.getEndereco();
			Helper.AUXENDERECOCOMPLETO.setValue(1);
			endereco = transportadora.getEndereco();
		}else{
			btnCadastrar.setText("SALVAR");
		}

		 Helper.AUXENDERECOCOMPLETO.addListener(new ChangeListener<Object>() {
		     @Override
		     public void changed(ObservableValue<?> observableValue, Object oldValue,
		         Object newValue) {
		         int newValuenovo =Integer.parseInt(newValue.toString());
		         if(newValuenovo == 1){
		             lblEnderecoCompleto.setText(Helper.ENDERECO_GERADO.getEnderecoCompleto());
		             endereco = Helper.ENDERECO_GERADO;
		         }
		     }
		   });
	}

	public TransportadoraCadastroDetalheController(Transportadora transportadora) {
		this.transportadora = transportadora;
	}

	public TransportadoraCadastroDetalheController() {}

	public void AtribuirBotoes(){
		btnAbrirEndereco.setOnAction(l-> AdicionarEndereco());
		btnCadastrar.setOnAction(l-> AdicionarTransportadora());
		btnCancelar.setOnAction(l-> Cancelar());

		txtCPF.setEditable(false);
		txtRG.setEditable(false);

		Mascaras.mascaraNumero(txtFrete);
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

	private void Cancelar() {
		if(btnCadastrar.getText().equals("ATUALIZAR")){
			CallScene.secondStage.close();
		}else if (btnCadastrar.getText().equals("SALVAR")){
			if(Helper.ENDERECO_GERADO != null){
				Endereco.Delete(Helper.ENDERECO_GERADO.getOid_endereco());
			}
			CallScene.secondStage.close();
		}
	}

	public void AdicionarEndereco(){
		if(Helper.AUXENDERECOCOMPLETO.getValue() == 1){
			callscene.LoadEndereco(endereco);
		}else{
			callscene.LoadEndereco(null);
		}
	}

	public void AdicionarTransportadora(){
		if(btnCadastrar.getText().equals("SALVAR")){
			Transportadora transportadora_gerada = new Transportadora();

			transportadora_gerada.setEndereco(endereco);
			transportadora_gerada.setCnpj(txtCPF.getText());
			transportadora_gerada.setFrete(format.format(Double.parseDouble(txtFrete.getText())));
			transportadora_gerada.setObservacoes(txtObservacoes.getText());
			transportadora_gerada.setRamo(txtRamo.getText());
			transportadora_gerada.setNome(txtNomeCompleto.getText());
			transportadora_gerada.setTelefone(txtTelefone.getText());

			if(cboTipoPessoa.getSelectionModel().getSelectedItem().equals("F�SICA")){
				transportadora_gerada.setNatureza("F");
				transportadora_gerada.setRg(txtRG.getText());
			}else if(cboTipoPessoa.getSelectionModel().getSelectedItem().equals("JUR�DICA")){
				transportadora_gerada.setNatureza("J");
			}else{}

			transportadora_gerada.setCidade(endereco.getCidade().getNome());
			transportadora_gerada.setEstado(endereco.getCidade().getEstado().getNome());

			Transportadora.Insert(transportadora_gerada);
			Helper.AUXTRANSPORTADORA.setValue(1);
			Helper.AUXENDERECOCOMPLETO.setValue(0);
			Helper.ENDERECO_GERADO = null;
			CallScene.secondStage.close();

		}else if(btnCadastrar.getText().equals("ATUALIZAR")){
			Transportadora transportadora_editada = new Transportadora();

			transportadora_editada.setOid_transportadora(transportadora.getOid_transportadora());
			transportadora_editada.setEndereco(endereco);
			transportadora_editada.setCnpj(txtCPF.getText());
			transportadora_editada.setFrete(format.format(Double.parseDouble(txtFrete.getText())));
			transportadora_editada.setObservacoes(txtObservacoes.getText());
			transportadora_editada.setRamo(txtRamo.getText());
			transportadora_editada.setNome(txtNomeCompleto.getText());
			transportadora_editada.setTelefone(txtTelefone.getText());

			if(cboTipoPessoa.getSelectionModel().getSelectedItem().equals("F�SICA")){
				transportadora_editada.setNatureza("F");
				transportadora_editada.setRg(txtRG.getText());
			}else if(cboTipoPessoa.getSelectionModel().getSelectedItem().equals("JUR�DICA")){
				transportadora_editada.setNatureza("J");
			}

			transportadora_editada.setCidade(endereco.getCidade().getNome());
			transportadora_editada.setEstado(endereco.getCidade().getEstado().getNome());

			Transportadora.Update(transportadora_editada);
			Helper.AUXTRANSPORTADORA.setValue(1);
			Helper.AUXENDERECOCOMPLETO.setValue(0);
			Helper.ENDERECO_GERADO = null;
			CallScene.secondStage.close();
		}
	}
}

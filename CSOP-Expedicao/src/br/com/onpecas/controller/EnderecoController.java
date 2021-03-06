package br.com.onpecas.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.onpecas.helper.*;
import br.com.onpecas.model.*;
import br.com.onpecas.view.CallScene;
import javafx.fxml.*;
import javafx.scene.control.*;

public class EnderecoController implements Initializable{

	@FXML Button btnCancelar, btnConfirmar;
	@FXML TextField txtLogradouro, txtBairro, txtNumero, txtCEP, txtComplemento;
	@FXML ComboBox<Cidade> cboCidade;
	@FXML ComboBox<Estado> cboEstado;

	Endereco endereco;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		AtribuirBotoes();
		Mascaras.mascaraCEP(txtCEP);

		/*
		 * L�gica usada para separar a inser�ao de dados, da edi��o de dados
		 * Ao instanciar a classe EnderecoController
		 * ter� a op��o de passar um objeto do tipo Endereco para ser editado
		 * ou passar um objeto nulo, que significa que ser� uma inser��o de dados
		 * */
		if(endereco == null){
			btnConfirmar.setText("SALVAR");
		}else{
			btnConfirmar.setText("ATUALIZAR");

			txtLogradouro.setText(endereco.getLogradouro());
			txtBairro.setText(endereco.getBairro());
			txtNumero.setText(endereco.getNumero());
			txtCEP.setText(endereco.getCep());
			txtComplemento.setText(endereco.getComplemento());
			cboEstado.setValue(endereco.getCidade().getEstado());
			CarregarCidades(endereco.getCidade().getEstado());
			cboCidade.setValue(endereco.getCidade());
		}
	}

	public EnderecoController(Endereco endereco) {
		this.endereco = endereco;
	}

	/*
	 * M�todo que ir� ser ativado quando selecionar um item da combobox
	 * No caso, ao selecionar um estado na combobox cboEstado
	 * esse m�todo ir� carregar a combobox cboCidade
	 * */
	@FXML
	private void handleComboBoxAction() {
		Estado estado = cboEstado.getSelectionModel().getSelectedItem();

		if(estado != null){
			CarregarCidades(estado);
		}
	}

	/*
	 * M�todo para preencher a combobox cboCidade ap�s selecionar um estado
	 * */
	public void CarregarCidades(Estado estado){
		cboCidade.getItems().clear();
		cboCidade.getItems().addAll(Cidade.BuscarCidade(estado));
	}

	/*
	 * M�todo utilizado para atribuir a��es e dados � alguns itens da tela
	 * */
	public void AtribuirBotoes(){
		cboEstado.getItems().addAll(Estado.Select());
		btnConfirmar.setOnAction(l-> Adicionar());
		btnCancelar.setOnAction(l-> CallScene.thirdStage.close());
	}

	/*
	 * Esse m�todo serve para verificar se a a��o est� sendo de inser��o ou edi��o
	 * e redirecionar para o m�todo especifico (Insert ou Update)
	 * de acordo com o nome do bot�o
	 * */
	private void Adicionar() {

		if(btnConfirmar.getText().equals("SALVAR")){
			if(cboCidade.getSelectionModel().getSelectedItem() == null || txtBairro.getText().isEmpty() || txtLogradouro.getText().isEmpty()
					|| txtNumero.getText().isEmpty() || txtCEP.getText().isEmpty()){
				Alerta.showError("Erro no cadastro", "Preencha todos os campos");
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
		}else if(btnConfirmar.getText().equals("ATUALIZAR")){
			if(cboCidade.getSelectionModel().getSelectedItem() == null){
				Alerta.showError("Erro ao Atualizar", "Selecione um estado e uma cidade");
			}else{
				Helper.AUXENDERECOCOMPLETO.setValue(0);
				Endereco endereco_novo = new Endereco();
				endereco_novo.setLogradouro(txtLogradouro.getText());
				endereco_novo.setBairro(txtBairro.getText());
				endereco_novo.setNumero(txtNumero.getText());
				endereco_novo.setCep(txtCEP.getText());
				endereco_novo.setClassname("TEnderecoTransportadora");
				endereco_novo.setComplemento(txtComplemento.getText());

				endereco_novo.setCidade(cboCidade.getSelectionModel().getSelectedItem());

				String enderecoCompleto = String.format("%s, n� %s, %s, %s, %s %s - %s", endereco_novo.getLogradouro(), endereco_novo.getNumero(),
						endereco_novo.getComplemento() , endereco_novo.getBairro(), endereco_novo.getCep(), endereco_novo.getCidade().getNome(), endereco_novo.getCidade().getEstado().getUF());

				endereco_novo.setEnderecoCompleto(enderecoCompleto);
				endereco_novo.setOid_endereco(endereco.getOid_endereco());
				Endereco.Update(endereco_novo);

				Helper.ENDERECO_GERADO = endereco_novo;
				Helper.AUXENDERECOCOMPLETO.setValue(1);
				CallScene.thirdStage.close();
			}
		}
	}
}

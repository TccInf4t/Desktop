package br.com.onpecas.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import br.com.onpecas.helper.Alerta;
import br.com.onpecas.helper.Helper;
import br.com.onpecas.model.*;
import br.com.onpecas.view.CallScene;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;

public class TransportadoraInicialController implements Initializable{

	@FXML TableView<Transportadora> tblTransportadoras;
	@FXML TableColumn<Transportadora, String> clnNomeExibicao, clnCidade,clnFretePadrao,  clnEstado, clnObservacao;

	@FXML Button btnEditar, btnAdicionar, btnExcluir;

	CallScene callscene;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		callscene = new CallScene();
		AtualizarTblTransportadora();
		AtribuirBotoes();


		/*
		 * Listener que auxilia na atualiza��o da tabela
		 * quando � inserido ou atualizado uma transportadora
		 * a variavel AUXTRANSPORTADORA muda para o valor 1
		 * ent�o esse Listener detecta e atualiza a tblTransportadoras
		 * */
		Helper.AUXTRANSPORTADORA.addListener(new ChangeListener<Object>() {
		     @Override
		     public void changed(ObservableValue<?> observableValue, Object oldValue,
		         Object newValue) {
		         int newValuenovo =Integer.parseInt(newValue.toString());
		         if(newValuenovo == 1){
		            AtualizarTblTransportadora();
		            Helper.AUXTRANSPORTADORA.setValue(0);
		         }else{

		         }
		     }
		   });
	}

	/*
	 * M�todo utilizado para atribuir a��es e dados � alguns itens da tela
	 * */
	public void AtribuirBotoes(){
		btnAdicionar.setOnAction(l-> AdicionarTransportadora() );
		btnExcluir.setOnAction(l-> ExcluirTransportadora());
		btnEditar.setOnAction(l-> AtualizarTransportadora());
	}

	/*
	 * M�todo utilizado para excluir uma transportadora que foi selecionada na tblTransportadoras
	 * */
	private void ExcluirTransportadora() {
		Transportadora transportadora = tblTransportadoras.getSelectionModel().getSelectedItem();

		int result = JOptionPane.showConfirmDialog(null,"Deseja Excluir ? ","Excluir",JOptionPane.YES_NO_CANCEL_OPTION);

        if(result ==JOptionPane.YES_OPTION){
        	if(transportadora == null){
    			Alerta.showError("Erro ao Excluir", "Selecione uma Transportadora");
    		}else{
    			transportadora.Delete(transportadora);
    			Helper.AUXTRANSPORTADORA.setValue(1);
    		}
        }
	}

	/*
	 * M�todo que chama a tela para adicionar uma transportadora
	 * */
	public void AdicionarTransportadora(){
		callscene.LoadTransportadoraCadastroDetalhe(null);
	}

	/*
	 * M�todo que chama a tela para atualizar a transportadora que foi selecionada na tblTransportadoras
	 * */
	public void AtualizarTransportadora(){
		Transportadora transportadora = tblTransportadoras.getSelectionModel().getSelectedItem();

		if(transportadora == null){
			Alerta.showError("Erro ao Editar", "Selecione uma Transportadora");
		}else{
			callscene.LoadTransportadoraCadastroDetalhe(transportadora);
		}
	}

	/*
	 * M�todo utilizado para carregar os dados da table tblTransportadoras
	 * */
	public void AtualizarTblTransportadora(){
		clnNomeExibicao.setCellValueFactory(new PropertyValueFactory<Transportadora, String>("nome"));
		clnFretePadrao.setCellValueFactory(new PropertyValueFactory<Transportadora, String>("frete"));
		clnCidade.setCellValueFactory(new PropertyValueFactory<Transportadora, String>("cidade"));
		clnEstado.setCellValueFactory(new PropertyValueFactory<Transportadora, String>("estado"));
		clnObservacao.setCellValueFactory(new PropertyValueFactory<Transportadora, String>("observacoes"));

        List<Transportadora> lstTransportadora = Transportadora.Select();
        ObservableList<Transportadora> data = FXCollections.observableList(lstTransportadora);

        tblTransportadoras.setItems(data);
    }

	/*
	 * M�todo utilizado para carregar os dados da table tblTransportadoras com os filtros selecionados
	 * */
    public void AtualizarTblTransportadoraComFiltro(List<Transportadora> lstTransportadora){

    	clnNomeExibicao.setCellValueFactory(new PropertyValueFactory<Transportadora, String>("nome"));
		clnFretePadrao.setCellValueFactory(new PropertyValueFactory<Transportadora, String>("frete"));
		clnCidade.setCellValueFactory(new PropertyValueFactory<Transportadora, String>("cidade"));
		clnEstado.setCellValueFactory(new PropertyValueFactory<Transportadora, String>("estado"));
		clnObservacao.setCellValueFactory(new PropertyValueFactory<Transportadora, String>("observacoes"));

        ObservableList<Transportadora> data = FXCollections.observableList(lstTransportadora);

        tblTransportadoras.setItems(data);
    }
}

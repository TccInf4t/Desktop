package br.com.onpecas.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.onpecas.model.*;
import br.com.onpecas.view.CallScene;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class TransportadoraInicialController implements Initializable{

	@FXML TableView<Transportadora> tblTransportadoras;
	@FXML TableColumn<Transportadora, String> clnNomeExibicao, clnFretePadrao, clnCidade, clnEstado, clnObservacao;

	@FXML Button btnEditar, btnAdicionar, btnExcluir;

	CallScene callscene;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		callscene = new CallScene();
		AtualizarTblTransportadora();
		AtribuirBotoes();
	}

	public void AtribuirBotoes(){
		btnAdicionar.setOnAction(l-> AdicionarTransportadora() );
	}

	public void AdicionarTransportadora(){
		callscene.LoadTransportadoraCadastroDetalhe();
	}
	public void AtualizarTblTransportadora(){
		clnNomeExibicao.setCellValueFactory(new PropertyValueFactory<Transportadora, String>("nome"));
		clnFretePadrao.setCellValueFactory(new PropertyValueFactory<Transportadora, String>("frete"));
		//clnCidade.setCellValueFactory(new PropertyValueFactory<Transportadora, String>("nomeGrupo"));
		//clnEstado.setCellValueFactory(new PropertyValueFactory<Transportadora, String>("nomeGrupo"));
		clnObservacao.setCellValueFactory(new PropertyValueFactory<Transportadora, String>("observacoes"));

        List<Transportadora> lstTransportadora = Transportadora.Select();
        ObservableList<Transportadora> data = FXCollections.observableList(lstTransportadora);

        tblTransportadoras.setItems(data);
    }
    public void AtualizarTblTransportadoraComFiltro( List<Transportadora> lstTransportadora){
    	clnNomeExibicao.setCellValueFactory(new PropertyValueFactory<Transportadora, String>("nome"));
		clnFretePadrao.setCellValueFactory(new PropertyValueFactory<Transportadora, String>("frete"));
		//clnCidade.setCellValueFactory(new PropertyValueFactory<Transportadora, String>("nomeGrupo"));
		//clnEstado.setCellValueFactory(new PropertyValueFactory<Transportadora, String>("nomeGrupo"));
		clnObservacao.setCellValueFactory(new PropertyValueFactory<Transportadora, String>("observacoes"));

        ObservableList<Transportadora> data = FXCollections.observableList(lstTransportadora);

        tblTransportadoras.setItems(data);
    }
}

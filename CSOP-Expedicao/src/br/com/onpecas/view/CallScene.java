package br.com.onpecas.view;

import java.io.IOException;

import br.com.onpecas.controller.*;
import br.com.onpecas.model.Endereco;
import br.com.onpecas.model.Pedido;
import br.com.onpecas.model.Transportadora;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CallScene {
	public static Stage secondStage;
	public static Stage thirdStage;

	/*Fun��o para abrir uma carregar a tela com alguns campos da transportadora, masntendo a primeira*/
	public void LoadTransportadoraInicial(){
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("TransportadoraInicial.fxml"));
        loader.setController(new TransportadoraInicialController());

		try {
			ScrollPane module= (ScrollPane) loader.load();

			CSOPControllerExpedicao.border.setCenter(module);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*Fun��o para abrir uma terceira tela com os campos do endereco, masntendo a segunda*/
	public void LoadEndereco(Endereco endereco){
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Endereco.fxml"));
        loader.setController(new EnderecoController(endereco));
        thirdStage = new Stage();
		try {
			AnchorPane module= (AnchorPane) loader.load();

			Scene scene = new Scene(module);
			thirdStage.initModality(Modality.APPLICATION_MODAL);
			thirdStage.setScene(scene);
			thirdStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

/*Fun��o para abrir uma segunda tela com os campos da transportadora, masntendo a primeira*/
	public void LoadTransportadoraCadastroDetalhe(Transportadora transportadora){
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("TransportadoraDetalhe.fxml"));
        loader.setController(new TransportadoraCadastroDetalheController(transportadora));
        secondStage = new Stage();

		try {
			AnchorPane module= (AnchorPane) loader.load();

			Scene scene = new Scene(module);
			secondStage.initModality(Modality.APPLICATION_MODAL);
			secondStage.setScene(scene);
			secondStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void LoadPedidoSemLoteInicial(){

		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PedidoSemLoteInicial.fxml"));
        loader.setController(new PedidoSemLoteInicialController());
        
		try {
			AnchorPane module= (AnchorPane) loader.load();

			CSOPControllerExpedicao.border.setCenter(module);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

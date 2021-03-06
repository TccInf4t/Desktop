package br.com.onpecas.view;

import java.io.IOException;
import java.util.List;

import br.com.onpecas.controller.*;
import br.com.onpecas.model.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.*;

public class CallScene {
	public static Stage secondStage;
	public static Stage thirdStage;
	public static BorderPane border;
	/*Fun��o para abrir uma carregar a tela com alguns campos da transportadora, masntendo a primeira*/
	public void LoadTransportadoraInicial(){
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("TransportadoraInicial.fxml"));
        loader.setController(new TransportadoraInicialController());

		try {
			ScrollPane module= (ScrollPane) loader.load();

			border.setCenter(module);
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
			thirdStage.setTitle("Endereco");
			thirdStage.getIcons().add(new Image(getClass().getResource("logo.png").toString()));
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
			secondStage.setTitle("Transportadora Detalhe");
			secondStage.getIcons().add(new Image(getClass().getResource("logo.png").toString()));
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

			border.setCenter(module);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void LoadPedidoSemLoteDetalhe(Pedido pedido, boolean lote){
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PedidoSemLoteDetalhe.fxml"));
        loader.setController(new PedidoSemLoteDetalheController(pedido, lote));
        thirdStage = new Stage();

		try {
			AnchorPane module= (AnchorPane) loader.load();

			Scene scene = new Scene(module);
			thirdStage.initModality(Modality.APPLICATION_MODAL);
			thirdStage.setScene(scene);
			thirdStage.setTitle("Pedidos sem Lote Detalhe");
			thirdStage.getIcons().add(new Image(getClass().getResource("logo.png").toString()));
			thirdStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void LoadLoteSemTransporteInicial(){

		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("LoteSemTransporteInicial.fxml"));
        loader.setController(new LoteSemTransporteInicialController());

		try {
			AnchorPane module= (AnchorPane) loader.load();

			border.setCenter(module);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void LoadLoteSemTransporteDetalhe(Lote lote){
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("LoteSemTransporteDetalhe.fxml"));
        loader.setController(new LoteSemTransporteDetalheController(lote));
        secondStage = new Stage();

		try {
			AnchorPane module= (AnchorPane) loader.load();

			Scene scene = new Scene(module);
			secondStage.initModality(Modality.APPLICATION_MODAL);
			secondStage.setScene(scene);
			secondStage.setTitle("Lote Inicial");
			secondStage.getIcons().add(new Image(getClass().getResource("logo.png").toString()));
			secondStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void LoadGerarLote(List<Pedido> lstTodosPedidos){
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("GerarLote.fxml"));
        loader.setController(new GerarLoteController(lstTodosPedidos));
        secondStage = new Stage();

		try {
			AnchorPane module= (AnchorPane) loader.load();

			Scene scene = new Scene(module);
			secondStage.initModality(Modality.APPLICATION_MODAL);
			secondStage.setScene(scene);
			secondStage.setTitle("Gerar Lote");
			secondStage.getIcons().add(new Image(getClass().getResource("logo.png").toString()));
			secondStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void LoadIniciarTransporte(){
		FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("IniciarTransporte.fxml"));
        loader.setController(new IniciarTransporteController());
        secondStage = new Stage();

		try {
			AnchorPane module= (AnchorPane) loader.load();

			Scene scene = new Scene(module);
			secondStage.initModality(Modality.APPLICATION_MODAL);
			secondStage.setScene(scene);
			secondStage.setTitle("Iniciar Transporte");
			secondStage.getIcons().add(new Image(getClass().getResource("logo.png").toString()));
			secondStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void LoadPainelPrincipal(){
		FXMLLoader loader = new FXMLLoader();

		loader.setLocation(getClass().getResource("PainelPrincipal.fxml"));
		loader.setController(new PainelPrincipalController());
		try {
			border = (BorderPane) loader.load();
			Scene scene = new Scene(border);

			CSOPControllerExpedicao.primaryStage.setScene(scene);
			CSOPControllerExpedicao.primaryStage.setTitle("CSOP - Expedi��o");
			CSOPControllerExpedicao.primaryStage.getIcons().add(new Image(getClass().getResource("logo.png").toString()));
			CSOPControllerExpedicao.primaryStage.show();
			CSOPControllerExpedicao.primaryStage.centerOnScreen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

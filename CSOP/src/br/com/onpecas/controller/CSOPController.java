package br.com.onpecas.controller;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import br.com.onpecas.view.CSOP;
import javafx.fxml.*;
import javafx.scene.control.*;

public class CSOPController implements Initializable  {

	@FXML Button btnRelatorio, btnfinanceiro, btnexpedicao, btnusuarios;

	BufferedReader read;
	final Runtime run = Runtime.getRuntime();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		btnRelatorio.setOnAction(l-> AbrirRelatorios());
		btnexpedicao.setOnAction(l-> AbrirExpedicao());
		btnusuarios.setOnAction(l-> AbrirUsuarios());
	}

	public void AbrirRelatorios(){

		String Start = "cmd.exe /c  java -jar C:/CSOP/relatorios.jar";
		Process pro;
		 try {
	        pro = run.exec(Start);
	        read = new BufferedReader(new InputStreamReader(pro.getInputStream()));
	        CSOP.primaryStage.close();
	        read.readLine();

	    } catch(Exception e) {
	        System.err.println(e);
	    }

	}

	public void AbrirExpedicao(){
		String Start = "cmd.exe /c  java -jar C:/CSOP/expedicao.jar";
		Process pro;
		 try {
	        pro = run.exec(Start);
	        read = new BufferedReader(new InputStreamReader(pro.getInputStream()));
	        CSOP.primaryStage.close();
	        read.readLine();

	    } catch(Exception e) {
	        System.err.println(e);
	    }
	}

	public void AbrirUsuarios(){
		String Start = "cmd.exe /c  java -jar C:/CSOP/controleusuario.jar";
		Process pro;
		 try {
	        pro = run.exec(Start);
	        read = new BufferedReader(new InputStreamReader(pro.getInputStream()));
	        CSOP.primaryStage.close();
	        read.readLine();

	    } catch(Exception e) {
	        System.err.println(e);
	    }
	}
}

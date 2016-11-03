package br.com.onpecas.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.onpecas.helper.Helper;
import br.com.onpecas.model.*;
import br.com.onpecas.view.CallScene;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PermissaoController implements Initializable {

	@FXML CheckBox CMSAcessar, SGEAcessar, ExpedicaoAcessar, RelatoriosAcessar;
	@FXML Button btnSalvar,btnCancelar;
	@FXML Label lbGrupo;

	CallScene scene;
	Stage myStage;
	Grupo grupo;

	Permissao permissao;
	//variavel que serve para verificar se tem registro do grupo nas permiss�es
	Boolean existe;

	int aux;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		AtribuirCheckbox(permissao);

		scene = new CallScene();

		AtribuirBotoes();
		lbGrupo.setText(grupo.getNome());

	}

	public PermissaoController(Stage myStage, Grupo grupo) {
		this.myStage = myStage;
		this.grupo = grupo;

		VerificarExistencia(grupo);
	}

	//Chama a fun��o que verifica se o grupo tem permiss�es.Caso o resultado seja true,� chamado a fun��o select,
	//no qual retorna um objeto,caso false,� criado uma nova instancia.
	public void VerificarExistencia(Grupo grupo){

		if(Permissao.Buscar(grupo)){
			permissao=Permissao.Select(grupo);
			existe = true;

		}else{
			permissao = new Permissao();
			existe=false;
		}
	}

	public void AtribuirBotoes(){
		btnSalvar.setOnAction(l-> SalvarPermissao());
		btnCancelar.setOnAction(l-> VoltarTela());

	}

	private void VoltarTela() {
		Helper.AUXGROUP.setValue(1);
		myStage.close();

	}

	//caso a variavel 'existe' seja false, � inserido um novo registro no banco,caso contr�rio, � apenas atualizado
	private void SalvarPermissao() {
		SetarPermissoes();

		if(existe){
			Permissao.Update(permissao);

		}else{
			Permissao.Insert(permissao, grupo.getOid_grupo());

		}
		VoltarTela();

	}

	public void SetarPermissoes(){
		permissao.setAcs_cms(CMSAcessar.isSelected());
		permissao.setAcs_expedicao(ExpedicaoAcessar.isSelected());
		permissao.setAcs_relatorios(RelatoriosAcessar.isSelected());
		permissao.setAcs_sge(SGEAcessar.isSelected());
	}

	//Fun��o que seta os valores para as checkboxs.
	public void AtribuirCheckbox(Permissao permissao){
		CMSAcessar.setSelected(permissao.getAcs_cms());
		SGEAcessar.setSelected(permissao.getAcs_sge());
		ExpedicaoAcessar.setSelected(permissao.getAcs_expedicao());
		RelatoriosAcessar.setSelected(permissao.getAcs_relatorios());
	}
}

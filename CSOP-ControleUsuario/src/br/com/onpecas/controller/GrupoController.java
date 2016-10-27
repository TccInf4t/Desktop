package br.com.onpecas.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.onpecas.helper.*;
import br.com.onpecas.model.Grupo;
import br.com.onpecas.view.CallScene;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


//Classe de "controller" para grupos de usu�rios
public class GrupoController implements Initializable {

	@FXML Button btnCadastrarGrupo, btnCancelar;
	@FXML TextField txtNome;
	@FXML TextArea txtDescricao;

	Grupo grupo;

	static int OID_GRUPOAUXILIO;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		OID_GRUPOAUXILIO = 0;
		AtribuirBotoes();

		//Foi criado um objeto do tipo grupo, caso ele seja nulo,
		//quer dizer que a instancia oi feita pelo primeiro modo
		if(grupo != null){
			PrepararUpdate(grupo);
		}

	}

	//O segundo � passado, alem do stage, um objeto do tipo Grupo, para ser usado na edi��o
	public GrupoController(Grupo grupo) {
		this.grupo = grupo;
	}

	//Esse m�todo serve para atribuir os bot�es � suas funcionalidade
	private void AtribuirBotoes() {
		btnCadastrarGrupo.setOnAction(l-> InserirAtualizarGrupo());
		btnCancelar.setOnAction(l-> CallScene.secondStage.close());
	}

	/* M�todo utilizado tanto para inserir quanto para atualizar um grupo
	 * Esse m�todo funciona da seguinte forma:
	 * Quando o GrupoController � chamado, mas na sua instancia � passada somente o Stage, quer dizer que esse m�todo ir� inserir um registro
	 * Se na instancia � passada, al�m do Stage, um objeto do tipo Grupo, quer dizer que esse m�todo ir� atualizar um registro
	 * PS: O nome do bot�o definir� como o m�todo ir� agir
	 * */

	public void InserirAtualizarGrupo(){
		String nome = txtNome.getText();
		String descricao = txtDescricao.getText();
		int oid_grupo = OID_GRUPOAUXILIO;

		switch (btnCadastrarGrupo.getText()){
			case "Cadastrar":
				if(!nome.isEmpty()){
					Grupo grupo = new Grupo();

					grupo.setNome(nome);
					grupo.setDescricao(descricao);

					Grupo.Insert(grupo);
					Helper.AUXGROUP.setValue(1);
					CallScene.secondStage.close();

				}else{
					Alerta.showError("N�o � poss�vel criar o grupo", "O grupo precisa de um nome para ser criado.");
				}

				break;
			case "Atualizar":
				if(!nome.isEmpty()){
					Grupo grupo = new Grupo();

					grupo.setNome(nome);
					grupo.setDescricao(descricao);
					grupo.setOid_grupo(oid_grupo);

					Grupo.Update(grupo);
					Helper.AUXGROUP.setValue(1);
					CallScene.secondStage.close();

				}else{
					Alerta.showError("N�o � poss�vel criar o grupo", "O grupo precisa de um nome para ser atualizado.");
				}
				break;
		}

	}

	//Metodo que sera usado para orientar na atualiza��o dos dados
	//Esse metodo ir� receber um objeto do tipo Grupo e separara seus componentes para os TextField
	//Tambem mudar� o nome do bot�o para Atualizar
	public void PrepararUpdate(Grupo grupo){

		txtNome.setText(grupo.getNome());
		txtDescricao.setText(grupo.getDescricao());
		OID_GRUPOAUXILIO = grupo.getOid_grupo();

		this.btnCadastrarGrupo.setText("Atualizar");
	}
}

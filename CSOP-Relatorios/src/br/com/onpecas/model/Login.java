package br.com.onpecas.model;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.onpecas.helper.Alerta;
import br.com.onpecas.helper.MySqlConnect;

public class Login {
	static File file = new File("C:/CSOP/login.ini");

	public static int VerificaUsuario(String login, String senha){
		Connection con = MySqlConnect.ConectarDb();

		String sql = "select u.*, p.* "
				+ "from usuariosistema as u inner join grupousuario as g on (u.oid_grupo = g.oid_grupo) "
				+ "left join permissao as p on (p.oid_permissao = g.oid_permissao) "
				+ "where login = '"+login+"' and senha = '"+senha+"';";
		try {
			ResultSet rs =  con.createStatement().executeQuery(sql);

			while (rs.next()){
				System.out.println(rs.getInt("oid_permissao")+"-"+rs.getInt("acs_expedicao"));
				if(rs.getInt("oid_permissao")==0){
					return 0;
				}else{
					if(rs.getInt("acs_relatorios") == 1){
						return 1;
					}else{
						return 2;
					}
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return 0;
	}

	public static String VerificarServer(){

		//Para ler, uma ideia seria:
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(file);

			BufferedReader reader = new BufferedReader(fileReader);
			String data = null;

			try {
				while((data = reader.readLine()) != null){
				    if(!data.isEmpty()){
						fileReader.close();
						reader.close();
						return data;

					}else{
						AtribuiIP("localhost");
						fileReader.close();
						reader.close();
						return "localhost";
					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e1) {
			Alerta.showError("Caminho Errado", "Caminho ou arquivo não encontrado\nContate o administrador");
		}

		return "localhost";
	}

	public static void AtribuiIP(String ip){

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(ip);

			writer.flush(); //Cria o conteúdo do arquivo.
			writer.close(); //Fechando conexão e escrita do arquivo.

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

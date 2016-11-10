package br.com.onpecas.model;

import java.io.*;

public class Login {
	static File file = new File("C:/CSOP/login.ini");

	public static String VerificarServer(){

		//Para ler, uma ideia seria:
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(file);

			BufferedReader reader = new BufferedReader(fileReader);
			String data = null;

			try {
				while((data = reader.readLine()) != null){
				    System.out.println(data);
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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return "localhost";
	}

	public static void AtribuiIP(String ip){

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(ip);

			writer.flush(); //Cria o conte�do do arquivo.
			writer.close(); //Fechando conex�o e escrita do arquivo.

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

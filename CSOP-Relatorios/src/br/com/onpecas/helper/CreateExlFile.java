package br.com.onpecas.helper;

import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class CreateExlFile {
	@SuppressWarnings({ "deprecation", "resource" })
	public static void Gerar(List<HashMap<Integer, String>> lstHash, String tipo, String periodoRelatorio){
	 try{
		 Date data = new Date();
		 String dataAtual = String.format("%s%s%s%s%s%s", data.getDay(), data.getMonth(), data.getYear(),
				 data.getHours(), data.getMinutes(), data.getSeconds());

	     // local do arquivo
		 String filename="C:/OnPecaControl/planilhas/"+tipo+"-"+dataAtual+".xls" ;
		 HSSFWorkbook workbook=new HSSFWorkbook();
		 HSSFSheet sheet =  workbook.createSheet("Sheet");


		 if(tipo.equals("Faturamento")){
			 HSSFRow rowDetalhe = sheet.createRow((short)0);
			 rowDetalhe.createCell(0).setCellValue("Periodo: ");
			 rowDetalhe.createCell(1).setCellValue(periodoRelatorio);

			// criando as linhas
			 HSSFRow rowhead = sheet.createRow((short)1);
			 rowhead.createCell(0).setCellValue("data");
			 rowhead.createCell(1).setCellValue("valor");

			 // definindo seus valores
			 // por exemplo protocolo.getProtocolo();


			 for(int i =0; i<lstHash.size(); i++){
				 HSSFRow row=   sheet.createRow((short)i+2);
				 row.createCell(0).setCellValue(lstHash.get(i).get(1));
				 row.createCell(1).setCellValue(lstHash.get(i).get(2));
			 }



		 }else if(tipo.equals("Pedido")){
			 HSSFRow rowDetalhe = sheet.createRow((short)0);
			 rowDetalhe.createCell(0).setCellValue("Periodo: ");
			 rowDetalhe.createCell(1).setCellValue(periodoRelatorio);

			// criando as linhas
			 HSSFRow rowhead = sheet.createRow((short)1);
			 rowhead.createCell(0).setCellValue("data");
			 rowhead.createCell(1).setCellValue("valor");

			 // definindo seus valores
			 // por exemplo protocolo.getProtocolo();


			 for(int i =0; i<lstHash.size(); i++){
				 HSSFRow row=   sheet.createRow((short)i+2);
				 row.createCell(0).setCellValue(lstHash.get(i).get(1));
				 row.createCell(1).setCellValue(lstHash.get(i).get(2));
			 }

		 }else if(tipo.equals("Venda")){
			// criando as linhas
			 HSSFRow rowDetalhe = sheet.createRow((short)0);
			 rowDetalhe.createCell(0).setCellValue("Periodo: ");
			 rowDetalhe.createCell(1).setCellValue(periodoRelatorio);

			// criando as linhas
			 HSSFRow rowhead = sheet.createRow((short)1);
			 rowhead.createCell(0).setCellValue("data");
			 rowhead.createCell(1).setCellValue("valor");

			 // definindo seus valores
			 // por exemplo protocolo.getProtocolo();


			 for(int i =0; i<lstHash.size(); i++){
				 HSSFRow row=   sheet.createRow((short)i+2);
				 row.createCell(0).setCellValue(lstHash.get(i).get(1));
				 row.createCell(1).setCellValue(lstHash.get(i).get(2));
			 }
		 }


		 FileOutputStream fileOut =  new FileOutputStream(filename);
		 workbook.write(fileOut);
		 fileOut.close();

	 } catch ( Exception ex ) {
	     System.out.println(ex);

	 }
	}
}

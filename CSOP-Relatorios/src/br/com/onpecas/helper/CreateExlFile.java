package br.com.onpecas.helper;

import java.io.*;
import java.util.*;
import org.apache.poi.hssf.usermodel.*;
import br.com.onpecas.model.Relatorio;

public class CreateExlFile {
	@SuppressWarnings({ "deprecation", "resource" })
	public static void Gerar(List<Relatorio> lstRelatorio, String tipo, String periodoRelatorio){
	 try{
		 Date data = new Date();
		 String dataAtual = String.format("%s%s%s%s%s%s", data.getDay(), data.getMonth(), data.getYear(),
				 data.getHours(), data.getMinutes(), data.getSeconds());

	     // local do arquivo
		 String filename=System.getProperty("user.home")+"/Documents/OnPecaControl/planilhas/"+tipo+"-"+dataAtual+".xls" ;
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

			 int cont = 0;
			 for(Relatorio item : lstRelatorio){
				 HSSFRow row=   sheet.createRow((short)cont+2);
				 row.createCell(0).setCellValue(item.getTituloData());
				 row.createCell(1).setCellValue(item.getValorQuantidade());
				 cont++;
			 }

		 }else if(tipo.equals("Pedido")){
			 HSSFRow rowDetalhe = sheet.createRow((short)0);
			 rowDetalhe.createCell(0).setCellValue("Tipo: ");
			 rowDetalhe.createCell(1).setCellValue("Quantidade de pedido agrupado pelo status");

			// criando as linhas
			 HSSFRow rowhead = sheet.createRow((short)1);
			 rowhead.createCell(0).setCellValue("Status");
			 rowhead.createCell(1).setCellValue("Quantidade");

			 // definindo seus valores
			 // por exemplo protocolo.getProtocolo();

			 int cont = 0;
			 for(Relatorio item : lstRelatorio){
				 HSSFRow row=   sheet.createRow((short)cont+2);
				 row.createCell(0).setCellValue(item.getTituloData());
				 row.createCell(1).setCellValue(item.getValorQuantidade());
				 cont++;
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


			 int cont = 0;
			 for(Relatorio item : lstRelatorio){
				 HSSFRow row=   sheet.createRow((short)cont+2);
				 row.createCell(0).setCellValue(item.getTituloData());
				 row.createCell(1).setCellValue(item.getValorQuantidade());
				 cont++;
			 }
		 }

		 File diretorio = new File(System.getProperty("user.home")+"/Documents/OnPecaControl/planilhas/");

		 if(!diretorio.exists())
			 diretorio.mkdirs();

		 FileOutputStream fileOut =  new FileOutputStream(filename);
		 workbook.write(fileOut);
		 fileOut.close();
		Alerta.showInformation("Sucesso", "Planilha gerada com sucesso\n Voc� pode visualiz�-la em C:/OnPecaControl/planilhas/");
	 } catch ( Exception ex ) {
	     System.out.println(ex);
		Alerta.showError("Erro ao exportar", ex.getMessage());
	 }
	}
}

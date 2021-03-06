package br.com.onpecas.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import br.com.onpecas.helper.MySqlConnect;

public class Relatorio {

	private String tituloData, valorQuantidade, quantidadeVenda;

	public String getTituloData() {return tituloData;}
	public void setTituloData(String tituloData) {this.tituloData = tituloData;}
	public String getValorQuantidade() {return valorQuantidade;}
	public void setValorQuantidade(String valorQuantidade) {this.valorQuantidade = valorQuantidade;}
	public String getQuantidadeVenda() {return quantidadeVenda;}
	public void setQuantidadeVenda(String quantidadeVenda) {this.quantidadeVenda = quantidadeVenda;}

	//Classe criada para realizar as buscas no banco de dados, filtrando de acordo com o que foi selecionado no controller
	public static List<Relatorio> FiltrarPedido(){
		Connection con = MySqlConnect.ConectarDb();
		List<Relatorio> lstRelatorio = new ArrayList<Relatorio>();

		String sql = "select p.*, s.*, count(*) as qtdpedido from pedido as p inner join status as s "
				+ "on (p.oid_status = s.oid_status) group by s.oid_status;";
		try {
			ResultSet rs = con.createStatement().executeQuery(sql);

			while(rs.next()){
				Relatorio relatorio = new Relatorio();

				relatorio.setTituloData(rs.getString("nome"));
				relatorio.setValorQuantidade(rs.getString("qtdpedido"));

				lstRelatorio.add(relatorio);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lstRelatorio;
	}

	public static List<Relatorio> FiltarFaturamento(String periodo, boolean temData, String dataInicial, String dataFinal){
		Connection con = MySqlConnect.ConectarDb();
		List<Relatorio> lstRelatorio = new ArrayList<>();
		String sql = "";

		if(temData){
			if(periodo.equals("Semanal")){
				sql = "select *,"
						+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Sunday'), '%X%V %W'), '%d/%m/%y') as semanainicial, "
						+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Saturday'), '%X%V %W'), '%d/%m/%y') as semanafinal, "
						+"concat('De: ', date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Sunday'), '%X%V %W'), '%d/%m/%y'), ' - At�: ',"
						+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Saturday'), '%X%V %W'), '%d/%m/%y')) as datareferencia,"
						+"sum(valortotal)+sum(frete) as valorfaturado "
						+"from visualizacaorelatorios "
						+ "where dtrealizado between '"+dataInicial+"' and '"+dataFinal+"' "
						+ "group by yearweek(dtrealizado) ;";

			}else if(periodo.equals("Mensal")){
				sql ="select *,concat(extract(month from dtrealizado),' - ', extract(year from dtrealizado)) as datareferencia, "
						+ "sum(valortotal)+sum(frete) as valorfaturado from visualizacaorelatorios "
						+ "where dtrealizado between '"+dataInicial+"' and '"+dataFinal+"' "
						+ "group by extract(year from dtrealizado), "
						+ "extract(month from dtrealizado) ;";

			}else if(periodo.equals("Anual")){
				sql ="select *, extract(year from dtrealizado) as datareferencia, sum(valortotal)+sum(frete) as valorfaturado "
						+ "from visualizacaorelatorios "
						+ "where dtrealizado between '"+dataInicial+"' and '"+dataFinal+"' "
						+ "group by extract(year from dtrealizado) ;";

			}else if(periodo.equals("Diario")){
				sql = "select *, dtrealizado as datareferencia, "
						+"sum(valortotal)+sum(frete) as valorfaturado "
						+"from visualizacaorelatorios "
						+ "where dtrealizado between '"+dataInicial+"' and '"+dataFinal+"' "
						+ "group by date(dtrealizado) ;";

			}
		}else{
			if(periodo.equals("Semanal")){
				sql = "select *,"
						+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Sunday'), '%X%V %W'), '%d/%m/%y') as semanainicial, "
						+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Saturday'), '%X%V %W'), '%d/%m/%y') as semanafinal, "
						+"concat('De: ', date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Sunday'), '%X%V %W'), '%d/%m/%y'), ' - At�: ',"
						+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Saturday'), '%X%V %W'), '%d/%m/%y')) as datareferencia,"
						+"sum(valortotal)+sum(frete) as valorfaturado "+
						"from visualizacaorelatorios group by yearweek(dtrealizado) ;";

			}else if(periodo.equals("Mensal")){
				sql ="select *,concat(extract(month from dtrealizado),' - ', extract(year from dtrealizado)) as datareferencia, "
						+ "sum(valortotal)+sum(frete) as valorfaturado from visualizacaorelatorios group by extract(year from dtrealizado), "
						+ "extract(month from dtrealizado) ;";

			}else if(periodo.equals("Anual")){
				sql ="select *, extract(year from dtrealizado) as datareferencia, sum(valortotal)+sum(frete) as valorfaturado "
						+ "from visualizacaorelatorios group by extract(year from dtrealizado) ;";

			}else if(periodo.equals("Diario")){
				sql = "select *, dtrealizado as datareferencia, "
						+"sum(valortotal)+sum(frete) as valorfaturado "
						+"from visualizacaorelatorios group by date(dtrealizado) ;";

			}
		}

		try {
			ResultSet rs = con.createStatement().executeQuery(sql);

			while(rs.next()){
				Relatorio relatorio = new Relatorio();

				relatorio.setTituloData(rs.getString("datareferencia"));
				relatorio.setValorQuantidade(rs.getString("ValorFaturado"));

				lstRelatorio.add(relatorio);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lstRelatorio;
	}

	public static List<Relatorio> FiltrarVenda(String entidade, String nomeEntidade, int oid_estado, boolean temCidade, int oid_cidade, String periodo, boolean temData, String dataInicial, String dataFinal){

		Connection con = MySqlConnect.ConectarDb();
		List<Relatorio> lstRelatorio = new ArrayList<>();
		String sql ="select v.*, p.*  from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca); ";


		if(entidade.equals("Regi�o")){
			if(temCidade){
				if(temData){
					if(periodo.equals("Semanal")){
						sql = "select v.*, p.*, "
								+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Sunday'), '%X%V %W'), '%d/%m/%y') as semanainicial, "
								+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Saturday'), '%X%V %W'), '%d/%m/%y') as semanafinal, "
								+"concat('De: ', date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Sunday'), '%X%V %W'), '%d/%m/%y'), ' - At�: ',"
								+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Saturday'), '%X%V %W'), '%d/%m/%y')) as datareferencia,"
								+"sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda "
								+"from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) "
								+ "where dtrealizado between '"+dataInicial+"' and '"+dataFinal+"' and oid_cidade = "+oid_cidade+" "
								+ "group by yearweek(dtrealizado) ;";

					}else if(periodo.equals("Mensal")){
						sql ="select v.*, p.*,concat(extract(month from dtrealizado),' - ', extract(year from dtrealizado)) as datareferencia, "
								+ "sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda  "
								+ "from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) "
								+ "where dtrealizado between '"+dataInicial+"' and '"+dataFinal+"' and oid_cidade = "+oid_cidade+"  "
								+ "group by extract(year from dtrealizado), "
								+ "extract(month from dtrealizado) ;";

					}else if(periodo.equals("Anual")){
						sql ="select v.*, p.*, extract(year from dtrealizado) as datareferencia, sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda  "
								+ "from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) "
								+ "where dtrealizado between '"+dataInicial+"' and '"+dataFinal+"' and oid_cidade = "+oid_cidade+" "
								+ "group by extract(year from dtrealizado) ;";

					}else if(periodo.equals("Diario")){
						sql = "select v.*, p.*,  dtrealizado as datareferencia, "
								+"sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda  "
								+"from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) "
								+ "where dtrealizado between '"+dataInicial+"' and '"+dataFinal+"' and oid_cidade = "+oid_cidade+" "
								+ "group by date(dtrealizado) ;";

					}
				}else{
					if(periodo.equals("Semanal")){
						sql = "select v.*, p.*,  "
								+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Sunday'), '%X%V %W'), '%d/%m/%y') as semanainicial, "
								+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Saturday'), '%X%V %W'), '%d/%m/%y') as semanafinal, "
								+"concat('De: ', date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Sunday'), '%X%V %W'), '%d/%m/%y'), ' - At�: ',"
								+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Saturday'), '%X%V %W'), '%d/%m/%y')) as datareferencia,"
								+"sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda  "+
								"from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) and oid_cidade = "+oid_cidade+" "
								+ "group by yearweek(dtrealizado) ;";

					}else if(periodo.equals("Mensal")){
						sql ="select v.*, p.*,concat(extract(month from dtrealizado),' - ', extract(year from dtrealizado)) as datareferencia, "
								+ "sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda  "
								+ "from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) and oid_cidade = "+oid_cidade+" "
								+ "group by extract(year from dtrealizado), "
								+ "extract(month from dtrealizado) ;";

					}else if(periodo.equals("Anual")){
						sql ="select v.*, p.*,  extract(year from dtrealizado) as datareferencia, sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda  "
								+ "from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) and oid_cidade = "+oid_cidade+" "
								+ "group by extract(year from dtrealizado) ;";

					}else if(periodo.equals("Diario")){
						sql = "select v.*, p.*,  dtrealizado as datareferencia, "
								+"sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda  "
								+"from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) and oid_cidade = "+oid_cidade+" "
								+ "group by date(dtrealizado) ;";

					}
				}
			}else{
				if(temData){
					if(periodo.equals("Semanal")){
						sql = "select v.*, p.*, "
								+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Sunday'), '%X%V %W'), '%d/%m/%y') as semanainicial, "
								+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Saturday'), '%X%V %W'), '%d/%m/%y') as semanafinal, "
								+"concat('De: ', date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Sunday'), '%X%V %W'), '%d/%m/%y'), ' - At�: ',"
								+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Saturday'), '%X%V %W'), '%d/%m/%y')) as datareferencia,"
								+"sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda  "
								+"from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) "
								+ "where dtrealizado between '"+dataInicial+"' and '"+dataFinal+"' and oid_estado = "+oid_estado+" "
								+ "group by yearweek(dtrealizado) ;";

					}else if(periodo.equals("Mensal")){
						sql ="select v.*, p.*,concat(extract(month from dtrealizado),' - ', extract(year from dtrealizado)) as datareferencia, "
								+ "sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda  "
								+ "from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) "
								+ "where dtrealizado between '"+dataInicial+"' and '"+dataFinal+"' and oid_estado = "+oid_estado+" "
								+ "group by extract(year from dtrealizado), "
								+ "extract(month from dtrealizado) ;";

					}else if(periodo.equals("Anual")){
						sql ="select v.*, p.*, extract(year from dtrealizado) as datareferencia, sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda  "
								+ "from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) "
								+ "where dtrealizado between '"+dataInicial+"' and '"+dataFinal+"' and oid_estado = "+oid_estado+" "
								+ "group by extract(year from dtrealizado) ;";

					}else if(periodo.equals("Diario")){
						sql = "select v.*, p.*,  dtrealizado as datareferencia, "
								+"sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda  "
								+"from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) "
								+ "where dtrealizado between '"+dataInicial+"' and '"+dataFinal+"' and oid_estado = "+oid_estado+" "
								+ "group by date(dtrealizado) ;";

					}
				}else{
					if(periodo.equals("Semanal")){
						sql = "select v.*, p.*,  "
								+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Sunday'), '%X%V %W'), '%d/%m/%y') as semanainicial, "
								+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Saturday'), '%X%V %W'), '%d/%m/%y') as semanafinal, "
								+"concat('De: ', date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Sunday'), '%X%V %W'), '%d/%m/%y'), ' - At�: ',"
								+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Saturday'), '%X%V %W'), '%d/%m/%y')) as datareferencia,"
								+"sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda  "+
								"from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) and oid_estado = "+oid_estado+" "
								+ "group by yearweek(dtrealizado) ;";

					}else if(periodo.equals("Mensal")){
						sql ="select v.*, p.*,concat(extract(month from dtrealizado),' - ', extract(year from dtrealizado)) as datareferencia, "
								+ "sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda  "
								+ "from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) and oid_estado = "+oid_estado+" "
								+ "group by extract(year from dtrealizado), "
								+ "extract(month from dtrealizado) ;";

					}else if(periodo.equals("Anual")){
						sql ="select v.*, p.*,  extract(year from dtrealizado) as datareferencia, sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda  "
								+ "from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) and oid_estado = "+oid_estado+" "
								+ "group by extract(year from dtrealizado) ;";

					}else if(periodo.equals("Diario")){
						sql = "select v.*, p.*,  dtrealizado as datareferencia, "
								+"sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda  "
								+"from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) and oid_estado = "+oid_estado+" "
								+ "group by date(dtrealizado) ;";

					}
				}
			}
		}else if(entidade.equals("Produto")){
			if(temData){
				if(periodo.equals("Semanal")){
					sql = "select v.*, p.*, "
							+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Sunday'), '%X%V %W'), '%d/%m/%y') as semanainicial, "
							+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Saturday'), '%X%V %W'), '%d/%m/%y') as semanafinal, "
							+"concat('De: ', date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Sunday'), '%X%V %W'), '%d/%m/%y'), ' - At�: ',"
							+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Saturday'), '%X%V %W'), '%d/%m/%y')) as datareferencia,"
							+"sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda  "
							+"from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) "
							+ "where dtrealizado between '"+dataInicial+"' and '"+dataFinal+"' and p.nome like '%"+nomeEntidade+"%' "
							+ "group by yearweek(dtrealizado) ;";

				}else if(periodo.equals("Mensal")){
					sql ="select v.*, p.*,concat(extract(month from dtrealizado),' - ', extract(year from dtrealizado)) as datareferencia, "
							+ "sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda  "
							+ "from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) "
							+ "where dtrealizado between '"+dataInicial+"' and '"+dataFinal+"' and p.nome like '%"+nomeEntidade+"%' "
							+ "group by extract(year from dtrealizado), "
							+ "extract(month from dtrealizado) ;";

				}else if(periodo.equals("Anual")){
					sql ="select v.*, p.*, extract(year from dtrealizado) as datareferencia, sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda  "
							+ "from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) "
							+ "where dtrealizado between '"+dataInicial+"' and '"+dataFinal+"' and p.nome like '%"+nomeEntidade+"%' "
							+ "group by extract(year from dtrealizado) ;";

				}else if(periodo.equals("Diario")){
					sql = "select v.*, p.*,  dtrealizado as datareferencia, "
							+"sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda  "
							+"from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) "
							+ "where dtrealizado between '"+dataInicial+"' and '"+dataFinal+"' and p.nome like '%"+nomeEntidade+"%' "
							+ "group by date(dtrealizado) ;";

				}
			}else{
				if(periodo.equals("Semanal")){
					sql = "select v.*, p.*,  "
							+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Sunday'), '%X%V %W'), '%d/%m/%y') as semanainicial, "
							+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Saturday'), '%X%V %W'), '%d/%m/%y') as semanafinal, "
							+"concat('De: ', date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Sunday'), '%X%V %W'), '%d/%m/%y'), ' - At�: ',"
							+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Saturday'), '%X%V %W'), '%d/%m/%y')) as datareferencia,"
							+"sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda  "+
							"from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) and p.nome like '%"+nomeEntidade+"%' "
							+ "group by yearweek(dtrealizado) ;";

				}else if(periodo.equals("Mensal")){
					sql ="select v.*, p.*,concat(extract(month from dtrealizado),' - ', extract(year from dtrealizado)) as datareferencia, "
							+ "sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda  "
							+ "from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) and p.nome like '%"+nomeEntidade+"%' "
							+ "group by extract(year from dtrealizado), "
							+ "extract(month from dtrealizado) ;";

				}else if(periodo.equals("Anual")){
					sql ="select v.*, p.*,  extract(year from dtrealizado) as datareferencia, sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda  "
							+ "from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) and p.nome like '%"+nomeEntidade+"%' "
							+ "group by extract(year from dtrealizado) ;";

				}else if(periodo.equals("Diario")){
					sql = "select v.*, p.*,  dtrealizado as datareferencia, "
							+"sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda  "
							+"from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) and p.nome like '%"+nomeEntidade+"%' "
							+ "group by date(dtrealizado) ;";

				}
			}
		}else if(entidade.equals("Cliente")){
			if(temData){
				if(periodo.equals("Semanal")){
					sql = "select v.*, p.*, "
							+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Sunday'), '%X%V %W'), '%d/%m/%y') as semanainicial, "
							+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Saturday'), '%X%V %W'), '%d/%m/%y') as semanafinal, "
							+"concat('De: ', date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Sunday'), '%X%V %W'), '%d/%m/%y'), ' - At�: ',"
							+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Saturday'), '%X%V %W'), '%d/%m/%y')) as datareferencia,"
							+"sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda  "
							+"from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) "
							+ "where dtrealizado between '"+dataInicial+"' and '"+dataFinal+"' and NomeCliente like '%"+nomeEntidade+"%' "
							+ "group by yearweek(dtrealizado) ;";

				}else if(periodo.equals("Mensal")){
					sql ="select v.*, p.*,concat(extract(month from dtrealizado),' - ', extract(year from dtrealizado)) as datareferencia, "
							+ "sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda  "
							+ "from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) "
							+ "where dtrealizado between '"+dataInicial+"' and '"+dataFinal+"' and NomeCliente like '%"+nomeEntidade+"%' "
							+ "group by extract(year from dtrealizado), "
							+ "extract(month from dtrealizado) ;";

				}else if(periodo.equals("Anual")){
					sql ="select v.*, p.*, extract(year from dtrealizado) as datareferencia, sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda  "
							+ "from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) "
							+ "where dtrealizado between '"+dataInicial+"' and '"+dataFinal+"' and NomeCliente like '%"+nomeEntidade+"%' "
							+ "group by extract(year from dtrealizado) ;";

				}else if(periodo.equals("Diario")){
					sql = "select v.*, p.*,  dtrealizado as datareferencia, "
							+"sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda  "
							+"from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) "
							+ "where dtrealizado between '"+dataInicial+"' and '"+dataFinal+"' and NomeCliente like '%"+nomeEntidade+"%' "
							+ "group by date(dtrealizado) ;";

				}
			}else{
				if(periodo.equals("Semanal")){
					sql = "select v.*, p.*,  "
							+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Sunday'), '%X%V %W'), '%d/%m/%y') as semanainicial, "
							+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Saturday'), '%X%V %W'), '%d/%m/%y') as semanafinal, "
							+"concat('De: ', date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Sunday'), '%X%V %W'), '%d/%m/%y'), ' - At�: ',"
							+"date_format(STR_TO_DATE(concat(yearweek(dtrealizado), ' Saturday'), '%X%V %W'), '%d/%m/%y')) as datareferencia,"
							+"sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda  "+
							"from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) and NomeCliente like '%"+nomeEntidade+"%' "
							+ "group by yearweek(dtrealizado) ;";

				}else if(periodo.equals("Mensal")){
					sql ="select v.*, p.*,concat(extract(month from dtrealizado),' - ', extract(year from dtrealizado)) as datareferencia, "
							+ "sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda  "
							+ "from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) and NomeCliente like '%"+nomeEntidade+"%' "
							+ "group by extract(year from dtrealizado), "
							+ "extract(month from dtrealizado) ;";

				}else if(periodo.equals("Anual")){
					sql ="select v.*, p.*,  extract(year from dtrealizado) as datareferencia, sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda  "
							+ "from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) and NomeCliente like '%"+nomeEntidade+"%' "
							+ "group by extract(year from dtrealizado) ;";

				}else if(periodo.equals("Diario")){
					sql = "select v.*, p.*,  dtrealizado as datareferencia, "
							+"sum(valortotal)+sum(frete) as valorfaturado, count(*) as quantidadevenda  "
							+"from visualizacaorelatorios as v inner join peca as p on (v.oid_peca = p.oid_peca) and NomeCliente like '%"+nomeEntidade+"%' "
							+ "group by date(dtrealizado) ;";

				}
			}
		}
		try {
			ResultSet rs = con.createStatement().executeQuery(sql);

			while(rs.next()){
				Relatorio relatorio = new Relatorio();
				relatorio.setQuantidadeVenda(rs.getString("quantidadevenda"));
				relatorio.setTituloData(rs.getString("datareferencia"));
				relatorio.setValorQuantidade(rs.getString("ValorFaturado"));

				lstRelatorio.add(relatorio);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lstRelatorio;
	}
}

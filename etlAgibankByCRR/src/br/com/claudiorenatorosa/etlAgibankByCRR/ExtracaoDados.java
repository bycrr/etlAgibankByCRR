package br.com.claudiorenatorosa.etlAgibankByCRR;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ExtracaoDados {
	private final static String EXTENSAO_IN = ".dat";
	private final static String EXTENSAO_OUT = ".done.dat";
	private final static String VAR_HOME = "HOMEPATH";
	private final static String DIRETORIO_IN = "data\\in\\";
	private final static String DIRETORIO_OUT = "data\\out\\";
	private final static String SEPARADOR_COLUNAS = "ç";
	private final static String SEPARADOR_ITENS = ",";
	private final static String SEPARADOR_ITEM = "-";
	
	public static void main(String[] args) {
		ArrayList<String> arquivos = new ArrayList<String>();
		ArrayList<String> linhas = new ArrayList<String>();
		RelatorioVO relatorioVO = new RelatorioVO();		
		String diretorioIn = System.getenv().get(VAR_HOME) + "\\" + DIRETORIO_IN;
		String diretorioOut = System.getenv().get(VAR_HOME) + "\\" + DIRETORIO_OUT;
		
    	try {
    		while (true) {    			
    			lerDiretorio(diretorioIn, EXTENSAO_IN, arquivos);
    			
    			for (String arquivo : arquivos) {
    				lerArquivo(diretorioIn, arquivo, linhas);
    			
        			for (String linha : linhas) {        				
        				extrairLinha(linha, relatorioVO);
		    		}
	    		
	    			if (!linhas.isEmpty()) {
	    				gravarRelatorio(diretorioOut, relatorioVO, 
	    						arquivo.replaceAll(EXTENSAO_IN, EXTENSAO_OUT));
	    				linhas.clear();
	       				apagarArquivo(diretorioIn, arquivo);
	    			}	    			
	    			relatorioVO.limparRalatorio();
    			}
   				arquivos.clear();
    			
    			new Thread();
				Thread.sleep(1000);
    		}           
    	} catch (Exception e) {
            System.out.println( "Erro: " + e );    		
    	}

	}

	public static void lerDiretorio(String diretorioIn, String extensao, 
			ArrayList<String> arquivos) throws Exception {
		
		FileFilter filtro = new FileFilter() {
		    public boolean accept(File arq) {
		        return arq.getName().endsWith(extensao);
		    }
		};
		File arqDiretorio = new File(diretorioIn);
		File arrayArquivos[] = arqDiretorio.listFiles(filtro);
		
		for (int i=0; i < arrayArquivos.length; i++) {
			arquivos.add(arrayArquivos[i].getName());
		}
	}
	
	private static void lerArquivo(String diretorioIn, String arquivo, 
			ArrayList<String> linhas) throws Exception {
		
		FileInputStream arqLido = new FileInputStream(diretorioIn + arquivo);
		BufferedReader buffer = new BufferedReader(new InputStreamReader(arqLido, "UTF8"));
		String linhaLida = null;
		
		while ((linhaLida = buffer.readLine()) != null) {
            linhas.add(linhaLida);
        }
		buffer.close();
	}
	
	private static void extrairLinha(String linha, RelatorioVO relatorioVO) 
			throws Exception {
		
		String[] colunas = linha.split(SEPARADOR_COLUNAS);
		String[] itens, item;
		Float valor;
		
		if (colunas[0].equals("001")) {
			relatorioVO.adicionarVendedor(colunas[2]);
			
		} else if (colunas[0].equals("002")) {
			relatorioVO.adicionarCliente(colunas[2]);
			
		} else if (colunas[0].equals("003")) {
			colunas[2] = colunas[2].replaceAll("\\[", "").replaceAll("\\]","");
			itens = colunas[2].split(SEPARADOR_ITENS);
			
			for (int i=0; i < itens.length; i++) {
				item = itens[i].split(SEPARADOR_ITEM);
				valor = Float.valueOf(item[1]) * Float.valueOf(item[2]); 
				relatorioVO.computarVenda(colunas[1], valor, colunas[3]);
			}
		}
	}
	
	private static void gravarRelatorio(String diretorioOut, RelatorioVO relatorioVO, 
			String arquivo) throws Exception {
		
		String qtdClientes = relatorioVO.getQtdClientes().toString();
		String qtdVendedores = relatorioVO.getQtdVendedores().toString();
		String maiorVenda = relatorioVO.getVendaMaisCara();
		String piorVendedor = relatorioVO.getPiorVendedor();
		
		File fileArquivo = new File(diretorioOut, arquivo);
		fileArquivo.createNewFile();
		FileWriter fileWriter = new FileWriter(fileArquivo, false);
		PrintWriter printWriter = new PrintWriter(fileWriter);
		
		printWriter.print("Quantidade de clientes no arquivo de entrada: " + qtdClientes + "\r\n");
		printWriter.print("Quantidade de vendedores no arquivo de entrada: " + qtdVendedores + "\r\n");
		printWriter.print("ID da venda mais cara: " + maiorVenda + "\r\n");
		printWriter.print("O pior vendedor foi: " + piorVendedor + "\r\n");
		
        printWriter.flush();
        printWriter.close();
        fileWriter.close();
	}
	
	private static void apagarArquivo(String diretorioIn, String arquivo) 
			throws Exception {
		
		File arqApagar = new File(diretorioIn + arquivo);
		arqApagar.delete();
	}
}


package br.com.claudiorenatorosa.etlAgibankByCRR;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class ExtracaoDadosTest {

	private final static String EXTENSAO_IN = ".dat";
	private final static String DIRETORIO_IN = "data\\in\\";
	private final static String VAR_HOME = "HOMEPATH";
	
	@Test
	void test() {
		//fail("Not yet implemented");
		
		ArrayList<String> arquivos = new ArrayList<String>();
		String diretorioIn = System.getenv().get(VAR_HOME) + "\\" + DIRETORIO_IN;
		
		try {
			ExtracaoDados.lerDiretorio(diretorioIn, EXTENSAO_IN, arquivos);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(!arquivos.isEmpty());
		//ExtracaoDados.lerArquivo(DIRETORIO_IN, arquivo, linhas);

	}

}

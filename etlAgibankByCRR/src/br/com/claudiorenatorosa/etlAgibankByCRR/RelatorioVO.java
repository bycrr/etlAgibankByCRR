package br.com.claudiorenatorosa.etlAgibankByCRR;

import java.util.HashMap;
import java.util.Iterator;

public final class RelatorioVO {
	
	private HashMap<String, Float> vendedores = new HashMap<String, Float>();
	private HashMap<String, Integer> clientes = new HashMap<String, Integer>();
	private HashMap<String, Float> vendas = new HashMap<String, Float>();
	private Integer qtdVendedores = 0;
	private Integer qtdClientes = 0;
	
	public void adicionarVendedor(String vendedor) {
		if (!vendedores.containsKey(vendedor)) {
			this.vendedores.put(vendedor, 0f);
			aumentarVendedor();
		}
	}

	public void adicionarCliente(String cliente) {
		if (!clientes.containsKey(cliente)) {
			this.clientes.put(cliente, 0);		
			aumentarCliente();
		}
	}
	
	public void aumentarVendedor() {
		this.qtdVendedores++;		
	}

	public void aumentarCliente() {
		this.qtdClientes++;		
	}
	
	public void computarVenda(String venda, Float valor, String vendedor) throws Exception {
		if (!vendedores.containsKey(vendedor)) {
			throw new Exception("Vendedor: " + vendedor + " não encontrado no arquivo!");
		}
		adicionarVenda( venda, valor);
		adicionarVendaVendedor( vendedor, valor);
	}
	
	public void adicionarVenda(String venda, Float valor) {
		if (vendas.containsKey(venda)) {
			valor += vendas.get(venda);
		}
		this.vendas.put(venda, valor);
	}
	
	public void adicionarVendaVendedor(String vendedor, Float valor) {
		valor += vendedores.get(vendedor);
		this.vendedores.put(vendedor, valor);
	}

	public Integer getQtdClientes() {
		return qtdClientes;
	}
	
	public Integer getQtdVendedores() {
		return qtdVendedores;
	}
	
	public String getVendaMaisCara() {
		Float maiorValor = 0f;
		Float valor = 0f;
		String maiorVenda = null;
		Iterator<String> soma = vendas.keySet().iterator();
		  
		while (soma.hasNext()) {
			String venda = soma.next();
		    valor = vendas.get(venda);
		    if (valor > maiorValor) {
		    	maiorValor = valor;
		    	maiorVenda = venda;
		    }
		}
		return maiorVenda;
	}
	
	public String getPiorVendedor() {
		Float menorValor = 0f;
		Float valor = 0f;
		String piorVendedor = null;
		Iterator<String> soma = vendedores.keySet().iterator();
		  
		while (soma.hasNext()) {
			String vendedor = soma.next();
		    valor = vendedores.get(vendedor);
		    if (valor < menorValor || menorValor == 0f) {
		    	menorValor = valor;
		    	piorVendedor = vendedor;
		    }
		}
		return piorVendedor;
	}
	
	public void limparRalatorio() {
		this.vendedores.clear();
		this.clientes.clear();
		this.vendas.clear();
		this.qtdVendedores = 0;
		this.qtdClientes = 0;
	}
}

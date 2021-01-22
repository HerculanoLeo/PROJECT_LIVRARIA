package br.com.herculano.livararia_api_rest.constants;

public enum ConfiguracaoEnum {

	PERFIL_ROOT_PADRAO("perfil.root.padrao"),
	PERFIL_ADMINISTADOR_PADRAO("perfil.administrador.padrao"),
	PERFIL_CLIENTE_PADRAO("perfil.cliente.padrao")
	
	
	
	
	;

	private String valor;
	
	ConfiguracaoEnum(String valor) {
		this.valor = valor;
	}
	
	public String getValor() {
		return this.valor;
	}
}

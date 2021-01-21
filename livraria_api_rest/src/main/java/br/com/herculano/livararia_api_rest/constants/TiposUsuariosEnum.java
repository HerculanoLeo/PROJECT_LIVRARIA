package br.com.herculano.livararia_api_rest.constants;

public enum TiposUsuariosEnum {

	ROOT("R", "Root"),
	ADMINISTRADOR("A", "Adminsitrador"),
	OPERADOR("O", "Operdador"),
	CLIENTE("C", "Cliente");
	
	private String valor;
	
	private String descricao;
	
	TiposUsuariosEnum(String valor, String descricao) {
		this.valor = valor;
		this.descricao = descricao;
	}
	
	public String getValor() {
		return this.valor;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public static TiposUsuariosEnum getTipoUsuario(String valor) {
		TiposUsuariosEnum[] enums = TiposUsuariosEnum.values();
		
		for (TiposUsuariosEnum e : enums) {
			if(e.valor.equals(valor)) {
				return e;
			}
		}
		
		throw new RuntimeException("Enum does not exist with value.");
	}
	
}

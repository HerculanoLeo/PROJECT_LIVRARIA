package br.com.herculano.livararia_api_rest.constants;

public enum CodigoRecuperaSenhaStatusEnum {

	ATIVO("A", "Ativo."),
	EXPIRADO("E", "Expirado");
	
	private String valor;
	
	private String descricao;
	
	CodigoRecuperaSenhaStatusEnum(String valor, String descricao) {
		this.valor = valor;
		this.descricao = descricao;
	}
	
	public String getValor() {
		return this.valor;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public static CodigoRecuperaSenhaStatusEnum getTipoUsuario(String valor) {
		CodigoRecuperaSenhaStatusEnum[] enums = CodigoRecuperaSenhaStatusEnum.values();
		
		for (CodigoRecuperaSenhaStatusEnum e : enums) {
			if(e.valor.equals(valor)) {
				return e;
			}
		}
		
		throw new RuntimeException("Enum does not exist with value.");
	}
}

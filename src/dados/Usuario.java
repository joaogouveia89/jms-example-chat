package dados;

public class Usuario {
	private String codigo;
	private String nome;
	
	public Usuario(String nome) {
		super();
		this.nome = nome;
		generateCodigo();
	}

	public Usuario() {
		super();
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	private void generateCodigo() {
		codigo = "";
		if(this.nome != null) {
			for(int  i = 0; i < nome.length(); i++) {
				int charCode = (int)nome.charAt(i);
				codigo = codigo + charCode;
			}
		}
	}
	
}	

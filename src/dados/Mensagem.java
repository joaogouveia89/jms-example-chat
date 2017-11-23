package dados;

public class Mensagem {
	private String remetente;
	private String destino;
	private String mensagem;
	
	
	public Mensagem(String remetente, String destino, String mensagem) {
		super();
		this.remetente = remetente;
		this.destino = destino;
		this.mensagem = mensagem;
	}
	
	
	public Mensagem() {
		super();
	}

	public String getRemetente() {
		return remetente;
	}
	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	@Override
	public String toString() {
		return this.getRemetente() +
				" para " +
				this.getDestino() + 
				": " +
				this.getMensagem();
	}
	
	
}

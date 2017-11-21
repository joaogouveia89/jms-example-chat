package dados;

public class Mensagem {
	private Usuario remetente;
	private Usuario destino;
	private String mensagem;
	
	
	public Mensagem(Usuario remetente, Usuario destino, String mensagem) {
		super();
		this.remetente = remetente;
		this.destino = destino;
		this.mensagem = mensagem;
	}
	
	
	public Mensagem() {
		super();
	}

	public Usuario getRemetente() {
		return remetente;
	}
	public void setRemetente(Usuario remetente) {
		this.remetente = remetente;
	}
	public Usuario getDestino() {
		return destino;
	}
	public void setDestino(Usuario destino) {
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
		return this.getRemetente().getNome() +
				" para " +
				this.getDestino().getNome() + 
				": " +
				this.getMensagem();
	}
	
	
}

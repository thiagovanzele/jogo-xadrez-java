package tabuleiro;

public class TabuleiroExcessao extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public TabuleiroExcessao(String msg) {
		super(msg);
	}

}

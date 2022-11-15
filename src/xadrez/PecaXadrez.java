package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;

public abstract class PecaXadrez extends Peca {

	private Cores cor;
	private int contaMovimento;

	public PecaXadrez(Tabuleiro tabuleiro, Cores cor) {
		super(tabuleiro);
		this.cor = cor;
		contaMovimento = 0;
	}

	public Cores getCor() {
		return cor;
	}
	
	public int getContaMovimento() {
		return contaMovimento;
	}
	
	public void incrementaContaMovimento() {
		contaMovimento++;
	}
	
	public void decrementaContaMovimento() {
		contaMovimento--;
	}
	
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.daPosicao(posicao);
	}
	
	protected boolean existeUmaPecaAdversaria(Posicao posicao) {
		PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor;
	}
}

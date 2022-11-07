package xadrez;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	private static Tabuleiro tabuleiro;

	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		configInicial();
	}

	public PecaXadrez[][] getPecas() {
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}
	
	public static PecaXadrez executarMovimentoXadrez(PosicaoXadrez posicaoInicial, PosicaoXadrez posicaoDestino) {
		Posicao inicial = posicaoInicial.paraPosicao();
		Posicao destino = posicaoDestino.paraPosicao();
		validaPosicaoInicial(inicial);
		Peca pecaCapturada = fazerMover(inicial, destino);
		return (PecaXadrez)pecaCapturada;
	}
	
	private static Peca fazerMover(Posicao inicial, Posicao destino) {
		Peca p = tabuleiro.removePeca(inicial);
		Peca pecaCapturada = tabuleiro.removePeca(destino);
		tabuleiro.colocarPeca(p, destino);
		return pecaCapturada;
	}
	
	private static void validaPosicaoInicial(Posicao posicao) {
		if(!tabuleiro.haUmapeca(posicao)) {
			throw new ExcessaoXadrez("Nao ha peca na posicao de origem");
		}
	}

	private void coloqueNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
	}

	private void configInicial() {
		coloqueNovaPeca('c', 1, new Torre(tabuleiro, Cores.BRANCO));
		coloqueNovaPeca('c', 2, new Torre(tabuleiro, Cores.BRANCO));
		coloqueNovaPeca('d', 2, new Torre(tabuleiro, Cores.BRANCO));
		coloqueNovaPeca('e', 2, new Torre(tabuleiro, Cores.BRANCO));
		coloqueNovaPeca('e', 1, new Torre(tabuleiro, Cores.BRANCO));
		coloqueNovaPeca('d', 1, new Rei(tabuleiro, Cores.BRANCO));

		coloqueNovaPeca('c', 7, new Torre(tabuleiro, Cores.PRETO));
		coloqueNovaPeca('c', 8, new Torre(tabuleiro, Cores.PRETO));
		coloqueNovaPeca('d', 7, new Torre(tabuleiro, Cores.PRETO));
		coloqueNovaPeca('e', 7, new Torre(tabuleiro, Cores.PRETO));
		coloqueNovaPeca('e', 8, new Torre(tabuleiro, Cores.PRETO));
		coloqueNovaPeca('d', 8, new Rei(tabuleiro, Cores.PRETO));
	}
}

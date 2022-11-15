package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import tabuleiro.Peca;
import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {

	private static int turno;
	private static Cores playerAtual;
	private static Tabuleiro tabuleiro;
	private static boolean check;
	private static boolean checkMate;


	private static List<Peca> pecasNoTabuleiro = new ArrayList<>();
	private static List<Peca> pecasCapturadas = new ArrayList<>();

	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		playerAtual = Cores.BRANCO;
		configInicial();
		check = false;
	}

	public int getTurno() {
		return turno;
	}

	public Cores getPlayerAtual() {
		return playerAtual;
	}

	public static boolean getCheck() {
		return check;
	}

	public static boolean getCheckMate() {
		return checkMate;
	}

	public static PecaXadrez[][] getPecas() {
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;
	}

	public static boolean[][] movimentosPossiveis(PosicaoXadrez posicaoOrigem) {
		Posicao posicao = posicaoOrigem.paraPosicao();
		validaPosicaoInicial(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();
	}

	public static PecaXadrez executarMovimentoXadrez(PosicaoXadrez posicaoInicial, PosicaoXadrez posicaoDestino) {
		Posicao inicial = posicaoInicial.paraPosicao();
		Posicao destino = posicaoDestino.paraPosicao();
		validaPosicaoInicial(inicial);
		validaPosicaoDestino(inicial, destino);
		Peca pecaCapturada = fazerMover(inicial, destino);

		if (testeCheck(playerAtual)) {
			desfazerMovimento(inicial, destino, pecaCapturada);
			throw new ExcessaoXadrez("Voce nao pode colocar voce mesmo em check");
		}

		check = (testeCheck(oponente(playerAtual))) ? true : false;

		if (testeCheckMate(oponente(playerAtual))) {
			checkMate = true;
		} else {
			trocaTurno();
		}
		return (PecaXadrez) pecaCapturada;
	}

	private static Peca fazerMover(Posicao inicial, Posicao destino) {
		PecaXadrez p = (PecaXadrez)tabuleiro.removePeca(inicial);
		p.incrementaContaMovimento();
		Peca pecaCapturada = tabuleiro.removePeca(destino);
		tabuleiro.colocarPeca(p, destino);

		if (pecaCapturada != null) {
			pecasNoTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);
		}
		return pecaCapturada;
	}

	private static void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
		PecaXadrez p = (PecaXadrez)tabuleiro.removePeca(destino);
		p.decrementaContaMovimento();
		tabuleiro.colocarPeca(p, origem);

		if (pecaCapturada != null) {
			tabuleiro.colocarPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasNoTabuleiro.add(pecaCapturada);
		}
	}

	private static void validaPosicaoInicial(Posicao posicao) {
		if (!tabuleiro.haUmapeca(posicao)) {
			throw new ExcessaoXadrez("Nao ha peca na posicao de origem");
		}
		if (playerAtual != ((PecaXadrez) tabuleiro.peca(posicao)).getCor()) {
			throw new ExcessaoXadrez("A peca escolhida nao e sua");
		}
		if (!tabuleiro.peca(posicao).existeAlgumMovimentoPossivel()) {
			throw new ExcessaoXadrez("Nao existem movimentos possiveis para peca escolhida");
		}
	}

	private static void validaPosicaoDestino(Posicao origem, Posicao destino) {
		if (!tabuleiro.peca(origem).movimentoPossivel(destino)) {
			throw new ExcessaoXadrez("A peca escolhida nao pode se mover para posicao de destino");
		}
	}

	private static void trocaTurno() {
		turno++;
		playerAtual = (playerAtual == Cores.BRANCO) ? Cores.PRETO : Cores.BRANCO;
	}

	private static Cores oponente(Cores cor) {
		return (cor == Cores.BRANCO) ? Cores.PRETO : Cores.BRANCO;
	}

	private static PecaXadrez rei(Cores cor) {
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof Rei) {
				return (PecaXadrez) p;
			}
		}
		throw new IllegalStateException("Nao existe rei da cor " + cor + "no tabuleiro");
	}

	private static boolean testeCheck(Cores cor) {
		Posicao posicaoRei = rei(cor).getPosicaoXadrez().paraPosicao();
		List<Peca> pecasOpontente = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == oponente(cor))
				.collect(Collectors.toList());
		for (Peca p : pecasOpontente) {
			boolean[][] mat = p.movimentosPossiveis();
			if (mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private static boolean testeCheckMate(Cores cor) {
		if (!testeCheck(cor)) {
			return false;
		}
		List<Peca> list = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			boolean[][] mat = p.movimentosPossiveis();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getColunas(); j++) {
					if (mat[i][j]) {
						Posicao origem = ((PecaXadrez) p).getPosicaoXadrez().paraPosicao();
						Posicao destino = new Posicao(i, j);
						Peca pecaCapturada = fazerMover(origem, destino);
						boolean testCheck = testeCheck(cor);
						desfazerMovimento(origem, destino, pecaCapturada);
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private void coloqueNovaPeca(char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocarPeca(peca, new PosicaoXadrez(coluna, linha).paraPosicao());
		pecasNoTabuleiro.add(peca);
	}

	private void configInicial() {
		coloqueNovaPeca('h', 7, new Torre(tabuleiro, Cores.BRANCO));
		coloqueNovaPeca('d', 1, new Torre(tabuleiro, Cores.BRANCO));
		coloqueNovaPeca('e', 1, new Rei(tabuleiro, Cores.BRANCO));

		coloqueNovaPeca('b', 8, new Torre(tabuleiro, Cores.PRETO));
		coloqueNovaPeca('a', 8, new Rei(tabuleiro, Cores.PRETO));
	}
}

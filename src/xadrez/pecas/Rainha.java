package xadrez.pecas;

import tabuleiro.Posicao;
import tabuleiro.Tabuleiro;
import xadrez.Cores;
import xadrez.PecaXadrez;

public class Rainha extends PecaXadrez{

	public Rainha(Tabuleiro tabuleiro, Cores cor) {
		super(tabuleiro, cor);
	}

	@Override
	public String toString() {
		return "Q";
	}
	
	@Override
	public boolean[][] movimentosPossiveis() {
		boolean [][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0, 0);
		
		// cima
		p.setValues(posicao.getLinha() - 1, posicao.getColuna());
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmapeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() - 1);
		}
		if (getTabuleiro().posicaoExiste(p) && existeUmaPecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// baixo
		p.setValues(posicao.getLinha() +1, posicao.getColuna());
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmapeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() + 1);
		}
		if (getTabuleiro().posicaoExiste(p) && existeUmaPecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// esquerda
		p.setValues(posicao.getLinha(), posicao.getColuna() - 1);
		while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmapeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() - 1);
		}
		if (getTabuleiro().posicaoExiste(p) && existeUmaPecaAdversaria(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		// direita
		p.setValues(posicao.getLinha(), posicao.getColuna() + 1);
		while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmapeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() + 1);
		}
		if (getTabuleiro().posicaoExiste(p) && existeUmaPecaAdversaria(p)) {
			mat[p.getLinha()] [p.getColuna()] = true;
		}
		
		// noroeste
				p.setValues(posicao.getLinha() - 1, posicao.getColuna() -1 );
				while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmapeca(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setValues(p.getLinha() -1	, p.getColuna() -1 );
				}
				if (getTabuleiro().posicaoExiste(p) && existeUmaPecaAdversaria(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
				
				// sudoeste
				p.setValues(posicao.getLinha() +1, posicao.getColuna() -1);
				while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmapeca(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setValues(p.getLinha() +1, p.getColuna() -1);;
				}
				if (getTabuleiro().posicaoExiste(p) && existeUmaPecaAdversaria(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
				
				// nordeste
				p.setValues(posicao.getLinha() -1 , posicao.getColuna() +1);
				while (getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmapeca(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setValues(p.getLinha() -1, p.getColuna() +1);
				}
				if (getTabuleiro().posicaoExiste(p) && existeUmaPecaAdversaria(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
				}
				
				// sudeste
				p.setValues(posicao.getLinha() +1, posicao.getColuna() + 1);
				while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().haUmapeca(p)) {
					mat[p.getLinha()][p.getColuna()] = true;
					p.setValues(p.getLinha() +1, p.getColuna() +1);;
				}
				if (getTabuleiro().posicaoExiste(p) && existeUmaPecaAdversaria(p)) {
					mat[p.getLinha()] [p.getColuna()] = true;
				}
		
		return mat;
	}
}

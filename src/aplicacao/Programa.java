package aplicacao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.ExcessaoXadrez;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.PosicaoXadrez;

public class Programa {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		PartidaXadrez partida = new PartidaXadrez();
		List<PecaXadrez> capturada = new ArrayList<>();

		while (!PartidaXadrez.getCheckMate()) {
			try {
				UI.limpaTela();
				UI.imprimePartida(partida, capturada);
				System.out.println();
				System.out.print("Origem: ");
				PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
				
				boolean [][] movimentosPossiveis = PartidaXadrez.movimentosPossiveis(origem);
				UI.limpaTela();
				UI.imprimeTabuleiro(PartidaXadrez.getPecas(),movimentosPossiveis);

				System.out.println();
				System.out.print("Destino: ");
				PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);

				PecaXadrez pecaCapturada = PartidaXadrez.executarMovimentoXadrez(origem, destino);
				
				if (pecaCapturada != null) {
					capturada.add(pecaCapturada);
				}
			} catch (ExcessaoXadrez e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			} catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
		}
		UI.limpaTela();
		UI.imprimePartida(partida, capturada);
	}

}

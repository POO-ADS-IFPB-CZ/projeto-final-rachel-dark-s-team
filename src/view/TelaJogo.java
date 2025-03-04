package src.view;

import javax.swing.*;
import java.awt.*;

public class TelaJogo extends JDialog {
    private JButton[][] botoes;
    private int tamanho;
    private String jogador1;
    private String jogador2;
    private String jogadorAtual;
    private JPanel contentPane;

    public TelaJogo(int tamanho, String jogador1, String jogador2) {
        this.tamanho = tamanho;
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        this.jogadorAtual = jogador1;

        setContentPane(contentPane);
        setModal(true);
        setSize(400,400);
        setLayout(new BorderLayout());
        JPanel tabuleiro = new JPanel();
        tabuleiro.setLayout(new GridLayout(tamanho, tamanho));

        botoes = new JButton[tamanho][tamanho];
        for (int i = 0; i < tamanho; i++){
            for (int j = 0; j < tamanho; j++){
                botoes[i][j] = new JButton(" ");
                botoes[i][j].setFont(new Font("Arial", Font.BOLD,30));
                tabuleiro.add(botoes[i][j]);
                final int x = i, y = j;
                botoes[i][j].addActionListener(e -> realizaJogada(x,y));
            }
        }
        add(tabuleiro, BorderLayout.CENTER);
        setLocationRelativeTo(null);
    }

    private void realizaJogada(int x, int y) {
        if (!botoes[x][y].getText().equals(" ")){
            return;
        }
        botoes[x][y].setText(jogadorAtual.equals(jogador1) ? "X" : "O");
        jogadorAtual = jogadorAtual.equals(jogador1) ? jogador2 : jogador1;
    }
}

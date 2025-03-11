package src.view;

import src.dao.JogadorDao;
import src.model.Jogador;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class TelaJogo extends JDialog {
    private JButton[][] botoes;
    private String jogadorAtual;
    private String jogador1, jogador2;
    private int tamanho;
    private JogadorDao jogadorDao;

    public TelaJogo(String jogador1, String jogador2, int tamanho) {
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        this.tamanho = tamanho;
        this.jogadorAtual = jogador1;
        this.jogadorDao = new JogadorDao();

        setTitle("Jogo da Velha");
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel painelTabuleiro = new JPanel(new GridLayout(tamanho, tamanho, 5, 5));
        painelTabuleiro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        botoes = new JButton[tamanho][tamanho];
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                botoes[i][j] = new JButton(" ");
                botoes[i][j].setFont(new Font("Arial", Font.BOLD, 28));
                botoes[i][j].setFocusPainted(false);
                botoes[i][j].setBackground(Color.WHITE);
                botoes[i][j].setPreferredSize(new Dimension(80, 80));
                painelTabuleiro.add(botoes[i][j]);

                final int x = i, y = j;
                botoes[i][j].addActionListener(e -> realizarJogada(x, y));
            }
        }

        add(painelTabuleiro, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    private void realizarJogada(int x, int y) {
        if (!botoes[x][y].getText().equals(" ")) return;

        botoes[x][y].setText(jogadorAtual.equals(jogador1) ? "X" : "O");
        botoes[x][y].setForeground(jogadorAtual.equals(jogador1) ? Color.RED : Color.BLUE);

        if (verificarVencedor()) {
            JOptionPane.showMessageDialog(this, "Vencedor: " + jogadorAtual);
            atualizarPlacar(jogadorAtual, jogadorAtual.equals(jogador1) ? jogador2 : jogador1, false);
            dispose();
            return;
        }

        if (verificarEmpate()) {
            JOptionPane.showMessageDialog(this, "O jogo terminou em empate!");
            atualizarPlacar(null, null, true);
            dispose();
            return;
        }

        jogadorAtual = jogadorAtual.equals(jogador1) ? jogador2 : jogador1;
    }

    private void atualizarPlacar(String vencedor, String perdedor, boolean empate) {
        try {
            List<Jogador> jogadores = jogadorDao.getJogadores();
            for (Jogador j : jogadores) {
                if (empate) {
                    if (j.getNome().equals(jogador1) || j.getNome().equals(jogador2)) {
                        j.adicionarEmpate();
                    }
                } else {
                    if (j.getNome().equals(vencedor)) {
                        j.adicionarVitoria();
                    } else if (j.getNome().equals(perdedor)) {
                        j.adicionarDerrota();
                    }
                }
            }
            jogadorDao.atualizarArquivo(jogadores);
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar placar.");
        }
    }


    private boolean verificarVencedor() {
        for (int i = 0; i < tamanho; i++) {
            if (verificaLinha(i) || verificaColuna(i)) return true;
        }
        return verificaDiagonalPrincipal() || verificaDiagonalSecundaria();
    }

    private boolean verificarEmpate() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                if (botoes[i][j].getText().equals(" ")) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean verificaLinha(int linha) {
        String primeiro = botoes[linha][0].getText();
        if (primeiro.equals(" ")) return false;
        for (int j = 1; j < tamanho; j++) {
            if (!botoes[linha][j].getText().equals(primeiro)) return false;
        }
        return true;
    }

    private boolean verificaColuna(int coluna) {
        String primeiro = botoes[0][coluna].getText();
        if (primeiro.equals(" ")) return false;
        for (int i = 1; i < tamanho; i++) {
            if (!botoes[i][coluna].getText().equals(primeiro)) return false;
        }
        return true;
    }

    private boolean verificaDiagonalPrincipal() {
        String primeiro = botoes[0][0].getText();
        if (primeiro.equals(" ")) return false;
        for (int i = 1; i < tamanho; i++) {
            if (!botoes[i][i].getText().equals(primeiro)) return false;
        }
        return true;
    }

    private boolean verificaDiagonalSecundaria() {
        String primeiro = botoes[0][tamanho - 1].getText();
        if (primeiro.equals(" ")) return false;
        for (int i = 1; i < tamanho; i++) {
            if (!botoes[i][tamanho - 1 - i].getText().equals(primeiro)) return false;
        }
        return true;
    }
}

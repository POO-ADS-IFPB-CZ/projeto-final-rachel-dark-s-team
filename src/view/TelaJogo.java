package src.view;

import src.dao.JogadorDao;
import src.model.Jogador;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TelaJogo extends JDialog {
    private JButton[][] botoes;
    private int tamanho;
    private String jogador1;
    private String jogador2;
    private String jogadorAtual;
    private JPanel contentPane;
    private JogadorDao jogadorDao;

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
        if (verificarVencedor()) {
            JOptionPane.showMessageDialog(this, "Vencedor: " + jogadorAtual);
            atualizarPlacar(jogadorAtual, jogadorAtual.equals(jogador1) ? jogador2 : jogador1);
            dispose();
            new TelaInicial().setVisible(true);
            return;
        }

        if (verificarEmpate()) {
            JOptionPane.showMessageDialog(this, "O jogo terminou em empate!");
            atualizarPlacarEmpate();
            dispose();
            new TelaInicial().setVisible(true);
            return;
        }

        jogadorAtual = jogadorAtual.equals(jogador1) ? jogador2 : jogador1;
    }

    private void atualizarPlacarEmpate() {
        try{
            List<Jogador> jogadores = jogadorDao.getJogadores();
            System.out.println(jogadores);
            if (jogadores == null){
                jogadores = new ArrayList<>();
            }
            for (Jogador j : jogadores){
                if (j.getNome().equals(jogador1) || j.getNome().equals(jogador2)){
                    j.adicionarEmpate();
                }
            }
            jogadorDao.atualizarArquivo(jogadores);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void atualizarPlacar(String vencedor, String perdedor) {
        try {
            List<Jogador> jogadores = jogadorDao.getJogadores();
            if (jogadores == null){
                jogadores = new ArrayList<>();
            }
            for (Jogador j : jogadores){
                if(j.getNome().equals(vencedor)){
                    j.adicionarVitoria();
                }else if(j.getNome().equals(perdedor)){
                    j.adicionarDerrota();
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
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


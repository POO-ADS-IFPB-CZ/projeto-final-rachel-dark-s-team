package src.view;

import src.dao.JogadorDao;
import src.model.Jogador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class TelaInicial extends JDialog {
    private JPanel contentPane;
    private JButton buttonJogar;
    private JButton buttonPlacar;
    private JTextField campoJogador1;
    private JTextField campoJogador2;
    private JButton opçõesButton;
    private Integer tamanhoTabuleiro = 3;

    public TelaInicial() {
        setTitle("Jogo da Velha");
        setSize(400,400);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonJogar);
        JogadorDao jogadorDao = new JogadorDao();
        setLocationRelativeTo(null);


        buttonJogar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome1 = campoJogador1.getText();
                String nome2 = campoJogador2.getText();

                if(nome1.isEmpty() || nome2.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Informe o nome dos jogadores");
                    return;
                }
                Jogador jogador1 = new Jogador(nome1,0,0,0);
                Jogador jogador2 = new Jogador(nome2,0,0,0);

                try {
                    if(!jogadorDao.getJogadores().contains(jogador1)){
                        jogadorDao.adicionarJogador(jogador1);
                    }
                    if(!jogadorDao.getJogadores().contains(jogador2)){
                        jogadorDao.adicionarJogador(jogador2);
                    }
                } catch (IOException | ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(null,"Erro ao salvar jogadores");
                        return;
                }
                TelaJogo telaJogo = new TelaJogo(nome1,nome2, tamanhoTabuleiro);
                telaJogo.setVisible(true);

            }
        });

        buttonPlacar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaPlacar telaPlacar = new TelaPlacar();
                telaPlacar.setVisible(true);
            }
        });
        opçõesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaOpcoes telaOpcoes = new TelaOpcoes(null);
                telaOpcoes.setVisible(true);
                tamanhoTabuleiro = telaOpcoes.getTamanhoTabuleiro();

                if (tamanhoTabuleiro != null) {
                    JOptionPane.showMessageDialog(null, "Tabuleiro ajustado para " + tamanhoTabuleiro + "x" + tamanhoTabuleiro);
                }
            }
        });

    }

    public static void main(String[] args) {
        TelaInicial dialog = new TelaInicial();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}

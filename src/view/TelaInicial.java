package src.view;

import src.dao.JogadorDao;
import src.model.Jogador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class TelaInicial extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField campoJogador1;
    private JTextField campoJogador2;

    public TelaInicial() {
        setTitle("Jogo da Velha");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        JogadorDao jogadorDao = new JogadorDao();
        buttonOK.addActionListener(new ActionListener() {
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
                    jogadorDao.adicionarJogador(jogador1);
                    jogadorDao.adicionarJogador(jogador2);
                } catch (IOException | ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(null,"Erro ao salvar jogadores");
                        return;
                }
                JOptionPane.showMessageDialog(null,"Aparecer tela jogo");

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

package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome1 = campoJogador1.getText();
                String nome2 = campoJogador2.getText();
                if(nome1.isEmpty() || nome2.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Informe o nome dos jogadores");
                } else{
                    JOptionPane.showMessageDialog(null,"Aparecer tela jogo");
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

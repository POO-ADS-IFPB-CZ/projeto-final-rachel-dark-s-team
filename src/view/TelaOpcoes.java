package src.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaOpcoes extends JDialog {
    private JTextField inputTamanho;
    private JButton confirmarButton;
    private Integer tamanhoTabuleiro;

    public TelaOpcoes(JFrame parent) {
        super(parent, "Escolher Tamanho", true);
        setSize(300, 150);
        setLayout(new FlowLayout());

        JLabel label = new JLabel("Digite o tamanho do tabuleiro (3 a 10):");
        inputTamanho = new JTextField(5);
        confirmarButton = new JButton("Confirmar");

        add(label);
        add(inputTamanho);
        add(confirmarButton);

        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int novoTamanho = Integer.parseInt(inputTamanho.getText().trim());

                    if (novoTamanho >= 3 && novoTamanho <= 10) {
                        tamanhoTabuleiro = novoTamanho;
                        JOptionPane.showMessageDialog(TelaOpcoes.this, "Tabuleiro definido como " + tamanhoTabuleiro + "x" + tamanhoTabuleiro);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(TelaOpcoes.this, "Escolha um tamanho entre 3 e 10.");
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(TelaOpcoes.this, "Digite um número válido.");
                }
            }
        });
    }

    public Integer getTamanhoTabuleiro() {
        return tamanhoTabuleiro;
    }
}

package src.view;

import src.dao.JogadorDao;
import src.model.Jogador;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class TelaPlacar extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JScrollPane scroll;
    private DefaultListModel<String> listModel;
    private JogadorDao jogadorDao;
    private JList<String> listaJogadores;

    public TelaPlacar() {
        setContentPane(contentPane);
        setSize(400,400);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        jogadorDao = new JogadorDao();
        listModel = new DefaultListModel<>();
        listaJogadores = new JList<>(listModel);
        atualizarLista();
        scroll.setViewportView(listaJogadores);
        setTitle("Placar de Jogadores");
    }

    private void atualizarLista(){
        try {
            List<Jogador> jogadores = jogadorDao.getJogadores();
            listModel.clear();
            for (Jogador j : jogadores){
                listModel.addElement(j.getNome() + " - Vitórias: "+ j.getVitorias() + ", Empates: "+j.getEmpates()+", Derrotas: " + j.getDerrotas());
            }
        }catch (IOException | ClassNotFoundException e){
            JOptionPane.showMessageDialog(this,"Erro ao carregar jogadores.");
        }
    }

    public static void main(String[] args) {
        TelaPlacar dialog = new TelaPlacar();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}

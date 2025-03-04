package src.view;

import src.dao.JogadorDao;
import src.model.Jogador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class TelaPlacar extends JDialog {
    private JPanel contentPane;
    private JButton buttonEditar;
    private JButton buttonDelete;
    private JScrollPane scroll;
    private DefaultListModel<String> listModel;
    private JogadorDao jogadorDao;
    private JList<String> listaJogadores;

    public TelaPlacar() {
        setContentPane(contentPane);
        setSize(400,400);
        setModal(true);
        jogadorDao = new JogadorDao();
        listModel = new DefaultListModel<>();
        listaJogadores = new JList<>(listModel);
        atualizarLista();
        scroll.setViewportView(listaJogadores);
        setTitle("Placar de Jogadores");

        buttonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = listaJogadores.getSelectedIndex();
                if(index == -1){
                    JOptionPane.showMessageDialog(null,"Selecione um jogador!");
                    return;
                }
                String nomeAtual = listModel.get(index).split(" - ")[0];
                String novoNome = JOptionPane.showInputDialog(null,"Novo nome: ", nomeAtual);
                System.out.println(nomeAtual);
                System.out.println(novoNome);

                if (novoNome != null && !novoNome.trim().isEmpty()){
                    try{
                      List<Jogador> jogadores = jogadorDao.getJogadores();
                      for(Jogador jogador : jogadores){
                          if(jogador.getNome().equals(nomeAtual)){
                              jogador.setNome(novoNome.trim());
                              jogadorDao.atualizarJogador(nomeAtual,novoNome);
                              break;
                          }
                      }
                      atualizarLista();
                    } catch (IOException | ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(null,"Erro ao editar jogador");
                    }
                }
            }
        });
        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = listaJogadores.getSelectedIndex();
                if(index != -1) {
                    String nomeJogador = listModel.get(index).split(" - ")[0];

                    int confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover " + nomeJogador + "?", "Confirmar remoção", JOptionPane.YES_NO_OPTION);

                    if (confirmacao == JOptionPane.YES_OPTION) {
                        try {
                            boolean removido = jogadorDao.removerJogador((nomeJogador));
                            if (removido) {
                                atualizarLista();
                                JOptionPane.showMessageDialog(null, "Jogador removido!");
                            } else {
                                JOptionPane.showMessageDialog(null, "Erro ao remover jogador.");
                            }
                        } catch (IOException | ClassNotFoundException ex) {
                            JOptionPane.showMessageDialog(null, "Erro ao acessar arquivo.");
                        }
                    }
                } else{
                    JOptionPane.showMessageDialog(null,"Selecione um jogador para remover.");
                }

            }
        });
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

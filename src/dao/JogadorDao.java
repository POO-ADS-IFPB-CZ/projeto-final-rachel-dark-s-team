package src.dao;

import src.model.Jogador;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JogadorDao {
    private final File arquivo;

    public JogadorDao() {
        arquivo = new File("jogadores.dat");

        if (!arquivo.exists()) {
            try {
                arquivo.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Erro ao criar o arquivo de jogadores", e);
            }
        }
    }

    public boolean adicionarJogador(Jogador jogador) throws IOException, ClassNotFoundException {
        List<Jogador> jogadores = getJogadores();
        if (!jogadores.contains(jogador)) {
            jogadores.add(jogador);
            atualizarArquivo(jogadores);
            System.out.println("Jogador salvo: " + jogador.getNome());
            return true;
        }
        return false;
    }

    public boolean removerJogador(String nome) throws IOException, ClassNotFoundException {
        List<Jogador> jogadores = getJogadores();
        boolean removido = jogadores.removeIf(j -> j.getNome().equals(nome));

        if (removido) {
            atualizarArquivo(jogadores);
        }

        return removido;
    }

    public boolean atualizarJogador(String nomeAntigo, String novoNome) throws IOException, ClassNotFoundException {
        List<Jogador> jogadores = getJogadores();
        for (Jogador j : jogadores) {
            if (j.getNome().equals(nomeAntigo)) {
                j.setNome(novoNome);
                atualizarArquivo(jogadores);
                return true;
            }
        }
        return false;
    }

    public void atualizarArquivo(List<Jogador> jogadores) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            out.writeObject(new ArrayList<>(jogadores));
        } catch (IOException e) {
            throw new IOException("Erro ao salvar os jogadores no arquivo.", e);
        }
    }

    public List<Jogador> getJogadores() throws IOException, ClassNotFoundException {
        if (!arquivo.exists() || arquivo.length() == 0) {
            System.out.println("Arquivo não encontrado ou vazio, retornando lista vazia.");
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            Object obj = in.readObject();
            if (obj instanceof List<?>) {
                return (List<Jogador>) obj;
            } else {
                System.out.println("Erro: O arquivo não contém uma lista de jogadores válida.");
                return new ArrayList<>();
            }
        } catch (EOFException e) {
            System.out.println("Arquivo vazio, retornando lista vazia.");
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new IOException("Erro ao ler o arquivo de jogadores.", e);
        }
    }
}





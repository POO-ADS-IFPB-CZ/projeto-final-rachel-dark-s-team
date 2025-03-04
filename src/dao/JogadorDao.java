package src.dao;

import src.model.Jogador;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JogadorDao {
    private File arquivo;
    public JogadorDao(){
        arquivo = new File("Jogadores");
        if(!arquivo.exists()){
            try{
                arquivo.createNewFile();
            } catch(IOException e){
                throw new RuntimeException();
            }
        }
    }

    public boolean adicionarJogador(Jogador jogador) throws  IOException, ClassNotFoundException{
        List<Jogador> jogadores = getJogadores();
        if(!jogadores.contains(jogador)){
            jogadores.add(jogador);
            atualizarArquivo(jogadores);
            System.out.println("Jogador salvo: " + jogador.getNome());
            return true;
        }
        return false;
    }


    public boolean removerJogador(String nome) throws IOException,
            ClassNotFoundException {
        List<Jogador> jogadores = getJogadores();
        boolean removido = jogadores.removeIf((j -> j.getNome().equals(nome)));
        if(removido){
            atualizarArquivo(jogadores);
            return true;
        }
        return removido;
    }

    public boolean atualizarJogador(String nomeAntigo, String novoNome) throws  IOException, ClassNotFoundException{
        List<Jogador> jogadores = getJogadores();
        for(Jogador j : jogadores){
            if(j.getNome().equals(nomeAntigo)){
                j.setNome(novoNome);
                atualizarArquivo(jogadores);
                return true;
            }
        }
        return false;
        // indice do jogador na lista. se não existe é -1.
        //int index = jogadores.indexOf(jogador);
        //if(index != -1){
        //    jogadores.set(index,jogador);
        //    atualizarArquivo(jogadores);
        //    return true;
       // }
        //return false;
    }

    private void atualizarArquivo(List<Jogador> jogadores)throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream((arquivo)))) {
            out.writeObject(jogadores);
        }
    }

    public List<Jogador> getJogadores() throws IOException, ClassNotFoundException {
        if (!arquivo.exists()) {
            System.out.println("Arquivo não encontrado, retornando lista vazia.");
            return new ArrayList<>();
        }

        if (arquivo.length() == 0) {
            System.out.println("Arquivo está vazio.");
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))) {
            Object obj = in.readObject();
            if (obj instanceof List<?>) {
                List<Jogador> jogadores = (List<Jogador>) obj;
                System.out.println("Jogadores carregados: " + jogadores.size());
                return jogadores;
            } else {
                System.out.println("Erro: O arquivo não contém uma lista de jogadores.");
                return new ArrayList<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            throw e;
        }
    }


}




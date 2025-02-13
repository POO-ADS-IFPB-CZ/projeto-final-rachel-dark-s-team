package src.dao;
import src.model.Jogador;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

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
        Set<Jogador> jogadores = getJogadores();
        if(jogadores.add(jogador)){
            atualizarArquivo(jogadores);
            return true;
        }
        return false;
    }


    public boolean removerJogador(Jogador jogador) throws IOException,
            ClassNotFoundException {
        Set<Jogador> jogadores = getJogadores();
        if(jogadores.remove(jogador)){
            atualizarArquivo(jogadores);
            return true;
        }
        return false;
    }

    public boolean atualizarJogador(Jogador jogador) throws  IOException, ClassNotFoundException{
        Set<Jogador> jogadores = getJogadores();
        if(jogadores.contains(jogador)){
            if(jogadores.remove(jogador) && jogadores.add(jogador)){
                atualizarArquivo(jogadores);
                return true;
            }
        }
        return false;
    }

    private void atualizarArquivo(Set<Jogador> jogadores)throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream((arquivo)))) {
            out.writeObject(jogadores);
        }
    }

    public Set<Jogador> getJogadores() throws IOException, ClassNotFoundException{
        if(arquivo.length()==0){
            return new HashSet<>();
        }
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivo))){
            return (Set<Jogador>) in.readObject();
        }
    }
}




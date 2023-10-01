package modelo;

import excecao.ExplosaoException;

import java.util.ArrayList;
import java.util.List;

public class Campo {
    private final int linha;
    private final int coluna;
    private boolean aberto = false;
    private boolean minado = false;
    private boolean marcado = false;
    private List<Campo> vizinhos = new ArrayList<>();
    public Campo(int linha, int coluna){
        this.linha = linha;
        this.coluna = coluna;
    }
    public boolean adicionarVizinho(Campo vizinho){
        boolean linhaDiferente = this.linha != vizinho.linha;
        boolean colunaDiferente = this.coluna != vizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(this.linha - vizinho.linha);
        int deltaColuna = Math.abs(this.coluna - vizinho.coluna);
        int deltaGeral = deltaLinha + deltaColuna;

        if(deltaGeral == 1 && !diagonal){
            vizinhos.add(vizinho);
            return true;
        } else if(deltaGeral == 2 && diagonal){
            vizinhos.add(vizinho);
            return true;
        }else {
            return false;
        }
    }
    public void alternarMarcacao(){
        if(!aberto ){
            marcado = !marcado;
        }
    }

    boolean abrir(){
        if(!aberto && !marcado){
            aberto = true;
            if(minado){
                throw new ExplosaoException();
            }
            if(vizinhancaSegura()){
                vizinhos.forEach(vizinho -> vizinho.abrir());
            }
            return true;
        }else{
            return false;
        }
    }

   public boolean vizinhancaSegura(){
        return vizinhos.stream().noneMatch(vizinho -> vizinho.minado);
    }
    boolean objetivoAlcancado(){
        boolean desvendado = !minado && aberto;
        boolean protegido = minado && marcado;
        return desvendado || protegido;
    }
    long minasNaVizinhanca(){
        return vizinhos.stream().filter(vizinho -> vizinho.minado).count();
    }
    void reiniciar(){
        aberto = false;
        minado = false;
        marcado = false;
    }
    void minar(){
        minado = true;
    }

    public boolean isMarcado(){
        return marcado;
    }
    public boolean isAberto(){
        return aberto;
    }
    public boolean isMinado(){
        return minado;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public String toString(){
        if(marcado){
            return "X";
        } else if(aberto && minado){
            return "*";
        } else if(aberto && minasNaVizinhanca() > 0){
            return Long.toString(minasNaVizinhanca());
        } else if(aberto){
            return " ";
        } else{
            return "?";
        }
    }
}

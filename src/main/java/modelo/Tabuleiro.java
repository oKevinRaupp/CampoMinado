package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Tabuleiro {
    private int qtdeLinhas;
    private int qtdeColunas;
    private int qtdeMinas;

    private final List<Campo> campos = new ArrayList<>();

    public Tabuleiro(int qtdeLinhas, int qtdeColunas, int qtdeMinas) {
        this.qtdeLinhas = qtdeLinhas;
        this.qtdeColunas = qtdeColunas;
        this.qtdeMinas = qtdeMinas;

        gerarCampos();
        associarOsVizinhos();
        sortearMinas();
    }

    public void abrir(int linha, int coluna){
        campos.parallelStream()
                .filter(campo -> campo.getLinha() == linha && campo.getColuna() == coluna).findFirst()
                .ifPresent(campo -> campo.abrir());
    }
    public void alternarMarcacao(int linha, int coluna){
        campos.parallelStream()
                .filter(campo -> campo.getLinha() == linha && campo.getColuna() == coluna).findFirst()
                .ifPresent(campo -> campo.alternarMarcacao());
    }
    private void gerarCampos() {
        for (int l = 0; l < qtdeLinhas; l++) {
            for (int c = 0; c < qtdeColunas; c++) {
                campos.add(new Campo(l,c));
            }
        }
    }

    private void associarOsVizinhos() {
        for(Campo c1: campos){
            for(Campo c2: campos){
                c1.adicionarVizinho(c2);
            }
        }
    }

    private void sortearMinas() {
        long minasArmadas = 0;
        Predicate<Campo> minado = campo -> campo.isMinado();

        do {
            minasArmadas = campos.stream().filter(minado).count();
            int aleatorio = (int) (Math.random() * campos.size());
            campos.get(aleatorio).minar();
        } while (minasArmadas < qtdeMinas);
    }

    public boolean objetivoAlcancado(){
        return campos.stream().allMatch(campo -> campo.objetivoAlcancado());
    }
    public void reiniciar(){
        campos.stream().forEach(campo -> campo.reiniciar());
        sortearMinas();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (int l = 0; l <qtdeLinhas ; l++) {
            for (int c = 0; c < qtdeColunas; c++) {
                sb.append(" ");
                sb.append(campos.get(i));
                sb.append(" ");
                i++;
            }
            sb.append("\n");
        }


        return sb.toString();
    }

}

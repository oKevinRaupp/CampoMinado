import modelo.Tabuleiro;
import visao.TabuleiroConsole;

public class Aplicacao {
    public static void main(String[] args) {
        Tabuleiro tabuleiro = new Tabuleiro(3,3,1);
        new TabuleiroConsole(tabuleiro);
    }
}

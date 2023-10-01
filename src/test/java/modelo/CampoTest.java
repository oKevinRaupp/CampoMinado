package modelo;

import excecao.ExplosaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CampoTest {
    private Campo campo;
    @BeforeEach
    void iniciarCampo(){
        campo = new Campo(3,3);
    }
    @Test
    void testeVizinhoRealDistancia1(){
        Campo vizinho = new Campo(3,2);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado);
    }
    @Test
    void testeVizinhoRealDistancia2(){
        Campo vizinho = new Campo(2,2);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado);
    }
    @Test
    void testeNaoVizinho(){
        Campo vizinho = new Campo(1,1);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertFalse(resultado);
    }
    @Test
    void testeValorPadraoMarcado(){
        assertFalse(campo.isMarcado());
    }
    @Test
    void testeAlternarMarcacao(){
        campo.alternarMarcacao();
        assertTrue(campo.isMarcado());
    }
    @Test
    void testeAlternarMarcacaoDuasChamadas(){
        campo.alternarMarcacao();
        campo.alternarMarcacao();
        assertFalse(campo.isMarcado());
    }
    @Test
    void testeAbrirNaoMinadoNaoMarcado(){
        assertTrue(campo.abrir());
    }
    @Test
    void testeAbrirNaoMinadoMarcado(){
        campo.alternarMarcacao();
        assertFalse(campo.abrir());
    }
    @Test
    void testeAbrirMinadoMarcado(){
        campo.alternarMarcacao();
        campo.minar();
        assertFalse(campo.abrir());
    }
    @Test
    void testeAbrirMinadoNaoMarcado(){
        campo.minar();

        assertThrows(ExplosaoException.class,() -> campo.abrir());
    }
    @Test
    void testeAbrirComVizinho(){
        Campo vizinhoDoVizinho1 = new Campo(1,1);
        Campo vizinho1 = new Campo(2,2);

        campo.adicionarVizinho(vizinho1);
        vizinho1.adicionarVizinho(vizinhoDoVizinho1);

        campo.abrir();
        assertTrue(vizinho1.isAberto() && vizinhoDoVizinho1.isAberto());
    }
    @Test
    void testeAbrirComVizinhoMinado(){
        Campo campo11 = new Campo(1,1);
        Campo campo12 = new Campo(1,2);
        Campo campo22 = new Campo(2,2);
        campo12.minar();
        campo22.adicionarVizinho(campo11);
        campo22.adicionarVizinho(campo12);

        campo.adicionarVizinho(campo22);
        campo.abrir();
        assertTrue(campo22.isAberto() && !campo11.isAberto());
    }
    @Test
    void objetivoAlcancado1(){
        campo.minar();
        campo.alternarMarcacao();
        assertTrue(campo.objetivoAlcancado());

    }
    @Test
    void objetivoAlcancado2(){
        campo.abrir();
        assertTrue(campo.objetivoAlcancado());

    }

    @Test
    void TesteReinciar(){
        campo.reiniciar();
        assertFalse(campo.isMarcado() && campo.isAberto() && campo.isMinado());
    }

}
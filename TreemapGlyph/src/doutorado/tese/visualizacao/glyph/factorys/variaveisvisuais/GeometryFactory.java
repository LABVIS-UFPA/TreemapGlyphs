/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.visualizacao.glyph.factorys.variaveisvisuais;

import doutorado.tese.visualizacao.glyph.strategy.variaveisvisuais.shapes.Circulo;
import doutorado.tese.visualizacao.glyph.strategy.variaveisvisuais.shapes.Cruz;
import doutorado.tese.visualizacao.glyph.strategy.variaveisvisuais.shapes.DrawBehavior;
import doutorado.tese.visualizacao.glyph.strategy.variaveisvisuais.shapes.Ellipse;
import doutorado.tese.visualizacao.glyph.strategy.variaveisvisuais.shapes.Hexagono;
import doutorado.tese.visualizacao.glyph.strategy.variaveisvisuais.shapes.Losango;
import doutorado.tese.visualizacao.glyph.strategy.variaveisvisuais.shapes.Pentagono;
import doutorado.tese.visualizacao.glyph.strategy.variaveisvisuais.shapes.Retangulo;
import doutorado.tese.visualizacao.glyph.strategy.variaveisvisuais.shapes.Trapezio;

/**
 *
 * @author Gustavo
 */
public class GeometryFactory {

    public static final class FORMAS {

        public enum GLYPH_FORMAS {
            CRUZ("CRUZ"),
            ELLIPSE("ELLIPSE"),
            HEXAGONO("HEXAGONO"),
            LOSANGO("LOSANGO"),
            PENTAGONO("PENTAGONO"),
            TRAPEZIO("TRAPEZIO"),
            RETANGULO("RETANGULO"),
            CIRCULO("CIRCULO");

            private final String nome;

            GLYPH_FORMAS(String nome) {
                this.nome = nome;
            }

            private String nome() {
                return nome;
            }
        }
    }

    private GeometryFactory() {
    }

    public static DrawBehavior create(FORMAS.GLYPH_FORMAS forma) {
        switch (forma) {
            case RETANGULO:
                return new Retangulo();
            case CRUZ:
                return new Cruz();
            case ELLIPSE:
                return new Ellipse();
            case CIRCULO:
                return new Circulo();
            case HEXAGONO:
                return new Hexagono();
            case LOSANGO:
                return new Losango();
            case PENTAGONO:
                return new Pentagono();
            case TRAPEZIO:
                return new Trapezio();
            default:
                return null;
        }
    }
}

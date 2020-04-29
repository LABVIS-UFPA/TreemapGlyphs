/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.control.business.visualizations.glyph.factorys.variaveisvisuais;

import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.shapes.Circulo;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.shapes.Cruz;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.DrawBehavior;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.shapes.Estrela;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.shapes.Pentagono;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.shapes.Quadrado;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.shapes.Serrilhado;

/**
 *
 * @author Gustavo
 */
public class GeometricalFactory {

    public static final class FORMAS {

        public enum GLYPH_FORMAS {
            CIRCULO("Circulo"),
            CRUZ("Cruz"),
            ESTRELA("Estrela"),
            QUADRADO_SERRILHADO("Serrilhado"),
            PENTAGONO("Pentagono");
            //QUADRADO("Quadrado");
            //LOSANGO("LOSANGO"),
            //TRAPEZIO("TRAPEZIO"),
            //HEXAGONO("HEXAGONO"),
            //ELLIPSE("ELLIPSE");

            private final String nome;

            GLYPH_FORMAS(String nome) {
                this.nome = nome;
            }

            public String shapeName() {
                return nome;
            }
        }
    }

    private GeometricalFactory() {
    }

    public static DrawBehavior create(FORMAS.GLYPH_FORMAS forma) {
        switch (forma) {
            case CIRCULO:
                return new Circulo();
            case CRUZ:
                return new Cruz();
//            case QUADRADO:
//                return new Quadrado();
            case ESTRELA:
                return new Estrela();
            case QUADRADO_SERRILHADO:
                return new Serrilhado();
//            case ELLIPSE:
//                return new Ellipse();
//            case HEXAGONO:
//                return new Hexagono();
//            case LOSANGO:
//                return new Losango();
            case PENTAGONO:
                return new Pentagono();
//            case TRAPEZIO:
//                return new Trapezio();
            default:
                return null;
        }
    }
}

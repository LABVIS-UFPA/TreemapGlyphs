/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.control.business.visualizations.glyph.factorys.variaveisvisuais;

import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.DrawBehavior;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.texture.CirculoTextura_10x10;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.texture.CirculoTextura_2x2;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.texture.CirculoTextura_2x2_Branco;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.texture.CirculoTextura_3x3;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.texture.CirculoTextura_3x3_Branco;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.texture.CirculoTextura_4x4;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.texture.CirculoTextura_6x6;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.texture.CirculoTextura_8x8;

/**
 *
 * @author Gustavo
 */
public class TexturesFactory {

    public static final class TEXTURE {

        public enum GLYPH_TEXTURAS {
            CIRCULO_2X2("CIRCULO_2X2"),                 //0
            CIRCULO_3x3_BRANCO("CIRCULO_3x3_Branco"),   //1
            CIRCULO_4X4("CIRCULO_4X4"),                 //2
            CIRCULO_2x2_BRANCO("CIRCULO_2x2_Branco"),   //3
            CIRCULO_3x3("CIRCULO_3X3");                 //4
//            CIRCULO_10X10("CIRCULO_10X10"),//0
//            CIRCULO_6X6("CIRCULO_6X6"),    //3
//            CIRCULO_8X8("CIRCULO_8X8");    //4

            private final String nome;

            GLYPH_TEXTURAS(String nome) {
                this.nome = nome;
            }

            private String nome() {
                return nome;
            }
        }
    }

    private TexturesFactory() {
    }

    public static DrawBehavior create(TEXTURE.GLYPH_TEXTURAS forma) {
        switch (forma) {
//            case CIRCULO_10X10:
//                return new CirculoTextura_10x10();
//            case CIRCULO_8X8:
//                return new CirculoTextura_8x8();
//            case CIRCULO_6X6:
//                return new CirculoTextura_6x6();
            case CIRCULO_4X4:
                return new CirculoTextura_4x4();
            case CIRCULO_3x3:
                return new CirculoTextura_3x3();
            case CIRCULO_3x3_BRANCO:
                return new CirculoTextura_3x3_Branco();
            case CIRCULO_2X2:
                return new CirculoTextura_2x2();        
            case CIRCULO_2x2_BRANCO:
                return new CirculoTextura_2x2_Branco();        
            default:
                return null;
        }
    }
}

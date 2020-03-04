/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.control.business.visualizations.glyph.factorys.variaveisvisuais;

import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.DrawBehavior;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.orientation.Arrow0;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.orientation.Arrow135;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.orientation.Arrow180;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.orientation.Arrow45;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.orientation.Arrow90;

public class OrientationFactory {

    public static final class ARROW {

        public enum GLYPH_ORIENTACAO {
            ARROW180("ARROW180"), //4
            ARROW135("ARROW135"),//135
            ARROW90("ARROW90"),//90
            ARROW45("ARROW45"),//45
            ARROW0("ARROW0");//0

            private final String nome;

            GLYPH_ORIENTACAO(String nome) {
                this.nome = nome;
            }

            public String nome() {
                return nome;

            }
        }
    }

    private OrientationFactory() {
    }

    public static DrawBehavior create(ARROW.GLYPH_ORIENTACAO orientacao) {
        switch (orientacao) {
            case ARROW0:
                return new Arrow0();
            case ARROW45:
                return new Arrow45();
            case ARROW90:
                return new Arrow90();
            case ARROW135:
                return new Arrow135();
            case ARROW180:
                return new Arrow180();

            default:
                return null;
        }
    }
}

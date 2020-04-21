/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.control.business.visualizations.glyph.factorys.variaveisvisuais;

import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.DrawBehavior;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.orientation.Arrow0Dir;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.orientation.Arrow135CanEsq;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.orientation.Arrow180Esq;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.orientation.Arrow45CanDir;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.orientation.Arrow90Cima;

public class OrientationFactory {

    public static final class ARROW {

        public enum GLYPH_ORIENTACAO {
            ARROW90("Arrow90Cima")  ,//90 - cima
            ARROW0("Arrow0Dir")    ,//0 - direita
            ARROW180("Arrow180Esq"), //4 - esquerda
            ARROW45("Arrow45CanDir")  ,//45 - canto direito 
            ARROW135("Arrow135CanEsq");//135 - canto esquerdo **

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
                return new Arrow0Dir();
            case ARROW45:
                return new Arrow45CanDir();
            case ARROW90:
                return new Arrow90Cima();
            case ARROW135:
                return new Arrow135CanEsq();
            case ARROW180:
                return new Arrow180Esq();
            default:
                return null;
        }
    }
}

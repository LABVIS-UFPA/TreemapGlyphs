/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.controle.negocio.visualizacao.glyph;

import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;

/**
 *
 * @author Anderson Soares
 */
public class GlyphConcrete extends Glyph {

    private Rectangle bounds;
        
    @Override
    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
        if (this.getChild() != null) {
            this.getChild().setBounds(new Rectangle(bounds));
        }
        
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public Shape getClipShape() {
        return null;
    }

    @Override
    public Paint getTexturePaint() {
        return null;
    }

    @Override
    public String getVarValue() {
        return "";
    }

    @Override
    public Object whoAmI() {
        return this.getClass();
    }


}

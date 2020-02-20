/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.position;

import doutorado.tese.control.business.visualizations.glyph.Glyph;
import doutorado.tese.util.Constantes;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

/**
 *
 * @author Anderson Soares
 */
public class Position extends Glyph{

    private int[] xPoints;
    private int[] yPoints;
    private Path2D p;
    private Constantes.POSICOES posicao;
    
    public Position() {
    }
    
    public void montarCirculo() {
        double x = xPoints[0];
        double y = yPoints[0];
        double w = xPoints[1];
        double h = xPoints[1];
        switch (getPosicao()) {            
            case DIR_SUP:
                System.out.println("DIR_SUP: "+x+","+y+","+w+","+h);
                x = x + (w / 2);
                break;
            case DIR_INF:                
                System.out.println("DIR_SUP: ");
                x = x + w / 2;
                y = y + h / 2;
                break;
            case ESQ_INF:
                System.out.println("ESQ_INF: "+x+","+y+","+w+","+h);
                y = y + h / 2;
                break;
            case ESQ_SUP:
                System.out.println("ESQ_SUP: ");
                break;
            default:
                throw new AssertionError();
        }
        p = new Path2D.Double();       
        p.append(new Ellipse2D.Double(x, y, w / 4, w / 4), true);   
    }

    private void montarRetangulo() {
        int[] points = new int[2];

        points[0] = getBounds().width;
        points[1] = getBounds().height;

        transformarRetanguloEmQuadrado(points);

        int width = Math.round(points[0] * getPectSobreposicao());
        int height = Math.round(points[1] * getPectSobreposicao());

        xPoints = new int[2];
        yPoints = new int[2];

        xPoints[0] = getBounds().x + getBounds().width / 2 - width / 2;
        yPoints[0] = getBounds().y + getBounds().height / 2 - height / 2;

        xPoints[1] = width;
        yPoints[1] = height;
    }
        
    @Override
    public void paint(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
               
        g2d.setColor(Color.white);
        g2d.fill(p);
        g2d.setColor(Color.black);
        g2d.drawRect(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
        g2d.draw(p);
    }
    
    @Override
    public void setBounds(Rectangle rect){
        super.setBounds(rect);
        montarRetangulo();
        montarCirculo();
    }
    
    @Override
    public Shape getClipShape() {
        return getBounds();
    }

    @Override
    public Paint getTexturePaint() {
        return null;
    }

    @Override
    public String getVarValue() {
        return getPosicao().name();
    }

    @Override
    public Object whoAmI() {
        return this.getClass();
    }

    /**
     * @return the posicao
     */
    public Constantes.POSICOES getPosicao() {
        return posicao;
    }

    /**
     * @param posicao the posicao to set
     */
    public void setPosicao(Constantes.POSICOES posicao) {
        this.posicao = posicao;
    }
    
    
    
    
}

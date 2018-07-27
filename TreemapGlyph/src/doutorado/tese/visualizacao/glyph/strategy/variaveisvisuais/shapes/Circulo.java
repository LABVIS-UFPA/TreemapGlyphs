package doutorado.tese.visualizacao.glyph.strategy.variaveisvisuais.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

/**
 * O objeto Circulo com cor ocupa 65% do item do treemap
 * 
 * @author Anderson Soares
 */
public class Circulo implements DrawBehavior{

    private int[] xPoints;
    private int[] yPoints;
    private int numberColor;
    private Color cor;
    private Rectangle bounds;
    private Path2D p;

    public Circulo() {
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
               
        g2d.setColor(Color.white);
        g2d.fill(p);
        g2d.setColor(Color.black);
        g2d.draw(p);
    }
    
    @Override
    public void tornarGlyphQuadrado(int[] point) {
        if (point[0] > point[1]) {
            point[0] = point[1];
        } else if (point[0] < point[1]) {
            point[1] = point[0];
        }
    }

    private void montarCirculo() {
        Rectangle rect = getBounds();
        
        int[] points = new int[2];

        points[0] = getBounds().width;
        points[1] = getBounds().height;
        
        tornarGlyphQuadrado(points);
        
        int width = (int) Math.round(points[0] * percentSobreposicao);
        int height = (int) Math.round(points[1] * percentSobreposicao);

        p = new Path2D.Double();       
        p.append(new Ellipse2D.Double(rect.x + 2, rect.y + 2, width, height), true);   
    }

    public Rectangle getBounds(){
        return this.bounds;
    }
    
    @Override
    public void setBounds(Rectangle bounds){
        this.bounds = bounds;
        montarCirculo();
    }
    
    @Override
    public int getArea() {
        return xPoints[1]*yPoints[1];
    }

    @Override
    public Shape getClipShape() {
        return p;
    }

    @Override
    public void drawForeground(Graphics2D g2d) {
        g2d.setColor(Color.black);
        g2d.draw(p);
    }
    
    @Override
    public Circulo clone() throws CloneNotSupportedException {
        try {
            // call clone in Object.
            return (Circulo) super.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("Cloning not allowed.");
            return this;
        }
    }

    @Override
    public String toString() {
        super.toString();
        return Circulo.class.getSimpleName();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.visualizacao.glyph.formasgeometricas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;

public class Hexagono extends FormaGeometrica{

    private int[] xPoints;
    private int[] yPoints;
    private Polygon p;
   

    public Hexagono(Rectangle r) {
        super(r, "HEXAGONO");
        montarHexagono();
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setPaint(Color.BLACK);
        

        g2d.setColor(Color.white);
        g2d.fillPolygon(p);
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(p);
    }

    
    private int[] verificarRetangulo(int [] point){
        if(point[0] > point[1]){
            point[0] = point[1];
           return point;
        }
        else if(point[0] < point[1]){
            point[1] = point[0];
           return point;
        }
        return null;
    }
    
    private void montarHexagono() {
        int[] points = new int[2];

        Rectangle rect = getBounds();
        
        points[0] = rect.width;
        points[1] = rect.height;

        verificarRetangulo(points);

        int width = (int) Math.round(points[0] * 0.5);
        int height = (int) Math.round(points[1] * 0.5);

        int halfWidth = width / 2;
        int halfHeight = height / 2;
        int innerWidth = width / 4;
        int innerHeight = height / 4;

        halfWidth += rect.x + rect.width/2 - width/2;
        halfHeight += rect.y + rect.height/2 - height/2;

       
        xPoints = new int[6];
        yPoints = new int[6];

        xPoints[0] = halfWidth;
        yPoints[0] = (int) Math.round(rect.y + rect.height/2 - height/2);

        xPoints[1] = (int) Math.round(rect.x + rect.width/2 - width/2);
        yPoints[1] = halfHeight - innerHeight;

        xPoints[2] = (int) Math.round(rect.x + rect.width/2 - width/2);
        yPoints[2] = halfHeight + innerHeight;

        xPoints[3] = halfWidth;
        yPoints[3] = height + (int) Math.round(rect.y + rect.height/2 - height/2);

        xPoints[4] = width + (int) Math.round(rect.x + rect.width/2 - width/2);
        yPoints[4] = halfHeight + innerHeight;

        xPoints[5] = width + (int) Math.round(rect.x + rect.width/2 - width/2);
        yPoints[5] = halfHeight - innerHeight;
        
        p = new Polygon();

        p.addPoint(xPoints[0], yPoints[0]);
        p.addPoint(xPoints[1], yPoints[1]);
        p.addPoint(xPoints[2], yPoints[2]);
        p.addPoint(xPoints[3], yPoints[3]);
        p.addPoint(xPoints[4], yPoints[4]);
        p.addPoint(xPoints[5], yPoints[5]);
    }

    @Override
    public int getArea() {
        return (xPoints[4]-xPoints[1])*(yPoints[3]-yPoints[0]);
    }
}

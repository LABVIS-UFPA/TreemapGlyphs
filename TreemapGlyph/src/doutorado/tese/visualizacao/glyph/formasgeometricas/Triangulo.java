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
import javax.swing.JPanel;


public class Triangulo extends JPanel{

    private int[] xPoints;
    private int[] yPoints;
    private Rectangle rect;
    
    public Triangulo(Rectangle r) {
        this.rect = r;
        setBounds(this.rect);
    }
    
    public void setBounds(Rectangle rect){
        this.rect = rect;
    }
    
    public Rectangle getBounds(){
        return rect;
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setPaint(Color.darkGray);
    
        montarTriangulo();
        Polygon p = new Polygon();
        
        p.addPoint(xPoints[0], yPoints[0]);
        p.addPoint(xPoints[1], yPoints[1]);
        p.addPoint(xPoints[2], yPoints[2]);
       
//        p.translate(xy[0],xy[1]);
        g2d.setColor(Color.WHITE);
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

    private void montarTriangulo() {
        int[] points = new int[2];

        points[0] = rect.width;
        points[1] = rect.height;

        verificarRetangulo(points);

        int width = (int) Math.round(points[0] * 0.45);
        int height = (int) Math.round(points[1] * 0.45);


        int halfWidth = width / 2;
        int halfHeight = height / 2;
        int innerWidth = width / 2;
        int innerHeight = height / 2;

        halfWidth += rect.x + rect.width/2 - width/2;
        halfHeight += rect.y + rect.height/2 - height/2;

        xPoints = new int[3];
        yPoints = new int[3];

        xPoints[0] = halfWidth;
        yPoints[0] = (int) Math.round(rect.y + rect.height/2 - height/2)- width/5;

        xPoints[1] = halfWidth - innerWidth;
        yPoints[1] = halfHeight+ innerHeight;

        xPoints[2] = width + (int) Math.round(rect.x + rect.width/2 - width/2);
        yPoints[2] = halfHeight+ innerHeight;
    }
}

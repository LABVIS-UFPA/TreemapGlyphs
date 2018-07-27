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

/**
 *
 * @author Anderson
 */
public class Estrela{

    private int[] xPoints;
    private int[] yPoints;
    private Rectangle rect;
   

    public Estrela(Rectangle r) {
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
        
        g2d.setPaint(Color.BLACK);
        montarEstrela();        
        
        Polygon p = new Polygon();
        
        p.addPoint(xPoints[0], yPoints[0]);
        p.addPoint(xPoints[1], yPoints[1]);
        p.addPoint(xPoints[2], yPoints[2]);
        p.addPoint(xPoints[3], yPoints[3]);
        p.addPoint(xPoints[4], yPoints[4]);
        p.addPoint(xPoints[5], yPoints[5]);
        p.addPoint(xPoints[6], yPoints[6]);
        p.addPoint(xPoints[7], yPoints[7]);
        p.addPoint(xPoints[8], yPoints[8]);
        p.addPoint(xPoints[9], yPoints[9]);
        p.addPoint(xPoints[10], yPoints[10]);


        
        g2d.setColor(Color.white);
        g2d.fillPolygon(p);
        g2d.setColor(Color.BLACK);
        g2d.drawPolygon(p);      
    }
    
     //função para deixar os glyphs quadrados
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

    private void montarEstrela() {
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

        xPoints = new int[11];
        yPoints = new int[11];

        xPoints[0] = halfWidth;
        yPoints[0] = (int) Math.round(rect.y + rect.height/2 - height/2)- width/5;
        
        xPoints[1] = (int) Math.round(rect.x + rect.width/2 -innerWidth/3);
        yPoints[1] = halfHeight - innerHeight+width/5;
        
        xPoints[2] = halfWidth- (innerWidth+innerWidth/3);
        yPoints[2] = halfHeight - innerHeight+width/5;
        
        xPoints[3] = (int) Math.round(rect.x + rect.width/2 -innerWidth/2);
        yPoints[3] = halfHeight;
        
        
        xPoints[4] = halfWidth-innerWidth;
        yPoints[4] = halfHeight + innerHeight;
        
        xPoints[5] = halfWidth;
        yPoints[5] = halfHeight + innerHeight/2;
        
        xPoints[6] = halfWidth+innerWidth;;
        yPoints[6] = halfHeight+ innerHeight;
        

        
      
        xPoints[7] = width + (int) Math.round(rect.x + rect.width/2 - width/2);
        yPoints[7] = halfHeight+ innerHeight;
        
        xPoints[8] = (int) Math.round(rect.x + rect.width/2 +innerWidth/2);
        yPoints[8] = halfHeight;
        
        xPoints[9] = halfWidth + (innerWidth+innerWidth/4);
        yPoints[9] = halfHeight - innerHeight+width/5;
        

        xPoints[10] =  (int) Math.round(rect.x + rect.width/2 + innerWidth/3);
        yPoints[10] = halfHeight - innerHeight+width/5;
        
        
      

        
        
        

    }
}

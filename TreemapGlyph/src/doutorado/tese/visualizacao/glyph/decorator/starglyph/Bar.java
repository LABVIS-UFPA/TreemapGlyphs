/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.visualizacao.glyph.decorator.starglyph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.util.HashSet;
import javax.swing.JPanel;

/**
 *
 * @author Anderson Soares
 */

public class Bar extends JPanel {
    
    private Rectangle rect;
    private Point center;
    private double dadoMaxVal;
    private double dado;
    private double[] values;
    private int valueX;
    private int valueY;
    private int barWidth;
    private int height;
    
    
    public Bar(double dado, double dadoMaxVal) {
        this.dado = dado;
       
        this.dadoMaxVal = dadoMaxVal;
    }

    public void paint(Graphics2D g2d) {
//        desenharBarChart(g2d);

        //g2d.setColor(Color.yellow);
        //g2d.fillRect(rect.x, rect.y, rect.width/8, rect.height/4);
    }

    

    /**
     * @return the center
     */
    public Point getCenter() {
        return center;
    }

    /**
     * @param center the center to set
     */
    public void setCenter(Point center) {
        this.center = center;
    }

    /**
     * @return the pontos
     */

    /**
     * @param pontos the pontos to set
     */

    /**
     * @return the rect
     */
    public Rectangle getRect() {
        return rect;
    }

    /**
     * @param rect the rect to set
     */
    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    /**
     * @return the dado
     */
    public double getDado() {
        return dado;
    }

    /**
     * @param dado the dado to set
     */
    public void setDado(double dado) {
        this.dado = dado;
    }

    /**
     * @return the dadoMaxVal
     */
    public double getDadoMaxVal() {
        return dadoMaxVal;
    }

    /**
     * @param dadoMaxVal the dadoMaxVal to set
     */
    public void setDadoMaxVal(double dadoMaxVal) {
        this.dadoMaxVal = dadoMaxVal;
    }
    
     public void setDadosBarra(int valuex, int valuey, int barwidth, int height) {
        this.valueX = valuex;
        this.valueY = valuey;
        this.barWidth = barwidth;
        this.height = height;
    }
     
     public int[] getDadosBarra() {
        return new int[] {this.valueX,this.valueY,this.barWidth,this.height};
    } 

}

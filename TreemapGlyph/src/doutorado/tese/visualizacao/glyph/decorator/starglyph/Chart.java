/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.visualizacao.glyph.decorator.starglyph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;

/**
 *
 * @author Anderson Soares
 */



public class Chart {
  
  class Slice {
  double value;
  Color color;

  public Slice(double value) {
    this.value = value;
  
  }
} 
    private double[] pontos;
    private Rectangle rect;
    private Point center;
    private double dadoMaxVal;
    private double dado;

    public Chart(double dado, double dadoMaxVal) {
        this.dado = dado;
        this.dadoMaxVal = dadoMaxVal;
    }
    
    public void paint(Graphics2D g2d) {
        desenharPieChart(g2d);
    }
    
    private void desenharPieChart(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        Slice[] slices = {new Slice(this.dado)};
        
        double total = 0.0D;
        for (int i = 0; i < slices.length; i++) {
            total += slices[i].value;
        }

        double curValue = 0.0D;
        int startAngle = 0;
        for (int i = 0; i < slices.length; i++) {
            startAngle = (int) (curValue * 360 / total);
            int arcAngle = (int) (slices[i].value * 360 / this.dadoMaxVal);

            g.setColor(slices[i].color);
            g2.fill(new Arc2D.Double(rect.x + rect.width / 4, rect.y + rect.height / 4, rect.width / 2, rect.height / 2, startAngle,arcAngle, Arc2D.PIE));
            //g.fillArc(rect.x + rect.width / 4, rect.y + rect.height / 4, rect.width / 2, rect.height / 2, startAngle, arcAngle);
            curValue += slices[i].value;

        }

    }

    public Point definePolo(Point ponto) {
        int xPolo = (int) Math.round(ponto.x + getRect().x);
        int yPolo = (int) Math.round(ponto.y + getRect().y);
        return new Point(xPolo, yPolo);
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
    public double[] getPontos() {
        return pontos;
    }

    /**
     * @param pontos the pontos to set
     */
    public void setPontos(double[] pontos) {
        this.pontos = pontos;
    }

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
    
}

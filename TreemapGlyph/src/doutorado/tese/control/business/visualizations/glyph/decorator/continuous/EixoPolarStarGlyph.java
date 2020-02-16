/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.control.business.visualizations.glyph.decorator.continuous;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;

/**
 *
 * @author Anderson Soares
 */
public class EixoPolarStarGlyph {

    private double[] pontos;
    private Rectangle rect;
    private Point center;
    private double dadoMaxVal;
    private double dado;

    public EixoPolarStarGlyph(double dado, double dadoMaxVal) {
        this.dado = dado;
        this.dadoMaxVal = dadoMaxVal;
    }

    public void paint(Graphics2D g2d) {
        desenharEixoPolar(g2d);
    }

    private void desenharEixoPolar(Graphics g) {
//        double[] pontos = parsePolar2Cartesiana(anguloAlfa, r);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.drawLine(definePolo(getCenter()).x, definePolo(getCenter()).y,
                (int) Math.round(getPontos()[0] + getCenter().x + getRect().x), (int) Math.round(getPontos()[1] + getCenter().y + getRect().y));
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

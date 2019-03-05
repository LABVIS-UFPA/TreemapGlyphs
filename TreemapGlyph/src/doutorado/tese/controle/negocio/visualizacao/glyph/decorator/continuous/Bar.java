/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.controle.negocio.visualizacao.glyph.decorator.continuous;

import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author Anderson Soares
 */
public class Bar {

    private Rectangle rect = null;
    private Point center = null;
    private double dadoMaxVal;
    private double dadoMinVal;
    private double dado;
    private double[] values = null;
    private int valueX;
    private int valueY;
    private int barWidth;
    private int height;
    int[] lineTop = null;
    int[] lineCenter = null;
    int[] lineBottom = null;

    public Bar(double dado, double dadoMaxVal) {
        this.dado = dado;
        this.dadoMaxVal = dadoMaxVal;
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

    public double getDadoMinVal() {
        return dadoMinVal;
    }

    public void setDadoMinVal(double dadoMinVal) {
        this.dadoMinVal = dadoMinVal;
    }

    public void setPosicaoBarra(int valuex, int valuey, int barwidth, int height) {
        this.valueX = valuex;
        this.valueY = valuey;
        this.barWidth = barwidth;
        this.height = height;
    }

    public int getBarraX() {
        return this.valueX;
    }
    public int getBarraY() {
        return this.valueY;
    }
    public int getBarraW() {
        return this.barWidth;
    }
    public int getBarraH() {
        return this.height;
    }

    public int[] getLineTop() {
        return lineTop;
    }

    public int[] getLineCenter() {
        return lineCenter;
    }

    public int[] getLineButton() {
        return lineBottom;
    }

    public void setLineTop(int x1, int y1, int x2, int y2) {
        this.lineTop[0] = x1;
        this.lineTop[1] = y1;
        this.lineTop[2] = x2;
        this.lineTop[3] = y2;
    }

    public void setLineButton(int x1, int y1, int x2, int y2) {
        this.lineBottom[0] = x1;
        this.lineBottom[1] = y1;
        this.lineBottom[2] = x2;
        this.lineBottom[3] = y2;
    }

    public void setLineCenter(int x1, int y1, int x2, int y2) {
        this.lineCenter[0] = x1;
        this.lineCenter[1] = y1;
        this.lineCenter[2] = x2;
        this.lineCenter[3] = y2;
    }

}

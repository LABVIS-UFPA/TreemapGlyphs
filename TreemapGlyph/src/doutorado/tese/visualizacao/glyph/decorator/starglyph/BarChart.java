/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.visualizacao.glyph.decorator.starglyph;

import doutorado.tese.io.ManipuladorArquivo;
import doutorado.tese.util.Constantes;
import doutorado.tese.visualizacao.glyph.Glyph;
//import io.ManipuladorArquivo;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.List;
//import util.Constantes;

/**
 *
 * @author Anderson
 */
public class BarChart extends Glyph {

    private Rectangle rect;
    private int quantVar;
    private double r;
    private double anguloAlfa = 0;
    private double anguloAcc = 0;
    private List<String> atributosEscolhidaoBase;
    private ManipuladorArquivo manipulador;
    private double porcentagemDado;
    private int maiorRaio;
    private EixoPolarStarGlyph[] eixosPolares;
    private Point center;

    public BarChart(List<String> variaveisEscolhidasStarGlyph) {
        this.atributosEscolhidaoBase = variaveisEscolhidasStarGlyph;
        eixosPolares = new EixoPolarStarGlyph[this.atributosEscolhidaoBase.size()];
       
    }

    /**
     * Calcula quantos porcentos o dado atual da linha corrente equivale ao
     * valor max da coluna.
     *
     * @param dadoColuna
     * @param maxValCol
     * @return Porcentagem equivalente ao valor max da coluna.
     */
    private double calcularPorcentagemDado(double dadoColuna, double maxValCol) {
        return (dadoColuna * 100) / maxValCol;
    }

    private double calcularPorcentagemParaR(double porcentagemDado, double maiorRaio) {
        return (porcentagemDado * maiorRaio) / 100;
    }

    @Override
    public void setBounds(Rectangle rect) {
        this.rect = rect;
        this.rect.x = rect.x + 2;
        this.rect.y = rect.y + 2;
        this.rect.width = rect.width - 2;
        this.rect.height = rect.height - 2;

        center = getCenter();
        maiorRaio = encontrarMaiorRaio();
    }

    @Override
    public void paint(Graphics2D g2d) {
        if (getQuantVar() != 0) {
            for (int i = 0; i < getQuantVar(); i++) {
                g2d.setColor(Color.decode(Constantes.getCor()[i]));
            }
        }
    }

    private Point getCenter() {
        int width = (int) Math.round(rect.width) - 1;
        int height = (int) Math.round(rect.height) - 1;

        int halfWidth = width / 2;
        int halfHeight = height / 2;
        return new Point(halfWidth, halfHeight);
    }

    private int encontrarMaiorRaio() {
        int bordaW = (int) Math.round(rect.width) - 2;
        int bordaH = (int) Math.round(rect.height) - 2;

        int raio = center.x - bordaW;
        int mRaio = raio;
        raio = center.y - bordaH;

        if (raio >= mRaio) {
            return raio;
        } else {
            return mRaio;
        }
    }

    public int getQuantVar() {
        return quantVar;
    }

    public void setQuantVar(int quantVar) {
        this.quantVar = quantVar;
    }

    /**
     * @return the atributosEscolhidaoBase
     */
    public List<String> getAtributosEscolhidaoBase() {
        return atributosEscolhidaoBase;
    }

    /**
     * @return the manipulador
     */
    public ManipuladorArquivo getManipulador() {
        return manipulador;
    }

    /**
     * @param manipulador the manipulador to set
     */
    public void setManipulador(ManipuladorArquivo manipulador) {
        this.manipulador = manipulador;
    }

    @Override
    public Shape getClipShape() {
        return this.getBounds();
    }

    @Override
    public Paint getTexturePaint() {
        return null;
    }

    @Override
    public String getVarValue() {
        return "BARCHART - teste";
    }

    /**
     * @param center the center to set
     */
    public void setCenter(Point center) {
        this.center = center;
    }

    /**
     * @return the eixosPolares
     */
  
    @Override
    public Object whoAmI() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

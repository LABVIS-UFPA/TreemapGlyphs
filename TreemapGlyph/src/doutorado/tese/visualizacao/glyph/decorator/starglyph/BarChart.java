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
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
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
//    private double anguloAlfa = 0;
//    private double anguloAcc = 0;
    private List<String> atributosEscolhidaoBase;
    private ManipuladorArquivo manipulador;
    private double porcentagemDado;
//    private int maiorRaio;
    private Bar[] barras;
//    private Point center;
    private int distancia;

    //intancias do grafico de barras
    private double[] values;
    private String[] labels;
    private Color[] colors;
    private String title;
    private float panelWidth;
    private float panelHeight;

    public BarChart(List<String> variaveisEscolhidasStarGlyph) {
        this.atributosEscolhidaoBase = variaveisEscolhidasStarGlyph;
        barras = new Bar[this.atributosEscolhidaoBase.size()];
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
    public void paint(Graphics2D g2d) {
        if (getQuantVar() != 0) {
            for (int i = 0; i < getQuantVar(); i++) {
                g2d.setColor(Color.decode(Constantes.getCor()[i]));
//                getBarras()[i].paint(g2d);
                int x = getBarras()[i].getDadosBarra()[0];
                int y = getBarras()[i].getDadosBarra()[1];
                int w = getBarras()[i].getDadosBarra()[2];
                int h = getBarras()[i].getDadosBarra()[3];

                //codigo cada barra
                g2d.fillRect(x, y, w, h);
                g2d.setColor(Color.black);
                g2d.drawRect(x, y, w, h);

            }
        }

    }

    public void calcularPosicaoBarras() {
//        Graphics2D g2 = (Graphics2D) g;
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//                RenderingHints.VALUE_ANTIALIAS_ON);
        double minValue = 0;
        double maxValue = 0;
        for (int i = 0; i < getBarras().length; i++) {
            if (minValue > getBarras()[i].getDado()) {
                minValue = getBarras()[i].getDado();
            }
            if (maxValue < getBarras()[i].getDado()) {
                maxValue = getBarras()[i].getDado();
            }
        }

        int panelWidth = (int) Math.round(rect.width * 0.9);
        int panelHeight = (int) Math.round(rect.height * 0.5);
//        panelWidth = 1f;
//        panelHeight = 1f;
        float barWidth = panelWidth / getBarras().length;

        if (maxValue == minValue) {
            return;
        }
        double scale = (panelHeight) / (maxValue - minValue);

        for (int j = 0; j < getBarras().length; j++) {
            int valueP = Math.round(j * barWidth + 1);
            int valueQ = 0;
            int height = (int) (getBarras()[j].getDado() * scale);
            if (getBarras()[j].getDado() >= 0) {
                valueQ += (int) ((maxValue - getBarras()[j].getDado()) * scale);
            } else {
                valueQ += (int) (maxValue * scale);
                height = -height;
            }
        }
        int top = 0;
        for (int i = 0; i < getBarras().length; i++) {
            int valueX = rect.x + Math.round(i * barWidth + 1);
            int valueY = rect.y;
            int height = (int) (getBarras()[i].getDado() * scale);
            if (getBarras()[i].getDado() >= 0) {
                valueY += (int) ((maxValue - getBarras()[i].getDado()) * scale);
            } else {
                valueY += (int) (maxValue * scale);
                height = -height;
            }

            getBarras()[i].setDadosBarra(valueX, valueY, Math.round(barWidth), height);

//             g.setColor(Color.red);
//            g.fillRect(valueX, valueY, barWidth - 2, height);
//            g.setColor(Color.black);
//            g.drawRect(valueX, valueY, barWidth - 2, height);
//      
        }

    }

    @Override
    public void setBounds(Rectangle rect) {
        this.rect = rect;
        this.rect.x = rect.x + 2;
        this.rect.y = rect.y + 2;
        this.rect.width = rect.width - 2;
        this.rect.height = rect.height - 2;
        
//        panelWidth = panelWidth * this.rect.width;  
//        panelHeight = panelWidth * this.rect.height;
//        center = getCenter();

        calcularPosicaoBarras();

        for (int i = 0; i < barras.length; i++) {
            barras[i].setRect(this.rect);
        }
        super.setBounds(this.rect);
    }

    private Point getCenter() {
        int width = (int) Math.round(rect.width) - 1;
        int height = (int) Math.round(rect.height) - 1;

        int halfWidth = width / 2;
        int halfHeight = height / 2;
        return new Point(halfWidth, halfHeight);
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
        return "BarChart - teste";
    }

    /**
     * @param center the center to set
     */
//    public void setCenter(Point center) {
//        this.center = center;
//    }
    public int getDistancia() {
        return distancia;
    }

    public void setDistancia() {
        this.distancia += (int) (this.rect.width * 0.3);
    }

    public Bar[] getBarras() {
        return barras;
    }

    public void setBarras(Bar[] barras) {
        this.barras = barras;
    }

    @Override
    public Object whoAmI() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

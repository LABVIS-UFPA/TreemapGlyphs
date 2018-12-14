/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.controle.negocio.visualizacao.glyph.decorator.continuous;

import doutorado.tese.dao.ManipuladorArquivo;
import doutorado.tese.util.Constantes;
import doutorado.tese.controle.negocio.visualizacao.glyph.Glyph;
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
        Graphics2D g2 = (Graphics2D) g2d;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        if (getQuantVar() != 0) {
            for (int i = 0; i < getQuantVar(); i++) {
                g2d.setColor(Color.decode(Constantes.getCor()[i]));
//                getBarras()[i].paint(g2d);
                int x = getBarras()[i].getDadosBarra()[0];
                int y = getBarras()[i].getDadosBarra()[1];
                int w = getBarras()[i].getDadosBarra()[2];
                int h = getBarras()[i].getDadosBarra()[3];

                //codigo cada barra
                g2.fillRect(x, y, w, h);
                g2.setColor(Color.black);
                g2.drawRect(x, y, w, h);
                
                       
                int lx1 = getBarras()[i].getLine()[0];
                int ly1 = getBarras()[i].getLine()[1];
                int lx2 = getBarras()[i].getLine()[2];
                int ly2 = getBarras()[i].getLine()[3];
                g2.drawLine(lx1,ly1,lx2,ly2);

            }
        }

    }

    public void calcularPosicaoBarras() {
        int[] points = new int[2];
        points[0] = rect.width;
        points[1] = rect.height;
        tornarGlyphQuadrado(points);

        double minValue = 0;
        double maxValue = 0;
        for (int i = 0; i < getBarras().length; i++) {
            if (minValue > Math.abs(getBarras()[i].getDado())) {
                minValue = Math.abs(getBarras()[i].getDado());
            }
            if (maxValue < Math.abs(getBarras()[i].getDado())) {
                maxValue = Math.abs(getBarras()[i].getDado());
            }
        }

        int panelWidth = (int) Math.round(points[0]);
        int panelHeight = (int) Math.round(points[1]);

        float barWidth = panelWidth / getBarras().length;

        if (maxValue == minValue) {
            return;
        }
        double scale = (panelHeight) / (maxValue - minValue);
        for (int j = 0; j < getBarras().length; j++) {
//            int valueP = Math.round(j * barWidth);
            int valueQ = 0;
            int height = (int) Math.round(Math.abs(getBarras()[j].getDado()) * scale);
            if (Math.abs(getBarras()[j].getDado()) >= 0) {
                valueQ += (int) ((maxValue - Math.abs(getBarras()[j].getDado())) * scale);
            } else {
                valueQ += (int) Math.round(maxValue * scale);
                height = -height;
            }
        }
        int top = 0;
        for (int i = 0; i < getBarras().length; i++) {
            int valueX = rect.x + rect.width / 2 - Math.round(i * barWidth) / 2;
//            int valueY = points[1];
            
            float max = (float) Math.abs(getBarras()[i].getDadoMaxVal());
            float data = Math.round(Math.abs(getBarras()[i].getDado()));
            float result = Math.round((data * panelHeight) / max);
            float sub;
            if (getBarras()[i].getDado() < 0) {
                sub = panelHeight;
            } else {
                sub = Math.round(panelHeight - result);

            }

            getBarras()[i].setDadosBarra(valueX, Math.round(rect.y + rect.height/2 - points[1]/4 + sub), Math.round(barWidth) / 2, Math.round(result));
            int [] line = new int[4]; 
            line[0] =valueX;
            line[1] = rect.y+rect.height/2 - points[1]/4;
            line[2] = valueX+ Math.round(barWidth) / 2;
            line[3] = rect.y+rect.height/2 - points[1]/4;
                    
            getBarras()[i].setLine(line);
        }

    }

    @Override
    public void setBounds(Rectangle rect) {
        this.rect = rect;
        this.rect.x = rect.x + rect.width / 2- rect.width/4;
        this.rect.y = rect.y + rect.height/2 - rect.height/4; //- rect.height;
        this.rect.width = rect.width / 2;
        this.rect.height = rect.height / 2;

//        panelWidth = panelWidth * this.rect.width;  
//        panelHeight = panelWidth * this.rect.height;
//        center = getCenter();
        calcularPosicaoBarras();

        for (int i = 0; i < barras.length; i++) {
            barras[i].setRect(this.rect);
        }
        super.setBounds(this.rect);
    }

    public void tornarGlyphQuadrado(int[] point) {
        if (point[0] > point[1]) {
            point[0] = point[1];
        } else if (point[0] < point[1]) {
            point[1] = point[0];
        }
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
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
                                       
                //top
                int lx1 = getBarras()[i].getLineTop()[0];
                int ly1 = getBarras()[i].getLineTop()[1];
                int lx2 = getBarras()[i].getLineTop()[2];
                int ly2 = getBarras()[i].getLineTop()[3];
                g2.drawLine(lx1,ly1,lx2,ly2);
                
                //center
                int cx1 = getBarras()[i].getLineCenter()[0];
                int cy1 = getBarras()[i].getLineCenter()[1];
                int cx2 = getBarras()[i].getLineCenter()[2];
                int cy2 = getBarras()[i].getLineCenter()[3];
                g2.drawLine(cx1,cy1,cx2,cy2);
                
                //buttom
                int bx1 = getBarras()[i].getLineButton()[0];
                int by1 = getBarras()[i].getLineButton()[1];
                int bx2 = getBarras()[i].getLineButton()[2];
                int by2 = getBarras()[i].getLineButton()[3];          
                g2.drawLine(bx1,by1,bx2,by2);
     
            }
        }

    }

    public void calcularPosicaoBarras() {
         int[] points = new int[2];
        points[0] = (int) (rect.width*0.8);
        points[1] = (int) (rect.height*0.8);
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
        int panelHeight = (int) Math.round(points[1])/2;

        float barWidth = panelWidth / getBarras().length;

        if (maxValue == minValue) {
            return;
        }
        double scale = (panelHeight) / (maxValue - minValue);
        for (int j = 0; j < getBarras().length; j++) {
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
            int valueX =0;
            valueX = rect.x + panelWidth - Math.round(i * barWidth)/2;//            int valueY = points[1];
            if(getBarras().length ==1){   
                valueX = rect.x + points[0]/2 - Math.round(i * barWidth)/2;

            }    
                
            float max = (float) Math.abs(getBarras()[i].getDadoMaxVal());
            float data = Math.round(Math.abs(getBarras()[i].getDado()));
            float result = Math.round((data * panelHeight) / max);
            float sub=0;
            if (getBarras()[i].getDado() < 0) {
                sub = panelHeight;
            } else {
                sub = Math.round(panelHeight - result);

            }

            int valueY = (int)(Math.round(rect.y)+sub);
            

            getBarras()[i].setDadosBarra(valueX, valueY, Math.round(barWidth/2) , Math.round(result));
        

            int [] line = new int[4]; 
            line[0] =valueX;
            line[1] = Math.round(rect.y);
            line[2] = Math.round(valueX)+ Math.round(barWidth );
            line[3] = Math.round(rect.y);
            getBarras()[i].setLineTop(line);
            
            int [] line2 = new int[4]; 
            line2[0] =Math.round(valueX);
            line2[1] = Math.round(rect.y)+  points[1];
            line2[2] = +Math.round(valueX)+ Math.round(barWidth);
            line2[3] = Math.round(rect.y)+  points[1];
            getBarras()[i].setLineButton(line2);
            
            
            int [] line3 = new int[4]; 
            line3[0] =Math.round(valueX);
            line3[1] = rect.y + points[1]/2;
            line3[2] = Math.round(valueX) + Math.round(barWidth);
            line3[3] =  rect.y +points[1]/2;
                    
            getBarras()[i].setLineCenter(line3);
        }

    }

    @Override
    public void setBounds(Rectangle rect) {
        int[] points = new int[2];
        points[0] = (int) (rect.width *0.8);
        points[1] = (int) (rect.height *0.8);
        tornarGlyphQuadrado(points);
        this.rect = rect;
        this.rect.x = rect.x + rect.width/2 - (points[0]);
        this.rect.y = (int) (rect.y + rect.height/2 - (points[1]/2));
        this.rect.width = rect.width;
        this.rect.height = rect.height;

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
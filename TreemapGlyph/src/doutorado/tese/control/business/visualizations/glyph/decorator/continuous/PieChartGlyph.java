/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.control.business.visualizations.glyph.decorator.continuous;

import doutorado.tese.dao.ManipuladorArquivo;
import doutorado.tese.util.Constantes;
import doutorado.tese.control.business.visualizations.glyph.Glyph;
//import io.ManipuladorArquivo;
import java.awt.Color;
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
public class PieChartGlyph extends Glyph {

    private Rectangle rect;
    private int quantVar;
    private double r;
//    private double anguloAlfa = 0;
//    private double anguloAcc = 0;
    private List<String> atributosEscolhidaoBase;
    private ManipuladorArquivo manipulador;
    private double porcentagemDado;
//    private int maiorRaio;
    private Slice[] slice;
//    private Point center;
    private int distancia;

    //intancias do grafico de barras
    private double[] values;
    private String[] labels;
    private Color[] colors;
    private String title;
    private float panelWidth;
    private float panelHeight;

    public PieChartGlyph(List<String> variaveisEscolhidasStarGlyph) {
        this.atributosEscolhidaoBase = variaveisEscolhidasStarGlyph;
        slice = new Slice[this.atributosEscolhidaoBase.size()];
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
//                getBarras()[i].paint(g2d);
                int x = getSlices()[i].getDadosBarra()[0];
                int y = getSlices()[i].getDadosBarra()[1];
                int w = getSlices()[i].getDadosBarra()[2];
                int h = getSlices()[i].getDadosBarra()[3];
                int s = getSlices()[i].getDadosBarra()[4];
                int ang = getSlices()[i].getDadosBarra()[5];

                //codigo cada barra
                 g2.setColor(Color.decode("#fffffff"));
                 g2.fillArc(x,y,w,h, s,90);
                 g2.setColor(Color.decode(Constantes.getCorTreemap()[i]));
                 g2.drawArc(x,y,w,h, s,90);
                 g2.fillArc(x,y,w,h, s,ang);

                 //getBarras()[i].setCurValue(getBarras()[].getDado());


            }
        }

    }

    public void calcularPartes() {
        int[] points = new int[2];
        points[0] = rect.width;
        points[1] = rect.height;
        tornarGlyphQuadrado(points);
        double total = 0.0D;
        double curValue = 0.0D;
        for (int i = 0; i <  getSlices().length; i++) {
            total +=  getSlices()[i].getDado();                      
        }
        int startAngle = 0;
        
        for (int i = 0; i <  getSlices().length; i++) {
            startAngle =  (int) curValue;
//                    (int) (curValue * 360 /getSlices()[i].getDadoMaxVal() );
            
            float max = (float) getSlices()[i].getDadoMaxVal();
            float data =  Math.round(getSlices()[i].getDado());
            float result = (data*90)/max;
            float sub = 90-result;
            
            int arcAngle =  90;
            int arcPorcent = (int) result;
//                    (int) (getSlices()[i].getDado()*360 /getSlices()[i].getDadoMaxVal());
            //int arcAngle = (int) (getSlices()[i].getDado()/360);

            int positionX = rect.x +rect.width/2 -points[0]/4 ;
            int positionY = rect.y+rect.height/2 -points[1]/4;
            int w = points[0]/2;
            int h = points[1]/2;
            
            getSlices()[i].setDadosBarra(positionX,positionY,w,h, startAngle,arcPorcent);
            curValue += 90;
            
        }
    }
  
     public void tornarGlyphQuadrado(int[] point) {
        if (point[0] > point[1]) {
            point[0] = point[1];
        } else if (point[0] < point[1]) {
            point[1] = point[0];
        }
    }
     
        @Override
        public void setBounds
        (Rectangle rect
        
            ) {
        this.rect = rect;
            this.rect.x = rect.x + 2;
            this.rect.y = rect.y + 2;
            this.rect.width = rect.width - 2;
            this.rect.height = rect.height - 2;

//        panelWidth = panelWidth * this.rect.width;  
//        panelHeight = panelWidth * this.rect.height;
//        center = getCenter();
            calcularPartes();

            for (int i = 0; i < slice.length; i++) {
                slice[i].setRect(this.rect);
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
        return "PieChart - teste";
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

    public Slice[] getSlices() {
        return slice;
    }

    public void setBarras(Slice[] slice) {
        this.slice = slice;
    }

    @Override
    public Object whoAmI() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

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
public class ProfileGlyph extends Glyph {

    private Rectangle rect;
    private Rectangle rectGrafico;
    private int quantVar;
    private int[] xPoints;
    private int[] yPoints;
    private List<String> atributosEscolhidaoBase;
    private ManipuladorArquivo manipulador;
    private double porcentagemDado;
    private Bar[] barras;
    private int distancia;
    private int quantBarras;
    private int[] pontosLinhaCentro;

    public ProfileGlyph(List<String> variaveisEscolhidasStarGlyph) {
        this.atributosEscolhidaoBase = variaveisEscolhidasStarGlyph;
        barras = new Bar[this.atributosEscolhidaoBase.size()];
        quantBarras = this.atributosEscolhidaoBase.size();
    }

    /**
     * Calcula quantos porcentos o dado atual da barra corrente equivale ao
     * valor max da coluna.
     *
     * @param g2d
     * @param dadoColuna
     * @param maxValCol
     * @return Porcentagem equivalente ao valor max da coluna.
     */
    private double calcularPorcentagemDado(double dadoColuna, double maxValCol) {
        return (dadoColuna * 100) / maxValCol;
    }

//    private double calcularPorcentagemParaR(double porcentagemDado, double maiorRaio) {
//        return (porcentagemDado * maiorRaio) / 100;
//    }
    @Override
    public void paint(Graphics2D g2d) {
        if (getQuantVar() != 0) {
            for (int i = 0; i < getQuantVar(); i++) {
                g2d.setColor(Color.decode(Constantes.getCor()[i]));
                //getBarras()[i].paint(g2d);
                int x = getBarras()[i].getBarraX();
                int y = getBarras()[i].getBarraY();
                int w = getBarras()[i].getBarraW();
                int h = getBarras()[i].getBarraH();

                //codigo cada barra
                g2d.fillRect(x, y, w, h);
                g2d.setColor(Color.black);
                g2d.drawRect(x, y, w, h);
            }
        }
        g2d.drawLine(this.rectGrafico.x,
                this.rectGrafico.y,
                this.rectGrafico.x + this.rectGrafico.width,
                this.rectGrafico.y);

        g2d.drawLine(pontosLinhaCentro[0], pontosLinhaCentro[1], pontosLinhaCentro[2], pontosLinhaCentro[3]);

        g2d.drawLine(this.rectGrafico.x,
                this.rectGrafico.y + this.rectGrafico.height,
                this.rectGrafico.x + this.rectGrafico.width,
                this.rectGrafico.y + this.rectGrafico.height);
    }

    public void calcularPosicaoBarras() {
        int widthEachbar = Math.round(this.rectGrafico.width / getBarras().length);
        int meiaAltura = Math.round(this.rectGrafico.height / 2.f);
        
        int xBarra = rectGrafico.x;
        int yBarra = rectGrafico.y;
        double porcentagemDado;
        for (Bar barra : getBarras()) {
            if (barra.getDado() > 0) {
                porcentagemDado= calcularPorcentagemDado(barra.getDado(), barra.getDadoMaxVal());

                int alturaBarra = (int) Math.round(calcularPorcentagemAlturaBarra(porcentagemDado, meiaAltura));
                int novaAltura = meiaAltura - alturaBarra;//aqui a meiaAltura seria a altura max de uma barra
                barra.setPosicaoBarra(xBarra, yBarra + novaAltura, widthEachbar, alturaBarra);

                xBarra += widthEachbar;
            } else {
                porcentagemDado = calcularPorcentagemDado(Math.abs(barra.getDado()), barra.getDadoMaxVal());
                int alturaBarra = (int) Math.round(calcularPorcentagemAlturaBarra(porcentagemDado, meiaAltura));
                barra.setPosicaoBarra(xBarra, pontosLinhaCentro[1], widthEachbar, alturaBarra);

                xBarra += widthEachbar;
            }
        }
    }

    private double calcularPorcentagemAlturaBarra(double porcentagemDado, double alturaMaxBarra) {
        return (porcentagemDado * alturaMaxBarra) / 100;
    }

    @Override
    public void setBounds(Rectangle rect) {
        super.setBounds(rect);
        montarRetangulo();
        calcularPosicaoBarras();
    }

    private void montarRetangulo() {
        int[] points = new int[2];

        points[0] = getBounds().width;
        points[1] = getBounds().height;

        transformarRetanguloEmQuadrado(points);

        int ladoInterior = Math.round(points[0] * getPectSobreposicao());

        xPoints = new int[2];
        yPoints = new int[2];

        xPoints[0] = getBounds().x + Math.round(getBounds().width / 2) - Math.round(ladoInterior / 2);//ponto x inicial retangulo interior
        yPoints[0] = getBounds().y + Math.round(getBounds().height / 2) - Math.round(ladoInterior / 2);//ponto y inicial retangulo interior

        xPoints[1] = ladoInterior;
        yPoints[1] = ladoInterior;
        this.rect = new Rectangle(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);

        montarRetanguloInterno();
    }

    public void montarRetanguloInterno() {
        int lado = Math.round(this.rect.height * 0.9f);
        xPoints = new int[2];
        yPoints = new int[2];

        xPoints[0] = this.rect.x + Math.round(this.rect.width / 2) - Math.round(lado / 2);//ponto x inicial retangulo interior
        yPoints[0] = this.rect.y + Math.round(this.rect.height / 2) - Math.round(lado / 2);//ponto y inicial retangulo interior
        xPoints[1] = lado;
        yPoints[1] = lado;
        rectGrafico = new Rectangle(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
        pontosLinhaCentro = new int[]{this.rectGrafico.x,
            this.rectGrafico.y + Math.round(this.rectGrafico.height / 2.f),
            this.rectGrafico.x + this.rectGrafico.width,
            this.rectGrafico.y + Math.round(this.rectGrafico.height / 2.f)};
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

    /**
     * @return the quantBarras
     */
    public int getQuantBarras() {
        return quantBarras;
    }

    /**
     * @param quantBarras the quantBarras to set
     */
    public void setQuantBarras(int quantBarras) {
        this.quantBarras = quantBarras;
    }

}

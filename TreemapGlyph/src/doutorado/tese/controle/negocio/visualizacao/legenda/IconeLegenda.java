/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.controle.negocio.visualizacao.legenda;

import doutorado.tese.util.Constantes;
import doutorado.tese.controle.negocio.visualizacao.glyph.Glyph;
import doutorado.tese.controle.negocio.visualizacao.glyph.decorator.categorical.variaveisvisuais.letters.Letra;
import doutorado.tese.controle.negocio.visualizacao.glyph.decorator.categorical.variaveisvisuais.numbers.Numeral;
import doutorado.tese.controle.negocio.visualizacao.glyph.decorator.categorical.variaveisvisuais.shapes.FormaGeometrica;
import doutorado.tese.controle.negocio.visualizacao.glyph.factorys.variaveisvisuais.GeometryFactory;
import doutorado.tese.controle.negocio.visualizacao.glyph.factorys.variaveisvisuais.GeometryFactory.FORMAS;
import doutorado.tese.controle.negocio.visualizacao.glyph.decorator.categorical.variaveisvisuais.texture.Textura;
import doutorado.tese.controle.negocio.visualizacao.glyph.decorator.continuous.Bar;
import doutorado.tese.controle.negocio.visualizacao.glyph.decorator.continuous.BarChart;
import doutorado.tese.dao.ManipuladorArquivo;
import doutorado.tese.modelo.Coluna;
import doutorado.tese.util.ColorInterpolator;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;
import static javafx.scene.paint.Color.color;
import javax.swing.Icon;

/**
 *
 * @author Anderson Soares
 */
public class IconeLegenda implements Icon {

    private int width = 32;
    private int height = 32;
    private int dimensao;
    private String valor = null;
    private double maxValorContIcon;
    private double minValorContIcon;

    private BasicStroke stroke = new BasicStroke(4);
    private FORMAS.GLYPH_FORMAS valorForma = null;
    private List<String> atributosEscolhidosGlyphContinuo = null;

    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();

        Rectangle bounds = new Rectangle(x, y, width, height);
        switch (dimensao) {
            case Constantes.COR_TREEMAP:
                if (valor == null) {
                    //TODO Criar legenda de cores continuas. 
                    ColorInterpolator interpolator = new ColorInterpolator();
                    interpolator.config(minValorContIcon, maxValorContIcon, Color.decode("#800080"), Color.WHITE);
                    x *= 0.1;
                    GradientPaint grad = new GradientPaint(x, y, Color.WHITE,x+100,y+height, Color.decode("#800080"));
                    g2d.setPaint(grad);
                    g2d.fillRect(x, y, 100, height);
                    g2d.setColor(Color.black);
                    g2d.drawRect(x, y, 100, height);
                } else {
                    Glyph iconColorTreemap = new FormaGeometrica();
                    FormaGeometrica shape = (FormaGeometrica) iconColorTreemap;
                    shape.setDrawBehavior(GeometryFactory.create(FORMAS.GLYPH_FORMAS.RETANGULO));
                    shape.setPectSobreposicao(0.65f);
                    shape.setOverlappingActivated(true);
                    shape.setCorLegenda(Color.decode(valor));
                    shape.setBounds(bounds);
                    shape.paint(g2d);
                }
                break;
            case 0:
                Glyph glyph = new Textura(Color.GRAY, Color.WHITE);
                Textura textura = (Textura) glyph;
                textura.setNomeTextura(valor);
                textura.setPectSobreposicao(0.84f);
                textura.setOverlappingActivated(true);
                textura.setBounds(bounds);
                textura.paint(g2d);
                break;
            case 1:
                if (valor==null) {
//                    TODO Criar legenda de cores continuas. 
                    x *= 0.1;
                    GradientPaint grad = new GradientPaint(x, y, Color.WHITE,x+100,y+ height, Color.YELLOW);
                    g2d.setPaint(grad);
                    g2d.fillRect(x,y, 100, height);
                    g2d.setColor(Color.black);
                    g2d.drawRect(x,y, 100, height);
                    break;
                } else {
                    Glyph iconRectColor = new FormaGeometrica();
                    FormaGeometrica shapeColor = (FormaGeometrica) iconRectColor;
                    shapeColor.setDrawBehavior(GeometryFactory.create(FORMAS.GLYPH_FORMAS.RETANGULO));
                    shapeColor.setPectSobreposicao(0.65f);
                    shapeColor.setOverlappingActivated(true);
                    shapeColor.setCorLegenda(Color.decode(valor));
                    shapeColor.setBounds(bounds);
                    shapeColor.paint(g2d);
                }
                break;
            case 2:
                Glyph iconGlyph = new FormaGeometrica();
                FormaGeometrica shapeIcon = (FormaGeometrica) iconGlyph;
                shapeIcon.setDrawBehavior(GeometryFactory.create(valorForma));
                shapeIcon.setPectSobreposicao(0.65f);
                shapeIcon.setOverlappingActivated(true);
                shapeIcon.setBounds(bounds);
                shapeIcon.paint(g2d);
                break;
            case 3:
                Glyph glyphLetra = new Letra();
                Letra letra = (Letra) glyphLetra;
                letra.setLetra(valor);
                letra.setPectSobreposicao(0.65f);
                letra.setOverlappingActivated(true);
                letra.setBounds(bounds);
                letra.paint(g2d);
                break;
            case 4:
                Glyph glyphNumeral = new Numeral();
                Numeral num = (Numeral) glyphNumeral;
                num.setLetra(valor);
                num.setPectSobreposicao(0.65f);
                num.setOverlappingActivated(true);
                num.setBounds(bounds);
                num.paint(g2d);
                break;
            case 5:
                if (valor==null) {
//                    System.out.println(getAtributosEscolhidosGlyphContinuo());
                    BarChart bar = new BarChart(getAtributosEscolhidosGlyphContinuo());
                    bar.setQuantVar(getAtributosEscolhidosGlyphContinuo().size());
                    bar.setPectSobreposicao(0.85f);
                    bar.setOverlappingActivated(true);
                    for (int i = 0; i < getAtributosEscolhidosGlyphContinuo().size(); i++) {
                        String nomeColunaEscolhida = getAtributosEscolhidosGlyphContinuo().get(i);
                        Coluna coluna = ManipuladorArquivo.getColuna(nomeColunaEscolhida);
                        double dado = 10;
                        double dadoMaxVal = 10;//0 - maxValue; 1 - minValue
                        bar.getBarras()[i] = new Bar(dado, dadoMaxVal);
                    }   
                    Rectangle r = new Rectangle(10,10,bounds.width*5,bounds.height*5);
                    
                    bar.setBounds(r);
                    bar.paint(g2d);
                    
                }
                break;
            default:
                inserirIconeAusente(g2d, x, y);
                break;
        }
        g2d.dispose();
    }

    @Override
    public int getIconWidth() {
        return width;
    }

    @Override
    public int getIconHeight() {
        return height;
    }

    public void setDimensao(int dimensao) {
        this.dimensao = dimensao;
    }

    private void inserirIconeAusente(Graphics2D g2d, int x, int y) {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x + 1, y + 1, width - 2, height - 2);

        g2d.setColor(Color.BLACK);
        g2d.drawRect(x + 1, y + 1, width - 2, height - 2);

        g2d.setColor(Color.RED);

        g2d.setStroke(stroke);
        g2d.drawLine(x + 10, y + 10, x + width - 10, y + height - 10);
        g2d.drawLine(x + 10, y + height - 10, x + width - 10, y + 10);
    }

    void setValorIcon(String valor) {
        this.valor = valor;
    }

    void setValorIcon(FORMAS.GLYPH_FORMAS forma) {
        this.valorForma = forma;
    }

    /**
     * @param maxValorContIcon the maxValorContIcon to set
     */
    public void setMaxValorContIcon(double maxValorContIcon) {
        this.maxValorContIcon = maxValorContIcon;
    }

    /**
     * @param minValorContIcon the minValorContIcon to set
     */
    public void setMinValorContIcon(double minValorContIcon) {
        this.minValorContIcon = minValorContIcon;
    }

    public List<String> getAtributosEscolhidosGlyphContinuo() {
        return atributosEscolhidosGlyphContinuo;
    }
    
    public void setAtributosEscolhidosGlyphContinuo(List<String> atributosEscolhidosGlyphContinuo) {
        this.atributosEscolhidosGlyphContinuo = atributosEscolhidosGlyphContinuo;
    }
}

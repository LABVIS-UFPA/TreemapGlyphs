/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.control.business.visualizations.legenda;

import doutorado.tese.util.Constantes;
import doutorado.tese.control.business.visualizations.glyph.Glyph;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.text.Text;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.position.Position;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.shapes.FormaGeometrica;
import doutorado.tese.control.business.visualizations.glyph.factorys.variaveisvisuais.GeometryFactory;
import doutorado.tese.control.business.visualizations.glyph.factorys.variaveisvisuais.GeometryFactory.FORMAS;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.texture.Textura;
import doutorado.tese.control.business.visualizations.glyph.decorator.continuous.Bar;
import doutorado.tese.control.business.visualizations.glyph.decorator.continuous.ProfileGlyph;
import doutorado.tese.util.ColorInterpolator;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;
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
    private Glyph glyph;

    private final BasicStroke stroke = new BasicStroke(4);
    private FORMAS.GLYPH_FORMAS valorForma = null;
    private List<String> atributosEscolhidosGlyphContinuo = null;
    private Constantes.POSICOES posicao;

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();

        Rectangle bounds = new Rectangle(x, y, width, height);
        switch (dimensao) {
            case Constantes.COR_TREEMAP:
                montarIconeCorTreemap(g2d, x, y, bounds);
                break;
            case 0:
                montarIconeGlyphTextura(g2d, valor, bounds);
                break;
            case 1:
                montarIconeGlyphCor(g2d, x, y, height, valor, bounds);
                break;
            case 2:
                montarIconeGlyphForma(g2d, valorForma, bounds);
                break;
            case 3:
                montarIconeGlyphText(g2d, valor, bounds);
                break;
            case 4:
                montarIconeGlyphPosition(g2d, posicao, bounds);
                break;
            case 5:
                if (valor == null) {
                    montarIconeProfileGlyph(g2d, bounds);
                }
                break;
            case 100:
                //Desenhar glyph como um icone
                getGlyph().setBounds(bounds);
                getGlyph().paint(g2d);
                break;
            default:
                inserirIconeAusente(g2d, x, y);
                break;
        }
        g2d.dispose();
    }

    private void montarIconeCorTreemap(Graphics2D g2d, int x, int y, Rectangle bounds) {
        if (valor == null) {
            ColorInterpolator interpolator = new ColorInterpolator();
            interpolator.config(minValorContIcon, maxValorContIcon, Color.decode("#800080"), Color.WHITE);
            x *= 0.1;
            GradientPaint grad = new GradientPaint(x, y, Color.WHITE, x + 100, y + height, Color.decode("#800080"));
            g2d.setPaint(grad);
            g2d.fillRect(x, y, 100, height);
            g2d.setColor(Color.black);
            g2d.drawRect(x, y, 100, height);
        } else {
            FormaGeometrica iconColorTreemap = new FormaGeometrica();
            iconColorTreemap.setDrawBehavior(GeometryFactory.create(FORMAS.GLYPH_FORMAS.QUADRADO));
            iconColorTreemap.setPectSobreposicao(0.65f);
            iconColorTreemap.setOverlappingActivated(true);
            iconColorTreemap.setCorLegenda(Color.decode(valor));
            iconColorTreemap.setLegenda(true);
            iconColorTreemap.setBounds(bounds);
            iconColorTreemap.paint(g2d);
        }
    }

    private void montarIconeGlyphTextura(Graphics2D g2d, String valor, Rectangle bounds) {
        Textura textura = new Textura(Color.GRAY, Color.WHITE);
        textura.setNomeTextura(valor);
        textura.setPectSobreposicao(0.84f);
        textura.setOverlappingActivated(true);
        textura.setBounds(bounds);
        textura.paint(g2d);
    }

    private void montarIconeGlyphCor(Graphics2D g2d, int x, int y, int height, String valor, Rectangle bounds) {
        if (valor == null) {
            x *= 0.1;
            GradientPaint grad = new GradientPaint(x, y, Color.WHITE, x + 100, y + height, Color.YELLOW);
            g2d.setPaint(grad);
            g2d.fillRect(x, y, 100, height);
            g2d.setColor(Color.black);
            g2d.drawRect(x, y, 100, height);
        } else {
            FormaGeometrica shapeColor = new FormaGeometrica();
            shapeColor.setDrawBehavior(GeometryFactory.create(FORMAS.GLYPH_FORMAS.QUADRADO));
            shapeColor.setPectSobreposicao(0.65f);
            shapeColor.setOverlappingActivated(true);
            shapeColor.setCorLegenda(Color.decode(valor));
            shapeColor.setLegenda(true);
            shapeColor.setBounds(bounds);
            shapeColor.paint(g2d);
        }
    }
    
    private void montarIconeGlyphForma(Graphics2D g2d, FORMAS.GLYPH_FORMAS valorForma, Rectangle bounds) {
        FormaGeometrica shapeIcon = new FormaGeometrica();
        shapeIcon.setDrawBehavior(GeometryFactory.create(valorForma));
        shapeIcon.setPectSobreposicao(0.65f);
        shapeIcon.setOverlappingActivated(true);
        shapeIcon.setBounds(bounds);
        shapeIcon.paint(g2d);
    }

    private void montarIconeGlyphText(Graphics2D g2d, String valor, Rectangle bounds) {
        Text letra = new Text();
        letra.setLetra(valor);
        letra.setPectSobreposicao(0.65f);
        letra.setOverlappingActivated(true);
        letra.setBounds(bounds);
        letra.paint(g2d);
    }

    private void montarIconeGlyphPosition(Graphics2D g2d, Constantes.POSICOES posicao, Rectangle bounds) {
        Position p = new Position();
        p.setPosicao(posicao);
        p.setPectSobreposicao(0.65f);
        p.setOverlappingActivated(true);
        p.setBounds(bounds);
        p.paint(g2d);
    }

    private void montarIconeProfileGlyph(Graphics2D g2d, Rectangle bounds) {
        ProfileGlyph bar = new ProfileGlyph(getAtributosEscolhidosGlyphContinuo());
        bar.setQuantVar(getAtributosEscolhidosGlyphContinuo().size());
        bar.setPectSobreposicao(0.85f);
        bar.setOverlappingActivated(true);
        double dado = 20;
        for (int i = 0; i < getAtributosEscolhidosGlyphContinuo().size(); i++) {
            dado -= 2;
            double dadoMaxVal = 20;//0 - maxValue; 1 - minValue
            bar.getBarras()[i] = new Bar(dado, dadoMaxVal);
        }
        Rectangle r = new Rectangle(10, 10, bounds.width * 3, bounds.height * 4);

        bar.setBounds(r);
        bar.paint(g2d);
    }

    /**
     * @param width the width to set
     */
    public void setIconWidth(int width) {
        this.width = width;
    }

    /**
     * @param height the height to set
     */
    public void setIconHeight(int height) {
        this.height = height;
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

    void setValorIcon(Constantes.POSICOES posicao) {
        this.posicao = posicao;
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

    /**
     * @return the glyph
     */
    public Glyph getGlyph() {
        return glyph;
    }

    /**
     * @param glyph the glyph to set
     */
    public void setGlyph(Glyph glyph) {
        this.glyph = glyph;
    }

}

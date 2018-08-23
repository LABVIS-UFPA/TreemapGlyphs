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
import doutorado.tese.util.ColorInterpolator;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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
    private String valor;
    private double maxValorContIcon;
    private double minValorContIcon;

    private BasicStroke stroke = new BasicStroke(4);
    private FORMAS.GLYPH_FORMAS valorForma;

    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();

        Rectangle bounds = new Rectangle(x, y, width, height);
        switch (dimensao) {
            case Constantes.COR_TREEMAP:
                if (valor == null) {
                    //TODO Criar legenda de cores continuas. 
                    ColorInterpolator interpolator = new ColorInterpolator();
                    interpolator.config(minValorContIcon, maxValorContIcon, Color.decode("#800080"), Color.WHITE);
//                    Color cor = interpolator.interpolate(Double.parseDouble(nodeItem.getMapaDetalhesItem().get(c)));
                    x *= 0.1;
//                    System.out.println("\tdepois\tX:"+x+"\tY:"+y);
                    GradientPaint grad = new GradientPaint(0, 0, Color.WHITE,100, height, Color.decode("#800080"));
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
                if (valor.equals("")) {
                    //TODO Criar legenda de cores continuas. 
//                    ColorInterpolator interpolator = new ColorInterpolator();
//                    interpolator.config(c.maiorMenorValues[0],c.maiorMenorValues[1] , Color.decode("#800080"), Color.WHITE);
//                    Color cor = interpolator.interpolate(Double.parseDouble(nodeItem.getMapaDetalhesItem().get(c)));
//                    nodeItem.setColor(cor);
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
}

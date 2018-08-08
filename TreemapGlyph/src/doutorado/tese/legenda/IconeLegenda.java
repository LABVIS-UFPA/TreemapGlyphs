/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.legenda;

import doutorado.tese.util.Constantes;
import doutorado.tese.visualizacao.glyph.Glyph;
import doutorado.tese.visualizacao.glyph.decorator.variaveisvisuais.letters.Letra;
import doutorado.tese.visualizacao.glyph.decorator.variaveisvisuais.shapes.FormaGeometrica;
import doutorado.tese.visualizacao.glyph.factorys.variaveisvisuais.GeometryFactory;
import doutorado.tese.visualizacao.glyph.factorys.variaveisvisuais.GeometryFactory.FORMAS;
import doutorado.tese.visualizacao.glyph.numeros.Numeral;
import doutorado.tese.visualizacao.glyph.decorator.variaveisvisuais.texture.Textura;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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

    private BasicStroke stroke = new BasicStroke(4);
    private FORMAS.GLYPH_FORMAS valorForma;

    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();

        Rectangle bounds = new Rectangle(x, y, width, height);
        switch (dimensao) {
            case Constantes.COR_TREEMAP:
//                FormaGeometrica corTreemap = GeometryFactory.create(bounds, Color.decode(valor), GeometryFactory.FORMAS.GLYPH_FORMAS.RETANGULO);
//                corTreemap.paint(g);
//                g.setColor(Color.decode(valor));
//                g.fillRect(x, y, width, height);
                Glyph iconColorTreemap = new FormaGeometrica();
                FormaGeometrica shape = (FormaGeometrica) iconColorTreemap;
                shape.setDrawBehavior(GeometryFactory.create(FORMAS.GLYPH_FORMAS.RETANGULO));
                shape.setPectSobreposicao(0.65f);
                shape.setOverlappingActivated(true);
                shape.setCorLegenda(Color.decode(valor));
                shape.setBounds(bounds);
                shape.paint(g2d);
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
                if (valor == null) {
                    //TODO Criar legenda de cores continuas. 
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
                Numeral num = new Numeral(bounds, valor, true);
                num.setFonte(new Font("Arial", Font.PLAIN, 12));
                num.paint(g);
                break;
            default:
                inserirIconeAusente(g2d, x, y);
                break;
        }
        g2d.dispose();
    }

    public int getIconWidth() {
        return width;
    }

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
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.legenda;

import doutorado.tese.util.Constantes;
import doutorado.tese.util.Metadados;
import doutorado.tese.visualizacao.glyph.alfabeto.Letra;
import doutorado.tese.visualizacao.glyph.formasgeometricas.FormaGeometrica;
import doutorado.tese.visualizacao.glyph.formasgeometricas.GeometryFactory;
import doutorado.tese.visualizacao.glyph.numeros.Numeral;
import doutorado.tese.visualizacao.glyph.texture.Textura;
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
    private GeometryFactory.FORMAS.GLYPH_FORMAS valorForma;

    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();

        Rectangle bounds = new Rectangle(x, y, width, height);
        switch (dimensao) {
            case Constantes.COR_TREEMAP:
                FormaGeometrica corTreemap = GeometryFactory.create(bounds, Color.decode(valor), GeometryFactory.FORMAS.GLYPH_FORMAS.RETANGULO);
                corTreemap.paint(g);
                break;
            case 0:
                Textura t = new Textura(bounds, valor);
                t.paint(g);
                break;
            case 1:
                if (valor == null) {
                    //TODO Criar legenda de cores continuas. 
               } else {
                    FormaGeometrica f = GeometryFactory.create(bounds, Color.decode(valor), GeometryFactory.FORMAS.GLYPH_FORMAS.CIRCULO);
                    f.paint(g);
                }
                break;
            case 2:
                FormaGeometrica forma = GeometryFactory.create(bounds, null, valorForma);
                forma.paint(g);
                break;
            case 3:
                Letra letra = new Letra(bounds, valor, true);
                letra.setFonte(new Font("Arial", Font.PLAIN, 12));
                letra.paint(g);
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

    void setValorIcon(GeometryFactory.FORMAS.GLYPH_FORMAS forma) {
        this.valorForma = forma;
    }
}

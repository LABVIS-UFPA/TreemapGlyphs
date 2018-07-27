/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.visualizacao.glyph.texture;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import net.bouthier.treemapAWT.TMPatternFactory;

/**
 *  O objeto textura ocupa 80% do item do treemap
 * 
 * @author Anderson Soares
 */
public class Textura {

    private int[] xPoints;
    private int[] yPoints;
    private Rectangle rect;
    private String nome;
    TMPatternFactory textura;

    public Textura(Rectangle r, String nome) {
        this.rect = r;
        setBounds(this.rect);
        this.nome = nome;
        montarRetangulo();
        textura = TMPatternFactory.getInstance();
    }

    public void setBounds(Rectangle rect) {
        this.rect = rect;
    }

    public Rectangle getBounds() {
        return rect;
    }

    public int getArea() {
        return xPoints[1] * yPoints[1];
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setPaint(textura.get(nome));
        
        g2d.fillRect(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
    }

    private void verificarRetangulo(int[] point) {
        if (point[0] > point[1]) {
            point[0] = point[1];
        } else if (point[0] < point[1]) {
            point[1] = point[0];
        }
    }

    private void montarRetangulo() {
        int[] points = new int[2];

        points[0] = rect.width;
        points[1] = rect.height;

        verificarRetangulo(points);

        int width = Math.round(points[0] * 0.8f);
        int height = Math.round(points[1] * 0.8f);

        xPoints = new int[2];
        yPoints = new int[2];

        xPoints[0] = rect.x + rect.width / 2 - width / 2;
        yPoints[0] = rect.y + rect.height / 2 - height / 2;

        xPoints[1] = width;
        yPoints[1] = height;
    }
}

package doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.shapes;

import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.DrawBehavior;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

public class Serrilhado implements DrawBehavior {

    private int[] xPoints;
    private int[] yPoints;
    private Color cor;
    private Rectangle glyphBounds;
    private List<Point> listaPontosPoligono;
    private Polygon polygon;
    private Path2D path;

    public Serrilhado() {
        cor = Color.decode("#f0f0f0");
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //desenha background da forma
//        g2d.setColor(this.cor);
//        g2d.fillRect(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
                
        g2d.setColor(cor);
        g2d.fill(path);
//        g2d.setColor(Color.BLACK);
//        g2d.draw(path);
//        g2d.setStroke(new BasicStroke(1.5f));
//        g2d.setStroke(new BasicStroke(1.0f));
    }

    private void drawSerrilhado() {
        polygon = new Polygon();
        listaPontosPoligono.forEach((Point point) -> {
            polygon.addPoint(point.x, point.y);
        });
        path = new Path2D.Double();
        path.append(polygon, true);
    }

    @Override
    public void tornarGlyphQuadrado(int[] point) {
        if (point[0] > point[1]) {
            point[0] = point[1];
        } else if (point[0] < point[1]) {
            point[1] = point[0];
        }
    }

    private void montarQuadradoSerrilhado() {
        int[] points = new int[2];

        points[0] = getGlyphBounds().width;
        points[1] = getGlyphBounds().height;

        tornarGlyphQuadrado(points);
        montarQuadradoSobreposicao(points);

        int slices = 6;
        int sliceWidth = xPoints[1] / slices;

        int trianguloLado = (int) (xPoints[1] * 0.17);
        listaPontosPoligono = new ArrayList<>();
        
        montaTrianguloSuperior(sliceWidth, trianguloLado);
        montaTrianguloDireita(sliceWidth, trianguloLado);
        montaTrianguloInferior(sliceWidth, trianguloLado);
        montaTrianguloEsquerda(sliceWidth, trianguloLado);
        
        drawSerrilhado();
    }

    private void montaTrianguloEsquerda(int sliceWidth, int trianguloLado) {
        for (int i = (6 * sliceWidth) - sliceWidth; i > sliceWidth; i -= sliceWidth) {
            listaPontosPoligono.add(new Point((xPoints[0] + trianguloLado), yPoints[0] + (trianguloLado * 2) + (i - sliceWidth * 2)));
            //center
            listaPontosPoligono.add(new Point(xPoints[0], yPoints[0] + trianguloLado + Math.round(trianguloLado / 2) + (i - sliceWidth * 2)));
            //right
            listaPontosPoligono.add(new Point((xPoints[0] + trianguloLado), yPoints[0] + trianguloLado + (i - sliceWidth * 2)));
        }
    }

    private void montaTrianguloDireita(int sliceWidth, int trianguloLado) {
        for (int i = sliceWidth; i < (6 * sliceWidth) - sliceWidth; i += sliceWidth) {
            //left
            Point p1 = new Point((xPoints[0]), yPoints[0] + (i - sliceWidth));
            //center
            Point p2 = new Point((xPoints[0] + trianguloLado), yPoints[0] + trianguloLado - Math.round(trianguloLado / 2) + (i - sliceWidth));
            //right
            Point p3 = new Point((xPoints[0]), yPoints[0] + trianguloLado + (i - sliceWidth));
            p1.translate(sliceWidth * 5, sliceWidth);
            p2.translate(sliceWidth * 5, sliceWidth);
            p3.translate(sliceWidth * 5, sliceWidth);
            listaPontosPoligono.add(p1);
            listaPontosPoligono.add(p2);
            listaPontosPoligono.add(p3);
        }
    }

    private void montaTrianguloInferior(int sliceWidth, int trianguloLado) {
        for (int i = (6 * sliceWidth) - sliceWidth; i > sliceWidth; i -= sliceWidth) {            
            //left
            Point p1 = new Point((i - sliceWidth * 2) + (xPoints[0] + trianguloLado), yPoints[0]);
            //center
            Point p2 = new Point((i - sliceWidth * 2) + (Math.round(trianguloLado / 2) + xPoints[0] + trianguloLado), trianguloLado + yPoints[0]);
            //right
            Point p3 = new Point((i - sliceWidth * 2) + (xPoints[0] + (trianguloLado * 2)), yPoints[0]);
            p3.translate(0, sliceWidth * 5);
            p2.translate(0, sliceWidth * 5);
            p1.translate(0, sliceWidth * 5);
            listaPontosPoligono.add(p3);
            listaPontosPoligono.add(p2);
            listaPontosPoligono.add(p1);
        }
    }

    private void montaTrianguloSuperior(int sliceWidth, int trianguloLado) {
        for (int i = sliceWidth; i < (6 * sliceWidth) - sliceWidth; i += sliceWidth) {
            //left
            listaPontosPoligono.add(new Point((i - sliceWidth) + (xPoints[0] + trianguloLado), yPoints[0] + trianguloLado));
            //center
            listaPontosPoligono.add(new Point((i - sliceWidth) + (Math.round(trianguloLado / 2) + xPoints[0] + trianguloLado), yPoints[0]));
            //right
            listaPontosPoligono.add(new Point((i - sliceWidth) + (xPoints[0] + (trianguloLado * 2)), yPoints[0] + trianguloLado));
        }
    }

    public void montarQuadradoSobreposicao(int[] points) {
        int widthSobreposicao = (int) Math.round(points[0] * PERCENT_SOBREPOSICAO);
        int heightSobreposicao = (int) Math.round(points[1] * PERCENT_SOBREPOSICAO);

        xPoints = new int[2];
        yPoints = new int[2];

        xPoints[0] = getGlyphBounds().x + Math.round(getGlyphBounds().width / 2) - Math.round(widthSobreposicao / 2);
        yPoints[0] = getGlyphBounds().y + Math.round(getGlyphBounds().height / 2) - Math.round(heightSobreposicao / 2);

        xPoints[1] = widthSobreposicao;
        yPoints[1] = heightSobreposicao;
    }

    public Rectangle getGlyphBounds() {
        return this.glyphBounds;
    }

    @Override
    public void setGlyphBounds(Rectangle glyphBounds) {
        this.glyphBounds = glyphBounds;
        montarQuadradoSerrilhado();
    }

    @Override
    public int getArea() {
        return xPoints[1] * yPoints[1];
    }

    @Override
    public Shape getClipShape() {
        return getGlyphBounds();
    }

    @Override
    public void drawForeground(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.draw(polygon);
    }

    @Override
    public Serrilhado clone() throws CloneNotSupportedException {
        try {
            // call clone in Object.
            return (Serrilhado) super.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("Cloning not allowed.");
            return this;
        }
    }

    @Override
    public String toString() {
        super.toString();
        return Serrilhado.class.getSimpleName();
    }

    public void setColor(Color cor) {
        this.cor = cor;
    }

}

package doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

public class Serrinhado implements DrawBehavior {

    private int[] xPoints;
    private int[] yPoints;
    private Color cor;
    private Rectangle glyphBounds;
    List<Polygon> listaPolygonSuperior = new ArrayList<>();
    List<Polygon> listaPolygonInferior = new ArrayList<>();
    List<Polygon> listaPolygonDireita = new ArrayList<>();
    List<Polygon> listaPolygonEsquerda = new ArrayList<>();
    private int[] xPointsInterno;
    private int[] yPointsInterno;

    public Serrinhado() {
        cor = Color.decode("#A9A9A9");
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //desenha background da forma
//        g2d.setColor(this.cor);
//        g2d.fillRect(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);


        g2d.setColor(Color.BLACK);
        g2d.drawRect(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);

        listaPolygonSuperior.forEach((polygon) -> {
            g2d.draw(polygon);
            g2d.fill(polygon);
        });
        listaPolygonInferior.forEach((polygon) -> {
            g2d.draw(polygon);
            g2d.fill(polygon);
        });
        listaPolygonDireita.forEach((polygon) -> {
            g2d.draw(polygon);
            g2d.fill(polygon);
        });
        listaPolygonEsquerda.forEach((polygon) -> {
            g2d.draw(polygon);
            g2d.fill(polygon);
        });
        g2d.drawRect(xPointsInterno[0], yPointsInterno[0], xPointsInterno[1], yPointsInterno[1]);
        g2d.fillRect(xPointsInterno[0], yPointsInterno[0], xPointsInterno[1], yPointsInterno[1]);
//        g2d.setStroke(new BasicStroke(1f));
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
        xPointsInterno = new int [2];
        yPointsInterno = new int [2];

        points[0] = getGlyphBounds().width;
        points[1] = getGlyphBounds().height;

        tornarGlyphQuadrado(points);
        montarQuadradoSobreposicao(points);

        int slices = 6;
        int sliceWidth = xPoints[1] / slices;

        int trianguloLado = (int) (xPoints[1] * 0.17);

        for (int i = sliceWidth; i < (6 * sliceWidth) - sliceWidth; i += sliceWidth) {
            listaPolygonSuperior.add(montaTrianguloSuperior(new Polygon(), i, sliceWidth, trianguloLado));
            listaPolygonInferior.add(montaTrianguloInferior(new Polygon(), i, sliceWidth, trianguloLado));
            listaPolygonDireita.add(montaTrianguloDireita(new Polygon(), i, sliceWidth, trianguloLado));
            listaPolygonEsquerda.add(montaTrianguloEsquerda(new Polygon(), i, sliceWidth, trianguloLado));
        }
        xPointsInterno[0] = listaPolygonSuperior.get(0).xpoints[0];
        yPointsInterno[0] = listaPolygonSuperior.get(0).ypoints[0];
        xPointsInterno[1] = listaPolygonInferior.get(3).getBounds().width + (trianguloLado * 3);
        yPointsInterno[1] = listaPolygonInferior.get(3).getBounds().height + (trianguloLado * 3);
    }
    
    private Polygon montaTrianguloEsquerda(Polygon p, int i, int sliceWidth, int trianguloLado) {
        //left
        p.addPoint((xPoints[0] + trianguloLado), yPoints[0] + (trianguloLado * 2) + (i - sliceWidth));
        //center
        p.addPoint(xPoints[0], yPoints[0] + trianguloLado + Math.round(trianguloLado / 2) + (i - sliceWidth));
        //right
        p.addPoint((xPoints[0] + trianguloLado), yPoints[0] + trianguloLado + (i - sliceWidth));
        return p;
    }
    
    private Polygon montaTrianguloDireita(Polygon p, int i, int sliceWidth, int trianguloLado) {
        //left
        p.addPoint((xPoints[0]), yPoints[0] + (i - sliceWidth));
        //center
        p.addPoint((xPoints[0] + trianguloLado), yPoints[0] + trianguloLado - Math.round(trianguloLado / 2) + (i - sliceWidth));
        //right
        p.addPoint((xPoints[0]), yPoints[0] + trianguloLado + (i - sliceWidth));
        p.translate(sliceWidth * 5, sliceWidth);
        return p;
    }

    private Polygon montaTrianguloInferior(Polygon p, int i, int sliceWidth, int trianguloLado) {
        //left
        p.addPoint((i - sliceWidth) + (xPoints[0] + trianguloLado), yPoints[0]);
        //center
        p.addPoint((i - sliceWidth) + (Math.round(trianguloLado / 2) + xPoints[0] + trianguloLado), trianguloLado + yPoints[0]);
        //right
        p.addPoint((i - sliceWidth) + (xPoints[0] + (trianguloLado * 2)), yPoints[0]);
        p.translate(0, sliceWidth * 5);
        return p;
    }

    private Polygon montaTrianguloSuperior(Polygon p, int i, int sliceWidth, int trianguloLado) {
        //left
        p.addPoint((i - sliceWidth) + (xPoints[0] + trianguloLado), yPoints[0] + trianguloLado);
        //center
        p.addPoint((i - sliceWidth) + (Math.round(trianguloLado / 2) + xPoints[0] + trianguloLado), yPoints[0]);
        //right
        p.addPoint((i - sliceWidth) + (xPoints[0] + (trianguloLado * 2)), yPoints[0] + trianguloLado);
        return p;
    }

    public void montarQuadradoSobreposicao(int[] points) {
        int widthSobreposicao = (int) Math.round(points[0] * percentSobreposicao);
        int heightSobreposicao = (int) Math.round(points[1] * percentSobreposicao);

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
        g2d.setColor(this.cor);
//        g2d.drawRect(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
    }

    @Override
    public Serrinhado clone() throws CloneNotSupportedException {
        try {
            // call clone in Object.
            return (Serrinhado) super.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("Cloning not allowed.");
            return this;
        }
    }

    @Override
    public String toString() {
        super.toString();
        return Serrinhado.class.getSimpleName();
    }

    public void setColor(Color cor) {
        this.cor = cor;
    }

}


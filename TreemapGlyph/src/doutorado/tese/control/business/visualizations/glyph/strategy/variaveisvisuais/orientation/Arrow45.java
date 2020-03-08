package doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.orientation;

import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.DrawBehavior;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * O objeto Circulo com cor ocupa 65% do item do treemap
 *
 * @author Anderson Soares
 */
public class Arrow45 implements DrawBehavior {

    private int[] xPoints;
    private int[] yPoints;
    private final Color cor;
    private Rectangle bounds;
    private Path2D path;
    private List<Polygon> triangulos;
    private List<Line2D.Double> retas;

    public Arrow45() {
        cor = Color.BLACK;
    }

    @Override
    public void paint(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.WHITE);
        g2d.fillRect(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
        g2d.setColor(cor);
        g2d.fill(path);
    }

    @Override
    public void tornarGlyphQuadrado(int[] point) {
        if (point[0] > point[1]) {
            point[0] = point[1];
        } else if (point[0] < point[1]) {
            point[1] = point[0];
        }
    }

    private void montarSetas45() {
        int[] points = new int[2];

        points[0] = getBounds().width;
        points[1] = getBounds().height;

        tornarGlyphQuadrado(points);
        montarQuadradoSobreposicao(points);

        int x1Metade = xPoints[0] + (xPoints[1] / 2);
        int y1Metade = yPoints[0];
        int x2Metade = xPoints[0] + (xPoints[1] / 2);
        int y2Metade = yPoints[0] + (yPoints[1] / 2);

        int slices = 6;
        int slicewidth = xPoints[1] / slices;
        int scalaLadoTriangulo = (int) (yPoints[1] * 0.08);

        Point a = new Point(x1Metade, y1Metade);
        Point centroQuadrado = new Point(x2Metade, y2Metade);

        double angulo = 45d;

        Point c1 = rotacionar(angulo, centroQuadrado, new Point(a.x, a.y));
        Point dir = rotacionar(angulo, centroQuadrado, new Point(a.x + scalaLadoTriangulo, a.y + (scalaLadoTriangulo * 2)));
        Point esq = rotacionar(angulo, centroQuadrado, new Point(a.x - scalaLadoTriangulo, a.y + (scalaLadoTriangulo * 2)));

        triangulos = new ArrayList<>();
        retas = new ArrayList<>();

        for (int i = 0; i < slices - 3; i++) {
            int intervaloSetas = (i * slicewidth * 2);
            Polygon triangulo = null;
            Point c = new Point(c1.x - intervaloSetas, c1.y);
            Point d = new Point(dir.x - intervaloSetas, dir.y);
            Point e = new Point(esq.x - intervaloSetas, esq.y);
            triangulo = montarTrianguloCentro(scalaLadoTriangulo, c, d, e);

            triangulos.add(triangulo);
        }
        for (int i = 1; i <= slices - 4; i++) {
            int intervaloSetas = (i * slicewidth * 2);
            Polygon triangulo = null;
            triangulo = montarTrianguloCentro(scalaLadoTriangulo, c1, dir, esq);
            triangulo.translate(0, intervaloSetas);

            triangulos.add(triangulo);
        }

        Point pCima = rotacionar(angulo, centroQuadrado, a);
        Point pBaixo = rotacionar(angulo * 5, centroQuadrado, a);

        for (int i = 0; i < slices - 3; i++) {
            int intervaloSetas = (i * slicewidth * 2);
            Point c = new Point(pCima.x - intervaloSetas, pCima.y);
            Point b = new Point(pBaixo.x - intervaloSetas, pBaixo.y);
            Line2D.Double reta = montarRetaCentro(scalaLadoTriangulo, c, b);

            reta.x2 += intervaloSetas;
            reta.y2 -= intervaloSetas;
            
            retas.add(reta);
        }
        for (int i = 1; i <= slices - 4; i++) {
            int intervaloSetas = (i * slicewidth * 2);
            Point c = new Point(pCima.x, pCima.y + intervaloSetas);
            Point b = new Point(pBaixo.x, pBaixo.y + intervaloSetas);
            Line2D.Double reta = montarRetaCentro(scalaLadoTriangulo, c, b);

            reta.x2 += intervaloSetas;
            reta.y2 -= intervaloSetas;

            retas.add(reta);
        }

        drawSetas();
    }

    public Point rotacionar(double angle, Point2D pivot, Point2D pointInicio) {
        Point result = new Point();
        AffineTransform rotation = new AffineTransform();
        double angleInRadians = Math.toRadians(angle);
        rotation.rotate(angleInRadians, pivot.getX(), pivot.getY());
        rotation.transform(pointInicio, result);
        return result;
    }

    private Polygon montarTrianguloCentro(int scalaLadoTriangulo, Point c1, Point dir, Point esq) {
        Polygon poly = new Polygon();
        //center
        poly.addPoint(c1.x + (2 * scalaLadoTriangulo), c1.y - (2 * scalaLadoTriangulo));
        //right
        poly.addPoint(dir.x + (2 * scalaLadoTriangulo), dir.y - (2 * scalaLadoTriangulo));
        //left
        poly.addPoint(esq.x + (2 * scalaLadoTriangulo), esq.y - (2 * scalaLadoTriangulo));

        return poly;
    }

    private Line2D.Double montarRetaCentro(int scalaLadoTriangulo, Point pCima, Point pBaixo) {
        Line2D.Double reta = new Line2D.Double(
                pCima.x + (2 * scalaLadoTriangulo),
                pCima.y - (2 * scalaLadoTriangulo),
                pBaixo.x - (2 * scalaLadoTriangulo),
                pBaixo.y + (2 * scalaLadoTriangulo));
        return reta;
    }

    private void drawSetas() {
        path = new Path2D.Double();
        triangulos.forEach((triangulo) -> {
            path.append(triangulo, false);
        });
        retas.forEach((reta) -> {
            path.append(reta, false);
        });
    }

    public void montarQuadradoSobreposicao(int[] points) {
        int widthSobreposicao = (int) Math.round(points[0] * PERCENT_SOBREPOSICAO);
        int heightSobreposicao = (int) Math.round(points[1] * PERCENT_SOBREPOSICAO);

        xPoints = new int[2];
        yPoints = new int[2];

        xPoints[0] = getBounds().x + Math.round(getBounds().width / 2) - Math.round(widthSobreposicao / 2);
        yPoints[0] = getBounds().y + Math.round(getBounds().height / 2) - Math.round(heightSobreposicao / 2);

        xPoints[1] = widthSobreposicao;
        yPoints[1] = heightSobreposicao;
    }

    public Rectangle getBounds() {
        return this.bounds;
    }

    @Override
    public void setGlyphBounds(Rectangle bounds) {
        this.bounds = bounds;
        montarSetas45();
    }

    @Override
    public int getArea() {
        return xPoints[1] * yPoints[1];
    }

    @Override
    public Shape getClipShape() {
        return path;
    }

    @Override
    public void drawForeground(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        g2d.draw(path);
//        g2d.setColor(Color.WHITE);
//        g2d.setStroke(new BasicStroke(1.5f));
//        g2d.draw(p);
//        g2d.fill(p);
//        g2d.setStroke(new BasicStroke(1f));
    }

    @Override
    public Arrow45 clone() throws CloneNotSupportedException {
        try {
            // call clone in Object.
            return (Arrow45) super.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println("Cloning not allowed.");
            return this;
        }
    }

    @Override
    public String toString() {
        super.toString();
        return Arrow45.class.getSimpleName();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.visualizacao.glyph;

import doutorado.tese.io.ManipuladorArquivo;
import doutorado.tese.util.Coluna;
import doutorado.tese.util.Constantes;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 *
 * @author Anderson
 */
public class StarGlyph extends JLabel{

    private Rectangle rect;
    private int pontoMedioX, pontoMedioY;
    private int quantVar;
    private double r;
    private double anguloAlfa = 0;
    private double anguloAcc = 0;
    private List<String> variaveisEscolhidasStarGlyph;
    private ManipuladorArquivo manipulador;
    private int numLinha = 2;

    public StarGlyph(Rectangle r, List<String> variaveisEscolhidasStarGlyph) {
        this.rect = r;
        this.setBounds(r.x + 2, r.y + 2, r.width - 2, r.height - 2);
//        this.setS
        this.setBorder(BorderFactory.createLineBorder(Color.red));
        this.variaveisEscolhidasStarGlyph = variaveisEscolhidasStarGlyph;
    }

    /**
     * Calcula quantos porcentos o dado atual da linha corrente equivale ao
     * valor max da coluna.
     *
     * @param dadoColuna
     * @param maxValCol
     * @return Porcentagem equivalente ao valor max da coluna.
     */
    private double calcularPorcentagemDado(double dadoColuna, double maxValCol) {
        return (dadoColuna * 100) / maxValCol;
    }

    private double calcularPorcentagemParaR(double porcentagemDado, double maiorRaio) {
        return (porcentagemDado * maiorRaio) / 100;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        if (getQuantVar() != 0) {
            anguloAcc = 360 / getQuantVar();
            anguloAlfa = 0;
            int maiorRaio = encontrarMaiorRaio();
//        System.out.println("Desenhar eixos polares deste star glyph");
            for (int i = 0; i < getQuantVar(); i++) {
                String nomeColuna = variaveisEscolhidasStarGlyph.get(i);
                Coluna coluna = getManipulador().getColuna(nomeColuna);
                String[] dadosColuna = coluna.getDadosColuna();
                double dado = Double.parseDouble(dadosColuna[numLinha]);
                double dadoMaxVal = coluna.getMapaMaiorMenor().get(coluna.getName())[0];//0 - maxValue; 1 - minValue
                double porcentagemDado = calcularPorcentagemDado(dado, dadoMaxVal);
                r = calcularPorcentagemParaR(porcentagemDado, maiorRaio);
                g2d.setColor(Color.decode(Constantes.getCor()[i]));
                desenharEixoPolar(g2d);
                anguloAlfa += anguloAcc;
            }
            numLinha++;
        }
        g2d.dispose();
    }

//    public void encontrarPontoCentro(int halfWidth, int width, int halfHeight, int height) {
//        pontoMedioX = (int) ((halfWidth + width) / 2);
//        pontoMedioY = (int) ((halfHeight + height) / 2);
//    }

    private int encontrarMaiorRaio() {
        Point center = getCenter();
        int bordaW = (int) Math.round(rect.width) - 2;
        int bordaH = (int) Math.round(rect.height) - 2;

        int raio = center.x - bordaW;
        int maiorRaio = raio;
        raio = center.y - bordaH;

        if (raio >= maiorRaio) {
            return raio;
        } else {
            return maiorRaio;
        }
    }

    public double[] parsePolar2Cartesiana(double anguloAlfa, double r) {
        double pontoX = r * Math.cos(Math.toRadians(anguloAlfa));
        double pontoY = r * Math.sin(Math.toRadians(anguloAlfa));
//        System.out.println("X: " + pontoX + " Y: " + pontoY);
        return new double[]{pontoX, pontoY};
    }

    public void parseCartesiana2Polar(double x, double y) {
        r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        anguloAlfa = Math.tan(x / y);

        if (x < 0 && y < 0) {
            anguloAlfa += 180;
        } else if (x > 0 && y < 0) {
            anguloAlfa -= 360;
        } else if (x < 0 && y > 0) {
            anguloAlfa -= 180;
        }
    }

    public int getQuantVar() {
        return quantVar;
    }

    public void setQuantVar(int quantVar) {
        this.quantVar = quantVar;
    }

    private void desenharEixoPolar(Graphics g) {
        Point center = getCenter();
        double[] pontos = parsePolar2Cartesiana(anguloAlfa, r);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.drawLine(definePolo(center).x, definePolo(center).y,
                (int) Math.round(pontos[0] + center.x + rect.x), (int) Math.round(pontos[1] + center.y + rect.y));
    }

    public Point definePolo(Point ponto) {
        int xPolo = (int) Math.round(ponto.x + rect.x);
        int yPolo = (int) Math.round(ponto.y + rect.y);
        return new Point(xPolo, yPolo);
    }

    private Point getCenter() {
        int width = (int) Math.round(rect.width) - 1;
        int height = (int) Math.round(rect.height) - 1;

        int halfWidth = width / 2;
        int halfHeight = height / 2;
        return new Point(halfWidth, halfHeight);
    }

    /**
     * @return the variaveisEscolhidasStarGlyph
     */
    public List<String> getVariaveisEscolhidasStarGlyph() {
        return variaveisEscolhidasStarGlyph;
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

}

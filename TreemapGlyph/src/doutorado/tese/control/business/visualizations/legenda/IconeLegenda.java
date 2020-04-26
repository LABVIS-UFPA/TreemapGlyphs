/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.control.business.visualizations.legenda;

import doutorado.tese.util.Constantes;
import doutorado.tese.control.business.visualizations.glyph.Glyph;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.orientation.Orientation;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.text.Text;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.position.Position;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.shapes.GeometricShape;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.texture.Texture;
import doutorado.tese.control.business.visualizations.glyph.factorys.variaveisvisuais.GeometryFactory;
import doutorado.tese.control.business.visualizations.glyph.factorys.variaveisvisuais.GeometryFactory.FORMAS;
import doutorado.tese.control.business.visualizations.glyph.decorator.continuous.Bar;
import doutorado.tese.control.business.visualizations.glyph.decorator.continuous.ProfileGlyph;
import doutorado.tese.control.business.visualizations.glyph.factorys.variaveisvisuais.OrientationFactory;
import doutorado.tese.control.business.visualizations.glyph.factorys.variaveisvisuais.OrientationFactory.ARROW;
import doutorado.tese.control.business.visualizations.glyph.factorys.variaveisvisuais.TexturesFactory;
import doutorado.tese.control.business.visualizations.glyph.factorys.variaveisvisuais.TexturesFactory.TEXTURE;
import doutorado.tese.dao.ManipuladorArquivo;
import doutorado.tese.model.Coluna;
import doutorado.tese.model.TreeMapItem;
import doutorado.tese.util.ColorInterpolator;
import static doutorado.tese.util.Constantes.VAR_VISUAIS_CATEGORICAS.TEXTURE;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.swing.Icon;

/**
 *
 * @author Anderson Soares
 */
public class IconeLegenda implements Icon {

    private int width = 32;
    private int height = 32;
    private Constantes.VAR_VISUAIS_CATEGORICAS dimensaoCategorical;
    private String valor = null;
    private double maxValorContIcon;
    private double minValorContIcon;
    private Glyph glyph;

    private final BasicStroke stroke = new BasicStroke(4);
    private FORMAS.GLYPH_FORMAS valorForma = null;
    private List<String> atributosEscolhidosGlyphContinuo = null;
    private Constantes.POSICOES posicao;
    private ARROW.GLYPH_ORIENTACAO orientacao;
    private TEXTURE.GLYPH_TEXTURAS valorTextura;
    private boolean visibilityTest = false;
    private HashMap<String, Double> mapaDetalhesItem;
    private TreeMapItem item;

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();

        Rectangle bounds = new Rectangle(x, y, width, height);

        if (dimensaoCategorical != null) {
            switch (dimensaoCategorical) {
                case TEXTURE:
                    montarIconeGlyphTextura(g2d, valorTextura, bounds);
                    break;
                case COLOR_HUE:
                    montarIconeGlyphCor(g2d, x, y, height, valor, bounds);
                    break;
                case SHAPE:
                    montarIconeGlyphForma(g2d, valorForma, bounds);
                    break;
                case TEXT:
                    montarIconeGlyphText(g2d, valor, bounds);
                    break;
                case POSITION:
                    montarIconeGlyphPosition(g2d, posicao, bounds);
                    break;
                case ORIENTATION:
                    montarIconeGlyphOrientacao(g2d, orientacao, bounds);
                    break;
            }
        } else {
            if (getAtributosEscolhidosGlyphContinuo() != null && !getAtributosEscolhidosGlyphContinuo().isEmpty()) {//legenda profile glyph
                if (!isVisibilityTest()) {
                    montarIconeProfileGlyph(g2d, bounds);
                }else{
                    montarIconeProfileGlyphAleatorio(g2d, bounds);
                }
            } else if (Constantes.SHOW_GLYPH_ON_DETAILS && getGlyph() != null) {
                getGlyph().setBounds(bounds);
                getGlyph().paint(g2d);
            } else if (Constantes.LEGENDA_COR_TREEMAP) {//legenda da cor do treemap
                montarIconeCorTreemap(g2d, x, y, bounds);
            } else if (isVisibilityTest()) {
//                montarIconeProfileGlyph(g2d, item);
//                montarIconeProfileGlyph(g2d, bounds, mapaDetalhesItem);
                montarIconeProfileGlyph(g2d, glyph, bounds);
            }
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
            GeometricShape iconColorTreemap = new GeometricShape();
            iconColorTreemap.setDrawBehavior(GeometryFactory.create(FORMAS.GLYPH_FORMAS.QUADRADO));
            iconColorTreemap.setPectSobreposicao(0.65f);
            iconColorTreemap.setOverlappingActivated(true);
            iconColorTreemap.setCorLegenda(Color.decode(valor));
            iconColorTreemap.setLegenda(true);
            iconColorTreemap.setBounds(bounds);
            iconColorTreemap.paint(g2d);
        }
    }

    private void montarIconeGlyphTextura(Graphics2D g2d, TEXTURE.GLYPH_TEXTURAS valor, Rectangle bounds) {
//    private void montarIconeGlyphTextura(Graphics2D g2d, String valor, Rectangle bounds) {
//        Texture_old textura = new Texture_old(Color.GRAY, Color.WHITE);
//        textura.setNomeTextura(valor);
//        textura.setPectSobreposicao(0.84f);
//        textura.setOverlappingActivated(true);
//        textura.setBounds(bounds);
//        textura.paint(g2d);
        Texture textura = new Texture();
        textura.setDrawBehavior(TexturesFactory.create(valor));
        textura.setPectSobreposicao(0.99f);
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
            GeometricShape shapeColor = new GeometricShape();
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
        GeometricShape shapeIcon = new GeometricShape();
        shapeIcon.setDrawBehavior(GeometryFactory.create(valorForma));
        shapeIcon.setPectSobreposicao(0.65f);
        shapeIcon.setOverlappingActivated(true);
        shapeIcon.setBounds(bounds);
        shapeIcon.paint(g2d);
    }

    private void montarIconeGlyphOrientacao(Graphics2D g2d, ARROW.GLYPH_ORIENTACAO valor, Rectangle bounds) {
        Orientation iconOrientacao = new Orientation();
        iconOrientacao.setDrawBehavior(OrientationFactory.create(valor));
        iconOrientacao.setPectSobreposicao(0.65f);
        iconOrientacao.setOverlappingActivated(true);
        iconOrientacao.setBounds(bounds);
        iconOrientacao.paint(g2d);
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
        ProfileGlyph profile = new ProfileGlyph(getAtributosEscolhidosGlyphContinuo());
        profile.setQuantVar(getAtributosEscolhidosGlyphContinuo().size());
        profile.setPectSobreposicao(0.85f);
        profile.setOverlappingActivated(true);
        double dado = 20;
        for (int i = 0; i < getAtributosEscolhidosGlyphContinuo().size(); i++) {
            dado -= 2;
            double dadoMaxVal = 20;//0 - maxValue; 1 - minValue
            double dadoMinVal = -10;
            profile.getBarras()[i] = new Bar(dado, dadoMaxVal, dadoMinVal);
        }
        Rectangle r = new Rectangle(10, 10, bounds.width * 3, bounds.height * 4);

        profile.setBounds(r);
        profile.paint(g2d);
    }

    private void montarIconeProfileGlyph(Graphics2D g2d, Glyph glyph, Rectangle newBounds) {
        ProfileGlyph profile = (ProfileGlyph) glyph;
        List<String> lista = new ArrayList<>();
        lista.add("a1");
        lista.add("a2");
        lista.add("a3");
        ProfileGlyph newProfile = new ProfileGlyph(lista);
        newProfile.setQuantVar(lista.size());
        newProfile.setPectSobreposicao(1f);
        newProfile.setOverlappingActivated(true);
        for (int i = 0; i < profile.getBarras().length; i++) {
            Bar barra = profile.getBarras()[i];
            double dado = barra.getDado();
            double dadoMaxVal = barra.getDadoMaxVal();//0 - maxValue; 1 - minValue
            double dadoMinVal = barra.getDadoMinVal();
            newProfile.getBarras()[i] = new Bar(dado, dadoMaxVal, dadoMinVal);
        }
        Rectangle r = new Rectangle(newBounds.x * 11, newBounds.y, newBounds.width, newBounds.height);
        newProfile.setBounds(r);
        newProfile.paint(g2d);
    }
    
    private void montarIconeProfileGlyphAleatorio(Graphics2D g2d, Rectangle bounds) {
        ProfileGlyph profile = new ProfileGlyph(getAtributosEscolhidosGlyphContinuo());
        profile.setQuantVar(getAtributosEscolhidosGlyphContinuo().size());
        profile.setPectSobreposicao(1f);
        profile.setOverlappingActivated(true);

        for (int i = 0; i < getAtributosEscolhidosGlyphContinuo().size(); i++) {
            double dado = mapaDetalhesItem.get("a"+i);
            double dadoMaxVal = 100;//0 - maxValue; 1 - minValue
            double dadoMinVal = -100;
            profile.getBarras()[i] = new Bar(dado, dadoMaxVal, dadoMinVal);
        }
        Rectangle r = new Rectangle(bounds.x * 11, bounds.y, bounds.width, bounds.height);
        profile.setBounds(r);
        profile.paint(g2d);
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

    public void setDimensaoCategorical(Constantes.VAR_VISUAIS_CATEGORICAS dimensaoCategorical) {
        this.dimensaoCategorical = dimensaoCategorical;
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

    void setValorIcon(ARROW.GLYPH_ORIENTACAO orientacao) {
        this.orientacao = orientacao;
    }

    void setValorIcon(TEXTURE.GLYPH_TEXTURAS textura) {
        this.valorTextura = textura;
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

    /**
     * @return the visibilityTest
     */
    public boolean isVisibilityTest() {
        return visibilityTest;
    }

    /**
     * @param visibilityTest the visibilityTest to set
     */
    public void setVisibilityTest(boolean visibilityTest) {
        this.visibilityTest = visibilityTest;
    }

    /**
     * @return the mapaDetalhesItem
     */
    public HashMap<String, Double> getMapaDetalhesItem() {
        return mapaDetalhesItem;
    }

    /**
     * @param mapaDetalhesItem the mapaDetalhesItem to set
     */
    public void setMapaDetalhesItem(HashMap<String, Double> mapaDetalhesItem) {
        this.mapaDetalhesItem = mapaDetalhesItem;
    }

    public void setItem(TreeMapItem item) {
        this.item = item;
    }

}

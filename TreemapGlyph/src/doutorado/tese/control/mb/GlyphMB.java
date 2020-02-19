/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.control.mb;

import doutorado.tese.control.business.machinelearning.tree.DecisionTreeClassifier;
import doutorado.tese.dao.ManipuladorArquivo;
import doutorado.tese.model.Coluna;
import doutorado.tese.util.Constantes;
import doutorado.tese.util.Metadados;
import doutorado.tese.control.business.visualizations.glyph.decorator.continuous.Bar;
import doutorado.tese.control.business.visualizations.glyph.decorator.continuous.ProfileGlyph;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.color.Cor;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.text.Text;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.shapes.FormaGeometrica;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.texture.Textura;
import doutorado.tese.model.TreeMapItem;
import doutorado.tese.util.ColorInterpolator;
import doutorado.tese.control.business.visualizations.glyph.Glyph;
import doutorado.tese.control.business.visualizations.glyph.decorator.continuous.AngChart;
import doutorado.tese.control.business.visualizations.glyph.decorator.continuous.StarGlyph;
import doutorado.tese.control.business.visualizations.glyph.decorator.continuous.EixoPolarStarGlyph;
import doutorado.tese.control.business.visualizations.glyph.decorator.continuous.PieChartGlyph;
import doutorado.tese.control.business.visualizations.glyph.decorator.continuous.Slice;
import doutorado.tese.control.business.visualizations.glyph.factorys.variaveisvisuais.GeometryFactory;
import doutorado.tese.control.business.visualizations.glyph.factorys.variaveisvisuais.GeometryFactory.FORMAS;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.bouthier.treemapAWT.TMNodeEncapsulator;
import net.bouthier.treemapAWT.TMNodeModel;
import net.bouthier.treemapAWT.TMNodeModelComposite;
import net.bouthier.treemapAWT.TMNodeModelRoot;

/**
 *
 * @author Anderson Soares
 */
public final class GlyphMB {

    private List<Object> atributosEscolhidos;
    private HashMap<String, List<String>> colunaDadosDist;
    private TMNodeModelRoot rootNodeZoom;
    private HashMap<String, Integer> configs;
    private String[] variaveisVisuaisEscolhidas;
    private Rectangle bounds;
    private boolean overlappingActivated;
    private List<String> atributosEscolhidosGlyphContinuo;
    private String glyphContinuoEscolhido;

    public GlyphMB() {
        this.configs = new HashMap<>();
    }

    public GlyphMB(ManipuladorArquivo manipulador, List<Object> atributosEscolhidos, Rectangle bounds) {
        this.atributosEscolhidos = atributosEscolhidos;
        colunaDadosDist = new HashMap<>();
        analisarAtributosEscolhidos();
        this.configs = new HashMap<>();
        this.bounds = bounds;
    }

    public void analisarAtributosEscolhidos() {
        for (int i = 0; i < atributosEscolhidos.size(); i++) {
            if (!atributosEscolhidos.get(i).equals("---")) {
                Coluna c = ManipuladorArquivo.getColuna(atributosEscolhidos.get(i).toString());
                List<String> dadosDistintos = c.getDadosDistintos();
                colunaDadosDist.put(c.getName(), dadosDistintos);
            }
        }
    }

    public void prepare2Draw() {
        if (getRootNodeZoom() != null) {
            prepareGlyphsInTreeMapItems(getRootNodeZoom().getRoot());
        }
    }

    private void paintAnalyser(Graphics g, TMNodeModel nodo) {
        Graphics2D g2d = (Graphics2D) g;
        if (nodo instanceof TMNodeModelComposite) {//se for TreeMap Level
            TMNodeModelComposite pai = (TMNodeModelComposite) nodo;
            for (TMNodeModel n : pai.getChildrenList()) {
                paintAnalyser(g2d, n);
            }
        } else {//se for um treemap Item ele vai desenhar os glyphs
            TMNodeEncapsulator nodeEncapsulator = (TMNodeEncapsulator) nodo.getNode();
            TreeMapItem item = (TreeMapItem) nodeEncapsulator.getNode();
            ArrayList<Glyph> list = new ArrayList<>();
            item.getGlyph().paint(g2d);
            item.getGlyph().getChildren(list);
            g2d.setClip(0, 0, bounds.width, bounds.height);
        }
    }

    public void paint(Graphics g) {
        if (getRootNodeZoom() != null) {
            paintAnalyser(g, getRootNodeZoom().getRoot());
        }
    }

    public void prepareGlyphsInTreeMapItems(TMNodeModel nodo) {
        if (nodo instanceof TMNodeModelComposite) {//se for TreeMap Level
            TMNodeModelComposite pai = (TMNodeModelComposite) nodo;
            for (TMNodeModel n : pai.getChildrenList()) {
                prepareGlyphsInTreeMapItems(n);
            }
        } else {//se for um treemap Item ele vai desenhar os glyphs
            TMNodeEncapsulator nodeEncapsulator = (TMNodeEncapsulator) nodo.getNode();
            TreeMapItem treemapItem = (TreeMapItem) nodeEncapsulator.getNode();
//            prepareDimension2DrawGlyph(treemapItem);
            configLayers(treemapItem);
//            imprimirTamanhoGlyphs(treemapItem);
        }
    }

    private void imprimirTamanhoGlyphs(TreeMapItem treemapItem) {
        System.out.println("item name: " + treemapItem.getLabel()
                + " - Glyph pai: " + treemapItem.getGlyph().toString() + " - " + treemapItem.getGlyph().getBounds()
                + " Filho: " + treemapItem.getGlyph().getChild().toString() + " - " + treemapItem.getGlyph().getChild().getBounds());
    }

    private double[] getFeatures(TreeMapItem item, double[] features) {
//        limparGlyphsTreemapItem(item);
        features[Constantes.FEATURE_LARGURA] = item.getBounds().width;
        features[Constantes.FEATURE_ALTURA] = item.getBounds().height;
        features[Constantes.PRESENCA_COR_TREEMAP] = item.getColor().equals(Constantes.ALICE_BLUE) ? Constantes.AUSENTE : Constantes.PRESENTE;
        features[Constantes.FEATURE_AREA] = features[Constantes.FEATURE_LARGURA] * features[Constantes.FEATURE_ALTURA];

        double aspect = features[Constantes.FEATURE_ALTURA] > features[Constantes.FEATURE_LARGURA]
                ? features[Constantes.FEATURE_LARGURA] / features[Constantes.FEATURE_ALTURA]
                : features[Constantes.FEATURE_ALTURA] / features[Constantes.FEATURE_LARGURA];

        features[Constantes.FEATURE_ASPECT] = aspect;

        List<Glyph> glyphFamily = item.getGlyphFamily(item.getGlyph(), new ArrayList<>());
        glyphFamily.forEach((glyph) -> {
            if (glyph instanceof Textura) {
                features[Constantes.AREA_TEXTURA] = glyph.getArea();//aqui a area sera calculada no getArea()
                features[Constantes.PRESENCA_TEXTURA] = Constantes.PRESENTE;
            } else if (glyph instanceof Cor) {
                features[Constantes.AREA_COR] = glyph.getArea();
                features[Constantes.PRESENCA_COR] = Constantes.PRESENTE;
            } else if (glyph instanceof FormaGeometrica) {
                features[Constantes.AREA_SHAPE] = glyph.getArea();
                features[Constantes.PRESENCA_FORMA] = Constantes.PRESENTE;
            } else if (glyph instanceof Text) {
                features[Constantes.AREA_LETRA] = glyph.getArea();
                features[Constantes.PRESENCA_LETRA] = Constantes.PRESENTE;
            }
//            else if (glyph instanceof Numeral) {
//                features[Constantes.AREA_NUMERO] = ((Numeral) glyph).getArea();
//                features[Constantes.PRESENCA_NUMERO] = Constantes.PRESENTE;
//            }
        });
        int[] predictions = DecisionTreeClassifier.predict(features);
        item.getWhat2Draw()[Constantes.PRESENCA_TEXTURA] = predictions[0];
        item.getWhat2Draw()[Constantes.PRESENCA_COR] = predictions[1];
        item.getWhat2Draw()[Constantes.PRESENCA_FORMA] = predictions[2];
        item.getWhat2Draw()[Constantes.PRESENCA_LETRA] = predictions[3];
//        item.getWhat2Draw()[Constantes.PRESENCA_NUMERO] = predictions[4];
//        item.getWhat2Draw()[Constantes.PRESENCA_STAR] = DecisionTreeClassifier.predict(features)[5];
        return features;
    }

    /**
     * Recebe um TreeMapItem, mata todos os seus filhos antigos, e adiciona seus
     * novos filhos de acordo com a hierarquia de Layers passada atraves da
     * funcao variaveisVisuaisEscolhidas(). Por fim, é definido o tamanho de
     * cada item no layout treemap.
     *
     * @param item
     * @return item com as layers configuradas
     */
    public TreeMapItem configLayers(TreeMapItem item) {
        Glyph father = item.getGlyph();
        father.killAllChild();//e feito um kill para garantir que nao ha filhos
        String glyphContinuo = getGlyphContinuoEscolhido();

        for (int i = 0; i < getVariaveisVisuaisEscolhidas().length; i++) {
            String varVisual = getVariaveisVisuaisEscolhidas()[i];
            int dimensao = mapearVarVisual2Dimensao(varVisual);
            Glyph child = setLayerInGlyph(varVisual, item, dimensao);
            father.appendChild(child);
            if (i == getVariaveisVisuaisEscolhidas().length - 1) {//se ja estiver na ultima camada
                if (Constantes.CONTINUOUS_GLYPH_ACTIVATED) {
                    Glyph childContinuousGlyph = setLayerInGlyph(glyphContinuo, item, -1);
                    father.appendChild(childContinuousGlyph);
                }
            }
        }
        if (getVariaveisVisuaisEscolhidas().length == 0) {
            Glyph childStarGlyph = setLayerInGlyph(glyphContinuo, item, -1);
            father.appendChild(childStarGlyph);
        }
        if (father.getBounds() != null) {
            father.setBounds(father.getBounds());
        }
        if (Constantes.DECISION_TREE_ACTIVATED) {
            double[] features = new double[15];
            getFeatures(item, features);
        }
        if (item.hasGlyphResposta(father)) {
            item.setPossuiGlyphResposta(true);
        }
        return item;
    }

    public Glyph setLayerInGlyph(String varVisual, TreeMapItem item, int dimensao) {
        Glyph glyph = null;
        String colunaEscolhida = null;
        Coluna col = null;
        List<String> dadosDistintos = null;

        if (dimensao != -1) {
            colunaEscolhida = atributosEscolhidos.get(dimensao).toString();
            col = ManipuladorArquivo.getColuna(colunaEscolhida);
            dadosDistintos = colunaDadosDist.get(colunaEscolhida);
        }
        if (varVisual != null) {
            switch (varVisual) {
                case "Texture":
                    glyph = prepareDimensaoTexturaDinamica(col, item, dadosDistintos);
                    break;
                case "Color":
                    glyph = prepareDimensaoCorDinamica(col, item, dadosDistintos);
                    break;
                case "Shape":
                    glyph = prepareDimensaoShapeDinamico(col, item, dadosDistintos);
                    break;
                case "Text"://case "Letter":
                    glyph = prepareDimensaoTextDinamico(col, item, dadosDistintos);
                    break;
                case "Number":
                    glyph = prepareDimensaoNumberDinamico(col, item, dadosDistintos);
                    break;
                case "Star":
                    glyph = configureStarGlyph(item);
                    break;
                case "Profile":
                    glyph = configureProfileGlyph(item);
                    break;
                case "Pie":
                    glyph = configureSliceGlyph(item);
                    break;
                case "Ang":
                    glyph = configureArcGlyph(item);
                    break;
                default:
                    System.out.println("error ");
                    break;
            }
        }
        return glyph;
    }

    private Glyph configureSliceGlyph(TreeMapItem item) {
        PieChartGlyph slice = new PieChartGlyph(getAtributosEscolhidosGlyphContinuo());
        slice.setQuantVar(getAtributosEscolhidosGlyphContinuo().size());
        slice.setPectSobreposicao(0.85f);
        slice.setOverlappingActivated(true);
        for (int i = 0; i < getAtributosEscolhidosGlyphContinuo().size(); i++) {
            String nomeColunaEscolhida = getAtributosEscolhidosGlyphContinuo().get(i);
            Coluna coluna = ManipuladorArquivo.getColuna(nomeColunaEscolhida);
            double dado = Double.parseDouble(item.getMapaDetalhesItem().get(coluna));
            double dadoMaxVal = coluna.getMapaMaiorMenor().get(coluna.getName())[0];//0 - maxValue; 1 - minValue
            slice.getSlices()[i] = new Slice(dado, dadoMaxVal);
        }
        return slice;
    }

    private Glyph configureArcGlyph(TreeMapItem item) {
        AngChart raio = new AngChart(getAtributosEscolhidosGlyphContinuo());
        raio.setQuantVar(getAtributosEscolhidosGlyphContinuo().size());
        raio.setPectSobreposicao(0.85f);
        raio.setOverlappingActivated(true);
        for (int i = 0; i < getAtributosEscolhidosGlyphContinuo().size(); i++) {
            String nomeColunaEscolhida = getAtributosEscolhidosGlyphContinuo().get(i);
            Coluna coluna = ManipuladorArquivo.getColuna(nomeColunaEscolhida);
            double dado = Double.parseDouble(item.getMapaDetalhesItem().get(coluna));
            double dadoMaxVal = coluna.getMapaMaiorMenor().get(coluna.getName())[0];//0 - maxValue; 1 - minValue
            raio.getSlices()[i] = new Slice(dado, dadoMaxVal);
        }
        return raio;
    }

    private Glyph configureProfileGlyph(TreeMapItem item) {
        ProfileGlyph profileGlyph = new ProfileGlyph(getAtributosEscolhidosGlyphContinuo());
        profileGlyph.setQuantVar(getAtributosEscolhidosGlyphContinuo().size());
        profileGlyph.setPectSobreposicao(0.75f);
        profileGlyph.setOverlappingActivated(true);
        for (int i = 0; i < getAtributosEscolhidosGlyphContinuo().size(); i++) {
            String nomeColunaEscolhida = getAtributosEscolhidosGlyphContinuo().get(i);
            Coluna coluna = ManipuladorArquivo.getColuna(nomeColunaEscolhida);
            double dado = Double.parseDouble(item.getMapaDetalhesItem().get(coluna));
            double dadoMaxVal = coluna.getMapaMaiorMenor().get(coluna.getName())[0];//0 - maxValue; 1 - minValue
            profileGlyph.getBarras()[i] = new Bar(dado, dadoMaxVal);
        }
        return profileGlyph;
    }

    private Glyph configureStarGlyph(TreeMapItem item) {
        StarGlyph starGlyph = new StarGlyph(getAtributosEscolhidosGlyphContinuo());
        starGlyph.setQuantVar(getAtributosEscolhidosGlyphContinuo().size());
        starGlyph.setPectSobreposicao(0.85f);
        starGlyph.setOverlappingActivated(true);
        for (int i = 0; i < getAtributosEscolhidosGlyphContinuo().size(); i++) {
            String nomeColunaEscolhida = getAtributosEscolhidosGlyphContinuo().get(i);
            Coluna coluna = ManipuladorArquivo.getColuna(nomeColunaEscolhida);
            double dado = Double.parseDouble(item.getMapaDetalhesItem().get(coluna));
            double dadoMaxVal = coluna.getMapaMaiorMenor().get(coluna.getName())[0];//0 - maxValue; 1 - minValue
            starGlyph.getEixosPolares()[i] = new EixoPolarStarGlyph(dado, dadoMaxVal);
        }
        return starGlyph;
    }

    public Glyph prepareDimensaoTexturaDinamica(Coluna col, TreeMapItem item, List<String> dadosDistintos) {
        for (int j = 0; j < Constantes.TIPO_TEXTURA.length; j++) {
            if (item.getMapaDetalhesItem().get(col).equalsIgnoreCase(dadosDistintos.get(j))) {
                Glyph textura = defineTexture(Constantes.TIPO_TEXTURA[j]);
                textura.setNodeTreemap(item);
                return textura;
            }
        }
        return null;
    }

    public Glyph prepareDimensaoCorDinamica(Coluna col, TreeMapItem item, List<String> dadosDistintos) {
        Glyph glyphCor = null;
        if (col.getDescription() == Metadados.Descricao.CONTINUOUS) {
            ColorInterpolator interpolator = new ColorInterpolator();
            //255,244,233 - branco
            //223,83,8    - laranja
            //129,40,4    - marrom
            interpolator.config(col.maiorMenorValues[0], col.maiorMenorValues[1], Color.yellow, Color.WHITE);
            Color cor = interpolator.interpolate(Double.parseDouble(item.getMapaDetalhesItem().get(col)));
            glyphCor = defineColor(cor);
            glyphCor.setNodeTreemap(item);
            return glyphCor;
        } else {
            for (int j = 0; j < Constantes.getCorGlyphs().length; j++) {
                if (item.getMapaDetalhesItem().get(col).equalsIgnoreCase(dadosDistintos.get(j))) {
                    Color cor = Color.decode(Constantes.getCorGlyphs()[j]);
                    glyphCor = defineColor(cor);
                    glyphCor.setNodeTreemap(item);
                    return glyphCor;
                }
            }
        }
        return glyphCor;
    }

    public Glyph prepareDimensaoShapeDinamico(Coluna col, TreeMapItem item, List<String> dadosDistintos) {
        for (int j = 0; j < GeometryFactory.FORMAS.GLYPH_FORMAS.values().length - 1; j++) {
            if (item.getMapaDetalhesItem().get(col).equalsIgnoreCase(dadosDistintos.get(j))) {
                Glyph shape = defineShape(FORMAS.GLYPH_FORMAS.values()[j]);
                shape.setNodeTreemap(item);
                return shape;
            }
        }
        return null;
    }

    public Glyph prepareDimensaoTextDinamico(Coluna col, TreeMapItem item, List<String> dadosDistintos) {
        for (int j = 0; j < Constantes.LETRAS_ALFABETO.length; j++) {
            if (item.getMapaDetalhesItem().get(col).equalsIgnoreCase(dadosDistintos.get(j))) {
                Glyph text = defineText(Constantes.LETRAS_ALFABETO[j]);
                text.setNodeTreemap(item);
                return text;
            }
        }
        return null;
    }

    @Deprecated
    public Glyph prepareDimensaoNumberDinamico(Coluna col, TreeMapItem item, List<String> dadosDistintos) {
        for (int j = 0; j < Constantes.NUMEROS.length; j++) {
            if (item.getMapaDetalhesItem().get(col).equalsIgnoreCase(dadosDistintos.get(j))) {
                Glyph result = defineText(Constantes.NUMEROS[j]);
//                result.usingNumber(true);
//                numeroUtilizado = Constantes.NUMEROS[j];
//                result.setNumber(numeroUtilizado);
                result.setNodeTreemap(item);
                return result;
            }
        }
        return null;
    }

    private Glyph defineTexture(String nomeTextura) {
        Glyph glyph = new Textura(Color.GRAY, Color.WHITE);
        Textura textura = (Textura) glyph;
        textura.setNomeTextura(nomeTextura);
        textura.setPectSobreposicao(0.65f);
        textura.setOverlappingActivated(overlappingActivated);
        return glyph;
    }

    private Glyph defineColor(Color color) {
        Glyph glyph = new Cor();
        Cor cor = (Cor) glyph;
        cor.setCor(color);
        cor.setPectSobreposicao(0.65f);
        cor.setOverlappingActivated(overlappingActivated);
        return glyph;
    }

    private Glyph defineShape(FORMAS.GLYPH_FORMAS forma) {
        Glyph glyph = new FormaGeometrica();
        FormaGeometrica shape = (FormaGeometrica) glyph;
        shape.setDrawBehavior(GeometryFactory.create(forma));
        shape.setPectSobreposicao(0.65f);
        shape.setOverlappingActivated(overlappingActivated);
        return glyph;
    }

    private Glyph defineText(String letter) {
        Text text = new Text();
        text.setLetra(letter);
        text.setPectSobreposicao(0.65f);
        text.setOverlappingActivated(overlappingActivated);
        return text;
    }

    /**
     * Mapeia as dimenssões 0 - textura, 1 - cor, 2 - forma, 3 - text, 4 -
     * numero
     *
     * @param varVisual nome da var visual
     * @return int representando a dimensao
     */
    private int mapearVarVisual2Dimensao(String varVisual) {
        int dimensao = -1;
        switch (varVisual) {
            case "Texture":
                dimensao = 0;
                break;
            case "Color":
                dimensao = 1;
                break;
            case "Shape":
                dimensao = 2;
                break;
            case "Text"://case "Letter":
                dimensao = 3;
                break;
            case "Number":
                dimensao = 4;
                break;
            case "Star":
                dimensao = 4;
                break;
            default:
                throw new AssertionError();
        }
        return dimensao;
    }

    public void configGlyphDesingModel(boolean overlappingActivated) {
        this.overlappingActivated = overlappingActivated;
    }

    /**
     * @return the rootNodeZoom
     */
    public TMNodeModelRoot getRootNodeZoom() {
        return rootNodeZoom;
    }

    /**
     * @param rootNodeZoom the rootNodeZoom to set
     */
    public void setRootNodeZoom(TMNodeModelRoot rootNodeZoom) {
        this.rootNodeZoom = rootNodeZoom;
//        System.out.println("Root Node Zoom: "+this.rootNodeZoom.getRoot().getTitle());
    }

    public String getGlyphContinuoEscolhido() {
        return glyphContinuoEscolhido;
    }

    public void setGlyphContinuoEscolhido(String glyphContinuoEscolhido) {
        this.glyphContinuoEscolhido = glyphContinuoEscolhido;
    }

    /**
     * @return the variaveisVisuaisEscolhidas
     */
    public String[] getVariaveisVisuaisEscolhidas() {
        return variaveisVisuaisEscolhidas;
    }

    /**
     * @param variaveisVisuaisEscolhidas the variaveisVisuaisEscolhidas to set
     */
    public void setVariaveisVisuaisEscolhidas(String[] variaveisVisuaisEscolhidas) {
        this.variaveisVisuaisEscolhidas = variaveisVisuaisEscolhidas;
    }

//    public void setQuantValoresVarVisuais(int quantValoresVarVisuais) {
//        this.quantValoresVarVisuais = quantValoresVarVisuais;
//    }
    /**
     * @return the atributosEscolhidosStarGlyph
     */
    public List<String> getAtributosEscolhidosGlyphContinuo() {
        return atributosEscolhidosGlyphContinuo;
    }

    /**
     * @param atributosEscolhidosStarGlyph the atributosEscolhidosStarGlyph to
     * set
     */
    public void setAtributosEscolhidosGlyphContinuo(List<String> atributosEscolhidosStarGlyph) {
        this.atributosEscolhidosGlyphContinuo = atributosEscolhidosStarGlyph;
    }
}

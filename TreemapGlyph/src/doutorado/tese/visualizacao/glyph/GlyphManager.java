/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.visualizacao.glyph;

import doutorado.tese.util.machinelearning.tree.DecisionTreeClassifier;
import doutorado.tese.io.ManipuladorArquivo;
import doutorado.tese.util.Coluna;
import doutorado.tese.util.Constantes;
import doutorado.tese.util.Metadados;
import doutorado.tese.visualizacao.glyph.decorator.variaveisvisuais.color.Cor;
import doutorado.tese.visualizacao.glyph.decorator.variaveisvisuais.letters.Letra;
import doutorado.tese.visualizacao.glyph.decorator.variaveisvisuais.numbers.Numeral;
import doutorado.tese.visualizacao.glyph.decorator.variaveisvisuais.shapes.FormaGeometrica;
import doutorado.tese.visualizacao.glyph.decorator.variaveisvisuais.texture.Textura;
import doutorado.tese.visualizacao.glyph.formasgeometricas.GeometryFactory;
import doutorado.tese.visualizacao.treemap.TreeMapItem;
import glyph.starglyph.EixoPolarStarGlyph;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import net.bouthier.treemapAWT.TMNode;
import net.bouthier.treemapAWT.TMNodeEncapsulator;
import net.bouthier.treemapAWT.TMNodeModel;
import net.bouthier.treemapAWT.TMNodeModelComposite;
import net.bouthier.treemapAWT.TMNodeModelRoot;

/**
 *
 * @author Anderson Soares
 */
public final class GlyphManager {

    private ManipuladorArquivo manipulador;
    private List<Object> atributosEscolhidos;
    private HashMap<String, List<String>> colunaDadosDist;
    private TMNodeModelRoot rootNodeZoom;
    private boolean dimensao4Ativada;
    private String letraUtilizada;
    private static String[] shufflerColors;

    private HashMap<String, Integer> configs;
    private boolean decisionTreeActivate;
    private String[] variaveisVisuaisEscolhidas;
//    private float perctOverlap;
    private int quantValoresVarVisuais;
    private Rectangle bounds;
    private boolean overlappingActivated;
    private String numeroUtilizado;
    private String[] atributosBaseEscolhidos;

    public GlyphManager() {
        this.configs = new HashMap<>();
    }

    public GlyphManager(ManipuladorArquivo manipulador, List<Object> atributosEscolhidos, Rectangle bounds) {
        this.manipulador = manipulador;
        this.atributosEscolhidos = atributosEscolhidos;
        colunaDadosDist = new HashMap<>();
        analisarAtributosEscolhidos();
        shufflerColors = Constantes.getCorGlyphs();
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

    public void setUseDecisionTree(boolean decisionTreeActivate) {
        this.decisionTreeActivate = decisionTreeActivate;
    }

    public void prepare2Draw() {
        if (getRootNodeZoom() != null) {
//            System.out.println("getRootNodeZoom() =  "+getRootNodeZoom().getRoot().getTitle()+
//            "\tbounds: "+getRootNodeZoom().getRoot().getArea().toString());
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
            item.getGlyph().setDecisionTreeActivate(decisionTreeActivate);
            item.getGlyph().paint(g2d);
            item.getGlyph().getChildren(list);
            g2d.setClip(0, 0, bounds.width, bounds.height);
//            if (!decisionTreeActivate || item.getWhat2Draw()[0] == 1) {
//                paintTextura(g, item);
//            }
//            if (!decisionTreeActivate || item.getWhat2Draw()[1] == 1) {
//                paintCorForma(g, item);
//            }
//            if (!decisionTreeActivate || item.getWhat2Draw()[2] == 1) {
//                paintFormaGeometrica(g, item);
//            }
//            if (!decisionTreeActivate || item.getWhat2Draw()[3] == 1) {
//                paintLetrasAlfabeto(g, item);
//            }
//            if (!decisionTreeActivate || item.getWhat2Draw()[4] == 1) {
//                paintNumeros(g, item);
//            }
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
//            System.out.println("item name: "+treemapItem.getLabel()+
//                    " - Glyph pai: "+treemapItem.getGlyph().toString()+" - "+treemapItem.getGlyph().getBounds() +
//                    " Filho: "+treemapItem.getGlyph().getChild().toString()+" - "+treemapItem.getGlyph().getChild().getBounds());
        }
    }

    public void prepareDimension2DrawGlyph(TreeMapItem item) {
//        double[] features = new double[15];
//        limparGlyphsTreemapItem(item);
//        features[Constantes.FEATURE_LARGURA] = item.getBounds().width;
//        features[Constantes.FEATURE_ALTURA] = item.getBounds().height;
//        features[Constantes.PRESENCA_COR_TREEMAP] = item.getColor().equals(Constantes.ALICE_BLUE) ? Constantes.AUSENTE : Constantes.PRESENTE;
//        features[Constantes.FEATURE_AREA] = features[Constantes.FEATURE_LARGURA] * features[Constantes.FEATURE_ALTURA];
//
//        double aspect = features[Constantes.FEATURE_ALTURA] > features[Constantes.FEATURE_LARGURA]
//                ? features[Constantes.FEATURE_LARGURA] / features[Constantes.FEATURE_ALTURA]
//                : features[Constantes.FEATURE_ALTURA] / features[Constantes.FEATURE_LARGURA];
//
//        features[Constantes.FEATURE_ASPECT] = aspect;
//        for (int dimensao = 0; dimensao < atributosEscolhidos.size(); dimensao++) {
//            String colunaEscolhida = atributosEscolhidos.get(dimensao).toString();
//            Coluna col = ManipuladorArquivo.getColuna(colunaEscolhida);
//            List<String> dadosDistintos = colunaDadosDist.get(colunaEscolhida);
//            if (atributosEscolhidos.get(dimensao).equals("---")) {
//                dadosDistintos = null;
//            }
//            switch (dimensao) {
//                case 0:
//                    features[Constantes.AREA_TEXTURA] = dadosDistintos != null ? prepareDimensaoTextura(col, item, dadosDistintos)
//                            : prepareTextura(item.getBounds(), Constantes.TIPO_TEXTURA[0], null);
//                    features[Constantes.PRESENCA_TEXTURA] = Constantes.PRESENTE;
//                    break;
//                case 1:
//                    features[Constantes.AREA_CIRCULO_COLORIDO] = dadosDistintos != null ? prepareSegundaDimensao(col, item, dadosDistintos)
//                            : prepareCorForma(item.getBounds(), Color.decode(Constantes.getCorGlyphs()[0]), null);
//                    features[Constantes.PRESENCA_COR_FORMA] = Constantes.PRESENTE;
//                    break;
//                case 2:
//                    features[Constantes.AREA_SHAPE] = dadosDistintos != null ? prepareTerceiraDimensao(col, item, dadosDistintos)
//                            : prepareFormaGeometrica(item.getBounds(), GeometryFactory.FORMAS.GLYPH_FORMAS.values()[0], null);
//                    features[Constantes.PRESENCA_FORMA] = Constantes.PRESENTE;
//                    break;
//                case 3:
//                    if (!atributosEscolhidos.get(dimensao).equals("---")) {
//                        dimensao4Ativada = true;
//                        letraUtilizada = "";
//                    }
//                    features[Constantes.AREA_LETRA] = dadosDistintos != null ? prepareQuartaDimensao(col, item, dadosDistintos)
//                            : prepareLetrasAlfabeto(item.getBounds(), Constantes.LETRAS_ALFABETO[0], null);
//                    features[Constantes.PRESENCA_LETRA] = Constantes.PRESENTE;
//                    break;
//                case 4:
//                    features[Constantes.AREA_NUMERO] = dadosDistintos != null ? prepareQuintaDimensao(col, item, dadosDistintos)
//                            : prepareNumeros(item.getBounds(), Constantes.NUMEROS[0], null);
//                    features[Constantes.PRESENCA_NUMERO] = Constantes.PRESENTE;
//                    break;
//                default:
//                    System.err.println("Nao foi possível calcular a dimensão.");
//            }
//        }
//        item.getWhat2Draw()[Constantes.PRESENCA_TEXTURA] = DecisionTreeClassifier.predict(features)[0];
//        item.getWhat2Draw()[Constantes.PRESENCA_COR_FORMA] = DecisionTreeClassifier.predict(features)[1];
//        item.getWhat2Draw()[Constantes.PRESENCA_FORMA] = DecisionTreeClassifier.predict(features)[2];
//        item.getWhat2Draw()[Constantes.PRESENCA_LETRA] = DecisionTreeClassifier.predict(features)[3];
//        item.getWhat2Draw()[Constantes.PRESENCA_NUMERO] = DecisionTreeClassifier.predict(features)[4];
    }

    private double[] getFeatures(TreeMapItem item, double[] features) {
        limparGlyphsTreemapItem(item);
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
                features[Constantes.AREA_TEXTURA] = ((Textura) glyph).getArea();//aqui a area ainda nao foi calculada
                features[Constantes.PRESENCA_TEXTURA] = Constantes.PRESENTE;
            } else if (glyph instanceof Cor) {
                features[Constantes.AREA_CIRCULO_COLORIDO] = ((Cor) glyph).getArea();
                features[Constantes.PRESENCA_COR_FORMA] = Constantes.PRESENTE;
            } else if (glyph instanceof FormaGeometrica) {
                features[Constantes.AREA_SHAPE] = ((FormaGeometrica) glyph).getArea();
                features[Constantes.PRESENCA_FORMA] = Constantes.PRESENTE;
            } else if (glyph instanceof Letra) {
                features[Constantes.AREA_LETRA] = ((Letra) glyph).getArea();
                features[Constantes.PRESENCA_LETRA] = Constantes.PRESENTE;
            } else if (glyph instanceof Numeral) {
                features[Constantes.AREA_NUMERO] = ((Numeral) glyph).getArea();
                features[Constantes.PRESENCA_NUMERO] = Constantes.PRESENTE;
            }
        });
        item.getWhat2Draw()[Constantes.PRESENCA_TEXTURA] = DecisionTreeClassifier.predict(features)[0];
        item.getWhat2Draw()[Constantes.PRESENCA_COR_FORMA] = DecisionTreeClassifier.predict(features)[1];
        item.getWhat2Draw()[Constantes.PRESENCA_FORMA] = DecisionTreeClassifier.predict(features)[2];
        item.getWhat2Draw()[Constantes.PRESENCA_LETRA] = DecisionTreeClassifier.predict(features)[3];
        item.getWhat2Draw()[Constantes.PRESENCA_NUMERO] = DecisionTreeClassifier.predict(features)[4];
//        item.getWhat2Draw()[Constantes.PRESENCA_STAR] = DecisionTreeClassifier.predict(features)[5];
        return features;
    }

    /**
     * Recebe um TreeMapItem, mata todos os seus filhos antigos, e adiciona seus
     * novos filhos de acordo com a hierarquia passada atraves da funcao
     * variaveisVisuaisEscolhidas(). Por fim, é definido o tamanho de cada item
     * no layout treemap.
     *
     * @param item
     * @return item com as layers configuradas
     */
    public TreeMapItem configLayers(TreeMapItem item) {
        Glyph father = item.getGlyph();
        father.killAllChild();
        for (String varVisual : getVariaveisVisuaisEscolhidas()) {
            int dimensao = mapearVarVisual2Dimensao(varVisual);
            Glyph child = setLayerInGlyph(varVisual, item, dimensao);
            father.appendChild(child);
//            ArrayList<Glyph> list = new ArrayList<>();
//            father.getChildren(list);
//            if (father.child.getClass().getSimpleName().equals("Textura")) {
//                System.out.println("Cor Textura: "+((Textura) father.child).getCor());
//            }
//            System.out.println(list.toString());
        }
        if (getVariaveisVisuaisEscolhidas()[0].equals("star")) {
            Glyph child = setLayerInGlyph("Star", item, -1);
            father.appendChild(child);
        }
        father.setBounds(father.getBounds());
        if (decisionTreeActivate) {
            double[] features = new double[15];
            getFeatures(item, features);
        }
        if (item.hasGlyphResposta(father)) {
            item.setPossuiGlyphResposta(true);
        }
        return item;
    }

    public void setAtributosBaseEscolhidos(String[] atributosBaseEscolhidos) {
        this.atributosBaseEscolhidos = atributosBaseEscolhidos;
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
            case "Letter":
                glyph = prepareDimensaoLetterDinamico(col, item, dadosDistintos);
                break;
            case "Number":
                glyph = prepareDimensaoNumberDinamico(col, item, dadosDistintos);
                break;
            case "Star":
                glyph = configureStarGlyph(item);
                break;
            default:
                System.out.println("error ");
                break;
        }
        return glyph;
    }

    private Glyph configureStarGlyph(TreeMapItem item) {
        glyph.starglyph.StarGlyph starGlyph = new glyph.starglyph.StarGlyph(Arrays.asList(atributosBaseEscolhidos));
        starGlyph.setQuantVar(atributosBaseEscolhidos.length);
        starGlyph.setPectSobreposicao(0.85f);
        starGlyph.setOverlappingActivated(true);
        for (int i = 0; i < atributosBaseEscolhidos.length; i++) {
            String nomeColunaEscolhida = atributosBaseEscolhidos[i];
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
            interpolator.config(col.maiorMenorValues[0], col.maiorMenorValues[1], Color.orange, Color.decode("#4682B4"));
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
                Glyph shape = defineShape(doutorado.tese.visualizacao.glyph.factorys.variaveisvisuais.GeometryFactory.FORMAS.GLYPH_FORMAS.values()[j]);
                shape.setNodeTreemap(item);
                return shape;
            }
        }
        return null;
    }

    // acionarStarGlyph(List<String> variaveisStarGlyph)
//    public Glyph prepareDimensaoStarGlyphDinamico(Coluna col, TreeMapItem item, List<String> dadosDistintos) {
//         for (int j = 0; j < GeometryFactory.FORMAS.GLYPH_FORMAS.values().length - 1; j++) {
//            if (item.getMapaDetalhesItem().get(col).equalsIgnoreCase(dadosDistintos.get(j))) {
//                Glyph shape = defineShape(doutorado.tese.visualizacao.glyph.factorys.variaveisvisuais.GeometryFactory.FORMAS.GLYPH_FORMAS.values()[j]);
//                shape.setNodeTreemap(item);
//                return ;
//            }
//        }
//        return null;
//        
//    }
//    
    public Glyph prepareDimensaoLetterDinamico(Coluna col, TreeMapItem item, List<String> dadosDistintos) {
        for (int j = 0; j < Constantes.LETRAS_ALFABETO.length; j++) {
            if (item.getMapaDetalhesItem().get(col).equalsIgnoreCase(dadosDistintos.get(j))) {
                Glyph result = defineLetter(Constantes.LETRAS_ALFABETO[j]);
                result.usingLetter(true);
                letraUtilizada = Constantes.LETRAS_ALFABETO[j];
                result.setLetter(letraUtilizada);
                result.setNodeTreemap(item);
                return result;
            }
        }
        return null;
    }

    public Glyph prepareDimensaoNumberDinamico(Coluna col, TreeMapItem item, List<String> dadosDistintos) {
        for (int j = 0; j < Constantes.NUMEROS.length; j++) {
            if (item.getMapaDetalhesItem().get(col).equalsIgnoreCase(dadosDistintos.get(j))) {
                Glyph result = defineLetter(Constantes.NUMEROS[j]);
                result.usingNumber(true);
                numeroUtilizado = Constantes.NUMEROS[j];
                result.setNumber(numeroUtilizado);
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

    private Glyph defineShape(doutorado.tese.visualizacao.glyph.factorys.variaveisvisuais.GeometryFactory.FORMAS.GLYPH_FORMAS forma) {
        Glyph glyph = new doutorado.tese.visualizacao.glyph.decorator.variaveisvisuais.shapes.FormaGeometrica();
        doutorado.tese.visualizacao.glyph.decorator.variaveisvisuais.shapes.FormaGeometrica shape
                = (doutorado.tese.visualizacao.glyph.decorator.variaveisvisuais.shapes.FormaGeometrica) glyph;
        shape.setDrawBehavior(doutorado.tese.visualizacao.glyph.factorys.variaveisvisuais.GeometryFactory.
                create(forma));
        shape.setPectSobreposicao(0.65f);
        shape.setOverlappingActivated(overlappingActivated);
        return glyph;
    }

    private Glyph defineLetter(String letter) {
        Glyph glyph = new doutorado.tese.visualizacao.glyph.decorator.variaveisvisuais.letters.Letra();
        doutorado.tese.visualizacao.glyph.decorator.variaveisvisuais.letters.Letra letra = (doutorado.tese.visualizacao.glyph.decorator.variaveisvisuais.letters.Letra) glyph;
        letra.setLetra(letter);
        letra.setPectSobreposicao(0.65f);
        letra.setOverlappingActivated(overlappingActivated);
        return glyph;
    }

    private void limparGlyphsTreemapItem(TreeMapItem item) {
        item.setTextura(null);
        item.setCorForma(null);
        item.setFormaGeometrica(null);
        item.setLetra(null);
        item.setNumero(null);
    }

    /**
     * Mapeia as dimenssões 0 - textura, 1 - cor, 2 - forma, 3 - letra, 4 -
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
            case "Letter":
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

    public void setQuantValoresVarVisuais(int quantValoresVarVisuais) {
        this.quantValoresVarVisuais = quantValoresVarVisuais;
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.control.mb.testeMB.scalabilityMB;

import doutorado.tese.control.business.visualizations.glyph.Glyph;
import doutorado.tese.control.business.visualizations.glyph.GlyphConcrete;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.color.ColorHue;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.orientation.Orientation;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.position.Position;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.shapes.GeometricShape;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.text.Text;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.texture.Texture;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.texture.Texture_old;
import doutorado.tese.control.business.visualizations.glyph.decorator.continuous.ProfileGlyph;
import doutorado.tese.control.business.visualizations.glyph.factorys.variaveisvisuais.GeometryFactory;
import doutorado.tese.control.business.visualizations.glyph.factorys.variaveisvisuais.OrientationFactory;
import doutorado.tese.control.business.visualizations.glyph.factorys.variaveisvisuais.TexturesFactory;
import doutorado.tese.control.mb.GlyphMB;
import doutorado.tese.dao.ManipuladorArquivo;
import doutorado.tese.model.Coluna;
import doutorado.tese.model.TreeMapItem;
import doutorado.tese.util.Constantes;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anderson Soares
 */
public class SetUpScalabilityTestMB {

    private TreeMapItem itemInput = null;
    private TreeMapItem itemOutput = null;
    private HashMap<String, Integer> inputConfigs;
    private HashMap<String, Boolean> outputConfigs;
    private HashMap<Coluna, String> dadosTreemapItem;
    private String[] dadosSimulados;
    private final HashMap<String, Integer> areas;
    private final GlyphMB glyphMB;
    private final boolean overlappingActivated;
    private int areaTextura = 0, areaColorHue = 0, areaForma = 0, areaText = 0, areaPosition = 0, areaOrientation = 0, areaProfileGlyph = 0;
    private List<String> atributosEscolhidosGlyphContinuo;
    private Coluna[] colunas = null;
    private Random rand;
    private Constantes.VAR_VISUAIS_CATEGORICAS[] layersMisturadas;

    public SetUpScalabilityTestMB() {
        areas = new HashMap<>();
        dadosTreemapItem = new HashMap<>();
        atributosEscolhidosGlyphContinuo = new ArrayList<>();
        rand = new Random(System.currentTimeMillis());

        overlappingActivated = true;
        try {
            colunas = ManipuladorArquivo.montarColunas();
        } catch (Exception ex) {
            Logger.getLogger(SetUpScalabilityTestMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        glyphMB = new GlyphMB();
        glyphMB.configGlyphDesingModel(this.overlappingActivated);
        for (Coluna coluna : colunas) {
            atributosEscolhidosGlyphContinuo.add(coluna.getName());
        }
    }

    private TreeMapItem criarTreemapItem(TreeMapItem item) {
        Rectangle bounds = new Rectangle(getInputConfigs().get("x"), getInputConfigs().get("y"), getInputConfigs().get("width"), getInputConfigs().get("height"));
        try {
            item.setBounds(bounds);
            item.setMapaDetalhesItem(dadosTreemapItem);
            Glyph glyphConcrete = new GlyphConcrete();
            glyphConcrete.setNodeTreemap(item);
            item.setGlyph(glyphConcrete);
            item.getGlyph().setBounds(item.getBounds());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    public void procurarAreaGlyphFilhos(TreeMapItem item) {
        areaTextura = areaColorHue = areaForma = areaText = areaPosition = areaOrientation = areaProfileGlyph = 0;

        for (Glyph child : item.getGlyphFamily(item.getGlyph(), new ArrayList<>())) {
            if (child instanceof Texture) {
                areaTextura = child.getArea();
            } else if (child instanceof ColorHue) {
                areaColorHue = child.getArea();
            } else if (child instanceof GeometricShape) {
                areaForma = child.getArea();
            } else if (child instanceof Text) {
                areaText = child.getArea();
            } else if (child instanceof Position) {
                areaPosition = child.getArea();
            } else if (child instanceof Orientation) {
                areaOrientation = child.getArea();
            } else if (child instanceof ProfileGlyph) {
                areaProfileGlyph = child.getArea();
            }
        }
        getAreas().put("texture", getAreaTextura());
        getAreas().put("colorhue", getAreaColorHue());
        getAreas().put("geometricshape", getAreaForma());
        getAreas().put("text", getAreaText());
        getAreas().put("position", getAreaPosition());
        getAreas().put("orientation", getAreaOrientation());
        getAreas().put("profileglyph", getAreaProfileGlyph());
    }

    public List<Glyph> configLayersInput() {
        criarDadosSimulados();
        itemInput = criarTreemapItem(new TreeMapItem(1, 0));

        Glyph father = getItemInput().getGlyph();
        father.killAllChild();//eh feito um kill para garantir que nao ha filhos
//TODO sortear familia e se o profile glyph for sorteado, ele deve ficar sempre na ultima camada.
        layersMisturadas = shuffleArray(Constantes.VAR_VISUAIS_CATEGORICAS.values());
        for (Constantes.VAR_VISUAIS_CATEGORICAS var : layersMisturadas) {
//        for (Constantes.VAR_VISUAIS_CATEGORICAS var : Constantes.VAR_VISUAIS_CATEGORICAS.values()) {
            Glyph child = null;
            switch (var) {
                case TEXTURE:
                    if (getInputConfigs().get("texture") >= 0) {
//                        child = getGlyphMB().defineTexture(Constantes.TIPO_TEXTURA[getInputConfigs().get("texture")]);
                        child = getGlyphMB().defineTexture(TexturesFactory.TEXTURE.GLYPH_TEXTURAS.values()[getInputConfigs().get("texture")]);
                        child.setNodeTreemap(getItemInput());
                    }
                    break;
                case COLOR_HUE:
                    if (getInputConfigs().get("colorhue") >= 0) {
                        child = getGlyphMB().defineColorHue(Color.decode(Constantes.getColorHueGlyphs()[getInputConfigs().get("colorhue")]));
                        child.setNodeTreemap(getItemInput());
                    }
                    break;
                case SHAPE:
                    if (getInputConfigs().get("geometricshape") >= 0) {
                        child = getGlyphMB().defineShape(GeometryFactory.FORMAS.GLYPH_FORMAS.values()[getInputConfigs().get("geometricshape")]);
                        child.setNodeTreemap(getItemInput());
                    }
                    break;
                case TEXT:
                    if (getInputConfigs().get("text") >= 0) {
                        //0 - text; 1 - profile glyph
                        if (rand.nextInt(2) == 0) {
                            child = getGlyphMB().defineText(Constantes.LETRAS_ALFABETO[getInputConfigs().get("text")]);
                            child.setNodeTreemap(getItemInput());
                            getInputConfigs().put("profileglyph", 0);
                        } else {
                            getInputConfigs().put("profileglyph", 1);
                        }
                    }
                    break;
                case POSITION:
                    if (getInputConfigs().get("position") >= 0) {
                        child = getGlyphMB().definePosition(Constantes.POSICOES.values()[getInputConfigs().get("position")]);
                        child.setNodeTreemap(getItemInput());
                    }
                    break;
                case ORIENTATION:
                    if (getInputConfigs().get("orientation") >= 0) {
                        child = getGlyphMB().defineOrientation(OrientationFactory.ARROW.GLYPH_ORIENTACAO.values()[getInputConfigs().get("orientation")]);
                        child.setNodeTreemap(getItemInput());
                    }
                    break;
            }

            if (child != null) {
                father.appendChild(child);
            }
        }
        if (getInputConfigs().get("profileglyph") > 0) {
            getInputConfigs().put("text", -1);
            getGlyphMB().setAtributosEscolhidosGlyphContinuo(atributosEscolhidosGlyphContinuo);
            Glyph child = getGlyphMB().configureProfileGlyph(itemInput);
            child.setNodeTreemap(getItemInput());
            father.appendChild(child);
        }
        List<Glyph> familiaGlyphs = getItemInput().getGlyphFamily(father, new ArrayList<>());
//        System.out.println("familia: " + familiaGlyphs.toString());
        if (father.getBounds() != null) {
            father.setBounds(father.getBounds());
        }
        procurarAreaGlyphFilhos(getItemInput());
        return familiaGlyphs;
    }

    public void configLayersOutput() {
        itemOutput = criarTreemapItem(new TreeMapItem(1, 0));
        itemOutput.getBounds().x = 200;
        Glyph father = getItemOutput().getGlyph();
        father.killAllChild();

        for (Constantes.VAR_VISUAIS_CATEGORICAS var : layersMisturadas) {
//        for (Constantes.VAR_VISUAIS_CATEGORICAS var : Constantes.VAR_VISUAIS_CATEGORICAS.values()) {
            Glyph child = null;
            switch (var) {
                case TEXTURE:
                    if (getOutputConfigs().get("texture") && getInputConfigs().get("texture") >= 0) {
                        child = getGlyphMB().defineTexture(TexturesFactory.TEXTURE.GLYPH_TEXTURAS.values()[getInputConfigs().get("texture")]);
                        child.setNodeTreemap(getItemOutput());
                    }
                    break;
                case COLOR_HUE:
                    if (getOutputConfigs().get("colorhue") && getInputConfigs().get("colorhue") >= 0) {
                        child = getGlyphMB().defineColorHue(Color.decode(Constantes.getColorHueGlyphs()[getInputConfigs().get("colorhue")]));
                        child.setNodeTreemap(getItemOutput());
                    }
                    break;
                case SHAPE:
                    if (getOutputConfigs().get("geometricshape") && getInputConfigs().get("geometricshape") >= 0) {
                        child = getGlyphMB().defineShape(GeometryFactory.FORMAS.GLYPH_FORMAS.values()[getInputConfigs().get("geometricshape")]);
                        child.setNodeTreemap(getItemOutput());
                    }
                    break;
                case TEXT:
                    if (getOutputConfigs().get("text") && getInputConfigs().get("text") >= 0) {
                        child = getGlyphMB().defineText(Constantes.LETRAS_ALFABETO[getInputConfigs().get("text")]);
                        child.setNodeTreemap(getItemOutput());
                    }
                    break;
                case POSITION:
                    if (getOutputConfigs().get("position") && getInputConfigs().get("position") >= 0) {
                        child = getGlyphMB().definePosition(Constantes.POSICOES.values()[getInputConfigs().get("position")]);
                        child.setNodeTreemap(getItemOutput());
                    }
                    break;
                case ORIENTATION:
                    if (getOutputConfigs().get("orientation") && getInputConfigs().get("orientation") >= 0) {
                        child = getGlyphMB().defineOrientation(OrientationFactory.ARROW.GLYPH_ORIENTACAO.values()[getInputConfigs().get("orientation")]);
                        child.setNodeTreemap(getItemOutput());
                    }
                    break;
            }

            if (child != null) {
                father.appendChild(child);
            }
        }
        if (getOutputConfigs().get("profileglyph") && getInputConfigs().get("profileglyph") > 0) {
            getGlyphMB().setAtributosEscolhidosGlyphContinuo(atributosEscolhidosGlyphContinuo);
            Glyph child = getGlyphMB().configureProfileGlyph(itemOutput);
            child.setNodeTreemap(getItemOutput());
            father.appendChild(child);
        }
        if (father.getBounds() != null) {
            father.setBounds(father.getBounds());
        }
    }

    private void criarDadosSimulados() {
        int valor = 100;
        List<String[]> dadosColunas = new ArrayList<>();
        for (int i = 0; i < colunas.length; i++) {
            dadosSimulados = new String[3];
            for (int j = 0; j < dadosSimulados.length; j++) {
                dadosSimulados[j]
                        = (rand.nextInt(2) == 0)
                        ? "" + (rand.nextInt(valor) * (-1))
                        : "" + rand.nextInt(valor);
            }
            dadosColunas.add(dadosSimulados);
        }
        int linha = rand.nextInt(3);//escolhe entre as linhas 0, 1, e 2 
        for (int i = 0; i < dadosColunas.size(); i++) {
            try {
                dadosTreemapItem.put(colunas[i], dadosColunas.get(i)[linha]);
                colunas[i].configurarDescricao(dadosColunas.get(i));
            } catch (Exception ex) {
                Logger.getLogger(SetUpScalabilityTestMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Funcao que recebe um vetor de inteiros e mistura seu conteudo
     *
     * @param ar vetor que tera seu conteudo misturado
     */
    public static void shuffleArray(int[] ar) {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {
            int indexSorteado = rnd.nextInt(i + 1);
            // Simple swap
            int valor = ar[indexSorteado];
            ar[indexSorteado] = ar[i];
            ar[i] = valor;
        }
    }

    public static Constantes.VAR_VISUAIS_CATEGORICAS[] shuffleArray(Constantes.VAR_VISUAIS_CATEGORICAS[] original) {
        Constantes.VAR_VISUAIS_CATEGORICAS[] novo = new Constantes.VAR_VISUAIS_CATEGORICAS[original.length];

        System.arraycopy(original, 0, novo, 0, original.length);

        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for (int i = novo.length - 1; i > 0; i--) {
            int indexSorteado = rnd.nextInt(i + 1);
            // Simple swap
            Constantes.VAR_VISUAIS_CATEGORICAS valor = novo[indexSorteado];
            novo[indexSorteado] = novo[i];
            novo[i] = valor;
        }
        return novo;
    }

    /**
     * @return the itemInput
     */
    public TreeMapItem getItemInput() {
        return itemInput;
    }

    /**
     * @return the itemOutput
     */
    public TreeMapItem getItemOutput() {
        return itemOutput;
    }

    /**
     * @return the outputConfigs
     */
    public HashMap<String, Boolean> getOutputConfigs() {
        return outputConfigs;
    }

    /**
     * @param outputConfigs the outputConfigs to set
     */
    public void setOutputConfigs(HashMap<String, Boolean> outputConfigs) {
        this.outputConfigs = outputConfigs;
    }

    /**
     * @return the inputConfigs
     */
    public HashMap<String, Integer> getInputConfigs() {
        return inputConfigs;
    }

    /**
     * @param inputConfigs the inputConfigs to set
     */
    public void setInputConfigs(HashMap<String, Integer> inputConfigs) {
        this.inputConfigs = inputConfigs;
    }

    /**
     * @return the areas
     */
    public HashMap<String, Integer> getAreas() {
        return areas;
    }

    /**
     * @return the glyphMB
     */
    public GlyphMB getGlyphMB() {
        return glyphMB;
    }

    /**
     * @return the overlappingActivated
     */
    public boolean isOverlappingActivated() {
        return overlappingActivated;
    }

    /**
     * @return the areaTextura
     */
    public int getAreaTextura() {
        return areaTextura;
    }

    /**
     * @return the areaColorHue
     */
    public int getAreaColorHue() {
        return areaColorHue;
    }

    /**
     * @return the areaForma
     */
    public int getAreaForma() {
        return areaForma;
    }

    /**
     * @return the areaText
     */
    public int getAreaText() {
        return areaText;
    }

    /**
     * @return the areaPosition
     */
    public int getAreaPosition() {
        return areaPosition;
    }

    public int getAreaOrientation() {
        return areaOrientation;
    }

    /**
     * @return the areaProfileGlyph
     */
    public int getAreaProfileGlyph() {
        return areaProfileGlyph;
    }
}

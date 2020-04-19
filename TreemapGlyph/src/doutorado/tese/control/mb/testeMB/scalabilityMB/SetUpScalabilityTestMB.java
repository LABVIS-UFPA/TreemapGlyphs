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
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
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
    private int[] glyphLayers2draw = {0, 1, 2, 3, 4, 5, 6};
    private String[] layers = new String[]{"texture", "colorhue", "geometricshape", "text", "position", "orientation", "coritem"};
    private List<List<Glyph>> listaFamilias;
    private final int quantGlyphsBase = 5;
    private List<TreeMapItem> listaItens;
    private int contTarefasRealizadas = 0;
    private int rodadaRemoveCamada = 0;
    private int controleGlyphBase = -1;

    public SetUpScalabilityTestMB() {
        areas = new HashMap<>();
        dadosTreemapItem = new HashMap<>();
        inputConfigs = new HashMap<>();
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
        listaFamilias = new ArrayList<>();
        listaItens = new ArrayList<>();
        criarGlyphsBase();
    }

    private TreeMapItem criarNovoItemRecuperandoItemAntigo(TreeMapItem item) {
        inputConfigs.put("x", item.getBounds().x);
        inputConfigs.put("y", item.getBounds().y);

        inputConfigs.put("width",item.getBounds().width);
        inputConfigs.put("height",item.getBounds().height);

        //pensar em como colocar a cor no item novo e no recuperado
        inputConfigs.put("coritem", rand.nextInt(Constantes.getCorTreemap().length));
        TreeMapItem newItem = new TreeMapItem(1, 0);
        newItem.setBounds(item.getBounds());
        return item;
    }

    private TreeMapItem criarTreemapItem(TreeMapItem item, boolean newItemBase) {
        Rectangle bounds;
        if (newItemBase) {
            int length = rand.nextInt(50) + 15;
            bounds = new Rectangle(
                    50,
                    50,
                    Math.abs(length - (rand.nextInt(100) + 15)),
                    Math.abs(length - (rand.nextInt(100) + 15))
            );
            inputConfigs.put("coritem", rand.nextInt(Constantes.getCorTreemap().length));
        } else {
            item = criarNovoItemRecuperandoItemAntigo(item);
            bounds = item.getBounds();
        }
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

    public Set<Integer> sortearCamadas() {
        Set numeros = new TreeSet();

        Random sorteio = new Random();
        //sortear 6 números de 0 até 6 sem repetição
        while (numeros.size() < getGlyphLayers2draw().length - 1) {
            numeros.add(sorteio.nextInt(getGlyphLayers2draw().length));
        }

        return numeros;
    }

    public List<String> defineCamadasGlyph(Set<Integer> camadasSorteadas, boolean profilePresent) {
        List<String> camadasGlyph = new ArrayList();

        Integer[] camadasMisturadas = shuffleArray(camadasSorteadas);
        for (Integer numCamada : camadasMisturadas) {
            Constantes.VAR_VISUAIS_CATEGORICAS varVisual = Constantes.VAR_VISUAIS_CATEGORICAS.values()[numCamada];

            camadasGlyph.add(varVisual.nomeVariavelVisual());
        }
        if (profilePresent) {//adiciona o profile glyph na ultima camada do glyph
            camadasGlyph.add(Constantes.CONTINUOUS_GLYPH_TYPE.PROFILE.getName());
        }
        return camadasGlyph;
    }

    public Glyph montarGlyph(Glyph father, List<String> familia, boolean glyphBase) {
        for (String var : familia) {
            Glyph child = null;
            switch (var) {
                case "Texture":
//                    if (glyphBase) {
                        inputConfigs.put("texture", rand.nextInt(TexturesFactory.TEXTURE.GLYPH_TEXTURAS.values().length));
//                    } else {
                        //vasculhar os valores das var visuais do pai
//                    }
                    child = getGlyphMB().defineTexture(TexturesFactory.TEXTURE.GLYPH_TEXTURAS.values()[getInputConfigs().get("texture")]);
                    child.setNodeTreemap(getItemInput());
                    break;
                case "Color_Hue":
                    inputConfigs.put("colorhue", rand.nextInt(Constantes.getColorHueGlyphs().length));
                    child = getGlyphMB().defineColorHue(Color.decode(Constantes.getColorHueGlyphs()[getInputConfigs().get("colorhue")]));
                    child.setNodeTreemap(getItemInput());
                    break;
                case "Shape":
                    inputConfigs.put("geometricshape", rand.nextInt(GeometryFactory.FORMAS.GLYPH_FORMAS.values().length - 1));
                    child = getGlyphMB().defineShape(GeometryFactory.FORMAS.GLYPH_FORMAS.values()[getInputConfigs().get("geometricshape")]);
                    child.setNodeTreemap(getItemInput());
                    break;
                case "Text":
                    inputConfigs.put("text", rand.nextInt(Constantes.LETRAS_ALFABETO.length));
                    child = getGlyphMB().defineText(Constantes.LETRAS_ALFABETO[getInputConfigs().get("text")]);
                    child.setNodeTreemap(getItemInput());
                    break;
                case "Position":
                    inputConfigs.put("position", rand.nextInt(Constantes.POSICOES.values().length));
                    child = getGlyphMB().definePosition(Constantes.POSICOES.values()[getInputConfigs().get("position")]);
                    child.setNodeTreemap(getItemInput());
                    break;
                case "Orientation":
                    inputConfigs.put("orientation", rand.nextInt(OrientationFactory.ARROW.GLYPH_ORIENTACAO.values().length));
                    child = getGlyphMB().defineOrientation(OrientationFactory.ARROW.GLYPH_ORIENTACAO.values()[getInputConfigs().get("orientation")]);
                    child.setNodeTreemap(getItemInput());
                    break;
                case "Profile":
                    criarDadosSimulados();
                    getGlyphMB().setAtributosEscolhidosGlyphContinuo(atributosEscolhidosGlyphContinuo);
                    child = getGlyphMB().configureProfileGlyph(getItemInput());
                    child.setNodeTreemap(getItemInput());
                    break;
            }

            if (child != null) {
                father.appendChild(child);
            }
        }
        if (father.getBounds() != null) {
            father.setBounds(father.getBounds());
        }
        procurarAreaGlyphFilhos(getItemInput());
        return father;
    }

    public void criarGlyphsBase() {
        for (int i = 0; i < quantGlyphsBase; i++) {
            itemInput = criarTreemapItem(new TreeMapItem(1, 0), true);

            Glyph father = itemInput.getGlyph();
            father.killAllChild();//eh feito um kill para garantir que nao ha filhos

            Set<Integer> camadasSorteadas = sortearCamadas();

            boolean profilePresent = camadasSorteadas.contains(6);//6 eh a camada do profile glyph
            if (profilePresent) {
                camadasSorteadas.remove(6);//remove o profile glyph para adiciona-lo na ultima camada
            }

            montarGlyph(father, defineCamadasGlyph(camadasSorteadas, profilePresent), true);
            listaFamilias.add(getItemInput().getGlyphFamily(father, new ArrayList<>()));
            listaItens.add(getItemInput());
        }
    }

    private List<Glyph> criarItemMaisGlyph(int camadasRemover, int controle) {
        List<Glyph> familia = listaFamilias.get(controle);

        TreeMapItem item = criarTreemapItem(listaItens.get(controle), false);

        Glyph father = item.getGlyph();
        father.killAllChild();//eh feito um kill para garantir que nao ha filhos

        List<String> localFamilia = new ArrayList<>();
        for (int i = 0; i < familia.size() - camadasRemover; i++) {
            localFamilia.add(familia.get(i).toString());
        }

        montarGlyph(father, localFamilia, false);

        return item.getGlyphFamily(father, new ArrayList<>());
    }

    public List<Glyph> getFamily2Draw() {
        contTarefasRealizadas++;
        controleGlyphBase++;
        if (controleGlyphBase > quantGlyphsBase - 1) {
            rodadaRemoveCamada++;
            controleGlyphBase = 0;
        }

        if (rodadaRemoveCamada == glyphLayers2draw.length - 1) {
            rodadaRemoveCamada = 0;
        }

        return criarItemMaisGlyph(rodadaRemoveCamada, controleGlyphBase);
    }

    public List<Glyph> configLayersInput() {
        criarDadosSimulados();
        itemInput = criarTreemapItem(new TreeMapItem(1, 0), true);

        Glyph father = getItemInput().getGlyph();
        father.killAllChild();//eh feito um kill para garantir que nao ha filhos
        //TODO sortear familia e se o profile glyph for sorteado, ele deve ficar sempre na ultima camada.
        layersMisturadas = shuffleArray(Constantes.VAR_VISUAIS_CATEGORICAS.values());
//        listaFamilias.add(layersMisturadas);
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

    /**
     * @deprecated
     */
    public void configLayersOutput() {
        itemOutput = criarTreemapItem(new TreeMapItem(1, 0), true);
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

    public static Integer[] shuffleArray(Set<Integer> original) {
        Integer[] novo = new Integer[original.size()];

        System.arraycopy(original.toArray(), 0, novo, 0, original.size());

        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for (int i = novo.length - 1; i > 0; i--) {
            int indexSorteado = rnd.nextInt(i + 1);
            // Simple swap
            Integer valor = novo[indexSorteado];
            novo[indexSorteado] = novo[i];
            novo[i] = valor;
        }
        return novo;
    }

    /**
     *
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

    /**
     * @return the quantGlyphsBase
     */
    public int getQuantGlyphsBase() {
        return quantGlyphsBase;
    }

    /**
     * @return the glyphLayers2draw
     */
    public int[] getGlyphLayers2draw() {
        return glyphLayers2draw;
    }

    /**
     * @return the contTarefasRealizadas
     */
    public int getContTarefasRealizadas() {
        return contTarefasRealizadas;
    }

    /**
     * @param contTarefasRealizadas the contTarefasRealizadas to set
     */
    public void setContTarefasRealizadas(int contTarefasRealizadas) {
        this.contTarefasRealizadas = contTarefasRealizadas;
    }
}

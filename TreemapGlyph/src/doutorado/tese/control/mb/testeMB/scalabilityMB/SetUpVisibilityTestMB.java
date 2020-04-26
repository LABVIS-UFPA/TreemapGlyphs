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
import doutorado.tese.control.business.visualizations.legenda.LegendaVisualizacao;
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
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JLabel;

/**
 *
 * @author Anderson Soares
 */
public class SetUpVisibilityTestMB {

    private TreeMapItem itemInput = null;
    private TreeMapItem itemOutput = null;
    private HashMap<String, Integer> inputConfigs;
    private HashMap<String, Boolean> outputConfigs;
//    private HashMap<Coluna, String> dadosTreemapItem;
    private HashMap<String, String> gabarito;
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
    /**
     * Essa variavel determina quantos glyphs base serao criados
     */
    private final int quantGlyphsBase = 5;
    private final List<TreeMapItem> listaItensBase;
    private int contTarefasRealizadas = 0;
    private int rodadaRemoveCamada = 0;
    private int controleGlyphBase = -1;
    private int idRadioButtonResposta = -1;
    private List<Glyph> family2Draw;

    public SetUpVisibilityTestMB() {
        areas = new HashMap<>();
//        dadosTreemapItem = new HashMap<>();
        inputConfigs = new HashMap<>();

        rand = new Random(System.currentTimeMillis());

        overlappingActivated = true;
        glyphMB = new GlyphMB();
        glyphMB.configGlyphDesingModel(this.overlappingActivated);

//        listaFamilias = new ArrayList<>();
        listaItensBase = new ArrayList<>();
        colunas = prepararColunas();
        criarGlyphsBase();
    }

    public void criarGlyphsBase() {
        for (int i = 0; i < quantGlyphsBase; i++) {
//            criarDadosSimuladosTreemapItem();
            TreeMapItem item = criarNovoTreemapItem();

            Set<Integer> camadasSorteadas = sortearCamadas();
            boolean profilePresent = camadasSorteadas.contains(6);//6 eh a camada do profile glyph
            if (profilePresent) {
                camadasSorteadas.remove(6);//remove o profile glyph para adiciona-lo na ultima camada
            }

            montarGlyphBase(item, defineCamadasGlyph(camadasSorteadas, profilePresent));
            listaItensBase.add(item);
        }
    }

    public TreeMapItem criarNovoTreemapItem() {
        int length = rand.nextInt(51) + 30;
        int posicaoVetorCorItem = rand.nextInt(2) == 0 ? -1 : rand.nextInt(Constantes.getCorTreemap().length);
        int w = 0;
        int h = 0;
//        while(w == 0 || h == 0){
        while (w <= 30 || h <= 30) {
            w = Math.abs(length - (rand.nextInt(151)));
            h = Math.abs(length - (rand.nextInt(151)));
        }
        Rectangle bounds = new Rectangle(20, 50, w, h);

        TreeMapItem item = new TreeMapItem(1, 0);
        item.setBounds(bounds);
        item.setMapaDetalhesItem(simularDados());

        Glyph glyphConcrete = new GlyphConcrete();
        glyphConcrete.setNodeTreemap(item);

        item.setGlyph(glyphConcrete);
        item.getGlyph().setBounds(item.getBounds());
        item.setColor(posicaoVetorCorItem == -1
                ? Constantes.ALICE_BLUE
                : Color.decode(Constantes.getCorTreemap()[posicaoVetorCorItem]));
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

    public Glyph montarGlyphBase(TreeMapItem item, List<String> familia) {
        Glyph father = item.getGlyph();//father = glyph concreto
        father.killAllChild();//eh feito um kill para garantir que nao ha filhos
        int valor = -1;
        for (String var : familia) {
            Glyph child = null;
            switch (var) {
                case "Texture":
                    valor = rand.nextInt(TexturesFactory.TEXTURE.GLYPH_TEXTURAS.values().length);
                    child = getGlyphMB().defineTexture(TexturesFactory.TEXTURE.GLYPH_TEXTURAS.values()[valor]);
                    child.setNodeTreemap(item);
                    break;
                case "Color_Hue":
                    valor = rand.nextInt(Constantes.getColorHueGlyphs().length);
                    child = getGlyphMB().defineColorHue(Color.decode(Constantes.getColorHueGlyphs()[valor]));
                    child.setNodeTreemap(item);
                    break;
                case "Shape":
                    valor = rand.nextInt(GeometryFactory.FORMAS.GLYPH_FORMAS.values().length);
                    child = getGlyphMB().defineShape(GeometryFactory.FORMAS.GLYPH_FORMAS.values()[valor]);
                    child.setNodeTreemap(item);
                    break;
                case "Text":
                    valor = rand.nextInt(Constantes.LETRAS_ALFABETO.length);
                    child = getGlyphMB().defineText(Constantes.LETRAS_ALFABETO[valor]);
                    child.setNodeTreemap(item);
                    break;
                case "Position":
                    valor = rand.nextInt(Constantes.POSICOES.values().length);
                    child = getGlyphMB().definePosition(Constantes.POSICOES.values()[valor]);
                    child.setNodeTreemap(item);
                    break;
                case "Orientation":
                    valor = rand.nextInt(OrientationFactory.ARROW.GLYPH_ORIENTACAO.values().length);
                    child = getGlyphMB().defineOrientation(OrientationFactory.ARROW.GLYPH_ORIENTACAO.values()[valor]);
                    child.setNodeTreemap(item);
                    break;
                case "Profile":
                    getGlyphMB().setAtributosEscolhidosGlyphContinuo(atributosEscolhidosGlyphContinuo);
                    child = getGlyphMB().configureProfileGlyph(item);
                    child.setNodeTreemap(item);
                    break;
            }

            if (child != null) {
                father.appendChild(child);
            }
        }
        if (father.getBounds() != null) {
            father.setBounds(father.getBounds());
        }
        return father;
    }

    public Glyph remontarGlyph(TreeMapItem item, int camadasRemover) {
        Glyph father = item.getGlyph();
        List<Glyph> familiaOriginal = item.getGlyphFamily(father, new ArrayList<>());
        father.killAllChild();
        for (int i = 0; i < familiaOriginal.size() - camadasRemover; i++) {
            Glyph glyph = familiaOriginal.get(i);
            String var = glyph.toString();
            Glyph child = null;
            switch (var) {
                case "Texture":
                    for (int j = 0; j <= TexturesFactory.TEXTURE.GLYPH_TEXTURAS.values().length - 1; j++) {
                        if (TexturesFactory.TEXTURE.GLYPH_TEXTURAS.values()[j].textureName().equals(glyph.getVarValue())) {
                            inputConfigs.put("texture", j);
                            break;
                        }
                    }
                    child = getGlyphMB().defineTexture(TexturesFactory.TEXTURE.GLYPH_TEXTURAS.values()[getInputConfigs().get("texture")]);
                    child.setNodeTreemap(item);
                    break;
                case "ColorHue":
                    for (int j = 0; j <= Constantes.getColorHueGlyphs().length - 1; j++) {
                        if (Color.decode(Constantes.getColorHueGlyphs()[j]).toString().equals(glyph.getVarValue())) {
                            inputConfigs.put("colorhue", j);
                            break;
                        }
                    }
                    child = getGlyphMB().defineColorHue(Color.decode(Constantes.getColorHueGlyphs()[getInputConfigs().get("colorhue")]));
                    child.setNodeTreemap(item);
                    break;
                case "GeometricShape":
                    for (int j = 0; j <= GeometryFactory.FORMAS.GLYPH_FORMAS.values().length - 1; j++) {
                        if (GeometryFactory.FORMAS.GLYPH_FORMAS.values()[j].shapeName().equals(glyph.getVarValue())) {
                            inputConfigs.put("geometricshape", j);
                            break;
                        }
                    }
                    child = getGlyphMB().defineShape(GeometryFactory.FORMAS.GLYPH_FORMAS.values()[getInputConfigs().get("geometricshape")]);
                    child.setNodeTreemap(item);
                    break;
                case "Text":
                    for (int j = 0; j <= Constantes.LETRAS_ALFABETO.length - 1; j++) {
                        if (Constantes.LETRAS_ALFABETO[j].equals(glyph.getVarValue())) {
                            inputConfigs.put("text", j);
                            break;
                        }
                    }
                    child = getGlyphMB().defineText(Constantes.LETRAS_ALFABETO[getInputConfigs().get("text")]);
                    child.setNodeTreemap(item);
                    break;
                case "Position":
                    for (int j = 0; j <= Constantes.POSICOES.values().length - 1; j++) {
                        if (Constantes.POSICOES.values()[j].getName().equalsIgnoreCase(glyph.getVarValue())) {
                            inputConfigs.put("position", j);
                            break;
                        }
                    }
                    child = getGlyphMB().definePosition(Constantes.POSICOES.values()[getInputConfigs().get("position")]);
                    child.setNodeTreemap(item);
                    break;
                case "Orientation":
                    for (int j = 0; j <= OrientationFactory.ARROW.GLYPH_ORIENTACAO.values().length - 1; j++) {
                        if (OrientationFactory.ARROW.GLYPH_ORIENTACAO.values()[j].nome().equals(glyph.getVarValue())) {
                            inputConfigs.put("orientation", j);
                            break;
                        }
                    }
                    child = getGlyphMB().defineOrientation(OrientationFactory.ARROW.GLYPH_ORIENTACAO.values()[getInputConfigs().get("orientation")]);
                    child.setNodeTreemap(item);
                    break;
                case "ProfileGlyph":
                    getInputConfigs().put("profileglyph", 1);
                    try {
                        child = glyph.clone();
                        if (child != null) {
                            child.setNodeTreemap(item);
                            break;
                        }
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(SetUpVisibilityTestMB.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
            if (child != null) {
                father.appendChild(child);
            }
        }
        if (father.getBounds() != null) {
            father.setBounds(father.getBounds());
        }
        procurarAreaGlyphFilhos(item);
        return father;
    }

    private void atualizarInputConfigs(TreeMapItem item) {
        inputConfigs.put("x", item.getBounds().x);
        inputConfigs.put("y", item.getBounds().y);

        inputConfigs.put("width", item.getBounds().width);
        inputConfigs.put("height", item.getBounds().height);

        for (int i = 0; i < Constantes.getCorTreemap().length; i++) {
            if (Color.decode(Constantes.getCorTreemap()[i]).equals(item.getColor())) {
                inputConfigs.put("coritem", i);
                break;
            } else {
                inputConfigs.put("coritem", -1);
            }
        }
    }

    private TreeMapItem recuperarCaracteristicasItemBase(TreeMapItem item) {
        atualizarInputConfigs(item);
        TreeMapItem newItem = new TreeMapItem(1, 0);
        newItem.setBounds(new Rectangle(
                getInputConfigs().get("x"),
                getInputConfigs().get("y"),
                getInputConfigs().get("width"),
                getInputConfigs().get("height"))
        );
        newItem.setColor(getInputConfigs().get("coritem") == -1
                ? Constantes.ALICE_BLUE
                : Color.decode(Constantes.getCorTreemap()[getInputConfigs().get("coritem")]));
        try {
            Set<Coluna> conjuntoColunas = item.getMapaDetalhesItem().keySet();
            for (Coluna coluna : conjuntoColunas) {
                String dado = item.getMapaDetalhesItem().get(coluna);
                newItem.getMapaDetalhesItem().put(coluna, dado);
            }

            Glyph glyphConcrete = new GlyphConcrete();
            glyphConcrete.setNodeTreemap(newItem);

            item.getGlyph().getChildren().forEach(glyphConcrete::appendChild);

            newItem.setGlyph(glyphConcrete);
            newItem.getGlyph().setBounds(item.getBounds());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return newItem;
    }

    private List<Glyph> criarItemMaisGlyph(int camadasRemover, int controle) {
        itemInput = null;
        itemInput = recuperarCaracteristicasItemBase(listaItensBase.get(controle));
        Glyph father = remontarGlyph(itemInput, camadasRemover);
//        configurarGabarito();
        return itemInput.getGlyphFamily(father, new ArrayList<>());
    }

    public void family2Draw() {
        contTarefasRealizadas++;
        controleGlyphBase++;
        if (controleGlyphBase > quantGlyphsBase - 1) {
            rodadaRemoveCamada++;
            controleGlyphBase = 0;
        }
        if (rodadaRemoveCamada == glyphLayers2draw.length - 1) {
            rodadaRemoveCamada = 0;
        }

        family2Draw = criarItemMaisGlyph(rodadaRemoveCamada, controleGlyphBase);
    }

    public String calculateUserScore(HashMap<String, String> respostasUsuario) {
        int pontuacao = 0;
        if (respostasUsuario.get("Texture").equalsIgnoreCase(getGabarito().get("Texture"))) {
            pontuacao++;
        }
        if (respostasUsuario.get("ColorHue").equalsIgnoreCase(getGabarito().get("ColorHue"))) {
            pontuacao++;
        }
        if (respostasUsuario.get("GeometricShape").equalsIgnoreCase(getGabarito().get("GeometricShape"))) {
            pontuacao++;
        }
        if (respostasUsuario.get("Text").equalsIgnoreCase(getGabarito().get("Text"))) {
            pontuacao++;
        }
        if (respostasUsuario.get("Position").equalsIgnoreCase(getGabarito().get("Position"))) {
            pontuacao++;
        }
        if (respostasUsuario.get("Orientation").equalsIgnoreCase(getGabarito().get("Orientation"))) {
            pontuacao++;
        }
        if (respostasUsuario.get("ProfileGlyph").equalsIgnoreCase(getGabarito().get("ProfileGlyph"))) {
            pontuacao++;
        }
        System.out.println("-----> usuario: " + respostasUsuario.get("ProfileGlyph"));
        System.out.println("=====> gabarito: " + getGabarito().get("ProfileGlyph"));
        return pontuacao + "/" + (glyphLayers2draw.length);
    }
   
    public void createProfileGlyphs(List<JLabel> listaLabels) {
        List<Glyph> children = getItemInput().getGlyphFamily(getItemInput().getGlyph(), new ArrayList<>());
        boolean profileNaFamilia = false;
        for (Glyph glyph : children) {
            if (glyph instanceof ProfileGlyph) {
                profileNaFamilia = true;
                idRadioButtonResposta = rand.nextInt(5);
                Glyph clone = null;
                try {
                    clone = glyph.clone();
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(SetUpVisibilityTestMB.class.getName()).log(Level.SEVERE, null, ex);
                }
                Icon icon = criarIconProfileGlyph(listaLabels.get(idRadioButtonResposta).getBounds(), clone);
                listaLabels.get(idRadioButtonResposta).setIcon(icon);
                idRadioButtonResposta++;//a GUI possui 6 radio buttons e 5 labels, logo: label 0 -> radio 1, label 1 -> radio 2, label 2 -> radio 3 ...
                break;
            }
        }
        if (!profileNaFamilia) {//desenhar profiles aleatorios na GUI
            for (int i = 0; i < listaLabels.size(); i++) {
                Icon icon = criarIconProfileGlyph(listaLabels.get(i).getBounds());
                listaLabels.get(i).setIcon(icon);
            }
        } else {//senao desenhar apenas 4 profiles, pois um eh do gabarido
            for (int i = 0; i < listaLabels.size(); i++) {
                if (i != (idRadioButtonResposta - 1)) {
                    Icon icon = criarIconProfileGlyph(listaLabels.get(i).getBounds());
                    listaLabels.get(i).setIcon(icon);
                }
            }
        }
    }

    public Icon criarIconProfileGlyph(Rectangle bounds, Glyph profile) {
        Rectangle newRectangle = new Rectangle(bounds.x * 11, bounds.y, bounds.width, bounds.height);
        LegendaVisualizacao legendaVisualizacao = new LegendaVisualizacao(newRectangle);
        return legendaVisualizacao.criarLegendaProfileGlyphTest(profile);
    }

    public Icon criarIconProfileGlyph(Rectangle boundsLabel) {
        LegendaVisualizacao legendaVisualizacao = new LegendaVisualizacao(boundsLabel);
        legendaVisualizacao.setAtributosGlyphsContinuos(atributosEscolhidosGlyphContinuo);
        return legendaVisualizacao.criarLegendaProfileGlyphTest();
    }

    /**
     * @return @deprecated
     */
    public List<Glyph> configLayersInput() {
//        criarDadosSimuladosTreemapItem();
        itemInput = criarNovoTreemapItem();

        Glyph father = getItemInput().getGlyph();
        father.killAllChild();//eh feito um kill para garantir que nao ha filhos

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

    /**
     * @deprecated
     */
    public void configLayersOutput() {
        itemOutput = criarNovoTreemapItem();
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

    public Coluna[] prepararColunas() {
        atributosEscolhidosGlyphContinuo = new ArrayList<>();
        try {
            colunas = ManipuladorArquivo.montarColunas();
        } catch (Exception ex) {
            Logger.getLogger(SetUpVisibilityTestMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Coluna coluna : colunas) {
            atributosEscolhidosGlyphContinuo.add(coluna.getName());
        }
        return colunas;
    }

    public HashMap<Coluna, String> simularDados() {
        HashMap<Coluna, String> newMapa = new HashMap<>();
//        List<Coluna> colunas = prepararColunas();
        int valor = 100;
        List<String[]> dadosColunas = new ArrayList<>();
//        for (int i = 0; i < colunas.size(); i++) {
        if (colunas == null) {
            colunas = prepararColunas();
        }
        for (int i = 0; i < colunas.length; i++) {
            String[] simulados = new String[3];
            for (int j = 0; j < simulados.length; j++) {
                simulados[j]
                        = (rand.nextInt(2) == 0)
                        ? "" + (rand.nextInt(valor) * (-1))
                        : "" + rand.nextInt(valor);
            }
            dadosColunas.add(simulados);
        }
//        System.out.println(Arrays.asList(dadosColunas.get(0)));
//        System.out.println(Arrays.asList(dadosColunas.get(1)));
//        System.out.println(Arrays.asList(dadosColunas.get(2)));
        int linha = rand.nextInt(3);//escolhe entre as linhas 0, 1, e 2 
//        System.out.println("linha escolhida: " + linha);
        for (int i = 0; i < dadosColunas.size(); i++) {
            try {
//                newMapa.put(colunas.get(i), dadosColunas.get(i)[linha]);
                newMapa.put(colunas[i], dadosColunas.get(i)[linha]);
                colunas[i].configurarDescricao(dadosColunas.get(i));
            } catch (Exception ex) {
                Logger.getLogger(SetUpVisibilityTestMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return newMapa;
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

        this.inputConfigs.put("coritem", -1);
        this.inputConfigs.put("texture", -1);
        this.inputConfigs.put("colorhue", -1);
        this.inputConfigs.put("geometricshape", -1);
        this.inputConfigs.put("text", -1);
        this.inputConfigs.put("position", -1);
        this.inputConfigs.put("orientation", -1);
        this.inputConfigs.put("profileglyph", -1);
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

    /**
     * @return the family2Draw
     */
    public List<Glyph> getFamily2Draw() {
        return family2Draw;
    }

    /**
     * se no gabarito estiver o valor zero (0) eh porque a variavl visual nao
     * foi sorteada.
     *
     * @return the gabarito
     */
    public HashMap<String, String> getGabarito() {
        return gabarito;
    }

    /**
     * @return the idRadioButtonResposta
     */
    public int getIdRadioButtonResposta() {
        return idRadioButtonResposta;
    }

    /**
     * @param gabarito the gabarito to set
     */
    public void configurarGabarito() {
        this.gabarito = new HashMap<>();
        gabarito.put("Texture", getInputConfigs().get("texture") == -1
                ? Constantes.NAO_IDENTIFICOU_NAO_APRESENTA
                : TexturesFactory.TEXTURE.GLYPH_TEXTURAS.values()[getInputConfigs().get("texture")].textureName());
        gabarito.put("ColorHue", getInputConfigs().get("colorhue") == -1
                ? Constantes.NAO_IDENTIFICOU_NAO_APRESENTA
                : Constantes.getColorHueGlyphs()[getInputConfigs().get("colorhue")]);
        gabarito.put("GeometricShape", getInputConfigs().get("geometricshape") == -1
                ? Constantes.NAO_IDENTIFICOU_NAO_APRESENTA
                : GeometryFactory.FORMAS.GLYPH_FORMAS.values()[getInputConfigs().get("geometricshape")].shapeName());
        gabarito.put("Text", getInputConfigs().get("text") == -1
                ? Constantes.NAO_IDENTIFICOU_NAO_APRESENTA
                : Constantes.LETRAS_ALFABETO[getInputConfigs().get("text")]);
        gabarito.put("Position", getInputConfigs().get("position") == -1
                ? Constantes.NAO_IDENTIFICOU_NAO_APRESENTA
                : Constantes.POSICOES.values()[getInputConfigs().get("position")].getName());
        gabarito.put("Orientation", getInputConfigs().get("orientation") == -1
                ? Constantes.NAO_IDENTIFICOU_NAO_APRESENTA
                : OrientationFactory.ARROW.GLYPH_ORIENTACAO.values()[getInputConfigs().get("orientation")].nome());
        gabarito.put("ProfileGlyph", getIdRadioButtonResposta() == -1
                ? Constantes.NAO_IDENTIFICOU_NAO_APRESENTA
                : getIdRadioButtonResposta() + "");
        idRadioButtonResposta = -1;//reiniciando o valor do radio do profile glyph
    }

}

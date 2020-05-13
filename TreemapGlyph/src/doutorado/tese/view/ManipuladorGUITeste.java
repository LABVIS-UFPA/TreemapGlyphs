/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.view;

import doutorado.tese.util.io.Leitor;
import java.io.File;
import java.util.HashMap;
import net.bouthier.treemapAWT.TMOnDrawFinished;
import net.bouthier.treemapAWT.TMUpdaterConcrete;
import net.bouthier.treemapAWT.TMView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Anderson
 */
public class ManipuladorGUITeste {

    private static final Logger logger = LogManager.getLogger(ManipuladorGUITeste.class);
    private Main mainGUI;
    private TMView view;
    private HashMap<Integer, String> mapaHierarquiasTreemap;
    private HashMap<Integer, String> mapaSizesTreemap;
    private HashMap<Integer, String> mapaCorTreemap;
    private HashMap<Integer, String> mapaGlyphsFamily;
    private HashMap<Integer, String> mapaTexturaGlyphs;
    private HashMap<Integer, String> mapaCorGlyphs;
    private HashMap<Integer, String> mapaFormaGlyphs;
    private HashMap<Integer, String> mapaTextoGlyphs;
    private HashMap<Integer, String> mapaPosicaoGlyphs;
    private HashMap<Integer, String> mapaOrientacaoGlyphs;
    private HashMap<Integer, String> mapaProfileGlyphs;
    private String arquivoHierarquiaTreemapTeste;
    private String arquivoSizeTreemapTeste;
    private String arquivoCorTreemapTeste;
    private String arquivoGlyphsConfigTeste;

    public ManipuladorGUITeste() {
        mapaHierarquiasTreemap = new HashMap<>();
        mapaSizesTreemap = new HashMap<>();
        mapaCorTreemap = new HashMap<>();
        mapaGlyphsFamily = new HashMap<>();
        mapaTexturaGlyphs = new HashMap<>();
        mapaCorGlyphs = new HashMap<>();
        mapaFormaGlyphs = new HashMap<>();
        mapaTextoGlyphs = new HashMap<>();
        mapaPosicaoGlyphs = new HashMap<>();
        mapaOrientacaoGlyphs = new HashMap<>();
        mapaProfileGlyphs = new HashMap<>();
        arquivoHierarquiaTreemapTeste = "setupTests" + File.separator + "treemapHierarquiasTeste.tsv";
        arquivoSizeTreemapTeste = "setupTests" + File.separator + "treemapSizesTeste.tsv";
        arquivoCorTreemapTeste = "setupTests" + File.separator + "treemapCoresTeste.tsv";
        arquivoGlyphsConfigTeste = "setupTests" + File.separator + "glyphs_setup_Teste.tsv";
    }

    public File carregarBaseDados() {
        //Se precisar carregar outra base de dados, pensar em como chamar: limparResquiciosBasesAnteriores()
        //tarefa 1
        String fileName = "database" + File.separator + "treemap_glyphs_base_CLIMA_1000_datagen.tsv";
        File file = new File(fileName);
        return file;
    }

    public void carregarAtributosTreemapTreinamento(int contQuestaoTreinamento) {
        //tarefa 1

    }

    public void usarGlyphCategorico() {
//        callListner();
        mainGUI.marcarCheckGlyphCategorico(true);  
    }   
    
    public void carregarAtributosTreemapTeste(int contQuestaoTeste) {
        String[] listaHierarquias = getMapaHiearquiasTreemap().get(contQuestaoTeste).split(";");
        mainGUI.carregarHierarquiasTreemapTeste(listaHierarquias);

        String sizeAttribute = getMapaSizesTreemap().get(contQuestaoTeste);
        mainGUI.carregarSizeTreemapTeste(sizeAttribute);

        String corTreemap = getMapaCorTreemap().get(contQuestaoTeste);
        mainGUI.carregarCorTreemapteste(corTreemap);

        mainGUI.simularCliqueBotaoTreemap();
        setView(mainGUI.getVisualizationTreemap().getView());
    }
    
    public void carregarAtributosVarVisuais(int contQuestaoTeste){
        String[] familia = getMapaGlyphsFamily().get(contQuestaoTeste).split(";");
        mainGUI.carregarFamiliaGlyph(familia);
        
        //fazer a chamada dos atributos aqui para as var visuais
    }

    public void carregarGlyphsConfig(){
        Leitor.lerArquivo(new File(getArquivoGlyphsConfigTeste()));
        String[] linhas = Leitor.getLinhas();

        for (int i = 1; i < linhas.length; i++) {
            String[] colunas = linhas[i].split("\t");
            getMapaGlyphsFamily().put(i, colunas[1]);
            getMapaTexturaGlyphs().put(i, colunas[2]);
            getMapaCorGlyphs().put(i, colunas[3]);
            getMapaFormaGlyphs().put(i, colunas[4]);
            getMapaTextoGlyphs().put(i, colunas[5]);
            getMapaPosicaoGlyphs().put(i, colunas[6]);
            getMapaOrientacaoGlyphs().put(i, colunas[7]);
            getMapaProfileGlyphs().put(i, colunas[8]);
        }
    }
    
    public void carregarHierarquiasTreemap() {
        Leitor.lerArquivo(new File(getArquivoHierarquiaTreemapTeste()));
        String[] linhas = Leitor.getLinhas();

        for (int i = 1; i < linhas.length; i++) {
            String[] colunas = linhas[i].split("\t");
            getMapaHiearquiasTreemap().put(i, colunas[1]);
        }
    }

    public void carregarSizesTreemap() {
        Leitor.lerArquivo(new File(getArquivoSizeTreemapTeste()));
        String[] linhas = Leitor.getLinhas();

        for (int i = 1; i < linhas.length; i++) {
            String[] colunas = linhas[i].split("\t");
            getMapaSizesTreemap().put(i, colunas[1]);
        }
    }

    public void carregarCoresTreemap() {
        Leitor.lerArquivo(new File(getArquivoCorTreemapTeste()));
        String[] linhas = Leitor.getLinhas();

        for (int i = 1; i < linhas.length; i++) {
            String[] colunas = linhas[i].split("\t");
            getMapaCorTreemap().put(i, colunas[1]);
        }
    }

    /**
     * @return the mainGUI
     */
    public Main getMainGUI() {
        return mainGUI;
    }

    /**
     * @param mainGUI the mainGUI to set
     */
    public void setMainGUI(Main mainGUI) {
        this.mainGUI = mainGUI;
    }

    public String getArquivoHierarquiaTreemapTeste() {
        return this.arquivoHierarquiaTreemapTeste;
    }

    public void setArquivoHierarquiaTreemapTeste(String arquivoHierarquiaTreemapTeste) {
        this.arquivoHierarquiaTreemapTeste = arquivoHierarquiaTreemapTeste;
    }

    public HashMap<Integer, String> getMapaHiearquiasTreemap() {
        return this.mapaHierarquiasTreemap;
    }

    public void setMapaHierarquiasTreemap(HashMap<Integer, String> mapaHierarquiasTreemap) {
        this.mapaHierarquiasTreemap = mapaHierarquiasTreemap;
    }

    /**
     * @return the arquivoSizeTreemapTeste
     */
    public String getArquivoSizeTreemapTeste() {
        return arquivoSizeTreemapTeste;
    }

    /**
     * @param arquivoSizeTreemapTeste the arquivoSizeTreemapTeste to set
     */
    public void setArquivoSizeTreemapTeste(String arquivoSizeTreemapTeste) {
        this.arquivoSizeTreemapTeste = arquivoSizeTreemapTeste;
    }

    /**
     * @return the mapaSizesTreemap
     */
    public HashMap<Integer, String> getMapaSizesTreemap() {
        return mapaSizesTreemap;
    }

    /**
     * @param mapaSizesTreemap the mapaSizesTreemap to set
     */
    public void setMapaSizesTreemap(HashMap<Integer, String> mapaSizesTreemap) {
        this.mapaSizesTreemap = mapaSizesTreemap;
    }

    /**
     * @return the mapaCorTreemap
     */
    public HashMap<Integer, String> getMapaCorTreemap() {
        return mapaCorTreemap;
    }

    /**
     * @param mapaCorTreemap the mapaCorTreemap to set
     */
    public void setMapaCorTreemap(HashMap<Integer, String> mapaCorTreemap) {
        this.mapaCorTreemap = mapaCorTreemap;
    }

    /**
     * @return the arquivoCorTreemapTeste
     */
    public String getArquivoCorTreemapTeste() {
        return arquivoCorTreemapTeste;
    }

    /**
     * @param arquivoCorTreemapTeste the arquivoCorTreemapTeste to set
     */
    public void setArquivoCorTreemapTeste(String arquivoCorTreemapTeste) {
        this.arquivoCorTreemapTeste = arquivoCorTreemapTeste;
    }

    /**
     * @return the view
     */
    public TMView getView() {
        return view;
    }

    /**
     * @param view the view to set
     */
    public void setView(TMView view) {
        this.view = view;
    }

    /**
     * @return the arquivoGlyphsConfigTeste
     */
    public String getArquivoGlyphsConfigTeste() {
        return arquivoGlyphsConfigTeste;
    }

    /**
     * @param arquivoGlyphsConfigTeste the arquivoGlyphsConfigTeste to set
     */
    public void setArquivoGlyphsConfigTeste(String arquivoGlyphsConfigTeste) {
        this.arquivoGlyphsConfigTeste = arquivoGlyphsConfigTeste;
    }

    /**
     * @return the mapaGlyphsFamily
     */
    public HashMap<Integer, String> getMapaGlyphsFamily() {
        return mapaGlyphsFamily;
    }

    /**
     * @param mapaGlyphsFamily the mapaGlyphsFamily to set
     */
    public void setMapaGlyphsFamily(HashMap<Integer, String> mapaGlyphsFamily) {
        this.mapaGlyphsFamily = mapaGlyphsFamily;
    }

    /**
     * @return the mapaTexturaGlyphs
     */
    public HashMap<Integer, String> getMapaTexturaGlyphs() {
        return mapaTexturaGlyphs;
    }

    /**
     * @param mapaTexturaGlyphs the mapaTexturaGlyphs to set
     */
    public void setMapaTexturaGlyphs(HashMap<Integer, String> mapaTexturaGlyphs) {
        this.mapaTexturaGlyphs = mapaTexturaGlyphs;
    }

    /**
     * @return the mapaCorGlyphs
     */
    public HashMap<Integer, String> getMapaCorGlyphs() {
        return mapaCorGlyphs;
    }

    /**
     * @param mapaCorGlyphs the mapaCorGlyphs to set
     */
    public void setMapaCorGlyphs(HashMap<Integer, String> mapaCorGlyphs) {
        this.mapaCorGlyphs = mapaCorGlyphs;
    }

    /**
     * @return the mapaFormaGlyphs
     */
    public HashMap<Integer, String> getMapaFormaGlyphs() {
        return mapaFormaGlyphs;
    }

    /**
     * @param mapaFormaGlyphs the mapaFormaGlyphs to set
     */
    public void setMapaFormaGlyphs(HashMap<Integer, String> mapaFormaGlyphs) {
        this.mapaFormaGlyphs = mapaFormaGlyphs;
    }

    /**
     * @return the mapaTextoGlyphs
     */
    public HashMap<Integer, String> getMapaTextoGlyphs() {
        return mapaTextoGlyphs;
    }

    /**
     * @param mapaTextoGlyphs the mapaTextoGlyphs to set
     */
    public void setMapaTextoGlyphs(HashMap<Integer, String> mapaTextoGlyphs) {
        this.mapaTextoGlyphs = mapaTextoGlyphs;
    }

    /**
     * @return the mapaPosicaoGlyphs
     */
    public HashMap<Integer, String> getMapaPosicaoGlyphs() {
        return mapaPosicaoGlyphs;
    }

    /**
     * @param mapaPosicaoGlyphs the mapaPosicaoGlyphs to set
     */
    public void setMapaPosicaoGlyphs(HashMap<Integer, String> mapaPosicaoGlyphs) {
        this.mapaPosicaoGlyphs = mapaPosicaoGlyphs;
    }

    /**
     * @return the mapaOrientacaoGlyphs
     */
    public HashMap<Integer, String> getMapaOrientacaoGlyphs() {
        return mapaOrientacaoGlyphs;
    }

    /**
     * @param mapaOrientacaoGlyphs the mapaOrientacaoGlyphs to set
     */
    public void setMapaOrientacaoGlyphs(HashMap<Integer, String> mapaOrientacaoGlyphs) {
        this.mapaOrientacaoGlyphs = mapaOrientacaoGlyphs;
    }

    /**
     * @return the mapaProfileGlyphs
     */
    public HashMap<Integer, String> getMapaProfileGlyphs() {
        return mapaProfileGlyphs;
    }

    /**
     * @param mapaProfileGlyphs the mapaProfileGlyphs to set
     */
    public void setMapaProfileGlyphs(HashMap<Integer, String> mapaProfileGlyphs) {
        this.mapaProfileGlyphs = mapaProfileGlyphs;
    }
}

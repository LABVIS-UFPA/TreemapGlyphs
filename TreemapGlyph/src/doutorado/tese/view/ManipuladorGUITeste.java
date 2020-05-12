/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.view;

import doutorado.tese.util.io.Leitor;
import java.io.File;
import java.util.HashMap;

/**
 *
 * @author Anderson
 */
public class ManipuladorGUITeste {
    
    private Main mainGUI;
    private String arquivoHierarquiaTreemapTeste;
    private HashMap<Integer, String> mapaHierarquiasTreemap;

    public ManipuladorGUITeste() {
        mapaHierarquiasTreemap = new HashMap<>();
        arquivoHierarquiaTreemapTeste = "setupTests" + File.separator + "treemapHierarquiasTeste.tsv";
    }

    public File carregarBaseDados() {
        //Se precisar carregar outra base de dados, pensar em como chamar: limparResquiciosBasesAnteriores()
        //tarefa 1
        String fileName = "database" + File.separator + "treemap_glyphs_base_CLIMA_1000_datagen.tsv";
        File file = new File(fileName);
        return file;
    }
       
    public void carregarAtributosTreemapTreinamento(int contQuestaoTreinamento){
        //tarefa 1
        
    }
    
    public void carregarAtributosTreemapTeste(int contQuestaoTeste){        
        String[] listaHierarquias = getMapaHiearquiasTreemap().get(contQuestaoTeste).split(";");
        mainGUI.carregarHierarquiasTreemapTeste(listaHierarquias);
        //carregar size
        //carregar cor
        //chamar evento do botao
    }
    
    public void carregarHierarquiasTreemap(){
        Leitor.lerArquivo(new File(getArquivoHierarquiaTreemapTeste()));
        String[] linhas = Leitor.getLinhas();
        
        for (int i = 1; i < linhas.length; i++) {
            String[] colunas = linhas[i].split("\t");
            getMapaHiearquiasTreemap().put(i, colunas[1]);
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
    
    public String getArquivoHierarquiaTreemapTeste(){
        return this.arquivoHierarquiaTreemapTeste;
    }
    
    public void setArquivoHierarquiaTreemapTeste(String arquivoHierarquiaTreemapTeste){
        this.arquivoHierarquiaTreemapTeste = arquivoHierarquiaTreemapTeste;
    }

    public HashMap<Integer, String> getMapaHiearquiasTreemap() {
        return this.mapaHierarquiasTreemap;
    }
    
    public void setMapaHierarquiasTreemap(HashMap<Integer, String> mapaHierarquiasTreemap){
        this.mapaHierarquiasTreemap = mapaHierarquiasTreemap;
    }
}

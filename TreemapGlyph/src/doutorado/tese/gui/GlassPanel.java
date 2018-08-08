/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.gui;

import doutorado.tese.io.ManipuladorArquivo;
import doutorado.tese.visualizacao.glyph.GlyphManager;
import doutorado.tese.visualizacao.treemap.TreeMapItem;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import net.bouthier.treemapAWT.TMNodeModelRoot;
import net.bouthier.treemapAWT.TMOnDrawFinished;
import net.bouthier.treemapAWT.TMUpdaterConcrete;
import net.bouthier.treemapAWT.TMView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Anderson Soares
 */
public class GlassPanel extends JPanel {

    private static final Logger logger = LogManager.getLogger(GlassPanel.class);
    private ManipuladorArquivo manipulador;
    private GlyphManager glyphManager;
    private TMNodeModelRoot nodeModelRoot;
    private TMView view;
    private boolean decisioTree;
    private String[] variaveisVisuaisEscolhidas;
    private ArrayList<TreeMapItem> gabarito;
    private float quantOlverlap;
    private int quantValoresVarVisuais;
    private boolean overlappingActivated;
    private String[] atributosBaseEscolhidos;

    /**
     * Construtor chamado ao selecionar o checkbox indicando que os glyphs serao
     * usados.
     */
    public GlassPanel() {
        setOpaque(false);
        setLayout(new GroupLayout(this));
        callListner();
//        glyphManager.setPerctOverlap(quantOlverlap);
        quantValoresVarVisuais = 8;
    }

    public GlassPanel(Rectangle bounds) {
        setOpaque(false);
        setBounds(bounds);
        setLayout(new GroupLayout(this));
        callListner();
//        glyphManager.setPerctOverlap(quantOlverlap);
        quantValoresVarVisuais = 5;
    }

    private void callListner() {
        TMUpdaterConcrete.listeners.add(new TMOnDrawFinished() {
            @Override
            public void onDrawFinished(String text) {
                logger.info("Acionando onDrawFinished()", text);
'
        glyphManager.setRootNodeZoom(view.getRootAnderson());
                logger.info("Acionando prepare2Draw() a partir do onDrawFinished() - Root: " + glyphManager.getRootNodeZoom());
                glyphManager.prepare2Draw();//chamado para redesenhar os glyphs no drill-down
            }
        });
    }
    
    public void setAtributosBaseEscolhidos(String[] atributosBaseEscolhidos) {
        this.atributosBaseEscolhidos = atributosBaseEscolhidos;
    }
/**
 * usado apenas no starglyph
 * @param atributosEscolhidos 
 */
//    public void setAtributosContinuos(List<Object> atributosEscolhidos) {
//        glyphManager = new GlyphManager(getManipulador(), atributosEscolhidos, view.getBounds());
//        glyphManager.setUseDecisionTree(decisioTree);
//        glyphManager.setRootNodeZoom(view.getRootAnderson());
//        setGlyphOverlappingModel(true);
//        logger.info("Acionando prepare2Draw() a partir do setAtributosEscolhidos() - Root: " + glyphManager.getRootNodeZoom());
//        //Aqui prepara para desenhar os glyphs da primeira versao
////        glyphManager.prepare2Draw();
//        //Aqui prepara para desenhar os glyphs da nova versao
//        setCofigItensGrid();
//     }

    public void setAtributosEscolhidos(List<Object> atributosEscolhidos) {
        glyphManager = new GlyphManager(getManipulador(), atributosEscolhidos, view.getBounds());
        glyphManager.setUseDecisionTree(decisioTree);
        glyphManager.setRootNodeZoom(view.getRootAnderson());
        setGlyphOverlappingModel(true);
        logger.info("Acionando prepare2Draw() a partir do setAtributosEscolhidos() - Root: " + glyphManager.getRootNodeZoom());
        //Aqui prepara para desenhar os glyphs da primeira versao
//        glyphManager.prepare2Draw();
        //Aqui prepara para desenhar os glyphs da nova versao
        setCofigItensGrid();
    }

    /**
     * Metodo responsavel por configurar os glyphs do Treemap. Varre o vetor de
     * Itens do Treemap, setando para cada obj TreeMapItem seu respectivo valor
     * de overlapping e chama a funcao que configura as layers dos glyphs. Por
     * fim, verifica se o item possui um glyph com um de seus filhos sendo a
     * resposta de uma pergunta, caso sim, o item é adicionado ao gabarito um
     * ArrayList de TreeMapItem.
     *
     * @return retorna o gabarito
     */
    public ArrayList<TreeMapItem> setCofigItensGrid() {
        gabarito = new ArrayList();
        glyphManager.setVariaveisVisuaisEscolhidas(getVariaveisVisuaisEscolhidas());

        for (TreeMapItem itemTreemap : getManipulador().getItensTreemap()) {
            glyphManager.setQuantValoresVarVisuais(quantValoresVarVisuais);
//            glyphManager.setPerctOverlap(quantOlverlap);
            glyphManager.setAtributosBaseEscolhidos(atributosBaseEscolhidos);
            glyphManager.configLayers(itemTreemap);
            
            if (itemTreemap.isPossuiGlyphResposta()) {
                getGabarito().add(itemTreemap);
            }
        }
        return getGabarito();
    }
    
    public void setGlyphOverlappingModel(boolean overlappingActivated) {
        this.overlappingActivated = overlappingActivated;
        glyphManager.configGlyphDesingModel(this.overlappingActivated);
    }
    
    public boolean getGlyphOverlappingModel() {
        return overlappingActivated;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0, 255, 0, 0));//painel com fundo transparente
        Rectangle r = getBounds();
        g.fillRect(r.x, r.y, r.width, r.height);

        glyphManager.paint(g);
        g.dispose();
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

    public void setTMView(TMView view) {
        this.view = view;
    }

    void setUseDecisionTree(boolean decisioTree) {
        this.decisioTree = decisioTree;
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

    /**
     * @return the gabarito
     */
    public ArrayList<TreeMapItem> getGabarito() {
        return gabarito;
    }

    /**
     * @param gabarito the gabarito to set
     */
    public void setGabarito(ArrayList<TreeMapItem> gabarito) {
        this.gabarito = gabarito;
    }
}

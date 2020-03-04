/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.view;

import doutorado.tese.dao.ManipuladorArquivo;
import doutorado.tese.control.mb.GlyphMB;
import doutorado.tese.control.mb.SetUpMB;
import doutorado.tese.model.TreeMapItem;
import doutorado.tese.util.Constantes;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
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
    private GlyphMB glyphMB;
//    private TMNodeModelRoot nodeModelRoot;
    private TMView view;
//    private boolean starGlyphActivated;
    private String[] variaveisVisuaisEscolhidas;
    private String glyphContinuoEscolhido;
    private ArrayList<TreeMapItem> gabarito;
//    private float quantOlverlap;
    private int quantValoresVarVisuais;
    private boolean overlappingActivated;
    private String[] atributosEscolhidosContinuousGlyph;

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
//        this.addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent evt) {
//                mouseClicando();
//            }
//        });

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
                if (getGlyphMB() != null && view.getRootAnderson() != null) {
                    getGlyphMB().setRootNodeZoom(view.getRootAnderson());
                    logger.info("Acionando prepare2Draw() a partir do onDrawFinished() - Root: " + getGlyphMB().getRootNodeZoom());
                    getGlyphMB().prepare2Draw();//chamado para redesenhar os glyphs no drill-down
                } else {
//                    System.err.println("glyphManager == null ou view.getRootAnderson() == null");
                }
            }
        });
    }

    public void setAtributosEscolhidosContinuousGlyph(String[] atributosEscolhidosStarGlyph) {
        this.atributosEscolhidosContinuousGlyph = atributosEscolhidosStarGlyph;
    }
    
    public void verificaGlyphMB(HashMap<Constantes.VAR_VISUAIS_CATEGORICAS, Object> atributosEscolhidos){
        if (getGlyphMB() == null) {
            setGlyphMB(new GlyphMB(atributosEscolhidos, view.getBounds()));
        }else{
            getGlyphMB().setAtributosCategoricosEscolhidos(atributosEscolhidos);
            getGlyphMB().setBounds(view.getBounds());
            getGlyphMB().analisarAtributosEscolhidos();
        }
    }

    public void setAtributosEscolhidos(HashMap<Constantes.VAR_VISUAIS_CATEGORICAS, Object> atributosEscolhidos) {
        verificaGlyphMB(atributosEscolhidos);
        getGlyphMB().setRootNodeZoom(view.getRootAnderson());
        if (Constantes.CONTINUOUS_GLYPH_ACTIVATED) {
            if (atributosEscolhidosContinuousGlyph != null) {
                getGlyphMB().setAtributosEscolhidosGlyphContinuo(Arrays.asList(atributosEscolhidosContinuousGlyph));
            }
        }
        setGlyphOverlappingModel(true);
        logger.info("Acionando setCofigItensGrid() a partir do setAtributosEscolhidos() - Root: " + getGlyphMB().getRootNodeZoom());

        //Aqui prepara para desenhar os glyphs da nova versao
        setCofigItensTreemap();
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
    public ArrayList<TreeMapItem> setCofigItensTreemap() {
        gabarito = new ArrayList();
        getGlyphMB().setGlyphContinuoEscolhido(getGlyphContinuoEscolhido());
        getGlyphMB().setVariaveisVisuaisEscolhidas(getVariaveisVisuaisEscolhidas());
//        glyphManager.setQuantValoresVarVisuais(quantValoresVarVisuais);

        for (TreeMapItem itemTreemap : SetUpMB.getItensTreemap()) {
//            glyphManager.setPerctOverlap(quantOlverlap);
            getGlyphMB().configLayers(itemTreemap);

            if (itemTreemap.isPossuiGlyphResposta()) {
                getGabarito().add(itemTreemap);
            }
        }
        return getGabarito();
    }

    public void setGlyphOverlappingModel(boolean overlappingActivated) {
        this.overlappingActivated = overlappingActivated;
        getGlyphMB().configGlyphDesingModel(this.overlappingActivated);
    }

    public boolean getGlyphOverlappingModel() {
        return overlappingActivated;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        this.graphicsGlobal = g;
        g.setColor(new Color(0, 255, 0, 0));//painel com fundo transparente
        Rectangle r = getBounds();
        g.fillRect(r.x, r.y, r.width, r.height);
        if (getGlyphMB() != null) {
            synchronized (getGlyphMB()) {
                getGlyphMB().paint(g);
            }
        }
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

    public String getGlyphContinuoEscolhido() {
        return glyphContinuoEscolhido;
    }

    public void setTipoGlyphContinuoEscolhido(String glyphContinuoEscolhido) {
        this.glyphContinuoEscolhido = glyphContinuoEscolhido;
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

    /**
     * @return the glyphMB
     */
    public GlyphMB getGlyphMB() {
        return glyphMB;
    }

    /**
     * @param glyphMB the glyphMB to set
     */
    public void setGlyphMB(GlyphMB glyphMB) {
        this.glyphMB = glyphMB;
    }

}

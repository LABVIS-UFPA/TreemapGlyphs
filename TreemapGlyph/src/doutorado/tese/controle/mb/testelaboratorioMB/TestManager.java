/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.controle.mb.testelaboratorioMB;

import doutorado.tese.controle.negocio.testelaboratorioNegocio.AmbienteTestes;
import doutorado.tese.controle.negocio.testelaboratorioNegocio.TarefaTestes;
import doutorado.tese.controle.negocio.visualizacao.legenda.LegendaVisualizacao;
import doutorado.tese.dao.ManipuladorArquivo;
import doutorado.tese.util.io.Escritor;
import doutorado.tese.util.io.Leitor;
import doutorado.tese.visao.GlassPanel;
import doutorado.tese.visao.VisualizationsArea;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.BoxLayout;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import net.bouthier.treemapAWT.TMView;

/**
 *
 * @author Anderson Soares
 */
public class TestManager {

    private String menuItem;
    private JPanel painelEsquerda;
    private JPanel painelLegendaVis;
    private ManipuladorArquivo manipulador;
    private JLayeredPane layerPane;
    private JTextPane task_TextArea;
    private int idTarefaAtual = 0;
    private LegendaVisualizacao legendaVisualizacao;
    private GlassPanel glyphPanel;
    private TMView view;
    private String itemCor;
    private int totalTarefas;
    private ArrayList<Object> atributosEscolhidosGlyph;
    private String[] linhaResposta; 
    private String[] linhaLog;
    
    
    public TestManager(String menuItem, JPanel painelEsquerda, ManipuladorArquivo manipulador, JTextPane task_TextArea, JPanel painelLegendaVis) {
        this.menuItem = menuItem;
        this.painelEsquerda = painelEsquerda;
        this.manipulador = manipulador;
        this.task_TextArea = task_TextArea;
        this.painelLegendaVis = painelLegendaVis;
        legendaVisualizacao = new LegendaVisualizacao(painelLegendaVis.getBounds());
        atributosEscolhidosGlyph = new ArrayList<>();
        layerPane = new JLayeredPane();
        
        
    }
    
    public void direcionarAmbienteTeste() {
        switch (menuItem) {
            case "A":
                System.out.println("Carregar Ambiente A");
                AmbienteTestes ambiente = new AmbienteTestes();
                ambiente.verificarTipoAmbiente(ambiente.AMBIENTE_A);
//              ambiente.getTarefasCat();
//              ambiente.getTarefasConti();
                carregarAmbienteA(ambiente);
                break;
            case "B":
                System.out.println("Carregar Ambiente B");
                AmbienteTestes ambienteB = new AmbienteTestes();
                ambienteB.verificarTipoAmbiente(ambienteB.AMBIENTE_B);
                break;
            case "C":
                System.out.println("Carregar Ambiente C");
                AmbienteTestes ambienteC = new AmbienteTestes();
                ambienteC.verificarTipoAmbiente(ambienteC.AMBIENTE_C);
                break;
            case "D":
                System.out.println("Carregar Ambiente D");
                AmbienteTestes ambienteD = new AmbienteTestes();
                ambienteD.verificarTipoAmbiente(ambienteD.AMBIENTE_D);
                break;
            case "E":
                System.out.println("Carregar Ambiente E");
                AmbienteTestes ambienteE = new AmbienteTestes();
                ambienteE.verificarTipoAmbiente(ambienteE.AMBIENTE_E);
                break;
        }
    }

     public void nextStep() {
        //bufferLog.append(linha).append("\n");
        //Escritor.escreverArquivo("log_GRID_Glyphs", bufferLog.toString());
        String headerLog = "ID_USER, ID_CONJ_VAR_VIS, ID_VAR_VIS, HAS_OVERLAP, ID_OVERLAP, ID_GRID, AREA_ITEM, TIPO_TAREFA, ACURACIA, TEMP_RES, RESPOSTA_CERTA, RESPOSTA_USER, CONJ_VAR_VIS";
        Escritor.escreverArquivo("log_GRID_Glyphs", headerLog);
           
     }   
    /**
     * @return the menuItem
     */
    public String getMenuItem() {
        return menuItem;
    }

    /**
     * @param menuItem the menuItem to set
     */
    public void setMenuItem(String menuItem) {
        this.menuItem = menuItem;
    }

    private void limparPainelEsquerda() {
        painelEsquerda.removeAll();
        painelEsquerda.repaint();
    }

    private void carregarAmbienteA(AmbienteTestes ambiente) {   
        TarefaTestes[] tarefasCat = ambiente.getTarefasCat();
        TarefaTestes[] tarefasConti = ambiente.getTarefasConti();

        System.out.println("Tarefa " + idTarefaAtual);
        if (totalTarefas <= 12) {
            if (idTarefaAtual < tarefasCat.length) {
                TarefaTestes tarefa = tarefasCat[idTarefaAtual];
                task_TextArea.setText(tarefa.getTextoTarefa());
                setupTreemap(tarefa, () -> {
                    setupCategoricalGlyph(tarefa);
                });
                idTarefaAtual++;
            } else {
                TarefaTestes tarefa = tarefasConti[idTarefaAtual];
                task_TextArea.setText(tarefa.getTextoTarefa());
                setupTreemap(tarefa, () -> {
                    setupContinuousGlyph(tarefa);
                });
            }
            totalTarefas++;
        }
    }

    public void setupCategoricalGlyph(TarefaTestes tarefa) {
        glyphPanel = new GlassPanel();
        glyphPanel.setTMView(view);
        layerPane.add(glyphPanel, new Integer(1), 0);

        String[] variaveisVisuaisEscolhidas = new String[]{"Texture", "Color", "Shape", "Letter"};
        glyphPanel.setManipulador(manipulador);
        glyphPanel.setVariaveisVisuaisEscolhidas(variaveisVisuaisEscolhidas);
        //Acoes para desenhar os glyphs
        glyphPanel.setBounds(painelEsquerda.getBounds());
        glyphPanel.setUseDecisionTree(false);
        atributosEscolhidosGlyph = getAtributosEscolhidosGlyph(tarefa);
        glyphPanel.setAtributosEscolhidos(atributosEscolhidosGlyph);
        glyphPanel.setVisible(true);
        glyphPanel.repaint();

        atualizarLegendaGlyphs(atributosEscolhidosGlyph);
    }
    
    public void setupContinuousGlyph(TarefaTestes tarefa){
        glyphPanel.setManipulador(manipulador);
        glyphPanel.setContinuousGlyphActivated(true);

        glyphPanel.setGlyphContinuoEscolhido("Bar");
        String[] variaveisVisuaisEscolhidas = new String[]{"Texture", "Color", "Shape"};
        glyphPanel.setVariaveisVisuaisEscolhidas(variaveisVisuaisEscolhidas);
        ArrayList<Object> atributosEscolhidosContinuousGlyph = getAtributosEscolhidosContinuousGlyph(tarefa);
        glyphPanel.setAtributosEscolhidosContinuousGlyph(atributosEscolhidosContinuousGlyph.toArray(new String[atributosEscolhidosContinuousGlyph.size()]));

        //Acoes para desenhar os glyphs
        glyphPanel.setBounds(painelEsquerda.getBounds());
        glyphPanel.setUseDecisionTree(false);
        atributosEscolhidosGlyph = getAtributosEscolhidosGlyph(tarefa);
        glyphPanel.setAtributosEscolhidos(atributosEscolhidosGlyph);
        glyphPanel.setVisible(true);
        glyphPanel.repaint();

        atualizarLegendaGlyphs(atributosEscolhidosGlyph);
        atualizarLegendaGlyphsContinuos(atributosEscolhidosContinuousGlyph.toArray(new String[atributosEscolhidosContinuousGlyph.size()]));
    }

    private ArrayList<Object> getAtributosEscolhidosGlyph(TarefaTestes tarefa) {
        ArrayList<Object> atributosEscolhidosGlyph = new ArrayList<>();
        atributosEscolhidosGlyph.add(tarefa.getParametroTexturaGlyph());
        atributosEscolhidosGlyph.add(tarefa.getParametroCorGlyph());
        atributosEscolhidosGlyph.add(tarefa.getParametroFormaGlyph());
        atributosEscolhidosGlyph.add(tarefa.getParametroLetraGlyph());

        return atributosEscolhidosGlyph;
    }
    
    private ArrayList<Object> getAtributosEscolhidosContinuousGlyph(TarefaTestes tarefa) {
        ArrayList<Object> atributosGlyph = new ArrayList<>();
        atributosGlyph.add(tarefa.getParametroBar1());
        atributosGlyph.add(tarefa.getParametroBar2());
        atributosGlyph.add(tarefa.getParametroBar3());

        return atributosGlyph;
    }

    public void setupTreemap(TarefaTestes tarefa, FinishedSetupCallBack callback) {
        limparPainelEsquerda();
        String tamanho = tarefa.getParametroTamanhoTreemap();
        String rotulo = tarefa.getParametroRotuloTreemap() == null ? "" : tarefa.getParametroRotuloTreemap();
        itemCor = tarefa.getParametroCorTreemap() == null ? "---" : tarefa.getParametroCorTreemap();
        String[] hierarquia = (tarefa.getParametrosHierarquiaTreemap().isEmpty())
                ? new String[]{}
                : tarefa.getParametrosHierarquiaTreemap().toArray(new String[tarefa.getParametrosHierarquiaTreemap().size()]);

        VisualizationsArea visualizationTreemap = new VisualizationsArea(painelEsquerda.getWidth(), painelEsquerda.getHeight(), manipulador,
                tamanho, hierarquia, rotulo, itemCor,
                tarefa.getParametrosDetalhesSobDemanda().toArray(new String[tarefa.getParametrosDetalhesSobDemanda().size()]),
                callback);
      
        
        painelEsquerda.add(layerPane);
        view = visualizationTreemap.getView();
        layerPane.setBounds(view.getBounds());
        layerPane.add(view, new Integer(0), 0);

        painelEsquerda.repaint();
        atualizarLegendaTreemap(itemCor);

        limparCacheGlyphs();
    }
    
    public void atualizarLegendaGlyphsContinuos(String[] atributosEscolhidosGlyphContinuo) {
        painelLegendaVis.removeAll();
        atualizarLegendaTreemap(itemCor);
        atualizarLegendaGlyphs(atributosEscolhidosGlyph);
        ArrayList<String> lista = new ArrayList();
        //converndo lista<object>
        for (int i = 0; i < atributosEscolhidosGlyphContinuo.length; i++) {
            lista.add(atributosEscolhidosGlyphContinuo[i]);
        }
//        legendaVisualizacao.setAtributosGlyphs(lista);
        legendaVisualizacao.setAtributosGlyphsontinuos(lista);
//        for (int i = 0; i < atributosEscolhidosGlyphContinuo.size(); i++) {   
        JPanel painelDimensao = legendaVisualizacao.addLegendaDimensao(5);
        painelLegendaVis.setLayout(new BoxLayout(painelLegendaVis, BoxLayout.Y_AXIS));
        painelLegendaVis.add(painelDimensao);

        painelLegendaVis.revalidate();
//        }

    }

    private void atualizarLegendaTreemap(String itemCor) {
        painelLegendaVis.removeAll();
        if (!itemCor.equals("---")) {
            JPanel painelDimensao = legendaVisualizacao.addLegendaCorTreemap(itemCor);
            painelLegendaVis.add(painelDimensao);
        }
    }

    private void limparCacheGlyphs() {
        if (glyphPanel != null) {
            glyphPanel.setVisible(false);
            layerPane.remove(glyphPanel);
            glyphPanel = null;
        }

        painelLegendaVis.removeAll();
        painelLegendaVis.repaint();
    }

    private void atualizarLegendaGlyphs(ArrayList<Object> atributosEscolhidosGlyph) {
        painelLegendaVis.removeAll();
        atualizarLegendaTreemap(itemCor);
        legendaVisualizacao.setAtributosGlyphs(atributosEscolhidosGlyph);
        for (int i = 0; i < atributosEscolhidosGlyph.size(); i++) {
            if (!atributosEscolhidosGlyph.get(i).equals("---")) {
                JPanel painelDimensao = legendaVisualizacao.addLegendaDimensao(i);
                painelLegendaVis.setLayout(new BoxLayout(painelLegendaVis, BoxLayout.Y_AXIS));
                painelLegendaVis.add(painelDimensao);
            }
            painelLegendaVis.revalidate();
        }

    }

}

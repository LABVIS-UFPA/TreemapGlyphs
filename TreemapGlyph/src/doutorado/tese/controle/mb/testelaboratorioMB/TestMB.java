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
import doutorado.tese.visao.GlassPanel;
import doutorado.tese.visao.VisualizationsArea;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import net.bouthier.treemapAWT.TMView;

/**
 *
 * @author Anderson Soares
 */
public class TestMB {

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
    private AmbienteTestes ambiente;
    TarefaTestes[] tarefasCat;
    TarefaTestes[] tarefasConti;
    LogMB logMB;
    private long inicioTempo;
    private long fimTempo;
    private Thread threadTempo;
    private int taskTime;
    private int numMaxTarefas;
    private boolean timeOver;

    public TestMB(String menuItem, JTextPane task_TextArea) {
        this.menuItem = menuItem;
        this.task_TextArea = task_TextArea;
        ambiente = new AmbienteTestes();
        tarefasCat = ambiente.carregarTarefasCategoricas();
        tarefasConti = ambiente.carregarTarefasMistas();
        logMB = new LogMB();
        this.taskTime = 15 * 1000;
        this.numMaxTarefas = 3;
    }

    public TestMB(String menuItem, JPanel painelEsquerda, ManipuladorArquivo manipulador, JTextPane task_TextArea, JPanel painelLegendaVis) {
        this.menuItem = menuItem;
        this.painelEsquerda = painelEsquerda;
        this.manipulador = manipulador;
        this.task_TextArea = task_TextArea;
        this.painelLegendaVis = painelLegendaVis;
        legendaVisualizacao = new LegendaVisualizacao(painelLegendaVis.getBounds());
        atributosEscolhidosGlyph = new ArrayList<>();
        layerPane = new JLayeredPane();
    }

    public int carregarTarefas() {
        totalTarefas++;
        System.out.println("Tarefa " + totalTarefas);
        threadTempo = new Thread(new Runnable() {
            @Override
            public void run() {
                if (totalTarefas < numMaxTarefas) {
                    if (idTarefaAtual < tarefasCat.length) {
                        TarefaTestes tarefa = tarefasCat[idTarefaAtual];
                        task_TextArea.setText(tarefa.getTextoTarefa());
                        idTarefaAtual++;
                    } else {
                        TarefaTestes tarefa = tarefasConti[totalTarefas - idTarefaAtual];
                        task_TextArea.setText(tarefa.getTextoTarefa());
                    }
                    try {
                        Thread.sleep(taskTime);
                        JOptionPane.showMessageDialog(null, "Sorry, your time is over! "
                                + "Click on \"Submit answer\" button.",
                                "Tic-tac, Tic-tac...!", JOptionPane.WARNING_MESSAGE);
                        setTimeOver(true);
                    } catch (InterruptedException ex) {
                        int showConfirmDialog = JOptionPane.showConfirmDialog(null, "Was your response given with trust?",
                                "Trust answer?", JOptionPane.YES_NO_OPTION);
                        if (showConfirmDialog == 0) {
                            JOptionPane.showMessageDialog(null, "Ok, click on \"Next\" button");
                        } else {
                            JOptionPane.showMessageDialog(null, "Your answer won't be counted.");
                        }
                    }
                } else {
                    logMB.salvarLog();
                }
            }

        });
        threadTempo.start();

        return totalTarefas;
    }

    public void interromperThreadTempo() {
        synchronized (threadTempo) {
            setTimeOver(false);
            threadTempo.interrupt();
            if (totalTarefas == numMaxTarefas) {
                System.out.println("Voltei pra salvar o log depois de interromper a thread");
                logMB.salvarLog();
            }
        }
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

    public void setupContinuousGlyph(TarefaTestes tarefa) {
        glyphPanel.setManipulador(manipulador);
        glyphPanel.setContinuousGlyphActivated(true);

        glyphPanel.setTipoGlyphContinuoEscolhido("Bar");
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

    /**
     * @return the inicioTempo
     */
    public long getInicioTempo() {
        return inicioTempo;
    }

    /**
     * @param inicioTempo the inicioTempo to set
     */
    public void setInicioTempo(long inicioTempo) {
        this.inicioTempo = inicioTempo;
    }

    /**
     * @return the fimTempo
     */
    public long getFimTempo() {
        return fimTempo;
    }

    /**
     * @param fimTempo the fimTempo to set
     */
    public void setFimTempo(long fimTempo) {
        this.fimTempo = fimTempo;
    }

    /**
     * @return the timeOver
     */
    public boolean isTimeOver() {
        return timeOver;
    }

    public void setTimeOver(boolean timeOver) {
        this.timeOver = timeOver;
    }

}

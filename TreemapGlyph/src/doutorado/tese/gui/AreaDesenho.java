/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.gui;

import doutorado.tese.io.ManipuladorArquivo;
import doutorado.tese.util.Constantes;
import doutorado.tese.visualizacao.glyph.formasgeometricas.Estrela;
import doutorado.tese.visualizacao.glyph.StarGlyph;
import doutorado.tese.visualizacao.treemap.TreeMapLevel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import doutorado.tese.visualizacao.treemap.Treemap;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Anderson
 */
@Deprecated
public class AreaDesenho extends JLabel {

    private int quantVarStarGlyph;
//    private TreeMapLayout algorithm;
    private TreeMapLevel treemapLevel;
    private int[] dadosTamanho;
    private String[] dadosLegenda;
    private Estrela[] estrelas;
    private StarGlyph[] starGlyphs;
    Treemap treemap;
//    TreeMapLayout squarifier;
    ManipuladorArquivo manipulador;

    public AreaDesenho(int w, int h,
            List<String> variaveisStarGlyph, ManipuladorArquivo manipulador) {
        setQuantVarStarGlyph(variaveisStarGlyph.size());

        this.manipulador = manipulador;

//        Rect bounds = new Rect(0, 0, w, h);
//        treemap = new Treemap(manipulador, bounds);
//        treemap.setHierarchy(itensHierarquia);
//        treemap.setSizeColumn(ManipuladorArquivo.getColuna(itemTamanho));
//        if (!Constantes.isShowLegenda()) {
//            this.treemap.setTreemapItemLabel(treemap.getRoot().getItemsFilhos(), false);
//        } else {
//            treemap.setLabelColumn(ManipuladorArquivo.getColuna(itemLegenda));
//        }
//        this.add(treeMap);
//        squarifier = new TreeMapLayout();//squarifier original da app
//        squarifier.layoutSquarifiedTreemap(treemap.getRoot());
        //funciona sem agrupar o treemap
//        dadosTamanho = converterList2IntArray(manipulador, itemTamanho);
//        dadosLegenda = ManipuladorArquivo.getColuna(itemLegenda).getDadosColuna();
//        
//        
//        treemapLevel = new TreeMapLevel(dadosTamanho, w, h);
//        algorithm = new TreeMapLayout();
//        algorithm.layoutSquarifiedTreemap(treemapLevel, bounds);
//
//        TreeMapNode[] items = treemapLevel.getItemsFilhos();
//        estrelas = new Estrela[treemap.getRoot().getItemsFilhos().size()];
        starGlyphs = new StarGlyph[manipulador.getItensTreemap().length];
        for (int i = 0; i < manipulador.getItensTreemap().length; i++) {
//            estrelas[i] = new Estrela(treemap.getRoot().getItemsFilhos().get(i).getBounds());
            StarGlyph starGlyph = new StarGlyph(manipulador.getItensTreemap()[i].getBounds(), variaveisStarGlyph);
            starGlyph.setQuantVar(getQuantVarStarGlyph());
            starGlyph.setManipulador(manipulador);
            starGlyphs[i] = starGlyph;
        }
        setBounds(0, 0, w, h);
        setVisible(true);
    }

//    public TreeMap loadTreemap(String itemTamanho, String[] itensHierarquia, String itemLegenda, String corColName) {
//        Object[][] matrizDados = manipulador.montarMatrizDados();
//
//        TreeMap treeMap = new TreeMap(criarTableModel(matrizDados));
//
//        // Tuning the appearance of the TreeMap
//        treeMap.setBounds(this.getBounds());
//        treeMap.setAlgorithm(AlgorithmFactory.SQUARIFIED);
//        treeMap.setSizeByName(itemTamanho);
//        treeMap.setGroupByByNames(itensHierarquia);
//        treeMap.setColorByName(corColName);
////        treeMap.getModel().
//        if (Constantes.isShowLegenda()) {
//            treeMap.setBackgroundByName(itemLegenda);
//        }
//        treeMap.setLabels();
//        treeMap.getView().setShowTiming(true);
//        treeMap.getModel().getSettings().setRendering(RenderingFactory.FLAT);
//        return treeMap;
//    }

    @Override
    public void paint(Graphics g) {
        //1. Copiamos o contexto gráfico
        Graphics2D g2d = (Graphics2D) g.create();

        //2. Desenhamos o treemap
//        treemap.paint(g2d);
        //3.1 Desenhamos os StarGlyphs por grupo
//        for (int i = 0; i < treemap.getRoot().getItemsFilhos().size(); i++) {
//            if (Constantes.isShowStarGlyph()) {
//                starGlyphs[i].paint(g);
//            }
//        }
        //3.2 Desenhamos os StarGlyphs por itens
        for (int i = 0; i < manipulador.getItensTreemap().length; i++) {
            if (Constantes.isShowStarGlyph()) {
                starGlyphs[i].paint(g);
            }
        }
        //3. Descartamos a cópia do contexto gráfico
        g2d.dispose();

        //funciona com o treemap sem grupos
//        TreeMapNode[] items = treemapLevel.getItemsFilhos();
//        Rect rect;
//        for (int i = 0; i < items.length; i++) {
//            rect = items[i].getBounds();
//
//            //Desenhar estrelas
//            if (Constantes.isShowGlyph()) {
//                estrelas[i].paint(g);
//            }
//            //Escrever label
//            if (Constantes.isShowLegenda()) {
//                rect.label = dadosLegenda[i];
//                Point centro = centralizarLegenda(rect, g2d);
//                g.drawString(rect.label, centro.x, centro.y);
//            }
//            if (Constantes.isShowStarGlyph()) {
//                starGlyphs[i].paint(g);
//            }
//            g.setColor(Color.black);
//            g.drawRect((int) Math.round(rect.x), (int) Math.round(rect.y), 
//                    (int) Math.round(rect.w), (int) Math.round(rect.h));
//        }
    }

    public int getQuantVarStarGlyph() {
        return quantVarStarGlyph;
    }

    public void setQuantVarStarGlyph(int quantVarStarGlyph) {
        this.quantVarStarGlyph = quantVarStarGlyph;
    }

    private int[] converterList2IntArray(ManipuladorArquivo manipulador, String itemSelecionado) {
        int[] dados = new int[manipulador.getColuna(itemSelecionado).getDadosColuna().length];
        for (int i = 0; i < dados.length; i++) {
            dados[i] = Integer.valueOf(manipulador.getColuna(itemSelecionado).getDadosColuna()[i]);
        }
        return dados;
    }

//    private Point centralizarLegenda(Rect rect, Graphics2D g2d) {
//        int stringW = (int) Math.round(g2d.getFontMetrics().getStringBounds(rect.label, g2d).getCenterX());
//        int stringH = (int) Math.round(g2d.getFontMetrics().getStringBounds(rect.label, g2d).getCenterY());
//        int xTexto = (int) ((rect.w / 2) + rect.x) - stringW;
//        int yTexto = (int) ((rect.h / 2) + rect.y) - stringH;
//        return new Point(xTexto, yTexto);
//    }
    private TableModel criarTableModel(Object[][] matrizDados) {
        TableModel tableModel = new DefaultTableModel(matrizDados, manipulador.getCabecalho()) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return manipulador.getClassTipos()[columnIndex];
            }
        };
        return tableModel;
    }
}

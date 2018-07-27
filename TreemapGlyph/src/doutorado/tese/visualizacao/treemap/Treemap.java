/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.visualizacao.treemap;

import doutorado.tese.io.ManipuladorArquivo;
import doutorado.tese.util.Coluna;
//import doutorado.tese.visualizacao.treemap.layout.Squarify;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Anderson Soares
 */
@Deprecated
public class Treemap {

    ManipuladorArquivo manipulador;
    private TreeMapNode root;
    Queue<String> hierarquiaFila;
//    TreeMapLayout squarifier;
    String sizeColumn = "";
    String labelColumn = "";
    String colorColumn = "";
//    private Squarify layout;

    public Treemap(ManipuladorArquivo manipulador, Rect rect) {
        this.manipulador = manipulador;
        root = new TreeMapLevel(rect);
        root.setPaiLevel(null);
        root.setRaiz(true);
        root.setLabel("ROOT");
//        squarifier = new TreeMapLayout();//squarifier original da app
//        layout = new Squarify();
        hierarquiaFila = new LinkedList<>();
    }

    public void setSizeColumn(Coluna column) {
        this.root.setSize(column);
//        this.root.organize();
    }

    public void setLabelColumn(Coluna column) {
        this.labelColumn = column.getName();
        setTreemapItemLabel(this.root.getChildren(), true);
    }

    public void setTreemapItemLabel(List<TreeMapNode> itemsFilhos, boolean isUseLabel) {
        itemsFilhos.forEach((filho) -> {
            if (filho instanceof TreeMapItem) {
                filho.setUseLabel(isUseLabel);
                if (filho.isUseLabel()) {
//                    filho.bounds.label = filho.getMapaDetalhesItem().get(ManipuladorArquivo.getColuna(labelColumn));
                    filho.setLabel(filho.getMapaDetalhesItem().get(ManipuladorArquivo.getColuna(labelColumn)));
                }
            } else {
                TreeMapLevel level = (TreeMapLevel) filho;
                setTreemapItemLabel(level.getChildren(), isUseLabel);
            }
        });
    }

    public void setHierarchy(String[] hierarquia) {
        for (TreeMapItem treeMapItem : manipulador.getItensTreemap()) {
            hierarquiaFila.addAll(Arrays.asList(hierarquia));
            root.inserirFilhos(hierarquiaFila, treeMapItem, root);
        }
//        System.out.println("ROOT\n");
//        for (TreeMapNode itemsFilho : root.getItemsFilhos()) {
//            System.out.println("L->"+itemsFilho.label);
//            for (TreeMapNode itemsFilho1 : itemsFilho.getItemsFilhos()) {
//                System.out.println("L--->"+itemsFilho1.label);
//            }
//        }
    }

    public void paint(Graphics2D g) {
        //chamar squarifier
//        squarifier.layoutSquarifiedTreemap(root);
//        layout.squarify(root);
//        root.paint(g);
    }

    public TreeMapNode getRoot() {
        return root;
    }

    public void setRoot(TreeMapNode root) {
        this.root = root;
    }

}

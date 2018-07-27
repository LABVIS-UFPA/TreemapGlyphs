/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.visualizacao.treemap;

import doutorado.tese.io.ManipuladorArquivo;
import doutorado.tese.util.Coluna;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Queue;

/**
 * Simple Map Model
 *
 */
public class TreeMapLevel extends TreeMapNode {

    double totalArea;

    public TreeMapLevel(Rectangle rect) {
        this.bounds = rect;
        totalArea = bounds.width * bounds.height;
        children = new ArrayList<>();
        bordaInterna = rect;
    }

    public TreeMapLevel() {
        children = new ArrayList<>();
    }

    @Override
    public void inserirFilhos(Queue<String> hierarquia, TreeMapNode item, TreeMapNode pai) {
        TreeMapNode treeMapNode = null;
        if (hierarquia.isEmpty()) {
            pai.getChildren().add(item);
            item.setPaiLevel(pai);
        } else {
            for (TreeMapNode filho : pai.getChildren()) {
                if (filho.getLabel().equalsIgnoreCase(item.getMapaDetalhesItem().get(ManipuladorArquivo.getColuna(hierarquia.peek())))) {
                    treeMapNode = filho;
                    break;
                }
            }
            if (treeMapNode == null) {
                treeMapNode = new TreeMapLevel(bounds);
//                treeMapNode = new TreeMapLevel();
                treeMapNode.setLabel(item.getMapaDetalhesItem().get(ManipuladorArquivo.getColuna(hierarquia.peek())));
                pai.getChildren().add(treeMapNode);
                treeMapNode.setPaiLevel(pai);
            }
            if (!hierarquia.isEmpty()) {
                hierarquia.remove();
            }
            inserirFilhos(hierarquia, item, treeMapNode);
        }
    }

    /**
     *
     * @param colunaTamanho
     */
    @Override
    public void setSize(Coluna colunaTamanho) {
        double sum = 0;
        for (TreeMapNode filhosIten : children) {
            filhosIten.setSize(colunaTamanho);
            sum += filhosIten.getSize();
            filhosIten.setPaiLevel(this);
            filhosIten.setDepth(this.getDepth() + 1);
        }
        this.size = sum;
    }

    @Override
    public double getSize() {
        return size;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }

}

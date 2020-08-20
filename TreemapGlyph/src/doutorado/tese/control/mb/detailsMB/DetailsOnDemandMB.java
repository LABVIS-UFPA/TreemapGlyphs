/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.control.mb.detailsMB;

import doutorado.tese.control.business.visualizations.glyph.Glyph;
import doutorado.tese.dao.ManipuladorArquivo;
import doutorado.tese.model.Coluna;
import doutorado.tese.model.TreeMapItem;
import doutorado.tese.model.TreeMapLevel;
import doutorado.tese.model.TreeMapNode;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Anderson Soares
 */
public class DetailsOnDemandMB {

    private StringBuilder tooltipText;
    private String[] colunasDetalhesDemanda;
    private Glyph glyphOnToolTip;

    public DetailsOnDemandMB() {
        tooltipText = new StringBuilder();
    }

    public StringBuilder getTooltipOfObject(Object node) {
        tooltipText.setLength(0);//limpando o buffer
        if (node instanceof TreeMapLevel) {
            tooltipText.append("Label: ").append(((TreeMapLevel) node).getLabel())
                    .append("\n")
                    .append("Leaves: ").append(((TreeMapLevel) node).getChildren().size())
                    .append("\n")
                    .append("Median: ").append(median(node))
                    .append("\n")
                    .append("Total: ").append(total(node));
            return tooltipText;
        } else {
            TreeMapItem nodeItem = (TreeMapItem) node;
            if (node != null) {
                for (int i = 0; i < colunasDetalhesDemanda.length; i++) {
                    Coluna c = ManipuladorArquivo.getColuna(getColunasDetalhesDemanda()[i]);
                    String valorColuna = nodeItem.getMapaDetalhesItem().get(c);
                    tooltipText.append(c.getName()).append(": ").append(valorColuna).append("\n");
                }
                tooltipText.append(nodeItem.getBounds()).append("\n").append(nodeItem.getSizeTreemapNode());
            }
            return tooltipText;
        }
    }

    private double median(Object node) {
        List<TreeMapNode> children = ((TreeMapLevel) node).getChildren();
        double[] vetor = new double[children.size()];
        for (int i = 0; i < vetor.length; i++) {
            vetor[i] = children.get(i).getSizeTreemapNode();
        }
        Arrays.sort(vetor);
        double median;
        int middle = vetor.length / 2;
        if (vetor.length % 2 == 0) {            
            median =  Math.abs((vetor[middle - 1] + vetor[middle]) / 2);
        } else {
            median = vetor[middle];
        }
        return median;
    }
    
    private int total(Object node){
        List<TreeMapNode> children = ((TreeMapLevel) node).getChildren();
        int total = 0;
        for (int i = 0; i < children.size(); i++) {
            total += children.get(i).getSizeTreemapNode();
        }
        return total;
    }

    /**
     * @return the colunasDetalhesDemanda
     */
    public String[] getColunasDetalhesDemanda() {
        return colunasDetalhesDemanda;
    }

    /**
     * @param colunasDetalhesDemanda the colunasDetalhesDemanda to set
     */
    public void setColunasDetalhesDemanda(String[] colunasDetalhesDemanda) {
        this.colunasDetalhesDemanda = colunasDetalhesDemanda;
    }

    /**
     * @return the glyphOnToolTip
     */
    public Glyph getGlyphOnToolTip() {
        return glyphOnToolTip;
    }

    /**
     * @param glyphOnToolTip the glyphOnToolTip to set
     */
    public void setGlyphOnToolTip(Glyph glyphOnToolTip) {
        this.glyphOnToolTip = glyphOnToolTip;
    }
}

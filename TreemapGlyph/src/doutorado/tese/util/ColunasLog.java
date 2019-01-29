/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.util;

/**
 *
 * @author Anderson Soares
 */
public enum ColunasLog {    
    ID_TREEMAP_ITEM(0),
    QUANDO_CLICOU(1),
    QUANDO_CLICOU_TIMESTAMP(2),
    ITEM_SELECIONADO(3),
    IS_TESTE(4),
    TREEMAP_LABEL(5),
    TIMESTAMP_FIM(6);
    
    private int seq;
    
    ColunasLog(int seq){
        this.seq = seq;
    }
    
    public int getSequencia() {
        return seq;
    }
}

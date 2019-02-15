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
    ID_TAREFA(0),
    TEMPO_INICIO(1),
    TEMPO_FINAL(2),
    TEMPO_QUANDO_CLICOU(3),
    TEMPO_FINAL_CALCULADO(4),
    TIMESTAMP_INICIO(5),
    TIMESTAMP_FIM(6),    
    TIMESTAMP_QUANDO_CLICOU(7),
    ID_TREEMAP_ITEM(8),
    SELECIONADO(9),//(true/false)
    TREEMAP_LABEL(10),
    RESPOSTA_CORRETA(11),//se a resposta do usuario esta correta ou nao (true/false)
    QUESTAO_CORRETA(12);//se o usuario acertou ou nao a tarefa (true/false)
    
    private int id;
    
    ColunasLog(int id){
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
}

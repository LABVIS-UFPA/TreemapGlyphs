/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.controle.mb.testelaboratorioMB;

import doutorado.tese.util.ColunasLog;
import doutorado.tese.util.io.Escritor;

/**
 *
 * @author Anderson Soares
 */
public class LoggerMB {

    private static StringBuilder bufferLog;
    private static String[] colunaLog;

    private LoggerMB() {
    }
        
    public static void startLog() {
        bufferLog = new StringBuilder();
        colunaLog = new String[ColunasLog.values().length];
        
        getBufferLog().append(ColunasLog.ID_TAREFA.name()).append("\t")
                 .append(ColunasLog.TEMPO_INICIO.name()).append("\t")
                 .append(ColunasLog.TEMPO_FINAL.name()).append("\t")
                 .append(ColunasLog.TEMPO_QUANDO_CLICOU.name()).append("\t")
                 .append(ColunasLog.TEMPO_FINAL_CALCULADO.name()).append("\t")
                 .append(ColunasLog.TIMESTAMP_INICIO.name()).append("\t")
                 .append(ColunasLog.TIMESTAMP_FIM.name()).append("\t")
                 .append(ColunasLog.TIMESTAMP_QUANDO_CLICOU.name()).append("\t")
                 .append(ColunasLog.ID_TREEMAP_ITEM.name()).append("\t")
                 .append(ColunasLog.SELECIONADO.name()).append("\t")
                 .append(ColunasLog.TREEMAP_LABEL.name()).append("\t")
                 .append(ColunasLog.RESPOSTA_CORRETA.name()).append("\t")
                 .append("\n");
    }
    
    public static void addNewLineLog() {
        getBufferLog().append(colunaLog[ColunasLog.ID_TAREFA.getId()]).append("\t")
                 .append(colunaLog[ColunasLog.TEMPO_INICIO.getId()]).append("\t")
                 .append(colunaLog[ColunasLog.TEMPO_FINAL.getId()]).append("\t")
                 .append(colunaLog[ColunasLog.TEMPO_QUANDO_CLICOU.getId()]).append("\t")
                 .append(colunaLog[ColunasLog.TEMPO_FINAL_CALCULADO.getId()]).append("\t")
                 .append(colunaLog[ColunasLog.TIMESTAMP_INICIO.getId()]).append("\t")
                 .append(colunaLog[ColunasLog.TIMESTAMP_FIM.getId()]).append("\t")
                 .append(colunaLog[ColunasLog.TIMESTAMP_QUANDO_CLICOU.getId()]).append("\t")
                 .append(colunaLog[ColunasLog.ID_TREEMAP_ITEM.getId()]).append("\t")
                 .append(colunaLog[ColunasLog.SELECIONADO.getId()]).append("\t")
                 .append(colunaLog[ColunasLog.TREEMAP_LABEL.getId()]).append("\t")
                 .append(colunaLog[ColunasLog.RESPOSTA_CORRETA.getId()]).append("\t")
                 .append("\n");
        System.out.println(getBufferLog().toString());
    }

    public static void salvarLog() {
        Escritor.escreverArquivo("LOG_treemapGlyphs_", getBufferLog().toString());
    }
    
    public static String[] getColunaLog() {
        return colunaLog;
    }

    public static void setColunaLog(String[] pLog) {
        colunaLog = pLog;
    }

    /**
     * @return the bufferLog
     */
    public static StringBuilder getBufferLog() {
        return bufferLog;
    }

}

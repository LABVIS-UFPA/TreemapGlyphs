/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.control.mb.testeMB.usertest;

import doutorado.tese.control.business.userTest.ManipuladorLog;
import doutorado.tese.util.ColunasLog;
import doutorado.tese.util.io.Escritor;

/**
 *
 * @author Anderson Soares
 */
public class LoggerMB {

    private static StringBuilder buffer;
    private static StringBuilder bufferLogTeste;
    private static StringBuilder bufferLogTreinamento;
    private static String[] colunaLog;

    private LoggerMB() {
    }

    public static void startLog() {
        colunaLog = new String[ColunasLog.values().length];

        if (ManipuladorLog.isTesteAcontecendo()) {
            bufferLogTreinamento = new StringBuilder();
            buffer = getBufferLogTreinamento();
        } else {
            bufferLogTeste = new StringBuilder();
            buffer = getBufferLogTeste();
        }
        
        getBuffer().append(ColunasLog.ID_TAREFA.name()).append("\t")
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
                .append(ColunasLog.QUESTAO_CORRETA.name()).append("\n");
    }

    public static void addNewLineLog() {
        getBuffer().append(colunaLog[ColunasLog.ID_TAREFA.getId()]).append("\t")
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
                .append(colunaLog[ColunasLog.QUESTAO_CORRETA.getId()]).append("\n");
//        System.out.println(getBuffer().toString());
    }

    public static void salvarLog() {
        Escritor.escreverArquivo("LOG_treemapGlyphs_", getBuffer().toString());
    }

    public static String[] getColunaLog() {
        return colunaLog;
    }

    public static void setColunaLog(String[] pLog) {
        colunaLog = pLog;
    }

    /**
     * @return the bufferLogTeste
     */
    public static StringBuilder getBufferLogTeste() {
        return bufferLogTeste;
    }

    /**
     * @return the bufferLogTreinamento
     */
    public static StringBuilder getBufferLogTreinamento() {
        return bufferLogTreinamento;
    }

    /**
     * @return the buffer
     */
    public static StringBuilder getBuffer() {
        return buffer;
    }

}

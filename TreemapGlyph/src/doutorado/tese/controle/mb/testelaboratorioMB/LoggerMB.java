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
    private static String[] posicaoLog;

    public LoggerMB() {
        bufferLog = new StringBuilder();
    }

    public static void startLog() {
        bufferLog = new StringBuilder();
        posicaoLog = new String[ColunasLog.values().length];
        bufferLog.append(
                       ColunasLog.ID_TREEMAP_ITEM.name()).append("\t").
                append(ColunasLog.QUANDO_CLICOU.name()).append("\t").
                append(ColunasLog.QUANDO_CLICOU_TIMESTAMP.name()).append("\t").
                append(ColunasLog.ITEM_SELECIONADO.name()).append("\t").
                append(ColunasLog.IS_TESTE.name()).append("\t").
                append(ColunasLog.TREEMAP_LABEL.name()).append("\t").
                append(ColunasLog.TIMESTAMP_FIM.name()).append("\t");
        bufferLog.append("\n");
    }

    public static void addNewLineLog() {
        System.out.println("new line");
        bufferLog.append(posicaoLog[ColunasLog.ID_TREEMAP_ITEM.getSequencia()]).append("\t")
                .append(posicaoLog[ColunasLog.QUANDO_CLICOU.getSequencia()]).append("\t")
                .append(posicaoLog[ColunasLog.QUANDO_CLICOU_TIMESTAMP.getSequencia()]).append("\t")
                .append(posicaoLog[ColunasLog.ITEM_SELECIONADO.getSequencia()]).append("\t")
                .append(posicaoLog[ColunasLog.IS_TESTE.getSequencia()]).append("\t")
                .append(posicaoLog[ColunasLog.TREEMAP_LABEL.getSequencia()]).append("\t")
                .append(posicaoLog[ColunasLog.TIMESTAMP_FIM.getSequencia()]).append("\t")
                .append("\n");
        System.out.println(bufferLog.toString());
    }

    public static void salvarLog() {
        Escritor.escreverArquivo("LOG_treemapGlyphs_", bufferLog.toString());
    }

    public static String[] getPosicaoLog() {
        return posicaoLog;
    }

    public static void setPosicaoLog(String[] pLog) {
        posicaoLog = pLog;
    }

}

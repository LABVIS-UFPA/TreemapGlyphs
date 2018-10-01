/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.controle.mb.testelaboratorioMB;

import doutorado.tese.dao.ManipuladorArquivo;
import doutorado.tese.modelo.TreeMapNode;
import doutorado.tese.util.io.Escritor;
import java.util.List;

/**
 *
 * @author Anderson Soares
 */
public class LogMB {

    //treemap
    private String tamanhoTreemapLog;
    private String corTreemapLog;
    private String rotuloTreemapLog;
    private String hierarquiaTreemapLog = "";
    //gliph categorio
    private String ordemCamadasGlyphLog = "";
    private String texturaLog;
    private String corGlyphLog;
    private String formaLog;
    private String textoLog;
    //gliph continuo
    private String atributosContinuosLog = "";
    private String tipoGliphContinuoLog;
    private String cabecalhoLog;
    //
    private String respostasUsuario = "";
    private static StringBuilder bufferLog;
    private long fimTempo;
    private long inicioTempo;
    private String tarefa;
    private String cenario;
    private boolean timeOver;

    public LogMB() {
        bufferLog = new StringBuilder();
        bufferLog.append("hierarquiaTreemap\t"
                + "tamanhoTreemap\t"
                + "corTreemap\t"
                + "rotuloTreemap\t"
                + "ordemCamadasGlyph\t"
                + "textura\t"
                + "corGlyph\t"
                + "formaGlyph\t"
                + "textoGlyph\t"
                + "tipoGliphContinuo\t"
                + "atributosContinuos\t"
                + "RESPOSTA_USUARIO\t"
                + "TEMPO\t"
                + "TIME_OVER\t"
                + "ACERTOU\t"
                + "TAREFA\t"
                + "CENARIO");
        bufferLog.append("\n");
    }

    public void addLineLog() {
        long tempo = getFimTempo() - getInicioTempo();
        System.out.println("tempo: " + tempo);
        bufferLog.append(hierarquiaTreemapLog).
                append("\t").append(tamanhoTreemapLog).
                append("\t").append(corTreemapLog).
                append("\t").append(rotuloTreemapLog).
                append("\t").append(ordemCamadasGlyphLog).
                append("\t").append(texturaLog).
                append("\t").append(corGlyphLog).
                append("\t").append(formaLog).
                append("\t").append(textoLog).
                append("\t").append(tipoGliphContinuoLog).
                append("\t").append(atributosContinuosLog).
                append("\t").append(getRespostasUsuario()).
                append("\t").append(tempo).
                append("\t").append(timeOver == true ? "TRUE" : "FALSE").
                append("\t").append("Acertou ou errou").
                append("\t").append(tarefa).
                append("\t").append(cenario).
                append("\n");
        System.out.println("-------------------------");
        System.out.println(bufferLog.toString());
        System.out.println("-------------------------");
    }

    public void salvarLog() {
        Escritor.escreverArquivo("LOG_TreemapGlyph_", bufferLog.toString());
    }

    /**
     * @return the tamanhoTreemapLog
     */
    public String getTamanhoTreemapLog() {
        return tamanhoTreemapLog;
    }

    /**
     * @param tamanhoTreemapLog the tamanhoTreemapLog to set
     */
    public void setTamanhoTreemapLog(String tamanhoTreemapLog) {
        if (!tamanhoTreemapLog.equalsIgnoreCase("---")) {
            this.tamanhoTreemapLog = tamanhoTreemapLog;
        } else {
            this.tamanhoTreemapLog = "NULL";
        }
    }

    /**
     * @return the corTreemapLog
     */
    public String getCorTreemapLog() {
        return corTreemapLog;
    }

    /**
     * @param corTreemapLog the corTreemapLog to set
     */
    public void setCorTreemapLog(String corTreemapLog) {
        if (!corTreemapLog.equalsIgnoreCase("---")) {
            this.corTreemapLog = corTreemapLog;
        } else {
            this.corTreemapLog = "NULL";
        }
    }

    /**
     * @return the rotuloTreemapLog
     */
    public String getRotuloTreemapLog() {
        return rotuloTreemapLog;
    }

    /**
     * @param rotuloTreemapLog the rotuloTreemapLog to set
     */
    public void setRotuloTreemapLog(String rotuloTreemapLog) {
        if (!rotuloTreemapLog.equalsIgnoreCase("---")) {
            this.rotuloTreemapLog = rotuloTreemapLog;
        } else {
            this.rotuloTreemapLog = "NULL";
        }
    }

    /**
     * @return the hierarquiaTreemapLog
     */
    public String getHierarquiaTreemapLog() {
        return hierarquiaTreemapLog;
    }

    /**
     * @param hierarquiaTreemapLog the hierarquiaTreemapLog to set
     */
    public void setHierarquiaTreemapLog(List<String> hierarquiaTreemapLog) {
        if (!hierarquiaTreemapLog.isEmpty()) {
            for (String atributo : hierarquiaTreemapLog) {
                this.hierarquiaTreemapLog += atributo + "-";
            }
        } else {
            this.hierarquiaTreemapLog = "NULL";
        }
    }

    /**
     * @return the texturaLog
     */
    public String getTexturaLog() {
        return texturaLog;
    }

    /**
     * @param texturaLog the texturaLog to set
     */
    public void setTexturaLog(String texturaLog) {
        if (!texturaLog.equalsIgnoreCase("---")) {
            this.texturaLog = texturaLog;
        } else {
            this.texturaLog = "NULL";
        }
    }

    /**
     * @return the corGlyphLog
     */
    public String getCorGlyphLog() {
        return corGlyphLog;
    }

    /**
     * @param corGlyphLog the corGlyphLog to set
     */
    public void setCorGlyphLog(String corGlyphLog) {
        if (!corGlyphLog.equalsIgnoreCase("---")) {
            this.corGlyphLog = corGlyphLog;
        } else {
            this.corGlyphLog = "NULL";
        }
    }

    /**
     * @return the formaLog
     */
    public String getFormaLog() {
        return formaLog;
    }

    /**
     * @param formaLog the formaLog to set
     */
    public void setFormaLog(String formaLog) {
        if (!formaLog.equalsIgnoreCase("---")) {
            this.formaLog = formaLog;
        } else {
            this.formaLog = "NULL";
        }
    }

    /**
     * @return the textoLog
     */
    public String getTextoLog() {
        return textoLog;
    }

    /**
     * @param textoLog the textoLog to set
     */
    public void setTextoLog(String textoLog) {
        if (!textoLog.equalsIgnoreCase("---")) {
            this.textoLog = textoLog;
        } else {
            this.textoLog = "NULL";
        }
    }

    /**
     * @return the ordemCamadasGlyphLog
     */
    public String getOrdemCamadasGlyphLog() {
        return ordemCamadasGlyphLog;
    }

    /**
     * @param ordemCamadasGlyphLog the ordemCamadasGlyphLog to set
     */
    public void setOrdemCamadasGlyphLog(List<String> ordemCamadasGlyphLog) {
        if (!ordemCamadasGlyphLog.isEmpty()) {
            ordemCamadasGlyphLog.forEach((atributo) -> {
                this.ordemCamadasGlyphLog += atributo + "-";
            });
        } else {
            this.ordemCamadasGlyphLog = "NULL";
        }
    }

    /**
     * @return the atributosContinuosLog
     */
    public String getAtributosContinuosLog() {
        return atributosContinuosLog;
    }

    /**
     * @param atributosContinuosLog the atributosContinuosLog to set
     */
    public void setAtributosContinuosLog(List<String> atributosContinuosLog) {
        if (!atributosContinuosLog.isEmpty()) {
            atributosContinuosLog.forEach((atributo) -> {
                this.atributosContinuosLog += atributo + "-";
            });
        } else {
            this.atributosContinuosLog = "NULL";
        }
    }

    /**
     * @return the tipoGliphContinuoLog
     */
    public String getTipoGliphContinuoLog() {
        return tipoGliphContinuoLog;
    }

    /**
     * @param tipoGliphContinuoLog the tipoGliphContinuoLog to set
     */
    public void setTipoGliphContinuoLog(String tipoGliphContinuoLog) {
        this.tipoGliphContinuoLog = tipoGliphContinuoLog;
    }

    /**
     * @return the respostasUsuario
     */
    public String getRespostasUsuario() {
        return respostasUsuario;
    }

    /**
     * @param respostasUsuario the respostasUsuario to set
     */
    public void setRespostasUsuario(List<TreeMapNode> respostasUsuario) {
        this.respostasUsuario = "";
        if (respostasUsuario != null && !respostasUsuario.isEmpty()) {
            respostasUsuario.forEach((atributo) -> {
                this.respostasUsuario += atributo.getMapaDetalhesItem().get(ManipuladorArquivo.getColuna("MARCA")) + "-";
            });
        } else {
            this.respostasUsuario = "NULL";
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
     * @return the tarefa
     */
    public String getTarefa() {
        return tarefa;
    }

    /**
     * @param tarefa the tarefa to set
     */
    public void setTarefa(String tarefa) {
        this.tarefa = tarefa;
    }

    /**
     * @return the cenario
     */
    public String getCenario() {
        return cenario;
    }

    /**
     * @param cenario the cenario to set
     */
    public void setCenario(String cenario) {
        this.cenario = cenario;
    }

    public void setTimeOver(boolean timeOver) {
        this.timeOver = timeOver;
    }
    
}

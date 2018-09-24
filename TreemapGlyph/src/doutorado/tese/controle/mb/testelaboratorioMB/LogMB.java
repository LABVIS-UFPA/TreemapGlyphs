/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.controle.mb.testelaboratorioMB;

import doutorado.tese.dao.ManipuladorArquivo;
import doutorado.tese.modelo.TreeMapNode;
import doutorado.tese.util.io.Escritor;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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

    public LogMB() {
        cabecalhoLog = "hierarquiaTreemap\t"
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
                + "ACERTOU\t"
                + "TAREFA\t"
                + "CENARIO\t"
                + "NUM_TESTE\n";
    }

    public void saveLog() {
        String linhaLog =  hierarquiaTreemapLog + "\t"
                + tamanhoTreemapLog + "\t"
                + corTreemapLog + "\t"
                + rotuloTreemapLog + "\t"
                + ordemCamadasGlyphLog + "\t"
                + texturaLog + "\t"
                + corGlyphLog + "\t"
                + formaLog + "\t"
                + textoLog + "\t"
                + tipoGliphContinuoLog + "\t"
                + atributosContinuosLog + "\t"
                + getRespostasUsuario() + "\n";
        Escritor.escreverArquivo("LOG_TreemapGlyph_", cabecalhoLog + linhaLog);
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
        if (!respostasUsuario.isEmpty()) {
            respostasUsuario.forEach((atributo) -> {
                this.respostasUsuario += atributo.getMapaDetalhesItem().get(ManipuladorArquivo.getColuna("MARCA")) + "-";
            });
        } else {
            this.respostasUsuario = "NULL";
        }
    }

}

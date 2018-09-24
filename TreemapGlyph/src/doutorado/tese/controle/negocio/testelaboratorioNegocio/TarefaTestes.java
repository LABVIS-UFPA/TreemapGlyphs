/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.controle.negocio.testelaboratorioNegocio;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Anderson Soares
 */
public class TarefaTestes {

    public final String DADOS_CATEGORICO = "categorico";
    public final String DADOS_MISTO = "misto";
    public final String ENCONTRAR = "ENCONTRAR";
    public final String LOCALIZAR = "LOCALIZAR";
    public final String PROCURAR = "PROCURAR";
    public final String EXPLORAR = "EXPLORAR";
    private String tipoSubTarefaBusca;
    private String tipoDados;
    private String textoTarefa;
    private String parametroTamanhoTreemap;
    private String parametroLegendaTreemap;
    private String parametroCorTreemap;
    private List<String> parametrosHierarquiaTreemap;
    private String parametroTexturaGlyph;
    private String parametroCorGlyph;
    private String parametroFormaGlyph;
    private String parametroLetraGlyph;
    private List<String> parametrosContinuosGlyph;
    private List<String> parametrosDetalhesSobDemanda;
    private String parametroBar1;
    private String parametroBar2;
    private String parametroBar3;

    public TarefaTestes() {
        parametrosHierarquiaTreemap = new ArrayList<>();
        parametrosContinuosGlyph = new ArrayList<>();
        parametrosDetalhesSobDemanda = new ArrayList<>();
        parametrosDetalhesSobDemanda.add("MARCA");
        parametrosDetalhesSobDemanda.add("VALOR");
        parametrosDetalhesSobDemanda.add("TIPO");
        parametrosDetalhesSobDemanda.add("PESO");
        parametrosDetalhesSobDemanda.add("POTENCIA");
        parametrosDetalhesSobDemanda.add("RPM");
    }

    /**
     * @return the tipoDados
     */
    public String getTipoDados() {
        return tipoDados;
    }

    /**
     * @param tipoDados the tipoDados to set
     */
    public void setTipoDados(String tipoDados) {
        this.tipoDados = tipoDados;
    }

    /**
     * @return the textoTarefa
     */
    public String getTextoTarefa() {
        return textoTarefa;
    }

    /**
     * @param textoTarefa the textoTarefa to set
     */
    public void setTextoTarefa(String textoTarefa) {
        this.textoTarefa = textoTarefa;
    }

    /**
     * @return the tipoSubTarefaBusca
     */
    public String getTipoSubTarefaBusca() {
        return tipoSubTarefaBusca;
    }

    /**
     * @param tipoSubTarefaBusca the tipoSubTarefaBusca to set
     */
    public void setTipoSubTarefaBusca(String tipoSubTarefaBusca) {
        this.tipoSubTarefaBusca = tipoSubTarefaBusca;
    }

    /**
     * @return the parametroTamanhoTreemap
     */
    public String getParametroTamanhoTreemap() {
        return parametroTamanhoTreemap;
    }

    /**
     * @param parametroTamanhoTreemap the parametroTamanhoTreemap to set
     */
    public void setParametroTamanhoTreemap(String parametroTamanhoTreemap) {
        this.parametroTamanhoTreemap = parametroTamanhoTreemap;
    }

    /**
     * @return the parametroLegendaTreemap
     */
    public String getParametroRotuloTreemap() {
        return parametroLegendaTreemap;
    }

    /**
     * @param parametroLegendaTreemap the parametroLegendaTreemap to set
     */
    public void setParametroLegendaTreemap(String parametroLegendaTreemap) {
        this.parametroLegendaTreemap = parametroLegendaTreemap;
    }

    /**
     * @return the parametroCorTreemap
     */
    public String getParametroCorTreemap() {
        return parametroCorTreemap;
    }

    /**
     * @param parametroCorTreemap the parametroCorTreemap to set
     */
    public void setParametroCorTreemap(String parametroCorTreemap) {
        this.parametroCorTreemap = parametroCorTreemap;
    }

    /**
     * @return the parametrosHierarquiaTreemap
     */
    public List<String> getParametrosHierarquiaTreemap() {
        return parametrosHierarquiaTreemap;
    }

    /**
     * @param parametrosHierarquiaTreemap the parametrosHierarquiaTreemap to set
     */
    public void setParametrosHierarquiaTreemap(List<String> parametrosHierarquiaTreemap) {
        this.parametrosHierarquiaTreemap = parametrosHierarquiaTreemap;
    }

    /**
     * @return the parametroTexturaGlyph
     */
    public String getParametroTexturaGlyph() {
        return parametroTexturaGlyph;
    }

    /**
     * @param parametroTexturaGlyph the parametroTexturaGlyph to set
     */
    public void setParametroTexturaGlyph(String parametroTexturaGlyph) {
        this.parametroTexturaGlyph = parametroTexturaGlyph;
    }

    /**
     * @return the parametroCorGlyph
     */
    public String getParametroCorGlyph() {
        return parametroCorGlyph;
    }

    /**
     * @param parametroCorGlyph the parametroCorGlyph to set
     */
    public void setParametroCorGlyph(String parametroCorGlyph) {
        this.parametroCorGlyph = parametroCorGlyph;
    }

    /**
     * @return the parametroFormaGlyph
     */
    public String getParametroFormaGlyph() {
        return parametroFormaGlyph;
    }

    /**
     * @param parametroFormaGlyph the parametroFormaGlyph to set
     */
    public void setParametroFormaGlyph(String parametroFormaGlyph) {
        this.parametroFormaGlyph = parametroFormaGlyph;
    }

    /**
     * @return the parametroLetraGlyph
     */
    public String getParametroLetraGlyph() {
        return parametroLetraGlyph;
    }

    /**
     * @param parametroLetraGlyph the parametroLetraGlyph to set
     */
    public void setParametroLetraGlyph(String parametroLetraGlyph) {
        this.parametroLetraGlyph = parametroLetraGlyph;
    }

    /**
     * @return the parametrosContinuosGlyph
     */
    public List<String> getParametrosContinuosGlyph() {
        return parametrosContinuosGlyph;
    }

    /**
     * @param parametrosContinuosGlyph the parametrosContinuosGlyph to set
     */
    public void setParametrosContinuosGlyph(List<String> parametrosContinuosGlyph) {
        this.parametrosContinuosGlyph = parametrosContinuosGlyph;
    }

    /**
     * @return the parametrosDetalhesSobDemanda
     */
    public List<String> getParametrosDetalhesSobDemanda() {
        return parametrosDetalhesSobDemanda;
    }

    /**
     * @param parametrosDetalhesSobDemanda the parametrosDetalhesSobDemanda to set
     */
    public void setParametrosDetalhesSobDemanda(List<String> parametrosDetalhesSobDemanda) {
        this.parametrosDetalhesSobDemanda = parametrosDetalhesSobDemanda;
    }

    /**
     * @return the parametroBar1
     */
    public String getParametroBar1() {
        return parametroBar1;
    }

    /**
     * @param parametroBar1 the parametroBar1 to set
     */
    public void setParametroBar1(String parametroBar1) {
        this.parametroBar1 = parametroBar1;
    }

    /**
     * @return the parametroBar2
     */
    public String getParametroBar2() {
        return parametroBar2;
    }

    /**
     * @param parametroBar2 the parametroBar2 to set
     */
    public void setParametroBar2(String parametroBar2) {
        this.parametroBar2 = parametroBar2;
    }

    /**
     * @return the parametroBar3
     */
    public String getParametroBar3() {
        return parametroBar3;
    }

    /**
     * @param parametroBar3 the parametroBar3 to set
     */
    public void setParametroBar3(String parametroBar3) {
        this.parametroBar3 = parametroBar3;
    }

}

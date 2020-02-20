/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.control.mb;

import doutorado.tese.control.business.visualizations.glyph.factorys.variaveisvisuais.GeometryFactory;
import doutorado.tese.dao.ManipuladorArquivo;
import doutorado.tese.model.Coluna;
import doutorado.tese.util.Constantes;
import doutorado.tese.util.Metadados;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 *
 * @author Anderson Soares
 */
public class SetUpMB {

    private List<String> atributosCategoricos;
    private List<String> atributosContinuos;
    private ManipuladorArquivo manipulador;

    public SetUpMB() {
    }

    public void loadVariaveisGlyph(Object[] objs, JComboBox<String> atributo) {
        DefaultComboBoxModel model = new DefaultComboBoxModel(objs);
        atributo.setModel(model);
    }

    /**
     * Metodo usado para carregar os atributos categoricos nas listas de glyphs
     *
     * @param nivel
     * @return um array contendo os atributos que serao exibidos nas listas dos
     * glyphs
     */
    public Object[] getListaAtributosCategoricos(Constantes.NivelGlyph nivel, boolean glyph) {
        ArrayList<String> list = new ArrayList<>();
        list.add(0, "---");
        list.addAll(analisarAtributosCategoricos(nivel, glyph));
        return list.toArray();
    }

    public List<String> analisarAtributosCategoricos(Constantes.NivelGlyph nivel, boolean glyph) {
        ArrayList<String> list = new ArrayList<>();
        switch (nivel) {
            case NIVEL_1:
                analisarQuantAtributosCategoricos(list, Constantes.TIPO_TEXTURA);
                break;
            case NIVEL_2:
                if (glyph) {
                    analisarQuantAtributosCategoricos(list, Constantes.getCorGlyphs());
                } else {
                    analisarQuantAtributosCategoricos(list, Constantes.getCor());
                }
                break;
            case NIVEL_3:
                GeometryFactory.FORMAS.GLYPH_FORMAS[] formas = new GeometryFactory.FORMAS.GLYPH_FORMAS[GeometryFactory.FORMAS.GLYPH_FORMAS.values().length - 1];
                for (int i = 0; i < formas.length; i++) {
                    formas[i] = GeometryFactory.FORMAS.GLYPH_FORMAS.values()[i];
                }
                analisarQuantAtributosCategoricos(list, formas);
                break;
            case NIVEL_4:
                analisarQuantAtributosCategoricos(list, Constantes.LETRAS_ALFABETO);
                break;
            case NIVEL_5:
                analisarQuantAtributosCategoricos(list, Constantes.POSICOES.values());
                break;
            default:
                System.err.println("Nao foi carregar atributos para a dimensão.");
        }
        return list;
    }

    private List<String> analisarQuantAtributosCategoricos(List<String> list, Object[] obj) {
        for (String colunasCategorica : getAtributosCategoricos()) {
            Coluna c = ManipuladorArquivo.getColuna(colunasCategorica);
            int quantDadosDistintos = c.getDadosDistintos().size();
            if (quantDadosDistintos <= obj.length) {
                list.add(c.getName());
            }
        }
        return list;
    }

    public List<String> loadCategorialAttributes() {
        setAtributosCategoricos(new ArrayList<>());
        for (int i = 0; i < getManipulador().getColunas().length - 1; i++) {
            Coluna c = getManipulador().getColunas()[i];
            if (c.getDescription().equals(Metadados.Descricao.CATEGORICAL)) {
                getAtributosCategoricos().add(c.getName());
            }
        }
        return getAtributosCategoricos();
    }

    public Object[] loadColorAttributes2Glyphs() {
        List<String> itens = new ArrayList<>();
        List<String> atributosCategoricosCor = new ArrayList<>();
        for (Object listaAtributosCategorico : getListaAtributosCategoricos(Constantes.NivelGlyph.NIVEL_2, true)) {
            atributosCategoricosCor.add((String) listaAtributosCategorico);
        }
        itens.addAll(atributosCategoricosCor);
        itens.addAll(getAtributosCategoricos());
        return itens.toArray();
    }

    public Object[] loadPositionAttributes2Glyphs() {
        List<String> atributosCategoricos = new ArrayList<>();
        for (Object listaAtributosCategorico : getListaAtributosCategoricos(Constantes.NivelGlyph.NIVEL_5, true)) {
            atributosCategoricos.add((String) listaAtributosCategorico);
        }
        return atributosCategoricos.toArray();
    }

    public Object[] loadColorAttributesTreemap() {
        List<String> coresTreemap = new ArrayList<>();
        coresTreemap.add("---");
        coresTreemap.addAll(Arrays.asList(manipulador.getCabecalho()));
        return coresTreemap.toArray();
    }

    public List<String> loadContinuousAttributes() {
        atributosContinuos = new ArrayList<>();
        for (String cabecalho : manipulador.getCabecalho()) {
            String tipo = manipulador.getMapaCabecalho().get(cabecalho);
            if (tipo.equalsIgnoreCase(Metadados.TipoDados.Integer.name())
                    || tipo.equalsIgnoreCase(Metadados.TipoDados.Double.name())) {
                atributosContinuos.add(cabecalho);
            }
        }
        return atributosContinuos;
    }

    /**
     * @return the atributosCategoricos
     */
    public List<String> getAtributosCategoricos() {
        return atributosCategoricos;
    }

    /**
     * @param atributosCategoricos the atributosCategoricos to set
     */
    public void setAtributosCategoricos(List<String> atributosCategoricos) {
        this.atributosCategoricos = atributosCategoricos;
    }

    /**
     * @return the manipulador
     */
    public ManipuladorArquivo getManipulador() {
        return manipulador;
    }

    /**
     * @param manipulador the manipulador to set
     */
    public void setManipulador(ManipuladorArquivo manipulador) {
        this.manipulador = manipulador;
    }

    /**
     * @return the atributosContinuos
     */
    public List<String> getAtributosContinuos() {
        return atributosContinuos;
    }

    /**
     * @param atributosContinuos the atributosContinuos to set
     */
    public void setAtributosContinuos(List<String> atributosContinuos) {
        this.atributosContinuos = atributosContinuos;
    }

}

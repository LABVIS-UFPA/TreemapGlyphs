/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.control.mb;

import doutorado.tese.control.business.visualizations.glyph.Glyph;
import doutorado.tese.control.business.visualizations.glyph.GlyphConcrete;
import doutorado.tese.control.business.visualizations.glyph.factorys.variaveisvisuais.GeometricalFactory;
import doutorado.tese.control.business.visualizations.glyph.factorys.variaveisvisuais.OrientationFactory;
import doutorado.tese.dao.ManipuladorArquivo;
import doutorado.tese.model.Coluna;
import doutorado.tese.model.TreeMapItem;
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
    private static TreeMapItem[] itensTreemap;

    public SetUpMB() {
    }
    
    /**
     * Metodo que cria cada item do treemap, associa os dados da base de dados
     * ao item correspondete, e define um objeto GlyphConcreto para esse item.
     */
    public void carregarItensTreemap() {
        int totalItens = getManipulador().getDadosColuna(getManipulador().getCabecalho()[0]).length;
        itensTreemap = new TreeMapItem[totalItens];
        for (int linha = 0; linha < totalItens; linha++) {
            String[] dadosLinha = getManipulador().getDadosLinha(linha + 2);
            TreeMapItem itemLocal = null;
            try {
                itemLocal = new TreeMapItem(1, 0);
                Glyph glyphConcrete = new GlyphConcrete();
                glyphConcrete.setNodeTreemap(itemLocal);
                itemLocal.setGlyph(glyphConcrete);
                itemLocal.setId(linha + 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (int coluna = 0; coluna < dadosLinha.length; coluna++) {
                itemLocal.getMapaDetalhesItem().put(getManipulador().getColunas()[coluna], dadosLinha[coluna]);
            }
            itensTreemap[linha] = itemLocal;
        }
    }

    public void loadVariaveisGlyph(Object[] objs, JComboBox<String> atributo) {
        DefaultComboBoxModel model = new DefaultComboBoxModel(objs);
        atributo.setModel(model);
    }

    /**
     * Metodo usado para carregar os atributos categoricos nas listas de glyphs
     *
     * @param varVisual
     * @param glyph
     * @return um array contendo os atributos que serao exibidos nas listas dos
     * glyphs
     */
    public Object[] getListaAtributosCategoricos(Constantes.VAR_VISUAIS_CATEGORICAS varVisual, boolean glyph) {
        ArrayList<String> list = new ArrayList<>();
        list.add(0, "---");
        list.addAll(analisarAtributosCategoricos(varVisual, glyph));
        return list.toArray();
    }

    public List<String> analisarAtributosCategoricos(Constantes.VAR_VISUAIS_CATEGORICAS varVisual, boolean glyph) {
        ArrayList<String> list = new ArrayList<>();
        switch (varVisual) {
            case TEXTURE:
                analisarQuantAtributosCategoricos(list, Constantes.TIPO_TEXTURA);
                break;
            case COLOR_HUE:
                if (glyph) {
                    analisarQuantAtributosCategoricos(list, Constantes.getColorHueGlyphs());
                } else {
                    analisarQuantAtributosCategoricos(list, Constantes.getCorTreemap());
                }
                break;
            case SHAPE:
                GeometricalFactory.FORMAS.GLYPH_FORMAS[] formas = new GeometricalFactory.FORMAS.GLYPH_FORMAS[GeometricalFactory.FORMAS.GLYPH_FORMAS.values().length];
                for (int i = 0; i < formas.length; i++) {
                    formas[i] = GeometricalFactory.FORMAS.GLYPH_FORMAS.values()[i];
                }
                analisarQuantAtributosCategoricos(list, formas);
                break;
            case TEXT:
                analisarQuantAtributosCategoricos(list, Constantes.LETRAS_ALFABETO);
                break;
            case POSITION:
                analisarQuantAtributosCategoricos(list, Constantes.POSICOES.values());
                break;
            case ORIENTATION:
                analisarQuantAtributosCategoricos(list, OrientationFactory.ARROW.GLYPH_ORIENTACAO.values());
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
        for (Object listaAtributosCategorico : getListaAtributosCategoricos(Constantes.VAR_VISUAIS_CATEGORICAS.COLOR_HUE, true)) {
            atributosCategoricosCor.add((String) listaAtributosCategorico);
        }
        itens.addAll(atributosCategoricosCor);
        itens.addAll(getAtributosContinuos());
        return itens.toArray();
    }

    public Object[] loadPositionAttributes2Glyphs() {
        List<String> atributos = new ArrayList<>();
        for (Object listaAtributosCategorico : getListaAtributosCategoricos(Constantes.VAR_VISUAIS_CATEGORICAS.POSITION, true)) {
            atributos.add((String) listaAtributosCategorico);
        }
        return atributos.toArray();
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

    /**
     * Retorna todos os TreemapItens do treemap
     *
     * @return the itensTreemap
     */
    public static TreeMapItem[] getItensTreemap() {
        return itensTreemap;
    }

    /**
     * @param itensTreemap the itensTreemap to set
     */
    public static void setItensTreemap(TreeMapItem[] itensTreemap) {
        SetUpMB.itensTreemap = itensTreemap;
    }
    
}

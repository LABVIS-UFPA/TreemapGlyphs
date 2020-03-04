/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.control.business.visualizations.legenda;

import doutorado.tese.dao.ManipuladorArquivo;
import doutorado.tese.model.Coluna;
import doutorado.tese.util.Constantes;
import doutorado.tese.util.Metadados;
import doutorado.tese.control.business.visualizations.glyph.factorys.variaveisvisuais.GeometryFactory.FORMAS;
import doutorado.tese.control.business.visualizations.glyph.factorys.variaveisvisuais.OrientationFactory.ARROW;
import doutorado.tese.util.Util;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.StyleConstants;

/**
 *
 * @author Anderson Soares
 */
public class LegendaVisualizacao {

    HashMap<Constantes.VAR_VISUAIS_CATEGORICAS, Object> atributosEscolhidosGlyph = null;
    private Rectangle bounds = null;
    private ArrayList<String> listaContinuos = null;

    public LegendaVisualizacao(Rectangle bound) {
        this.bounds = null;
        this.listaContinuos = null;
        setBounds(bound);
    }

    public JLabel criarLabel(String conteudoDist, Icon icon) {
        JLabel label = null;
        if (icon != null) {
            label = new JLabel(conteudoDist, icon, JLabel.RIGHT);
            //Set the position of the text, relative to the icon:
            label.setVerticalTextPosition(JLabel.CENTER);
            label.setHorizontalTextPosition(JLabel.RIGHT);
            label.setFont(new Font("Arial", Font.PLAIN, 12));
        } else {
            label = new JLabel(conteudoDist, JLabel.CENTER);
        }
        label.setVisible(true);
        return label;
    }

    public JPanel addLegendaCorTreemap(String itemCor) {
        JPanel painel = new JPanel(new GridLayout(0, 3));
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createTitledBorder("Color TreeMap (" + itemCor + ") Subtitle"));
        painel.setBounds(bounds);
        painel.setVisible(true);

        Coluna c = ManipuladorArquivo.getColuna(itemCor);

        if (c.getDescription() == Metadados.Descricao.CONTINUOUS) {
            criarlegendaContinua(null, c, painel);
        } else {
            List<String> dadosDistintos = c.getDadosDistintos();
            for (int i = 0; i < dadosDistintos.size(); i++) {
                IconeLegenda icon = new IconeLegenda();
                icon.setDimensaoCategorical(null);//Constantes.COR_TREEMAP
                icon.setValorIcon(Constantes.getCorTreemap()[i]);
                JLabel label = criarLabel(dadosDistintos.get(i), icon);
                painel.add(label);
                label.setHorizontalAlignment(SwingConstants.LEFT);
                painel.setAlignmentX(label.LEFT_ALIGNMENT);
            }
        }
        return painel;
    }

    public void analisarDadosDistintos(JPanel painel, Coluna c, List<String> dadosDistintos, Constantes.VAR_VISUAIS_CATEGORICAS layer) {
        for (int i = 0; i < dadosDistintos.size(); i++) {
            IconeLegenda icon = new IconeLegenda();
            switch (layer) {
                case TEXTURE:
                    icon.setDimensaoCategorical(Constantes.VAR_VISUAIS_CATEGORICAS.TEXTURE);
                    icon.setValorIcon(Constantes.TIPO_TEXTURA[i]);
                    break;
                case COLOR_HUE:
                    if (c.getDescription() == Metadados.Descricao.CATEGORICAL) {
                        icon.setDimensaoCategorical(Constantes.VAR_VISUAIS_CATEGORICAS.COLOR_HUE);
                        icon.setValorIcon(Constantes.getColorHueGlyphs()[i]);
                    } else {
                        criarlegendaContinua(Constantes.VAR_VISUAIS_CATEGORICAS.COLOR_HUE, c, painel);
                    }
                    break;
                case SHAPE:
                    icon.setDimensaoCategorical(Constantes.VAR_VISUAIS_CATEGORICAS.SHAPE);
                    icon.setValorIcon(FORMAS.GLYPH_FORMAS.values()[i]);
                    break;
                case TEXT:
                    icon.setDimensaoCategorical(Constantes.VAR_VISUAIS_CATEGORICAS.TEXT);
                    icon.setValorIcon(Constantes.LETRAS_ALFABETO[i]);
                    break;
                case POSITION:
                    icon.setDimensaoCategorical(Constantes.VAR_VISUAIS_CATEGORICAS.POSITION);
                    icon.setValorIcon(Constantes.POSICOES.values()[i]);
                    break;
                case ORIENTATION:
                    icon.setDimensaoCategorical(Constantes.VAR_VISUAIS_CATEGORICAS.ORIENTATION);
                    icon.setValorIcon(ARROW.GLYPH_ORIENTACAO.values()[i]);
                    break;
            }
            if (c.getDescription() == Metadados.Descricao.CATEGORICAL) {
                JLabel label = criarLabel(dadosDistintos.get(i), icon);
                painel.add(label);

                label.setHorizontalAlignment(SwingConstants.LEFT);
                painel.setAlignmentX(label.LEFT_ALIGNMENT);
            }
        }
    }

    /**
     * Dimensao analisada no treemap representada por um glyph
     *
     * @param layer
     * @param atributo atributo escolhido no JCombobox da GUI
     * @return
     */
    public JPanel addLegendaDimensao(Constantes.VAR_VISUAIS_CATEGORICAS layer, Object atributo) {
        JPanel painel = new JPanel(new GridLayout(0, 3));
        painel.setBackground(Color.WHITE);
        Coluna c = null;
        List<String> dadosDistintos = null;
        if (atributo != null && layer != null) {
            painel.setBorder(BorderFactory.createTitledBorder(atributo.toString() + "'s Subtitle"));
            c = ManipuladorArquivo.getColuna(atributo.toString());
            dadosDistintos = c.getDadosDistintos();
            analisarDadosDistintos(painel, c, dadosDistintos, layer);
        } else {
            painel.setBorder(BorderFactory.createTitledBorder("Profile" + "'s Subtitle"));
            return criarLegendaGlyphContinuo(painel, listaContinuos);
        }
        painel.setBounds(bounds);
        painel.setVisible(true);

        return painel;
    }

    public void criarlegendaContinua(Constantes.VAR_VISUAIS_CATEGORICAS dimensao, Coluna c, JPanel painel) {
        IconeLegenda icon = new IconeLegenda();
        icon.setDimensaoCategorical(dimensao);//Constantes.COR_TREEMAP
        icon.setMaxValorContIcon(c.maiorMenorValues[0]);
        icon.setMinValorContIcon(c.maiorMenorValues[1]);

        JLabel labelMax = criarLabel(c.maiorMenorValues[1] + "", null);
        painel.add(labelMax);
        labelMax.setHorizontalAlignment(SwingConstants.RIGHT);
        painel.setAlignmentX(labelMax.RIGHT_ALIGNMENT);

        JLabel labelIcone = criarLabel("", icon);
        painel.add(labelIcone);
        labelIcone.setVerticalAlignment(SwingConstants.CENTER);
        labelIcone.setHorizontalAlignment(SwingConstants.CENTER);
        painel.setAlignmentX(labelIcone.LEFT_ALIGNMENT);

        JLabel labelMin = criarLabel(c.maiorMenorValues[0] + "", null);
        painel.add(labelMin);
        labelMin.setHorizontalAlignment(SwingConstants.LEFT);
        painel.setAlignmentX(labelMin.LEFT_ALIGNMENT);
    }

    private JPanel criarLegendaGlyphContinuo(JPanel painel, ArrayList<String> atributosEscolhidosGlyphContinuo) {
        IconeLegenda icon = new IconeLegenda();
//        icon.setDimensaoCategorical(dimensao);
        icon.setMaxValorContIcon(10);
        icon.setMinValorContIcon(10);
        icon.setAtributosEscolhidosGlyphContinuo(atributosEscolhidosGlyphContinuo);

        JLabel labelIcone = criarLabel("", icon);
        painel.add(labelIcone);
        labelIcone.setVerticalAlignment(SwingConstants.CENTER);
        labelIcone.setHorizontalAlignment(SwingConstants.CENTER);
        painel.setAlignmentX(labelIcone.LEFT_ALIGNMENT);

        JTextPane legendas = new JTextPane();
        legendas.setEditable(true);
        legendas.setText("");
        for (int i = 0; i < 2; i++) {
            Util.appendToPane(legendas, "\n", Color.decode("#000000"), StyleConstants.ALIGN_JUSTIFIED, 10);
        }
        for (int i = 0; i < atributosEscolhidosGlyphContinuo.size(); i++) {
            Util.appendToPane(legendas, atributosEscolhidosGlyphContinuo.get(i) + "\n", Color.decode(Constantes.getColorContinuousGlyphs()[i]), StyleConstants.ALIGN_JUSTIFIED, 12);
        }

        legendas.setEditable(false);
        painel.add(legendas);
        return painel;
    }

    public void setAtributosEscolhidosGlyphCategorico(HashMap<Constantes.VAR_VISUAIS_CATEGORICAS, Object> atributosEscolhidosGlyph) {
        this.atributosEscolhidosGlyph = atributosEscolhidosGlyph;
    }

    private void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    private Rectangle getBounds() {
        return this.bounds;
    }

    public void setAtributosGlyphsContinuos(ArrayList<String> listaContinuos) {
        this.listaContinuos = listaContinuos;
    }
}

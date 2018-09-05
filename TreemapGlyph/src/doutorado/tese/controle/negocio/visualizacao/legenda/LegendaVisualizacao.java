/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.controle.negocio.visualizacao.legenda;

import doutorado.tese.dao.ManipuladorArquivo;
import doutorado.tese.modelo.Coluna;
import doutorado.tese.util.Constantes;
import doutorado.tese.util.Metadados;
import doutorado.tese.controle.negocio.visualizacao.glyph.factorys.variaveisvisuais.GeometryFactory.FORMAS;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 *
 * @author Anderson Soares
 */
public class LegendaVisualizacao {

    ArrayList<Object> atributosEscolhidosGlyph;
    private Rectangle bounds;
    private ArrayList<String> listaContinuos;

    public LegendaVisualizacao(Rectangle bound) {
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
            //TODO Criar legenda de cores continuas.
            IconeLegenda icon = new IconeLegenda();
            icon.setDimensao(Constantes.COR_TREEMAP);
            icon.setMaxValorContIcon(c.maiorMenorValues[0]);
            icon.setMinValorContIcon(c.maiorMenorValues[1]);

            JLabel labelMax = criarLabel(c.maiorMenorValues[1] + "", null);
//            labelMax.setBorder(BorderFactory.createLineBorder(Color.RED));
            painel.add(labelMax);
            labelMax.setHorizontalAlignment(SwingConstants.RIGHT);
            painel.setAlignmentX(labelMax.RIGHT_ALIGNMENT);

            JLabel labelIcone = criarLabel("", icon);
//            labelIcone.setBorder(BorderFactory.createLineBorder(Color.yellow));
            painel.add(labelIcone);
            labelIcone.setVerticalAlignment(SwingConstants.CENTER);
            labelIcone.setHorizontalAlignment(SwingConstants.CENTER);
            painel.setAlignmentX(labelIcone.LEFT_ALIGNMENT);

            JLabel labelMin = criarLabel(c.maiorMenorValues[0] + "", null);
//            labelMin.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            painel.add(labelMin);
            labelMin.setHorizontalAlignment(SwingConstants.LEFT);
            painel.setAlignmentX(labelMin.LEFT_ALIGNMENT);
        } else {
            List<String> dadosDistintos = c.getDadosDistintos();
            for (int i = 0; i < dadosDistintos.size(); i++) {
                IconeLegenda icon = new IconeLegenda();
                icon.setDimensao(Constantes.COR_TREEMAP);
                icon.setValorIcon(Constantes.getCor()[i]);
                JLabel label = criarLabel(dadosDistintos.get(i), icon);
                painel.add(label);
                label.setHorizontalAlignment(SwingConstants.LEFT);
                painel.setAlignmentX(label.LEFT_ALIGNMENT);
            }
        }
        return painel;
    }

    /**
     * Dimensao analisada no treemap representada por um glyph
     *
     * @param dimensao varia de 0 a 4, pois sao 5 dimensoes de analise de glyphs
     * @return
     */
    public JPanel addLegendaDimensao(int dimensao) {
        JPanel painel = new JPanel(new GridLayout(0, 3));
        painel.setBackground(Color.WHITE);
        Coluna c = null;
        List<String> dadosDistintos = null;
        if (dimensao != 5) {
            painel.setBorder(BorderFactory.createTitledBorder(this.atributosEscolhidosGlyph.get(dimensao).toString() + "'s Subtitle"));
            c = ManipuladorArquivo.getColuna(this.atributosEscolhidosGlyph.get(dimensao).toString());
            dadosDistintos = c.getDadosDistintos();
        } else {
            painel.setBorder(BorderFactory.createTitledBorder("Profile" + "'s Subtitle"));
            return criarLegendaContinua(5, painel, listaContinuos);

        }
        painel.setBounds(bounds);
        painel.setVisible(true);

        for (int i = 0; i < dadosDistintos.size(); i++) {
            IconeLegenda icon = new IconeLegenda();
            icon.setDimensao(dimensao);
            switch (dimensao) {
                case 0:
                    icon.setValorIcon(Constantes.TIPO_TEXTURA[i]);
                    break;
                case 1:
                    if (c.getDescription() == Metadados.Descricao.CATEGORICAL) {
                        icon.setValorIcon(Constantes.getCorGlyphs()[i]);
                    }
                    break;
                case 2:
                    icon.setValorIcon(FORMAS.GLYPH_FORMAS.values()[i]);
                    break;
                case 3:
                    icon.setValorIcon(Constantes.LETRAS_ALFABETO[i]);
                    break;
                case 4:
                    icon.setValorIcon(Constantes.NUMEROS[i]);
                    break;
                case 5:
//                    criarLegendaContinua(5,painel);
                    break;
                default:
                    throw new AssertionError();
            }
            if (c.getDescription() == Metadados.Descricao.CATEGORICAL) {
                JLabel label = criarLabel(dadosDistintos.get(i), icon);
                painel.add(label);

                label.setHorizontalAlignment(SwingConstants.LEFT);
                painel.setAlignmentX(label.LEFT_ALIGNMENT);

            }
        }
        if (c.getDescription() == Metadados.Descricao.CONTINUOUS && dimensao == 1) {
            IconeLegenda icon = new IconeLegenda();
            icon.setDimensao(dimensao);
            icon.setMaxValorContIcon(c.maiorMenorValues[0]);
            icon.setMinValorContIcon(c.maiorMenorValues[1]);

            JLabel labelMax = criarLabel(c.maiorMenorValues[1] + "", null);
//            labelMax.setBorder(BorderFactory.createLineBorder(Color.RED));
            painel.add(labelMax);
            labelMax.setHorizontalAlignment(SwingConstants.RIGHT);
            painel.setAlignmentX(labelMax.RIGHT_ALIGNMENT);

            JLabel labelIcone = criarLabel("", icon);
//            labelIcone.setBorder(BorderFactory.createLineBorder(Color.yellow));
            painel.add(labelIcone);
            labelIcone.setVerticalAlignment(SwingConstants.CENTER);
            labelIcone.setHorizontalAlignment(SwingConstants.CENTER);
            painel.setAlignmentX(labelIcone.LEFT_ALIGNMENT);

            JLabel labelMin = criarLabel(c.maiorMenorValues[0] + "", null);
//            labelMin.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            painel.add(labelMin);
            labelMin.setHorizontalAlignment(SwingConstants.LEFT);
            painel.setAlignmentX(labelMin.LEFT_ALIGNMENT);

        }

        return painel;
    }

    public void setAtributosGlyphs(ArrayList<Object> atributosEscolhidosGlyph) {
        this.atributosEscolhidosGlyph = atributosEscolhidosGlyph;
    }

    private void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    private Rectangle getBounds() {
        return this.bounds;
    }

    private JPanel criarLegendaContinua(int dimensao, JPanel painel, ArrayList<String> atributosEscolhidosGlyphContinuo) {
        IconeLegenda icon = new IconeLegenda();
        icon.setDimensao(dimensao);
        icon.setMaxValorContIcon(10);
        icon.setMinValorContIcon(10);
        icon.setAtributosEscolhidosGlyphContinuo(atributosEscolhidosGlyphContinuo);
        JLabel labelIcone = criarLabel("", icon);
//            labelIcone.setBorder(BorderFactory.createLineBorder(Color.yellow));
        painel.add(labelIcone);
        labelIcone.setVerticalAlignment(SwingConstants.CENTER);
        labelIcone.setHorizontalAlignment(SwingConstants.CENTER);
        painel.setAlignmentX(labelIcone.LEFT_ALIGNMENT);

        JTextPane legendas = new JTextPane();
        legendas.setEditable(true);
        legendas.setText("");
        for (int i = 0; i < atributosEscolhidosGlyphContinuo.size(); i++) {
            appendToPane(legendas, atributosEscolhidosGlyphContinuo.get(i) + "\n", Color.decode(Constantes.getCor()[i]));
        }
        
        legendas.setEditable(false);
        painel.add(legendas);
        return painel;
    }
   
    
    
    private void appendToPane(JTextPane tp, String msg, Color c) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }

    public void setAtributosGlyphsontinuos(ArrayList<String> listaContinuos) {
        this.listaContinuos = listaContinuos;
    }
}

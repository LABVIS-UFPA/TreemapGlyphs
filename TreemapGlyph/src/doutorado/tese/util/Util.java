/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JTextPane;
import javax.swing.ListModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 *
 * @author Anderson Soares
 */
public class Util {
    public static String[] parseListModel2ArrayString(ListModel<String> lista) {
        String[] convertida = new String[lista.getSize()];
        for (int i = 0; i < convertida.length; i++) {
            convertida[i] = lista.getElementAt(i);
        }
        return convertida;
    }
    
    public static List<String> parseListModel2ListString(ListModel<String> lista) {
        List<String> convertida = new ArrayList<>();
        for (int i = 0; i < lista.getSize(); i++) {
            convertida.add(lista.getElementAt(i));
        }
        return convertida;
    }
    
    public static void appendToPane(JTextPane tp, String msg, Color c, int alinhamentoStyleConstants, int fontSize) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Arial");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, alinhamentoStyleConstants);
        aset = sc.addAttribute(aset, StyleConstants.FontSize, fontSize);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }
}

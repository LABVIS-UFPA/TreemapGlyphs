/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.util;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ListModel;

/**
 *
 * @author Anderson Soares
 */
public class Conversor {
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
}

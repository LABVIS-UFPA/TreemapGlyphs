/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.controle.negocio.teste;

import doutorado.tese.util.io.Leitor;
import java.io.File;
import java.util.HashMap;

/**
 *
 * @author Anderson Soares
 */
public class ManipuladorLog {

    private HashMap<Integer, String> mapaCliques;

    public ManipuladorLog() {
        mapaCliques = new HashMap<>();
    }

    public void analisarLinas() {
//        File logTreemap = new File("C:\\Users\\Anderson Soares\\Documents\\GitHub\\TreemapGlyphs\\TreemapGlyph\\treemap_log.txt");
//        Leitor.lerArquivo(logTreemap);
//        String[] linhasLogTreemap = Leitor.getLinhas();
//        
//        File logPerguntas = new File("C:\\Users\\Anderson Soares\\Google Drive\\workspace\\java_projects\\perguntasTesteLaboratorio\\perguntas_log.txt");
//        Leitor.lerArquivo(logPerguntas);
//        String[] linhasLogPerguntas = Leitor.getLinhas();
//
//        for (int i = 1; i < linhasLogPerguntas.length; i++) {
//            String[] partesLinhaPerguntas = linhasLogPerguntas[i].split("\\t|,");
//            long tempoInicio = Long.parseLong(partesLinhaPerguntas[1]);
//            long tempoFim = Long.parseLong(partesLinhaPerguntas[2]);
//            for (int j = 1; j < linhasLogTreemap.length; j++) {
//                String[] partesLinhaTreemap = linhasLogTreemap[j].split("\\t|,");
//                long quandoClicou = Long.parseLong(partesLinhaTreemap[1]);
//                if (tempoInicio < quandoClicou && tempoFim > quandoClicou) {
//                    mapaCliques.put(i, String.valueOf(quandoClicou));
//                }
//            }
//        }
//        for (int i = 1; i < linhasLogPerguntas.length; i++) {
//            System.out.println("questao " + i + " - " + mapaCliques.get(i));
//        }
    }
//
    public static void main(String[] args) {
        ManipuladorLog m = new ManipuladorLog();
        m.analisarLinas();
        
    }

    public void montarMapaDados() {

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.controle.negocio.teste;

import doutorado.tese.modelo.TreeMapItem;
import doutorado.tese.util.io.Leitor;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Anderson Soares
 */
public class ManipuladorLog {

    private static HashMap<Integer, String> mapaGabarito;
    private static List<Long> respostaUsuarioTemp;

    private ManipuladorLog() {
    }

    public static void carregarGabarito() {
        setMapaGabarito(new HashMap<>());
        setRespostaUsuarioTemp(new ArrayList<>());
        Leitor.lerArquivo(new File("respostas.tsv"));
        String[] linhas = Leitor.getLinhas();
        for (int i = 1; i < linhas.length; i++) {
            String[] colunas = linhas[i].split("\t");
            getMapaGabarito().put(i, colunas[1]);
        }
    }

    public static boolean verificarResposta(TreeMapItem nodeClicado, int idTarefa) {
        boolean respostaCorreta = false;
        String[] respostas = getMapaGabarito().get(idTarefa).split(",");

        for (String resposta : respostas) {
            if (Integer.parseInt(resposta) == nodeClicado.getId()) {
                respostaCorreta = true;
                System.out.println("Encontrou resposta certa: " + nodeClicado.getId());
                break;
            }
        }
        return respostaCorreta;
    }

    public static boolean verificaQuestaoCorreta(int idTarefa) {
        boolean questaoCorreta = false;
        List<Long> respostasLong = new ArrayList();
        String[] respostas = getMapaGabarito().get(idTarefa).split(",");
        
        for (String resposta : respostas) {
            respostasLong.add(Long.parseLong(resposta));
        }
        
        if (respostas.length == respostaUsuarioTemp.size()) {
            System.out.println("respostasLong: "+respostasLong.toString());
            System.out.println("respostaUsuarioTemp: "+respostaUsuarioTemp.toString());
            questaoCorreta = respostaUsuarioTemp.containsAll(respostasLong);
        }
        System.out.println("Questao correta: " + questaoCorreta);
        return questaoCorreta;
    }
    
//    public static void main(String[] args) {
//        ManipuladorLog.carregarGabarito();
//        ManipuladorLog.getRespostaUsuarioTemp().add(1L);
//        ManipuladorLog.getRespostaUsuarioTemp().add(5L);
//        
//        ManipuladorLog.verificaQuestaoCorreta(1);
//    }

    /**
     * @return the mapaGabarito
     */
    public static HashMap<Integer, String> getMapaGabarito() {
        return mapaGabarito;
    }

    /**
     * @param aMapaGabarito the mapaGabarito to set
     */
    public static void setMapaGabarito(HashMap<Integer, String> aMapaGabarito) {
        mapaGabarito = aMapaGabarito;
    }

    /**
     * @return the respostaUsuarioTemp
     */
    public static List<Long> getRespostaUsuarioTemp() {
        return respostaUsuarioTemp;
    }

    /**
     * @param aRespostaUsuarioTemp the respostaUsuarioTemp to set
     */
    public static void setRespostaUsuarioTemp(List<Long> aRespostaUsuarioTemp) {
        respostaUsuarioTemp = aRespostaUsuarioTemp;
    }

}

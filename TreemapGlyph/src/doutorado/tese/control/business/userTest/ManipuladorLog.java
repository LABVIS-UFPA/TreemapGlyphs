/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.control.business.userTest;

import doutorado.tese.model.TreeMapItem;
import doutorado.tese.model.TreeMapLevel;
import doutorado.tese.model.TreeMapNode;
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
    private static List<Object> respostaUsuarioTemp;
    private static String arquivoGabarito;
    private static boolean testeAcontecendo;
    private static boolean treinamentoAcontecendo;

    private ManipuladorLog() {
    }

    public static void carregarGabarito() {
        setMapaGabarito(new HashMap<>());
        setRespostaUsuarioTemp(new ArrayList<>());
        Leitor.lerArquivo(new File(getArquivoGabarito()));
        String[] linhas = Leitor.getLinhas();
        for (int i = 1; i < linhas.length; i++) {
            String[] colunas = linhas[i].split("\t");
            getMapaGabarito().put(i, colunas[1]);
        }
    }

    public static boolean verificarResposta(TreeMapNode nodeClicado, String respostaCombo, int idTarefa) {
        boolean respostaCorreta = false;
        String[] respostasGabarito = getMapaGabarito().get(idTarefa).split(",");

        for (String resposta : respostasGabarito) {
            if (!respostaCombo.isEmpty()) {
                if (resposta.equalsIgnoreCase(respostaCombo)) {
                    respostaCorreta = true;
                }
            } else if (nodeClicado instanceof TreeMapItem) {
                TreeMapItem node = (TreeMapItem) nodeClicado;
                try {
                    if (Integer.parseInt(resposta) == node.getId()) {
                        respostaCorreta = true;
                        break;
                    }
                } catch (NumberFormatException e) {
                    respostaCorreta = false;
                }
            } else {
                TreeMapLevel node = (TreeMapLevel) nodeClicado;
                if (resposta.equalsIgnoreCase(node.getLabel())) {
                    respostaCorreta = true;
                }
            }
        }
        return respostaCorreta;
    }

    public static boolean verificarResposta(TreeMapNode nodeClicado, int idTarefa) {
        boolean respostaCorreta = false;
        String[] respostasGabarito = getMapaGabarito().get(idTarefa).split(",");

        for (String resposta : respostasGabarito) {
            if (nodeClicado instanceof TreeMapItem) {
                TreeMapItem node = (TreeMapItem) nodeClicado;
                try {
                    if (Integer.parseInt(resposta) == node.getId()) {
                        respostaCorreta = true;
//                        System.out.println("Encontrou resposta certa: " + node.getId());
                        break;
                    }
                } catch (NumberFormatException e) {
                    respostaCorreta = false;
//                    System.out.println("Encontrou resposta ERRADA: Aguardava " + resposta);
                }
            } else {
                TreeMapLevel node = (TreeMapLevel) nodeClicado;
                if (resposta.equalsIgnoreCase(node.getLabel())) {
                    respostaCorreta = true;
//                    System.out.println("Encontrou resposta certa: " + node.getLabel());
                }
            }
        }
        return respostaCorreta;
    }

    public static boolean verificarResposta(String resposta, int idTarefa) {
        boolean respostaCorreta = false;

        String[] respostasGabarito = getMapaGabarito().get(idTarefa).split(",");

        for (String r : respostasGabarito) {
            if (resposta.equalsIgnoreCase(r)) {
                respostaCorreta = true;
            }
        }

        return respostaCorreta;
    }

    public static boolean verificaQuestaoCorreta(int idTarefa) {
        boolean questaoCorreta = false;

        String[] gabarito = getMapaGabarito().get(idTarefa).split(",");
//        List<Long> gabaritoIds = new ArrayList<>();
        List<String> gabaritoList = new ArrayList();
        gabaritoList.addAll(Arrays.asList(gabarito));
        List<String> respostasUsuario = new ArrayList<>();
        respostaUsuarioTemp.forEach((obj) -> {
            respostasUsuario.add(obj.toString());
        });
        if (gabaritoList.size() == respostasUsuario.size()) {
            questaoCorreta = respostasUsuario.containsAll(gabaritoList);
        }
//        if (gabaritoList.size() == respostaUsuarioTemp.size()) {
//            try {
//                gabaritoList.forEach((resposta) -> {
//                    gabaritoIds.add(Long.parseLong(resposta));
//                });
////                System.out.println("respostasLong: " + gabaritoIds.toString());
////                System.out.println("respostaUsuarioTemp: " + respostaUsuarioTemp.toString());
//                questaoCorreta = respostaUsuarioTemp.containsAll(gabaritoIds);
//            } catch (NumberFormatException e) {
////                System.out.println("respostasLong: " + gabaritoList.toString());
////                System.out.println("respostaUsuarioTemp: " + respostaUsuarioTemp.toString());
//                questaoCorreta = respostaUsuarioTemp.containsAll(gabaritoList);
//            }
//        }
//        System.out.println("Questao correta: " + questaoCorreta);
        return questaoCorreta;
    }

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
    public static List<Object> getRespostaUsuarioTemp() {
        return respostaUsuarioTemp;
    }

    /**
     * @param aRespostaUsuarioTemp the respostaUsuarioTemp to set
     */
    public static void setRespostaUsuarioTemp(List<Object> aRespostaUsuarioTemp) {
        respostaUsuarioTemp = aRespostaUsuarioTemp;
    }

    /**
     * @return the arquivoGabarito
     */
    public static String getArquivoGabarito() {
        return arquivoGabarito;
    }

    /**
     * @param aArquivoGabarito the arquivoGabarito to set
     */
    public static void setArquivoGabarito(String aArquivoGabarito) {
        arquivoGabarito = aArquivoGabarito;
    }

    /**
     * @return the testeAcontecendo
     */
    public static boolean isTesteAcontecendo() {
        return testeAcontecendo;
    }

    /**
     * @param aTesteAcontecendo the testeAcontecendo to set
     */
    public static void setTesteAcontecendo(boolean aTesteAcontecendo) {
        testeAcontecendo = aTesteAcontecendo;
    }

    /**
     * @return the treinamentoAcontecendo
     */
    public static boolean isTreinamentoAcontecendo() {
        return treinamentoAcontecendo;
    }

    /**
     * @param aTreinamentoAcontecendo the treinamentoAcontecendo to set
     */
    public static void setTreinamentoAcontecendo(boolean aTreinamentoAcontecendo) {
        treinamentoAcontecendo = aTreinamentoAcontecendo;
    }

}

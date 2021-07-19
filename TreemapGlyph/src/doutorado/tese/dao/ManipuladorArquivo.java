/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.dao;

import doutorado.tese.model.Coluna;
import doutorado.tese.util.Constantes;
import doutorado.tese.util.Metadados;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author Anderson Soares
 */
public class ManipuladorArquivo {

    private Map<String, String> mapaCabecalho = new HashMap<>();
//    private Map<String, int[]> mapaMaiorMenor;
    private String[] cabecalho = {};
    private String[] tipos = {};
    private File file;
    private Charset charset;
    private StringBuilder bufferArquivo;
    private static Coluna[] colunas;
    private String[] linhas;
    private String extensaoArquivo;
    private String delimitador = "";

    public ManipuladorArquivo() {
        charset = Charset.forName("iso-8859-1");//iso-8859-1
        bufferArquivo = new StringBuilder();
    }

    public String getExtensionFile(String fileName) {
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }
        return extension;
    }

    public void lerArquivo(File arquivo) {
        file = arquivo;
        extensaoArquivo = getExtensionFile(file.getName());
        try (BufferedReader reader = Files.newBufferedReader(file.toPath(), charset)) {
            String line = null;
            int numLinha = 0;

            while ((line = reader.readLine()) != null) {
                if (numLinha == 0) {
                    cabecalho = getCabecalhoTipoDado(line, numLinha);
                }
                if (numLinha == 1) {
                    tipos = getCabecalhoTipoDado(line, numLinha);
                    montarMapaCabecalhoTipos(getCabecalho(), tipos);
                }
                if (numLinha <= 1) {//feito para ler apenas as linhas que nao sao cabecalhos (linha 0 e 1)
                    bufferArquivo.append(line).append("\n");
                } else {
                    switch (delimitador) {
                        case ",":
                            bufferArquivo.append(line).append(",").append(Constantes.VALUE_SAME_SIZE).append("\n");
                            break;
                        case ";":
                            bufferArquivo.append(line).append(";").append(Constantes.VALUE_SAME_SIZE).append("\n");
                            break;
                        default:
                            bufferArquivo.append(line).append(Constantes.VALUE_SAME_SIZE).append("\n");
                            break;
                    }
                }
                numLinha++;
            }
            linhas = bufferArquivo.toString().split("\n");
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    /**
     * Responsavel por capturar os dados da coluna especificada
     *
     * @param nomeColuna
     * @return
     */
    public String[] getDadosColuna(String nomeColuna) {
        boolean erro = false;
        String[] dados = new String[getLinhas().length - 2];
        for (int numLinha = 2; numLinha < getLinhas().length; numLinha++) {
            String[] vetorLinha = getDadosLinha(numLinha);

            for (int j = 0; j < cabecalho.length; j++) {
                if (cabecalho[j].equalsIgnoreCase(nomeColuna)) {
                    try {
                        dados[numLinha - 2] = vetorLinha[j];
                        break;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        erro = true;
                        JOptionPane.showMessageDialog(null, "Fail to define the colums. " + nomeColuna,
                                "Erro!", JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                        break;
                    }
                }
            }
            if (erro) {
                break;
            }
        }
        return dados;
    }

    /**
     * Retorna um objeto coluna pelo nome da coluna. Compara usando
     * equalsIgnoreCase()
     *
     * @param nomeColuna String com o nome da coluna
     * @return O objeto coluna pesquisado
     */
    public static Coluna getColuna(String nomeColuna) {
        Coluna c = null;
        for (Coluna coluna : colunas) {
            if (coluna.getName().equalsIgnoreCase(nomeColuna)) {
                c = coluna;
                break;
            }
        }
        return c;
    }

    private String[] getCabecalhoTipoDado(String line, int numLine) {
        List<String> asList = new ArrayList<>();
        if (extensaoArquivo.equalsIgnoreCase("txt") || extensaoArquivo.equalsIgnoreCase("tsv")) {
            asList.addAll(Arrays.asList(line.replace("\"", "").split("\t")));
            delimitador = "\t";
        } else if (extensaoArquivo.equalsIgnoreCase("csv")) {
            asList.clear();
            if (line.contains(",")) {
                delimitador = ",";
                asList.addAll(Arrays.asList(line.replace("\t", ",").split(",")));
            } else if (line.contains(";")) {//deve ser ponto e virgula (;)
                delimitador = ";";
                asList.addAll(Arrays.asList(line.replace("\t", ";").split(";")));
            }
        }
        if (numLine == 0) {
            asList.add("SAME_SIZE");
        } else {
            asList.add("Integer");
        }
        String[] cabecalhoLocal = new String[asList.size()];
        for (int i = 0; i < cabecalhoLocal.length; i++) {
            cabecalhoLocal[i] = asList.get(i);
        }
        return cabecalhoLocal;
    }

//    private String[] getCabecalhoArquivo(String line) {
//        List<String> asList = new ArrayList<>();
//        if (extensaoArquivo.equalsIgnoreCase("txt") || extensaoArquivo.equalsIgnoreCase("tsv")) {
//            asList.addAll(Arrays.asList(line.replace("\"", "").split("\t")));
//        } else if (extensaoArquivo.equalsIgnoreCase("csv")) {
//            if (line.contains(",")) {
//                asList.addAll(Arrays.asList(line.replace("\t", ",").split(",")));
//            } else if (line.contains(";")) {//deve ser ponto e virgula (;)
//                asList.addAll(Arrays.asList(line.replace("\t", ";").split(";")));
//            }
//        }
//        asList.add("SAME_SIZE");
//
//        String[] cabecalhoLocal = new String[asList.size()];
//        for (int i = 0; i < cabecalhoLocal.length; i++) {
//            cabecalhoLocal[i] = asList.get(i);
//        }
//        return cabecalhoLocal;
//    }
//
//    private String[] getTiposDadosArquivo(String line) {
//        List<String> asList = new ArrayList<>();
//        if (extensaoArquivo.equalsIgnoreCase("txt") || extensaoArquivo.equalsIgnoreCase("tsv")) {
//            asList.addAll(Arrays.asList(line.replace("\"", "").split("\t")));
//        } else if (extensaoArquivo.equalsIgnoreCase("csv")) {
//            asList.clear();
//            if (line.contains(",")) {
//                asList.addAll(Arrays.asList(line.replace("\t", ",").split(",")));
//            } else if (line.contains(";")) {//deve ser ponto e virgula (;)
//                asList.addAll(Arrays.asList(line.replace("\t", ";").split(";")));
//            }
//        }
//        asList.add("Integer");//adicionado aqui para ser o tipo do SAME_SIZE
//
//        String[] tiposLocal = new String[asList.size()];
//        for (int i = 0; i < tiposLocal.length; i++) {
//            tiposLocal[i] = asList.get(i);
//        }
//        return tiposLocal;
//    }
    public String[] getDadosLinha(int numLinha) {
        String[] vetorLinha = null;
        if (extensaoArquivo.equalsIgnoreCase("txt") || extensaoArquivo.equalsIgnoreCase("tsv")) {
            vetorLinha = getLinhas()[numLinha].replace("\"", "").split("\t");
        } else if (extensaoArquivo.equalsIgnoreCase("csv")) {
            if (delimitador.equals(",")) {
//                vetorLinha = getLinhas()[numLinha].replace("\t", ",").split(",");
                vetorLinha = getLinhas()[numLinha].split(",");
            } else if (delimitador.equals(";")) {//deve ser ponto e virgula (;)
                vetorLinha = getLinhas()[numLinha].split(";");
            }
        }
        return vetorLinha;
    }

    private void montarMapaCabecalhoTipos(String[] cabecalho, String[] tipos) {
        for (int i = 0; i < cabecalho.length; i++) {
            getMapaCabecalho().put(cabecalho[i], tipos[i]);
        }
    }

    /**
     * Hashmap key = Nome da Coluna --> value = Tipo de dado da coluna
     *
     * @return Mapa (Coluna --> Tipo)
     */
    public Map<String, String> getMapaCabecalho() {
        return mapaCabecalho;
    }

    /**
     * @return the cabecalho
     */
    public String[] getCabecalho() {
        return cabecalho;
    }

    /**
     * @return the bufferArquivo
     */
    public StringBuilder getBufferArquivo() {
        return bufferArquivo;
    }

    public void montarColunas(String[] nomeColunas, String[] tipos) throws Exception {
        colunas = new Coluna[cabecalho.length];
        for (int i = 0; i < nomeColunas.length; i++) {
            String type = "";
            if (tipos[i].equalsIgnoreCase(Metadados.TipoDados.String.name())) {
                type = Metadados.TipoDados.String.name();
            } else if (tipos[i].equalsIgnoreCase(Metadados.TipoDados.Double.name())) {
                type = Metadados.TipoDados.Double.name();
            } else if (tipos[i].equalsIgnoreCase(Metadados.TipoDados.Integer.name())) {
                type = Metadados.TipoDados.Integer.name();
            } else if (tipos[i].equalsIgnoreCase(Metadados.TipoDados.Float.name())) {
                type = Metadados.TipoDados.Float.name();
            } else if (tipos[i].equalsIgnoreCase(Metadados.TipoDados.Boolean.name())) {
                type = Metadados.TipoDados.Boolean.name();
            } else {
                System.err.println("nome coluna: " + nomeColunas[i]);
                System.err.println("tipo: " + tipos[i]);
                System.err.println("Fail to create the column, the type does not exist!");
            }
            colunas[i] = new Coluna(this, nomeColunas[i], type);
        }
    }

    /**
     * Metodo para criar colunas APENAS para o teste de escalabilidade
     *
     * @return Retorna um vetor com tres colunas
     * @throws Exception
     */
    public static Coluna[] montarColunas() throws Exception {
        colunas = new Coluna[3];
        for (int i = 0; i < colunas.length; i++) {
            Coluna c = new Coluna("a" + (i + 1));
            colunas[i] = c;
        }
        return colunas;
    }

    /**
     * @return the colunas
     */
    public Coluna[] getColunas() {
        return colunas;
    }

    public String[] getTipos() {
        return tipos;
    }

//    public Class[] getClassTipos() {
//        Class[] classes = new Class[tipos.length];
//        for (int i = 0; i < tipos.length; i++) {
//            switch (tipos[i]) {
//                case "String":
//                    classes[i] = String.class;
//                    break;
//                case "Integer":
//                    classes[i] = Integer.class;
//                    break;
//                case "Double":
//                    classes[i] = Double.class;
//                    break;
//                case "Float":
//                    classes[i] = Float.class;
//                    break;
//                case "Boolean":
//                    classes[i] = Boolean.class;
//                    break;
//            }
//        }
//        return classes;
//    }
    /**
     * @return Total de linhas do arquivo, incluindo cabeçalho e linha de tipos
     * (caso tenha)
     */
    public String[] getLinhas() {
        return linhas;
    }

    /**
     * Responsavel por fazer a analise do tipo do dado. Essa analise e
     * importante para que o DataModel que sera enviado ao treemap possa
     * carregar o treemap corretamente.
     *
     * @param dado
     * @param type
     * @return O dado convertido em seu tipo original
     */
//    public static Object verificarTipoDado(String dado, Class type) {
//        Object valorConvertido = null;
//        if (dado != null && dado.length() != 0) {
//            if (type.equals(Integer.class)) {
//                valorConvertido = Integer.parseInt(dado);
//            } else if (type.equals(Double.class)) {
//                valorConvertido = Double.parseDouble(dado);
//            } else if (type.equals(Float.class)) {
//                valorConvertido = Float.parseFloat(dado);
//            } else {
//                valorConvertido = dado;
//            }
//        } else {
//            valorConvertido = null;
//        }
//        return valorConvertido;
//    }
//    public Object[][] montarMatrizDados() {
//        Object[][] data = new Object[getLinhas().length - 2][getCabecalho().length];
//        for (int lin = 0; lin < getLinhas().length - 2; lin++) {
//            String[] linha = getDadosLinha(lin + 2);
//            for (int col = 0; col < linha.length; col++) {
//                Object valorConvertido = verificarTipoDado(linha[col], getClassTipos()[col]);
//                data[lin][col] = valorConvertido;
//            }
//        }
//        return data;
//    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.io;

import doutorado.tese.util.Coluna;
import doutorado.tese.util.Constantes;
import doutorado.tese.visualizacao.glyph.Glyph;
import doutorado.tese.visualizacao.glyph.GlyphConcrete;
import doutorado.tese.visualizacao.treemap.TreeMapItem;
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
    private TreeMapItem[] itensTreemap;
    private String[] linhas;
    private String extensaoArquivo;

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
                    cabecalho = montarCabecalho(line);
                }
                if (numLinha == 1) {
                    tipos = desvendarTiposDados(line);
                    montarMapaCabecalhoTipos(getCabecalho(), tipos);
                }
//                montarColunas(cabecalho, tipos);
                if (numLinha <= 1) {
                    bufferArquivo.append(line).append("\n");
                } else {
                    bufferArquivo.append(line).append(Constantes.VALUE_SAME_SIZE).append("\n");
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
//        String[] linhas = bufferArquivo.toString().split("\n");
        String[] dados = new String[getLinhas().length - 2];
        for (int numLinha = 2; numLinha < getLinhas().length; numLinha++) {
            String[] vetorLinha = null;
            if (extensaoArquivo.equalsIgnoreCase("txt")) {
                vetorLinha = getLinhas()[numLinha].split("\t");
            } else if (extensaoArquivo.equalsIgnoreCase("csv")) {
                vetorLinha = getLinhas()[numLinha].split(",");
            }
            for (int j = 0; j < cabecalho.length; j++) {
                if (cabecalho[j].equalsIgnoreCase(nomeColuna)) {
                    dados[numLinha - 2] = vetorLinha[j];
                    break;
                }
            }
        }
        return dados;
    }

    public String[] getDadosLinha(int numLinha) {
        String[] vetorLinha = null;
        if (extensaoArquivo.equalsIgnoreCase("txt")) {
            vetorLinha = getLinhas()[numLinha].split("\t");
        } else if (extensaoArquivo.equalsIgnoreCase("csv")) {
            vetorLinha = getLinhas()[numLinha].split(",");
        }
        return vetorLinha;
    }

//    public Object[] getDadosByLinha(int numLinha) {
//        System.out.println("Ainda é usado...");
//        String[] vetorLinha = getLinhas()[numLinha].split("\t|,");
//        return vetorLinha;
//    }
    /**
     * Retorna um objeto coluna pelo nome da coluna
     *
     * @param nomeColuna String com o nome da coluna
     * @return O objeto coluna pesquisado
     */
    public static Coluna getColuna(String nomeColuna) {
        Coluna c = null;
        for (Coluna coluna : colunas) {
            if (coluna.getName().equalsIgnoreCase(nomeColuna)) {
                c = coluna;
            }
        }
        return c;
    }

    private String[] montarCabecalho(String line) {
        List<String> asList = new ArrayList<>();

        if (extensaoArquivo.equalsIgnoreCase("txt")) {
            asList.addAll(Arrays.asList(line.split("\t")));
        } else if (extensaoArquivo.equalsIgnoreCase("csv")) {
            asList.addAll(Arrays.asList(line.split(",")));
        }
        asList.add("SAME_SIZE");

        String[] cabecalhoLocal = new String[asList.size()];
        for (int i = 0; i < cabecalhoLocal.length; i++) {
            cabecalhoLocal[i] = asList.get(i);
        }
        return cabecalhoLocal;
    }

    private String[] desvendarTiposDados(String line) {
        List<String> asList = new ArrayList<>();
        if (extensaoArquivo.equalsIgnoreCase("txt")) {
            asList.addAll(Arrays.asList(line.split("\t")));
        } else if (extensaoArquivo.equalsIgnoreCase("csv")) {
            asList.addAll(Arrays.asList(line.split(",")));
        }
        asList.add("Integer");

        String[] tiposLocal = new String[asList.size()];
        for (int i = 0; i < tiposLocal.length; i++) {
            tiposLocal[i] = asList.get(i);
        }
        return tiposLocal;
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

    public Coluna[] montarColunas(String[] nomeColunas, String[] tipos) {
        colunas = new Coluna[cabecalho.length];
        for (int i = 0; i < nomeColunas.length; i++) {
            colunas[i] = new Coluna(this, nomeColunas[i], tipos[i]);
        }
        return colunas;
    }

    public Coluna[] montarColunas() {
        colunas = new Coluna[cabecalho.length];
        for (int i = 0; i < cabecalho.length; i++) {
            Coluna c = new Coluna(this, cabecalho[i], getMapaCabecalho().get(cabecalho[i]));
//            c.setDadosColuna(getDadosColuna(cabecalho[i]));
            c.configurarDescricao(getDadosColuna(cabecalho[i]));
            colunas[i] = c;
        }
        return getColunas();
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

    public Class[] getClassTipos() {
        Class[] classes = new Class[tipos.length];
        for (int i = 0; i < tipos.length; i++) {
            switch (tipos[i]) {
                case "String":
                    classes[i] = String.class;
                    break;
                case "Integer":
                    classes[i] = Integer.class;
                    break;
                case "Double":
                    classes[i] = Double.class;
                    break;
                case "Float":
                    classes[i] = Float.class;
                    break;
                case "Boolean":
                    classes[i] = Boolean.class;
                    break;
            }
        }
        return classes;
    }

    /**
     * Retorna todos os TreemapItens do treemap
     * @return the itensTreemap
     */
    public TreeMapItem[] getItensTreemap() {
        return itensTreemap;
    }

    /**
     * @param itensTreemap the itensTreemap to set
     */
    public void setItensTreemap(TreeMapItem[] itensTreemap) {
        this.itensTreemap = itensTreemap;
    }

    /**
     * Metodo que cria cada item do treemap, associa os dados da base de dados ao
     * item correspondete, e define um objeto GlyphConcreto para esse item.
     */
    public void carregarItensTreemap() {
        int totalItens = getDadosColuna(getCabecalho()[0]).length;
        itensTreemap = new TreeMapItem[totalItens];
        for (int linha = 0; linha < totalItens; linha++) {
            String[] dadosLinha = getDadosLinha(linha + 2);
            TreeMapItem itemLocal = null;
            try {
                itemLocal = new TreeMapItem(1, 0);
                Glyph glyphConcrete = new GlyphConcrete();
                glyphConcrete.setNodeTreemap(itemLocal);
                itemLocal.setGlyph(glyphConcrete);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            System.out.print("linha:" + linha);
            for (int coluna = 0; coluna < dadosLinha.length; coluna++) {
//                System.out.print("\t[" + coluna + "]: " + dadosLinha[coluna]);
                itemLocal.getMapaDetalhesItem().put(getColunas()[coluna], dadosLinha[coluna]);
            }
//            System.out.println("");
            itensTreemap[linha] = itemLocal;
        }
    }

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
    public static Object verificarTipoDado(String dado, Class type) {
        Object valorConvertido = null;
        if (dado != null && dado.length() != 0) {
            if (type.equals(Integer.class)) {
                valorConvertido = Integer.parseInt(dado);
            } else if (type.equals(Double.class)) {
                valorConvertido = Double.parseDouble(dado);
            } else if (type.equals(Float.class)) {
                valorConvertido = Float.parseFloat(dado);
            } else {
                valorConvertido = dado;
            }
        } else {
            valorConvertido = null;
        }
        return valorConvertido;
    }

    public Object[][] montarMatrizDados() {
        Object[][] data = new Object[getLinhas().length - 2][getCabecalho().length];
        for (int lin = 0; lin < getLinhas().length - 2; lin++) {
            String[] linha = getDadosLinha(lin + 2);
            for (int col = 0; col < linha.length; col++) {
                Object valorConvertido = verificarTipoDado(linha[col], getClassTipos()[col]);
                data[lin][col] = valorConvertido;
            }
        }
        return data;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.util.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Anderson Soares
 */
public class Leitor {

    private static Charset charset;
    private static StringBuilder bufferArquivo;
    private static String[] linhas;
    private static File file;
    private static String extensaoArquivo;
    private static List<String> cabecalho;

    public Leitor() {
    }
    
    public static String getExtensionFile(String fileName) {
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }
        return extension;
    }
    
    public static StringBuilder lerArquivo(File arquivo) {
        charset = Charset.forName("iso-8859-1");
        setBufferArquivo(new StringBuilder());
        file = arquivo;
        extensaoArquivo = getExtensionFile(file.getName());
        try (BufferedReader reader = Files.newBufferedReader(file.toPath(), charset)) {
            String line = null;
            int numLinha = 0;
            
            while ((line = reader.readLine()) != null) {
                if (numLinha == 0) {
                    setCabecalho(montarCabecalho(line));
                }
                
                getBufferArquivo().append(line).append("\n");
                numLinha++;
            }
            setLinhas(getBufferArquivo().toString().split("\n"));
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        return getBufferArquivo();
    }
    
    private static List<String> montarCabecalho(String line) {
        setCabecalho(new ArrayList<>());

        if (extensaoArquivo.equalsIgnoreCase("txt") || extensaoArquivo.equalsIgnoreCase("tsv")) {
            getCabecalho().addAll(Arrays.asList(line.replace("\"", "").split("\t")));
        } else if (extensaoArquivo.equalsIgnoreCase("csv")) {
            getCabecalho().addAll(Arrays.asList(line.split(",")));
        }
        return getCabecalho();
    }
    
    public String[] getDadosLinha(int numLinha) {
        String[] vetorLinha = null;
        if (extensaoArquivo.equalsIgnoreCase("txt") || extensaoArquivo.equalsIgnoreCase("tsv")) {
            vetorLinha = getLinhas()[numLinha].replace("\"", "").split("\t");
        } else if (extensaoArquivo.equalsIgnoreCase("csv")) {
            vetorLinha = getLinhas()[numLinha].split(",");
        }
        return vetorLinha;
    }

    /**
     * @return the bufferArquivo
     */
    public static StringBuilder getBufferArquivo() {
        return bufferArquivo;
    }

    /**
     * @param aBufferArquivo the bufferArquivo to set
     */
    public static void setBufferArquivo(StringBuilder aBufferArquivo) {
        bufferArquivo = aBufferArquivo;
    }

    /**
     * @return the cabecalho
     */
    public static List<String> getCabecalho() {
        return cabecalho;
    }

    /**
     * @param aCabecalho the cabecalho to set
     */
    public static void setCabecalho(List<String> aCabecalho) {
        cabecalho = aCabecalho;
    }

    /**
     * @return the linhas
     */
    public static String[] getLinhas() {
        return linhas;
    }

    /**
     * @param aLinhas the linhas to set
     */
    public static void setLinhas(String[] aLinhas) {
        linhas = aLinhas;
    }
    
}

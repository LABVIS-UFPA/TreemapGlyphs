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

/**
 *
 * @author Anderson Soares
 */
public class Leitor {

    private final Charset charset;
    private StringBuilder bufferArquivo;
    private String[] linhas;

    public Leitor() {
        charset = Charset.forName("iso-8859-1");
        bufferArquivo = new StringBuilder();
    }

    public StringBuilder lerArquivo(File arquivo) {

        try (BufferedReader reader = Files.newBufferedReader(arquivo.toPath(), charset)) {
            String line = null;
            int numLinha = 0;

            while ((line = reader.readLine()) != null) {
//                if (numLinha == 0) {
//                    cabecalho = montarCabecalho(line);
//                }
//                if (numLinha == 1) {
//                    tipos = desvendarTiposDados(line);
//                    montarMapaCabecalhoTipos(getCabecalho(), tipos);
//                }

                bufferArquivo.append(line).append("\n");
                numLinha++;
            }
            linhas = bufferArquivo.toString().split("\n");
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        return bufferArquivo;
    }
    
}

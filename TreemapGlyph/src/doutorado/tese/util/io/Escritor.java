/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.util.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 *
 * @author Anderson Soares
 */
public class Escritor {

    public static void escreverArquivo(String nomeArquivo, String texto) {
        Calendar calendar = new GregorianCalendar(Locale.getDefault());
        Date trialTime = new Date();
        calendar.setTime(trialTime);
        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DATE);
        try {
            File file = new File(System.getProperty("user.name") + "_"
                    + nomeArquivo + ano + mes + dia +".csv"); // Criação do arquivo
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw); // BufferedWriter 
            bw.write(texto); // Inserção bufferizada da String texto1 no arquivo file.txt
            bw.newLine(); // Inserção bufferizada de quebra de linha no arquivo file.txt
            bw.flush();
            bw.close();//Imprime Texto da segunda linha do arquivo
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

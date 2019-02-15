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
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anderson Soares
 */
public class Escritor {

    private static String fileName = "";
    private static final String defaltLogstFolder = "logs"+File.separator;
    
    public static void escreverArquivo(String nomeArquivo, String texto) {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonth().getValue();
        int dayOfMonth = now.getDayOfMonth();
        int hour = now.getHour();
        int min = now.getMinute();
        int sec = now.getSecond();
        fileName = System.getProperty("user.name") + "_" + nomeArquivo;
        String nameSuffix = year +"_"+ month +"_"+ dayOfMonth + "_" + hour + "_" + min + "_" + sec;
        try {
            createLogDir();
            File file = new File(defaltLogstFolder + fileName + nameSuffix + ".txt"); // Criação do arquivo
            FileWriter fw = new FileWriter(file);
            try (BufferedWriter bw = new BufferedWriter(fw)) {// BufferedWriter
                bw.write(texto); // Insercao bufferizada da String texto1 no arquivo file.txt
                bw.newLine(); // Insercao bufferizada de quebra de linha no arquivo file.txt
                bw.flush();
            } 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createLogDir(){
        File f = new File(defaltLogstFolder);
        if(!f.exists()){
            f.mkdir();
        }
    }
    
    private static File[] listOnlyFiles(File file) {
        File[] listFiles = file.listFiles((File pathname) -> {
            String name = pathname.getName().toLowerCase();
            return name.endsWith(".txt") && pathname.isFile();
        });
        return listFiles;
    }

    public static void moveLogFile() {
        File f = new File("logs");
        if (!f.exists()) {
            f.mkdir();
        }
        File folderParent = new File(f.getAbsoluteFile().getParent());
        File[] listFiles = listOnlyFiles(folderParent);
        for (File arquivo : listFiles) {
            String name = arquivo.getName();
//            System.out.println("Original: " + name);
            File newCopiedFile = new File("logs\\" + name);
            try {
                Files.copy(arquivo.toPath(), newCopiedFile.toPath());
            } catch (IOException ex) {
                Logger.getLogger(Escritor.class.getName()).log(Level.SEVERE, null, ex);
            }
            File renamedFile = new File("treemap_log.txt");
            boolean renameTo = arquivo.renameTo(renamedFile);
//            System.out.println("renomeou: " + renameTo);
        }
    }

}

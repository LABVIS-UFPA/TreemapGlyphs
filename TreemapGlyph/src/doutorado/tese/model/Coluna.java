/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.model;

import doutorado.tese.dao.ManipuladorArquivo;
import doutorado.tese.util.Metadados;
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
public class Coluna {

    public double[] maiorMenorValues = new double[2];
    private String name;
    private Metadados.Descricao description;
    private Metadados.TipoDados type;
    private String[] dadosColuna;
    private int quantValoresDistintos = 0;
    private final int categoricalLimitGlyph = 8;
    private ManipuladorArquivo manipulador;
    private Map<String, double[]> mapaMaiorMenor;
    private List<String> dadosDistintos;

    /**
     * 
     * @param name Nome da coluna, assumi-se que o tipo eh Integer
     */
    public Coluna(String name) {
        this.name = name;
        this.type = Metadados.TipoDados.Integer;
        mapaMaiorMenor = new HashMap<>();
    }    
    
    public Coluna(ManipuladorArquivo manipulador) {
        this.manipulador = manipulador;
        mapaMaiorMenor = new HashMap<>();
    }

    public Coluna(ManipuladorArquivo manipulador, String name, String type) {
        this.name = name;
        switch (type) {
            case "Boolean":
                this.type = Metadados.TipoDados.Boolean;
                break;
            case "Double":
                this.type = Metadados.TipoDados.Double;
                break;
            case "Integer":
                this.type = Metadados.TipoDados.Integer;
                break;
            case "String":
                this.type = Metadados.TipoDados.String;
                break;
        }
        this.manipulador = manipulador;
        mapaMaiorMenor = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Metadados.TipoDados getType() {
        return type;
    }

    public void setType(Metadados.TipoDados type) {
        this.type = type;
    }

    public String[] getDadosColuna() {
        return dadosColuna;
    }

    public void setDadosColuna(String[] distinctValues) {
        this.dadosColuna = distinctValues;
    }

    public int getQuantValoresDistintos() {
        return quantValoresDistintos;
    }

    public void setQuantValoresDistintos(int quantValoresDistintos) {
        this.quantValoresDistintos = quantValoresDistintos;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(Metadados.Descricao description) {
        this.description = description;
    }

    /**
     * @return the description
     */
    public Metadados.Descricao getDescription() {
        return description;
    }

    private double findMax(double dadoColuna, double max) {
//        return (dadoColuna >= max) ? dadoColuna : max;        
        return Math.max(dadoColuna, max);
    }

    private double findMin(Object dadoColuna, double min) {
        return Math.min(Double.valueOf(dadoColuna.toString()), min);
    }

    /**
     * @return the mapaMaiorMenor
     */
    public Map<String, double[]> getMapaMaiorMenor() {
        return mapaMaiorMenor;
    }

    private List<String> analisarDadosDistintos(List<String> dados) {
        List<String> oficial = new ArrayList<>();
        List<String> repetidos = new ArrayList<>();
        dados.forEach((s) -> {
            if (oficial.contains(s) || repetidos.contains(s)) {
                repetidos.add(s);
            } else {
                oficial.add(s);
            }
        });
        return oficial;
    }

    public void configurarDescricao(String[] dadosColunas) throws Exception {
        dadosDistintos = analisarDadosDistintos(Arrays.asList(dadosColunas));
        this.quantValoresDistintos = getDadosDistintos().size();
        //pass 1: define description
        if (type.equals(Metadados.TipoDados.String)) {
            this.setDescription(Metadados.Descricao.CATEGORICAL);
        } else if (this.quantValoresDistintos > categoricalLimitGlyph) {
            setDescription(Metadados.Descricao.CONTINUOUS);
        } else {
            setDescription(Metadados.Descricao.CATEGORICAL);
        }
        //pass 2: setValues
        if (getDescription() == Metadados.Descricao.CONTINUOUS
                || (getDescription() == Metadados.Descricao.CATEGORICAL && type.equals(Metadados.TipoDados.Integer))
                || (getDescription() == Metadados.Descricao.CATEGORICAL && type.equals(Metadados.TipoDados.Double))) {
            double higher = Integer.MIN_VALUE;
            double lower = Integer.MAX_VALUE;
            for (int i = 0; i < dadosColunas.length; i++) {
//                System.out.println("linha["+i+"]: "+dadosColunas[i]);
                try {
                    double dado = Double.valueOf(dadosColunas[i]);
                    higher = findMax(dado, higher);
                    lower = findMin(dado, lower);
                    maiorMenorValues[0] = higher;
                    maiorMenorValues[1] = lower;
                    mapaMaiorMenor.put(getName(), maiorMenorValues);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "We can't parse the value: \""+dadosColunas[i]+"\""+
                            ". Column name: "+name, "Erro!", JOptionPane.ERROR_MESSAGE);
                    break;
                }
            }

        }// else {        
        setDadosColuna(dadosColunas);
        //}
    }
    
    /**
     * @return Retorna uma lista com os dados da coluna que sao distintos.
     */
    public List<String> getDadosDistintos() {
        return dadosDistintos;
    }

    @Override
    public String toString() {
        return this.name;
    }

}

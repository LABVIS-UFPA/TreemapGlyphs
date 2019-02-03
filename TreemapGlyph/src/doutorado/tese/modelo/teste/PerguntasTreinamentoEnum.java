/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.modelo.teste;

/**
 *
 * @author Anderson Soares
 */
public enum PerguntasTreinamentoEnum {
    Q1("Encontre o carro cuja posição do motor (POSMOTOR) seja CENTRAL, e com TRAÇÃO na TRASEIRA."),//categorica
    Q2("Encontre o carro de maior VALOR, o carro de maior POTENCIA e o carro de maior PESO movidos à GASOLINA;"),//categorica e continua
    Q3("Encontre o único carro do TIPO SUV."),//categorica
    Q4("Localize o carro de maior RPM, o carro de maior VALOR e o carro de maior POTENCIA;"),//categorica e continua
    Q5("Encontre o ÚNICO carro a ÁLCOOL."),//categorica
    Q6("Encontre os carros à GASOLINA de maior POTENCIA, o de maior RPM e o de maior VALOR."),//categorica e continua
    Q7("Encontre o carro, cuja posição do motor (POSMOTOR) é CENTRAL, e COM TURBO."),//categorica
    Q8("Encontre o carro de maior POTENCIA."),//categorica e continua
    Q9("Encontre o único carro a ALCOOL de DUAS portas (NRPORTAS) e de TRACAO FRONTAL."),//categorica
    Q10("Encontre o item visual anterior cuja a característica é o carro de maior POTENCIA.");//categorica e continua

    private final String q;

    PerguntasTreinamentoEnum(String q) {
        this.q = q;
    }

    public String questao() {
        return q;
    }
}

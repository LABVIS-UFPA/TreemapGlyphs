/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.controle.mb.testelaboratorioMB;

import doutorado.tese.modelo.teste.Pergunta;
import doutorado.tese.modelo.teste.PerguntasTesteEnum;
import doutorado.tese.modelo.teste.PerguntasTreinamentoEnum;

/**
 *
 * @author Anderson Soares
 */
public class PerguntaMB {

    private Pergunta[] perguntasTeste = new Pergunta[12];
    private Pergunta[] perguntasTreinamento = new Pergunta[10];

    public void managerPerguntasTeste() {
        for (int i = 0; i < perguntasTeste.length; i++) {
            perguntasTeste[i] = new Pergunta();
            perguntasTeste[i].setTexto(i + 1 +" - "+PerguntasTesteEnum.values()[i].questao());
        }
    }

    public void managerPerguntasTreinamento() {
        for (int i = 0; i < perguntasTreinamento.length; i++) {
            perguntasTreinamento[i] = new Pergunta();
            perguntasTreinamento[i].setTexto(i + 1 +" - "+PerguntasTreinamentoEnum.values()[i].questao());
        }
    }

    public Pergunta[] getPerguntasTeste() {
        return perguntasTeste;
    }

    public void setPerguntasTeste(Pergunta[] perguntasTeste) {
        this.perguntasTeste = perguntasTeste;
    }

    public Pergunta[] getPerguntasTreinamento() {
        return perguntasTreinamento;
    }

    public void setPerguntasTreinamento(Pergunta[] perguntasTreinamento) {
        this.perguntasTreinamento = perguntasTreinamento;
    }
}

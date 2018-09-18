/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.controle.negocio.testelaboratorioNegocio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Anderson Soares
 */
public class Ambiente {

    public final String AMBIENTE_A = "ambienteA";
    public final String AMBIENTE_B = "ambienteB";
    public final String AMBIENTE_C = "ambienteC";
    public final String AMBIENTE_D = "ambienteD";
    public final String AMBIENTE_E = "ambienteE";
    private Tarefa[] tarefasCat;
    private Tarefa[] tarefasConti;

    public Ambiente() {

    }

    public void verificarTipoAmbiente(String tipoAmbiente) {
        switch (tipoAmbiente) {
            case AMBIENTE_A:
                configurarTarefasAmbienteA();
                break;
            case AMBIENTE_B:
                break;
            case AMBIENTE_C:
                break;
            case AMBIENTE_D:
                break;
            case AMBIENTE_E:
                break;
        }
    }

    public void configurarTarefasAmbienteA() {
        Tarefa[] tarefasCategoricas = carregarTarefasCategoricas();
        configurarTarefa_1_Ambiente_A(tarefasCategoricas);
        configurarTarefa_2_Ambiente_A(tarefasCategoricas);
        configurarTarefa_3_Ambiente_A(tarefasCategoricas);
        configurarTarefa_4_Ambiente_A(tarefasCategoricas);
        configurarTarefa_5_Ambiente_A(tarefasCategoricas);
        configurarTarefa_6_Ambiente_A(tarefasCategoricas);
        setTarefasCat(tarefasCategoricas);

        Tarefa[] tarefasMistas = carregarTarefasMistas();
        configurarTarefa_1_Ambiente_A(tarefasCategoricas);
        //TODO continuar configurando as tarefas de acordo com os ambientes
        setTarefasConti(tarefasMistas);
    }

    public void configurarTarefa_1_Ambiente_A(Tarefa[] tarefasCat) {
        tarefasCat[0].setParametroTamanhoTreemap("VALOR");
//        tarefasCa0[0].setParametroCorGlyph("Potencia");
        tarefasCat[0].setParametroTexturaGlyph("TURBO");
        tarefasCat[0].setParametroCorGlyph("TRACAO");
        tarefasCat[0].setParametroFormaGlyph("TIPO");
        tarefasCat[0].setParametroLetraGlyph("NRPORTAS");
    }

    public void configurarTarefa_2_Ambiente_A(Tarefa[] tarefasCat) {        
        tarefasCat[1].setParametroTamanhoTreemap("SAME_SIZE");
        tarefasCat[1].setParametroCorTreemap("POTENCIA");
        tarefasCat[1].setParametroTexturaGlyph("TURBO");
        tarefasCat[1].setParametroCorGlyph("TRACAO");
        tarefasCat[1].setParametroFormaGlyph("NRPORTAS");
        tarefasCat[1].setParametroLetraGlyph("POSMOTOR");
    }

    public void configurarTarefa_3_Ambiente_A(Tarefa[] tarefasCat) {
        tarefasCat[2].setParametroTamanhoTreemap("POTENCIA");
//        tarefasCat[0].setParametroCorGlyph("Potencia");
        tarefasCat[2].setParametroTexturaGlyph("TURBO");
        tarefasCat[2].setParametroCorGlyph("POSMOTOR");
        tarefasCat[2].setParametroFormaGlyph("NRPORTAS");
        tarefasCat[2].setParametroLetraGlyph("TIPO");
    }

    public void configurarTarefa_4_Ambiente_A(Tarefa[] tarefasCat) {
        tarefasCat[3].setParametroTamanhoTreemap("POTENCIA");
//        tarefasCat[0].setParametroCorGlyph("Potencia");
        tarefasCat[3].setParametroTexturaGlyph("TURBO");
        tarefasCat[3].setParametroCorGlyph("COMBUSTIVEL");
        tarefasCat[3].setParametroFormaGlyph("NRPORTAS");
        tarefasCat[3].setParametroLetraGlyph("ORIGEM");
    }

    public void configurarTarefa_5_Ambiente_A(Tarefa[] tarefasCat) {
        tarefasCat[4].setParametroTamanhoTreemap("NRCILINDROS");
//        tarefasCat[0].setParametroCorGlyph("Potencia");
        tarefasCat[4].setParametroTexturaGlyph("TURBO");
        tarefasCat[4].setParametroCorGlyph("TRACAO");
        tarefasCat[4].setParametroFormaGlyph("NRPORTAS");
        tarefasCat[4].setParametroLetraGlyph("POSMOTOR");
    }

    public void configurarTarefa_6_Ambiente_A(Tarefa[] tarefasCat) {
        tarefasCat[5].setParametroTamanhoTreemap("VALOR");
//        tarefasCat[0].setParametroCorGlyph("Potencia");
        tarefasCat[5].setParametroTexturaGlyph("COMBUSTIVEL");
        tarefasCat[5].setParametroCorGlyph("TURBO");
        tarefasCat[5].setParametroFormaGlyph("NRPORTAS");
        tarefasCat[5].setParametroLetraGlyph("TRACAO");
    }

    private Tarefa[] carregarTarefasCategoricas() {
        int quantTarefas = 6;
        Tarefa[] tarefas = new Tarefa[quantTarefas];
        for (int i = 0; i < quantTarefas; i++) {
            Tarefa t = new Tarefa();
            t.setTipoDados(t.DADOS_CATEGORICO);
            tarefas[i] = t;
        }

        tarefas[0].setTextoTarefa("Encontre o carro cujo valor da variável visual Forma é um quadrado.");
        tarefas[0].setTipoSubTarefaBusca(tarefas[0].ENCONTRAR);

        tarefas[1].setTextoTarefa("Encontre o carro cujo valor da variável visual LETRA é C, com TRAÇÃO na TRASEIRA.");
        tarefas[1].setTipoSubTarefaBusca(tarefas[1].LOCALIZAR);

        tarefas[2].setTextoTarefa("Encontre o único carro COM TURBO.");
        tarefas[2].setTipoSubTarefaBusca(tarefas[2].PROCURAR);

        tarefas[3].setTextoTarefa("Encontre o ÚNICO carro a ÁLCOOL.");
        tarefas[3].setTipoSubTarefaBusca(tarefas[3].EXPLORAR);

        tarefas[4].setTextoTarefa("Encontre o carro, cujo valor da variável visual Letra é C, e COM TURBO.");
        tarefas[4].setTipoSubTarefaBusca(tarefas[4].ENCONTRAR);

        tarefas[5].setTextoTarefa("Encontre o carro cujo valor da variável visual Textura é Horizontal.");
        tarefas[5].setTipoSubTarefaBusca(tarefas[5].LOCALIZAR);
        return tarefas;
    }

    private Tarefa[] carregarTarefasMistas() {
        int quantTarefas = 6;
        Tarefa[] tarefas = new Tarefa[quantTarefas];
        for (int i = 0; i < quantTarefas; i++) {
            Tarefa t = new Tarefa();
            t.setTipoDados(t.DADOS_MISTO);
            tarefas[i] = t;
        }
        tarefas[0].setTextoTarefa("Encontre o carros de maior VALOR, o carro de maior POTENCIA e o carro de maior PESO movidos à GASOLINA;");
        tarefas[0].setTipoSubTarefaBusca(tarefas[0].ENCONTRAR);

        tarefas[1].setTextoTarefa("Encontre o carro de maior RPM, o carro de maior VALOR e o carro de maior POTENCIA;");
        tarefas[1].setTipoSubTarefaBusca(tarefas[1].LOCALIZAR);

        tarefas[2].setTextoTarefa("Encontre os carros à GASOLINA de maior POTENCIA, o de maior RPM e o de maior VALOR;");
        tarefas[2].setTipoSubTarefaBusca(tarefas[2].ENCONTRAR);

        tarefas[3].setTextoTarefa("Encontre o carro de maior POTENCIA;");
        tarefas[3].setTipoSubTarefaBusca(tarefas[3].EXPLORAR);

        tarefas[4].setTextoTarefa("Encontre o item visual anterior cuja a característica é o carro de maior POTENCIA;.");
        tarefas[4].setTipoSubTarefaBusca(tarefas[4].LOCALIZAR);

        tarefas[5].setTextoTarefa("Encontre o carro de maior POTENCIA, o carro de maior RPM e o carro de maior VALOR movidos à GASOLINA;");
        tarefas[5].setTipoSubTarefaBusca(tarefas[5].ENCONTRAR);
        return tarefas;
    }

    /**
     * @return the tarefasCat
     */
    public Tarefa[] getTarefasCat() {
        return tarefasCat;
    }

    /**
     * @param tarefasCat the tarefasCat to set
     */
    public void setTarefasCat(Tarefa[] tarefasCat) {
        this.tarefasCat = tarefasCat;
    }

    /**
     * @return the tarefasConti
     */
    public Tarefa[] getTarefasConti() {
        return tarefasConti;
    }

    /**
     * @param tarefasConti the tarefasConti to set
     */
    public void setTarefasConti(Tarefa[] tarefasConti) {
        this.tarefasConti = tarefasConti;
    }

}

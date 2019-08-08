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
public enum PerguntasTesteEnum {
    Q1("Localize a região em que houve o registro de apenas uma chuva ácida com temperatura baixa."),//categorica
    Q2("Localize as três regiões com mais registros de chuva ácida."),//categorica
    Q3("Localize a região em que registrou a ocorrência de apenas um furacão."),//categorica
    Q4("Encontre (Clique) o registro de furação no nordeste e descubra se o alerta da defesa civil foi ativado."),//categorica
    Q5("Localize a região que ocorreu o registro de chuva base com ventos massivos e que tenham ativado o alerta da defesa civil."),//categorica
    Q6("Na região norte, encontre o registro de furacão que ativou a defesa civil com chuva de PH básico e temperatura baixa."),//categorica
    Q7("Qual o padrão na Intensidade do vento na localidade Rural da região centro-oeste?"),//categorica e continua
    Q8("Durante a primavera, qual região apresentou as menores médias de umidade do ar (%)?"),//categorica e continua
    Q9("Na região norte durante o verão, selecione as três maiores intensidades de vento."),//categorica e continua
    Q10("Na região Norte, durante o outono na localidade rural, selecione o registro (único) de maior volume de chuva e menor intensidade de vento."),//categorica e continua
    Q11("Selecione o único registro da região norte, com temperatura média, que possui ph da chuva elevado e baixa umidade do ar."),//categorica e continua
    Q12("Selecione o único registro da região sul com ph da chuva igual a Zero (0), com a ocorrência de vento forte, na localidade rural."),//categorica e continua
    //Q13("Na região nordeste,selecione o registro(único) de temperatura negativa ");
    Q13("In the northeast region, select the (single) negative temperature log");
    private final String q;

    PerguntasTesteEnum(String q) {
        this.q = q;
    }

    public String questao() {
        return q;
    }
}

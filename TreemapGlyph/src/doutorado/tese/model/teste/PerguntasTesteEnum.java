/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.model.teste;

/**
 *
 * @author Anderson Soares
 */
public enum PerguntasTesteEnum {
    Q1("Localize as regiões que o alerta da defesa civil foi ativado em registros de furacão."),//categorica
    Q2("Localize a região com o maior número de registros de furacoes em localidade rural e o alerta da defesa não ativado."),//categorica
    Q3("Localize o único registro que apresentou intensidade da chuva muito forte, temperatura mediana, e ativou o alerta da defesa civil."),//categorica
    Q4("Localize o único registro de furacão que não ativou o alerta da defesa civil, com temperatura mediana na localidade rural."),//categorica
    Q5("Identifique o registro que apresenta intensidade do vento e da chuva moderado, com temperatura alta, na localidade urbana, e que não ativou o alerta da defesa civil."),//categorica
    Q6("Localize a região que registrou apenas ventos Massivos e Muito Fortes, somente temperaturas Mediana, e não registrou chuvas de PH neutro.");//categorica
//    Q7("Qual o padrão na Intensidade do vento na localidade Rural da região centro-oeste?"),//categorica e continua
//    Q8("Durante a primavera, qual região apresentou as menores médias de umidade do ar (%)?"),//categorica e continua
//    Q9("Na região norte durante o verão, selecione as três maiores intensidades de vento."),//categorica e continua
//    Q10("Na região Norte, durante o outono na localidade rural, selecione o registro (único) de maior volume de chuva e menor intensidade de vento."),//categorica e continua
//    Q11("Selecione o único registro da região norte, com temperatura média, que possui ph da chuva elevado e baixa umidade do ar."),//categorica e continua
//    Q12("Selecione o único registro da região sul com ph da chuva igual a Zero (0), com a ocorrência de vento forte, na localidade rural."),//categorica e continua
//    Q13("Na região nordeste,selecione o registro(único) de temperatura negativa ");
//    Q13("In the northeast region, select the (single) negative temperature log");
    
//    Q1("Locate the region which had only one record of acid rain, with low temperature."),//categorica
//    Q2("Locate the three regions with the most acid rain records."),//categorica
//    Q3("Locate the region that registered only one record of a hurricane."),//categorica
//    Q4("Find a hurricane record in the northeast and discover if the civil defense alert was activated."),//categorica
//    Q5("Locate the region where a basic rain record occurred, with massive winds, and that had the civil defense alert activated."),//categorica
//    Q6("In the North region, find a hurricane record that activated the civil defense alert, with basic PH rain and low temperature."),//categorica
//    Q7("What is the wind intensity pattern at the rural location of the midwest region?"),//categorica e continua
//    Q8("During the spring, which region presented the lowest average air humidity (%)?"),//categorica e continua
//    Q9("In the northern region, during the summer, select the three highest wind intensities."),//categorica e continua
//    Q10("In the North region, during the autumn in the rural location, select the single record with the highest rainfall volume and lowest wind intensity."),//categorica e continua
//    Q11("Select the single record in the north region, with average temperature, which has high PH rain and low air humidity."),//categorica e continua
//    Q12("Select the single record in the south region with PH zero rain, with strong winds, in a rural location."),//categorica e continua
//    Q13("In the Northeast region, select the record (one only) with negative temperature.");
    
    private final String q;

    PerguntasTesteEnum(String q) {
        this.q = q;
    }

    public String questao() {
        return q;
    }
}

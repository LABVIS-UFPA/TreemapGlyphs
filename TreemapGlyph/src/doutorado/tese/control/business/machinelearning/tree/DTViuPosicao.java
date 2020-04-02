package doutorado.tese.control.business.machinelearning.tree;

import static doutorado.tese.util.Constantes.AREA_ORIENTACAO;

/**
 * criterion: entropy; test_size: 0.4; max_depth: 2; min_samples_split: 61;
 * Accuracy: 0.8854166666666666; F1-score: 0.8796815970482682;
 * 
 * @author Anderson Soares
 */
public class DTViuPosicao {

    private static final int NAO = 0;
    private static final int SIM = 1;
    
    private static int findMax(int[] nums) {
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            index = nums[i] > nums[index] ? i : index;
        }
        return index;
    }

    /**
     * Os valores armazenados em cada posicao do vetor representam a confianca 
     * que o algoritmo tem nas respostas.                               <br>
     * Exemplo:                                                         <br>
     *      [0] - NAO - NaoDesenha                                      <br>
     *      [1] - SIM - Desenha                                         <br>
     *      classes[NAO] = X; > classes[SIM] = Y; <br>
     *      se X for maior que Y, entao nao desenha a camada.           <br>
     *                                                                  <br>
     *      classes[0] = X; < classes[1] = Y; <br>
     *      se Y for maior que X, entao desenha a camada.               <br>
     * 
     * @param features
     * @return 
     */
    public static int predict(double[] features) {
        int[] classes = new int[2];

        if (features[AREA_ORIENTACAO] <= 30.5) {
            if (features[AREA_ORIENTACAO] <= 2.5) {
                classes[NAO] = 338;
                classes[SIM] = 1;
            } else {
                classes[NAO] = 150;
                classes[SIM] = 12;
            }
        } else {
            if (features[AREA_ORIENTACAO] <= 156.5) {
                classes[NAO] = 73;
                classes[SIM] = 28;
            } else {
                classes[NAO] = 49;
                classes[SIM] = 69;
            }
        }

        return findMax(classes);
    }

//    public static void main(String[] args) {
//        if (args.length == 2) {
//
//            // Features:
//            double[] features = new double[args.length];
//            for (int i = 0, l = args.length; i < l; i++) {
//                features[i] = Double.parseDouble(args[i]);
//            }
//
//            // Prediction:
//            int prediction = DTViuPosicao.predict(features);
//            System.out.println(prediction);
//
//        } 
//    }
}

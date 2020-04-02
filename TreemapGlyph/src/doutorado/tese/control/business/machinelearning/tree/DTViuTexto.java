package doutorado.tese.control.business.machinelearning.tree;

import static doutorado.tese.util.Constantes.AREA_TEXTO;
import static doutorado.tese.util.Constantes.AREA_VISIVEL_TEXT;

/**
 * criterion: gini; test_size: 0.4; max_depth: 3; min_samples_split: 61;
 * Accuracy: 0.9083333333333333; F1-score: 0.8821814306583328.
 *
 * @author Anderson Soares
 */
public class DTViuTexto {

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
     *      classes[0] = X; > classes[1] = Y; <br>
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

        if (features[AREA_TEXTO] <= 3.5) {
            if (features[AREA_TEXTO] <= 1.5) {
                classes[NAO] = 485;
                classes[SIM] = 0;
            } else {
                if (features[AREA_VISIVEL_TEXT] <= 0.44999998807907104) {
                    classes[NAO] = 22;
                    classes[SIM] = 2;
                } else {
                    classes[NAO] = 23;
                    classes[SIM] = 2;
                }
            }
        } else {
            if (features[AREA_TEXTO] <= 112.5) {
                if (features[AREA_VISIVEL_TEXT] <= 6.75) {
                    classes[NAO] = 37;
                    classes[SIM] = 26;
                } else {
                    classes[NAO] = 33;
                    classes[SIM] = 11;
                }
            } else {
                if (features[AREA_VISIVEL_TEXT] <= 49.94999885559082) {
                    classes[NAO] = 6;
                    classes[SIM] = 26;
                } else {
                    classes[NAO] = 25;
                    classes[SIM] = 22;
                }
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
//            int prediction = DTViuTexto.predict(features);
//            System.out.println(prediction);
//
//        }
//    }
}

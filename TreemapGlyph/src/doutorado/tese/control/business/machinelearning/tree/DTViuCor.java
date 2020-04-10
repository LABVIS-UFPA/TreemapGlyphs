package doutorado.tese.control.business.machinelearning.tree;

import static doutorado.tese.util.Constantes.AREA_COR;

/**
 * criterion: gini; test_size: 0.2; max_depth: 2; min_samples_split: 61; 
 * Accuracy: 0.85 F1-score: 0.8533854166666667;
 * 
 * @author Anderson Soares
 */
public class DTViuCor {
    
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
        
        if (features[AREA_COR] <= 2.5) {
//            if (features[AREA_COR] <= 0.5) {
                classes[NAO] = 436;
                classes[SIM] = 30;
//            } else {
//                classes[NAO] = 78;
//                classes[SIM] = 30;
//            }
        } else {
//            if (features[AREA_COR] <= 56.5) {
                classes[NAO] = 117;
                classes[SIM] = 377;
//            } else {
//                classes[NAO] = 24;
//                classes[SIM] = 188;
//            }
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
//            int prediction = DTViuCor.predict(features);
//            System.out.println(prediction);
//
//        }
//    }
}

package doutorado.tese.control.business.machinelearning.tree;

import static doutorado.tese.util.Constantes.AREA_SHAPE;
import static doutorado.tese.util.Constantes.AREA_VISIVEL_SHAPE;

/**
 * criterion: gini; test_size: 0.2; max_depth: 5; min_samples_split: 61; 
 * Accuracy: 0.8958333333333334; F1-score: 0.896246297562087;
 * 
 * @author Anderson Soares
 */
public class DTViuForma {

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

        if (features[AREA_SHAPE] <= 6.5) {
            if (features[AREA_SHAPE] <= 2.5) {
                if (features[AREA_SHAPE] <= 0.5) {
                    classes[NAO] = 347;
                    classes[SIM] = 0;
                } else {
                    if (features[AREA_VISIVEL_SHAPE] <= 0.15000000596046448) {
                        classes[NAO] = 19;
                        classes[SIM] = 4;
                    } else {
                        classes[NAO] = 79;
                        classes[SIM] = 2;
                    }
                }
            } else {
                if (features[AREA_VISIVEL_SHAPE] <= 0.6000000238418579) {
                    classes[NAO] = 8;
                    classes[SIM] = 4;
                } else {
                    classes[NAO] = 44;
                    classes[SIM] = 10;
                }
            }
        } else {
            if (features[AREA_SHAPE] <= 30.5) {
                if (features[AREA_VISIVEL_SHAPE] <= 1.350000023841858) {
                    classes[NAO] = 14;
                    classes[SIM] = 11;
                } else {
                    if (features[AREA_VISIVEL_SHAPE] <= 3.7500001192092896) {
                        classes[NAO] = 39;
                        classes[SIM] = 17;
                    } else {
                        if (features[AREA_SHAPE] <= 20.5) {
                            classes[NAO] = 8;
                            classes[SIM] = 3;
                        } else {
                            classes[NAO] = 48;
                            classes[SIM] = 19;
                        }
                    }
                }
            } else {
                if (features[AREA_SHAPE] <= 462.5) {
                    if (features[AREA_VISIVEL_SHAPE] <= 5.400000095367432) {
                        classes[NAO] = 5;
                        classes[SIM] = 10;
                    } else {
                        if (features[AREA_VISIVEL_SHAPE] <= 16.950000286102295) {
                            classes[NAO] = 31;
                            classes[SIM] = 28;
                        } else {
                            classes[NAO] = 73;
                            classes[SIM] = 92;
                        }
                    }
                } else {
                    if (features[AREA_VISIVEL_SHAPE] <= 173.0999984741211) {
                        classes[NAO] = 0;
                        classes[SIM] = 12;
                    } else {
                        if (features[AREA_VISIVEL_SHAPE] <= 195.1500015258789) {
                            classes[NAO] = 3;
                            classes[SIM] = 2;
                        } else {
                            classes[NAO] = 6;
                            classes[SIM] = 22;
                        }
                    }
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
//            int prediction = DTViuForma.predict(features);
//            System.out.println(prediction);
//
//        }
//    }
}

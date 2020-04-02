package doutorado.tese.control.business.machinelearning.tree;

import static doutorado.tese.util.Constantes.AREA_TEXTURA;
import static doutorado.tese.util.Constantes.AREA_VISIVEL_TEXTURE;

/**
 * criterion: gini; test_size: 0.3; max_depth: 6; min_samples_split: 61;
 * Accuracy: 0.925; F1-score: 0.907777024737377;
 * 
 * @author Anderson Soares
 */
public class DTViuTextura {

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

        if (features[AREA_VISIVEL_TEXTURE] <= 101.70000076293945) {
            if (features[AREA_TEXTURA] <= 8.5) {
                if (features[AREA_VISIVEL_TEXTURE] <= 1.649999976158142) {
                    if (features[AREA_VISIVEL_TEXTURE] <= 0.15000000596046448) {
                        if (features[AREA_TEXTURA] <= 0.5) {
                            classes[NAO] = 340;
                            classes[SIM] = 7;
                        } else {
                            classes[NAO] = 8;
                            classes[SIM] = 0;
                        }
                    } else {
                        if (features[AREA_TEXTURA] <= 3.5) {
                            if (features[AREA_TEXTURA] <= 1.5) {
                                classes[NAO] = 32;
                                classes[SIM] = 1;
                            } else {
                                classes[NAO] = 27;
                                classes[SIM] = 1;
                            }
                        } else {
                            classes[NAO] = 4;
                            classes[SIM] = 0;
                        }
                    }
                } else {
                    classes[NAO] = 40;
                    classes[SIM] = 2;
                }
            } else {
                if (features[AREA_VISIVEL_TEXTURE] <= 1.649999976158142) {
                    classes[NAO] = 0;
                    classes[SIM] = 1;
                } else {
                    if (features[AREA_VISIVEL_TEXTURE] <= 46.94999885559082) {
                        if (features[AREA_TEXTURA] <= 91.0) {
                            if (features[AREA_TEXTURA] <= 67.5) {
                                classes[NAO] = 114;
                                classes[SIM] = 16;
                            } else {
                                classes[NAO] = 16;
                                classes[SIM] = 5;
                            }
                        } else {
                            if (features[AREA_TEXTURA] <= 131.0) {
                                classes[NAO] = 34;
                                classes[SIM] = 1;
                            } else {
                                classes[NAO] = 13;
                                classes[SIM] = 2;
                            }
                        }
                    } else {
                        if (features[AREA_VISIVEL_TEXTURE] <= 49.94999885559082) {
                            classes[NAO] = 2;
                            classes[SIM] = 4;
                        } else {
                            if (features[AREA_VISIVEL_TEXTURE] <= 57.14999961853027) {
                                classes[NAO] = 8;
                                classes[SIM] = 0;
                            } else {
                                classes[NAO] = 36;
                                classes[SIM] = 8;
                            }
                        }
                    }
                }
            }
        } else {
            if (features[AREA_VISIVEL_TEXTURE] <= 794.25) {
                if (features[AREA_TEXTURA] <= 354.5) {
                    classes[NAO] = 0;
                    classes[SIM] = 4;
                } else {
                    if (features[AREA_TEXTURA] <= 574.5) {
                        if (features[AREA_TEXTURA] <= 359.0) {
                            classes[NAO] = 1;
                            classes[SIM] = 1;
                        } else {
                            if (features[AREA_VISIVEL_TEXTURE] <= 116.8499984741211) {
                                classes[NAO] = 5;
                                classes[SIM] = 0;
                            } else {
                                classes[NAO] = 24;
                                classes[SIM] = 6;
                            }
                        }
                    } else {
                        if (features[AREA_VISIVEL_TEXTURE] <= 632.25) {
                            if (features[AREA_VISIVEL_TEXTURE] <= 434.1000061035156) {
                                classes[NAO] = 28;
                                classes[SIM] = 20;
                            } else {
                                classes[NAO] = 3;
                                classes[SIM] = 10;
                            }
                        } else {
                            classes[NAO] = 6;
                            classes[SIM] = 0;
                        }
                    }
                }
            } else {
                if (features[AREA_VISIVEL_TEXTURE] <= 1224.0) {
                    classes[NAO] = 0;
                    classes[SIM] = 9;
                } else {
                    classes[NAO] = 1;
                    classes[SIM] = 0;
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
//            int prediction = DTViuTextura.predict(features);
//            System.out.println(prediction);
//
//        }
//    }
}

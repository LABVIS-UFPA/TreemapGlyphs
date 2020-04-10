package doutorado.tese.control.business.machinelearning.tree;

import static doutorado.tese.util.Constantes.AREA_TEXTURA;
import static doutorado.tese.util.Constantes.AREA_VISIVEL_TEXTURE;

/**
 * criterion: gini; test_size: 0.3; max_depth: 3; min_samples_split: 61;
 * Accuracy: 0.9944444444444445; F1-score: 0.9944444444444445;
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
     * [0] - NAO - NaoDesenha                                      <br>
     * [1] - SIM - Desenha                                         <br>
     * classes[NAO] = X; > classes[SIM] = Y; <br>
     * se X for maior que Y, entao nao desenha a camada.           <br>
     * <br>
     * classes[NAO] = X; < classes[SIM] = Y; <br> se Y for maior que X, entao
     * desenha a camada.               <br>
     *
     * @param features
     * @return
     */
    public static int predict(double[] features) {
        int[] classes = new int[2];

//        if (features[AREA_TEXTURA] <= 2647.5) {
//            if (features[AREA_VISIVEL_TEXTURE] <= 509.8500061035156) {
//                classes[NAO] = 819; 
//                classes[SIM] = 0; 
//            } else {
//                if (features[AREA_VISIVEL_TEXTURE] <= 632.25) {
//                    classes[NAO] = 1; 
//                    classes[SIM] = 4; 
//                } else {
//                    classes[NAO] = 6; 
//                    classes[SIM] = 0; 
//                }
//            }
//        } else {
//            if (features[AREA_VISIVEL_TEXTURE] <= 1224.0) {
//                classes[NAO] = 0; 
//                classes[SIM] = 9; 
//            } else {
//                classes[NAO] = 1; 
//                classes[SIM] = 0; 
//            }
//        }
        
        if (features[AREA_TEXTURA] <= 6.5) {
            if (features[AREA_TEXTURA] <= 2.5) {
                if (features[AREA_TEXTURA] <= 0.5) {
                    classes[NAO] = 347;
                    classes[SIM] = 0;
                } else {
                    if (features[AREA_VISIVEL_TEXTURE] <= 0.15000000596046448) {
                        classes[NAO] = 19;
                        classes[SIM] = 4;
                    } else {
                        classes[NAO] = 79;
                        classes[SIM] = 2;
                    }
                }
            } else {
                if (features[AREA_VISIVEL_TEXTURE] <= 0.6000000238418579) {
                    classes[NAO] = 8;
                    classes[SIM] = 4;
                } else {
                    classes[NAO] = 44;
                    classes[SIM] = 10;
                }
            }
        } else {
            if (features[AREA_TEXTURA] <= 30.5) {
                if (features[AREA_VISIVEL_TEXTURE] <= 1.350000023841858) {
                    classes[NAO] = 14;
                    classes[SIM] = 11;
                } else {
                    if (features[AREA_VISIVEL_TEXTURE] <= 3.7500001192092896) {
                        classes[NAO] = 39;
                        classes[SIM] = 17;
                    } else {
                        if (features[AREA_TEXTURA] <= 20.5) {
                            classes[NAO] = 8;
                            classes[SIM] = 3;
                        } else {
                            classes[NAO] = 48;
                            classes[SIM] = 19;
                        }
                    }
                }
            } else {
                if (features[AREA_TEXTURA] <= 462.5) {
                    if (features[AREA_VISIVEL_TEXTURE] <= 5.400000095367432) {
                        classes[NAO] = 5;
                        classes[SIM] = 10;
                    } else {
                        if (features[AREA_VISIVEL_TEXTURE] <= 16.950000286102295) {
                            classes[NAO] = 31;
                            classes[SIM] = 28;
                        } else {
                            classes[NAO] = 73;
                            classes[SIM] = 92;
                        }
                    }
                } else {
                    if (features[AREA_VISIVEL_TEXTURE] <= 173.0999984741211) {
                        classes[NAO] = 0;
                        classes[SIM] = 12;
                    } else {
                        if (features[AREA_VISIVEL_TEXTURE] <= 195.1500015258789) {
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
//            int prediction = DTViuTextura.predict(features);
//            System.out.println(prediction);
//
//        }
//    }
}

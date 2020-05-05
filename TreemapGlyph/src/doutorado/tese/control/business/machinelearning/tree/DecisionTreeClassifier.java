/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.control.business.machinelearning.tree;

/**
 *
 * @author Anderson Soares
 *
 * criterion: gini test_size: 0.4 max_depth: 4 min_samples_split: 10
 * accuracy_score: 0.8558718861209964 Precision: 0.7707066988451207 F1-scote:
 * 0.8558718861209964
 */
public class DecisionTreeClassifier {

    private static int findMax(int[] nums) {
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            index = nums[i] > nums[index] ? i : index;
        }
        return index;
    }
    
    public static int predict(double[] features) {
        int[] classes = new int[2];
            
        if (features[0] <= 3.5) {
            if (features[0] <= 2.5) {
                if (features[0] <= 1.5) {
                    classes[0] = 2; 
                    classes[1] = 161; 
                } else {
                    classes[0] = 22; 
                    classes[1] = 141; 
                }
            } else {
                if (features[2] <= 47.5) {
                    classes[0] = 60; 
                    classes[1] = 39; 
                } else {
                    classes[0] = 11; 
                    classes[1] = 51; 
                }
            }
        } else {
            if (features[2] <= 73.0) {
                if (features[0] <= 4.5) {
                    classes[0] = 121; 
                    classes[1] = 28; 
                } else {
                    classes[0] = 294; 
                    classes[1] = 8; 
                }
            } else {
                if (features[0] <= 5.5) {
                    classes[0] = 5; 
                    classes[1] = 24; 
                } else {
                    classes[0] = 13; 
                    classes[1] = 2; 
                }
            }
        }
    
        return findMax(classes);
    }

    public static int predict1(double[] features) {
        int[] classes = new int[2];

        if (features[0] <= 3.5) {
            if (features[0] <= 2.5) {
                if (features[0] <= 1.5) {
                    if (features[2] <= 0.9717948734760284) {
                        classes[0] = 0;
                        classes[1] = 132;
                    } else {
                        classes[0] = 1;
                        classes[1] = 4;
                    }
                } else {
                    if (features[1] <= 6568.0) {
                        classes[0] = 18;
                        classes[1] = 106;
                    } else {
                        classes[0] = 0;
                        classes[1] = 19;
                    }
                }
            } else {
                if (features[1] <= 1818.0) {
                    if (features[2] <= 0.6433946490287781) {
                        classes[0] = 11;
                        classes[1] = 0;
                    } else {
                        classes[0] = 13;
                        classes[1] = 7;
                    }
                } else {
                    if (features[2] <= 0.5373424887657166) {
                        classes[0] = 20;
                        classes[1] = 11;
                    } else {
                        classes[0] = 18;
                        classes[1] = 59;
                    }
                }
            }
        } else {
            if (features[1] <= 7000.5) {
                if (features[0] <= 4.5) {
                    if (features[1] <= 3545.0) {
                        classes[0] = 76;
                        classes[1] = 10;
                    } else {
                        classes[0] = 29;
                        classes[1] = 14;
                    }
                } else {
                    if (features[1] <= 4285.0) {
                        classes[0] = 202;
                        classes[1] = 0;
                    } else {
                        classes[0] = 55;
                        classes[1] = 9;
                    }
                }
            } else {
                if (features[0] <= 5.5) {
                    if (features[2] <= 0.8381987512111664) {
                        classes[0] = 2;
                        classes[1] = 7;
                    } else {
                        classes[0] = 0;
                        classes[1] = 10;
                    }
                } else {
                    classes[0] = 8;
                    classes[1] = 1;
                }
            }
        }

        return findMax(classes);
    }

//    public static void main(String[] args) {
//        if (args.length == 3) {
//
//            // Features:
//            double[] features = new double[args.length];
//            for (int i = 0, l = args.length; i < l; i++) {
//                features[i] = Double.parseDouble(args[i]);
//            }
//
//            // Prediction:
//            int prediction = DecisionTreeClassifier.predict(features);
//            System.out.println(prediction);
//
//        }
//    }
}

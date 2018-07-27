package doutorado.tese.util.machinelearning.tree;

import doutorado.tese.util.Constantes;

public class DTViuTextura {

    private static int findMax(int[] nums) {
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            index = nums[i] > nums[index] ? i : index;
        }
        return index;
    }

    public static int predict(double[] features) {
        int[] classes = new int[2];
            
        if (features[10] <= 182.5) {
            if (features[10] <= 56.5) {
                classes[0] = 332; 
                classes[1] = 0; 
            } else {
                classes[0] = 21; 
                classes[1] = 7; 
            }
        } else {
            if (features[5] <= 25.5) {
                classes[0] = 15; 
                classes[1] = 15; 
            } else {
                if (features[7] <= 1397.0) {
                    if (features[11] <= 240.5) {
                        classes[0] = 2; 
                        classes[1] = 23; 
                    } else {
                        classes[0] = 9; 
                        classes[1] = 17; 
                    }
                } else {
                    if (features[5] <= 56.5) {
                        if (features[11] <= 380.5) {
                            classes[0] = 0; 
                            classes[1] = 61; 
                        } else {
                            if (features[11] <= 870.5) {
                                if (features[11] <= 650.5) {
                                    classes[0] = 3; 
                                    classes[1] = 29; 
                                } else {
                                    classes[0] = 7; 
                                    classes[1] = 20; 
                                }
                            } else {
                                classes[0] = 2; 
                                classes[1] = 35; 
                            }
                        }
                    } else {
                        classes[0] = 0; 
                        classes[1] = 202; 
                    }
                }
            }
        }
    
        return findMax(classes);
    }

    public static void main(String[] args) {
        if (args.length == 15) {

            // Features:
            double[] features = new double[args.length];
            for (int i = 0, l = args.length; i < l; i++) {
                features[i] = Double.parseDouble(args[i]);
            }

            // Prediction:
            int prediction = DTViuTextura.predict(features);
            System.out.println(prediction);

        }
    }
}
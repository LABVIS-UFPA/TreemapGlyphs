package doutorado.tese.controle.negocio.machinelearning.tree;

public class DTViuLetra {

    private static int findMax(int[] nums) {
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            index = nums[i] > nums[index] ? i : index;
        }
        return index;
    }

    public static int predict(double[] features) {
        int[] classes = new int[2];
            
        if (features[13] <= 100.5) {
            if (features[13] <= 51.5) {
                if (features[13] <= 43.5) {
                    if (features[8] <= 0.95959186554) {
                        classes[0] = 481; 
                        classes[1] = 0; 
                    } else {
                        classes[0] = 33; 
                        classes[1] = 1; 
                    }
                } else {
                    classes[0] = 25; 
                    classes[1] = 2; 
                }
            } else {
                if (features[6] <= 61.5) {
                    if (features[7] <= 2825.5) {
                        if (features[7] <= 2365.5) {
                            classes[0] = 29; 
                            classes[1] = 3; 
                        } else {
                            classes[0] = 28; 
                            classes[1] = 0; 
                        }
                    } else {
                        classes[0] = 23; 
                        classes[1] = 7; 
                    }
                } else {
                    classes[0] = 18; 
                    classes[1] = 21; 
                }
            }
        } else {
            if (features[12] <= 650.5) {
                if (features[13] <= 138.0) {
                    classes[0] = 8; 
                    classes[1] = 14; 
                } else {
                    classes[0] = 2; 
                    classes[1] = 20; 
                }
            } else {
                if (features[8] <= 0.947790563107) {
                    if (features[6] <= 62.0) {
                        classes[0] = 1; 
                        classes[1] = 19; 
                    } else {
                        classes[0] = 0; 
                        classes[1] = 45; 
                    }
                } else {
                    classes[0] = 3; 
                    classes[1] = 17; 
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
            int prediction = DTViuLetra.predict(features);
            System.out.println(prediction);

        }
    }
}
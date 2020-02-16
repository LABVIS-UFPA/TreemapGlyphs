package doutorado.tese.control.business.machinelearning.tree;

public class DTViuNumero {

    private static int findMax(int[] nums) {
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            index = nums[i] > nums[index] ? i : index;
        }
        return index;
    }

    public static int predict(double[] features) {
        int[] classes = new int[2];
            
        if (features[14] <= 100.5) {
            if (features[14] <= 57.0) {
                if (features[5] <= 67.5) {
                    classes[0] = 467; 
                    classes[1] = 0; 
                } else {
                    if (features[7] <= 3758.5) {
                        classes[0] = 17; 
                        classes[1] = 3; 
                    } else {
                        classes[0] = 70; 
                        classes[1] = 0; 
                    }
                }
            } else {
                if (features[11] <= 1122.5) {
                    if (features[6] <= 53.5) {
                        if (features[8] <= 0.773504257202) {
                            classes[0] = 24; 
                            classes[1] = 0; 
                        } else {
                            classes[0] = 29; 
                            classes[1] = 4; 
                        }
                    } else {
                        if (features[12] <= 380.5) {
                            classes[0] = 19; 
                            classes[1] = 2; 
                        } else {
                            classes[0] = 12; 
                            classes[1] = 8; 
                        }
                    }
                } else {
                    classes[0] = 13; 
                    classes[1] = 14; 
                }
            }
        } else {
            if (features[12] <= 650.5) {
                classes[0] = 10; 
                classes[1] = 26; 
            } else {
                if (features[7] <= 4611.0) {
                    classes[0] = 2; 
                    classes[1] = 28; 
                } else {
                    classes[0] = 0; 
                    classes[1] = 52; 
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
            int prediction = DTViuNumero.predict(features);
            System.out.println(prediction);

        }
    }
}
package doutorado.tese.util.machinelearning.tree;

public class DTViuCor {

    private static int findMax(int[] nums) {
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            index = nums[i] > nums[index] ? i : index;
        }
        return index;
    }

    public static int predict(double[] features) {
        int[] classes = new int[2];
            
        if (features[11] <= 12.5) {
            classes[0] = 333; 
            classes[1] = 0; 
        } else {
            if (features[11] <= 506.5) {
                if (features[9] <= 0.5) {
                    if (features[11] <= 272.5) {
                        classes[0] = 0; 
                        classes[1] = 24; 
                    } else {
                        classes[0] = 1; 
                        classes[1] = 19; 
                    }
                } else {
                    if (features[6] <= 30.5) {
                        if (features[7] <= 795.0) {
                            classes[0] = 1; 
                            classes[1] = 22; 
                        } else {
                            classes[0] = 2; 
                            classes[1] = 18; 
                        }
                    } else {
                        classes[0] = 7; 
                        classes[1] = 28; 
                    }
                }
            } else {
                if (features[14] <= 10.5) {
                    if (features[7] <= 2346.0) {
                        classes[0] = 3; 
                        classes[1] = 29; 
                    } else {
                        if (features[7] <= 5574.5) {
                            classes[0] = 0; 
                            classes[1] = 69; 
                        } else {
                            classes[0] = 1; 
                            classes[1] = 19; 
                        }
                    }
                } else {
                    classes[0] = 0; 
                    classes[1] = 224; 
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
            int prediction = DTViuCor.predict(features);
            System.out.println(prediction);

        }
    }
}
package doutorado.tese.util.machinelearning.tree;

public class DTViuForma {

    private static int findMax(int[] nums) {
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            index = nums[i] > nums[index] ? i : index;
        }
        return index;
    }

    public static int predict(double[] features) {
        int[] classes = new int[2];
            
        if (features[12] <= 76.5) {
            if (features[12] <= 22.5) {
                classes[0] = 342; 
                classes[1] = 0; 
            } else {
                classes[0] = 20; 
                classes[1] = 7; 
            }
        } else {
            if (features[12] <= 264.0) {
                if (features[10] <= 98.0) {
                    classes[0] = 2; 
                    classes[1] = 29; 
                } else {
                    if (features[8] <= 0.554373502731) {
                        classes[0] = 9; 
                        classes[1] = 11; 
                    } else {
                        if (features[6] <= 35.5) {
                            classes[0] = 8; 
                            classes[1] = 25; 
                        } else {
                            classes[0] = 1; 
                            classes[1] = 19; 
                        }
                    }
                }
            } else {
                if (features[5] <= 49.5) {
                    if (features[7] <= 2758.0) {
                        if (features[10] <= 812.5) {
                            classes[0] = 0; 
                            classes[1] = 44; 
                        } else {
                            classes[0] = 3; 
                            classes[1] = 35; 
                        }
                    } else {
                        classes[0] = 3; 
                        classes[1] = 17; 
                    }
                } else {
                    if (features[7] <= 7112.0) {
                        if (features[8] <= 0.629401445389) {
                            classes[0] = 1; 
                            classes[1] = 19; 
                        } else {
                            classes[0] = 0; 
                            classes[1] = 185; 
                        }
                    } else {
                        classes[0] = 1; 
                        classes[1] = 19; 
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
            int prediction = DTViuForma.predict(features);
            System.out.println(prediction);

        }
    }
}
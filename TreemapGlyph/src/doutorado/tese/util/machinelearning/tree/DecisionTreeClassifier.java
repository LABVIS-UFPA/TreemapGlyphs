/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.util.machinelearning.tree;

import doutorado.tese.util.Constantes;
import java.util.Arrays;

/**
 *
 * @author Gustavo
 */
public class DecisionTreeClassifier {

    private static int[] findMax(int[][] nums) {
        int[] index = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums[i].length; j++)
                index[i] = nums[i][j] > nums[i][index[i]] ? j : index[i];
        }
        return index;
    }

    public static int[] predict(double[] features) {
        int[][] classes = new int[5][2];
            
        if(features[Constantes.AREA_CIRCULO_COLORIDO] <= 12.5){ // CCA
            if(features[Constantes.AREA_TEXTURA] <= 182.5){ // TA
                if(features[Constantes.AREA_SHAPE] <= 72.5){ //SA
                    // 110/115 Representa a confiaça que o algoritmo tem nessa resposta.
                    //Editado Manualmente com expertise do Anderson
                    if(features[Constantes.AREA_CIRCULO_COLORIDO] <= 5){ //CCA - para de desenhar o circulo colorido quando chegar em 5 pixels quadrados
                        classes[0][0] = 110;classes[0][1] = 5;
                        classes[1][0] = 115;classes[1][1] = 0;
                        classes[2][0] = 111;classes[2][1] = 4;
                        classes[3][0] = 102;classes[3][1] = 13;
                        classes[4][0] = 107;classes[4][1] = 8;
                    }else{
                        if(features[Constantes.PRESENCA_COR_TREEMAP] <= 0.5){
                            classes[0][0] = 110;classes[0][1] = 5;
                            classes[1][0] = 0;  classes[1][1] = 115;
                            classes[2][0] = 111;classes[2][1] = 4;
                            classes[3][0] = 102;classes[3][1] = 13;
                            classes[4][0] = 107;classes[4][1] = 8;
                        }else{
                            classes[0][0] = 110;classes[0][1] = 5;
                            classes[1][0] = 115;classes[1][1] = 0;
                            classes[2][0] = 111;classes[2][1] = 4;
                            classes[3][0] = 102;classes[3][1] = 13;
                            classes[4][0] = 107;classes[4][1] = 8;
                        }
                    }
                    
                }else{
                    classes[0][0] = 64;classes[0][1] = 0;
                    classes[1][0] = 64;classes[1][1] = 0;
                    classes[2][0] = 0; classes[2][1] = 64;
                    classes[3][0] = 59;classes[3][1] = 5;
                    classes[4][0] = 61;classes[4][1] = 3;
                }
            }else{
                if(features[Constantes.PRESENCA_FORMA] <= 0.5){ // SP
                    classes[0][0] = 1; classes[0][1] = 78;
                    classes[1][0] = 79;classes[1][1] = 0;
                    classes[2][0] = 79;classes[2][1] = 0;
                    classes[3][0] = 70;classes[3][1] = 9;
                    classes[4][0] = 74;classes[4][1] = 5;
                }else{
                    classes[0][0] = 4; classes[0][1] = 71;
                    classes[1][0] = 75;classes[1][1] = 0;
                    classes[2][0] = 10;classes[2][1] = 65;
                    classes[3][0] = 57;classes[3][1] = 18;
                    classes[4][0] = 59;classes[4][1] = 16;
                }
            }
        }else{
            if(features[Constantes.AREA_SHAPE] <= 76.5){ //SA
                if(features[Constantes.AREA_TEXTURA] <= 442){ // TA
                    if(features[Constantes.PRESENCA_COR_TREEMAP] <= 0.5){ //Editado Manualmente com expertise do Anderson
                        classes[0][0] = 108;classes[0][1] = 4;
                        classes[1][0] = 3;  classes[1][1] = 109;
                        classes[2][0] = 109;classes[2][1] = 3;
                        classes[3][0] = 103;classes[3][1] = 9;
                        classes[4][0] = 106;classes[4][1] = 6;
                    }else{
                        if(features[Constantes.AREA_SHAPE] <= 20){ //SA
                            classes[0][0] = 108;classes[0][1] = 4;
                            classes[1][0] = 109;classes[1][1] = 3;
                            classes[2][0] = 109;classes[2][1] = 3;
                            classes[3][0] = 103;classes[3][1] = 9;
                            classes[4][0] = 106;classes[4][1] = 6;
                        }else{
                            classes[0][0] = 108;classes[0][1] = 4;
                            classes[1][0] = 109;classes[1][1] = 3;
                            classes[2][0] = 3;  classes[2][1] = 109;
                            classes[3][0] = 103;classes[3][1] = 9;
                            classes[4][0] = 106;classes[4][1] = 6;
                        }
                        
                    }
                    
                }else{
                    classes[0][0] = 3; classes[0][1] = 60;
                    classes[1][0] = 2; classes[1][1] = 61;
                    classes[2][0] = 63;classes[2][1] = 0;
                    classes[3][0] = 54;classes[3][1] = 9;
                    classes[4][0] = 52;classes[4][1] = 11;
                }
            }else{
                if(features[Constantes.AREA_NUMERO] <= 100.5){ //NA
                    if(features[Constantes.AREA_TEXTURA] <= 290){ //TA
                        classes[0][0] = 69;classes[0][1] = 1;
                        classes[1][0] = 3; classes[1][1] = 67;
                        classes[2][0] = 5; classes[2][1] = 65;
                        classes[3][0] = 66;classes[3][1] = 4;
                        classes[4][0] = 63;classes[4][1] = 7;
                    }else{
                        classes[0][0] = 22; classes[0][1] = 126;
                        classes[1][0] = 7;  classes[1][1] = 141;
                        classes[2][0] = 12; classes[2][1] = 136;
                        classes[3][0] = 128;classes[3][1] = 20;
                        classes[4][0] = 138;classes[4][1] = 10;
                    }
                }else{
                    classes[0][0] = 10;classes[0][1] = 64;
                    classes[1][0] = 0; classes[1][1] = 74;
                    classes[2][0] = 1; classes[2][1] = 73;
                    classes[3][0] = 12;classes[3][1] = 62;
                    classes[4][0] = 3; classes[4][1] = 71;
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
            int[] prediction = DecisionTreeClassifier.predict(features);
            System.out.println(Arrays.toString(prediction));

        }
    }
}


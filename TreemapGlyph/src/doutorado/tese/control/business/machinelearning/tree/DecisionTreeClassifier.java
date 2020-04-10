/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.control.business.machinelearning.tree;

import doutorado.tese.util.Constantes;
import static doutorado.tese.util.Constantes.AREA_COR;
import static doutorado.tese.util.Constantes.AREA_ORIENTACAO;
import static doutorado.tese.util.Constantes.AREA_POSICAO;
import static doutorado.tese.util.Constantes.AREA_PROFILE_GLYPH;
import static doutorado.tese.util.Constantes.AREA_SHAPE;
import static doutorado.tese.util.Constantes.AREA_TEXTO;
import static doutorado.tese.util.Constantes.AREA_TEXTURA;
import static doutorado.tese.util.Constantes.PRESENCA_COR_TREEMAP;
import static doutorado.tese.util.Constantes.PRESENCA_FORMA;

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

    /**
     * Os valores armazenados em cada posicao do vetor representam a confianca 
     * que o algoritmo tem nas respostas.                               <br>
     * Exemplo:                                                         <br>
     *      [camadaGLyph]: Podendo ser de 0 a 4;                        <br>
     *      [0] - NaoDesenha                                            <br>
     *      [1] - Desenha                                               <br>
     *      classes[camadaGLyph][0] = X; > classes[camadaGLyph][1] = Y; <br>
     *      se X for maior que Y, entao nao desenha a camada [camadaGLyph].       <br>
     *                                                                  <br>
     *      classes[camadaGLyph][0] = X; < classes[camadaGLyph][1] = Y; <br>
     *      se Y for maior que X, entao nao desenha a camada [camadaGLyph].       <br>
     * 
     * @param features
     * @return 
     */
    public static int[] predict(double[] features) {
        int[][] classes = new int[7][2];
            
        if (features[AREA_COR] <= 2.5) {
            if (features[AREA_TEXTO] <= 14.5) {
                if (features[AREA_SHAPE] <= 20.5) {
                    if (features[AREA_PROFILE_GLYPH] <= 72.5) {
                        if (features[AREA_POSICAO] <= 30.5) {
//                            if (features[AREA_ORIENTACAO] <= 50.0) {
//                                classes[0] = 236; 
//                                classes[1] = 1; 
                                classes[0][0] = 247;classes[0][1] = 1;
                                classes[1][0] = 247;classes[1][1] = 1;
                                classes[2][0] = 247;classes[2][1] = 1;
                                classes[3][0] = 248;classes[3][1] = 0;
                                classes[4][0] = 247;classes[4][1] = 1;
                                classes[5][0] = 248;classes[5][1] = 0;
                                classes[6][0] = 246;classes[6][1] = 2;
//                            } else {
//                                classes[0] = 11; 
//                                classes[1] = 0; 
//                            }
                        } else {
                            if (features[AREA_POSICAO] <= 426.5) {
                                classes[0][0] = 34;classes[0][1] = 0;
                                classes[1][0] = 34;classes[1][1] = 0;
                                classes[2][0] = 29;classes[2][1] = 5;
                                classes[3][0] = 33;classes[3][1] = 1;
                                classes[4][0] = 33;classes[4][1] = 1;
                                classes[5][0] = 34;classes[5][1] = 0;
                                classes[6][0] = 29;classes[6][1] = 5;
                            } else {
                                classes[0][0] = 4;classes[0][1] = 0;
                                classes[1][0] = 4;classes[1][1] = 0;
                                classes[2][0] = 4;classes[2][1] = 0;
                                classes[3][0] = 4;classes[3][1] = 0;
                                classes[4][0] = 3;classes[4][1] = 1;
                                classes[5][0] = 0;classes[5][1] = 4;
                                classes[6][0] = 4;classes[6][1] = 0;
                            }
                        }
                    } else {
                        if (features[AREA_PROFILE_GLYPH] <= 90.5) {
                            classes[0][0] = 7;classes[0][1] = 0;
                            classes[1][0] = 7;classes[1][1] = 0;
                            classes[2][0] = 7;classes[2][1] = 0;
                            classes[3][0] = 7;classes[3][1] = 0;
                            classes[4][0] = 7;classes[4][1] = 0;
                            classes[5][0] = 7;classes[5][1] = 0;
                            classes[6][0] = 4;classes[6][1] = 3; 
                        } else {
//                            if (features[AREA_POSICAO] <= 362.0) {
                                classes[0][0] = 31;classes[0][1] = 0;
                                classes[1][0] = 31;classes[1][1] = 0;
                                classes[2][0] = 31;classes[2][1] = 0;
                                classes[3][0] = 31;classes[3][1] = 0;
                                classes[4][0] = 30;classes[4][1] = 1;
                                classes[5][0] = 29;classes[5][1] = 2;
                                classes[6][0] = 2; classes[6][1] = 29; 
//                            } else {
//                                classes[0] = 7; 
//                                classes[1] = 0; 
//                            }
                        }
                    }
                } else {
                    if (features[AREA_PROFILE_GLYPH] <= 20.5) {
                        if (features[AREA_TEXTURA] <= 24.5) {
                            if (features[AREA_PROFILE_GLYPH] <= 8.0) {
                                classes[0][0] = 9;classes[0][1] = 0;
                                classes[1][0] = 9;classes[1][1] = 0;
                                classes[2][0] = 1;classes[2][1] = 8;
                                classes[3][0] = 9;classes[3][1] = 0;
                                classes[4][0] = 9;classes[4][1] = 0;
                                classes[5][0] = 8;classes[5][1] = 1;
                                classes[6][0] = 9;classes[6][1] = 0; 
                            } else {
                                classes[0][0] = 3;classes[0][1] = 0;
                                classes[1][0] = 3;classes[1][1] = 0;
                                classes[2][0] = 3;classes[2][1] = 0;
                                classes[3][0] = 3;classes[3][1] = 0;
                                classes[4][0] = 3;classes[4][1] = 0;
                                classes[5][0] = 3;classes[5][1] = 0;
                                classes[6][0] = 3;classes[6][1] = 0; 
                            }
                        } else {
//                            if (features[AREA_POSICAO] <= 197.0) {
                                classes[0][0] = 13;classes[0][1] = 0;
                                classes[1][0] = 13;classes[1][1] = 0;
                                classes[2][0] = 0;classes[2][1] = 13;
                                classes[3][0] = 13;classes[3][1] = 0;
                                classes[4][0] = 12;classes[4][1] = 1;
                                classes[5][0] = 13;classes[5][1] = 0;
                                classes[6][0] = 13;classes[6][1] = 0;
//                            } else {
//                                classes[0] = 2; 
//                                classes[1] = 0; 
//                            }
                        }
                    } else {
                        if (features[AREA_ORIENTACAO] <= 420.5) {
                            if (features[AREA_SHAPE] <= 306.5) {
                                classes[0][0] = 28;classes[0][1] = 0;
                                classes[1][0] = 28;classes[1][1] = 0;
                                classes[2][0] = 8;classes[2][1] = 20;
                                classes[3][0] = 28;classes[3][1] = 0;
                                classes[4][0] = 28;classes[4][1] = 0;
                                classes[5][0] = 28;classes[5][1] = 0;
                                classes[6][0] = 20;classes[6][1] = 8;
                            } else {
                                classes[0][0] = 5;classes[0][1] = 0;
                                classes[1][0] = 5;classes[1][1] = 0;
                                classes[2][0] = 0;classes[2][1] = 5;
                                classes[3][0] = 5;classes[3][1] = 0;
                                classes[4][0] = 5;classes[4][1] = 0;
                                classes[5][0] = 5;classes[5][1] = 0;
                                classes[6][0] = 0;classes[6][1] = 5;
                            }
                        } else {
//                            if (features[AREA_POSICAO] <= 904.0) {
                                classes[0][0] = 21;classes[0][1] = 0;
                                classes[1][0] = 21;classes[1][1] = 0;
                                classes[2][0] = 1; classes[2][1] = 20;
                                classes[3][0] = 21;classes[3][1] = 0;
                                classes[4][0] = 20;classes[4][1] = 1;
                                classes[5][0] = 20;classes[5][1] = 1;
                                classes[6][0] = 1; classes[6][1] = 20; 
//                            } else {
//                                classes[0] = 2; 
//                                classes[1] = 0; 
//                            }
                        }
                    }
                }
            } else {
                if (features[AREA_SHAPE] <= 20.5) {
                    if (features[AREA_TEXTO] <= 47.5) {
                        if (features[AREA_POSICAO] <= 30.5) {
                            classes[0][0] = 8;classes[0][1] = 0;
                            classes[1][0] = 8;classes[1][1] = 0;
                            classes[2][0] = 8;classes[2][1] = 0;
                            classes[3][0] = 6;classes[3][1] = 2;
                            classes[4][0] = 8;classes[4][1] = 0;
                            classes[5][0] = 8;classes[5][1] = 0;
                            classes[6][0] = 8;classes[6][1] = 0;
                        } else {
                            classes[0][0] = 5;classes[0][1] = 0;
                            classes[1][0] = 5;classes[1][1] = 0;
                            classes[2][0] = 5;classes[2][1] = 0;
                            classes[3][0] = 1;classes[3][1] = 4;
                            classes[4][0] = 5;classes[4][1] = 0;
                            classes[5][0] = 5;classes[5][1] = 0;
                            classes[6][0] = 5;classes[6][1] = 0;
                        }
                    } else {
                        if (features[AREA_TEXTO] <= 692.0) {
//                            if (features[AREA_ORIENTACAO] <= 420.5) {
                                classes[0][0] = 25;classes[0][1] = 0;
                                classes[1][0] = 25;classes[1][1] = 0;
                                classes[2][0] = 25;classes[2][1] = 0;
                                classes[3][0] = 0; classes[3][1] = 25;
                                classes[4][0] = 22;classes[4][1] = 3;
                                classes[5][0] = 25;classes[5][1] = 0;
                                classes[6][0] = 25;classes[6][1] = 0; 
//                            } else {
//                                classes[0] = 6; 
//                                classes[1] = 0; 
//                            }
                        } else {
                            classes[0][0] = 1;classes[0][1] = 3;
                            classes[1][0] = 4;classes[1][1] = 0;
                            classes[2][0] = 4;classes[2][1] = 0;
                            classes[3][0] = 0;classes[3][1] = 4;
                            classes[4][0] = 4;classes[4][1] = 0;
                            classes[5][0] = 4;classes[5][1] = 0;
                            classes[6][0] = 4;classes[6][1] = 0; 
                        }
                    }
                } else {
                    if (features[AREA_TEXTO] <= 112.0) {
                        if (features[AREA_ORIENTACAO] <= 290.0) {
//                            if (features[AREA_TEXTO] <= 71.5) {
                                classes[0][0] = 15;classes[0][1] = 0;
                                classes[1][0] = 15;classes[1][1] = 0;
                                classes[2][0] = 11;classes[2][1] = 4;
                                classes[3][0] = 1; classes[3][1] = 14;
                                classes[4][0] = 15;classes[4][1] = 0;
                                classes[5][0] = 15;classes[5][1] = 0;
                                classes[6][0] = 15;classes[6][1] = 0;
//                            } else {
//                                classes[0] = 5; 
//                                classes[1] = 0; 
//                            }
                        } else {
                            classes[0][0] = 5;classes[0][1] = 0;
                            classes[1][0] = 5;classes[1][1] = 0;
                            classes[2][0] = 1;classes[2][1] = 4;
                            classes[3][0] = 0;classes[3][1] = 5;
                            classes[4][0] = 5;classes[4][1] = 0;
                            classes[5][0] = 3;classes[5][1] = 2;
                            classes[6][0] = 5;classes[6][1] = 0;
                        }
                    } else {
                        if (features[AREA_POSICAO] <= 650.5) {
                            classes[0][0] = 11;classes[0][1] = 0;
                            classes[1][0] = 11;classes[1][1] = 0;
                            classes[2][0] = 0; classes[2][1] = 11;
                            classes[3][0] = 0; classes[3][1] = 11;
                            classes[4][0] = 11;classes[4][1] = 0;
                            classes[5][0] = 11;classes[5][1] = 0;
                            classes[6][0] = 11;classes[6][1] = 0;
                        } else {
                            classes[0][0] = 2;classes[0][1] = 0;
                            classes[1][0] = 2;classes[1][1] = 0;
                            classes[2][0] = 0;classes[2][1] = 2;
                            classes[3][0] = 0;classes[3][1] = 2;
                            classes[4][0] = 2;classes[4][1] = 0;
                            classes[5][0] = 0;classes[5][1] = 2;
                            classes[6][0] = 2;classes[6][1] = 0;
                        }
                    }
                }
            }
        } else {
            if (features[AREA_SHAPE] <= 12.5) {
                if (features[AREA_TEXTO] <= 3.5) {
                    if (features[AREA_PROFILE_GLYPH] <= 30.5) {
                        if (features[AREA_POSICAO] <= 20.5) {
                            if (features[AREA_ORIENTACAO] <= 20.5) {
                                classes[0][0] = 34;classes[0][1] = 0;
                                classes[1][0] = 23;classes[1][1] = 11;
                                classes[2][0] = 34;classes[2][1] = 0;
                                classes[3][0] = 34;classes[3][1] = 0;
                                classes[4][0] = 34;classes[4][1] = 0;
                                classes[5][0] = 34;classes[5][1] = 0;
                                classes[6][0] = 34;classes[6][1] = 0; 
                            } else {
                                classes[0][0] = 41;classes[0][1] = 0;
                                classes[1][0] = 9; classes[1][1] = 32;
                                classes[2][0] = 41;classes[2][1] = 0;
                                classes[3][0] = 41;classes[3][1] = 0;
                                classes[4][0] = 41;classes[4][1] = 0;
                                classes[5][0] = 41;classes[5][1] = 0;
                                classes[6][0] = 41;classes[6][1] = 0;
                            }
                        } else {
//                            if (features[AREA_ORIENTACAO] <= 110.5) {
                                classes[0][0] = 77;classes[0][1] = 0;
                                classes[1][0] = 5; classes[1][1] = 72;
                                classes[2][0] = 74;classes[2][1] = 3;
                                classes[3][0] = 77;classes[3][1] = 0;
                                classes[4][0] = 77;classes[4][1] = 0;
                                classes[5][0] = 77;classes[5][1] = 0;
                                classes[6][0] = 75;classes[6][1] = 2;
//                            } else {
//                                classes[0] = 21; 
//                                classes[1] = 0; 
//                            }
                        }
                    } else {
                        if (features[AREA_ORIENTACAO] <= 420.5) {
                            if (features[AREA_COR] <= 210.5) {
                                classes[0][0] = 22;classes[0][1] = 0;
                                classes[1][0] = 0; classes[1][1] = 22;
                                classes[2][0] = 22;classes[2][1] = 0;
                                classes[3][0] = 22;classes[3][1] = 0;
                                classes[4][0] = 22;classes[4][1] = 0;
                                classes[5][0] = 21;classes[5][1] = 1;
                                classes[6][0] = 16;classes[6][1] = 6;
                            } else {
                                classes[0][0] = 7;classes[0][1] = 0;
                                classes[1][0] = 0;classes[1][1] = 7;
                                classes[2][0] = 7;classes[2][1] = 0;
                                classes[3][0] = 7;classes[3][1] = 0;
                                classes[4][0] = 7;classes[4][1] = 0;
                                classes[5][0] = 7;classes[5][1] = 0;
                                classes[6][0] = 0;classes[6][1] = 7;
                            }
                        } else {
//                            if (features[AREA_PROFILE_GLYPH] <= 182.5) {
                                classes[0][0] = 11;classes[0][1] = 0;
                                classes[1][0] = 0; classes[1][1] = 11;
                                classes[2][0] = 11;classes[2][1] = 0;
                                classes[3][0] = 11;classes[3][1] = 0;
                                classes[4][0] = 10;classes[4][1] = 1;
                                classes[5][0] = 10;classes[5][1] = 1;
                                classes[6][0] = 0; classes[6][1] = 11;
//                            } else {
//                                classes[0] = 1; 
//                                classes[1] = 0; 
//                            }
                        }
                    }
                } else {
                    if (features[AREA_COR] <= 72.5) {
                        if (features[AREA_ORIENTACAO] <= 90.5) {
//                            if (features[AREA_POSICAO] <= 42.5) {
                                classes[0][0] = 32;classes[0][1] = 0;
                                classes[1][0] = 5; classes[1][1] = 27;
                                classes[2][0] = 32;classes[2][1] = 0;
                                classes[3][0] = 30;classes[3][1] = 2;
                                classes[4][0] = 32;classes[4][1] = 0;
                                classes[5][0] = 32;classes[5][1] = 0;
                                classes[6][0] = 32;classes[6][1] = 0;
//                            } else {
//                                classes[0] = 13; 
//                                classes[1] = 0; 
//                            }
                        } else {
//                            if (features[AREA_TEXTO] <= 6.0) {
                                classes[0][0] = 30;classes[0][1] = 0;
                                classes[1][0] = 1; classes[1][1] = 29;
                                classes[2][0] = 29;classes[2][1] = 1;
                                classes[3][0] = 11;classes[3][1] = 19;
                                classes[4][0] = 30;classes[4][1] = 0;
                                classes[5][0] = 30;classes[5][1] = 0;
                                classes[6][0] = 30;classes[6][1] = 0;
//                            } else {
//                                classes[0] = 24; 
//                                classes[1] = 0; 
//                            }
                        }
                    } else {
//                        if (features[AREA_COR] <= 1090.0) {
//                            if (features[AREA_POSICAO] <= 1125.0) {
                                classes[0][0] = 14;classes[0][1] = 1;
                                classes[1][0] = 0; classes[1][1] = 15;
                                classes[2][0] = 15;classes[2][1] = 0;
                                classes[3][0] = 0; classes[3][1] = 15;
                                classes[4][0] = 15;classes[4][1] = 0;
                                classes[5][0] = 14;classes[5][1] = 1;
                                classes[6][0] = 15;classes[6][1] = 0;
//                            } else {
//                                classes[0] = 1; 
//                                classes[1] = 0; 
//                            }
//                        } else {
//                            classes[0] = 0; 
//                            classes[1] = 1; 
//                        }
                    }
                }
            } else {
                if (features[AREA_TEXTO] <= 10.5) {
                    if (features[AREA_ORIENTACAO] <= 506.5) {
                        if (features[AREA_POSICAO] <= 156.5) {
                            if (features[AREA_ORIENTACAO] <= 110.5) {
                                classes[0][0] = 36;classes[0][1] = 0;
                                classes[1][0] = 1; classes[1][1] = 35;
                                classes[2][0] = 24;classes[2][1] = 12;
                                classes[3][0] = 36;classes[3][1] = 0;
                                classes[4][0] = 36;classes[4][1] = 0;
                                classes[5][0] = 36;classes[5][1] = 0;
                                classes[6][0] = 35;classes[6][1] = 1; 
                            } else {
                                classes[0][0] = 30;classes[0][1] = 0;
                                classes[1][0] = 1; classes[1][1] = 29;
                                classes[2][0] = 7; classes[2][1] = 23;
                                classes[3][0] = 30;classes[3][1] = 0;
                                classes[4][0] = 30;classes[4][1] = 0;
                                classes[5][0] = 30;classes[5][1] = 0;
                                classes[6][0] = 30;classes[6][1] = 0; 
                            }
                        } else {
//                            if (features[AREA_ORIENTACAO] <= 462.5) {
                                classes[0][0] = 24;classes[0][1] = 0;
                                classes[1][0] = 1; classes[1][1] = 23;
                                classes[2][0] = 4; classes[2][1] = 20;
                                classes[3][0] = 24;classes[3][1] = 0;
                                classes[4][0] = 24;classes[4][1] = 0;
                                classes[5][0] = 23;classes[5][1] = 1;
                                classes[6][0] = 21;classes[6][1] = 3;
//                            } else {
//                                classes[0] = 2; 
//                                classes[1] = 0; 
//                            }
                        }
                    } else {
                        if (features[AREA_PROFILE_GLYPH] <= 30.5) {
//                            if (features[AREA_POSICAO] <= 488.0) {
                                classes[0][0] = 19;classes[0][1] = 0;
                                classes[1][0] = 0; classes[1][1] = 19;
                                classes[2][0] = 0; classes[2][1] = 19;
                                classes[3][0] = 19;classes[3][1] = 0;
                                classes[4][0] = 12;classes[4][1] = 7;
                                classes[5][0] = 17;classes[5][1] = 2;
                                classes[6][0] = 19;classes[6][1] = 0;
//                            } else {
//                                classes[0] = 4; 
//                                classes[1] = 0; 
//                            }
                        } else {
                            if (features[AREA_COR] <= 257.0) {
                                classes[0][0] = 14;classes[0][1] = 0;
                                classes[1][0] = 0; classes[1][1] = 14;
                                classes[2][0] = 2; classes[2][1] = 12;
                                classes[3][0] = 14;classes[3][1] = 0;
                                classes[4][0] = 14;classes[4][1] = 0;
                                classes[5][0] = 10;classes[5][1] = 4;
                                classes[6][0] = 8; classes[6][1] = 6;
                            } else {
                                classes[0][0] = 8;classes[0][1] = 0;
                                classes[1][0] = 0;classes[1][1] = 8;
                                classes[2][0] = 1;classes[2][1] = 7;
                                classes[3][0] = 8;classes[3][1] = 0;
                                classes[4][0] = 7;classes[4][1] = 1;
                                classes[5][0] = 7;classes[5][1] = 1;
                                classes[6][0] = 0;classes[6][1] = 8;
                            }
                        }
                    }
                } else {
                    if (features[AREA_ORIENTACAO] <= 506.5) {
                        if (features[AREA_POSICAO] <= 533.0) {
                            if (features[AREA_TEXTURA] <= 56.5) {
                                classes[0][0] = 16;classes[0][1] = 0;
                                classes[1][0] = 0; classes[1][1] = 16;
                                classes[2][0] = 15;classes[2][1] = 1;
                                classes[3][0] = 7; classes[3][1] = 9;
                                classes[4][0] = 16;classes[4][1] = 0;
                                classes[5][0] = 16;classes[5][1] = 0;
                                classes[6][0] = 16;classes[6][1] = 0;
                            } else {
                                classes[0][0] = 31;classes[0][1] = 0;
                                classes[1][0] = 0; classes[1][1] = 31;
                                classes[2][0] = 16;classes[2][1] = 15;
                                classes[3][0] = 10;classes[3][1] = 21;
                                classes[4][0] = 31;classes[4][1] = 0;
                                classes[5][0] = 30;classes[5][1] = 1;
                                classes[6][0] = 31;classes[6][1] = 0;
                            }
                        } else {
                            classes[0][0] = 5;classes[0][1] = 0;
                            classes[1][0] = 0;classes[1][1] = 5;
                            classes[2][0] = 0;classes[2][1] = 5;
                            classes[3][0] = 0;classes[3][1] = 5;
                            classes[4][0] = 5;classes[4][1] = 0;
                            classes[5][0] = 5;classes[5][1] = 0;
                            classes[6][0] = 5;classes[6][1] = 0;
                        }
                    } else {
//                        if (features[AREA_POSICAO] <= 1262.5) {
//                            if (features[AREA_TEXTO] <= 36.0) {
                                classes[0][0] = 30;classes[0][1] = 0;
                                classes[1][0] = 0; classes[1][1] = 30;
                                classes[2][0] = 3; classes[2][1] = 27;
                                classes[3][0] = 2; classes[3][1] = 28;
                                classes[4][0] = 29;classes[4][1] = 1;
                                classes[5][0] = 23;classes[5][1] = 7;
                                classes[6][0] = 30;classes[6][1] = 0; 
//                            } else {
//                                classes[0] = 21; 
//                                classes[1] = 0; 
//                            }
//                        } else {
//                            classes[0] = 3; 
//                            classes[1] = 0; 
//                        }
                    }
                }
            }
        }
    
        return findMax(classes);
    }
    
    public static int[] predict_old(double[] features) {
        int[][] classes = new int[5][2];
        if(features[AREA_COR] <= 12.5){ // CCA
            if(features[AREA_TEXTURA] <= 182.5){ // TA
                if(features[AREA_SHAPE] <= 72.5){ //SA
                    //Editado Manualmente com expertise do Gustavo
                    if(features[AREA_COR] <= 5){ //CCA - para de desenhar o circulo colorido quando chegar em 5 pixels quadrados
                        classes[0][0] = 110;classes[0][1] = 5;
                        classes[1][0] = 115;classes[1][1] = 0;
                        classes[2][0] = 111;classes[2][1] = 4;
                        classes[3][0] = 102;classes[3][1] = 13;
                        classes[4][0] = 107;classes[4][1] = 8;
                    }else{
                        if(features[PRESENCA_COR_TREEMAP] <= 0.5){
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
                if(features[PRESENCA_FORMA] <= 0.5){ // SP
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
                            if(features[Constantes.PRESENCA_COR] > 0.5){//Editado manualmente em 16 de fev 2020
                                if(features[Constantes.AREA_TEXTO] <= 100.5){
                                    classes[0][0] = 108;classes[0][1] = 4;
                                    classes[1][0] = 3;classes[1][1] = 109;
                                    classes[2][0] = 109;classes[2][1] = 3;
                                    classes[3][0] = 103;classes[3][1] = 9;
                                    classes[4][0] = 106;classes[4][1] = 6;
                                }else{//Editado manualmente em 16 de fev 2020
                                    classes[0][0] = 108;classes[0][1] = 4;
                                    classes[1][0] = 3;classes[1][1] = 109;
                                    classes[2][0] = 109;classes[2][1] = 3;
                                    classes[3][0] = 9;classes[3][1] = 103;
                                    classes[4][0] = 106;classes[4][1] = 6;
                                }
                            }else{
                                classes[0][0] = 108;classes[0][1] = 4;
                                classes[1][0] = 109;classes[1][1] = 3;
                                classes[2][0] = 109;classes[2][1] = 3;
                                classes[3][0] = 103;classes[3][1] = 9;
                                classes[4][0] = 106;classes[4][1] = 6;
                            }
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
                if(features[Constantes.AREA_TEXTO] <= 100.5){ //NA
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

//    public static void main(String[] args) {
//        if (args.length == 15) {
//
//            // Features:
//            double[] features = new double[args.length];
//            for (int i = 0, l = args.length; i < l; i++) {
//                features[i] = Double.parseDouble(args[i]);
//            }
//
//            // Prediction:
//            int[] prediction = DecisionTreeClassifier.predict(features);
//            System.out.println(Arrays.toString(prediction));
//
//        }
//    }
}


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
    
    public static int predict_v1_MenorLado(double[] features) {
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

    public static int predict_v1_Aspecto(double[] features) {
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

    public static int predict_v2_MenorLado(double[] features) {//predict_v2_MenorLado --> melhor resultado
        int[] classes = new int[2];
            
        if (features[0] <= 3.5) {
            if (features[2] <= 25.5) {
                if (features[0] <= 1.5) {
                    if (features[1] <= 65.0) {
                        classes[0] = 7; 
                        classes[1] = 0; 
                    } else {
                        classes[0] = 4; 
                        classes[1] = 26; 
                    }
                } else {
                    if (features[2] <= 19.5) {
                        classes[0] = 53; 
                        classes[1] = 1; 
                    } else {
                        classes[0] = 14; 
                        classes[1] = 8; 
                    }
                }
            } else {
                if (features[0] <= 1.5) {
                    if (features[1] <= 6162.5) {
                        classes[0] = 3; 
                        classes[1] = 86; 
                    } else {
                        classes[0] = 0; 
                        classes[1] = 81; 
                    }
                } else {
                    if (features[2] <= 46.5) {
                        classes[0] = 27; 
                        classes[1] = 52; 
                    } else {
                        classes[0] = 32; 
                        classes[1] = 263; 
                    }
                }
            }
        } else {
            if (features[1] <= 2550.5) {
                if (features[1] <= 1260.5) {
                    classes[0] = 181; 
                    classes[1] = 0; 
                } else {
                    if (features[0] <= 4.5) {
                        classes[0] = 30; 
                        classes[1] = 6; 
                    } else {
                        classes[0] = 60; 
                        classes[1] = 0; 
                    }
                }
            } else {
                if (features[0] <= 5.5) {
                    if (features[2] <= 81.5) {
                        classes[0] = 82; 
                        classes[1] = 53; 
                    } else {
                        classes[0] = 68; 
                        classes[1] = 108; 
                    }
                } else {
                    if (features[2] <= 104.5) {
                        classes[0] = 103; 
                        classes[1] = 8; 
                    } else {
                        classes[0] = 28; 
                        classes[1] = 16; 
                    }
                }
            }
        }
    
        return findMax(classes);
    }

    public static int predictAreaGlyphs_v2(double[] features) {//predictAreaGlyphs_v2
        int[] classes = new int[2];
            
        if (features[0] <= 3.5) {
            if (features[1] <= 552.5) {
                if (features[0] <= 1.5) {
                    if (features[1] <= 65.0) {
                        classes[0] = 7; 
                        classes[1] = 0; 
                    } else {
                        classes[0] = 4; 
                        classes[1] = 21; 
                    }
                } else {
                    if (features[4] <= 56.5) {
                        classes[0] = 62; 
                        classes[1] = 3; 
                    } else {
                        classes[0] = 9; 
                        classes[1] = 4; 
                    }
                }
            } else {
                if (features[0] <= 2.5) {
                    if (features[6] <= 72.0) {
                        classes[0] = 10; 
                        classes[1] = 293; 
                    } else {
                        classes[0] = 12; 
                        classes[1] = 82; 
                    }
                } else {
                    if (features[1] <= 2256.5) {
                        classes[0] = 22; 
                        classes[1] = 20; 
                    } else {
                        classes[0] = 23; 
                        classes[1] = 133; 
                    }
                }
            }
        } else {
            if (features[1] <= 2550.5) {
                if (features[8] <= 72.5) {
                    if (features[6] <= 257.0) {
                        classes[0] = 228; 
                        classes[1] = 0; 
                    } else {
                        classes[0] = 41; 
                        classes[1] = 2; 
                    }
                } else {
                    if (features[4] <= 84.5) {
                        classes[0] = 5; 
                        classes[1] = 5; 
                    } else {
                        classes[0] = 11; 
                        classes[1] = 0; 
                    }
                }
            } else {
                if (features[0] <= 5.5) {
                    if (features[1] <= 7140.5) {
                        classes[0] = 88; 
                        classes[1] = 60; 
                    } else {
                        classes[0] = 53; 
                        classes[1] = 97; 
                    }
                } else {
                    if (features[1] <= 11342.5) {
                        classes[0] = 105; 
                        classes[1] = 10; 
                    } else {
                        classes[0] = 29; 
                        classes[1] = 17; 
                    }
                }
            }
        }
    
        return findMax(classes);
    }

    public static int predict_v2_MenorLado_6_7_e_6_7(double[] features) {//
        int[] classes = new int[2];
            
        if (features[1] <= 1122.5) {
            if (features[0] <= 2.5) {
                if (features[2] <= 11.5) {
                    if (features[0] <= 1.5) {
                        classes[0] = 1; 
                        classes[1] = 11; 
                    } else {
                        classes[0] = 17; 
                        classes[1] = 2; 
                    }
                } else {
                    if (features[0] <= 1.5) {
                        classes[0] = 0; 
                        classes[1] = 51; 
                    } else {
                        if (features[2] <= 26.5) {
                            classes[0] = 8; 
                            classes[1] = 20; 
                        } else {
                            classes[0] = 0; 
                            classes[1] = 21; 
                        }
                    }
                }
            } else {
                if (features[0] <= 3.5) {
                    if (features[2] <= 22.5) {
                        if (features[1] <= 342.5) {
                            classes[0] = 32; 
                            classes[1] = 0; 
                        } else {
                            classes[0] = 7; 
                            classes[1] = 3; 
                        }
                    } else {
                        classes[0] = 9; 
                        classes[1] = 15; 
                    }
                } else {
                    if (features[0] <= 4.5) {
                        if (features[1] <= 420.5) {
                            classes[0] = 30; 
                            classes[1] = 0; 
                        } else {
                            if (features[1] <= 930.5) {
                                classes[0] = 26; 
                                classes[1] = 5; 
                            } else {
                                classes[0] = 7; 
                                classes[1] = 0; 
                            }
                        }
                    } else {
                        classes[0] = 135; 
                        classes[1] = 0; 
                    }
                }
            }
        } else {
            if (features[0] <= 4.5) {
                if (features[0] <= 3.5) {
                    if (features[0] <= 2.5) {
                        if (features[1] <= 4970.5) {
                            if (features[1] <= 4830.5) {
                                classes[0] = 0; 
                                classes[1] = 137; 
                            } else {
                                classes[0] = 1; 
                                classes[1] = 2; 
                            }
                        } else {
                            classes[0] = 0; 
                            classes[1] = 254; 
                        }
                    } else {
                        if (features[1] <= 11556.5) {
                            if (features[2] <= 46.5) {
                                classes[0] = 2; 
                                classes[1] = 28; 
                            } else {
                                classes[0] = 1; 
                                classes[1] = 123; 
                            }
                        } else {
                            if (features[1] <= 12210.5) {
                                classes[0] = 3; 
                                classes[1] = 10; 
                            } else {
                                classes[0] = 0; 
                                classes[1] = 41; 
                            }
                        }
                    }
                } else {
                    if (features[1] <= 1806.5) {
                        classes[0] = 10; 
                        classes[1] = 13; 
                    } else {
                        if (features[2] <= 123.5) {
                            if (features[1] <= 2550.5) {
                                classes[0] = 4; 
                                classes[1] = 17; 
                            } else {
                                classes[0] = 8; 
                                classes[1] = 152; 
                            }
                        } else {
                            classes[0] = 1; 
                            classes[1] = 1; 
                        }
                    }
                }
            } else {
                if (features[1] <= 4556.5) {
                    if (features[2] <= 48.5) {
                        classes[0] = 58; 
                        classes[1] = 0; 
                    } else {
                        if (features[0] <= 5.5) {
                            if (features[1] <= 3782.5) {
                                classes[0] = 8; 
                                classes[1] = 22; 
                            } else {
                                classes[0] = 12; 
                                classes[1] = 11; 
                            }
                        } else {
                            if (features[2] <= 65.5) {
                                classes[0] = 30; 
                                classes[1] = 0; 
                            } else {
                                classes[0] = 3; 
                                classes[1] = 2; 
                            }
                        }
                    }
                } else {
                    if (features[0] <= 5.5) {
                        if (features[1] <= 14762.5) {
                            if (features[2] <= 72.5) {
                                classes[0] = 3; 
                                classes[1] = 9; 
                            } else {
                                classes[0] = 12; 
                                classes[1] = 104; 
                            }
                        } else {
                            classes[0] = 2; 
                            classes[1] = 4; 
                        }
                    } else {
                        if (features[2] <= 89.5) {
                            if (features[2] <= 76.5) {
                                classes[0] = 11; 
                                classes[1] = 11; 
                            } else {
                                classes[0] = 24; 
                                classes[1] = 8; 
                            }
                        } else {
                            if (features[1] <= 15006.5) {
                                classes[0] = 26; 
                                classes[1] = 50; 
                            } else {
                                classes[0] = 0; 
                                classes[1] = 6; 
                            }
                        }
                    }
                }
            }
        }
    
        return findMax(classes);
    }

    public static int predict(double[] features) {
        int[] classes = new int[2];
            
        if (features[2] <= 72.5) {//areaUltimaCamada
            if (features[0] <= 1.5) {//numCamadas
                if (features[2] <= 56.5) {//AreaUltimaCamada
                    classes[0] = 11; //nao
                    classes[1] = 0;//sim 
                } else {
                    classes[0] = 1; 
                    classes[1] = 6; 
                }
            } else {
                if (features[2] <= 56.5) {
                    if (features[0] <= 2.5) {
                        classes[0] = 21; 
                        classes[1] = 7; 
                    } else {
                        classes[0] = 301; 
                        classes[1] = 5; 
                    }
                } else {
                    if (features[0] <= 4.5) {
                        classes[0] = 21; 
                        classes[1] = 14; 
                    } else {
                        classes[0] = 28; 
                        classes[1] = 6; 
                    }
                }
            }
        } else {
            if (features[2] <= 132.5) {
                if (features[1] <= 61.5) {
                    if (features[2] <= 90.5) {
                        classes[0] = 17; 
                        classes[1] = 23; 
                    } else {
                        classes[0] = 13; 
                        classes[1] = 47; 
                    }
                } else {
                    if (features[1] <= 89.5) {
                        classes[0] = 39; 
                        classes[1] = 30; 
                    } else {
                        classes[0] = 2; 
                        classes[1] = 7; 
                    }
                }
            } else {
                if (features[0] <= 4.5) {
                    if (features[2] <= 380.5) {
                        classes[0] = 13; 
                        classes[1] = 183; 
                    } else {
                        classes[0] = 8; 
                        classes[1] = 663; 
                    }
                } else {
                    if (features[0] <= 5.5) {
                        classes[0] = 20; 
                        classes[1] = 120; 
                    } else {
                        classes[0] = 23; 
                        classes[1] = 51; 
                    }
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

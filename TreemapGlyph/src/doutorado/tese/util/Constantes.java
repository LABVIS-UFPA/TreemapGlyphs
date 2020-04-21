/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.util;

import java.awt.Color;

/**
 *
 * @author Anderson
 */
public class Constantes {

    public final static String PROGRESS = "progress";
    public final static String VALUE_SAME_SIZE = "\t1";
    public final static int COR_TREEMAP = -1;
    private static boolean showGlyph = false;
    private static boolean showLegenda = false;
    private static boolean showStarGlyph = false;
    public static Color ALICE_BLUE = Color.decode("#F0F8FF");
    //INICIO - features para a decision tree
    public static final int PRESENCA_TEXTURA = 0;
    public static final int PRESENCA_COR = 1;
    public static final int PRESENCA_FORMA = 2;//SP -SHAPE PRESENCE
    public static final int PRESENCA_TEXTO = 3;
    public static final int PRESENCA_POSICAO = 4;
    public static final int PRESENCA_ORIENTACAO = 5;
    public static final int PRESENCA_PROFILE_GLYPH = 6;
    public static final int FEATURE_ALTURA = 7;
    public static final int FEATURE_LARGURA = 8;
    public static final int FEATURE_AREA = 9;//TIA - TOTAL ITEM AREA
    public static final int FEATURE_ASPECT = 10;
    public static final int PRESENCA_COR_TREEMAP = 11;//
    public static final int AREA_TEXTURA = 12;//TA - TEXTURE AREA
    public static final int AREA_COR = 13;//CCA - Colored circle area
    public static final int AREA_SHAPE = 14;//SA - SHAPE AREA
    public static final int AREA_TEXTO = 15;
    public static final int AREA_POSICAO = 16;//NA - NUMBER AREA
    public static final int AREA_ORIENTACAO = 17;//NA - NUMBER AREA
    public static final int AREA_PROFILE_GLYPH = 18;//NA - NUMBER AREA
    public static final int AREA_VISIVEL_SHAPE = 19;
    public static final int AREA_VISIVEL_TEXTURE = 20;
    public static final int AREA_VISIVEL_TEXT = 21;
    public static final int AREA_VISIVEL_POSICAO = 22;
    public static final int AREA_VISIVEL_ORIENTACAO = 23;
    //FIM - features para a decision tree
    public static final int PRESENTE = 1;
    public static final int AUSENTE = 0;
    public static int QUANT_HIERARQUIAS = 0;
    public static boolean DECISION_TREE_ACTIVATED = false;
    public static boolean CONTINUOUS_GLYPH_ACTIVATED = false;
    public static boolean CATEGORICAL_GLYPH_ACTIVATED = false;
    public static boolean LEGENDA_COR_TREEMAP = false;
    public static final int LIMITE_TESTES = 100;
    public static int PRESENCA_STAR;
    public static boolean SHOW_GLYPH_ON_DETAILS = false;
    public static String NAO_IDENTIFICOU = "nao_identificou";
    
//    private static Color[] cores = {Color.BLUE, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.YELLOW};
    private static String[] corTreemap = {
//        "#3366cc", "#dc3912", "#ff9900", "#109618", "#990099", "#0099c6", "#dd4477",
//        "#66aa00", "#b82e2e", "#316395", "#994499", "#22aa99", "#aaaa11", "#6633cc",
//        "#e67300", "#8b0707", "#651067", "#329262", "#5574a6", "#3b3eac", "#F0F8FF"
        //"#86efff",//"#1f77b4",//"#ff4b45",
        //cores MDPI information
        //"#7dc3d1", "#ff9b30", "#248022", "#ff7f78", "#9467bd", "#8c564b", "#e377c2","#7f7f7f"
        
        //D3 -> d3.schemeSet3:
//        "#8dd3c7", "#ffffb3", "#bebada", 
//        "#ffbab3",//essa era a cor da paleta do D3: "#fb8072", mas achei melhor deixa-la mais suave
//        "#80b1d3", "#fdb462", "#fccde5", "#ccebc5"
            
        //D3 -> d3.schemePastel2
        "#B3E2CD", "#FDCDAC", "#CBD5E8", "#F4CAE4", "#E6F5C9", "#FFF2AE", "#F1E2CC", "#CCCCCC"
    };

    private static String[] colorHueGlyphs = {
        //cores IV 2019
        //"#FF0101", "#2C2CFF", "#EBC089", "#FFFF01", "#41BA2F"
        
        //D3 --> d3.schemePaired
        "#1F78B4", "#33A02C", "#E31A1C", "#FDBF6F", "#FFFF99"
        //cores MDPI information
        //"#B51212", "#fae768", "#174580", "#00a1d7", "#15d400", "#ff3797", "#8c8589", "#000000"
    };
    
    private static String[] colorContinuousGlyph = {
        //"#B51212", "#fae768", "#174580", "#00a1d7", "#15d400", "#ff3797", "#8c8589", "#000000"
        //colors of new tableau 10
        "#4e79a7", "#f28e2b", "#e15759", "#76b7b2", "#59a14f", "#b07aa1", "#ff9da7", "#9c755f"
    };

    public final static String[] TIPO_TEXTURA = {
        "PATTERN_DIAG_RIGHT_LEFT",
        "PATTERN_HORIZONTAL",
        "PATTERN_VERTICAL",
        "PATTERN_UP",
        "PATTERN_DIAG_LEFT_RIGHT",
        "PATTERN_RIGHT",
        "PATTERN_DIAG_CROSS_LINES",
        "PATTERN_CROSS_LINES"
//        "PATTERN_DIAGDOTS2" ,
//        "PATTERN_DOTS",
//        "PATTERN_CHESS",
//        "PATTERN_PLUS" ,
//        "PATTERN_DOWN",
//        "PATTERN_LEFT",
//        "PATTERN_DIAGDOTS"
    };

    public final static String[] LETRAS_ALFABETO = {
        "A", "C", "J", "K", "M"
//        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
//        "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    public final static String[] NUMEROS = {
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"
    };

    public enum POSICOES{
        ESQ_SUP("ESQ_SUP"), 
        ESQ_INF("ESQ_INF"),
        DIR_SUP("DIR_SUP"),
        DIR_INF("DIR_INF"),
        CENTRO("CENTRO");
        
        private final String nome;
        
        POSICOES(String nome){
            this.nome = nome;
        }
        
        public String getName(){
            return nome;
        }
    }
    
    public enum CENARIOS{
        A, B, C, D, E, SEM_CENARIO;
    }
    
    public enum VAR_VISUAIS_CATEGORICAS {
        POSITION("Position"),        
        ORIENTATION("Orientation"),
        TEXTURE("Texture"),
        COLOR_HUE("Color_Hue"),
        SHAPE("Shape"),
        TEXT("Text");

        private final String varVisual;

        VAR_VISUAIS_CATEGORICAS(String varVisual) {
            this.varVisual = varVisual;
        }

        public String nomeVariavelVisual() {
            return varVisual;
        }
    }
    
    public enum CONTINUOUS_GLYPH_TYPE{
        PROFILE ("Profile"),
        STAR ("Star"),
        PIE ("Pie"),
        ANG ("Ang");
        
        private final String name;

        private CONTINUOUS_GLYPH_TYPE(String name) {
            this.name = name;
        }
        
        public String getName(){
            return name;
        }
    }

    public static boolean isShowGlyph() {
        return showGlyph;
    }

    public static void setShowGlyph(boolean aShowGlyph) {
        showGlyph = aShowGlyph;
    }

    public static boolean isShowLegenda() {
        return showLegenda;
    }

    public static void setShowLegenda(boolean aShowLegenda) {
        showLegenda = aShowLegenda;
    }

    public static boolean isShowStarGlyph() {
        return showStarGlyph;
    }

    public static void setShowStarGlyph(boolean aShowStarGlyph) {
        showStarGlyph = aShowStarGlyph;
    }

    /**
     * Vetor de cores usado na dimensao cores do
     * treemap
     *
     * @return
     */
    public static String[] getCorTreemap() {
        return corTreemap;
    }

    /**
     * Vetor usado no segundo nivel de glyphs
     * @return the colorHueGlyphs
     */
    public static String[] getColorHueGlyphs() {
        return colorHueGlyphs;
    }
    
    /**
     * Vetor usado nos glyphs continuous
     * @return the colorContinuousGlyph
     */
    public static String[] getColorContinuousGlyphs() {
        return colorContinuousGlyph;
    }

}

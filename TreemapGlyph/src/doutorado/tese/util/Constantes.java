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
    public static final int PRESENCA_TEXTURA = 0;
    public static final int PRESENCA_COR_FORMA = 1;
    public static final int PRESENCA_FORMA = 2;//SP -SHAPE PRESENCE
    public static final int PRESENCA_LETRA = 3;
    public static final int PRESENCA_NUMERO = 4;
    public static final int FEATURE_ALTURA = 5;
    public static final int FEATURE_LARGURA = 6;
    public static final int FEATURE_AREA = 7;//TIA - TOTAL ITEM AREA
    public static final int FEATURE_ASPECT = 8;
    public static final int PRESENCA_COR_TREEMAP = 9;//
    public static final int AREA_TEXTURA = 10;//TA - TEXTURE AREA
    public static final int AREA_CIRCULO_COLORIDO = 11;//CCA - Colored circle area
    public static final int AREA_SHAPE = 12;//SA - SHAPE AREA
    public static final int AREA_LETRA = 13;
    public static final int AREA_NUMERO = 14;//NA - NUMBER AREA
    public static final int PRESENTE = 1;
    public static final int AUSENTE = 0;

//    private static Color[] cores = {Color.BLUE, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.YELLOW};
    private static String[] cor = {
        "#3366cc", "#dc3912", "#ff9900", "#109618", "#990099", "#0099c6", "#dd4477",
        "#66aa00", "#b82e2e", "#316395", "#994499", "#22aa99", "#aaaa11", "#6633cc",
        "#e67300", "#8b0707", "#651067", "#329262", "#5574a6", "#3b3eac", "#F0F8FF"
    };

    private static String[] corGlyphs = {
        "#ff0101", "#f8d930", "#174580", "#00a1d7",
        "#0a8300", "#ff3797", "#8c8589", "#000000"
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
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
        "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    public final static String[] NUMEROS = {
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"
    };
    public static final int LIMITE_TESTES = 100;

    public enum NivelGlyph {
        NIVEL_1(1),
        NIVEL_2(2),
        NIVEL_3(3),
        NIVEL_4(4),
        NIVEL_5(5);

        private final int nivelGlyph;

        NivelGlyph(int nivelGlyph) {
            this.nivelGlyph = nivelGlyph;
        }

        private double nivel() {
            return nivelGlyph;
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
     * Vetor de cores usado no segundo nivel de glyphs e na dimensao cores do
     * treemap
     *
     * @return
     */
    public static String[] getCor() {
        return cor;
    }

    /**
     * @return the corGlyphs
     */
    public static String[] getCorGlyphs() {
        return corGlyphs;
    }

}

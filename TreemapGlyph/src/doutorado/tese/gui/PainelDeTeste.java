/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.gui;

import doutorado.tese.util.Constantes;
import doutorado.tese.visualizacao.glyph.GlyphManager;
import doutorado.tese.visualizacao.glyph.formasgeometricas.GeometryFactory;
import doutorado.tese.visualizacao.treemap.TreeMapItem;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashMap;

/**
 *
 * @author Elvis (LABVIS)
 */
public class PainelDeTeste extends javax.swing.JPanel {

    private HashMap<String, Integer> configs;
    private HashMap<String, Boolean> output;
    private HashMap<String, Integer> areas;
    private AreaCallback areaCallback;

    /**
     * Creates new form PainelDeTeste
     */
    public PainelDeTeste() {
        configs = new HashMap<>();
        output = new HashMap<>();
        areas = new HashMap<>();
        initComponents();
        this.areaCallback = new AreaCallback() {
            @Override
            public void areaUpdated(HashMap<String, Integer> areas) {
            }
        };
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.
        Graphics2D g2d = (Graphics2D) g;

        Color coritem = configs.get("coritem") >= 0
                ? Color.decode(Constantes.getCor()[configs.get("coritem")])
                : Color.decode(Constantes.getCor()[Constantes.getCor().length - 1]);
        g2d.setColor(coritem);
        g2d.fillRect(configs.get("x"), configs.get("y"), configs.get("width"), configs.get("height"));
        g2d.setColor(Color.BLACK);
        g2d.drawRect(configs.get("x"), configs.get("y"), configs.get("width"), configs.get("height"));

        GlyphManager manager = new GlyphManager();
        Rectangle bounds = new Rectangle(configs.get("x"), configs.get("y"), configs.get("width"), configs.get("height"));
        TreeMapItem item = new TreeMapItem();
        System.out.println("width: " + configs.get("width") + " height: " + configs.get("height"));
        int areaTextura = 0, areaFormaCor = 0, areaForma = 0, areaLetra = 0, areaNumero = 0;
        if (configs.get("textura") >= 0) {
            areaTextura = manager.prepareTextura(bounds, Constantes.TIPO_TEXTURA[configs.get("textura")], item);
            manager.paintTextura(g, item);
        }
        if (configs.get("cor") >= 0) {
            areaFormaCor = manager.prepareCorForma(bounds, Color.decode(Constantes.getCorGlyphs()[configs.get("cor")]), item);
            manager.paintCorForma(g, item);
        }
        if (configs.get("forma") >= 0) {
            areaForma = manager.prepareFormaGeometrica(bounds, GeometryFactory.FORMAS.GLYPH_FORMAS.values()[configs.get("forma")], item);
            manager.paintFormaGeometrica(g, item);
        }
        if (configs.get("letra") >= 0 && configs.get("numero") >= 0) {
            areaNumero = manager.prepareNumeros(bounds, Constantes.LETRAS_ALFABETO[configs.get("letra")] + Constantes.NUMEROS[configs.get("numero")], item);
            manager.paintNumeros(g, item);
            areaLetra = areaNumero;
        } else {
            if (configs.get("letra") >= 0) {
                areaLetra = manager.prepareLetrasAlfabeto(bounds, Constantes.LETRAS_ALFABETO[configs.get("letra")], item);
                manager.paintLetrasAlfabeto(g, item);
            }
            if (configs.get("numero") >= 0) {
                areaNumero = manager.prepareNumeros(bounds, Constantes.NUMEROS[configs.get("numero")], item);
                manager.paintNumeros(g, item);
            }
        }
        areas.put("textura", areaTextura);
        areas.put("cor", areaFormaCor);
        areas.put("forma", areaForma);
        areas.put("letra", areaLetra);
        areas.put("numero", areaNumero);

        this.areaCallback.areaUpdated(areas);

        bounds.x = 400;

        g2d.setColor(coritem);
        g2d.fillRect(bounds.x, bounds.y, configs.get("width"), configs.get("height"));
        g2d.setColor(Color.BLACK);
        g2d.drawRect(bounds.x, bounds.y, configs.get("width"), configs.get("height"));

        if (output.get("texture") && configs.get("textura") >= 0) {
            manager.prepareTextura(bounds, Constantes.TIPO_TEXTURA[configs.get("textura")], item);
            manager.paintTextura(g, item);
        }
        if (output.get("circle") && configs.get("cor") >= 0) {
            manager.prepareCorForma(bounds, Color.decode(Constantes.getCorGlyphs()[configs.get("cor")]), item);
            manager.paintCorForma(g, item);
        }
        if (output.get("geometry") && configs.get("forma") >= 0) {
            manager.prepareFormaGeometrica(bounds, GeometryFactory.FORMAS.GLYPH_FORMAS.values()[configs.get("forma")], item);
            manager.paintFormaGeometrica(g, item);
        }
        if (output.get("letter") && output.get("number") && configs.get("numero") >= 0 && configs.get("letra") >= 0) {
            manager.prepareNumeros(bounds, Constantes.LETRAS_ALFABETO[configs.get("letra")] + Constantes.NUMEROS[configs.get("numero")], item);
            manager.paintNumeros(g, item);
        } else {
            if (output.get("letter") && configs.get("letra") >= 0) {
                manager.prepareLetrasAlfabeto(bounds, Constantes.LETRAS_ALFABETO[configs.get("letra")], item);
                manager.paintLetrasAlfabeto(g, item);
            }
            if (output.get("number") && configs.get("numero") >= 0) {
                manager.prepareNumeros(bounds, Constantes.NUMEROS[configs.get("numero")], item);
                manager.paintNumeros(g, item);
            }
        }
    }

    public void setAreaCallback(AreaCallback areaCallback) {
        this.areaCallback = areaCallback;
    }

    public interface AreaCallback {

        public void areaUpdated(HashMap<String, Integer> areas);
    }

    public void setConfigs(HashMap<String, Integer> configs) {
        this.configs = configs;
        repaint();
    }

    public void updateOutput(HashMap<String, Boolean> output) {
        this.output = output;
        repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

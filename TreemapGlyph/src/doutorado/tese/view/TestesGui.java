/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.view;

import doutorado.tese.control.business.visualizations.glyph.Glyph;
import doutorado.tese.control.business.visualizations.glyph.GlyphConcrete;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.orientation.Orientation;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.position.Position;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.shapes.GeometricShape;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.text.Text;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.texture.Texture;
import doutorado.tese.control.business.visualizations.glyph.factorys.variaveisvisuais.GeometricalFactory;
import doutorado.tese.control.business.visualizations.glyph.factorys.variaveisvisuais.OrientationFactory;
import doutorado.tese.control.business.visualizations.glyph.factorys.variaveisvisuais.TexturesFactory;
import doutorado.tese.model.TreeMapItem;
import doutorado.tese.util.Constantes;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JFrame;

/**
 *
 * @author Anderson Soares
 */
public class TestesGui extends javax.swing.JPanel {

    TreeMapItem item;

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        TestesGui test = new TestesGui();
        
        f.add(test);
        f.setSize(500, 500);
        f.setLocationRelativeTo(null);
        f.setVisible(true);

        test.repaint();
    }

    /**
     * Creates new form TestesGui
     */
    public TestesGui() {
        initComponents();
        
        item = criarTreemapItem(new TreeMapItem(1, 0));

        Glyph father = item.getGlyph();
//        Glyph child = criarOrientacao(OrientationFactory.ARROW.GLYPH_ORIENTACAO.ARROW90);
//        Glyph child = criarTextura(TexturesFactory.TEXTURE.GLYPH_TEXTURAS.CIRCULO_3x3);
//        Glyph child = criarTextura(TexturesFactory.TEXTURE.GLYPH_TEXTURAS.CIRCULO_3x3_BRANCO);
//        Glyph child = criarForma(GeometricalFactory.FORMAS.GLYPH_FORMAS.PENTAGONO);
//        Glyph child = criarPosicao(Constantes.POSICOES.DIR_INF);
        Glyph child = criarTexto(Constantes.LETRAS_ALFABETO[4]);
        
        child.setNodeTreemap(item);

        father.appendChild(child);

        if (father.getBounds() != null) {
            father.setBounds(father.getBounds());
        }
    }
    
    private GeometricShape criarForma(GeometricalFactory.FORMAS.GLYPH_FORMAS forma){
        GeometricShape s = new GeometricShape();
        s.setDrawBehavior(GeometricalFactory.create(forma));
        s.setOverlappingActivated(true);
        return s;
    }

    private Orientation criarOrientacao(OrientationFactory.ARROW.GLYPH_ORIENTACAO orientacao) {
        Orientation o = new Orientation();
        o.setDrawBehavior(OrientationFactory.create(orientacao));
//        o.setPectSobreposicao(0.65f);
        o.setOverlappingActivated(true);
        return o;
    }
    
    private Texture criarTextura(TexturesFactory.TEXTURE.GLYPH_TEXTURAS textura) {
        Texture o = new Texture();
        o.setDrawBehavior(TexturesFactory.create(textura));
//        o.setPectSobreposicao(0.65f);
        o.setOverlappingActivated(true);
        return o;
    }
    
    private Position criarPosicao(Constantes.POSICOES posicao) {
        Position p = new Position();
        p.setPosicao(posicao);
        p.setOverlappingActivated(true);
        return p;
    }

    private Text criarTexto(String letra) {
        Text t = new Text();
        t.setLetra(letra);
        t.setOverlappingActivated(true);
        return t;
    }
    
    private TreeMapItem criarTreemapItem(TreeMapItem item) {
        Rectangle bounds = new Rectangle(
                this.getBounds().x,
                this.getBounds().y,
                500,
                500);        
        
        try {
            item.setBounds(bounds);
            Glyph glyphConcrete = new GlyphConcrete();
            glyphConcrete.setNodeTreemap(item);
            item.setGlyph(glyphConcrete);
            item.getGlyph().setBounds(item.getBounds());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.

        Graphics2D g2d = (Graphics2D) g;

//        ArrayList<Glyph> listItemOutput = new ArrayList<>();
        item.getGlyph().paint(g2d);
//        item.getGlyph().getChildren(listItemOutput);

//        System.out.println(listItemOutput.toString());
        g2d.setClip(0, 0, getBounds().width, getBounds().height);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(51, 51, 51));
        setPreferredSize(new java.awt.Dimension(500, 500));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 211, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 188, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

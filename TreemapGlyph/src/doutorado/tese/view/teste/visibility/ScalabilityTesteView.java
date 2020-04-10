/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.view.teste.visibility;

import doutorado.tese.control.business.visualizations.glyph.factorys.variaveisvisuais.GeometryFactory;
import doutorado.tese.control.business.visualizations.glyph.factorys.variaveisvisuais.OrientationFactory;
import doutorado.tese.control.business.visualizations.glyph.factorys.variaveisvisuais.TexturesFactory;
import doutorado.tese.control.mb.testeMB.scalabilityMB.SetUpScalabilityTestMB;
import doutorado.tese.util.Constantes;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Anderson
 */
public class ScalabilityTesteView extends javax.swing.JFrame {

    private HashMap<String, Integer> configs;
    private HashMap<String, Boolean> output;
    private Random rand;
    private String data;
    private boolean selectAll = true;
    private HashMap<String, Integer> areas;
    private int[] glyphlayers2draw = {0, 1, 2, 3, 4, 5, 6};
    private String[] layers = new String[]{"texture", "colorhue", "geometricshape", "text", "position", "orientation", "coritem"};
    private HashMap<String, JCheckBox> checkboxes;

    private int cont = 0;
    private int numLayers2remove = 0;
    private int numAmostras = 100;
    private int step = (numAmostras / 6) + 1;
    private String nomeArquivo = "result_" + System.getProperty("user.name") + ".csv";

    /**
     * Creates new form Main
     */
    public ScalabilityTesteView() {

        data = "Textura,Cor,Forma,Texto,Posicao,Orientacao,ProfileGlyph,"
                + "Altura,Largura,"
                + "AreaItem,AspectoItem,CorItem,"
                + "AreaTextura,AreaCor,AreaForma,AreaTexto,AreaPosicao,AreaOrientacao,AreaProfileGlyph,"
                + "ViuTextura,ViuCor,ViuForma,ViuTexto,ViuPosicao,ViuOrientacao,ViuProfileGlyph,FamiliaGlyph";
        rand = new Random(System.currentTimeMillis());
        configs = new HashMap<>();
        output = new HashMap<>();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ScalabilityTesteView.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents();

//        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        checkboxes = new HashMap<>();
        checkboxes.put("texture", checkboxTexture);
        checkboxes.put("colorhue", checkboxColorHue);
        checkboxes.put("geometricshape", checkboxGeometry);
        checkboxes.put("text", checkboxLetter);
        checkboxes.put("position", checkboxPosition);
        checkboxes.put("orientation", checkboxOrientation);
        checkboxes.put("profileglyph", checkboxProfileGlyph);

        configCheckBox();
        changeConfigs();

        this.painelEsquerda.setAreaCallback(new PainelDeTeste.AreaCallback() {
            @Override
            public void areaUpdated(HashMap<String, Integer> areas) {
                ScalabilityTesteView.this.areas = areas;
            }
        });

        updateOutput();
    }

    private void configCheckBox() {
        for (JCheckBox c : checkboxes.values()) {
            c.setSelected(false);
//            c.setEnabled(true);//antes
            c.setEnabled(false);
        }

    }

    public void changeConfigs() {
        selectAll = true;
        cont++;
        contadorLabel.setText(cont + " / " + numAmostras);

        if (cont % step == 0) {
            numLayers2remove++;
        }

        configs.put("texture", rand.nextInt(TexturesFactory.TEXTURE.GLYPH_TEXTURAS.values().length));
        configs.put("colorhue", rand.nextInt(Constantes.getColorHueGlyphs().length));
        configs.put("geometricshape", rand.nextInt(GeometryFactory.FORMAS.GLYPH_FORMAS.values().length - 1));
        configs.put("text", rand.nextInt(Constantes.LETRAS_ALFABETO.length));
        configs.put("position", rand.nextInt(Constantes.POSICOES.values().length));
        configs.put("orientation", rand.nextInt(OrientationFactory.ARROW.GLYPH_ORIENTACAO.values().length));
        configs.put("profileglyph", rand.nextInt(3));//nesse caso 0 - nao desenha, 1 - desenha ao inves do text; 2 - desenha na ultima camada
        configs.put("x", 50);
        configs.put("y", 50);
        int length = rand.nextInt(50) + 5;
        configs.put("width", Math.abs(length - rand.nextInt(100)));
        configs.put("height", Math.abs(length - rand.nextInt(100)));
        configs.put("coritem", rand.nextInt(Constantes.getCorTreemap().length));

        SetUpScalabilityTestMB.shuffleArray(glyphlayers2draw);
        //dependendo da step as camadas serao removidas (representado por -1) do glyph e seu respectivo checkbox sera desabilitado
        for (int i = 0; i < numLayers2remove; i++) {
            configs.put(layers[glyphlayers2draw[i]], -1);
//            if (checkboxes.get(layers[glyphlayers2draw[i]]) != null) {
//                checkboxes.get(layers[glyphlayers2draw[i]]).setEnabled(false);
//            }
        }
        painelEsquerda.setInputConfigs(configs);

        for (int i = 1; i <= painelEsquerda.getFamilia2Desenho().size() - 1; i++) {
            int index = painelEsquerda.getFamilia2Desenho().get(i).whoAmI().toString().lastIndexOf('.');
            String nomeClasse = "";
            if (index > 0) {
                nomeClasse = painelEsquerda.getFamilia2Desenho().get(i).whoAmI().toString().substring(index + 1).toLowerCase();
            }
            checkboxes.get(nomeClasse).setEnabled(true);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        painelEsquerda = new PainelDeTeste();
        separador = new javax.swing.JSeparator();
        glyphsLabel = new javax.swing.JLabel();
        shouldBeLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        btnConfirm = new javax.swing.JButton();
        checkboxTexture = new javax.swing.JCheckBox();
        checkboxColorHue = new javax.swing.JCheckBox();
        checkboxGeometry = new javax.swing.JCheckBox();
        checkboxLetter = new javax.swing.JCheckBox();
        checkboxPosition = new javax.swing.JCheckBox();
        btnSelectAll = new javax.swing.JButton();
        contadorLabel = new javax.swing.JLabel();
        checkboxProfileGlyph = new javax.swing.JCheckBox();
        checkboxOrientation = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Treemap Glyphs");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        jSplitPane1.setDividerLocation(400);
        jSplitPane1.setOpaque(false);

        painelEsquerda.setBackground(new java.awt.Color(153, 255, 153));
        painelEsquerda.setOpaque(false);

        separador.setOrientation(javax.swing.SwingConstants.VERTICAL);

        glyphsLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        glyphsLabel.setText("The Glyph:");

        shouldBeLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        shouldBeLabel.setText("How it should be:");

        javax.swing.GroupLayout painelEsquerdaLayout = new javax.swing.GroupLayout(painelEsquerda);
        painelEsquerda.setLayout(painelEsquerdaLayout);
        painelEsquerdaLayout.setHorizontalGroup(
            painelEsquerdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelEsquerdaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(glyphsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(separador, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(shouldBeLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        painelEsquerdaLayout.setVerticalGroup(
            painelEsquerdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelEsquerdaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelEsquerdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelEsquerdaLayout.createSequentialGroup()
                        .addComponent(shouldBeLabel)
                        .addContainerGap(227, Short.MAX_VALUE))
                    .addGroup(painelEsquerdaLayout.createSequentialGroup()
                        .addComponent(glyphsLabel)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addComponent(separador)
        );

        jSplitPane1.setLeftComponent(painelEsquerda);

        jTextPane1.setEditable(false);
        jTextPane1.setBorder(null);
        jTextPane1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jTextPane1.setText("Which of the glyph's layers do you identify clearly?");
        jTextPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTextPane1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextPane1.setEnabled(false);
        jScrollPane2.setViewportView(jTextPane1);

        btnConfirm.setText("Confirm");
        btnConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmActionPerformed(evt);
            }
        });

        checkboxTexture.setText("Texture");
        checkboxTexture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxTextureActionPerformed(evt);
            }
        });

        checkboxColorHue.setText("Color Hue");
        checkboxColorHue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxColorHueActionPerformed(evt);
            }
        });

        checkboxGeometry.setText("Shape");
        checkboxGeometry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxGeometryActionPerformed(evt);
            }
        });

        checkboxLetter.setText("Text");
        checkboxLetter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxLetterActionPerformed(evt);
            }
        });

        checkboxPosition.setText("Position");
        checkboxPosition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxPositionActionPerformed(evt);
            }
        });

        btnSelectAll.setText("SelectAll");
        btnSelectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectAllActionPerformed(evt);
            }
        });

        contadorLabel.setText("0 / 100");

        checkboxProfileGlyph.setText("Profile Glyph");
        checkboxProfileGlyph.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxProfileGlyphActionPerformed(evt);
            }
        });

        checkboxOrientation.setText("Orientation");
        checkboxOrientation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxOrientationActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(contadorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkboxTexture)
                            .addComponent(checkboxColorHue))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkboxGeometry)
                            .addComponent(checkboxLetter))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(checkboxPosition)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(checkboxOrientation))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(checkboxProfileGlyph)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSelectAll)))
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkboxTexture)
                    .addComponent(checkboxGeometry)
                    .addComponent(checkboxPosition)
                    .addComponent(checkboxOrientation))
                .addGap(2, 2, 2)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkboxColorHue)
                    .addComponent(checkboxLetter)
                    .addComponent(btnSelectAll)
                    .addComponent(checkboxProfileGlyph))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contadorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public String[] parseListString2Array(ListModel<String> lista) {
        String[] convertida = new String[lista.getSize()];
        for (int i = 0; i < convertida.length; i++) {
            convertida[i] = lista.getElementAt(i);
        }
        return convertida;
    }

    private void btnConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmActionPerformed
        float aspect = configs.get("height") > configs.get("width")
                ? (configs.get("width") * 1.f) / configs.get("height")
                : (configs.get("height") * 1.f) / configs.get("width");

        data += "\n" + (configs.get("texture") >= 0 ? 1 : 0)
                + "," + (configs.get("colorhue") >= 0 ? 1 : 0)
                + "," + (configs.get("geometricshape") >= 0 ? 1 : 0)
                + "," + (configs.get("text") >= 0 ? 1 : 0)
                + "," + (configs.get("position") >= 0 ? 1 : 0)
                + "," + (configs.get("orientation") >= 0 ? 1 : 0)
                + "," + (configs.get("profileglyph") > 0 ? 1 : 0)
                + "," + configs.get("height")
                + "," + configs.get("width")
                + "," + (configs.get("width") * configs.get("height"))
                + "," + aspect
                + "," + (configs.get("coritem") >= 0 ? 1 : 0)
                + "," + areas.get("texture")
                + "," + areas.get("colorhue")
                + "," + areas.get("geometricshape")
                + "," + areas.get("text")
                + "," + areas.get("position")
                + "," + areas.get("orientation")
                + "," + areas.get("profileglyph")
                + "," + (checkboxTexture.isSelected() ? 1 : 0)
                + "," + (checkboxColorHue.isSelected() ? 1 : 0)
                + "," + (checkboxGeometry.isSelected() ? 1 : 0)
                + "," + (checkboxLetter.isSelected() ? 1 : 0)
                + "," + (checkboxPosition.isSelected() ? 1 : 0)
                + "," + (checkboxOrientation.isSelected() ? 1 : 0)
                + "," + (checkboxProfileGlyph.isSelected() ? 1 : 0)
                + "," + painelEsquerda.getFamilia2Desenho().toString();

        for (JCheckBox c : checkboxes.values()) {
            c.setSelected(false);
//            c.setEnabled(true);//antes
            c.setEnabled(false);
        }
//        configCheckBox();

        output.put("texture", false);
        output.put("colorhue", false);
        output.put("geometricshape", false);
        output.put("text", false);
        output.put("position", false);
        output.put("orientation", false);
        output.put("profileglyph", false);

        if (cont >= numAmostras) {
            PrintWriter writer = null;
            try {
                File file = new File(nomeArquivo);
                writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
                writer.print(data);
                writer.flush();
                writer.close();
                JOptionPane.showMessageDialog(null, "Thank you for participating.", "Thanks!", JOptionPane.INFORMATION_MESSAGE);
//                System.exit(0);
                this.dispose();
            } catch (IOException ex) {
                Logger.getLogger(ScalabilityTesteView.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                writer.close();
            }
        }
        changeConfigs();
        updateOutput();
    }//GEN-LAST:event_btnConfirmActionPerformed

    private void checkboxTextureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxTextureActionPerformed
        updateOutput();
    }//GEN-LAST:event_checkboxTextureActionPerformed

    private void checkboxColorHueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxColorHueActionPerformed
        updateOutput();
    }//GEN-LAST:event_checkboxColorHueActionPerformed

    private void btnSelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectAllActionPerformed
        for (JCheckBox c : checkboxes.values()) {
            if (c.isEnabled()) {
                c.setSelected(selectAll);
            }
        }
        selectAll = !selectAll;
        updateOutput();
    }//GEN-LAST:event_btnSelectAllActionPerformed


    private void checkboxGeometryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxGeometryActionPerformed
        updateOutput();
    }//GEN-LAST:event_checkboxGeometryActionPerformed

    private void checkboxLetterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxLetterActionPerformed
        updateOutput();
    }//GEN-LAST:event_checkboxLetterActionPerformed

    private void checkboxPositionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxPositionActionPerformed
        updateOutput();
    }//GEN-LAST:event_checkboxPositionActionPerformed

    private void checkboxProfileGlyphActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxProfileGlyphActionPerformed
        updateOutput();
    }//GEN-LAST:event_checkboxProfileGlyphActionPerformed

    private void checkboxOrientationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxOrientationActionPerformed
        updateOutput();
    }//GEN-LAST:event_checkboxOrientationActionPerformed

    private void updateOutput() {
        output.put("texture", checkboxTexture.isSelected());
        output.put("colorhue", checkboxColorHue.isSelected());
        output.put("geometricshape", checkboxGeometry.isSelected());
        output.put("text", checkboxLetter.isSelected());
        output.put("position", checkboxPosition.isSelected());
        output.put("orientation", checkboxOrientation.isSelected());
        output.put("profileglyph", checkboxProfileGlyph.isSelected());
        painelEsquerda.updateOutput(output);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ScalabilityTesteView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            frame = new ScalabilityTesteView();
            frame.setVisible(true);
        });
    }
    /*
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfirm;
    private javax.swing.JButton btnSelectAll;
    private javax.swing.JCheckBox checkboxColorHue;
    private javax.swing.JCheckBox checkboxGeometry;
    private javax.swing.JCheckBox checkboxLetter;
    private javax.swing.JCheckBox checkboxOrientation;
    private javax.swing.JCheckBox checkboxPosition;
    private javax.swing.JCheckBox checkboxProfileGlyph;
    private javax.swing.JCheckBox checkboxTexture;
    private javax.swing.JLabel contadorLabel;
    private javax.swing.JLabel glyphsLabel;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JPanel painelEsquerda;
    private javax.swing.JSeparator separador;
    private javax.swing.JLabel shouldBeLabel;
    // End of variables declaration//GEN-END:variables
    */

    private javax.swing.JButton btnConfirm;
    private javax.swing.JCheckBox checkboxColorHue;
    private javax.swing.JCheckBox checkboxGeometry;
    private javax.swing.JCheckBox checkboxLetter;
    private javax.swing.JCheckBox checkboxPosition;
    private javax.swing.JCheckBox checkboxOrientation;
    private javax.swing.JCheckBox checkboxTexture;
    private javax.swing.JCheckBox checkboxProfileGlyph;
    private javax.swing.JButton btnSelectAll;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextPane jTextPane1;
    private PainelDeTeste painelEsquerda;
    static ScalabilityTesteView frame;
    private javax.swing.JLabel contadorLabel;
    private javax.swing.JLabel glyphsLabel;
    private javax.swing.JLabel shouldBeLabel;
    private JSeparator separador;

    
}

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
public class VisibilityTesteView extends javax.swing.JFrame {

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
    public VisibilityTesteView() {

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
            Logger.getLogger(VisibilityTesteView.class.getName()).log(Level.SEVERE, null, ex);
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
                VisibilityTesteView.this.areas = areas;
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
        btnSelectAll = new javax.swing.JButton();
        contadorLabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        checkboxTexture = new javax.swing.JCheckBox();
        cor1RadioButton2 = new javax.swing.JRadioButton();
        cor3Label2 = new javax.swing.JLabel();
        cor4Label2 = new javax.swing.JLabel();
        cor5RadioButton2 = new javax.swing.JRadioButton();
        cor1Label2 = new javax.swing.JLabel();
        cor2Label2 = new javax.swing.JLabel();
        cor3RadioButton2 = new javax.swing.JRadioButton();
        cor5Label2 = new javax.swing.JLabel();
        cor2RadioButton2 = new javax.swing.JRadioButton();
        cor4RadioButton2 = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        checkboxColorHue = new javax.swing.JCheckBox();
        cor4RadioButton = new javax.swing.JRadioButton();
        cor5RadioButton = new javax.swing.JRadioButton();
        cor1RadioButton = new javax.swing.JRadioButton();
        cor2RadioButton = new javax.swing.JRadioButton();
        cor3RadioButton = new javax.swing.JRadioButton();
        cor1Label = new javax.swing.JLabel();
        cor2Label = new javax.swing.JLabel();
        cor3Label = new javax.swing.JLabel();
        cor4Label = new javax.swing.JLabel();
        cor5Label = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        checkboxGeometry = new javax.swing.JCheckBox();
        cor3Label1 = new javax.swing.JLabel();
        cor4Label1 = new javax.swing.JLabel();
        cor5RadioButton1 = new javax.swing.JRadioButton();
        cor1Label1 = new javax.swing.JLabel();
        cor2Label1 = new javax.swing.JLabel();
        cor2RadioButton1 = new javax.swing.JRadioButton();
        cor5Label1 = new javax.swing.JLabel();
        cor1RadioButton1 = new javax.swing.JRadioButton();
        cor4RadioButton1 = new javax.swing.JRadioButton();
        cor3RadioButton1 = new javax.swing.JRadioButton();
        jPanel5 = new javax.swing.JPanel();
        checkboxLetter = new javax.swing.JCheckBox();
        cor4RadioButton3 = new javax.swing.JRadioButton();
        cor4Label3 = new javax.swing.JLabel();
        cor1Label3 = new javax.swing.JLabel();
        cor5RadioButton3 = new javax.swing.JRadioButton();
        cor3Label3 = new javax.swing.JLabel();
        cor1RadioButton3 = new javax.swing.JRadioButton();
        cor3RadioButton3 = new javax.swing.JRadioButton();
        cor2RadioButton3 = new javax.swing.JRadioButton();
        cor2Label3 = new javax.swing.JLabel();
        cor5Label3 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        checkboxOrientation = new javax.swing.JCheckBox();
        cor4RadioButton5 = new javax.swing.JRadioButton();
        cor5RadioButton5 = new javax.swing.JRadioButton();
        cor3RadioButton5 = new javax.swing.JRadioButton();
        cor2RadioButton5 = new javax.swing.JRadioButton();
        cor4Label5 = new javax.swing.JLabel();
        cor3Label5 = new javax.swing.JLabel();
        cor5Label5 = new javax.swing.JLabel();
        cor1RadioButton5 = new javax.swing.JRadioButton();
        cor2Label5 = new javax.swing.JLabel();
        cor1Label5 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        checkboxPosition = new javax.swing.JCheckBox();
        cor2RadioButton4 = new javax.swing.JRadioButton();
        cor1RadioButton4 = new javax.swing.JRadioButton();
        cor5RadioButton4 = new javax.swing.JRadioButton();
        cor4Label4 = new javax.swing.JLabel();
        cor3Label4 = new javax.swing.JLabel();
        cor2Label4 = new javax.swing.JLabel();
        cor1Label4 = new javax.swing.JLabel();
        cor5Label4 = new javax.swing.JLabel();
        cor3RadioButton4 = new javax.swing.JRadioButton();
        cor4RadioButton4 = new javax.swing.JRadioButton();
        jPanel9 = new javax.swing.JPanel();
        checkboxProfileGlyph = new javax.swing.JCheckBox();

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
                        .addContainerGap(469, Short.MAX_VALUE))
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

        btnSelectAll.setText("SelectAll");
        btnSelectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectAllActionPerformed(evt);
            }
        });

        contadorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        contadorLabel.setText("0 / 100");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(75, 55));

        checkboxTexture.setText("Texture");
        checkboxTexture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxTextureActionPerformed(evt);
            }
        });

        cor1RadioButton2.setEnabled(false);

        cor3Label2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/textura/textura5.PNG"))); // NOI18N
        cor3Label2.setText("jLabel1");
        cor3Label2.setEnabled(false);

        cor4Label2.setBackground(new java.awt.Color(255, 255, 255));
        cor4Label2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/textura/textura1.PNG"))); // NOI18N
        cor4Label2.setEnabled(false);

        cor5RadioButton2.setEnabled(false);

        cor1Label2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/textura/textura3.PNG"))); // NOI18N
        cor1Label2.setText("jLabel1");
        cor1Label2.setEnabled(false);

        cor2Label2.setBackground(new java.awt.Color(255, 255, 255));
        cor2Label2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/textura/textura4.PNG"))); // NOI18N
        cor2Label2.setEnabled(false);

        cor3RadioButton2.setEnabled(false);

        cor5Label2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/textura/textura2.PNG"))); // NOI18N
        cor5Label2.setText("jLabel1");
        cor5Label2.setEnabled(false);

        cor2RadioButton2.setEnabled(false);

        cor4RadioButton2.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkboxTexture)
                .addGap(18, 18, 18)
                .addComponent(cor4RadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor4Label2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cor5RadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor5Label2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(cor1RadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor1Label2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(cor2RadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor2Label2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(cor3RadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor3Label2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(cor5RadioButton2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(cor2RadioButton2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(cor3RadioButton2))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(cor3Label2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cor2Label2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cor1Label2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cor5Label2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cor4Label2, javax.swing.GroupLayout.Alignment.LEADING))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(checkboxTexture)
                            .addComponent(cor4RadioButton2)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(cor1RadioButton2)))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        checkboxColorHue.setText("Color Hue");
        checkboxColorHue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxColorHueActionPerformed(evt);
            }
        });

        cor4RadioButton.setEnabled(false);

        cor5RadioButton.setEnabled(false);

        cor1RadioButton.setEnabled(false);

        cor2RadioButton.setEnabled(false);

        cor3RadioButton.setEnabled(false);

        cor1Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/cor/cor3.PNG"))); // NOI18N
        cor1Label.setText("jLabel1");
        cor1Label.setEnabled(false);

        cor2Label.setBackground(new java.awt.Color(255, 255, 255));
        cor2Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/cor/cor4.PNG"))); // NOI18N
        cor2Label.setEnabled(false);

        cor3Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/cor/cor5.PNG"))); // NOI18N
        cor3Label.setText("jLabel1");
        cor3Label.setEnabled(false);

        cor4Label.setBackground(new java.awt.Color(255, 255, 255));
        cor4Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/cor/cor1.PNG"))); // NOI18N
        cor4Label.setEnabled(false);

        cor5Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/cor/cor2.PNG"))); // NOI18N
        cor5Label.setText("jLabel1");
        cor5Label.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkboxColorHue)
                .addGap(10, 10, 10)
                .addComponent(cor4RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor4Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cor5RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor5Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(cor1RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor1Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(cor2RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor2Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(cor3RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor3Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(checkboxColorHue)
                            .addComponent(cor4RadioButton)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(cor1RadioButton))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(cor5RadioButton))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(cor2RadioButton))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(cor3RadioButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cor3Label, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cor2Label, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cor1Label, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cor5Label, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cor4Label, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        checkboxGeometry.setText("Shape");
        checkboxGeometry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxGeometryActionPerformed(evt);
            }
        });

        cor3Label1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/forma/forma5.PNG"))); // NOI18N
        cor3Label1.setText("jLabel1");

        cor4Label1.setBackground(new java.awt.Color(255, 255, 255));
        cor4Label1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/forma/forma1.PNG"))); // NOI18N

        cor5RadioButton1.setEnabled(false);

        cor1Label1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/forma/forma3.PNG"))); // NOI18N
        cor1Label1.setText("jLabel1");

        cor2Label1.setBackground(new java.awt.Color(255, 255, 255));
        cor2Label1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/forma/forma4.PNG"))); // NOI18N

        cor2RadioButton1.setEnabled(false);

        cor5Label1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/forma/forma2.PNG"))); // NOI18N
        cor5Label1.setText("jLabel1");

        cor1RadioButton1.setEnabled(false);

        cor4RadioButton1.setEnabled(false);

        cor3RadioButton1.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkboxGeometry)
                .addGap(28, 28, 28)
                .addComponent(cor4RadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor4Label1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cor5RadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor5Label1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(cor1RadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor1Label1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(cor2RadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor2Label1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(cor3RadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor3Label1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(checkboxGeometry)
                .addGap(15, 15, 15))
            .addComponent(cor4Label1)
            .addComponent(cor1Label1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cor5Label1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cor2Label1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cor3Label1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(cor4RadioButton1))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(cor1RadioButton1))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(cor5RadioButton1))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(cor2RadioButton1))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(cor3RadioButton1))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        checkboxLetter.setText("Text");
        checkboxLetter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxLetterActionPerformed(evt);
            }
        });

        cor4RadioButton3.setEnabled(false);

        cor4Label3.setBackground(new java.awt.Color(255, 255, 255));
        cor4Label3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/textura/textura1.PNG"))); // NOI18N
        cor4Label3.setEnabled(false);

        cor1Label3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/textura/textura1.PNG"))); // NOI18N
        cor1Label3.setText("jLabel1");
        cor1Label3.setEnabled(false);

        cor5RadioButton3.setEnabled(false);

        cor3Label3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/textura/textura1.PNG"))); // NOI18N
        cor3Label3.setText("jLabel1");
        cor3Label3.setEnabled(false);

        cor1RadioButton3.setEnabled(false);

        cor3RadioButton3.setEnabled(false);

        cor2RadioButton3.setEnabled(false);

        cor2Label3.setBackground(new java.awt.Color(255, 255, 255));
        cor2Label3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/textura/textura1.PNG"))); // NOI18N
        cor2Label3.setEnabled(false);

        cor5Label3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/textura/textura1.PNG"))); // NOI18N
        cor5Label3.setText("jLabel1");
        cor5Label3.setEnabled(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkboxLetter)
                .addGap(36, 36, 36)
                .addComponent(cor4RadioButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor4Label3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cor5RadioButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor5Label3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(cor1RadioButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor1Label3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(cor2RadioButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor2Label3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(cor3RadioButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor3Label3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(checkboxLetter))
            .addComponent(cor4Label3)
            .addComponent(cor1Label3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cor5Label3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cor2Label3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cor3Label3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(cor4RadioButton3))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(cor1RadioButton3))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(cor5RadioButton3))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(cor2RadioButton3))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(cor3RadioButton3))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        checkboxOrientation.setText("Orientation");
        checkboxOrientation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxOrientationActionPerformed(evt);
            }
        });

        cor4RadioButton5.setEnabled(false);

        cor5RadioButton5.setEnabled(false);

        cor3RadioButton5.setEnabled(false);

        cor2RadioButton5.setEnabled(false);

        cor4Label5.setBackground(new java.awt.Color(255, 255, 255));
        cor4Label5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/textura/textura1.PNG"))); // NOI18N
        cor4Label5.setEnabled(false);

        cor3Label5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/textura/textura1.PNG"))); // NOI18N
        cor3Label5.setText("jLabel1");

        cor5Label5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/textura/textura1.PNG"))); // NOI18N
        cor5Label5.setText("jLabel1");
        cor5Label5.setEnabled(false);

        cor1RadioButton5.setEnabled(false);

        cor2Label5.setBackground(new java.awt.Color(255, 255, 255));
        cor2Label5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/textura/textura1.PNG"))); // NOI18N
        cor2Label5.setEnabled(false);

        cor1Label5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/textura/textura1.PNG"))); // NOI18N
        cor1Label5.setText("jLabel1");
        cor1Label5.setEnabled(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkboxOrientation)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cor4RadioButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor4Label5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(cor5RadioButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor5Label5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(cor1RadioButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor1Label5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(cor2RadioButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor2Label5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(cor3RadioButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor3Label5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cor4Label5, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(cor5Label5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cor2Label5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cor3Label5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cor1Label5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(checkboxOrientation))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(cor4RadioButton5))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(cor5RadioButton5))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(cor2RadioButton5))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(cor3RadioButton5))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(cor1RadioButton5)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        checkboxPosition.setText("Position");
        checkboxPosition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxPositionActionPerformed(evt);
            }
        });

        cor2RadioButton4.setEnabled(false);

        cor1RadioButton4.setEnabled(false);

        cor5RadioButton4.setEnabled(false);

        cor4Label4.setBackground(new java.awt.Color(255, 255, 255));
        cor4Label4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/textura/textura1.PNG"))); // NOI18N
        cor4Label4.setEnabled(false);

        cor3Label4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/textura/textura1.PNG"))); // NOI18N
        cor3Label4.setEnabled(false);

        cor2Label4.setBackground(new java.awt.Color(255, 255, 255));
        cor2Label4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/textura/textura1.PNG"))); // NOI18N
        cor2Label4.setEnabled(false);

        cor1Label4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/textura/textura1.PNG"))); // NOI18N
        cor1Label4.setText("jLabel1");
        cor1Label4.setEnabled(false);

        cor5Label4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/textura/textura1.PNG"))); // NOI18N
        cor5Label4.setText("jLabel1");
        cor5Label4.setEnabled(false);

        cor3RadioButton4.setEnabled(false);

        cor4RadioButton4.setEnabled(false);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkboxPosition)
                .addGap(18, 18, 18)
                .addComponent(cor4RadioButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor4Label4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cor5RadioButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor5Label4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(cor1RadioButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor1Label4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(cor2RadioButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor2Label4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(cor3RadioButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor3Label4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(checkboxPosition)
                .addGap(15, 15, 15))
            .addComponent(cor4Label4, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(cor1Label4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cor5Label4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cor2Label4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cor3Label4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(cor4RadioButton4))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(cor1RadioButton4))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(cor5RadioButton4))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(cor2RadioButton4))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(cor3RadioButton4))
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        checkboxProfileGlyph.setText("Profile Glyph");
        checkboxProfileGlyph.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxProfileGlyphActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(checkboxProfileGlyph)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(checkboxProfileGlyph)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 587, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(btnSelectAll, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(231, 231, 231)
                            .addComponent(btnConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(contadorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnConfirm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(contadorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSelectAll, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1005, Short.MAX_VALUE)
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
                Logger.getLogger(VisibilityTesteView.class.getName()).log(Level.SEVERE, null, ex);
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
            java.util.logging.Logger.getLogger(VisibilityTesteView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
            frame = new VisibilityTesteView();
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
    private javax.swing.JLabel cor1Label;
    private javax.swing.JLabel cor1Label1;
    private javax.swing.JLabel cor1Label2;
    private javax.swing.JLabel cor1Label3;
    private javax.swing.JLabel cor1Label4;
    private javax.swing.JLabel cor1Label5;
    private javax.swing.JRadioButton cor1RadioButton;
    private javax.swing.JRadioButton cor1RadioButton1;
    private javax.swing.JRadioButton cor1RadioButton2;
    private javax.swing.JRadioButton cor1RadioButton3;
    private javax.swing.JRadioButton cor1RadioButton4;
    private javax.swing.JRadioButton cor1RadioButton5;
    private javax.swing.JLabel cor2Label;
    private javax.swing.JLabel cor2Label1;
    private javax.swing.JLabel cor2Label2;
    private javax.swing.JLabel cor2Label3;
    private javax.swing.JLabel cor2Label4;
    private javax.swing.JLabel cor2Label5;
    private javax.swing.JRadioButton cor2RadioButton;
    private javax.swing.JRadioButton cor2RadioButton1;
    private javax.swing.JRadioButton cor2RadioButton2;
    private javax.swing.JRadioButton cor2RadioButton3;
    private javax.swing.JRadioButton cor2RadioButton4;
    private javax.swing.JRadioButton cor2RadioButton5;
    private javax.swing.JLabel cor3Label;
    private javax.swing.JLabel cor3Label1;
    private javax.swing.JLabel cor3Label2;
    private javax.swing.JLabel cor3Label3;
    private javax.swing.JLabel cor3Label4;
    private javax.swing.JLabel cor3Label5;
    private javax.swing.JRadioButton cor3RadioButton;
    private javax.swing.JRadioButton cor3RadioButton1;
    private javax.swing.JRadioButton cor3RadioButton2;
    private javax.swing.JRadioButton cor3RadioButton3;
    private javax.swing.JRadioButton cor3RadioButton4;
    private javax.swing.JRadioButton cor3RadioButton5;
    private javax.swing.JLabel cor4Label;
    private javax.swing.JLabel cor4Label1;
    private javax.swing.JLabel cor4Label2;
    private javax.swing.JLabel cor4Label3;
    private javax.swing.JLabel cor4Label4;
    private javax.swing.JLabel cor4Label5;
    private javax.swing.JRadioButton cor4RadioButton;
    private javax.swing.JRadioButton cor4RadioButton1;
    private javax.swing.JRadioButton cor4RadioButton2;
    private javax.swing.JRadioButton cor4RadioButton3;
    private javax.swing.JRadioButton cor4RadioButton4;
    private javax.swing.JRadioButton cor4RadioButton5;
    private javax.swing.JLabel cor5Label;
    private javax.swing.JLabel cor5Label1;
    private javax.swing.JLabel cor5Label2;
    private javax.swing.JLabel cor5Label3;
    private javax.swing.JLabel cor5Label4;
    private javax.swing.JLabel cor5Label5;
    private javax.swing.JRadioButton cor5RadioButton;
    private javax.swing.JRadioButton cor5RadioButton1;
    private javax.swing.JRadioButton cor5RadioButton2;
    private javax.swing.JRadioButton cor5RadioButton3;
    private javax.swing.JRadioButton cor5RadioButton4;
    private javax.swing.JRadioButton cor5RadioButton5;
    private javax.swing.JLabel glyphsLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
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
    static VisibilityTesteView frame;
    private javax.swing.JLabel contadorLabel;
    private javax.swing.JLabel glyphsLabel;
    private javax.swing.JLabel shouldBeLabel;
    private JSeparator separador;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton cor4RadioButton;
    private javax.swing.JRadioButton cor5RadioButton;
    private javax.swing.JRadioButton cor1RadioButton;
    private javax.swing.JRadioButton cor2RadioButton;
    private javax.swing.JRadioButton cor3RadioButton;
    private javax.swing.JLabel cor1Label;
    private javax.swing.JLabel cor2Label;
    private javax.swing.JLabel cor3Label;
    private javax.swing.JLabel cor4Label;
    private javax.swing.JLabel cor5Label;
    private javax.swing.JLabel cor1Label1;
    private javax.swing.JLabel cor1Label2;
    private javax.swing.JLabel cor1Label3;
    private javax.swing.JLabel cor1Label4;
    private javax.swing.JLabel cor1Label5;
    private javax.swing.JRadioButton cor1RadioButton1;
    private javax.swing.JRadioButton cor1RadioButton2;
    private javax.swing.JRadioButton cor1RadioButton3;
    private javax.swing.JRadioButton cor1RadioButton4;
    private javax.swing.JRadioButton cor1RadioButton5;
    private javax.swing.JLabel cor2Label1;
    private javax.swing.JLabel cor2Label2;
    private javax.swing.JLabel cor2Label3;
    private javax.swing.JLabel cor2Label4;
    private javax.swing.JLabel cor2Label5;
    private javax.swing.JRadioButton cor2RadioButton1;
    private javax.swing.JRadioButton cor2RadioButton2;
    private javax.swing.JRadioButton cor2RadioButton3;
    private javax.swing.JRadioButton cor2RadioButton4;
    private javax.swing.JRadioButton cor2RadioButton5;
    private javax.swing.JLabel cor3Label1;
    private javax.swing.JLabel cor3Label2;
    private javax.swing.JLabel cor3Label3;
    private javax.swing.JLabel cor3Label4;
    private javax.swing.JLabel cor3Label5;
    private javax.swing.JRadioButton cor3RadioButton1;
    private javax.swing.JRadioButton cor3RadioButton2;
    private javax.swing.JRadioButton cor3RadioButton3;
    private javax.swing.JRadioButton cor3RadioButton4;
    private javax.swing.JRadioButton cor3RadioButton5;
    private javax.swing.JLabel cor4Label1;
    private javax.swing.JLabel cor4Label2;
    private javax.swing.JLabel cor4Label3;
    private javax.swing.JLabel cor4Label4;
    private javax.swing.JLabel cor4Label5;
    private javax.swing.JRadioButton cor4RadioButton1;
    private javax.swing.JRadioButton cor4RadioButton2;
    private javax.swing.JRadioButton cor4RadioButton3;
    private javax.swing.JRadioButton cor4RadioButton4;
    private javax.swing.JRadioButton cor4RadioButton5;
    private javax.swing.JLabel cor5Label1;
    private javax.swing.JLabel cor5Label2;
    private javax.swing.JLabel cor5Label3;
    private javax.swing.JLabel cor5Label4;
    private javax.swing.JLabel cor5Label5;
    private javax.swing.JRadioButton cor5RadioButton1;
    private javax.swing.JRadioButton cor5RadioButton2;
    private javax.swing.JRadioButton cor5RadioButton3;
    private javax.swing.JRadioButton cor5RadioButton4;
    private javax.swing.JRadioButton cor5RadioButton5;
}

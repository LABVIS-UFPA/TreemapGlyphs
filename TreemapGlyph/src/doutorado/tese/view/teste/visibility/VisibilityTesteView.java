/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.view.teste.visibility;

import doutorado.tese.control.business.visualizations.glyph.Glyph;
import doutorado.tese.control.business.visualizations.glyph.GlyphConcrete;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.color.ColorHue;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.orientation.Orientation;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.position.Position;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.shapes.GeometricShape;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.text.Text;
import doutorado.tese.control.business.visualizations.glyph.decorator.categorical.variaveisvisuais.texture.Texture;
import doutorado.tese.control.business.visualizations.glyph.decorator.continuous.ProfileGlyph;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.orientation.Arrow0Dir;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.orientation.Arrow135CanEsq;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.orientation.Arrow180Esq;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.orientation.Arrow45CanDir;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.orientation.Arrow90Cima;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.shapes.Circulo;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.shapes.Cruz;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.shapes.Estrela;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.shapes.Quadrado;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.shapes.Serrilhado;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.texture.CirculoTextura_2x2;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.texture.CirculoTextura_2x2_Branco;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.texture.CirculoTextura_3x3;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.texture.CirculoTextura_3x3_Branco;
import doutorado.tese.control.business.visualizations.glyph.strategy.variaveisvisuais.texture.CirculoTextura_4x4;
import doutorado.tese.control.mb.testeMB.scalabilityMB.SetUpVisibilityTestMB;
import doutorado.tese.util.Constantes;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Anderson
 */
public class VisibilityTesteView extends javax.swing.JFrame {

    private HashMap<String, Integer> configs;
    private HashMap<String, String> respostasUsuario;
    private StringBuilder data;
    private HashMap<String, Integer> areas;
    private int numAmostras = 0;//total de amostras calculado no construtor
    private String nomeArquivo = "result_" + System.getProperty("user.name") + ".csv";
    private final SetUpVisibilityTestMB visibilityTestMB;

    /**
     * Creates new form Main
     */
    public VisibilityTesteView() {
        visibilityTestMB = new SetUpVisibilityTestMB();
        data = new StringBuilder();
        data.append(
                "Textura,Cor,Forma,Texto,Posicao,Orientacao,ProfileGlyph,"
                + "Altura,Largura,"
                + "AreaItem,AspectoItem,CorItem,"
                + "AreaTextura,AreaCor,AreaForma,AreaTexto,AreaPosicao,AreaOrientacao,AreaProfileGlyph,"
                //+ "ViuTextura,ViuCor,ViuForma,ViuTexto,ViuPosicao,ViuOrientacao,ViuProfileGlyph,"
                + "FamiliaGlyph,"
                + "TexturaValorUsuario,CorValorUsuario,FormaValorUsuario,TextoValorUsuario,PosicaoValorUsuario,OrientacaoValorUsuario,achouProfileCorreto,"
                + "areaVisivel_Textura,areaVisivel_Cor,areaVisivel_Forma,areaVisivel_Texto,areaVisivel_Posicao,areaVisivel_Orientacao,"
                + "TexturaValorGabarito,CorValorGabarito,FormaValorGabarito,TextoValorGabarito,PosicaoValorGabarito,OrientacaoValorGabarito,"
                + "userScore"
        );
        configs = new HashMap<>();
        respostasUsuario = new HashMap<>();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(VisibilityTesteView.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents();
        configButtonGroups();
        numAmostras = visibilityTestMB.getQuantGlyphsBase() * (visibilityTestMB.getGlyphLayers2draw().length - 1);
        
        changeConfigs();
        configRadioButtonsActionCommand();
        
        this.painelEsquerda.setVisibilityTestMB(visibilityTestMB);
        this.painelEsquerda.setAreaCallback(new PainelDeTeste.AreaCallback() {
            @Override
            public void areaUpdated(HashMap<String, Integer> areas) {
                VisibilityTesteView.this.areas = areas;
            }
        });

//        updateOutput();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        texturaButtonGroup = new javax.swing.ButtonGroup();
        corButtonGroup = new javax.swing.ButtonGroup();
        formaButtonGroup = new javax.swing.ButtonGroup();
        textoButtonGroup = new javax.swing.ButtonGroup();
        posicaoButtonGroup = new javax.swing.ButtonGroup();
        orientacaoButtonGroup = new javax.swing.ButtonGroup();
        profileButtonGroup = new javax.swing.ButtonGroup();
        fundoPainel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        pergunta2_TextPane = new javax.swing.JTextPane();
        panelOpcoesRadios = new javax.swing.JPanel();
        texturasPanel = new javax.swing.JPanel();
        textura3RadioButton = new javax.swing.JRadioButton();
        textura5Label = new javax.swing.JLabel();
        textura1Label = new javax.swing.JLabel();
        textura2RadioButton = new javax.swing.JRadioButton();
        textura3Label = new javax.swing.JLabel();
        textura4Label = new javax.swing.JLabel();
        textura5RadioButton = new javax.swing.JRadioButton();
        textura2Label = new javax.swing.JLabel();
        textura4RadioButton = new javax.swing.JRadioButton();
        textura1RadioButton = new javax.swing.JRadioButton();
        textura0RadioButton = new javax.swing.JRadioButton();
        coresPanel = new javax.swing.JPanel();
        cor1RadioButton = new javax.swing.JRadioButton();
        cor2RadioButton = new javax.swing.JRadioButton();
        cor3RadioButton = new javax.swing.JRadioButton();
        cor4RadioButton = new javax.swing.JRadioButton();
        cor5RadioButton = new javax.swing.JRadioButton();
        cor3Label = new javax.swing.JLabel();
        cor4Label = new javax.swing.JLabel();
        cor5Label = new javax.swing.JLabel();
        cor1Label = new javax.swing.JLabel();
        cor2Label = new javax.swing.JLabel();
        cor0RadioButton = new javax.swing.JRadioButton();
        orientationPanel = new javax.swing.JPanel();
        orientation1RadioButton = new javax.swing.JRadioButton();
        orientation2RadioButton = new javax.swing.JRadioButton();
        orientation5RadioButton = new javax.swing.JRadioButton();
        orientation4RadioButton = new javax.swing.JRadioButton();
        orientation1Label = new javax.swing.JLabel();
        orientation5Label = new javax.swing.JLabel();
        orientation2Label = new javax.swing.JLabel();
        orientation3RadioButton = new javax.swing.JRadioButton();
        orientation4Label = new javax.swing.JLabel();
        orientation3Label = new javax.swing.JLabel();
        orientation0RadioButton = new javax.swing.JRadioButton();
        textPanel = new javax.swing.JPanel();
        texto3Label = new javax.swing.JLabel();
        text2RadioButton = new javax.swing.JRadioButton();
        texto5Label = new javax.swing.JLabel();
        text3RadioButton = new javax.swing.JRadioButton();
        text5RadioButton = new javax.swing.JRadioButton();
        text4RadioButton = new javax.swing.JRadioButton();
        texto4Label = new javax.swing.JLabel();
        texto2Label = new javax.swing.JLabel();
        text1RadioButton = new javax.swing.JRadioButton();
        texto1Label = new javax.swing.JLabel();
        text0RadioButton = new javax.swing.JRadioButton();
        shapePanel = new javax.swing.JPanel();
        forma5Label = new javax.swing.JLabel();
        forma1Label = new javax.swing.JLabel();
        forma2RadioButton = new javax.swing.JRadioButton();
        forma3Label = new javax.swing.JLabel();
        forma4Label = new javax.swing.JLabel();
        forma4RadioButton = new javax.swing.JRadioButton();
        forma2Label = new javax.swing.JLabel();
        forma3RadioButton = new javax.swing.JRadioButton();
        forma1RadioButton = new javax.swing.JRadioButton();
        forma5RadioButton = new javax.swing.JRadioButton();
        forma0RadioButton = new javax.swing.JRadioButton();
        positionPanel = new javax.swing.JPanel();
        position4RadioButton = new javax.swing.JRadioButton();
        position3RadioButton = new javax.swing.JRadioButton();
        position2RadioButton = new javax.swing.JRadioButton();
        posicao1Label = new javax.swing.JLabel();
        posicao5Label = new javax.swing.JLabel();
        posicao4Label = new javax.swing.JLabel();
        posicao3Label = new javax.swing.JLabel();
        posicao2Label = new javax.swing.JLabel();
        position5RadioButton = new javax.swing.JRadioButton();
        position1RadioButton = new javax.swing.JRadioButton();
        position0RadioButton = new javax.swing.JRadioButton();
        profileGlyphPanel = new javax.swing.JPanel();
        profileGlyphLabel3 = new javax.swing.JLabel();
        profileGlyphLabel1 = new javax.swing.JLabel();
        profileGlyphLabel2 = new javax.swing.JLabel();
        profileGlyphLabel5 = new javax.swing.JLabel();
        profileGlyphLabel4 = new javax.swing.JLabel();
        profile1RadioButton = new javax.swing.JRadioButton();
        profile2RadioButton = new javax.swing.JRadioButton();
        profile3RadioButton = new javax.swing.JRadioButton();
        profile4RadioButton = new javax.swing.JRadioButton();
        profile5RadioButton = new javax.swing.JRadioButton();
        profile0RadioButton = new javax.swing.JRadioButton();
        btnConfirm = new javax.swing.JButton();
        contadorLabel = new javax.swing.JLabel();
        painelEsquerda = new PainelDeTeste();
        glyphsLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Treemap Glyphs");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        fundoPainel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        pergunta2_TextPane.setEditable(false);
        pergunta2_TextPane.setBorder(null);
        pergunta2_TextPane.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        pergunta2_TextPane.setText("Quais dos valores das variáveis visuais você pode identificar claramente?");
        pergunta2_TextPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pergunta2_TextPane.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        pergunta2_TextPane.setEnabled(false);
        jScrollPane3.setViewportView(pergunta2_TextPane);

        texturasPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        texturasPanel.setPreferredSize(new java.awt.Dimension(75, 55));

        textura5Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/textura/textura5.PNG"))); // NOI18N
        textura5Label.setText("jLabel1");
        textura5Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textura5LabelMouseClicked(evt);
            }
        });

        textura1Label.setBackground(new java.awt.Color(255, 255, 255));
        textura1Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/textura/textura1.PNG"))); // NOI18N
        textura1Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textura1LabelMouseClicked(evt);
            }
        });

        textura3Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/textura/textura3.PNG"))); // NOI18N
        textura3Label.setText("jLabel1");
        textura3Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textura3LabelMouseClicked(evt);
            }
        });

        textura4Label.setBackground(new java.awt.Color(255, 255, 255));
        textura4Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/textura/textura4.PNG"))); // NOI18N
        textura4Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textura4LabelMouseClicked(evt);
            }
        });

        textura2Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/textura/textura2.PNG"))); // NOI18N
        textura2Label.setText("jLabel1");
        textura2Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textura2LabelMouseClicked(evt);
            }
        });

        textura0RadioButton.setText("Não apresenta");

        javax.swing.GroupLayout texturasPanelLayout = new javax.swing.GroupLayout(texturasPanel);
        texturasPanel.setLayout(texturasPanelLayout);
        texturasPanelLayout.setHorizontalGroup(
            texturasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(texturasPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(textura0RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(textura1RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textura1Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67)
                .addComponent(textura2RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textura2Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(textura3RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textura3Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(textura4RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textura4Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(textura5RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textura5Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
        );
        texturasPanelLayout.setVerticalGroup(
            texturasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(texturasPanelLayout.createSequentialGroup()
                .addGroup(texturasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(texturasPanelLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(textura2RadioButton))
                    .addGroup(texturasPanelLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(textura5RadioButton))
                    .addGroup(texturasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(textura5Label, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textura3Label, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textura2Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textura1Label, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, texturasPanelLayout.createSequentialGroup()
                            .addGap(16, 16, 16)
                            .addComponent(textura4RadioButton))
                        .addComponent(textura4Label, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, texturasPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(textura3RadioButton)
                        .addGap(15, 15, 15))
                    .addGroup(texturasPanelLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(texturasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textura0RadioButton)
                            .addComponent(textura1RadioButton))))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        coresPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        cor3Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/cor/cor3.PNG"))); // NOI18N
        cor3Label.setText("jLabel1");
        cor3Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cor3LabelMouseClicked(evt);
            }
        });

        cor4Label.setBackground(new java.awt.Color(255, 255, 255));
        cor4Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/cor/cor4.PNG"))); // NOI18N
        cor4Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cor4LabelMouseClicked(evt);
            }
        });

        cor5Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/cor/cor5.PNG"))); // NOI18N
        cor5Label.setText("jLabel1");
        cor5Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cor5LabelMouseClicked(evt);
            }
        });

        cor1Label.setBackground(new java.awt.Color(255, 255, 255));
        cor1Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/cor/cor1.PNG"))); // NOI18N
        cor1Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cor1LabelMouseClicked(evt);
            }
        });

        cor2Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/cor/cor2.PNG"))); // NOI18N
        cor2Label.setText("jLabel1");
        cor2Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cor2LabelMouseClicked(evt);
            }
        });

        cor0RadioButton.setText("Não apresenta");

        javax.swing.GroupLayout coresPanelLayout = new javax.swing.GroupLayout(coresPanel);
        coresPanel.setLayout(coresPanelLayout);
        coresPanelLayout.setHorizontalGroup(
            coresPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(coresPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cor0RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cor1RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor1Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(cor2RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor2Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(cor3RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor3Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(cor4RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor4Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(cor5RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cor5Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        coresPanelLayout.setVerticalGroup(
            coresPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(coresPanelLayout.createSequentialGroup()
                .addComponent(cor5Label)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(cor3Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cor4Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cor1Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cor2Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(coresPanelLayout.createSequentialGroup()
                .addGroup(coresPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(coresPanelLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(cor5RadioButton))
                    .addGroup(coresPanelLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(cor3RadioButton))
                    .addGroup(coresPanelLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(cor4RadioButton))
                    .addGroup(coresPanelLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(cor2RadioButton))
                    .addGroup(coresPanelLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(coresPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cor0RadioButton)
                            .addComponent(cor1RadioButton))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        orientationPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        orientation1Label.setBackground(new java.awt.Color(255, 255, 255));
        orientation1Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/orientacao/orientacao1.PNG"))); // NOI18N
        orientation1Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                orientation1LabelMouseClicked(evt);
            }
        });

        orientation5Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/orientacao/orientacao5.PNG"))); // NOI18N
        orientation5Label.setText("jLabel1");
        orientation5Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                orientation5LabelMouseClicked(evt);
            }
        });

        orientation2Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/orientacao/orientacao2.PNG"))); // NOI18N
        orientation2Label.setText("jLabel1");
        orientation2Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                orientation2LabelMouseClicked(evt);
            }
        });

        orientation4Label.setBackground(new java.awt.Color(255, 255, 255));
        orientation4Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/orientacao/orientacao4.PNG"))); // NOI18N
        orientation4Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                orientation4LabelMouseClicked(evt);
            }
        });

        orientation3Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/orientacao/orientacao3.PNG"))); // NOI18N
        orientation3Label.setText("jLabel1");
        orientation3Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                orientation3LabelMouseClicked(evt);
            }
        });

        orientation0RadioButton.setText("Não apresenta");

        javax.swing.GroupLayout orientationPanelLayout = new javax.swing.GroupLayout(orientationPanel);
        orientationPanel.setLayout(orientationPanelLayout);
        orientationPanelLayout.setHorizontalGroup(
            orientationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(orientationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(orientation0RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(orientation1RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(orientation1Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addComponent(orientation2RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(orientation2Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(orientation3RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(orientation3Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(orientation4RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(orientation4Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(orientation5RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(orientation5Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        orientationPanelLayout.setVerticalGroup(
            orientationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(orientation5Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(orientation3Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(orientation4Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(orientation1Label, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(orientation2Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(orientationPanelLayout.createSequentialGroup()
                .addGroup(orientationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(orientationPanelLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(orientation5RadioButton))
                    .addGroup(orientationPanelLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(orientation3RadioButton))
                    .addGroup(orientationPanelLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(orientation4RadioButton))
                    .addGroup(orientationPanelLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(orientation2RadioButton))
                    .addGroup(orientationPanelLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(orientationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(orientation0RadioButton)
                            .addComponent(orientation1RadioButton))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        textPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        texto3Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/texto/J.PNG"))); // NOI18N
        texto3Label.setText("jLabel1");
        texto3Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                texto3LabelMouseClicked(evt);
            }
        });

        texto5Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/texto/M.PNG"))); // NOI18N
        texto5Label.setText("jLabel1");
        texto5Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                texto5LabelMouseClicked(evt);
            }
        });

        texto4Label.setBackground(new java.awt.Color(255, 255, 255));
        texto4Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/texto/K.PNG"))); // NOI18N
        texto4Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                texto4LabelMouseClicked(evt);
            }
        });

        texto2Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/texto/C.PNG"))); // NOI18N
        texto2Label.setText("jLabel1");
        texto2Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                texto2LabelMouseClicked(evt);
            }
        });

        texto1Label.setBackground(new java.awt.Color(255, 255, 255));
        texto1Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/texto/A.PNG"))); // NOI18N
        texto1Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                texto1LabelMouseClicked(evt);
            }
        });

        text0RadioButton.setText("Não apresenta");

        javax.swing.GroupLayout textPanelLayout = new javax.swing.GroupLayout(textPanel);
        textPanel.setLayout(textPanelLayout);
        textPanelLayout.setHorizontalGroup(
            textPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(textPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(text0RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(text1RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(texto1Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(text2RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(texto2Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(text3RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(texto3Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(text4RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(texto4Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addComponent(text5RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(texto5Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        textPanelLayout.setVerticalGroup(
            textPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(texto5Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(texto2Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(texto3Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(texto4Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(textPanelLayout.createSequentialGroup()
                .addGroup(textPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(textPanelLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(text5RadioButton))
                    .addComponent(texto1Label)
                    .addGroup(textPanelLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(text2RadioButton))
                    .addGroup(textPanelLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(text3RadioButton))
                    .addGroup(textPanelLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(text4RadioButton))
                    .addGroup(textPanelLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(textPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(text0RadioButton)
                            .addComponent(text1RadioButton))))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        shapePanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        forma5Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/forma/forma5.PNG"))); // NOI18N
        forma5Label.setText("jLabel1");
        forma5Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                forma5LabelMouseClicked(evt);
            }
        });

        forma1Label.setBackground(new java.awt.Color(255, 255, 255));
        forma1Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/forma/forma1.PNG"))); // NOI18N
        forma1Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                forma1LabelMouseClicked(evt);
            }
        });

        forma3Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/forma/forma3.PNG"))); // NOI18N
        forma3Label.setText("jLabel1");
        forma3Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                forma3LabelMouseClicked(evt);
            }
        });

        forma4Label.setBackground(new java.awt.Color(255, 255, 255));
        forma4Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/forma/forma4.PNG"))); // NOI18N
        forma4Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                forma4LabelMouseClicked(evt);
            }
        });

        forma2Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/forma/forma2.PNG"))); // NOI18N
        forma2Label.setText("jLabel1");
        forma2Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                forma2LabelMouseClicked(evt);
            }
        });

        forma0RadioButton.setText("Não apresenta");

        javax.swing.GroupLayout shapePanelLayout = new javax.swing.GroupLayout(shapePanel);
        shapePanel.setLayout(shapePanelLayout);
        shapePanelLayout.setHorizontalGroup(
            shapePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(shapePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(forma0RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(forma1RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(forma1Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(forma2RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(forma2Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(forma3RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(forma3Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addComponent(forma4RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(forma4Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(forma5RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(forma5Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        shapePanelLayout.setVerticalGroup(
            shapePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(forma3Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(forma2Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(forma4Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(forma5Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(shapePanelLayout.createSequentialGroup()
                .addGroup(shapePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(forma1Label)
                    .addGroup(shapePanelLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(forma3RadioButton))
                    .addGroup(shapePanelLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(forma2RadioButton))
                    .addGroup(shapePanelLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(forma4RadioButton))
                    .addGroup(shapePanelLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(forma5RadioButton))
                    .addGroup(shapePanelLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(shapePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(forma0RadioButton)
                            .addComponent(forma1RadioButton))))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        positionPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        posicao1Label.setBackground(new java.awt.Color(255, 255, 255));
        posicao1Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/posicao/posicao1.PNG"))); // NOI18N
        posicao1Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                posicao1LabelMouseClicked(evt);
            }
        });

        posicao5Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/posicao/posicao5.PNG"))); // NOI18N
        posicao5Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                posicao5LabelMouseClicked(evt);
            }
        });

        posicao4Label.setBackground(new java.awt.Color(255, 255, 255));
        posicao4Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/posicao/posicao4.PNG"))); // NOI18N
        posicao4Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                posicao4LabelMouseClicked(evt);
            }
        });

        posicao3Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/posicao/posicao3.PNG"))); // NOI18N
        posicao3Label.setText("jLabel1");
        posicao3Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                posicao3LabelMouseClicked(evt);
            }
        });

        posicao2Label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/visibilityIcons/posicao/posicao2.PNG"))); // NOI18N
        posicao2Label.setText("jLabel1");
        posicao2Label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                posicao2LabelMouseClicked(evt);
            }
        });

        position0RadioButton.setText("Não apresenta");

        javax.swing.GroupLayout positionPanelLayout = new javax.swing.GroupLayout(positionPanel);
        positionPanel.setLayout(positionPanelLayout);
        positionPanelLayout.setHorizontalGroup(
            positionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(positionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(position0RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(position1RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(posicao1Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(position2RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(posicao2Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(position3RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(posicao3Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(position4RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(posicao4Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(position5RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(posicao5Label, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
        );
        positionPanelLayout.setVerticalGroup(
            positionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, positionPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(posicao1Label))
            .addComponent(posicao3Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(posicao2Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(posicao4Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(posicao5Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(positionPanelLayout.createSequentialGroup()
                .addGroup(positionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(positionPanelLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(position3RadioButton))
                    .addGroup(positionPanelLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(position2RadioButton))
                    .addGroup(positionPanelLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(position4RadioButton))
                    .addGroup(positionPanelLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(position5RadioButton))
                    .addGroup(positionPanelLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(positionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(position0RadioButton)
                            .addComponent(position1RadioButton))))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        profileGlyphPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        profileGlyphLabel3.setBackground(new java.awt.Color(255, 204, 204));
        profileGlyphLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        profileGlyphLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profileGlyphLabel3MouseClicked(evt);
            }
        });

        profileGlyphLabel1.setBackground(new java.awt.Color(255, 204, 204));
        profileGlyphLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        profileGlyphLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profileGlyphLabel1MouseClicked(evt);
            }
        });

        profileGlyphLabel2.setBackground(new java.awt.Color(255, 204, 204));
        profileGlyphLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        profileGlyphLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profileGlyphLabel2MouseClicked(evt);
            }
        });

        profileGlyphLabel5.setBackground(new java.awt.Color(255, 204, 204));
        profileGlyphLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        profileGlyphLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profileGlyphLabel5MouseClicked(evt);
            }
        });

        profileGlyphLabel4.setBackground(new java.awt.Color(255, 204, 204));
        profileGlyphLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        profileGlyphLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profileGlyphLabel4MouseClicked(evt);
            }
        });

        profile0RadioButton.setText("Não apresenta");

        javax.swing.GroupLayout profileGlyphPanelLayout = new javax.swing.GroupLayout(profileGlyphPanel);
        profileGlyphPanel.setLayout(profileGlyphPanelLayout);
        profileGlyphPanelLayout.setHorizontalGroup(
            profileGlyphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profileGlyphPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(profile0RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(profile1RadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(profileGlyphLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(profile2RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(profileGlyphLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(profile3RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(profileGlyphLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addComponent(profile4RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(profileGlyphLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(profile5RadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(profileGlyphLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        profileGlyphPanelLayout.setVerticalGroup(
            profileGlyphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(profileGlyphLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(profileGlyphLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(profileGlyphPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(profile4RadioButton))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profileGlyphPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(profile5RadioButton)
                .addGap(16, 16, 16))
            .addGroup(profileGlyphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(profileGlyphLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(profileGlyphLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(profileGlyphLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, profileGlyphPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(profileGlyphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(profile0RadioButton)
                        .addComponent(profile1RadioButton))
                    .addGap(16, 16, 16))
                .addGroup(profileGlyphPanelLayout.createSequentialGroup()
                    .addGap(17, 17, 17)
                    .addGroup(profileGlyphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(profile2RadioButton)
                        .addComponent(profile3RadioButton))))
        );

        javax.swing.GroupLayout panelOpcoesRadiosLayout = new javax.swing.GroupLayout(panelOpcoesRadios);
        panelOpcoesRadios.setLayout(panelOpcoesRadiosLayout);
        panelOpcoesRadiosLayout.setHorizontalGroup(
            panelOpcoesRadiosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(texturasPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
            .addComponent(coresPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(orientationPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(textPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(shapePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(positionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(profileGlyphPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelOpcoesRadiosLayout.setVerticalGroup(
            panelOpcoesRadiosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOpcoesRadiosLayout.createSequentialGroup()
                .addComponent(texturasPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(coresPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(orientationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(shapePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(positionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(profileGlyphPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btnConfirm.setText("Confirm");
        btnConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmActionPerformed(evt);
            }
        });

        contadorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        contadorLabel.setText("0 / 100");

        painelEsquerda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        glyphsLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        glyphsLabel.setText("Dado o Glifo:");

        javax.swing.GroupLayout painelEsquerdaLayout = new javax.swing.GroupLayout(painelEsquerda);
        painelEsquerda.setLayout(painelEsquerdaLayout);
        painelEsquerdaLayout.setHorizontalGroup(
            painelEsquerdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelEsquerdaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(glyphsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE))
        );
        painelEsquerdaLayout.setVerticalGroup(
            painelEsquerdaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelEsquerdaLayout.createSequentialGroup()
                .addComponent(glyphsLabel)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout fundoPainelLayout = new javax.swing.GroupLayout(fundoPainel);
        fundoPainel.setLayout(fundoPainelLayout);
        fundoPainelLayout.setHorizontalGroup(
            fundoPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fundoPainelLayout.createSequentialGroup()
                .addComponent(painelEsquerda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(fundoPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(panelOpcoesRadios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fundoPainelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(contadorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        fundoPainelLayout.setVerticalGroup(
            fundoPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fundoPainelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(fundoPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(fundoPainelLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelOpcoesRadios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(painelEsquerda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(fundoPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contadorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(fundoPainel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fundoPainel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private List<JLabel> labelsProfileGlyphGroup(){
        List<JLabel> lista = new ArrayList<>();
        lista.add(profileGlyphLabel1);
        lista.add(profileGlyphLabel2);
        lista.add(profileGlyphLabel3);
        lista.add(profileGlyphLabel4);
        lista.add(profileGlyphLabel5);
        return lista;
    }
    
    private void configButtonGroups() {
        texturaButtonGroup.add(textura0RadioButton);
        texturaButtonGroup.add(textura1RadioButton);
        texturaButtonGroup.add(textura2RadioButton);
        texturaButtonGroup.add(textura3RadioButton);
        texturaButtonGroup.add(textura4RadioButton);
        texturaButtonGroup.add(textura5RadioButton);

        corButtonGroup.add(cor0RadioButton);
        corButtonGroup.add(cor1RadioButton);
        corButtonGroup.add(cor2RadioButton);
        corButtonGroup.add(cor3RadioButton);
        corButtonGroup.add(cor4RadioButton);
        corButtonGroup.add(cor5RadioButton);

        formaButtonGroup.add(forma0RadioButton);
        formaButtonGroup.add(forma1RadioButton);
        formaButtonGroup.add(forma2RadioButton);
        formaButtonGroup.add(forma3RadioButton);
        formaButtonGroup.add(forma4RadioButton);
        formaButtonGroup.add(forma5RadioButton);

        textoButtonGroup.add(text0RadioButton);
        textoButtonGroup.add(text1RadioButton);
        textoButtonGroup.add(text2RadioButton);
        textoButtonGroup.add(text3RadioButton);
        textoButtonGroup.add(text4RadioButton);
        textoButtonGroup.add(text5RadioButton);

        posicaoButtonGroup.add(position0RadioButton);
        posicaoButtonGroup.add(position1RadioButton);
        posicaoButtonGroup.add(position2RadioButton);
        posicaoButtonGroup.add(position3RadioButton);
        posicaoButtonGroup.add(position4RadioButton);
        posicaoButtonGroup.add(position5RadioButton);

        orientacaoButtonGroup.add(orientation0RadioButton);
        orientacaoButtonGroup.add(orientation1RadioButton);
        orientacaoButtonGroup.add(orientation2RadioButton);
        orientacaoButtonGroup.add(orientation3RadioButton);
        orientacaoButtonGroup.add(orientation4RadioButton);
        orientacaoButtonGroup.add(orientation5RadioButton);

        profileButtonGroup.add(profile0RadioButton);
        profileButtonGroup.add(profile1RadioButton);
        profileButtonGroup.add(profile2RadioButton);
        profileButtonGroup.add(profile3RadioButton);
        profileButtonGroup.add(profile4RadioButton);
        profileButtonGroup.add(profile5RadioButton);
    }

    public void configRadioButtonsActionCommand() {
        textura0RadioButton.setActionCommand(Constantes.NAO_IDENTIFICOU_NAO_APRESENTA);
        textura1RadioButton.setActionCommand(CirculoTextura_2x2.class.getSimpleName());
        textura2RadioButton.setActionCommand(CirculoTextura_3x3_Branco.class.getSimpleName());
        textura3RadioButton.setActionCommand(CirculoTextura_4x4.class.getSimpleName());
        textura4RadioButton.setActionCommand(CirculoTextura_2x2_Branco.class.getSimpleName());
        textura5RadioButton.setActionCommand(CirculoTextura_3x3.class.getSimpleName());

        cor0RadioButton.setActionCommand(Constantes.NAO_IDENTIFICOU_NAO_APRESENTA);
        cor1RadioButton.setActionCommand(Constantes.getColorHueGlyphs()[0]);
        cor2RadioButton.setActionCommand(Constantes.getColorHueGlyphs()[1]);
        cor3RadioButton.setActionCommand(Constantes.getColorHueGlyphs()[2]);
        cor4RadioButton.setActionCommand(Constantes.getColorHueGlyphs()[3]);
        cor5RadioButton.setActionCommand(Constantes.getColorHueGlyphs()[4]);

        forma0RadioButton.setActionCommand(Constantes.NAO_IDENTIFICOU_NAO_APRESENTA);
        forma1RadioButton.setActionCommand(Circulo.class.getSimpleName());
        forma2RadioButton.setActionCommand(Serrilhado.class.getSimpleName());
        forma3RadioButton.setActionCommand(Cruz.class.getSimpleName());
        forma4RadioButton.setActionCommand(Estrela.class.getSimpleName());
        forma5RadioButton.setActionCommand(Quadrado.class.getSimpleName());

        text0RadioButton.setActionCommand(Constantes.NAO_IDENTIFICOU_NAO_APRESENTA);
        text1RadioButton.setActionCommand(Constantes.LETRAS_ALFABETO[0]);
        text2RadioButton.setActionCommand(Constantes.LETRAS_ALFABETO[1]);
        text3RadioButton.setActionCommand(Constantes.LETRAS_ALFABETO[2]);
        text4RadioButton.setActionCommand(Constantes.LETRAS_ALFABETO[3]);
        text5RadioButton.setActionCommand(Constantes.LETRAS_ALFABETO[4]);

        position0RadioButton.setActionCommand(Constantes.NAO_IDENTIFICOU_NAO_APRESENTA);
        position1RadioButton.setActionCommand(Constantes.POSICOES.ESQ_INF.name());
        position2RadioButton.setActionCommand(Constantes.POSICOES.DIR_SUP.name());
        position3RadioButton.setActionCommand(Constantes.POSICOES.DIR_INF.name());
        position4RadioButton.setActionCommand(Constantes.POSICOES.ESQ_SUP.name());
        position5RadioButton.setActionCommand(Constantes.POSICOES.CENTRO.name());

        orientation0RadioButton.setActionCommand(Constantes.NAO_IDENTIFICOU_NAO_APRESENTA);
        orientation1RadioButton.setActionCommand(Arrow90Cima.class.getSimpleName());
        orientation2RadioButton.setActionCommand(Arrow180Esq.class.getSimpleName());
        orientation3RadioButton.setActionCommand(Arrow45CanDir.class.getSimpleName());
        orientation4RadioButton.setActionCommand(Arrow0Dir.class.getSimpleName());
        orientation5RadioButton.setActionCommand(Arrow135CanEsq.class.getSimpleName());

        profile0RadioButton.setActionCommand(Constantes.NAO_IDENTIFICOU_NAO_APRESENTA);
        profile1RadioButton.setActionCommand("1");
        profile2RadioButton.setActionCommand("2");
        profile3RadioButton.setActionCommand("3");
        profile4RadioButton.setActionCommand("4");
        profile5RadioButton.setActionCommand("5");
    }

    public void resetPainelsRadioButtos() {
        texturaButtonGroup.clearSelection();
        corButtonGroup.clearSelection();
        formaButtonGroup.clearSelection();
        textoButtonGroup.clearSelection();
        posicaoButtonGroup.clearSelection();
        orientacaoButtonGroup.clearSelection();
        profileButtonGroup.clearSelection();
    }

    public boolean isSelectedRadioButton(ButtonGroup buttonGroup) {
        boolean selected = false;
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                selected = true;
                break;
            }
        }

        return selected;
    }

    public String[] parseListString2Array(ListModel<String> lista) {
        String[] convertida = new String[lista.getSize()];
        for (int i = 0; i < convertida.length; i++) {
            convertida[i] = lista.getElementAt(i);
        }
        return convertida;
    }

    private boolean validateRadioButtons() {
        boolean valid = true;
        if (!isSelectedRadioButton(texturaButtonGroup)) {
            JOptionPane.showMessageDialog(null, "Please, choose one of the Texture values.", "Warning !!!", JOptionPane.WARNING_MESSAGE);
            valid = false;
        } else if (!isSelectedRadioButton(corButtonGroup)) {
            JOptionPane.showMessageDialog(null, "Please, choose one of the Color-hue values.", "Warning !!!", JOptionPane.WARNING_MESSAGE);
            valid = false;
        } else if (!isSelectedRadioButton(formaButtonGroup)) {
            JOptionPane.showMessageDialog(null, "Please, choose one of the Shape values.", "Warning !!!", JOptionPane.WARNING_MESSAGE);
            valid = false;
        } else if (!isSelectedRadioButton(textoButtonGroup)) {
            JOptionPane.showMessageDialog(null, "Please, choose one of the Text values.", "Warning !!!", JOptionPane.WARNING_MESSAGE);
            valid = false;
        } else if (!isSelectedRadioButton(posicaoButtonGroup)) {
            JOptionPane.showMessageDialog(null, "Please, choose one of the Position values.", "Warning !!!", JOptionPane.WARNING_MESSAGE);
            valid = false;
        } else if (!isSelectedRadioButton(orientacaoButtonGroup)) {
            JOptionPane.showMessageDialog(null, "Please, choose one of the Orientation values.", "Warning !!!", JOptionPane.WARNING_MESSAGE);
            valid = false;
        }
        return valid;
    }

    /**
     * @param familiaGlyph
     * @return vetor com a seguinte ordem: [0] textureAreaVisivel; [1]
     * hueAreaVisivel; [2] shapeAreaVisivel; [3] textAreaVisivel; [4]
     * orientationAreaVisivel; [5] positionAreaVisivel;
     */
    public String calcularAreaVisivel(List<Glyph> familiaGlyph) {
        double textureAreaVisivel = 0;
        double hueAreaVisivel = 0;
        double shapeAreaVisivel = 0;
        double textAreaVisivel = 0;
        double orientationAreaVisivel = 0;
        double positionAreaVisivel = 0;

        DecimalFormat df = new DecimalFormat("#.###");

        for (Glyph varVisual : familiaGlyph) {
            if (!(varVisual instanceof GlyphConcrete || varVisual instanceof ProfileGlyph)) {
                if (varVisual instanceof Texture) {
                    textureAreaVisivel = Double.valueOf(df.format(areas.get("texture") - (areas.get("texture") * 0.7)).replace(",", "."));
                } else if (varVisual instanceof ColorHue) {
                    hueAreaVisivel = Double.valueOf(df.format(areas.get("colorhue") - (areas.get("colorhue") * 0.7)).replace(",", "."));
                } else if (varVisual instanceof GeometricShape) {
                    shapeAreaVisivel = Double.valueOf(df.format(areas.get("geometricshape") - (areas.get("geometricshape") * 0.7)).replace(",", "."));
                } else if (varVisual instanceof Text) {
                    textAreaVisivel = Double.valueOf(df.format(areas.get("text") - (areas.get("text") * 0.7)).replace(",", "."));
                } else if (varVisual instanceof Orientation) {
                    orientationAreaVisivel = Double.valueOf(df.format(areas.get("orientation") - (areas.get("orientation") * 0.7)).replace(",", "."));
                } else if (varVisual instanceof Position) {
                    positionAreaVisivel = Double.valueOf(df.format(areas.get("position") - (areas.get("position") * 0.7)).replace(",", "."));
                }
            }
        }
        return textureAreaVisivel + "," + hueAreaVisivel + "," + shapeAreaVisivel + ","
                + textAreaVisivel + "," + orientationAreaVisivel + "," + positionAreaVisivel;
    }

    public void setData(String userScore) {
        float aspect = configs.get("height") > configs.get("width")
                ? (configs.get("width") * 1.f) / configs.get("height")
                : (configs.get("height") * 1.f) / configs.get("width");

        String[] areaVisivelVarVisuais = calcularAreaVisivel(visibilityTestMB.getFamily2Draw()).replace("[", "").replace("]", "").split(",");

        data.append("\n").
                append(configs.get("texture") >= 0 ? 1 : 0).append(",").
                append(configs.get("colorhue") >= 0 ? 1 : 0).append(",").
                append(configs.get("geometricshape") >= 0 ? 1 : 0).append(",").
                append(configs.get("text") >= 0 ? 1 : 0).append(",").
                append(configs.get("position") >= 0 ? 1 : 0).append(",").
                append(configs.get("orientation") >= 0 ? 1 : 0).append(",").
                append(configs.get("profileglyph") > 0 ? 1 : 0).append(",").
                append(configs.get("height")).append(",").
                append(configs.get("width")).append(",").
                append(configs.get("width") * configs.get("height")).append(",").
                append(aspect).append(",").
                append(configs.get("coritem") >= 0 ? 1 : 0).append(",").
                append(areas.get("texture")).append(",").
                append(areas.get("colorhue")).append(",").
                append(areas.get("geometricshape")).append(",").
                append(areas.get("text")).append(",").
                append(areas.get("position")).append(",").
                append(areas.get("orientation")).append(",").
                append(areas.get("profileglyph")).append(",").
                append(visibilityTestMB.getFamily2Draw().toString().replace(",", ">").replace("[", "").replace("]", "")).append(",").
                append(texturaButtonGroup.getSelection().getActionCommand())    .append(","). //"TexturaValor"
                append(corButtonGroup.getSelection().getActionCommand())        .append(","). //"CorValor"
                append(formaButtonGroup.getSelection().getActionCommand())      .append(","). //"FormaValor"
                append(textoButtonGroup.getSelection().getActionCommand())      .append(","). //"TextoValor"
                append(posicaoButtonGroup.getSelection().getActionCommand())    .append(","). //"PosicaoValor"
                append(orientacaoButtonGroup.getSelection().getActionCommand()) .append(",").//"OrientacaoValor"
                append(visibilityTestMB.isAchouProfileCorreto() == true ? "1" : "0") .append(",").//"achouProfileCorreto?"
                append(areaVisivelVarVisuais[0]).append(","). //area visivel Textura
                append(areaVisivelVarVisuais[1]).append(","). //area visivel cor
                append(areaVisivelVarVisuais[2]).append(","). //area visivel forma
                append(areaVisivelVarVisuais[3]).append(","). //area visivel Texto
                append(areaVisivelVarVisuais[4]).append(","). //area visivel Posicao
                append(areaVisivelVarVisuais[5]).append(","). //area visivel Orientacao
                append(visibilityTestMB.getGabarito().get("Texture"))       .append(",").   //gabarito Textura
                append(visibilityTestMB.getGabarito().get("ColorHue"))      .append(",").   //gabarito cor
                append(visibilityTestMB.getGabarito().get("GeometricShape")).append(",").   //gabarito forma
                append(visibilityTestMB.getGabarito().get("Text"))          .append(",").   //gabarito Texto
                append(visibilityTestMB.getGabarito().get("Position"))      .append(",").   //gabarito Posicao
                append(visibilityTestMB.getGabarito().get("Orientation"))   .append(",").   //gabarito Orientacao
                append(userScore)//user score
                ;
    }

    public void saveDataInFile() {
        PrintWriter writer = null;
        Calendar calendar = new GregorianCalendar(Locale.getDefault());
        Date trialTime = new Date();
        calendar.setTime(trialTime);
        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DATE);
        int hora = calendar.get(Calendar.HOUR);
        int min = calendar.get(Calendar.MINUTE);
        try {
            nomeArquivo = "result_" + System.getProperty("user.name")
                    + "_" + ano + mes + dia + "_" + hora + "_" + min + ".csv";
            File f = new File("testVisibility" + File.separator);
            if (!f.exists()) {
                f.mkdir();
            } 
            if(f.exists()){
                File file = new File("testVisibility" + File.separator + nomeArquivo);
                writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
                writer.print(data);
                writer.flush();
                writer.close();
                JOptionPane.showMessageDialog(null, "Thank you for participating.", "Thanks!", JOptionPane.INFORMATION_MESSAGE);
            }
            this.dispose();
        } catch (IOException ex) {
            Logger.getLogger(VisibilityTesteView.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            writer.close();
        }
    }

    public void changeConfigs() {
        resetPainelsRadioButtos();
        
        visibilityTestMB.setInputConfigs(configs);
        visibilityTestMB.family2Draw();
        visibilityTestMB.createProfileGlyphs(labelsProfileGlyphGroup());
        visibilityTestMB.configurarGabarito();
        painelEsquerda.repaint();
        
        contadorLabel.setText(visibilityTestMB.getContTarefasRealizadas() + " / " + numAmostras);
    }
    
    private HashMap<String, String> atualizarRespostasUsuario(){
        respostasUsuario.put("Texture"       , texturaButtonGroup.getSelection().getActionCommand()     );
        respostasUsuario.put("ColorHue"      , corButtonGroup.getSelection().getActionCommand()         );
        respostasUsuario.put("GeometricShape", formaButtonGroup.getSelection().getActionCommand()       );
        respostasUsuario.put("Text"          , textoButtonGroup.getSelection().getActionCommand()       );
        respostasUsuario.put("Position"      , posicaoButtonGroup.getSelection().getActionCommand()     );
        respostasUsuario.put("Orientation"   , orientacaoButtonGroup.getSelection().getActionCommand()  );
        respostasUsuario.put("ProfileGlyph"  , profileButtonGroup.getSelection().getActionCommand()     );
        return respostasUsuario;
    }

    private void btnConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmActionPerformed
        if (validateRadioButtons()) {
            setData(visibilityTestMB.calculateUserScore(atualizarRespostasUsuario()));
            if (visibilityTestMB.getContTarefasRealizadas() >= numAmostras) {//se true encerra o teste e salva os dados em arquivo
                saveDataInFile();
            } else {
                changeConfigs();
            }
        }
    }//GEN-LAST:event_btnConfirmActionPerformed

    private void textura1LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textura1LabelMouseClicked
        textura1RadioButton.setSelected(true);
    }//GEN-LAST:event_textura1LabelMouseClicked

    private void textura2LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textura2LabelMouseClicked
        textura2RadioButton.setSelected(true);
    }//GEN-LAST:event_textura2LabelMouseClicked

    private void textura3LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textura3LabelMouseClicked
        textura3RadioButton.setSelected(true);
    }//GEN-LAST:event_textura3LabelMouseClicked

    private void textura4LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textura4LabelMouseClicked
        textura4RadioButton.setSelected(true);
    }//GEN-LAST:event_textura4LabelMouseClicked

    private void textura5LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textura5LabelMouseClicked
        textura5RadioButton.setSelected(true);
    }//GEN-LAST:event_textura5LabelMouseClicked

    private void cor1LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cor1LabelMouseClicked
        cor1RadioButton.setSelected(true);
    }//GEN-LAST:event_cor1LabelMouseClicked

    private void cor2LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cor2LabelMouseClicked
        cor2RadioButton.setSelected(true);
    }//GEN-LAST:event_cor2LabelMouseClicked

    private void cor3LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cor3LabelMouseClicked
        cor3RadioButton.setSelected(true);
    }//GEN-LAST:event_cor3LabelMouseClicked

    private void cor4LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cor4LabelMouseClicked
        cor4RadioButton.setSelected(true);
    }//GEN-LAST:event_cor4LabelMouseClicked

    private void cor5LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cor5LabelMouseClicked
        cor5RadioButton.setSelected(true);
    }//GEN-LAST:event_cor5LabelMouseClicked

    private void forma1LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forma1LabelMouseClicked
        forma1RadioButton.setSelected(true);
    }//GEN-LAST:event_forma1LabelMouseClicked

    private void forma2LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forma2LabelMouseClicked
        forma2RadioButton.setSelected(true);
    }//GEN-LAST:event_forma2LabelMouseClicked

    private void forma3LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forma3LabelMouseClicked
        forma3RadioButton.setSelected(true);
    }//GEN-LAST:event_forma3LabelMouseClicked

    private void forma4LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forma4LabelMouseClicked
        forma4RadioButton.setSelected(true);
    }//GEN-LAST:event_forma4LabelMouseClicked

    private void forma5LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forma5LabelMouseClicked
        forma5RadioButton.setSelected(true);
    }//GEN-LAST:event_forma5LabelMouseClicked

    private void texto1LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_texto1LabelMouseClicked
        text1RadioButton.setSelected(true);
    }//GEN-LAST:event_texto1LabelMouseClicked

    private void texto2LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_texto2LabelMouseClicked
        text2RadioButton.setSelected(true);
    }//GEN-LAST:event_texto2LabelMouseClicked

    private void texto3LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_texto3LabelMouseClicked
        text3RadioButton.setSelected(true);
    }//GEN-LAST:event_texto3LabelMouseClicked

    private void texto4LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_texto4LabelMouseClicked
        text4RadioButton.setSelected(true);
    }//GEN-LAST:event_texto4LabelMouseClicked

    private void texto5LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_texto5LabelMouseClicked
        text5RadioButton.setSelected(true);
    }//GEN-LAST:event_texto5LabelMouseClicked

    private void posicao1LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_posicao1LabelMouseClicked
        position1RadioButton.setSelected(true);
    }//GEN-LAST:event_posicao1LabelMouseClicked

    private void posicao2LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_posicao2LabelMouseClicked
        position2RadioButton.setSelected(true);
    }//GEN-LAST:event_posicao2LabelMouseClicked

    private void posicao3LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_posicao3LabelMouseClicked
        position3RadioButton.setSelected(true);
    }//GEN-LAST:event_posicao3LabelMouseClicked

    private void posicao4LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_posicao4LabelMouseClicked
        position4RadioButton.setSelected(true);
    }//GEN-LAST:event_posicao4LabelMouseClicked

    private void posicao5LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_posicao5LabelMouseClicked
        position5RadioButton.setSelected(true);
    }//GEN-LAST:event_posicao5LabelMouseClicked

    private void orientation1LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orientation1LabelMouseClicked
        orientation1RadioButton.setSelected(true);
    }//GEN-LAST:event_orientation1LabelMouseClicked

    private void orientation2LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orientation2LabelMouseClicked
        orientation2RadioButton.setSelected(true);
    }//GEN-LAST:event_orientation2LabelMouseClicked

    private void orientation3LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orientation3LabelMouseClicked
        orientation3RadioButton.setSelected(true);
    }//GEN-LAST:event_orientation3LabelMouseClicked

    private void orientation4LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orientation4LabelMouseClicked
        orientation4RadioButton.setSelected(true);
    }//GEN-LAST:event_orientation4LabelMouseClicked

    private void orientation5LabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orientation5LabelMouseClicked
        orientation5RadioButton.setSelected(true);
    }//GEN-LAST:event_orientation5LabelMouseClicked

    private void profileGlyphLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileGlyphLabel1MouseClicked
        profile1RadioButton.setSelected(true);
    }//GEN-LAST:event_profileGlyphLabel1MouseClicked

    private void profileGlyphLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileGlyphLabel2MouseClicked
        profile2RadioButton.setSelected(true);
    }//GEN-LAST:event_profileGlyphLabel2MouseClicked

    private void profileGlyphLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileGlyphLabel3MouseClicked
        profile3RadioButton.setSelected(true);
    }//GEN-LAST:event_profileGlyphLabel3MouseClicked

    private void profileGlyphLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileGlyphLabel4MouseClicked
        profile4RadioButton.setSelected(true);
    }//GEN-LAST:event_profileGlyphLabel4MouseClicked

    private void profileGlyphLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileGlyphLabel5MouseClicked
        profile5RadioButton.setSelected(true);
    }//GEN-LAST:event_profileGlyphLabel5MouseClicked

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
    private javax.swing.JLabel contadorLabel;
    private javax.swing.JRadioButton cor0RadioButton;
    private javax.swing.JLabel cor1Label;
    private javax.swing.JRadioButton cor1RadioButton;
    private javax.swing.JLabel cor2Label;
    private javax.swing.JRadioButton cor2RadioButton;
    private javax.swing.JLabel cor3Label;
    private javax.swing.JRadioButton cor3RadioButton;
    private javax.swing.JLabel cor4Label;
    private javax.swing.JRadioButton cor4RadioButton;
    private javax.swing.JLabel cor5Label;
    private javax.swing.JRadioButton cor5RadioButton;
    private javax.swing.ButtonGroup corButtonGroup;
    private javax.swing.JPanel coresPanel;
    private javax.swing.JRadioButton forma0RadioButton;
    private javax.swing.JLabel forma1Label;
    private javax.swing.JRadioButton forma1RadioButton;
    private javax.swing.JLabel forma2Label;
    private javax.swing.JRadioButton forma2RadioButton;
    private javax.swing.JLabel forma3Label;
    private javax.swing.JRadioButton forma3RadioButton;
    private javax.swing.JLabel forma4Label;
    private javax.swing.JRadioButton forma4RadioButton;
    private javax.swing.JLabel forma5Label;
    private javax.swing.JRadioButton forma5RadioButton;
    private javax.swing.ButtonGroup formaButtonGroup;
    private javax.swing.JPanel fundoPainel;
    private javax.swing.JLabel glyphsLabel;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.ButtonGroup orientacaoButtonGroup;
    private javax.swing.JRadioButton orientation0RadioButton;
    private javax.swing.JLabel orientation1Label;
    private javax.swing.JRadioButton orientation1RadioButton;
    private javax.swing.JLabel orientation2Label;
    private javax.swing.JRadioButton orientation2RadioButton;
    private javax.swing.JLabel orientation3Label;
    private javax.swing.JRadioButton orientation3RadioButton;
    private javax.swing.JLabel orientation4Label;
    private javax.swing.JRadioButton orientation4RadioButton;
    private javax.swing.JLabel orientation5Label;
    private javax.swing.JRadioButton orientation5RadioButton;
    private javax.swing.JPanel orientationPanel;
    private javax.swing.JPanel painelEsquerda;
    private javax.swing.JPanel panelOpcoesRadios;
    private javax.swing.JTextPane pergunta2_TextPane;
    private javax.swing.JLabel posicao1Label;
    private javax.swing.JLabel posicao2Label;
    private javax.swing.JLabel posicao3Label;
    private javax.swing.JLabel posicao4Label;
    private javax.swing.JLabel posicao5Label;
    private javax.swing.ButtonGroup posicaoButtonGroup;
    private javax.swing.JRadioButton position0RadioButton;
    private javax.swing.JRadioButton position1RadioButton;
    private javax.swing.JRadioButton position2RadioButton;
    private javax.swing.JRadioButton position3RadioButton;
    private javax.swing.JRadioButton position4RadioButton;
    private javax.swing.JRadioButton position5RadioButton;
    private javax.swing.JPanel positionPanel;
    private javax.swing.JRadioButton profile0RadioButton;
    private javax.swing.JRadioButton profile1RadioButton;
    private javax.swing.JRadioButton profile2RadioButton;
    private javax.swing.JRadioButton profile3RadioButton;
    private javax.swing.JRadioButton profile4RadioButton;
    private javax.swing.JRadioButton profile5RadioButton;
    private javax.swing.ButtonGroup profileButtonGroup;
    private javax.swing.JLabel profileGlyphLabel1;
    private javax.swing.JLabel profileGlyphLabel2;
    private javax.swing.JLabel profileGlyphLabel3;
    private javax.swing.JLabel profileGlyphLabel4;
    private javax.swing.JLabel profileGlyphLabel5;
    private javax.swing.JPanel profileGlyphPanel;
    private javax.swing.JPanel shapePanel;
    private javax.swing.JRadioButton text0RadioButton;
    private javax.swing.JRadioButton text1RadioButton;
    private javax.swing.JRadioButton text2RadioButton;
    private javax.swing.JRadioButton text3RadioButton;
    private javax.swing.JRadioButton text4RadioButton;
    private javax.swing.JRadioButton text5RadioButton;
    private javax.swing.JPanel textPanel;
    private javax.swing.JLabel texto1Label;
    private javax.swing.JLabel texto2Label;
    private javax.swing.JLabel texto3Label;
    private javax.swing.JLabel texto4Label;
    private javax.swing.JLabel texto5Label;
    private javax.swing.ButtonGroup textoButtonGroup;
    private javax.swing.JRadioButton textura0RadioButton;
    private javax.swing.JLabel textura1Label;
    private javax.swing.JRadioButton textura1RadioButton;
    private javax.swing.JLabel textura2Label;
    private javax.swing.JRadioButton textura2RadioButton;
    private javax.swing.JLabel textura3Label;
    private javax.swing.JRadioButton textura3RadioButton;
    private javax.swing.JLabel textura4Label;
    private javax.swing.JRadioButton textura4RadioButton;
    private javax.swing.JLabel textura5Label;
    private javax.swing.JRadioButton textura5RadioButton;
    private javax.swing.ButtonGroup texturaButtonGroup;
    private javax.swing.JPanel texturasPanel;
    // End of variables declaration//GEN-END:variables
    */

    private javax.swing.JButton btnConfirm;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextPane pergunta2_TextPane;
    private PainelDeTeste painelEsquerda;
    static VisibilityTesteView frame;
    private javax.swing.JLabel contadorLabel;
    private javax.swing.JLabel glyphsLabel;
    private javax.swing.JPanel texturasPanel;
    private javax.swing.JPanel coresPanel;
    private javax.swing.JPanel shapePanel;
    private javax.swing.JPanel textPanel;
    private javax.swing.JPanel orientationPanel;
    private javax.swing.JPanel positionPanel;
    private javax.swing.JPanel fundoPainel;
    private javax.swing.JPanel panelOpcoesRadios;
    private javax.swing.JPanel profileGlyphPanel;
    private javax.swing.JRadioButton cor0RadioButton;
    private javax.swing.JRadioButton cor1RadioButton;
    private javax.swing.JRadioButton cor2RadioButton;
    private javax.swing.JRadioButton cor3RadioButton;
    private javax.swing.JRadioButton cor4RadioButton;
    private javax.swing.JRadioButton cor5RadioButton;
    private javax.swing.JLabel cor3Label;
    private javax.swing.JLabel cor4Label;
    private javax.swing.JLabel cor5Label;
    private javax.swing.JLabel cor1Label;
    private javax.swing.JLabel cor2Label;
    private javax.swing.JLabel forma3Label;
    private javax.swing.JLabel textura3Label;
    private javax.swing.JLabel texto3Label;
    private javax.swing.JLabel posicao3Label;
    private javax.swing.JLabel orientation3Label;
    private javax.swing.JRadioButton forma3RadioButton;
    private javax.swing.JRadioButton textura3RadioButton;
    private javax.swing.JRadioButton text3RadioButton;
    private javax.swing.JRadioButton position3RadioButton;
    private javax.swing.JRadioButton orientation3RadioButton;
    private javax.swing.JLabel forma4Label;
    private javax.swing.JLabel textura4Label;
    private javax.swing.JLabel texto4Label;
    private javax.swing.JLabel posicao4Label;
    private javax.swing.JLabel orientation4Label;
    private javax.swing.JRadioButton forma4RadioButton;
    private javax.swing.JRadioButton textura4RadioButton;
    private javax.swing.JRadioButton text4RadioButton;
    private javax.swing.JRadioButton position4RadioButton;
    private javax.swing.JRadioButton orientation4RadioButton;
    private javax.swing.JRadioButton textura0RadioButton;
    private javax.swing.JLabel forma5Label;
    private javax.swing.JLabel textura5Label;
    private javax.swing.JLabel texto5Label;
    private javax.swing.JLabel posicao5Label;
    private javax.swing.JLabel orientation5Label;
    private javax.swing.JRadioButton forma5RadioButton;
    private javax.swing.JRadioButton textura5RadioButton;
    private javax.swing.JRadioButton text5RadioButton;
    private javax.swing.JRadioButton position5RadioButton;
    private javax.swing.JRadioButton orientation5RadioButton;
    private javax.swing.JLabel forma1Label;
    private javax.swing.JLabel textura1Label;
    private javax.swing.JLabel texto1Label;
    private javax.swing.JLabel posicao1Label;
    private javax.swing.JLabel orientation1Label;
    private javax.swing.JRadioButton forma1RadioButton;
    private javax.swing.JRadioButton textura1RadioButton;
    private javax.swing.JRadioButton text1RadioButton;
    private javax.swing.JRadioButton position1RadioButton;
    private javax.swing.JRadioButton position0RadioButton;
    private javax.swing.JRadioButton orientation1RadioButton;
    private javax.swing.JRadioButton orientation0RadioButton;
    private javax.swing.JLabel forma2Label;
    private javax.swing.JLabel textura2Label;
    private javax.swing.JLabel texto2Label;
    private javax.swing.JLabel posicao2Label;
    private javax.swing.JLabel orientation2Label;
    private javax.swing.JRadioButton forma2RadioButton;
    private javax.swing.JRadioButton textura2RadioButton;
    private javax.swing.JRadioButton text2RadioButton;
    private javax.swing.JRadioButton position2RadioButton;
    private javax.swing.JRadioButton orientation2RadioButton;
    private javax.swing.JRadioButton forma0RadioButton;
    private javax.swing.JRadioButton text0RadioButton;
    private javax.swing.ButtonGroup texturaButtonGroup;
    private javax.swing.ButtonGroup corButtonGroup;
    private javax.swing.ButtonGroup formaButtonGroup;
    private javax.swing.ButtonGroup textoButtonGroup;
    private javax.swing.ButtonGroup posicaoButtonGroup;
    private javax.swing.ButtonGroup orientacaoButtonGroup;
    private javax.swing.ButtonGroup profileButtonGroup;
    private javax.swing.JLabel profileGlyphLabel3;
    private javax.swing.JLabel profileGlyphLabel1;
    private javax.swing.JLabel profileGlyphLabel2;
    private javax.swing.JLabel profileGlyphLabel5;
    private javax.swing.JLabel profileGlyphLabel4;
    private javax.swing.JRadioButton profile0RadioButton;
    private javax.swing.JRadioButton profile1RadioButton;
    private javax.swing.JRadioButton profile2RadioButton;
    private javax.swing.JRadioButton profile3RadioButton;
    private javax.swing.JRadioButton profile4RadioButton;
    private javax.swing.JRadioButton profile5RadioButton;
}

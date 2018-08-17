package doutorado.tese.controle.negocio.visualizacao.glyph.factorys.variaveisvisuais;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The TMPatternFactory is a utility to get filling patterns. It's a Singleton
 * design pattern, and the only method of interest is get(), which take a String
 * in parameter and return the (if existing) corresponding Paint pattern object.
 * <P>
 * The patterns are :
 * <UL>
 * <IL> PATTERN_DIAGDOTS2       </IL>
 * <IL> PATTERN_HORIZONTAL      </IL>
 * <IL> PATTERN_UP              </IL>
 * <IL> PATTERN_DIAG_RIGHT_LEFT </IL>
 * <IL> PATTERN_PLUS            </IL>
 * <IL> PATTERN_RIGHT           </IL>
 * <IL> PATTERN_DIAGDOTS        </IL>
 * <IL> PATTERN_CHESS           </IL>
 * <IL> PATTERN_CROSS_LINES     </IL>
 * <IL> PATTERN_DOWN            </IL>
 * <IL> PATTERN_DIAG_CROSS_LINES</IL>
 * <IL> PATTERN_LEFT            </IL>
 * <IL> PATTERN_DOTS            </IL>
 * <IL> PATTERN_VERTICAL        </IL>
 * <IL> PATTERN_DIAG_LEFT_RIGHT </IL>
 * </UL>
 * <P>
 * @author Anderson Soares
 */
public class TMPatternFactory {

    private static TMPatternFactory instance = null;
    // Singleton design pattern

    private HashMap<String, Object> textures = null;// set of patterns 
    private Color textureColor;
    private Color backgroungColor;

    /* --- Constructor --- */
    /**
     * Constructor.
     * @param color
     * @param backgroundColor
     */
    public TMPatternFactory(Color color, Color backgroundColor) {
        textures = new HashMap<>();
        textureColor = color;
        this.backgroungColor = backgroundColor;
//        buildPatternHorizontalPolygon();
        buildPatternHorizontal();
        buildPatternVertical();
        buildPatternDiag_Right2Left();
        buildPatternDiag_Left2Right();
        buildPattern_CrossLines();
        buildPatternDiag_CrossLines();
        buildPatternRight();
        buildPatternUp();
//        buildPatternLeft();
//        buildPatternDiagDots();
//        buildPatternDiagDots2();
//        buildPatternDown();
//        buildPatternTuiles();
//        buildPatternSquares();
//        buildPatternSquareC();
//        buildPatternLightGray();
//        buildPatternDots();
//        buildPatternPlus();
    }

    /**
     * Call this method if anyting change in textures, e.g: color
     */
    public void resetTextures(){
        //        buildPatternHorizontalPolygon();
        buildPatternHorizontal();
        buildPatternVertical();
        buildPatternDiag_Right2Left();
        buildPatternDiag_Left2Right();
        buildPattern_CrossLines();
        buildPatternDiag_CrossLines();
        buildPatternRight();
        buildPatternUp();
//        buildPatternLeft();
//        buildPatternDiagDots();
//        buildPatternDiagDots2();
//        buildPatternDown();
//        buildPatternTuiles();
//        buildPatternSquares();
//        buildPatternSquareC();
//        buildPatternLightGray();
//        buildPatternDots();
//        buildPatternPlus();
    }

    /* --- Singleton pattern --- */
    /**
     * Returns the running instance of TMPatternFactory.
     *
     * @param color the texture color
     * @param backgroundColor
     * @return the running instance of TMPatternFactory
     */
    public static TMPatternFactory getInstance(Color color, Color backgroundColor) {
        if (instance == null) {
            instance = new TMPatternFactory(color, backgroundColor);
        }
        return instance;
    }


    /* --- Patterns accessor --- */
    /**
     * Returns the pattern whose name is given in parameter. If the name is not
     * a recognized pattern name, prints an error on System.err, and returns
     * <CODE>null</CODE>.
     *
     * @param patternName the name of the pattern to find
     * @return the desired pattern; <CODE>null</CODE> if the pattern is not
     * found
     */
    public Paint get(String patternName) {
//        Paint pattern = (Paint) patterns.get(patternName);
        Paint pattern = (Paint) textures.get(patternName);
        return pattern;
    }

    /**
     * @param aBackgroungColor the backgroungColor to set
     */
    public Color setBackgroungColor(Color aBackgroungColor) {
        backgroungColor = aBackgroungColor;
        return backgroungColor;
    }

    public void setTextureColor(Color color) {
        textureColor = color;
    }

    public Color getTextureColor() {
        return textureColor;
    }
    
    /**
     * Returns an Enumeration of patterns names.
     *
     * @return an Enumeration of patterns' names
     */
    public Enumeration getPatternsNames() {
//        return patterns.keys();
        return null;
    }

    public Set<String> getTexturesNames() {
        return textures.keySet();
    }

    /* --- Patterns testing --- */
    /**
     * Display the differents patterns.
     *
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Test Pattern Factory");
        frame.setPreferredSize(new Dimension(800, 600));
        JPanel panel = new JPanel();
        frame.setContentPane(panel);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        TMPatternFactory TM = TMPatternFactory.getInstance(Color.BLACK, Color.WHITE);
        
        for (String textureName : TM.getTexturesNames()) {
            System.out.println(textureName);
//            TM.setTextureColor(Color.YELLOW);
//            TM.setBackgroungColor(Color.WHITE);
//            TM.resetTextures();
            panel.add(buildTestPatternPanel(textureName, TM.get(textureName)));
        }

        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Returns a JPanel containing the tested pattern with its name.
     *
     * @param name the name of the tested pattern
     * @param pattern the tested pattern
     * @return the JPanel containing the tested pattern
     */
    private static JPanel buildTestPatternPanel(String name, Paint pattern) {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel nameLabel = new JLabel(name);
        panel.add(nameLabel, BorderLayout.SOUTH);

        JPatternPanel patternPanel = new JPatternPanel(pattern);
        panel.add(patternPanel, BorderLayout.CENTER);

        return panel;
    }

    /* --- Patterns building --- */
    /**
     * Builds and adds the PATTERN_HORIZONTAL in patterns.
     */
    private void buildPatternHorizontal() {
        BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setStroke(new BasicStroke(3f));
        g.setColor(backgroungColor);
        
        g.fillRect(0, 0, 16, 16);
        g.setColor(textureColor);
//        g.drawLine(0, 1, 16, 1);
        g.drawLine(0, 6, 16, 6);
//        g.drawLine(0, 8, 16, 8);
        g.drawLine(0, 14, 16, 14);
        Rectangle r = new Rectangle(0, 0, 16, 16);
        Paint pattern = new TexturePaint(image, r);
//        patterns.put("PATTERN_HORIZONTAL", pattern);
        textures.put("PATTERN_HORIZONTAL", pattern);
    }

    /**
     * Builds and adds the PATTERN_DIAG_RIGHT_LEFT in patterns.
     */
    private void buildPatternDiag_Right2Left() {
        BufferedImage image
                = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setStroke(new BasicStroke(3f));
        g.setColor(backgroungColor);
        g.fillRect(0, 0, 16, 16);
        g.setColor(textureColor);
        g.drawLine(8, 0, 16, 8);
//        g.drawLine(0, 0, 16, 16);
        g.drawLine(0, 8, 8, 16);
        Rectangle r = new Rectangle(0, 0, 16, 16);
        Paint pattern = new TexturePaint(image, r);
//        patterns.put("PATTERN_DIAG_RIGHT_LEFT", pattern);
        textures.put("PATTERN_DIAG_RIGHT_LEFT", pattern);
    }

    /**
     * Builds and adds the PATTERN_DIAG2 in patterns.
     */
    private void buildPattern_CrossLines() {
        BufferedImage image
                = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(backgroungColor);
        g.fillRect(0, 0, 16, 16);
        g.setColor(textureColor);
        g.drawLine(0, 4, 16, 4);
        g.drawLine(0, 9, 16, 9);
        g.drawLine(0, 14, 16, 14);
        g.drawLine(1, 0, 1, 15);
        g.drawLine(6, 0, 6, 15);
        g.drawLine(11, 0, 11, 15);
        Rectangle r = new Rectangle(0, 0, 16, 16);
        Paint pattern = new TexturePaint(image, r);
//        patterns.put("PATTERN_CROSS_LINES", pattern);
        textures.put("PATTERN_CROSS_LINES", pattern);
    }

    /**
     * Builds and adds the PATTERN_DIAG_CROSS_LINES in patterns.
     */
    private void buildPatternDiag_CrossLines() {
        BufferedImage image
                = new BufferedImage(14, 14, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setStroke(new BasicStroke(2.2f));
        g.setColor(backgroungColor);
        g.fillRect(0, 0, 14, 14);
        g.setColor(textureColor);
        g.drawLine(0, 0, 14, 14);
        g.drawLine(0, 14, 14, 0);
        Rectangle r = new Rectangle(0, 0, 14, 14);
        Paint pattern = new TexturePaint(image, r);
//        patterns.put("PATTERN_DIAG_CROSS_LINES", pattern);
        textures.put("PATTERN_DIAG_CROSS_LINES", pattern);
    }

    /**
     * Builds and adds the PATTERN_DIAG4 in patterns.
     */
    private void buildPatternDiag_Left2Right() {
        BufferedImage image
                = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setStroke(new BasicStroke(2.1f));
        g.setColor(backgroungColor);
        g.fillRect(0, 0, 16, 16);
        g.setColor(textureColor);
        g.drawLine(8, 0, 0, 8);
//        g.drawLine(0, 13, 13, 0);
        g.drawLine(16, 8, 8, 16);
        Rectangle r = new Rectangle(0, 0, 16, 16);
        Paint pattern = new TexturePaint(image, r);
//        patterns.put("PATTERN_DIAG_LEFT_RIGHT", pattern);
        textures.put("PATTERN_DIAG_LEFT_RIGHT", pattern);
    }

    /**
     * Builds and adds the PATTERN_VERTICAL in patterns.
     */
    private void buildPatternVertical() {
        BufferedImage image
                = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setStroke(new BasicStroke(1.8f));
        g.setColor(backgroungColor);
        g.fillRect(0, 0, 16, 16);
        g.setColor(textureColor);
//        g.drawLine(1, 0, 1, 16);
        g.drawLine(3, 0, 3, 16);
        g.drawLine(8, 0, 8, 16);
        g.drawLine(13, 0, 13, 16);
        Rectangle r = new Rectangle(0, 0, 16, 16);
        Paint pattern = new TexturePaint(image, r);
//        patterns.put("PATTERN_VERTICAL", pattern);
        textures.put("PATTERN_VERTICAL", pattern);
    }

    /**
     * Builds and adds the PATTERN_UP in patterns.
     */
    private void buildPatternUp() {
        BufferedImage image
                = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setStroke(new BasicStroke(1.5f));
        g.setColor(backgroungColor);
        g.fillRect(0, 0, 16, 16);
        g.setColor(textureColor);
        g.drawLine(3, 1, 0, 4);
        g.drawLine(3, 1, 6, 4);
        g.drawLine(4, 8, 0, 12);
        g.drawLine(4, 8, 8, 12);
        Rectangle r = new Rectangle(0, 0, 16, 16);
        Paint pattern = new TexturePaint(image, r);
//        patterns.put("PATTERN_DIAGS", pattern);
        textures.put("PATTERN_UP", pattern);
    }

    /**
     * /**
     * Builds and adds the PATTERN_DIAGDOTS in patterns.
     */
    private void buildPatternDiagDots() {
        BufferedImage image
                = new BufferedImage(13, 13, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, 13, 13);
        g.setColor(Color.black);
        g.drawLine(0, 0, 13, 13);
        g.drawLine(3, 0, 13, 10);
        g.drawLine(7, 0, 13, 6);
        g.drawLine(11, 0, 13, 2);
        g.drawLine(0, 3, 10, 13);
        g.drawLine(0, 7, 6, 13);
        g.drawLine(0, 11, 2, 13);
        Rectangle r = new Rectangle(0, 0, 13, 13);
        Paint pattern = new TexturePaint(image, r);
        textures.put("PATTERN_DIAGDOTS", pattern);
    }

    /**
     * /**
     * Builds and adds the PATTERN_DIAGDOTS in patterns.
     */
    private void buildPatternDiagDots2() {
        BufferedImage image
                = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.white);
        g.fillRect(0, 0, 16, 16);
        g.setColor(Color.black);
        g.fillOval(0, 0, 4, 4);
//        g.fillOval(4, 4, 3, 3);
        g.fillOval(8, 0, 4, 4);
        Rectangle r = new Rectangle(0, 0, 16, 16);
        Paint pattern = new TexturePaint(image, r);
        textures.put("PATTERN_DIAGDOTS2", pattern);
    }

    /**
     * Builds and adds the PATTERN_DOTS in patterns.
     */
    private void buildPatternDots() {
        BufferedImage image
                = new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, 5, 5);
        g.setColor(Color.black);
        g.fillOval(1, 1, 3, 3);
        Rectangle r = new Rectangle(0, 0, 5, 5);
        Paint pattern = new TexturePaint(image, r);
        textures.put("PATTERN_DOTS", pattern);
    }

    /**
     * Builds and adds the PATTERN_PLUS in patterns.
     */
    private void buildPatternPlus() {
        BufferedImage image
                = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, 10, 10);
        g.setColor(Color.black);
        g.drawLine(3, 5, 8, 5);
        g.drawLine(3, 6, 8, 6);
        g.drawLine(5, 3, 5, 8);
        g.drawLine(6, 3, 6, 8);
        Rectangle r = new Rectangle(0, 0, 10, 10);
        Paint pattern = new TexturePaint(image, r);
        textures.put("PATTERN_PLUS", pattern);
    }

    /**
     * Builds and adds the PATTERN_TUILES in patterns.
     */
    private void buildPatternTuiles() {
        BufferedImage image
                = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, 10, 10);
        g.setColor(Color.black);
        g.drawArc(-5, -5, 10, 10, 0, -90);
        g.drawArc(5, -5, 10, 10, 270, -90);
        g.drawArc(0, 0, 10, 10, 0, -180);
        Rectangle r = new Rectangle(0, 0, 10, 10);
        Paint pattern = new TexturePaint(image, r);
//        patterns.put("PATTERN_TUILES", pattern);
    }

    /**
     * Builds and adds the PATTERN_LEFT in patterns.
     */
    private void buildPatternLeft() {
        BufferedImage image
                = new BufferedImage(14, 14, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, 14, 14);
        g.setColor(Color.black);
        g.drawLine(0, 6, 3, 3);
        g.drawLine(0, 6, 3, 9);
        g.drawLine(5, 6, 8, 3);
        g.drawLine(5, 6, 8, 9);
        g.drawLine(10, 6, 13, 3);
        g.drawLine(10, 6, 13, 9);

        Rectangle r = new Rectangle(0, 0, 14, 14);
        Paint pattern = new TexturePaint(image, r);
//        patterns.put("PATTERN_LEFT", pattern);
        textures.put("PATTERN_LEFT", pattern);
    }

    /**
     * Builds and adds the PATTERN_SQUARES in patterns.
     */
    private void buildPatternSquares() {
        BufferedImage image
                = new BufferedImage(8, 9, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, 8, 9);
        g.setColor(Color.black);
        g.fillRect(2, 2, 3, 3);
        g.fillRect(5, 6, 3, 3);
        Rectangle r = new Rectangle(0, 0, 8, 9);
        Paint pattern = new TexturePaint(image, r);
//        patterns.put("PATTERN_SQUARES", pattern);
    }

    /**
     * Builds and adds the PATTERN_LIGHT_GRAY in patterns.
     */
    private void buildPatternLightGray() {
//        patterns.put("PATTERN_LIGHT_GRAY", Color.lightGray);
    }

    /**
     * Builds and adds the PATTERN_DOWN in patterns.
     */
    private void buildPatternDown() {
//        patterns.put("PATTERN_DARK_GRAY", Color.darkGray);
        BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setStroke(new BasicStroke(1.8f));
        g.setColor(Color.white);
        g.fillRect(0, 0, 10, 10);
        g.setColor(Color.black);
        g.drawLine(3, 4, 0, 1);
        g.drawLine(3, 4, 6, 1);
        g.drawLine(3, 8, 0, 5);
        g.drawLine(3, 8, 6, 5);
        Rectangle r = new Rectangle(0, 0, 10, 10);
        Paint pattern = new TexturePaint(image, r);

        textures.put("PATTERN_DOWN", pattern);
    }

    /**
     * Builds and adds the PATTERN_RIGHT in patterns.
     */
    private void buildPatternRight() {
        BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setStroke(new BasicStroke(1.8f));
        g.setColor(backgroungColor);
        g.fillRect(0, 0, 16, 16);
        g.setColor(textureColor);
        g.drawLine(8, 8, 4, 4);
        g.drawLine(8, 8, 4, 12);
//        g.drawLine(5, 3, 8, 6);
//        g.drawLine(5, 9, 8, 6);
        g.drawLine(15, 8, 11, 4);
        g.drawLine(15, 8, 11, 12);
        Rectangle r = new Rectangle(0, 0, 16, 16);
        Paint pattern = new TexturePaint(image, r);
        textures.put("PATTERN_RIGHT", pattern);
    }

    /**
     * Builds and adds the PATTERN_CHESS in patterns.
     */
    private void buildPatternSquareC() {
        BufferedImage image = new BufferedImage(14, 14, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, 14, 14);
        g.setColor(Color.black);
        g.fillRect(0, 0, 7, 7);
        g.setColor(Color.gray);
        g.fillRect(7, 7, 7, 7);
        Rectangle r = new Rectangle(0, 0, 14, 14);
        Paint pattern = new TexturePaint(image, r);
        textures.put("PATTERN_CHESS", pattern);
    }

}

/**
 * Inner class implementing a JPanel filled with a pattern.
 */
class JPatternPanel extends JPanel {

    private Paint pattern = null; // the pattern to paint

    /**
     * Constructor taking the filling pattern in parameter.
     *
     * @param pattern the filling pattern
     */
    public JPatternPanel(Paint pattern) {
        this.pattern = pattern;
        setPreferredSize(new Dimension(50, 50));
    }

    /**
     * Paint method.
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(pattern);

        g2.fillRect(getX(), getY(), getWidth(), getHeight());
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.visao;

import doutorado.tese.controle.mb.GlyphManager;
import doutorado.tese.modelo.TreeMapNode;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import net.bouthier.treemapAWT.TMOnDrawFinished;
import net.bouthier.treemapAWT.TMUpdaterConcrete;
import net.bouthier.treemapAWT.TMView;

/**
 *
 * @author Anderson Soares
 */
public class GlassPanelClick extends JPanel {

    private TMView view;
    private GlyphManager glyphManager;
    public JButton teste;
    private Graphics graphicsGlobal;
    private boolean clicou = false;
    private List<TreeMapNode> listClick;
    private OnClick onClickListener;
    private OnMouseOver itemDetailsOnDemand;

    /**
     * Construtor chamado ao selecionar o checkbox indicando que os glyphs serao
     * usados.
     */
    public GlassPanelClick() {
        setOpaque(false);
        setLayout(new GroupLayout(this));
        setVisible(true);
        callListner();
        listClick = new ArrayList<>();

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mouseClicando();
                onClickListener.clicou(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                itemDetailsOnDemand.getDetailsOnDemand(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
        });
        
        this.onClickListener = (MouseEvent evt) -> {
            //não faz nada.
        };
        
        this.itemDetailsOnDemand = new OnMouseOver() {
            @Override
            public void getDetailsOnDemand(MouseEvent evt) {
                
            }

            @Override
            public String tooltipEvent(MouseEvent evt) {
                return "";
            }
        };
    }

    @Override
    public String getToolTipText(MouseEvent evt) {
        return itemDetailsOnDemand.tooltipEvent(evt); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    public void setOnClickListener(OnClick onClickListener) {
        this.onClickListener = onClickListener;
    }
           
    public static interface OnClick{
        public void clicou(MouseEvent evt);
    }
    
    public void setOnMouseOverListener(OnMouseOver itemDetailsOnDemand){
        this.itemDetailsOnDemand = itemDetailsOnDemand;
    }
    
    public static interface OnMouseOver{
        public void getDetailsOnDemand(MouseEvent evt);
        public String tooltipEvent(MouseEvent evt);
    }    

    private void mouseClicando() {
        clicou = true;
        this.repaint();
    }

    void setTMView(TMView view) {
        this.view = view;
    }

    private void callListner() {
        TMUpdaterConcrete.listeners.add(new TMOnDrawFinished() {
            @Override
            public void onDrawFinished(String text) {
//                glyphManager.setRootNodeZoom(view.getRootAnderson());
//                logger.info("Acionando prepare2Draw() a partir do onDrawFinished() - Root: " + glyphManager.getRootNodeZoom());
//                glyphManager.prepare2Draw();//chamado para redesenhar os glyphs no drill-down
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
//        graphicsGlobal = g;
        g2d.setColor(new Color(0, 255, 0, 0));//painel com fundo transparente
        setBounds(view.getBounds());
        Rectangle r = getBounds();
        g2d.fillRect(r.x, r.y, r.width, r.height);
        if (clicou) {
            g2d.setColor(new Color(50, 205, 50));
            for (TreeMapNode treeMapNode : listClick) {
                treeMapNode.getBounds();
                g2d.setStroke(new BasicStroke(3f));
                g2d.drawRect(treeMapNode.getBounds().x+2, treeMapNode.getBounds().y +2,
                        treeMapNode.getBounds().width-4, treeMapNode.getBounds().height -4);
            }
        } else {
            
        }
        g.dispose();

    }

    public void setListaItnsClicados(List<TreeMapNode> listClick) {
        this.listClick = listClick;
    }

}

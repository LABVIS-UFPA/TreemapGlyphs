/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.visao;

import doutorado.tese.controle.mb.GlyphManager;
import doutorado.tese.modelo.TreeMapNode;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    public void setOnClickListener(OnClick onClickListener) {
        this.onClickListener = onClickListener;
    }
    
    public static interface OnClick{
        public void clicou(MouseEvent evt);
    }
    

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
            public void mouseClicked(MouseEvent evt) {
                mouseClicando();
                onClickListener.clicou(evt);
            }
        });
        
        this.onClickListener = (MouseEvent evt) -> {
            //não faz nada.
        };

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
        graphicsGlobal = g;
        g.setColor(new Color(0, 255, 0, 0));//painel com fundo transparente
        setBounds(view.getBounds());
        Rectangle r = getBounds();
        g.fillRect(r.x, r.y, r.width, r.height);
        if (clicou) {
            g.setColor(Color.red);
            for (TreeMapNode treeMapNode : listClick) {
                treeMapNode.getBounds();
                g.drawRect(treeMapNode.getBounds().x, treeMapNode.getBounds().y,
                        treeMapNode.getBounds().width, treeMapNode.getBounds().height);
            }
        } else {
            
        }
        g.dispose();

    }

    public void setListaItnsClicados(List<TreeMapNode> listClick) {
        this.listClick = listClick;
    }

}
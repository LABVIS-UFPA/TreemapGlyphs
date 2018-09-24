/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.visao;

import doutorado.tese.controle.mb.testelaboratorioMB.FinishedSetupCallBack;
import doutorado.tese.controle.negocio.visualizacao.treemap.treemapAPI.TMModel_Draw;
import doutorado.tese.controle.negocio.visualizacao.treemap.treemapAPI.TMModel_Size;
import doutorado.tese.dao.ManipuladorArquivo;
import doutorado.tese.modelo.Coluna;
import doutorado.tese.util.Constantes;
import doutorado.tese.modelo.Rect;
import doutorado.tese.modelo.TreeMapItem;
import doutorado.tese.modelo.TreeMapLevel;
import doutorado.tese.modelo.TreeMapNode;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import net.bouthier.treemapAWT.TMModelNode;
import net.bouthier.treemapAWT.TMNodeEncapsulator;
import net.bouthier.treemapAWT.TMOnDrawFinished;
import net.bouthier.treemapAWT.TMNodeModel;
import net.bouthier.treemapAWT.TMNodeModelComposite;
import net.bouthier.treemapAWT.TMThreadModel;
import net.bouthier.treemapAWT.TMUpdaterConcrete;
import net.bouthier.treemapAWT.TMView;
import net.bouthier.treemapAWT.TreeMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Anderson Soares
 */
public class VisualizationsArea {

    private static final Logger logger = LogManager.getLogger(VisualizationsArea.class);
    private final ManipuladorArquivo manipulador;
    private Queue<String> hierarquiaFila;
    private String labelColumn = "";
    private TreeMapNode root;
    private TreeMapNode fixedRoot;
    boolean call = false;

//variaveis para a API do Treemap
    private TMModelNode modelTree = null; // the model of the demo tree
    private TreeMap treeMap = null; // the treemap builded
    private TMView view = null;
    private TMModel_Draw cDraw = null;
    private TMModel_Size cSize = null;
    //Star Glyph
//    private StarGlyph[] starGlyphs;
    private String itemCor;
    private String[] colunasDetalhesDemanda;
    private TreeMapNode itemRespostaUsuario;
    private List<TreeMapNode> respostasUsuario;

    public VisualizationsArea(int w, int h, ManipuladorArquivo manipulador,
            String itemTamanho, String[] itensHierarquia, String itemLegenda, String itemCor, String[] itensDetalhes,
            FinishedSetupCallBack callback) {
        this.manipulador = manipulador;
        this.hierarquiaFila = new LinkedList<>();
        this.respostasUsuario = new ArrayList<>();

        Rectangle rect = new Rectangle(0, 0, w, h);
        root = new TreeMapLevel(new Rect(rect.x, rect.y, rect.width, rect.height));//TMModelNode
        fixedRoot = root;
        root.setPaiLevel(null);
        root.setRaiz(true);
        root.setLabel("ROOT");
        //é possivel imprimir a arvore chamando o metodo printTree()
        modelTree = createTree(itensHierarquia, itemTamanho, itemLegenda, itemCor);
        treeMap = new TreeMap(modelTree);

        cSize = new TMModel_Size();
        cDraw = new TMModel_Draw();
        cDraw.setItemCor(itemCor);
        cDraw.getFillingOfObject(root);
        colunasDetalhesDemanda = itensDetalhes;
        updateDetalhesDemanda();

        this.view = treeMap.getView(cSize, cDraw);//getView() retorna um JPainel

        this.view.getAlgorithm().setBorderSize(15);
        this.view.setBounds(rect);

        TMOnDrawFinished listener = (new TMOnDrawFinished() {

            @Override
            public void onDrawFinished(String t) {
                getRootBoundsFromView(t);
                synchronized (callback) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (!call) {
                                call = true;
                                callback.onFinished();
                            }
                        }
                    }).start();
                }
            }
//            }
        });
        TMThreadModel.listeners.add(listener);
        TMUpdaterConcrete.listeners.add(listener);

        //        TMThreadModel.listener = listener;
        TMUpdaterConcrete.listeners.add(listener);

        this.view.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                TreeMapNode nodeUnderTheMouse = (TreeMapNode) VisualizationsArea.this.view.getNodeUnderTheMouse(e);
                setItemRespostaUsuario(nodeUnderTheMouse);
                System.out.println("---->"+nodeUnderTheMouse.getMapaDetalhesItem().get(manipulador.getColunas()[0]));
            }

        });
    }

    public void getRootBoundsFromView(String t) {
        TMNodeModel nodeModel = this.view.getAlgorithm().getRoot();//TMNodeModel
        if (nodeModel != null) {
            Rectangle area = nodeModel.getArea();
            this.root.setBounds(area);

            this.root = this.fixedRoot;
            boolean equalizeRoots = equalizeRoots(root, nodeModel);
            try {
                if (!equalizeRoots) {
                    throw new Exception();
                } else {
                    logger.info("Equalizou os Root do Treemap? " + equalizeRoots);
                }
            } catch (Exception e) {
                logger.error("Erro, não foi possível equalizar os objs Roots", e);
            }

            setAreaNodesTree(this.root, nodeModel);
        }
    }

    public boolean equalizeRoots(TreeMapNode equalized, TMNodeModel nodeModel) {
        TreeMapNode aux = (TreeMapNode) ((TMNodeEncapsulator) nodeModel.getNode()).getNode();
        if (equalized == aux) {
            logger.info("meu root: " + equalized + "\n\t\t root API: " + aux);
            this.root = equalized;
            return true;
        } else {
            for (TreeMapNode e : equalized.getChildren()) {
                if (equalizeRoots(e, nodeModel)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Metodo que define o tamanho de cada no da arvore do treemap. Este metodo
     * tambem define o tamanho dos glyphs dos TreeMapItems.
     *
     * @param item
     * @param nodoPaiModel
     */
    public void setAreaNodesTree(TMModelNode item, TMNodeModel nodoPaiModel) {
        TreeMapNode nodo = (TreeMapNode) item;

        if (nodoPaiModel instanceof TMNodeModelComposite) {//treemapLevel
            for (int i = 0; i < nodo.getChildren().size(); i++) {

                TreeMapNode filho = nodo.getChildren().get(i);
                TMNodeModel filhoModel = ((TMNodeModelComposite) nodoPaiModel).getChildrenList().get(i);

                filho.setBounds(filhoModel.getArea());
                filho.setLabel(filhoModel.getTitle());
                filho.setParent(nodo);
                if (filho instanceof TreeMapItem) {
                    filho.getGlyph().setBounds(filho.getBounds());
                }
                setAreaNodesTree(filho, filhoModel);
            }
        }
    }

    public void setHierarchy(String[] hierarquia) {
        for (TreeMapItem treeMapItem : manipulador.getItensTreemap()) {
            hierarquiaFila.addAll(Arrays.asList(hierarquia));
            root.inserirFilhos(hierarquiaFila, treeMapItem, root);
        }
    }

    public void setSizeColumn(Coluna column) {
        this.root.setSize(column);
//        this.root.organize();
    }

    public void setLabelColumn(Coluna column) {
        this.labelColumn = column.getName();
        setTreemapItemLabel(this.root.getChildren(), true);
    }

    public void setTreemapItemLabel(List<TreeMapNode> itensFilhos, boolean isUseLabel) {
        itensFilhos.forEach((filho) -> {
            if (filho instanceof TreeMapItem) {
                filho.setUseLabel(isUseLabel);
                if (filho.isUseLabel()) {
                    filho.setLabel(filho.getMapaDetalhesItem().get(ManipuladorArquivo.getColuna(labelColumn)));
                } else {
                    filho.setLabel("");
                }
            } else {
                TreeMapLevel level = (TreeMapLevel) filho;
                setTreemapItemLabel(level.getChildren(), isUseLabel);
            }
        });
    }

    private TreeMapNode createTree(String[] hierarquia, String itemTamanho, String itemLegenda, String itemCor) {
        setHierarchy(hierarquia);
        setSizeColumn(ManipuladorArquivo.getColuna(itemTamanho));
        if (!Constantes.isShowLegenda()) {
            setTreemapItemLabel(root.getChildren(), false);
        } else {
            setLabelColumn(ManipuladorArquivo.getColuna(itemLegenda));
        }
        return root;
    }

    public void printTree(TMModelNode item, String appender) {
        TreeMapNode nodo = (TreeMapNode) item;
        System.out.println(appender + nodo.getLabel() + " - " + nodo.getSize());
        nodo.getChildren().forEach(each -> printTree(each, appender + appender));
    }

    /**
     * @return the treeMap
     */
    public TreeMap getTreeMap() {
        return treeMap;
    }

    /**
     * @return the view
     */
    public TMView getView() {
        return view;
    }

    /**
     * @return the colunasDetalhesDemand
     */
    public String[] getColunasDetalhesDemanda() {
        return colunasDetalhesDemanda;
    }

    /**
     * @param colunasDetalhesDemanda the colunasDetalhesDemanda to set
     */
    public void setColunasDetalhesDemanda(String[] colunasDetalhesDemanda) {
        this.colunasDetalhesDemanda = colunasDetalhesDemanda;
    }

    public void updateDetalhesDemanda() {
        cDraw.setColunasDetalhesDemanda(getColunasDetalhesDemanda());
        cDraw.getTooltipOfObject(root);
    }

    /**
     * @return the itemRespostaUsuario
     */
    public TreeMapNode getItemRespostaUsuario() {
        return itemRespostaUsuario;
    }

    /**
     * @param itemRespostaUsuario the itemRespostaUsuario to set
     */
    public void setItemRespostaUsuario(TreeMapNode itemRespostaUsuario) {
        this.itemRespostaUsuario = itemRespostaUsuario;
        if (itemRespostaUsuario != null) {
            getRespostasUsuario().add(this.itemRespostaUsuario);
        }
    }

    /**
     * @return the respostasUsuario
     */
    public List<TreeMapNode> getRespostasUsuario() {
        return respostasUsuario;
    }

    /**
     * @param respostasUsuario the respostasUsuario to set
     */
    public void setRespostasUsuario(List<TreeMapNode> respostasUsuario) {
        this.respostasUsuario = respostasUsuario;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.control.mb;

import doutorado.tese.control.mb.testelaboratorioMB.FinishedSetupCallBack;
import doutorado.tese.control.mb.testelaboratorioMB.LoggerMB;
import doutorado.tese.control.business.userTest.ManipuladorLog;
import doutorado.tese.control.business.visualizations.treemap.treemapAPI.TMModel_Draw;
import doutorado.tese.control.business.visualizations.treemap.treemapAPI.TMModel_Size;
import doutorado.tese.dao.ManipuladorArquivo;
import doutorado.tese.model.Coluna;
import doutorado.tese.util.Constantes;
import doutorado.tese.model.Rect;
import doutorado.tese.model.TreeMapItem;
import doutorado.tese.model.TreeMapLevel;
import doutorado.tese.model.TreeMapNode;
import doutorado.tese.util.ColunasLog;
import doutorado.tese.util.Metadados;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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
public class DrawAreaMB {

    private static final Logger logger = LogManager.getLogger(DrawAreaMB.class);
    private final ManipuladorArquivo manipulador;
    private Queue<String> hierarquiaFila = null;
    private String labelColumn = "";
    private TreeMapNode root = null;
    private TreeMapNode fixedRoot = null;
    boolean call = false;
    private List<TreeMapNode> listClickedItems = null;

//variaveis para a API do Treemap
    private TMModelNode modelTree = null; // the model of the demo tree
    private TreeMap treeMap = null; // the treemap builded
    private TMView view = null;
    private TMModel_Draw cDraw = null;
    private TMModel_Size cSize = null;
//    private String itemCor;
    private String[] colunasDetalhesDemanda = null;
    private TreeMapNode itemRespostaUsuario = null;

    public DrawAreaMB(int w, int h, ManipuladorArquivo manipulador,
            String itemTamanho, String[] itensHierarquia, String itemLegenda, String itemCor, String[] itensDetalhes,
            FinishedSetupCallBack callback) {

        this.hierarquiaFila = null;
        this.listClickedItems = null;
        root = null;
        fixedRoot = null;
        modelTree = null;
        treeMap = null;
        cSize = null;
        cDraw = null;
        this.view = null;
        System.gc();

        this.manipulador = manipulador;
        this.hierarquiaFila = new LinkedList<>();
        this.listClickedItems = new ArrayList<>();
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
                    new Thread(() -> {
                        if (!call) {
                            call = true;
                            callback.onFinished();
                        }
                    }).start();
                }
            }
        });
        TMThreadModel.listeners.add(listener);
        TMUpdaterConcrete.listeners.add(listener);

        this.view.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                TreeMapNode nodeUnderTheMouse = (TreeMapNode) DrawAreaMB.this.view.getNodeUnderTheMouse(e);
                //Para não desenhar o highlight dos levels basta descomentar o if abaixo
                //if (nodeUnderTheMouse.getMapaDetalhesItem() != null) {
                changeHighLight(nodeUnderTheMouse);
//                } 
//                  else {
//                    if (nodeUnderTheMouse instanceof TreeMapLevel) {
//                        TreeMapLevel level = (TreeMapLevel) nodeUnderTheMouse;
//                        changeHighLight(nodeUnderTheMouse);
//                        System.out.println("Level: " + level.getLabel());
//                    }
//                }
            }
        });
    }
    
    private void highLightTreemapItem(TreeMapItem node) {
        if (node.isHighLighted()) {
            node.setHighLight(false);
            getNodosSelecionadosUsuario().remove(node);
            if (ManipuladorLog.getRespostaUsuarioTemp() != null) {
                if (ManipuladorLog.getRespostaUsuarioTemp().contains(node.getId())) {
                    ManipuladorLog.getRespostaUsuarioTemp().remove(node.getId());
                }
            }
        } else {
            node.setHighLight(true);
            getNodosSelecionadosUsuario().add(node);
            if (ManipuladorLog.getRespostaUsuarioTemp() != null) {
                if (!ManipuladorLog.getRespostaUsuarioTemp().contains(node.getId())) {
                    ManipuladorLog.getRespostaUsuarioTemp().add(node.getId());
                }
            }
        }
    }

    private void highLightTreemapLevel(TreeMapLevel node) {
        if (node.isHighLighted()) {
            node.setHighLight(false);
            getNodosSelecionadosUsuario().remove(node);
            if (ManipuladorLog.getRespostaUsuarioTemp() != null) {
                if (ManipuladorLog.getRespostaUsuarioTemp().contains(node.getLabel())) {
                    ManipuladorLog.getRespostaUsuarioTemp().remove(node.getLabel());
                }
            }
        } else {
            node.setHighLight(true);
            getNodosSelecionadosUsuario().add(node);
            if (ManipuladorLog.getRespostaUsuarioTemp() != null) {
                if (!ManipuladorLog.getRespostaUsuarioTemp().contains(node.getLabel())) {
                    ManipuladorLog.getRespostaUsuarioTemp().add(node.getLabel());
                }
            }
        }
    }

    public void changeHighLight(TreeMapNode nodeUnderTheMouse) {
        if (nodeUnderTheMouse instanceof TreeMapItem) {
            TreeMapItem node = (TreeMapItem) nodeUnderTheMouse;
            highLightTreemapItem(node);
        } else {
            TreeMapLevel node = (TreeMapLevel) nodeUnderTheMouse;
            highLightTreemapLevel(node);
        }
        updateChangeHighLightedLog(nodeUnderTheMouse);
    }

    private void updateChangeHighLightedLog(TreeMapNode node) {
        if (LoggerMB.getColunaLog() != null) {
            LoggerMB.getColunaLog()[ColunasLog.ID_TREEMAP_ITEM.getId()]
                    = (node instanceof TreeMapItem) ? ((TreeMapItem) node).getId() + "" : ((TreeMapLevel) node).getLabel();
            LoggerMB.getColunaLog()[ColunasLog.SELECIONADO.getId()] = node.isHighLighted() + "";
            LoggerMB.getColunaLog()[ColunasLog.TREEMAP_LABEL.getId()] = node.getLabel() + "";
            LoggerMB.getColunaLog()[ColunasLog.RESPOSTA_CORRETA.getId()]
                    = ManipuladorLog.verificarResposta(node, Integer.parseInt(LoggerMB.getColunaLog()[ColunasLog.ID_TAREFA.getId()])) + "";
            LoggerMB.addNewLineLog();
        }
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

                TreeMapNode treemapItem = nodo.getChildren().get(i);
                TMNodeModel filhoModel = ((TMNodeModelComposite) nodoPaiModel).getChildrenList().get(i);

                treemapItem.setBounds(filhoModel.getArea());
                treemapItem.setLabel(filhoModel.getTitle());
                treemapItem.setParent(nodo);
                if (treemapItem instanceof TreeMapItem) {
                    treemapItem.getGlyph().setBounds(treemapItem.getBounds());
                }
                setAreaNodesTree(treemapItem, filhoModel);
            }
//            imprimirAreasTreemap(nodoPaiModel);
        }
    }

    private void imprimirAreasTreemap(TMNodeModel nodoPaiModel) {
        int x = ((TMNodeModelComposite) nodoPaiModel).getArea().x;
        int y = ((TMNodeModelComposite) nodoPaiModel).getArea().y;
        int w = ((TMNodeModelComposite) nodoPaiModel).getArea().width;
        int h = ((TMNodeModelComposite) nodoPaiModel).getArea().height;
        System.out.println(
                ((TMNodeModelComposite) nodoPaiModel).getId() + "\t"
                + ((TMNodeModelComposite) nodoPaiModel).getTitle() + "\t"
                + "x: " + x + "\t"
                + "y: " + y + "\t"
                + "w: " + w + "\t"
                + "h: " + h
        );
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
        System.out.println(appender + nodo.getLabel() + " - " + nodo.getSizeTreemapNode());
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

//    /**
//     * @param itemRespostaUsuario the itemRespostaUsuario to set
//     */
//    public void setItemRespostaUsuario(TreeMapNode itemRespostaUsuario) {
//        this.itemRespostaUsuario = itemRespostaUsuario;
//        if (itemRespostaUsuario != null) {
//            getNodosSelecionadosUsuario().add(this.itemRespostaUsuario);
//        }
//    }
    /**
     * @return the list containing the treemapnodes clicked by user
     */
    public List<TreeMapNode> getNodosSelecionadosUsuario() {
        return listClickedItems;
    }

}

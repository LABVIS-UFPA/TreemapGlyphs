/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.visualizacao.treemap.treemapAPI;

import doutorado.tese.visualizacao.treemap.TreeMapItem;
import doutorado.tese.visualizacao.treemap.TreeMapLevel;
import doutorado.tese.visualizacao.treemap.TreeMapNode;
import javax.swing.JFrame;
import net.bouthier.treemapAWT.TMModelNode;
import net.bouthier.treemapAWT.TMView;
import net.bouthier.treemapAWT.TreeMap;

/**
 *
 * @author Anderson Soares
 */
public class TreemapAPITeste {
    private static TreeMapNode model   = null; // the model of the demo tree
    private static TreeMap 		   treeMap = null; // the treemap builded
    private static String 		   name    = "Teste Treemap API"; // name for this demo
    
    public static void printTree3(TreeMapNode item, String appender) {
        TreeMapNode i = (TreeMapNode) item;
        System.out.println(appender+i.getLabel()+" - "+i.getSize());
        i.getChildren().forEach(each -> printTree3(each, appender + appender));
    }
    
    public static void main(String[] args) {
        model = createTree3();
        printTree3(model, "\t");
        treeMap = new TreeMap(model);

        TMModel_Size fSize = new TMModel_Size();
        TMModel_Draw fDraw = new TMModel_Draw();
        TMView view = treeMap.getView(fSize, fDraw);
        //TODO Definir tamanho das bordas

        JFrame viewFrame = new JFrame(name);
        viewFrame.setContentPane(view);
        viewFrame.pack();
        viewFrame.setVisible(true);
        viewFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    }
            
    private static TreeMapNode createTree3() {
        TreeMapNode root = new TreeMapLevel();
        root.setSize(0d);
        root.setLabel("Root");

        TreeMapNode node1 = root.addChild(new TreeMapLevel());//gasolina
        node1.setSize(10d);
        node1.setLabel("Gasolina");
        
        TreeMapNode node11 = node1.addChild(new TreeMapLevel());//tracao - sim
        node11.setSize(8d);        
        node11.setLabel("Tracao - sim");
        
        TreeMapNode node111 = node11.addChild(new TreeMapItem());//alfa-romeu
        node111.setSize(7d);
        node111.setLabel("alfa-romeu");
        
        TreeMapNode node112 = node11.addChild(new TreeMapItem());//bmw
        node112.setSize(6d);
        node112.setLabel("bmw");
        
        TreeMapNode node12 = node1.addChild(new TreeMapItem());//tracao - item
        node12.setSize(5d);
        node12.setLabel("Tracao - Item");
        
        TreeMapNode node2 = root.addChild(new TreeMapLevel());//diesel
        node2.setSize(5d);
        node2.setLabel("Diesel");
        
        TreeMapNode node22 = node2.addChild(new TreeMapLevel());//tracao - null
        node22.setSize(7d);
        node22.setLabel("Tracao - outra");
        
        TreeMapNode node221 = node22.addChild(new TreeMapItem());//dodge
        node221.setSize(6d);
        node221.setLabel("dodge");
        
        return root;
    }
}
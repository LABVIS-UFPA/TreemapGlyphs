/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.organize;

import doutorado.tese.visualizacao.treemap.TreeMapNode;
import java.util.List;

/**
 *
 * @author Anderson Soares
 */
public class QuickSort {
    
    public static List<TreeMapNode> sortDescending(List<TreeMapNode> items) {
        if (items == null || items.isEmpty()) {
            return null;
        }

        quickSortDesc(items, 0, items.size() - 1);
        return items;
    }
    
    private static void quickSortDesc(List<TreeMapNode> inputArr, int lowerIndex, int higherIndex) {

        int i = lowerIndex;
        int j = higherIndex;
        // calculate pivot number
        double pivot = inputArr.get(lowerIndex + (higherIndex - lowerIndex) / 2).getSize();
        // Divide into two arrays
        while (i <= j) {
            /**
             * In each iteration, we will identify a number from left side which
             * is greater then the pivot value, and also we will identify a
             * number from right side which is less then the pivot value. Once
             * the search is done, then we exchange both numbers.
             */
            while (inputArr.get(i).getSize() > pivot) {
                i++;
            }
            while (inputArr.get(j).getSize() < pivot) {
                j--;
            }
            if (i <= j) {
                TreeMapNode temp = inputArr.get(i);
                inputArr.set(i, inputArr.get(j));
                inputArr.set(j, temp);
                //move index to next position on both sides
                i++;
                j--;
            }
        }
        // call quickSort() method recursively
        if (lowerIndex < j) {
            quickSortDesc(inputArr, lowerIndex, j);
        }
        if (i < higherIndex) {
            quickSortDesc(inputArr, i, higherIndex);
        }
    }
}

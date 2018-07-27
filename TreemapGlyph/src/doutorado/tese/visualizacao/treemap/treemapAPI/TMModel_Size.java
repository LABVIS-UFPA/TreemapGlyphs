/*
 * TMModel_Size.java
 * www.bouthier.net
 *
 * The MIT License :
 * -----------------
 * Copyright (c) 2001 Christophe Bouthier
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package doutorado.tese.visualizacao.treemap.treemapAPI;

import doutorado.tese.visualizacao.treemap.TreeMapNode;

import net.bouthier.treemapAWT.TMComputeSizeAdapter;


/**
 * The TMModel_Size class implements an example of a TMComputeSizeAdapter
 for a TMFileModelNode.
 *
 * @author Christophe Bouthier [bouthier@loria.fr]
 * @version 2.5
 */
public class TMModel_Size 
	extends TMComputeSizeAdapter {


    /* --- TMComputeSizeAdapter -- */

    @Override
    public boolean isCompatibleWithObject(Object node) {
        return node instanceof TreeMapNode;
    }

    /**
     * Returns the size of the node.
     * @param node
     * @return 
     */
    @Override
    public float getSizeOfObject(Object node) {
        if (node instanceof TreeMapNode) {
            TreeMapNode n = (TreeMapNode) node;
            return (float) Math.round(n.getSize());
        }
        return 0.0f;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.visualizacao.treemap;

import java.awt.Rectangle;

public class Rect extends Rectangle{
//    public double x, y, w, h;
//    public String label;

    public Rect() {
        this(0,0,1,1);
    }

    public Rect(Rect r) {
        setRect(r.x, r.y, r.width, r.height);
    }

    public Rect(int x, int y, int w, int h) {
        setRect(x, y, w, h);
    }

    public double aspectRatio() {
        return Math.max(width/height, height/width);
    }

    public void setRect(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    @Override
    public String toString() {
        return "X: "+(int) Math.round(x)+"\tY: "+(int) Math.round(y)+
                "\tW: "+(int) Math.round(width)+"\tH: "+(int) Math.round(height);
    }
    
    
}
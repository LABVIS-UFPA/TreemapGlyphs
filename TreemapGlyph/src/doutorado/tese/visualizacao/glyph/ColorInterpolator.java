/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doutorado.tese.visualizacao.glyph;

import java.awt.Color;

/**
 *
 * @author Elvis (LABVIS)
 */
public class ColorInterpolator {
    double ar,ag,ab,br,bg,bb;
    
    public  void config(double Xmin,double Xmax,Color c,Color c2){
       
       ar = (1.0*c2.getRed()-c.getRed())/(Xmax-Xmin);
       br = c.getRed() - ar* Xmin;
       
       ab = (1.0*c2.getBlue()-c.getBlue())/(Xmax-Xmin);
       bb = c.getBlue() - ab* Xmin;
       
       ag = (1.0*c2.getGreen()-c.getGreen())/(Xmax-Xmin);
       bg = c.getGreen() - ag* Xmin;
        
    }   
    
    public Color interpolate(double valueX){
        int r = (int) (Math.round(ar * valueX +br)) ;
        int g = (int) (Math.round(ag * valueX +bg)) ;
        int b = (int) (Math.round(ab * valueX +bb)) ;
       
        return new Color(r,g,b);
    }  
    

        
}

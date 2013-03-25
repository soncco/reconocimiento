package filters;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;

public class Process {
	
	private String camino;
	
	public Process() {
		camino = "";
	}
	
	public String getCamino() {
		return this.camino;
	}
	
	public void setCamino(String pCamino) {
		this.camino = pCamino;
	}
	
	public ArrayList<Point> updown(Bitmap bitmap) {
		ArrayList<Point> Lineas = new ArrayList<Point>();
		int iMaxF = bitmap.getHeight()-1;
        int iMaxC = bitmap.getWidth()-1;
        // Obtenemos los puntos de color pColor
        for (int i = 0; i < iMaxF; i++) {
            for (int j = 0; j < iMaxC; j++) {
                if (bitmap.getPixel(j, i) != Color.WHITE) {
                    // Buscar a la derecha y a la izquierda
                    int der = i;
                    int aba = j;
                    Lineas.add(new Point(j, i));
                    while (der < iMaxF -1 && aba < iMaxC -1&& (bitmap.getPixel(aba, 1+der) != Color.WHITE 
                    		|| bitmap.getPixel(1+aba, der) != Color.WHITE
                    		|| bitmap.getPixel(1+aba, der+1) != Color.WHITE)) {
                        if (bitmap.getPixel(aba, 1+der) != Color.WHITE) {                            
                            der = der+1;
                            Lineas.add(new Point(aba, der));
                            bitmap.setPixel(aba, der, Color.WHITE);
                            camino += "D";
                        } else {
                            if (bitmap.getPixel(1+aba, der) != Color.WHITE) {
                                aba = aba+1;
                                Lineas.add(new Point(aba, der));
                                bitmap.setPixel(aba, der, Color.WHITE);
                            } else {
                                aba = aba+1;
                                der = der+1;
                                Lineas.add(new Point(aba, der));
                                bitmap.setPixel(aba, der, Color.WHITE);
                                camino += "W";
                            }
                        }                        
                    }
                    
                    Lineas.add(new Point(j, i));
                    int izq = i;
                    aba = j;
                    Lineas.add(new Point(j, i));
                    while (izq > 0 && aba < iMaxC -1 && (bitmap.getPixel(aba, izq - 1) != Color.WHITE 
                    		|| bitmap.getPixel(1+aba, izq) != Color.WHITE && bitmap.getPixel(1+aba, izq+1) != Color.WHITE)) {
                        if (bitmap.getPixel(aba, 1+izq) != Color.WHITE) {
                            izq = izq-1;
                            Lineas.add(new Point(aba, izq));
                            bitmap.setPixel(aba, izq, Color.WHITE);
                            camino += "I";
                        } else {
                            if (bitmap.getPixel(1+aba, izq) != Color.WHITE) {
                                aba = aba+1;
                                Lineas.add(new Point(aba, izq));
                                bitmap.setPixel(aba, izq, Color.WHITE);
                            } else {
                                aba = aba+1;
                                izq = izq-1;
                                Lineas.add(new Point(aba, izq));
                                bitmap.setPixel(aba, izq, Color.WHITE);
                                camino += "X";
                            }
                        }
                        camino += "I";
                    }
                }
            }
        }
        return Lineas;
	}
}

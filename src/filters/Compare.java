package filters;

import java.util.ArrayList;

import android.graphics.Point;

public class Compare {
	
	public Compare() {
	}
	
	public static double compare(ArrayList<Point> pParametro, String pS1, ArrayList<Point> pFactor, String pS2) {
        // Verificamos datos a la misma escala
        double[] Pend1 = new double[pParametro.size()/2];
        double[] Pend2 = new double[pFactor.size()/2];
        
        for (int i = 0; i < pParametro.size()/2; i++) {
            double den = (pParametro.get(i).x - pParametro.get(i+1).x);
            if (den==0)
                den=0.01;
            Pend1[i] = (pParametro.get(i).y - pParametro.get(i+1).y)/den;
            i +=1;
        }
        
        for (int i = 0; i < pFactor.size()/2; i++) {
            double den = (pFactor.get(i).x - pFactor.get(i+1).x);
            if (den==0)
                den=0.01;
            Pend2[i] = (pFactor.get(i).y - pFactor.get(i+1).y)/den;
            i +=1;
        }
        
        double[] ta1 = new double[pParametro.size()/2];
        double[] ta2 = new double[pFactor.size()/2];
        for (int i = 0; i < pParametro.size()/2; i++) {
            //ta1[i] = pParametro.get(i).distance(pParametro.get(i+1));
        	ta1[i] = distance(pFactor.get(i), pFactor.get(i+1));
            i++;
        }
        
        for (int i = 0; i < pFactor.size()/2; i++) {
        	//ta2[i] = pFactor.get(i).distance(pFactor.get(i+1));
        	ta2[i] = distance(pFactor.get(i), pFactor.get(i+1));
            i++;
        }
        
        int Aciertos = 0;
        int Total = 0;
        double s1 = 0, s2 = 0;
        
        for (int i = 0; i < Pend1.length; i++) {
            for (int j = 0; j <Pend2.length ; j++) {
                if(Pend1[i]>0 && Pend2[j]>0&& Pend2[j]<0&&Math.abs(ta1[i]-ta2[j])<20)
                    Aciertos = Aciertos + 1;
                if(Pend1[i]<0 && Pend2[j]<0&& Pend2[j]<0&&Math.abs(ta1[i]-ta2[j])<20)
                    Aciertos = Aciertos + 1;
                if(ta1[i]==ta2[j])
                    Aciertos += 1;
                s1 = ta1[i]+s1;
                s2 = ta2[j]+s2;
                Total += 1;                
            }            
        }
        
        for (int i = 0; i < ta1.length; i++) {
            for (int j = 0; j <ta2.length ; j++) {
                if(Pend1[i]>0 && Pend2[j]>0)
                    Aciertos = Aciertos + 1;
                if(Pend1[i]<0 && Pend2[j]<0)
                    Aciertos = Aciertos + 1;
                if(ta1[i]==ta2[j]||Math.abs(s2-s1)<20)
                    Aciertos += 1;
                Total += 1;                
            }            
        }
        
        // Verificamos datos a diferente escala
        int i = 0;
        String Aux;        
        while(i < pS1.length()-1) {
            if(pS1.charAt(i)==pS1.charAt(i+1)&&i+2<pS1.length()) {
                Aux = pS1.substring(0, i)+pS1.substring(i+1);
                pS1 = Aux;
            } else if(pS1.charAt(i)==pS1.charAt(i+1)) {
                    Aux = pS1.substring(0, i+1);
                    pS1 = Aux;
                } else
                    i += 1;
        }
        
        i = 0;
        
        while(i<pS2.length()-1) {
            if(pS2.charAt(i) == pS2.charAt(i+1)&&i+2<pS2.length()) {
                Aux = pS2.substring(0, i)+pS2.substring(i+1);
                pS2 = Aux;
            } else if(pS2.charAt(i) == pS2.charAt(i+1)) {
                    Aux = pS2.substring(0, i+1);
                    pS2 = Aux;
                } else
                    i += 1;
        }
        
        if(pS1.length() > pS2.length()) {
            Aux = pS1;
            pS1 = pS2;
            pS2 = Aux;
        }
        
        double Acumulador2 = 0;
        i = 1;        
        for (int j = 0; j < pS2.length()&&i*(pS2.length()/pS1.length())<pS1.length(); j++) {
            if (pS1.charAt(i*(pS2.length()/pS1.length())) == pS2.charAt(j)) {
                // buscamos el siguiente punto                
                Acumulador2 = Acumulador2 + 1;
                i++;
            }            
        }
        
        double val2 = Acumulador2 * 100 / (pS1.length());
        double val = Aciertos*100/Total;
        if(val > val2 && val > 50) {
            return val;
        } else {
            if(val2>50)
                return val2;
            else
                return val;
        }            
    }
	
	public static double distance(Point p1, Point p2) {
		return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
	}
}

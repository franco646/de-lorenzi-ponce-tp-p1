package juego;

public class Colisiones {
	
	//clase para colisionesss
	
	public static boolean checkRect(double x , double y ,double width , double height,
			double x2 , double y2 ,double width2 , double height2,Personaje pj
			
			) {
		
		double distanciaX = x2 - x;// calculamos la distancia del eje X
		double distanciaY = y2 - y;// calculamos la distancia del eje Y
		
		double distancia = Math.sqrt(distanciaX * distanciaX + distanciaY * distanciaY);
		// Formula de distancia euclidiana
		
		
		// Calcular el tamaño mínimo para la colision
		double mindistancia = Math.min(width, height) / 2 
				+ Math.min(width2, height2) / 2;
		
		if(!pj.is_colisionando) {//esto es para que solo colisione una vez
			pj.is_colisionando = true;
			return distancia <= mindistancia;//entonces si la variable distancia es menor o igual 
			//a lo minimo de distancia que pueden tener entonces es TRUE
		}
		else if(distancia > mindistancia) {
			pj.is_colisionando = false;//si sale el pj sale de la colision entonces ya 
										//esta disponible para detectar la colision devuelta
		}
		return false;
	}	
	
	//is_on_floor detecta si el objeto esta tocando el suelo :::
	
	public static boolean is_on_floor(double x , double y ,double width , double height,
			double x2 , double y2 ,double width2 , double height2) {
		
		return x > x2 - width2 / 2
				&& x < x2 + width2 / 2
				&& Math.abs(y - (y2 - height2 / 2)) <= 5;
		
	}
	
}

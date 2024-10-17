package juego;

public class Colisiones {
	
	//clase para colisionesss
	
	public static boolean checkRect(Personaje pj,Enemigo ene
			) {
		
		double x =pj.x;
		double y = pj.y; 
		double width = pj.width;
		double height = pj.height;
		double x2 = ene.x;
		double y2 = ene.y;
		double width2 = ene.width;
		double height2 = ene.height;
		
		double distanciaX = x2 - x;// calculamos la distancia del eje X
		double distanciaY = y2 - y;// calculamos la distancia del eje Y
		
		double distancia = Math.sqrt(distanciaX * distanciaX + distanciaY * distanciaY);
		// Formula de distancia euclidiana
		
		
		// Calcular el tamaño mínimo para la colision
		double mindistancia = Math.min(width, height) / 2 
				+ Math.min(width2, height2) / 2;
		
		
		return distancia <= mindistancia;
		
	}	
	
	//is_on_floor detecta si el objeto esta tocando el suelo :::
	
	public static boolean is_on_floor(Personaje pj , Isla isla) {
		
		double x =pj.x;
		double y = pj.y;
		double x2 = isla.x;
		double y2 = isla.y;
		double width2 = isla.width;
		double height2 = isla.height;
		
		return x > x2 - width2 / 2
				&& x < x2 + width2 / 2
				&& Math.abs(y - (y2 - height2 / 2)) <= 5;
		
	}
public static boolean is_on_floor(Enemigo ene , Isla isla) {
		
		double x =ene.x;
		double y = ene.y;
		double x2 = isla.x;
		double y2 = isla.y;
		double width2 = isla.width;
		double height2 = isla.height;
		
		return x > x2 - width2 / 2
				&& x < x2 + width2 / 2
				&& Math.abs(y - (y2 - height2 / 2)) <= 5;
		
	}
	
}

package juego;

<<<<<<< Upstream, based on origin/master
public class Colisiones {
	
	//clase para colisionesss
	
	public static boolean checkRect(Personaje pj,Gnomo gno
			) {
		
		double x =pj.x;
		double y = pj.y; 
		double width = pj.width;
		double height = pj.height;
		double x2 = gno.x;
		double y2 = gno.y;
		double width2 = gno.width;
		double height2 = gno.height;
		
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
public static boolean is_on_floor(Gnomo gno , Isla isla) {
		
		double x =gno.x;
		double y = gno.y;
		double x2 = isla.x;
		double y2 = isla.y;
		double width2 = isla.width;
		double height2 = isla.height;
		
		return x > x2 - width2 / 2
				&& x < x2 + width2 / 2
				&& Math.abs(y - (y2 - height2 / 2)) <= 5;
		
	}
	
=======
import java.awt.Rectangle;

public class Colisiones {

	// clase para colisionesss

	public static boolean colisionan(Rectangle caja1, Rectangle caja2) {
		int bordeDerCaja1 = caja1.x + caja1.width / 2;
		int bordeIzqCaja1 = caja1.x - caja1.width / 2;
		int bordeSupCaja1 = caja1.y + caja1.height / 2;
		int bordeInfCaja1 = caja1.y - caja1.height / 2;

		int bordeDerCaja2 = caja2.x + caja2.width / 2;
		int bordeIzqCaja2 = caja2.x - caja2.width / 2;
		int bordeSupCaja2 = caja2.y + caja2.height / 2;
		int bordeInfCaja2 = caja2.y - caja2.height / 2;

		boolean colizionHorizontal = bordeDerCaja1 > bordeIzqCaja2 && bordeIzqCaja1 < bordeDerCaja2;
		boolean colisionVertical = bordeSupCaja1 > bordeInfCaja2 && bordeInfCaja1 < bordeSupCaja2;

		return colizionHorizontal && colisionVertical;

	}

	// is_on_floor detecta si el objeto esta tocando el suelo :::

	public static boolean estaSobreIsla(Rectangle caja, Isla isla) {
		int bordeInfCaja = caja.y + caja.height / 2;

		double bordeDerIsla = isla.x + isla.ancho / 2;
		double bordeIzqIsla = isla.x - isla.ancho / 2;
		double bordeSupIsla = isla.y - isla.alto / 4; // Es un poco mas abajo del borde superior para que lo que esté
														// encima no parezca flotando

		boolean colisionHorizontal = caja.x > bordeIzqIsla && caja.x < bordeDerIsla;
		boolean colisionVertical = Math.abs(bordeInfCaja - bordeSupIsla) <= 5;

		return colisionHorizontal && colisionVertical;

	}

>>>>>>> 16185b6 Corrige gravedad y colisiones
}

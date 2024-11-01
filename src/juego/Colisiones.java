package juego;

import java.awt.Rectangle;

public class Colisiones {
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

	public static boolean estaSobreIsla(Rectangle caja, Isla isla) {
		int bordeInfCaja = caja.y + caja.height / 2;

		double bordeDerIsla = isla.getX() + isla.getAncho() / 2;
		double bordeIzqIsla = isla.getX() - isla.getAncho() / 2;
		double bordeSupIsla = isla.getY() - isla.getAlto() / 4; // Es un poco mas abajo del borde superior para que lo
																// que esté
																// encima no parezca flotando

		boolean colisionHorizontal = caja.x > bordeIzqIsla && caja.x < bordeDerIsla;
		boolean colisionVertical = Math.abs(bordeInfCaja - bordeSupIsla) <= 5;

		return colisionHorizontal && colisionVertical;

	}

	public static boolean colisionaConInferiorIsla(Rectangle caja, Isla isla) {
		int bordeSupCaja = caja.y - caja.height / 2;

		double bordeDerIsla = isla.getX() + isla.getAncho() / 2;
		double bordeIzqIsla = isla.getX() - isla.getAncho() / 2;
		double bordeSupIsla = isla.getY() - isla.getAlto() / 4;

		boolean colisionHorizontal = caja.x > bordeIzqIsla && caja.x < bordeDerIsla;
		boolean colisionVertical = Math.abs(bordeSupCaja - bordeSupIsla) <= 5;

		return colisionHorizontal && colisionVertical;

	}

}

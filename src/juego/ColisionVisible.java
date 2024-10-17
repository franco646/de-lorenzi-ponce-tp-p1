package juego;

import java.awt.Color;

import entorno.Entorno;

public class ColisionVisible {

	// esta clase es solo para que sea mas faciles las PRUEBAS

	// colores con transparencia
	private static Color COLOR_RED = new Color(255, 0, 0, 70);
	private static Color COLOR_GREEN = new Color(0, 255, 0, 70);
	private static Color COLOR_BLUE = new Color(255, 0, 255, 70);

	public static void dibujar(Entorno e, Personaje pj) {
		Color colorb = ColisionVisible.COLOR_BLUE;
		e.dibujarRectangulo(pj.x, pj.y, pj.ancho, pj.alto, 0, colorb);
	}

	public static void dibujar(Entorno e, Gnomo gno) {
		Color colorb = ColisionVisible.COLOR_GREEN;
		e.dibujarRectangulo(gno.x, gno.y, gno.ancho, gno.alto, 0, colorb);
	}

	public static void dibujar(Entorno e, Isla i) {
		Color colorb = ColisionVisible.COLOR_RED;
		e.dibujarRectangulo(i.x, i.y, i.ancho, i.alto, 0, colorb);
	}

}

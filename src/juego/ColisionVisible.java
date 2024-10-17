package juego;

import java.awt.Color;

import entorno.Entorno;

public class ColisionVisible {
	
	
	//esta clase es solo para que sea mas faciles las PRUEBAS

	
	//colores con transparencia
	private static Color COLOR_RED = new Color(255, 0, 0, 70);
	private static Color COLOR_GREEN = new Color(0, 255, 0, 70);
	private static Color COLOR_BLUE = new Color(255, 0, 255, 70);
	
	
	public static void dibujar(Entorno e,Personaje pj) {
		Color colorb = ColisionVisible.COLOR_BLUE;
		e.dibujarRectangulo(pj.x, pj.y, pj.width, pj.height, 0, colorb);
	}
	
	public static void dibujar(Entorno e,Enemigo ene) {
		Color colorb = ColisionVisible.COLOR_GREEN;
		e.dibujarRectangulo(ene.x, ene.y, ene.width, ene.height, 0, colorb);
	}
	
	public static void dibujar(Entorno e,Isla i) {
		Color colorb = ColisionVisible.COLOR_RED;
		e.dibujarRectangulo(i.x, i.y, i.width, i.height, 0, colorb);
	}
	
	
}

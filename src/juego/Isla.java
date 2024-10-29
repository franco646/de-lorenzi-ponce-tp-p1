package juego;

import java.awt.Image;

import entorno.Entorno;

public class Isla {
	private double x;
	private double y;

	private static double ESCALA = 0.12;

	private static Image IMAGEN = entorno.Herramientas.cargarImagen("imagenes/isla/pngegg.png");

	private double ancho = IMAGEN.getWidth(null) * ESCALA;
	private double alto = IMAGEN.getHeight(null) * ESCALA;

	public Isla(double x, double y) {
		this.x = x;
		this.y = y;

	}

	public void dibujar(Entorno e) {
		e.dibujarImagen(IMAGEN, this.x, this.y, 0, ESCALA);
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getAncho() {
		return ancho;
	}

	public void setAncho(double ancho) {
		this.ancho = ancho;
	}

	public double getAlto() {
		return alto;
	}

	public void setAlto(double alto) {
		this.alto = alto;
	}

}

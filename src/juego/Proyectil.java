package juego;

import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;

public class Proyectil {
	double x;
	double y;
	double ancho;
	double alto;
	double escala;

	boolean derecha;

	// imagen derecha

	private final Image IMAGEN_DER = entorno.Herramientas.cargarImagen("imagenes/proyectil/proyectil-der.png");
	private final Image IMAGEN_IZQ = entorno.Herramientas.cargarImagen("imagenes/proyectil/proyectil-izq.png");

	private Image imagen = IMAGEN_DER;

	private final int VELOCIDAD = 10;
	private final double ESCALA = 0.05;

	public Proyectil(double x, double y, boolean derecha) {
		this.x = derecha ? x + 40 : x - 40;
		this.y = y;
		this.derecha = derecha;

		this.escala = 0.05;

		this.ancho = this.imagen.getWidth(null) * ESCALA;
		this.alto = this.imagen.getHeight(null) * ESCALA;
	}

	public void dibujar(Entorno e) {
		e.dibujarImagen(this.imagen, this.x, this.y, 0, this.escala);
	}

	public void mover() {
		if (derecha) {
			this.imagen = IMAGEN_DER;
			this.x = this.x + VELOCIDAD;
		} else {
			this.imagen = IMAGEN_IZQ;
			this.x = this.x - VELOCIDAD;
		}
	}

	public Rectangle obtenerDimensiones() {
		int x = (int) this.x;
		int y = (int) this.y;
		int ancho = (int) this.ancho;
		int alto = (int) this.alto;

		return new Rectangle(x, y, ancho, alto);
	}

}

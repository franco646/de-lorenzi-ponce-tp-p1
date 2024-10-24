package juego;

import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;

public class Proyectil {
	double x;
	double y;

	boolean derecha;

	public static final int VELOCIDAD = 12;
	private final double LIMITE_ESCALA = 0.14;
	private final int POSICION_INICIAL = 40;

	private final Image imagen = entorno.Herramientas.cargarImagen("imagenes/bola/fireball.png");

	double escala = 0.05;

	double ancho = this.imagen.getWidth(null) * this.escala;
	double alto = this.imagen.getHeight(null) * this.escala;
	public int tiempoDeCreacion;
	public boolean esVisible = true;

	public Proyectil(double x, double y, boolean derecha, Entorno entorno) {

		if (derecha) {
			this.x = x + this.POSICION_INICIAL;
		} else {
			this.x = x - this.POSICION_INICIAL;
		}

		this.y = y;

		this.derecha = derecha;
		this.tiempoDeCreacion = entorno.tiempo();
	}

	public void dibujar(Entorno e) {
		if (this.esVisible) {
			e.dibujarImagen(this.imagen, this.x, this.y, 0, this.escala);
		}
	}

	public void mover() {
		if (derecha) {
			this.x = this.x + VELOCIDAD;
		} else {
			this.x = this.x - VELOCIDAD;
		}
	}

	public void volverInvisible() {
		this.esVisible = false;
	}

	public void scalaSumar() {
		if (this.escala <= this.LIMITE_ESCALA) {
			this.escala += 0.002;

			this.ancho = this.imagen.getWidth(null) * this.escala;
			this.alto = this.imagen.getHeight(null) * this.escala;
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

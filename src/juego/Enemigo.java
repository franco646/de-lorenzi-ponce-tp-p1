package juego;

import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;

public class Enemigo {
	double x;
	double y;
	Image imagen;
	double escala;
	double ancho;
	double alto;
	// Image[] imagenesDer;
	Image[] imagenesIzq;
	int frame;
	long tiempoAnterior;

	public static int LAYER = 2; /// Capa de COLISION
	Boolean derecha = true;

	Boolean enisla = true;

	public Enemigo(double x, double y) {
		this.x = x;
		this.y = y;
		this.escala = 2;
		this.imagen = entorno.Herramientas.cargarImagen("imagenes/enemigo/izquierda/Run1_izq.png");
		this.ancho = imagen.getWidth(null) * this.escala;
		this.alto = imagen.getHeight(null) * this.escala;
		this.frame = 0;

		this.imagenesIzq = new Image[] { entorno.Herramientas.cargarImagen("imagenes/enemigo/izquierda/Run1_izq.png"),
				entorno.Herramientas.cargarImagen("imagenes/enemigo/izquierda/Run2_izq.png"),
				entorno.Herramientas.cargarImagen("imagenes/enemigo/izquierda/Run3_izq.png") };

		this.tiempoAnterior = System.currentTimeMillis();

	}

	public void dibujar(Entorno e) {
		e.dibujarImagen(this.imagen, this.x, this.y, 0, this.escala);
	}

	public void caer() {
		this.imagen = entorno.Herramientas.cargarImagen("imagenes/enemigo/izquierda/Run2_izq.png");
		this.y = this.y + 3;
		this.enisla = false;
		if (this.y >= 768) {
			this.x = 660;
			this.y = 0;
			if (this.derecha) {
				this.derecha = false;
			} else {
				this.derecha = true;
			}

		}
	}

	public void mover() {

		if (this.x >= 1366) {
			this.x = 0;
		} else if (this.x <= 0) {
			this.x = 1366;
		}

		if (this.derecha) {
			long tiempoActual = System.currentTimeMillis();
			if (tiempoActual - this.tiempoAnterior > 150) {
				this.tiempoAnterior = tiempoActual;
				this.imagen = this.imagenesIzq[frame];
				this.frame = (frame + 1) % this.imagenesIzq.length;
			}
			this.x = this.x + 1;
		} else if (this.derecha == false) {
			long tiempoActual = System.currentTimeMillis();
			if (tiempoActual - this.tiempoAnterior > 150) {
				this.tiempoAnterior = tiempoActual;
				this.imagen = this.imagenesIzq[frame];
				this.frame = (frame + 1) % this.imagenesIzq.length;
			}
			this.x = this.x - 1;
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

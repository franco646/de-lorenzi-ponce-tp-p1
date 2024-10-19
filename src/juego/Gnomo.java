package juego;

import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;

public class Gnomo {

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

	boolean habitacion_direccion = true;

	Boolean enisla = true;

	public boolean is_colisionando = false;// para saber si esta colisionando o no
	// con esto se puede colisionar una vez por contacto

	public Gnomo(double x, double y) {

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

		ColisionVisible.dibujar(e, this);
		// PARA HACER VISIBLE EL TAMAÑO DE LA COLISION PARA LAS PRUEBAS

	}

	private boolean aleatorioNumero() {
		int random = (int) (Math.random() * 2);
		boolean boleano;
		if (random == 0) {
			boleano = false;
		} else {
			boleano = true;
		}

		return boleano;

	}

	public void caer() {

		if (this.habitacion_direccion) {
			this.habitacion_direccion = false;
			this.derecha = this.aleatorioNumero();

		}

		this.imagen = entorno.Herramientas.cargarImagen("imagenes/enemigo/izquierda/Run2_izq.png");
		this.y = this.y + 3;
		this.enisla = false; 
	}
	public void mover() {

		if (this.x >= 1366) {
			this.x = 0;
		} else if (this.x <= 0) {
			this.x = 1366;
		}

		if (this.habitacion_direccion) {
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

	}

	public Rectangle obtenerDimensiones() {
		int x = (int) this.x;
		int y = (int) this.y;
		int ancho = (int) this.ancho;
		int alto = (int) this.alto;

		return new Rectangle(x, y, ancho, alto);
	}

}

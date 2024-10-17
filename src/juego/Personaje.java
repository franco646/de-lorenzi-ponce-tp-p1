package juego;

import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;

public class Personaje {
	double x;
	double y;
	Image imagen;
	double escala;
	double ancho;
	double alto;

	public boolean enIsla;

	private int frame;
	private long tiempoAnterior;

	private Image[] imagenesDer;
	private Image[] imagenesIzq;
	private Image imagenQuietoDer;
	private Image imagenQuietoIzq;
	private Image imagenCaerDer;
	private Image imagenCaerIzq;
	private Image imagenSaltarDer;
	private Image imagenSaltarIzq;

	private Boolean derecha = true; /// comprobar si esta a la derecha == true

	private static final int ALTURA_DEL_SALTO = 170; // altura en pixeles del salto
	double alturaAlComenzarSalto; // coordenada en el eje y del personaje al comenzar el salto
	public boolean isJumping = false;

	private static final double VELOCIDAD_INICIAL_SALTO = 8;
	private static final double VELOCIDAD_FINAL_SALTO = 3;

	private static final double VELOCIDAD_INICIAL_CAIDA = 3;
	private static final double VELOCIDAD_FINAL_CAIDA = 8;

	private static final double GRAVEDAD = 0.15; // Cantidad de pixeles que se aumenta/disminuye por cada frame durante
													// salto/caida

	private double velocidadSalto = VELOCIDAD_INICIAL_SALTO;
	private double velocidadCaida = VELOCIDAD_INICIAL_CAIDA;

	public Personaje(double x, double y) {
		this.x = x;
		this.y = y;
		this.escala = 0.5;
		this.imagen = entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/quieto.png");
		this.ancho = imagen.getWidth(null) * this.escala;
		this.alto = imagen.getHeight(null) * this.escala;
		this.frame = 0;

		// Carga las imagenes de los distintos movimientos del personaje

		this.imagenesDer = new Image[] { entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/correr-2.png"),
				entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/correr-3.png"),
				entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/correr-4.png"),
				entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/correr-6.png"),
				entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/correr-5.png"),
				entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/correr-7.png"),
				entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/correr-8.png"),
				entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/correr-9.png"), };

		this.imagenesIzq = new Image[] { entorno.Herramientas.cargarImagen("imagenes/personaje/izquierda/correr-1.png"),
				entorno.Herramientas.cargarImagen("imagenes/personaje/izquierda/correr-2.png"),
				entorno.Herramientas.cargarImagen("imagenes/personaje/izquierda/correr-3.png"),
				entorno.Herramientas.cargarImagen("imagenes/personaje/izquierda/correr-4.png"),
				entorno.Herramientas.cargarImagen("imagenes/personaje/izquierda/correr-5.png"),
				entorno.Herramientas.cargarImagen("imagenes/personaje/izquierda/correr-6.png"),
				entorno.Herramientas.cargarImagen("imagenes/personaje/izquierda/correr-7.png"),
				entorno.Herramientas.cargarImagen("imagenes/personaje/izquierda/correr-8.png"), };

		this.imagenQuietoDer = entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/quieto.png");
		this.imagenQuietoIzq = entorno.Herramientas.cargarImagen("imagenes/personaje/izquierda/quieto.png");
		this.imagenCaerDer = entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/caer.png");
		this.imagenCaerIzq = entorno.Herramientas.cargarImagen("imagenes/personaje/izquierda/caer_izq.png");
		this.imagenSaltarDer = entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/saltar.png");
		this.imagenSaltarIzq = entorno.Herramientas.cargarImagen("imagenes/personaje/izquierda/saltar.png");

		this.tiempoAnterior = System.currentTimeMillis();
	}

	public void dibujar(Entorno e) {
		e.dibujarImagen(this.imagen, this.x, this.y, 0, this.escala);

		ColisionVisible.dibujar(e, this);
		// PARA HACER VISIBLE EL TAMAÑO DE LA COLISION PARA LAS PRUEBAS
	}

	public void caer() {
		if (this.derecha) {
			this.imagen = this.imagenCaerDer;
		} else {
			this.imagen = this.imagenCaerIzq;
		}
		this.y = this.y + this.velocidadCaida;

		this.velocidadCaida = this.velocidadCaida > VELOCIDAD_FINAL_CAIDA ? VELOCIDAD_FINAL_CAIDA
				: this.velocidadCaida + GRAVEDAD;
		if (this.y >= 768) {
			this.x = 660;
			this.y = 0;
		}
	}

	public void resetVelocidadCaida() {
		this.velocidadCaida = VELOCIDAD_INICIAL_CAIDA;
	}

	public void moverDer() {
		this.derecha = true;
		if (this.x < 1366 - this.ancho / 2) {
			this.animar("derecha");
			this.x = this.x + 3;
		} else {
			this.quieto();
		}
	}

	public void moverIzq() {
		this.derecha = false;
		if (this.x > 0 + this.ancho / 2) {
			this.animar("izquierda");
			this.x = this.x - 3;
		} else {
			this.quieto();
		}
	}

	public void animar(String direccion) {
		long tiempoActual = System.currentTimeMillis();
		if (tiempoActual - this.tiempoAnterior > 150 && !this.isJumping && this.enIsla) {
			this.tiempoAnterior = tiempoActual;
			this.imagen = direccion == "izquierda" ? this.imagenesIzq[frame] : this.imagenesDer[frame];
			this.frame = (frame + 1) % this.imagenesIzq.length;
		}
	}

	public void quieto() {
		if (this.derecha) {
			this.imagen = this.imagenQuietoDer;
		} else {
			this.imagen = this.imagenQuietoIzq;
		}
	}

	public void comenzarSalto() {
		if (this.derecha) {
			this.imagen = this.imagenSaltarDer;
		} else {
			this.imagen = this.imagenSaltarIzq;
		}
		this.isJumping = true;
		this.alturaAlComenzarSalto = this.y;
	}

	public void subir() {
		this.y = this.y - this.velocidadSalto;
		this.velocidadSalto = this.velocidadSalto < VELOCIDAD_FINAL_SALTO ? VELOCIDAD_FINAL_SALTO
				: this.velocidadSalto - GRAVEDAD; // La velocidad inicial del salto ira disminuyendo por efecto de la
													// gravedad
		if (this.alturaAlComenzarSalto > this.y + ALTURA_DEL_SALTO) {
			this.isJumping = false;
			this.velocidadSalto = VELOCIDAD_INICIAL_SALTO;
			this.caer();
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

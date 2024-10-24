package juego;

import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;

public class Personaje {
	private double x;
	private double y;

	private static final int ALTURA_DEL_SALTO = 170; // altura en pixeles del salto
	private static final double ESCALA = 0.5;
	private static final double VELOCIDAD_INICIAL_SALTO = 8;
	private static final double VELOCIDAD_FINAL_SALTO = 3;
	private static final double VELOCIDAD_INICIAL_CAIDA = 3;
	private static final double VELOCIDAD_FINAL_CAIDA = 8;
	private static final double GRAVEDAD = 0.15; // Cantidad de pixeles que se aumenta/disminuye por cada frame durante
	// salto/caida

	private static final Image[] IMAGENES_DER = new Image[] {
			entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/correr-2.png"),
			entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/correr-3.png"),
			entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/correr-4.png"),
			entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/correr-6.png"),
			entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/correr-5.png"),
			entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/correr-7.png"),
			entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/correr-8.png"),
			entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/correr-9.png"), };

	private static final Image[] IMAGENES_IZQ = new Image[] {
			entorno.Herramientas.cargarImagen("imagenes/personaje/izquierda/correr-1.png"),
			entorno.Herramientas.cargarImagen("imagenes/personaje/izquierda/correr-2.png"),
			entorno.Herramientas.cargarImagen("imagenes/personaje/izquierda/correr-3.png"),
			entorno.Herramientas.cargarImagen("imagenes/personaje/izquierda/correr-4.png"),
			entorno.Herramientas.cargarImagen("imagenes/personaje/izquierda/correr-5.png"),
			entorno.Herramientas.cargarImagen("imagenes/personaje/izquierda/correr-6.png"),
			entorno.Herramientas.cargarImagen("imagenes/personaje/izquierda/correr-7.png"),
			entorno.Herramientas.cargarImagen("imagenes/personaje/izquierda/correr-8.png"), };

	private static final Image IMAGEN_QUIETO_DER = entorno.Herramientas
			.cargarImagen("imagenes/personaje/derecha/quieto.png");
	private static final Image IMAGEN_QUIETO_IZQ = entorno.Herramientas
			.cargarImagen("imagenes/personaje/izquierda/quieto.png");
	private static final Image IMAGEN_CAER_DER = entorno.Herramientas
			.cargarImagen("imagenes/personaje/derecha/caer.png");
	private static final Image IMAGEN_CAER_IZQ = entorno.Herramientas
			.cargarImagen("imagenes/personaje/izquierda/caer_izq.png");
	private static final Image IMAGEN_SALTAR_DER = entorno.Herramientas
			.cargarImagen("imagenes/personaje/derecha/saltar.png");
	private static final Image IMAGEN_SALTAR_IZQ = entorno.Herramientas
			.cargarImagen("imagenes/personaje/izquierda/saltar.png");

	private Image imagen = IMAGEN_QUIETO_DER;

	private double ancho = imagen.getWidth(null) * ESCALA;
	private double alto = imagen.getHeight(null) * ESCALA;

	private double velocidadSalto = VELOCIDAD_INICIAL_SALTO;
	private double velocidadCaida = VELOCIDAD_INICIAL_CAIDA;

	private boolean enIsla;

	private int frame = 0;
	private long tiempoAnterior = 0;

	private Boolean derecha = true; // direccion del personaje

	private double alturaAlComenzarSalto; // coordenada en el eje y del personaje al comenzar el salto
	private boolean estaSaltando = false;

	private boolean estaVivo = true;
	private int anguloImagen = 0;

	public Personaje(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public boolean getEnIsla() {
		return this.enIsla;
	}

	public void setEnIsla(boolean enIsla) {
		this.enIsla = enIsla;
	}

	public boolean getEstaSaltando() {
		return this.estaSaltando;
	}

	public boolean getEstaVivo() {
		return this.estaVivo;
	}

	public boolean getDerecha() {
		return this.derecha;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(this.imagen, this.x, this.y, this.anguloImagen, ESCALA);
	}

	public void caer() {
		if (this.derecha) {
			this.imagen = IMAGEN_CAER_DER;
		} else {
			this.imagen = IMAGEN_CAER_IZQ;
		}
		this.y = this.y + this.velocidadCaida;

		this.velocidadCaida = this.velocidadCaida > VELOCIDAD_FINAL_CAIDA ? VELOCIDAD_FINAL_CAIDA
				: this.velocidadCaida + GRAVEDAD;
	}

	public void resetVelocidadCaida() {
		this.velocidadCaida = VELOCIDAD_INICIAL_CAIDA;
	}

	public void moverDer(Entorno entorno) {
		this.derecha = true;
		if (this.x < 1366 - this.ancho / 2) {
			this.animar(entorno);
			this.x = this.x + 3;
		} else {
			this.quieto();
		}
	}

	public void moverIzq(Entorno entorno) {
		this.derecha = false;
		if (this.x > 0 + this.ancho / 2) {
			this.animar(entorno);
			this.x = this.x - 3;
		} else {
			this.quieto();
		}
	}

	public void animar(Entorno entorno) {
		long tiempoActual = entorno.tiempo();
		if (tiempoActual - this.tiempoAnterior > 150 && !this.estaSaltando && this.enIsla) {
			this.tiempoAnterior = tiempoActual;
			this.imagen = this.derecha ? IMAGENES_DER[frame] : IMAGENES_IZQ[frame];
			this.frame = (frame + 1) % IMAGENES_IZQ.length;
		}
	}

	public void quieto() {
		if (this.derecha) {
			this.imagen = IMAGEN_QUIETO_DER;
		} else {
			this.imagen = IMAGEN_QUIETO_IZQ;
		}
	}

	public void morir() {
		this.estaVivo = false;
		this.imagen = IMAGEN_CAER_DER;
		this.anguloImagen = 180;
	}

	public void comenzarSalto() {
		if (this.derecha) {
			this.imagen = IMAGEN_SALTAR_DER;
		} else {
			this.imagen = IMAGEN_SALTAR_IZQ;
		}
		this.estaSaltando = true;
		this.alturaAlComenzarSalto = this.y;
	}

	public void subir() {
		this.y = this.y - this.velocidadSalto;
		this.velocidadSalto = this.velocidadSalto < VELOCIDAD_FINAL_SALTO ? VELOCIDAD_FINAL_SALTO
				: this.velocidadSalto - GRAVEDAD; // La velocidad inicial del salto ira disminuyendo por efecto de la
													// gravedad
		if (this.alturaAlComenzarSalto > this.y + ALTURA_DEL_SALTO) {
			this.estaSaltando = false;
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

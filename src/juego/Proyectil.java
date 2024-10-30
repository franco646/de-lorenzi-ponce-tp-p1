package juego;

import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;

public class Proyectil {
	private double x;
	private double y;
	private boolean derecha; // la dirección del proyectil

	private boolean esVisible = true;

	private double escala = 0.05;
	private double ancho;
	private double alto;

	private static final int VELOCIDAD = 12;
	private static final double LIMITE_ESCALA = 0.1;

	private static final Image IMAGEN_DER = entorno.Herramientas.cargarImagen("imagenes/proyectil/proyectil-der.png");
	private static final Image IMAGEN_IZQ = entorno.Herramientas.cargarImagen("imagenes/proyectil/proyectil-izq.png");

	private Image imagen = IMAGEN_DER;

	public Proyectil(double x, double y, boolean derecha) {
		this.x = derecha ? x + 20 : x - 20; // suma/resta 20px para que el proyectil spawnee delante del personaje y no
											// sobre él
		this.y = y;
		this.derecha = derecha; // es la direccion hacia donde se mueve
		this.imagen = derecha ? IMAGEN_DER : IMAGEN_IZQ;
		this.ancho = this.imagen.getWidth(null) * this.escala;
		this.alto = this.imagen.getHeight(null) * this.escala;
	}

	public void dibujar(Entorno entorno) {
		if (this.esVisible) {
			entorno.dibujarImagen(this.imagen, this.x, this.y, 0, this.escala);
		}
	}

	public double getX() {
		return this.x;
	}

	public boolean getEsVisible() {
		return this.esVisible;
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
		if (this.escala <= LIMITE_ESCALA) {
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

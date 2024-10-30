package juego;

import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;

public class Enemigo {

	private double x;
	private double y;

	private Image imagen = entorno.Herramientas.cargarImagen("imagenes/enemigo/real/enemigoGeneric.png");

	private static double ESCALA = 0.1;
	private static double VELOCIDAD = 0.7;

	private double ancho = imagen.getWidth(null) * Enemigo.ESCALA;
	private double alto = imagen.getHeight(null) * Enemigo.ESCALA;

	private Boolean derecha = true;

	private boolean puedeMoverse = false;

	private Boolean enisla = true;

	private boolean colisionando = false;

	private boolean lanzoBomba = false;

	public Enemigo(double x, double y) {

		this.x = x;
		this.y = y;

		this.derecha = this.booleanoAleatorio();

	}

	public boolean getLanzoBomba() {
		return this.lanzoBomba;
	}

	public void setLanzoBomba(boolean lanzoBomba) {
		this.lanzoBomba = lanzoBomba;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	private boolean booleanoAleatorio() {
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
		this.y = this.y + 2.5;

	}

	public void mover() {
		if (!this.enisla) {
			this.derecha = !this.derecha;
		}

		if (this.derecha) {

			this.x += VELOCIDAD;

		}

		else if (!this.derecha) {

			this.x -= VELOCIDAD;

		}

	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(this.imagen, this.x, this.y, 0, Enemigo.ESCALA);

	}

	public Rectangle obtenerDimensiones() {
		int x = (int) this.x;
		int y = (int) this.y;
		int ancho = (int) this.ancho;
		int alto = (int) this.alto;

		return new Rectangle(x, y, ancho, alto);
	}

	// GET Y SET :

	public boolean getPuedeMoverse() {
		return puedeMoverse;
	}

	public void setPuedeMoverse(boolean puedeMoverse) {
		this.puedeMoverse = puedeMoverse;
	}

	public Boolean getEnisla() {
		return enisla;
	}

	public void setEnisla(Boolean enisla) {
		this.enisla = enisla;
	}

	public boolean getColisionando() {
		return colisionando;
	}

	public void setColisionando(boolean is_colisionando) {
		this.colisionando = is_colisionando;
	}

}

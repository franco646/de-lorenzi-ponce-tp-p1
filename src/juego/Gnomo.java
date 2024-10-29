package juego;

import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;

public class Gnomo {

	private double x;
	private double y;

	private static Image[] IMAGENES_IZQ = new Image[] {
			entorno.Herramientas.cargarImagen("imagenes/gnomo/izquierda/Run1_izq.png"),
			entorno.Herramientas.cargarImagen("imagenes/gnomo/izquierda/Run2_izq.png"),
			entorno.Herramientas.cargarImagen("imagenes/gnomo/izquierda/Run3_izq.png") };

	private static Image[] IMAGENES_DER = new Image[] {
			entorno.Herramientas.cargarImagen("imagenes/gnomo/derecha/Run1_der.png"),
			entorno.Herramientas.cargarImagen("imagenes/gnomo/derecha/Run2_der.png"),
			entorno.Herramientas.cargarImagen("imagenes/gnomo/derecha/Run3_der.png") };

	private Image imagen = IMAGENES_DER[0];

	private static int ESCALA = 2;

	private double ancho = imagen.getWidth(null) * ESCALA;
	private double alto = imagen.getHeight(null) * ESCALA;

	private int frame = 0;
	private long tiempoAnterior;

	private Boolean derecha = true;

	private boolean habilitacionMovimiento = true;

	private Boolean enisla = false;

	public Gnomo(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(this.imagen, this.x, this.y, 0, Gnomo.ESCALA);

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
		if (this.habilitacionMovimiento) {
			this.habilitacionMovimiento = false;
			this.derecha = this.booleanoAleatorio();
		}
		this.y = this.y + 3;
	}

	public void mover(Entorno entorno) {
		if (this.habilitacionMovimiento) {
			if (this.derecha) {
				this.animar(entorno);
				this.x = this.x + 1;
			} else {
				this.animar(entorno);
				this.x = this.x - 1;
			}
		}
	}

	public void animar(Entorno entorno) {
		long tiempoActual = entorno.tiempo();
		if (tiempoActual - this.tiempoAnterior > 150) {
			this.tiempoAnterior = tiempoActual;
			this.imagen = this.derecha ? IMAGENES_DER[frame] : IMAGENES_IZQ[frame];
			this.frame = (frame + 1) % IMAGENES_IZQ.length;
		}
	}

	public Rectangle obtenerDimensiones() {
		int x = (int) this.x;
		int y = (int) this.y;
		int ancho = (int) this.ancho;
		int alto = (int) this.alto;

		return new Rectangle(x, y, ancho, alto);
	}

	// set y get

	public double getY() {
		return y;
	}

	public void setHabilitacionMovimiento(boolean habilitacionMovimiento) {
		this.habilitacionMovimiento = habilitacionMovimiento;
	}

	public Boolean getEnisla() {
		return enisla;
	}

	public void setEnisla(Boolean enisla) {
		this.enisla = enisla;
	}

}

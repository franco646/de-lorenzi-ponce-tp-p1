package juego;

import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;

public class Bomba {
	private double x;
	private double y;

	private int tiempoAlMomentoDeCreacion;
	private int tiempoAlMomentoDeExplosion;

	private Image imagen = entorno.Herramientas.cargarImagen("imagenes/bomba/bomba.png");

	private double ancho;
	private double alto;

	private double escala = 0.1;

	private boolean exploto = false;

	private static int TIEMPO_DE_DETONACION = 5 * 1000; // en milesimas de segundos
	private static double LIMITE_ESCALA = 0.3;

	public Bomba(double x, double y, Entorno entorno) {
		this.x = x;
		this.y = y;
		this.tiempoAlMomentoDeCreacion = entorno.tiempo();
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(imagen, this.x, this.y, 1, this.escala);
	}

	public void controlarExplosion(Entorno entorno) {
		int tiempoActual = entorno.tiempo();
		int tiempoDeVida = tiempoActual - this.tiempoAlMomentoDeCreacion;

		this.escala = this.escala + 0.0003;

		if (tiempoDeVida > TIEMPO_DE_DETONACION) {
			if (!this.exploto) {
				this.tiempoAlMomentoDeExplosion = entorno.tiempo();
			}
			this.explotar();
		}
	}

	public void explotar() {
		this.exploto = true;
		if (this.escala < LIMITE_ESCALA) {
			this.escala = this.escala + 0.01;
			this.ancho = this.imagen.getWidth(null) * this.escala;
			this.alto = this.imagen.getHeight(null) * this.escala;
		}
		this.imagen = entorno.Herramientas.cargarImagen("imagenes/bomba/explosion.png");
	}

	public Rectangle obtenerDimensiones() {
		int x = (int) this.x;
		int y = (int) this.y;
		int ancho = (int) this.ancho;
		int alto = (int) this.alto;

		return new Rectangle(x, y, ancho, alto);
	}

	public int getTiempoAlMomenteDeExplosion() {
		return this.tiempoAlMomentoDeExplosion;
	}

	public boolean getExploto() {
		return this.exploto;
	}
}

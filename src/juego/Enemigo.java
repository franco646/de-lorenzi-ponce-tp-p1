package juego;

import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;

public class Enemigo {

	double xInicial;
	double x;
	double y;
	
	Image imagen = entorno.Herramientas.cargarImagen("imagenes/enemigo/real/enemigoGeneric.png");
	
	private static final double ESCALA = 0.1;
	private static final double VELOCIDAD = 0.7;
	private static final double GRAVEDAD = 2.5;
	
	double ancho = imagen.getWidth(null) * Enemigo.ESCALA;
	double alto = imagen.getHeight(null) * Enemigo.ESCALA;

	Image[] imagenes;
	int frame;
	long tiempoAnterior;

	Boolean derecha = true;

	boolean puedeMoverse = false;

	Boolean enisla = true;

	public boolean is_colisionando = false;

	public Enemigo(double x, double y) {

		this.xInicial = x;

		this.x = x;
		this.y = y;
		
		this.derecha = this.numeroAleatorio();

		this.frame = 0;

		this.tiempoAnterior = System.currentTimeMillis();

	}

	private boolean numeroAleatorio() {
		int random = (int) (Math.random() * 2);
		boolean boleano;
		
		if(random == 0) {
			boleano = false;
		}
		else {
			boleano = true;
		}
		
		return boleano;
		

	}

	public void caer() {
		this.imagen = entorno.Herramientas.cargarImagen("imagenes/enemigo/real/enemigoGeneric.png");
		this.y = this.y + Enemigo.GRAVEDAD;

	}

	public void mover() {
		if (!this.enisla) {
			this.derecha = !this.derecha;
		}

		if (this.derecha) {

			this.x += Enemigo.VELOCIDAD;

		}

		else if (!this.derecha) {

			this.x -=Enemigo.VELOCIDAD;

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

}

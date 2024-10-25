package juego;

import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;

public class Gnomo {

	private double x;
	private double y;
	private Image imagen = entorno.Herramientas.cargarImagen("imagenes/enemigo/izquierda/Run1_izq.png");
	
	private Image[] imagenesIzq = new Image[] { entorno.Herramientas.cargarImagen("imagenes/enemigo/izquierda/Run1_izq.png"),
			entorno.Herramientas.cargarImagen("imagenes/enemigo/izquierda/Run2_izq.png"),
			entorno.Herramientas.cargarImagen("imagenes/enemigo/izquierda/Run3_izq.png") };
	
	private static final int ESCALA = 2;
	private static final int GRAVEDAD = 3;
	
	
	private double ancho = imagen.getWidth(null) * Gnomo.ESCALA;
	private double alto = imagen.getHeight(null) * Gnomo.ESCALA;
	
	

	

	private int frame;
	private long tiempoAnterior;

	private Boolean derecha = true;

	private boolean habilitacionMovimiento = true;

	private Boolean enisla = false;

	

	public Gnomo(double x, double y) {

		this.x = x;
		this.y = y;

		this.frame = 0;

		this.tiempoAnterior = System.currentTimeMillis();

	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(this.imagen, this.x, this.y, 0, Gnomo.ESCALA);

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

		if (this.habilitacionMovimiento) {
			this.habilitacionMovimiento = false;
			this.derecha = this.numeroAleatorio();

		}

		this.imagen = entorno.Herramientas.cargarImagen("imagenes/enemigo/izquierda/Run2_izq.png");
		this.y = this.y + Gnomo.GRAVEDAD;
		this.enisla = false;
	}

	public void mover() {

		if (this.x >= 1366) {
			this.x = 0;
		} else if (this.x <= 0) {
			this.x = 1366;
		}

		if (this.habilitacionMovimiento) {
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
	
	//set y get

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

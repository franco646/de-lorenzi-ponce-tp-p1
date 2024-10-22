package juego;

import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;

public class Proyectil {
	double x;
	double y;
	
	boolean derecha;

	private final int VELOCIDAD = 12;
	private final double LIMITE_ESCALA = 0.14;
	private final int POSICION_INICIAL = 40;

	private final Image IMAGEN_DER = entorno.Herramientas.cargarImagen("imagenes/proyectil/proyectil-der.png");
	private final Image IMAGEN_IZQ = entorno.Herramientas.cargarImagen("imagenes/proyectil/proyectil-izq.png");

	private Image imagen = IMAGEN_DER;

	double escala = 0.05;

	double ancho = this.imagen.getWidth(null) * this.escala;
	double alto = this.imagen.getHeight(null) * this.escala;

	public Proyectil(double x, double y, boolean derecha) {
		
		if(derecha) {
			this.x =x + this.POSICION_INICIAL;
		}
		else {
			this.x =x - this.POSICION_INICIAL;
		}
		
		this.y = y;
		
		this.derecha = derecha;
	}

	public void dibujar(Entorno e) {
		e.dibujarImagen(this.imagen, this.x, this.y, 0, this.escala);
	}

	public void mover() {
		if (derecha) {
			this.imagen = IMAGEN_DER;
			this.x = this.x + VELOCIDAD;
		} else {
			this.imagen = IMAGEN_IZQ;
			this.x = this.x - VELOCIDAD;
		}
	}

	public void scalaSumar() {
		if (this.escala <= this.LIMITE_ESCALA) {
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

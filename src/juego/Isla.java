package juego;

import java.awt.Image;

import entorno.Entorno;

public class Isla {
	private double x;
	private double y;
	private double ancho;
	private double alto;

	private double escala;

	private Image imagen;

	public Isla(double x, double y) {
		this.x = x;
		this.y = y;
		this.escala = 0.12;
		this.imagen = entorno.Herramientas.cargarImagen("imagenes/isla/pngegg.png");
		this.ancho = imagen.getWidth(null) * this.escala;
		this.alto = imagen.getHeight(null) * this.escala;
		
	}

	public void dibujar(Entorno e) {
		e.dibujarImagen(this.imagen, this.x, this.y, 0, this.escala);

		// ColisionVisible.dibujar(e, this);
		// PARA HACER VISIBLE EL TAMAÑO DE LA COLISION PARA LAS PRUEBAS
	}
	
	//set y get : 
	
	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getAncho() {
		return ancho;
	}

	public void setAncho(double ancho) {
		this.ancho = ancho;
	}

	public double getAlto() {
		return alto;
	}

	public void setAlto(double alto) {
		this.alto = alto;
	}


	
	
	
}

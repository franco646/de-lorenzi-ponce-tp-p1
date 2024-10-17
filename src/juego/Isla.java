package juego;

import java.awt.Image;

import entorno.Entorno;

public class Isla {
	public double x;
	public double y;
	public double ancho;
	public double alto;

	double escala;

	Image imagen;

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
}

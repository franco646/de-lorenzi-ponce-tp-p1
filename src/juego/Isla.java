package juego;

import java.awt.Image;

import entorno.Entorno;

public class Isla {
	double x;
	double y;
	Image imagen;
	double escala;
	double width;
	double height;
	
	public Isla(double x, double y) {
		this.x = x;
		this.y = y;
		this.escala = 0.12;
		this.imagen = entorno.Herramientas.cargarImagen("imagenes/isla/pngegg.png");
		this.width = imagen.getWidth(null) * this.escala;
		this.height = imagen.getHeight(null) * this.escala;
	}
	
	public void dibujar(Entorno e) {
		e.dibujarImagen(this.imagen, this.x, this.y, 0, this.escala);
	}
}

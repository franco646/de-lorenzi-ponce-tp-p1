package juego;

import java.awt.Image;

import entorno.Entorno;

public class Personaje {
	double x;
	double y;
	Image imagen;
	double escala;
	double width;
	double height;
	Image[] imagenesDer;
	Image[] imagenesIzq;
	int frame;
	long tiempoAnterior;
	
	public Personaje(double x, double y) {
		this.x = x;
		this.y = y;
		this.escala = 0.5;
		this.imagen = entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/quieto.png");
		this.width = imagen.getWidth(null);
		this.height = imagen.getHeight(null);
		this.frame = 0;
		
		this.imagenesDer = new Image[]{
	            entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/correr-2.png"),
	            entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/correr-3.png"),
	            entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/correr-4.png"),
	            entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/correr-6.png"),
	            entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/correr-5.png"),
	            entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/correr-7.png"),
	            entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/correr-8.png"),
	            entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/correr-9.png"),
	        };
		
		this.imagenesIzq = new Image[]{
	            entorno.Herramientas.cargarImagen("imagenes/personaje/izquierda/correr-1.png"),
	            entorno.Herramientas.cargarImagen("imagenes/personaje/izquierda/correr-2.png"),
	            entorno.Herramientas.cargarImagen("imagenes/personaje/izquierda/correr-3.png"),
	            entorno.Herramientas.cargarImagen("imagenes/personaje/izquierda/correr-4.png"),
	            entorno.Herramientas.cargarImagen("imagenes/personaje/izquierda/correr-5.png"),
	            entorno.Herramientas.cargarImagen("imagenes/personaje/izquierda/correr-6.png"),
	            entorno.Herramientas.cargarImagen("imagenes/personaje/izquierda/correr-7.png"),
	            entorno.Herramientas.cargarImagen("imagenes/personaje/izquierda/correr-8.png"),
	        };
		
		this.tiempoAnterior = System.currentTimeMillis();
	}
	
	public void dibujar(Entorno e) {
		e.dibujarImagen(this.imagen, this.x, this.y, 0, this.escala);
	}
	
	public void caer() {
		this.imagen = entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/caer.png");
		this.y = this.y + 3;
	}
	
	public void moverDer(){
		if (this.x < 1366 - (this.width * this.escala) / 2) {
			long tiempoActual = System.currentTimeMillis();
			if (tiempoActual - this.tiempoAnterior > 150) {
				this.tiempoAnterior = tiempoActual;
				this.imagen = this.imagenesDer[frame];
				this.frame = (frame + 1) % this.imagenesDer.length;
			}
			this.x = this.x + 3;
		}else {
			this.quieto();
		}
	}
	
	public void moverIzq(){
		if (this.x > 0 + (this.width * this.escala) / 2) {
			long tiempoActual = System.currentTimeMillis();
			if (tiempoActual - this.tiempoAnterior > 150) {
				this.tiempoAnterior = tiempoActual;
				this.imagen = this.imagenesIzq[frame];
				this.frame = (frame + 1) % this.imagenesIzq.length;
			}
			this.x = this.x - 3;
		}else {
			this.quieto();
		}
	}
	
	public void quieto() {
		this.imagen = entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/quieto.png");
	}
}

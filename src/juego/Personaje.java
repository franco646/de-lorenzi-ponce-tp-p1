package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.InterfaceJuego;

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
	
	public static int LAYER = 1; ///Capa de COLISION
	Boolean derecha = true; ///comprobar si esta a la derecha == true
	public float grav_mult = 1;// esta variable es para ir aumentando el valor 
								//de la gravedad mientas mas pase el tiempo
	
	public boolean isJumping = false;
	public int limitjump =60;
	public int currentjump = 0;
	
	
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

		if (this.derecha) {
			this.imagen = entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/caer.png");
		} else {
			this.imagen = entorno.Herramientas.cargarImagen("imagenes/personaje/izquierda/caer_izq.png");
		}
		this.y = this.y + 3 * this.grav_mult;

		this.grav_mult += 0.01;

		if (this.y >= 768) {
			this.x = 660;
			this.y = 0;
		}
	}
	
	public void moverDer(){
		this.derecha = true;
		if (this.x < 1366 - (this.width * this.escala) / 2) {
			long tiempoActual = System.currentTimeMillis();
			if (tiempoActual - this.tiempoAnterior > 150 && !this.isJumping) {
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
		this.derecha = false;
		if (this.x > 0 + (this.width * this.escala) / 2) {
			long tiempoActual = System.currentTimeMillis();
			if (tiempoActual - this.tiempoAnterior > 150 && !this.isJumping) {
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
		
		this.frame = 0;
		this.imagen = entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/quieto.png");
	}
	
	public void salto() {
		if (this.currentjump < this.limitjump) {
			if (this.derecha) {
				this.imagen = entorno.Herramientas.cargarImagen("imagenes/personaje/derecha/saltar.png");
			} else {
				this.imagen = entorno.Herramientas.cargarImagen("imagenes/personaje/izquierda/saltar.png");
			}
				this.isJumping = true;
				this.y -=3;//si currentjump llega al limite deja de saltar, es basicamente un limite
		}
		else {
			this.isJumping = false;
		}
	}
		
		
	
	public void colisione(int capa) {
		
		if(capa == 2) {//capa 2 enemigo
			System.out.println("COLISIONASTE CON UN ENEMIGO :(");///Nose si hacemos asi las 
																///colisiones pero podria ser
		}
		
	}
	
}

package juego;

import java.awt.Image;

import entorno.Entorno;

public class Enemigo {

	double x;
	double y;
	Image imagen;
	double escala;
	double width;
	double height;
	//Image[] imagenesDer;
	Image[] imagenesIzq;
	int frame;
	long tiempoAnterior;
	
	public static int LAYER = 2; ///Capa de COLISION
	Boolean derecha = true;
	
	boolean habitacion_direccion = true;
	
	Boolean enisla = true;
	
	public boolean is_colisionando = false;//para saber si esta colisionando o no 
									// con esto se puede colisionar una vez por contacto
	
	public Enemigo(double x , double y) {
		
		this.x = x;
		this.y = y;
		this.escala = 2;
		this.imagen = entorno.Herramientas.cargarImagen("imagenes/enemigo/izquierda/Run1_izq.png");
		this.width = imagen.getWidth(null);
		this.height = imagen.getHeight(null);
		this.frame = 0;
		
		this.imagenesIzq = new Image[]{
	            entorno.Herramientas.cargarImagen("imagenes/enemigo/izquierda/Run1_izq.png"),
	            entorno.Herramientas.cargarImagen("imagenes/enemigo/izquierda/Run2_izq.png"),
	            entorno.Herramientas.cargarImagen("imagenes/enemigo/izquierda/Run3_izq.png")
	        };
		
		this.tiempoAnterior = System.currentTimeMillis();
		
	}
	
	public void dibujar(Entorno e) {
		e.dibujarImagen(this.imagen, this.x, this.y, 0, this.escala);
	}
	
	private boolean aleatorioNumero() {
		int random = (int)(Math.random() * 2);
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
		
		if(this.habitacion_direccion) {
			this.habitacion_direccion = false;
			
			this.derecha = this.aleatorioNumero();
			
		}
		
		this.imagen = entorno.Herramientas.cargarImagen("imagenes/enemigo/izquierda/Run2_izq.png");
		this.y = this.y + 3;
		this.enisla = false;
		if(this.y >= 768) {
			this.x =660;
			this.y = 0;
			if(this.derecha) {this.derecha = false;}
			else {this.derecha = true;}
			
			
		}
	}
	
	public void mover() {
		
		if(this.x >=1366) {
			this.x = 0;
		}
		else if(this.x <=0) {
			this.x = 1366;
		}
		
		if (this.derecha) {
			long tiempoActual = System.currentTimeMillis();
			if (tiempoActual - this.tiempoAnterior > 150) {
				this.tiempoAnterior = tiempoActual;
				this.imagen = this.imagenesIzq[frame];
				this.frame = (frame + 1) % this.imagenesIzq.length;
			}
			this.x = this.x + 1;
		}else if(this.derecha == false) {
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

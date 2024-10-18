package juego;

import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;

public class Enemigo {
	
	double xInicial;
	double x;
	double y;
	Image imagen;
	double escala;
	double ancho;
	double alto;
	
	Image[] imagenes;
	int frame;
	long tiempoAnterior;
	
	Boolean derecha = true;
	
	boolean puedeMoverse = false;

	Boolean enisla = true;
	
	public boolean is_colisionando = false;
	
	
	public Enemigo(double x , double y) {
		
		this.xInicial = x;
		
		this.x = x;
		this.y = y;
		
		this.escala = 0.1;
		this.imagen = entorno.Herramientas.cargarImagen("imagenes/enemigo/real/enemigoGeneric.png");
		this.ancho = imagen.getWidth(null) * this.escala;
		this.alto = imagen.getHeight(null) * this.escala;
		
		this.frame = 0;
		
		this.tiempoAnterior = System.currentTimeMillis();
		
	}
	
	private boolean aleatorioNumero() {
		int random = (int) (Math.random() * 2);
		boolean boleano;
		if (random == 0) {
			boleano = false;
		} else {
			boleano = true;
		}

		return boleano;

	}
	
	public static int seleccionAleatoria(int Maximo) {
		int random = (int) (Math.random() * Maximo);
		return random;
	}
	
	public void caer() {
		this.imagen = entorno.Herramientas.cargarImagen("imagenes/enemigo/real/enemigoGeneric.png");
		this.y = this.y + 3;
		 
	}
	public void mover(double ancho,double xIsla) {
			if(this.puedeMoverse) {	
				double anchoMitad = ancho/2;
				
				double posicion1 = anchoMitad + xIsla;//extremo derecho de la isla
				double posicion2 = xIsla - anchoMitad + 5;//extremo izquiero de la isla
				
				if (this.x <= posicion2) {
					this.derecha = true;
					
				} else if (this.x >= posicion1) {
					this.derecha = false;
				}
				
				if(this.derecha) {
					
					this.x +=0.7;
					
				}
				
				else if(!this.derecha) {
					
					this.x -=0.7;
					
				}
			}

	}
	

	
	
	
	public void dibujar(Entorno e) {
		e.dibujarImagen(this.imagen, this.x, this.y, 0, this.escala);

		ColisionVisible.dibujar(e, this);
		// PARA HACER VISIBLE EL TAMAÑO DE LA COLISION PARA LAS PRUEBAS

	}
	
	
	public Rectangle obtenerDimensiones() {
		int x = (int) this.x;
		int y = (int) this.y;
		int ancho = (int) this.ancho;
		int alto = (int) this.alto;

		return new Rectangle(x, y, ancho, alto);
	}
	
	
	
	
	
	
	
	
}

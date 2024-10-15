package juego;


import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private Personaje personaje;
	private Enemigo enemigo;
	private Isla[] islas;
	private Image fondo;
	private int anchoPantalla;
	private int altoPantalla;
	
	
	// Variables y métodos propios de cada grupo
	// ...
	Juego()
	{
 	
    	this.anchoPantalla = 1366;
    	this.altoPantalla = 768;
    	
		int qFilas = 5;
		int qIslas = 0;
		for (int i = 1; i <= qFilas; i++) {
			qIslas = qIslas + i;
		}
		
		this.entorno = new Entorno(this, "Proyecto para TP", this.anchoPantalla, this.altoPantalla);
		this.personaje = new Personaje(660, 0);
		this.enemigo = new Enemigo(660,200);
		this.islas = new Isla[qIslas];
		this.fondo = Herramientas.cargarImagen("imagenes/fondo/download (1).jpeg");
		
		
		int index = 0;
		for (int fila = 1; fila <= qFilas; fila++) {
            for (int isla = 1; isla <= fila; isla++) {            	
            	
            	int tamanioSeccionHorizontal = this.anchoPantalla / fila;
            	int medioSeccionHorizontal = (tamanioSeccionHorizontal * isla) - (tamanioSeccionHorizontal / 2);
            	
            	int tamanioSeccionVertical = this.altoPantalla / qFilas;
            	int medioSeccionVertical = (tamanioSeccionVertical * fila) - (tamanioSeccionVertical / 2); 
            	
            	this.islas[index] = new Isla(medioSeccionHorizontal, medioSeccionVertical);
            	index = index + 1;
            }
        }
		
		// Inicializar lo que haga falta para el juego
		// ...

		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick()
	{
		
		this.entorno.dibujarImagen(this.fondo, this.anchoPantalla / 2, this.altoPantalla / 2, 0, 1);
		
		boolean enIsla = false;
		
		enemigo.enisla = false;//enemigo en isla PROVICIONAL
		
		for(Isla isla : this.islas) {
			isla.dibujar(entorno);
			
			if (
				    (this.personaje.x > isla.x - isla.width / 2
				    && this.personaje.x < isla.x + isla.width / 2
				    && Math.abs(this.personaje.y - (isla.y - isla.height / 2)) <= 5)
				) 
				{	
					enIsla = true;
				}
			
			
			if (
				    (this.enemigo.x > isla.x - isla.width / 2
				    && this.enemigo.x < isla.x + isla.width / 2
				    && Math.abs(this.enemigo.y - (isla.y - isla.height / 2)) <= 5)
				) 
				{
					enemigo.enisla = true;
				}
			
		}
		
		if (!enIsla && !this.personaje.isJumping) {
			this.personaje.caer();
		}
		else {
			this.personaje.grav_mult = 1;
		}
		
		if(!enemigo.enisla) {///PROVICIONAL
			enemigo.caer();
		}
		
		this.personaje.dibujar(this.entorno);
		
		this.enemigo.dibujar(this.entorno);
		///PROVICIONAL ENEMIGO DIBUJAR , MOVER
		this.enemigo.mover();
		
		
		if (this.entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
			this.personaje.salto();
			if (!enIsla) {
				this.personaje.currentjump += 1;
				this.personaje.grav_mult = 1; // si el boton de salto es precionado el contador del salto se suma
			} else if (!this.personaje.isJumping) {
				this.personaje.currentjump = 0;
			}
		}
		if (this.entorno.seLevanto(entorno.TECLA_ARRIBA)) {
			this.personaje.isJumping = false;
		}
		
		if(this.entorno.estaPresionada(this.entorno.TECLA_DERECHA)) {
			this.personaje.moverDer();
		} else if(this.entorno.estaPresionada(this.entorno.TECLA_IZQUIERDA)) {
			this.personaje.moverIzq();
		} else {
			this.personaje.quieto();
		}
		
		

	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}

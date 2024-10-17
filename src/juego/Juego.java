package juego;


//import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private Personaje personaje;
	//private Enemigo enemigo;
	private Enemigo[] enemigos = new Enemigo[4];
	
	private Isla[] islas;
	private Image fondo;
	private int anchoPantalla;
	private int altoPantalla;
	
	boolean enIsla;
	
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
		//this.enemigo = new Enemigo(660,200);
		this.islas = new Isla[qIslas];
		this.fondo = Herramientas.cargarImagen("imagenes/fondo/download (1).jpeg");
		
		
		int index = 0;
		for (int fila = 1; fila <= qFilas; fila++) {
            for (int isla = 1; isla <= fila; isla++) {            	
            	
            	int medioSeccionHorizontal;
            	int tamanioSeccionHorizontal = this.anchoPantalla / fila;
            	if (fila == 2) {
            		medioSeccionHorizontal = (this.anchoPantalla / 3) * isla; // Las islas de la segunda fila no están centradas dos columna
            	}else {
                	medioSeccionHorizontal = (tamanioSeccionHorizontal * isla) - (tamanioSeccionHorizontal / 2);
            	}
            	
            	int tamanioSeccionVertical = this.altoPantalla / qFilas;
            	int medioSeccionVertical = (tamanioSeccionVertical * fila) - (tamanioSeccionVertical / 2); 
            	
            	this.islas[index] = new Isla(medioSeccionHorizontal, medioSeccionVertical);
            	index = index + 1;
            	
            }
        }
		
		this.crearEnemigos();
		
		
		// Inicializar lo que haga falta para el juego
		// ...

		// Inicia el juego!
		this.entorno.iniciar();
	}
	
	private void crearEnemigos() {
		
		System.out.println("creando array clase enemigos");
		
		Enemigo auxiliar;
		
		for(int i = 0;i < this.enemigos.length;i++) {
			
			auxiliar = new Enemigo(30 * i , 0);
			
			this.enemigos[i] = auxiliar;
			
		}
		
		
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
		
		enIsla = false;
		
		//enemigo.enisla = false;//enemigo en isla PROVICIONAL
		
		for(Enemigo enem : this.enemigos) {
			enem.enisla = false;
				
			}
		
		
		this.lasColisiones();
		
		
		
		this.movimientosJuego();
		
		
			

	}
	
	public void lasColisiones() {
		
		for(Isla isla : this.islas) {
			isla.dibujar(entorno);
			
			if(Colisiones.is_on_floor(this.personaje , isla)) {
			
				enIsla = true;
				
			}
			
			for(Enemigo enem : this.enemigos) {
				
				if(Colisiones.is_on_floor(enem, isla)) {
					enem.enisla = true;
					enem.habitacion_direccion = true;
					
				}
				
					
				}
			
			//if (Colisiones.is_on_floor(enemigo,isla)) 
				//{
					//enemigo.enisla = true;
					//enemigo.habitacion_direccion = true;
				//}
			
		}
		
		for(Enemigo enem : this.enemigos) {// colisiones enemigos
			
			if(Colisiones.checkRect(this.personaje, enem)) {
				
				System.out.println(this.personaje.is_colisionando);
				
			}
			
			
			
		}
		
		
		// colisiones enemigos
		//if (Colisiones.checkRect(this.personaje,enemigo)) {
			//System.out.println(this.personaje.is_colisionando);

		//} // provicional hasta crear bien la lista de enemigos y todo eso
		
		
	}
	
	public void movimientosJuego() {
		
		if (!enIsla && !this.personaje.isJumping) {
			this.personaje.caer();
		}
		else {
			this.personaje.grav_mult = 1;
		}
		
		for(Enemigo enem : this.enemigos) {
			
			enem.dibujar(this.entorno);
			
			if(!enem.enisla) {
				enem.caer();
			}
			
			enem.mover();
			
		}
		
		
		//if(!enemigo.enisla) {///PROVICIONAL
			//enemigo.caer();
		//}
		
		
		
		
		//this.enemigo.dibujar(this.entorno);
		
		this.personaje.dibujar(this.entorno);
		
		///PROVICIONAL ENEMIGO DIBUJAR , MOVER
		//this.enemigo.mover();
		
		this.jugadorMovimiento();
		
		
	}
	
	private void jugadorMovimiento() {
		
		if (this.personaje.isJumping) {
			this.personaje.subir();
		}
		
		if (this.entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
			if (enIsla && !this.personaje.isJumping) {
				this.personaje.comenzarSalto();
			}
		}
		
		if(this.entorno.estaPresionada(this.entorno.TECLA_DERECHA)) {
			this.personaje.moverDer();
			
			
		} else if(this.entorno.estaPresionada(this.entorno.TECLA_IZQUIERDA)) {
			this.personaje.moverIzq();
			
			
		} else {
			if (!this.personaje.isJumping) {
				this.personaje.quieto();
			}
		}
		
		
	}
	
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}

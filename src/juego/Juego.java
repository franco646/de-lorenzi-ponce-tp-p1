package juego;


import java.awt.Color;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private Personaje personaje;
	private Isla[] islas;
	
	// Variables y métodos propios de cada grupo
	// ...
	Juego()
	{
		
		int qFilas = 5;
		int qIslas = 0;
		for (int i = 1; i <= qFilas; i++) {
			qIslas = qIslas + i;
		}
		
		this.entorno = new Entorno(this, "Proyecto para TP", 1366, 768);
		this.personaje = new Personaje(100, 100);
		this.islas = new Isla[qIslas];
		
		int index = 0;
		for (int i = 1; i <= qFilas; i++) {
            for (int j = 1; j <= i; j++) {
            	this.islas[index] = new Isla((1366 / (i + 1)) * j, (768 / 5) * i - 50);
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
		boolean enIsla = false;
		
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
		}
		
		if (!enIsla) {
			this.personaje.caer();
		}
		
		this.personaje.dibujar(this.entorno);
		
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

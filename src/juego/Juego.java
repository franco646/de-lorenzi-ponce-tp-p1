package juego;

import java.awt.Image;
import java.util.LinkedList;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private TablaInterface tablainterface;// la clase que maneja los puntos y todo eso
	
	private Personaje personaje;
	
	private int respawnPj_x;//el spawn para el personaje
	private int respawnPj_y;
	
	private LinkedList<Gnomo> Gnomos = new LinkedList<>() ;//Linked de gnomos
	
	private int limiteGnomosParaColisionar;//esto es para que los Gnomos solo puedan colisionar en las ultimas dos filas
	private int contadorColisiones = 0;//contador de colisiones para probar nomas
	
	
	private Isla[] islas;
	private Image fondo;
	private int anchoPantalla;
	private int altoPantalla;
<<<<<<< Upstream, based on origin/master
	
	boolean enIsla;
	
=======

>>>>>>> 16185b6 Corrige gravedad y colisiones
	// Variables y métodos propios de cada grupo
	// ...
<<<<<<< Upstream, based on origin/master
	Juego()
	{
		
		
		
    	this.anchoPantalla = 1366;
    	this.altoPantalla = 768;
    	
		
    	this.crearIslasInicio();
    	
		
		this.entorno = new Entorno(this, "Proyecto para TP", this.anchoPantalla, this.altoPantalla);
		this.personaje = new Personaje(this.respawnPj_x, this.respawnPj_y);
		
		this.fondo = Herramientas.cargarImagen("imagenes/fondo/download (1).jpeg");
		
		tablainterface = new TablaInterface(0);
		
		
		this.crearEnemigos();
		
		
		// Inicializar lo que haga falta para el juego
		// ...

		// Inicia el juego!
		this.entorno.iniciar();
	}
	
	private void crearIslasInicio() {
		
=======
	Juego() {

		this.anchoPantalla = 1366;
		this.altoPantalla = 768;

>>>>>>> 16185b6 Corrige gravedad y colisiones
		int qFilas = 5;
		int qIslas = 0;
		for (int i = 1; i <= qFilas; i++) {
			qIslas = qIslas + i;
		}
<<<<<<< Upstream, based on origin/master
		
=======

		this.entorno = new Entorno(this, "Proyecto para TP", this.anchoPantalla, this.altoPantalla);
		this.personaje = new Personaje(660, 0);
		this.enemigo = new Enemigo(660, 200);
>>>>>>> 16185b6 Corrige gravedad y colisiones
		this.islas = new Isla[qIslas];
<<<<<<< Upstream, based on origin/master
		
=======
		this.fondo = Herramientas.cargarImagen("imagenes/fondo/download (1).jpeg");

>>>>>>> 16185b6 Corrige gravedad y colisiones
		int index = 0;
		for (int fila = 1; fila <= qFilas; fila++) {
<<<<<<< Upstream, based on origin/master
            for (int isla = 1; isla <= fila; isla++) {            	
            	
            	int medioSeccionHorizontal;
            	int tamanioSeccionHorizontal = this.anchoPantalla / fila;
            	if (fila == 2) {
            		medioSeccionHorizontal = (this.anchoPantalla / 3) * isla; // Las islas de la segunda fila no están centradas dos columna
            	}
            	else if(fila == 3) {
            		
            		medioSeccionHorizontal = (this.anchoPantalla / 4) * isla;
            		
            	}
            	
            	else {
                	medioSeccionHorizontal = (tamanioSeccionHorizontal * isla) - (tamanioSeccionHorizontal / 2);
            	}
            	
            	
            	
            	int tamanioSeccionVertical = this.altoPantalla / qFilas;
            	int medioSeccionVertical = (tamanioSeccionVertical * fila) - (tamanioSeccionVertical / 2);
            	
            	
            	if(fila == 3 && isla == 1) {
            		this.limiteGnomosParaColisionar =medioSeccionVertical; 
            		
            		
            		System.out.println("El Lim es : " + this.limiteGnomosParaColisionar);
            		
            		
            	}
            	
            	
            	if(fila == qFilas && isla == 2) {
            		
            		this.respawnPj_x = medioSeccionHorizontal;
            		
            		this.respawnPj_y = medioSeccionVertical - (tamanioSeccionVertical/2);
            		
            	}
            	
            	this.islas[index] = new Isla(medioSeccionHorizontal, medioSeccionVertical);
            	index = index + 1;
            	
            }
        }
		
		
		
=======
			for (int isla = 1; isla <= fila; isla++) {

				int medioSeccionHorizontal;
				int tamanioSeccionHorizontal = this.anchoPantalla / fila;
				if (fila == 2) {
					medioSeccionHorizontal = (this.anchoPantalla / 3) * isla; // Las islas de la segunda fila no están
																				// centradas dos columna
				} else {
					medioSeccionHorizontal = (tamanioSeccionHorizontal * isla) - (tamanioSeccionHorizontal / 2);
				}

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
>>>>>>> 16185b6 Corrige gravedad y colisiones
	}
	
	
	private void crearEnemigos() {
		
		System.out.println("creando LinkedList de clase enemigos");
		
		Gnomo auxiliar;
		
		for(int i = 0;i < 5.;i++) {
			
			auxiliar = new Gnomo(this.anchoPantalla/2, 0);
			
			this.Gnomos.add(auxiliar);
			
		}
	}
	
	private void agregarEnemigos() {
		
		
		System.out.println("Se añadio uno nuevo");
		Gnomo auxiliar = new Gnomo(this.anchoPantalla/2,0);
		
		this.Gnomos.add(auxiliar);
		
		
	}
		
		
	
	

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y por lo
	 * tanto es el método más importante de esta clase. Aquí se debe actualizar el
	 * estado interno del juego para simular el paso del tiempo (ver el enunciado
	 * del TP para mayor detalle).
	 */
	public void tick() {

		this.entorno.dibujarImagen(this.fondo, this.anchoPantalla / 2, this.altoPantalla / 2, 0, 1);
<<<<<<< Upstream, based on origin/master
		
		this.tablainterface.setTiempo(this.entorno.tiempo());
		
		System.out.println("TIEMPO : " + this.entorno.tiempo());
		
		this.tablainterface.dibujar(entorno);
		
		
		//DIBUJA UNA LINEA ROJA PARA SABER CUANDO EL PJ PUEDE COLISIONAR CON LOS GNOMOS
		this.entorno.dibujarRectangulo(this.anchoPantalla/2, this.limiteGnomosParaColisionar
				, this.anchoPantalla, 3, 0, Color.red);
		
		enIsla = false;
		
		
		for(Gnomo gnomo : this.Gnomos) {
			gnomo.enisla = false;
				
			}
		
		
		this.lasColisiones();
		
		
		
		this.movimientosJuego();
		
		
			

	}
	
	public void lasColisiones() {
		
		for(Isla isla : this.islas) {
=======

		boolean enIsla = false;

		enemigo.enisla = false;// enemigo en isla PROVICIONAL

		for (Isla isla : this.islas) {
>>>>>>> 16185b6 Corrige gravedad y colisiones
			isla.dibujar(entorno);
<<<<<<< Upstream, based on origin/master
			
			if(Colisiones.is_on_floor(this.personaje , isla)) {
			
=======

			// if (
			// (this.personaje.x > isla.x - isla.width / 2
			// && this.personaje.x < isla.x + isla.width / 2
			// && Math.abs(this.personaje.y - (isla.y - isla.height / 2)) <= 5)
			// )
			// {
			// enIsla = true;

			if (Colisiones.estaSobreIsla(this.personaje.obtenerDimensiones(), isla)) {
>>>>>>> 16185b6 Corrige gravedad y colisiones
				enIsla = true;

			}
<<<<<<< Upstream, based on origin/master
			
			for(Gnomo gnomo : this.Gnomos) {
				
				if(Colisiones.is_on_floor(gnomo, isla)) {
					gnomo.enisla = true;
					gnomo.habitacion_direccion = true;
					
				}
				
					
				}
			
=======

			if (Colisiones.estaSobreIsla(this.enemigo.obtenerDimensiones(), isla)) {
				this.enemigo.enisla = true;
			}

>>>>>>> 16185b6 Corrige gravedad y colisiones
		}
<<<<<<< Upstream, based on origin/master
		
		for(int i = 0;i < this.Gnomos.size();i++) {// colisiones enemigos
			
			Gnomo gnomo = this.Gnomos.get(i);
			
			if(Colisiones.checkRect(this.personaje, gnomo) && gnomo.y > this.limiteGnomosParaColisionar) {
				
				this.contadorColisiones +=1;//solo para saber cuantas veces colisiono
				
				System.out.println("Contador de Col: " + this.contadorColisiones );
				
				this.Gnomos.remove(i);
				
				this.agregarEnemigos();
				
			}	
		}
	}
	
	public void movimientosJuego() {
		
=======
		// colisiones enemigos
		if (Colisiones.colisionan(this.personaje.obtenerDimensiones(), this.enemigo.obtenerDimensiones())) {
			System.out.println("colisionaron");
		}

>>>>>>> 16185b6 Corrige gravedad y colisiones
		if (!enIsla && !this.personaje.isJumping) {
			this.personaje.caer();
		} else {
			this.personaje.resetVelocidadCaida();
		}
<<<<<<< Upstream, based on origin/master
		else {
			this.personaje.grav_mult = 1;
		}
		
		for(Gnomo gnomo : this.Gnomos) {
			
			gnomo.dibujar(this.entorno);
			
			if(!gnomo.enisla) {
				gnomo.caer();
			}
			
			gnomo.mover();
			
=======

		if (!enemigo.enisla) {/// PROVICIONAL
			enemigo.caer();
>>>>>>> 16185b6 Corrige gravedad y colisiones
		}

		this.personaje.dibujar(this.entorno);
<<<<<<< Upstream, based on origin/master
		
		this.jugadorMovimiento();
		
		
	}
	
	private void jugadorMovimiento() {
		
=======

		this.enemigo.dibujar(this.entorno);
		/// PROVICIONAL ENEMIGO DIBUJAR , MOVER
		this.enemigo.mover();

>>>>>>> 16185b6 Corrige gravedad y colisiones
		if (this.personaje.isJumping) {
			this.personaje.subir();
		}

		if (this.entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
			if (enIsla && !this.personaje.isJumping) {
				this.personaje.comenzarSalto();
			}
		}

		if (this.entorno.estaPresionada(this.entorno.TECLA_DERECHA)) {
			this.personaje.moverDer();
<<<<<<< Upstream, based on origin/master
			
			
		} else if(this.entorno.estaPresionada(this.entorno.TECLA_IZQUIERDA)) {
=======
		} else if (this.entorno.estaPresionada(this.entorno.TECLA_IZQUIERDA)) {
>>>>>>> 16185b6 Corrige gravedad y colisiones
			this.personaje.moverIzq();
			
			
		} else {
			if (!this.personaje.isJumping) {
				this.personaje.quieto();
			}
		}
<<<<<<< Upstream, based on origin/master
		
		
=======

>>>>>>> 16185b6 Corrige gravedad y colisiones
	}
<<<<<<< Upstream, based on origin/master
	
	
=======
>>>>>>> 16185b6 Corrige gravedad y colisiones

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}

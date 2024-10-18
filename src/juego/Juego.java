package juego;

import java.awt.Color;
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

	private int respawnPj_x;// el spawn para el personaje
	private int respawnPj_y;

	private LinkedList<Gnomo> Gnomos = new LinkedList<>();// Linked de gnomos
	private LinkedList<Proyectil> proyectiles = new LinkedList<Proyectil>();

	private LinkedList<Temporizador> GnomosTempo = new LinkedList<>();

	private int limiteGnomosParaColisionar;// esto es para que los Gnomos solo puedan colisionar en las ultimas dos
											// filas
	private int contadorColisiones = 0;// contador de colisiones para probar nomas

	private Isla[] islas;
	private Proyectil proyectil;
	private Image fondo;
	private int anchoPantalla;
	private int altoPantalla;

	// Variables y métodos propios de cada grupo
	// ...
	Juego() {

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

		int qFilas = 5;
		int qIslas = 0;
		for (int i = 1; i <= qFilas; i++) {
			qIslas = qIslas + i;
		}

		this.islas = new Isla[qIslas];

		int index = 0;
		for (int fila = 1; fila <= qFilas; fila++) {
			for (int isla = 1; isla <= fila; isla++) {

				int medioSeccionHorizontal;
				int tamanioSeccionHorizontal = this.anchoPantalla / fila;
				if (fila == 2) {
					medioSeccionHorizontal = (this.anchoPantalla / 3) * isla; // Las islas de la segunda fila no están
																				// centradas dos columna
				} else if (fila == 3) {

					medioSeccionHorizontal = (this.anchoPantalla / 4) * isla;

				}

				else {
					medioSeccionHorizontal = (tamanioSeccionHorizontal * isla) - (tamanioSeccionHorizontal / 2);
				}

				int tamanioSeccionVertical = this.altoPantalla / qFilas;
				int medioSeccionVertical = (tamanioSeccionVertical * fila) - (tamanioSeccionVertical / 2);

				if (fila == 3 && isla == 1) {
					this.limiteGnomosParaColisionar = medioSeccionVertical;

					System.out.println("El Lim es : " + this.limiteGnomosParaColisionar);

				}

				if (fila == qFilas && isla == 2) {

					this.respawnPj_x = medioSeccionHorizontal;

					this.respawnPj_y = medioSeccionVertical - (tamanioSeccionVertical / 2);

				}

				this.islas[index] = new Isla(medioSeccionHorizontal, medioSeccionVertical);
				index = index + 1;

			}
		}

	}
	
	public void GnomoPerdidos() {
		
		for(int i =0;i <this.Gnomos.size();i++) {
			
			if(this.Gnomos.get(i).y>=768) {
				
				this.Gnomos.remove(i);
				
				this.tablainterface.sumarPerdidos();
				
				this.crearGnomoTempo();
				
				
			}
			
		}
	}

	private void crearGnomoTempo() {

		Temporizador t = new Temporizador();

		this.GnomosTempo.add(t);

	}

	private void comprobarGnomoTempo() {

		int index = 0;

		if (!this.GnomosTempo.isEmpty())
			for (Temporizador t : this.GnomosTempo) {

				if (t.terminado) {
					this.agregarEnemigos();
					this.GnomosTempo.remove(index);

				}
				index += 1;

			}

	}

	private void crearEnemigos() {

		System.out.println("creando LinkedList de clase enemigos");

		Gnomo auxiliar;

		for (int i = 0; i < 5.; i++) {

			auxiliar = new Gnomo(this.anchoPantalla / 2, 0);

			this.Gnomos.add(auxiliar);

		}
	}

	private void agregarEnemigos() {

		System.out.println("Se añadio uno nuevo");
		Gnomo auxiliar = new Gnomo(this.anchoPantalla / 2, 0);

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

		this.tablainterface.setTiempo(this.entorno.tiempo());

		this.tablainterface.dibujar(entorno);

		// DIBUJA UNA LINEA ROJA PARA SABER CUANDO EL PJ PUEDE COLISIONAR CON LOS GNOMOS
		this.entorno.dibujarRectangulo(this.anchoPantalla / 2, this.limiteGnomosParaColisionar, this.anchoPantalla, 3,
				0, Color.red);

		this.personaje.enIsla = false;

		for (Gnomo gnomo : this.Gnomos) {
			gnomo.enisla = false;

		}

		this.GnomoPerdidos();
		
		this.comprobarGnomoTempo();

		this.dibujarIslas();
		this.dibujarJugador();
		this.dibujarGnomos();

		this.controlarMovimientosJugador();
		this.controlarMovimientoProyectiles();
		this.controlarColisionConGnomo();

	}

	public void dibujarIslas() {
		for (Isla isla : this.islas) {
			isla.dibujar(entorno);

			this.controlarColisionesConIsla(isla);

		}
	}

	public void controlarColisionesConIsla(Isla isla) {

		if (Colisiones.estaSobreIsla(this.personaje.obtenerDimensiones(), isla)) {

			this.personaje.enIsla = true;

		}

		for (Gnomo gnomo : this.Gnomos) {

			if (Colisiones.estaSobreIsla(gnomo.obtenerDimensiones(), isla)) {
				gnomo.enisla = true;
				gnomo.habitacion_direccion = true;

			}

		}

	}

	public void controlarColisionConGnomo() {

		for (int i = 0; i < this.Gnomos.size(); i++) {// colisiones enemigos

			Gnomo gnomo = this.Gnomos.get(i);

			if (Colisiones.colisionan(this.personaje.obtenerDimensiones(), gnomo.obtenerDimensiones())
					&& gnomo.y > this.limiteGnomosParaColisionar) {

				this.Gnomos.remove(i);

				this.tablainterface.sumarSalvados();

				this.crearGnomoTempo();

			}
		}
	}

	public void dibujarGnomos() {
		for (Gnomo gnomo : this.Gnomos) {

			gnomo.dibujar(this.entorno);

			this.controlarMovimientosGnomo(gnomo);

		}
	}

	public void dibujarJugador() {
		this.personaje.dibujar(this.entorno);
	}

	public void controlarCaidaJugador() {
		if (!this.personaje.enIsla && !this.personaje.isJumping) {
			this.personaje.caer();
		} else {
			this.personaje.resetVelocidadCaida();
		}
	}

	public void controlarSaltoJugador() {
		if (this.entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
			if (this.personaje.enIsla && !this.personaje.isJumping) {
				this.personaje.comenzarSalto();
			}
		}

		if (this.personaje.isJumping) {
			this.personaje.subir();
		}
	}

	public void controlarCaminataJugador() {
		if (this.entorno.estaPresionada(this.entorno.TECLA_DERECHA)) {
			this.personaje.moverDer();

		} else if (this.entorno.estaPresionada(this.entorno.TECLA_IZQUIERDA)) {
			this.personaje.moverIzq();

		} else {
			if (!this.personaje.isJumping && this.personaje.enIsla) {
				this.personaje.quieto();
			}
		}
	}

	public void controlarDisparoJugador() {
		if (this.entorno.estaPresionada('c')) {
			if (this.proyectiles.isEmpty() || Math.abs(this.personaje.x - this.proyectiles.getFirst().x) > 100) {
				Proyectil proyectil = new Proyectil(this.personaje.x, this.personaje.y, this.personaje.derecha);
				proyectiles.addFirst(proyectil);
			}
		}
	}

	public void eliminarProyectilesFueraDePantalla(Proyectil proyectil) {
		double bordeIzqProyectil = proyectil.x - proyectil.ancho / 2;
		double bordeDerProyectil = proyectil.x + proyectil.ancho / 2;

		if (bordeIzqProyectil > this.anchoPantalla || bordeDerProyectil < 0) {
			this.proyectiles.remove(proyectil);
		}
	}

	public void controlarMovimientoProyectiles() {
		for (Proyectil proyectil : this.proyectiles) {
			proyectil.dibujar(entorno);
			proyectil.mover();
			this.eliminarProyectilesFueraDePantalla(proyectil);
		}
	}

	public void controlarMovimientosJugador() {
		this.controlarSaltoJugador();
		this.controlarCaidaJugador();
		this.controlarCaminataJugador();
		this.controlarDisparoJugador();
	}

	public void controlarMovimientosGnomo(Gnomo gnomo) {
		if (!gnomo.enisla) {
			gnomo.caer();
		}

		gnomo.mover();
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}

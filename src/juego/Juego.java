package juego;

import java.awt.Color;
import java.awt.Image;
import java.util.LinkedList;
import java.util.Random;

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

	private LinkedList<Enemigo> enemigos = new LinkedList<Enemigo>();

	private LinkedList<Proyectil> proyectiles = new LinkedList<Proyectil>();

	private LinkedList<Isla> islas = new LinkedList<Isla>();

	private int limiteGnomosParaColisionar;// esto es para que los Gnomos solo puedan colisionar en las ultimas dos
											// filas

	private final int CANTIDAD_MAXIMA_GNOMOS = 5;
	private final int TIEMPO_RESPAWN_GNOMO = 5; // en segundos
	private final int CANTIDAD_INICIAL_ENEMIGOS = 5;
	private final int CANTIDAD_MAXIMA_ENEMIGOS = 10;
	private final int TIEMPO_RESPAWN_ENEMIGOS = 3; // en segundos

	private final int CANTIDAD_FILAS = 5;

	private int tiempoDeCreacionGnomo; // guarda el tiempo del momento en el que se creó el ultimo gnomo
	private int tiempoDeCreacionEnemigo; // guarda el tiempo del momento en el que se creó el ultimo Enemigo

	private boolean perdiste;
	private boolean ganaste;

	private Image fondo;
	private int anchoPantalla;
	private int altoPantalla;

	private Image gameOver;
	private Image youWin;
	private double winAndOverScale;
	private double giroImagen;

	// Variables y métodos propios de cada grupo
	// ...
	Juego() {

		this.perdiste = false;
		this.ganaste = false;

		this.anchoPantalla = 1366;
		this.altoPantalla = 768;

		this.crearIslasInicio();

		this.entorno = new Entorno(this, "Proyecto para TP", this.anchoPantalla, this.altoPantalla);
		this.personaje = new Personaje(this.respawnPj_x, this.respawnPj_y);

		this.fondo = Herramientas.cargarImagen("imagenes/fondo/download (1).jpeg");

		this.gameOver = Herramientas.cargarImagen("imagenes/finalJuego/gameOver.png");
		this.youWin = Herramientas.cargarImagen("imagenes/finalJuego/youWin.png");

		tablainterface = new TablaInterface(0);

		this.crearEnemigos(this.CANTIDAD_INICIAL_ENEMIGOS);

		// Inicializar lo que haga falta para el juego
		// ...

		// Inicia el juego!
		this.entorno.iniciar();
	}

	private void crearIslasInicio() {
		for (int fila = 1; fila <= this.CANTIDAD_FILAS; fila++) {

			int tamanioSeccionVertical = this.altoPantalla / this.CANTIDAD_FILAS;
			int medioSeccionVertical = (tamanioSeccionVertical * fila) - (tamanioSeccionVertical / 2);

			int tamanioSeccionHorizontal = this.anchoPantalla / fila;
			int medioSeccionHorizontal;

			for (int isla = 1; isla <= fila; isla++) {
				if (fila == 2) {
					medioSeccionHorizontal = (this.anchoPantalla / 3) * isla; // Las islas de la segunda fila no están
																				// // centradas dos columna
				} else if (fila == 3) {
					medioSeccionHorizontal = (this.anchoPantalla / 4) * isla;
				}

				else {
					medioSeccionHorizontal = (tamanioSeccionHorizontal * isla) - (tamanioSeccionHorizontal / 2);
				}

				this.islas.add(new Isla(medioSeccionHorizontal, medioSeccionVertical));

			}
		}

	}

	public void GnomoPerdidos() {

		for (int i = 0; i < this.Gnomos.size(); i++) {

			if (this.Gnomos.get(i).y >= 768) {

				this.Gnomos.remove(i);

				this.tablainterface.sumarPerdidos();

			}

		}
	}

	private void controlarCreacionDeEnemigos() {
		int tiempoActualEnSegundos = this.entorno.tiempo() / 1000;
		if (tiempoActualEnSegundos > this.tiempoDeCreacionEnemigo + this.TIEMPO_RESPAWN_ENEMIGOS
				&& this.enemigos.size() < this.CANTIDAD_MAXIMA_ENEMIGOS) {
			this.tiempoDeCreacionEnemigo = tiempoActualEnSegundos;
			crearEnemigos(1);
		}
	}

	private void crearEnemigos(int cantidadDeEnemigos) {
		for (int i = 0; i < cantidadDeEnemigos; i++) {

			// guarda tiempo en segundos
			Random random = new Random();

			int numeroAleatorio = random.nextInt(this.islas.size());
			Isla islaAleatoria = this.islas.get(numeroAleatorio == 0 ? 1 : numeroAleatorio); // no pueden generarse
																								// enemigos
																								// sobre la primer isla

			Enemigo enemigo = new Enemigo(islaAleatoria.x, islaAleatoria.y - 200);

			this.enemigos.add(enemigo);
		}

	}

	private void crearGnomos() {
		int tiempoActualEnSegundos = this.entorno.tiempo() / 1000;
		if (this.Gnomos.isEmpty() || tiempoActualEnSegundos > this.tiempoDeCreacionGnomo + this.TIEMPO_RESPAWN_GNOMO
				&& this.Gnomos.size() <= this.CANTIDAD_MAXIMA_GNOMOS) {
			this.tiempoDeCreacionGnomo = tiempoActualEnSegundos; // guarda tiempo en segundos
			Gnomo gnomo = new Gnomo(this.anchoPantalla / 2, 0);
			this.Gnomos.add(gnomo);
		}
	}

	public void comprobarEstadoDeJuego() {

		if (this.tablainterface.getPerdidos() >= 10) {

			this.perdiste = true;

		}

		if (this.tablainterface.getSalvados() >= 10) {

			this.ganaste = true;
		}

	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y por lo
	 * tanto es el método más importante de esta clase. Aquí se debe actualizar el
	 * estado interno del juego para simular el paso del tiempo (ver el enunciado
	 * del TP para mayor detalle).
	 */
	public void tick() {

		this.comprobarEstadoDeJuego();

		this.entorno.dibujarImagen(this.fondo, this.anchoPantalla / 2, this.altoPantalla / 2, 0, 1);

		if (!this.perdiste && !this.ganaste) {

			this.tablainterface.setTiempo(this.entorno.tiempo());

			this.tablainterface.dibujar(entorno);

			// DIBUJA UNA LINEA ROJA PARA SABER CUANDO EL PJ PUEDE COLISIONAR CON LOS GNOMOS
			this.entorno.dibujarRectangulo(this.anchoPantalla / 2, this.limiteGnomosParaColisionar + 20,
					this.anchoPantalla, 3, 0, Color.red);

			this.personaje.enIsla = false;

			for (int i = 0; i < this.Gnomos.size(); i++) {
				Gnomo gnomo = this.Gnomos.get(i);
				gnomo.enisla = false;
			}
			for (int i = 0; i < this.enemigos.size(); i++) {
				Enemigo enemigo = this.enemigos.get(i);
				enemigo.enisla = false;
			}

			this.crearGnomos();
			this.controlarCreacionDeEnemigos();

			this.GnomoPerdidos();

			this.controlarIslas();
			this.controlarEnemigos();
			this.controlarGnomos();
			this.controlarJugador();
			this.controlarProyectiles();
		}

		else if (this.perdiste) {
			this.entorno.dibujarImagen(gameOver, this.anchoPantalla / 2, this.altoPantalla / 2,
					Herramientas.radianes(this.giroImagen), this.winAndOverScale);

			if (this.winAndOverScale < 6) {

				this.winAndOverScale += 0.01;
			}
			if (this.giroImagen <= 358) {
				this.giroImagen += 358 / 120;
			}

		} else if (this.ganaste) {

			this.entorno.dibujarImagen(this.youWin, this.anchoPantalla / 2, this.altoPantalla / 2, 0,
					this.winAndOverScale);

			if (this.winAndOverScale < 1.20) {

				this.winAndOverScale += 0.005;
			}

		}

	}

	public void controlarIslas() {
		for (Isla isla : this.islas) {
			isla.dibujar(entorno);
			this.controlarColisionesConIsla(isla);

		}
	}

	public void controlarColisionesConIsla(Isla isla) {

		if (Colisiones.estaSobreIsla(this.personaje.obtenerDimensiones(), isla)) {

			this.personaje.enIsla = true;

		}

		for (int i = 0; i < this.Gnomos.size(); i++) {

			Gnomo gnomo = this.Gnomos.get(i);

			if (Colisiones.estaSobreIsla(gnomo.obtenerDimensiones(), isla)) {
				gnomo.enisla = true;
				gnomo.habitacion_direccion = true;

			}

		}

		for (int i = 0; i < this.enemigos.size(); i++) {

			Enemigo enemigo = this.enemigos.get(i);

			if (Colisiones.estaSobreIsla(enemigo.obtenerDimensiones(), isla)) {
				enemigo.enisla = true;
				enemigo.puedeMoverse = true;

			}
		}

	}

	public void controlarColisionesEnemigo(Enemigo enemigo) {
		for (int i = 0; i < this.proyectiles.size(); i++) {

			Proyectil proyectil = this.proyectiles.get(i);

			if (Colisiones.colisionan(enemigo.obtenerDimensiones(), proyectil.obtenerDimensiones())) {

				this.proyectiles.remove(proyectil);

				this.enemigos.remove(enemigo);

				this.tablainterface.sumarEliminados();
			}
			;
		}
	}

	public void controlarColisionesGnomo(Gnomo gnomo) {

		if (Colisiones.colisionan(this.personaje.obtenerDimensiones(), gnomo.obtenerDimensiones())
				&& gnomo.y > this.limiteGnomosParaColisionar - 20) {

			this.Gnomos.remove(gnomo);

			this.tablainterface.sumarSalvados();

		}

		for (int i = 0; i < this.enemigos.size(); i++) {

			Enemigo enemigo = this.enemigos.get(i);

			if (Colisiones.colisionan(gnomo.obtenerDimensiones(), enemigo.obtenerDimensiones())) {

				this.Gnomos.remove(gnomo);

				this.tablainterface.sumarPerdidos();

			}

		}

	}

	public void controlarGnomos() {
		for (int i = 0; i < this.Gnomos.size(); i++) {

			Gnomo gnomo = this.Gnomos.get(i);

			gnomo.dibujar(entorno);

			this.controlarColisionesGnomo(gnomo);
			this.controlarMovimientosGnomo(gnomo);

		}
	}

	public void dibujarJugador() {
		this.personaje.dibujar(this.entorno);
	}

	public void controlarEnemigos() {

		for (int i = 0; i < this.enemigos.size(); i++) {

			Enemigo enemigo = this.enemigos.get(i);

			enemigo.dibujar(entorno);

			this.controlarMovimientosEnemigo(enemigo);
			this.controlarColisionesEnemigo(enemigo);

		}

	}

	public void jugadorSeCayo() {

		if (this.personaje.y >= 768) {
			this.perdiste = true;
		}

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
			this.personaje.moverDer(this.entorno);

		} else if (this.entorno.estaPresionada(this.entorno.TECLA_IZQUIERDA)) {
			this.personaje.moverIzq(this.entorno);

		} else {
			if (!this.personaje.isJumping && this.personaje.enIsla) {
				this.personaje.quieto();
			}
		}
	}

	public void controlarDisparoJugador() {
		if (this.entorno.estaPresionada('c')) {
			if (this.proyectiles.isEmpty() || Math.abs(this.personaje.x - this.proyectiles.getFirst().x) > 200) {
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

	public void controlarProyectiles() {
		for (int i = 0; i < this.proyectiles.size(); i++) {
			Proyectil proyectil = this.proyectiles.get(i);

			proyectil.dibujar(entorno);
			proyectil.mover();
			proyectil.scalaSumar();
			this.eliminarProyectilesFueraDePantalla(proyectil);
		}
	}

	public void controlarJugador() {
		this.jugadorSeCayo();
		this.dibujarJugador();
		this.controlarSaltoJugador();
		this.controlarCaidaJugador();
		this.controlarCaminataJugador();
		this.controlarDisparoJugador();
	}

	public void controlarMovimientosEnemigo(Enemigo enemigo) {
		if (!enemigo.enisla && !enemigo.puedeMoverse) {
			enemigo.caer();
		}
		enemigo.mover();
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

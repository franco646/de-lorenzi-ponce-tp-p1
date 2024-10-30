package juego;

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
	private Proyectil proyectil;

	private int respawnPj_x = 100;// Spawn por defecto del personaje
	private int respawnPj_y = 100;

	private LinkedList<Gnomo> gnomos = new LinkedList<>();// Linked de gnomos

	private LinkedList<Enemigo> enemigos = new LinkedList<Enemigo>();

	private LinkedList<Isla> islas = new LinkedList<Isla>();

	private LinkedList<Bomba> bombas = new LinkedList<Bomba>();

	private static int CANTIDAD_MAXIMA_GNOMOS = 5;
	private static int TIEMPO_RESPAWN_GNOMO = 2; // en segundos
	private static int CANTIDAD_INICIAL_ENEMIGOS = 5;
	private static int CANTIDAD_MAXIMA_ENEMIGOS = 10;
	private static int TIEMPO_RESPAWN_ENEMIGOS = 5; // en segundos
	private static int TIEMPO_RESPAWN_BOMBAS = 5 * 1000; // en milesimas

	private static int CANTIDAD_FILAS = 5;

	private static int CANTIDAD_DE_GNOMOS_PARA_GANAR = 10;

	private static int CANTIDAD_DE_GNOMOS_PARA_PERDER = 10;

	private int tiempoDeCreacionUltimoGnomo; // guarda el tiempo del momento en el que se creó el ultimo gnomo
	private int tiempoDeCreacionUltimoEnemigo; // guarda el tiempo del momento en el que se creó el ultimo Enemigo
	private int tiempoDeCreacionUltimaBomba = 0;

	private boolean perdiste = false;
	private boolean ganaste = false;

	private static int ANCHO_PANTALLA = 1366;
	private static int ALTO_PANTALLA = 768;

	private static int LIMITE_SALVACION_GNOMOS = (ALTO_PANTALLA / 2) - 20;// esto es para que los Gnomos solo
																			// puedan
																			// ser salvados en las ultimas dos filas

	private static Image FONDO = Herramientas.cargarImagen("imagenes/fondo/download (1).jpeg");
	private static Image CASA_DE_GNOMOS = Herramientas.cargarImagen("imagenes/casa/casa de gnomos.png");
	private static Image GAME_OVER_IMAGEN = Herramientas.cargarImagen("imagenes/finalJuego/gameOver.png");
	private static Image VICTORY_IMAGEN = Herramientas.cargarImagen("imagenes/finalJuego/youWin.png");

	private double winAndOverScale;
	private double giroImagen;

	Juego() {
		this.entorno = new Entorno(this, "Proyecto para TP", ANCHO_PANTALLA, ALTO_PANTALLA);

		this.tablainterface = new TablaInterface(0);

		this.crearIslasInicio();

		this.crearJugador();

		this.crearEnemigos(CANTIDAD_INICIAL_ENEMIGOS);

		this.entorno.iniciar();
	}

	public void tick() {

		this.comprobarEstadoDeJuego();

		this.entorno.dibujarImagen(FONDO, ANCHO_PANTALLA / 2, ALTO_PANTALLA / 2, 0, 1);

		if (!this.perdiste && !this.ganaste) {

			this.tablainterface.setTiempo(this.entorno.tiempo());

			this.tablainterface.dibujar(entorno);

			this.controlarCreacionDeGnomos();
			this.controlarCreacionDeEnemigos();

			this.GnomoPerdidos();

			this.controlarIslas();
			this.entorno.dibujarImagen(CASA_DE_GNOMOS, ANCHO_PANTALLA / 2, 10, 0, 0.3);
			this.controlarEnemigos();
			this.controlarGnomos();
			this.controlarJugador();
			this.controlarProyectil();
			this.controlarBombas();

		}

		else if (this.perdiste) {
			this.entorno.dibujarImagen(GAME_OVER_IMAGEN, ANCHO_PANTALLA / 2, ALTO_PANTALLA / 2,
					Herramientas.radianes(this.giroImagen), this.winAndOverScale);

			if (this.winAndOverScale < 6) {

				this.winAndOverScale += 0.01;
			}
			if (this.giroImagen <= 358) {
				this.giroImagen += 358 / 120;
			}

		} else if (this.ganaste) {

			this.entorno.dibujarImagen(VICTORY_IMAGEN, ANCHO_PANTALLA / 2, ALTO_PANTALLA / 2, 0, this.winAndOverScale);

			if (this.winAndOverScale < 1.20) {

				this.winAndOverScale += 0.005;
			}

		}

	}

	private void crearJugador() {
		this.personaje = new Personaje(this.respawnPj_x, this.respawnPj_y);
	}

	private void crearIslasInicio() {
		for (int fila = 1; fila <= CANTIDAD_FILAS; fila++) {

			int tamanioSeccionVertical = ALTO_PANTALLA / CANTIDAD_FILAS;
			int medioSeccionVertical = (tamanioSeccionVertical * fila) - (tamanioSeccionVertical / 2);

			int tamanioSeccionHorizontal = ANCHO_PANTALLA / fila;
			int medioSeccionHorizontal;

			for (int isla = 1; isla <= fila; isla++) {
				if (fila == 2) {
					medioSeccionHorizontal = (ANCHO_PANTALLA / 3) * isla; // Las islas de la segunda fila no están
																			// // centradas dos columna
				} else if (fila == 3) {
					medioSeccionHorizontal = (ANCHO_PANTALLA / 4) * isla;
				}

				else {
					medioSeccionHorizontal = (tamanioSeccionHorizontal * isla) - (tamanioSeccionHorizontal / 2);
					if (fila == CANTIDAD_FILAS && isla == 1) { // crea el spawn del jugador en la primer isla de la
																// ultima fila
						this.crearSpawnJugador(medioSeccionHorizontal - 100, medioSeccionVertical);
					}
				}

				this.islas.add(new Isla(medioSeccionHorizontal, medioSeccionVertical));

			}
		}

	}

	private void crearSpawnJugador(int x, int y) {
		this.respawnPj_x = x;
		this.respawnPj_y = y - 100;
	}

	public void controlarIslas() {
		for (Isla isla : this.islas) {
			isla.dibujar(entorno);
		}
		this.controlarColisionesConIsla();
	}

	public void controlarColisionesConIsla() {
		for (Isla isla : this.islas) {
			if (Colisiones.estaSobreIsla(this.personaje.obtenerDimensiones(), isla)) {
				this.personaje.setEnIsla(true);
				break; // si el personaje está sobre una isla detiene el ciclo
			} else {
				this.personaje.setEnIsla(false);
			}

			if (Colisiones.colisionaConInferiorIsla(this.personaje.obtenerDimensiones(), isla)) {
				this.personaje.caer();
			}
		}

		for (int i = 0; i < this.gnomos.size(); i++) {

			Gnomo gnomo = this.gnomos.get(i);

			for (Isla isla : islas) {
				if (Colisiones.estaSobreIsla(gnomo.obtenerDimensiones(), isla)) {
					gnomo.setEnisla(true);
					;
					gnomo.setHabilitacionMovimiento(true);
					;
					break; // si el gnomo está sobre una isla detiene el ciclo
				} else {
					gnomo.setEnisla(false);
				}
			}

		}

		for (int i = 0; i < this.enemigos.size(); i++) {

			Enemigo enemigo = this.enemigos.get(i);

			for (Isla isla : islas) {
				if (Colisiones.estaSobreIsla(enemigo.obtenerDimensiones(), isla)) {
					enemigo.setEnisla(true);
					enemigo.setPuedeMoverse(true);
					break;
				} else {
					enemigo.setEnisla(false);
				}
			}

		}

	}

	public void GnomoPerdidos() {

		for (int i = 0; i < this.gnomos.size(); i++) {

			if (this.gnomos.get(i).getY() >= 768) {

				this.gnomos.remove(i);

				this.tablainterface.sumarPerdidos();

			}

		}
	}

	private void controlarCreacionDeEnemigos() {
		int tiempoActualEnSegundos = this.entorno.tiempo() / 1000;
		if (tiempoActualEnSegundos > this.tiempoDeCreacionUltimoEnemigo + TIEMPO_RESPAWN_ENEMIGOS // añade un enemigo
																									// cada
				// cierto tiempo
				&& this.enemigos.size() < CANTIDAD_MAXIMA_ENEMIGOS) {
			this.tiempoDeCreacionUltimoEnemigo = tiempoActualEnSegundos;
			crearEnemigos(1);
		}
	}

	private void crearEnemigos(int cantidadDeEnemigos) {
		for (int i = 0; i < cantidadDeEnemigos; i++) {

			Random random = new Random();

			int numeroAleatorio = random.nextInt(this.islas.size());
			Isla islaAleatoria = this.islas.get(numeroAleatorio == 0 ? 1 : numeroAleatorio); // no pueden generarse
																								// enemigos
																								// sobre la primer isla

			Enemigo enemigo = new Enemigo(islaAleatoria.getX(), islaAleatoria.getY() - 200);

			this.enemigos.add(enemigo);
		}

	}

	private void controlarCreacionDeGnomos() { // crea gnomos cada cierto tiempo
		int tiempoActualEnSegundos = this.entorno.tiempo() / 1000;
		if (this.gnomos.isEmpty() || tiempoActualEnSegundos > this.tiempoDeCreacionUltimoGnomo + TIEMPO_RESPAWN_GNOMO
				&& this.gnomos.size() <= CANTIDAD_MAXIMA_GNOMOS) {
			this.tiempoDeCreacionUltimoGnomo = tiempoActualEnSegundos; // guarda tiempo en segundos
			Gnomo gnomo = new Gnomo(ANCHO_PANTALLA / 2, 0);
			this.gnomos.add(gnomo);
		}
	}

	public void comprobarEstadoDeJuego() {

		if (this.tablainterface.getPerdidos() >= CANTIDAD_DE_GNOMOS_PARA_PERDER) {

			this.perdiste = true;

		}

		if (this.tablainterface.getSalvados() >= CANTIDAD_DE_GNOMOS_PARA_GANAR) {

			this.ganaste = true;
		}

	}

	public void controlarColisionesEnemigo(Enemigo enemigo) {

		if (Colisiones.colisionan(enemigo.obtenerDimensiones(), this.personaje.obtenerDimensiones())
				&& enemigo.getEnisla()) { // el enemigo debe estar sobre una isla para matar al jugador
			this.personaje.morir();
		}

		if (this.proyectil != null && this.proyectil.getEsVisible()) { // debe haber un proyectil visible
			if (Colisiones.colisionan(enemigo.obtenerDimensiones(), proyectil.obtenerDimensiones())) {

				this.proyectil.volverInvisible(); // el proyectil se vuelve invisible al chocar con un enemigo hasta que
													// eventualmente es eliminado al superar cierta distancia con el
													// jugador

				this.enemigos.remove(enemigo);

				this.tablainterface.sumarEliminados();
			}
		}
	}

	public void controlarColisionesExplosion(Bomba bomba) {
		if (Colisiones.colisionan(bomba.obtenerDimensiones(), this.personaje.obtenerDimensiones())) {
			this.personaje.morir();
		}
	}

	public void controlarColisionesGnomo(Gnomo gnomo) {

		if (Colisiones.colisionan(this.personaje.obtenerDimensiones(), gnomo.obtenerDimensiones())
				&& gnomo.getY() > LIMITE_SALVACION_GNOMOS - 20) {

			this.gnomos.remove(gnomo);

			this.tablainterface.sumarSalvados();

		}

		for (int i = 0; i < this.enemigos.size(); i++) {

			Enemigo enemigo = this.enemigos.get(i);

			if (Colisiones.colisionan(gnomo.obtenerDimensiones(), enemigo.obtenerDimensiones())) {

				this.gnomos.remove(gnomo);

				this.tablainterface.sumarPerdidos();

			}

		}

	}

	public void controlarBombas() {
		for (int i = 0; i < this.bombas.size(); i++) {

			Bomba bomba = this.bombas.get(i);

			bomba.dibujar(entorno);
			if (bomba.getExploto()) {
				this.controlarColisionesExplosion(bomba);
				this.controlarEliminacionBomba(bomba);
			}
			this.controlarExplosionBomba(bomba);
		}
	}

	public void controlarEliminacionBomba(Bomba bomba) {
		int tiempoActual = entorno.tiempo();
		int tiempoDesdeExplosion = tiempoActual - bomba.getTiempoAlMomenteDeExplosion();

		if (tiempoDesdeExplosion > 1000) {
			this.bombas.remove(bomba);
		}
	}

	public void controlarExplosionBomba(Bomba bomba) {
		bomba.controlarExplosion(entorno);
	}

	public void controlarGnomos() {
		for (int i = 0; i < this.gnomos.size(); i++) {

			Gnomo gnomo = this.gnomos.get(i);

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

			this.contolarBombasEnemigo(enemigo);
			this.controlarMovimientosEnemigo(enemigo);
			this.controlarColisionesEnemigo(enemigo);

		}

	}

	public void contolarBombasEnemigo(Enemigo enemigo) { // cada 5 segundos un enemigo deja una bomba
		int tiempoActual = entorno.tiempo();
		int tiempoDesdeLaLaUltimaBomba = tiempoActual - this.tiempoDeCreacionUltimaBomba; // el tiempo que transcurrio
																							// desde la creacion de la
																							// ultima bomba
		if (tiempoDesdeLaLaUltimaBomba > TIEMPO_RESPAWN_BOMBAS && enemigo.getEnisla() && !enemigo.getLanzoBomba()) {
			enemigo.setLanzoBomba(true);
			this.tiempoDeCreacionUltimaBomba = entorno.tiempo();
			this.bombas.add(new Bomba(enemigo.getX(), enemigo.getY(), entorno));
		}
	}

	public void controlarLimitesPantallaJugador() {
		if (this.personaje.getY() > ALTO_PANTALLA) {
			this.perdiste = true;
		}
	}

	public void controlarCaidaJugador() {
		if (!this.personaje.getEnIsla() && !this.personaje.getEstaSaltando()) {
			this.personaje.caer();
		} else {
			this.personaje.resetVelocidadCaida();
		}
	}

	public void controlarSaltoJugador() {
		if (this.entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
			if (this.personaje.getEnIsla() && !this.personaje.getEstaSaltando()) {
				this.personaje.comenzarSalto();
			}
		}

		if (this.personaje.getEstaSaltando()) {
			this.personaje.subir();
		}
	}

	public void controlarCaminataJugador() {
		if (this.entorno.estaPresionada(this.entorno.TECLA_DERECHA)) {
			this.personaje.moverDer(this.entorno);

		} else if (this.entorno.estaPresionada(this.entorno.TECLA_IZQUIERDA)) {
			this.personaje.moverIzq(this.entorno);

		} else {
			if (!this.personaje.getEstaSaltando() && this.personaje.getEnIsla()) {
				this.personaje.quieto();
			}
		}
	}

	public void controlarDisparoJugador() {
		if (this.entorno.estaPresionada('c') && this.proyectil == null) {
			this.proyectil = new Proyectil(this.personaje.getX(), this.personaje.getY(), this.personaje.getDerecha());
			// getDerecha() le pasa al proyectil la dirección a la que se debe mover
		}
	}

	public double calcularDistanciaProyectilJugador() {
		return Math.abs(this.proyectil.getX() - this.personaje.getX());
	}

	public void eliminarProyectilSegunDistancia() {
		double distanciaProyectilJugador = calcularDistanciaProyectilJugador();
		if (distanciaProyectilJugador > ANCHO_PANTALLA) {
			this.proyectil = null;
		}
	}

	public void controlarProyectil() {
		if (this.proyectil != null) {
			this.proyectil.dibujar(entorno);
			this.proyectil.mover();
			this.proyectil.scalaSumar();
			this.eliminarProyectilSegunDistancia();
		}
	}

	public void controlarJugador() {
		this.dibujarJugador();
		this.controlarLimitesPantallaJugador();
		if (this.personaje.getEstaVivo()) {
			this.controlarSaltoJugador();
			this.controlarCaidaJugador();
			this.controlarCaminataJugador();
			this.controlarDisparoJugador();
		} else {
			this.personaje.caer();
		}
	}

	public void controlarMovimientosEnemigo(Enemigo enemigo) {
		if (!enemigo.getEnisla() && !enemigo.getPuedeMoverse()) {
			enemigo.caer();
		}
		enemigo.mover();
	}

	public void controlarMovimientosGnomo(Gnomo gnomo) {
		if (!gnomo.getEnisla()) {
			gnomo.caer();
		}

		gnomo.mover(entorno);
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}

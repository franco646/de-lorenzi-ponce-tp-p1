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

	private int[] islasCordenadasX;
	private int[] islasCordenadasY;

	private LinkedList<Gnomo> Gnomos = new LinkedList<>();// Linked de gnomos

	private LinkedList<Enemigo> enemigos = new LinkedList<>();

	private LinkedList<Proyectil> proyectiles = new LinkedList<Proyectil>();

	private LinkedList<Temporizador> GnomosTempo = new LinkedList<>();

	private LinkedList<Temporizador> enemigoTempo = new LinkedList<>();

	private int limiteGnomosParaColisionar;// esto es para que los Gnomos solo puedan colisionar en las ultimas dos
											// filas

	private final int CANTIDAD_MAXIMA_GNOMOS = 5;

	int tiempoDeCreacionGnomo;

	private boolean perdiste;
	private boolean ganaste;

	private Isla[] islas;
	private double anchoisla;
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

		this.islasCordenadasX = new int[qIslas];

		this.islasCordenadasY = new int[qIslas];

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

				this.anchoisla = this.islas[index].ancho;

				this.islasCordenadasX[index] = medioSeccionHorizontal;

				this.islasCordenadasY[index] = medioSeccionVertical - (tamanioSeccionVertical / 2);

				index = index + 1;

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

	private void crearEnemigoTempo() {

		Temporizador t = new Temporizador(5000);

		this.enemigoTempo.add(t);

	}

	private void comprobarEnemigoTempo() {

		if (!this.enemigoTempo.isEmpty())
			for (int i = 0; i < this.enemigoTempo.size(); i++) {

				if (this.enemigoTempo.get(i).terminado) {
					this.agregarEnemigo();
					this.enemigoTempo.remove(i);

				}

			}

	}

	private void crearEnemigos() {

		int total = this.islasCordenadasX.length - 1;

		int aleatorioNum;

		int numEnemigo = 4;

		Enemigo auxiliar;

		for (int i = 0; i < numEnemigo; i++) {

			aleatorioNum = Enemigo.seleccionAleatoria(total);

			while (aleatorioNum == 0 || aleatorioNum == 12) {
				aleatorioNum = Enemigo.seleccionAleatoria(total);
			}

			auxiliar = new Enemigo(this.islasCordenadasX[aleatorioNum], this.islasCordenadasY[aleatorioNum] - 90);

			this.enemigos.add(auxiliar);

		}

	}

	private void crearGnomos() {
		int tiempoActualEnSegundos = this.entorno.tiempo() / 1000;
		if (this.Gnomos.isEmpty() || tiempoActualEnSegundos > this.tiempoDeCreacionGnomo + 5
				&& this.Gnomos.size() <= this.CANTIDAD_MAXIMA_GNOMOS) {
			this.tiempoDeCreacionGnomo = tiempoActualEnSegundos; // guarda tiempo en segundos
			Gnomo gnomo = new Gnomo(this.anchoPantalla / 2, 0);
			this.Gnomos.add(gnomo);
		}
	}

	private void agregarEnemigo() {

		int total = this.islasCordenadasX.length - 1;

		int aleatorioNum;

		Enemigo auxiliar;

		aleatorioNum = Enemigo.seleccionAleatoria(total);

		while (aleatorioNum == 0 || aleatorioNum == 12) {
			aleatorioNum = Enemigo.seleccionAleatoria(total);
		}

		auxiliar = new Enemigo(this.islasCordenadasX[aleatorioNum], this.islasCordenadasY[aleatorioNum] - 90);

		this.enemigos.add(auxiliar);

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
			this.entorno.dibujarRectangulo(this.anchoPantalla / 2, this.limiteGnomosParaColisionar + 20, this.anchoPantalla,
					3, 0, Color.red);

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

			this.GnomoPerdidos();
			this.comprobarEnemigoTempo();
			this.controlarIslas();
			this.controlarEnemigos();
			this.controlarGnomos();
			this.controlarJugador();
			this.controlarProyectiles();
		}
		
		else if(this.perdiste) {
			this.entorno.dibujarImagen(gameOver, this.anchoPantalla/2, this.altoPantalla/2
					, Herramientas.radianes(this.giroImagen) , this.winAndOverScale);
			
			if(this.winAndOverScale < 6) {
				
				this.winAndOverScale +=0.01;
			}
			if(this.giroImagen <= 358) {
				this.giroImagen +=358/120;
			}
			
		}
		else if(this.ganaste) {
			
			this.entorno.dibujarImagen(this.youWin, this.anchoPantalla/2
					, this.altoPantalla/2, 0 , this.winAndOverScale);
			
			if(this.winAndOverScale < 1.20) {
				
				this.winAndOverScale +=0.005;
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

			if (Colisiones.colisionan(enemigo.obtenerDimensiones()
					, proyectil.obtenerDimensiones())) {

				this.proyectiles.remove(proyectil);

				this.enemigos.remove(enemigo);

				this.tablainterface.sumarEliminados();

				this.crearEnemigoTempo();
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
		if (!enemigo.enisla) {
			enemigo.puedeMoverse = false;
			enemigo.caer();
		}

		enemigo.mover(this.anchoisla, enemigo.xInicial);
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

package juego;

import entorno.Entorno;

public class TablaInterface {

	private int tiempo;
	private int perdidos;
	private int salvados;
	private int eliminados;
	
	
	public TablaInterface(int tiempoInicial)
	{
		this.tiempo = tiempoInicial;
	}
	public void dibujar(Entorno e) {
		
		String tiempoTexto = Integer.toString(this.getTiempo());
		
		char segundo = tiempoTexto.charAt(0);
		
		String mostrartiempo = String.valueOf(segundo);//tratando de hacer los segundos
														//Arreglar
		
		e.escribirTexto(mostrartiempo, 15, 15);
		
	}
	
	
	public int getTiempo() {
		return tiempo;
	}


	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}


	public int getPerdidos() {
		return perdidos;
	}


	public void setPerdidos(int perdidos) {
		this.perdidos = perdidos;
	}


	public int getSalvados() {
		return salvados;
	}


	public void setSalvados(int salvados) {
		this.salvados = salvados;
	}


	public int getEliminados() {
		return eliminados;
	}


	public void setEliminados(int eliminados) {
		this.eliminados = eliminados;
	}
	
}

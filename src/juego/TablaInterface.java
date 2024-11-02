package juego;

import entorno.Entorno;

import java.awt.Color;


public class TablaInterface {

	private int tiempo;
	private int perdidos;
	private int salvados;
	private int eliminados;
	
	private String tiempototal;
	
	
	public TablaInterface(int tiempoInicial)
	{
		this.tiempo = tiempoInicial;
	}
	public void dibujar(Entorno e) {
		
		this.convertirTiempo();
		
		e.escribirTexto(this.tiempototal, 15, 15);
		
		
		String sal = Integer.toString(this.salvados);
		
		String SalvadosTotal = "Salvados :" + sal; 
		
		e.escribirTexto(SalvadosTotal, 160, 15);
		
		String per = Integer.toString(this.perdidos);
		
		String perTotal = "Perdidos :" + per; 
		
		e.escribirTexto(perTotal, 275, 15);
		
		String eli = Integer.toString(this.eliminados);
		
		String eliTotal = "Eliminados :" + eli; 
		
		e.escribirTexto(eliTotal, 375, 15);
		
	}
	
	private void convertirTiempo() {
		
		
		long segundosTotales = this.getTiempo() / 1000;
		long minutos = segundosTotales / 60;
		long segundos = segundosTotales % 60;
		
		
		if(minutos <10 && segundos < 10) {
			this.tiempototal = "Time : " + "0" + minutos + " : " + "0" + segundos;
		}
		else if(segundos < 10) {
			this.tiempototal = "Time : " + minutos + " : " + "0" + segundos;
		}
		
		else if(minutos < 10) {
			this.tiempototal = "Time : " + "0" + minutos + " : " + segundos;
		}
		
		else {
			this.tiempototal = "Time : " + minutos + " : " + segundos;
		}
			
	}
	
	public void sumarEliminados() {
		
		this.eliminados +=1;
	}
	
	public void sumarPerdidos() {
		
		this.perdidos +=1;
	}
	
	
	public void sumarSalvados() {
		
		this.salvados +=1;
		
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

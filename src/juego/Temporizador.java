package juego;

import java.util.Timer;
import java.util.TimerTask;

public class Temporizador {

	private Timer tempo;

	private TimerTask task;
		
	public boolean terminado;

	public Temporizador(int Milisegundos) {//temporizador para respawnear a los Gnomos
		    this.terminado = false;
		    	
		    this.tempo = new Timer();
		    	
		    this.task = new TimerTask(){
		    @Override
		    public void run() {
		                
		    	terminado = true;
		             
		    	tempo.cancel();
		        }
		    };
		        
		    this.tempo.schedule(task, Milisegundos);
		        
		    	
		
		}
	}


package polito.it.noleggio.model;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.PriorityQueue;

import polito.it.noleggio.model.Event.EventType;

public class Simulator {
	
	//Definire chi sono gli eventi
	private PriorityQueue<Event> queue;
	
	//Definire parametri d'ingresso della simulazione  (sono costanti, non cambiano durante la simulazione)
	private int NC; //number of cars
	private Duration T_IN; //intervallo di tempo tra i clienti in arrivo
	//ora aggiungo altri parametri che non sono stati detti esplicitamente ma sono venuti fuori mentre costruisco la simulazione:
	private LocalTime oraApertura= LocalTime.of(8, 0);
	private LocalTime oraChiusura= LocalTime.of(20, 0);
	
	//Definire lo stato del sistema
	private int nAuto; //auto attualmente presenti
	
	//Definire le misure in uscita
	private int nClienti;
	private int nClientiInsoddisfatti;
	
	//impostazioni dei parametri iniziali
	public void setNumCars(int NC) {
		this.NC=NC;
	}

	public void setClientFrequency(Duration d) {
		this.T_IN=d;
	}

	//Simulazione
	public void run() {
		this.queue= new PriorityQueue<Event>();
		//stato iniziale:
		this.nAuto=NC;
		this.nClienti=0;
		this.nClientiInsoddisfatti=0;
		
		//eventi iniziali:
		LocalTime ora= this.oraApertura;
		while(ora.isBefore(this.oraChiusura)) { //riempio la coda
			this.queue.add(new Event(ora,EventType.NUOVO_CLIENTE) );
			ora= ora.plus(this.T_IN);
		}
		
		//ciclo di simulazione:
		while (!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);
		}
		
	}
	
	private void processEvent(Event e) {
		switch(e.getType()) {
		case NUOVO_CLIENTE:
			this.nClienti++;
			if (this.nAuto>0) {
				//noleggia
				this.nAuto--;
				double num= Math.random()*3;// mi da un numero tra [0,1)*3 -> tra [0,3)
				if (num<1.0) { //ritorno dell'auto tra un'ora
					this.queue.add(new Event(e.getTime().plus(Duration.of(1, ChronoUnit.HOURS)), EventType.RITORNO_AUTO) );
				} else if (num<2.0) { //dopo 2 ore
					this.queue.add(new Event(e.getTime().plus(Duration.of(2, ChronoUnit.HOURS)), EventType.RITORNO_AUTO) );
				} else { //dopo 3 ore
					this.queue.add(new Event(e.getTime().plus(Duration.of(3, ChronoUnit.HOURS)), EventType.RITORNO_AUTO) );
				}
			}
			else {
				//e' insoddisfratto
				this.nClientiInsoddisfatti++;
			}
			break;
		case RITORNO_AUTO:
			this.nAuto++;
			break;
		}
	}

	public int getTotClients() {
		return this.nClienti;
	}

	public int getDissatisfied() {
		return this.nClientiInsoddisfatti;
	}
	
}

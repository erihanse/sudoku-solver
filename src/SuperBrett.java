
public class SuperBrett {
	private int dimensjon;
	private Rute[] rute;
	
	SuperBrett(int dim) {
		this.dimensjon = dim;
		rute = new Rute[dim];
	}
	
	void leggTilRute(Rute ru) {	
		for(int i = 0; i < dimensjon; i++) {
			if(rute[i] == null) {
				rute[i] = ru;
				break;
			}
		}
		if(this instanceof Rad) {
			ru.settRad((Rad) this);
		}
		if(this instanceof Kolonne) {
			ru.settKolonne((Kolonne) this);
		}
		if(this instanceof Boks) {
			ru.settBoks((Boks) this);
		}
	}
	
	int hentDimensjon() {
		return dimensjon;
	}
	
	/*
	 * Sjekker om en kolonne/rad/boks allerede inneholder en rute med den verdien
	 */
	boolean lovlig(char c) {
		for(Rute r : rute) {
			if(r.hentVerdi() == c) {
				return false;
			}
		}
		return true;
	}
}


public abstract class Rute {
	
	
	private Rute neste;
	private Boks boks;
	private Rad rad;
	private Kolonne kolonne;
	private char verdi;
	private Brett brett;
	private static Rute forsteRute;

	Rute(char v, Brett brett) {
		verdi = v;
		this.brett = brett;
	}
	
	public void settRad(Rad r) {
		this.rad = r;
	}
	
	public void settKolonne(Kolonne k) {
		this.kolonne = k;
	}
	
	public void settBoks(Boks b) {
		this.boks = b;
	}
	
	public void settVerdi(char c) {
		verdi = c;
	}
	
	public Rad hentRad() {
		return rad;
	}
	
	public Kolonne hentKolonne() {
		return kolonne;
	}
	
	public Boks hentBoks() {
		return boks;
	}

	public void settForsteRute(Rute r) {
		Rute.forsteRute = r;
	}
	
	public boolean settTallMegOgResten() {
		char tmp = '1';
		if(this instanceof Utfylt) {
			if(neste == null) {
				brett.leggIBuffer();
				return false;
			}
			neste.settTallMegOgResten();
		}
		if(this instanceof Tom) {
			for(int i = 0; i < rad.hentDimensjon(); i++) {
					if(boks.lovlig(tmp) && rad.lovlig(tmp) && kolonne.lovlig(tmp)) {
						verdi = tmp;
						if(neste == null) {	
							verdi = tmp;
							brett.leggIBuffer();
							return false;
						}
						if(neste != null) {
							if(neste.settTallMegOgResten()) {
								return true;
							} 
						}
					}
				if(tmp == '9') {
					tmp = 'A';
				}
				else 
				tmp++;
			}
			verdi = '.';
		} 
		return false;
	}

	public void settNesteRute(Rute r) {
		neste = r;
	}
	
	public Rute hentNesteRute() {
		return neste;
	}

	public char hentVerdi() {
		return verdi;
	}
}


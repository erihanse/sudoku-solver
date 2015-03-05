

public class Brett {
	int dimensjon;
	int radPerBoks;
	int kolPerBoks;
	Rute[][] rute;
	Rute forsteRute;
	Kolonne[] kolArr;
	Rad[] radArr;
	Boks[] boksArr;
	Rute r;
	Sudokubuffer sb;
	
	Brett(char[][] charBrett, int dim, int rader, int kol, Sudokubuffer sb) {
		dimensjon = dim;
		radPerBoks = rader;
		kolPerBoks = kol;
		kolArr = new Kolonne[dim];
		radArr = new Rad[dim];
		rute = new Rute[dim][dim];
		boksArr = new Boks[dim]; 
		int k = 0;		//For å finne ut hvilken boks en rute tilhører
		int l = 0;		//Teller som brukes når ulike bokser opprettes
		this.sb = sb;
		
		
		for(int i = 0; i < dim; i++) {
			radArr[i] = new Rad(dim);
			
			for(int j = 0; j < dim; j++) {
		
				if(kolArr[j] == null) {
					kolArr[j] = new Kolonne(dim);
				}
				if(i % radPerBoks == 0 && j % kolPerBoks == 0) {
					boksArr[l++] = new Boks(dim);
				}
				
				if(charBrett[i][j] == '.') {
					rute[i][j] = new Tom('.', this);
					
				} else {
				 	rute[i][j] = new Utfylt(charBrett[i][j], this);
				}
				
				if(forsteRute == null) {
					forsteRute = rute[i][j];
					r = forsteRute;
				}
				
				else {
					r.settNesteRute(rute[i][j]);
					r = rute[i][j];					
				}
								
				fiksPekere(rute[i][j], i, j, k);
				
				//Følgende tester er for å finne ut av hvilken boks en rute tilhører
				if(((i+1) % radPerBoks == 0 && j == dim-1)) {	//Ferdig med raden og boksen
					k++;
				} else if((j+1) % kolPerBoks == 0 && (j+1) == dimensjon) { //Ferdig med en rad, men ikke boksen
					k -= ((dimensjon/kolPerBoks)-1);
				} else if((j+1) % kolPerBoks == 0) {		//Ferdig med boksen men ikke raden
					k++;
				}
			}
		} 
	}
	
	/*
	 * Sørger for at en rute peker på dens kolonne, boks og rad, i tillegg til at kolonnen, boksen og raden har ruten inneholdt
	 */
	void fiksPekere(Rute r, int rad, int kol, int boks) {
		kolArr[kol].leggTilRute(r);
		radArr[rad].leggTilRute(r);
		boksArr[boks].leggTilRute(r);
	}
	
	/*
	 * Starter å løse
	 */
	void losBrett() {
		forsteRute.settTallMegOgResten();
		
	}
	
	
	public void leggIBuffer() {
		sb.settInn(this);
	}
}
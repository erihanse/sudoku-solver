


public class Sudokubuffer {
	private Brett[] b = new Brett[500];
	private int antLosninger = 0;
    private char losningen[][][];
	private int dimensjon;
	private String losningsString = "";
	
	Sudokubuffer(int dim) {
		dimensjon = dim;
		losningen = new char[500][dimensjon][dimensjon];
	}
    
	public char[][] taUt(int teller) {
		return losningen[teller-1];
	}
	
	public int hentAntallLosninger() {
		return antLosninger;
	}

	public String hentLosningsString() {
		return losningsString;
	}
	
	public void settInn(Brett brett) {
		losningsString += Integer.toString(antLosninger+1) + ": ";
		Rute tmp = brett.forsteRute;
		if(antLosninger < 500) {
			for(int i = 0; i < dimensjon; i++) {
				for(int j = 0; j < dimensjon; j++) {
					losningen[antLosninger][i][j] = tmp.hentVerdi();
					losningsString += tmp.hentVerdi();
					tmp = tmp.hentNesteRute();				
				}
				losningsString += "// ";
			}
		losningsString += "\r";
		}
		antLosninger++;
	}
}
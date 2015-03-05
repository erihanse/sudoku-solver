import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;


/**
 * Rakk dessverre ikke å gjøre programmet så 'proft' som det burde sett ut. Slik som å sjekke om det var txtfil som kom inn,
 * håndtere nullpointere og evt. andre unntak. Programmet er i alle fall ment for at det skal kunne skrive ut alle resterende
 * tomme ruter, eller bare en enkelt rute ved å klikke på den ruten man vil se verdien til. 
 * 
 * Forsøk 2: Har nå rettet opp i at man ikke kunne se alle løsningene.
 * 
 */

import javax.swing.JFileChooser;

public class Main {
	static Sudokubuffer sb;
	static int dimensjon;
	static int rader;
	static int kolonner;
	static char[][] brettString;
	
	public static void main(String[] args) throws FileNotFoundException {
		
		if(args.length == 0) {
			
			JFileChooser c = new JFileChooser();
			c.showOpenDialog(null);
			
			File f = c.getSelectedFile();	
			lesFil(f).losBrett();
			new Gui(dimensjon, rader, kolonner, brettString, sb);
		}

		else if(args.length == 1) {
			
			File f = new File(args[0]);
			
			Brett bb = lesFil(f);
			bb.losBrett();
			new Gui(dimensjon, rader, kolonner, brettString, sb);
			
		} else if(args.length == 2) {
			File f = new File(args[0]);
			Brett bb = lesFil(f); 
			bb.losBrett();
			PrintWriter s = new PrintWriter(new FileOutputStream((args[1])));
			s.write(sb.hentLosningsString());
			s.close();												
			new Gui(dimensjon, rader, kolonner, brettString, sb);
		}
	}
	
	/*
	 * Leser inn en fil, returnerer et brett
	 */
	static Brett lesFil(File f) throws FileNotFoundException {
		Scanner scan = new Scanner(f);
		dimensjon = scan.nextInt();
		rader = scan.nextInt();
		kolonner = scan.nextInt();
				
		brettString = new char[dimensjon][dimensjon];	//representerer char'en som blir lest inn
		String linje;

		int i = 0;
		while(scan.hasNext()) {
			linje = scan.next();
			
			for(int j = 0; j < dimensjon; j++) {
				brettString[i][j] = linje.charAt(j);
			}
			i++;
		}
		scan.close();
		sb = new Sudokubuffer(dimensjon);
		return new Brett(brettString, dimensjon, rader, kolonner, sb);
	}
}
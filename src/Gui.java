import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/*
 * Har hentet noe kode fra folk.uio.no/inf1010/v10/oblig/oblig3/SudokuGUI.java
 * Er inneforstått med at jeg må forstå all koden jeg har brukt.
 */
class Gui extends JFrame {
	
	private final int RUTE_STORRELSE = 60;	
	private int dimensjon;					
	private int radBoks;		//Ant rader per boks
	private int kolBoks;		//Ant kolonner per boks
	
	
	JButton[][] brett;
	JButton ruten;
	JButton losAlle;
	JButton visForrige;
	JButton visNeste;
	Sudokubuffer sb;
	char ulost[][];
	char lost[][];
	Rute tmp;
	JLabel teller;			//hvilken løsning som vises
	int tellerInt;
	
	Gui (int dim, int rader, int kol, char[][] brettString, Sudokubuffer sb) {
		
		ulost = brettString;
		dimensjon 	= dim;
		kolBoks 	= kol;
		radBoks 	= rader;
		brett = new JButton[dimensjon][dimensjon];	//Brettet
		this.sb = sb;
		tellerInt = 1;
		
		setLayout(new BorderLayout());
		
		
		setTitle(dimensjon + " x " + dimensjon);
		setLayout(new BorderLayout());
		
		JPanel knapperOgTeller = new JPanel();
		visForrige = new JButton("Forrige løsning");
		
		if(tellerInt == 1) {
			visForrige.setEnabled(false);
		} 
		visForrige.addActionListener(new LosOppgaveLytter(null));
		
		losAlle = new JButton("Løs hele brettet");
		
		visNeste = new JButton("Neste løsning");
		visNeste.addActionListener(new LosOppgaveLytter(null));
		if(tellerInt == sb.hentAntallLosninger()) {
			visNeste.setEnabled(false);
		}
		
		losAlle.addActionListener(new LosOppgaveLytter(null));
		teller = new JLabel(Integer.toString(tellerInt) + " av " + sb.hentAntallLosninger());
		
		knapperOgTeller.add(visForrige);
		knapperOgTeller.add(losAlle);
		knapperOgTeller.add(visNeste);
		knapperOgTeller.add(teller);
		
		JPanel brettPanel = tegnBrett(brettString);
		
		
		getContentPane().add(knapperOgTeller, BorderLayout.NORTH);
		getContentPane().add(brettPanel, BorderLayout.CENTER);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setSize(600,600);
		setVisible(true);
		
	}
	
	/**
	 * Returnerer paneletet som inneholder brettet, med hensyn på parameteret
	 * brettString, som er en dobbel char array av brettet slik det skal se ut.
	 * 
	 * @param 	brettString	
	 * @return 				et panel inneholdt av brettet
	 */
	
	
	JPanel tegnBrett(char[][] brettString) {
		int topp, venstre;
		JPanel brettet = new JPanel();
		
		
		brettet.setLayout(new GridLayout(dimensjon,dimensjon));
		
		brettet.setAlignmentX(CENTER_ALIGNMENT);
		
		brettet.setAlignmentY(CENTER_ALIGNMENT);

		setPreferredSize(new Dimension(dimensjon * RUTE_STORRELSE, 
				dimensjon * RUTE_STORRELSE));		

		
		for(int i = 0; i < dimensjon; i++) {
			topp = (i % radBoks == 0 && i != 0) ? 4 : 1;
			for(int j = 0; j < dimensjon; j++) {
				venstre = (j % kolBoks == 0 && j != 0) ? 4 : 1; 
				
				String tilString = Character.toString(brettString[i][j]);
				brett[i][j] = ruten = new JButton(tilString);
				
//				ruten.addActionListener(new LosOppgaveLytter(ruten));
				
				ruten.setFont(new Font("Dialog", Font.PLAIN, 32));
				ruten.setForeground(Color.red);
				ruten.setHorizontalAlignment(SwingConstants.CENTER);
				ruten.setPreferredSize(new Dimension(RUTE_STORRELSE, RUTE_STORRELSE));
				ruten.addActionListener(new LosOppgaveLytter(ruten));
				
				ruten.setBorder(BorderFactory.createMatteBorder
						(topp,venstre,1,1, Color.black));

				if(ulost[i][j] == '.') {
					ruten.setForeground(Color.black);
				}

					
					brett[i][j] = ruten;
					
					
					brettet.add(ruten);

				}
			}		
		return brettet;			
	}
	
	class LosOppgaveLytter implements ActionListener {
		JButton jb;
		
		LosOppgaveLytter(JButton jb) {
			this.jb = jb;
		}
		
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == losAlle) {
				lost = sb.taUt(tellerInt);
				for(int i = 0; i < dimensjon; i++) {
					for(int j = 0; j < dimensjon; j++) {
						brett[i][j].setText(Character.toString(lost[i][j]));
					}
				}
				
			} else if(e.getSource() == visForrige) {
				tellerInt--;
				visNeste.setEnabled(true);
				if(tellerInt == 1) {
					visForrige.setEnabled(false);
				}
				teller.setText(Integer.toString(tellerInt) + " av " + sb.hentAntallLosninger());
				for(int i = 0; i < dimensjon; i++) {
					for(int j = 0; j < dimensjon; j++) {
						brett[i][j].setText(Character.toString(ulost[i][j]));
					}
				}
				
			} else if(e.getSource() == visNeste) {
				visForrige.setEnabled(true);
				tellerInt++;
				teller.setText(Integer.toString(tellerInt) + " av " + sb.hentAntallLosninger());
				
				for(int i = 0; i < dimensjon; i++) {
					for(int j = 0; j < dimensjon; j++) {
						brett[i][j].setText(Character.toString(ulost[i][j]));
					}
				}
				
				if(tellerInt == sb.hentAntallLosninger()) {
					visNeste.setEnabled(false);
				}

			} else if(e.getSource() == jb) {
				for(int i = 0; i < dimensjon; i++) {
					for(int j = 0; j < dimensjon; j++) {
						if(brett[i][j] == jb) {
							jb.setText(Character.toString(sb.taUt(tellerInt)[i][j]));
						}
					}
				}
			}
		}
	}
}

	


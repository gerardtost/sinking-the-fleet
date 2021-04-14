	/* 
	 *  Guerra Naval (Sinking the fleet)
	 * 
	 *  GNU General Public License (GPL) 3.0
	 * 	
	 *  Gerard Tost (recull@digipime.com)
	 *  https://github.com/gerardtost/
	 *  
	 */



import java.util.*;

public class guerraNaval {

	public static void main(String[] args) {

        // Tauler de joc bàsic (ampliable)
		int fileres = 10;
		int columnes = 10;
		
		// Declarem la matriu que utilitzarem per a desar l'estat del tauler i dels vaixell		
		String[][] vaixells = new String[10][]; // Deu barquetes diferents
		
		// Cada barqueta conté informació interessant (mirar-ho a les funcions de creació d'escenaris)
		vaixells[0] = new String[8]; // Barqueta 1
		
		vaixells[1] = new String[7]; // Barqueta 2
		vaixells[2] = new String[7]; // Barqueta 3
		
		vaixells[3] = new String[6]; // Barqueta 4
		vaixells[4] = new String[6]; // Barqueta 5
		vaixells[5] = new String[6]; // Barqueta 6
		
		vaixells[6] = new String[5]; // Barqueta 7
		vaixells[7] = new String[5]; // Barqueta 8
		vaixells[8] = new String[5]; // Barqueta 9
		vaixells[9] = new String[5]; // Barqueta 10
		
		// Variables importants per al control del flux	
		int maxJugades = 25; // Quin és el límit de jugades de l'usuari
		int jugada = 0; // Encara no ha jugat cap vegada
		int coordx = 0; // Coordenada X usuari
		int coordy = 0; // Coordenada Y usuari


		//Joc de PROVES i comprovacions en temps de desenvolupament
		//Aquí sota n'hi ha algunes però se'n troben més al codi
		
		//comprovarFinal(vaixells);
		//omplirDeBrossa(vaixells);
		//omplirDeNumeros(vaixells);
		
		//vaixells = escenariFictici(); //En blanc immaculat
		//vaixells = escenariFicticiTocat(); //Amb resultats arbitraris
		//vaixells = escenariAleatori(); //Totalment aleatori
		
		//System.out.println(comprovarCoordenades(8,3, vaixells));
		//vaixells = canviarEstat(8,3, vaixells);
		//System.out.println(comprovarCoordenades(8,3, vaixells));
		
		//extreureCoordenades(vaixells);
		//pintarBarquetes(vaixells);
		
		//System.out.println(buscarEnfonsat(5,1, vaixells));
		//pintarEstatusBarquetes(vaixells);
		//taulerAmbBarquetes(fileres, columnes, vaixells);
		//taulerSenseMarcs(fileres, columnes, vaixells);
		
		//pintarBarquetes(vaixells); //Coordenades dels vaixells
		//llistarBarquetes(vaixells); //Totes les dades de la matriu
		
		// Presentació ASCII
		aixoEsLaGuerra();
		
		// Inicialitzem l'escenari
		vaixells = escenariAleatori();
		
		
		// Instruccions per a l'usuari
		System.out.println("\nEl joc comença amb un nou tauler en blanc.\nQuan toquis o enfonsis un vaixell tens una jugada extra.\n");
		System.out.println("Prepara't per a jugar...\n");
		
		// Pintem un nou tauler en blanc
		nouTaulerBlanc(fileres, columnes);
		
		// Comença el joc
		Scanner reader = new Scanner(System.in);
		
		do {
			
			System.out.println("Jugada: " + (jugada + 1));
			
			System.out.println("Introdueix la coordenada X 0-9: ");
			coordx = reader.nextInt();
			
			System.out.println("Introdueix la coordenada Y 0-9: ");
			coordy = reader.nextInt();
			
			// Comprovem que les coordenades numèriques siguin vàlides
			if ( (coordx < 0 || coordx > 9 ) || (coordy < 0 || coordy > 9) )
			{
				System.out.println("Si us plau, introdueix coordenades vàlides.");
			}
			
			else {
				
				// Ja estava enfonsat			
				if (comprovarCoordenades(coordx, coordy, vaixells) == 'E')
					
				{
					System.out.println("Ja estava enfonsat, però la jugada compta igualment.");
					jugada++;
					taulerAmbBarquetes(fileres, columnes, vaixells);
					
				}
				
				// Ja estava tocat
				else if (comprovarCoordenades(coordx, coordy, vaixells) == 'T')
					
				{
					System.out.println("Ja estava tocat, però la jugada compta igualment.");
					jugada++;
					taulerAmbBarquetes(fileres, columnes, vaixells);
					
				} 
				
				// Verifiquem que hi ha un vaixell que no havia estat descobert
				else if (comprovarCoordenades(coordx, coordy, vaixells) == 'N')
					
				{
					canviarATocat(coordx, coordy, vaixells);
					
					if (buscarEnfonsat(coordx, coordy, vaixells))
					{
						marcarEnfonsat(coordx, coordy, vaixells);
						System.out.println("TOCAT I ENFONSAT!");
						taulerAmbBarquetes(fileres, columnes, vaixells);
						
						if (totEnfonsat(vaixells))
						{
							jugada = maxJugades;
							System.out.println("HAS ENFONSAT TOTS ELS VAIXELLS. HAS GUANYAT EL JOC!");
						}
					}
					else 
					{
						System.out.println("TOCAT!");
						taulerAmbBarquetes(fileres, columnes, vaixells);
					}
					
				}
	
				else
				{
					System.out.println("AIGUA!");
					jugada++;
					pintarAigua(fileres, columnes, coordx, coordy, vaixells);
				}
		}
				
		if ((jugada < maxJugades)) 
		{
				System.out.println("\nJugades restants: " + (maxJugades - jugada));
		}
			
			
		} while (jugada < maxJugades); // El joc seguirà mentre no s'esgoti el total de jugades
		
		System.out.println("El joc ha finalitzat.");
		reader.close(); // Buidem el buffer	
					

	}
	
	
  
  
	/*
	 * Funcions que es criden al programa principal
	 */
	
	public static void nouTaulerBlanc(int totalFileres, int totalColumnes)
	{
		// Dibuixem un tauler en blanc, com a màxim de 10 x 10
		for (int filera = 0 ; filera <= totalFileres ; filera++)
		{
			for (int columna = 0 ; columna <= totalColumnes+1 ; columna ++)
			{
				// Primera filera
				if (filera == 0 && columna == 0) 
				{
					System.out.print("  "); // Escriu dos espais inicials
				} 
				else if (filera == 0 && columna > 0 && columna <= totalColumnes)
				{
					
					System.out.print(columna - 1 + " "); // Escriu el número de columna
				}
				else if (filera > 0 && columna == 0)
				{
					System.out.print(filera - 1 + " "); // Escriu el número de filera
				}
				else if (columna == totalColumnes+1)
				{
					System.out.print("\n");
				}
				else System.out.print("· ");
			}
			
		}	
			
	}
	
	
	
  
	public static String [][] omplirDeBrossa(String matriuBuida[][] )
	//Funció de test que omple de brossa la matriu
	{
		
		String novaMatriu [][] = new String [matriuBuida.length][];
		
		novaMatriu[0] = new String[8]; // Barqueta 1
		
		novaMatriu[1] = new String[7]; // Barqueta 2
		novaMatriu[2] = new String[7]; // Barqueta 3
		
		novaMatriu[3] = new String[6]; // Barqueta 4
		novaMatriu[4] = new String[6]; // Barqueta 5
		novaMatriu[5] = new String[6]; // Barqueta 6
		
		novaMatriu[6] = new String[5]; // Barqueta 7
		novaMatriu[7] = new String[5]; // Barqueta 8
		novaMatriu[8] = new String[5]; // Barqueta 9
		novaMatriu[9] = new String[5]; // Barqueta 10
		

		
		System.out.println("inici");
		for (int i = 0 ; i < novaMatriu.length ; i++ )
		{
			System.out.println("Inici de la barqueta " + i + "\n");
			for (int col = 0 ; col < novaMatriu[i].length ; col++)	
			{
				novaMatriu[i][col] = Integer.toString(i);
				System.out.print(novaMatriu[i][col]);
			}
			System.out.println("\nFinal de la barqueta " + i + "\n");
		}
		System.out.println("final");
		return novaMatriu;
		
	}
	
  
  
  
	public static String [][] omplirDeNumeros (String matriuBuida[][] )
	// Més brossa (test)
	{
		
		String novaMatriu [][] = matriuBuida;
		
		
		System.out.println("inici");
		for (int i = 0 ; i < novaMatriu.length ; i++ )
		{
			System.out.println("Inici de la barqueta " + i + "\n");
			for (int col = 0 ; col < novaMatriu[i].length ; col++)	
			{
				novaMatriu[i][col] = Integer.toString(i);
				System.out.print(novaMatriu[i][col]);
			}
			System.out.println("\nFinal de la barqueta " + i + "\n");
		}
		System.out.println("final");
		return novaMatriu;
		
	}
	
	public static void pintarBarquetes(String [][] barquetes) {
		// Funció de test per a escriure les posicions cartesianes de cada vaixell
		
		for (int i = 0 ; i < barquetes.length ; i++ )
		{
			System.out.println("Posicions de la barqueta " + (i+1));
			
			for (int posicions = 4 ; posicions < barquetes[i].length ; posicions++)
			{
				System.out.print(barquetes[i][posicions] + " ");
			}
			System.out.println();
			
		}
		
		
	}
	
  
  
	public static void pintarEstatusBarquetes(String [][] barquetes) 
	//Funció de test per a saber l'estatus de cada vaixell
	{
		
		for (int i = 0 ; i < barquetes.length ; i++ )
		{
			System.out.println("Posicions de la barqueta " + (i+1));
			
			if (barquetes[i][3] == "N") {
				
				System.out.println("Barqueta "+ (i+1) + " no descoberta." );
			}
			
			else if (barquetes[i][3] == "E") {
				
				System.out.println("Barqueta " + (i+1) + " enfonsada." );
			}
			else {
				System.out.println("Barqueta " + (i+1) + " tocada." );
				
				for (int posicions = 4 ; posicions < barquetes[i].length ; posicions++)
				{
					System.out.print(barquetes[i][posicions] + " ");
				}
			}
			
			System.out.println();
			
		}
		
		
	}
	

	
	public static String [][] escenariFictici()
	// Crea un escenari inicial fictici sense dades conegudes a priori (test)
	{
		
		String [][] novaMatriu = 
				
			{
					
				{"1", "4","6","N","54N","55N","56N","57N"},	
				{"2", "3", "7", "N", "07N", "08N", "09N"},
				{"3", "3", "7", "N", "41N", "51N", "61N"},
				{"4", "2", "8", "N", "10N", "11N"},
				{"5", "2", "8", "N", "23N", "24N"},
				{"6", "2", "8", "N", "86N", "96N"},
				{"7", "1", "9", "N", "83N"},
				{"8", "1", "9", "N", "36N"},
				{"8", "1", "9", "N", "38N"},
				{"10", "1", "9", "N", "99N"}
								
			};


		return novaMatriu;
		
	}
	
  
  
	public static String [][] escenariFicticiTocat()
	//Crea un escenari fictici per a fer proves amb vaixells tocats i enfonsats (test)
	{
		
		String [][] novaMatriu = 
				
			{
					
				{"1", "4","D","N","54N","55N","56N","57N"},	
				{"2", "3", "D", "T", "07N", "08N", "09T"},
				{"3", "3", "I", "N", "41T", "51T", "61N"},
				{"4", "2", "D", "N", "10N", "11N"},
				{"5", "2", "D", "T", "23T", "24N"},
				{"6", "2", "D", "N", "86N", "96N"},
				{"7", "1", "D", "N", "83N"},
				{"8", "1", "D", "E", "36E"},
				{"8", "1", "D", "N", "38N"},
				{"10", "1", "D", "E", "99E"}
								
			};


		return novaMatriu;
		
	}
	
	public static String [][] canviarATocat(int cx, int cy, String [][] barquetes) 
	// Canvia de no tocada a tocada una part d'un vaixell
	{
		
		for (int i = 0 ; i < barquetes.length ; i++ )
		{
						
			for (int posicions = 4 ; posicions < barquetes[i].length ; posicions++)
			{
				char ix = barquetes[i][posicions].charAt(barquetes[i][posicions].length()-3);
				char ii = barquetes[i][posicions].charAt(barquetes[i][posicions].length()-2);
				
				int coordenadax = Character.getNumericValue(ix);
				int coordenaday = Character.getNumericValue(ii);
				

				if (cx == coordenadax && cy == coordenaday)
				{
					
					barquetes[i][posicions] = Character.toString(ix) + Character.toString(ii) + "T";

				}
				
			}

		}
		return barquetes;
		
	}
	
  
  
	public static String [][] marcarEnfonsat(int cx, int cy, String [][] barquetes)
	// Marca un vaixell com a enfonsat
	{
		
		for (int i = 0 ; i < barquetes.length ; i++ )
		{
			
			for (int posicions = 4 ; posicions < barquetes[i].length ; posicions++)
			{
				char ix = barquetes[i][posicions].charAt(barquetes[i][posicions].length()-3);
				char ii = barquetes[i][posicions].charAt(barquetes[i][posicions].length()-2);
				
				int coordenadax = Character.getNumericValue(ix);
				int coordenaday = Character.getNumericValue(ii);
				
				if (cx == coordenadax && cy == coordenaday)
				{
					barquetes[i][3] = "E";
					
					for (int salts = 4 ; salts < barquetes[i].length ; salts++)
					{
						char posx = barquetes[i][salts].charAt(barquetes[i][salts].length()-3);
						char posy = barquetes[i][salts].charAt(barquetes[i][salts].length()-2);
						barquetes[i][salts] = Character.toString(posx) + Character.toString(posy) + "E";
					}
				}

			}
			
			
		} return barquetes;
		
	}
		
	
	
	
	public static boolean buscarEnfonsat(int cx, int cy, String [][] barquetes)
	// Comprova si totes les parts d'un vaixell han estat tocades, de manera que estarà enfonsat
	{
		
		for (int i = 0 ; i < barquetes.length ; i++ )
		{
			
			for (int posicions = 4 ; posicions < barquetes[i].length ; posicions++)
			{
				char ix = barquetes[i][posicions].charAt(barquetes[i][posicions].length()-3);
				char ii = barquetes[i][posicions].charAt(barquetes[i][posicions].length()-2);
				
				int coordenadax = Character.getNumericValue(ix);
				int coordenaday = Character.getNumericValue(ii);
				
				if (cx == coordenadax && cy == coordenaday)
				{
				
					int caselles = Integer.parseInt(barquetes[i][1]) ;
					int tocats = 0;
					
					for (int salts = 4 ; salts < barquetes[i].length ; salts++)
					{
						char estat = barquetes[i][salts].charAt(barquetes[i][salts].length()-1);
						
						if (estat == 'T' || estat == 'E')
						{
							tocats++;
						}
						
					}
					
					if (tocats == caselles) {
						
						return true;
					}
					
					
				}
				

			}
			
			
		} return false;
		
	}
		
	
	
	public static char comprovarCoordenades(int cx, int cy, String [][] barquetes) 
	// Comprovem què hi ha a les coordenades que ha introduït el jugador
	{
		
		char resultat = ' ';
		for (int i = 0 ; i < barquetes.length ; i++ )
		{
			//System.out.println("Posicions de la barqueta " + (i+1));
			
			for (int posicions = 4 ; posicions < barquetes[i].length ; posicions++)
			{
				char ix = barquetes[i][posicions].charAt(barquetes[i][posicions].length()-3);
				char ii = barquetes[i][posicions].charAt(barquetes[i][posicions].length()-2);
				
				int coordenadax = Character.getNumericValue(ix);
				int coordenaday = Character.getNumericValue(ii);
				
				if (cx == coordenadax && cy == coordenaday)
				{
					char estat = barquetes[i][posicions].charAt(barquetes[i][posicions].length()-1);
					
					if (estat == 'T') {
					resultat = 'T';
					
					} else if (estat == 'E')
					{
						resultat = 'E';
								
					} else
					{
						resultat = 'N';
					}
					
				}
				

			}
			
			
		}
		return resultat;
		
	}
	
  
  
	public static void extreureCoordenades(String [][] barquetes) {
		// Funció de control per a veure coordenades dels vaixells (test)
		
		for (int i = 0 ; i < barquetes.length ; i++ )
		{
			System.out.println("Posicions de la barqueta " + (i+1));
			
			for (int posicions = 4 ; posicions < barquetes[i].length ; posicions++)
			{
				
				char ix = barquetes[i][posicions].charAt(barquetes[i][posicions].length()-3);
				char ii = barquetes[i][posicions].charAt(barquetes[i][posicions].length()-2);
				char estat = barquetes[i][posicions].charAt(barquetes[i][posicions].length()-1);
				System.out.print("Posició X: " + ix + " ");
				System.out.print("Posició Y: " + ii + " ");
				System.out.print("Estat: " + estat + " ");
			}
			System.out.println();
			
		}
		
	}
	
	
	
	public static void taulerAmbBarquetes(int totalFileres, int totalColumnes, String [][] barquetes)
	{
		// Dibuixem un tauler amb tots els resultats coneguts fins al moment
		for (int filera = 0 ; filera <= totalFileres ; filera++)
		{
			for (int columna = 0 ; columna <= totalColumnes+1 ; columna ++)
			{
				// Primera filera
				if (filera == 0 && columna == 0) 
				{
					System.out.print("  "); // Escriu dos espais inicials
				} 
				else if (filera == 0 && columna > 0 && columna <= totalColumnes)
				{
					
					System.out.print(columna - 1 + " "); // Escriu el número de columna
				}
				else if (filera > 0 && columna == 0)
				{
					System.out.print(filera - 1 + " "); // Escriu el número de filera
				}
				else if (columna == totalColumnes+1)
				{
					System.out.print("\n");
				}
				else 
					
				{
					if (comprovarCoordenades((filera-1), (columna-1), barquetes) == 'E')
						
					{
						System.out.print("E ");
						
					}
					
					else if (comprovarCoordenades((filera-1), (columna-1), barquetes) == 'T')
						
					{
						System.out.print("T ");
						
					}
		
					else System.out.print("· ");
			}
			
		}	
	}
			
	}
	
  
  
		public static void pintarAigua(int totalFileres, int totalColumnes, int coordx, int coordy, String [][] barquetes)
		{
			// Dibuixem un tauler on marquem la casella buida amb aigua
			for (int filera = 0 ; filera <= totalFileres ; filera++)
			{
				for (int columna = 0 ; columna <= totalColumnes+1 ; columna ++)
				{
					// Primera filera
					if (filera == 0 && columna == 0) 
					{
						System.out.print("  "); // Escriu dos espais inicials
					} 
					else if (filera == 0 && columna > 0 && columna <= totalColumnes)
					{
						
						System.out.print(columna - 1 + " "); // Escriu el número de columna
					}
					else if (filera > 0 && columna == 0)
					{
						System.out.print(filera - 1 + " "); // Escriu el número de filera
					}
					else if (columna == totalColumnes+1)
					{
						System.out.print("\n");
					}
					else 
						
					{
						if (comprovarCoordenades((filera-1), (columna-1), barquetes) == 'E')
							
						{
							System.out.print("E ");
							
						}
						
						else if (comprovarCoordenades((filera-1), (columna-1), barquetes) == 'T')
							
						{
							System.out.print("T ");
							
						}
						
						else if (coordx == filera-1 && coordy == columna-1)

						{
							System.out.print("~ ");
						}
			
						else System.out.print("· ");
				}
				
			}	
				
		}

	
	}

  
  
	public static boolean totEnfonsat(String [][] barquetes)
	// Comprova si totes les barquetes han estat enfonsades
	{
	
		int enfonsat = 0;
		for (int i = 0 ; i < barquetes.length ; i++ )
		{
			if (barquetes[i][3] == "E")
			{
				enfonsat++;
			}
		}
		
		if (enfonsat == barquetes.length)
		{
			return true;
		}
		
		else return false;
	}
		
		
		
	public static void taulerSenseMarcs(int totalFileres, int totalColumnes, String [][] barquetes)
	{
		// Dibuixem un tauler sense marcs i sense espais al mig (test)
		for (int filera = 0 ; filera < totalFileres ; filera++)
		{
			for (int columna = 0 ; columna < totalColumnes ; columna ++)
			{

					if (comprovarCoordenades(filera, columna, barquetes) == 'E')
						
					{
						System.out.print("E");
						
					}
					
					else if (comprovarCoordenades(filera, columna, barquetes) == 'T')
						
					{
						System.out.print("T");
						
					} 
					else if (comprovarCoordenades(filera, columna, barquetes) == 'N')
						
					{
						System.out.print("B");
						
					}
		
					else System.out.print("·");
			}
			System.out.println();
		}	
			
	}
	
  
  
	public static void aixoEsLaGuerra() 
	// Caràtula ASCII
	{
		
		System.out.println("   _____                           ");
		System.out.println("  / ____|                          ");
		System.out.println(" | |  __ _   _  ___ _ __ _ __ __ _ ");
		System.out.println(" | | |_ | | | |/ _ \\ '__| '__/ _` |");
		System.out.println(" | |__| | |_| |  __/ |  | | | (_| |");
		System.out.println("  \\_____|\\__,_|\\___|_|  |_| _\\__,_|");
		System.out.println("    | \\ | |                | |     ");
		System.out.println("    |  \\| | __ ___   ____ _| |     ");
		System.out.println("    | . ` |/ _` \\ \\ / / _` | |     ");
		System.out.println("    | |\\  | (_| |\\ V / (_| | |     ");
		System.out.println("    |_| \\_|\\__,_| \\_/ \\__,_|_|     \n");
		
	}

	
  
public static String [][] escenariAleatori() 

	{

	/* 
	 * Estructura de la matriu de les barquetes [10][n]
	 * 
	 *  Número de barqueta
	 * 	Caselles: total d'elements ocupats a l'escenari
	 *  Límit de posicionament X i Y damunt de l'escenari
	 *  Estat global: No tocat, Tocat, Enfonsat
	 *  Caselles: entre 1 i 4, amb informació de coordenades i estat
	 *  
	 *  {Número,Caselles,Límit,Estat,Caselles...}
	 */

	// Creem l'escenari definitiu amb informació en blanc
	
	String escenari[][] = {  
				
				{"1", "4","6","N","x","x","x","x"},	
				{"2", "3", "7", "N", "x", "x", "x"},
				{"3", "3", "7", "N", "x", "x", "x"},
				{"4", "2", "8", "N", "x", "x"},
				{"5", "2", "8", "N", "x", "x"},
				{"6", "2", "8", "N", "x", "x"},
				{"7", "1", "9", "N", "x"},
				{"8", "1", "9", "N", "x"},
				{"8", "1", "9", "N", "x"},
				{"10", "1", "9", "N", "x"}
								
			};
				
	
	String [] llista = new String [20];
	
	int limit;
	int parts;
	int posicio;
	int llargada = 0;
	boolean valid;
	String [] coordenades = new String [4];
	
	for (int i = 0 ; i < escenari.length ; i++)
	{
		
		// Busquem coordenades vàlides		
		valid = false;
		
		if (i == 0)
		{
			limit = Integer.parseInt(escenari[i][2]);
			parts = Integer.parseInt(escenari[i][1]);
			
			coordenades = aleatoris(limit, parts);
			
			llista = afegirAlaLlista(llista, llargada, coordenades);
			valid = true;
			
		}
		
		else if (i > 0)
		
		{
		
			while (!valid)
			{
				limit = Integer.parseInt(escenari[i][2]);
				parts = Integer.parseInt(escenari[i][1]);
				
				coordenades = aleatoris(limit, parts);
				
				llargada = calcuLlista(escenari, i);
				
				// Afegim a la llista de control
				if (!comprovarLlista(llista, llargada, coordenades))
					{
					llista = afegirAlaLlista(llista, llargada,coordenades);
					valid = true;						
											
					} 
				
				else
					{
						//System.out.println("Elements repetits");
						valid = false;
					}
			}
		
		}
		
		// Afegim les coordenades a la barqueta
		posicio = 0; // Posició de la coordenada al vector corresponent
		for (int count = 4 ; count < escenari[i].length ; count++)
		{
			escenari[i][count] = coordenades[posicio];
			posicio++;
			
		}
		
	}
	//llistarBarquetes(escenari); //Test
	return escenari;
}


  
public static void llistarBarquetes(String [][] barquetes)
{

for (int i = 0 ; i < barquetes.length ; i++)
{
	System.out.print("\nBarqueta " + i);
	
	for (int count = 0 ; count < barquetes[i].length ; count++)
	{
		System.out.print(" Posició: " + count + "Valor: " + barquetes[i][count]);
		
	}
		
}
}

  

public static int calcuLlista(String [][] model, int barqueta)
{
int llarg = 0;
if (barqueta == 0)
{
	llarg = 0;
	return llarg;
}

else {
	for (int i = 0 ; i < barqueta ; i++ )
	{
		llarg += Integer.parseInt(model[i][1]);
	}
}
return llarg;
}
  
  
  
public static String [] afegirAlaLlista(String [] llistaparcial, int dimensions, String [] coordenades)
{ 


for (int i = 0 ; i < coordenades.length ; i++ )
{
	
	llistaparcial[dimensions+i] = coordenades[i];
	
}

return llistaparcial;

}




public static boolean comprovarLlista(String [] llistaparcialplena, int llarg, String [] coordenades) 

{	
for (int l = 0 ; l < llarg ; l++)
{
	for (int i = 0 ; i < coordenades.length ; i++ )
	{		
		if (llistaparcialplena[l].equals(coordenades[i]))
			{			
			return true;			
			}
	}
}
return false;
}
	


public static String [] aleatoris(int limit, int nums)
{

String [] vector = new String [nums];

int x = (int)(Math.random() * (limit+1)); // posició inicial de 0 a límit       
int y = (int)(Math.random() * (limit+1)); // posició inicial de 0 a límit
int z = (int)(Math.random() * 2); // orientació entre 0 i 1

for (int i = 0 ; i < nums ; i++)
  {
        if (z == 0) {

          vector[i] = Integer.toString(x) + Integer.toString(y+i) + "N"; 

        } else {

        vector[i] = Integer.toString(x+i) + Integer.toString(y) + "N";
      }

    }
    return vector;

  }

}

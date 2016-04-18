package simplex;

public class Simplex{

	private static Simplex instance; 

	private Simplex(){}

	public static Simplex getInstance(){
		if(instance == null){
			instance = new Simplex();
		}

		return instance;
	}
	
	public void start(Expression e) {
		
	}

	private void changeAlgorithm(Cell[][] simplexTable, double allowedElement, int selectedLine, int selectedColumn){
		//Calculates the multiplicative inverse of the allowed element
		double inverse = Math.pow(allowedElement, -1);
		simplexTable[selectedLine][selectedColumn].setBottom(inverse);

		// Multiply each element from the line and column selecteds 
		// by the allowed element and stores on the bottom cell
		for( int i = 0; i < simplexTable.length; i++ ){
			//MULTIPLY LINES
			if( i == selectedColumn ){	// ALLOWED ELEMENT
				continue;
			}else{
				simplexTable[selectedLine][i].setBottom((simplexTable[selectedLine][i].getTop())*inverse);
			}

			//MULTIPLY COLUMNS
			if( i < simplexTable[0].length ){
				if( i == selectedLine ){   // ALLOWED ELEMENT
					continue;
				}else{
					simplexTable[i][selectedColumn].setBottom((simplexTable[i][selectedColumn].getTop())*inverse);
				}
			}
		}

	

	}

}


	//Procurar variavel negativa, na coluna de membro livre
		// Se nao encontra
			//INFACTIVEL
		// Se encontra
			// Varre linha procurando elemento negativo
				// Se não existe
					// INFACTIVEL
				// Se existe 
					// Seleciona a coluna do primeiro que encontra para entrar na coluna de Variaveis Basicas
						// Divide membro livre pelo elemento da coluna selecionada
						// Seleciona a linha de menor quociente para sair da coluna de Variaveis Basicas
						// Seleciona o elemento permitido
						//Chama algoritmo da troca

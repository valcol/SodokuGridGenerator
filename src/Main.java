import java.util.ArrayList;


/*This algorithm creates a Sudoku and notes the difficulty by solving the grid.
 *The solving algorithm use backtracking and count the number of iterations 
  to determinate the difficulty.*/

public class Main {
	
	

	public static void main(String[] args) {
		
		//Valid and filled Sudoku grid
		
		int[][] base = new int[][] {
								  {1,2,3,4,5,6,7,8,9},
								  {4,5,6,7,8,9,1,2,3},
								  {7,8,9,1,2,3,4,5,6},
								  {2,3,4,5,6,7,8,9,1},
								  {5,6,7,8,9,1,2,3,4},
								  {8,9,1,2,3,4,5,6,7},
								  {3,4,5,6,7,8,9,1,2},
								  {6,7,8,9,1,2,3,4,5},
								  {9,1,2,3,4,5,6,7,8}};
		
		createSolvedSudoku(base);
		createUnsolvedSudoku(base);
		Print(base);
		solve(base);
		Print(base);
		
	}
	
	/*Randomly swap rows and columns within the 3×3 borders 
	  and swap individual numbers globally.*/
	 
	public static int[][] createSolvedSudoku(int[][] arr){
		
		for (int l=0;l<100;l++){
			double ran =Math.random();
			if (ran<0.33)
				arr=swapLine(arr);
			else if (ran<0.66)
				arr=swapCol(arr);
			else if (ran<1)
				arr=swapNumber(arr);
		}
		return arr;
	}
	
	/*Erase between 50% and 80% of the numbers to create the unsolved grid
	  with a random difficulty.*/
	
	public static int[][] createUnsolvedSudoku(int[][] arr) {
		
		int r1 = (int) Math.random()*100;
		int r2 = (int) (Math.random()*30)+50;
		for (int i=0; i<9; i++)
		{
			for (int j=0; j<9; j++) 
			{
				if (r1 < r2) 
					arr[i][j] = 0;
				r1 = (int) (Math.random()*100);
			}
		}
		return arr;
	}
	
	/*Simple solving algorithm:
	 * 1.List all empty cells in order.
	 * 2.Take the first empty cell as current cell.
	 * 3.Fill the current cell with the current cell value +1. 
	 * 4.If this number violate the Sudoku condition back to (3), if not 
	 * 	back to (3) with the next cell as current cell.If the number == 9 
	 * 	and still violate the Sudoku condition erase the cell and back to (3)
	  	with the previous cell as current cell.*/
	
	public static int[][] solve(int[][] arr){
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i=0; i<9; i++)
		{
			for (int j=0; j<9; j++) 
			{
				if (arr[j][i] == 0) {
					list.add((j*10)+i);
				}
			}
		}
		
		int it = 0;
		for(int i=0;i<list.size();i++){
			int t = 0;
			int l = list.get(i);
			int jc = l%10;
			int ic = (l-l%10)/10;
			do{
				t = arr[ic][jc]+1;
				arr[ic][jc] = t;
				it++;

			}while(verify(l, arr) == false);
			if (t>9)
				if (i==list.size())
					i=list.size()+1;
				else
				{
					arr[ic][jc] = 0;
					if (i>0)
						i=i-2;
					else
						i=-1;
				}
			else if (l == 88)
				i=list.size()+1;
		}
		
		System.out.println("Number of iterations: "+ it);
		System.out.print("Difficulty rating: ");
		if (it < 1000)
			System.out.println("Easy \n");
		else if (it < 10000)
			System.out.println("Normal \n");
		else if (it < 100000)
			System.out.println("Hard \n");
		else if (it < 1000000)
			System.out.println("Very hard \n");
		else if (it < 10000000)
			System.out.println("Extreme \n");

		System.out.println("Solution: \n");
		
		return arr;
	}
	
	/*Verify if the cell respect the Sudoku condition : there 
	 * can be no repeated numbers in both horizontal and vertical 
	   lines, as well as within the 3x3 squares.*/
	 
			
	private static boolean verify(int l, int[][] arr) {
		
		int j = l%10;
		int i = (l-l%10)/10;
		int ic, jc;
		
		boolean bool = true;
		for (int i1= 0; i1<9;i1++)
			if (i1 != i && arr[i1][j]==arr[i][j])
				bool = false;
		for (int j1= 0; j1<9;j1++)
			if (j1 != j && arr[i][j1]==arr[i][j])
				bool = false;
		
		if (i>=0 && i<3)
			ic=1;
		else if (i>2&&i<6)
			ic=2;
		else
			ic=3;
		if (j>=0 && j<3)
			jc=1;
		else if (j>2&&j<6)
			jc=2;
		else
			jc=3;
		
		for(int i1=ic*3-3;i1<ic*3;i1++){
			for(int j1=jc*3-3;j1<jc*3;j1++){
				if ((j1 != j || i1 != i)  && arr[i1][j1]==arr[i][j])
					bool = false;
			}
		}
		return bool;
	}

	public static int[][] swapLine(int[][] arr) {

		int r = (int)(Math.random()*3)*3;
		int l1= r + (int)(Math.random()*3);
		int l2= r + (int)(Math.random()*3);
	    int[] temp = arr[l1];
	    arr[l1]= arr[l2];
	    arr[l2] = temp;
	    
	    return arr;
	}
	
	public static int[][] swapCol(int[][] arr) {
		
		int temp;
		int r = (int)(Math.random()*3)*3;
		int c1= r + (int)(Math.random()*3);
		int c2= r + (int)(Math.random()*3);
		for (int line=0; line<9; line++){
		    temp = arr[line][c1];
			arr[line][c1] = arr[line][c2];
		    arr[line][c2] = temp;
		}
		return arr;
	}
	
	public static int[][] swapNumber(int[][] arr) {
		
		int r1 = (int) (Math.random()*10);
		int r2 = (int) (Math.random()*10);
		
		for (int i=0; i<9; i++)
		{
			for (int j=0; j<9; j++) 
			{
				if (arr[i][j] == r1) 
					arr[i][j] = r2;
				else 
					if (arr[i][j] == r2) 
						arr[i][j] = r1;
			}
		}
		return arr;
	}
	
	public static void Print(int[][] arr) {

		for(int i=0;i<9;i++){
		    for(int j=0;j<9;j++) {
		        System.out.print(arr[i][j]+" ");
		    }
		    System.out.println("");
		}
		System.out.println("");
	}
}

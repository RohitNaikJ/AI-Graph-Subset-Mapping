import java.io.*;
import java.util.*;
public class Verifier{
	public static void main(String[] args){
		try{
			int f=0;
			FileInputStream fstream = new FileInputStream(args[0]+".graphs");
			Scanner sr = new Scanner(fstream);
			FileInputStream fstream1 = new FileInputStream(args[0]+".satoutput");
			Scanner sr1 = new Scanner(fstream1);
			String s1 = "";
			String s2 = "";
			while(sr.hasNext()){
				String t = sr.nextLine();
				if(t.startsWith("0")){
					f = 1;
				}
				else{
					if(f == 0){
						s1 = s1+t+",";
					}
					else{
						s2 = s2+t+",";
					}
				}
			}
			s1 = s1.substring(0,s1.length()-1);
			s2 = s2.substring(0,s2.length()-1);
			if(s1.length()<s2.length()){
				
			}
			else{
				String temp = s1;
				s1 = s2;
				s2 = temp;
			}

			graphChecker gc = new graphChecker(s1,s2, args[0]);
			String sat = sr1.nextLine();
			if(sat.equalsIgnoreCase("UNSAT")){
				FileOutputStream ostream = new FileOutputStream(args[0]+".mapping");
				PrintStream pr = new PrintStream(ostream);
				pr.println("0");
	            System.exit(0);
			}
			String x = sr1.nextLine();
			x = x.substring(0,x.length()-2);
			String[] y = x.split(" ");
			int[][] fm = new int[gc.m.length][gc.m.length];
			for(int i = 0;i<y.length;i++){
				if(y[i].startsWith("-")){
					fm[i/gc.m.length][i%gc.m.length] = 0;
				}
				else{
					fm[i/gc.m.length][i%gc.m.length] = 1;	
				}
			}

			String z = "";
			for(int i =0;i<gc.m1.length;i++){
				for(int j=0;j<gc.m2.length;j++){
					if(fm[i][j+gc.m1.length]==1){
						z = z+(i+1)+" "+(j+1)+"\n";
					}
				}	
			}
			z = z.substring(0,z.length()-1);
			// System.out.print(z);

			FileOutputStream ostream = new FileOutputStream(args[0]+".mapping");
			PrintStream pr = new PrintStream(ostream);
			pr.print(z);
		}
		catch(FileNotFoundException e){
			System.out.println("ERROR: FileNotFound");
		}
	}
}

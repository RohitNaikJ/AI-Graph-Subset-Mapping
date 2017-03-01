import java.io.*;
import java.util.*;
public class Checker{
	public static void main(String[] args){
		try{
			int f=0;
			FileInputStream fstream = new FileInputStream(args[0]+".graphs");
			Scanner sr = new Scanner(fstream);
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
			
			
			
			graphChecker gc = new graphChecker(s1,s2,args[0]);
			gc.generateCNF();
			//String x = gc.generateCNF();
			// x = x.substring(0,x.length()-1);
			// String[] y = x.split(" 0\n");
			// x = "p cnf "+(gc.m.length*gc.m.length)+" "+y.length+"\n"+x;
			// // System.out.println(x);
			// FileOutputStream ostream = new FileOutputStream(args[0]+".satinput");
			// PrintStream pr = new PrintStream(ostream);
			// pr.println(x);
		}
		catch(FileNotFoundException e){
			System.out.println("ERROR: FileNotFound");
		}
	}
}

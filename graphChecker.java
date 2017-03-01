import java.util.*;
import java.io.*;
public class graphChecker{
	String s1;
	String s2;
	int[][] m1;
	int[][] m2;
	int[][] m;
	FileOutputStream ostream;
	PrintStream pr;
	
	public graphChecker(String p,String q,String inp){
		s1 = p;
		s2 = q;
		m1 = creatematrix(p);
		m2 = creatematrix(q);
		m = creatematrix(p,q);
		try{
			ostream = new FileOutputStream(inp+".satinput");
			pr = new PrintStream(ostream);
		}
		catch(FileNotFoundException e){

		}
	}
	
	public int getID(int p, int q){
		return (m.length*(p-1))+(q);
	}
	
	public void generateCNF(){
		pr.println("c pnf "+(m.length*m.length)+" ");
		defineEdges(m1,1);
		System.out.println("1");
		defineEdges(m2,2);
		System.out.println("2");
		defBidi();
		System.out.println("3");
		defOneOne();
		System.out.println("4");
		minOneMap();
		System.out.println("5");
		MapGraphs2();
		System.out.println("6");
	}

	public void MapGraphs2(){
		for(int i=0;i<m1.length;i++){
			for(int j=0;j<m1.length;j++){
				if(j!=i){
					if(m1[i][j]==1){
						for(int k=0;k<m2.length;k++){
							int ev = getID(i+1,k+m1.length+1);
							for(int p=0;p<m2.length;p++){
								if(p!=k){
									pr.println("-" +ev+" -"+getID(j+1, p+m1.length+1)+" "+getID(k+m1.length+1,p+m1.length+1)+" 0");
								}
							}
						}
					}
					else{
						if(m1[i][j]==0){
							for(int k=0;k<m2.length;k++){
								int es = getID(i+1,k+m1.length+1);
							for(int p=0;p<m2.length;p++){
								if(p!=k){
									pr.println("-" + es +" -"+getID(j+1, p+m1.length+1)+" -"+getID(k+m1.length+1,p+m1.length+1)+" 0");
								}
							}
						}		
						}
					}
				}
			}
		}
	}
	
	public void MapGraphs(){
		for(int i=0;i<m1.length;i++){
			for(int j=0;j<m1[0].length;j++){
				if(j!=i){
					if(m1[i][j]==1){
					for(int k=0;k<m2.length;k++){
						int[][] internal = new int[m2.length-1][2];
						for(int p=0;p<m1.length;p++){
							if(p!=i){
								int t = 0;
								for(int q=0;q<m2.length;q++){
									
									if(q!=k){
										internal[t][0] = getID(p+1,q+m1.length+1);
										internal[t][1] = -1*getID(k+m1.length+1,q+m1.length+1);
										t = t+1;
									}
								}
							}
						}
	
						breakToCNF(internal,getID(i+1,k+m1.length+1));
					}
				}
				else{
					for(int k=0;k<m2.length;k++){
						int[][] internal = new int[m2.length-1][2];
						for(int p=0;p<m1.length;p++){
							if(p!=i){
								int t=0;
								for(int q=0;q<m2.length;q++){
									if(q!=k){
										if(q!=k){
											internal[t][0] = getID(p+1,q+m1.length+1);
											internal[t][1] = getID(k+m1.length+1,q+m1.length+1);
											t = t+1;
										}									}
								}
							}
						}
						breakToCNF(internal,getID(i+1,k+m1.length+1));
					}
				}
				}
				
			}
		}
	}
	
	public void breakToCNF(int[][] a, int x){
		ArrayList<String> ans = run(a, 0);
		String ret = "";
		for(int i=0; i<ans.size(); i++)
			pr.println(ans.get(i)+" "+x+" 0");			
	}
	
    public static ArrayList<String> run(int[][] n, int i){
        if(i==n.length-1){
            ArrayList<String> str = new ArrayList();
            str.add(n[i][0]+"");
            str.add(n[i][1]+"");
            return str;
        }
        ArrayList<String> str_sub = run(n, i+1);
        ArrayList<String> str = new ArrayList();
        for(int k=0; k<2; k++){
            for(int l=0; l<str_sub.size(); l++)
                str.add(n[i][k]+" "+str_sub.get(l));
        }
        return str;
    }
	
	public void minOneMap(){
		for(int i=0;i<m1.length;i++){
			String x = "";
			for(int j=0;j<m2.length;j++){
				x = x + getID(i+1,j+m1.length+1)+" ";
			}
			pr.println(x +"0");
		}
	}
	
	public void defOneOne(){
		for(int i=0;i<m1.length;i++){
			for(int j=0;j<m2.length;j++){
				for(int k = 0;k<m1.length;k++){
					if(k!=i){
						pr.println("-"+getID(i+1,j+m1.length+1)+" "+"-"+getID(k+1,j+m1.length+1)+" 0");
					}
				}
				// for(int k = 0;k<m2.length;k++){
				// 	if(k!=j){
				// 		ret = ret+"-"+getID(i+1,j+m1.length+1)+" "+"-"+getID(i+1,k+m1.length+1)+" 0\n";
				// 	} 
				// }
			}
		}
	}
	
	public void defBidi(){
		for(int i=0;i<m1.length;i++){
			for(int j=0;j<m2.length;j++){
				pr.println("-"+getID(i+1,m1.length+j+1)+" "+getID(m1.length+j+1,i+1)+" 0");
				pr.println("-"+getID(m1.length+j+1,i+1)+" "+getID(i+1,m1.length+j+1)+" 0");
			}
		}
	}
	
	public void defineEdges(int[][] a,int x){
		if(x==1){
			for(int i=0;i<a.length;i++){
				for(int j=0;j<a[0].length;j++){
					if(a[i][j]==1){
						pr.println(getID(i+1,j+1)+" 0");
					}
					else{
						if(a[i][j]==0){
							pr.println("-"+getID(i+1,j+1)+" 0");
						}
					}
				}
			}
		}
		else{
			for(int i=0;i<a.length;i++){
				for(int j=0;j<a[0].length;j++){
					if(a[i][j]==1){
						pr.println(getID(m1.length+i+1,m1.length+j+1)+" 0");
					}
					else{
						if(a[i][j]==0){
							pr.println("-"+getID(m1.length+i+1,m1.length+j+1)+" 0");
						}
					}
				}
			}
		}
	}
	
	public int[][] creatematrix(String p){
		String[] s = p.split(",");
		//String[] t = new String[maxIntIn(p)];
		int a = maxIntIn(p);
		int[][] ret = new int[a][a];
		for(int i=0;i<s.length;i++){
			String[] x = s[i].split(" ");
			ret[Integer.parseInt(x[0])-1][Integer.parseInt(x[1])-1] = 1;
		}
		return ret;
	}
	
	public int[][] creatematrix(String p,String q){
		int a = maxIntIn(p);
		String[] s = q.split(",");
		String r = "";
		for(int i=0;i<s.length;i++){
			String[] x = s[i].split(" ");
			int[] y = new int[2];
			y[0] = Integer.parseInt(x[0])+a;
			y[1] = Integer.parseInt(x[1])+a;
			r = r+y[0]+" "+y[1]+",";
		}
		r = r.substring(0,r.length()-1);
		return creatematrix(p+","+r);
	}
	
	public int maxIntIn(String p){
		String[] s = p.split(",");
		int a = Integer.parseInt(p.substring(0,1));
		for(int i=0;i<s.length;i++){
			String[] x = s[i].split(" ");
			if(Integer.parseInt(x[0])>a){
				a = Integer.parseInt(x[0]);
			}
			if(Integer.parseInt(x[1])>a){
				a = Integer.parseInt(x[1]);
			}
		}
		return a;
	}
}

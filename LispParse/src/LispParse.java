/**
 * Java program to accept String values (assumed
 *   pasted Lisp code lines) to validate their
 *   parenthetical nesting symmetry.
 * */
import java.io.*;
public class LispParse {
	public static void main(String[]args)throws IOException {
		BufferedReader rd=
		  new BufferedReader(new InputStreamReader(System.in));
		String xs="";String line="";
		
		System.out.println("Paste lines of code to "
		  +"validate proper nesting (:q to quit):");
		while((line=rd.readLine())!=null) {
			if(!line.equalsIgnoreCase(":q")) {xs+=line;}
			else {break;}
		}
		System.out.println("Exited.");
		
		int lP=0;int rP=0;
		for(int i=0;i<xs.length();i++) {
			if( xs.charAt(i)=='('  ) {++lP;}
			if(xs.charAt(i)==')') {++rP;}
		}
		if(lP==rP) {System.out.println("Valid parentheses nesting.");}
		else {System.out.println("Invalid nesting.");}
	}
}

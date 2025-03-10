import StringBuilder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Tokenizer{
    List<String> tokens;
    StringBuilder sb;

    public static string muestraContenido(String archivo) throws FileNotFoundException, IOException { 
    	String cadena; 
        FileReader f = new FileReader(archivo); 
        BufferedReader b = new BufferedReader(f); 
        while((cadena = b.readLine())!=null) { 
        	return(cadena); 
        } 
        b.close();
        
	} 

    public List<String> tokenize(String texto){
        for(int i = 0; i < texto.length(); i++){
            char c = s.charAt(i);
            if(Character.isLetter(c)){
                sb.append(c);
            }else{
                if(sb.length() > 0){
                    tokens.add(sb.toString());
                    sb.setLength(0);
                }
            }
        }
        if(sb.length() > 0){
            tokens.add(sb.toString());
        }
        return tokens;
    }   
}

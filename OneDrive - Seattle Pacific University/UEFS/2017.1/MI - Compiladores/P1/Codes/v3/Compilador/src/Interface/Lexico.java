/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import AnalisadorLexico.Leitura;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Paty, Emanuel
 */
public class Lexico {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        
        Scanner s = new Scanner("entrada");
        File arquivos[];        
        //Lê todos os arquivos do diretorio.
        File diretorio = new File(s.nextLine());
        arquivos = diretorio.listFiles();
        
        for (File arquivo : arquivos) {
            System.out.println(arquivo.getAbsolutePath());
            Leitura ler = new Leitura();
            ler.fazerLeitura(arquivo);
        }   
    }  
}

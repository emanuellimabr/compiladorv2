/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import AnalisadorLexico.Leitura;
import java.io.IOException;

/**
 *
 * @author Paty, Emanuel
 */
public class Lexico {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String diretorio = "entrada.txt"; // Caminho para o arquivo 
        Leitura ler = new Leitura();
        ler.fazerLeitura(diretorio);   
    }  
}

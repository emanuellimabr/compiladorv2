/*
 * Realiza a leitura do arquivo.
 */
package AnalisadorLexico;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Paty, Emanuel, Jadna
 */
public class Leitura {

    private File arquivo;
    private Comparacao comparacao;
    private List<Token> tokens;
    private List<String> palavrasReservadas;
    private List<String> digitos;
    private List<String> letras;
    private List<String> operadoresAritmeticos;
    private List<String> operadoresRelacionais;
    private List<String> operadoresLogicos;
    private List<String> delimitadoresComentarios;
    private List<String> delimitadores;
    private List<String> simbolos;
    private String entrada = ""; //arquivo a ser lido.

    /* --------------------------------------------------------------------------------------------
     * O método abaixo carrega o arquivo a ser analisado e chama os métodos que carregam as listas.
     */
    public void fazerLeitura(String diretorio) throws FileNotFoundException, IOException {
        tokens = new ArrayList<Token>();
        arquivo = new File(diretorio);
        Scanner ler;
        ler = new Scanner(new FileReader(arquivo));
        while (ler.hasNextLine()) {
            String linhas = ler.nextLine();
            if (!(linhas.equals(""))) {
                entrada = entrada + linhas + "\n";
            }
        }
        //System.out.println(entrada);
        ler.close();

        criarListaLetras();
        criarListaDigitos();
        criarListaPalavrasReservadas();
        criarListaOperadoresAritmeticos();
        criarListaOperadoresRelacionais();
        criarListaOperadoresLogicos();
        criarListaDelimitadoresComentario();
        criarListaDelimitadores();
        criarListaSimbolos();
        verificarTokens();
    }

    /* --------------------------------------------------------------------------------------------
     * O método abaixo realiza a verificação de cada token.
     */
    public void verificarTokens() {
        comparacao = new Comparacao();
        char[] caractere = null;
        int i = 0;
        int j = 0;
        String[] linha = entrada.split("\n");
        //System.out.println(linha[0]);
        while (i < linha.length) {
            caractere = linha[i].toCharArray(); //forma um arrray de caracter
            //System.out.println(caractere[i]);
            while (j < caractere.length) {
                //System.out.println(caractere[j]);

                if (letras.contains("" + caractere[j])) { //Verifica se é palavra reservada ou identficador
                    int aux = j;
                    j = comparacao.verificarPalavraReservada(j, caractere, letras, palavrasReservadas, i, tokens);
                    if (aux == j) {
                        j = comparacao.verificarIdentificador(j, caractere, letras, digitos, i, tokens);
                    }
                } else if (digitos.contains("" + caractere[j])) { //Verifica se é um número
                    j = comparacao.verificarNumero(j, caractere, digitos, i, tokens);
                } else if (caractere[j] == '-') { //Verifica se é um número ou um operador aritmético
                    int aux2 = j;
                    j = comparacao.verificarNumero(j, caractere, digitos, i, tokens);
                    if (aux2 == j) {
                        j = comparacao.verificarOperadoresAritmeticos(j, caractere, i, tokens);
                    }
                } else if (operadoresAritmeticos.contains("" + caractere[j])) {//verifica se é operador aritmético ou comentário
                    if (caractere[j] == '/') {
                        int aux3 = j;
                        j = comparacao.verificarOperadoresAritmeticos(j, caractere, i, tokens);
                        if (aux3 == j) {
                            j = comparacao.verificarDelimitadorCometarios(j, caractere, i, tokens);
                        } else {
                            j = comparacao.verificarOperadoresAritmeticos(j, caractere, i, tokens);
                        }
                    }
                } else if (operadoresRelacionais.contains("" + caractere[j])) {//Verifica se é operador relacional ou lógico (!)
                    if (caractere[j] == '!') {
                        int aux4 = j;
                        j = comparacao.verificarOperadoresRelacionais(j, caractere, i, tokens);
                        if (aux4 == j) {
                            j = comparacao.verificarOperadoresLogicos(j, caractere, operadoresLogicos, i, tokens);
                        }
                    } else {
                        j = comparacao.verificarOperadoresRelacionais(j, caractere, i, tokens);
                    }
                } else if (operadoresLogicos.contains("" + caractere[j])) {
                    j = comparacao.verificarOperadoresLogicos(j, caractere, operadoresLogicos, i, tokens);
                } else if (delimitadores.contains("" + caractere[j])){
                    j = comparacao.verificarDelimitadores(j, caractere, i, delimitadores, tokens);
                } else if(caractere[j] == '"'){
                    j = comparacao.verificarCadeiaCaracteres(j, caractere, letras, digitos, simbolos, i, tokens);
                }else if (caractere[j] == '\''){
                    j = comparacao.verificarCaractere(j, caractere, i, letras, digitos, tokens);
                } else {
                    if(caractere[j] != ' ' && caractere[j] != '\t'){
                        Token token = new Token("Não Pertence à Linguagem", caractere[j]+"");
                        tokens.add(token);
                        token.setLinha(i+1);
                    }

                    j++;//incrementa os caracteres
                }

            }//fecha o laço do j
            i++; //incrementa as linhas
        }
    }

    /* --------------------------------------------------------------------------------------------
     * Os métodos abaixo carregam as listas com os dados dos arquivos de nome correpondente.
     */
    public void criarListaLetras() throws FileNotFoundException {
        letras = new ArrayList<String>();
        arquivo = new File("letras.txt");
        Scanner ler;
        ler = new Scanner(new FileReader(arquivo));
        while (ler.hasNextLine()) {
            String linha = "";
            linha = ler.nextLine();
            if (!(linha.equals(""))) {
                letras.add(linha);
            }
        }
        //System.out.println(letras);
        ler.close();
    }

//_________________________________________________________________________________________________________________    
    public void criarListaDigitos() throws FileNotFoundException {
        digitos = new ArrayList<String>();
        arquivo = new File("digitos.txt");
        Scanner ler;
        ler = new Scanner(new FileReader(arquivo));
        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            if (!(linha.equals(""))) {
                digitos.add(linha);
            }
        }
        //System.out.println(digitos);
        ler.close();
    }

//_________________________________________________________________________________________________________________    
    public void criarListaPalavrasReservadas() throws FileNotFoundException {
        palavrasReservadas = new ArrayList<String>();
        arquivo = new File("palavrasReservadas.txt");
        Scanner ler;
        ler = new Scanner(new FileReader(arquivo));
        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            if (!(linha.equals(""))) {
                palavrasReservadas.add(linha);
            }
        }
        //System.out.println(palavrasReservadas);
        ler.close();
    }

//_________________________________________________________________________________________________________________    
    public void criarListaOperadoresAritmeticos() throws FileNotFoundException {
        operadoresAritmeticos = new ArrayList<String>();
        arquivo = new File("operadoresAritmeticos.txt");
        Scanner ler;
        ler = new Scanner(new FileReader(arquivo));
        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            if (!(linha.equals(""))) {
                operadoresAritmeticos.add(linha);
            }
        }
        //System.out.println(operadoresAritmeticos);
        ler.close();
    }

//_________________________________________________________________________________________________________________    
    public void criarListaOperadoresRelacionais() throws FileNotFoundException {
        operadoresRelacionais = new ArrayList<String>();
        arquivo = new File("operadoresRelacionais.txt");
        Scanner ler;
        ler = new Scanner(new FileReader(arquivo));
        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            if (!(linha.equals(""))) {
                operadoresRelacionais.add(linha);
            }
        }
        //System.out.println(operadoresRelacionais);
        ler.close();
    }

//_________________________________________________________________________________________________________________    
    public void criarListaOperadoresLogicos() throws FileNotFoundException {
        operadoresLogicos = new ArrayList<String>();
        arquivo = new File("operadoresLogicos.txt");
        Scanner ler;
        ler = new Scanner(new FileReader(arquivo));
        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            if (!(linha.equals(""))) {
                operadoresLogicos.add(linha);
            }
        }
        //System.out.println(operadoresLogicos);
        ler.close();
    }

//_________________________________________________________________________________________________________________    
    public void criarListaDelimitadoresComentario() throws FileNotFoundException {
        delimitadoresComentarios = new ArrayList<String>();
        arquivo = new File("delimitadoresComentarios.txt");
        Scanner ler;
        ler = new Scanner(new FileReader(arquivo));
        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            if (!(linha.equals(""))) {
                delimitadoresComentarios.add(linha);
            }
        }
        //System.out.println(delimitadoresComentarios);
        ler.close();
    }

//_________________________________________________________________________________________________________________    
    public void criarListaDelimitadores() throws FileNotFoundException {
        delimitadores = new ArrayList<String>();
        arquivo = new File("delimitadores.txt");
        Scanner ler;
        ler = new Scanner(new FileReader(arquivo));
        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            if (!(linha.equals(""))) {
                delimitadores.add(linha);
            }
        }
        //System.out.println(delimitadores);
        ler.close();
    }

//_________________________________________________________________________________________________________________    
    public void criarListaSimbolos() throws FileNotFoundException {
        simbolos = new ArrayList<String>();
        arquivo = new File("simbolos.txt");
        Scanner ler;
        ler = new Scanner(new FileReader(arquivo));
        while (ler.hasNextLine()) {
            String linha = ler.nextLine();
            if (!(linha.equals(""))) {
                simbolos.add(linha);
            }
        }
        //System.out.println(simbolos);
        ler.close();
    }
}

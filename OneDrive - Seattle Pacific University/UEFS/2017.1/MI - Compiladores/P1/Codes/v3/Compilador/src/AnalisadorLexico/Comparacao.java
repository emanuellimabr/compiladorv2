/*
 * Realiza a comparação para verificar se a sentença é válida e em caso afirmativo 
 * adiciona na lista de tokens.
 */
package AnalisadorLexico;

import java.util.List;

/**
 *
 * @author Paty, Emanuel, Jadna
 */
public class Comparacao {

    public int verificarPalavraReservada(int j, char[] caractere, List<String> letras, List<String> palavrasReservadas, int linha, List<Token> tokens) {
        String palavra = "";
        int J = j;
        while (J < caractere.length) {
            if (letras.contains(caractere[J] + "")) {
                palavra = palavra + caractere[J];
                J++;
            } else {
                break;
            }
        }
        if (palavrasReservadas.contains(palavra)) {
            Token token = new Token("Palavra Reservada", palavra);
            token.setLinha(linha + 1);
            tokens.add(token);
            return J;
        } else {
            return j; //retorna isso para verificar se é identificador
        }
    }
//_________________________________________________________________________________________________________________

    public int verificarIdentificador(int j, char[] caractere, List<String> letras, List<String> digitos, int linha, List<Token> tokens) {
        String palavra = "";
        boolean erro = false;
        int J = j;
        if (letras.contains(caractere[J] + "")) {
            palavra = palavra + caractere[J];
            J = J + 1;
            //System.out.println(caractere[J]);
            while (J < caractere.length) {
                if (letras.contains(caractere[J] + "") || digitos.contains(caractere[J] + "") || caractere[J] == '_') {
                    palavra = palavra + caractere[J];
                    J++;
                } else {
                    palavra = palavra + caractere[J];
                    erro = true;
                    break; //erro de identificador mal formado
                }
            }
        }
        if (erro == true) {
            //System.out.println(palavra);
            Token token = new Token("Identificador Mal Formado", palavra);
            token.setLinha(linha + 1);
            tokens.add(token);
            return J;
        }

        //System.out.println(palavra);
        Token token = new Token("Identificador", palavra);
        token.setLinha(linha + 1);
        tokens.add(token);
        return J;
    }

//_________________________________________________________________________________________________________________    
    public int verificarNumero(int j, char[] caractere, List<String> digitos, int linha, List<Token> tokens) {
        String palavra = "";
        boolean erro = false;
        int J = j;
        int aux = J + 1;
        if (digitos.contains(caractere[J] + "")) {
            palavra = palavra + caractere[J];
            J = J + 1;
            while (J < caractere.length) {
                if (digitos.contains(caractere[J] + "")) {
                    palavra = palavra + caractere[J];
                    J++;
                } else if (caractere[J] == '.' && digitos.contains(caractere[aux] + "")) {
                    palavra = palavra + caractere[J];
                    J++;
                } else {
                    palavra = palavra + caractere[J];
                    erro = true;
                    break; //Aqui é erro de número mal formado
                }
            }
        } else if (caractere[J] == '-') {
            palavra = palavra + caractere[J];
            J = J + 1;
            while (J < caractere.length) {
                if (caractere[J] == ' ') {
                    palavra = palavra + caractere[J];//se for ora desprezar os espaços comento essa linha.
                    J++;
                } else if (digitos.contains(caractere[J] + "")) {
                    palavra = palavra + caractere[J];
                    J = J + 1;
                    while (J < caractere.length) {
                        if (digitos.contains(caractere[J] + "")) {
                            palavra = palavra + caractere[J];
                            J++;
                        } else if (caractere[J] == '.' && digitos.contains(caractere[aux] + "")) {
                            palavra = palavra + caractere[J];
                            J++;
                        } else {
                            palavra = palavra + caractere[J];
                            erro = true;
                            break; //erro de número mal formado
                        }
                    }
                } else {
                    //break; 
                    return j; // retorna o j para o método que forma token operador aritmético
                }
            }
        }
        if (erro == true) {
            //System.out.println(palavra);
            Token token = new Token("Número Mal Formado", palavra);
            token.setLinha(linha + 1);
            tokens.add(token);
            return J;
        }
        //System.out.println(palavra);
        Token token = new Token("Número", palavra);
        token.setLinha(linha + 1);
        tokens.add(token);
        return J;
    }

//_________________________________________________________________________________________________________________
    public int verificarOperadoresAritmeticos(int j, char[] caractere, int linha, List<Token> tokens) {
        String palavra = "";
        int J = j;
        if (caractere[J] == '+' || caractere[J] == '-' || caractere[J] == '*' || caractere[J] == '%') {
            palavra = palavra + caractere[J];
            Token token = new Token("Operadores Aritméticos", palavra);
            token.setLinha(linha + 1);
            tokens.add(token);
            J = J + 1;
        } else if (caractere[J] == '/') {
            palavra = palavra + caractere[J];
            J = J + 1;
            if (caractere[J] == '/' || caractere[J] == '*') {
                return j; //retorna para verificar se é comentário
            } else {
                Token token = new Token("Operadores Aritméticos", palavra);
                token.setLinha(linha + 1);
                tokens.add(token);
                return J;
            }
        }
        return J;
    }

//_________________________________________________________________________________________________________________    
    public int verificarOperadoresRelacionais(int j, char[] caractere, int linha, List<Token> tokens) {
        String palavra = "";
        int J = j;
        if (caractere[J] == '=') {
            palavra = palavra + caractere[J];
            Token token = new Token("Operadores Relacionais", palavra);
            token.setLinha(linha + 1);
            tokens.add(token);
            J = J + 1;
        } else if (caractere[J] == '!') {
            palavra = palavra + caractere[J];
            J = J + 1;
            if (caractere[J] == '=') {
                palavra = palavra + caractere[J];
                Token token = new Token("Operadores Relacionais", palavra);
                token.setLinha(linha + 1);
                tokens.add(token);
                J = J + 1;
            } else if (caractere[J] == ' ') {
                while (J < caractere.length) {
                    if (caractere[J] == ' ') {
                        J++;
                    } else if (caractere[J] == '=') {
                        palavra = palavra + caractere[J];
                        Token token = new Token("Operadores Relacionais", palavra);
                        token.setLinha(linha + 1);
                        tokens.add(token);
                        J = J + 1;
                    } else {
                        J = j;
                        break;
                    }
                }
            } else {
                J = j;//envia o j para verificar se é operador lógico
            }
        } else if (caractere[J] == '<') {
            palavra = palavra + caractere[J];
            J = J + 1;
            if (caractere[J] == '=') {
                palavra = palavra + caractere[J];
                Token token = new Token("Operadores Relacionais", palavra);
                token.setLinha(linha + 1);
                tokens.add(token);
                J = J + 1;
            } else if (caractere[J] == ' ') {
                while (J < caractere.length) {
                    if (caractere[J] == ' ') {
                        J++;
                    } else if (caractere[J] == '=') {
                        palavra = palavra + caractere[J];
                        Token token = new Token("Operadores Relacionais", palavra);
                        token.setLinha(linha + 1);
                        tokens.add(token);
                        J = J + 1;
                    } else {
                        Token token = new Token("Operadores Relacionais", palavra);
                        token.setLinha(linha + 1);
                        tokens.add(token);
                        J = J + 1;
                        break;
                    }
                }
            } else {
                Token token = new Token("Operadores Relacionais", palavra);
                token.setLinha(linha + 1);
                tokens.add(token);
                J = J + 1;
            }
        } else if (caractere[J] == '>') {
            palavra = palavra + caractere[J];
            J = J + 1;
            if (caractere[J] == '=') {
                palavra = palavra + caractere[J];
                Token token = new Token("Operadores Relacionais", palavra);
                token.setLinha(linha + 1);
                tokens.add(token);
                J = J + 1;
            } else if (caractere[J] == ' ') {
                while (J < caractere.length) {
                    if (caractere[J] == ' ') {
                        J++;
                    } else if (caractere[J] == '=') {
                        palavra = palavra + caractere[J];
                        Token token = new Token("Operadores Relacionais", palavra);
                        token.setLinha(linha + 1);
                        tokens.add(token);
                        J = J + 1;
                    } else {
                        Token token = new Token("Operadores Relacionais", palavra);
                        token.setLinha(linha + 1);
                        tokens.add(token);
                        J = J + 1;
                        break;
                    }
                }
            } else {
                Token token = new Token("Operadores Relacionais", palavra);
                token.setLinha(linha + 1);
                tokens.add(token);
                J = J + 1;
            }
        }
        return J;
    }

//_________________________________________________________________________________________________________________    
    public int verificarOperadoresLogicos(int j, char[] caractere, List<String> operadoresLogicos, int linha, List<Token> tokens) {
        String palavra = "";
        int J = j;
        if (caractere[J] == '!') {
            Token token = new Token("Operadores Lógicos", palavra);
            token.setLinha(linha + 1);
            tokens.add(token);
            J = J + 1;
        } else if (caractere[J] == '&') {
            palavra = palavra + caractere[J];
            J = J + 1;
            if (caractere[J] == '&') {
                palavra = palavra + caractere[J];
                Token token = new Token("Operadores Lógicos", palavra);
                token.setLinha(linha + 1);
                tokens.add(token);
                J = J + 1;
            } else if (caractere[J] == ' ') {
                while (J < caractere.length) {
                    if (caractere[J] == ' ') {
                        J++;
                    } else if (caractere[J] == '&') {
                        palavra = palavra + caractere[J];
                        Token token = new Token("Operadores Lógicos", palavra);
                        token.setLinha(linha + 1);
                        tokens.add(token);
                        J = J + 1;
                    } else {
                        palavra = palavra + caractere[J];
                        Token token = new Token("Operadore Lógico Mal Formado", palavra);
                        token.setLinha(linha + 1);
                        tokens.add(token);
                        J = J + 1;
                        break;
                    }
                }
            } else { //má formação de operador.
                palavra = palavra + caractere[J];
                Token token = new Token("Operadore Lógico Mal Formado", palavra);
                token.setLinha(linha + 1);
                tokens.add(token);
                J = J + 1;
            }
        } else if (caractere[J] == '|') {
            palavra = palavra + caractere[J];
            J = J + 1;
            if (caractere[J] == '|') {
                palavra = palavra + caractere[J];
                Token token = new Token("Operadores Lógicos", palavra);
                token.setLinha(linha + 1);
                tokens.add(token);
                J = J + 1;
            } else if (caractere[J] == ' ') {
                while (J < caractere.length) {
                    if (caractere[J] == ' ') {
                        J++;
                    } else if (caractere[J] == '|') {
                        palavra = palavra + caractere[J];
                        Token token = new Token("Operadores Lógicos", palavra);
                        token.setLinha(linha + 1);
                        tokens.add(token);
                        J = J + 1;
                    } else {
                        palavra = palavra + caractere[J];
                        Token token = new Token("Operadore Lógico Mal Formado", palavra);
                        token.setLinha(linha + 1);
                        tokens.add(token);
                        J = J + 1;
                        break;
                    }
                }
            } else {//má formação de operador.
                palavra = palavra + caractere[J];
                Token token = new Token("Operadore Lógico Mal Formado", palavra);
                token.setLinha(linha + 1);
                tokens.add(token);
                J = J + 1;
            }
        }
        return J;
    }

//_________________________________________________________________________________________________________________     
   /* public int verificaDelimitadorComentarioLinha(int j, char[] caractere, int linha, List<Token> tokens){
     int J = j;
     boolean encontrou = false;
     String palavra = "";
     if (caractere[J] == '/') {
     palavra = palavra + caractere[J];
     J = J + 1;
     if (caractere[J] == '/') {
     palavra = palavra + caractere[J];
     J = J + 1;
     while (J < caractere.length) {
     if (caractere[J] == '\n') {
     palavra = palavra + caractere[J];
     encontrou = true;
     J = J + 1;
     break;
     } else {
     palavra = palavra + caractere[J];
     J++;
     }
     }
     }
     }
     palavra = palavra + caractere[J];
     Token token = new Token("Comentário de Linha", palavra);
     token.setLinha(linha + 1);
     tokens.add(token);
     return J;         
     }
    
     //_________________________________________________________________________________________________________________     
     public int verificaDelimitadorComentarioBloco(){
     return 0;
     }*/
//_________________________________________________________________________________________________________________     
    public int verificarDelimitadorCometarios(int j, char[] caractere, int linha, List<Token> tokens) {
        int J = j;
        boolean encontrouLinha = false;
        boolean encontrouBloco = false;
        String palavra = "";
        if (caractere[J] == '/') {
            palavra = palavra + caractere[J];
            J = J + 1;
            if (caractere[J] == '/') {
                palavra = palavra + caractere[J];
                J = J + 1;
                while (J < caractere.length) {
                    if (caractere[J] == '\n') {
                        palavra = palavra + caractere[J];
                        encontrouLinha = true;
                        J = J + 1;
                        break;
                    } else {
                        palavra = palavra + caractere[J];
                        J++;
                    }
                }
            } else if (caractere[J] == '*') {
                palavra = palavra + caractere[J];
                J = J + 1;
                while (J < caractere.length) {
                    if (caractere[J] == '*') {
                        palavra = palavra + caractere[J];
                        J = J + 1;
                        if (caractere[J] == '/') {
                            palavra = palavra + caractere[J];
                            encontrouBloco = true;
                            J = J + 1;
                            break;
                        } else { // Comentário mal formado
                            palavra = palavra + caractere[J];
                            Token token = new Token("Comentário Mal Formado", palavra);
                            token.setLinha(linha + 1);
                            tokens.add(token);
                            break;
                            //preciso ver o que retorno em caso de comentário mal formado 
                        }
                    } else {
                        palavra = palavra + caractere[J];
                        J++;
                    }
                }
            }
            if (encontrouLinha == true) {
                palavra = palavra + caractere[J];
                Token token = new Token("Comentário de Linha", palavra);
                token.setLinha(linha + 1);
                tokens.add(token);
                return J;
            }

            if (encontrouBloco == true) {
                palavra = palavra + caractere[J];
                Token token = new Token("Comentário de Bloco", palavra);
                token.setLinha(linha + 1);
                tokens.add(token);
                return J;
            }
        }
        palavra = palavra + caractere[J];
        Token token = new Token("Comentário Mal Formado", palavra);
        token.setLinha(linha + 1);
        tokens.add(token);
        return J;    //preciso ver o que retorno em caso de comentário mal formado 
    }

//_________________________________________________________________________________________________________________    
    public int verificarDelimitadores(int j, char[] caractere, int linha, List<String> delimitadores, List<Token> tokens) {
        String palavra = "";
        int J = j;
        if (delimitadores.contains(caractere[J] + "")) {
            palavra = palavra + caractere[J];
            Token token = new Token("Delimitadores", palavra);
            token.setLinha(linha + 1);
            tokens.add(token);
            return J + 1;
        }
        return J;
    }

//_________________________________________________________________________________________________________________    
    public int verificarCadeiaCaracteres(int j, char[] caractere, List<String> letras, List<String> digitos, List<String> simbolos, int linha, List<Token> tokens) {
        String palavra = "";
        int J = j;
        boolean encontrou = false;

        if (caractere[J] == '"') {
            palavra = palavra + caractere[J];
            J = J + 1;
            if (letras.contains(caractere[J] + "")) {
                palavra = palavra + caractere[J];
                J = J + 1;
                while (J < caractere.length) {
                    if (caractere[J] == '"') {
                        palavra = palavra + caractere[J];
                        encontrou = true;
                        J = J + 1;
                        break;
                    } else if (letras.contains(caractere[J]) || digitos.contains(caractere[J]) || simbolos.contains(caractere[J]) || caractere[J] == '\"') {
                        palavra = palavra + caractere[J];
                        J++;
                    } else {
                        palavra = palavra + caractere[J];
                        Token token = new Token("Cadeia de Caracteres Mal Formada", palavra);
                        token.setLinha(linha + 1);
                        tokens.add(token);
                        return J;
                    }
                }
            } else {
                palavra = palavra + caractere[J];
                Token token = new Token("Cadeia de Caracteres Mal Formada", palavra);
                token.setLinha(linha + 1);
                tokens.add(token);
                return J;
            }
            if (encontrou != true) { //se não fechou as "
                palavra = palavra + caractere[J];
                Token token = new Token("Cadeia de Caracteres Mal Formada", palavra);
                token.setLinha(linha + 1);
                tokens.add(token);
                return J;
            }
        }
        palavra = palavra + caractere[J];
        Token token = new Token("Cadeia de Caracteres", palavra);
        token.setLinha(linha + 1);
        tokens.add(token);
        return J;
    }

//_________________________________________________________________________________________________________________    
    public int verificarCaractere(int j, char[] caractere, int linha, List<String> letras, List<String> digitos, List<Token> tokens) {
        String palavra = "";
        int J = j;
        if (caractere[J] == '\'') {
            palavra = palavra + caractere[J];
            J = J + 1;
            if (letras.contains(caractere[J] + "") || digitos.contains(caractere[J] + "") || caractere[J] == ' ') {
                palavra = palavra + caractere[J];
                J = J + 1;
                if (caractere[J] == '\'') {
                    palavra = palavra + caractere[J];
                    Token token = new Token("Caracteres", palavra);
                    token.setLinha(linha + 1);
                    tokens.add(token);
                    J = J + 1;
                } else {
                    palavra = palavra + caractere[J];
                    Token token = new Token("Caractere Mal Formado", palavra);
                    token.setLinha(linha + 1);
                    tokens.add(token);
                    return J;
                }
            } else {
                palavra = palavra + caractere[J];
                Token token = new Token("Caractere Mal Formado", palavra);
                token.setLinha(linha + 1);
                tokens.add(token);
                return J;
            }
        }
        return J;
    }
}

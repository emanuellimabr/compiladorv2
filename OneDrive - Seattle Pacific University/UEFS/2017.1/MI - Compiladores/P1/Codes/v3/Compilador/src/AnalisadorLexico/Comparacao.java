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
            //System.out.println(palavra);
            Token token = new Token("Palavra Reservada", palavra);
            token.setLinha(linha + 1);
            tokens.add(token);
            return J;
        } else {
            return j; //retorna isso para verificar se é identificador
        }
    }
//_________________________________________________________________________________________________________________

    public int verificarIdentificador(int j, char[] caractere, List<String> letras, List<String> delimitadores, List<String> digitos, int linha, List<Token> tokens) {
        String palavra = "";
        boolean erro = false;
        int J = j;
        if (letras.contains(caractere[J] + "")) {
            palavra = palavra + caractere[J];
            J = J + 1;
            //System.out.println(palavra);
            while (J < caractere.length) {
                if (letras.contains(caractere[J] + "") || digitos.contains(caractere[J] + "") || caractere[J] == '_') {
                    palavra = palavra + caractere[J];
                    J++;
                    //System.out.println(palavra);
                } else if (caractere[J] == ' ' ) {
                    break;
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
            return J + 1;
        }
        //System.out.println(palavra);
        Token token = new Token("Identificador", palavra);
        token.setLinha(linha + 1);
        tokens.add(token);
        return J;
    }

//_________________________________________________________________________________________________________________    
    public int verificarNumero(int j, char[] caractere, List<String> digitos, List<String> delimitadores, int linha, List<Token> tokens) {
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
                } else if (caractere[J] == ' ') {
                    break;
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
                    palavra = palavra + caractere[J];//se for pra desprezar os espaços comento essa linha.
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
                        } else if (caractere[J] == ' ') {
                            break;
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
            return J + 1;
        }
        //System.out.println(palavra);
        Token token = new Token("Número", palavra);
        token.setLinha(linha + 1);
        tokens.add(token);
        return J;
    }

//_________________________________________________________________________________________________________________
    public int verificarOperadoresAritmeticos(int j, char[] caractere, int linha, List<String> operadoresAritmeticos, List<Token> tokens) {
        String palavra = "";
        int J = j;
        if (operadoresAritmeticos.contains(caractere[J] + "")) {
            if (caractere[J] == '/') {
                palavra = palavra + caractere[J];
                J = J + 1;
                if (caractere[J] == '/' || caractere[J] == '*') {
                    return j; //retorna para verificar se é comentário
                } else {
                    //System.out.println(palavra);
                    Token token = new Token("Operadores Aritméticos", palavra);
                    token.setLinha(linha + 1);
                    tokens.add(token);
                    return J;
                }
            } else {
                //System.out.println(palavra);
                Token token = new Token("Operadores Aritméticos", palavra);
                token.setLinha(linha + 1);
                tokens.add(token);
                J = J + 1;
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
            palavra = palavra + caractere[J];
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
                        Token token = new Token("Operador Lógico Mal Formado", palavra);
                        token.setLinha(linha + 1);
                        tokens.add(token);
                        J = J + 1;
                        break;
                    }
                }
            } else { //má formação de operador.
                palavra = palavra + caractere[J];
                Token token = new Token("Operador Lógico Mal Formado", palavra);
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
                        Token token = new Token("Operador Lógico Mal Formado", palavra);
                        token.setLinha(linha + 1);
                        tokens.add(token);
                        J = J + 1;
                        break;
                    }
                }
            } else {//má formação de operador.
                palavra = palavra + caractere[J];
                Token token = new Token("Operador Lógico Mal Formado", palavra);
                token.setLinha(linha + 1);
                tokens.add(token);
                J = J + 1;
            }
        }
        return J;
    }

//_________________________________________________________________________________________________________________     
    public int verificaDelimitadorComentarioLinha(int j, char[] caractere, int linha, List<Token> tokens) {
        int J = j;
        int aux = J + 1;
        boolean encontrou = false;
        String palavra = "";
        if (caractere[J] == '/') {
            palavra = palavra + caractere[J];
            J = J + 1;
            if (caractere[J] == '/') {
                palavra = palavra + caractere[J];
                J = J + 1;
                while (J < caractere.length) {
                    palavra = palavra + caractere[J];
                    J++;
                }
            }
        }
        //System.out.println(palavra);
        Token token = new Token("Comentário de Linha", palavra);
        token.setLinha(linha + 1);
        tokens.add(token);
        J = J + 1;
        return J;
    }

    /*  if (caractere[J] == '\\' && caractere[aux] == 'n') {
     palavra = palavra + caractere[J];
     System.out.println(palavra);
     encontrou = true;
     J = J + 1;
     break;
     } else if (caractere[J] == ' ') {
     palavra = palavra + caractere[J];
     J++;
                        
     } else {
     palavra = palavra + caractere[J];
     J++;
     }
     }
     }
     }
     if (encontrou == true) {
     palavra = palavra + caractere[J];
     //System.out.println(palavra);
     Token token = new Token("Comentário de Linha", palavra);
     token.setLinha(linha + 1);
     tokens.add(token);
     J = J + 1;
     }
     return J;
     }*/
//_________________________________________________________________________________________________________________     
    public int[] verificaDelimitadorComentarioBloco(String[] Linhas, int linha, char[] caractere, int j, List<Token> tokens, String comentario, int x) {
        int J = j;
        int[] p = {linha, j};
        if (linha >= Linhas.length) {
            p[0] = linha - 1;
            p[1] = Linhas[p[0]].length();
            Token token = new Token("Comentário de Bloco Não Fechado", comentario);
            token.setLinha(linha - 1);
            tokens.add(token);
            return p;
        }

        boolean encontrou = false;
        while (J < caractere.length) {
            if (caractere[J] == '*') {
                comentario = comentario + caractere[J];
                J++;
                if (J < caractere.length && caractere[J] == '/') {
                    comentario = comentario + caractere[J];
                    encontrou = true;
                    p[0] = J;
                    p[1] = j;
                    break;
                }
            } else {
                comentario = comentario + caractere[J];
                J++;
            }
        }
        if (!encontrou) {
            j = 0;
            if (linha + 1 >= Linhas.length) {
                p[0] = linha;
                p[1] = Linhas[p[0]].length();
                Token token = new Token("Comentário de Bloco Não Fechado", comentario);
                token.setLinha(linha);
                tokens.add(token);
                return p;
            }
            caractere = Linhas[linha + 1].toCharArray();
            p = verificaDelimitadorComentarioBloco(Linhas, linha + 1, caractere, j, tokens, comentario + " ", x);
        } else {
            p[0] = linha;
            p[1] = J + 1;
            Token token = new Token("Comentário de Bloco", comentario);
            //System.out.println(comentario);
            token.setLinha(x + 1);
            tokens.add(token);
        }
        return p;
    }
//_________________________________________________________________________________________________________________     
/*    public int verificarDelimitadorCometarios(int j, char[] caractere, int linha, List<Token> tokens) {
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
     }*/

//_________________________________________________________________________________________________________________    
    public int verificarDelimitadores(int j, char[] caractere, int linha, List<String> delimitadores, List<Token> tokens) {
        String palavra = "";
        int J = j;
        if (delimitadores.contains(caractere[J] + "")) {
            palavra = palavra + caractere[J];
            //System.out.println(palavra);
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
        //System.out.println(caractere[j]);
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
                         //System.out.println(palavra);
                        encontrou = true;
                        J = J + 1;
                        break;
                    } else if (letras.contains(caractere[J]) || digitos.contains(caractere[J]) || simbolos.contains(caractere[J]) || caractere[J] == '\"') {
                        palavra = palavra + caractere[J];
                        J = J + 1;
                    } else {
                        palavra = palavra + caractere[J]; //se não tem nenhum dos símbolos aceitos e não fechou "
                        //System.out.println(palavra);
                        Token token = new Token("Cadeia de Caracteres Mal Formada", palavra);
                        token.setLinha(linha + 1);
                        tokens.add(token);
                        return J+1;
                    }
                }
            } else {
                palavra = palavra + caractere[J];//se após as " não tem uma letra.
                //System.out.println(palavra);
                Token token = new Token("Cadeia de Caracteres Mal Formada", palavra);
                token.setLinha(linha + 1);
                tokens.add(token);
                return J+1;
            }
            if (encontrou == true) {
                palavra = palavra + caractere[J];
                //System.out.println(palavra);
                Token token = new Token("Cadeia de Caracteres", palavra);
                token.setLinha(linha + 1);
                tokens.add(token);
                return J+1;
            }
        }
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
                    //System.out.println(palavra);
                    Token token = new Token("Caracteres", palavra);
                    token.setLinha(linha + 1);
                    tokens.add(token);
                    J = J + 1;
                } else {
                    palavra = palavra + caractere[J];
                    //System.out.println(palavra);
                    Token token = new Token("Caractere Mal Formado", palavra);
                    token.setLinha(linha + 1);
                    tokens.add(token);
                    return J;
                }
            } else {
                palavra = palavra + caractere[J];
                //System.out.println(palavra);
                Token token = new Token("Caractere Mal Formado", palavra);
                token.setLinha(linha + 1);
                tokens.add(token);
                return J;
            }
        }
        return J;
    }
}

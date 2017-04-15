# compiladorv2
# Compilador Problema 1  - Analisador Léxico

 Autores: Emanuel, Jadna e Patrícia
 
O que falta:

 => consertar o método que forma token de comentário de bloco (mudar retorno pq tá retornando um int, mas no caso de comentário de bloco inválido deve retornar a linha inteira)

=> colocar para ler os arquivos de várias pastas

=> salvar em arquivo a saída(ver o padrão de saída mandado pelo professor)

=> testar

=> fazer relatório


1.	DOCUMENTAÇÃO DO PROJETO

O projeto foi desenvolvido na linguagem de programação java e implementado no Netbeans IDE.

2.	EXECUÇÃO DO CÓDIGO FONTE

Para executar o analisador léxico é necessário ter instalado em sua máquina no mínimo o ambiente de execução JAVA. Caso queira fazer alguma alteração no código presente na pasta src deve-se instalar o ambiente de desenvolvimento JAVA para implementação e simulação.

2.1.	Arquivos presentes

Lexico.java - Este arquivo contem a classe java responsável por ler todos os arquivos txt que se encontram no diretório entrada, ele é a classe que contém a main.

Leitura.java - Este arquivo contem a classe java resposavel pela análise léxica do código fonte, nela o arquivo de entrada é quebrado em vários arquivos no formato txt com os tipos de cada ítem lido (delimitadores, delimitadores comentarios, dígitos, letras, operadores aritméticos, operadores lógicos, operadores relacionais e palavras reservadas).

Comparacao.java - Este arquivo lê os arquivos (delimitadores, (delimitadores, delimitadores comentarios, dígitos, letras, operadores aritméticos, operadores lógicos, operadores relacionais e palavras reservadas).

Token.java - É a classe responsável pelos tokens.

Entrada – É um diretório que se encontra dentro da pasta do projeto com todos os arquivos para análise léxica.

Saída – É um diretório que se encontra dentro da pasta do projeto com todos os arquivos de saída no formato txt.

input.txt - Este arquivo contém o código fonte a ser analisado pelo analisador léxico. Caso queira testar outros códigos deve-se insere neste arquivo.

output.txt - Este arquivo contém o resultado da análise léxica. Ele é criado ao executar o analisador léxico.


2.2.	Analisador Léxico

A implementação do analisador léxico para a linguagem de programação JAVA foi definida de acordo com as expressões regulares definidas abaixo.

Palavras reservadas: program, const, var, function, begin, end, if, then, else, while, do, read, write, integer, real, boolean, true, false, string, char

Identificadores Letra: (Letra|Dígito|_)*

Número: (-)?Dígito+(.Dígito+)?

Letra: (a..z|A..Z)

Dígito: 0..9

Símbolo ASCII: de 33 a 126

Cadeia Constante: "(Letra|Dígito|Símbolo (exceto 34))*"

Caractere Constante: '(Letra|Dígito)'

Operadores Aritméticos: + - * / %

Operadores Relacionais: != = < <= > >=

Operadores Lógicos: ! && ||

Delimitadores: ; , ( ) []

Comentários de Linha: /* Isto é um comentário de bloco */''

Comentários de Bloco: // Isto é um comentário de linha```

A execução do analisador léxico resulta no arquivo simbolos.txt na pasta saída, o arquivo que contém em cada linha um token representando os padrões analisados.



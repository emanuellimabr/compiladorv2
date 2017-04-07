/*
 * Classe responsável pelos tokens.
 */
package AnalisadorLexico;

/**
 *
 * @author Paty, Emanuel, Jadna
 */
public class Token {
    private String palavra;
    private String id;
    private int linha;
    
    public Token (String id, String palavra){
       this.palavra = palavra;
       this.id = id;
   }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }
    
   
}

package mack.ps2.estagios.estagios.model;

public class Universidade {
    private Long id;
    private String nome;
    private int anoFundacao;

    public Universidade(Long id, String nome, int anoFundacao){
        this.id=id;
        this.nome = nome;
        this.anoFundacao = anoFundacao;
    }

    public void setId(long id){
        this.id=id;
    }

    public Long getId(){
        return this.id;
    }

    public void setNome(String nome){
        this.nome=nome;
    }
    public void setAnoFundacao(int ano){
        this.anoFundacao = ano;
    }
    public String getNome(){
        return this.nome;
    }
    public int getAnoFundacao(){
        return this.anoFundacao;
    }
}
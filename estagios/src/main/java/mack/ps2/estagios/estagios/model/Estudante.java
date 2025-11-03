package mack.ps2.estagios.estagios.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Estudante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private int anoIngresso;

    @OneToMany(mappedBy = "estudante")
    @JsonIgnore
    private List<Inscricao> inscricoes;

    @ManyToMany
    @JoinTable(
            name = "estudante_area_interesse",
            joinColumns = @JoinColumn(name = "estudante_id"),
            inverseJoinColumns = @JoinColumn(name = "area_id")
    )
    private List<Area> areasDeInteresse;

    public Estudante() {
    }

    public Estudante(Long id, String nome, String email, int anoIngresso) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.anoIngresso = anoIngresso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAnoIngresso() {
        return anoIngresso;
    }

    public void setAnoIngresso(int anoIngresso) {
        this.anoIngresso = anoIngresso;
    }

    public List<Inscricao> getInscricoes() {
        return inscricoes;
    }

    public void setInscricoes(List<Inscricao> inscricoes) {
        this.inscricoes = inscricoes;
    }

    public List<Area> getAreasDeInteresse() {
        return areasDeInteresse;
    }

    public void setAreasDeInteresse(List<Area> areasDeInteresse) {
        this.areasDeInteresse = areasDeInteresse;
    }
}
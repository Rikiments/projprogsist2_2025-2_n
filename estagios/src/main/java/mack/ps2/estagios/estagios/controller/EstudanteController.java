package mack.ps2.estagios.estagios.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import mack.ps2.estagios.estagios.model.Estudante;

@RestController
public class EstudanteController {

    private List<Estudante> estudantes;

    public EstudanteController() {
        estudantes = new ArrayList<>();

        estudantes.add(new Estudante(1L, "Yuri Alberto", "protagonista@email.com", LocalDate.parse("2002-03-15"), 2022));
        estudantes.add(new Estudante(2L, "Carlos Miguel", "traidor@email.com", LocalDate.parse("2001-10-22"), 2019));
        estudantes.add(new Estudante(3L, "Hugo Souza", "salvador@email.com", LocalDate.parse("2003-07-05"), 2021));
        estudantes.add(new Estudante(4L, "Rodrigo Garro", "argentino@email.com", LocalDate.parse("2002-04-11"), 2020));
        estudantes.add(new Estudante(5L, "Breno Bidon", "breno.polvo@email.com", LocalDate.parse("2001-12-25"), 2019));
        estudantes.add(new Estudante(6L, "Ronaldo Nazário", "goat@email.com", LocalDate.parse("2000-09-13"), 2018));
        estudantes.add(new Estudante(7L, "Fabio Santos", "careca@email.com", LocalDate.parse("2002-06-18"), 2020));
        estudantes.add(new Estudante(8L, "Cassio Ramos", "abeleza@email.com", LocalDate.parse("2003-01-30"), 2021));
        estudantes.add(new Estudante(9L, "Memphis Depay", "leao@email.com", LocalDate.parse("2001-11-08"), 2019));
        estudantes.add(new Estudante(10L, "Jose Martinez", "josemartinez@email.com", LocalDate.parse("2000-08-27"), 2018));
    }

    //GET - Listar todas os estudantes
    @GetMapping("/mackenzie/estudantes")
    public List<Estudante> getEstudantes() {
        return estudantes;
    }

    //GET - Listar estudante por id
    @GetMapping("/mackenzie/estudantes/{id}")
    public Estudante getEstudante(@PathVariable Long id) {
        for (Estudante e : estudantes) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    //POST - Adicionar novo estudante
    @PostMapping("/mackenzie/estudantes")
    public Estudante create(@RequestBody Estudante e) {
        long maiorId = 0;
        for (Estudante estudante : estudantes) {
            if (estudante.getId() > maiorId) {
                maiorId = estudante.getId();
            }
        }
        e.setId(maiorId + 1);
        estudantes.add(e);
        return e;
    }

    //PUT - Atualizar estudante existente
    @PutMapping("/mackenzie/estudantes/{id}")
    public Estudante update(@PathVariable long id, @RequestBody Estudante novo) {
        for (Estudante e : estudantes) {
            if (e.getId() == id) {
                e.setNome(novo.getNome());
                e.setEmail(novo.getEmail());
                e.setNascimento(novo.getNascimento());
                e.setAnoIngresso(novo.getAnoIngresso());
                return e;
            }
        }
        return null;
    }

    //Delete - Remover estudante por id  
    @DeleteMapping("/mackenzie/estudantes/{id}")
    public String delete(@PathVariable long id) {
        for (Estudante e : estudantes) {
            if (e.getId() == id) {
                estudantes.remove(e);
                return "Estudante removido com sucesso.";
            }
        }
        return "Estudante não encontrado.";
    }
}
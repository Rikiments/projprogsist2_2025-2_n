package mack.ps2.estagios.estagios.controller;

import mack.ps2.estagios.estagios.model.Estudante;
import mack.ps2.estagios.estagios.service.EstudanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudantes")
public class EstudanteController {

    @Autowired
    private EstudanteService estudanteService;

    @GetMapping
    public List<Estudante> listar() {
        return estudanteService.listarTodos();
    }

    @GetMapping("/{id}")
    public Estudante buscar(@PathVariable Long id) {
        return estudanteService.buscarPorId(id);
    }

    @PostMapping
    public Estudante criar(@RequestBody Estudante estudante) {
        return estudanteService.salvar(estudante);
    }

    @PutMapping("/{id}")
    public Estudante atualizar(@PathVariable Long id, @RequestBody Estudante estudante) {
        return estudanteService.atualizar(id, estudante);
    }

    @DeleteMapping("/{id}")
    public String deletar(@PathVariable Long id) {
        return estudanteService.deletar(id) ? "Estudante removido." : "Estudante não encontrado.";
    }
}

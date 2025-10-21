package mack.ps2.estagios.estagios.controller;

import mack.ps2.estagios.estagios.model.Estudante;
import mack.ps2.estagios.estagios.repository.EstudanteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudantes")
public class EstudanteController {

    @Autowired
    private EstudanteRepo estudanteRepo;

    // 🔹 GET - Listar todos
    @GetMapping
    public List<Estudante> listarEstudantes() {
        return estudanteRepo.findAll();
    }

    // 🔹 GET - Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Estudante> buscarPorId(@PathVariable Long id) {
        return estudanteRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 POST - Adicionar
    @PostMapping
    public Estudante adicionar(@RequestBody Estudante novoEstudante) {
        return estudanteRepo.save(novoEstudante);
    }

    // 🔹 PUT - Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<Estudante> atualizar(@PathVariable Long id, @RequestBody Estudante estudanteAtualizado) {
        return estudanteRepo.findById(id)
                .map(estudante -> {
                    estudanteAtualizado.setId(id);
                    estudanteRepo.save(estudanteAtualizado);
                    return ResponseEntity.ok(estudanteAtualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 DELETE - Remover
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        if (estudanteRepo.existsById(id)) {
            estudanteRepo.deleteById(id);
            return ResponseEntity.ok("Estudante removido com sucesso.");
        }
        return ResponseEntity.notFound().build();
    }
}

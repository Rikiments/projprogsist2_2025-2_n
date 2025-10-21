package mack.ps2.estagios.estagios.controller;

import mack.ps2.estagios.estagios.model.Vagas;
import mack.ps2.estagios.estagios.repository.VagasRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vagas")
public class VagasController {

    @Autowired
    private VagasRepo vagaRepo;

    // 🔹 GET - Listar todas
    @GetMapping
    public List<Vagas> listarVagas() {
        return vagaRepo.findAll();
    }

    // 🔹 GET - Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Vagas> buscarPorId(@PathVariable Long id) {
        return vagaRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 POST - Adicionar
    @PostMapping
    public Vagas adicionar(@RequestBody Vagas novaVaga) {
        return vagaRepo.save(novaVaga);
    }

    // 🔹 PUT - Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<Vagas> atualizar(@PathVariable Long id, @RequestBody Vagas vagaAtualizada) {
        return vagaRepo.findById(id)
                .map(vaga -> {
                    vagaAtualizada.setId(id);
                    vagaRepo.save(vagaAtualizada);
                    return ResponseEntity.ok(vagaAtualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 DELETE - Remover
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        if (vagaRepo.existsById(id)) {
            vagaRepo.deleteById(id);
            return ResponseEntity.ok("Vaga removida com sucesso.");
        }
        return ResponseEntity.notFound().build();
    }
}

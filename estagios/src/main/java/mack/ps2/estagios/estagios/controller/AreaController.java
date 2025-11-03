package mack.ps2.estagios.estagios.controller;

import mack.ps2.estagios.estagios.model.Area;
import mack.ps2.estagios.estagios.repository.AreaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/areas") // Define o caminho base para este controller
public class AreaController {

    @Autowired
    private AreaRepo areaRepo;

    // 🔹 POST - Adicionar nova
    @PostMapping
    public Area adicionar(@RequestBody Area novaArea) {
        return areaRepo.save(novaArea);
    }

    // 🔹 GET - Listar todas
    @GetMapping
    public List<Area> listarAreas() {
        return areaRepo.findAll();
    }

    // 🔹 GET - Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Area> buscarPorId(@PathVariable Long id) {
        return areaRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 PUT - Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<Area> atualizar(@PathVariable Long id, @RequestBody Area areaAtualizada) {
        return areaRepo.findById(id)
                .map(area -> {
                    // Atualiza os campos da entidade existente
                    area.setNome(areaAtualizada.getNome());
                    // (Adicione outros campos aqui se a entidade 'Area' crescer)
                    
                    Area areaSalva = areaRepo.save(area);
                    return ResponseEntity.ok(areaSalva);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 DELETE - Remover
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        if (areaRepo.existsById(id)) {
            areaRepo.deleteById(id);
            return ResponseEntity.ok("Área removida com sucesso.");
        }
        return ResponseEntity.notFound().build();
    }
}
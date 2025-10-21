package mack.ps2.estagios.estagios.controller;

import mack.ps2.estagios.estagios.model.Empresa;
import mack.ps2.estagios.estagios.repository.EmpresaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaRepo empresaRepo;

    // 🔹 GET - Listar todas
    @GetMapping
    public List<Empresa> listarEmpresas() {
        return empresaRepo.findAll();
    }

    // 🔹 GET - Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Empresa> buscarPorId(@PathVariable Long id) {
        return empresaRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 POST - Adicionar nova
    @PostMapping
    public Empresa adicionar(@RequestBody Empresa novaEmpresa) {
        return empresaRepo.save(novaEmpresa);
    }

    // 🔹 PUT - Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<Empresa> atualizar(@PathVariable Long id, @RequestBody Empresa empresaAtualizada) {
        return empresaRepo.findById(id)
                .map(empresa -> {
                    empresaAtualizada.setId(id);
                    empresaRepo.save(empresaAtualizada);
                    return ResponseEntity.ok(empresaAtualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 DELETE - Remover
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        if (empresaRepo.existsById(id)) {
            empresaRepo.deleteById(id);
            return ResponseEntity.ok("Empresa removida com sucesso.");
        }
        return ResponseEntity.notFound().build();
    }
}

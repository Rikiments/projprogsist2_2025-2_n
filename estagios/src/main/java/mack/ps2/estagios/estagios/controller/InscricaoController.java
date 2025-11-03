package mack.ps2.estagios.estagios.controller;

import mack.ps2.estagios.estagios.model.Inscricao;
import mack.ps2.estagios.estagios.repository.InscricaoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inscricoes")
public class InscricaoController {

    @Autowired
    private InscricaoRepo inscricaoRepo;


    @PostMapping
    public Inscricao adicionar(@RequestBody Inscricao novaInscricao) {
        return inscricaoRepo.save(novaInscricao);
    }

    // 🔹 GET - Listar todas
    @GetMapping
    public List<Inscricao> listarInscricoes() {
        return inscricaoRepo.findAll();
    }

    // 🔹 GET - Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Inscricao> buscarPorId(@PathVariable Long id) {
        return inscricaoRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 PUT - Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<Inscricao> atualizar(@PathVariable Long id, @RequestBody Inscricao inscricaoAtualizada) {
        return inscricaoRepo.findById(id)
                .map(inscricaoExistente -> {
                    
                    inscricaoExistente.setDataInscricao(inscricaoAtualizada.getDataInscricao());
                    inscricaoExistente.setStatus(inscricaoAtualizada.getStatus());
                    inscricaoExistente.setMensagemApresentacao(inscricaoAtualizada.getMensagemApresentacao());

                    
                    inscricaoExistente.setEstudante(inscricaoAtualizada.getEstudante());
                    inscricaoExistente.setVaga(inscricaoAtualizada.getVaga());

                    Inscricao inscricaoSalva = inscricaoRepo.save(inscricaoExistente);
                    return ResponseEntity.ok(inscricaoSalva);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 DELETE - Remover
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        if (inscricaoRepo.existsById(id)) {
            inscricaoRepo.deleteById(id);
            return ResponseEntity.ok("Inscrição removida com sucesso.");
        }
        return ResponseEntity.notFound().build();
    }
}
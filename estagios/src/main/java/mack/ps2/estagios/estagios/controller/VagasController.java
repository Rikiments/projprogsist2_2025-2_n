package mack.ps2.estagios.estagios.controller;

import mack.ps2.estagios.estagios.model.Vagas;
import mack.ps2.estagios.estagios.service.VagasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vagas")
@CrossOrigin(origins = "*")
public class VagasController {

    @Autowired
    private VagasService vagasService;

    @GetMapping
    public List<Vagas> listar() {
        return vagasService.listarTodas();
    }

    @GetMapping("/{id}")
    public Vagas buscar(@PathVariable Long id) {
        return vagasService.buscarPorId(id);
    }

    @PostMapping
    public Vagas criar(@RequestBody Vagas vaga) {
        return vagasService.salvar(vaga);
    }

    @PutMapping("/{id}")
    public Vagas atualizar(@PathVariable Long id, @RequestBody Vagas vaga) {
        return vagasService.atualizar(id, vaga);
    }

    @DeleteMapping("/{id}")
    public String deletar(@PathVariable Long id) {
        return vagasService.deletar(id) ? "Vaga removida." : "Vaga não encontrada.";
    }
}

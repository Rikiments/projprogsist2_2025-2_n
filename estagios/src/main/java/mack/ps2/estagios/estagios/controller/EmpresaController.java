package mack.ps2.estagios.estagios.controller;

import mack.ps2.estagios.estagios.model.Empresa;
import mack.ps2.estagios.estagios.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @GetMapping
    public List<Empresa> listar() {
        return empresaService.listarTodas();
    }

    @GetMapping("/{id}")
    public Empresa buscar(@PathVariable Long id) {
        return empresaService.buscarPorId(id);
    }

    @PostMapping
    public Empresa criar(@RequestBody Empresa empresa) {
        return empresaService.salvar(empresa);
    }

    @PutMapping("/{id}")
    public Empresa atualizar(@PathVariable Long id, @RequestBody Empresa empresa) {
        return empresaService.atualizar(id, empresa);
    }

    @DeleteMapping("/{id}")
    public String deletar(@PathVariable Long id) {
        return empresaService.deletar(id) ? "Empresa removida." : "Empresa não encontrada.";
    }
}

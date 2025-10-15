package mack.ps2.estagios.estagios.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import mack.ps2.estagios.estagios.model.Empresa;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    private List<Empresa> empresas = new ArrayList<>();

    public EmpresaController() {
        empresas.add(new Empresa(1L, "Empresa Alfa LTDA", "12.345.678/0001-90", "contato@empresa-alfa.com"));
        empresas.add(new Empresa(2L, "Beta Comércio ME", "98.765.432/0001-10", "beta@comercio.com"));
        empresas.add(new Empresa(3L, "Gamma Serviços S.A.", "11.222.333/0001-44", "servicos@gamma.com"));
        empresas.add(new Empresa(4L, "Delta Engenharia", "22.333.444/0001-55", "contato@deltaeng.com"));
        empresas.add(new Empresa(5L, "Epsilon Digital", "33.444.555/0001-66", "email@epsilondigital.com"));
    }

    //GET - Listar todas as empresas
    @GetMapping
    public List<Empresa> listarEmpresas() {
        return empresas;
    }

    //GET - Buscar empresa por ID
    @GetMapping("/{id}")
    public Empresa buscarPorId(@PathVariable Long id) {
        return empresas.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    //POST - Adicionar nova empresa
    @PostMapping
    public Empresa adicionar(@RequestBody Empresa novaEmpresa) {
        novaEmpresa.setId((long) (empresas.size() + 1));
        empresas.add(novaEmpresa);
        return novaEmpresa;
    }

    //PUT - Atualizar empresa existente
    @PutMapping("/{id}")
    public Empresa atualizar(@PathVariable Long id, @RequestBody Empresa empresaAtualizada) {
        for (int i = 0; i < empresas.size(); i++) {
            Empresa e = empresas.get(i);
            if (e.getId().equals(id)) {
                empresaAtualizada.setId(id);
                empresas.set(i, empresaAtualizada);
                return empresaAtualizada;
            }
        }
        return null;
    }

    //DELETE - Remover empresa por ID
    @DeleteMapping("/{id}")
    public String deletar(@PathVariable Long id) {
        boolean removido = empresas.removeIf(e -> e.getId().equals(id));
        return removido ? "Empresa removida com sucesso." : "Empresa não encontrada.";
    }
}
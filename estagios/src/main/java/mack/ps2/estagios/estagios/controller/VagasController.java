package mack.ps2.estagios.estagios.controller;

import mack.ps2.estagios.estagios.model.Vagas;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/vagas")
public class VagasController {

    private final List<Vagas> vagas = new ArrayList<>();

    public VagasController() {
        vagas.add(new Vagas(1L, "Desenvolvedor Java",
                "Atuação em projetos backend com Java e Spring. Experiência desejada em APIs REST.",
                LocalDate.parse("2025-10-01"), true, 1L));

        vagas.add(new Vagas(2L, "Analista de Suporte Técnico",
                "Suporte a clientes, resolução de chamados e participação em treinamentos internos.",
                LocalDate.parse("2025-09-27"), true, 2L));

        vagas.add(new Vagas(3L, "Engenheiro de Software",
                "Desenvolvimento de soluções para sistemas corporativos, integração e automação.",
                LocalDate.parse("2025-10-03"), false, 3L));

        vagas.add(new Vagas(4L, "Analista de Dados",
                "Manipulação e análise de grandes volumes de dados. Conhecimentos de SQL e Python.",
                LocalDate.parse("2025-09-18"), true, 4L));

        vagas.add(new Vagas(5L, "Designer Digital",
                "Criação de materiais gráficos, UX/UI e participação em campanhas de marketing.",
                LocalDate.parse("2025-09-30"), false, 5L));

        vagas.add(new Vagas(6L, "Consultor de Projetos",
                "Elaboração e acompanhamento de projetos empresariais e treinamentos.",
                LocalDate.parse("2025-10-06"), true, 1L));

        vagas.add(new Vagas(7L, "Programador Full Stack",
                "Desenvolvimento de aplicações web frontend e backend com foco em automação.",
                LocalDate.parse("2025-10-04"), true, 2L));
    }

    //GET - Listar todas as vagas
    @GetMapping
    public List<Vagas> listarVagas() {
        return vagas;
    }

    //GET - Buscar vaga por ID
    @GetMapping("/{id}")
    public ResponseEntity<Vagas> buscarPorId(@PathVariable Long id) {
        return vagas.stream()
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //POST - Adicionar nova vaga
    @PostMapping
    public ResponseEntity<Vagas> adicionar(@RequestBody Vagas novaVaga) {
        novaVaga.setId((long) (vagas.size() + 1));
        vagas.add(novaVaga);
        return ResponseEntity.ok(novaVaga);
    }

    //PUT - Atualizar vaga existente
    @PutMapping("/{id}")
    public ResponseEntity<Vagas> atualizar(@PathVariable Long id, @RequestBody Vagas vagaAtualizada) {
        for (int i = 0; i < vagas.size(); i++) {
            Vagas v = vagas.get(i);
            if (v.getId().equals(id)) {
                vagaAtualizada.setId(id);
                vagas.set(i, vagaAtualizada);
                return ResponseEntity.ok(vagaAtualizada);
            }
        }
        return ResponseEntity.notFound().build();
    }

    //DELETE - Remover vaga por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        boolean removido = vagas.removeIf(v -> v.getId().equals(id));
        if (removido) {
            return ResponseEntity.ok("Vaga removida com sucesso.");
        } else {
            return ResponseEntity.status(404).body("Vaga não encontrada.");
        }
    }
}

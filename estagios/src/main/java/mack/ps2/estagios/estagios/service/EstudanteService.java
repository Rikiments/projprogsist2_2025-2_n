package mack.ps2.estagios.estagios.service;

import mack.ps2.estagios.estagios.model.Estudante;
import mack.ps2.estagios.estagios.repository.EstudanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudanteService {

    @Autowired
    private EstudanteRepository estudanteRepository;

    public List<Estudante> listarTodos() {
        return estudanteRepository.findAll();
    }

    public Estudante buscarPorId(Long id) {
        return estudanteRepository.findById(id).orElse(null);
    }

    public Estudante salvar(Estudante estudante) {
        return estudanteRepository.save(estudante);
    }

    public Estudante atualizar(Long id, Estudante estudanteAtualizado) {
        if (estudanteRepository.existsById(id)) {
            estudanteAtualizado.setId(id);
            return estudanteRepository.save(estudanteAtualizado);
        }
        return null;
    }

    public boolean deletar(Long id) {
        if (estudanteRepository.existsById(id)) {
            estudanteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

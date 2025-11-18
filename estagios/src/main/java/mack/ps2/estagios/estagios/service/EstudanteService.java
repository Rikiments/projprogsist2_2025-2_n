package mack.ps2.estagios.estagios.service;

import mack.ps2.estagios.estagios.model.Estudante;
import mack.ps2.estagios.estagios.repository.EstudanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

@Service
public class EstudanteService {

    @Autowired
    private EstudanteRepository estudanteRepository;

    public List<Estudante> listarTodos() {
        return estudanteRepository.findAll();
    }

    @Nullable
    public Estudante buscarPorId(@NonNull Long id) {
        return estudanteRepository.findById(id).orElse(null);
    }

    public Estudante salvar(@NonNull Estudante estudante) {
        return estudanteRepository.save(estudante);
    }

    @Nullable
    public Estudante atualizar(@NonNull Long id, @NonNull Estudante estudanteAtualizado) {
        if (estudanteRepository.existsById(id)) {
            estudanteAtualizado.setId(id);
            return estudanteRepository.save(estudanteAtualizado);
        }
        return null;
    }

    public boolean deletar(@NonNull Long id) {
        if (estudanteRepository.existsById(id)) {
            estudanteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
package mack.ps2.estagios.estagios.service;

import mack.ps2.estagios.estagios.model.Vagas;
import mack.ps2.estagios.estagios.repository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

@Service
public class VagasService {

    @Autowired
    private VagaRepository vagaRepository;

    public List<Vagas> listarTodas() {
        return vagaRepository.findAll();
    }

    @Nullable
    public Vagas buscarPorId(@NonNull Long id) {
        return vagaRepository.findById(id).orElse(null);
    }

    public Vagas salvar(@NonNull Vagas vaga) {
        return vagaRepository.save(vaga);
    }

    @Nullable
    public Vagas atualizar(@NonNull Long id, @NonNull Vagas vagaAtualizada) {
        if (vagaRepository.existsById(id)) {
            vagaAtualizada.setId(id);
            return vagaRepository.save(vagaAtualizada);
        }
        return null;
    }

    public boolean deletar(@NonNull Long id) {
        if (vagaRepository.existsById(id)) {
            vagaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
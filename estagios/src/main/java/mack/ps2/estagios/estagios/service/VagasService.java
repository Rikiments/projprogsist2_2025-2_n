package mack.ps2.estagios.estagios.service;

import mack.ps2.estagios.estagios.model.Vagas;
import mack.ps2.estagios.estagios.repository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VagasService {

    @Autowired
    private VagaRepository vagaRepository;

    public List<Vagas> listarTodas() {
        return vagaRepository.findAll();
    }

    public Vagas buscarPorId(Long id) {
        return vagaRepository.findById(id).orElse(null);
    }

    public Vagas salvar(Vagas vaga) {
        return vagaRepository.save(vaga);
    }

    public Vagas atualizar(Long id, Vagas vagaAtualizada) {
        if (vagaRepository.existsById(id)) {
            vagaAtualizada.setId(id);
            return vagaRepository.save(vagaAtualizada);
        }
        return null;
    }

    public boolean deletar(Long id) {
        if (vagaRepository.existsById(id)) {
            vagaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

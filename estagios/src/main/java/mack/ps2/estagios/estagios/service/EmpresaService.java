package mack.ps2.estagios.estagios.service;

import mack.ps2.estagios.estagios.model.Empresa;
import mack.ps2.estagios.estagios.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    public List<Empresa> listarTodas() {
        return empresaRepository.findAll();
    }

    public Empresa buscarPorId(Long id) {
        return empresaRepository.findById(id).orElse(null);
    }

    public Empresa salvar(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public Empresa atualizar(Long id, Empresa empresaAtualizada) {
        if (empresaRepository.existsById(id)) {
            empresaAtualizada.setId(id);
            return empresaRepository.save(empresaAtualizada);
        }
        return null;
    }

    public boolean deletar(Long id) {
        if (empresaRepository.existsById(id)) {
            empresaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

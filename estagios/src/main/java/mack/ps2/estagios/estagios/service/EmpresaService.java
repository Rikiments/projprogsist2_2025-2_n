package mack.ps2.estagios.estagios.service;

import mack.ps2.estagios.estagios.model.Empresa;
import mack.ps2.estagios.estagios.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    public List<Empresa> listarTodas() {
        return empresaRepository.findAll();
    }

 
    @Nullable
    public Empresa buscarPorId(@NonNull Long id) {
        return empresaRepository.findById(id).orElse(null);
    }

    public Empresa salvar(@NonNull Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    @Nullable
    public Empresa atualizar(@NonNull Long id, @NonNull Empresa empresaAtualizada) {
        if (empresaRepository.existsById(id)) {
            empresaAtualizada.setId(id);
            return empresaRepository.save(empresaAtualizada);
        }
        return null;
    }

    public boolean deletar(@NonNull Long id) {
        if (empresaRepository.existsById(id)) {
            empresaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
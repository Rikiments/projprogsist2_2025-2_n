package mack.ps2.estagios.estagios.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;


import mack.ps2.estagios.estagios.model.Universidade;

@RestController
@CrossOrigin(origins = "*")
public class UniversidadeController {
    private List<Universidade> listaUni;

    public UniversidadeController(){
        this.listaUni = new ArrayList<>();
        this.listaUni.add(new Universidade(1L,"Mackenzie SP",1870));
        this.listaUni.add(new Universidade(2L,"Mackenzie Tambore",1990));
    }

    @GetMapping("/api/universidades/")
    public List<Universidade> getUniversidades(){
        return this.listaUni;
    }

}
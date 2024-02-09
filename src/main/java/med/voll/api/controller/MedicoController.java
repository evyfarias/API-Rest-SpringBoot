package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.medico.DadosCadastroMedico;
import med.voll.api.medico.DadosListagemMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados){
        repository.save(new Medico(dados));
    }

    /*
     Para controlar a quantidade de registros e a página que será consultada, basta
     modificar a url
     http://localhost:8080/medicos?size=2&page=1 -> ?size=2&page=1

     Para controlar a ordenação dos dados, utilizar o SORT
     http://localhost:8080/medicos?sort=especialidade

     Para a ordenação ser decrescente, usar o DESC
     http://localhost:8080/medicos?sort=especialidade,desc

     Utilizando todos os parâmetros
      http://localhost:8080/medicos?sort=nome,desc&size=2&page=1

      -> Quem controla isto é a Interface Pageable e o retorno Page
      -> O uso do @PageableDefault ocorre de forma default, caso não haja os parâmetros
      na URL
    */
    @GetMapping
    public Page<DadosListagemMedico> listar(@PageableDefault(size = 1, sort = {"nome"}) Pageable paginacao){
        return repository.findAll(paginacao).map(DadosListagemMedico::new);
    }

}

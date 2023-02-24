package com.example.demo.controller;

import com.example.demo.model.Produto;
import com.example.demo.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    //retornar resposta de produto salvo com sucesso | ver caso em que o usuário não informe os atributos corretamente ou passe algum vazio
    @PostMapping("/salvar")
    public ResponseEntity<Produto> salvar(@RequestBody Produto produto){
        Produto novoProduto = produtoService.salvar(produto);
        return new ResponseEntity<>(produto, HttpStatus.CREATED);
    }

    // retornar para o usuário resposta de dado deletado
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletar(@PathVariable("id") Long id){
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

     @GetMapping("/buscarTodos")
    public ResponseEntity<Page<Produto>>  buscarTodos(@PageableDefault(sort = "valor", direction = Sort.Direction.ASC)Pageable pageable){
        return ResponseEntity.ok(produtoService.buscarTodos(pageable));
    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable("id")Long id){
        Optional<Produto> optionalProduto = produtoService.buscarPorId(id);
        if (optionalProduto.isEmpty()){
            return ResponseEntity.notFound().build();
        }
       return ResponseEntity.ok(optionalProduto.get());
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<Produto> alterar(@PathVariable("id") Long id, @RequestBody Produto produto) {
        Produto  produtoAlterado = produtoService.alterarProduto(produto, id);
        return ResponseEntity.ok(produtoAlterado);
    }

}


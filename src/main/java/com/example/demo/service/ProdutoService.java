package com.example.demo.service;

import com.example.demo.model.Produto;
import com.example.demo.repository.ProdutoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto salvar(Produto produto){
        return produtoRepository.save(produto);
    }

    public void deletar(Long id){
        if(produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Produto nao encontrado.");
        }
    }

    public Page<Produto> buscarTodos(@PageableDefault(page = 0, size = 4, sort = "valor", direction = Sort.Direction.ASC) Pageable pageable) {
        return produtoRepository.findAll(pageable);
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto alterarProduto(Produto produto, Long id){
         var produtoBanco = produtoRepository.findById(id).orElseThrow(() ->new RuntimeException("Produto não encontrado"));
         BeanUtils.copyProperties(produto, produtoBanco, "id");
         return produtoRepository.save(produtoBanco);
    }
}







package Controller.impl;

import Controller.api.ProdutoController;
import Model.entidade.Produto;
import Model.exception.ProdutoInexistenteException;
import Model.service.api.ProdutoService;
import Model.service.impl.ProdutoServiceImpl;
import Model.IO.ProdutoExportador;

import java.io.IOException;
import java.util.List;

public class ProdutoControllerImpl implements ProdutoController {
    private ProdutoService ProdutoService;

    public ProdutoControllerImpl() {
        this.ProdutoService = new ProdutoServiceImpl();
    }

    public void gerarArquivoListagem() throws IOException {
        List<Produto> produtosParaExportar = this.ProdutoService.listarTodos();
        ProdutoExportador exportador = new ProdutoExportador();
        exportador.exportarLista(produtosParaExportar);
    }

    @Override
    public void salvar(Produto Produto) {
        this.ProdutoService.salvar(Produto);
    }

    @Override
    public void excluir(int id) throws ProdutoInexistenteException {
        this.ProdutoService.excluir(id);
    }

    @Override
    public List<Produto> listarTodos() {
        return this.ProdutoService.listarTodos();
    }

    @Override
    public List<Produto> buscarMenosQueDez() {
        return this.ProdutoService.buscarMenosQueDez();
    }

    @Override
    public Produto buscarPorId(int id) {
        return this.ProdutoService.buscarPorId(id);
    }
}

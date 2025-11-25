package Controller.api;

import Model.entidade.Produto;
import Model.exception.ProdutoInexistenteException;

import java.io.IOException;
import java.util.List;

public interface ProdutoController {
    void salvar(Produto produto);
    void excluir(int id) throws ProdutoInexistenteException;
    List<Produto> listarTodos();
    List<Produto> buscarMenosQueDez();
    Produto buscarPorId(int id);
    void gerarArquivoListagem() throws IOException;
}

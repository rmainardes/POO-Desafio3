package Controller.api;

import Model.entidade.Produto;
import Model.exception.ProdutoInexistenteException;

import java.util.List;

public interface ProdutoController {
    public void salvar(Produto produto);
    public void excluir(int id) throws ProdutoInexistenteException;
    public List<Produto> listarTodos();
    public List<Produto> buscarMenosQueDez();
    public Produto buscarPorId(int id);
}

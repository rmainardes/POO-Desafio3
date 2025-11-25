package Model.service.impl;
import Model.dao.impl.ProdutoDaoImpl;
import Model.entidade.Produto;
import Model.exception.ProdutoInexistenteException;
import Model.service.api.ProdutoService;
import java.util.List;
import Model.dao.api.ProdutoDAO;

public class ProdutoServiceImpl implements ProdutoService {
    private ProdutoDAO ProdutoDAO;
    public ProdutoServiceImpl() {
        this.ProdutoDAO = new ProdutoDaoImpl();
    }

    @Override
    public void salvar(Produto Produto) {
        this.ProdutoDAO.salvar(Produto);
    }
    @Override
    public void excluir(int id) {
        Produto ProdutoParaExclusao = this.buscarPorId(id);
        if (ProdutoParaExclusao == null) {
            throw new ProdutoInexistenteException("Não foi possível localizar o produto que você deseja remover!");
        }
        this.ProdutoDAO.excluir(id);
    }

    @Override
    public List<Produto> listarTodos() {
        return this.ProdutoDAO.listarTodos();
    }

    @Override
    public List<Produto> buscarMenosQueDez() {
        return this.ProdutoDAO.buscarMenosQueDez();
    }

    @Override
    public Produto buscarPorId(int id) {
        return this.ProdutoDAO.buscarPorId(id);
    }
}
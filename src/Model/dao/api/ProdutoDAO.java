package Model.dao.api;
import Model.entidade.Produto;

import java.util.List;

public interface ProdutoDAO extends DAO<Produto> {

    void excluir(int id);

    public Produto buscarPorId(int id);
    public List<Produto> buscarMenosQueDez();
}

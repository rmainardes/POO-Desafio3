package Model.service.api;
import Model.entidade.Produto;
import java.util.List;

public interface ProdutoService {
    public void salvar(Produto produto);
    public void excluir(int id);
    public List<Produto> listarTodos();
    public List<Produto> buscarMenosQueDez();
    public Produto buscarPorId(int id);
}

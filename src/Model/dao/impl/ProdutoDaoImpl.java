package Model.dao.impl;
import Model.dao.api.ProdutoDAO;
import Model.entidade.Produto;
import Model.exception.ProdutoInexistenteException;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDaoImpl implements ProdutoDAO {
    private static final String INSERT = "INSERT INTO produto (descricao, quantidade) values (?, ?)";
    private static final String UPDATE = "UPDATE produto SET quantidade = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM produto WHERE id = ?";
    private static final String LISTAR_TODOS = "SELECT * FROM produto";
    private static final String LISTAR_ESTOQUE_MENOR_QUE_DEZ = "SELECT * FROM produto WHERE quantidade < 10";
    private static final String BUSCAR_POR_ID = "SELECT * FROM produto WHERE id = ?";

    @Override
    public void salvar(Produto produto) {
        if (produto != null && produto.getId() == 0) {
            this.salvarProduto(produto);
        }
        else {
            this.alterarProduto(produto);
        }
    }

    private void salvarProduto(Produto produto) {
        try (PreparedStatement ps = Conexao.getInstance().getConnection().prepareStatement(INSERT)) {
            ps.setString(1, produto.getDescricao());
            ps.setInt(2, produto.getQuantidade());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void alterarProduto(Produto produto) {
        try (PreparedStatement ps = Conexao.getInstance().getConnection().prepareStatement(UPDATE)) {
            ps.setInt(1, produto.getQuantidade());
            ps.setInt(2, produto.getId());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void excluir(int id) {
        try (PreparedStatement ps = Conexao.getInstance().getConnection().prepareStatement(DELETE)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Produto> listarTodos() {
        List<Produto> produtos = new ArrayList<>();
        try (
                PreparedStatement ps = Conexao.getInstance().getConnection().prepareStatement(LISTAR_TODOS);
                ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                Produto produto = new Produto(
                rs.getInt("id"),
                rs.getString("descricao"),
                rs.getInt("quantidade")
                );
                produtos.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }

    @Override
    public Produto buscarPorId(int id) throws ProdutoInexistenteException {
        Produto produto = null;
        try (PreparedStatement ps = Conexao.getInstance().getConnection().prepareStatement(BUSCAR_POR_ID)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    produto = new Produto(
                            rs.getInt("id"),
                            rs.getString("descricao"),
                            rs.getInt("quantidade")
                    );
                } else {
                    JOptionPane.showMessageDialog(null, "Produto com ID " + id + " não encontrado.");
                    throw new ProdutoInexistenteException("Produto com ID " + id + " não encontrado.");
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
                throw new ProdutoInexistenteException("Erro ao buscar produto no banco de dados.", e);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new ProdutoInexistenteException("Erro de acesso ao banco de dados.", e);
        }
        return produto;
    }

    @Override
    public List<Produto> buscarMenosQueDez() {
        List<Produto> produtos = new ArrayList<>();
        try (
                PreparedStatement ps = Conexao.getInstance().getConnection().prepareStatement(LISTAR_ESTOQUE_MENOR_QUE_DEZ);
                ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                try {
                    Produto produto = new Produto(
                            rs.getInt("id"),
                            rs.getString("descricao"),
                            rs.getInt("quantidade")
                    );
                    produtos.add(produto);
                } catch (Exception validationException) {
                    System.err.println("Produto com dados inválidos no banco de dados: " + validationException.getMessage());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }
    
    
    
    
    
}


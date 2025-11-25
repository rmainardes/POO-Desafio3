package View;

import Controller.api.ProdutoController;
import Controller.impl.ProdutoControllerImpl;
import Model.entidade.Produto;
import Model.exception.NomeProdutoVazio;
import Model.exception.ProdutoInexistenteException;
import Model.exception.QuantidadeNegativaException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CadastroProduto extends JFrame {

    private JPanel panel;
    private JTextField textFieldId;
    private JTextField textFieldDescricao;
    private JTextField textFieldQuantidade;
    private DefaultListModel<Produto> listaProdutosModel;

    private ProdutoController ProdutoController;

    public CadastroProduto() {
        setTitle("Cadastro de Produtos");
        setLayout(new FlowLayout());

        ProdutoController = new ProdutoControllerImpl();

        panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setPreferredSize(new Dimension(500, 500));
        add(panel);

        criarTextFieldId("Código");
        criarTextFieldDescricao("Descrição");
        criarTextFieldQuantidade("Quantidade no estoque");

        criarBotao("Salvar", new ButtonSalvarHandler(), 150);
        criarBotao("Excluir", new ButtonExcluirHandler(), 150);
        criarBotao("Buscar por ID", new ButtonBuscarPorIdHandler(), 150);
        criarBotao("Listar Todos", new ButtonListarHandler(), 220);
        criarBotao("Listar Produtos com Estoque <10", new ButtonListarMenosHandler(), 230);
        criarBotao("Gerar arquivo com lista de produtos", new ButtonGerarArquivoHandler(), 250);

        setSize(500, 500);
        setPreferredSize(new Dimension(500, 500));
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setPreferredSize(new Dimension(400, 40));
        panel.add(label);
    }

    private void criarTextFieldDescricao(String label) {
        this.criarLabel(label);
        textFieldDescricao = new JTextField();
        textFieldDescricao.setPreferredSize(new  Dimension(400, 40));
        panel.add(textFieldDescricao);
    }

    private void criarTextFieldId(String label) {
        this.criarLabel(label);
        textFieldId = new JTextField();
        textFieldId.setPreferredSize(new  Dimension(400, 40));
        panel.add(textFieldId);
    }

    private void criarTextFieldQuantidade(String label) {
        this.criarLabel(label);
        textFieldQuantidade = new JTextField();
        textFieldQuantidade.setPreferredSize(new  Dimension(400, 40));
        panel.add(textFieldQuantidade);
    }

    private void criarBotao(String label, ActionListener handler, int largura) {
        JButton botao = new JButton(label);
        botao.addActionListener(handler);
        botao.setPreferredSize(new  Dimension(largura, 40));
        panel.add(botao);
    }

    private Produto criarObjetoProduto() {
        int id = 0;
        int quantidade = 0;
        String descricao = textFieldDescricao.getText();

        String idTexto = textFieldId.getText();
        if (idTexto != null && !idTexto.isEmpty()) {
            try {
                id = Integer.parseInt(idTexto);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Erro interno ao ler ID para edição. ID inválido.", "Erro Interno", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }

        try {
            quantidade = Integer.parseInt(textFieldQuantidade.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida. Use apenas números inteiros.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        try {
            Produto produto = new Produto(id, descricao, quantidade);
            return produto;
        } catch (NomeProdutoVazio e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (QuantidadeNegativaException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private void limparCampos() {
        textFieldId.setText("");
        textFieldDescricao.setText("");
        textFieldQuantidade.setText("");
    }

    private class ButtonSalvarHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Produto Produto = criarObjetoProduto();
            ProdutoController.salvar(Produto);
            limparCampos();
            JOptionPane.showMessageDialog(null, "Produto salvo com sucesso!");
        }
    }

    private class ButtonExcluirHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ProdutoController.excluir(Integer.parseInt(textFieldId.getText()));
                limparCampos();
                JOptionPane.showMessageDialog(null, "Produto excluído com sucesso!");
            } catch (ProdutoInexistenteException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        }
    }

    private class ButtonListarHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Listagem(Listagem.TipoListagem.TODOS);
        }
    }

    private class ButtonListarMenosHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Listagem(Listagem.TipoListagem.ESTOQUE_BAIXO);
        }
    }

    private class ButtonGerarArquivoHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            criarArquivo();
        }
    }

    private class ButtonBuscarPorIdHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Produto Produto = ProdutoController.buscarPorId(Integer.parseInt(textFieldId.getText()));
            textFieldId.setText(String.valueOf(Produto.getId()));
            textFieldDescricao.setText(Produto.getDescricao());
            textFieldQuantidade.setText(String.valueOf(Produto.getQuantidade()));
        }
    }

    public void criarArquivo() {
        try {
            File file = new File("ListagemDeProdutos");
            FileWriter fileWriter = new FileWriter(file, false);
            List<Produto> ProdutosCarregados = ProdutoController.listarTodos();
            fileWriter.write("Cód.- Descricao - Qtde");
            fileWriter.write("\n");
            fileWriter.write("-----------------------");
            fileWriter.write("\n");
            for (Produto produto : ProdutosCarregados) {
                fileWriter.write(produto.toString());
                fileWriter.write("\n");
            }

            fileWriter.close();
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(null, "Erro ao escrever no arquivo");
        }
        JOptionPane.showMessageDialog(null, "Arquivo 'ListagemDeProdutos.txt' gerado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }


    public static void main (String[] args) {
        new CadastroProduto();
    }
}

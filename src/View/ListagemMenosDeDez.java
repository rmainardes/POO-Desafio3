package View;

import Controller.api.ProdutoController;
import Controller.impl.ProdutoControllerImpl;
import Model.entidade.Produto;
import Model.exception.ProdutoInexistenteException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ListagemMenosDeDez extends JFrame {
    private JPanel panel;
    private JList<Produto> listaProdutos;
    private DefaultListModel<Produto> listaProdutosModel;
    private ProdutoController ProdutoController;

    public ListagemMenosDeDez() {
        setTitle("Listagem de Produtos");
        setLayout(new FlowLayout());

        ProdutoController = new ProdutoControllerImpl();
        this.panel = new JPanel();
        this.panel.setLayout(new FlowLayout());
        this.panel.setPreferredSize(new Dimension(500, 500));
        add(this.panel);

        criarBotao("Voltar", new ButtonVoltarHandler());
        criarList();

        List<Produto> ProdutosCarregados = ProdutoController.buscarMenosQueDez();
        listaProdutosModel.addAll(ProdutosCarregados);
        setSize(new Dimension(500, 500));
        setPreferredSize(new Dimension(500, 500));
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    private void criarBotao(String label, ActionListener handler) {
        JButton botao = new JButton(label);
        botao.addActionListener(handler);
        botao.setPreferredSize(new Dimension(300, 40));
        this.panel.add(botao);
    }

    private void criarList() {
        listaProdutosModel = new DefaultListModel<Produto>();
        listaProdutos = new JList<Produto>(listaProdutosModel);
        listaProdutos.setCellRenderer(new ProdutoListCellRenderer());
        listaProdutos.setPreferredSize(new Dimension(400, 500));
        JScrollPane scrollPane = new JScrollPane(listaProdutos);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        panel.add(scrollPane);
    }

    private class ButtonVoltarHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
        }
    }

    private class ProdutoListCellRenderer extends JLabel implements ListCellRenderer<Produto> {

        public ProdutoListCellRenderer() {
            setOpaque(true);
            setHorizontalAlignment(LEFT);
            setVerticalAlignment(CENTER);
        }

        @Override
        public Component getListCellRendererComponent(
                JList<? extends Produto> list,
                Produto produto,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {

            String textoFormatado = String.format(
                    "<html><b>ID:</b> %d | <b>Descrição:</b> %s | <b>Qtde em estoque:</b> %d</html>",
                    produto.getId(),
                    produto.getDescricao(),
                    produto.getQuantidade()
            );
            setText(textoFormatado);

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            return this;
        }
    }
}

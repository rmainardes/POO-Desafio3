package Model.IO;

import Model.entidade.Produto;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ProdutoExportador {

    private static final String NOME_ARQUIVO = "ListagemDeProdutos.txt";

    public void exportarLista(List<Produto> produtos) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOME_ARQUIVO, false))) {

            writer.write("Cód. - Descricao - Qtde");
            writer.newLine();
            writer.write("--------------------------");
            writer.newLine();

            for (Produto produto : produtos) {
                writer.write(String.format("%s - %s - %d",
                        produto.getId(),
                        produto.getDescricao(),
                        produto.getQuantidade()));
                writer.newLine();
            }
        }
    }
}

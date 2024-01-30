package ygorgarofalo.Food2YouBe_Server.csvReader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ygorgarofalo.Food2YouBe_Server.entities.Product;
import ygorgarofalo.Food2YouBe_Server.entities.ProductType;
import ygorgarofalo.Food2YouBe_Server.repositories.ProductRepo;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
public class CsvService {

    @Autowired
    private ProductRepo productService;

    public void importProductsFromCsv(String filePath) {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> csvData = reader.readAll();
            // Ignora la prima riga se contiene l'intestazione
            if (!csvData.isEmpty() && isHeaderRow(csvData.get(0))) {
                csvData.remove(0);
            }
            for (String[] row : csvData) {
                Product product = mapToProduct(row);
                productService.save(product);
                System.out.println("prodotto salvato");
            }
        } catch (IOException | CsvException e) {
            // Gestisci l'eccezione in base alle tue esigenze
            e.printStackTrace();
        }
    }

    private boolean isHeaderRow(String[] row) {
        // Verifica se la riga contiene l'intestazione
        // Puoi personalizzare la logica in base al tuo file CSV
        return row.length > 0 && row[0].equalsIgnoreCase("id");
    }

    private Product mapToProduct(String[] row) {
        Product product = new Product();
        product.setCalories(Integer.parseInt(row[1]));
        product.setDescription(row[2]);

        product.setIngredients(row[4]);
        product.setName(row[5]);
        product.setPrice(Double.parseDouble(row[6]));
        product.setProductType(row[7].equals("DRINK") ? ProductType.DRINK : ProductType.FOOD);
        // Altri settaggi, se necessari
        return product;
    }


}

package ygorgarofalo.Food2YouBe_Server.csvReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CSVRunner implements CommandLineRunner {

    @Autowired
    private CsvService csvService;

    @Override
    public void run(String... args) throws Exception {


        String filePath = "./CSV/kebab_drinks.csv";
        //csvService.importProductsFromCsv(filePath);

        
    }
}


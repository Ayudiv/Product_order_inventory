package productOrderInventoryService.InventoryService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import productOrderInventoryService.InventoryService.model.Inventory;
import productOrderInventoryService.InventoryService.repository.InventoryRepositorty;

@SpringBootApplication
@EnableJpaRepositories
@EnableEurekaClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepositorty inventoryRepositorty){
		return args -> {
			Inventory inventory=new Inventory();
			inventory.setSkucode("Iphone-14");
			inventory.setQuantity(100);

			Inventory inventory1=new Inventory();
			inventory1.setSkucode("Iphone-14_blue");
			inventory1.setQuantity(0);

			inventoryRepositorty.save(inventory);
			inventoryRepositorty.save(inventory1);
		};
	}

}

package productOrderInventoryService.InventoryService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import productOrderInventoryService.InventoryService.model.Inventory;

import java.util.Optional;

@Repository
public interface InventoryRepositorty extends JpaRepository<Inventory,Long>{
    Optional<Inventory> findByskucode(String skucode);
}

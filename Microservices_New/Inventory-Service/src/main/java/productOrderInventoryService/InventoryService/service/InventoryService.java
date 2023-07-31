package productOrderInventoryService.InventoryService.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import productOrderInventoryService.InventoryService.dto.InventoryResponse;
import productOrderInventoryService.InventoryService.repository.InventoryRepositorty;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepositorty inventoryRepository;

    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> skucode) {
        log.info("Checking Inventory");
        return inventoryRepository.findByskucode(skucode.toString()).stream()
                .map(inventory -> InventoryResponse.builder().skucode(inventory.getSkucode())
                        .isInStock(inventory.getQuantity()>0)
                        .build()).toList();
    }
}
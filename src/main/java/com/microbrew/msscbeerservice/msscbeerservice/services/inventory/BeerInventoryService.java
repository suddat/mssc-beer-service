package com.microbrew.msscbeerservice.msscbeerservice.services.inventory;

import java.util.UUID;

public interface BeerInventoryService {
  Integer getOnhandInventory(UUID beerId);
}

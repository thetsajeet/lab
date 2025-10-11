package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.repositories.BeerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerControllerIT {
    @Autowired
    BeerController beerController;
    @Autowired
    BeerRepository beerRepository;

    @Test
    void listBeers() {
        assertFalse(beerController.listBeers().isEmpty());
    }

    @Transactional
    @Test
    @Rollback
    void testEmptyList(){
        beerRepository.deleteAll();
        assertTrue(beerController.listBeers().isEmpty());
    }

    @Test
    void testBeerIdNotFound(){
        assertThrows(NotFoundException.class, () -> {
            beerController.getBeerById(UUID.randomUUID());
        });
    }

    @Test
    void testBeerIdFound(){
        var beer = beerRepository.findAll().getFirst();
        var foundBeer = beerController.getBeerById(beer.getId());
        assertNotNull(foundBeer);
        assertEquals(beer.getId(), foundBeer.getId());
    }
}
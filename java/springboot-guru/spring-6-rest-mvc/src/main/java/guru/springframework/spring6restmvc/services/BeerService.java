package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by jt, Spring Framework Guru.
 */
public interface BeerService {

    List<BeerDTO> listBeers();
    Optional<BeerDTO> getBeerById(UUID id);
    BeerDTO saveNewBeer(BeerDTO beerDTO);
    Optional<BeerDTO> updateBeer(UUID beerId, BeerDTO beerDTO);
    Boolean deleteById(UUID beerId);
    void patchBeer(UUID beerId, BeerDTO beerDTO);
}

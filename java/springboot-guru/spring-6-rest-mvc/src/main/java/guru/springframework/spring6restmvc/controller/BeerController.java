package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.constants.URIConstants;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Created by jt, Spring Framework Guru.
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(URIConstants.BEER_PATH_V1)
public class BeerController {
    private final BeerService beerService;

    @GetMapping(URIConstants.BEER_ID_PATH)
    public BeerDTO getBeerById(@PathVariable UUID beerId){
        log.debug("Get BeerDTO by Id - in controller");
        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
    }

    @GetMapping()
    public List<BeerDTO> listBeers(){
        return beerService.listBeers();
    }

    @PostMapping
    public ResponseEntity<?> createBeer(@RequestBody BeerDTO beerDTO){
        BeerDTO savedBeerDTO = beerService.saveNewBeer(beerDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beerDTO/" + savedBeerDTO.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(URIConstants.BEER_ID_PATH)
    public ResponseEntity<?> updateBeer(@PathVariable UUID beerId, @RequestBody BeerDTO beerDTO){
        if(beerService.updateBeer(beerId, beerDTO).isEmpty()) {
            throw new NotFoundException();
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(URIConstants.BEER_ID_PATH)
    public ResponseEntity<?> deleteBeer(@PathVariable UUID beerId){
        if(!beerService.deleteById(beerId)) {
            throw new NotFoundException();
        }
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(URIConstants.BEER_ID_PATH)
    public ResponseEntity<?> patchBeer(@PathVariable UUID beerId, @RequestBody BeerDTO beerDTO) {
        beerService.patchBeer(beerId, beerDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    // Common technique to implement in controller
//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<String> handleNotFoundException(NotFoundException e) {
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//    }
}

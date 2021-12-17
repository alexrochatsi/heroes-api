package com.dio.heroesAPI.controller;


import com.dio.heroesAPI.document.Heroes;
import com.dio.heroesAPI.repository.HeroesRepository;
import com.dio.heroesAPI.service.HeroesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.dio.heroesAPI.constants.HeroesConstant.HEROES_ENDPOINT_LOCAL;

@RestController
public class HeroesController {

    HeroesService heroesService;
    HeroesRepository heroesRepository;

    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(HeroesController.class);

    public HeroesController (HeroesService heroesService, HeroesRepository heroesRepository) {
        this.heroesRepository = heroesRepository;
        this.heroesService = heroesService;
    }

    @GetMapping(HEROES_ENDPOINT_LOCAL)
    public Flux<Heroes> getAllItems() {
        log.info("Requisitando a lista de todos os herois");
        return heroesService.findaAll();
    }

    @GetMapping(HEROES_ENDPOINT_LOCAL + "/id")
    public Mono<ResponseEntity<Heroes>> findbyIdHero(@PathVariable String id) {
        log.info("Requesitando um heroi por ID: {}",id);
        return heroesService.findByIdHero(id)
                .map((item) -> new ResponseEntity<>(item, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(HEROES_ENDPOINT_LOCAL)
    @ResponseStatus(code=HttpStatus.CREATED)
    public Mono<Heroes> createHero(@RequestBody Heroes heroes) {
        log.info("Um novo heroi foi criado");
        return heroesService.save(heroes);
    }

    @DeleteMapping(HEROES_ENDPOINT_LOCAL)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<HttpStatus> deleteByIdHero (@PathVariable String id) {
        heroesService.deleteByIDHero(id);
        log.info("Deletando um Hero pelo ID: {}", id );
        return Mono.just(HttpStatus.CONTINUE);
    }

}

package com.dio.heroesAPI.service;


import com.dio.heroesAPI.document.Heroes;
import com.dio.heroesAPI.repository.HeroesRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class HeroesService {
    private final HeroesRepository heroesRepository;

    public HeroesService (HeroesRepository heroesRepository) {
        this.heroesRepository = heroesRepository;
    }

    public Flux<Heroes> findaAll() {
        return Flux.fromIterable(this.heroesRepository.findAll());
    }

    public Mono <Heroes> findByIdHero(String id) {
        return Mono.justOrEmpty(this.heroesRepository.findById(id));
    }

    public Mono<Heroes> save (Heroes heroes) {
        return Mono.justOrEmpty(this.heroesRepository.save(heroes));
    }

    public Mono<Boolean> deleteByIDHero (String id) {
        heroesRepository.deleteById(id);
        return Mono.justOrEmpty(true);
    }
}

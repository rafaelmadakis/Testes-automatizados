package com.example.swplanetapi.domain;

import org.springframework.stereotype.Service;

@Service
public class PlanetService {

  private final PlanetRepository planetRepository;

  public PlanetService(PlanetRepository planetRepository) {
    this.planetRepository = planetRepository;
  }


  public Planet create(Planet planet) {
    return planetRepository.save(planet);
  }

}

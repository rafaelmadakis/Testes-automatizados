package com.example.swplanetapi;


import static com.example.swplanetapi.domain.common.PlanetConstants.PLANET;
import static com.example.swplanetapi.domain.common.PlanetConstants.TATOOINE;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.swplanetapi.domain.Planet;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@ActiveProfiles("it")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/import_planets.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"/remove_planets.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class PlanetIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

//  @Test
//  public void contextLoads() {
//  }
//
  @Test
  public void createPlanet_ReturnCreated() {
   ResponseEntity<Planet> sut = testRestTemplate.postForEntity("/planets", PLANET, Planet.class);

   assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.CREATED);
   assertThat(sut.getBody().getId()).isNotNull();
   assertThat(sut.getBody().getName()).isEqualTo(PLANET.getName());
   assertThat(sut.getBody().getClimate()).isEqualTo(PLANET.getClimate());
   assertThat(sut.getBody().getTerrain()).isEqualTo(PLANET.getTerrain());

  }

  @Test
  public void getPLanet_ReturnsPlanets() {

    ResponseEntity<Planet>  sut = testRestTemplate.getForEntity("/planets/1", Planet.class );

    assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(sut.getBody()).isEqualTo(TATOOINE);
  }

  @Test
  public void getPlanetByName_ReturnsPlanet() {
    ResponseEntity<Planet>  sut = testRestTemplate.getForEntity("/planets/name/" + TATOOINE.getName(), Planet.class );

    assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(sut.getBody()).isEqualTo(TATOOINE);
  }

  @Test
  public void listPlanets_ReturnsAllPlanets() {
    ResponseEntity<Planet[]>  sut = testRestTemplate.getForEntity("/planets" , Planet[].class );

    assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(sut.getBody()).hasSize(3);
    assertThat(sut.getBody()[0]).isEqualTo(TATOOINE);

  }

  @Test
  public void listPlanets_ByClimate_ReturnsPlanets() {
    ResponseEntity<Planet[]>  sut = testRestTemplate.getForEntity("/planets?climate=" + TATOOINE.getClimate(), Planet[].class );

    assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(sut.getBody()).hasSize(1);
    assertThat(sut.getBody()[0]).isEqualTo(TATOOINE);
  }

  @Test
  public void listPlanets_ByTerrain_ReturnsPlanets() {
    ResponseEntity<Planet[]>  sut = testRestTemplate.getForEntity("/planets?terrain=" + TATOOINE.getTerrain(), Planet[].class );

    assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(sut.getBody()).hasSize(1);
    assertThat(sut.getBody()[0]).isEqualTo(TATOOINE);
  }

  @Test
  public void removePlanet_ReturnsNoContent() {
    ResponseEntity<Void> sut = testRestTemplate.exchange("/planets/" + TATOOINE.getId(), HttpMethod.DELETE,
       null, Void.class);

    assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }
}

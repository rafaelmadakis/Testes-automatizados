package com.example.swplanetapi.domain.common;

import com.example.swplanetapi.domain.Planet;

public class PlanetConstants {

  public static final Planet PLANET = new Planet("name", "clima", "terreno");

  public static final Planet INVALID_PLANET = new Planet("", "", "");

}

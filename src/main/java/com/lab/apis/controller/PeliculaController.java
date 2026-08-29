package com.lab.apis.controller;

import com.lab.apis.model.Pelicula;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/peliculas")
public class PeliculaController {
    private final List<Pelicula> datos = new ArrayList<>();
    private long siguienteId = 6L;

    public PeliculaController() {
        datos.add(new Pelicula(1L, "Inception", "Christopher Nolan", "Ciencia ficcion", 2010));
        datos.add(new Pelicula(2L, "Interstellar", "Christopher Nolan", "Ciencia ficcion", 2014));
        datos.add(new Pelicula(3L, "Parasite", "Bong Joon-ho", "Drama", 2019));
        datos.add(new Pelicula(4L, "The Godfather", "Francis Ford Coppola", "Crimen", 1972));
        datos.add(new Pelicula(5L, "Spirited Away", "Hayao Miyazaki", "Animacion", 2001));
    }

    @GetMapping public List<Pelicula> todos() { return datos; }
    @GetMapping("/{id}") public ResponseEntity<Pelicula> uno(@PathVariable Long id) { return buscar(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }
    @PostMapping public ResponseEntity<Pelicula> crear(@RequestBody Pelicula x) { x.setId(siguienteId++); datos.add(x); return ResponseEntity.status(HttpStatus.CREATED).body(x); }

    @PutMapping("/{id}") public ResponseEntity<Pelicula> put(@PathVariable Long id, @RequestBody Pelicula x) {
        Optional<Pelicula> o=buscar(id); if(o.isEmpty()) return ResponseEntity.notFound().build(); Pelicula a=o.get();
        a.setTitulo(x.getTitulo());
        a.setDirector(x.getDirector());
        a.setGenero(x.getGenero());
        a.setAnio(x.getAnio());
        return ResponseEntity.ok(a); }
    @PatchMapping("/{id}") public ResponseEntity<Pelicula> patch(@PathVariable Long id, @RequestBody Map<String,Object> c) {
        Optional<Pelicula> o=buscar(id); if(o.isEmpty()) return ResponseEntity.notFound().build(); Pelicula a=o.get();
        if(c.containsKey("titulo")) a.setTitulo(String.valueOf(c.get("titulo")));
        if(c.containsKey("director")) a.setDirector(String.valueOf(c.get("director")));
        if(c.containsKey("genero")) a.setGenero(String.valueOf(c.get("genero")));
        if(c.containsKey("anio")) a.setAnio(((Number)c.get("anio")).intValue());
        return ResponseEntity.ok(a); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> borrar(@PathVariable Long id) { return datos.removeIf(x -> x.getId().equals(id)) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build(); }
    private Optional<Pelicula> buscar(Long id) { return datos.stream().filter(x -> x.getId().equals(id)).findFirst(); }
}
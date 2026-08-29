package com.lab.apis.controller;

import com.lab.apis.model.Libro;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/libros")
public class LibroController {
    private final List<Libro> datos = new ArrayList<>();
    private long siguienteId = 6L;

    public LibroController() {
        datos.add(new Libro(1L, "Cien anos de soledad", "Gabriel Garcia Marquez", "Realismo magico", 125.00));
        datos.add(new Libro(2L, "1984", "George Orwell", "Distopia", 95.50));
        datos.add(new Libro(3L, "El principito", "Antoine de Saint-Exupery", "Fabula", 75.00));
        datos.add(new Libro(4L, "Don Quijote de la Mancha", "Miguel de Cervantes", "Novela", 150.00));
        datos.add(new Libro(5L, "Clean Code", "Robert C. Martin", "Tecnologia", 325.00));
    }

    @GetMapping public List<Libro> todos() { return datos; }
    @GetMapping("/{id}") public ResponseEntity<Libro> uno(@PathVariable Long id) { return buscar(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }
    @PostMapping public ResponseEntity<Libro> crear(@RequestBody Libro x) { x.setId(siguienteId++); datos.add(x); return ResponseEntity.status(HttpStatus.CREATED).body(x); }

    @PutMapping("/{id}") public ResponseEntity<Libro> put(@PathVariable Long id, @RequestBody Libro x) {
        Optional<Libro> o=buscar(id); if(o.isEmpty()) return ResponseEntity.notFound().build(); Libro a=o.get();
        a.setTitulo(x.getTitulo());
        a.setAutor(x.getAutor());
        a.setGenero(x.getGenero());
        a.setPrecio(x.getPrecio());
        return ResponseEntity.ok(a); }
    @PatchMapping("/{id}") public ResponseEntity<Libro> patch(@PathVariable Long id, @RequestBody Map<String,Object> c) {
        Optional<Libro> o=buscar(id); if(o.isEmpty()) return ResponseEntity.notFound().build(); Libro a=o.get();
        if(c.containsKey("titulo")) a.setTitulo(String.valueOf(c.get("titulo")));
        if(c.containsKey("autor")) a.setAutor(String.valueOf(c.get("autor")));
        if(c.containsKey("genero")) a.setGenero(String.valueOf(c.get("genero")));
        if(c.containsKey("precio")) a.setPrecio(((Number)c.get("precio")).doubleValue());
        return ResponseEntity.ok(a); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> borrar(@PathVariable Long id) { return datos.removeIf(x -> x.getId().equals(id)) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build(); }
    private Optional<Libro> buscar(Long id) { return datos.stream().filter(x -> x.getId().equals(id)).findFirst(); }
}
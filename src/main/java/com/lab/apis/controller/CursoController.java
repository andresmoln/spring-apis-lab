package com.lab.apis.controller;

import com.lab.apis.model.Curso;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {
    private final List<Curso> datos = new ArrayList<>();
    private long siguienteId = 6L;

    public CursoController() {
        datos.add(new Curso(1L, "Programacion II", "Programacion orientada a objetos con Java", 5, "Presencial"));
        datos.add(new Curso(2L, "Bases de Datos", "Fundamentos de bases de datos relacionales", 4, "Hibrida"));
        datos.add(new Curso(3L, "Redes I", "Fundamentos de redes de computadoras", 4, "Presencial"));
        datos.add(new Curso(4L, "Calculo II", "Calculo integral y aplicaciones", 5, "Presencial"));
        datos.add(new Curso(5L, "Ciberseguridad", "Fundamentos de seguridad informatica", 4, "Virtual"));
    }

    @GetMapping public List<Curso> todos() { return datos; }
    @GetMapping("/{id}") public ResponseEntity<Curso> uno(@PathVariable Long id) { return buscar(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }
    @PostMapping public ResponseEntity<Curso> crear(@RequestBody Curso x) { x.setId(siguienteId++); datos.add(x); return ResponseEntity.status(HttpStatus.CREATED).body(x); }

    @PutMapping("/{id}") public ResponseEntity<Curso> put(@PathVariable Long id, @RequestBody Curso x) {
        Optional<Curso> o=buscar(id); if(o.isEmpty()) return ResponseEntity.notFound().build(); Curso a=o.get();
        a.setNombre(x.getNombre());
        a.setDescripcion(x.getDescripcion());
        a.setCreditos(x.getCreditos());
        a.setModalidad(x.getModalidad());
        return ResponseEntity.ok(a); }
    @PatchMapping("/{id}") public ResponseEntity<Curso> patch(@PathVariable Long id, @RequestBody Map<String,Object> c) {
        Optional<Curso> o=buscar(id); if(o.isEmpty()) return ResponseEntity.notFound().build(); Curso a=o.get();
        if(c.containsKey("nombre")) a.setNombre(String.valueOf(c.get("nombre")));
        if(c.containsKey("descripcion")) a.setDescripcion(String.valueOf(c.get("descripcion")));
        if(c.containsKey("creditos")) a.setCreditos(((Number)c.get("creditos")).intValue());
        if(c.containsKey("modalidad")) a.setModalidad(String.valueOf(c.get("modalidad")));
        return ResponseEntity.ok(a); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> borrar(@PathVariable Long id) { return datos.removeIf(x -> x.getId().equals(id)) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build(); }
    private Optional<Curso> buscar(Long id) { return datos.stream().filter(x -> x.getId().equals(id)).findFirst(); }
}
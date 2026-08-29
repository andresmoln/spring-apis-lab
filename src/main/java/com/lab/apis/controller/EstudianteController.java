package com.lab.apis.controller;

import com.lab.apis.model.Estudiante;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {
    private final List<Estudiante> datos = new ArrayList<>();
    private long siguienteId = 6L;

    public EstudianteController() {
        datos.add(new Estudiante(1L, "Carlos", "Lopez", "Ingenieria en Sistemas", 20));
        datos.add(new Estudiante(2L, "Maria", "Garcia", "Administracion", 21));
        datos.add(new Estudiante(3L, "Luis", "Perez", "Ingenieria Industrial", 22));
        datos.add(new Estudiante(4L, "Ana", "Martinez", "Derecho", 19));
        datos.add(new Estudiante(5L, "Jose", "Ramirez", "Ingenieria en Sistemas", 23));
    }

    @GetMapping public List<Estudiante> todos() { return datos; }
    @GetMapping("/{id}") public ResponseEntity<Estudiante> uno(@PathVariable Long id) { return buscar(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }
    @PostMapping public ResponseEntity<Estudiante> crear(@RequestBody Estudiante x) { x.setId(siguienteId++); datos.add(x); return ResponseEntity.status(HttpStatus.CREATED).body(x); }

    @PutMapping("/{id}") public ResponseEntity<Estudiante> put(@PathVariable Long id, @RequestBody Estudiante x) {
        Optional<Estudiante> o=buscar(id); if(o.isEmpty()) return ResponseEntity.notFound().build(); Estudiante a=o.get();
        a.setNombre(x.getNombre());
        a.setApellido(x.getApellido());
        a.setCarrera(x.getCarrera());
        a.setEdad(x.getEdad());
        return ResponseEntity.ok(a); }
    @PatchMapping("/{id}") public ResponseEntity<Estudiante> patch(@PathVariable Long id, @RequestBody Map<String,Object> c) {
        Optional<Estudiante> o=buscar(id); if(o.isEmpty()) return ResponseEntity.notFound().build(); Estudiante a=o.get();
        if(c.containsKey("nombre")) a.setNombre(String.valueOf(c.get("nombre")));
        if(c.containsKey("apellido")) a.setApellido(String.valueOf(c.get("apellido")));
        if(c.containsKey("carrera")) a.setCarrera(String.valueOf(c.get("carrera")));
        if(c.containsKey("edad")) a.setEdad(((Number)c.get("edad")).intValue());
        return ResponseEntity.ok(a); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> borrar(@PathVariable Long id) { return datos.removeIf(x -> x.getId().equals(id)) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build(); }
    private Optional<Estudiante> buscar(Long id) { return datos.stream().filter(x -> x.getId().equals(id)).findFirst(); }
}
package com.lab.apis.controller;

import com.lab.apis.model.Tarea;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {
    private final List<Tarea> datos = new ArrayList<>();
    private long siguienteId = 6L;

    public TareaController() {
        datos.add(new Tarea(1L, "Estudiar Spring", "Repasar controladores REST", "ALTA", false));
        datos.add(new Tarea(2L, "Completar laboratorio", "Finalizar Laboratorio V", "ALTA", false));
        datos.add(new Tarea(3L, "Subir repositorio", "Realizar commits y push a GitHub", "MEDIA", false));
        datos.add(new Tarea(4L, "Probar endpoints", "Ejecutar pruebas en Postman", "ALTA", false));
        datos.add(new Tarea(5L, "Preparar evidencias", "Crear documento PDF con capturas", "MEDIA", false));
    }

    @GetMapping public List<Tarea> todos() { return datos; }
    @GetMapping("/{id}") public ResponseEntity<Tarea> uno(@PathVariable Long id) { return buscar(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }
    @PostMapping public ResponseEntity<Tarea> crear(@RequestBody Tarea x) { x.setId(siguienteId++); datos.add(x); return ResponseEntity.status(HttpStatus.CREATED).body(x); }

    @PutMapping("/{id}") public ResponseEntity<Tarea> put(@PathVariable Long id, @RequestBody Tarea x) {
        Optional<Tarea> o=buscar(id); if(o.isEmpty()) return ResponseEntity.notFound().build(); Tarea a=o.get();
        a.setTitulo(x.getTitulo());
        a.setDescripcion(x.getDescripcion());
        a.setPrioridad(x.getPrioridad());
        a.setCompletada(x.getCompletada());
        return ResponseEntity.ok(a); }
    @PatchMapping("/{id}") public ResponseEntity<Tarea> patch(@PathVariable Long id, @RequestBody Map<String,Object> c) {
        Optional<Tarea> o=buscar(id); if(o.isEmpty()) return ResponseEntity.notFound().build(); Tarea a=o.get();
        if(c.containsKey("titulo")) a.setTitulo(String.valueOf(c.get("titulo")));
        if(c.containsKey("descripcion")) a.setDescripcion(String.valueOf(c.get("descripcion")));
        if(c.containsKey("prioridad")) a.setPrioridad(String.valueOf(c.get("prioridad")));
        if(c.containsKey("completada")) a.setCompletada((Boolean)c.get("completada"));
        return ResponseEntity.ok(a); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> borrar(@PathVariable Long id) { return datos.removeIf(x -> x.getId().equals(id)) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build(); }
    private Optional<Tarea> buscar(Long id) { return datos.stream().filter(x -> x.getId().equals(id)).findFirst(); }
}
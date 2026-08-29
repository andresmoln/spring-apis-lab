package com.lab.apis.controller;

import com.lab.apis.model.Empleado;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {
    private final List<Empleado> datos = new ArrayList<>();
    private long siguienteId = 6L;

    public EmpleadoController() {
        datos.add(new Empleado(1L, "Andrea Morales", "Desarrolladora Backend", 8500.00, "Tecnologia"));
        datos.add(new Empleado(2L, "Pedro Castillo", "Analista Financiero", 7200.00, "Finanzas"));
        datos.add(new Empleado(3L, "Sofia Herrera", "Disenadora UX", 6800.00, "Diseno"));
        datos.add(new Empleado(4L, "Miguel Santos", "Administrador", 7500.00, "Administracion"));
        datos.add(new Empleado(5L, "Laura Diaz", "Analista SOC", 8000.00, "Ciberseguridad"));
    }

    @GetMapping public List<Empleado> todos() { return datos; }
    @GetMapping("/{id}") public ResponseEntity<Empleado> uno(@PathVariable Long id) { return buscar(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }
    @PostMapping public ResponseEntity<Empleado> crear(@RequestBody Empleado x) { x.setId(siguienteId++); datos.add(x); return ResponseEntity.status(HttpStatus.CREATED).body(x); }

    @PutMapping("/{id}") public ResponseEntity<Empleado> put(@PathVariable Long id, @RequestBody Empleado x) {
        Optional<Empleado> o=buscar(id); if(o.isEmpty()) return ResponseEntity.notFound().build(); Empleado a=o.get();
        a.setNombre(x.getNombre());
        a.setPuesto(x.getPuesto());
        a.setSalario(x.getSalario());
        a.setDepartamento(x.getDepartamento());
        return ResponseEntity.ok(a); }
    @PatchMapping("/{id}") public ResponseEntity<Empleado> patch(@PathVariable Long id, @RequestBody Map<String,Object> c) {
        Optional<Empleado> o=buscar(id); if(o.isEmpty()) return ResponseEntity.notFound().build(); Empleado a=o.get();
        if(c.containsKey("nombre")) a.setNombre(String.valueOf(c.get("nombre")));
        if(c.containsKey("puesto")) a.setPuesto(String.valueOf(c.get("puesto")));
        if(c.containsKey("salario")) a.setSalario(((Number)c.get("salario")).doubleValue());
        if(c.containsKey("departamento")) a.setDepartamento(String.valueOf(c.get("departamento")));
        return ResponseEntity.ok(a); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> borrar(@PathVariable Long id) { return datos.removeIf(x -> x.getId().equals(id)) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build(); }
    private Optional<Empleado> buscar(Long id) { return datos.stream().filter(x -> x.getId().equals(id)).findFirst(); }
}
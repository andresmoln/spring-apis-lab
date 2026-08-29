package com.lab.apis.controller;

import com.lab.apis.model.Vehiculo;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {
    private final List<Vehiculo> datos = new ArrayList<>();
    private long siguienteId = 6L;

    public VehiculoController() {
        datos.add(new Vehiculo(1L, "Toyota", "Corolla", 2022, 145000.00));
        datos.add(new Vehiculo(2L, "Honda", "Civic", 2021, 138000.00));
        datos.add(new Vehiculo(3L, "Mazda", "CX-5", 2023, 225000.00));
        datos.add(new Vehiculo(4L, "Kia", "Sportage", 2022, 189000.00));
        datos.add(new Vehiculo(5L, "Hyundai", "Elantra", 2020, 115000.00));
    }

    @GetMapping public List<Vehiculo> todos() { return datos; }
    @GetMapping("/{id}") public ResponseEntity<Vehiculo> uno(@PathVariable Long id) { return buscar(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }
    @PostMapping public ResponseEntity<Vehiculo> crear(@RequestBody Vehiculo x) { x.setId(siguienteId++); datos.add(x); return ResponseEntity.status(HttpStatus.CREATED).body(x); }

    @PutMapping("/{id}") public ResponseEntity<Vehiculo> put(@PathVariable Long id, @RequestBody Vehiculo x) {
        Optional<Vehiculo> o=buscar(id); if(o.isEmpty()) return ResponseEntity.notFound().build(); Vehiculo a=o.get();
        a.setMarca(x.getMarca());
        a.setModelo(x.getModelo());
        a.setAnio(x.getAnio());
        a.setPrecio(x.getPrecio());
        return ResponseEntity.ok(a); }
    @PatchMapping("/{id}") public ResponseEntity<Vehiculo> patch(@PathVariable Long id, @RequestBody Map<String,Object> c) {
        Optional<Vehiculo> o=buscar(id); if(o.isEmpty()) return ResponseEntity.notFound().build(); Vehiculo a=o.get();
        if(c.containsKey("marca")) a.setMarca(String.valueOf(c.get("marca")));
        if(c.containsKey("modelo")) a.setModelo(String.valueOf(c.get("modelo")));
        if(c.containsKey("anio")) a.setAnio(((Number)c.get("anio")).intValue());
        if(c.containsKey("precio")) a.setPrecio(((Number)c.get("precio")).doubleValue());
        return ResponseEntity.ok(a); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> borrar(@PathVariable Long id) { return datos.removeIf(x -> x.getId().equals(id)) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build(); }
    private Optional<Vehiculo> buscar(Long id) { return datos.stream().filter(x -> x.getId().equals(id)).findFirst(); }
}
package com.lab.apis.controller;

import com.lab.apis.model.Producto;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final List<Producto> datos = new ArrayList<>();
    private long siguienteId = 6L;

    public ProductoController() {
        datos.add(new Producto(1L, "Laptop Lenovo IdeaPad", 5499.99, "Tecnologia"));
        datos.add(new Producto(2L, "Mouse Logitech M185", 129.50, "Accesorios"));
        datos.add(new Producto(3L, "Teclado Mecanico Redragon", 399.00, "Accesorios"));
        datos.add(new Producto(4L, "Monitor Samsung 24", 1199.99, "Tecnologia"));
        datos.add(new Producto(5L, "Audifonos JBL Tune", 349.75, "Audio"));
    }

    @GetMapping public List<Producto> todos() { return datos; }
    @GetMapping("/{id}") public ResponseEntity<Producto> uno(@PathVariable Long id) { return buscar(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }
    @PostMapping public ResponseEntity<Producto> crear(@RequestBody Producto x) { x.setId(siguienteId++); datos.add(x); return ResponseEntity.status(HttpStatus.CREATED).body(x); }

    @PutMapping("/{id}") public ResponseEntity<Producto> put(@PathVariable Long id, @RequestBody Producto x) {
        Optional<Producto> o=buscar(id); if(o.isEmpty()) return ResponseEntity.notFound().build(); Producto a=o.get();
        a.setNombre(x.getNombre());
        a.setPrecio(x.getPrecio());
        a.setCategoria(x.getCategoria());
        return ResponseEntity.ok(a); }
    @PatchMapping("/{id}") public ResponseEntity<Producto> patch(@PathVariable Long id, @RequestBody Map<String,Object> c) {
        Optional<Producto> o=buscar(id); if(o.isEmpty()) return ResponseEntity.notFound().build(); Producto a=o.get();
        if(c.containsKey("nombre")) a.setNombre(String.valueOf(c.get("nombre")));
        if(c.containsKey("precio")) a.setPrecio(((Number)c.get("precio")).doubleValue());
        if(c.containsKey("categoria")) a.setCategoria(String.valueOf(c.get("categoria")));
        return ResponseEntity.ok(a); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> borrar(@PathVariable Long id) { return datos.removeIf(x -> x.getId().equals(id)) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build(); }
    private Optional<Producto> buscar(Long id) { return datos.stream().filter(x -> x.getId().equals(id)).findFirst(); }
}
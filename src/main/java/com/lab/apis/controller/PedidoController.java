package com.lab.apis.controller;

import com.lab.apis.model.Pedido;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final List<Pedido> datos = new ArrayList<>();
    private long siguienteId = 6L;

    public PedidoController() {
        datos.add(new Pedido(1L, "Juan Perez", "Laptop Lenovo IdeaPad", 1, 5499.99, "PENDIENTE"));
        datos.add(new Pedido(2L, "Maria Lopez", "Mouse Logitech M185", 2, 259.00, "PROCESANDO"));
        datos.add(new Pedido(3L, "Carlos Gomez", "Monitor Samsung 24", 1, 1199.99, "ENVIADO"));
        datos.add(new Pedido(4L, "Ana Ruiz", "Audifonos JBL Tune", 2, 699.50, "ENTREGADO"));
        datos.add(new Pedido(5L, "Luis Mendez", "Teclado Mecanico Redragon", 1, 399.00, "PENDIENTE"));
    }

    @GetMapping public List<Pedido> todos() { return datos; }
    @GetMapping("/{id}") public ResponseEntity<Pedido> uno(@PathVariable Long id) { return buscar(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }
    @PostMapping public ResponseEntity<Pedido> crear(@RequestBody Pedido x) { x.setId(siguienteId++); datos.add(x); return ResponseEntity.status(HttpStatus.CREATED).body(x); }

    @PutMapping("/{id}") public ResponseEntity<Pedido> put(@PathVariable Long id, @RequestBody Pedido x) {
        Optional<Pedido> o=buscar(id); if(o.isEmpty()) return ResponseEntity.notFound().build(); Pedido a=o.get();
        a.setCliente(x.getCliente());
        a.setProducto(x.getProducto());
        a.setCantidad(x.getCantidad());
        a.setTotal(x.getTotal());
        a.setEstado(x.getEstado());
        return ResponseEntity.ok(a); }
    @PatchMapping("/{id}") public ResponseEntity<Pedido> patch(@PathVariable Long id, @RequestBody Map<String,Object> c) {
        Optional<Pedido> o=buscar(id); if(o.isEmpty()) return ResponseEntity.notFound().build(); Pedido a=o.get();
        if(c.containsKey("cliente")) a.setCliente(String.valueOf(c.get("cliente")));
        if(c.containsKey("producto")) a.setProducto(String.valueOf(c.get("producto")));
        if(c.containsKey("cantidad")) a.setCantidad(((Number)c.get("cantidad")).intValue());
        if(c.containsKey("total")) a.setTotal(((Number)c.get("total")).doubleValue());
        if(c.containsKey("estado")) a.setEstado(String.valueOf(c.get("estado")));
        return ResponseEntity.ok(a); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> borrar(@PathVariable Long id) { return datos.removeIf(x -> x.getId().equals(id)) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build(); }
    private Optional<Pedido> buscar(Long id) { return datos.stream().filter(x -> x.getId().equals(id)).findFirst(); }
}
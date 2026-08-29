package com.lab.apis.controller;

import com.lab.apis.model.Cliente;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final List<Cliente> datos = new ArrayList<>();
    private long siguienteId = 6L;

    public ClienteController() {
        datos.add(new Cliente(1L, "Juan", "Perez", "juan.perez@example.com", "5551-1001"));
        datos.add(new Cliente(2L, "Maria", "Lopez", "maria.lopez@example.com", "5551-1002"));
        datos.add(new Cliente(3L, "Carlos", "Gomez", "carlos.gomez@example.com", "5551-1003"));
        datos.add(new Cliente(4L, "Ana", "Ruiz", "ana.ruiz@example.com", "5551-1004"));
        datos.add(new Cliente(5L, "Luis", "Mendez", "luis.mendez@example.com", "5551-1005"));
    }

    @GetMapping public List<Cliente> todos() { return datos; }
    @GetMapping("/{id}") public ResponseEntity<Cliente> uno(@PathVariable Long id) { return buscar(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); }
    @PostMapping public ResponseEntity<Cliente> crear(@RequestBody Cliente x) { x.setId(siguienteId++); datos.add(x); return ResponseEntity.status(HttpStatus.CREATED).body(x); }

    @PutMapping("/{id}") public ResponseEntity<Cliente> put(@PathVariable Long id, @RequestBody Cliente x) {
        Optional<Cliente> o=buscar(id); if(o.isEmpty()) return ResponseEntity.notFound().build(); Cliente a=o.get();
        a.setNombre(x.getNombre());
        a.setApellido(x.getApellido());
        a.setCorreo(x.getCorreo());
        a.setTelefono(x.getTelefono());
        return ResponseEntity.ok(a); }
    @PatchMapping("/{id}") public ResponseEntity<Cliente> patch(@PathVariable Long id, @RequestBody Map<String,Object> c) {
        Optional<Cliente> o=buscar(id); if(o.isEmpty()) return ResponseEntity.notFound().build(); Cliente a=o.get();
        if(c.containsKey("nombre")) a.setNombre(String.valueOf(c.get("nombre")));
        if(c.containsKey("apellido")) a.setApellido(String.valueOf(c.get("apellido")));
        if(c.containsKey("correo")) a.setCorreo(String.valueOf(c.get("correo")));
        if(c.containsKey("telefono")) a.setTelefono(String.valueOf(c.get("telefono")));
        return ResponseEntity.ok(a); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> borrar(@PathVariable Long id) { return datos.removeIf(x -> x.getId().equals(id)) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build(); }
    private Optional<Cliente> buscar(Long id) { return datos.stream().filter(x -> x.getId().equals(id)).findFirst(); }
}
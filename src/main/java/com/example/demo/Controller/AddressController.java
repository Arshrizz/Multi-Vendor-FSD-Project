
package com.example.demo.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Entity.AddressEntity;
import com.example.demo.Repository.AddressRepository;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressRepository repository;

    public AddressController(AddressRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<AddressEntity> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressEntity> getById(@PathVariable Integer id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AddressEntity> create(
            @RequestBody AddressEntity address) {
        address.setAddress_id(null);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(repository.save(address));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressEntity> update(
            @PathVariable Integer id,
            @RequestBody AddressEntity updated) {

        return repository.findById(id).map(existing -> {
            existing.setUser_id(updated.getUser_id());
            existing.setType(updated.getType());
            existing.setBuilding_name(updated.getBuilding_name());
            existing.setCity(updated.getCity());
            existing.setLandmark(updated.getLandmark());
            existing.setState(updated.getState());
            existing.setPincode(updated.getPincode());

            return ResponseEntity.ok(repository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AddressEntity> patch(
            @PathVariable Integer id,
            @RequestBody AddressEntity updates) {

        return repository.findById(id).map(existing -> {
            if (updates.getUser_id() != null)
                existing.setUser_id(updates.getUser_id());
            if (updates.getType() != null)
                existing.setType(updates.getType());
            if (updates.getBuilding_name() != null)
                existing.setBuilding_name(updates.getBuilding_name());
            if (updates.getCity() != null)
                existing.setCity(updates.getCity());
            if (updates.getLandmark() != null)
                existing.setLandmark(updates.getLandmark());
            if (updates.getState() != null)
                existing.setState(updates.getState());
            if (updates.getPincode() != null)
                existing.setPincode(updates.getPincode());

            return ResponseEntity.ok(repository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


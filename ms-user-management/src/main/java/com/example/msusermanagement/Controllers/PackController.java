package com.example.msusermanagement.Controllers;

import com.example.msusermanagement.Entities.Pack;

import com.example.msusermanagement.Services.PackServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/packs")
public class PackController {
    @Autowired
    private PackServiceImpl packService;

    @GetMapping
    public List<Pack> listPacks() {
        return packService.listPacks();
    }

    @PostMapping
    public Pack addPack(@RequestBody Pack pack) {
        return packService.addPack(pack);
    }

    @PutMapping("/{id}")
    public Pack updatePack(@PathVariable Long id, @RequestBody Pack pack) {
        return packService.updatePack(id, pack);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePack(@PathVariable Long id) {
        packService.deletePack(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Pack> getPackById(@PathVariable Long id) {
        Pack pack = packService.getPackById(id);
        return ResponseEntity.ok(pack);
    }
}

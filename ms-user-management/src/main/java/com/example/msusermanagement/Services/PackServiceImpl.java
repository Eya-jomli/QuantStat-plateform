package com.example.msusermanagement.Services;

import com.example.msusermanagement.Entities.Pack;
import com.example.msusermanagement.Repositories.PackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackServiceImpl  {
    @Autowired
    private PackRepository packRepository;


    public List<Pack> listPacks() {
        return packRepository.findAll();
    }


    public Pack addPack(Pack pack) {
        // Sauvegarder le pack avec les fonctionnalités directement
        return packRepository.save(pack);
    }


    public Pack updatePack(Long id, Pack pack) {
        Pack existingPack = packRepository.findById(id).orElseThrow(() -> new RuntimeException("Pack not found"));
        existingPack.setName(pack.getName());
        existingPack.setPrice(pack.getPrice());
        existingPack.setDescription(pack.getDescription());
        existingPack.setDuration(pack.getDuration());

        // Supprimer les anciennes fonctionnalités et ajouter les nouvelles
        existingPack.getFeatures().clear();
        existingPack.getFeatures().addAll(pack.getFeatures());

        return packRepository.save(existingPack);
    }


    public void deletePack(Long id) {
        packRepository.deleteById(id);
    }
    public Pack getPackById(Long id) {
        return packRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pack not found"));
    }
}

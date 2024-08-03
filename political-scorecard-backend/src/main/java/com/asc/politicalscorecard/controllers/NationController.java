package com.asc.politicalscorecard.controllers;

import com.asc.politicalscorecard.json.dtos.NationDTO;
import com.asc.politicalscorecard.json.dtos.PlanetDTO;
import com.asc.politicalscorecard.services.NationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nations")
public class NationController {

    private final NationService nationService;

    @Autowired
    public NationController(NationService nationService) {
        this.nationService = nationService;
    }

    // Baseline Test
    @GetMapping("")
    public ResponseEntity<String> baseline() {
        return ResponseEntity.ok("Successfully hit the Nation Controller.");
    }

    // CREATE

    @PostMapping("create")
    public ResponseEntity<NationDTO> createNation(@RequestBody NationDTO nationDTO) {
        if (nationService.createNation(nationDTO)) {
            return ResponseEntity.ok(nationDTO);
        } else {
            return ResponseEntity.status(500).build();
        }
    }

    // READ

    @GetMapping("/{id}")
    public ResponseEntity<NationDTO> getNationById(@PathVariable String id) {
        NationDTO nationDTO = nationService.getNationById(id);
        if (nationDTO != null) {
            return ResponseEntity.ok(nationDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("all")
    public ResponseEntity<List<NationDTO>> getAllNations() {
        List<NationDTO> nations = nationService.getAllNations();
        return ResponseEntity.ok(nations);
    }

    // UPDATE

    @PutMapping("/{id}")
    public ResponseEntity<NationDTO> updateNation(@PathVariable String id, @RequestBody NationDTO nationDTO) {
        System.out.println("Inside update Controller function");
        nationDTO.setId(id); // Ensure the ID is set correctly
        if (nationService.updateNation(nationDTO)) {
            return ResponseEntity.ok(nationDTO);
        } else {
            return ResponseEntity.status(500).build();
        }
    }

    // DELETE

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNation(@PathVariable String id) {
        if (nationService.deleteNation(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(500).build();
        }
    }
}

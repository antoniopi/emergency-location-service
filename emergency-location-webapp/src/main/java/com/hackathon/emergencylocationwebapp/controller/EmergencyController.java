package com.hackathon.emergencylocationwebapp.controller;

import com.hackathon.emergencylocationwebapp.model.Emergency;
import com.hackathon.emergencylocationwebapp.model.Location;
import com.hackathon.emergencylocationwebapp.repository.EmergencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/emergencies")
public class EmergencyController {

    private final EmergencyRepository repo;

    @Autowired
    private SimpMessagingTemplate template;

    public EmergencyController(EmergencyRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Emergency> getEmergencies() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Emergency getEmergency(@PathVariable Long id) {
        return repo.findById(id).orElse(null);
    }

    @PostMapping
    public ResponseEntity<Emergency> createEmergency(@RequestBody Emergency emergency) throws URISyntaxException {
        Emergency savedEmergency = repo.save(emergency);
        template.convertAndSend("/topic/emergencies/new", savedEmergency);
        return ResponseEntity.created(new URI("/emergencies/" + savedEmergency.getId())).body(savedEmergency);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Emergency> updateEmergency(@PathVariable Long id, @RequestBody Emergency emergency) {
        Emergency updatedEmergency = repo.findById(id)
                .map(storedEmergency -> {
                    storedEmergency.setLocation(emergency.getLocation());
                    storedEmergency.setCallerTelephoneNumber(emergency.getCallerTelephoneNumber());
                    return repo.save(storedEmergency);
                })
                .orElseGet(() -> repo.save(emergency));
        template.convertAndSend("/topic/emergencies/update", emergency);
        return ResponseEntity.ok(updatedEmergency);
    }

    @PutMapping("/{id}/updateLocation")
    public ResponseEntity<Emergency> updateEmergencyLocation(@PathVariable Long id, @RequestBody Location location) {
        Emergency emergency = repo.findById(id).orElseThrow();
        emergency.setLocation(location);
        Emergency updatedEmergency = repo.save(emergency);
        template.convertAndSend("/topic/emergencies/update", emergency);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Emergency> deleteEmergency(@PathVariable Long id) {
        repo.deleteById(id);
        template.convertAndSend("/topic/emergencies/delete", id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Emergency> deleteAllEmergencies() {
        repo.deleteAll();
        template.convertAndSend("/topic/emergencies/delete/all", "");
        return ResponseEntity.ok().build();
    }
}

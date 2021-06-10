package learn.field_agent.controllers;

import learn.field_agent.domain.Result;
import learn.field_agent.models.SecurityClearance;
import learn.field_agent.domain.clearanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author NAHID
 * Date  07,  June, 2021
 **/
@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/clearance")
public class SecurityClearanceController {

    private final clearanceService service;

    public SecurityClearanceController(clearanceService service) {
        this.service = service;
    }

    // GET ALL SECURITY CLEARANCE
    @GetMapping
    public List<SecurityClearance> findAll() {
        return service.findAll();
    }

    // GET SECURITY CLEARANCE BY ID
    @GetMapping("/{securityClearanceId}")
    public SecurityClearance findById(@PathVariable int securityClearanceId) {
        return service.findById(securityClearanceId);
    }

    // ADD NEW CLEARANCE
    @PostMapping
    public ResponseEntity<Object> add(@RequestBody SecurityClearance securityClearance) {
        Result<SecurityClearance> result = service.add(securityClearance);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    // UPDATE EXISTING SECURITY CLEARANCE
    @PutMapping("/{securityClearanceId}")
    public ResponseEntity<Object> update(@PathVariable int securityClearanceId, @RequestBody SecurityClearance securityClearance) {
        if (securityClearanceId != securityClearance.getSecurityClearanceId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<SecurityClearance> result = service.update(securityClearance);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    // DELETE EXISTING SECURITY CLEARANCE BY ID
    @DeleteMapping("/{securityClearanceId}")
    public ResponseEntity<Void> deleteById(@PathVariable int securityClearanceId) {
        if (service.deleteById(securityClearanceId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

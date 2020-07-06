package hr.luka.b2match.controller;

import hr.luka.b2match.data.model.Organization;
import hr.luka.b2match.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    private OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService){
        this.organizationService = organizationService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Organization>> getAllOrganizations(){
        return ResponseEntity.ok(this.organizationService.getAllOrganizations());
    }

    @GetMapping("")
    public ResponseEntity<Organization> getOrganizationById(@RequestParam(name = "organizationId") Long organizationId){
        return ResponseEntity.ok(this.organizationService.getOrganizationById(organizationId));
    }

    @PostMapping("")
    public ResponseEntity<Boolean> registerOrganization(@RequestBody Organization organization){
        return ResponseEntity.ok(this.organizationService.registerOrganization(organization));
    }
}

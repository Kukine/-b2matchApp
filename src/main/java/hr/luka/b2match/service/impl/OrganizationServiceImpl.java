package hr.luka.b2match.service.impl;

import hr.luka.b2match.data.model.Organization;
import hr.luka.b2match.data.repository.OrganizationRepository;
import hr.luka.b2match.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static hr.luka.b2match.config.exception.ExceptionSuppliers.ENTITY_NOT_FOUND_EXCEPTION;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationServiceImpl(OrganizationRepository organizationRepository){
        this.organizationRepository = organizationRepository;
    }

    @Override
    public Organization getOrganizationById(Long organizationId){
        return this.organizationRepository.findById(organizationId).orElseThrow(ENTITY_NOT_FOUND_EXCEPTION);
    }

    @Override
    public boolean registerOrganization(Organization organization) {
       if(!this.organizationRepository.existsByName(organization.getName())){
           this.organizationRepository.save(organization);
           return true;
       }
       return false;
    }

    @Override
    public List<Organization> getAllOrganizations() {
        return this.organizationRepository.findAll();
    }


}

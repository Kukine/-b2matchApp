package hr.luka.b2match.service;


import hr.luka.b2match.data.model.Organization;

import java.util.List;

public interface OrganizationService {

    Organization getOrganizationById(Long organizationId);

    boolean registerOrganization(Organization organization);

    List<Organization> getAllOrganizations();


}

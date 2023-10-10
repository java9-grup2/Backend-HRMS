package org.hrms.service;

import org.hrms.repository.IPackageRepository;
import org.hrms.repository.entity.Package;
import org.hrms.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class PackageService extends ServiceManager<Package, Long> {

    private final IPackageRepository repository;

    public PackageService(IPackageRepository repository) {
        super(repository);
        this.repository = repository;
    }
}

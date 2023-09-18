package org.hrms.service;

import org.hrms.exception.CompanyManagerException;
import org.hrms.exception.ErrorType;
import org.hrms.mapper.ICompanyMapper;
import org.hrms.rabbitmq.model.CreateCompanyModel;
import org.hrms.rabbitmq.producer.DeleteAuthByIdProducer;
import org.hrms.repository.ICompanyRepository;
import org.hrms.repository.entity.Company;
import org.hrms.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class CompanyService extends ServiceManager<Company, Long> {

    private final ICompanyRepository repository;
    private final DeleteAuthByIdProducer deleteAuthByIdProducer;

    public CompanyService(ICompanyRepository repository, DeleteAuthByIdProducer deleteAuthByIdProducer) {
        super(repository);
        this.repository = repository;
        this.deleteAuthByIdProducer = deleteAuthByIdProducer;
    }

    public Boolean createCompany(CreateCompanyModel model) {
        boolean existsByTaxNo = repository.existsByTaxNo(model.getTaxNo());
        if (existsByTaxNo) {
//            userService'e gider ve verilen authid'li useri siler
            deleteAuthByIdProducer.deleteAuthById(model.getManagerId());
            return false;
        }
        try {
            Company company = ICompanyMapper.INSTANCE.toCompany(model);
            company.setNumOfEmployees(1L);
            save(company);
            return true;
        } catch (Exception e) {
            throw new CompanyManagerException(ErrorType.COMPANY_NOT_CREATED);
        }

    }

    public Boolean IsCompanyExists(String companyName) {
        return repository.existsByCompanyName(companyName);
    }
}

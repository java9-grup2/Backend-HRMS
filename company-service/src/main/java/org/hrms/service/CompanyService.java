package org.hrms.service;

import org.hrms.dto.request.UpdateCompanyRequestDto;
import org.hrms.exception.CompanyManagerException;
import org.hrms.exception.ErrorType;
import org.hrms.mapper.ICompanyMapper;
import org.hrms.rabbitmq.model.CreateCompanyModel;
import org.hrms.rabbitmq.model.UpdateAuthCompanyNameDetailsModel;
import org.hrms.rabbitmq.model.UpdateUsersCompanyNameDetailsModel;
import org.hrms.rabbitmq.producer.DeleteAuthByIdProducer;
import org.hrms.rabbitmq.producer.DeleteUsersContainsCompanyNameProducer;
import org.hrms.rabbitmq.producer.UpdateAuthCompanyNameDetailsProducer;
import org.hrms.rabbitmq.producer.UpdateUsersCompanyNameDetailsProducer;
import org.hrms.repository.ICompanyRepository;
import org.hrms.repository.entity.Company;
import org.hrms.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService extends ServiceManager<Company, Long> {

    private final ICompanyRepository repository;
    private final DeleteAuthByIdProducer deleteAuthByIdProducer;

    private final DeleteUsersContainsCompanyNameProducer deleteUsersContainsCompanyNameProducer;

    private final UpdateUsersCompanyNameDetailsProducer updateUsersCompanyNameDetailsProducer;

    private final UpdateAuthCompanyNameDetailsProducer updateAuthCompanyNameDetailsProducer;

    public CompanyService(ICompanyRepository repository, DeleteAuthByIdProducer deleteAuthByIdProducer, DeleteUsersContainsCompanyNameProducer deleteUsersContainsCompanyNameProducer, UpdateUsersCompanyNameDetailsProducer updateUsersCompanyNameDetailsProducer, UpdateAuthCompanyNameDetailsProducer updateAuthCompanyNameDetailsProducer) {
        super(repository);
        this.repository = repository;
        this.deleteAuthByIdProducer = deleteAuthByIdProducer;
        this.deleteUsersContainsCompanyNameProducer = deleteUsersContainsCompanyNameProducer;
        this.updateUsersCompanyNameDetailsProducer = updateUsersCompanyNameDetailsProducer;
        this.updateAuthCompanyNameDetailsProducer = updateAuthCompanyNameDetailsProducer;
    }

    public Boolean createCompany(CreateCompanyModel model) {
        boolean existsByTaxNo = repository.existsByTaxNo(model.getTaxNo());
        boolean existsByCompanyName = repository.existsByCompanyName(model.getCompanyName());
        if (existsByTaxNo || existsByCompanyName) {
//            userService'e gider ve verilen authid'li useri siler
            deleteAuthByIdProducer.deleteAuthById(model.getManagerId());
            return false;
        }
        try {
            Company company = ICompanyMapper.INSTANCE.toCompany(model);
            company.setCompanyName(company.getCompanyName().toLowerCase());
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
    public Company findCompanyById(Long id) {
        Optional<Company> optionalCompany = findById(id);

        if (optionalCompany.isEmpty()) {
            throw new CompanyManagerException(ErrorType.COMPANY_NOT_FOUND);
        }
        return optionalCompany.get();
    }

    public Boolean updateCompanyDetailsByManager(UpdateCompanyRequestDto dto) {
        Optional<Company> optionalCompany = findById(dto.getId());
        if (optionalCompany.isEmpty()) {
            throw new CompanyManagerException(ErrorType.COMPANY_NOT_FOUND);
        }
        boolean existsByCompanyName = repository.existsByCompanyName(dto.getCompanyName());

        boolean existsByTaxNo = repository.existsByTaxNo(dto.getTaxNo());

        boolean isCompanyNameSame = optionalCompany.get().getCompanyName().equals(dto.getCompanyName());
        if (!isCompanyNameSame && existsByCompanyName) {
            throw new CompanyManagerException(ErrorType.COMPANYNAME_EXISTS);
        }

        if (!optionalCompany.get().getTaxNo().equals(dto.getTaxNo()) && existsByTaxNo) {
            throw new CompanyManagerException(ErrorType.TAXNO_IS_BELONG_TO_ANOTHER_COMPANY);
        }

        if (!isCompanyNameSame) {
            UpdateUsersCompanyNameDetailsModel updateUsersCompanyNameDetailsModel = new UpdateUsersCompanyNameDetailsModel();
            updateUsersCompanyNameDetailsModel.setOldCompanyName(optionalCompany.get().getCompanyName());
            updateUsersCompanyNameDetailsModel.setNewCompanyName(dto.getCompanyName());
            updateUsersCompanyNameDetailsProducer.updateUsersCompanyNameDetails(updateUsersCompanyNameDetailsModel);

            UpdateAuthCompanyNameDetailsModel updateAuthCompanyNameDetailsModel = new UpdateAuthCompanyNameDetailsModel();
            updateAuthCompanyNameDetailsModel.setOldCompanyName(optionalCompany.get().getCompanyName());
            updateAuthCompanyNameDetailsModel.setNewCompanyName(dto.getCompanyName());
            updateAuthCompanyNameDetailsProducer.updateAuthCompanyNameDetails(updateAuthCompanyNameDetailsModel);
        }

        optionalCompany.get().setCompanyName(dto.getCompanyName());
        optionalCompany.get().setAbout(dto.getAbout());
        optionalCompany.get().setNumOfEmployees(dto.getNumOfEmployees());
        optionalCompany.get().setTaxNo(dto.getTaxNo());
        update(optionalCompany.get());
        return true;

    }

    public Boolean deleteCompanyById(Long id) {
        Optional<Company> optionalCompany = findById(id);
        if (optionalCompany.isEmpty()) {
            throw new CompanyManagerException(ErrorType.COMPANY_NOT_FOUND);
        }
        deleteById(id);

        deleteUsersContainsCompanyNameProducer.deleteUsersContainsCompanyName(ICompanyMapper.INSTANCE.toDeleteUsersContainsCompanyNameModel(optionalCompany.get()));

        return true;
    }
}

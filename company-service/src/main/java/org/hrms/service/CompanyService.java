package org.hrms.service;

import org.hrms.dto.request.IsCompanyRequestValidDto;
import org.hrms.dto.request.PublicHolidayCompanyRequestDto;
import org.hrms.dto.request.UpdateCompanyRequestDto;
import org.hrms.dto.response.ContactInformationResponseDto;
import org.hrms.exception.CompanyManagerException;
import org.hrms.exception.ErrorType;
import org.hrms.mapper.ICompanyMapper;
import org.hrms.rabbitmq.model.*;
import org.hrms.rabbitmq.producer.DeleteAuthByIdProducer;
import org.hrms.rabbitmq.producer.DeleteUsersContainsCompanyNameProducer;
import org.hrms.rabbitmq.producer.UpdateAuthCompanyNameDetailsProducer;
import org.hrms.rabbitmq.producer.UpdateUsersCompanyNameDetailsProducer;
import org.hrms.repository.ICompanyRepository;
import org.hrms.repository.entity.Company;
import org.hrms.repository.enums.EStatus;
import org.hrms.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService extends ServiceManager<Company, Long> {

    private final ICompanyRepository repository;
    private final DeleteAuthByIdProducer deleteAuthByIdProducer;

    private final DeleteUsersContainsCompanyNameProducer deleteUsersContainsCompanyNameProducer;

    private final UpdateUsersCompanyNameDetailsProducer updateUsersCompanyNameDetailsProducer;

    private final UpdateAuthCompanyNameDetailsProducer updateAuthCompanyNameDetailsProducer;
   public final List<PublicHolidayCompanyRequestDto> publicHoliday;

    public CompanyService(ICompanyRepository repository, DeleteAuthByIdProducer deleteAuthByIdProducer, DeleteUsersContainsCompanyNameProducer deleteUsersContainsCompanyNameProducer, UpdateUsersCompanyNameDetailsProducer updateUsersCompanyNameDetailsProducer, UpdateAuthCompanyNameDetailsProducer updateAuthCompanyNameDetailsProducer, List<PublicHolidayCompanyRequestDto> publicHoliday) {
        super(repository);
        this.repository = repository;
        this.deleteAuthByIdProducer = deleteAuthByIdProducer;
        this.deleteUsersContainsCompanyNameProducer = deleteUsersContainsCompanyNameProducer;
        this.updateUsersCompanyNameDetailsProducer = updateUsersCompanyNameDetailsProducer;
        this.updateAuthCompanyNameDetailsProducer = updateAuthCompanyNameDetailsProducer;
        this.publicHoliday = publicHoliday;
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
//        Optional<Company> optionalCompany = repository.findByCompanyName(dto.getCompanyName());
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
            updateUsersCompanyNameDetailsModel.setNewCompanyName(dto.getCompanyName().toLowerCase());
            updateUsersCompanyNameDetailsProducer.updateUsersCompanyNameDetails(updateUsersCompanyNameDetailsModel);

            UpdateAuthCompanyNameDetailsModel updateAuthCompanyNameDetailsModel = new UpdateAuthCompanyNameDetailsModel();
            updateAuthCompanyNameDetailsModel.setOldCompanyName(optionalCompany.get().getCompanyName());
            updateAuthCompanyNameDetailsModel.setNewCompanyName(dto.getCompanyName().toLowerCase());
            updateAuthCompanyNameDetailsProducer.updateAuthCompanyNameDetails(updateAuthCompanyNameDetailsModel);
        }

        optionalCompany.get().setCompanyName(dto.getCompanyName().toLowerCase());
        optionalCompany.get().setAbout(dto.getAbout());
        optionalCompany.get().setNumOfEmployees(dto.getNumOfEmployees());
        optionalCompany.get().setTaxNo(dto.getTaxNo());
        optionalCompany.get().setAddress(dto.getAddress());
        optionalCompany.get().setFax(dto.getFax());
        optionalCompany.get().setPhone(dto.getPhone());
        optionalCompany.get().setCompanyEmail(dto.getCompanyEmail());
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

    public List<PublicHolidayCompanyRequestDto> getPublicHoliday(){

        List<PublicHolidayCompanyRequestDto> listHoliday = new ArrayList<>();

        PublicHolidayCompanyRequestDto holiday1 = new PublicHolidayCompanyRequestDto();
        Long id1= 1L;
        holiday1.setId(id1);
        holiday1.setName("Yılbaşı");
        holiday1.setDate("01.01.2023");
        holiday1.setExplanation("Miladi takvimin ilk günü");
        listHoliday.add(holiday1);

        PublicHolidayCompanyRequestDto holiday2 = new PublicHolidayCompanyRequestDto();
        Long id2= 2L;
        holiday2.setId(id2);
        holiday2.setName("Ramazan Bayramı");
        holiday2.setDate("21.04.2023 - 23.04.2023");
        holiday2.setExplanation("Dini bayram hicri takvime göre ramazan ayının sonu");
        listHoliday.add(holiday2);

        PublicHolidayCompanyRequestDto holiday3 = new PublicHolidayCompanyRequestDto();
        Long id3= 3L;
        holiday3.setId(id3);
        holiday3.setName("Ulusal Egemenlik ve Çocuk Bayramı");
        holiday3.setDate("23.04.2023");
        holiday3.setExplanation("Ulusal Egemenlik ve Çocuk Bayramının kutlandığı gündür");
        listHoliday.add(holiday3);

        PublicHolidayCompanyRequestDto holiday4 = new PublicHolidayCompanyRequestDto();
        Long id4= 4L;
        holiday4.setId(id4);
        holiday4.setName("Emek ve Dayanışma Günü");
        holiday4.setDate("01.05.2023");
        holiday4.setExplanation("Emekçi bayramıdır.");
        listHoliday.add(holiday4);

        PublicHolidayCompanyRequestDto holiday5 = new PublicHolidayCompanyRequestDto();
        Long id5= 5L;
        holiday5.setId(id5);
        holiday5.setName("Atatürk’ü Anma, Gençlik ve Spor Bayramı");
        holiday5.setDate("19.05.2023");
        holiday5.setExplanation("Atatürk’ü Anma, Gençlik ve Spor Bayramı'dır.");
        listHoliday.add(holiday5);

        PublicHolidayCompanyRequestDto holiday6 = new PublicHolidayCompanyRequestDto();
        Long id6= 6L;
        holiday6.setId(id6);
        holiday6.setName("Kurban Bayramı");
        holiday6.setDate("28.06.2023-01.07.2023");
        holiday6.setExplanation("Kurban Bayramıdır.");
        listHoliday.add(holiday6);

        PublicHolidayCompanyRequestDto holiday7 = new PublicHolidayCompanyRequestDto();
        Long id7= 7L;
        holiday7.setId(id7);
        holiday7.setName("Zafer Bayramı");
        holiday7.setDate("30.08.2023");
        holiday7.setExplanation("Zafer Bayramıdır.");
        listHoliday.add(holiday7);

        PublicHolidayCompanyRequestDto holiday8 = new PublicHolidayCompanyRequestDto();
        Long id8= 8L;
        holiday8.setId(id8);
        holiday8.setName("Cumhuriyet Bayramı");
        holiday8.setDate("29.10.2023");
        holiday8.setExplanation("Cumhuriyet Bayramı'dır.");
        listHoliday.add(holiday8);


        return listHoliday;
    }

    public Company findCompanyByCompanyName(String companyName) {
        Optional<Company> optionalCompany = repository.findByCompanyName(companyName);
        if (optionalCompany.isEmpty()) {
            throw new CompanyManagerException(ErrorType.COMPANY_NOT_FOUND);
        }
        return optionalCompany.get();
    }

    public Boolean deleteByCompanyName(DeleteCompanyByRegisterDenyModel model) {
        Optional<Company> optionalCompany = repository.findByCompanyName(model.getCompanyName());
        if (optionalCompany.isEmpty()) {
            throw new CompanyManagerException(ErrorType.COMPANY_NOT_FOUND);
        }
        try {
            deleteById(optionalCompany.get().getId());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean activateCompanyStatus(ActivateCompanyStatusModel model) {

        Optional<Company> optionalCompany = repository.findByCompanyName(model.getCompanyName());

        if (optionalCompany.isEmpty()) {
            throw new CompanyManagerException(ErrorType.COMPANY_NOT_FOUND);
        }
        optionalCompany.get().setStatus(EStatus.ACTIVE);
        update(optionalCompany.get());
        return true;
    }

    public List<Company> findAll2() {
        List<Company> list = findAll();

        if(list.isEmpty()){
            throw  new CompanyManagerException(ErrorType.NO_DATA);
        }
        return list;
    }

    public Boolean isCompanyRequestValid(IsCompanyRequestValidDto dto) {
        boolean existsByTaxNo = repository.existsByTaxNo(dto.getTaxNo());
        boolean existsByCompanyName = repository.existsByCompanyName(dto.getCompanyName());
        if (existsByCompanyName || existsByTaxNo) {
            return false;
        }
        return true;
    }

    public Boolean increaseCompanyWorker(IncreaseCompanyWorkerModel model) {

        Optional<Company> optionalCompany = repository.findByCompanyName(model.getCompanyName());
        if (optionalCompany.isEmpty()) {
            throw new CompanyManagerException(ErrorType.COMPANY_NOT_FOUND);
        }
        optionalCompany.get().setNumOfEmployees(optionalCompany.get().getNumOfEmployees()+1L);
        update(optionalCompany.get());
        return true;
    }

    public ContactInformationResponseDto getContactInformation(String companyName) {
        Optional<Company> optionalCompany = repository.findByCompanyName(companyName);
        if (optionalCompany.isEmpty()) {
            throw new CompanyManagerException(ErrorType.COMPANY_NOT_FOUND);
        }

        return ICompanyMapper.INSTANCE.toContactInformationResponseDto(optionalCompany.get());
    }
}

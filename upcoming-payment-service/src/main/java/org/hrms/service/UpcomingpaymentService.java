package org.hrms.service;

import org.hrms.dto.CompanyNameToStringDto;
import org.hrms.exception.ErrorType;
import org.hrms.exception.UpcomingPaymentManagerException;
import org.hrms.mapper.IUpcomingPaymentMapper;
import org.hrms.repository.IUpcomingpaymentRepository;
import org.hrms.repository.Upcomingpayment;
import org.hrms.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UpcomingpaymentService extends ServiceManager<Upcomingpayment, Long> {
    private final IUpcomingpaymentRepository repository;


    public UpcomingpaymentService(IUpcomingpaymentRepository repository, IUpcomingPaymentMapper iUpcomingPaymentMapper) {
        super(repository);
        this.repository = repository;
    }

//    public Boolean createNewCompany(CompanySaveToUpcomingDto dto) {
//        Upcomingpayment upcomingpayment = IUpcomingPaymentMapper.INSTANCE.toUpcomingpayment(dto);
//        this.save(upcomingpayment);
//        return true;
//    }

    public List<Upcomingpayment> findAllWithCompany(String companyName) {
        List<Upcomingpayment> list = this.findAll();
        List<Upcomingpayment> listCompany = new ArrayList();
        if (list.isEmpty()) {
            throw new UpcomingPaymentManagerException(ErrorType.PAYMENT_NOT_FOUND);
        } else {


            for (int i = 0; i < list.size(); ++i) {
                if (list.get(i).getCompanyName().equals(companyName)) {
                    listCompany.add(list.get(i));
                }
            }

            if (listCompany.isEmpty()) {
                throw new UpcomingPaymentManagerException(ErrorType.PAYMENT_NOT_FOUND);
            } else {
                return listCompany;
            }
        }
    }

    public Boolean deletePaymentById(Long id) {
        this.deleteById(id);
        return true;
    }

    public String saveUpcomingPayment(Upcomingpayment upcomingpayment) {
        System.out.println(upcomingpayment.getPaymentName());
        if(upcomingpayment.getCompanyName() == null || (upcomingpayment.getCompanyName().equals("")))  {
            throw  new UpcomingPaymentManagerException(ErrorType.COMPANY_NOT_CREATED);
        }
        if(upcomingpayment.getPaymentName() == null || (upcomingpayment.getPaymentName().equals(""))){
            throw  new UpcomingPaymentManagerException(ErrorType.NOT_FOUND_PAYMENT_NAME);
        }
        if(upcomingpayment.getPaymentAmount() == null || (upcomingpayment.getPaymentAmount().equals(""))){

        }
        if (upcomingpayment.getPaymentDate() == null || (upcomingpayment.getPaymentDate().equals(""))){
            throw  new UpcomingPaymentManagerException(ErrorType.NOT_FOUND_PAYMENT_DATE);
        }
       try {

           save(upcomingpayment);
       }catch (Exception e) {
           throw new UpcomingPaymentManagerException(ErrorType.DATABASE_SAVE_PROBLEM);
       }
       return "Kayit islemi yapıldı";
    }
}
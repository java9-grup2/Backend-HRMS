package org.hrms.service;

import org.hrms.dto.request.CreateFinancialPerformanceRequestDto;
import org.hrms.dto.request.FindSelectedYearRequestDto;
import org.hrms.exception.ErrorType;
import org.hrms.exception.FinancialPerformanceException;
import org.hrms.manager.ICompanyManager;
import org.hrms.mapper.IFinancialPerformanceMapper;
import org.hrms.repository.IFinancialPerformanceRepository;
import org.hrms.repository.entity.FinancialPerformance;
import org.hrms.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinancialPerformanceService extends ServiceManager<FinancialPerformance,Long> {

    private final IFinancialPerformanceRepository repository;
    private final ICompanyManager companyManager;

    public FinancialPerformanceService(IFinancialPerformanceRepository repository, ICompanyManager companyManager) {
        super(repository);
        this.repository = repository;
        this.companyManager = companyManager;
    }

    /**
     * Sirketin girilen yil icin harcamalarini tutar, eger daha onceden girilmis bir yil bilgisi girilirse eski bilgiyi gunceller.
     * @param dto
     * @return
     */
    public Boolean create(CreateFinancialPerformanceRequestDto dto) {

        if (!dto.getUserType().equals("MANAGER")) {
            throw new FinancialPerformanceException(ErrorType.INSUFFICIENT_PERMISSION);
        }

        Boolean isCompanyExists = companyManager.isCompanyExists(dto.getCompanyName()).getBody();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (!isCompanyExists) {
            throw new FinancialPerformanceException(ErrorType.COMPANY_NOT_FOUND);
        }

        List<FinancialPerformance> financialPerformanceList = repository.findByCompanyName(dto.getCompanyName());

        int SelectedYear = dto.getFinancialYear().getYear();

        for (FinancialPerformance financialPerformance : financialPerformanceList) {
            if (financialPerformance.getFinancialYear().getYear() == SelectedYear) {
                financialPerformance.setFinancialYear(dto.getFinancialYear());
                financialPerformance.setAnnualRevenue(dto.getAnnualRevenue());
                financialPerformance.setAnnualExpenses(dto.getAnnualExpenses());
                update(financialPerformance);
                return true;
            }
        }

        try {
            save(IFinancialPerformanceMapper.INSTANCE.toFinancialPerformance(dto));
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<FinancialPerformance> listCompanyFinancialPerformance(String companyName) {
        List<FinancialPerformance> listFinancialPerformance = repository.findByCompanyName(companyName);
        if (listFinancialPerformance.isEmpty()) {
            throw new FinancialPerformanceException(ErrorType.COMPANY_NOT_FOUND);
        }

        return listFinancialPerformance;
    }

    public FinancialPerformance findSelectedYear(FindSelectedYearRequestDto dto) {
        boolean existsByCompanyName = repository.existsByCompanyName(dto.getCompanyName());
        if (!existsByCompanyName) {
            throw new FinancialPerformanceException(ErrorType.COMPANY_NOT_FOUND);
        }

        List<FinancialPerformance> performanceList = repository.findByCompanyName(dto.getCompanyName());

        for (FinancialPerformance financialPerformance : performanceList) {
            if (financialPerformance.getFinancialYear().getYear() == dto.getSelectedYear()) {
                return financialPerformance;
            }
        }

        throw new FinancialPerformanceException(ErrorType.NO_DETAILS_FOR_SELECTED_YEAR);
    }

    /**
     * Şirketin toplam gider bilgisini hesaplayan metod.
     * @param companyName Şirket adı
     * @return Toplam gider bilgisi
     */
    public Double calculateTotalExpensesForCompany(String companyName) {
        List<FinancialPerformance> financialPerformanceList = repository.findByCompanyName(companyName);

        Double totalExpenses = 0.0;

        for (FinancialPerformance financialPerformance : financialPerformanceList) {
            if (financialPerformance.getAnnualExpenses() != null) {
                totalExpenses += financialPerformance.getAnnualExpenses();
            }
        }

        return totalExpenses;
    }

}

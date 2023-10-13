package org.hrms.service;

import org.hrms.dto.request.GeneralStatusRequestDto;
import org.hrms.dto.response.SuccessMessageResponseDto;
import org.hrms.exception.ErrorType;
import org.hrms.exception.PackageManagerException;
import org.hrms.mapper.IPackageMapper;
import org.hrms.rabbitmq.model.ActivateCompanyPackageModel;
import org.hrms.rabbitmq.model.CreateCompanyPackageModel;
import org.hrms.rabbitmq.model.DenyCompanyPackageModel;
import org.hrms.repository.IPackageRepository;
import org.hrms.repository.entity.Package;
import org.hrms.repository.enums.EPackageType;
import org.hrms.repository.enums.EStatus;
import org.hrms.repository.enums.EUserType;
import org.hrms.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PackageService extends ServiceManager<Package, Long> {

    private final IPackageRepository repository;

    public PackageService(IPackageRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public SuccessMessageResponseDto createPackage(CreateCompanyPackageModel model) {

        if (!model.getUserType().equals(EUserType.MANAGER)) {
            throw new PackageManagerException(ErrorType.INSUFFICIENT_PERMISSION);
        }

        String companyName = model.getCompanyName();

        Optional<Package> optionalPackage = repository.findActivatedCompanyPack(companyName);
        if (!optionalPackage.isEmpty()) {
            throw new PackageManagerException(ErrorType.COMPANY_PACKAGE_ALREADY_EXIST);
        }

        Optional<Package> optionalPendingCompanyPack = repository.findPendingCompanyPack(model.getCompanyName());
        if (!optionalPendingCompanyPack.isEmpty()) {
            throw new PackageManagerException(ErrorType.COMPANY_PACKAGE_STATUS_PENDING);
        }

        Package selectedPackage = IPackageMapper.INSTANCE.toPackage(model);

        Double price = setPrice(model.getPackageType());

        selectedPackage.setPrice(price);
        save(selectedPackage);
        return new SuccessMessageResponseDto(model.getCompanyName()+ " sirketine paket olusturuldu onay bekliyor.");
    }

    private Double setPrice(EPackageType packageType) {

        switch (packageType) {
            case SILVER -> {
                return 10000D;
            }
            case GOLD -> {
                return 15000D;
            }
            case PLATINUM -> {
                return 18000D;
            }
            default -> {
                throw new PackageManagerException(ErrorType.BAD_REQUEST);
            }
        }
    }

    public SuccessMessageResponseDto activateStatus(ActivateCompanyPackageModel model) {

        if (!model.getUserType().equals(EUserType.ADMIN)) {
            throw new PackageManagerException(ErrorType.INSUFFICIENT_PERMISSION);
        }

        Optional<Package> optionalPackage = repository.findPendingCompanyPack(model.getCompanyName());
        if (optionalPackage.isEmpty()) {
            throw new PackageManagerException(ErrorType.PACKAGE_NOT_FOUND);
        }
        optionalPackage.get().setStatus(EStatus.ACTIVE);
        optionalPackage.get().setActivateDate(LocalDate.now());
        int months =setEndDate(optionalPackage.get().getPackageType());
        LocalDate endDate = LocalDate.now().plusMonths(months);
        optionalPackage.get().setEndDate(endDate);
        update(optionalPackage.get());
        return new SuccessMessageResponseDto("paket aktivasyon islemi basarili");
    }

    private int setEndDate(EPackageType packageType) {

        switch (packageType) {
            case SILVER -> {
                return 3;
            }
            case GOLD -> {
                return 8;
            }
            case PLATINUM -> {
                return 12;
            }
            default -> {
                throw new PackageManagerException(ErrorType.BAD_REQUEST);
            }
        }
    }

    public SuccessMessageResponseDto denyStatus(DenyCompanyPackageModel model) {
        if (!model.getUserType().equals(EUserType.ADMIN)) {
            throw new PackageManagerException(ErrorType.INSUFFICIENT_PERMISSION);
        }
        Optional<Package> optionalPackage = repository.findPendingCompanyPack(model.getCompanyName());
        if (optionalPackage.isEmpty()) {
            throw new PackageManagerException(ErrorType.PACKAGE_NOT_FOUND);
        }
        optionalPackage.get().setStatus(EStatus.REJECTED);
        update(optionalPackage.get());
        return new SuccessMessageResponseDto("paket red islemi basarili");
    }
}

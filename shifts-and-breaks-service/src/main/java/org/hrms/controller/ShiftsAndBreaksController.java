package org.hrms.controller;

import lombok.RequiredArgsConstructor;
import org.hrms.constant.EndPoints;
import org.hrms.dto.request.CreateShiftsAndBreaksRequestDto;
import org.hrms.repository.entity.ShiftsAndBreaks;
import org.hrms.service.ShiftsAndBreaksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.hrms.constant.EndPoints.SAVE;
import static org.hrms.constant.EndPoints.SHIFTSANDBREAKS;

@RestController
@RequestMapping(SHIFTSANDBREAKS)
@RequiredArgsConstructor
public class ShiftsAndBreaksController {

    private final ShiftsAndBreaksService service;

    @PostMapping(SAVE)
    public ResponseEntity<Boolean> createShiftsAndBreaks(CreateShiftsAndBreaksRequestDto dto) {
        return ResponseEntity.ok(service.createShiftsAndBreaks(dto));
    }

    @GetMapping("/apideneme")
    public ResponseEntity<String> denemeApi() {
        return ResponseEntity.ok("Selam");
    }
}

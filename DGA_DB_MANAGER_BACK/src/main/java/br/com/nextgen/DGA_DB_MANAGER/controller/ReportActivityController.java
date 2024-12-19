package br.com.nextgen.DGA_DB_MANAGER.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nextgen.DGA_DB_MANAGER.dto.report_activity.ReportActivityResponseDTO;
import br.com.nextgen.DGA_DB_MANAGER.repositories.activity_detail.ActivityDetailRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/report_activity")
@RequiredArgsConstructor
public class ReportActivityController {

    private final ActivityDetailRepository  repository;

    @GetMapping("/{id_activity}")
    public ResponseEntity<List<ReportActivityResponseDTO>> get(@PathVariable BigInteger id_activity) {
        List<ReportActivityResponseDTO> results = repository.findReportActivityByActivityId(id_activity);
        return ResponseEntity.ok(results);
    }

}

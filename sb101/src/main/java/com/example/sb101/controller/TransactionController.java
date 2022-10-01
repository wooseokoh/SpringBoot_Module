package com.example.sb101.controller;

import com.example.sb101.service.TransactionService;
import com.example.sb101.web.dto.TransactionDto;
import com.example.sb101.web.dto.TransactionViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transaction")
    public ResponseEntity<TransactionViewDto> getTransactionDetails(@RequestBody TransactionDto transactionDto) {
        return ResponseEntity.ok(transactionService.getTransactionDetails(transactionDto));
    }

}
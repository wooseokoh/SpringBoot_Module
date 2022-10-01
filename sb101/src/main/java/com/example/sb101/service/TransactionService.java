package com.example.sb101.service;

import com.example.sb101.mapper.TransactionMapper;
import com.example.sb101.web.dto.TransactionDto;
import com.example.sb101.web.dto.TransactionViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionMapper transactionMapper;

    public TransactionViewDto getTransactionDetails(TransactionDto transactionDTO) {

        return transactionMapper.toViewDTO(transactionDTO);
    }
}
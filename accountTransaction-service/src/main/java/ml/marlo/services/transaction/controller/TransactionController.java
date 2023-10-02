package ml.marlo.services.transaction.controller;

import ml.marlo.services.transaction.request.AccountTransactionRequest;
import ml.marlo.services.transaction.response.APIResponse;
import ml.marlo.services.transaction.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TransactionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);
    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public Mono<APIResponse> transaction(
            @RequestBody AccountTransactionRequest accountTransaction,
            @RequestHeader(value = "transactionType", required = false) String transactionType) {
        LOGGER.info("findById: Id {} transactionType {} ", accountTransaction, transactionType);
        return transactionService.transaction(accountTransaction, transactionType);
    }

}

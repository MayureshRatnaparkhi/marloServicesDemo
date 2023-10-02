package ml.marlo.services.account.controller;

import ml.marlo.services.account.request.AccountRequest;
import ml.marlo.services.account.response.APIResponse;
import ml.marlo.services.account.service.AccountService;
import ml.marlo.services.account.response.AccountResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/account")
public class AccountController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);
    @Autowired
	AccountService accountService;

    @GetMapping("/{id}")
    public Mono<AccountResponse> getAccount(@PathVariable("id") String id) {
        LOGGER.info("search account by account Id : {}", id);
        return accountService.getAccount(id);
    }

    @PutMapping("/transaction/{id}/{creditAmount}/{transactionType}")
    public Mono<AccountResponse> debitAccount(@PathVariable("id") String id,
											  @PathVariable("creditAmount") String transactionAmount,
                                              @PathVariable("transactionType") String transactionType) {
        LOGGER.info(" account Id : {} amount {} transactionType {}  transaction",
				id, transactionAmount, transactionType);
        return accountService.accountTransaction(id, transactionAmount, transactionType);
    }

    @PostMapping
    public Mono<APIResponse> createAccount(@Valid @RequestBody AccountRequest accountRequest) {
        LOGGER.info("create new account: {}", accountRequest);
        return accountService.createAccount(accountRequest);
    }

}

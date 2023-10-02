package ml.marlo.services.transaction.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ml.marlo.services.transaction.event.AccountTransactionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AccountTransactionListenerController {
    List<AccountTransactionEvent> accountTransactionEvents=new ArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountTransactionListenerController.class);

    @KafkaListener(topics = "new-transaction", groupId = "account-transaction")
    public void consumeTransaction(String accountTransaction)  {
        try {
            LOGGER.info(" account Transaction {} ", accountTransaction);
            AccountTransactionEvent accountTransactionEvent = new ObjectMapper().readValue(accountTransaction, AccountTransactionEvent.class);
            accountTransactionEvents.add(accountTransactionEvent);
            LOGGER.info(" account Transaction {} ", accountTransactionEvent);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    @GetMapping("/status")
    public List<AccountTransactionEvent> getAccountTransactionEventStatus()
    {
        return accountTransactionEvents;
    }

}

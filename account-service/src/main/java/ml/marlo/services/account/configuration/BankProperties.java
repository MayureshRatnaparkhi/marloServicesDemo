package ml.marlo.services.account.configuration;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("system")
@Configuration
@Data
public class BankProperties {
    private List<Bank> banks;
}

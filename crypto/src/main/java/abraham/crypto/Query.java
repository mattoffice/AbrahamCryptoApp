package abraham.crypto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Query {

    @NotNull
    private String currency;

    @NotNull
    private String cryptoType;

    private Double amount;

}

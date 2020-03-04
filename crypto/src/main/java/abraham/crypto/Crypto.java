package abraham.crypto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Crypto {

    private final String  cryptoId;

    private final String cryptoName;
}

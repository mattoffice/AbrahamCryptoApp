package abraham.crypto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Currency {

    private final String name;
    private final Abbr currId;

    public static enum Abbr {
        US_DOLLARS, GB_POUNDS
    }
}

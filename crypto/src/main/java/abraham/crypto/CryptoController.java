package abraham.crypto;

import abraham.crypto.Query.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping("/crypto")
@Controller
public class CryptoController {



    @GetMapping("/value")
    public String cryptoVal(Model model, @ModelAttribute("query") Query q) throws IOException {

        model.addAttribute("cryptoVal", q);
        //perform Coinbase API query...

        String bitcoinPrice = getBitcoinPrice(q);
        log.info(bitcoinPrice);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode btcPrice = objectMapper.readTree(bitcoinPrice);
        String price = btcPrice.findValue("amount").toString().replace("\"", "") + " " + q.getCurrency();
        log.info(price);

        model.addAttribute("btcPrice", price);

        return "cryptoValue";

    }

    public String getBitcoinPrice(Query q) {
        String ccyPair = q.getCryptoType() + "-" + q.getCurrency();
        final String uri = "https://api.coinbase.com/v2/prices/{ccy_pair}/buy";

        Map<String, String> params = new HashMap<String, String>();
        params.put("ccy_pair", ccyPair);

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class, params);

        log.info(result);
        return result;
    }
}

package abraham.crypto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import abraham.crypto.Currency.Abbr;
import abraham.crypto.Query;
import abraham.crypto.Currency;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String home(Model model) {

        List<Currency> currencies = Arrays.asList(
            new Currency("USD", Abbr.US_DOLLARS),
            new Currency("GBP", Abbr.GB_POUNDS)
        );

        List<Crypto> cryptos = Arrays.asList(new Crypto("ETH", "ETHEREUM"),
                new Crypto("BTC", "BITCOIN"),
                new Crypto("LINK", "CHAINLINK"),
                new Crypto("XTZ", "TEZOS"),
                new Crypto("KNC", "KYBER NETWORK"),
                new Crypto("LTC", "LITECOIN"),
                new Crypto("XRP", "XRP"),
                new Crypto("ALGO", "ALGORAND"),
                new Crypto("OXT", "ORCHID PROTOCOL"),
                new Crypto("REP", "AUGUR"),
                new Crypto("BAT", "BASIC ATTENTION TOKEN"),
                new Crypto("XLM", "STELLAR"));

        List<String> cryptoTypes = cryptos.stream().map(Crypto::getCryptoId).collect(Collectors.toList());

        List<String> ccyNames = currencies.stream().map(Currency::getName).collect(Collectors.toList());
        log.info(String.valueOf(ccyNames));

        Double amt = 8000.00;

        model.addAttribute("currencyNames", ccyNames);
        model.addAttribute("crypto", cryptoTypes);

        model.addAttribute("amount", amt);

        model.addAttribute("query", new Query());
        return "home";
    }

    @PostMapping
    public String getCrypto(@ModelAttribute("query") Query query, RedirectAttributes redirectAttributes) {
       log.info("Processing crypto query: " + query);
       redirectAttributes.addFlashAttribute("query", query);
       return "redirect:/crypto/value";
    }

}

var ws = null;
var btc_url = "wss://ws-feed-public.sandbox.pro.coinbase.com";

var checkedCurrency = document.querySelector('.currencyCheckbox:checked').value;
console.log(checkedCurrency);

var price = "[[${btcPrice}]]";
console.log(price + "from app.js file");

function setConnected(connected) {
    document.getElementById('connect').disabled = connected;
    document.getElementById('disconnect').disabled = !connected;
    document.getElementById('echo').disabled = !connected;
    }

function log(price, product_id) {
        var console = document.getElementById('logging');
        var p = document.createElement('p');
        p.appendChild(document.createTextNode("Latest Price for " + product_id + " relative to mother Bitcoin: " + price));
        console.appendChild(p);
        while (console.childNodes.length > 12) {
            console.removeChild(console.firstChild);
        }
        console.scrollTop = console.scrollHeight;
    }

function parseResponse(r) {
    var json = JSON.parse(r);
    console.log(json);
    console.log(json.price);
    if (json.price != undefined) {
    log(json.price, json.product_id);
    };
}

function connect() {

console.log("Inside connect function");

    ws = new WebSocket(btc_url);

    ws.onopen =  function() {
        setConnected(true);
    }

    ws.onmessage = function(event) {
        parseResponse(event.data);
    }

    ws.onclose = function(event) {
        setConnected(false);
        log('Info: Closing connection.');
    }
}

function disconnect() {
    if (ws != null) {
        ws.close();
        ws = null;
    }

}

function echo(p) {
    console.log(p);
    if (ws != null) {
        var message = JSON.stringify({
                                          "type": "subscribe",
                                          "product_ids": [
                                              "ETH-USD",
                                              "ETH-EUR"
                                          ],
                                          "channels": [
                                              "level2",
                                              "heartbeat",
                                              {
                                                  "name": "ticker",
                                                  "product_ids": [
                                                      p,
                                                      "ETH-USD"
                                                  ]
                                              }
                                          ]
                                     });
        ws.send(message);
    } else {
        alert('Connection not established, please connect.');
    }
}
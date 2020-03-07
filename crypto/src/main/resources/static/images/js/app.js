var ws = null;
var btc_url = "wss://ws-feed-public.sandbox.pro.coinbase.com";

function setConnected(connected) {
    document.getElementById('connect').disabled = connected;
    document.getElementById('disconnect').disabled = !connected;
    document.getElementById('echo').disabled = !connected;
    }

function log(message, message2) {
        var console = document.getElementById('logging');
        var p = document.createElement('p');
        p.appendChild(document.createTextNode("Latest Price for " + message2 + " :" + message));
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
    log(JSON.stringify(json), json.product_id);
    };
}

function connect() {



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

var log2 = function (message) {
    var console = document.getElementById('logging');
    var p = document.createElement('p');
    p.appendChild(document.createTextNode(message));
    console.appendChild(p);
    while (console.childNodes.length > 12) {
        console.removeChild(console.firstChild);
    }
    console.scrollTop = console.scrollHeight;
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
                                                      "ETH-BTC",
                                                      "ETH-USD"
                                                  ]
                                              }
                                          ]
                                     });
        log2('Sent: ' + message);
        ws.send(message);
    } else {
        alert('Connection not established, please connect.');
    }
}
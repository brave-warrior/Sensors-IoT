#include <ESP8266WiFi.h>

const char* ssid = "FRITZ!Box Fon WLAN 7360";
const char* password = "62884846722859294257";

const char* host = "192.168.178.34";
const int httpPort = 8080;
 
char* clientId = "nodemcu";

const int delayInMillis = 10000;
const int timeout = 8000;

unsigned int nextTime = 0;    // Next time to contact the server

void setup() {
  Serial.begin(115200);
  delay(10);
  
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);
  
  WiFi.begin(ssid, password);
  
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println();
  Serial.println("WiFi connected");  
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}

void loop() {
  if (nextTime > millis()) {
        return;
  }

  Serial.println("connecting to " + String(host));
  
  // Use WiFiClient class to create TCP connections
  WiFiClient client;
  
  if (!client.connect(host, httpPort)) {
    Serial.println("connection failed");
    return;
  }

  String path = "/devices";
  Serial.println("Requesting URL: " + path);

  // TODO Read temperature and humidity from sensors
  float temperature = 55.3;
  float humidity = 33.4;

  // prepare request body
  String body = "{\"name\":\"" + String(clientId) + "\"," +
                "\"weatherData\": { \"temperature\":\"" + temperature + "\"," +
                "\"humidity\":\"" + humidity + "\" }}";
   
  // send request to the server
  client.print("POST " + path + " HTTP/1.1\r\n" +
               "Host: " + host + "\r\n" + 
               "Connection: close\r\n" + 
               "Content-Type: application/json\r\n" +
               "Content-Length: " + body.length() + "\r\n" +
               "\r\n" +
               body);

  // process connection timeout
  unsigned long startTime = millis();
  while (client.available() == 0) {
    if (millis() - startTime > timeout) {
      Serial.println("Connection Timeout");
      client.stop();
      return;
    }
  }
  
  // Read all the lines of the reply from server and print them to Serial
  while(client.available()){
    String line = client.readStringUntil('\r');
    Serial.print(line);
  }

  Serial.println();
  Serial.println("Close connection");

  // do delay
  nextTime = millis() + delayInMillis;
}

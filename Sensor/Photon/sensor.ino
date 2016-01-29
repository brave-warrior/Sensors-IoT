// This #include statement was automatically added by the Particle IDE.
#include "HTU21D/HTU21D.h"
#include "application.h"
#include "HttpClient/HttpClient.h"

unsigned int nextTime = 0;    // Next time to contact the server
HttpClient http;
HTU21D htu = HTU21D();

char* serverUrl = "192.168.178.34";
char* clientId = "photo1";
char* requestBody = "{ \"name\":\"%s\", \"weatherData\": { \"temperature\":\"%f\", \"humidity\":\"%f\" } }";

int delayInMillis = 10000;

// Headers currently need to be set at init, useful for API keys etc.
http_header_t headers[] = {
    { "Content-Type", "application/json" },
    //  { "Accept" , "application/json" },
    { "Accept" , "*/*"},
    { NULL, NULL } // NOTE: Always terminate headers will NULL
};

http_request_t request;
http_response_t response;

void setup() {
    Serial.begin(9600);
    htu.begin();
}

void loop() {
    if (nextTime > millis()) {
        return;
    }

	//Serial.println("Application>\tStart of Loop.");
		
    float temperature = htu.readTemperature();
    float humidity = htu.readHumidity();
	
	//Serial.println("Temperature: %f, Humidity: %f", temperature, humidity);

    //Serial.println("Sending data...");
	
	char payload[128];
    snprintf(payload, sizeof(payload), requestBody, clientId, temperature, humidity);

    request.hostname = serverUrl;
    request.port = 8080;
    request.path = "/devices";
    request.body = payload;
	
    http.post(request, response, headers);
	
    //Serial.println("Data sent");

	// do delay
    nextTime = millis() + delayInMillis;
}


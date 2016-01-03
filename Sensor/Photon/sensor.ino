// This #include statement was automatically added by the Particle IDE.
#include "HTU21D.h"
#include "application.h"
#include "HttpClient.h"

unsigned int nextTime = 0;    // Next time to contact the server
HttpClient http;
HTU21D htu = HTU21D();

char* clientId = "photo1";

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

    float c = htu.readTemperature();
    float h = htu.readHumidity();

    char payload[128];
    snprintf(payload, sizeof(payload), "{ \"clientId\":\"%s\", \"humidity\":\"%f\",\"temperature\":\"%f\" }", clientId,h,c);

    Serial.println();
    Serial.println("Application>\tStart of Loop.");

    request.hostname = "192.168.43.219";
    request.port = 8080;
    request.path = "/update";

    request.body = payload;
    http.post(request, response, headers);

    nextTime = millis() + 10000;
}

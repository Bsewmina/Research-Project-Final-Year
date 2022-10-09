#include <WiFi.h>
#include <FirebaseESP32.h>
#include <TinyGPS++.h>

#define FIREBASE_HOST "gps-b123c-default-rtdb.firebaseio.com"
#define FIREBASE_AUTH "gEjXkGOWaMEzkZKpKIYqwFH7dQMetih25gsmligT"
#define WIFI_SSID "LAZI 5G"
#define WIFI_PASSWORD "fmrp1530"
FirebaseData firebaseData;

#define RXD2 5
#define TXD2 21
HardwareSerial neogps(1);
String ESP32_API_KEY = "Ad5F10jkBM0";
TinyGPSPlus gps;
void setup(){
  
  Initialization();
  WiFiConnection();
  neogps.begin(9600, SERIAL_8N1, RXD2, TXD2);
  
 }

float counter2 = 0.5;

void Initialization(){
  
  Serial.begin(115200); 
 }

void WiFiConnection(){
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to Wi-Fi");
  while (WiFi.status() != WL_CONNECTED)
  {
    Serial.print(".");
    delay(300);
  }
  Serial.println();
  Serial.print("Connected with IP: ");
  Serial.println(WiFi.localIP());
  Serial.println();

  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
  Firebase.reconnectWiFi(true);
}

void loop(){
  boolean newData = false;
  for (unsigned long start = millis(); millis() - start < 2000;){
    while(neogps.available()){
      if(gps.encode(neogps.read())){
        if(gps.location.isValid() == 1){
          newData = true;
          break;
        }
      }
    }
  }
  //NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN
  
  //MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
  //If newData is true
  if(true){
    newData = false;
  
    //NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN
    String latitude, longitude;
    //float altitude;
    //unsigned long date, time, speed, satellites;
    
    latitude = String(gps.location.lat(), 6); // Latitude in degrees (double)
    longitude = String(gps.location.lng(), 6); // Longitude in degrees (double)
    
    //altitude = gps.altitude.meters(); // Altitude in meters (double)
    //date = gps.date.value(); // Raw date in DDMMYY format (u32)
    //time = gps.time.value(); // Raw time in HHMMSSCC format (u32)
    //speed = gps.speed.kmph();
    
    //Serial.print("Latitude= "); 
    //Serial.print(latitude);
    //Serial.print(" Longitude= "); 
    //Serial.println(longitude);

    String lati;
    String longi;
    String gps_data;
    gps_data = "api_key="+ESP32_API_KEY;
    lati +=latitude;
    longi +=longitude;

    Serial.print("gps_data: ");
    Serial.println(gps_data);
    
    String z = gps_data;
    String x = lati;
    String y = longi;
    
  if(Firebase.setString(firebaseData, "latitude", x)){
    }
    if(Firebase.getString(firebaseData, "latitude")){
    if(firebaseData.dataType() == "string"){
      Serial.print("data = ");
      Serial.println(firebaseData.stringData());
    }
  }

  if(Firebase.setString(firebaseData, "longitude", y)){
    }
    if(Firebase.getString(firebaseData, "longitude")){
    if(firebaseData.dataType() == "string"){
      Serial.print("data = ");
      Serial.println(firebaseData.stringData());
    }
  }




  
}
}

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { WeatherData } from 'src/app/DTOs/WeatherDTO';

@Injectable({
  providedIn: 'root'
})
export class WeatherService {
  private url = 'http://localhost:8080';
  constructor(private http: HttpClient) { }

      getWeatherInfo(cityName : string){
        console.log(   (this.url+ "/info/" + cityName ));
        
        return this.http.get<WeatherData>(this.url+ "/info/" + cityName )
      }

}



  // @GetMapping("/info")
  // public ResponseEntity<WeatherDTO> getWeatherData(@PathParam("cityName") String cityName) {
  //     return ResponseEntity.ok(weatherService.getCurrentWeather(cityName));
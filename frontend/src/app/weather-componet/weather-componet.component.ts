// weather-componet.component.ts
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { WeatherService } from './weather.service';
import { WeatherNotes } from 'src/app/DTOs/WeatherNotesDTO';
import { WeatherData } from 'src/app/DTOs/WeatherDTO';
import { AuthService } from 'src/app/auth/auth.service';
import { Subscription } from 'rxjs';


@Component({
  selector: 'weather',
  templateUrl: './weather-componet.component.html',
  styleUrls: ['./weather-componet.component.css']
})
export class WeatherComponet implements OnInit { 
  currentPage = 0;
  itemsPerPage = 1;
  slicedWeatherNotes: WeatherNotes[] = [];
  userSub: Subscription;
  cityName: string;
  role: string;
  weatherData: WeatherData = {
    currentWeatherCondition: "",
    icon: "",
    temperature: "",
    weatherNotes: []
  };

  constructor(private authService: AuthService, private service: WeatherService) { }

  ngOnInit(): void {
    if (!this.cityName)
      this.cityName = "cairo";
    
    this.getWeatherForCity();
    this.userSub = this.authService.user.subscribe(admin => {
      this.role = admin.userRole;
    });
  }

  getWeatherForCity() {
    this.service.getWeatherInfo(this.cityName).subscribe(resp => {
      console.log(resp);

      this.weatherData.currentWeatherCondition = resp.currentWeatherCondition;
      this.weatherData.icon = resp.icon;
      this.weatherData.temperature = resp.temperature;
      this.weatherData.weatherNotes = resp.weatherNotes;

      this.updateSlicedWeatherNotes();
    });
  }

  updateSlicedWeatherNotes() {
    const startIndex = this.currentPage * this.itemsPerPage;
    this.slicedWeatherNotes = this.weatherData.weatherNotes.slice(startIndex, startIndex + this.itemsPerPage);
  }

  totalPages() {
    return Math.ceil(this.weatherData.weatherNotes.length / this.itemsPerPage);
  }

  nextPage() {
    if (this.currentPage < this.totalPages() - 1) {
      this.currentPage++;
      this.updateSlicedWeatherNotes();
    }
  }

  previousPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.updateSlicedWeatherNotes();
    }
  }


}
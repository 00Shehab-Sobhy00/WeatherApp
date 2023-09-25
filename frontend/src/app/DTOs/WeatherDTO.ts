import { WeatherNotes } from "./WeatherNotesDTO";

export interface WeatherData {
    currentWeatherCondition: string;
    icon: string;
    temperature: string;
    weatherNotes: WeatherNotes[];
  }
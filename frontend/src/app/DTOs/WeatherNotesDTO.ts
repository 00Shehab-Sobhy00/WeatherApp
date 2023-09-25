import { User } from "./UserDTO";

export interface WeatherNotes {
    id: number;
    cityName: string;
    note: string;
    date: string;
    adminName: string;
    user: User;  
  }
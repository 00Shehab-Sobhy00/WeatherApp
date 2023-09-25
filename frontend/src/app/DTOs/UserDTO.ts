import { ERole } from "./ERole";
import { WeatherNotes } from "./WeatherNotesDTO";

export interface User {
    id: number;
    name: string;
    email: string;
    password: string;
    phone: number;
    roles: ERole;
    notes: WeatherNotes[];  

}


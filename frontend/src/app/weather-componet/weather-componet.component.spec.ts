import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WeatherComponet } from './weather-componet.component';

describe('WeatherComponetComponent', () => {
  let component: WeatherComponet;
  let fixture: ComponentFixture<WeatherComponet>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WeatherComponet ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WeatherComponet);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

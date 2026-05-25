package net.engineeringdigest.journalApp.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherResponse {
        private Current current;

    @Getter
    @Setter
    public class Current {
        private int temperature;
        private int pressure;
        private int humidity;
        private int feelslike;
    }
}

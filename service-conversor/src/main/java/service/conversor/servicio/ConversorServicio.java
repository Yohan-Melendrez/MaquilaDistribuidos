/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.conversor.servicio;

import java.util.Map;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import service.conversor.dto.ConversionDTO;

/**
 *
 * @author USER
 */
@Service
public class ConversorServicio {
    private final RestTemplate restTemplate;

    public ConversorServicio(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public ConversionDTO getConversion(String from, String to) {
        String apiUrl = "https://api.exchangerate.host/live?access_key=5be23dd42b90bb252dc3f8128329c372&format=1";
        ResponseEntity<Map> response = restTemplate.getForEntity(apiUrl, Map.class);

        Map<String, Object> quotes = (Map<String, Object>) response.getBody().get("quotes");
        String pairKey = from + to;
        Double rate = (Double) quotes.get(pairKey);

        return new ConversionDTO(from, to, rate);
    }
}

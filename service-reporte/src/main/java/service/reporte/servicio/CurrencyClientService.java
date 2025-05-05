/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.reporte.servicio;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import service.reporte.dtos.ConversionDTO;

/**
 *
 * @author USER
 */
@Service
public class CurrencyClientService {

    private final RestTemplate restTemplate;

    public CurrencyClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ConversionDTO getConversion(String from, String to) {
        String url = "http://localhost:9092/conversor/obtener?from=" + from + "&to=" + to;
        return restTemplate.getForObject(url, ConversionDTO.class);
    }
}

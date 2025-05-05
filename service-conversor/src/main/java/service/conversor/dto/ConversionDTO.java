/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.conversor.dto;

/**
 *
 * @author USER
 */
public class ConversionDTO {
    private String from;
    private String to;
    private Double rate;

    public ConversionDTO() {
    }

    public ConversionDTO(String from, String to, Double rate) {
        this.from = from;
        this.to = to;
        this.rate = rate;
    }

    
    
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
    
    
}

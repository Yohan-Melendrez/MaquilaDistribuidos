/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.reporte.dtos;

/**
 *
 * @author USER
 */
public class ConversionDTO {
    private String from;
    private String to;
    private double rate;

    public ConversionDTO() {
    }

    public ConversionDTO(String from, String to, double rate) {
        this.from = from;
        this.to = to;
        this.rate = rate;
    }
    
    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public double getRate() { return rate; }
    public void setRate(double rate) { this.rate = rate; }
}

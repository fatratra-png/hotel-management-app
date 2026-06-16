package origa.hotely;

import lombok.Getter;
@Getter

public class Hotely {
    private final String anarana;
    private final String toerana;
    private final String adiresy;
    private final String laharanaFinday;
    private int isanKintana;
    private final int isanEfitra;

    public Hotely(String anarana, String toerana, String adiresy, String laharanaFinday, int isanKintana, int isanEfitra) {
        if(isanKintana<0 || isanKintana>5) throw new IllegalArgumentException("Tsy manaraka ny lalana mamehy ny fetran'ny isa azo apetaka");
        this.anarana = anarana;
        this.toerana = toerana;
        this.adiresy = adiresy;
        this.laharanaFinday = laharanaFinday;
        this.isanKintana = isanKintana;
        this.isanEfitra = isanEfitra;
    }

}

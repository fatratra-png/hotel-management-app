package origa.hotely;

import java.time.LocalDateTime;

public class Mpandray extends Mpiasa{
    public Mpandray(String laharana, String anarana, String fanampiny, LocalDateTime datyNahaterahana, Asa asa, String toeranaNahaterahana, String laharanTarobia, Hotely hotely) {
        super(laharana, anarana, fanampiny, datyNahaterahana, asa, toeranaNahaterahana, laharanTarobia, hotely);
    }
    public String miasa(){
        return "Mandray ireo mpanofa trano am-panajana i "+this.getAnarana();
    }
}

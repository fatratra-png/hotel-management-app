package origa.hotely;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter

public class Mpahandro extends Mpiasa{
    public Mpahandro(String laharana, String anarana, String fanampiny, LocalDateTime datyNahaterahana, Asa asa, String toeranaNahaterahana, String laharanTarobia, Hotely hotely) {
        super(laharana, anarana, fanampiny, datyNahaterahana, asa, toeranaNahaterahana, laharanTarobia, hotely);
    }

    @Override
    public String miasa(){
        return "Mahandro sakafo ao an-dakozia i "+this.getAnarana();
    }


}

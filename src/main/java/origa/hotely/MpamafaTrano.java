package origa.hotely;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter

public class MpamafaTrano extends Mpiasa {

    public MpamafaTrano(String laharana, String anarana, String fanampiny, LocalDateTime datyNahaterahana, Asa asa, String toeranaNahaterahana, String laharanTarobia, Hotely hotely) {
        super(laharana, anarana, fanampiny, datyNahaterahana, asa, toeranaNahaterahana, laharanTarobia, hotely);
    }

    @Override
    public String miasa(){
            return "Mamafa trano i "+this.getAnarana();
    }
}

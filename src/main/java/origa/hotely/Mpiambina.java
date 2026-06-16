package origa.hotely;

import java.time.LocalDateTime;

public class Mpiambina extends Mpiasa{
    public Mpiambina(String laharana, String anarana, String fanampiny, LocalDateTime datyNahaterahana, Asa asa, String toeranaNahaterahana, String laharanTarobia, Hotely hotely) {
        super(laharana, anarana, fanampiny, datyNahaterahana, asa, toeranaNahaterahana, laharanTarobia, hotely);
    }

    @Override
    public String miasa() {
        return "Mpiandry tanana ao amin' "+this.getHotely()+" I "+this.getAnarana();
    }
}

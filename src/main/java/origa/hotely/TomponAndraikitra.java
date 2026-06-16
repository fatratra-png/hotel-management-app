package origa.hotely;

import java.time.LocalDateTime;

public class TomponAndraikitra extends Mpiasa{
    public TomponAndraikitra(String laharana, String anarana, String fanampiny, LocalDateTime datyNahaterahana, Asa asa, String toeranaNahaterahana, String laharanTarobia, Hotely hotely) {
        super(laharana, anarana, fanampiny, datyNahaterahana, asa, toeranaNahaterahana, laharanTarobia, hotely);
    }

    @Override
    public String miasa(){
        return "I "+this.getAnarana()+" no tompon'andraikitran ao amin' "+this.getHotely();
    }
}

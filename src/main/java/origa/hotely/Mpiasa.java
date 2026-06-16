package origa.hotely;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter

public abstract class Mpiasa {
    private String laharana;
    private String anarana;
    private String fanampiny;
    private LocalDateTime datyNahaterahana;
    private Asa asa;
    private String toeranaNahaterahana;
    private String laharanTarobia;
    private Hotely hotely;

    public abstract String miasa();
}

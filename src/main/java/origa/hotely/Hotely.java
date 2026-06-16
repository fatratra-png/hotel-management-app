package origa.hotely;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Set;
import java.util.HashSet;

@EqualsAndHashCode
@Getter

public class Hotely {
    private final String anarana;
    private final String toerana;
    private final String adiresy;
    private final String laharanTarobia;
    private int isanKintana;
    private final int isanEfitra;
    private final Set<Mpiasa> mpiasa_s;

    public Hotely(String anarana, String toerana, String adiresy,String laharanTarobia, int isanKintana, int isanEfitra,Set<Mpiasa> mpiasa_s) {
        if(isanKintana<0 || isanKintana>5) throw new IllegalArgumentException("Tsy manaraka ny lalana mamehy ny fetran'ny isa azo apetaka");
        this.anarana = anarana;
        this.toerana = toerana;
        this.adiresy = adiresy;
        this.isanKintana = isanKintana;
        this.isanEfitra = isanEfitra;
        this.laharanTarobia = laharanTarobia;
        this.mpiasa_s=new HashSet<>();
    }

    public void mampiditraMpiasa(Mpiasa mpiasa){
        mpiasa_s.add(mpiasa);
    }

    public void manalaMpiasa(String laharana){
        mpiasa_s.remove(laharana);
    }

    public int manisaMpiasa(Set<Mpiasa> mpiasa_s){
        int isa=0;
        for(Mpiasa mpiasa : mpiasa_s){
            isa++;
        }
        return isa;
    }

    public int manisaMpiasaArakarakinAsany(Asa asa){
        int isa=0;
        for(Mpiasa mpiasa:mpiasa_s){
            if(mpiasa.getAsa()==asa){
                isa++;
            }
        }
        return isa;
    }
}

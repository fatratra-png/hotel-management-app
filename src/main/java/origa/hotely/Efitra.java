package origa.hotely;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class Efitra {
    private boolean misyWifi = true;
    private boolean misyManofa = false;
    private boolean mahitaRanomasina = false;
    private boolean misyKlima = false;

    public Efitra(boolean misyKlima) {
        this.misyKlima = misyKlima;
    }

    public Efitra(boolean misyKlima,boolean mahitaRanomasina){
        this.misyKlima=misyWifi;
        this.mahitaRanomasina=mahitaRanomasina;
    }
}

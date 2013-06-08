package allas.peli;

import allas.domain.Pallo;
import allas.domain.Pelaaja;
import allas.gui.Paivitettava;
import allas.tools.Vektori;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.swing.Timer;

public class Peli extends Timer {

    private ArrayList<Pallo> pallot;
    private Random arpoja;
    private int leveys; // pöydän koko
    private int pituus;
    private int seina; // seinän paksuus
    private int r; // pallon säde
    private int reijanKoko;
    private int valkoisenEnsimmainenOsuma;
    private int pelitilanne; // pyörii(0), aseta valkoinen pallo(1), aseta suuntavektori(2), aseta nopeus(3) 
    private double kitka;
    private double[] lyontivoima;
    private boolean aloitustilanne;
    private Paivitettava paivitettava;
    private Pelaaja pelaaja1;
    private Pelaaja pelaaja2;
    public int hiirenX;
    public int hiirenY;
    private Vektori lyonninSuunta;
    private int x, y;

    public Peli(int pituus, int leveys, int seina, int r, int reijanKoko) {
        super(100, null);


        this.pallot = new ArrayList<>();
        this.arpoja = new Random();
        this.leveys = leveys;
        this.pituus = pituus;
        this.seina = seina;
        this.r = r;
        this.reijanKoko = reijanKoko;
        this.kitka = 0.03;
        double[] d = {0, 0.2, 20}; // ensimmäinen on lyönnin voima, toinen lyönnin kasvu iteraation aikana ja kolmas maksimivoima
        this.lyontivoima = d;
        this.aloitustilanne = false;
        this.pelitilanne = 0;
        this.pelaaja1 = new Pelaaja();
        this.pelaaja2 = new Pelaaja();

        generoiPallot();
        asetaPallot(this.pituus * 6 / 8, this.leveys / 2);
    }

    public boolean generoiPallot() {
        if (!this.pallot.isEmpty()) {
            return false;
        }
        this.pallot.add(new Pallo(this.pituus / 8, this.leveys / 2, 0, this.r)); // lyöntipallo
        int nro;

        int i = 1;
        while (i < 16) {
            if (i == 5) {
                this.pallot.add(new Pallo(100, 100, 8, this.r));
                i++;
                continue;
            }
            nro = arpoja.nextInt(15) + 1;
//            System.out.println(nro);

            if (haePallo(nro) == null && nro != 8) {
                this.pallot.add(new Pallo(100, 100, nro, this.r));
                i++;
            }
        }
        int pallo11 = this.pallot.get(11).getN();
        int pallo15 = this.pallot.get(15).getN();
        if ((pallo11 < 8 && pallo15 < 8) || (pallo11 > 8 && pallo15 > 8)) {
            vaihdaPallo(pallo15);
        }
        return true;
    }

    public void vaihdaPallo(int pallo15) {
        while (true) {
            int nro = arpoja.nextInt(14) + 1;
            int palloX = this.pallot.get(nro).getN();
            if (((palloX < 8 && pallo15 > 8) || (palloX > 8 && pallo15 < 8)) && (palloX != 11)) { // Palloja  8  tai 11 ei saa vaihtaa 
                Collections.swap(this.pallot, nro, 15);
                break;
            }
        }
    }

    public void asetaPallot(double rivinEkaX, double rivinEkaY) {
        double ekaX = rivinEkaX;
        double ekaY = rivinEkaY;
        int nro = 1;

        for (int i = 1; i < 6; i++) {
            for (int j = 0; j < i; j++) {
                this.pallot.get(nro).setX(ekaX);
                this.pallot.get(nro).setY(ekaY + j * 2 * this.r);
                nro++;
            }
            ekaX += Math.sqrt(3 * this.r * this.r);
            ekaY -= this.r;
        }
    }

    public void aja() {
//        for (Pallo pallo : pallot) {
//            pallo.setVy(arpoja.nextInt(20));
//            pallo.setVx(arpoja.nextInt(20));
//        }

        while (true) {
            if (this.pallot.get(0).getPussissa()) {
                this.pelitilanne = 1;
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                System.out.println("Nukkuminen ei onnistunut.");
            }
            if (this.pelitilanne == 0) {
                while (!this.pallot.get(5).getPussissa()) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        System.out.println("Nukkuminen ei onnistunut.");
                    }
                    aikaHyppy();
                    this.paivitettava.paivita();
                    if (pallotPaikoillaan()) {
                        this.pelitilanne = 2;
                        break;
                    }
                }
            }
            if (this.pelitilanne == 3) {
                this.lyontivoima[0] += this.lyontivoima[1];

                if (this.lyontivoima[0] <= 0 || this.lyontivoima[0] >= this.lyontivoima[2]) {
                    this.lyontivoima[1] *= -1;
                }
                System.out.println(this.lyontivoima[0]);
            }
            this.paivitettava.paivita();
        }
    }

    public void aikaHyppy() {
        for (Pallo pallo : this.pallot) {
            if (pallo.getPussissa()) {
                continue;
            }
            // jos this.putosi = false, ei tarvii kattoa joku pallo pussittunut
            pallo.liikuta();
            pallo.jarruta(this.kitka);
            int pussinNumero = putoaaPussiin(pallo); // palautetaan 0, jos ei tipu pussiin
            if (pussinNumero > 0) {
                System.out.println("pallo " + pallo.getN() + " putosi");
                pallo.setPussissa(true);
                if (pallo.getN() == 8) {
                    kasinPussitus(pussinNumero);
                } else {
                    poistaPelista(pallo);
                }
            } else {
                osuuko(pallo);
            }
        }

    }

    public boolean osuuko(Pallo pallo1) {
        if (osuuSeinaan(pallo1)) {
//            System.out.println("osuu");
            return true;
        }

        for (Pallo pallo2 : this.pallot) {
            if (pallo1 == pallo2) {
                continue;
            }
            if (osuuPalloon(pallo1, pallo2)) {
                return true;
            }
        }
        return false;
    }

    public boolean osuuSeinaan(Pallo pallo) {
        // lisää ehtoja pussien lähettyville tulossa
        if (pallo.getX() <= 0 + this.r) {
            pallo.setVx(-1 * pallo.getVx());
            pallo.setX(this.r);
            return true;

        } else if (pallo.getX() >= this.pituus - this.r) {
            pallo.setVx(-1 * pallo.getVx());
            pallo.setX(this.pituus - this.r);
            return true;

        } else if (pallo.getY() <= 0 + this.r) {
            pallo.setVy(-1 * pallo.getVy());
            pallo.setY(this.r);
            return true;


        } else if (pallo.getY() > this.leveys - this.r) {
            pallo.setVy(-1 * pallo.getVy());
            pallo.setY(this.leveys - this.r);
            return true;
        }
        return false;
    }

    public boolean osuuPalloon(Pallo pallo1, Pallo pallo2) {
        if (pallo1.etaisyys(pallo2.getX(), pallo2.getY()) <= 2 * this.r) {
            if (pallo1.liikkuuko() || pallo2.liikkuuko()) {
//                System.out.println(pallo1.getN() + " " + pallo2.getN());
                laskeTormaysPalloille(pallo1, pallo2);
                return true;
            }
        }
        return false;
    }

    public void laskeTormaysPalloille(Pallo pallo1, Pallo pallo2) {
        Vektori delta = (pallo1.getPaikkavektori().miinus(pallo2.getPaikkavektori()));// Pallojen välinen etaisyysvektori 
        Vektori siirtoMatka = delta.tulo(((2 * this.r) - delta.pituus()) / delta.pituus()); //Lyhin matka, joka palloja tulee siirtää(ettei enää törmää uudestaan)

        pallo1.setPaikkavektori(pallo1.getPaikkavektori().plus(siirtoMatka.tulo(1 / 2)));// Lasketaan uudet etäisyydet, huom! palloilla on sama massa
        pallo2.setPaikkavektori(pallo2.getPaikkavektori().miinus(siirtoMatka.tulo(1 / 2)));

        Vektori deltaNopeus = (pallo1.getNopeusvektori().miinus(pallo2.getNopeusvektori()));// Nopeuksien erotus
        double impulssi = deltaNopeus.pistetulo(siirtoMatka.normalisoi());

        if (impulssi > 0) {// Pallot ovat liikkeellä pois toisistaan, joten voidaan poistua
            return;
        }

        Vektori impulse = siirtoMatka.tulo(-impulssi);

        pallo1.setNopeusvektori(pallo1.getNopeusvektori().plus(impulse.tulo(1))); // Päivitetään nopeudet
        pallo2.setNopeusvektori(pallo2.getNopeusvektori().miinus(impulse.tulo(1)));
    }

    public int putoaaPussiin(Pallo pallo) {
        if (pallo.etaisyys(0, 0) <= this.reijanKoko) {
            return 1;
        }
        if (pallo.etaisyys(pituus / 2, 0) <= this.reijanKoko) {
            return 2;
        }
        if (pallo.etaisyys(0, leveys) <= this.reijanKoko) {
            return 3;
        }
        if (pallo.etaisyys(pituus, 0) <= this.reijanKoko) {
            return 4;
        }
        if (pallo.etaisyys(pituus / 2, leveys) <= this.reijanKoko) {
            return 5;
        }
        if (pallo.etaisyys(pituus, leveys) <= this.reijanKoko) {
            return 6;
        }
        return 0;
    }

    public void asetaVauhti(Pallo pallo, double vx, double vy) {
        pallo.setVx(vx);
        pallo.setVy(vy);
    }

    public void poistaPelista(Pallo pallo) {
        pallo.setX(pallo.getN() * 2 * this.r);
        pallo.setY(this.leveys + 2 * this.seina);
    }

    public Pallo haePallo(int n) {
        for (Pallo pallo : this.pallot) {
            if (pallo.getN() == n) {
                return pallo;
            }
        }
        return null;
    }

    public boolean pallotPaikoillaan() {
        for (Pallo pallo : this.pallot) {
            if (pallo.getPussissa()) {
                continue;
            }
            if (pallo.liikkuuko()) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Pallo> getPallot() {
        return this.pallot;
    }

    public double getKitka() {
        return this.kitka;
    }

    public void piirra(Graphics g) {
        g.setColor(Color.WHITE);
        if (this.pelitilanne == 3) {
        } else if (this.pelitilanne > 1) {
            g.drawLine((int) this.pallot.get(0).getX() + this.seina, (int) this.pallot.get(0).getY() + this.seina, this.hiirenX, this.hiirenY);
        }
        for (Pallo pallo : pallot) {
            pallo.piirra(g, this.seina);
        }
    }

    public void setPelitilanne(int pelitilanne) {
        this.pelitilanne = pelitilanne;
    }

    public int getPelitilanne() {
        return this.pelitilanne;
    }

    public void setPaivitettava(Paivitettava paivitettava) {
        this.paivitettava = paivitettava;
    }

    public Paivitettava getPaivitettava(Paivitettava paivitettava) {
        return this.paivitettava;
    }

    public int getPituus() {
        return this.pituus;
    }

    public int getLeveys() {
        return this.leveys;
    }

    public int getSeina() {
        return this.seina;
    }

    public int getReijanKoko() {
        return reijanKoko;
    }

    private void valkoisenPussitus() {
        vuoronVaihto();
        this.pelitilanne = 1;

    }

    private void kasinPussitus(int pussinNumero) {
// pysäytä

        Pelaaja pelaaja = getVuorossaOlevaPelaaja();

        if (!pallotPussissa(pelaaja) || (pallotPussissa(pelaaja) && pelaaja.getMaalattuPussi() != pussinNumero)) {
// pelaaja hävisi
        }
//	if (virhelyönti) - häviää

// pelaaja voitti


        // kysy pelataanko uudestaan
    }

    private void vuoronVaihto() {
        if (pelaaja1.getVuorossa()) {
            this.pelaaja1.setVuorossa(false);
            this.pelaaja2.setVuorossa(true);
        } else {
            this.pelaaja2.setVuorossa(false);
            this.pelaaja1.setVuorossa(true);
        }
    }

    private Pelaaja getVuorossaOlevaPelaaja() {
        if (pelaaja1.getVuorossa()) {
            return pelaaja1;
        }
        return pelaaja2;
    }

    private void maalaa() {
        //     do something
    }

    private boolean pallotPussissa(Pelaaja pelaaja) {
        if (pelaaja.hasIsotPallot()) {
            for (int i = 9; i < 16; i++) {
                if (!haePallo(i).getPussissa()) {
                    return false;
                }
            }
        } else {
            for (int i = 1; i < 8; i++) {
                if (!haePallo(i).getPussissa()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean onAloituksessa() {
        return this.aloitustilanne;
    }

    public boolean valkoinenPalloSallitussaPaikassa() {
        for (Pallo pallo : this.pallot) {
            if (pallo.getN() == 0) {
                continue;
            }
            if (this.pallot.get(0).etaisyys(pallo.getX(), pallo.getY()) < 2 * this.r) {
                System.out.println("Et voi asettaa lyöntipalloa jonkin muun pallon päälle");
                return false;
            }
        }
        return true;
    }

    public void setHiirenPaikka(int x, int y) {
        this.hiirenX = x;
        this.hiirenY = y;
    }

    public void valkoisenPallonAsetus() { // monsterimetodi, asettaa aina pallon kentälle, vaikka hiiren osoitin menisi reunojen päälle
        if (this.aloitustilanne && this.hiirenX > this.pituus / 4) {
            this.pallot.get(0).setX(this.pituus / 4);
        } else if (this.hiirenX < this.seina + this.r) {
            this.pallot.get(0).setX(this.r);
        } else if (this.hiirenX > this.pituus + this.seina - this.r) {
            this.pallot.get(0).setX(this.pituus - this.r);
        } else {
            this.pallot.get(0).setX(this.hiirenX - this.seina);
        }

        if (this.hiirenY < this.seina + this.r) {
            this.pallot.get(0).setY(this.r);
        } else if (this.hiirenY > this.leveys + this.seina - this.r) {
            this.pallot.get(0).setY(this.leveys - this.r);
        } else {
            this.pallot.get(0).setY(this.hiirenY - this.seina);
        }

    }

    public void asetaLyonninSuunta() {
        Vektori hiirenPaikkavektori = new Vektori(this.hiirenX - this.seina, this.hiirenY - this.seina);
        System.out.println("hiiren koordinaatit " + this.hiirenX + " " + this.hiirenY);
        System.out.println("valkosen koordinaatit " + this.pallot.get(0).getX() + " " + this.pallot.get(0).getY());
        this.lyonninSuunta = hiirenPaikkavektori.miinus(this.pallot.get(0).getPaikkavektori());
        this.lyonninSuunta = this.lyonninSuunta.normalisoi();
        this.pelitilanne = 3;
    }

    public void asetaNopeus() {

        Vektori lyonti = this.lyonninSuunta.tulo(this.lyontivoima[0]);

        System.out.println("lyönnin pituus " + lyonti.pituus());
        System.out.println(this.lyonninSuunta.getX() + " " + this.lyonninSuunta.getY());
        this.pallot.get(0).setVx(lyonti.getX());
        this.pallot.get(0).setVy(lyonti.getY());
        System.out.println("asetetaan nopeus: " + lyonti.getX() + " " + lyonti.getY());
//        this.pallot.get(0).setVx(12);
//        this.pallot.get(0).setVy(0);
        this.lyontivoima[0] = 0;
        if (lyontivoima[1] < 0){
            lyontivoima[1] *= -1;
        }
        this.pelitilanne = 0;
        
    }
}

/*

 pelitilanteet:
 aloitustilanne
 lyö palloa
 	aseta suuntavektori (jos alotus Vx < 0 kielletty)
 	aseta nopeus (aina > 0)
 pallot pyörii
 kasi pussissa
 faul - virhetilanne, printtaa syy
 

 valkoisen eka osuma

 komentojen antaminen näppiksellä aina:
 maalaa
 aloita alusta
 kysymyksiin vastaaminen

0 - pyörii (jatkuu)
1 - aseta valkoinen pallo
2 - aseta suuntavektori
3 - aseta nopeus ja lyö	




aloitustilanne 0/1


*/
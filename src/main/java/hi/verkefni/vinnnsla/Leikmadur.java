package hi.verkefni.vinnnsla;

import hi.verkefni.vinnsla.LeikmadurInterface;
import hi.verkefni.vinnsla.SpilV;
import hi.verkefni.vinnsla.Stokkur;

/******************************************************************************
 *  Nafn    : Ívan Már Þrastarson
 *  T-póstur: imt1@hi.is
 *
 *  Lýsing  : Vinnsluklasi fyrir leikmann sem inniheldur allar aðferðir
 *            og tilviksbreytur sem leikmaður þarf fyrir Blackjack.
 *
 *
 *****************************************************************************/
public class Leikmadur implements LeikmadurInterface {

    private int samtala;

    private String nafn;

    private int fjoldiSpila;

    /**
     * Getter fyrir samtölu leikmanns
     *
     *
     * @return samtala
     */
    public int getSamtals() {
        //System.out.println(samtala);
        return samtala;
    }

    /**
     * Getter fyrir nafn leikmanns
     *
     * @return nafn
     */
    public String getNafn() {
        return nafn;
    }

    /**
     * Setter fyrir nafn leikmanns.
     *
     * @param s nafnið sem leikmaður á að fá.
     */
    public void setNafn(String s) {
        this.nafn = s;

    }

    /**
     * Gefur leikmanni nýtt spil á hendi.
     *
     * @param spilV spilið sem leikmaður á að fá.
     */
    public void gefaSpil(SpilV spilV) {
        samtala += spilV.getVirdi();
        fjoldiSpila++;
        //System.out.println(spilV);

    }

    /**
     * Getter fyrir fjöldi spila sem leikmaður hefur.
     *
     * @return fjoldiSpila
     */
    public int getFjoldiSpila() {
        return fjoldiSpila;
    }



    // Hérna miða ég við að leikmadurInterface er einhver
    // leikmaður í staðinn fyrir aðeins dealer svo
    // hægt sé að alhæfa lýsinguna.
    /**
     * Athugar hvort leikmaður vinnur leikmadurInterface.
     *
     * @param leikmadurInterface
     * @return true ef leikmaður vinnur leikmadurInterface, annars false.
     */
    public boolean vinnurDealer(LeikmadurInterface leikmadurInterface) {
        int dealerSamtala = leikmadurInterface.getSamtals();
        //System.out.println(dealerSamtala);

        if (dealerSamtala > 21) return true;

        return samtala > dealerSamtala;
    }

    /**
     * Athugar hvort leikmaður vinnur leikmadurInterface.
     *
     * @param leikmadurInterface
     * @return this ef leikmaður winnur leikmadurInterface, annars leikmadurInterface.
     */
    public LeikmadurInterface hvorVann(LeikmadurInterface leikmadurInterface) {
        if (this.vinnurDealer(leikmadurInterface)) {
            return this;
        }
        return leikmadurInterface;
    }

    /**
     *
     * Tekur öll spil frá leikmanni
     * þ.e.a.s. fjöldi spila verður 0
     * og samtala leikmanns verður 0.
     *
     */
    public void nyrLeikur() {
        samtala = 0;
        fjoldiSpila = 0;
    }
}

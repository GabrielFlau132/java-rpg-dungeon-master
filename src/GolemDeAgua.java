
public class GolemDeAgua extends Personagem {

    public GolemDeAgua() {
        super("Golem de Agua", 100, 45, 13, 8);
    }

    public void usarMagia() {
        if (mana >= 15) {
            mana = mana - 15;
            int dano = danoBase + 10;
            System.out.println(nome + " usou SUFOCAR e causou " + dano + " de dano");
            if (inimigo != null) {
                inimigo.defender(dano);
            }
        } else {
            System.out.println(nome + " esta sem energia.");
        }
    }
}

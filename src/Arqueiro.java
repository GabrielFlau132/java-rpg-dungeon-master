public class Arqueiro extends Personagem {

    public Arqueiro(String nome) {
        super(nome, 100, 60, 14, 6);
    }

    public void usarMagia() {
        if (mana >= 15) {
            mana = mana - 15;
            int dano = danoBase + 12;
            System.out.println(nome + " atirou uma Flecha de Energia causando " + dano + " de dano.");
            if (inimigo != null) {
                inimigo.defender(dano);
            }
        } else {
            System.out.println(nome + " nao tem flechas magicas.");
        }
    }
}

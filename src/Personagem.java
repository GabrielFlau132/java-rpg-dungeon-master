
public abstract class Personagem {

    protected String nome;
    protected int level;
    protected int vida;
    protected int mana;
    protected int danoBase;
    protected int defesaBase;
    protected int exp;

    protected int vidaMaxima;
    protected int manaMaxima;
    protected Mochila mochila;
    protected Personagem inimigo;

    public Personagem(String nome, int vida, int mana, int danoBase, int defesaBase) {
        this.nome = nome;
        this.level = 1;
        this.vida = vida;
        this.vidaMaxima = vida;
        this.mana = mana;
        this.manaMaxima = mana;
        this.danoBase = danoBase;
        this.defesaBase = defesaBase;
        this.exp = 0;
        this.mochila = new Mochila();
    }

    public int atacar() {
        System.out.println(nome + " atacou");
        return danoBase;
    }

    public void defender(int dano) {
        int danoTotal = dano - defesaBase;
        if (danoTotal < 1) {
            danoTotal = 1;
        }
        vida = vida - danoTotal;
        if (vida < 0) {
            vida = 0;
        }
        System.out.println(nome + " recebeu " + danoTotal + " de dano. Vida: " + vida + "/" + vidaMaxima);
    }

    public abstract void usarMagia();

    public void descansar() {
        vida = vida + 20;
        if (vida > vidaMaxima) {
            vida = vidaMaxima;
        }
        mana = mana + 15;
        if (mana > manaMaxima) {
            mana = manaMaxima;
        }
        System.out.println(nome + " descansou. Vida: " + vida + " Mana: " + mana);
    }

    public void ganharExp(int pontos) {
        exp = exp + pontos;
        System.out.println(nome + " ganhou " + pontos + " de experiencia.");
        while (exp >= 100) {
            exp = exp - 100;
            subirDeNivel();
        }
    }

    public void subirDeNivel() {
        level = level + 1;
        vidaMaxima = vidaMaxima + 20;
        manaMaxima = manaMaxima + 10;
        danoBase = danoBase + 5;
        defesaBase = defesaBase + 2;
        vida = vidaMaxima;
        mana = manaMaxima;
        System.out.println(">>> " + nome + " subiu para o nivel " + level + "! <<<");
    }

    public void exibirDetalhes() {
        System.out.println("Nome: " + nome + " | Nivel: " + level);
        System.out.println("Vida: " + vida + "/" + vidaMaxima + " | Mana: " + mana + "/" + manaMaxima);
        System.out.println("Dano: " + danoBase + " | Defesa: " + defesaBase + " | Exp: " + exp);
    }

    public void curarVida(int quantidade) {
        vida = vida + quantidade;
        if (vida > vidaMaxima) {
            vida = vidaMaxima;
        }
    }

    public void curarMana(int quantidade) {
        mana = mana + quantidade;
        if (mana > manaMaxima) {
            mana = manaMaxima;
        }
    }

    public void aumentarDano(int quantidade) {
        danoBase = danoBase + quantidade;
    }

    public void aumentarDefesa(int quantidade) {
        defesaBase = defesaBase + quantidade;
    }

    public void ficarMaisForte(int rodada) {
        vida = vida + (rodada - 1) * 10;
        vidaMaxima = vida;
        danoBase = danoBase + (rodada - 1) * 3;
        level = rodada;
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    public String getNome() {
        return nome;
    }

    public int getVida() {
        return vida;
    }

    public int getMana() {
        return mana;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getManaMaxima() {
        return manaMaxima;
    }

    public int getLevel() {
        return level;
    }

    public Mochila getMochila() {
        return mochila;
    }

    public void setInimigo(Personagem inimigo) {
        this.inimigo = inimigo;
    }
}

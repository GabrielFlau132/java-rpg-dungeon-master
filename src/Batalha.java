import java.util.ArrayList;

public class Batalha {

    public static final int NADA = 0;
    public static final int VITORIA = 1;
    public static final int DERROTA = 2;

    private Personagem heroi;
    private Personagem golem;
    private int rodada;
    private boolean jogoAcabou;

    public void criarHeroi(String classe, String nome) {
        if (classe.equals("Mago")) {
            heroi = new Mago(nome);
        } else if (classe.equals("Arqueiro")) {
            heroi = new Arqueiro(nome);
        } else if (classe.equals("Paladino")) {
            heroi = new Paladino(nome);
        } else {
            heroi = new Guerreiro(nome);
        }

        System.out.println("Bem vindo, " + heroi.getNome() + "!");
        heroi.exibirDetalhes();

        rodada = 1;
        jogoAcabou = false;
    }

    public void novoGolem() {
        golem = sortearGolem();
        golem.ficarMaisForte(rodada);
        heroi.setInimigo(golem);
        golem.setInimigo(heroi);

        System.out.println();
        System.out.println("----- RODADA " + rodada + " -----");
        System.out.println("Um " + golem.getNome() + " apareceu!");
    }

    private Personagem sortearGolem() {
        int sorteio = (int) (Math.random() * 4);
        if (sorteio == 0) {
            return new GolemDeTerra();
        } else if (sorteio == 1) {
            return new GolemDeFogo();
        } else if (sorteio == 2) {
            return new GolemDeVento();
        } else {
            return new GolemDeAgua();
        }
    }

    public int heroiAtaca() {
        System.out.println();
        int dano = heroi.atacar();
        golem.defender(dano);
        return resolverTurno();
    }

    public int heroiUsaMagia() {
        System.out.println();
        heroi.usarMagia();
        return resolverTurno();
    }

    public int heroiDescansa() {
        System.out.println();
        heroi.descansar();
        return resolverTurno();
    }

    public int heroiUsaItem(int numero) {
        System.out.println();
        heroi.getMochila().usarItem(numero, heroi);
        return resolverTurno();
    }

    private int resolverTurno() {
        if (golem.estaVivo() == false) {
            System.out.println();
            System.out.println("Voce derrotou o " + golem.getNome() + "!");
            heroi.ganharExp(40 + rodada * 10);
            rodada = rodada + 1;
            talvezDarItem();
            return VITORIA;
        }

        golemJoga();

        if (heroi.estaVivo() == false) {
            System.out.println();
            System.out.println("Voce foi derrotado...");
            return DERROTA;
        }
        return NADA;
    }

    private void golemJoga() {
        System.out.println("-- Vez do " + golem.getNome() + " --");
        int escolha = (int) (Math.random() * 2);
        if (escolha == 0) {
            int dano = golem.atacar();
            heroi.defender(dano);
        } else {
            golem.usarMagia();
        }
    }

    private void talvezDarItem() {

        if ((int) (Math.random() * 3) != 0) {
            return;
        }

        Item[] possiveis = {
            new MantoDaInvisibilidade(),
            new ChapeuMagico(),
            new Clava(),
            new Escudo(),
            new ArcoDeEnergia(),
            new Bastao()
        };

        ArrayList<Item> faltando = new ArrayList<Item>();
        for (int i = 0; i < possiveis.length; i++) {
            if (temNaMochila(possiveis[i].getNome()) == false) {
                faltando.add(possiveis[i]);
            }
        }

        if (faltando.isEmpty()) {
            return;
        }

        Item novo = faltando.get((int) (Math.random() * faltando.size()));
        heroi.getMochila().adicionar(novo);
        System.out.println("Voce encontrou um item: " + novo.getNome() + "!");
    }

    private boolean temNaMochila(String nome) {
        ArrayList<Item> itens = heroi.getMochila().getItens();
        for (int i = 0; i < itens.size(); i++) {
            if (itens.get(i).getNome().equals(nome)) {
                return true;
            }
        }
        return false;
    }

    public void encerrar() {
        jogoAcabou = true;
        System.out.println();
        System.out.println("=== FIM DE JOGO ===");
        System.out.println("Seu heroi chegou ao nivel " + heroi.getLevel() + ".");
    }

    public Personagem getHeroi() {
        return heroi;
    }

    public Personagem getGolem() {
        return golem;
    }

    public int getRodada() {
        return rodada;
    }

    public boolean isJogoAcabou() {
        return jogoAcabou;
    }
}

# Reinos em Guerra: Desafio dos Heróis

Um RPG de batalha **por turnos** feito em **Java (Swing)** para a disciplina de
Linguagem de Programação I. O jogador escolhe um herói e enfrenta uma sequência
de **golens elementais**, ganhando experiência, subindo de nível e coletando itens.

O jogo foi inspirado em uma campanha de **Dungeons & Dragons** cujo objetivo eram
os itens de *Caverna do Dragão* e cujos chefes eram Golems Elementais.

## Como rodar

Requer o **JDK** (Java 8 ou superior). Na pasta do projeto:

```bash
javac -d out src/*.java
java -cp out Main
```

Isso abre a janela do jogo. Os sprites ficam na pasta `imagens/`.

## O jogo

- **Heróis:** Mago, Guerreiro, Arqueiro e Paladino — cada um com atributos e magia próprios.
- **Chefes:** Golens de Terra, Fogo, Vento e Água — ficam mais fortes a cada rodada.
- **Itens:** aparecem como *loot* (1/3 de chance) ao vencer um golem.
- **Combate por turnos**, com experiência e evolução de nível.

## Conceitos de Orientação a Objetos

- **Classe abstrata:** `Personagem` e `Item`
- **Herança:** heróis e golens `extends Personagem`; itens `extends Item`
- **Polimorfismo:** `usarMagia()` e `usar()` sobrescritos em cada subclasse
- **Composição:** todo `Personagem` tem uma `Mochila` (que guarda uma lista de `Item`)
- **Encapsulamento:** atributos protegidos acessados por *getters*
- **Separação lógica/visual:** `Batalha` (regras do jogo) e `Tela` (interface Swing)

## Estrutura

```
src/        código-fonte Java
imagens/    sprites (heróis e golens) e imagem de fundo
```

## Créditos

O **layout da tela de batalha** (`Tela.java`) foi adaptado do projeto
**[Jade-Journey](https://github.com/xingchen-jin/Jade-Journey)** — uma recriação
do Pokémon Emerald em Java Swing, sob licença **MIT** (Copyright © 2026 Jiawei Jin).
Consulte o arquivo [`LICENSE-Jade-Journey.txt`](LICENSE-Jade-Journey.txt).

Agradecimento ao mestre da campanha que inspirou o jogo, **Tio Rafa**.

## Equipe

- Gabriel Flaulhabe
- Matheus Abraao
- Mestre Willian

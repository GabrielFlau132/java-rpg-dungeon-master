import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

// O layout da batalha foi adaptado do projeto Jade-Journey (recriacao do
// Pokemon Emerald em Java Swing), sob licenca MIT:
//   Repositorio: https://github.com/xingchen-jin/Jade-Journey
//   Copyright (c) 2026 Jiawei Jin (ver LICENSE-Jade-Journey.txt)

// O repositorio original estava em chinhes, e foi traduzido para o portugues utilizando inteligencia artificial (ChatGPT).
// A logica do jogo foi reescrita para o desafio dos herois, mas a interface grafica e o layout da batalha sao do Jade-Journey.

public class Tela extends JFrame implements ActionListener {

    private static final Color CREME = new Color(248, 248, 216);
    private static final Color BORDA = new Color(60, 60, 70);

    private Batalha logica;
    private HashMap<String, BufferedImage> imagens = new HashMap<String, BufferedImage>();
    private BufferedImage imgFundo;
    private PainelBatalha painelBatalha;
    private JTextArea areaTexto;
    private JButton botaoAtacar;
    private JButton botaoMagia;
    private JButton botaoDescansar;
    private JButton botaoItem;

    public Tela() {
        setTitle("Reinos em Guerra - Desafio dos Herois");
        setSize(900, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        logica = new Batalha();
        carregarImagens();

        painelBatalha = new PainelBatalha();
        painelBatalha.setPreferredSize(new Dimension(880, 470));
        add(painelBatalha, BorderLayout.CENTER);

        JPanel painelBaixo = new JPanel(new BorderLayout());
        painelBaixo.setBackground(new Color(30, 30, 40));
        painelBaixo.setPreferredSize(new Dimension(880, 150));

        areaTexto = new JTextArea(4, 20);
        areaTexto.setEditable(false);
        areaTexto.setLineWrap(true);
        areaTexto.setBackground(CREME);
        areaTexto.setForeground(new Color(40, 40, 40));
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(areaTexto);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(70, 110, 190), 4, true));
        painelBaixo.add(scroll, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new GridLayout(2, 2, 6, 6));
        painelBotoes.setBackground(new Color(30, 30, 40));
        painelBotoes.setPreferredSize(new Dimension(300, 150));
        botaoAtacar = criarBotao("Atacar");
        botaoMagia = criarBotao("Usar Magia");
        botaoDescansar = criarBotao("Descansar");
        botaoItem = criarBotao("Usar Item");
        painelBotoes.add(botaoAtacar);
        painelBotoes.add(botaoMagia);
        painelBotoes.add(botaoDescansar);
        painelBotoes.add(botaoItem);
        painelBaixo.add(painelBotoes, BorderLayout.EAST);

        add(painelBaixo, BorderLayout.SOUTH);

        redirecionarSaida();

        setVisible(true);
        iniciar();
    }

    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("SansSerif", Font.BOLD, 15));
        botao.setBackground(CREME);
        botao.setForeground(new Color(50, 50, 60));
        botao.setFocusPainted(false);
        botao.addActionListener(this);
        return botao;
    }

    private void iniciar() {
        String nome = JOptionPane.showInputDialog(this, "Digite o nome do seu heroi:");
        if (nome == null || nome.trim().equals("")) {
            nome = "Heroi";
        }

        String[] classes = { "Mago", "Guerreiro", "Arqueiro", "Paladino" };
        String escolha = (String) JOptionPane.showInputDialog(this, "Escolha seu heroi:",
                "Heroi", JOptionPane.QUESTION_MESSAGE, null, classes, classes[0]);
        if (escolha == null) {
            escolha = "Guerreiro";
        }

        logica.criarHeroi(escolha, nome);
        logica.novoGolem();
        atualizarTela();
        ligarBotoes(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (logica.isJogoAcabou()) {
            return;
        }
        Object fonte = e.getSource();
        if (fonte == botaoAtacar) {
            tratarResultado(logica.heroiAtaca());
        } else if (fonte == botaoMagia) {
            tratarResultado(logica.heroiUsaMagia());
        } else if (fonte == botaoDescansar) {
            tratarResultado(logica.heroiDescansa());
        } else if (fonte == botaoItem) {
            usarItem();
        }
    }

    private void usarItem() {
        Mochila mochila = logica.getHeroi().getMochila();
        if (mochila.quantidade() == 0) {
            JOptionPane.showMessageDialog(this, "Sua mochila esta vazia!");
            return;
        }
        ArrayList<Item> itens = mochila.getItens();
        String[] nomes = new String[itens.size()];
        for (int i = 0; i < itens.size(); i++) {
            nomes[i] = itens.get(i).getNome();
        }

        String escolha = (String) JOptionPane.showInputDialog(this, "Escolha um item:",
                "Mochila", JOptionPane.QUESTION_MESSAGE, null, nomes, nomes[0]);
        if (escolha == null) {
            return;
        }

        int pos = 0;
        for (int i = 0; i < nomes.length; i++) {
            if (nomes[i].equals(escolha)) {
                pos = i;
            }
        }
        tratarResultado(logica.heroiUsaItem(pos + 1));
    }

    private void tratarResultado(int resultado) {
        atualizarTela();

        if (resultado == Batalha.VITORIA) {
            ligarBotoes(false);
            int op = JOptionPane.showConfirmDialog(this, "Voce venceu! Quer continuar?",
                    "Vitoria", JOptionPane.YES_NO_OPTION);
            if (op == JOptionPane.YES_OPTION) {
                logica.novoGolem();
                atualizarTela();
                ligarBotoes(true);
            } else {
                fimDeJogo();
            }
        } else if (resultado == Batalha.DERROTA) {
            fimDeJogo();
        }
    }

    private void fimDeJogo() {
        logica.encerrar();
        ligarBotoes(false);
        atualizarTela();
        JOptionPane.showMessageDialog(this,
                "Fim de jogo! Seu heroi chegou ao nivel " + logica.getHeroi().getLevel() + ".");
    }

    private void ligarBotoes(boolean ligar) {
        botaoAtacar.setEnabled(ligar);
        botaoMagia.setEnabled(ligar);
        botaoDescansar.setEnabled(ligar);
        botaoItem.setEnabled(ligar);
    }

    private void atualizarTela() {
        painelBatalha.repaint();
        areaTexto.setCaretPosition(areaTexto.getDocument().getLength());
    }

    private void carregarImagens() {
        String[] arquivos = { "mago.png", "guerreiro.png", "arqueiro.png", "paladino.png",
                "golem_terra.png", "golem_fogo.png", "golem_vento.png", "golem_agua.png" };
        for (int i = 0; i < arquivos.length; i++) {
            BufferedImage img = carregar(arquivos[i]);
            if (img != null) {
                imagens.put(arquivos[i], img);
            }
        }
        imgFundo = carregar("fundo.png");
    }

    private BufferedImage carregar(String nome) {
        try {
            File arquivo = new File("imagens/" + nome);
            if (arquivo.exists() && arquivo.length() > 0) {
                return ImageIO.read(arquivo);
            }
        } catch (Exception e) {

        }
        return null;
    }

    private String spriteDe(Personagem p) {
        if (p instanceof Mago) {
            return "mago.png";
        } else if (p instanceof Guerreiro) {
            return "guerreiro.png";
        } else if (p instanceof Arqueiro) {
            return "arqueiro.png";
        } else if (p instanceof Paladino) {
            return "paladino.png";
        } else if (p instanceof GolemDeTerra) {
            return "golem_terra.png";
        } else if (p instanceof GolemDeFogo) {
            return "golem_fogo.png";
        } else if (p instanceof GolemDeVento) {
            return "golem_vento.png";
        } else if (p instanceof GolemDeAgua) {
            return "golem_agua.png";
        }
        return "";
    }

    private void redirecionarSaida() {
        OutputStream saida = new OutputStream() {
            public void write(int b) {
                if (b != '\r') {
                    areaTexto.append(String.valueOf((char) b));
                }
            }
        };
        System.setOut(new PrintStream(saida, true));
    }

    private class PainelBatalha extends JPanel {

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            desenharFundo(g2, w, h);

            Personagem heroi = logica.getHeroi();
            Personagem golem = logica.getGolem();
            if (heroi == null) {
                return;
            }

            int inimigoCx = (int) (w * 0.72);
            int inimigoCy = (int) (h * 0.40);
            int heroiCx = (int) (w * 0.24);
            int heroiCy = (int) (h * 0.80);
            desenharPlataforma(g2, inimigoCx, inimigoCy, 150, 30);
            desenharPlataforma(g2, heroiCx, heroiCy, 185, 38);

            if (golem != null) {
                desenharSprite(g2, golem, inimigoCx - 95, inimigoCy + 10 - 190, 190, 190);
            }
            desenharSprite(g2, heroi, heroiCx - 105, heroiCy + 10 - 205, 210, 205);

            if (golem != null) {
                desenharCaixaVida(g2, golem, 25, 25, 320, false);
            }
            desenharCaixaVida(g2, heroi, w - 25 - 340, h - 118, 340, true);

            g2.setFont(new Font("SansSerif", Font.BOLD, 14));
            desenharTextoContorno(g2, "Rodada " + logica.getRodada(), w - 115, 30, Color.WHITE);
        }

        private void desenharFundo(Graphics2D g2, int w, int h) {
            if (imgFundo != null) {
                g2.drawImage(imgFundo, 0, 0, w, h, null);
                return;
            }
            GradientPaint ceu = new GradientPaint(0, 0, new Color(150, 200, 235),
                    0, h, new Color(215, 235, 245));
            g2.setPaint(ceu);
            g2.fillRect(0, 0, w, h);
            g2.setColor(new Color(150, 210, 140));
            g2.fillRect(0, (int) (h * 0.55), w, h);
        }

        private void desenharPlataforma(Graphics2D g2, int cx, int cy, int rx, int ry) {

            g2.setColor(new Color(128, 128, 128, 128));
            g2.fillOval(cx - rx, cy - ry, rx * 2, ry * 2);
        }   
        private void desenharSprite(Graphics2D g2, Personagem p, int x, int y, int larg, int alt) {
            BufferedImage img = imagens.get(spriteDe(p));
            if (img != null) {
                g2.drawImage(img, x, y, larg, alt, null);
            } else {
                g2.setColor(new Color(255, 255, 255, 200));
                g2.fillRoundRect(x, y, larg, alt, 20, 20);
                g2.setColor(new Color(120, 120, 120));
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(x, y, larg, alt, 20, 20);
                g2.setColor(new Color(90, 90, 90));
                g2.setFont(new Font("SansSerif", Font.BOLD, 15));
                desenharTextoCentralizado(g2, "[sprite]", x, y + alt / 2 - 6, larg);
                desenharTextoCentralizado(g2, p.getNome(), x, y + alt / 2 + 16, larg);
            }
        }

        private void desenharCaixaVida(Graphics2D g2, Personagem p, int x, int y, int larg, boolean detalhado) {
            int alt = detalhado ? 92 : 58;

            g2.setColor(CREME);
            g2.fillRoundRect(x, y, larg, alt, 16, 16);
            g2.setColor(BORDA);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(x, y, larg, alt, 16, 16);

            g2.setColor(new Color(40, 40, 45));
            g2.setFont(new Font("SansSerif", Font.BOLD, 16));
            g2.drawString(p.getNome(), x + 14, y + 24);
            String lv = "Lv " + p.getLevel();
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(lv, x + larg - 14 - fm.stringWidth(lv), y + 24);

            g2.setColor(new Color(70, 70, 80));
            g2.setFont(new Font("SansSerif", Font.BOLD, 13));
            g2.drawString("HP", x + 14, y + 44);
            int barraX = x + 44;
            int barraLarg = larg - 58;
            desenharBarraVida(g2, barraX, y + 34, barraLarg, 12, p.getVida(), p.getVidaMaxima());

            if (detalhado) {
                String hp = p.getVida() + "/" + p.getVidaMaxima();
                g2.setColor(new Color(40, 40, 45));
                g2.setFont(new Font("SansSerif", Font.BOLD, 13));
                FontMetrics fm2 = g2.getFontMetrics();
                g2.drawString(hp, x + larg - 14 - fm2.stringWidth(hp), y + 60);
                g2.drawString("MP", x + 14, y + 82);
                desenharBarra(g2, barraX, y + 72, barraLarg, 10, p.getMana(), p.getManaMaxima(),
                        new Color(80, 140, 240), new Color(30, 35, 70));
            }
        }

        private void desenharBarraVida(Graphics2D g2, int x, int y, int larg, int alt, int atual, int max) {
            double razao = max > 0 ? (double) atual / max : 0;
            Color cor;
            if (razao > 0.5) {
                cor = new Color(96, 208, 80);
            } else if (razao > 0.2) {
                cor = new Color(240, 200, 64);
            } else {
                cor = new Color(216, 72, 56);
            }
            desenharBarra(g2, x, y, larg, alt, atual, max, cor, new Color(60, 60, 60));
        }

        private void desenharBarra(Graphics2D g2, int x, int y, int larg, int alt,
                                   int atual, int max, Color frente, Color fundo) {
            g2.setColor(fundo);
            g2.fillRoundRect(x, y, larg, alt, 6, 6);
            int p = max > 0 ? (int) ((double) atual / max * larg) : 0;
            if (p < 0) {
                p = 0;
            }
            if (p > larg) {
                p = larg;
            }
            g2.setColor(frente);
            g2.fillRoundRect(x, y, p, alt, 6, 6);
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(x, y, larg, alt, 6, 6);
        }

        private void desenharTextoContorno(Graphics2D g2, String texto, int x, int y, Color cor) {
            g2.setColor(Color.BLACK);
            g2.drawString(texto, x - 1, y);
            g2.drawString(texto, x + 1, y);
            g2.drawString(texto, x, y - 1);
            g2.drawString(texto, x, y + 1);
            g2.setColor(cor);
            g2.drawString(texto, x, y);
        }

        private void desenharTextoCentralizado(Graphics2D g2, String texto, int x, int y, int larg) {
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(texto, x + (larg - fm.stringWidth(texto)) / 2, y);
        }
    }
}

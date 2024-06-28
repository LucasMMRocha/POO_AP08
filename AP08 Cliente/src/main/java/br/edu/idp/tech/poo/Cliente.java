package main.java.br.edu.idp.tech.poo;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public Cliente() {
    }

    public void iniciar(String endereco, int porta) {
        Scanner ler = new Scanner(System.in);
        String mensagem = "";

        try {
            Socket conexao = new Socket(endereco, porta);
            System.out.println("Conectado ao servidor " + endereco + ", na porta: " + porta);
            System.out.println("Digite: FIM para encerrar a conexao");
            ObjectOutputStream saida = new ObjectOutputStream(conexao.getOutputStream());
            saida.flush();
            ObjectInputStream entrada = new ObjectInputStream(conexao.getInputStream());
            mensagem = (String)entrada.readObject();
            System.out.println("Servidor>> " + mensagem);

            do {
                System.out.print("Eru..: ");
                mensagem = ler.nextLine();
                String mensagem_copia = mensagem;
                saida.writeObject(mensagem);
                saida.flush();
                System.out.print("    A sua mensagem foi enviada para Melkor e ele respondeu:\n");
                mensagem = (String)entrada.readObject();
                System.out.print("    >> " + mensagem + "\n");
                System.out.print("(a mensagem original foi: " + mensagem_copia + ")\n");
            } while(!mensagem.equals("FIM"));

            saida.close();
            entrada.close();
            conexao.close();
        } catch (Exception var9) {
            Exception e = var9;
            System.err.println("erro: " + e.toString());
        }

    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Uso: java tcp.Cliente <endereco-IP> <porta>");
            System.exit(1);
        }

        Cliente c = new Cliente();
        c.iniciar(args[0], Integer.parseInt(args[1]));
    }
}
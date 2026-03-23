package br.com.fiap.soap.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MensagemResponse {

    private boolean sucesso;
    private String mensagem;

    public MensagemResponse() {}

    public MensagemResponse(boolean sucesso, String mensagem) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
    }

    public boolean isSucesso() { return sucesso; }
    public void setSucesso(boolean sucesso) { this.sucesso = sucesso; }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
}

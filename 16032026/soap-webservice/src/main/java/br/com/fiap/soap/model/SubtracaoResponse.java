package br.com.fiap.soap.model;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class SubtracaoResponse {
    private int resultado;
    public SubtracaoResponse() {}
    public SubtracaoResponse(int resultado) {
        this.resultado = resultado;
    }
    public int getResultado() {
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }
}

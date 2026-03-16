package br.com.fiap.soap.model;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class SomaResponse {
    private int resultado;
    public SomaResponse() {}
    public SomaResponse(int resultado) {
        this.resultado = resultado;
    }
    public int getResultado() {
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }
}

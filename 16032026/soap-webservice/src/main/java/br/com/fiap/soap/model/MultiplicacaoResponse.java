package br.com.fiap.soap.model;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class MultiplicacaoResponse {
    private int resultado;
    public MultiplicacaoResponse() {}
    public MultiplicacaoResponse(int resultado) {
        this.resultado = resultado;
    }
    public int getResultado() {
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }
}

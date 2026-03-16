package br.com.fiap.soap.service;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import br.com.fiap.soap.model.DivisaoResponse;
import br.com.fiap.soap.model.MultiplicacaoResponse;
import br.com.fiap.soap.model.SomaResponse;
import br.com.fiap.soap.model.SubtracaoResponse;

@WebService
public class CalculadoraService {
    @WebMethod
    public SomaResponse somar(
            // @WebParam define explicitamente o nome do campo no XML
            @WebParam(name = "numero1") int numero1,
            @WebParam(name = "numero2") int numero2
    ) {
        int resultado = numero1 + numero2;
        return new SomaResponse(resultado);
    }

    @WebMethod
    public SubtracaoResponse subtrair(
            // @WebParam define explicitamente o nome do campo no XML
            @WebParam(name = "numero1") int numero1,
            @WebParam(name = "numero2") int numero2
    ) {
        int resultado = numero1 - numero2;
        return new SubtracaoResponse(resultado);
    }

    @WebMethod
    public MultiplicacaoResponse multiplicar(
            // @WebParam define explicitamente o nome do campo no XML
            @WebParam(name = "numero1") int numero1,
            @WebParam(name = "numero2") int numero2
    ) {
        int resultado = numero1 * numero2;
        return new MultiplicacaoResponse(resultado);
    }

    @WebMethod
    public DivisaoResponse dividir(
            // @WebParam define explicitamente o nome do campo no XML
            @WebParam(name = "numero1") int numero1,
            @WebParam(name = "numero2") int numero2
    ) {
        int resultado = numero1 / numero2;
        return new DivisaoResponse(resultado);
    }
}
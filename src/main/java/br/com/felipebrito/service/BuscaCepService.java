package br.com.felipebrito.service;

import br.com.felipebrito.beans.Endereco;
import br.com.felipebrito.beans.TipoRetornoCEP;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Exemplo de implementação de busca de cep.
 *
 * Created by Felipe on 21/06/2017.
 */
public class BuscaCepService {


    private static final String ENDERECO_SERVICO_CEP = "https://viacep.com.br/ws";


    public Endereco buscarEnderecoPorCep(String cep) throws IOException {

        URL url = new URL(ENDERECO_SERVICO_CEP+"/"+cep+"/"+TipoRetornoCEP.json.name());

        //cria o objeto de conexão
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Request-Method", "GET");
        connection.setDoInput(true);
        connection.setDoOutput(false);
        connection.connect();

        //recebe os dados da conexão
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        StringBuilder newData = new StringBuilder();

        String aux;

        //monta o json com o retorno do serviço
        while (null != ((aux = br.readLine()))) {
            newData.append(aux);
        }
        //fecha a conexão com a url do serviço
        br.close();


        Gson gson = new Gson();

        //popula o objeto com o endereço completo
        Endereco endereco = new Endereco();
        endereco = gson.fromJson(newData.toString(), Endereco.class);

        return endereco;


    }


    public static void main(String[] args) {


        BuscaCepService buscaCepService = new BuscaCepService();

        try {

            System.out.println(  buscaCepService.buscarEnderecoPorCep("05547030") );

        } catch (IOException e) {
            e.printStackTrace();
        }


    }




}

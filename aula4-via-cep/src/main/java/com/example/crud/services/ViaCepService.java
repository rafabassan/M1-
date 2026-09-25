package com.example.crud.services;

import com.example.crud.domain.product.ViaCepResponse;
import com.example.crud.exceptions.IntegrationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCepService {
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public ViaCepService(
            @Value("${viacep.base-url}") String baseUrl,
            @Value("${viacep.connect-timeout-ms:3000}") int connectTimeout,
            @Value("${viacep.read-timeout-ms:5000}") int readTimeout) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeout);
        factory.setReadTimeout(readTimeout);
        this.restTemplate = new RestTemplate(factory);
        this.baseUrl = baseUrl;
    }

    public ViaCepResponse consultar(String cep) {
        String cepNormalizado = cep == null ? "" : cep.replaceAll("\\D", "");
        if (!cepNormalizado.matches("\\d{8}")) {
            throw new IllegalArgumentException("CEP deve conter 8 dígitos");
        }
        try {
            ViaCepResponse response = restTemplate.getForObject(baseUrl + "/" + cepNormalizado + "/json/", ViaCepResponse.class);
            if (response == null) {
                throw new IntegrationException("A ViaCEP não retornou dados");
            }
            if (Boolean.TRUE.equals(response.erro())) {
                throw new IllegalArgumentException("CEP não encontrado");
            }
            return response;
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (RestClientException ex) {
            throw new IntegrationException("Não foi possível consultar a ViaCEP no momento");
        }
    }
}

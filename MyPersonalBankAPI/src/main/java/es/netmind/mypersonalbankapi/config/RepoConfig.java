package es.netmind.mypersonalbankapi.config;

import es.netmind.mypersonalbankapi.persistencia.ClientesDBRepo;
import es.netmind.mypersonalbankapi.persistencia.IClientesRepo;
import es.netmind.mypersonalbankapi.properties.PropertyValues;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class RepoConfig {

//    @Bean
//    public String getUrlConn() throws IOException {
//        PropertyValues props = new PropertyValues();
//        String connectUrl = props.getPropValues().getProperty("db_url");
//        return connectUrl;
//    }

    //@Autowired
    @Value("${db_url}") //Inyecta un valor de propiedad
    String connectUrl;

    @Bean
    public IClientesRepo getClientesDBRepo() throws Exception {
        ClientesDBRepo repo = new ClientesDBRepo();
        repo.setDb_url(connectUrl); //Error "java.sql.SQLException: The url cannot be null" si dejamos comentada esta línea
        return repo;
    }

}

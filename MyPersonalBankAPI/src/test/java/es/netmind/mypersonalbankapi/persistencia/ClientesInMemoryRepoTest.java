package es.netmind.mypersonalbankapi.persistencia;

import es.netmind.mypersonalbankapi.config.SpringConfig;
import es.netmind.mypersonalbankapi.exceptions.ClienteException;
import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

@Component@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
@ActiveProfiles("testing")
class ClientesInMemoryRepoTest {

    @Autowired
    private IClientesRepo clientesRepo;

    @Test
    void testBeans() throws Exception {
        assertThat(clientesRepo, notNullValue());
        System.out.println(clientesRepo.getClientById(2));
    }

    @Test
    void dadounRepositorioClientes_cuandoClienteExiste_entoncesClaseCliente() throws Exception {
        //IClientesRepo clientesRepo = ClientesInMemoryRepo.getInstance();  //Usará bean SPRING
        Cliente cl = clientesRepo.getClientById(1);
        System.out.println(cl);
        assertNotNull(cl);
    }

    @Test
    void dadounRepositorioClientes_cuandoClienteNoExiste_entoncesExcepcion() {
        //IClientesRepo clientesRepo = ClientesInMemoryRepo.getInstance();  //Usará bean SPRING
        assertThrows(ClienteException.class, () -> {
            Cliente cl = clientesRepo.getClientById(4);
            System.out.println(cl);
        });
    }
}
package es.netmind.mypersonalbankapi.persistencia;

import es.netmind.mypersonalbankapi.exceptions.ClienteException;
import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientesInMemoryRepoTest {

    @Test
    void dadounRepositorioClientes_cuandoClienteExiste_entoncesClaseCliente() throws Exception {
        IClientesRepo clientesRepo = ClientesInMemoryRepo.getInstance();
        Cliente cl = clientesRepo.getClientById(1);
        assertNotNull(cl);
    }

    @Test
    void dadounRepositorioClientes_cuandoClienteNoExiste_entoncesExcepcion() {
        IClientesRepo clientesRepo = ClientesInMemoryRepo.getInstance();
        assertThrows(ClienteException.class, () -> {
            Cliente cl = clientesRepo.getClientById(4);
        });
    }
}
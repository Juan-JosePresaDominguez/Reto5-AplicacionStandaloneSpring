package es.netmind.mypersonalbankapi.controladores;

import es.netmind.mypersonalbankapi.exceptions.ClienteException;
import es.netmind.mypersonalbankapi.modelos.clientes.Cliente;
import es.netmind.mypersonalbankapi.persistencia.ClientesInMemoryRepo;
import es.netmind.mypersonalbankapi.persistencia.IClientesRepo;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static java.lang.System.out;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClientesControllerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    @Order(1)
    void dadoUsuarioConsultaDetalle_cuandoHayClientes_entoncesObtieneDatosCliente() throws Exception {
        //given
        int long1 = ClientesController.numeroClientes();
        //when
        ClientesController.mostrarDetalle(1);
        //then
        assertEquals(3, long1);
        assertThat(outContent.toString(), containsString("Personal{dni='12345678J'} Cliente{id=1, nombre='Juan Juanez', email='jj@j.com', direccion='Calle JJ 1', alta=2023-10-23, activo=true, moroso=false, cuentas=[Cuenta{id=1, fechaCreacion=2023-10-23, saldo=100.0, transacciones=null, interes=1.1, comision=0.2}, Cuenta{id=4, fechaCreacion=2023-10-23, saldo=300.0, transacciones=null, interes=1.1, comision=0.2}], prestamos=[Prestamo{id=1, fechaConcesion=2023-10-23, monto=1000.0, saldo=1000.0, pagos=null, moras=null, interes=4, interesMora=2, moroso=false, liquidado=false}]}"));
    }

    @Test
    @Order(2)
    void dadoUsuarioConsultaDetalle_cuandoClienteNoExiste_entoncesError() {
        //given
        int long1 = 0;
        try {
            long1 = ClientesController.numeroClientes();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //when
        ClientesController.mostrarDetalle(4);
        //then
        assertEquals(3, long1);
        assertThat(outContent.toString(), containsString("Cliente NO encontrado"));
    }

    @Test
    @Order(3)
    void dadoUsuarioQuiereModificarCliente_cuandoDatosNOK_entoncesModificacionNOK() {
        String[] datos = {
                "Francisco Lopez",
                "emaile|gmail.com",
                "C/Huelva 13, Barcelona",
                "2023-10-18",
                "true",
                "false",
                "12345678Z"
        };
        ClientesController.actualizar(1, datos);

        assertThat(outContent.toString(), containsString("nombre='Juan Juanez', email='jj@j.com'"));
    }

    @Test
    @Order(4)
    void dadoUsuarioQuiereModificarCliente_cuandoDatosOK_entoncesModificacionOK() {
        String[] datos = {
                "Carlos Lopez",
                "emaile@gmail.com",
                "C/Huelva 13, Barcelona",
                "2023-10-18",
                "true",
                "false",
                "12345678Z"
        };
        ClientesController.actualizar(1, datos);

        assertThat(outContent.toString(), containsString("Carlos Lopez"));
    }

    @Test
    @Order(5)
    void dadoUsuarioQuiereConsultar_cuandoHayClientes_entoncesObtieneListaClientes() throws Exception {
        //given
        int long1 = ClientesController.numeroClientes();
        //when
        ClientesController.mostrarLista();
        //then
        assertEquals(3, long1);
        assertThat(outContent.toString(), containsString("(3) Servicios Informatico SL 3"));
    }

    @Test
    @Order(6)
    void dadoUsuarioQuiereConsultar_cuandoNoHayClientes_entoncesObtieneListaVacia() throws Exception {
        //given
        ClientesController.eliminar(1);
        ClientesController.eliminar(2);
        ClientesController.eliminar(3);
        int long1 = ClientesController.numeroClientes();
        //when
        ClientesController.mostrarLista();
        //then
        assertEquals(0, long1);
    }

    @Test
    @Order(7)
    void dadoUsuarioQuiereAltaCliente_cuandoDatosOK_entoncesAltaOK() {
        String[] datos = {
                "personal",
                "Carlos Sanchez",
                "emaile@gmail.com",
                "C/Huelva 13, Barcelona",
                "2023-10-18",
                "12345678Z"
        };
        ClientesController.add(datos);
        String respuesta = outContent.toString();

        assertTrue(respuesta.contains("Cliente añadido"));
    }

    @Test
    @Order(8)
    void dadoUsuarioQuiereAltaCliente_cuandoDatosNOK_entoncesAltaNOK() throws Exception {
        String[] datos = {
                "empresa",
                "Servicios Informatico SL",
                "sis.com",
                "Calle SI 3",
                "2023-10-23",
                "J12345678"
        };
        ClientesController.add(datos);

        ClientesController.mostrarLista();
        System.out.println(outContent);
        //Test NOK
        //assertThat(outContent.toString(), containsString("Oops ha habido un problema, inténtelo más tarde 😞!"));

        //Test OK (Cliente no válido, falta @ en email)
        assertThat(outContent.toString(), containsString("Cliente NO válido"));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
}

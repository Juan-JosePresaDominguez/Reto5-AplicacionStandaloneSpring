package es.netmind.mypersonalbankapi.config;

public interface IClientesController {
    void mostrarLista() throws Exception;
    int numeroClientes() throws Exception;
    void mostrarDetalle(Integer uid);
    void add(String[] args);
    void eliminar(Integer uid);
    void actualizar(Integer uid, String[] args);
    void evaluarPrestamo(Integer uid, Double cantidad);
}

package com.bank.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.bank.config.DbConnection;
import com.bank.model.Cliente;

import com.bank.error.SystemException;

@Repository
public class DaoCliente {
    public void agregarCliente(Cliente cliente) throws SystemException {
        Connection con = DbConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet result = null;
        String sql = "INSERT INTO clientes (nombre, apellido, fechaDeNacimiento, edad, curp) VALUES (?,?,?,?,?)";

        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getFechaDeNacimiento());
            ps.setInt(4, cliente.getEdad());
            ps.setString(5, cliente.getCurp());

            ps.execute();
            result = ps.getGeneratedKeys();

            if (result.next()) {
                ps = con.prepareStatement(
                        "INSERT INTO cuentas (clienteId, fechaVencimiento, saldo) VALUES (?, ?, ?)");
                ps.setString(1, result.getString(1));
                ps.setString(2, cliente.getCuentas().get(0).getFechaVencimiento());
                ps.setDouble(3, cliente.getCuentas().get(0).getSaldo());
                ps.execute();
            }

            con.commit();
        } catch (Exception e) {
            try {
                con.rollback();
            } catch (Exception e1) {
            }
            e.printStackTrace();
            throw new SystemException();
        } finally {
            try {
                con.close();
            } catch (Exception e) {
            }
        }
    }

    public void eliminarCliente(String clienteId) throws SystemException {
        Connection con = DbConnection.getConnection();
        PreparedStatement ps = null;
        String sql = "DELETE FROM clientes WHERE id = (?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, clienteId);
            ps.execute();
            con.close();
        } catch (Exception e) {
            throw new SystemException();
        } finally {
            try {
                con.close();
            } catch (Exception e) {
            }
        }
    }

    public Cliente getClienteByCurp(String curp) throws SystemException {
        Cliente cliente = null;
        Connection con = DbConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM clientes WHERE curp = (?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, curp);
            rs = ps.executeQuery();
            if (rs.next()) {
                DaoCuenta daoCuenta = new DaoCuenta();
                String id = rs.getString("id");
                cliente = Cliente.builder()
                        .id(id)
                        .nombre(rs.getString("nombre"))
                        .apellido(rs.getString("apellido"))
                        .edad(rs.getInt("edad"))
                        .fechaDeNacimiento(rs.getString("fechaDeNacimiento"))
                        .curp(curp)
                        .cuentas(daoCuenta.getCuentasCliente(id))
                        .build();
            }
        } catch (Exception e) {
            throw new SystemException();
        } finally {
            try {
                con.close();
            } catch (Exception e) {
            }
        }
        return cliente;
    }

    public Cliente getClienteById(String id) throws SystemException {
        Cliente cliente = null;
        Connection con = DbConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM clientes WHERE id = (?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                cliente = Cliente.builder()
                        .id(rs.getString("id"))
                        .nombre(rs.getString("nombre"))
                        .apellido(rs.getString("apellido"))
                        .edad(rs.getInt("edad"))
                        .fechaDeNacimiento(rs.getString("fechaDeNacimiento"))
                        .curp(rs.getString("curp"))
                        .build();
            }
        } catch (Exception e) {
            throw new SystemException();
        } finally {
            try {
                con.close();
            } catch (Exception e) {
            }
        }

        return cliente;
    }

    public void actualizarCliente(Cliente cliente) throws SystemException {
        Connection con = DbConnection.getConnection();
        PreparedStatement ps = null;
        String sql = "UPDATE clientes SET nombre = (?), apellido = (?), fechaDeNacimiento = (?), edad = (?), curp = (?) WHERE id = (?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getFechaDeNacimiento());
            ps.setInt(4, cliente.getEdad());
            ps.setString(5, cliente.getCurp());
            ps.setString(6, cliente.getId());
            ps.execute();
        } catch (Exception e) {
            throw new SystemException();
        } finally {
            try {
                con.close();
            } catch (Exception e) {
            }
        }

    }

    public ArrayList<Cliente> obtenerClientes() throws SystemException {
        ArrayList<Cliente> clientes = new ArrayList<>();
        DaoCuenta daoCuenta = new DaoCuenta();

        Connection con = DbConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM clientes";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                Cliente cliente = Cliente.builder()
                        .id(rs.getString("id"))
                        .nombre(rs.getString("nombre"))
                        .apellido(rs.getString("apellido"))
                        .edad(rs.getInt("edad"))
                        .fechaDeNacimiento(rs.getString("fechaDeNacimiento"))
                        .curp(rs.getString("curp"))
                        .cuentas(daoCuenta.getCuentasCliente(id))
                        .build();
                clientes.add(cliente);
            }

        } catch (Exception e) {
            throw new SystemException();
        } finally {
            try {
                con.close();
            } catch (Exception e) {
            }
        }

        return clientes;
    }

    public boolean existeCurpRegistrada(String curp) throws SystemException {
        Cliente cliente = getClienteByCurp(curp);
        if (cliente != null) {
            return true;
        }

        return false;
    }
}

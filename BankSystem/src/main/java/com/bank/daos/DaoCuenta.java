package com.bank.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.bank.config.DbConnection;
import com.bank.error.SystemException;
import com.bank.model.Cuenta;
@Repository
public class DaoCuenta {
    public void agregarCuentaCliente(Cuenta cuenta) throws SystemException {
        Connection con = DbConnection.getConnection();
        PreparedStatement ps = null;

        String sql = "INSERT INTO cuentas (clienteId, fechaVencimiento, saldo) VALUES (?, ?, ?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, cuenta.getClienteId());
            ps.setString(2, cuenta.getFechaVencimiento());
            ps.setDouble(3, cuenta.getSaldo());
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

    public ArrayList<Cuenta> getCuentasCliente(String clienteId) throws SystemException {
        ArrayList<Cuenta> cuentas = new ArrayList<>();
        Connection con = DbConnection.getConnection();
        ResultSet rs = null;
        PreparedStatement ps = null;
        String sql = "SELECT * FROM cuentas WHERE clienteId = (?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, clienteId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Cuenta cuenta = Cuenta.builder()
                        .noCuenta(rs.getString("numeroDeCuenta"))
                        .clienteId(clienteId)
                        .fechaVencimiento(rs.getString("fechaVencimiento"))
                        .saldo(rs.getDouble("saldo"))
                        .build();
                cuentas.add(cuenta);
            }
        } catch (Exception e) {
            throw new SystemException();
        }

        return cuentas;
    }

    public Cuenta getCuentaCliente(String noCuenta) throws SystemException {
        Cuenta cuenta = null;
        Connection con = DbConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM cuentas WHERE numeroDeCuenta = (?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, noCuenta);
            rs = ps.executeQuery();

            if (rs.next()) {
                cuenta = Cuenta.builder()
                .noCuenta(rs.getString("numeroDeCuenta"))
                .clienteId(rs.getString("clienteId"))
                .fechaVencimiento(rs.getString("fechaVencimiento"))
                .saldo(rs.getDouble("saldo"))
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
        return cuenta;
    }

    public void eliminarCuenta(String noCuenta) throws SystemException {
        Connection con = DbConnection.getConnection();
        PreparedStatement ps = null;
        String sql = "DELETE FROM cuentas WHERE numeroDeCuenta = (?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, noCuenta);
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

    public boolean tieneCuentas(String clienteId) throws SystemException {
        Connection con = DbConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT COUNT(*) FROM cuentas WHERE clienteId = (?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, clienteId);
            rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (Exception e) {
            throw new SystemException();
        } finally {
            try {
                con.close();
            } catch (Exception e) {
            }
        }
        return false;
    }
}

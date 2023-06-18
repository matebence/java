package com.bence.mate;

import com.bence.mate.annotation.PrimaryKey;
import com.bence.mate.annotation.Column;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Field;

import java.util.concurrent.atomic.AtomicLong;
import java.util.StringJoiner;
import java.util.ArrayList;

import java.sql.*;

public class Hibernate<T> {

    private final Connection con;

    private final AtomicLong id = new AtomicLong(0L);

    public static <T> Hibernate<T> getConnection() throws Exception {
        return new Hibernate<T>();
    }

    private Hibernate() throws SQLException {
        this.con = DriverManager.getConnection("jdbc:h2:./Database", "sa", "");
    }

    public void write(T t) throws IllegalArgumentException, IllegalAccessException, SQLException {

        Class<?> clss = t.getClass();

        Field[] declaredFields = clss.getDeclaredFields();

        Field pkey = null;

        ArrayList<Field> columns = new ArrayList<>();
        StringJoiner joiner = new StringJoiner(",");

        for (Field field : declaredFields) {
            if(field.isAnnotationPresent(PrimaryKey.class)) {

                pkey = field;
                //System.out.println("The Primary Key is : "+ field.getName() + " value :"+ field.get(t) + " and the columns are :");
            }else if(field.isAnnotationPresent(Column.class)) {
                joiner.add(field.getName());
                columns.add(field);
                //System.out.println(field.getName() + " value : "+ field.get(t));
            }
        }

        assert pkey != null;
        String sql = "insert into "+ clss.getSimpleName() + "("+pkey.getName()+ ","+ joiner +") "+ "values (?,?,?,?,?);";

        PreparedStatement stmt = con.prepareStatement(sql);

        if(pkey.getType() == long.class) {
            stmt.setLong(1, id.incrementAndGet());
        }

        int index = 2;
        for (Field field : columns) {

            field.setAccessible(true);
            if(field.getType() == int.class) {
                stmt.setInt(index++, (int) field.get(t));
            }else if(field.getType() == String.class) {
                stmt.setString(index++, (String) field.get(t));
            }
        }

        stmt.executeUpdate();

    }

    public T read(Class<T> clss, long l) throws SQLException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InvocationTargetException {

        Field[] declaredFields = clss.getDeclaredFields();

        Field pkey = null;
        for (Field field : declaredFields) {
            if(field.isAnnotationPresent(PrimaryKey.class)) {
                pkey = field;
                break;
            }
        }

        assert pkey != null;
        String sql = "select * from "+ clss.getSimpleName() + " where "+pkey.getName() +" = "+ l;

        PreparedStatement stmt = con.prepareStatement(sql);

        ResultSet rs = stmt.executeQuery();
        rs.next();

        T t = clss.getConstructor().newInstance();

        long transactionId = rs.getInt(pkey.getName());
        pkey.setAccessible(true);
        pkey.set(t, transactionId);

        for (Field field : declaredFields) {
            if(field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);

                if(field.getType() == int.class) {
                    field.set(t, rs.getInt(field.getName()));
                }else if(field.getType() == String.class) {
                    field.set(t, rs.getString(field.getName()));
                }
            }
        }
        return t;
    }
}

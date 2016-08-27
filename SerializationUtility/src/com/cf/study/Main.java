package com.cf.study;

import java.io.*;

public class Main {

    public static void main(final String[] args) {
        final Employee emp = new Employee();
        emp.setName("Fei Chen");
        emp.setTranslatedName("Chen Fei");

        final String file = "employee.ser";
        serialize(emp, file);
        final Employee obj = (Employee) deserialize(file);

        System.out.println("Name: " + obj.getName());
        System.out.println("Translated Name: " + obj.getTranslatedName());
    }

    private static void serialize(final Object obj, final String file) {
        try {
            final FileOutputStream fileOut = new FileOutputStream(file);
            final ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(obj);
            out.close();
            fileOut.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private static Object deserialize(final String file) {
        Object obj = null;
        try {
            final FileInputStream fileIn = new FileInputStream(file);
            final ObjectInputStream in = new ObjectInputStream(fileIn);
            obj = in.readObject();
            in.close();
            fileIn.close();
        } catch (final IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }
}

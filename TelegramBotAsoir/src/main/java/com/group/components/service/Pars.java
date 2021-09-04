package com.group.components.service;

public enum Pars {
    первая, вторая, третья, четвертая, пятая;
    private static Pars[] list = Pars.values();

    public static Pars getPar(int i) {
        return list[i];
    }
}

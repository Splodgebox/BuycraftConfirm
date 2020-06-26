package net.splodgebox.buycraftconfirm.utils.gui;

import java.beans.ConstructorProperties;

public class GuiItem<A, B> {
    private final A a;
    private final B b;

    @ConstructorProperties({"a", "b"})
    public GuiItem(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A getA() {
        return this.a;
    }

    public B getB() {
        return this.b;
    }
}

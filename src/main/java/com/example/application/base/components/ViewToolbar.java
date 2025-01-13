package com.example.application.base.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.theme.lumo.LumoUtility.*;

public final class ViewToolbar extends Composite<Header> {

    private final H1 title;
    private final Div actionArea;

    public ViewToolbar() {
        this("");
    }

    public ViewToolbar(String title, Component... components) {
        addClassNames(Display.FLEX, FlexDirection.COLUMN, JustifyContent.BETWEEN, AlignItems.STRETCH, Gap.SMALL,
                FlexDirection.Breakpoint.Medium.ROW, AlignItems.Breakpoint.Medium.BASELINE);

        this.title = new H1(title);
        this.title.addClassNames(FontSize.XLARGE, Margin.NONE);

        this.actionArea = new Div(components);
        this.actionArea.addClassNames(Display.FLEX, FlexDirection.COLUMN, JustifyContent.BETWEEN, AlignItems.STRETCH,
                Gap.SMALL, FlexDirection.Breakpoint.Medium.ROW);

        getContent().add(this.title, this.actionArea);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void add(Component... components) {
        actionArea.add(components);
    }
}

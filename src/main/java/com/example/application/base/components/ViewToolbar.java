package com.example.application.base.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.theme.lumo.LumoUtility.*;

public final class ViewToolbar extends Composite<Header> {

    private final H1 title;
    private final Div leftActionArea;
    private final Div rightActionArea;

    public ViewToolbar() {
        this("");
    }

    public ViewToolbar(String title) {
        addClassNames(Display.FLEX, FlexDirection.COLUMN, JustifyContent.BETWEEN, AlignItems.STRETCH, Gap.MEDIUM,
                FlexDirection.Breakpoint.Medium.ROW, AlignItems.Breakpoint.Medium.CENTER);

        this.title = new H1(title);
        this.title.addClassNames(FontSize.XLARGE, Margin.NONE, FontWeight.LIGHT);

        leftActionArea = new Div();
        leftActionArea.addClassNames(Display.FLEX, FlexDirection.COLUMN, JustifyContent.START, AlignItems.STRETCH,
                Flex.GROW, Gap.XSMALL, FlexDirection.Breakpoint.Medium.ROW);

        rightActionArea = new Div();
        rightActionArea.addClassNames(Display.FLEX, FlexDirection.COLUMN, JustifyContent.END, AlignItems.STRETCH,
                Gap.XSMALL, FlexDirection.Breakpoint.Medium.ROW);

        var drawerToggle = new DrawerToggle();
        drawerToggle.addClassNames(Margin.NONE);

        var toggleAndTitle = new Div(drawerToggle, this.title);
        toggleAndTitle.addClassNames(Display.FLEX, AlignItems.CENTER);

        getContent().add(toggleAndTitle, leftActionArea, rightActionArea);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public ViewToolbar addToLeftActionArea(Component... components) {
        leftActionArea.add(components);
        return this;
    }

    public ViewToolbar addToRightActionArea(Component... components) {
        rightActionArea.add(components);
        return this;
    }
}

package ru.code.task.views.completequestionnaire;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import ru.code.task.views.MainLayout;
import com.vaadin.flow.router.RouteAlias;
import javax.annotation.security.PermitAll;

@PageTitle("Заполнить анкету")
@Route(value = "questionnaire", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class CompleteQuestionnaireView extends Div {

    public CompleteQuestionnaireView() {
        addClassNames("completequestionnaire-view", "flex", "flex-col", "h-full", "items-center", "justify-center",
                "p-l", "text-center", "box-border");

        Div wrapper = new Div();
        wrapper.addClassNames("box-border");
        wrapper.setWidth("176px");
        wrapper.setHeight("176px");
        Style wrapperStyle = wrapper.getStyle();
        wrapperStyle.set("padding-top", "34px");
        wrapperStyle.set("border-radius", "100px");
        wrapperStyle.set("background", "var(--lumo-shade-10pct)");

        Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("150px");
        wrapper.add(img);
        add(wrapper);

        add(new H2("This place intentionally left empty"));
        add(new Paragraph("It’s a place where you can grow your own UI 🤗"));
    }

}

package ru.code.task.views.login;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import java.io.ByteArrayOutputStream;
import com.vaadin.flow.component.upload.Upload;
import java.util.Base64;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.component.textfield.TextField;
import java.nio.charset.StandardCharsets;
import org.springframework.web.util.UriUtils;
import elemental.json.Json;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Label;

@PageTitle("Авторизация")
@Route(value = "login")
public class LoginView extends LoginOverlay {
    public LoginView() {
        setAction("login");

        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("Анкета");
        i18n.getHeader().setDescription("Используйте для входа user/user или admin/admin");
        i18n.setAdditionalInformation(null);
        setI18n(i18n);

        setForgotPasswordButtonVisible(false);
        setOpened(true);
    }

}

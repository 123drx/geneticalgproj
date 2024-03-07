package daniel.proj.Pages;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import daniel.proj.Servieses.PrincipleServies;
import daniel.proj.Servieses.SchoolServies;
import daniel.proj.objects.Principle;
import daniel.proj.objects.School;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;

@Route("/CreateSchool")
@PageTitle("Create School Page")
public class CreateSchoolPage extends VerticalLayout {
        SchoolServies schoolServies;
        PrincipleServies principleServies;
        String proffesion;
        String realname;
        String userName;
        String password;

    private TextField schoolNameField;

    public CreateSchoolPage(SchoolServies SchoolServies, PrincipleServies PrincipleServies) {
        this.schoolServies = SchoolServies;
        this.principleServies = PrincipleServies;
        VerticalLayout vl = new VerticalLayout();
        proffesion = (String) VaadinSession.getCurrent().getSession().getAttribute("proffesion");
        realname = (String) VaadinSession.getCurrent().getSession().getAttribute("RealName");
        userName = (String) VaadinSession.getCurrent().getSession().getAttribute("username");
        password = (String) VaadinSession.getCurrent().getSession().getAttribute("password");


        H2 Title = new H2("You Are Not Connected To A School Yet");
        Dialog dialog = new Dialog("Create A New School");
       // dialog.getElement().executeJs("this._primaryButton.textContent = 'mmm';");
        //dialog.setHeaderTitle("Create New School");

        VerticalLayout dialogLayout = createDialogLayout();
        dialog.add(dialogLayout);

        Button saveButton = createSaveButton(dialog);
        Button cancelButton = new Button("Cancel", e -> dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        dialog.getHeader().add(cancelButton);
        dialog.getFooter().add(saveButton);

        Button button = new Button("Create School", e -> dialog.open());

        vl.add(Title,dialog, button);
        vl.setAlignItems(Alignment.CENTER);
        add(vl);
    }

    private VerticalLayout createDialogLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(true);
        layout.setSpacing(true);

        // Initialize schoolNameField
        schoolNameField = new TextField("Add a School");
        layout.add(schoolNameField);

        return layout;
    }

    private Button createSaveButton(Dialog dialog) {
        Button saveButton = new Button("Save", event -> {
            String schoolName = schoolNameField.getValue();
            if(schoolServies.findByName(schoolName)!= null)
            {
                Notification.show("School Name Already Taken");
            }
            else
            {
            School school = new School(schoolName);
            schoolServies.insertSchool(school);
            Principle principle = principleServies.findbyname(realname);
            principle.setSchool(school);
            principleServies.UpdatePrinciple(principle);
            routeToSchoolPagePage();
            }
            System.out.println("School Name entered: " + schoolName);
            dialog.close();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return saveButton;
    }

   
   private void routeToSchoolPagePage() {
    UI.getCurrent().getPage().setLocation("/Principle");
}

}

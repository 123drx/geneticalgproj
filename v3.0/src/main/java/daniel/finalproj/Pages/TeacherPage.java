package daniel.finalproj.Pages;

import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import daniel.finalproj.Objects.Principle;
import daniel.finalproj.Objects.School;
import daniel.finalproj.Objects.Subject;
import daniel.finalproj.Objects.Teacher;
import daniel.finalproj.Servieses.PrincipleServies;

@Route("TeacherPage")
@PageTitle("Teachers Page")

public class TeacherPage extends VerticalLayout {
    PrincipleServies ps;
    String PrincipleName;
    School ThisSchool;
    Principle ThisPrinciple;
    Teacher This;
    String TeachersName;
    TextField SubjectNameField;
    TextField subjectWeeklyHoursField;
    TextField SubjectTeacherNameField;
    TextField SubjectPriorityField;
    TextField LockedLessonName;
    TextField LockedLessonTeacher;
    TextField LockedLessonday;
    TextField LockedLessonhour;

    public TeacherPage(PrincipleServies prs) {
        this.ps = prs;
        PrincipleName = (String) VaadinSession.getCurrent().getSession().getAttribute("RealName");
        TeachersName = (String) VaadinSession.getCurrent().getSession().getAttribute("TeachersName");
        ThisPrinciple = ps.findbyname(PrincipleName);
        ThisSchool = ThisPrinciple.getSchool();
        This = ThisSchool.getTeacherByName(TeachersName);
        H1 PageTitle = new H1("Welcome " + TeachersName);
        VerticalLayout vl = createSubjectLayout();

        add(PageTitle,vl);

    }

    public VerticalLayout createSubjectLayout() {
        VerticalLayout v1 = new VerticalLayout();
        HorizontalLayout buttons = new HorizontalLayout();
        Button editSubjectbtn = new Button("Edit", e -> editsubject());
        Button RemoveSubjectButton = new Button("Remove Subject ", e -> removeSubject());
        Button addSubjectButton;
        // TODO add a dialog that create a teacher that exists in the system and adds
        Dialog dialog = new Dialog("Create A New Subject");

        VerticalLayout dialogLayout = createAddSubejctDialogLayout();
        dialog.add(dialogLayout);

        Button saveButton = createSubjectSaveButton(dialog);
        Button cancelButton = new Button("Cancel", e -> dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        dialog.getHeader().add(cancelButton);
        dialog.getFooter().add(saveButton);

        addSubjectButton = new Button("Add Subject", e -> dialog.open());

        buttons.add(addSubjectButton, RemoveSubjectButton, editSubjectbtn);
        Grid<Subject> grid = new Grid<>(Subject.class, false);
        grid.addColumn(Subject::getSubjectName).setHeader("SubjectName");
        grid.addColumn(Subject::getWeeklyHours).setHeader("Weekly Hours");
        grid.addColumn(Subject::getPriority).setHeader("Priority");
        grid.addColumn(Subject::getClasses).setHeader("Classes");
        grid.addItemClickListener(event -> SubjctPressed(event.getItem()));

        List<Subject> Subjects = This.getSubjects();

        H3 h = new H3("Sybjects");
        grid.setItems(Subjects);
        v1.add(h, buttons, grid);

        return v1;
    }
    public void SubjctPressed(Subject sub)
    {

    }
    public void editsubject() {

    }

    public void removeSubject() {

    }

    public VerticalLayout createAddSubejctDialogLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(true);
        layout.setSpacing(true);

        // Initialize schoolNameField
        SubjectNameField = new TextField("SubjectName");
        subjectWeeklyHoursField = new TextField("Subject Weekly Hours");
        SubjectTeacherNameField = new TextField("Subjec Class Name");
        SubjectPriorityField = new TextField("Subject Priority");
        layout.add(SubjectNameField, subjectWeeklyHoursField, SubjectTeacherNameField, SubjectPriorityField);

        return layout;
    }
    public Button createSubjectSaveButton(Dialog d)
    {
        Button saveButton = new Button("Save", event -> { 
            String subname = SubjectNameField.getValue();
            int Weekly = Integer.parseInt(subjectWeeklyHoursField.getValue());
            String clsName = SubjectTeacherNameField.getValue();

            int Priority = Integer.parseInt(SubjectPriorityField.getValue());

            if (This.isSubjectExist(subname)) {
                Notification.show("A Subject With that name already Exists");
            } else {
              
                    Subject sub = new Subject(subname);
                    sub.setPriority(Priority);
                    sub.setTeachersName(This.getName());
                    sub.setWeeklyHours(Weekly);

                    ThisSchool.getClasses().get(ThisSchool.getClassIndexByName(clsName)).addSubject(sub);
                    ps.UpdatePrinciple(ThisPrinciple);
                }
            
            System.out.println("Class Name entered: " + subname);
            d.close();
    });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return saveButton;

    }

}

package daniel.finalproj.Pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vaadin.flow.component.UI;
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

import daniel.finalproj.Algorithms.GeneticAlgorithm;
import daniel.finalproj.Objects.Day;
import daniel.finalproj.Objects.Principle;
import daniel.finalproj.Objects.Schedule;
import daniel.finalproj.Objects.School;
import daniel.finalproj.Objects.SchoolClass;
import daniel.finalproj.Objects.Subject;
import daniel.finalproj.Objects.Teacher;
import daniel.finalproj.Servieses.PrincipleServies;

@Route("/SchoolPage")
@PageTitle("School Page")
public class SchoolPage extends VerticalLayout {
    PrincipleServies PrincipleServies;
    String realname;
    Principle ThisPrinciple;
    School school;
    String Proffesion;
    String PressedTeachersName;
    String PressedClassName;
    TextField ClassNameField;
    Grid<String[]> ScheduleGrid;

    public SchoolPage(PrincipleServies ps) {
        this.PrincipleServies = ps;
        realname = (String) VaadinSession.getCurrent().getSession().getAttribute("RealName");
        Proffesion = (String) VaadinSession.getCurrent().getSession().getAttribute("proffesion");
        ThisPrinciple = PrincipleServies.findbyname(realname);
        System.out.println(ThisPrinciple.getName());
        school = ThisPrinciple.getSchool();
        H1 heather = new H1("Welcome to Schoool : " + school.getSchoolName());
        if (!Proffesion.equals("Principle")) {
            routeToPage("/");
        } else {
            VerticalLayout vl = createTeacherLayout(school);
            VerticalLayout vl2 = createclassLayout(school);
            VerticalLayout vl3 = CreateScheduleLayout(school);
            Button b = new Button("Genetic Algorithm", e -> PreformGeneticAlgorithm(school));
            Button eval = new Button("Eval", e -> PreformEval(school));
            add(heather, vl, vl2, vl3, b,eval);
        }

    }

    public void PreformEval(School school)
    {
        school.printEvaluation();
    }

    public void PreformGeneticAlgorithm(School school) {
        school.initializeBreakFast();
        school.adjustEmptySubjects();
        GeneticAlgorithm GA = new GeneticAlgorithm();
        school = GA.Geneticalgorithm(school);
        ThisPrinciple.setSchool(school);
        PrincipleServies.UpdatePrinciple(ThisPrinciple);
        SetScheduleGridItems(school);

    }

    public VerticalLayout createTeacherLayout(School ThisSchool) {
        VerticalLayout v1 = new VerticalLayout();
        HorizontalLayout buttons = new HorizontalLayout();
        Button editTeacherButton = new Button("Edit", e -> editTeacher());
        Button RemoveTeacherButton = new Button("Remove Teacher ", e -> removeTeacher());
        Button addTeacherButton;
        // TODO add a dialog that create a teacher that exists in the system and adds
        Dialog dialog = new Dialog("Create A New Teacher");
        
        // dialog.getElement().executeJs("this._primaryButton.textContent = 'mmm';");
        // dialog.setHeaderTitle("Create New School");

        VerticalLayout dialogLayout = createAddTeacherDialogLayout();
        dialog.add(dialogLayout);

        Button saveButton = createTeacherSaveButton(dialog);
        Button cancelButton = new Button("Cancel", e -> dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        dialog.getHeader().add(cancelButton);
        dialog.getFooter().add(saveButton);

        addTeacherButton = new Button("Add Teacher", e -> dialog.open());

        buttons.add(addTeacherButton, RemoveTeacherButton, editTeacherButton);
        Grid<Teacher> grid = new Grid<>(Teacher.class, false);
        grid.addColumn(Teacher::getName).setHeader("name");
        grid.addColumn(teacher -> getSubjectNames(teacher.getSubjects())).setHeader("Subjects");
        grid.addItemClickListener(event -> TeacherPressed(event.getItem()));
        List<Teacher> Teachers = ThisSchool.getTeachers();

        H3 h = new H3("Teachers");
        grid.setItems(Teachers);
        v1.add(h, buttons, grid);

        return v1;
    }

    public void editTeacher() {

    }

    public void removeTeacher() {

    }

    public VerticalLayout createAddTeacherDialogLayout() {
        VerticalLayout v1 = new VerticalLayout();

        return v1;
    }

    public Button createTeacherSaveButton(Dialog dialog) {
        Button b = new Button();

        return b;
    }

    public ArrayList<String> getSubjectNames(List<Subject> subjects) {
        ArrayList<String> names = new ArrayList<>();
        for (Subject s : subjects) {
            names.add(s.getSubjectName());
        }
        return names;
    }

    private void TeacherPressed(Teacher Teacher) {
        PressedTeachersName = Teacher.getName();
    }

    private void routeToPage(String proffesion) {
        if (proffesion.equals("Teacher"))
            UI.getCurrent().getPage().setLocation("/Principle");
        else if (proffesion.equals("Principle"))
            UI.getCurrent().getPage().setLocation("/Principle");
        else
            UI.getCurrent().getPage().setLocation("/SignUp");

    }

    public VerticalLayout createclassLayout(School ThisSchool) {
        VerticalLayout v1 = new VerticalLayout();
        HorizontalLayout buttons = new HorizontalLayout();
        Button RemoveTeacherButton = new Button("Remove class ", e -> removeSchoolClass());
        Button addClassButton;
        Button EnterClassButton = new Button("Enter Class", e -> enterClass());
        // TODO add a dialog that create a School that exists in the system and adds
        Dialog dialog1 = new Dialog("Create A New Class");
        Dialog dialog2 = new Dialog("Are You Sure?");
        // dialog.getElement().executeJs("this._primaryButton.textContent = 'mmm';");
        // dialog.setHeaderTitle("Create New School");

        VerticalLayout dialogLayout = createAddClassDialogLayout();
        dialog1.add(dialogLayout);

        Button saveButton = createClassSaveButton(dialog1);
        Button cancelButton = new Button("Cancel", e -> dialog1.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        dialog1.getHeader().add(cancelButton);
        dialog1.getFooter().add(saveButton);

        addClassButton = new Button("Add class", e -> dialog1.open());

        buttons.add(addClassButton, RemoveTeacherButton, EnterClassButton);
        Grid<SchoolClass> grid = new Grid<>(SchoolClass.class, false);
        grid.addColumn(SchoolClass::getClassName).setHeader("name");
        List<SchoolClass> SchoolClasses = ThisSchool.getClasses();

        H3 h = new H3("classes");
        grid.setItems(SchoolClasses);
        grid.addItemClickListener(event -> classPressed(event.getItem()));
        v1.add(h, buttons, grid);

        return v1;
    }

    public void SetScheduleGridItems(School school)
    {
        String[][] scheduleStrings = school.getSchedule().getScheduleStrings();
        String[] headers = scheduleStrings[0];
        for (int i = 1; i < headers.length; i++) { // Start from 1 to skip the "Hour\\Day" cell
        ScheduleGrid = new Grid<>();
        ScheduleGrid.setItems(Arrays.asList(scheduleStrings).subList(1, scheduleStrings.length)); // Skip the header ro
            final int colIndex = i;
            ScheduleGrid.addColumn(rw -> rw[colIndex]).setHeader(headers[colIndex]);
        }

    }

    public VerticalLayout CreateScheduleLayout(School ThisSchool) {

        String[][] scheduleStrings = ThisSchool.getSchedule().getScheduleStrings();

        ScheduleGrid = new Grid<>();
        ScheduleGrid.setItems(Arrays.asList(scheduleStrings).subList(1, scheduleStrings.length)); // Skip the header row

        // Set headers from the first row (which we skipped for items)
        String[] headers = scheduleStrings[0];
        for (int i = 1; i < headers.length; i++) { // Start from 1 to skip the "Hour\\Day" cell
            final int colIndex = i;
            ScheduleGrid.addColumn(rw -> rw[colIndex]).setHeader(headers[colIndex]);
        }
        VerticalLayout v = new VerticalLayout();
        v.add(ScheduleGrid);
        
        v.add(ScheduleGrid);

        return v;
    }

    public void removeSchoolClass() {
        String ClassName = PressedClassName;

    }

    public void enterClass() {
        String ClassName = PressedClassName;
        VaadinSession.getCurrent().getSession().setAttribute("ClassName", ClassName);
        UI.getCurrent().getPage().setLocation("/ClassPage");
    }

    private VerticalLayout createAddClassDialogLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(true);
        layout.setSpacing(true);

        // Initialize schoolNameField
        ClassNameField = new TextField("Class Name");
        layout.add(ClassNameField);

        return layout;
    }

    private Button createClassSaveButton(Dialog dialog) {
        Button saveButton = new Button("Save", event -> {
            String className = ClassNameField.getValue();
            int num = school.getClassIndexByName(className);
            if (num != -1) {
                Notification.show("A Class With that name already Exists");
            } else {
                School newSchool = new School(school);
                SchoolClass newschoolclass = new SchoolClass(className);
                newSchool.addclass(newschoolclass);
                // Update The School
                ThisPrinciple.setSchool(newSchool);
                // Update The Principle
                // TODO Finish this
                // PrincipleServies.UpdatePrinciple(ThisPrinciple);
            }
            System.out.println("Class Name entered: " + className);
            dialog.close();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return saveButton;
    }

    public void classPressed(SchoolClass sc) {
        PressedClassName = sc.getClassName();
    }
}

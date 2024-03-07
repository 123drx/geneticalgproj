package daniel.proj.Pages;

import java.util.ArrayList;
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

import daniel.proj.Algorithms.GeneticAlgorithm;
import daniel.proj.Servieses.PrincipleServies;
import daniel.proj.Servieses.SchoolServies;
import daniel.proj.Servieses.TeacherServies;
import daniel.proj.objects.Principle;
import daniel.proj.objects.School;
import daniel.proj.objects.SchoolClass;
import daniel.proj.objects.Subject;
import daniel.proj.objects.Teacher;
import javassist.bytecode.stackmap.TypeData.ClassName;

@Route("/Principle")
@PageTitle("SchoolPage")
public class SchoolPage extends VerticalLayout {
    PrincipleServies Principalservies;
    TeacherServies teacherServies;
    SchoolServies schoolServies;
    String PressedClassName;
    String PressedTeachersName;
    TextField TeacherNameField;
    TextField ClassNameField;
    Principle ThisPrinciple;

    public SchoolPage(PrincipleServies ps, SchoolServies Ss, TeacherServies Ts) {
        this.Principalservies = ps;
        this.schoolServies = Ss;
        this.teacherServies = Ts;

        String proffesion = (String) VaadinSession.getCurrent().getSession().getAttribute("proffesion");
        String realname = (String) VaadinSession.getCurrent().getSession().getAttribute("RealName");
        ThisPrinciple = Principalservies.findbyname(realname);
        if (ThisPrinciple == null) {
            Notification.show("User Is Not A Principle!");
            System.out.println(realname);
            routeToPage("/");
        }
        if (ThisPrinciple.getSchool() != null) {
            H1 Heather = new H1("School : " + ThisPrinciple.getSchool().getName());
            String SchoolName = ThisPrinciple.getSchool().getName();
            School ThisSchool = ThisPrinciple.getSchool();
            VerticalLayout Vl = createTeacherLayout(ThisSchool);
            VerticalLayout Vl2 = createclassLayout(ThisSchool);
            Button Genetic = new Button("Genetic Algo");
            Genetic.addClickListener(event -> PerformGeneticAlgorithm());
            Button Eval = new Button("Eval");
            Eval.addClickListener(event -> PreformEvaluation());
            add(Heather, Vl, Vl2, Genetic);
        } else
            routeTofillPage();

    }

    private void routeToPage(String proffesion) {
        if (proffesion.equals("Teacher"))
            UI.getCurrent().getPage().setLocation("/TeacherPage");
        else if (proffesion.equals("Principle"))
            UI.getCurrent().getPage().setLocation("/Principle");
        else
            UI.getCurrent().getPage().setLocation("/");
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
    public ArrayList<String> getSubjectNames(List<Subject> subjects)
    {
        ArrayList<String> names = new ArrayList<>();
        for(Subject s : subjects)
        {
            names.add(s.getSubjectName());
        }
        return names;
    }

    public VerticalLayout createclassLayout(School ThisSchool) {
        VerticalLayout v1 = new VerticalLayout();
        HorizontalLayout buttons = new HorizontalLayout();
        Button editclassButton = new Button("Edit", e -> editSchoolClass());
        Button RemoveTeacherButton = new Button("Remove class ", e -> removeSchoolClass());
        Button addClassButton;
        Button EnterClassButton = new Button("Enter Class", e -> enterClass());
        // TODO add a dialog that create a School that exists in the system and adds
        Dialog dialog = new Dialog("Create A New Class");
        // dialog.getElement().executeJs("this._primaryButton.textContent = 'mmm';");
        // dialog.setHeaderTitle("Create New School");

        VerticalLayout dialogLayout = createAddClassDialogLayout();
        dialog.add(dialogLayout);

        Button saveButton = createClassSaveButton(dialog);
        Button cancelButton = new Button("Cancel", e -> dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        dialog.getHeader().add(cancelButton);
        dialog.getFooter().add(saveButton);

        addClassButton = new Button("Add class", e -> dialog.open());

        buttons.add(addClassButton, RemoveTeacherButton, editclassButton, EnterClassButton);
        Grid<SchoolClass> grid = new Grid<>(SchoolClass.class, false);
        grid.addColumn(SchoolClass::getClassName).setHeader("name");
        List<SchoolClass> SchoolClasses = ThisSchool.getClasses();

        H3 h = new H3("classes");
        grid.setItems(SchoolClasses);
        grid.addItemClickListener(event -> classPressed(event.getItem()));
        v1.add(h, buttons, grid);

        return v1;
    }

    public void editTeacher() {

    }

    public void removeTeacher() {

    }

    public void addTeacher() {

    }

    public void editSchoolClass() {

    }

    public void removeSchoolClass() {

    }

    public void addSchoolClass() {

    }

    public void enterClass() {
        String ClassName = PressedClassName;
        VaadinSession.getCurrent().getSession().setAttribute("ClassName", ClassName);
        UI.getCurrent().getPage().setLocation("/ClassPage");

    }

    private void classPressed(SchoolClass SchoolClass) {
        PressedClassName = SchoolClass.getClassName();
    }

    private void TeacherPressed(Teacher Teacher) {
        PressedTeachersName = Teacher.getName();
    }

    private void routeTofillPage() {
        UI.getCurrent().getPage().setLocation("/CreateSchool");
    }

    private VerticalLayout createAddTeacherDialogLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(true);
        layout.setSpacing(true);

        // Initialize schoolNameField
        TeacherNameField = new TextField("Teacher Name");
        layout.add(TeacherNameField);

        return layout;
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
            School school = schoolServies.findByName(ThisPrinciple.getSchool().getName());
            SchoolClass schoolclass = school.getClass(className);
            if (schoolclass != null) {
                Notification.show("A Class With that name already Exists");
            } else {
                School newSchool = schoolServies.findByName(ThisPrinciple.getSchool().getName());
                SchoolClass newschoolclass = new SchoolClass(className);
                newSchool.addclass(newschoolclass);
                // Update The School
                schoolServies.updateSchool(newSchool);
                ThisPrinciple.setSchool(newSchool);
                // Update The Principle
                Principalservies.UpdatePrinciple(ThisPrinciple);
            }
            System.out.println("Class Name entered: " + className);
            dialog.close();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return saveButton;
    }

    private Button createTeacherSaveButton(Dialog dialog) {
        Button saveButton = new Button("Save", event -> {
            String TeachersName = TeacherNameField.getValue();
            Teacher t = teacherServies.findByName(TeachersName);
            if (t == null) {
                Notification.show("Teacher Does Not Exsist ! (");
            } else {
                School newSchool = schoolServies.findByName(ThisPrinciple.getSchool().getName());
                newSchool.addTeacher(t);
                // Update The School
                schoolServies.updateSchool(newSchool);
                ThisPrinciple.getSchool().addTeacher(t);
                // Update The Principle
                Principalservies.UpdatePrinciple(ThisPrinciple);
            }
            System.out.println("School Name entered: " + TeachersName);
            dialog.close();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return saveButton;
    }

    public void PreformEvaluation()
    {
        School testSchool = ThisPrinciple.getSchool();
        testSchool.printEvaluation("יג");
        System.out.println();
        testSchool.printEvaluation("יד");
        
    }

    public void PerformGeneticAlgorithm() {
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
        School testSchool = ThisPrinciple.getSchool();
        testSchool.adjustEmptySubjects();
        School ss = geneticAlgorithm.Geneticalgorithm(testSchool, "יג");
        ss = geneticAlgorithm.Geneticalgorithm(ss, "יד");
        System.out.println("============================================Eval=======================");
        ss.printEvaluation("יג");
        System.out.println();
        ss.printEvaluation("יד");
        ss.PrintTeachersWeeklyHours();
        ThisPrinciple.setSchool(ss);
        Principalservies.UpdatePrinciple(ThisPrinciple);
        Notification.show("Done");

    }

}

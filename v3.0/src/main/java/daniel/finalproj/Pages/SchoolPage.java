package daniel.finalproj.Pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vaadin.flow.component.AttachEvent;
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
import daniel.finalproj.Servieses.PrincipleServies.PrincipleChangeListener;

@Route("/SchoolPage")
@PageTitle("School Page")
public class SchoolPage extends VerticalLayout {
    PrincipleServies PrincipleServies;
    String PrincipleName;
    Principle ThisPrinciple;
    School school;
    String Proffesion;
    String PressedTeachersName;
    String PressedClassName;
    TextField ClassNameField;
    TextField TeacherNameField;
    Grid<String[]> ScheduleGrid;
    Grid<Teacher> TeachersGrid;
    Grid<SchoolClass> ClassesGrid;

    VerticalLayout ScheduleLayout = new VerticalLayout();

    public SchoolPage(PrincipleServies ps) {
        this.PrincipleServies = ps;
        PrincipleName = (String) VaadinSession.getCurrent().getSession().getAttribute("RealName");
        Proffesion = (String) VaadinSession.getCurrent().getSession().getAttribute("proffesion");
        ThisPrinciple = PrincipleServies.findbyname(PrincipleName);
        System.out.println(ThisPrinciple.getName());
        if (ThisPrinciple.getSchool() == null) {
            routeToPageByProffetion("CreateSchool");
        }
        school = ThisPrinciple.getSchool();
        H1 heather = new H1("Welcome to Schoool : " + school.getSchoolName());
        if (!Proffesion.equals("Principle")) {
            routeToPageByProffetion("/");
        } else {
            VerticalLayout vl = createTeacherLayout(school);
            VerticalLayout vl2 = createclassLayout(school);
            VerticalLayout vl3 = CreateScheduleLayout(school);
            add(heather, vl, vl2, vl3);

        }
        ps.AddPrincipleChangeListener(new PrincipleChangeListener() {
            @Override
            public void onChange() {
                UI ui = getUI().orElseThrow();
                ui.access(() -> SetScheduleGridItems(school));
                ui.access(() -> SetTeachersGridItems(school));
                ui.access(() -> SetClassesGridItems(school));
            }

        });

    }

    public void PreformEval(School school) {
        school.printEvaluation();
    }

    public void PreformGeneticAlgorithm(School school) {
        UI ui = UI.getCurrent();
        school.initializeBreakFast();
        school.adjustEmptySubjects();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                GeneticAlgorithm GA = new GeneticAlgorithm();
                School updatedSchool = GA.Geneticalgorithm(school);

                // Update UI with the new schedule after genetic algorithm completes
                ui.access(() -> {
                    ThisPrinciple.setSchool(updatedSchool);
                    PrincipleServies.UpdatePrinciple(ThisPrinciple);
                    SetScheduleGridItems(updatedSchool);
                    Notification.show("Genetic algorithm completed!");
                });
            }
        });

        thread.start(); // Start the thread
    }

    public VerticalLayout createTeacherLayout(School ThisSchool) {
        VerticalLayout v1 = new VerticalLayout();
        HorizontalLayout buttons = new HorizontalLayout();
        Button editTeacherButton = new Button("Enter Teacher Page", e -> EnterTeacherspage());
        //TODO Finish ^
        Button RemoveTeacherButton = new Button("Remove Teacher ", e -> removeTeacher());
        Button addTeacherButton;
        // TODO add a dialog that create a teacher that exists in the system and adds
        Dialog dialog = new Dialog("Create A New Teacher");


        VerticalLayout dialogLayout = createAddTeacherDialogLayout();
        dialog.add(dialogLayout);

        Button saveButton = createTeacherSaveButton(dialog);
        Button cancelButton = new Button("Cancel", e -> dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        dialog.getHeader().add(cancelButton);
        dialog.getFooter().add(saveButton);

        addTeacherButton = new Button("Add Teacher", e -> dialog.open());

        buttons.add(addTeacherButton, RemoveTeacherButton, editTeacherButton);
        TeachersGrid = new Grid<>(Teacher.class, false);
        TeachersGrid.addColumn(Teacher::getName).setHeader("name");
        TeachersGrid.addColumn(teacher -> getSubjectNames(teacher.getSubjects())).setHeader("Subjects");
        TeachersGrid.addItemClickListener(event -> TeacherPressed(event.getItem()));
        List<Teacher> Teachers = ThisSchool.getTeachers();

        H3 h = new H3("Teachers");
        TeachersGrid.setItems(Teachers);
        v1.add(h, buttons, TeachersGrid);

        return v1;
    }

    public void EnterTeacherspage() {
        VaadinSession.getCurrent().getSession().setAttribute("TeachersName", PressedTeachersName);
        routeToPage("TeacherPage");
    }

    public void removeTeacher() {
        String TeachersName = PressedTeachersName;
        System.out.println("Teachers Name : " + TeachersName);
        if (TeachersName == null) {
            Notification.show("Please Press On A Teacher Before Pressing The Remove Button");
        } else {
            Teacher t = school.getTeacherByName(TeachersName);
           for (int ClassIndex = 0; ClassIndex < school.getClasses().size(); ClassIndex++) {
                school.getClasses().get(ClassIndex).getTeachers().remove(school.getClasses().get(ClassIndex).getteacherIndex(TeachersName));
                for (int SubjectIndex = 0; SubjectIndex < school.getClasses().get(ClassIndex).getSubjects().size(); SubjectIndex++) {
                    
                }
           }

        }

    }

    public VerticalLayout createAddTeacherDialogLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setPadding(true);
        layout.setSpacing(true);

        // Initialize schoolNameField
        ClassNameField = new TextField("Teacher's Name");
        layout.add(ClassNameField);
        return layout;
    }

    public Button createTeacherSaveButton(Dialog dialog) {
        Button saveButton = new Button("Save", event -> {
            String TeachersName = TeacherNameField.getValue();
            int num = school.getTeacherIndex(TeachersName);
            if (num != -1) {
                Notification.show("A Teacher With that name already Exists");
            } else {
                School newSchool = new School(school);
                Teacher t  = new Teacher();
                t.setName(TeachersName);
                newSchool.addTeacher(t);
                // Update The School
                ThisPrinciple.setSchool(newSchool);
                // Update The Principle
                PrincipleServies.UpdatePrinciple(ThisPrinciple);
            }
            Notification.show("Added Teacher");
            dialog.close();
            SetClassesGridItems(ThisPrinciple.getSchool());
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return saveButton;
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

    private void routeToPageByProffetion(String proffesion) {
        if (proffesion.equals("Teacher"))
            UI.getCurrent().getPage().setLocation("/Principle");
        else if (proffesion.equals("Principle"))
            UI.getCurrent().getPage().setLocation("/Principle");
        else
            UI.getCurrent().getPage().setLocation("/SignUp");

    }
     private void routeToPage(String proffesion) {
        UI.getCurrent().getPage().setLocation("/" + proffesion);
    }

    public VerticalLayout createclassLayout(School ThisSchool) {
        VerticalLayout v1 = new VerticalLayout();
        HorizontalLayout buttons = new HorizontalLayout();
        Button RemoveTeacherButton = new Button("Remove class ", e -> removeSchoolClass());
        Button addClassButton;
        Button EnterClassButton = new Button("Enter Class", e -> enterClass());
        // TODO add a dialog that create a School that exists in the system and adds
        Dialog dialog1 = new Dialog("Create A New Class");
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
        ClassesGrid = new Grid<>(SchoolClass.class, false);
        ClassesGrid.addColumn(SchoolClass::getClassName).setHeader("name");
        List<SchoolClass> SchoolClasses = ThisSchool.getClasses();

        H3 h = new H3("classes");
        ClassesGrid.setItems(SchoolClasses);
        ClassesGrid.addItemClickListener(event -> classPressed(event.getItem()));
        v1.add(h, buttons, ClassesGrid);

        return v1;
    }

    public void SetScheduleGridItems(School school) {
        String[][] scheduleStrings = school.getSchedule().getScheduleStrings();
        ScheduleGrid.setItems(Arrays.asList(scheduleStrings).subList(1, scheduleStrings.length)); // Skip the header row
        ScheduleGrid.removeAllColumns();
        // Set headers from the first row (which we skipped for items)
        String[] headers = scheduleStrings[0];
        for (int i = 1; i < headers.length; i++) { // Start from 1 to skip the "Hour\\Day" cell
            final int colIndex = i;
            ScheduleGrid.addColumn(rw -> rw[colIndex]).setHeader(headers[colIndex]);
        }
    }

    public void SetTeachersGridItems(School school) {
        TeachersGrid.removeAllColumns();
        TeachersGrid.addColumn(Teacher::getName).setHeader("name");
        TeachersGrid.addColumn(teacher -> getSubjectNames(teacher.getSubjects())).setHeader("Subjects");
        TeachersGrid.addItemClickListener(event -> TeacherPressed(event.getItem()));
        List<Teacher> Teachers = school.getTeachers();

        TeachersGrid.setItems(Teachers);
    }

    public void SetClassesGridItems(School school) {
        // ClassesGrid.removeAllColumns();
        // ClassesGrid.addColumn(SchoolClass::getClassName).setHeader("name");
        List<SchoolClass> SchoolClasses = school.getClasses();
        ClassesGrid.setItems(SchoolClasses);
        ClassesGrid.addItemClickListener(event -> classPressed(event.getItem()));
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
        ScheduleLayout = new VerticalLayout();
        Button b = new Button("Genetic Algorithm", e -> PreformGeneticAlgorithm(school));
        Button eval = new Button("Eval", e -> PreformEval(school));
        ScheduleLayout.add(ScheduleGrid, b, eval);

        return ScheduleLayout;
    }

    public void removeSchoolClass() {
        String ClassName = PressedClassName;
        System.out.println("ClassName : " + ClassName);
        school = ThisPrinciple.getSchool();
        if (ClassName == null) {
            Notification.show("to Remove a Class Press On A Class Before Pressing The Remove Button");
        } else {
            school.getClasses().remove(school.getClassIndexByName(ClassName));
            ThisPrinciple.setSchool(school);
            PrincipleServies.UpdatePrinciple(ThisPrinciple);
            SetClassesGridItems(ThisPrinciple.getSchool());
        }

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
                PrincipleServies.UpdatePrinciple(ThisPrinciple);
            }
            Notification.show("Added Class");
            dialog.close();
            SetClassesGridItems(ThisPrinciple.getSchool());
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return saveButton;
    }

    public void classPressed(SchoolClass sc) {
        PressedClassName = sc.getClassName();
    }
}

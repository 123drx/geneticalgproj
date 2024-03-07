package daniel.proj.Pages;

import java.security.Principal;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import daniel.proj.Servieses.PrincipleServies;
import daniel.proj.objects.Lesson;
import daniel.proj.objects.Principle;
import daniel.proj.objects.Schedule;
import daniel.proj.objects.School;
import daniel.proj.objects.SchoolClass;
import daniel.proj.objects.Subject;
import daniel.proj.objects.Teacher;

@Route("/ClassPage")
public class ClassPage extends VerticalLayout {
    PrincipleServies ps;
    String realname;
    String ClassName;
    Principle ThisPrinciple;
    SchoolClass This;

    public ClassPage(PrincipleServies p) {
        this.ps = p;
        realname = (String) VaadinSession.getCurrent().getSession().getAttribute("RealName");
        ClassName = (String) VaadinSession.getCurrent().getSession().getAttribute("ClassName");
        ThisPrinciple = ps.findbyname(realname);
        This = ThisPrinciple.getSchool().getClass(ClassName);

        SchoolClass This = ThisPrinciple.getSchool().getClasses()
                .get(ThisPrinciple.getSchool().getClassIndex(ClassName));
        H1 Title = new H1("Class : " + This.getClassName());
        VerticalLayout l = createSubjectLayout();
        VerticalLayout ll = createTeachersLayout();
        VerticalLayout lll = createLockedLessonLayout();
         VerticalLayout llll = createScheduleLayout();

        add(Title, l, ll, lll,llll);

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
        grid.addColumn(Subject::getSubjectName).setHeader("name");
        grid.addColumn(Subject::getTeacherName).setHeader("Teacher");
        grid.addColumn(Subject::getWeaklyHours).setHeader("Weekly Hours");
        grid.addColumn(Subject::getPriority).setHeader("Priority");
        grid.addItemClickListener(event -> SubjctPressed(event.getItem()));

        List<Subject> Subjects = This.getSubjects();

        H3 h = new H3("Sybjects");
        grid.setItems(Subjects);
        v1.add(h, buttons, grid);

        return v1;
    }

    public VerticalLayout createTeachersLayout() {
        VerticalLayout v1 = new VerticalLayout();
        HorizontalLayout buttons = new HorizontalLayout();

        Grid<String> grid = new Grid<>(String.class, false);
        grid.addColumn(str -> str).setHeader("Name");
        grid.addItemClickListener(event -> TeacherPressed(event.getItem()));
        List<String> Teachers = This.getTeachers();

        H3 h = new H3("Teachers");
        grid.setItems(Teachers);
        v1.add(h, buttons, grid);


        return v1;
    }

    public VerticalLayout createLockedLessonLayout() {
        VerticalLayout v1 = new VerticalLayout();
        HorizontalLayout buttons = new HorizontalLayout();
        Button editLesson = new Button("Edit", e -> editsubject());
        Button RemoveLesson = new Button("Remove Lesson ", e -> removeSubject());
        Button addLessonButton;
        // TODO add a dialog that create a teacher that exists in the system and adds
        Dialog dialog = new Dialog("Create A New Lesson");

        VerticalLayout dialogLayout = createAddSubejctDialogLayout();
        dialog.add(dialogLayout);

        Button saveButton = createSubjectSaveButton(dialog);
        Button cancelButton = new Button("Cancel", e -> dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        dialog.getHeader().add(cancelButton);
        dialog.getFooter().add(saveButton);

        addLessonButton = new Button("Add Lesson", e -> dialog.open());

        buttons.add(addLessonButton, RemoveLesson, editLesson);
        Grid<Lesson> grid = new Grid<>(Lesson.class, false);
        grid.addColumn(Lesson::getLessonSubject).setHeader("Lesson Name");
        grid.addColumn(Lesson::getTeacher).setHeader("Teacher");
        grid.addColumn(Lesson::getDay).setHeader("day");
        grid.addColumn(Lesson::getStartHour).setHeader("Huor");
        grid.addItemClickListener(event -> LessonPressed(event.getItem()));

        List<Lesson> lessons = This.getLockedLessons();
      
        H3 h = new H3("Locked Lessons");
        grid.setItems(lessons);
        v1.add(h, buttons, grid);

        return v1;
    }

    
public VerticalLayout createScheduleLayout() {
    VerticalLayout layout = new VerticalLayout();
    HorizontalLayout buttons = new HorizontalLayout();

    // Create buttons and dialog
    Button editLesson = new Button("Edit", e -> editsubject());
    Button removeLesson = new Button("Remove Lesson", e -> removeSubject());
    Dialog dialog = new Dialog("Create A New Lesson");
    VerticalLayout dialogLayout = createAddSubejctDialogLayout();
    dialog.add(dialogLayout);
    Button saveButton = createSubjectSaveButton(dialog);
    Button cancelButton = new Button("Cancel", e -> dialog.close());
    cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
    dialog.getHeader().add(cancelButton);
    dialog.getFooter().add(saveButton);

    // Create add lesson button
    Button addLessonButton = new Button("Add Lesson", e -> dialog.open());

    buttons.add(addLessonButton, removeLesson, editLesson);

    // Create and configure the grid
    String[][] data;
    data = This.getSchedule().getScheduleasarr();
    Grid<String[]> grid = new Grid<>();
    for (int i = 0; i < data[0].length; i++) {
        int columnIndex = i;
        if(i>0)
        grid.addColumn(row -> row[columnIndex]).setHeader((columnIndex-1 + Schedule.StartingHour)+":00");
        else
        grid.addColumn(row -> row[columnIndex]).setHeader(("Day\\Hour"));;
    }

    // Set the items of the grid to the 2D array
    grid.setItems(data);

    // Header
    H3 h = new H3("Locked Lessons");

    // Add components to layout
    layout.add(h, buttons, grid);

    return layout;
}

    public void editsubject() {

    }

    public void removeSubject() {

    }

    public VerticalLayout createAddSubejctDialogLayout() {
        VerticalLayout l = new VerticalLayout();

        return l;
    }

    public Button createSubjectSaveButton(Dialog d) {
        Button n = new Button();

        return n;
    }

    public void SubjctPressed(Subject s) {

    }

    public void TeacherPressed(String s) {

    }

    public void LessonPressed(Lesson s) {

    }

}

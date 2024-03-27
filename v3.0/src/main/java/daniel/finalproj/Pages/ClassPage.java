package daniel.finalproj.Pages;

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
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import daniel.finalproj.Objects.Day;
import daniel.finalproj.Objects.Lesson;
import daniel.finalproj.Objects.Principle;
import daniel.finalproj.Objects.Schedule;
import daniel.finalproj.Objects.School;
import daniel.finalproj.Objects.SchoolClass;
import daniel.finalproj.Objects.Subject;
import daniel.finalproj.Servieses.PrincipleServies;
@Route("/ClassPage")
@PageTitle("ClassPage")
public class ClassPage extends VerticalLayout{
    PrincipleServies ps;
    String realname;
    String ClassName;
    School ThisSchool;
    Principle ThisPrinciple;
    String Proffesion;
    SchoolClass This;
    TextField SubjectNameField;
    TextField subjectWeeklyHoursField;
    TextField SubjectTeacherNameField;
    TextField SubjectPriorityField;
    TextField LockedLessonName;
    TextField LockedLessonTeacher;
    TextField LockedLessonday;
    TextField LockedLessonhour;
    int ClassIndex;



    public ClassPage(PrincipleServies p)
    {
        this.ps = p;
        realname = (String) VaadinSession.getCurrent().getSession().getAttribute("RealName");
        Proffesion = (String) VaadinSession.getCurrent().getSession().getAttribute("proffesion");
        ClassName = (String) VaadinSession.getCurrent().getSession().getAttribute("ClassName");
        ThisPrinciple = ps.findbyname(realname);
        ThisSchool = ThisPrinciple.getSchool();
        ClassIndex = ThisSchool.getClassIndexByName(ClassName);
        This = ThisSchool.getClasses().get(ThisSchool.getClassIndexByName(ClassName));
        if(!Proffesion.equals("Principle"))
        {
            routeToPage("/");
        }
        else
        {
            H1 heather = new H1("Welcome To Class : "+This.getClassName());
            VerticalLayout vl = createSubjectLayout();
            VerticalLayout vl2 = createTeachersLayout();
            VerticalLayout vl3 = createLockedLessonLayout();
            add(heather,vl,vl2,vl3);
        }

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
        grid.addColumn(Subject::getTeachersName).setHeader("Teacher");
        grid.addColumn(Subject::getWeeklyHours).setHeader("Weekly Hours");
        grid.addColumn(Subject::getPriority).setHeader("Priority");
        grid.addItemClickListener(event -> SubjctPressed(event.getItem()));

        List<Subject> Subjects = This.getSubjects();

        H3 h = new H3("Sybjects");
        grid.setItems(Subjects);
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

        VerticalLayout dialogLayout = createLockedLessonDialogLayout();
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
        grid.addColumn(Lesson::getUsableHour).setHeader("Hour");
        grid.addItemClickListener(event -> LessonPressed(event.getItem()));

        List<Lesson> lessons = This.getLockedLessons();
      
        H3 h = new H3("Locked Lessons");
        grid.setItems(lessons);
        v1.add(h, buttons, grid);

        return v1;
    }

    public void LessonPressed(Lesson leeson)
    {

    }

    public VerticalLayout createLockedLessonDialogLayout()
    {
        VerticalLayout vl = new VerticalLayout();
        TextField LockedLessonName = new TextField("LockedLessonName");
        TextField LockedLessonTeacher = new TextField("LockedLessonTeacher");
        TextField LockedLessonday = new TextField("LockedLessonday");
        TextField LockedLessonhour = new TextField("LockedLessonhour");
        vl.add(LockedLessonName,LockedLessonTeacher,LockedLessonday,LockedLessonhour);
        return vl;
    }

    public Button createLessonSaveButton(Dialog d)
    {
        Button saveButton = new Button("Save", event -> { 
            String name = LockedLessonName.getValue();
            String Teacher = LockedLessonTeacher.getValue();
            int day = Integer.parseInt(LockedLessonday.getValue());
            int hour = Integer.parseInt(LockedLessonhour.getValue());
            int num = This.getLockedLesson(name);
            if (num != -1) {
                Notification.show("A Lesson With that name already Locked");
            } else {
                
                    Lesson lesson = new Lesson();
                    lesson.setDay(day);
                    lesson.setClassName(ClassName);
                    lesson.setHour(hour);
                    lesson.setLessonSubject(name);
                    lesson.setTeacher(Teacher);
                    ThisPrinciple.getSchool().getClasses().get(ClassIndex).addLockedLesson(lesson);
                    ps.UpdatePrinciple(ThisPrinciple);
                
            }
            System.out.println("Class Name entered: " + name);
            d.close();
    });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return saveButton;

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
    public void TeacherPressed(String s)
    {

    }

    public void editsubject()
    {

    }
    public void removeSubject()
    {

    }
    public VerticalLayout createAddSubejctDialogLayout()
    {
          VerticalLayout layout = new VerticalLayout();
        layout.setPadding(true);
        layout.setSpacing(true);

        // Initialize schoolNameField
        SubjectNameField = new TextField("SubjectName");
        subjectWeeklyHoursField= new TextField("SubjectWeeklyHours");
        SubjectTeacherNameField= new TextField("SubjecTeacherName");
        SubjectPriorityField= new TextField("SubjectPriority");
        layout.add(SubjectNameField,subjectWeeklyHoursField,SubjectTeacherNameField,SubjectPriorityField);

        return layout;
    }
    public Button createSubjectSaveButton(Dialog d)
    {
        Button saveButton = new Button("Save", event -> { 
            String subname = SubjectNameField.getValue();
            int Weekly = Integer.parseInt(subjectWeeklyHoursField.getValue());
            String Teacher = SubjectTeacherNameField.getValue();
            int Priority = Integer.parseInt(SubjectPriorityField.getValue());

            int num = This.getSubjectIndex(subname,Teacher);
            if (num != -1) {
                Notification.show("A Subject With that name already Exists");
            } else {
                int check = ThisPrinciple.getSchool().getClasses().get(ClassIndex).getSubjectIndex(subname,Teacher);
                if(check==-1)
                {
                    Subject sub = new Subject(subname);
                    sub.setPriority(Priority);
                    sub.setTeachersName(Teacher);
                    sub.setWeeklyHours(Weekly);

                    ThisPrinciple.getSchool().getClasses().get(ClassIndex).addSubject(sub);
                    ps.UpdatePrinciple(ThisPrinciple);
                }
            }
            System.out.println("Class Name entered: " + subname);
            d.close();
    });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return saveButton;

    }
    public void SubjctPressed(Subject sub)
    {

    }

      private void routeToPage(String proffesion) {
    if (proffesion.equals("Principle"))
         UI.getCurrent().getPage().setLocation("/Principle");
      else
         UI.getCurrent().getPage().setLocation("/SignUp");

   }
}

package daniel.proj.Pages;

import java.util.List;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import daniel.proj.objects.School;
import daniel.proj.objects.Subject;
import daniel.proj.objects.Teacher;

@Route("/TeacherPage")

public class TeacherView extends VerticalLayout {
    public TeacherView(School sc, String ClassName) {
        H1 Heather = new H1("Teachers in School " + sc.getName() + " In Class : " + ClassName);
        Grid<Teacher> mainGrid = new Grid<>(Teacher.class, false);
        mainGrid.addColumn(Teacher::getName).setHeader("Teacher Name");

        mainGrid.addComponentColumn(teacher -> {
            // Create a vertical layout to hold the teacher name and subject grid
            VerticalLayout layout = new VerticalLayout();
            layout.setPadding(false);
            layout.setSpacing(false);

            // Add teacher name to the layout
            layout.add(new Text(teacher.getName()));

            // Create a grid for subjects
            Grid<Subject> subjectGrid = new Grid<>(Subject.class, false);
            subjectGrid.setItems(teacher.getSubjects());
            subjectGrid.removeAllColumns();
            subjectGrid.addColumn(Subject::getSubjectName).setHeader("Subject");
            subjectGrid.addColumn(Subject::getWeaklyHours).setHeader("Weekly Hours");

            // Add subject grid to the layout
            layout.add(subjectGrid);

            return layout;
        }).setHeader("Subjects");

        List<Teacher> teachers = sc.getTeachers(sc.getClass(ClassName).getTeachers());
        mainGrid.setItems(teachers);
        add(Heather, mainGrid);

    }

}

package daniel.proj.Pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import daniel.proj.Algorithms.GeneticAlgorithm;
import daniel.proj.Servieses.PrincipleServies;
import daniel.proj.Servieses.UserServies;
import daniel.proj.objects.Constrains;
import daniel.proj.objects.Lesson;
import daniel.proj.objects.Principle;
import daniel.proj.objects.Schedule;
import daniel.proj.objects.School;
import daniel.proj.objects.SchoolClass;
import daniel.proj.objects.Teacher;
import daniel.proj.objects.User;
import daniel.proj.objects.Subject;

@Route("/page")
@PageTitle("TestPage")
public class Page extends VerticalLayout {
        PrincipleServies Ps;
    public Page(PrincipleServies PrinceServies) {
        Ps = PrinceServies;
        Button btn = new Button("print empty schedule");
        btn.addClickListener(event -> printTeacherSubjects());
        Button btn1 = new Button("test crossover()");
        btn1.addClickListener(event -> PreformCrossOver());
        Button btn2 = new Button("print tournamentSelection");
        btn2.addClickListener(event -> PreformtournamentSelection("יא"));
        Button btn3 = new Button("Prefotm mutate");
        btn3.addClickListener(event -> PerformMutate());
        Button btn4 = new Button("print Genetic Algo");
        btn4.addClickListener(event -> PerformGeneticAlgorithm());
        Button btn5 = new Button("Preform Avaliability");
        btn5.addClickListener(event -> PreformAvaliability());
        Button btn6 = new Button("prefome soms");
        btn6.addClickListener(e -> preformEmptys());
        Button btn7 = new Button("PrintSchool");
        btn7.addClickListener(e -> PrintSchool());

        // Button btn5 = new Button("CheckEvaluate");
        // btn5.addClickListener(event -> CheckEvaluate());
        // Button btn6 = new Button("ChecckgeneticAlgo");
        // btn6.addClickListener(event -> ChecckgeneticAlgo());
        // add list of theacher with prefereces and enable next lines

        // Button btn4 = new Button("Print GeneticAlgorithmSolution");
        // btn4.addClickListener(event -> GeneticAlgorithm());
        H1 s = new H1("Test Page");
        add(s, btn, btn1, btn2, btn3, btn4, btn5, btn6,btn7);
        // add(s, btn, btn1, btn2, btn3, btn4,btn5,btn6);

    }

    public void preformEmptys() {
        School sc = CreateSchool();
        sc.adjustEmptySubjects();
        sc.printweeklyhours();
    }

    public void PerformMutate() {
        School school = new School();
        school = CreateSchool();
        school.printSChool();
        System.out.println("Schedule : (Before the Mutation)");
        school.getClasses().get(0).getSchedule().FillThisScheduleWithLessons(school.getClasses().get(0).getSubjects());
        school.getClasses().get(0).getSchedule().printSchedule();
        Schedule s = new Schedule();
        s = school.getClasses().get(0).getSchedule().Bigmutate(school.getClasses().get(0).getSubjects());
        System.out.println("Schedule : (after the Mutation)");
        school.getClasses().get(0).setSchedule(s);
        school.getClasses().get(0).getSchedule().printSchedule();

    }

    public void PreformAvaliability() {
        School s = CreateSchool();
        s.Avaliability();
        s.AvaliabilityNumbers();
    }

    public void PrintEmptySchool() {
        School school = new School();
        SchoolClass sc1 = new SchoolClass("יא");
        SchoolClass sc2 = new SchoolClass("יב");
        school.addclass(sc1);
        school.addclass(sc2);
        for (SchoolClass s : school.getClasses()) {
            s.initSchedule();
        }
        school.printSChool();
    }
    public void PrintSchool() {
        School school = CreateSchool();
        school.printSChool();
    }

    public void PreformtournamentSelection(String ClassName) {
        List<School> schools = new ArrayList<>();
        List<Integer> Evals = new ArrayList<>();
        School school = CreateSchool();
        for (SchoolClass cs : school.getClasses()) {
            cs.getSchedule().FillThisScheduleWithLessons(cs.getSubjects());
        }
        School school2 = CreateSchool();
        for (SchoolClass cs : school2.getClasses()) {
            cs.getSchedule().FillThisScheduleWithLessons(cs.getSubjects());
        }
        School school3 = CreateSchool();
        for (SchoolClass cs : school3.getClasses()) {
            cs.getSchedule().FillThisScheduleWithLessons(cs.getSubjects());
        }
        schools.add(school);
        schools.add(school2);
        schools.add(school3);
        Evals.add(school.evaluateSchoolClass(ClassName));
        Evals.add(school2.evaluateSchoolClass(ClassName));
        Evals.add(school3.evaluateSchoolClass(ClassName));
        List<Integer> SortedIndexes = new ArrayList<>();
        getSortedIndexes(Evals);
        School schl = school3.tournamentSelection(schools, Evals, SortedIndexes);
        int i = 0;
        for (School s : schools) {
            System.out.println("===========================================school " + (i + 1)
                    + "================================================");
            s.printSChool();
            i++;
        }

        for (int unt : Evals) {
            System.out.println("Eval :" + unt);
        }
        System.out.println(
                "=================================returned : ============================================================");
        schl.printSChool();

    }

    public void PreformCrossOver() {
        School school = CreateSchool();
        for (SchoolClass cs : school.getClasses()) {
            cs.getSchedule().FillThisScheduleWithLessons(cs.getSubjects());
        }
        School school2 = CreateSchool();
        for (SchoolClass cs : school2.getClasses()) {
            cs.getSchedule().FillThisScheduleWithLessons(cs.getSubjects());
        }
        school.crossover(school2, school);
        System.out
                .println("=====================================returned=============================================");
        school.printSChool();
    }

    private static List<Integer> getSortedIndexes(List<Integer> values) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            indices.add(i);
        }

        // Sort indices based on corresponding values
        Collections.sort(indices, Comparator.comparing(values::get).reversed());

        return indices;
    }

    public void printTeacherSubjects()
    {
        School Created = CreateSchool();
        Created.printSChool();
        Created.PrintTeachersWeeklyHours();

        
    }

    private void PerformGeneticAlgorithm() {
        School testSchool = CreateSchool();
        testSchool.adjustEmptySubjects();
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
        School s = geneticAlgorithm.Geneticalgorithm(testSchool, "יג");
        s = geneticAlgorithm.Geneticalgorithm(s, "יד");
        System.out.println("============================================Eval=======================");
        s.printEvaluation("יג");
        System.out.println();
        s.printEvaluation("יד");
        s.PrintTeachersWeeklyHours();

    }

    public School CreateSchool() {
        Principle p = new Principle();
        School Created = new School("New School");
        SchoolClass class1 = new SchoolClass("יג");
        SchoolClass class2 = new SchoolClass("יד");
        Created.addclass(class1);
        Created.addclass(class2);

        Set<String> classes = new HashSet<>();
        classes.add("יג");
        classes.add("יד");

        Teacher Guy = new Teacher("Guy");
        Constrains[] GuysConsts = new Constrains[Schedule.MaxDays];
        GuysConsts[0] = new Constrains(8, 14);
        GuysConsts[4] = new Constrains(12, 16);
        Guy.setConstrains(GuysConsts);
        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject("1machine learning",Guy.getName(), classes));
        subjects.add(new Subject("1python",Guy.getName(), classes));
        subjects.add(new Subject("1operating systems",Guy.getName(), classes));
        Guy.setSubjects(subjects);
        Created.addTeacher(Guy);

        Teacher kodesh = new Teacher("Harab le Kodesh");
        Constrains[] KodeshConsts = new Constrains[Schedule.MaxDays];
        KodeshConsts[3] = new Constrains(12, 14);
        kodesh.setConstrains(KodeshConsts);
        List<Subject> Kodeshsubjects = new ArrayList<>();
        Kodeshsubjects.add(new Subject("2Kodesh",kodesh.getName(), classes));
        kodesh.setSubjects(Kodeshsubjects);
        Created.addTeacher(kodesh);

        Teacher ilan = new Teacher("ilan");
        Constrains[] ilanConsts = new Constrains[Schedule.MaxDays];
        ilanConsts[0] = new Constrains(8, 16);
        ilanConsts[3] = new Constrains(8, 8);
        ilanConsts[1] = new Constrains(8, 14);
        ilanConsts[4] = new Constrains(8, 10);
        ilan.setConstrains(ilanConsts);
        List<Subject> ilansubjects = new ArrayList<>();
        ilansubjects.add(new Subject("3java",ilan.getName(), classes));
        ilansubjects.add(new Subject("3project",ilan.getName(), classes));
        ilansubjects.add(new Subject("3Asmbly",ilan.getName(), classes));
        ilan.setSubjects(ilansubjects);
        Created.addTeacher(ilan);

        Teacher trabelsy = new Teacher("trabelsy");
        Constrains[] trabelsyConsts = new Constrains[Schedule.MaxDays];
        trabelsyConsts[1] = new Constrains(8, 16);
        trabelsyConsts[2] = new Constrains(14, 16);
        trabelsyConsts[3] = new Constrains(14, 16);
        trabelsyConsts[4] = new Constrains(8, 14);
        trabelsy.setConstrains(trabelsyConsts);
        List<Subject> trabelsysubjects = new ArrayList<>();
        trabelsysubjects.add(new Subject("4statistics",trabelsy.getName(), classes));
        trabelsysubjects.add(new Subject("4linear",trabelsy.getName(), classes));
        trabelsy.setSubjects(trabelsysubjects);
        Created.addTeacher(trabelsy);

        Teacher mergi = new Teacher("Mergi");
        Constrains[] mergiConsts = new Constrains[Schedule.MaxDays];
        mergiConsts[0] = new Constrains(15, 16);
        mergiConsts[2] = new Constrains(10, 13);
        mergiConsts[3] = new Constrains(8, 9);
        mergi.setConstrains(mergiConsts);
        List<Subject> mergisubjects = new ArrayList<>();
        mergisubjects.add(new Subject("5nets",mergi.getName(), classes));
        mergi.setSubjects(mergisubjects);
        Created.addTeacher(mergi);

        Teacher Yaniv = new Teacher("Yaniv");
        Constrains[] YanivConsts = new Constrains[Schedule.MaxDays];
        YanivConsts[2] = new Constrains(8, 16);
        YanivConsts[3] = new Constrains(8, 13);
        Yaniv.setConstrains(YanivConsts);
        List<Subject> Yanivsubjects = new ArrayList<>();
        Yanivsubjects.add(new Subject("6Python",Yaniv.getName(), classes));
        Yanivsubjects.add(new Subject("6hystory",Yaniv.getName(), classes));
        Yanivsubjects.add(new Subject("6halaha",Yaniv.getName(), classes));
        Yaniv.setSubjects(Yanivsubjects);
        Created.addTeacher(Yaniv);

        Teacher Amsalem = new Teacher("Amsalem");
        Constrains[] AmsalemConsts = new Constrains[Schedule.MaxDays];
        AmsalemConsts[1] = new Constrains(15, 16);
        Amsalem.setConstrains(AmsalemConsts);
        List<Subject> Amsalemsubjects = new ArrayList<>();
        Amsalemsubjects.add(new Subject("7computers",Amsalem.getName(), classes));
        Amsalem.setSubjects(Amsalemsubjects);
        Created.addTeacher(Amsalem);

        Teacher Daniel = new Teacher("Daniel");
        Constrains[] DanielConsts = new Constrains[Schedule.MaxDays];
        DanielConsts[2] = new Constrains(8, 10);
        DanielConsts[3] = new Constrains(10, 16);
        DanielConsts[4] = new Constrains(15, 16);
        Daniel.setConstrains(DanielConsts);
        List<Subject> Danielsubjects = new ArrayList<>();
        Danielsubjects.add(new Subject("8Life",Daniel.getName(), classes));
        Daniel.setSubjects(Danielsubjects);
        Created.addTeacher(Daniel);

        Subject c0s0 = new Subject(Guy.getSubjects().get(0).getSubjectName(), Guy.getName(), 2);
        Subject c0s1 = new Subject(Guy.getSubjects().get(1).getSubjectName(), Guy.getName(), 3);
        //Subject c0s2 = new Subject(Guy.getSubjects().get(2).getSubjectName(), Guy.getName(), 3);
        Subject c0s3 = new Subject(ilan.getSubjects().get(0).getSubjectName(), ilan.getName(), 4,4);
        Subject c0s4 = new Subject(ilan.getSubjects().get(1).getSubjectName(), ilan.getName(), 6,5);
        Subject c0s5 = new Subject(trabelsy.getSubjects().get(0).getSubjectName(), trabelsy.getName(), 6);
        Subject c0s6 = new Subject(trabelsy.getSubjects().get(1).getSubjectName(), trabelsy.getName(), 2);
        Subject c0s7 = new Subject(mergi.getSubjects().get(0).getSubjectName(), mergi.getName(), 2);
        Subject c0s8 = new Subject(Daniel.getSubjects().get(0).getSubjectName(), Daniel.getName(), 2);
        Subject c0s9 = new Subject(Yaniv.getSubjects().get(1).getSubjectName(), Yaniv.getName(), 4);
        Subject c0s10 = new Subject(kodesh.getSubjects().get(0).getSubjectName(), kodesh.getName(), 2);

        Subject c1s0 = new Subject(ilan.getSubjects().get(2).getSubjectName(), ilan.getName(), 3);
        Subject c1s1 = new Subject(Guy.getSubjects().get(2).getSubjectName(), Guy.getName(), 2);
        Subject c1s2 = new Subject(Guy.getSubjects().get(0).getSubjectName(), Guy.getName(), 4);
        Subject c1s3 = new Subject(ilan.getSubjects().get(0).getSubjectName(), ilan.getName(), 5);
        Subject c1s4 = new Subject(trabelsy.getSubjects().get(1).getSubjectName(), trabelsy.getName(), 9);
        Subject c1s5 = new Subject(Amsalem.getSubjects().get(0).getSubjectName(), Amsalem.getName(), 2);
        Subject c1s6 = new Subject(Daniel.getSubjects().get(0).getSubjectName(), Daniel.getName(), 6);
        Subject c1s7 = new Subject(Yaniv.getSubjects().get(0).getSubjectName(), Yaniv.getName(), 4);
        Subject c1s8 = new Subject(Yaniv.getSubjects().get(2).getSubjectName(), Yaniv.getName(), 4);

        Created.addsubject(c0s0, class1.getClassName());
        Created.addsubject(c0s1, class1.getClassName());
       // Created.addsubject(c0s2, class1.getClassName());
        Created.addsubject(c0s3, class1.getClassName());
        Created.addsubject(c0s4, class1.getClassName());
        Created.addsubject(c0s5, class1.getClassName());
        Created.addsubject(c0s6, class1.getClassName());
        Created.addsubject(c0s7, class1.getClassName());
        Created.addsubject(c0s8, class1.getClassName());
        Created.addsubject(c0s9, class1.getClassName());
        Created.addsubject(c0s10, class1.getClassName());

        Created.addsubject(c1s0, class2.getClassName());
        Created.addsubject(c1s1, class2.getClassName());
        Created.addsubject(c1s2, class2.getClassName());
        Created.addsubject(c1s3, class2.getClassName());
        Created.addsubject(c1s4, class2.getClassName());
        Created.addsubject(c1s5, class2.getClassName());
        Created.addsubject(c1s6, class2.getClassName());
        Created.addsubject(c1s7, class2.getClassName());
        Created.addsubject(c1s8, class2.getClassName());

        Lesson l = new Lesson();
        l.setTeacher(ilan.getName());
        l.setStartHour(8);
        l.setDay(3);
        l.setLessonSubject(c0s3.getSubjectName());
        Created.getClasses().get(0).addLockedLesson(l);

        Lesson ll = new Lesson();
        ll.setTeacher(Daniel.getName());
        ll.setStartHour(8);
        ll.setDay(2);
        ll.setLessonSubject(c0s8.getSubjectName());
        Created.getClasses().get(0).addLockedLesson(ll);
        
        Created.getClasses().get(0).initSchedule();
        Created.getClasses().get(1).initSchedule();
        

        return Created;
    }
}

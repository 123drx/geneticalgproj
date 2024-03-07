package danielgras.schoolschedule.Pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Random;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import danielgras.schoolschedule.Objects.Constrains;
import danielgras.schoolschedule.Objects.Lesson;
import danielgras.schoolschedule.Objects.Schedual;
import danielgras.schoolschedule.Objects.Teacher;
import danielgras.schoolschedule.Servieces.TeacherServies;
import danielgras.schoolschedule.Algorithm.GeneticAlgorithm;

@Route("/")
@PageTitle("Test Page")
public class TestPage extends VerticalLayout {
    TeacherServies s;

    // temp
    private final Map<Integer, String> subjectsMap = new HashMap<>();
    {
        subjectsMap.put(1, "Mathematics");
        subjectsMap.put(2, "Physics");
        subjectsMap.put(3, "Chemistry");
        subjectsMap.put(4, "Biology");
        subjectsMap.put(5, "English");
        subjectsMap.put(6, "History");
        subjectsMap.put(7, "Geography");
        subjectsMap.put(8, "Computer Science");
        subjectsMap.put(9, "Physical Education");
        subjectsMap.put(0, "Art");
    }

    public TestPage() {
        Button btn = new Button("print empty schedule");
        btn.addClickListener(event -> PrintEmptySchedule());
        Button btn1 = new Button("test crossover()");
        btn1.addClickListener(event -> PreformCrossOver());
        Button btn2 = new Button("print mutate");
        btn2.addClickListener(event -> PerformMutate());
        Button btn3 = new Button("print Teachers with subjects");
        btn3.addClickListener(event -> PerformPrintLessonsfromTeachers());
        Button btn4 = new Button("Print Teacher preferces");
        btn4.addClickListener(event -> PerformPrintTeacherpreferces());
        // TODO add more checks to Evaluate
        Button btn5 = new Button("CheckEvaluate");
        btn5.addClickListener(event -> CheckEvaluate());
        // TODO add more checks to GeneticAlgorithm
        Button btn6 = new Button("ChecckgeneticAlgo");
        btn6.addClickListener(event -> ChecckgeneticAlgo());
        // add list of theacher with prefereces and enable next lines

        // Button btn4 = new Button("Print GeneticAlgorithmSolution");
        // btn4.addClickListener(event -> GeneticAlgorithm());
        H1 s = new H1("Test Page");
        add(s, btn, btn1, btn2, btn3, btn4,btn5,btn6);

    }

    public void CheckEvaluate() {

        Schedual s = new Schedual();
        s.InitSchedual();
        Teacher t1 = new Teacher("teacher");
        Teacher t2 = new Teacher("Ilan");
        Teacher t3 = new Teacher("Moshe");
        Teacher t4 = new Teacher("david");
        Teacher t5 = new Teacher("shmuel");
        Teacher t6 = new Teacher("yaniv");
        List<Teacher> tarr = new ArrayList<>();
        List<Lesson> lessons = new ArrayList<>();
         Random rand = new Random();
        int randHour, randHour2, rrand;
        tarr.add(t1);
        tarr.add(t2);
        tarr.add(t3);
        tarr.add(t4);
        tarr.add(t5);
        tarr.add(t6);

        // adds rundom subjects for the teacher to teach
        for (Teacher t : tarr) {
            int number = rand.nextInt(1,3);// number of subjects teacher teaches
            for (int i = 0; i < number; i++) {
                int randomnumber = rand.nextInt(10);
                t.addsubject(subjectsMap.get(randomnumber));
            }
        }
        // create lessons with there names
        for (Teacher t : tarr) {
            for (String sbj : t.getSubjects()) {
                Lesson l = new Lesson(sbj, t.getName(), -1, -1);
                lessons.add(l);
            }
        }

        // set random days that the teacher can work in dayCont
        // and if so then create hour constrains
        for (Teacher t : tarr) {
            boolean[] dayconst = new boolean[Schedual.MaxDays];
            boolean[][] hourPrefrences = new boolean[Schedual.MaxDays][Schedual.MaxHours];       
            for (int i = 0; i < Schedual.MaxDays; i++) {
                rrand = rand.nextInt(0, 2);
                // 1 means he can work that day
                if (rrand == 0) {
                    // set true on that day
                    dayconst[i] = true;
                    // 2 random hours 1 for starting that day and one for ending
                    randHour = rand.nextInt(0, (Schedual.MaxHours / 2) - 1);
                    randHour2 = rand.nextInt((Schedual.MaxHours / 2) + 1, Schedual.MaxHours);
                    for (int j = 0; j < Schedual.MaxHours; j++) {
                        if (j > randHour && j < randHour2) {
                            hourPrefrences[i][j] = true;
                        } else {
                            hourPrefrences[i][j] = false;
                        }

                    }
                } else {
                    dayconst[i] = false;
                    for (int j = 0; j < Schedual.MaxHours; j++) {
                        hourPrefrences[i][j] = false;
                    }
                }

            }

            t.setConstrains(hourPrefrences);
            t.SetDayConstrains(dayconst);
        }

        //builds a schedule with all the lesson we created
        s.FillScheduleWithLessons(lessons);

        //print all teachers
        for(Teacher t : tarr)
        {
            System.out.println("=========================="+"Name :"+t.getName()+"=====================");
            System.out.println();
            System.out.println("=========================="+"Constrains"+"=====================");
            t.printconstrains();
            System.out.println("=========================="+"Subjects"+"=====================");
            t.printSubjects();
            System.out.println("=========================="+"End print"+"=====================");
        } 
        System.out.println("=========================="+"schedule"+"=====================");
        s.printSchedule();
        GeneticAlgorithm g  = new GeneticAlgorithm();
        int eval = g.evaluateSchedule(s,tarr);
        System.out.println("=========================="+"Eval"+"=====================");
        System.out.println(eval);
        System.out.println("=========================="+"End"+"=====================");

        

    }

    /* 
    public void ChecckgeneticAlgo() {
        Schedual s = new Schedual();
        s.InitSchedual();
        Teacher t1 = new Teacher("teacher");
        Teacher t2 = new Teacher("Ilan");
        Teacher t3 = new Teacher("Moshe");
        Teacher t4 = new Teacher("david");
        Teacher t5 = new Teacher("shmuel");
        Teacher t6 = new Teacher("yaniv");
        List<Teacher> tarr = new ArrayList<>();
        List<Lesson> lessons = new ArrayList<>();
         Random rand = new Random();
        int randHour, randHour2, rrand;
        tarr.add(t1);
        tarr.add(t2);
        tarr.add(t3);
        tarr.add(t4);
        tarr.add(t5);
        tarr.add(t6);

        // adds rundom subjects for the teacher to teach
        for (Teacher t : tarr) {
            int number = rand.nextInt(1,3);// number of subjects teacher teaches
            for (int i = 0; i < number; i++) {
                int randomnumber = rand.nextInt(10);
                t.addsubject(subjectsMap.get(randomnumber));
            }
        }
        // create lessons with there names
        for (Teacher t : tarr) {
            for (String sbj : t.getSubjects()) {
                Lesson l = new Lesson(sbj, t.getName(), -1, -1);
                lessons.add(l);
            }
        }

        // set random days that the teacher can work in dayCont
        // and if so then create hour constrains
        for (Teacher t : tarr) {
            boolean[] dayconst = new boolean[Schedual.MaxDays];
            boolean[][] hourPrefrences = new boolean[Schedual.MaxDays][Schedual.MaxHours];       
            for (int i = 0; i < Schedual.MaxDays; i++) {
                rrand = rand.nextInt(0, 2);
                // 1 means he can work that day
                if (rrand == 0) {
                    // set true on that day
                    dayconst[i] = true;
                    // 2 random hours 1 for starting that day and one for ending
                    randHour = rand.nextInt(0, (Schedual.MaxHours / 2) - 1);
                    randHour2 = rand.nextInt((Schedual.MaxHours / 2) + 1, Schedual.MaxHours+1);
                    for (int j = 0; j < Schedual.MaxHours; j++) {
                        if (j >= randHour && j <= randHour2) {
                            hourPrefrences[i][j] = true;
                        } else {
                            hourPrefrences[i][j] = false;
                        }

                    }
                } else {
                    dayconst[i] = false;
                    for (int j = 0; j < Schedual.MaxHours; j++) {
                        hourPrefrences[i][j] = false;
                    }
                }

            }

            t.setConstrains(hourPrefrences);
            t.SetDayConstrains(dayconst);
        }
        GeneticAlgorithm g = new GeneticAlgorithm();
        
        Schedual schedual = g.Geneticalgorithm(tarr);
        System.out.println("===============================returned Schedual========================");
        System.out.println("Evaluation" + g.evaluateSchedule(schedual, tarr));
    }
    */

    public void ChecckgeneticAlgo()
    {
        Teacher guy,trb,mrg,yaniv,moshe,ilan;
        guy = new Teacher("Guy");
        ilan = new Teacher("Ilan");
        trb = new Teacher("Trabelsy");
        mrg = new Teacher("Mergi");
        yaniv = new Teacher("Yaniv");
        moshe = new Teacher("Moshe");
        Constrains[] guyscont = new Constrains[Schedual.MaxDays];
        guyscont[0] = new Constrains(4,6);
        guyscont[1] = null;
        guyscont[2] = new Constrains(2,5);
        guyscont[3] = new Constrains(5,6);
        guyscont[4] = new Constrains(6,8);
        Constrains[] trbscont = new Constrains[Schedual.MaxDays];
        trbscont[0] = new Constrains(7,8);
        trbscont[1] = new Constrains(0,1);
        trbscont[2] = new Constrains(6,8);
        trbscont[3] = new Constrains(7,8);
        trbscont[4] = null;
        Constrains[] mrgcont = new Constrains[Schedual.MaxDays];
        mrgcont[0] = null;
        mrgcont[1] = new Constrains(6,8);
        mrgcont[2] = new Constrains(0,1);
        mrgcont[3] = null;
        mrgcont[4] = null;
        Constrains[] yanivscont = new Constrains[Schedual.MaxDays];
        yanivscont[0] = null;
        yanivscont[1] = null;
        yanivscont[2] = null;
        yanivscont[3] = new Constrains(0,1);
        yanivscont[4] = null;
        Constrains[] moshescont = new Constrains[Schedual.MaxDays];
        moshescont[0] = null;
        moshescont[1] = null;
        moshescont[2] = null;
        moshescont[3] = new Constrains(2,4);
        moshescont[4] = new Constrains(4,5);
        Constrains[] Ilancont = new Constrains[Schedual.MaxDays];
        Ilancont[0] = new Constrains(0,2);
        Ilancont[1] = new Constrains(2,5);
        Ilancont[2] = null;
        Ilancont[3] = null;
        Ilancont[4] = new Constrains(0,2);
        guy.setConstrains1(guyscont);
        moshe.setConstrains1(moshescont);
        ilan.setConstrains1(Ilancont);
        trb.setConstrains1(trbscont);
        mrg.setConstrains1(mrgcont);
        yaniv.setConstrains1(yanivscont);
        List<Teacher> list = new ArrayList<>();
        list.add(mrg);
        list.add(guy);
        list.add(moshe);
        list.add(ilan);
        list.add(trb);
        list.add(yaniv);
        Random rand = new Random();
        for (Teacher t : list) {
            int number = rand.nextInt(1,3);// number of subjects teacher teaches
            for (int i = 0; i < number; i++) {
                int randomnumber = rand.nextInt(10);
                t.addsubject(subjectsMap.get(randomnumber));
            }
        }

        GeneticAlgorithm g = new GeneticAlgorithm();
        
        Schedual schedual = g.Geneticalgorithm(list);
        System.out.println("===============================returned Schedual========================");
        System.out.println("Evaluation" + g.evaluateSchedule(schedual, list));

         
        
    }
    
    public void PerformPrintTeacherpreferces() {

        Teacher teacher1 = new Teacher();
        boolean[] dayconst = new boolean[Schedual.MaxDays];
        boolean[][] hourPrefrences = new boolean[Schedual.MaxDays][Schedual.MaxHours];
        Random r = new Random();
        int randHour, randHour2, rand;
        // set random days that the teacher can work in dayCont
        // and if so then create hour constrains
        for (int i = 0; i < Schedual.MaxDays; i++) {
            rand = r.nextInt(0, 2);
            // 1 means he can work that day
            if (rand == 0) {
                // set true on that day
                dayconst[i] = true;
                // 2 random hours 1 for starting that day and one for ending
                randHour = r.nextInt(0, (Schedual.MaxHours / 2) - 1);
                randHour2 = r.nextInt((Schedual.MaxHours / 2) + 1, Schedual.MaxHours);
                for (int j = 0; j < Schedual.MaxHours; j++) {
                    if (j > randHour && j < randHour2) {
                        hourPrefrences[i][j] = true;
                    } else {
                        hourPrefrences[i][j] = false;
                    }

                }
            } else {
                dayconst[i] = false;
                for (int j = 0; j < Schedual.MaxHours; j++) {
                    hourPrefrences[i][j] = false;
                }
            }

        }
        teacher1.setConstrains(hourPrefrences);
        teacher1.SetDayConstrains(dayconst);
        teacher1.printconstrains();
    }

    private void PerformPrintLessonsfromTeachers() {
        Random rand = new Random();
        int num = rand.nextInt(2);

        boolean[] dayPref = new boolean[Schedual.MaxDays];
        for (int i = 0; i < Schedual.MaxDays; i++) {
            if (num == 1) {
                dayPref[i] = true;

            } else {
                dayPref[i] = false;
            }
        }
        List<String> subjects = new ArrayList<>();
        String s1 = "Math";
        String s2 = "Physics";
        String s3 = "Chemistry";
        List<String> subjects2 = new ArrayList<>();
        String s21 = "Englis";
        String s22 = "Algebra";
        String s23 = "History";

        subjects.add(s1);
        subjects.add(s2);
        subjects.add(s3);
        subjects2.add(s3);
        subjects2.add(s21);
        subjects2.add(s22);
        subjects2.add(s23);
        Constrains[] constrains = new Constrains[Schedual.MaxDays];
        for (int i = 0; i < Schedual.MaxDays; i++) {
            int randomnumber = rand.nextInt(Schedual.StartingHour, Schedual.MaxHours + Schedual.StartingHour);
            int randomnumber2 = rand.nextInt(Schedual.StartingHour, Schedual.MaxHours + Schedual.StartingHour);
            if (randomnumber > randomnumber2) {
                Constrains con = new Constrains(randomnumber2, randomnumber);
                constrains[i] = con;
            } else {
                Constrains con = new Constrains(randomnumber, randomnumber2);
                constrains[i] = con;
            }

        }

        Teacher teacher = new Teacher();
        teacher.setName("Guy Yechezkel");
        teacher.setSubjects(subjects);
        teacher.SetDayConstrains(dayPref);
        teacher.setConstrains(constrains);
        Teacher teacher2 = new Teacher();
        teacher2.setName("IlanPerets");
        teacher2.setSubjects(subjects2);
        teacher2.SetDayConstrains(dayPref);
        teacher2.setConstrains(constrains);
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(teacher);
        teachers.add(teacher2);

        List<Lesson> lessons = teacher.GetLessonFromTeachers(teachers);
        for (Lesson s : lessons) {
            s.PrintLesson();
        }

    }

    private void PerformMutate() {
        List<Lesson> list = new ArrayList<>();
        Lesson lesson1 = new Lesson("English", "Teacher", 10, 11);
        Lesson lesson2 = new Lesson("math", "Teacher", 10, 11);
        Lesson lesson3 = new Lesson("History", "Teacher", 10, 11);
        Lesson lesson4 = new Lesson("Phisics", "Teacher", 10, 11);
        Lesson lesson5 = new Lesson("Astronomy", "Teacher", 10, 11);
        Lesson lesson6 = new Lesson("Sport", "Teacher", 10, 11);
        list.add(lesson1);
        list.add(lesson2);
        list.add(lesson3);
        list.add(lesson4);
        list.add(lesson5);
        list.add(lesson6);

        Schedual s = new Schedual("א");
        s.InitSchedual();
        s.FillScheduleWithLessons(list);
        System.out.println("==================================before=========================");
        s.printScheduleteachers();
        s = s.mutate();
        System.out.println("==================================after=========================");
        s.printScheduleteachers();
        System.out.println("==============================End============================");
    }

    private void PreformCrossOver() {
        Schedual s1 = new Schedual("ב");
        Schedual s2 = new Schedual("ב");
        s1.InitSchedual();
        s2.InitSchedual();
        Lesson lesson1 = new Lesson("English", "Teacher", 10, 11);
        Lesson lesson2 = new Lesson("math", "Teacher", 10, 11);
        Schedual returnSchedual = new Schedual(s1.getClassname());
        returnSchedual.InitSchedual();
        s1.InitSchedual();
        s2.InitSchedual();
        s1.fillschedualwithLesson(lesson1);
        s2.fillschedualwithLesson(lesson2);
        System.out.println("================================S1================================");
        s1.printSchedule();
        System.out.println("================================S2================================");
        s2.printSchedule();
        returnSchedual.crossover(s2, s1);
        System.out.println("================================Result================================");
        returnSchedual.printSchedule();
        System.out.println("================================End===================================");
    }

    public void PrintEmptySchedule() {
        Schedual s = new Schedual();
        s.InitSchedual();
        s.printSchedule();
    }


}

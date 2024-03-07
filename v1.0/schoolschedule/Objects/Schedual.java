package danielgras.schoolschedule.Objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.Random;
import java.util.Set;

import danielgras.schoolschedule.Servieces.SchedualServies;
import danielgras.schoolschedule.Servieces.TeacherServies;

public class Schedual {
    TeacherServies TeacherServies;
    private Lesson[][] Schedual;
    private String classname;
    public static final int MaxDays = 5;
    public static final int MaxHours = 9;
    public static final int LunchHour = 11;
    public static final int StartingHour = 8;
    private static final Map<Integer, String> daysOfWeekMap = new HashMap<>();
    SchedualServies Sservies ;
    static {
        daysOfWeekMap.put(0, "Sunday");
        daysOfWeekMap.put(1, "Monday");
        daysOfWeekMap.put(2, "Tuesday");
        daysOfWeekMap.put(3, "Wednesday");
        daysOfWeekMap.put(4, "Thursday");
    }

    public boolean IsTeacherScheduled(Teacher t , int day , int hour)
    {
        for(String g:t.getClasses())
        {
            Schedual[] s =(Sservies.findbyclassname(g));
            for(Schedual sc : s)
            {
                if(sc.getSchedual()[day][hour].getTeacher()==t.getName())
                {
                    return true;
                }
            }
            

        }
       
       return false;
    }

    public boolean isTeacherScheduledOnDay(Teacher teacher, int day)
    {
        for(String g:teacher.getClasses())
        {
            Schedual[] schedule =(Sservies.findbyclassname(g));
            for(Schedual sc : schedule)
            {
               List<String> s = sc.getTeachersInDay(day);
               for(String v : s)
               {
                if(v==teacher.getName())
                {
                    return true;
                }
               }
            }
        }
        return false;
    }
    public int TeacherScheduledtimes(Teacher teacher , int day , int hour)
    {
        int value = 0 ;
        for(String g:teacher.getClasses())
        {
            Schedual[] schedule =(Sservies.findbyclassname(g));
            for(Schedual sc : schedule)
            {
                if(sc.getSchedual()[day][hour].getTeacher()==teacher.getName())
                {
                    value +=1;
                }
            }
            

        }
       
       return value;
    }

    public Schedual() {
        this.Schedual = new Lesson[MaxDays][MaxHours];
    }

    public Schedual(Lesson[][] schedule, String s) {
        this.Schedual = schedule;
        this.classname = s;

    }

    public Schedual(String s) {
        this.classname = s;

    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public List<String> getTeachersInDay(int day) {
        Set<String> Tempteacher = new HashSet<>();
        List<String> teachers = new ArrayList<>();

        for (int i = 0; i < MaxHours; i++) {
            String teacher = this.Schedual[day][i].getTeacher();
            Tempteacher.add(teacher);
        }
        for (String s : Tempteacher) {
            teachers.add(s);
        }
        return teachers;
    }

    public List<Teacher> getTeachers() {
        Set<String> Tempteacher = new HashSet<>();
        List<Teacher> teachers = new ArrayList<>();

        for (int i = 0; i < MaxDays; i++) {
            for (int j = 0; j < MaxHours; j++) {
                if(this.Schedual[i][j] == null)
                {
                    continue;
                }
                String teacher = this.Schedual[i][j].getTeacher();
                Tempteacher.add(teacher);
            }
        }
        for (String s : Tempteacher) {
            Teacher t = TeacherServies.findByName(s);
            teachers.add(t);
        }
        return teachers;
    }

    public Lesson[][] getSchedual() {
        return Schedual;
    }

    public void setSchedual(Lesson[][] schedual) {
        Schedual = schedual;
    }

    public void FillScheduleWithLessons(List<Lesson> l) {
        Random r = new Random();
        int rand;
        for (int i = 0; i < MaxDays; i++) {
            for (int j = 0; j < MaxHours; j++) {
                if (j != LunchHour - StartingHour) {
                    rand = r.nextInt(0, l.size());
                    Lesson s = l.get(rand);
                    s.setStartHour(j + StartingHour + 1);
                    s.setEndHour(j + StartingHour + 1);
                    this.SetLesson(i, j, l.get(rand));
                }
            }
        }
    }

    public void printSchedule() {
        System.out.println("Schedule for class "+this.classname+":");

        // Print column headers (hours)
        System.out.print("\t");
        for (int hour = 0; hour < MaxHours; hour++) {
            System.out.printf("%2d" + ":00\t\t", (StartingHour + hour));
        }
        System.out.println();

        // Print schedule content   
        for (int day = 0; day < MaxDays; day++) {
            System.out.print("Day " + (day + 1) + ":\t");

            for (int hour = 0; hour < MaxHours; hour++) {
                Lesson currentLesson = Schedual[day][hour];
                if (currentLesson.getLessonSubject() != null) {
                    // Print lesson information
                    if(currentLesson.getTeacher()!=null)
                    {
                        System.out.print(currentLesson.getLessonSubject() + ","+currentLesson.getTeacher()+"\t");
                    }
                    else
                    {
                    System.out.print(currentLesson.getLessonSubject() +"\t" + "\t");
                    }
                } else {
                    // Print empty slot
                    System.out.print("--------\t");
                }
            }
            System.out.println();
        }
    }

    public void printScheduleteachers() {
        System.out.println("Schedule for class "+this.classname+":");

        // Print column headers (hours)
        System.out.print("\t");
        for (int hour = 0; hour < MaxHours; hour++) {
            System.out.printf("%2d" + ":00\t\t", (StartingHour + hour));
        }
        System.out.println();

        // Print schedule content   
        for (int day = 0; day < MaxDays; day++) {
            System.out.print("Day " + (day + 1) + ":\t");

            for (int hour = 0; hour < MaxHours; hour++) {
                Lesson currentLesson = Schedual[day][hour];
                if (currentLesson.getLessonSubject() != null) {
                    // Print lesson information
                    if(currentLesson.getTeacher()!=null)
                    {
                        System.out.print(currentLesson.getTeacher()+"\t"+"\t");
                    }
                    else
                    {
                    System.out.print(currentLesson.getLessonSubject() +"\t" + "\t");
                    }
                } else {
                    // Print empty slot
                    System.out.print("--------\t");
                }
            }
            System.out.println();
        }
    }

    public void crossover(Schedual parent1, Schedual parent2) {
        Random random = new Random();
        Schedual child = new Schedual();
        child.InitSchedual();
        // 2 random number 1 for a random hour to start copying utill that number hit in
        // the if
        // secend number for a rundom day to start copying utill that number hit in the
        // if
        int crossoverDayPoint = random.nextInt(1, MaxDays);
        int crossoverHourPoint = random.nextInt(0, MaxHours);
        // third random for how we are copying from the random day or the random hour
        int randomint = random.nextInt(2);
        if (randomint == 1) {
            for (int day = 0; day < MaxDays; day++) {
                for (int hour = 0; hour < MaxHours; hour++) {
                    if (day < crossoverDayPoint) {

                        child.getSchedual()[day][hour] = parent1.getSchedual()[day][hour];
                    } else {
                        // Copy genes from parent2 to child after the crossover point
                        child.getSchedual()[day][hour] = parent2.getSchedual()[day][hour];
                    }
                }
            }
            this.setSchedual(child.getSchedual());
        } else {
            for (int day = 0; day < MaxDays; day++) {
                for (int hour = 0; hour < MaxHours; hour++) {
                    if (hour < crossoverHourPoint) {

                        child.getSchedual()[day][hour] = parent1.getSchedual()[day][hour];
                    } else {
                        // Copy genes from parent2 to child after the crossover point
                        child.getSchedual()[day][hour] = parent2.getSchedual()[day][hour];
                    }
                }
            }
        }
        this.setSchedual(child.getSchedual());
    }
    public Schedual(Schedual original) {
        // Assuming Lesson and other properties are properly copied or cloned
        this.TeacherServies = original.TeacherServies;
        this.classname = original.classname;

        // Deep copy of the schedule array
        this.Schedual = new Lesson[MaxDays][MaxHours];
        for (int day = 0; day < MaxDays; day++) {
            for (int hour = 0; hour < MaxHours; hour++) {
                this.Schedual[day][hour] = new Lesson(original.Schedual[day][hour]);
            }
        }
    }

    // Function to perform mutation on a schedule
    public Schedual mutate() {
        Schedual mutated = new Schedual(this);
        Random random = new Random();
        // two random nmbers
        int day = random.nextInt(MaxDays);
        int hour = random.nextInt(MaxHours);
        // another 2 randoms
        int day2 = random.nextInt(MaxDays);
        int hour2 = random.nextInt(MaxHours);
        if (hour == LunchHour - StartingHour) {
            hour++;
        }
        if (hour2 == LunchHour - StartingHour) {
            hour2++;
        }
        while(mutated.getSchedual()[day][hour].getTeacher().equals(mutated.getSchedual()[day2][hour2].getTeacher()))
        {
            day2 = random.nextInt(MaxDays);
            hour2 = random.nextInt(MaxHours);
        }

        //System.out.println("replaced the (" + hour + "," + day + ")" + "with(" + hour2 + "," + day2 + ")");
        Lesson s = new Lesson();
        s = mutated.Schedual[day][hour];
        mutated.Schedual[day][hour] = mutated.Schedual[day2][hour2];
        mutated.Schedual[day2][hour2] = s;
        return mutated;
  
    }

    public Schedual spesificmutate(int day,int hour) {
        Schedual mutated = new Schedual(this);
        Random random = new Random();
        // another 2 randoms
        int day2 = random.nextInt(MaxDays);
        int hour2 = random.nextInt(MaxHours);
        if (hour2 == LunchHour - StartingHour) {
            hour2++;
        }
        while(mutated.getSchedual()[day][hour].getTeacher().equals(mutated.getSchedual()[day2][hour2].getTeacher()))
        {
            day2 = random.nextInt(MaxDays);
            hour2 = random.nextInt(MaxHours);
            if (hour == LunchHour - StartingHour) {
                hour = (hour+1)%MaxHours;
            }
        }

        //System.out.println("replaced the (" + hour + "," + day + ")" + "with(" + hour2 + "," + day2 + ")");
        Lesson s = new Lesson();
        s = mutated.Schedual[day][hour];
        mutated.Schedual[day][hour] = mutated.Schedual[day2][hour2];
        mutated.Schedual[day2][hour2] = s;
        this.setSchedual(mutated);
        return mutated;
    }

    public void setSchedual(Schedual s)
    {
        this.Schedual = s.getSchedual();
        this.classname = s.getClassname();
    }

    public void SetLesson(int i, int j, Lesson l) {
        this.Schedual[i][j] = l;
    }

    // public void printSchedule1() {
    // // Print table header
    // System.out.println(
    // "----------------------------------------------------------scheduale---------------------------------------------------------");
    // System.out.printf("%-20s", " ");
    // for (int day = 0; day <= MaxDays; day++) {
    // System.out.printf("| %-15s", daysOfWeekMap.get(day));
    // }
    // System.out.println("|");

    // // Print table rows
    // for (int hour = 0; hour <= MaxDays; hour++) {
    // System.out.printf("%-20s", "Hour " + hour);
    // for (int day = 0; day <= MaxDays; day++) {
    // Lesson lesson = Schedual[day][hour];
    // if (lesson != null) {
    // System.out.printf("|"+"| %-15s", lesson.GetLesson() +"|"+ "\n" +
    // lesson.PrintHours());
    // } else {
    // System.out.printf("| %-15s", "|No Lesson|");
    // }
    // }
    // System.out.println("|");
    // }
    // System.out.println(
    // "----------------------------------------------------------scheduale---------------------------------------------------------");
    // }

    public void fillschedualwithLesson(Lesson l) {

        for (int i = 0; i < MaxDays; i++) {
            for (int j = 0; j < MaxHours; j++) {
                if (j != LunchHour - StartingHour) {
                    SetLesson(i, j, l);
                }
            }
        }
    }

    public void InitSchedual() {
        this.Schedual = new Lesson[MaxDays][MaxHours];
        for (int i = 0; i < MaxDays; i++) {
            for (int j = 0; j < MaxHours; j++) {
                if (j == LunchHour - StartingHour) {
                    this.Schedual[i][j] = new Lesson("BreakFast", null, i + StartingHour, j + StartingHour + 1);
                } else {
                    this.Schedual[i][j] = new Lesson();
                }
            }
        }
    }

}

package daniel.proj.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Schools")
public class School {
    private String name;
    private List<Teacher> Teachers = new ArrayList<>();
    private List<SchoolClass> Classes = new ArrayList<>();

    public void printSChool() {
        String s;
        s = "School Name : " + name + "\n";
        s += "==============Whole School Teacher List======================" + "\n";
        for (Teacher t : Teachers) {
            s += t.getName() + "\n";
        }
        s += "==================" + Classes.size() + "classes=========================" + "\n";

        for (SchoolClass c : Classes) {
            s += "class : " + c.getClassName() + "\n";
            s += "Subjects : \n";
            for (Subject subj : c.getSubjects()) {
                s += "Subject : " + subj.getSubjectName() + " , Teacher : " + subj.getTeacherName() + "\n";
            }
            s += "\n=================================================================================\n";
            s += "Schedule \n";
            if (c.getSchedule() == null) {
                s += "null";
            } else {
                s += c.getSchedule().getScheduleAsString();
            }
            s += "\n";
            s += "\n=================================================================================\n";
            s += " Teachers : \n";
            for (String t : c.getTeachers()) {
                s += t + " \n";
            }
            s += "\n=================================================================================\n";
            s += "\nLocked Lessons : \n";
            for (Lesson l : c.getLockedLessons()) {
                s += l.getLessonSubject() + " " + l.getTeacher() + " in day :" + l.getDay() + "Hours "
                        + l.getStartHour();
                s += "\n";
            }
            s += "\n=================================================================================\n";
        }

        System.out.println(s);

    }

    public Schedule AvaliabilityNumbers() {
        int count = 0;
        Schedule s = new Schedule();
        s.InitSchedule();
        for (SchoolClass cs : Classes) {
            for (int Day = 0; Day < Schedule.MaxDays; Day++) {
                for (int Hour = 0; Hour < Schedule.MaxHours; Hour++) {
                    count = 0;
                    for (Teacher t : getTeachers(cs.getTeachers())) {
                        if (t.getHourPrefrences()[Day][Hour]) {
                            count += 1;
                        }
                    }
                    Lesson les = new Lesson();
                    les.setLessonSubject("" + count);
                    s.SetLesson(Day, Hour, les);
                }
            }
            s.printSchedule();
        }
        return s;
    }

    public int getTeacherWeeklyHours(String Teacher, String ClassName) {
        int index;
        SchoolClass cs = getClass(ClassName);
        index = getTeacherIndex(Teacher);
        Teacher t = Teachers.get(index);
        int count = 0;
        for (Subject subj : cs.getTeachersSubjectsInClass(Teacher)) {
            count += subj.getWeaklyHours();
        }
        return count;
    }

    public static ArrayList<Integer> extractFirstNumbers(String input) {
        ArrayList<Integer> numbers = new ArrayList<>();
        String[] pairs = input.split(":");
        for (String pair : pairs) {
            if (!pair.isEmpty()) {
                String[] values = pair.split(",");
                if (values.length >= 1) {
                    int firstNumber = Integer.parseInt(values[0]);
                    numbers.add(firstNumber);
                }
            }
        }
        return numbers;
    }

    public Schedule Avaliability() {
        String count = "";
        Schedule s = new Schedule();
        s.InitSchedule();
        for (SchoolClass cs : Classes) {
            for (int Day = 0; Day < Schedule.MaxDays; Day++) {
                for (int Hour = 0; Hour < Schedule.MaxHours; Hour++) {
                    count = "";
                    for (Teacher t : getTeachers(cs.getTeachers())) {
                        if (t.getHourPrefrences()[Day][Hour]) {
                            count += t.getName() + " ";
                        }
                    }
                    Lesson les = new Lesson();
                    les.setLessonSubject("" + count);
                    s.SetLesson(Day, Hour, les);
                }
            }
            s.printSchedule();
            System.out.println("\n");
        }
        return s;
    }

    public Schedule Avaliabilityadindex() {
        String count = "";
        Schedule s = new Schedule();
        s.InitSchedule();
        for (SchoolClass cs : Classes) {
            for (int Day = 0; Day < Schedule.MaxDays; Day++) {
                for (int Hour = 0; Hour < Schedule.MaxHours; Hour++) {
                    count = "";
                    for (Teacher t : getTeachers(cs.getTeachers())) {
                        if (t.getHourPrefrences()[Day][Hour]) {
                            count += getTeacherIndex(t.getName()) + ","
                                    + getTeacherWeeklyHours(t.getName(), cs.getClassName()) + ":";
                        }
                    }
                    Lesson les = new Lesson();
                    les.setLessonSubject("" + count);
                    s.SetLesson(Day, Hour, les);
                }
            }
            s.printSchedule();
            System.out.println("\n");
        }
        return s;
    }

    public School(School otherSchool) {
        this.name = otherSchool.name;

        // Deep copy for Teachers
        for (Teacher teacher : otherSchool.Teachers) {
            this.Teachers.add(new Teacher(teacher));
        }

        // Deep copy for Classes
        for (SchoolClass schoolClass : otherSchool.Classes) {
            this.Classes.add(new SchoolClass(schoolClass));
        }
        

    }

    public void setSchool(School otherSchool) {
        this.name = otherSchool.name;

        // Deep copy for Teachers
        for (Teacher teacher : otherSchool.Teachers) {
            this.Teachers.add(new Teacher(teacher));
        }

        // Deep copy for Classes
        for (SchoolClass schoolClass : otherSchool.Classes) {
            this.Classes.add(new SchoolClass(schoolClass));
        }
    }

    public School() {

    }

    public void adjustEmptySubjects() {
        for (int i = 0; i < Classes.size(); i++) {
            Classes.get(i).adjustEmptySubject();
        }
    }

    public int countweaklyapearance(int day) {
        int count = 0;
        // TODO finish
        return count;
    }

    public void addLockedLesson(String ClassName, Lesson LockedLesson) {
        int index = getClassIndex(ClassName);
        if (index != -1) {
            this.Classes.get(index).addLockedLesson(LockedLesson);
        } else {
            System.out.println("Class Dosn't Exist");
        }
    }

    public int evaluateTeacherClassDieversity(String ClassName) {
        int Eval = 0;
        List<Teacher> Teacherss = getTeachers(getClass(ClassName).getTeachers());
        for (Teacher t : Teacherss) {
            for (int Day = 0; Day < Schedule.MaxHours; Day++) {
                if (t.getDayConstrains()[Day] = true) {
                    // TODO finish
                }
            }
        }
        return Eval;
    }

    public int countTeacherClassDiaversity(String ClassName) {
        SchoolClass sc = new SchoolClass();
        Schedule s = sc.getSchedule();
        for (int day = 0; day < Schedule.MaxDays; day++) {
            for (Teacher Teacher : getTeachers(sc.getTeachers())) {
                if (Teacher.getDayConstrains()[day] == true) {
                    for (int hour = 0; hour < Schedule.MaxHours; hour++) {
                        ArrayList TeachersSubjects = sc.getTeachersSubjectsInClass(Teacher.getName());
                        // TODO Finish
                    }
                }
            }
        }
        return 1;

    }

    public static int[] findLowestNumberAndIndices(ArrayList<ArrayList<Integer>> days) {
        int minNumber = Integer.MAX_VALUE;
        int dayIndex = -1;
        int hourIndex = -1;

        for (int i = 0; i < days.size(); i++) {
            ArrayList<Integer> hours = days.get(i);
            for (int j = 0; j < hours.size(); j++) {
                int currentNumber = hours.get(j);
                if (currentNumber < minNumber) {
                    minNumber = currentNumber;
                    dayIndex = i;
                    hourIndex = j;
                }
            }
        }

        return new int[] { minNumber, dayIndex, hourIndex };
    }

    // TODO
    // =====================================================================================================================================================================================================================================================================================================================
    public int evaluateSchoolClass(String className) {
        int value = 100;

        Double priority = EvaluatePriority(className);

        int MidEmptys = CountEmptyClassInMidSchrdule(className);

        int hourmismutchs = CountTeachersHourMisMutch(className);

        List<Integer> Distances = EvalWeeklyHours(className);

        int LockedLessonsBreaks = CountLockedBreaks(className);

        int Elapces = Elpaces(className);

        value -= (priority * 4);

        value -= (Elapces * 100);

        value -= (LockedLessonsBreaks * 75);

        // valuate the hour mismutches(-10 for any hour that put to work in a day he
        // cant work)
        value -= (hourmismutchs * 100);

        // Remove Points if a empty class in the middle of the schedule

        value -= (MidEmptys * 50);

        // -10 points if a subjects was more or less time then the weekly hours
        for (int distance : Distances) {
            if (distance > 0) {
                value -= (distance * 50);
            } else if (distance < 0) {
                value += (distance * 5);
            }
        }

        return value;
    }

    public int Elpaces(String ClassName) {
        int elpaces = 0;
        SchoolClass sc = getClass(ClassName);
        for (int day = 0; day < Schedule.MaxDays; day++) {
            for (int hour = 0; hour < Schedule.MaxHours; hour++) {
                if (hour != Schedule.LunchHour - Schedule.StartingHour) {
                    Lesson les = sc.getSchedule().getSchedule()[day][hour];
                    Teacher t = getTeacherByName(les.getTeacher());
                    if (!les.getLessonSubject().equals("Empty")) {
                        elpaces += CountElapces(t, day, hour);
                    }

                }
            }
        }
        return elpaces;
    }

    public int CountLockedBreaks(String ClassName) {
        int count = 0;
        SchoolClass sc = getClass(ClassName);
        for (Lesson s : sc.getLockedLessons()) {
            int Day = s.getDay();
            int hour = s.getStartHour() - Schedule.StartingHour;

            if (sc.getSchedule().getSchedule()[Day][hour].getLessonSubject() != s.getLessonSubject()) {
                count++;
            }

        }

        return count;
    }

    public int countweeklyEmptys(String ClassName, int day) {
        int count = 0;
        SchoolClass sc = getClass(ClassName);
        Schedule schedule = sc.getSchedule();
        for (int i = 0; i < Schedule.MaxHours; i++) {
            if (i != Schedule.LunchHour - Schedule.StartingHour) {
                if (schedule.getSchedule()[day][i].GetLesson().equals("Empty")) {
                    count++;
                }
            }
        }
        return count;
    }

    public Double EvaluatePriority(String ClassName) {
        SchoolClass Class = getClass(ClassName);
        Schedule schedule = Class.getSchedule();
        Double d = 0.0;
        for (int day = 0; day < Schedule.MaxDays; day++) {
            for (int hour = 0; hour < Schedule.MaxHours; hour++) {
                if (!(hour == Schedule.LunchHour - Schedule.StartingHour)
                        && !(schedule.getSchedule()[day][hour].getLessonSubject().equals("Empty"))) {
                    Teacher t = getTeacherByName(schedule.getSchedule()[day][hour].getTeacher());
                    Subject subject = getSubject(ClassName, schedule.getSchedule()[day][hour].getLessonSubject());
                    int weeklyHours = subject.getWeaklyHours();
                    int[][] avaliability = t.getAvaliability();
                    if ((subject.getPriority() > 3)) {
                        for (int Hour = 0; Hour < Schedule.MaxHours; Hour++) {
                            for (int Day = 0; Day < Schedule.MaxDays; Day++) {
                                if (avaliability[Day][Hour] == 1) {
                                    if (weeklyHours > 0 && !schedule.getSchedule()[Day][Hour].getLessonSubject()
                                            .equals(subject.getSubjectName())) {
                                        if (subject.getPriority() == 4) {
                                            d += 1;
                                        } else if (subject.getPriority() == 5) {
                                            d += 1.5;

                                        }
                                        weeklyHours--;
                                    } else if (schedule.getSchedule()[Day][Hour].getLessonSubject()
                                            .equals(subject.getSubjectName())) {
                                        weeklyHours--;
                                    }
                                }

                            }
                        }
                    } else if ((subject.getPriority() < 3)) {
                        for (int Hour = Schedule.MaxHours-1; Hour >= 0; Hour--) {
                            for (int Day = 0; Day < Schedule.MaxDays; Day++) {
                                if (avaliability[Day][Hour] == 1) {
                                    if (weeklyHours > 0 && !schedule.getSchedule()[Day][Hour].getLessonSubject()
                                            .equals(subject.getSubjectName())) {
                                        if (subject.getPriority() == 2) {
                                            d += 1;
                                        } else if (subject.getPriority() == 1) {
                                            d += 1.5;

                                        }
                                        weeklyHours--;
                                    } else if (schedule.getSchedule()[Day][Hour].getLessonSubject()
                                            .equals(subject.getSubjectName())) {
                                        weeklyHours--;
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }

        return d;
    }

    public void printEvaluation(String ClassName) {

        SchoolClass sc = new SchoolClass();
        sc = getClass(ClassName);

        Double Priority = EvaluatePriority(ClassName);

        Schedule schedule = sc.getSchedule();

        List<Subject> subjects = sc.getSubjects();

        int MidEmptys = CountEmptyClassInMidSchrdule(ClassName);

        List<Integer> Streaks = CountStreaks(ClassName);

        int DayMismutchs = CountTeachersDaysMismutch(ClassName);

        int hourmismutchs = CountTeachersHourMisMutchprint(ClassName);

        List<Integer> Distances = EvalWeeklyHours(ClassName);

        int LockedLessonsBreaks = CountLockedBreaks(ClassName);

        int Elapces = Elpaces(ClassName);

        String s = "========================================================================================================"
                + "\n";

        s += "Evaluation for Class : " + ClassName + "\n";
        s += " Priority Breaks : " + Priority;
        s += "Empty Classes Mid Schedule : " + MidEmptys + "\n";
        s += "Locked Lessons Breaks : " + LockedLessonsBreaks + "\n";
        s += " Elapcces : " + Elapces + "\n";
        s += "Streaks : " + "\n";
        for (int i = 0; i < Streaks.size(); i++) {
            if (Streaks.get(i) >= 0) {
                s += Streaks.get(i) + " , ";
            } else {
                s += "\n";
            }
        }
        s += "\n";
        s += "Day Mismutches : " + DayMismutchs;
        s += "\n" + "Hour Mismutches : " + hourmismutchs + "\n";
        s += " Distances : " + "\n";
        s += "(";
        for (int i = 0; i < Distances.size(); i++) {
            // s += subjects.get(i).getSubjectName();
            s += " ";
            s += Distances.get(i) + ",";
        }
        s += ")";
        s += "\n hours we have : \n";
        for (Subject subj : subjects) {
            int classweeklyhours = schedule.CountWeeklyHours(subj.getSubjectName());
            s += subj.getSubjectName();
            s += " - " + classweeklyhours + "\t";
        }
        s += "\n hours we need : \n";
        for (Subject subj : subjects) {
            s += subj.getSubjectName();
            s += " - " + subj.getWeaklyHours() + "\t";
        }
        s += "\nToatal School Classes : " + (Schedule.MaxDays * Schedule.MaxHours);
        s += "Total School Teacher needed hours " + countClassNeededHours(ClassName);
        s += "\n";
        s += "========================================================================================================"
                + "\n";
        // all there subject
        // weekly hours combined(the time they should work a week)
        System.out.println(s);

    }

    // TODO make that it calculates the amount of empty classes in that scehdule
    // class and add it to subject list

    public int countClassNeededHours(String ClassName) {
        int count = 0;
        SchoolClass sc = this.getClass(ClassName);
        for (Subject s : sc.getSubjects()) {
            if (!s.getSubjectName().equals("Empty")) {
                count += s.getWeaklyHours();
            }
        }
        return count;
    }

    public List<Integer> EvalWeeklyHours(String ClassName) {
        List<Integer> Distances = new ArrayList<>();
        SchoolClass sc = getClass(ClassName);
        Schedule classSchedule = sc.getSchedule();
        List<Subject> subjects = sc.getSubjects();
        for (Subject s : subjects) {
            // if (s.getSubjectName().equals("Empty")) {
            // continue;
            // }
            int classweeklyhours = classSchedule.CountWeeklyHours(s.getSubjectName());
            int classNeededWeeklyHours = s.getWeaklyHours();
            int DistanceFromWeeklyHours = classNeededWeeklyHours - classweeklyhours;
            Distances.add(DistanceFromWeeklyHours);
        }
        return Distances;
    }

    public void PrintTeachersWeeklyHours() {

        List<Teacher> Teachers = getTeachers();
        for (Teacher teach : Teachers) {
            System.out.println(
                    "Teacher Name  :  " + teach.getName() + " , Teaher can work : " + teach.getWeeklyHours());
        }
        for (int Class = 0; Class < Classes.size(); Class++) {
            System.out.println("Class " + this.getClasses().get(Class).getClassName());
            for (Subject sbj : this.getClasses().get(Class).getSubjects()) {
                System.out.println(" Subject : " + sbj.getSubjectName() + " : " + sbj.getWeaklyHours() + " Priority :"
                        + sbj.getPriority() + "\t");
            }
            System.out.println();
        }
    }

    public Subject getSubject(String ClassName, String SubjectName) {
        return getClass(ClassName).getSubject(SubjectName);
    }

    public void printweeklyhours() {
        for (SchoolClass s : Classes) {
            s.printSubjectsweeklyhours();
        }
    }

    public int CountEmptyClassInMidSchrdule(String ClassName) {
        int count = 0;
        int index = 0;
        SchoolClass sc = getClass(ClassName);
        if (sc != null) {
            Schedule schedule = sc.getSchedule();
            for (int day = 0; day < Schedule.MaxDays; day++) {
                for (int hour = Schedule.MaxHours - 1; hour >= 0; hour--) {
                    if (!schedule.getSchedule()[day][hour].getLessonSubject().equals("Empty")) {
                        index = hour;
                        break;
                    }
                }
                for (int hour = 0; hour < index; hour++) {
                    if (schedule.getSchedule()[day][hour].getLessonSubject().equals("Empty")) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public SchoolClass getClass(String ClassName) {
        for (SchoolClass schoolClass : this.getClasses()) {
            if (schoolClass.getClassName().equals(ClassName)) {
                return schoolClass;
            }
        }
        return null;
    }

    public int getClassIndex(String ClassName) {
        for (int i = 0; i < this.getClasses().size(); i++) {
            if (getClasses().get(i).getClassName().equals(ClassName)) {
                return i;
            }
        }
        return -1;
    }

    public List<Integer> CountStreaks(String ClassName) {
        // returns a list with all the straks
        int streak = 0;
        List<Integer> list = new ArrayList<>();
        SchoolClass sc = getClass(ClassName);
        if (sc != null) {
            Schedule classSchedule = sc.getSchedule();
            List<Teacher> teachers = getTeachers(sc.getTeachers());
            for (int day = 0; day < Schedule.MaxDays; day++) {
                list.add(-100);
                for (int hour = 0; hour < Schedule.MaxHours; hour++) {
                    Lesson lesson = classSchedule.getSchedule()[day][hour];

                    if (hour == (Schedule.LunchHour - Schedule.StartingHour)) {
                        // break if on lunch
                        continue;
                    }

                    if (lesson != null) {
                        Teacher teacher = findTeacher(teachers, lesson.getTeacher());

                        if (teacher != null || lesson.getLessonSubject().equals("empty")) {
                            if (hour > 0) {
                                // checking the teacher streak;
                                if (classSchedule.getSchedule()[day][hour - 1].getLessonSubject().equals("BreakFast")) {
                                    if (lesson.getTeacher().equals(classSchedule.getSchedule()[day][hour - 2]
                                            .getTeacher())) {
                                        streak++;
                                    } else {
                                        list.add(streak);
                                        streak = 0;
                                    }
                                } else if (classSchedule.getSchedule()[day][hour - 1] != null) {
                                    if (lesson.getTeacher().equals(classSchedule.getSchedule()[day][hour - 1]
                                            .getTeacher())) {
                                        streak++;
                                        if (hour == Schedule.MaxHours - 1) {
                                            list.add(streak);
                                            streak = 0;
                                        }
                                    } else {
                                        list.add(streak);
                                        streak = 0;
                                        if (hour == Schedule.MaxHours - 1) {
                                            list.add(streak);
                                            streak = 0;
                                        }
                                    }
                                }
                            }
                        } else if (lesson.getLessonSubject().equals("Empty")) {

                        } else {
                            list.add(streak);
                            streak = 0;
                            if (hour == Schedule.MaxHours - 1) {
                                list.add(streak);
                                streak = 0;
                            }
                        }
                    }
                }

            }

        }

        return list;
    }

    public int CountTeachersDaysMismutch(String ClassName) {
        int value = 0;

        for (SchoolClass sc : this.getClasses()) {
            if (sc.getClassName().equals(ClassName)) {
                List<Teacher> Teachers = getTeachers(sc.getTeachers());
                for (int day = 0; day < Schedule.MaxDays; day++) {
                    for (int hour = 0; hour < Schedule.MaxHours; hour++) {
                        Schedule schedule = sc.getSchedule();
                        if (!(hour == Schedule.LunchHour - Schedule.StartingHour)) {
                            if (findTeacher(Teachers, schedule.getSchedule()[day][hour].getTeacher()) != null) {
                                Teacher t = findTeacher(Teachers, schedule.getSchedule()[day][hour].getTeacher());
                                if (!t.getDayConstrains()[day]) {
                                    value++;
                                }
                            }
                        }
                    }
                }
            }
        }

        return value;
    }

    public int CountTeachersHourMisMutch(String ClassName) {
        int value = 0;

        for (SchoolClass sc : this.getClasses()) {
            if (sc.getClassName().equals(ClassName)) {
                List<Teacher> Teachers = getTeachers(sc.getTeachers());
                for (int day = 0; day < Schedule.MaxDays; day++) {
                    for (int hour = 0; hour < Schedule.MaxHours; hour++) {
                        Schedule schedule = sc.getSchedule();
                        if (!(hour == Schedule.LunchHour - Schedule.StartingHour)) {
                            if (findTeacher(Teachers, schedule.getSchedule()[day][hour].getTeacher()) != null) {
                                Teacher t = findTeacher(Teachers, schedule.getSchedule()[day][hour].getTeacher());
                                if (!t.getHourPrefrences()[day][hour]) {
                                    value++;
                                }
                            }
                        }
                    }
                }
            }
        }

        return value;
    }

    // TODO remove this function
    public int CountTeachersHourMisMutchprint(String ClassName) {
        int value = 0;

        for (SchoolClass sc : this.getClasses()) {
            if (sc.getClassName().equals(ClassName)) {
                List<Teacher> Teachers = getTeachers(sc.getTeachers());
                for (int day = 0; day < Schedule.MaxDays; day++) {
                    for (int hour = 0; hour < Schedule.MaxHours; hour++) {
                        Schedule schedule = sc.getSchedule();
                        if (!(hour == Schedule.LunchHour - Schedule.StartingHour)) {
                            if (findTeacher(Teachers, schedule.getSchedule()[day][hour].getTeacher()) != null) {
                                Teacher t = findTeacher(Teachers, schedule.getSchedule()[day][hour].getTeacher());
                                if (!t.getHourPrefrences()[day][hour]) {
                                    value++;
                                    System.out.println(day + "<- Day" + "Hour ->" + hour);
                                }
                            }
                        }
                    }
                }
            }
        }

        return value;
    }

    public void mutate(String ClassName, List<Subject> sublist) {
        Schedule o = new Schedule();
        o = getClasses().get(this.getClassIndex(ClassName)).getSchedule().mutate(sublist);
        this.getClasses().get(this.getClassIndex(ClassName)).setSchedule(o);
    }

    public void BigMuate(String ClassName, List<Subject> Subjects) {
        Schedule o = new Schedule();
        o = getClasses().get(this.getClassIndex(ClassName)).getSchedule().Bigmutate(Subjects);
        this.getClasses().get(this.getClassIndex(ClassName)).setSchedule(o);
    }

    private Teacher findTeacher(List<Teacher> teachers, String teacherName) {
        for (Teacher teacher : teachers) {
            if (teacher != null) {
                if (teacher.getName().equals(teacherName)) {
                    return teacher;
                }
            }
        }
        return null;
    }

    public School tournamentSelection(List<School> Population, List<Integer> Evaluations, List<Integer> SortedIndexes) {
        Random rand = new Random();
        // get 2 random schools
        int randomnumber1 = rand.nextInt(0, SortedIndexes.size() / 2);
        int randomnumber2 = rand.nextInt(0, SortedIndexes.size()) / 2;
        // check if they are the same one and if so replace one of theme
        while (randomnumber1 == randomnumber2) {
            randomnumber2 = rand.nextInt(0, SortedIndexes.size() / 2);
        }
        // get there evaluations
        int candidate1Score = Evaluations.get(randomnumber1);
        int candidate2Score = Evaluations.get(randomnumber2);
        // System.out.println("Candidates = "+ randomnumber1 +","+ randomnumber2+".");
        // return the higher evaluation
        if (candidate1Score > candidate2Score) {
            setSchool(Population.get(randomnumber1));
            return Population.get(randomnumber1);
        } else {
            setSchool(Population.get(randomnumber2));
            return Population.get(randomnumber2);
        }
    }

    public void crossover(School s, School ss) {
        for (int i = 0; i < this.getClasses().size(); i++) {
            System.out.println("==============================================" + "התיכ " + i
                    + "======================================");
            Schedule s1 = new Schedule();
            Schedule s2 = new Schedule();
            Schedule s3 = new Schedule();

            s1 = s.getClasses().get(i).getSchedule();
            s2 = ss.getClasses().get(i).getSchedule();
            s3.InitSchedule();
            System.out.println("============================תכרעמ 1========================");
            s1.printSchedule();
            System.out.println("===============================תכרעמ 2========================");
            s2.printSchedule();
            System.out.println("=================================ירחא=======================");
            s3.crossover(s1, s2);
            s3.printSchedule();
            this.getClasses().get(i).setSchedule(s3);
        }
    }

    public void crossover(School s, School ss, String ClassName) {
        for (int i = 0; i < this.getClasses().size(); i++) {
            // check of we are on the same class ass we want to change
            if (s.getClasses().get(i).getClassName() != ClassName) {
                continue;
            }
            // System.out.println("==============================================" + "התיכ "
            // + i
            // + "======================================");
            Schedule s1 = new Schedule();
            Schedule s2 = new Schedule();
            Schedule s3 = new Schedule();

            s1 = s.getClasses().get(i).getSchedule();
            s2 = ss.getClasses().get(i).getSchedule();
            s3.InitSchedule();
            // System.out.println("============================תכרעמ
            // 1========================");
            // s1.printSchedule();
            // System.out.println("===============================תכרעמ
            // 2========================");
            // s2.printSchedule();
            // System.out.println("=================================ירחא=======================");
            s3.crossover(s1, s2);
            // s3.printSchedule();
            this.getClasses().get(i).setSchedule(s3);
        }
    }

    public School(String Name) {
        this.name = Name;
    }

    public void addTeacher(Teacher t) {
        int index = getTeacherIndex(t.getName());
        if (index == -1)
            this.Teachers.add(t);
        else
            System.out.println("Teacher Already Exist");
    }

    public void addsubject(Subject Subject, String ClassName) {
        if (this.Classes.get(getClassIndex(ClassName)).IsSubjectExist(Subject)) {
            System.out.println("Subject Already Exist");
            System.out.println(Subject.getSubjectName() + " <- Name , Class : " + ClassName + ",  Teacher -> "
                    + Subject.getTeacherName() + "index : " + getClassIndex(ClassName));
        } else if (getTeacherIndex(Subject.getTeacherName()) == -1) {
            System.out.println("Teacher Dosnt Exist");
            System.out.println(Subject.getTeacherName() + Subject.getSubjectName());
        } else {
            this.Classes.get(getClassIndex(ClassName)).addSubject(Subject);
            this.Teachers.get(getTeacherIndex(Subject.getTeacherName())).AdjustWeeklyHours(Subject);
        }
    }

    public int getTeacherIndex(String TeacherName) {
        int index = -1;
        for (int i = 0; i < Teachers.size(); i++) {
            if (Teachers.get(i).getName().equals(TeacherName)) {
                index = i;
                return index;
            }
        }
        return index;
    }

    public String getName() {
        return name;
    }

    public void addclass(SchoolClass clas) {
        this.Classes.add(clas);
    }

    public List<Teacher> getTeachers(List<String> TeacherNames) {
        List<Teacher> Teachers = new ArrayList<>();
        for (String teacher : TeacherNames) {
            Teacher t = getTeacherByName(teacher);
            Teachers.add(t);
        }

        return Teachers;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public List<Teacher> getTeachers() {
        return Teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        Teachers = teachers;
    }

    public List<SchoolClass> getClasses() {
        return Classes;
    }

    public void setClasses(List<SchoolClass> classes) {
        Classes = classes;
    }

    public SchoolClass GetClassByName(String Name) {
        for (SchoolClass classs : this.getClasses()) {
            if (classs.getClassName().equals(Name)) {
                return classs;
            }
        }
        return null;
    }

    public int CountElapces(Teacher teacher, int Day, int hour) {
        int count = 0;
        // go trow all classes in the school
        for (SchoolClass s : this.getClasses()) {
            // go throw all the theachers in that class
            for (String teacherName : s.getTeachers()) {
                // check if the teacher is theaching there
                if (teacher.getName().equals(teacherName)) {
                    if (s.getSchedule().getSchedule()[Day][hour].getTeacher() != null) {
                        // if the teacher is theaching there check if there any elapces in the scheduals
                        if (s.getSchedule().getSchedule()[Day][hour].getTeacher().equals(teacherName)) {
                            count++;
                        }
                    }

                }
            }
        }
        // becuse he is on the class that checks him its -1
        return count - 1;
    }

    public Teacher getTeacherByName(String Name) {

        for (Teacher t : this.getTeachers()) {
            if (t.getName().equals(Name)) {
                return t;
            }
        }

        return null;
    }

}

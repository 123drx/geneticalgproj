package danielgras.schoolschedule.Algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import danielgras.schoolschedule.Objects.Lesson;
import danielgras.schoolschedule.Objects.Schedual;
import danielgras.schoolschedule.Objects.Teacher;

public class GeneticAlgorithm {

    private static final int POPULATION_SIZE = 200;
    private static final int MAX_GENERATIONS = 420;
    private static final double MUTATION_RATE = 0.55;

    // public int evaluate(Schedual schedual) {
    // int penalty = 0;
    // // Constraint: Avoid teachers being scheduled in multiple classes at the same
    // time
    // penalty += penaltyForTeacherConflicts(schedual);

    // // Constraint: Ensure teachers have preferred hours
    // penalty += penaltyForTeacherPreferredHours(schedual);

    // // Constraint: Ensure teachers have preferred days
    // penalty += penaltyForTeacherPreferredDays(schedual);

    // int fitness = 100 - penalty;

    // // Ensure fitness is within the specified range
    // return Math.max(Math.min(fitness, 100), -100);
    // }

    // private int penaltyForTeacherConflicts(Schedual schedual) {
    // int conflicts = 0;

    // // Iterate through each teacher
    // for (Teacher teacher : schedual.getTeachers()) {
    // // Iterate through the schedual for each day and hour
    // for (int day = 0; day < Schedual.MaxDays; day++) {
    // for (int hour = 0; hour < Schedual.MaxHours; hour++) {
    // // Check if the teacher is scheduled in multiple classes at the same time
    // if (schedual.TeacherScheduledtimes(teacher, day, hour) > 1) {
    // conflicts++;
    // }
    // }
    // }
    // }

    // // Penalize conflicts
    // return conflicts * 10; // Adjust the weight based on the importance of this
    // constraint
    // }

    // private int penaltyForTeacherPreferredHours(Schedual schedual) {
    // int penalty = 0;

    // // Iterate through each teacher
    // for (Teacher teacher : schedual.getTeachers()) {
    // // Iterate through the schedual for each day and hour
    // for (int day = 0; day < Schedual.MaxDays; day++) {
    // for (int hour = 0; hour < Schedual.MaxHours; hour++) {
    // // Check if the teacher is not scheduled during their preferred hours
    // if (teacher.getHourPrefrences()[day][hour] &&
    // !schedual.IsTeacherScheduled(teacher, day, hour)) {
    // penalty += 5; // Penalize for not respecting preferred hours
    // }
    // }
    // }
    // }

    // return penalty;
    // }

    // private int penaltyForTeacherPreferredDays(Schedual schedual) {
    // int penalty = 0;

    // // Iterate through each teacher
    // for (Teacher teacher : schedual.getTeachers()) {
    // // Iterate through the schedual for each day
    // for (int day = 0; day < Schedual.MaxDays; day++) {
    // // Check if the teacher is not scheduled on their preferred days
    // if (teacher.getDayConstrains()[day] &&
    // !schedual.isTeacherScheduledOnDay(teacher, day)) {
    // penalty += 10; // Penalize for not respecting preferred days
    // }
    // }
    // }

    // return penalty;
    // }

    // public static int evaluateSchedule(Schedual schedual, List<Teacher> teachers)
    // {
    // int totalScore = 0;

    // Lesson[][] schedule = schedual.getSchedual();

    // for (int day = 0; day < Schedual.MaxDays; day++) {
    // for (int hour = 0; hour < Schedual.MaxHours; hour++) {
    // Lesson lesson = schedule[day][hour];

    // if (lesson != null) {
    // Teacher teacher = findTeacher(teachers, lesson.getTeacher());

    // if (teacher != null) {
    // // Check if the lesson's hour is within teacher's preferred hours
    // boolean isPreferredHour = teacher.getHourPrefrences()[day][hour];

    // // Check if the lesson is within teacher's day constraints
    // boolean isPreferredDay = teacher.getDayConstrains()[day];

    // if (isPreferredHour && isPreferredDay) {
    // // You can customize the scoring logic based on your requirements
    // totalScore += 10; // Add a positive score for meeting preferences
    // } else {
    // totalScore -= 10; // Deduct points for not meeting preferences
    // }
    // }
    // }
    // }
    // }

    // return totalScore;
    // }

    /*
     * 
     * public Schedual GeneticAlgorithm(List<Teacher> teachers) {
     * List<Schedual> population = new ArrayList<>();
     * for (int i = 0; i < POPULATION_SIZE; i++) {
     * Teacher t = new Teacher();
     * Schedual s = new Schedual();
     * s.InitSchedual();
     * s.FillScheduleWithLessons(t.GetLessonFromTeachers(teachers));
     * //todo
     * population.add(s);
     * }
     * 
     * for(Schedual s : population)
     * {
     * s.printSchedule();
     * }
     * List<Integer> fitnessValues = new ArrayList<>();
     * 
     * // Evolution
     * for (int generation = 1; generation <= MAX_GENERATIONS; generation++) {
     * // Evaluate fitness
     * for (Schedual schedual : population) {
     * fitnessValues.add(evaluateSchedule(schedual, teachers));
     * }
     * 
     * List<Schedual> parent1 = new ArrayList<>();
     * List<Schedual> parent2 = new ArrayList<>();
     * 
     * // Select parents for crossover
     * // (pairing them by there fitnessValues)
     * for (int i = 0; i < population.size(); i++) {
     * parent1.add(population.get(fitnessValues.indexOf(Collections.max(
     * fitnessValues))));
     * parent2.add(population.get(fitnessValues.indexOf(Collections.max(
     * fitnessValues))));
     * 
     * }
     * 
     * // Perform crossover and mutation to generate new population
     * List<Schedual> newPopulation = new ArrayList<>();
     * Random rand = new Random();
     * double randomNumber = rand.nextInt(11) / 10.0;
     * for (int i = 0; i < POPULATION_SIZE; i++) {
     * Schedual child = new Schedual();
     * child.crossover(parent1.get(i), parent2.get(i));
     * if (randomNumber < MUTATION_RATE) {
     * child.mutate();
     * }
     * newPopulation.add(child);
     * }
     * 
     * // Replace old population with new population
     * population = newPopulation;
     * 
     * // Output best schedule in this generation
     * int bestIndex = fitnessValues.indexOf(Collections.min(fitnessValues));
     * System.out.println("Generation " + generation + ": Best Fitness - " +
     * fitnessValues.get(bestIndex));
     * }
     * 
     * // Output final best schedule
     * int bestIndex = fitnessValues.indexOf(Collections.min(fitnessValues));
     * System.out.println("Final Best Schedule: " + population.get(bestIndex));
     * return population.get(bestIndex);
     * }
     */

    private Teacher findTeacher(List<Teacher> teachers, String teacherName) {
        for (Teacher teacher : teachers) {
            if (teacher.getName().equals(teacherName)) {
                return teacher;
            }
        }
        return null;
    }

    // public int evaluateSchedule(Schedual schedual, List<Teacher> teachers) {
    // int totalScore = 0;
    // int streak;
    // Lesson[][] schedule = schedual.getSchedual();
    // for (int day = 0; day < Schedual.MaxDays; day++) {
    // streak = 0;
    // for (int hour = 0; hour < Schedual.MaxHours; hour++) {
    // if(hour == (Schedual.LunchHour - Schedual.StartingHour))
    // {
    // continue;
    // }
    // Lesson lesson = schedule[day][hour];
    // if (lesson != null) {
    // Teacher teacher = findTeacher(teachers, lesson.getTeacher());
    // if (teacher != null) {
    // if (hour > 0) {
    // if (schedule[day][hour - 1] != null) {
    // if ( lesson.getTeacher() == schedule[day][hour - 1].getTeacher()) {
    // streak++;
    // } else {
    // streak = 0;
    // }
    // }
    // }
    // // Check if the lesson's hour is within teacher's preferred hours
    // boolean isPreferredHour = teacher.getHourPrefrences()[day][hour];
    // // Check if the lesson is within teacher's day constraints
    // boolean isPreferredDay = teacher.getDayConstrains()[day];
    // if (streak == 1)
    // totalScore += 5;
    // if (streak == 2)
    // totalScore += 10;
    // if (streak == 3)
    // totalScore += 5;
    // if (streak > 3)
    // totalScore -= streak*10;
    // if (isPreferredDay) {
    // totalScore += 10; // Deduct points for not meeting preferences
    // } else {
    // totalScore -= 15;
    // }
    // if (isPreferredHour) {
    // totalScore += 25; // Add a positive score for meeting preferences
    // } else {
    // totalScore -= 10;
    // }
    // }
    // }
    // }
    // }
    // return totalScore;
    // }
    // ===================================================================================================================================================================================
    // ===================================================================================================================================================================================

    public int evaluateSchedule(Schedual schedual, List<Teacher> teachers) {
        int totalScore = 0;
        int streak = 0;

        Lesson[][] schedule = schedual.getSchedual();

        for (int day = 0; day < Schedual.MaxDays; day++) {
            int count = 0;
            Map<String, Integer> teacherCountMap = new HashMap<>(); // Map to track teacher occurrences
            streak = 0; // Reset streak for each day

            for (int hour = 0; hour < Schedual.MaxHours; hour++) {
                if (hour == (Schedual.LunchHour - Schedual.StartingHour)) {
                    continue;
                }

                Lesson lesson = schedule[day][hour];

                if (lesson != null) {
                    Teacher teacher = findTeacher(teachers, lesson.getTeacher());

                    if (teacher != null) {
                        if (hour > 0) {
                            if (schedule[day][hour - 1] != null) {
                                if (lesson.getTeacher() == schedule[day][hour - 1].getTeacher()) {
                                    streak++;
                                } else {
                                    if (streak == 1)
                                        totalScore += 10;
                                    if (streak == 2)
                                        totalScore += 5;
                                    if (streak == 3)
                                        totalScore -= 15;
                                    if (streak > 3) {
                                        totalScore -= (streak+1) * 6;
                                        streak = 0;
                                    }
                                }
                            }
                        }
                        // Check if the lesson's hour is within teacher's preferred hours
                        boolean isPreferredHour = teacher.getHourPrefrences()[day][hour];

                        // Check if the lesson is within teacher's day constraints
                        boolean isDayAvaliable = teacher.getDayConstrains()[day];

                        // Count occurrences of the same teacher on the same day
                        count = teacherCountMap.getOrDefault(teacher.getName(), 0);
                        count++;
                        teacherCountMap.put(teacher.getName(), count);

                        if (isDayAvaliable) {
                            totalScore += 25;
                        } else {
                            totalScore -= 50;
                        }
                        if (isPreferredHour) {
                            totalScore += 30; // Add a positive score for meeting preferences
                        } else {
                            totalScore -= 50;
                        }
                    }
                }
            }

            // TODO add a loop that goes for all teacher in that day and chec the time that
            // they have been in that day then checks
            // how many hours they have in the preferences and evaluate by that

            // if (count ==1 ||count==2||count==3) {
            // totalScore += (count*5);
            // } else if (count >= 4) {
            // totalScore -= count * 15;
            // }

        }

        return totalScore;
    }

    public Schedual Geneticalgorithm(List<Teacher> teachers) {
        List<Schedual> population = initializePopulation(teachers);
        List<Integer> list = new ArrayList<>();
        // for (Schedual s : population) {
        // s.printSchedule();
        // }

        // Evolution
        for (int generation = 1; generation <= MAX_GENERATIONS; generation++) {
            // Evaluate fitness
            List<Integer> Evaluations = new ArrayList<>();
            for (Schedual schedual : population) {
                Evaluations.add(evaluateSchedule(schedual, teachers));
            }

            List<Schedual> newPopulation = new ArrayList<>();

            // Tournament selection for parents
            for (int i = 0; i < POPULATION_SIZE; i++) {
                if (i < (99 * (POPULATION_SIZE / 100))) {
                    Schedual parent1 = tournamentSelection(population, Evaluations);
                    Schedual parent2 = tournamentSelection(population, Evaluations);
                    while (parent2.equals(parent1)) {
                        parent2 = tournamentSelection(population, Evaluations);
                    }

                    // Perform crossover and mutation to generate new population
                    Schedual child = new Schedual();
                    child.crossover(parent1, parent2);

                    Random rand = new Random();
                    double randomNumber = rand.nextInt(11) / 10.0;

                    if (randomNumber < MUTATION_RATE) {
                        child.mutate();
                    }

                    newPopulation.add(child);
                } else if (i == (99 * (POPULATION_SIZE / 100))) {
                    list = getSortedIndexes(Evaluations);
                } else {

                    int in = i % (99 * (POPULATION_SIZE / 100));
                    newPopulation.add(population.get(list.get(in)));

                }
                // System.out.println(
                // "=========================================================================generation
                // : " + i
                // + "=======================");
                // int bestIndex = Evaluations.indexOf(Collections.max(Evaluations));
                // population.get(bestIndex).printScheduleteachers();
                // System.out.println(
                // "==============+=====================++===========================+++====================================++++================");
            }

            // Replace old population with new population
            population = newPopulation;

            // Output best schedule in this generation
            int bestIndex = Evaluations.indexOf(Collections.max(Evaluations));
            // population.get(bestIndex).printSchedule();
            System.out.println("Generation " + generation + ": Best Fitness : " + Evaluations.get(bestIndex));
            // population.get(bestIndex).printScheduleteachers();
        }

        // Output final best schedule
        int bestIndex = evaluateBestScheduleIndex(population, teachers);
        System.out.println("Final Best Schedule: ");
        System.out.println(
                "==========================================teachers deatails====================================");
        for (Teacher t : teachers) {
            System.out.println("===================================================" + t.getName()
                    + "========================================");
            t.printconstrains();
            System.out.println(
                    "=============================================================================================");
        }
        population.get(bestIndex).printScheduleteachers();
        int v = evaluateSchedule(population.get(bestIndex), teachers);
        System.out.println("Evaluation ; " + v);
        return population.get(bestIndex);
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

    private List<Schedual> initializePopulation(List<Teacher> teachers) {
        List<Schedual> population = new ArrayList<>();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            Teacher t = new Teacher();
            Schedual s = new Schedual();
            s.InitSchedual();
            s.FillScheduleWithLessons(t.GetLessonFromTeachers(teachers));
            population.add(s);
        }
        return population;
    }

    private int evaluateBestScheduleIndex(List<Schedual> population, List<Teacher> teachers) {
        List<Integer> fitnessValues = new ArrayList<>();
        for (Schedual schedual : population) {
            fitnessValues.add(evaluateSchedule(schedual, teachers));
        }
        return fitnessValues.indexOf(Collections.max(fitnessValues));
    }

    private Schedual tournamentSelection(List<Schedual> population, List<Integer> fitnessValues) {
        Random rand = new Random();

        // Select the first individual for the tournament randomly
        int index1 = rand.nextInt(population.size());
        Schedual candidate1 = population.get(index1);

        // Select the second individual for the tournament randomly, ensuring it's
        // different from the first
        int index2;
        do {
            index2 = rand.nextInt(population.size());
        } while (index2 == index1);
        Schedual candidate2 = population.get(index2);

        // Choose the individual with the highest fitness in the tournament
        int fitness1 = fitnessValues.get(index1);
        int fitness2 = fitnessValues.get(index2);

        return (fitness1 > fitness2) ? candidate1 : candidate2;
    }

}

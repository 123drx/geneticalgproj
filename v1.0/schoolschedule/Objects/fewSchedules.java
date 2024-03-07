package danielgras.schoolschedule.Objects;

import java.util.List;

public class fewSchedules {
    List<Schedual> scheduals;

    public fewSchedules(List<Schedual> scheduals)
    {
        this.scheduals = scheduals;
    }

    public List<Schedual> getScheduals() {
        return scheduals;
    }

    public void addSchedual(Schedual schedual)
    {
    this.scheduals.add(schedual);
    }

    public void setScheduals(List<Schedual> scheduals) {
        this.scheduals = scheduals;
    }


    


    
}

package frc.robot.subsystems;

import java.util.ArrayList;

public class SubSystemManager implements SubSystem{
    final ArrayList<SubSystem> subSystems;
    
    public SubSystemManager(SubSystem... subSystems){
        this.subSystems = new ArrayList<>();
        for(SubSystem system  : subSystems){
            this.subSystems.add(system);
        }
    }
    public SubSystemManager(){
        this.subSystems = new ArrayList<>(); 
    }

    @Override
    public void init() {
        for(SubSystem system : subSystems){
            system.init();
        }
    }

    @Override
    public void execute(RobotState state) {
        for(SubSystem system  : subSystems){
            system.execute(state);
        }
    }

    public void add(SubSystem subSystem){
        subSystems.add(subSystem);
    }
}
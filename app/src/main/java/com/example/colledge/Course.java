package com.example.colledge;

import java.io.Serializable;

public class Course implements Serializable {
    private Assignment[] asg;
    private double[] typeWeights;
    private String[] typeNames;
    private String name;
    private char letterGrade;
    private double grade;
    private int numTypes;
    private int numAssigns;
    private int currAsg;

    public Course(String n){
        name = n;
        typeWeights = new double[15];
        typeNames = new String[15];
        letterGrade = 'F';
        grade = 0;
        asg = new Assignment[50];
        numTypes = 0;
        numAssigns = 0;
        currAsg = 0;
    }

    public boolean addGradeType(String n, double w){
        for(int i=0;i<typeNames.length;i++)
        {
            if(typeNames[i].equals(n))
                return false;
        }
        typeWeights[numTypes] = w;
        typeNames[numTypes] = n;
        numTypes++;
        return true;
    }

    public void addAssignment(String t,String n, String possible, String earned){
        double p = Double.parseDouble(possible);
        double e = Double.parseDouble(earned);
        asg[numAssigns] = new Assignment(t, n, p, e);
        numAssigns++;
        if(numAssigns == asg.length){
            Assignment[] newasg = new Assignment[asg.length*2];
            for(int i=0;i<asg.length;i++)
            {
                newasg[i] = asg[i];
            }
            asg = newasg;
        }
    }

    public void removeAssignment(String n){
        for(int i=0;i<numAssigns;i++) {
            if (asg[i].name.equals(n)) {
                for (int j = i; j < asg.length - 2; j++) {
                    asg[j] = asg[j + 1];
                }
                asg[asg.length - 1] = null;
            }

        }
    }

    public double calcGrade(){
        if(numAssigns ==0)
            return 0;

        double runningTotal;
        double[] typePoss = new double[numTypes];
        double[] typeEarned = new double[numTypes];

        for(int i=0; i<numAssigns;i++){
            for(int j=0;j<numTypes;j++) {
                if (asg[i].type.equals(typeNames[j])) {
                    typePoss[j] += asg[i].possible;
                    typeEarned[j] += asg[i].earned;
                    break;
                }
            }
        }
        runningTotal = 0;
        for(int i=0;i<numTypes;i++)
        {
            runningTotal = runningTotal + ((typeEarned[i]/typePoss[i])*typeWeights[i]);
        }

        if(runningTotal<60)
            letterGrade = 'F';
        if(runningTotal<70)
            letterGrade='D';
        if(runningTotal<80)
            letterGrade='C';
        if(runningTotal<90)
            letterGrade='B';
        else
            letterGrade='A';


        return runningTotal;
    }

    public String getName(){
        return name;
    }

    public int getNumTypes(){
        return numTypes;
    }

    public int getNumAssigns(){
        return numAssigns;
    }

    public String toString(){
        return name+ "\n" + calcGrade() + "\n" + letterGrade;

    }

    public String allAssignments(){
        String a = "Name\tType\tPossible\tEarned\n";
        if(numAssigns == 0)
            return a + "N/A";
        for (int i=0; i<numAssigns;i++){
            a += asg[i].name + "\t" + asg[i].type + "\t" + asg[i].possible + "\t" + asg[i].earned + "\n";
        }
        return a;
    }

    public String getCurrAsg(){
        if(numAssigns==0)
            return "N/A";
        return asg[currAsg].name + "\t" + asg[currAsg].type + "\t" + asg[currAsg].possible + "\t" +asg[currAsg].earned;
    }

    public String nextAsg(){
        currAsg++;
        if(currAsg==numAssigns)
            currAsg = 0;
        return getCurrAsg();
    }

    public String prevAsg(){
        currAsg--;
        if(currAsg==-1)
            currAsg = numAssigns;
        return getCurrAsg();
    }

    public void removeCurrAssignment(){
        removeAssignment(asg[currAsg].name);
    }
}


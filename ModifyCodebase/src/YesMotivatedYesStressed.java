import java.util.Random;

public class YesMotivatedYesStressed extends IState
{
    private Student student = Student.getInstance();

    @Override
    public void doSchoolwork()
    {
        student.addNewIntelligenceLevel();
        System.out.println("You are still stressed but wiser");
    }

    @Override
    public void doHobby()
    {
        student.setStressed(false);
        student.subtractMotivationLevel();
        System.out.println("You are no longer stressed, but your motivation has dropped a little"); // Fixed typo
        student.setState(new YesMotivatedNotStressed());
    }

    @Override
    public void takeTest()
    {
        student.setStressed(true);
        int chanceToPass = student.getIntelligenceLevel() + student.getMotivationLevel();
        Random rn = new Random();
        int chanceAgainstYou = rn.nextInt(20) + 1;

        if(chanceToPass == chanceAgainstYou)
        {
            student.increaseCompletedTests();
            student.subtractMotivationLevel();
            student.setIntelligenceLevel(0);
            student.setPassedATest(true);
        }
        else
        {
            student.setMotivationLevel(0);
            student.setIntelligenceLevel(0);
            student.setPassedATest(false);
            student.setState(new NotMotivatedYesStressed());
        }
    }
}

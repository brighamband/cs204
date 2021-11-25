public class NotMotivatedNotStressed extends IState
{
    private Student student = Student.getInstance();

    @Override
    public void doSchoolwork()
    {
        student.addNewIntelligenceLevel();      // Added line - make sure to increase intelligence for doing schoolwork
        student.setStressed(true);
        System.out.println("You are now stressed but wiser");
        student.setState(new NotMotivatedYesStressed());
    }

    @Override
    public void inspireSelf()
    {
        student.addNewMotivationalLevel();
        System.out.println("You are now a little more motivated");
        student.setState(new YesMotivatedNotStressed());
    }
}

package by.epam.like_it.controller.command;

import by.epam.like_it.controller.util.CommandLoader;


import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

    private static CommandFactory ourInstance = new CommandFactory();
    private Map<String,Command> commandMap=new HashMap<>();

    public static CommandFactory getInstance() {
        return ourInstance;
    }

    public Command getCommand(String commandType){
        return commandMap.get(commandType);
    }

    private CommandFactory() {

       commandMap= CommandLoader.getInstance().getCommands();

//       commandMap.put("question_view",new StartCommand());
//       commandMap.put("question_details", new QuestionDetailsCommand());
//       commandMap.put("sign_in",new SignInCommand());
//       commandMap.put("sign_out",new SignOutCommand());
//       commandMap.put("sign_up",new GoToSignUpCommand());
//       commandMap.put("sign_up_user",new SignUpUserCommand());
//       commandMap.put("add_mark",new AddMarkCommand());
//       commandMap.put("ask",new GoToQuestionFormCommand());
//       commandMap.put("change_lang",new ChangeLanguageCommand());
//       commandMap.put("add_question", new AddQuestionCommand());
//       commandMap.put("answer",new AddAnswerCommand());
//       commandMap.put("set_solution",new SetSolutionCommand());
//       commandMap.put("edit_question_form", new GoToQuestionEditCommand());
//       commandMap.put("edit_question",new EditQuestionCommand());
//       commandMap.put("delete_question",new DeleteQuestionCommand());
//       commandMap.put("delete_answer",new DeleteAnswerCommand());
//       commandMap.put("edit_answer", new EditAnswerCommand());
//       commandMap.put("edit_answer_form", new GoToAnswerEditCommand());
//       commandMap.put("user_details", new GoToUserDetailsCommand());
//       commandMap.put("admin_menu", new GoToAdminMenuCommand());
//       commandMap.put("add_user",new AddUserCommand());
//       commandMap.put("edit_user_form",new GoToEditUserFormCommand());
//       commandMap.put("edit_user", new EditUserCommand());
//       commandMap.put("delete_user", new DeleteUserCommand());
//       commandMap.put("edit_tag_form", new GoToEditTagFormCommand());
//       commandMap.put("edit_tag",new EditTagCommand());
//       commandMap.put("add_tag", new AddTagCommand());
//       commandMap.put("delete_tag",new DeleteTagCommand());
    }
}
